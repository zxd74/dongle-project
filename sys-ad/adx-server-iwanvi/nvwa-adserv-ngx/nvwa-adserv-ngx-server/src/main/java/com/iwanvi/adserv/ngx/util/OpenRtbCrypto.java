/*
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iwanvi.adserv.ngx.util;

import static java.lang.Math.min;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encryption and decryption support for the DoubleClick Ad Exchange RTB
 * protocol.
 * <p>
 * Encrypted payloads are wrapped by "packages" in the general format: <code>
* initVector:16 || E(payload:?) || I(signature:4)
* </code> <br>
 * where:
 * <ol>
 * <li>{@code initVector = timestamp:8 || serverId:8} (AdX convention)</li>
 * <li>{@code E(payload) = payload ^ hmac(encryptionKey, initVector)} per
 * max-20-byte block</li>
 * <li>{@code I(signature) = hmac(integrityKey, payload || initVector)[0..3]}</li>
 * </ol>
 * <p>
 * This class, and all nested classes / subclasses, are threadsafe.
 */
public class OpenRtbCrypto {
	private static final Logger logger = LoggerFactory.getLogger(OpenRtbCrypto.class);
	public static final String KEY_ALGORITHM = "HmacSHA1";

	/** Initialization vector offset in the crypto package. */
	public static final int INITV_BASE = 0;
	/** Initialization vector size. */
	public static final int INITV_SIZE = 16;
	/** Timestamp subfield offset in the initialization vector. */
	public static final int INITV_TIMESTAMP_OFFSET = 0;
	/** ServerId subfield offset in the initialization vector. */
	public static final int INITV_SERVERID_OFFSET = 8;
	/** Payload offset in the crypto package. */
	public static final int PAYLOAD_BASE = INITV_BASE + INITV_SIZE;
	/** Integrity signature size. */
	public static final int SIGNATURE_SIZE = 4;
	/** Overhead (non-Payload data) total size. */
	public static final int OVERHEAD_SIZE = INITV_SIZE + SIGNATURE_SIZE;

	private static final int COUNTER_PAGESIZE = 20;
	private static final int COUNTER_SECTIONS = 3 * 256 + 1;

	private static final int MICROS_PER_CURRENCY_UNIT = 1_000_000;
	
	public static final int BYTES = Integer.SIZE / Byte.SIZE;

	private final Keys keys;
	private final ThreadLocalRandom fastRandom = ThreadLocalRandom.current();

	/**
	 * Initializes with the encryption keys.
	 *
	 * @param keys Keys for the buyer's Ad Exchange account
	 */
	public OpenRtbCrypto(Keys keys) {
		this.keys = keys;
	}

	/**
	 * Decodes data, from string to binary form. The default implementation performs
	 * websafe-base64 decoding (RFC 3548).
	 */
	protected byte[] decode(String data) {
		return data == null ? null : Base64.decodeBase64(data);
	}

	/**
	 * Encodes data, from binary form to string. The default implementation performs
	 * websafe-base64 encoding (RFC 3548).
	 */
	protected String encode(byte[] data) {
		return data == null ? null : Base64.encodeBase64URLSafeString(data);
	}

	/**
	 * Decrypts data.
	 *
	 * @param cipherData {@code initVector || E(payload) || I(signature)}
	 * @return {@code initVector || payload || I'(signature)} Where I'(signature) ==
	 *         I(signature) for success, different for failure
	 */
	public byte[] decrypt(byte[] cipherData) throws SignatureException {
		checkArgument(cipherData.length >= OVERHEAD_SIZE, "Invalid cipherData, %s bytes", 
				cipherData.length);

		// workBytes := initVector || E(payload) || I(signature)
		byte[] workBytes = cipherData.clone();
		ByteBuffer workBuffer = ByteBuffer.wrap(workBytes);
		boolean success = false;

		try {
			// workBytes := initVector || payload || I(signature)
			xorPayloadToHmacPad(workBytes);
			// workBytes := initVector || payload || I'(signature)
			int confirmationSignature = hmacSignature(workBytes);
			int integritySignature = workBuffer.getInt(workBytes.length - SIGNATURE_SIZE);
			workBuffer.putInt(workBytes.length - SIGNATURE_SIZE, confirmationSignature);

			if (confirmationSignature != integritySignature) {
				throw new SignatureException("Signature mismatch: " + Integer.toHexString(confirmationSignature)
						+ " vs " + Integer.toHexString(integritySignature));
			}

			if (logger.isDebugEnabled()) {
				logger.debug(dump("Decrypted", cipherData, workBytes));
			}

			success = true;
			return workBytes;
		} finally {
			if (!success && logger.isDebugEnabled()) {
				logger.debug(dump("Decrypted (failed)", cipherData, workBytes));
			}
		}
	}

	/**
	 * Encrypts data.
	 *
	 * @param plainData {@code initVector || payload || zeros:4}
	 * @return {@code initVector || E(payload) || I(signature)}
	 */
	public byte[] encrypt(byte[] plainData) {
		checkArgument(plainData.length >= OVERHEAD_SIZE, "Invalid plainData, %d bytes", plainData.length);

		// workBytes := initVector || payload || zeros:4
		byte[] workBytes = plainData.clone();
		ByteBuffer workBuffer = ByteBuffer.wrap(workBytes);
		boolean success = false;

		try {
			// workBytes := initVector || payload || I(signature)
			int signature = hmacSignature(workBytes);
			workBuffer.putInt(workBytes.length - SIGNATURE_SIZE, signature);
			// workBytes := initVector || E(payload) || I(signature)
			xorPayloadToHmacPad(workBytes);

			if (logger.isDebugEnabled()) {
				logger.debug(dump("Encrypted", plainData, workBytes));
			}

			success = true;
			return workBytes;
		} finally {
			if (!success && logger.isDebugEnabled()) {
				logger.debug(dump("Encrypted (failed)", plainData, workBytes));
			}
		}
	}

	/**
	 * Creates the initialization vector from component
	 * {@code (timestamp, serverId)} fields. This is the format used by DoubleClick,
	 * and it's a good format generally, even though the initialization vector can
	 * be any random data (a cryptographic nonce).
	 * <p>
	 * NOTE: Follow the advice from
	 * https://developers.google.com/ad-exchange/rtb/response-guide/decrypt-price#detecting_stale
	 * by using a high-resolution timestamp; also if the {@code serverId} is not
	 * necessary, providing a random value there helps further to prevent replay
	 * attacks. In all methods that have a {@code initVector} parameter, passing
	 * null will cause {@code (current time, random)} to be used (so if you really
	 * want all-zeros {@code initVector}, e.g. in unit tests to make results
	 * reproducible, pass a zero-filled array).
	 */
	public byte[] createInitVector(Date timestamp, long serverId) {
		byte[] initVector = new byte[INITV_SIZE];
		ByteBuffer byteBuffer = ByteBuffer.wrap(initVector);

		if (timestamp != null) {
			byteBuffer.putLong(INITV_TIMESTAMP_OFFSET, timestamp.getTime());
		}

		byteBuffer.putLong(INITV_SERVERID_OFFSET, serverId);
		return initVector;
	}

	/**
	 * Returns the {@code timestamp} field from encrypted or decrypted data. Assumes
	 * that its initialization vector has the structure
	 * {@code (timestamp, serverId)}.
	 *
	 * @param data Encrypted or decrypted data (the initialization vector is never
	 *             encrypted)
	 * @return Timestamp subfield of the initialization vector.
	 */
	public Date getTimestamp(byte[] data) {
		return new Date(ByteBuffer.wrap(data).getLong(INITV_BASE + INITV_TIMESTAMP_OFFSET));
	}

	/**
	 * Returns the {@code serverId} field from encrypted or decrypted data. Assumes
	 * that its initialization vector has the structure
	 * {@code (timestamp, serverId)}.
	 *
	 * @param data Encrypted or decrypted data (the initialization vector is never
	 *             encrypted)
	 * @return Timestamp subfield of the initialization vector.
	 */
	public long getServerId(byte[] data) {
		return ByteBuffer.wrap(data).getLong(INITV_BASE + INITV_SERVERID_OFFSET);
	}

	/**
	 * Packages plaintext payload for encryption; returns
	 * {@code initVector || payload || zeros:4}.
	 */
	protected byte[] initPlainData(int payloadSize, byte[] initVector) {
		byte[] plainData = new byte[OVERHEAD_SIZE + payloadSize];

		if (initVector == null) {
			ByteBuffer byteBuffer = ByteBuffer.wrap(plainData);
			byteBuffer.putLong(INITV_TIMESTAMP_OFFSET, System.nanoTime());
			byteBuffer.putLong(INITV_SERVERID_OFFSET, fastRandom.nextLong());
		} else {
			System.arraycopy(initVector, 0, plainData, INITV_BASE, min(INITV_SIZE, initVector.length));
		}

		return plainData;
	}

	/**
	 * {@code payload = payload ^ hmac(encryptionKey, initVector || counterBytes)}
	 * per max-20-byte blocks.
	 */
	private void xorPayloadToHmacPad(byte[] workBytes) {
		int payloadSize = workBytes.length - OVERHEAD_SIZE;
		int sections = (payloadSize + COUNTER_PAGESIZE - 1) / COUNTER_PAGESIZE;
		checkArgument(sections <= COUNTER_SECTIONS, "Payload is %s bytes, exceeds limit of %s", payloadSize,
				COUNTER_PAGESIZE * COUNTER_SECTIONS);

		Mac encryptionHmac = createMac();

		byte[] pad = new byte[COUNTER_PAGESIZE + 3];
		int counterSize = 0;

		for (int section = 0; section < sections; ++section) {
			int sectionBase = section * COUNTER_PAGESIZE;
			int sectionSize = min(payloadSize - sectionBase, COUNTER_PAGESIZE);

			try {
				encryptionHmac.reset();
				encryptionHmac.init(keys.getEncryptionKey());
				encryptionHmac.update(workBytes, INITV_BASE, INITV_SIZE);
				if (counterSize != 0) {
					encryptionHmac.update(pad, COUNTER_PAGESIZE, counterSize);
				}
				encryptionHmac.doFinal(pad, 0);
			} catch (ShortBufferException | InvalidKeyException e) {
				throw new IllegalStateException(e);
			}

			for (int i = 0; i < sectionSize; ++i) {
				workBytes[PAYLOAD_BASE + sectionBase + i] ^= pad[i];
			}

			Arrays.fill(pad, 0, COUNTER_PAGESIZE, (byte) 0);

			if (counterSize == 0 || ++pad[COUNTER_PAGESIZE + counterSize - 1] == 0) {
				++counterSize;
			}
		}
	}

	private static void checkArgument(boolean b, String format, Object... args) {
		if (!b)
			throw new IllegalArgumentException(String.format(format, args));
	}

	private static Mac createMac() {
		try {
			return Mac.getInstance("HmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}

	 public static int fromByteArray(byte[] bytes) {
		    checkArgument(bytes.length >= BYTES,
		        "array too small: %s < %s", bytes.length, BYTES);
		    return fromBytes(bytes[0], bytes[1], bytes[2], bytes[3]);
    }
	 
	 public static int fromBytes(byte b1, byte b2, byte b3, byte b4) {
		    return b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | (b4 & 0xFF);
		    }
	 
	/**
	 * {@code signature = hmac(integrityKey, payload || initVector)}
	 */
	private int hmacSignature(byte[] workBytes) {
		try {
			Mac integrityHmac = createMac();
			integrityHmac.init(keys.getIntegrityKey());
			integrityHmac.update(workBytes, PAYLOAD_BASE, workBytes.length - OVERHEAD_SIZE);
			integrityHmac.update(workBytes, INITV_BASE, INITV_SIZE);
			return fromByteArray(integrityHmac.doFinal());
		} catch (InvalidKeyException e) {
			throw new IllegalStateException(e);
		}
	}

	private static String dump(String header, byte[] inData, byte[] workBytes) {
		ByteBuffer initvBuffer = ByteBuffer.wrap(workBytes, INITV_BASE, INITV_SIZE);
		Date timestamp = new Date(initvBuffer.getLong(INITV_BASE + INITV_TIMESTAMP_OFFSET));
		long serverId = initvBuffer.getLong(INITV_BASE + INITV_SERVERID_OFFSET);
		
		return new StringBuilder().append(header).append(": initVector={timestamp ")
				.append(DateFormat.getDateTimeInstance().format(timestamp)).append(", serverId ").append(serverId)
				.append("}, input =").append(Hex.encodeHex(inData)).append(", output =")
				.append(Hex.encodeHex(workBytes)).toString();
	}

	@Override
	public String toString() {
		return null;
		//return MoreObjects.toStringHelper(this).omitNullValues().add("keys", keys).toString();
	}

	/**
	 * Holds the keys used to configure DoubleClick cryptography.
	 */
	public static class Keys {
		private final SecretKey encryptionKey;
		private final SecretKey integrityKey;

		public Keys(SecretKey encryptionKey, SecretKey integrityKey) throws InvalidKeyException {
			this.encryptionKey = encryptionKey;
			this.integrityKey = integrityKey;

			// Forces early failure if any of the keys are not good.
			// This allows us to spare callers from InvalidKeyException in several methods.
			Mac hmac = OpenRtbCrypto.createMac();
			hmac.init(encryptionKey);
			hmac.reset();
			hmac.init(integrityKey);
			hmac.reset();
		}

		public SecretKey getEncryptionKey() {
			return encryptionKey;
		}

		public SecretKey getIntegrityKey() {
			return integrityKey;
		}

		@Override
		public int hashCode() {
			return encryptionKey.hashCode() ^ integrityKey.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			} else if (!(obj instanceof Keys)) {
				return false;
			}
			Keys other = (Keys) obj;
			return encryptionKey.equals(other.encryptionKey) && integrityKey.equals(other.integrityKey);
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
			/*
			 * return MoreObjects.toStringHelper(this).omitNullValues()
			 * .add("encryptionKey", encryptionKey.getAlgorithm() + '/' +
			 * encryptionKey.getFormat()) .add("integrityKey", integrityKey.getAlgorithm() +
			 * '/' + integrityKey.getFormat()).toString();
			 */
		}
	}

	/**
	 * Encryption for winning price.
	 * <p>
	 * See <a href=
	 * "https://developers.google.com/ad-exchange/rtb/response-guide/decrypt-price">
	 * Decrypting Price Confirmations</a>.
	 */
	public static class Price extends OpenRtbCrypto {
		private static final int PAYLOAD_SIZE = 8;

		public Price(Keys keys) {
			super(keys);
		}

		/**
		 * Encrypts the winning price.
		 *
		 * @param priceValue the price in micros (1/1.000.000th of the currency unit)
		 * @param initVector up to 16 bytes of nonce data
		 * @return encrypted price
		 * @see #createInitVector(Date, long)
		 */
		public byte[] encryptPriceMicros(long priceValue, byte[] initVector) {
			byte[] plainData = initPlainData(PAYLOAD_SIZE, initVector);
			ByteBuffer.wrap(plainData).putLong(PAYLOAD_BASE, priceValue);
			return encrypt(plainData);
		}

		/**
		 * Decrypts the winning price.
		 *
		 * @param priceCipher encrypted price
		 * @return the price value in micros (1/1.000.000th of the currency unit)
		 */
		public long decryptPriceMicros(byte[] priceCipher) throws SignatureException {
			checkArgument(priceCipher.length == (OVERHEAD_SIZE + PAYLOAD_SIZE), "Price is %s bytes, should be %s",
					priceCipher.length, (OVERHEAD_SIZE + PAYLOAD_SIZE));

			byte[] plainData = decrypt(priceCipher);
			return ByteBuffer.wrap(plainData).getLong(PAYLOAD_BASE);
		}

		/**
		 * Encrypts and encodes the winning price.
		 *
		 * @param priceMicros the price in micros (1/1.000.000th of the currency unit)
		 * @param initVector  up to 16 bytes of nonce data, or {@code null} for default
		 *                    generated data (see {@link #createInitVector(Date, long)}
		 * @return encrypted price, encoded as websafe-base64
		 */
		public String encodePriceMicros(long priceMicros,  byte[] initVector) {
			return encode(encryptPriceMicros(priceMicros, initVector));
		}

		/**
		 * Encrypts and encodes the winning price.
		 *
		 * @param priceValue the price
		 * @param initVector up to 16 bytes of nonce data, or {@code null} for default
		 *                   generated data (see {@link #createInitVector(Date, long)}
		 * @return encrypted price, encoded as websafe-base64
		 */
		public String encodePriceValue(double priceValue, byte[] initVector) {
			return encodePriceMicros((long) (priceValue * MICROS_PER_CURRENCY_UNIT), initVector);
		}

		/**
		 * Decodes and decrypts the winning price.
		 *
		 * @param priceCipher encrypted price, encoded as websafe-base64
		 * @return the price value in micros (1/1.000.000th of the currency unit)
		 */
		public long decodePriceMicros(String priceCipher) throws SignatureException {
			return decryptPriceMicros(decode(priceCipher));
		}

		/**
		 * Decodes and decrypts the winning price.
		 *
		 * @param priceCipher encrypted price, encoded as websafe-base64
		 * @return the price value
		 */
		public double decodePriceValue(String priceCipher) throws SignatureException {
			return decodePriceMicros(priceCipher) / ((double) MICROS_PER_CURRENCY_UNIT);
		}
	}

	/**
	 * Encryption for Advertising ID.
	 * <p>
	 * See <a href=
	 * "https://developers.google.com/ad-exchange/rtb/response-guide/decrypt-advertising-id">
	 * Decrypting Advertising ID</a>.
	 */
	public static class AdId extends OpenRtbCrypto {
		private static final int PAYLOAD_SIZE = 16;

		public AdId(Keys keys) {
			super(keys);
		}

		/**
		 * Encrypts the Advertising Id.
		 *
		 * @param adidPlain  the AdId
		 * @param initVector up to 16 bytes of nonce data, or {@code null} for default
		 *                   generated data (see {@link #createInitVector(Date, long)}
		 * @return encrypted AdId
		 */
		public byte[] encryptAdId(byte[] adidPlain, byte[] initVector) {
			checkArgument(adidPlain.length == PAYLOAD_SIZE, "AdId is %s bytes, should be %s", adidPlain.length,
					PAYLOAD_SIZE);

			byte[] plainData = initPlainData(PAYLOAD_SIZE, initVector);
			System.arraycopy(adidPlain, 0, plainData, PAYLOAD_BASE, PAYLOAD_SIZE);
			return encrypt(plainData);
		}

		/**
		 * Decrypts the AdId.
		 *
		 * @param adidCipher encrypted AdId
		 * @return the AdId
		 */
		public byte[] decryptAdId(byte[] adidCipher) throws SignatureException {
			checkArgument(adidCipher.length == (OVERHEAD_SIZE + PAYLOAD_SIZE), "AdId is %s bytes, should be %s",
					adidCipher.length, (OVERHEAD_SIZE + PAYLOAD_SIZE));

			byte[] plainData = decrypt(adidCipher);
			return Arrays.copyOfRange(plainData, PAYLOAD_BASE, plainData.length - SIGNATURE_SIZE);
		}
	}

	/**
	 * Encryption for IDFA.
	 * <p>
	 * See <a href="https://support.google.com/adxbuyer/answer/3221407"> Targeting
	 * mobile app inventory with IDFA</a>.
	 */
	public static class Idfa extends OpenRtbCrypto {
		public Idfa(Keys keys) {
			super(keys);
		}

		/**
		 * Encrypts the IDFA.
		 *
		 * @param idfaPlain  the IDFA
		 * @param initVector up to 16 bytes of nonce data, or {@code null} for default
		 *                   generated data (see {@link #createInitVector(Date, long)}
		 * @return encrypted IDFA
		 */
		public byte[] encryptIdfa(byte[] idfaPlain, byte[] initVector) {
			byte[] plainData = initPlainData(idfaPlain.length, initVector);
			System.arraycopy(idfaPlain, 0, plainData, PAYLOAD_BASE, idfaPlain.length);
			return encrypt(plainData);
		}

		/**
		 * Decrypts the IDFA.
		 *
		 * @param idfaCipher encrypted IDFA
		 * @return the IDFA
		 */
		public byte[] decryptIdfa(byte[] idfaCipher) throws SignatureException {
			byte[] plainData = decrypt(idfaCipher);
			return Arrays.copyOfRange(plainData, PAYLOAD_BASE, plainData.length - SIGNATURE_SIZE);
		}

		/**
		 * Encrypts and encodes the IDFA.
		 *
		 * @param idfaPlain  the IDFA
		 * @param initVector up to 16 bytes of nonce data, or {@code null} for default
		 *                   generated data (see {@link #createInitVector(Date, long)}
		 * @return encrypted IDFA, websafe-base64 encoded
		 */
		public String encodeIdfa(byte[] idfaPlain, byte[] initVector) {
			return encode(encryptIdfa(idfaPlain, initVector));
		}

		/**
		 * Decodes and decrypts the IDFA.
		 *
		 * @param idfaCipher encrypted IDFA, websafe-base64 encoded
		 * @return the IDFA
		 */
		public byte[] decodeIdfa(String idfaCipher) throws SignatureException {
			return decryptIdfa(decode(idfaCipher));
		}
	}

	/**
	 * Encryption for {@code HyperlocalSet} geofence information.
	 * <p>
	 * See <a href=
	 * "https://developers.google.com/ad-exchange/rtb/response-guide/decrypt-hyperlocal">
	 * Decrypting Hyperlocal Targeting Signals</a>.
	 */
	public static class Hyperlocal extends OpenRtbCrypto {
		public Hyperlocal(Keys keys) {
			super(keys);
		}

		/**
		 * Encrypts the serialized {@code HyperlocalSet}.
		 *
		 * @param hyperlocalPlain the {@code HyperlocalSet}
		 * @param initVector      up to 16 bytes of nonce data, or {@code null} for
		 *                        default generated data (see
		 *                        {@link #createInitVector(Date, long)}
		 * @return encrypted {@code HyperlocalSet}
		 */
		public byte[] encryptHyperlocal(byte[] hyperlocalPlain, byte[] initVector) {
			byte[] plainData = initPlainData(hyperlocalPlain.length, initVector);
			System.arraycopy(hyperlocalPlain, 0, plainData, PAYLOAD_BASE, hyperlocalPlain.length);
			return encrypt(plainData);
		}

		/**
		 * Decrypts the serialized {@code HyperlocalSet}.
		 *
		 * @param hyperlocalCipher encrypted {@code HyperlocalSet}
		 * @return the {@code HyperLocalSet}
		 */
		public byte[] decryptHyperlocal(byte[] hyperlocalCipher) throws SignatureException {
			byte[] plainData = decrypt(hyperlocalCipher);
			return Arrays.copyOfRange(plainData, PAYLOAD_BASE, plainData.length - SIGNATURE_SIZE);
		}
	}
	
	public static void main(String[] args) throws Exception {
		//YWJjMTIzZGVmNDU2Z2hpN7fhCuPemCce_6msaw 100
		//YWJjMTIzZGVmNDU2Z2hpN7fhCuPemCAWJRxOgA 1900
		//YWJjMTIzZGVmNDU2Z2hpN7fhCuPemC32prpWWw 2700
		
		String ekey = "1c3c324024e3457b8573fdf26d31a903";
		@SuppressWarnings("unused")
		String ikey="40ef1bdcbd7b410e1407ebdd0ed49574";
		
		System.out.println(ekey.getBytes().length);
		System.out.println();
		//skU7Ax_NL5pPAFyKdkfZjZz2-VhIN8bjj1rVFOaJ_5o=  // Encryption key (e_key)
		//arO23ykdNqUQ5LEoQ0FVmPkBd7xB5CO89PDZlSjpFxo=  // Integrity key (i_key)
		
		
		String b64_ekey="skU7Ax_NL5pPAFyKdkfZjZz2-VhIN8bjj1rVFOaJ_5o=";
		String b64_ikey="arO23ykdNqUQ5LEoQ0FVmPkBd7xB5CO89PDZlSjpFxo=";
		
		byte[] ekeyBytes = Base64.decodeBase64(b64_ekey);
		byte[] ikeyBytes = Base64.decodeBase64(b64_ikey);
		
		System.out.println(ekeyBytes.length);
		System.out.println(ikeyBytes.length);
		
		ekey = Hex.encodeHexString(Base64.decodeBase64("skU7Ax_NL5pPAFyKdkfZjZz2-VhIN8bjj1rVFOaJ_5o="));
		ikey = Hex.encodeHexString(Base64.decodeBase64("arO23ykdNqUQ5LEoQ0FVmPkBd7xB5CO89PDZlSjpFxo="));
		
		//System.out.println("ekey: "+ekey+", ikey: "+ikey);
		String algorithm = "HmacSHA1";
		SecretKeySpec e_key = new SecretKeySpec(ekeyBytes, algorithm);
		SecretKeySpec i_key = new SecretKeySpec(ikeyBytes,algorithm);
		
		Price price = new Price(new Keys(e_key, i_key));
		long p = price.decodePriceMicros("YWJjMTIzZGVmNDU2Z2hpN7fhCuPemCce_6msaw");
		System.out.println(p);
		
		String er = price.encodePriceMicros(100, null);
		System.out.println("encode result: "+er);
		
		System.out.println(price.decodePriceMicros(er));
	}
}

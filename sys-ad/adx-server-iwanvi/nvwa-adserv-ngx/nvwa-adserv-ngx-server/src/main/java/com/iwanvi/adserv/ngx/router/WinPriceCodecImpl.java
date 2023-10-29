/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.router;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 默认价格加解密算法实现，加密算法采用AES 128位的ECB模式，padding方式为 PKCS5
 * 
 * @author weiping wang
 *
 */
public class WinPriceCodecImpl implements WinPriceCodec {
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	private static final String ALGORITHM = "AES";

	@Override
	public double decode(String price, String token) {
		try {
			byte[] encryptBytes = Base64.decodeBase64(price);
			byte[] decodeBytes = codec(encryptBytes, Hex.decodeHex(token), Cipher.DECRYPT_MODE);
			return Double.parseDouble(new String(decodeBytes));
		} catch (DecoderException ex) {
			// ignore this exception
		}
		return -1d;
	}

	@Override
	public String encode(double price, String token) {
		try {
			String strPrice = Double.toString(price);
			byte[] encryptBytes = codec(strPrice.getBytes(Charsets.UTF_8), Hex.decodeHex(token), Cipher.ENCRYPT_MODE);
			return Base64.encodeBase64URLSafeString(encryptBytes);
		} catch (Exception ex) {
			// ignore this exception
		}
		return null;
	}

	private static byte[] codec(byte[] value, byte[] key, int mode) {
		try {
			SecretKeySpec securekey = new SecretKeySpec(key, ALGORITHM);
			Cipher encryptor = Cipher.getInstance(CIPHER_ALGORITHM);
			encryptor.init(mode, securekey);
			byte[] encryptBytes = encryptor.doFinal(value);
			return encryptBytes;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void main(String[] args) throws Exception {
		double price = 123456;

		WinPriceCodec codec = new WinPriceCodecImpl();
		//System.out.println(Hex.decodeHex("46356afe55fa3cea9cbe73ad442cad47").length);
		System.out.println(codec.encode(price, "17813fc430c84278b982837d69638b84"));

		System.out.println(codec.decode("e9Es+nc2JppafN5ZqBepEw==", "46356afe55fa3cea9cbe73ad442cad47"));
	}
}

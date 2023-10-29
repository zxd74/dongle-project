/**
 * 
 */
package com.iwanvi.adserv.ngx.router.brokers.iflytek;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;

/**
 * @author wangweiping
 *
 */
public class IflytekWinPriceCodec implements WinPriceCodec {
	private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	private static final String ALGORITHM = "AES";

	// 讯飞价格加密秘钥
	private static final String key = MinervaCfg.get().getConfigProperty("iflytek.ekey");

	@Override
	public double decode(String price, String token) {
		try {
			byte[] encryptBytes = Base64.decodeBase64(price);
			byte[] decodeBytes = codec(encryptBytes, key.getBytes(), Cipher.DECRYPT_MODE);
			return Double.parseDouble(new String(decodeBytes));
		} catch (Exception ex) {
			// ignore this exception
		}
		return -1d;
	}

	@Override
	public String encode(double price, String token) {
		try {
			//讯飞转换价格单位从分到元
			double priceForYuan = price/100d;;
			String strPrice = Double.toString(priceForYuan);
			byte[] encryptBytes = codec(strPrice.getBytes(), key.getBytes(), Cipher.ENCRYPT_MODE);
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
		IflytekWinPriceCodec codec = new IflytekWinPriceCodec();
		String encodedPrice = codec.encode(34.0, null);
		
		System.out.println(encodedPrice);
		
		double price = codec.decode(encodedPrice, "5120aba7dd8880ba");
		System.out.println(price);
	}
}

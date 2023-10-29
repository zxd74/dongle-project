package com.iwanvi.adserv.ngx.util;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public final class MD5Utils {

	public static String md5Hex(String str) {
		return Hex.encodeHexString(Hashing.MD5.hash(str.getBytes()));
	}
	
	public static String md5Hex(byte[] data) {
		return Hex.encodeHexString(Hashing.MD5.hash(data));
	}
	
	public static String md5Hex(String str, Charset charset) {
		return Hex.encodeHexString(Hashing.MD5.hash(str.getBytes(charset)));
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(md5Hex("12345"));
		System.out.println(DigestUtils.md5Hex("12345"));
	}
}

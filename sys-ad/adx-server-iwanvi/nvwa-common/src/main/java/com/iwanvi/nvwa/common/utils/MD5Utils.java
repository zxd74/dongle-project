package com.iwanvi.nvwa.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class MD5Utils {
    private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };

	public final static String MD5(String s) {
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
            return toHexString(md);
		} catch (Exception e) {
			return null;
		}
	}

    public static String MD5(File file){
        if(file == null){
            return null;
        }
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            return toHexString(md);
        } catch (Exception e) {
            return null;
        }
    }
    public static String MD5(byte[] buffer){
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
//            FileInputStream fis = new FileInputStream(file);
//            FileChannel fc = fis.getChannel();
//            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
//            byte[] buffer=
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            return toHexString(md);
        } catch (Exception e) {
            return null;
        }
    }
    private static String toHexString(byte[] md){
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
	
	public static void main(String[] args) {
//		System.out.println(MD5Utils.MD5(new File("/Users/li/Downloads/10024597_com.tencent.tmgp.supercell.clashofclans_h100_9.105.9.apk")));
		System.out.println(MD5Utils.MD5("hello1234"));
	}
}
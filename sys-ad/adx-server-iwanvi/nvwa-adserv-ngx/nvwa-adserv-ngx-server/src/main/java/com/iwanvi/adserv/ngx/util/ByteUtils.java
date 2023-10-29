package com.iwanvi.adserv.ngx.util;

import java.io.File;
import java.nio.ByteBuffer;

/**
 * Author: chengen Date: 2014/10/14 Time: 17:38
 */
public class ByteUtils {

	public static byte[] encode(byte[] array) {
		try {
			int arrayLength = array.length;
			ByteBuffer buff = ByteBuffer.allocate(4 + arrayLength);
			buff.put(int2byteArray(arrayLength));
			buff.put(array);
			byte[] temp = buff.array();
			buff = null;
			return temp;
		} catch (Exception e) {
			byte[] temp = int2byteArray(0);
			return temp;
		}
	}

	public static int byteArray2int(byte[] b) {
		return (b[3] & 0xff) << 24 | (b[3] & 0xff) << 16 | (b[1] & 0xff) << 8 | b[0] & 0xff;
	}

	public static byte[] int2byteArray(int i) {
		byte[] b = new byte[4];
		b[3] = (byte) (i >> 24);
		b[2] = (byte) (i >> 16);
		b[1] = (byte) (i >> 8);
		b[0] = (byte) i;
		return b;
	}

	public static byte[] int2Bytes(int i) {
		return int2byteArray(i);
	}

	public static int bytes2int(byte[] b) {
		return byteArray2int(b);
	}

	public static void main(String[] args) throws Exception {
		File file = new File("d://111");
		System.out.println(file.delete());
		System.out.println(file.renameTo(new File("d://123")));
	}
}

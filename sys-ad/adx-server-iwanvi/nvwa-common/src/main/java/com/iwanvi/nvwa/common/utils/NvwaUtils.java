package com.iwanvi.nvwa.common.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public class NvwaUtils {

	private static final Logger LOG = LoggerFactory.getLogger(NvwaUtils.class);
	
	private static final String EMPTY = "";
	
	private static final int INTEGER_0 = 0;
	private static final int INTEGER_1 = 1;
	private static final int INTEGER__1 = -1;

	/** 产生一个随机的字符串 */
	public static String getRandomString(int length) {
		String result = "888888";
		try {
			String str = "012210";
			Random random = new Random();
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < length; i++) {
				int num = random.nextInt(6);
				buf.append(str.charAt(num));
			}
			result = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String encryptPwd(String pwd) {
		try {
			char[] data = new char[pwd.length()];
			pwd.getChars(0, pwd.length(), data, 0);
			for (int i = 0; i < pwd.length(); i++) {
				data[i] += i;
			}
			pwd = new String(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pwd;
	}

	public static String reverse(String str) {
		return new StringBuffer(str).reverse().toString();
	}
	
	public static String obj2Empty(Object temp) {
		return obj2Empty(temp, EMPTY);
	}

	public static String obj2Empty(Object temp, String defaultValue) {
		return temp == null ? defaultValue : temp.toString();
	}

	public static int integer2int(Integer temp) {
		return integer2int(temp, INTEGER__1);
	}

	public static int integer2int(Integer temp, int defaultValue) {
		return temp == null ? defaultValue : temp;
	}

	public static long long2long(Long temp) {
		return long2long(temp, INTEGER__1);
	}

	public static long long2long(Long temp, long defaultValue) {
		return temp == null ? defaultValue : temp;
	}

	public static float float2float(Float temp, float defaultValue) {
		return temp == null ? defaultValue : temp;
	}

	public static int obj2int(Object obj) {
		return obj2int(obj, INTEGER_0);
	}
	public static int obj2int(Object obj, int defaultValue) {
		int value = defaultValue;
		try {
			if(obj != null && StringUtils.isNotBlank(obj.toString()) 
					&& NumberUtils.isCreatable(obj.toString())){
				value = Integer.valueOf(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static float obj2float(Object obj, int defaultValue) {
		float value = defaultValue;
		try {
			if(obj != null && StringUtils.isNotBlank(obj.toString()) 
					&& NumberUtils.isCreatable(obj.toString())){
				value = Float.valueOf(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static long obj2long(Object obj, long defaultValue) {
		long value = defaultValue;
		try {
			if(obj != null && StringUtils.isNotBlank(obj.toString()) 
					&& NumberUtils.isCreatable(obj.toString())){
				value = Long.valueOf(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static Set<Integer> getRandomSet(Integer size, int num){
		Set<Integer> result = new HashSet<>();
		try {
			while (result.size() < num) {
				result.add(new Random().nextInt(size));
			}
		} catch (Exception e) {
			LOG.error("getRandomSet error. ", e);
		}
		return result;
	}
	
	public static Integer getPageTotal(Integer count, int pre_count){
		Integer total = null;
		try {
			total = (count % pre_count == INTEGER_0) ? (count / pre_count)
					: (count / pre_count + INTEGER_1);
		} catch (Exception e) {
			LOG.error("getPageTotal error. ", e);
		}
		return total;
	}
	
	/**
	 * 返回参数值的给定长度的二进制字符串，高位补0
	 */
	public static String toBinnryString(int param, int len) {
		char[] chs = new char[len];
		for (int i = 0; i < len; i++) {
			chs[len - 1 - i] = (char) ((param >> i & 1) + '0');
		}
		return new String(chs);
	}
	
	public static String buildUnusualBinnryString(int s1, int s2, int len) {
		int i = s1 & s2;
		
		char[] array = toBinnryString(i, len).toCharArray();
		char[] arrayS2 = toBinnryString(s2, len).toCharArray();

		int temp = 0;
		for (int j = 0; j < array.length; j++) {
			if (array[j] == '1') {
				temp = j;
				break;
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int j = 0; j < arrayS2.length; j++){
			if (temp == j) {
				sb.append('2');
				continue;
			}
			sb.append(arrayS2[j]);
		}
		return sb.toString();
	}
	/**
	 * 返回给定区间为1的二进制字符串，其他位补0
	 */
	public static String toBinnryStringEx(int start, int end, int len) {
		char[] chs = new char[len];
		for (int i = 0; i < len; i++) {
			if (i >= start - 1 && i <= end - 1) {
				chs[i] = '1';
			} else {
				chs[i] = '0';
			}
		}
		return new String(chs);
    }
	
	public static Set<String> subSet(Set<String> objSet, int size) {
	    if (CollectionUtils.isEmpty(objSet)) {
	        return Collections.emptySet();
	    }
	    return ImmutableSet.copyOf(Iterables.limit(objSet, size));
	}
	
	public static void main(String[] args) {
//		System.out.println(buildUnusualBinnryString(9, 59, 31));
	}
}

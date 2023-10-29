package com.iwanvi.nvwa.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 扩展commons-lang中的StringUtils增加concat和sprinf2个实用方法
 * 
 * @see String
 * @since 1.0
 * @version $Id: StringUtils.java 1199894 2011-11-09 17:53:59Z ggregory $
 */
// @Immutable 
public class StringUtils {
	/**
	 * The empty String {@code ""}.
	 * 
	 * @since 2.0
	 */
	public static final String EMPTY = "";
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * 仿c语言的sprintf实现
	 * 
	 * @param format
	 *            字符串输出格
	 * @param args
	 * @return
	 */
	public static String sprintf(String format, Object... args) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		writer.printf(format, args);
		writer.close();
		return stringWriter.toString();
	}

	/**
	 * 字符串连接
	 * 
	 * @param args
	 * @return
	 */
	public static String concat(Object... args) {
		if (args == null || args.length == 0) {
			return EMPTY;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (Object arg : args) {
			stringBuilder.append(arg);
		}
		return stringBuilder.toString();
	}

	private static final String REGEXP_FORMAT_STRING = "(\\{\\d+\\})";
	private static final Pattern pattern = Pattern.compile(REGEXP_FORMAT_STRING, Pattern.CASE_INSENSITIVE);
	
	public static boolean isTrueForRegex(String target, String regex){
		boolean result = false;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		if (matcher.matches()) {
			result = true;
		}
		return result;
	}

	/**
	 * 
	 * @param format
	 *            aaaa{0}hello world{1}, welcome {0}
	 * @param args
	 * @return
	 */
	public static String buildString(String format, Object... args) {
		Matcher matcher = pattern.matcher(format);
		String result = format;
		if (args == null) {
			return result;
		}
		while (matcher.find()) {
			String token = matcher.group();
			int idx = Integer.parseInt(token.substring(1, token.length() - 1));
			result = result.replace(token, args[idx] == null ? "" : args[idx].toString());
		}
		return result;
	}
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

	public static String replace(String format, Object... args) {
		if (args == null || args.length == 0) {
			return format;
		}
		// String result=null;
		for (int i = 0; i < args.length; i++) {
			format = format.replace("{" + i + "}", args[i].toString());
		}
		return format;
	}

	public static void main(String[] args) {
		String format = "hellworld ${vid}, dest ${pid}, google baidu ${ptype}";

		String dest = null;
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			dest = StringUtils.replace(format, "${vid}", "ccf5b7a111a54dd8be29bea58c3f5f1e");
			dest = StringUtils.replace(dest, "${pid}", "65535");
			dest = StringUtils.replace(dest, "${ptype}", "2");
			// System.out.println(dest);
		}
		System.out.println(dest);
		System.out.println("using replace method in StringUtils, spend time: "
				+ (System.currentTimeMillis() - startTime) + " ms");
		System.out.println();

		format = "hellworld %s, dest %s, google baidu %s";
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			dest = StringUtils.sprintf(format, "ccf5b7a111a54dd8be29bea58c3f5f1e", "65535", "2");
		}
		System.out.println(dest);
		System.out.println("using sprintf method in StringUtils. spent time: "
				+ (System.currentTimeMillis() - startTime) + " ms");

		System.out.println();
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			dest = String.format(format, "ccf5b7a111a54dd8be29bea58c3f5f1e", "65535", "2");
		}
		System.out.println(dest);
		System.out.println("using format method in String. spent time: " + (System.currentTimeMillis() - startTime)
				+ " ms");
		System.out.println();

		format = "hellworld {0}, dest {1}, google baidu {2}";
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			dest = StringUtils.buildString(format, "ccf5b7a111a54dd8be29bea58c3f5f1e", "65535", "2");
		}
		System.out.println(dest);
		System.out.println("using buildString method in StringUtils. spent time: "
				+ (System.currentTimeMillis() - startTime) + " ms");

		System.out.println();
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			dest = MessageFormat.format(format, "ccf5b7a111a54dd8be29bea58c3f5f1e", "65535", "2");
		}
		System.out.println(dest);
		System.out.println("using format method in MessageFormat. spent time: "
				+ (System.currentTimeMillis() - startTime) + " ms");

		System.out.println();
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			dest = StringUtils.replace(format, "ccf5b7a111a54dd8be29bea58c3f5f1e", "65535", "2");
		}
		System.out.println(dest);
		System.out.println("using replace(format,args)  method in StringUtils. spent time: "
				+ (System.currentTimeMillis() - startTime) + " ms");
		// String.format(format, args)
	}

	public static int toInt(String str) {
		return toInt(str, -1);
	}

	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static int toInt(String str, int defaultValue) {
		if (isBlank(str)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception ex) {
			// ignore this ex
		}
		return defaultValue;
	}

    public static Integer toInteger(String str) {
        if (isBlank(str)) {
            return null;
        }
        try {
            return Integer.valueOf(str);
        } catch (Exception ex) {
            // ignore this ex
        }
        return null;
    }

	public static long toLong(String str) {
		return toLong(str, -1L);
	}

	public static long toLong(String str, long defaultValue) {
		if (isBlank(str)) {
			return defaultValue;
		}
		try {
			return Long.parseLong(str);
		} catch (Exception ex) {
			// ignore this ex
		}
		return defaultValue;
	}

	public static String list2str(List<String> strList) {
		return list2str(strList, ",");
	}

	public static String list2str(List<String> strList, String split) {
		StringBuilder resultBuffer = new StringBuilder();
		if (strList == null || strList.size() == 0) {
			return EMPTY;
		}
		for (Iterator<String> iterator = strList.iterator(); iterator.hasNext();) {
			resultBuffer.append(split).append(iterator.next());
		}
		return resultBuffer.toString().substring(1);
	}
	
	public static String list2str4Int(List<Integer> strList, String split) {
		StringBuilder resultBuffer = new StringBuilder();
		if (strList == null || strList.size() == 0) {
			return EMPTY;
		}
		for (Iterator<Integer> iterator = strList.iterator(); iterator.hasNext();) {
			resultBuffer.append(split).append(iterator.next());
		}
		return resultBuffer.toString().substring(1);
	}

	public static List<String> str2List(String str, String split) {
		if (isBlank(str)) {
			return Collections.emptyList();
		}
		String[] splitInfo = str.split(split);
		List<String> strList = new ArrayList<String>();
		for (String _str : splitInfo) {
			strList.add(_str);
		}
		return strList;
	}
	
	public static List<Integer> str2List4Int(String str, String split) {
		if (isBlank(str)) {
			return Collections.emptyList();
		}
		String[] splitInfo = str.split(split);
		List<Integer> strList = new ArrayList<Integer>();
		for (String _str : splitInfo) {
			if (StringUtils.isNotBlank(_str)) {
				strList.add(Integer.parseInt(_str));
			}
		}
		return strList;
	}

	private static final String SINGLE_QUOTATION_MARKS = "'";
	private static final String DEFAULT_SPLIT = ",";

	private static final String UTF_8 = "UTF-8";

	public static String toSqlInStr(String[] strArray) {
		if (strArray == null || strArray.length == 0) {
			return StringUtils.EMPTY;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (String _string : strArray) {
			if (_string.startsWith(SINGLE_QUOTATION_MARKS) && _string.endsWith(SINGLE_QUOTATION_MARKS)) {
				stringBuilder.append(DEFAULT_SPLIT).append(_string);
			} else {
				stringBuilder.append(DEFAULT_SPLIT);
				stringBuilder.append(SINGLE_QUOTATION_MARKS).append(_string).append(SINGLE_QUOTATION_MARKS);
			}
		}
		return stringBuilder.substring(1);
	}

	public static String toSqlInStr(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return StringUtils.EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		Iterator<?> iterator = collection.iterator();

		String strElement = null;
		while (iterator.hasNext()) {
			strElement = iterator.next().toString();
			if (strElement.startsWith(SINGLE_QUOTATION_MARKS) && strElement.endsWith(SINGLE_QUOTATION_MARKS)) {
				sb.append(DEFAULT_SPLIT).append(strElement);
			} else {
				sb.append(DEFAULT_SPLIT).append(SINGLE_QUOTATION_MARKS).append(strElement)
						.append(SINGLE_QUOTATION_MARKS);
			}
		}
		return sb.substring(1);
	}

	public static String newString(byte[] bytes, String charsetName) {
		if (bytes == null) {
			return null;
		}
		try {
			return new String(bytes, charsetName);
		} catch (UnsupportedEncodingException e) {
			// ignore this exception
		}
		return null;
	}

	public static String newStringUtf8(byte[] bytes) {
		return StringUtils.newString(bytes, UTF_8);
	}

	public static byte[] getBytesUtf8(String string) {
		return StringUtils.getBytesUnchecked(string, UTF_8);
	}

	public static byte[] getBytesUnchecked(String string, String charsetName) {
		if (string == null) {
			return null;
		}
		try {
			return string.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
			// ignore this exception
		}
		return null;
	}

	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != INDEX_NOT_FOUND) {
			int end = str.indexOf(close, start + open.length());
			if (end != INDEX_NOT_FOUND) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	public static String trim(String str) {
		return str == null ? EMPTY : str.trim();
	}

    public static String trim(Object obj) {
        return obj == null ? EMPTY : obj.toString().trim();
    }
	
	public static String substringAfter(String str, String separator) {
		if (isBlank(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	public static boolean isNotBlank(CharSequence str) {
		return !isBlank(str);
	}
	
	public static String substringBefore(String str, String separator) {
		if (isBlank(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}
	//unicode转中文
	 public static String decodeUnicode(String theString) {      
	        char aChar;      
	        int len = theString.length();      
	        StringBuffer outBuffer = new StringBuffer(len);      
	        for (int x = 0; x < len;) {      
	        	aChar = theString.charAt(x++);      
	        	if (aChar == '\\') {      
	        		aChar = theString.charAt(x++);      
	        		if (aChar == 'u') {      
	        			int value = 0;      
	        			for (int i = 0; i < 4; i++) {      
	        				aChar = theString.charAt(x++);      
	        				switch (aChar) {      
	        				case '0':      
	        				case '1':      
	        				case '2':      
	        				case '3':      
	        				case '4':      
	        				case '5':      
	        				case '6':      
	        				case '7':      
	        				case '8':      
	        				case '9':      
	        					value = (value << 4) + aChar - '0';      
	        						break;      
	        				case 'a':      
	        				case 'b':      
	        				case 'c':      
	        				case 'd':      
	        				case 'e':      
	        				case 'f':      
	        					value = (value << 4) + 10 + aChar - 'a';      
	        					break;      
	        				case 'A':      
	        				case 'B':      
	        				case 'C':      
	        				case 'D':      
	        				case 'E':      
	        				case 'F':      
	        					value = (value << 4) + 10 + aChar - 'A';      
	        					break;      
	        				default:      
	               throw new IllegalArgumentException(      "Malformed   \\uxxxx   encoding.");      
	              }      
	       
	            }      
	             outBuffer.append((char) value);      
	        		} else {      
	             if (aChar == 't')      
	              aChar = '\t';      
	             else if (aChar == 'r')      
	              aChar = '\r';      
	             else if (aChar == 'n')      
	              aChar = '\n';      
	             else if (aChar == 'f')      
	              aChar = '\f';      
	             outBuffer.append(aChar);      
	        }      
	       } else     
	           outBuffer.append(aChar);      
	      }      
	          return outBuffer.toString();      
	     }     

}

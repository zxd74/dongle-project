package com.iwanvi.nvwa.pixel.connector.common.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.utils.StringUtils;

public class NvwaUtils {

	private static final Logger LOG = LoggerFactory.getLogger(NvwaUtils.class);
	
	private static final String EMPTY = "";
	
	private static final int INTEGER_0 = 0;
	private static final int INTEGER_1 = 1;
	private static final int INTEGER__1 = -1;
	public static final Pattern PATTERN_IMEI = Pattern.compile("[0-9]{15}");
	public static final Pattern PATTERN_IDFA = Pattern.compile("[0-9A-Z]{8}-([0-9A-Z]{4}-){3}[0-9A-Z]{12}");
	
	public static final String INVALIDDID15_IMEI = "5284047f4ffb4e04824a2fd1d1f0cd62";     //15个0
	public static final String INVALIDDID1_IMEI = "cfcd208495d565ef66e7dff9f98764da";     //1个0
	public static final String INVALIDDIDNULL_IMEI = "d41d8cd98f00b204e9800998ecf8427e";     //空串
	public static final String INVALIDDID1_IDFA = "0";     //1个0

    /**
     * 获得版本数字
     * V5.5.4 = 554,V6 = 600,V5.1 = 510
     *
     * @param version
     * @return
     */
    public static int gerVersionNumber(String version) {
        int result = 0;
        if (StringUtils.isBlank(version) || !version.startsWith("V")) {
            return -1;
        }
        try {
            String v = version.replaceAll("[^0-9]", "");
            if (NumberUtils.isDigits(v)) {
                result = Integer.parseInt(v);
                if (result > 0 && result < 10) {
                    result = result * 1000;
                }
                if (result >= 10 && result < 100) {
                    result = result * 100;
                }
                if (result >= 100 && result < 1000) {
                    result = result * 10;
                }
                if (result >= 10000) {
                    result = -1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

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
					&& NumberUtils.isParsable(obj.toString())){
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
					&& NumberUtils.isParsable(obj.toString())){
				value = Float.valueOf(obj.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static long obj2long(Object obj) {
		return obj2long(obj, 0l);
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
	
	public static void main(String[] args) {
		System.out.println(NumberUtils.isCreatable("09"));
		System.out.println(NumberUtils.isParsable("wewewe"));
		System.out.println(Long.valueOf("eee"));
	}
}

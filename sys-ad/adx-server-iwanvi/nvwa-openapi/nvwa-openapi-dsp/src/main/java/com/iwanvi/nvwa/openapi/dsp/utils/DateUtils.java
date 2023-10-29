package com.iwanvi.nvwa.openapi.dsp.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class DateUtils {

	private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_FORMAT = "yyyyMMdd";
	public static final String SHORT_FORMAT_EX = "yyyy-MM-dd";
	public static final String SHORT_FORMAT_MINUTE = "yyyyMMddHHmm";
	public static final String SHORT_FORMAT_HOUR = "yyyyMMddHH";
	public static final String SHORT_FORMAT_MONTH = "yyyyMM";
	public static final String LONG_FORMAT = "yyyyMMddHHmmssS";
	public static final String FORMAT_YMDHMS = "yyyyMMddHHmmss";
	public static final String FORMAT_NGINX = "dd/MMM/yyyy:HH:mm:ss Z";
	public static final String FORMAT_HOUR = "HH";
	public static final String FORMAT_MINUTE = "mm";

	private static final int EXPIRE_TIME = 60 * 60 * 24;

	public static String formatPure(Date date) {
		return new SimpleDateFormat(FORMAT_YMDHMS).format(date);
	}

	public static String format(Date date) {
		return new SimpleDateFormat(DEFAULT_FORMAT).format(date);
	}

	public static String format(Date date, String format) {
		try {
			if (date == null) {
				return null;
			}
			if (format == null)
				return format(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String format(Date date, String format, Locale locale) {
		try {
			if (date == null) {
				return null;
			}
			if (format == null)
				return format(date);
			SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
			return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parse(String source) {
		try {
			Date date = new SimpleDateFormat(DEFAULT_FORMAT).parse(source);
			return date;
		} catch (Exception ex) {
			LOG.error("Date parse exception. source: {}", source, ex);
		}
		return null;
	}

	public static Date parse(String source, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			Date date = dateFormat.parse(source);
			return date;
		} catch (ParseException ex) {
			LOG.error("Date parse exception. source: {}, format: {}", source, format);
		}
		return null;
	}

	public static String format(String source, String format, String desform) {
		Date date = parse(source, format);
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(desform);
		return dateFormat.format(date);
	}

	public static Date getPreMonth(Date date) {
		return getPreMonthByNumber(date, 1);
	}

	public static Date getPreMonthByNumber(Date date, int number) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - number);
		return calendar.getTime();
	}

	public static Date getNextMonthByNumber(Date date, int number) {
		return getPreMonthByNumber(date, -number);
	}

	public static Date getPreDaysDate(Date date, int number) {
		if (date == null) {
			return null;
		}
		long predaysTime = date.getTime() - 1000L * number * 24L * 3600L;
		return new Date(predaysTime);
	}

	public static Date getPreDayDate(Date date) {
		return getPreDaysDate(date, 1);
	}

	public static Date getNextDaysDate(Date date, int number) {
		return getPreDaysDate(date, -number);
	}

	public static String getLastDayOfMonth(int year, int month) {
		Calendar calendar = GregorianCalendar.getInstance(Locale.CHINESE);
		calendar.set(year, month, 1);

		Date date = new Date(calendar.getTimeInMillis() - 86400000L);
		return format(date, "yyyy年M月dd日");
	}

	public static long getDValue2Day(Date start, Date end) {
		long dValue = 0;
		try {
			long l = end.getTime() - start.getTime();
			dValue = l / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
		}
		return dValue;
	}

	/**
	 * 例如：得到12日19:43的上一小时12日18:43
	 */
	public static Date getPreviousHour(Date time, int num) {
		Date result = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);// 日历为今天
			long tm, tm1;
			tm = cal.getTimeInMillis();// 得到当前时间与1970年1月1日0点相距的毫秒数
			tm1 = tm - num * 60 * 60 * 1000;// 得到前一小时与1970年1月1日0点相距的毫秒数
			result = new Date(tm1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date getNextHour(Date time, int num) {
		return getPreviousHour(time, -num);
	}

	/**
	 * 例如：得到12日19:43的上一分钟
	 */
	public static Date getPreviousMinute(Date time, int num) {
		Date result = null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(time);// 日历为今天
			long tm, tm1;
			tm = cal.getTimeInMillis();// 得到当前时间与1970年1月1日0点相距的毫秒数
			tm1 = tm - num * 60 * 1000;// 得到前一分钟与1970年1月1日0点相距的毫秒数
			result = new Date(tm1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Date getNextMinute(Date time, int num) {
		return getPreviousMinute(time, -num);
	}

	/**
	 * cc like "14/Nov/2013:00:00:01";
	 * 
	 * @param cc
	 * @return
	 */
	public static String getDateTime(String cc, String format) {
		String result = StringUtils.EMPTY;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", new Locale("English"));
		try {
			if (StringUtils.isBlank(cc)) {
				return result;
			}
			cc = cc.replaceAll("\\[", StringUtils.EMPTY);
			Date date = sdf.parse(cc);
			SimpleDateFormat dateformat = new SimpleDateFormat(format);
			result = dateformat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static long getDifference(String date1, String date2, String format) {
		long l1 = 0L;
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date now = df.parse(date1);
			Date date = df.parse(date2);
			l1 = now.getTime() - date.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l1;
	}

	public static long getSecond(String source, String format) {
		long second = 0L;
		try {
			Date tempDate = parse(source, format);
			second = tempDate.getTime() / 1000L;
		} catch (Exception e) {
		}
		return second;
	}

	public static int getCurrentMonthDays() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 返回指定日期是全年的第几周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		int week = -1;
		try {
			Calendar cl = Calendar.getInstance();
			cl.setTime(date);
			week = cl.get(Calendar.WEEK_OF_YEAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return week;
	}

	/**
	 * 返回指定日期是全年的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		int month = -1;
		try {
			Calendar cl = Calendar.getInstance();
			cl.setTime(date);
			month = cl.get(Calendar.MONTH) + 1;
			if (month < 10) {
				month = Integer.valueOf(String.valueOf(cl.get(Calendar.YEAR)) + 0 + month);
			} else {
				month = Integer.valueOf(String.valueOf(cl.get(Calendar.YEAR)) + month);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return month;
	}

	/**
	 * 获取缓存的过期时间
	 * 
	 * @param expiryTime
	 * @return
	 */
	public static int getExpireDate(String expiryTime) {
		int overdueTime;
		if (StringUtils.isNotBlank(expiryTime)) {
			long expirySecond = DateUtils.getSecond(expiryTime, DateUtils.FORMAT_YMDHMS);
			long nowSecond = new Date().getTime() / 1000l;
			long overdueTimeTemp = expirySecond - nowSecond;
			if (expirySecond > 0 && (overdueTimeTemp) > 0) {
				overdueTime = (int) overdueTimeTemp;
			} else {
				overdueTime = EXPIRE_TIME;
			}
		} else {
			overdueTime = EXPIRE_TIME;
		}
		return overdueTime;
	}

	/**
	 * 返回开始日期与结束日期之间的日期列表（包含给定的开始日期、结束日期）；返回日期格式yyyyMMdd
	 * 
	 * @param start_date
	 * @param end_date
	 */
	public static List<String> getDatesByInterval(String start_date, String end_date, String format) {
		List<String> result = new ArrayList<String>();
		try {
			Calendar start = new GregorianCalendar();
			Calendar end = new GregorianCalendar();

			// 按照format格式转换为日期
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date daystart = df.parse(start_date);
			Date dayend = df.parse(end_date);

			// 设置calendar的日期
			start.setTime(daystart);
			end.setTime(dayend);

			end.add(Calendar.DATE, 1);
			while (start.before(end)) {
				result.add(format(start.getTime(), SHORT_FORMAT));
				start.add(Calendar.DATE, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void test() {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			Date now = df.parse("20131016170800");
			Date date = df.parse("20131016180800");
			long l = now.getTime() - date.getTime();

			System.out.println("l=======" + l);

			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			System.out.println("日期差值：" + day + "天" + hour + "小时" + min + "分" + s + "秒");

			Date date1 = df.parse("20131205230000");
			System.out.println(getPreviousHour(date1, 1));
			System.out.println(getNextHour(date1, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			// long l0 = 1394726400;
			// Date date = new Date(l0 * 1000L);

			// Calendar calendar = Calendar.getInstance();
			// calendar.setTime(date);
			// int hour = calendar.get(Calendar.HOUR);
			// System.out.println("hour is = " + hour);

			// System.out.println(DateUtils.format(date));
			//
			// Date tempDate = parse("20141206010000", FORMAT_YMDHMS);
			// System.out.println(l0);
			// System.out.println(tempDate.getTime() / 1000L);
			//
			// Date date11 = DateUtils.getPreviousMinute(date, 5);
			// System.out.println(date11);
			//
			// System.out.println(getCurrentMonthDays());
			//
			// System.out.println(getWeek(new Date()));
			//
			// System.out.println("month===========" + getMonth(new Date()));

			List<String> result = getDatesByInterval("20140826", "20140926", "yyyyMMdd");
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

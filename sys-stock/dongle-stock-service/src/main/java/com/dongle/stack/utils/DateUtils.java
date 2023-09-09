package com.dongle.stack.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/9 009 10:48
 */
public class DateUtils {

    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";

    public static String formatDate(Date date){
        return formatDate(date,DATE_FORMAT_DEFAULT);
    }

    /**
     * 日期格式化
     * @param date 待格式化日期
     * @param format 格式化模式
     * @return 格式化结果
     */
    public static String formatDate(Date date,String format){
        if (date==null) throw new NullPointerException("date is null!");
        if (format == null) format = DATE_FORMAT_DEFAULT;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date dateFormat(String str){
        return dateFormat(str,DATE_FORMAT_DEFAULT);
    }

    /**
     * 格式化日期
     * @param str 日期字符串
     * @param format 格式化模式
     * @return 格式化结果
     */
    public static Date dateFormat(String str,String format){
        if (DataUtils.isEmpty(str)) throw new RuntimeException("data format: param not is empty!");
        if (format == null) format = DATE_FORMAT_DEFAULT;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(str);
        }catch (Exception ex){
            throw new RuntimeException("date format is error!",ex);
        }
        return date;
    }

    public static int compare(String d1, Date d2) {
        return compare(dateFormat(d1),d2);
    }
    public static int compare(Date d1, String d2) {
        return compare(d1,dateFormat(d2));
    }
    /**
     * 比较两个日期大小
     *  如果d1==null，则d1>d2, return 1
     *  如果d2==null, 则d1<d2,return -1
     *  如果都不为null，则比较getTime，
     *      gap值大于0 则return 1
     *      gap值等于0 则return 0
     *      否则 return -1
     * @param d1 日期1
     * @param d2 日期2
     * @return d1>d2，return 1；d1==d2return 0；d1<d2 return -1
     */
    public static int compare(Date d1, Date d2) {
        if (d1 == null) return 1;
        if (d2 == null) return -1;
        long gap = d1.getTime() - d2.getTime();
        return gap > 0 ? 1 : gap == 0 ? 0 : -1;
    }

}

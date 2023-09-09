package com.dongle.stack.utils;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/2 11:14
 */
public class Constants {

    public static final int OPTIONS_ADD = 1;
    public static final int OPTIONS_DELETE = 2;
    public static final int OPTIONS_UPDATE = 3;
    public static final int OPTIONS_QUERY = 0;

    public static final int DAY_WEEK = 7;
    public static final String STR_DAY_WEEK = "7";
    public static final int DAY_MONTH = 30;
    public static final String STR_DAY_MONTH = "30";
    public static final int DAY_THREE_MONTH = 90;
    public static final String STR_DAY_THREE_MONTH = "90";
    public static final int DAY_HALF_YEAR = 180;
    public static final String STR_DAY_HALF_YEAR = "180";
    public static final int DAY_YEAR = 365;
    public static final String STR_DAY_YEAR = "365";

    public static final String REDIS_STOCK_PREFIX = "stock:%s"; // stock:code
    public static final String REDIS_STOCK_DATA_PREFIX = "data:%s";// data:day
    public static final String REDIS_STOCK_DATA_NEW = "new";
}

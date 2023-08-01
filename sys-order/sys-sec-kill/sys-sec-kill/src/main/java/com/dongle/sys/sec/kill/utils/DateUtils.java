package com.dongle.sys.sec.kill.utils;

import java.util.Date;

public class DateUtils {

    public static boolean compare(Date d1,Date d2){
        return d2.compareTo(d1) < 0;
    }
}

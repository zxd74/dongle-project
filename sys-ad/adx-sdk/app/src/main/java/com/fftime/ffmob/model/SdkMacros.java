package com.fftime.ffmob.model;

import com.fftime.ffmob.nativead.ClkEventData;

public final class SdkMacros {
    public static final String DOWN_X = "{DOWN_X}";
    public static final String DOWN_Y = "{DOWN_Y}";

    public static final String UP_X = "{UP_X}";
    public static final String UP_Y = "{UP_Y}";

    public static final String MACRO_DOWN_X = "__DOWN_X__";
    public static final String MACRO_DOWN_Y = "__DOWN_Y__";
    public static final String MACRO_UP_X = "__UP_X__";
    public static final String MACRO_UP_Y = "__UP_Y__";
    public static final String EVNET_TIME = "__EVENT_TIME__";


    public static String replaceClkEventMacros(String cm, ClkEventData clkEventData) {
        String dx = clkEventData.getDownX() + "";
        String dy = clkEventData.getDownY() + "";
        String upx = clkEventData.getUpX() + "";
        String upy = clkEventData.getUpY() + "";
        String time = System.currentTimeMillis() + "";
        return cm.replace(DOWN_X, dx).replace(DOWN_Y, dy)
                .replace(UP_X, upx).replace(UP_Y, upy).
                        replace(MACRO_DOWN_X, dx).replace(MACRO_DOWN_Y, dy)
                .replace(MACRO_UP_X, upx).replace(MACRO_UP_Y, upy).replace(EVNET_TIME,time);
    }
}

package com.fftime.ffmob.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class SizeUtil {
    @SuppressLint("DefaultLocale")
    public static String asM(long sizeInByte) {
        float sizeMB = (float) (sizeInByte / (1024 * 1024));
        return String.format("%.2fM", sizeMB);
    }

    @SuppressLint("DefaultLocale")
    public static String asKB(long sizeInByte) {
        float sizeInKB = (float) (sizeInByte / 1024);
        return String.format("%.2f K", sizeInKB);
    }

    public static String asH(long sizeInByte) {
        if (sizeInByte > 1024 * 1024) {
            return asM(sizeInByte);
        } else {
            return asKB(sizeInByte);
        }
    }

    public static int dp2px(Context context, int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * dps);
    }

    public static String dpiType(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int densityDpi = dm.densityDpi;

        return String.valueOf(densityDpi);
    }

    public static int dpi(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int densityDpi = dm.densityDpi;

        return densityDpi;
    }
}

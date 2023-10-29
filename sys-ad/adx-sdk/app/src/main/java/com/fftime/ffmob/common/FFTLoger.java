package com.fftime.ffmob.common;

import com.fftime.ffmob.util.Constants;

import android.util.Log;

public class FFTLoger {
  private static final String TAG = "FFTMOB";

  public static void d(String tag, String msg) {
    if (Constants.DEBUG) Log.d(TAG + tag, msg);
  }

  public static void d(String tag, String msg, Throwable e) {
    if (Constants.DEBUG) {
      Log.d(TAG + tag, msg, e);
    }
  }

  public static void w(String tag, String msg) {
    Log.d(TAG + "-" + tag, msg);
  }

  public static void w(String tag, String msg, Throwable e) {
    Log.w(TAG + tag, msg, e);
  }

  public static void e(String tag, String msg) {
    Log.e(TAG + tag, msg);
  }

  public static void e(String tag, String msg, Throwable e) {
    Log.e(TAG + tag, msg, e);
  }

  public static void i(String tag, String msg) {
    Log.i(TAG + "-" + tag, msg);
  }

  public static void i(String tag, String msg, Throwable e) {
    Log.i(TAG + tag, msg, e);
  }
}

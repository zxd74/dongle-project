package com.fftime.ffmob.util;

public class StringUtil {
  public static final String EMPTY = "";

  public static boolean isEmpty(String in) {
    return in == null || in.trim().length() == 0;
  }
}

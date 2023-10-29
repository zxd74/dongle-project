package com.fftime.ffmob.util;

public class URLUtil {
  public static String appendParam(String url, String key, String value) {
    if (url.contains("?")) {
      return url + "&" + key + "=" + value;
    } else {
      return url + "?" + key + "=" + value;
    }
  }

  public static String appendParam(String url, String key, int value) {
    if (url.contains("?")) {
      return url + "&" + key + "=" + value;
    } else {
      return url + "?" + key + "=" + value;
    }
  }
}

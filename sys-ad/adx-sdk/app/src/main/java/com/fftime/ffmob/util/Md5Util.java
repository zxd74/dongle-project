package com.fftime.ffmob.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

import com.fftime.ffmob.common.FFTLoger;

import android.util.Base64;

public class Md5Util {
  /***************************************************************************
   * md5 类实现了RSA Data Security, Inc.在提交给IETF 的RFC1321中的MD5 message-digest 算法。
   **************************************************************************/
  private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
      "b", "c", "d", "e", "f"};

  /**
   * 转换字节数组为16进制字串
   * 
   * @param b 字节数组
   * @return 16进制字串
   */
  public static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }

  public static byte[] hexStringtoByteArray(String str) {
    if (str.length() % 2 != 0) {
      return null;
    } else {
      byte[] bytes = new byte[str.length() / 2];
      for (int i = 0; i < str.length() - 1; i += 2) {
        byte b = charPairToByte(str.charAt(i), str.charAt(i + 1));
        bytes[i / 2] = b;
      }
      return bytes;
    }
  }

  private static byte charPairToByte(char a, char b) {
    int value = 0;
    a = Character.toLowerCase(a);
    b = Character.toLowerCase(b);

    if (a <= '9') {
      value = a - '0';
    } else {
      value = a - 'a' + 10;
    }
    value = value << 4;
    if (b <= '9') {
      value += b - '0';
    } else {
      value += (b - 'a' + 10);
    }
    if (value > 127) {
      value = value - 256;
    }
    return (byte) value;
  }

  /**
   * J 转换byte到16进制
   * 
   * @param b
   * @return
   */
  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0) {
      n = 256 + n;
    }
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  /**
   * J 编码
   * 
   * @param origin
   * @return
   */

  // MessageDigest 为 JDK 提供的加密类
  public static String encode(String origin) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
    } catch (Exception ex) {}
    return resultString;
  }

  public static String encodeBase64String(String base64) {
    byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      return byteArrayToHexString(md.digest(bytes));
    } catch (Exception e) {
      FFTLoger.e("", "Exception while md5 base64String", e);
    }
    return null;
  }

  /**
   * 用于计算文件MD5值同md5sum
   * 
   * @param file
   * @return
   */
  public static String encode(File file) {
    if (file == null) {
      return "";
    }
    FileInputStream input = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      input = new FileInputStream(file);
      int index = 0;
      byte[] buffer = new byte[1024];
      while ((index = input.read(buffer)) > 0) {
        md.update(buffer, 0, index);
      }
      return byteArrayToHexString(md.digest());
    } catch (Exception ex) {} finally {
      if (input != null) {
        try {
          input.close();
        } catch (Exception e) {

        }
      }
    }
    return "";
  }
}

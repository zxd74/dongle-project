package com.fftime.ffmob.common.adservices.downloader;

import java.io.File;

import com.fftime.ffmob.common.FFTLoger;

import android.content.Context;

public class APKFileUtil {
  private static final String FFT_DOWN_DIR = "fft_down";

  public static File getDownDir(Context context) {
    File rt = null;
    try {
      File externalCacheDir = new File(context.getExternalCacheDir(), FFT_DOWN_DIR);
      if (externalCacheDir != null) {
        externalCacheDir.mkdirs();
        rt = externalCacheDir.exists() ? externalCacheDir : null;
      }
    } catch (Throwable e) {}
    if (rt == null) {
      rt = new File(context.getCacheDir(), FFT_DOWN_DIR);
    }
    rt.mkdirs();
    FFTLoger.d("DownAPK", rt.getAbsolutePath());
    return rt;
  }

  public static final String getFileName(String pkgName) {
    return pkgName + ".apk";
  }

  public static final String getTmpFileName(String pkgName) {
    return pkgName + ".apk.tmp";
  }

  public static final String getIconFileName(String pkgName) {
    return pkgName + ".icon";
  }

  public static final File getFinalFile(Context context, String pkgName) {
    return new File(getDownDir(context), getFileName(pkgName));
  }

  public static final File getTmpFile(Context context, String pkgName) {
    return new File(getDownDir(context), getTmpFileName(pkgName));
  }

}

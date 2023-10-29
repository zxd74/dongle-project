package com.fftime.ffmob.common.adservices.downloader;

import com.fftime.ffmob.common.adservices.downloader.APKManager.APKStatus;

public interface APKStatusMonitor {
  /**
   * @param progress 使用时需要检查是否为空
   */
  void onAPKStatusUpdate(String pkgName, APKStatus status, DownProgress progress);
}

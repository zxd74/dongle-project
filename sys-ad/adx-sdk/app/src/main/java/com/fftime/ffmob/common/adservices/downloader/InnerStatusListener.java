package com.fftime.ffmob.common.adservices.downloader;

import com.fftime.ffmob.common.adservices.downloader.APKManager.APKStatus;

/**
 * 仅供下载器模块内部使用
 * 
 * @author MR.Z
 *
 */
interface InnerStatusListener {
  public void onStatusUpdate(String pkgName, APKStatus oldStatus, APKStatus newStatus);
}

package com.fftime.ffmob.common.adservices.downloader;

/**
 * 仅供下载器模块内部使用
 * 
 * @author MR.Z
 * 
 */
interface InnerDownProgressListener {
  public void onProgressUpdate(APKDownTask task, DownProgress progress);
}

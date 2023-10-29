package com.fftime.ffmob.banner;

/**
 * 
 * @author MR.Z
 *
 */
public interface BannerADListener {
  /**
   * 当广告准备成功时调用，自动轮播时同样会调用
   */
  void onADReady();

  /**
   * 当加载广告失败是调用
   */
  void onADFail();
}

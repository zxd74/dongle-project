package com.fftime.ffmob.banner;

/**
 * Banner广告加载请求，可以设置自动轮播时间等参数
 * 
 * @author MR.Z
 *
 */
public class BannerADRequest {

  private final int rollingTime;

  /**
   * 
   * @param rollingTime 自动轮播时间单位s。0表示不轮播。有效取值区间：[30,120]
   */
  public BannerADRequest(int rollingTime) {
    this.rollingTime = rollingTime;
  }

  public int getRollingTime() {
    return rollingTime;
  }


}

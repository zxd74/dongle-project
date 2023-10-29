package com.fftime.ffmob.common.adservices.downloader;

import android.content.Context;


public class APKDownTask {
  final int cid;
  final String url;
  final String pkgName;

  private FFTNotificationBuilder nb;



  public FFTNotificationBuilder getNb(Context context) {
    if (this.nb == null) {
      this.nb = FFTNotificationBuilder.builderBySupportV4(context);
    }
    return nb;
  }



  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {
    private int cid;
    private String url;
    private String pkgName;


    public Builder setCid(int cid) {
      this.cid = cid;
      return this;
    }



    public Builder setUrl(String url) {
      this.url = url;
      return this;
    }


    public Builder setPkgName(String pkgName) {
      this.pkgName = pkgName;
      return this;
    }



    public APKDownTask build() {
      return new APKDownTask(url, pkgName, cid);
    }
  }



  private APKDownTask(String url, String pkgName, int cid) {
    this.cid = cid;
    this.url = url;
    this.pkgName = pkgName;
  }



  public int getCid() {
    return cid;
  }



  public String getUrl() {
    return url;
  }



  public String getPkgName() {
    return pkgName;
  }



}

package com.fftime.ffmob.common.network;

import com.fftime.ffmob.common.FFTLoger;

import org.apache.http.HttpResponse;

public class PlainNetRequest extends NetRequest {
  private static final String TAG = "PlainRequest";

  public static interface CallBack {
    public void onResponse(PlainNetRequest req, PlainADNetResponse resp);

    public void onError(Exception e);
  }

  private final CallBack cb;

  public PlainNetRequest(String url, Method method, byte[] postData, CallBack cb) {
    super(url, method, postData);
    this.cb = cb;
  }

  @Override
  public void onResponse(final HttpResponse origResp) {
    if (this.cb != null) {
          cb.onResponse(PlainNetRequest.this, new PlainADNetResponse(origResp, PlainNetRequest.this));
    }
  }

  @Override
  public void onError(Exception e) {
    if (this.cb != null) {
      this.cb.onError(e);
    } else {
      FFTLoger.e(TAG, "Exception for plain Request to url:" + this.getUrlWithParas(), e);
    }
  }

}

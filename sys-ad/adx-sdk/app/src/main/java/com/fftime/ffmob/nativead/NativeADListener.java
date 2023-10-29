package com.fftime.ffmob.nativead;

import com.fftime.ffmob.model.NatiAd;

import java.util.Collection;


public interface NativeADListener {
  public static final int ERROR_NETWORK = -1;
  public static final int ERROR_RESPONSE = -2;

  void onSucc(NatiAd natiAd);

  void onFail(int errCode, String msg);
}

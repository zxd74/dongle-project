package com.fftime.ffmob.interstitial;

public interface InterstitialADListener {
  void onADReady();

  void onADFail();

  void onADPresent();

  void onADClosed();
}

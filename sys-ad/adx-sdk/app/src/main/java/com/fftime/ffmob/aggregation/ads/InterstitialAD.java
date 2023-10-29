package com.fftime.ffmob.aggregation.ads;

import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;

public interface InterstitialAD {
    void loadAD();
    void showAD();
    void setAdListener(InterstitialAdListener listener);
    void destroy();
}

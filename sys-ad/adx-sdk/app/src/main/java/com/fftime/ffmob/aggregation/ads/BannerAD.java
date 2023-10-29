package com.fftime.ffmob.aggregation.ads;

import android.view.View;

import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;

public interface BannerAD {
    void loadAD();
    void destroy();
    void setAdListener(BannerAdListener listener);
    View getAdView();
}

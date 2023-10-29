package com.fftime.ffmob.aggregation.baidu.interstitial;

import android.app.Activity;

import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;
import com.fftime.ffmob.aggregation.ads.InterstitialAD;
import com.fftime.ffmob.aggregation.model.FFAdError;

public class BaiduInterstitialAD implements InterstitialAD {
    private final InterstitialAd interstitialAd;
    private final Activity activity;

    public BaiduInterstitialAD(Activity activity, String appId, String posId) {
        this.activity = activity;
        InterstitialAd.setAppSid(activity, appId);
        interstitialAd = new InterstitialAd(activity, posId);
    }

    @Override
    public void loadAD() {
        interstitialAd.loadAd();
    }

    @Override
    public void showAD() {
        interstitialAd.showAd(this.activity);
    }

    @Override
    public void setAdListener(final com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener listener) {
        interstitialAd.setListener(new InterstitialAdListener() {
            @Override
            public void onAdReady() {
            }

            @Override
            public void onAdPresent() {
                listener.onAdExposure();
            }

            @Override
            public void onAdClick(InterstitialAd interstitialAd) {
                listener.onAdClick();
            }

            @Override
            public void onAdDismissed() {
            }

            @Override
            public void onAdFailed(String s) {
                listener.onAdFailed(new FFAdError(-1,s));
            }
        });
    }

    @Override
    public void destroy() {
        if (interstitialAd != null)
            interstitialAd.destroy();
    }
}

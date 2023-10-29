package com.fftime.ffmob.aggregation.baidu.splash;

import android.app.Activity;
import android.view.ViewGroup;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.fftime.ffmob.aggregation.ads.SplashAD;
import com.fftime.ffmob.aggregation.model.FFAdError;

public class BaiduSplashAD implements SplashAD {
    private SplashAd splashAd;

    private final Activity activity;
    private final ViewGroup adContainer;
    private final String appId;
    private final String posId;
    private final com.fftime.ffmob.aggregation.base.listener.SplashAdListener listener;

    public BaiduSplashAD(Activity activity, ViewGroup parent, String appId, String posId,
                         final com.fftime.ffmob.aggregation.base.listener.SplashAdListener listener) {
        SplashAd.setAppSid(activity, appId);
        this.activity = activity;
        this.adContainer = parent;
        this.appId = appId;
        this.posId = posId;
        this.listener = listener;
    }

    @Override
    public void fetchAndShowAD() {
        splashAd = new SplashAd(activity, adContainer, new SplashAdListener() {
            @Override
            public void onAdPresent() {
                listener.onAdExposure();
            }

            @Override
            public void onAdDismissed() {
                listener.onAdFinished();
            }

            @Override
            public void onAdFailed(String s) {
                listener.onAdFailed(new FFAdError(-1, s));
            }

            @Override
            public void onAdClick() {
                listener.onAdClick();
            }
        }, posId, true);

    }
}

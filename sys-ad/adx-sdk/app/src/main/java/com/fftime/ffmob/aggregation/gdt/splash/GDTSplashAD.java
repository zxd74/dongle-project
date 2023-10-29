package com.fftime.ffmob.aggregation.gdt.splash;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.SplashAD;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;

public class GDTSplashAD implements SplashAD {
    public com.qq.e.ads.splash.SplashAD splashAD;

    private final Activity activity;
    private final ViewGroup adContainer;
    private final View skipView;
    private final String appId;
    private final String posId;
    private final SplashAdListener listener;
    public GDTSplashAD(Activity activity, ViewGroup adContainer, View skipView, String appId, String posId, final SplashAdListener listener) {
        this.activity = activity;
        this.adContainer = adContainer;
        this.skipView = skipView;
        this.appId = appId;
        this.posId = posId;
        this.listener = listener;
    }

    @Override
    public void fetchAndShowAD() {
        //需要传入跳过按钮
        splashAD = new com.qq.e.ads.splash.SplashAD(activity,appId, posId, new SplashADListener() {
            @Override
            public void onADDismissed() {
                listener.onAdFinished();
            }

            @Override
            public void onNoAD(AdError adError) {
                listener.onAdFailed(new FFAdError(adError.getErrorCode(), adError.getErrorMsg()));
            }

            @Override
            public void onADPresent() {
                //DO NOTHING
            }

            @Override
            public void onADClicked() {
                listener.onAdClick();
            }

            @Override
            public void onADTick(long l) {
                //DO NOTHING
            }

            @Override
            public void onADExposure() {
                listener.onAdExposure();
            }
        },3000);
        splashAD.fetchAndShowIn(adContainer);
    }
}

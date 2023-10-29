package com.fftime.ffmob.aggregation.toutiao.splash;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.fftime.ffmob.aggregation.ads.SplashAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;

public class TTSplashAD implements SplashAD {
    private Activity activity;
    private SplashAdListener listener;
    private AdSlotSetting adSlotSetting;
    private TTAdNative ttAdNative;
    private ViewGroup adContainer;

    private static final int AD_TIMEOUT = 2000;

    public TTSplashAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, SplashAdListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.adContainer = adContainer;

        ttAdNative = TTAdSdk.getAdManager().createAdNative(activity);

    }

    @Override
    public void fetchAndShowAD() {
        AdSlot adSlot = new AdSlot.Builder().setSupportDeepLink(true).setAdCount(1)
                .setImageAcceptedSize(adSlotSetting.getWidth(), adSlotSetting.getHeight()).build();
        ttAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int i, String s) {
                listener.onAdFailed(new FFAdError(i, s));
            }

            @Override
            public void onTimeout() {
                listener.onAdFinished();
            }

            @Override
            public void onSplashAdLoad(TTSplashAd ttSplashAd) {
                View splashView = ttSplashAd.getSplashView();
                adContainer.removeAllViews();
                adContainer.addView(splashView);

                ttSplashAd.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int i) {
                        listener.onAdClick();
                    }

                    @Override
                    public void onAdShow(View view, int i) {
                        listener.onAdExposure();
                    }

                    @Override
                    public void onAdSkip() {
                        listener.onAdFinished();
                    }

                    @Override
                    public void onAdTimeOver() {
                        listener.onAdFinished();
                    }
                });
            }
        }, AD_TIMEOUT);
    }
}

package com.fftime.ffmob.aggregation.toutiao.interstitial;

import android.app.Activity;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTInteractionAd;
import com.fftime.ffmob.aggregation.ads.InterstitialAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;

import java.util.concurrent.atomic.AtomicReference;

public class TTInterstitialAD implements InterstitialAD {
    private AdSlot adSlot;
    private InterstitialAdListener listener;
    private TTAdNative ttAdNative;
    private AtomicReference<TTInteractionAd> ttInteractionAdHolder;
    private Activity activity;

    public TTInterstitialAD(Activity activity, AdSlotSetting adSlotSetting, final InterstitialAdListener listener) {
        ttAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        this.listener = listener;
        adSlot = new AdSlot.Builder().setAdCount(1).setNativeAdType(AdSlot.TYPE_INTERACTION_AD).setCodeId(adSlotSetting.getPosId()).
                setImageAcceptedSize(adSlotSetting.getWidth(), adSlotSetting.getHeight()).setSupportDeepLink(true).build();
        this.activity = activity;
    }

    @Override
    public void loadAD() {
        ttInteractionAdHolder = new AtomicReference<>();
        ttAdNative.loadInteractionAd(adSlot, new TTAdNative.InteractionAdListener() {
            @Override
            public void onError(int i, String s) {
                listener.onAdFailed(new FFAdError(i, s));
            }

            @Override
            public void onInteractionAdLoad(TTInteractionAd ttInteractionAd) {
                listener.onAdSuccess();
                ttInteractionAdHolder.set(ttInteractionAd);
                ttInteractionAd.setAdInteractionListener(new TTInteractionAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked() {
                        listener.onAdClick();
                    }

                    @Override
                    public void onAdShow() {
                        listener.onAdExposure();
                    }

                    @Override
                    public void onAdDismiss() {
                        //DO NOTHING
                    }
                });
            }
        });
    }

    @Override
    public void showAD() {
        ttInteractionAdHolder.get().showInteractionAd(this.activity);
    }

    @Override
    public void setAdListener(InterstitialAdListener listener) {
    }

    @Override
    public void destroy() {
    }
}

package com.fftime.ffmob.aggregation.toutiao;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.BannerAD;
import com.fftime.ffmob.aggregation.ads.BaseADFactory;
import com.fftime.ffmob.aggregation.ads.InterstitialAD;
import com.fftime.ffmob.aggregation.ads.NativeAD;
import com.fftime.ffmob.aggregation.ads.NativeExpressAD;
import com.fftime.ffmob.aggregation.ads.NativeVideoAD;
import com.fftime.ffmob.aggregation.ads.SplashAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeExpressAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeVideoAdListener;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;
import com.fftime.ffmob.aggregation.toutiao.banner.TTBannerAD;
import com.fftime.ffmob.aggregation.toutiao.interstitial.TTInterstitialAD;
import com.fftime.ffmob.aggregation.toutiao.nativead.TTNativeAD;
import com.fftime.ffmob.aggregation.toutiao.splash.TTSplashAD;

public class TTADFactory extends BaseADFactory {
    private static boolean available;

    static {
        try {
            available = Class.forName("com.bytedance.sdk.openadsdk.TTAdSdk") != null;
        } catch (Throwable ex) {
        }
    }

    public TTADFactory() {
    }

    @Override
    public boolean available() {
        try {
            return Class.forName("com.bytedance.sdk.openadsdk.TTAdSdk") != null;
        } catch (Throwable ex) {
        }
        return false;
    }

    @Override
    public String getName() {
        return "aABn2y";
    }

    @Override
    public BannerAD createBannerAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, BannerAdListener listener) {
        if (!available) return null;
        TTAdSdkInitializer.initTTAdSdk(activity);
        return new TTBannerAD(activity, adSlotSetting, listener);
    }

    @Override
    public InterstitialAD createInterstitialAD(Activity activity, AdSlotSetting adSlotSetting, InterstitialAdListener listener) {
        if (!available) return null;
        TTAdSdkInitializer.initTTAdSdk(activity);
        return new TTInterstitialAD(activity, adSlotSetting, listener);
    }

    @Override
    public NativeAD createNativeAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeAdListener listener) {
        if (!available) return null;
        TTAdSdkInitializer.initTTAdSdk(activity);
        if (adContainer == null) throw new RuntimeException("adContainer should not be null");
        return new TTNativeAD(activity, adContainer, adSlotSetting, listener);
    }

    @Override
    public NativeExpressAD createNativeExpressAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeExpressAdListener listener) {
        return null;
    }

    @Override
    public NativeVideoAD createNativeVideoAD(Activity activity, AdSlotSetting adSlotSetting, NativeVideoAdListener listener) {
        return null;
    }

    @Override
    public SplashAD createSplashAD(Activity activity, ViewGroup container, View skipView, AdSlotSetting adSlotSetting, SplashAdListener listener) {
        if (!available) return null;
        TTAdSdkInitializer.initTTAdSdk(activity);
        return new TTSplashAD(activity, container, adSlotSetting, listener);
    }
}

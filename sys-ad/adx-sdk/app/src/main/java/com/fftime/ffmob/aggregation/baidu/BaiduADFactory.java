package com.fftime.ffmob.aggregation.baidu;

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
import com.fftime.ffmob.aggregation.baidu.banner.BaiduBannerAD;
import com.fftime.ffmob.aggregation.baidu.interstitial.BaiduInterstitialAD;
import com.fftime.ffmob.aggregation.baidu.nativead.BaiduNativeAD;
import com.fftime.ffmob.aggregation.baidu.splash.BaiduSplashAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeExpressAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeVideoAdListener;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;

public class BaiduADFactory extends BaseADFactory {
    private static boolean available;

    static {
        try {
            available = Class.forName("com.baidu.mobads.AdView") != null;
        } catch (Throwable ex) {
        }
    }

    @Override
    public String getName() {
        return "baidu";
    }

    @Override
    public boolean available() {
        try {
            return Class.forName("com.baidu.mobads.AdView") != null;
        } catch (Throwable ex) {
        }
        return false;
    }

    @Override
    public BannerAD createBannerAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, BannerAdListener listener) {
        if (!available) return null;
        return new BaiduBannerAD(activity, adContainer, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
    }

    @Override
    public NativeVideoAD createNativeVideoAD(Activity activity, AdSlotSetting adSlotSetting, NativeVideoAdListener listener) {
        return null;
    }

    @Override
    public InterstitialAD createInterstitialAD(Activity activity, AdSlotSetting adSlotSetting, InterstitialAdListener listener) {
        if (!available) return null;
        BaiduInterstitialAD interstitialAD = new BaiduInterstitialAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId());
        interstitialAD.setAdListener(listener);

        return interstitialAD;
    }

    @Override
    public NativeAD createNativeAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeAdListener listener) {
        if (!available) return null;
        return new BaiduNativeAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
    }

    @Override
    public NativeExpressAD createNativeExpressAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeExpressAdListener listener) {
        return null;
    }

    @Override
    public SplashAD createSplashAD(Activity activity, ViewGroup container, View skipView, AdSlotSetting adSlotSetting, SplashAdListener listener) {
        if (!available) return null;
        return new BaiduSplashAD(activity, container, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
    }
}

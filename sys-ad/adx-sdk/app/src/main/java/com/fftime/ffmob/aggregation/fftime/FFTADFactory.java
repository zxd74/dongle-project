package com.fftime.ffmob.aggregation.fftime;

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
import com.fftime.ffmob.aggregation.fftime.banner.FFTBannerAD;
import com.fftime.ffmob.aggregation.fftime.interstitial.FFTInterstitialAD;
import com.fftime.ffmob.aggregation.fftime.nativead.FFTNativeAD;
import com.fftime.ffmob.aggregation.fftime.splash.FFTSplashAD;

public class FFTADFactory extends BaseADFactory {
    @Override
    public String getName() {
        return "RvMNju";
    }

    @Override
    public BannerAD createBannerAD(Activity activity, ViewGroup adContainer,AdSlotSetting adSlotSetting, BannerAdListener listener) {
        return new FFTBannerAD(activity, adContainer,adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
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
    public InterstitialAD createInterstitialAD(Activity activity, AdSlotSetting adSlotSetting, InterstitialAdListener listener) {
        return new FFTInterstitialAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
    }

    @Override
    public NativeAD createNativeAD(Activity activity, ViewGroup adContainer,AdSlotSetting adSlotSetting, NativeAdListener listener) {
        return new FFTNativeAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
    }

    @Override
    public SplashAD createSplashAD(Activity activity, ViewGroup container, View skipView, AdSlotSetting adSlotSetting, SplashAdListener listener) {
        return new FFTSplashAD(activity, container, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
    }
}

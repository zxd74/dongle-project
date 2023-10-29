package com.fftime.ffmob.aggregation.api;

import android.app.Activity;
import android.content.Context;
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

public class FFTAggrAds extends BaseADFactory {

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public BannerAD createBannerAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, BannerAdListener listener) {
        return new AggrBannerAD(activity,adContainer,adSlotSetting,listener);
    }

    @Override
    public InterstitialAD createInterstitialAD(Activity activity, AdSlotSetting adSlotSetting, InterstitialAdListener listener) {
        return new AggrInterstitialAD(activity,adSlotSetting,listener);
    }

    @Override
    public NativeAD createNativeAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeAdListener listener) {
        return new AggrNativeAD(activity,adContainer,adSlotSetting.getAppId(),adSlotSetting.getPosId(),listener);
    }

    @Override
    public NativeExpressAD createNativeExpressAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeExpressAdListener listener) {
        return super.createNativeExpressAD(activity, adContainer, adSlotSetting, listener);
    }

    @Override
    public NativeVideoAD createNativeVideoAD(Activity activity, AdSlotSetting adSlotSetting, NativeVideoAdListener listener) {
        return super.createNativeVideoAD(activity, adSlotSetting, listener);
    }

    @Override
    public SplashAD createSplashAD(Activity activity, ViewGroup container, View skipView, AdSlotSetting adSlotSetting, SplashAdListener listener) {
        return new AggrSplashAD(activity,container,adSlotSetting.getAppId(),adSlotSetting.getPosId(),listener);
    }

    @Override
    public String getName() {
        return null;
    }
}

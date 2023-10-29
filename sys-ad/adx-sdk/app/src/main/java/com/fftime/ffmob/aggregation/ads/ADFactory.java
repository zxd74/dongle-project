package com.fftime.ffmob.aggregation.ads;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeExpressAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeVideoAdListener;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;

public interface ADFactory {
    //广告工厂初始化
    void init(Context context);
    //广告工厂名称
    String getName();
    //广告工厂是否可用
    boolean available();

    //创建BannerAD
    BannerAD createBannerAD(Activity activity, ViewGroup adContainer,AdSlotSetting adSlotSetting, BannerAdListener listener);
    //创建插屏广告
    InterstitialAD createInterstitialAD(Activity activity, AdSlotSetting adSlotSetting, InterstitialAdListener listener);
    //创建自渲染原生广告
    NativeAD createNativeAD(Activity activity, ViewGroup adContainer,AdSlotSetting adSlotSetting, NativeAdListener listener);
    //模版原生广告
    NativeExpressAD createNativeExpressAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeExpressAdListener listener);
    //原生视频广告
    NativeVideoAD createNativeVideoAD(Activity activity, AdSlotSetting adSlotSetting, NativeVideoAdListener listener);
    //创建开屏广告
    SplashAD createSplashAD(Activity activity, ViewGroup container, View skipView, AdSlotSetting adSlotSetting, SplashAdListener listener);
}

package com.fftime.ffmob.aggregation.gdt;

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
import com.fftime.ffmob.aggregation.gdt.banner.GDTBannerAD;
import com.fftime.ffmob.aggregation.gdt.interstitial.GDTInterstitialAD;
import com.fftime.ffmob.aggregation.gdt.nativead.GDTNativeAD;
import com.fftime.ffmob.aggregation.gdt.nativead.GDTNativeExpressAD;
import com.fftime.ffmob.aggregation.gdt.splash.GDTSplashAD;

public class GDTADFactory extends BaseADFactory {
    private static boolean available;

    static {
        try {
            available = Class.forName("com.qq.e.ads.banner.BannerView") != null;
        } catch (Throwable t) {
        }
    }

    @Override
    public boolean available() {
        try {
            return Class.forName("com.qq.e.ads.banner.BannerView") != null;
        } catch (Throwable ex) {
        }
        return false;
    }

    @Override
    public String getName() {
        return "gdt";
    }

    @Override
    public BannerAD createBannerAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, BannerAdListener listener) {
        if (!available) return null;
        return new GDTBannerAD(activity, adContainer, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
    }

    @Override
    public InterstitialAD createInterstitialAD(Activity activity, AdSlotSetting adSlotSetting, InterstitialAdListener listener) {
        if (!available) return null;
        GDTInterstitialAD interstitialAD = new GDTInterstitialAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId());
        interstitialAD.setAdListener(listener);

        return interstitialAD;
    }

    @Override
    public NativeAD createNativeAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeAdListener listener) {
        if (!available) return null;
        GDTNativeAD nativeAD = new GDTNativeAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
        return nativeAD;
    }

    @Override
    public NativeExpressAD createNativeExpressAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeExpressAdListener listener) {
        if (!available) return null;
        return new GDTNativeExpressAD(activity, adContainer, adSlotSetting, listener);
    }

    @Override
    public NativeVideoAD createNativeVideoAD(Activity activity, AdSlotSetting adSlotSetting, NativeVideoAdListener listener) {
        return null;
    }

    @Override
    public SplashAD createSplashAD(Activity activity, ViewGroup container, View skipView, AdSlotSetting adSlotSetting, SplashAdListener listener) {
        if (!available) return null;
        return new GDTSplashAD(activity, container, skipView, adSlotSetting.getAppId(), adSlotSetting.getPosId(), listener);
    }
}

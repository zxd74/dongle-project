package com.fftime.ffmob.aggregation.toutiao.banner;

import android.app.Activity;
import android.view.View;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTBannerAd;
import com.fftime.ffmob.aggregation.ads.BannerAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;

import java.util.concurrent.atomic.AtomicReference;

public class TTBannerAD implements BannerAD {
    private TTAdNative ttAdNative;
    private AdSlot adSlot;
    private BannerAdListener listener;
    private AtomicReference<View> ttAdViewHolder;


    public TTBannerAD(Activity activity, AdSlotSetting adSlotSetting, final BannerAdListener listener) {
        //TTAdSdk.init();
        ttAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        adSlot = new AdSlot.Builder().setAdCount(1).setNativeAdType(AdSlot.TYPE_BANNER).setCodeId(adSlotSetting.getPosId()).
                setImageAcceptedSize(adSlotSetting.getWidth(), adSlotSetting.getHeight()).build();
        this.listener = listener;
        this.ttAdViewHolder = new AtomicReference<>();
    }

    @Override
    public void loadAD() {
        ttAdNative.loadBannerAd(adSlot, new TTAdNative.BannerAdListener() {
            @Override
            public void onError(int i, String s) {
                listener.onAdFailed(new FFAdError(i, s));
            }

            @Override
            public void onBannerAdLoad(TTBannerAd ttBannerAd) {
                listener.onAdSuccess();
                ttAdViewHolder.set(ttBannerAd.getBannerView());

                ttBannerAd.setBannerInteractionListener(new TTBannerAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int i) {
                        listener.onAdClick();
                    }

                    @Override
                    public void onAdShow(View view, int i) {
                        listener.onAdExposure();
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        //DO NOTHING
    }

    @Override
    public void setAdListener(BannerAdListener listener) {
    }

    @Override
    public View getAdView() {
        return ttAdViewHolder.get();
    }
}

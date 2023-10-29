package com.fftime.ffmob.aggregation.toutiao.nativead;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.fftime.ffmob.aggregation.ads.NativeAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.aggregation.model.NativeAdData;
import com.fftime.ffmob.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TTNativeAD implements NativeAD {
    private TTAdNative ttAdNative;
    private AdSlot adSlot;
    private NativeAdListener listener;
    private ViewGroup adContainer;

    public TTNativeAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeAdListener listener) {
        ttAdNative = TTAdSdk.getAdManager().createAdNative(activity);
        adSlot = new AdSlot.Builder().setCodeId(adSlotSetting.getPosId()).setAdCount(1).
                setImageAcceptedSize(adSlotSetting.getWidth(), adSlotSetting.getHeight()).setSupportDeepLink(true).build();
        this.listener = listener;
        this.adContainer = adContainer;
    }

    @Override
    public void loadAD() {
        ttAdNative.loadNativeAd(adSlot, new TTAdNative.NativeAdListener() {
            @Override
            public void onError(int i, String s) {
                listener.onAdFailed(new FFAdError(i, s));
            }

            @Override
            public void onNativeAdLoad(List<TTNativeAd> list) {
                TTNativeAd natiAd = list.get(0);
                listener.onAdSuccess(new TTNativeAdData(natiAd));
                adContainer.removeAllViews();
                adContainer.addView(natiAd.getAdView());

                natiAd.registerViewForInteraction(adContainer, adContainer, new TTNativeAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, TTNativeAd ttNativeAd) {
                        listener.onAdClick();
                    }

                    @Override
                    public void onAdCreativeClick(View view, TTNativeAd ttNativeAd) {
                        //DO NOTHING
                    }

                    @Override
                    public void onAdShow(TTNativeAd ttNativeAd) {
                        listener.onAdExposure();
                    }
                });
            }
        });
    }

    public static class TTNativeAdData implements NativeAdData {
        private TTNativeAd ttNativeAd;

        public TTNativeAdData(TTNativeAd nativeAd) {
            this.ttNativeAd = nativeAd;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getTitle() {
            return ttNativeAd.getTitle();
        }

        @Override
        public String getIcon() {
            TTImage ttImg = ttNativeAd.getIcon();
            return ttImg == null ? StringUtil.EMPTY : ttImg.getImageUrl();
        }

        @Override
        public String getDesc() {
            return ttNativeAd.getDescription();
        }

        @Override
        public String getDesc2() {
            return null;
        }

        @Override
        public String getImg() {
            List<TTImage> imgs = ttNativeAd.getImageList();
            return imgs == null || imgs.isEmpty() ? StringUtil.EMPTY : imgs.get(0).getImageUrl();
        }

        @Override
        public String getTmid() {
            return null;
        }

        @Override
        public String getSource() {
            return null;
        }

        @Override
        public String getCtatext() {
            return ttNativeAd.getButtonText();
        }

        @Override
        public List<String> getImgList() {
            List<TTImage> imgs = ttNativeAd.getImageList();
            if (imgs == null || imgs.isEmpty()) return null;

            List<String> imageList = new ArrayList<>();
            for (TTImage img : imgs) {
                imageList.add(img.getImageUrl());
            }
            return imageList;
        }

        @Override
        public void imp(View v) {

        }

        @Override
        public void click(View v) {

        }
    }
}

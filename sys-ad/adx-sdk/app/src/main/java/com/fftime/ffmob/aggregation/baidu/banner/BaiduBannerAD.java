package com.fftime.ffmob.aggregation.baidu.banner;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.fftime.ffmob.aggregation.ads.BannerAD;
import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;

import org.json.JSONObject;

public class BaiduBannerAD implements BannerAD {
    private final AdView adView;
    private final ViewGroup adContainer;

    public BaiduBannerAD(Activity activity, ViewGroup adContainer,String appId, String posId, final BannerAdListener listener) {
        AdView.setAppSid(activity, appId);
        this.adContainer=adContainer;
        adView = new AdView(activity, posId);
        adView.setListener(new BaiduAdViewListener(listener));

        this.adContainer.removeAllViews();
        //this.adContainer.addView(adView);
    }

    @Override
    public void loadAD() {
        //DO NOTHING
    }

    @Override
    public void destroy() {
        if (adView != null) adView.destroy();
    }

    @Override
    public void setAdListener(BannerAdListener listener) {
        if (adView != null) adView.setListener(new BaiduAdViewListener(listener));
    }

    @Override
    public View getAdView() {
        return adView;
    }

    public class BaiduAdViewListener implements AdViewListener {
        private final BannerAdListener listener;

        public BaiduAdViewListener(BannerAdListener listener) {
            this.listener = listener;
        }

        @Override
        public void onAdReady(AdView adView) {
            listener.onAdSuccess();
            adContainer.addView(adView);
        }

        @Override
        public void onAdShow(JSONObject jsonObject) {
            listener.onAdExposure();
        }

        @Override
        public void onAdClick(JSONObject jsonObject) {
            listener.onAdClick();
        }

        @Override
        public void onAdFailed(String s) {
            listener.onAdFailed(new FFAdError(-1, s));
        }

        @Override
        public void onAdSwitch() {
        }

        @Override
        public void onAdClose(JSONObject jsonObject) {
        }
    }
}

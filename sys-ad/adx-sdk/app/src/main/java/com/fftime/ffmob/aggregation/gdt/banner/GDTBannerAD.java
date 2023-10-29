package com.fftime.ffmob.aggregation.gdt.banner;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.BannerAD;
import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.BannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.comm.util.AdError;

public class GDTBannerAD implements BannerAD {
    private final BannerView bannerView;
    private final ViewGroup adContainer;

    public GDTBannerAD(Activity activity, ViewGroup adContainer, String appId, String posId, BannerAdListener listener) {
        bannerView = new BannerView(activity, ADSize.BANNER, appId, posId);
        bannerView.setADListener(new BaiduBannerAdListener(listener));

        adContainer.removeAllViews();
        this.adContainer = adContainer;
        //adContainer.addView(bannerView);
    }

    @Override
    public void loadAD() {
        bannerView.loadAD();
    }

    @Override
    public void destroy() {
        if (bannerView != null) bannerView.destroy();
    }

    @Override
    public void setAdListener(BannerAdListener listener) {
        bannerView.setADListener(new BaiduBannerAdListener(listener));
    }

    @Override
    public View getAdView() {
        return bannerView;
    }

    private class BaiduBannerAdListener implements BannerADListener {
        private final BannerAdListener listener;

        public BaiduBannerAdListener(BannerAdListener listener) {
            this.listener = listener;
        }

        @Override
        public void onNoAD(AdError adError) {
            listener.onAdFailed(new FFAdError(-1, "No ad"));
        }

        @Override
        public void onADReceiv() {
            adContainer.addView(bannerView);
            listener.onAdSuccess();
        }

        @Override
        public void onADExposure() {
            listener.onAdExposure();
        }

        @Override
        public void onADClosed() {
        }

        @Override
        public void onADClicked() {
            listener.onAdClick();
        }

        @Override
        public void onADLeftApplication() {
        }

        @Override
        public void onADOpenOverlay() {
        }

        @Override
        public void onADCloseOverlay() {
        }
    }
}

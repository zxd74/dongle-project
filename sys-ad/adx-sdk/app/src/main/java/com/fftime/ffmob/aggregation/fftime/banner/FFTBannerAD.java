package com.fftime.ffmob.aggregation.fftime.banner;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.BannerAD;
import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.banner.BannerADListener;
import com.fftime.ffmob.banner.BannerADRequest;

public class FFTBannerAD implements BannerAD {
    private final com.fftime.ffmob.banner.BannerAD bannerAD;

    public FFTBannerAD(Activity activity, final ViewGroup adContainer, String appId, String posId, final BannerAdListener listener) {
        bannerAD = new com.fftime.ffmob.banner.BannerAD(activity, appId, posId, new BannerADListener() {
            @Override
            public void onADReady() {
                listener.onAdSuccess();
                adContainer.removeAllViews();
                adContainer.addView(bannerAD);
            }

            @Override
            public void onADFail() {
                listener.onAdFailed(new FFAdError(-1, "load ad failed"));
            }
        });
    }

    @Override
    public void loadAD() {
        bannerAD.loadAD(new BannerADRequest(0));

    }

    @Override
    public void destroy() {
    }

    @Override
    public void setAdListener(BannerAdListener listener) {
        //DO NOTHING
    }

    @Override
    public View getAdView() {
        return bannerAD;
    }
}

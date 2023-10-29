package com.fftime.ffmob.aggregation.fftime.interstitial;


import android.app.Activity;

import com.fftime.ffmob.aggregation.ads.InterstitialAD;
import com.fftime.ffmob.aggregation.base.listener.AdListener;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.interstitial.InterstitialADListener;

public class FFTInterstitialAD implements InterstitialAD {
    private com.fftime.ffmob.interstitial.InterstitialAD interstitialAD;

    public FFTInterstitialAD(Activity activity, String appId, String posId, final InterstitialAdListener listener) {
        this.interstitialAD = new com.fftime.ffmob.interstitial.InterstitialAD(activity, appId, posId, new InterstitialADListener() {
            @Override
            public void onADReady() {
            }

            @Override
            public void onADFail() {
                listener.onAdFailed(new FFAdError(-1, "fail loadad"));
            }

            @Override
            public void onADPresent() {
                listener.onAdExposure();
            }

            @Override
            public void onADClosed() {
            }
        });
    }

    @Override
    public void loadAD() {
        interstitialAD.load();
    }

    @Override
    public void showAD() {
        interstitialAD.show();
    }

    @Override
    public void setAdListener(InterstitialAdListener listener) {
        //DO NOTHING
    }

    @Override
    public void destroy() {
    }
}

package com.fftime.ffmob.aggregation.gdt.interstitial;

import android.app.Activity;

import com.fftime.ffmob.aggregation.ads.InterstitialAD;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.qq.e.ads.interstitial.InterstitialADListener;
import com.qq.e.comm.util.AdError;

public class GDTInterstitialAD implements InterstitialAD {
    private com.qq.e.ads.interstitial.InterstitialAD interstitialAD;

    public GDTInterstitialAD(Activity activity, String appId, String posId) {
        interstitialAD = new com.qq.e.ads.interstitial.InterstitialAD(activity, appId, posId);
    }

    @Override
    public void loadAD() {
        interstitialAD.loadAD();
    }

    @Override
    public void showAD() {
        interstitialAD.show();
    }

    @Override
    public void setAdListener(final InterstitialAdListener listener) {
        interstitialAD.setADListener(new InterstitialADListener() {
            @Override
            public void onADReceive() {
                listener.onAdSuccess();
            }

            @Override
            public void onNoAD(AdError adError) {
                listener.onAdFailed(new FFAdError(adError.getErrorCode(), adError.getErrorMsg()));
            }

            @Override
            public void onADOpened() {
            }

            @Override
            public void onADExposure() {
                listener.onAdExposure();
            }

            @Override
            public void onADClicked() {
                listener.onAdClick();
            }

            @Override
            public void onADLeftApplication() {
            }

            @Override
            public void onADClosed() {
            }
        });
    }

    @Override
    public void destroy() {
        if (interstitialAD != null)
            interstitialAD.destroy();
    }
}

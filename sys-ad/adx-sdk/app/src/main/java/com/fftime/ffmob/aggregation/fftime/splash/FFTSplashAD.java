package com.fftime.ffmob.aggregation.fftime.splash;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.SplashAD;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.splash.SplashADListener;

public class FFTSplashAD implements SplashAD {
    private com.fftime.ffmob.splash.SplashAD splashAD;

    public FFTSplashAD(Activity activity, ViewGroup adContainer, String appId, String posId,
                       final SplashAdListener listener) {
        this.splashAD = new com.fftime.ffmob.splash.SplashAD(activity, appId, posId, adContainer,
                new SplashADListener() {
                    @Override
                    public void onSplashFinish() {
                        listener.onAdFinished();
                    }

                    @Override
                    public void onSplashFail() {
                        Log.e("FFTSplashAD","Load FFTSplashAd fail");
                        listener.onAdFailed(new FFAdError(-1, "LoadAd failed"));
                    }
                });
    }

    @Override
    public void fetchAndShowAD() {
        Log.i("FFTSplashAD","fetch fftsplash ad");
        this.splashAD.fetchAndShow();
    }
}

package com.fftime.ffmob.aggregation.api;


import android.app.Activity;

import com.fftime.ffmob.aggregation.ads.ADFactory;
import com.fftime.ffmob.aggregation.ads.ADFactorys;
import com.fftime.ffmob.aggregation.ads.AggrAD;
import com.fftime.ffmob.aggregation.ads.InterstitialAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.model.AdLoadResult;
import com.fftime.ffmob.aggregation.model.AdPlatform;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.common.adservices.ApiService;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AggrInterstitialAD implements InterstitialAD, AggrAD {
    private Activity activity;
    private AdSlotSetting adSlotSetting;
    private InterstitialAdListener listener;
    private InterstitialAD interstitialAD;
    private AdPlatform adPlatform;

    private ConcurrentMap<String, InterstitialAD> interstitialADs = new ConcurrentHashMap<>();

    public AggrInterstitialAD(Activity activity, AdSlotSetting adSlotSetting, InterstitialAdListener listener) {
        this.activity = activity;
        this.adSlotSetting = adSlotSetting;
        this.listener = listener;
    }

    @Override
    public void loadAD() {
    }

    private void loadAD(AdPlatform adp, AdLoadResult adLoadResult, boolean stop) {
        adLoadResult.reset();
        ADFactory adFactory = ADFactorys.getADFactory(adp.getId());
        AdSlotSetting adSlot = AdSlotSetting.newBuilder().appId(adp.getAppId()).posId(adp.getPosId()).build();

        InterstitialAD interstitialAD = adFactory.createInterstitialAD(activity, adSlot, new AggrInterstitialADListener(adLoadResult, stop));
        if(interstitialAD==null) return;
        interstitialADs.put(adp.getId(), interstitialAD);
        interstitialAD.loadAD();
    }

    @Override
    public void showAD() {
        if (interstitialAD != null) interstitialAD.showAD();
    }

    @Override
    public void setAdListener(InterstitialAdListener listener) {

    }

    @Override
    public void destroy() {
        if (interstitialAD != null) interstitialAD.destroy();
    }

    @Override
    public AdPlatform getAdPlatform() {
        return this.adPlatform;
    }

    private class AggrInterstitialADListener implements InterstitialAdListener {
        private AdLoadResult adLoadResult;
        private boolean stop;

        public AggrInterstitialADListener(AdLoadResult adLoadResult, boolean stop) {
            this.adLoadResult = adLoadResult;
            this.stop = stop;
        }

        @Override
        public void onAdFailed(FFAdError error) {
            adLoadResult.setCompleted(true);
            if (stop) {
                listener.onAdFailed(error);
            }
        }

        @Override
        public void onAdSuccess() {
            adLoadResult.setCompleted(true);
            adLoadResult.setLoaded(true);

            listener.onAdSuccess();
        }

        @Override
        public void onAdClick() {
            listener.onAdClick();
        }

        @Override
        public void onAdExposure() {
            listener.onAdExposure();
        }
    }
}

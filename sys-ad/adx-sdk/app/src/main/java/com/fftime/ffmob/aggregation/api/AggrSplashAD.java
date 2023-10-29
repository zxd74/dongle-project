package com.fftime.ffmob.aggregation.api;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.ADFactory;
import com.fftime.ffmob.aggregation.ads.ADFactorys;
import com.fftime.ffmob.aggregation.ads.SplashAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;
import com.fftime.ffmob.aggregation.model.AdLoadResult;
import com.fftime.ffmob.aggregation.model.AdPlatform;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.splash.SplashADListener;
import com.fftime.ffmob.util.HandlerUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AggrSplashAD extends BaseAggrAD implements SplashAD {
    private Activity activity;
    private ViewGroup adContainer;
    private ConcurrentMap<String, SplashAD> splashAds = new ConcurrentHashMap<>();

    public AggrSplashAD(Activity activity, ViewGroup adContainer, String appId, String posId, SplashAdListener listener) {
        StatusManager.getInstance().init(activity);
        this.adSlotSetting = AdSlotSetting.newBuilder().appId(appId).posId(posId).build();
        this.activity = activity;
        this.adContainer = adContainer;
        this.adListener = listener;
    }

    @Override
    public void onAdLoaded() {
        HandlerUtils.getUiThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                ((SplashAdListener) adListener).onAdFinished();
            }
        });
    }

    @Override
    public void loadAD(final AdPlatform adp, final AdLoadResult adLoadResult, final boolean stop) {
        adLoadResult.reset();
        final ADFactory adFactory = ADFactorys.getADFactory(adp.getId());
        final AdSlotSetting adSlot = AdSlotSetting.newBuilder().appId(adp.getAppId()).posId(adp.getPosId()).build();
        StatusManager.getInstance().getAppStatus().putAppExtParams("adt","2");

        HandlerUtils.getUiThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                SplashAD splashAD = adFactory.createSplashAD(activity, adContainer, null, adSlot, new AggrSplashAdListener(adLoadResult, stop));
                if (splashAD == null) return;
                splashAds.put(adp.getId(), splashAD);
                splashAD.fetchAndShowAD();
            }
        });
    }

    @Override
    public void order(final AdLoadResult adLoadResult) {
        adLoadResult.reset();
        HandlerUtils.getUiThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                _order(adLoadResult);
            }
        });
    }

    private void _order(final AdLoadResult adLoadResult){
        StatusManager.getInstance().getAppStatus().putAppExtParams("adt", "1");
        com.fftime.ffmob.splash.SplashAD splashAD = new com.fftime.ffmob.splash.SplashAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId(), adContainer, new SplashADListener() {
            @Override
            public void onSplashFinish() {
                adLoadResult.setLoaded(true);
                adLoadResult.setCompleted(true);

                onAdLoaded();
            }

            @Override
            public void onSplashFail() {
                adLoadResult.setCompleted(true);
            }
        });
        splashAD.fetchAndShow();
    }

    @Override
    public void def(final AdLoadResult adLoadResult) {
        adLoadResult.reset();

        HandlerUtils.getUiThreadHandler().post(new Runnable() {
            @Override
            public void run() {
               _def(adLoadResult);
            }
        });
    }

    private void _def(final AdLoadResult adLoadResult){
        StatusManager.getInstance().getAppStatus().putAppExtParams("adt", "3");
        com.fftime.ffmob.splash.SplashAD splashAD = new com.fftime.ffmob.splash.SplashAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId(), adContainer, new SplashADListener() {
            @Override
            public void onSplashFinish() {
                adLoadResult.setLoaded(true);
                adLoadResult.setCompleted(true);

                onAdLoaded();
            }

            @Override
            public void onSplashFail() {
                adLoadResult.setCompleted(true);
            }
        });
        splashAD.fetchAndShow();
    }

    @Override
    public void fetchAndShowAD() {
        loadAggrAD();
    }

    @Override
    public AdPlatform getAdPlatform() {
        return adPlatform;
    }

    private class AggrSplashAdListener implements SplashAdListener {
        private AdLoadResult adLoadResult;
        private boolean stop;

        public AggrSplashAdListener(AdLoadResult adLoadResult, boolean stop) {
            this.adLoadResult = adLoadResult;
            this.stop = stop;
        }

        @Override
        public void onAdFinished() {
            Log.i("AggrSplashAD", "onAdFinished");
            adLoadResult.setLoaded(true);
            adLoadResult.setCompleted(true);
            if(adListener instanceof SplashAdListener){
                ((SplashAdListener) adListener).onAdFinished();
            }
        }

        @Override
        public void onAdFailed(FFAdError error) {
            Log.i("AggrSplashAD", "onAdFailed");
            adLoadResult.setCompleted(true);
        }

        @Override
        public void onAdSuccess() {
        }

        @Override
        public void onAdClick() {
            adListener.onAdClick();
        }

        @Override
        public void onAdExposure() {
            adListener.onAdExposure();
        }
    }
}

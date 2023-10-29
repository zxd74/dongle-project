package com.fftime.ffmob.aggregation.api;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.ADFactory;
import com.fftime.ffmob.aggregation.ads.ADFactorys;
import com.fftime.ffmob.aggregation.ads.NativeAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.fftime.nativead.FFTNativeAD;
import com.fftime.ffmob.aggregation.model.AdLoadResult;
import com.fftime.ffmob.aggregation.model.AdPlatform;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.aggregation.model.NativeAdData;
import com.fftime.ffmob.common.adservices.ApiService;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.nativead.NativeADListener;
import com.fftime.ffmob.util.HandlerUtils;

public class AggrNativeAD extends BaseAggrAD implements NativeAD {
    private static final String TAG = AggrNativeAD.class.getSimpleName();

    private Activity activity;
    private ViewGroup adContainer;
    private NativeAdListener listener;

    public AggrNativeAD(Activity activity, ViewGroup adContainer, String appId, String posId, NativeAdListener listener) {
        StatusManager.getInstance().init(activity);
        this.adListener = listener;
        this.listener = (NativeAdListener) this.adListener;
        this.adSlotSetting = AdSlotSetting.newBuilder().appId(appId).posId(posId).build();
        this.adContainer = adContainer;
        this.activity = activity;
    }

    @Override
    public void loadAD() {
        loadAggrAD();
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

    private void _order(final AdLoadResult adLoadResult) {
        StatusManager.getInstance().getAppStatus().putAppExtParams("adt", "1");
        com.fftime.ffmob.nativead.NativeAD nativeAD = new com.fftime.ffmob.nativead.NativeAD(activity,adSlotSetting.getAppId(),adSlotSetting.getPosId());
        nativeAD.loadAD(new NativeADListener() {
            @Override
            public void onSucc(NatiAd natiAd) {
                adLoadResult.setLoaded(true);
                adLoadResult.setCompleted(true);

                listener.onAdSuccess(new FFTNativeAD.FFTNativeAdData(natiAd,activity,listener));
            }

            @Override
            public void onFail(int errCode, String msg) {
                Log.i(TAG, "加载原生订单广告失败");
                adLoadResult.setCompleted(true);
            }
        });
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

    private void _def(final AdLoadResult adLoadResult) {
        StatusManager.getInstance().getAppStatus().putAppExtParams("adt", "3");
        com.fftime.ffmob.nativead.NativeAD nativeAD = new com.fftime.ffmob.nativead.NativeAD(activity,adSlotSetting.getAppId(),adSlotSetting.getPosId());
        nativeAD.loadAD(new NativeADListener() {
            @Override
            public void onSucc(NatiAd natiAd) {
                adLoadResult.setLoaded(true);
                adLoadResult.setCompleted(true);

                listener.onAdSuccess(new FFTNativeAD.FFTNativeAdData(natiAd,activity,listener));
            }

            @Override
            public void onFail(int errCode, String msg) {
                Log.i(TAG, "加载打底广告失败");
                adLoadResult.setCompleted(true);
            }
        });
    }

    @Override
    public void onAdLoaded() {
    }

    @Override
    public void loadAD(AdPlatform adp, final AdLoadResult adLoadResult, final boolean stop) {
        adLoadResult.reset();
        final ADFactory adFactory = ADFactorys.getADFactory(adp.getId());
        if(adFactory == null){
            new AggrNativeAdListener(adLoadResult,stop).onAdFailed(new FFAdError(-1,"no platform!"));
        }
        final AdSlotSetting adSlot = AdSlotSetting.newBuilder().appId(adp.getAppId()).posId(adp.getPosId()).build();

        StatusManager.getInstance().getAppStatus().putAppExtParams("adt", "2");
        HandlerUtils.getUiThreadHandler().post(new Runnable() {
            @Override
            public void run() {
                NativeAD nativeAD = adFactory.createNativeAD(activity, adContainer, adSlot,
                        new AggrNativeAdListener(adLoadResult, stop));
                if (nativeAD == null) return;
                nativeAD.loadAD();
            }
        });
    }

    @Override
    public AdPlatform getAdPlatform() {
        return this.adPlatform;
    }

    private class AggrNativeAdListener implements NativeAdListener {
        private final AdLoadResult adLoadResult;
        private final boolean stop;

        public AggrNativeAdListener(AdLoadResult adLoadResult, boolean stop) {
            this.adLoadResult = adLoadResult;
            this.stop = stop;
        }

        @Override
        public void onAdSuccess(NativeAdData nativeAdData) {
            adLoadResult.setCompleted(true);
            adLoadResult.setLoaded(true);
            listener.onAdSuccess(nativeAdData);
            //AggrNativeAD.this.listener.onAdSuccess(nativeAdData);
        }

        @Override
        public void onAdFailed(FFAdError error) {
            adLoadResult.setCompleted(true);
            //AggrNativeAD.this.listener.onAdFailed(error);
        }

        @Override
        public void onAdSuccess() {
            //listener.onAdSuccess();
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

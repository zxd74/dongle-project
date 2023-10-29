package com.fftime.ffmob.aggregation.api;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.ADFactory;
import com.fftime.ffmob.aggregation.ads.ADFactorys;
import com.fftime.ffmob.aggregation.ads.AggrAD;
import com.fftime.ffmob.aggregation.ads.BannerAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.model.AdLoadResult;
import com.fftime.ffmob.aggregation.model.AdPlatform;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.common.adservices.ApiService;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AggrBannerAD implements BannerAD, AggrAD {
    private BannerAdListener listener;
    private Activity activity;
    private ViewGroup adContainer;
    private AdSlotSetting adSlotSetting;
    private ConcurrentMap<String, BannerAD> bannerAds = new ConcurrentHashMap<>();
    private BannerAD bannerAD;
    private AdPlatform adPlatform;

    public AggrBannerAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, BannerAdListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.adContainer = adContainer;
        this.adSlotSetting = adSlotSetting;
    }

    @Override
    public void loadAD() {
        //包段sdk投放
        ApiService.getInstance().getSdkAdsByCPT(this.adSlotSetting.getAppId(), this.adSlotSetting.getPosId(),
                new ApiService.DefGetSdkAdsCallback() {
                    @Override
                    public void onApiResponse(final List<AdPlatform> adPlatforms) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final Iterator<AdPlatform> iterator = adPlatforms.iterator();

                                AdLoadResult adLoadResult = new AdLoadResult();
                                while (iterator.hasNext()) {
                                    AdPlatform adp = iterator.next();
                                    loadAD(adp, adLoadResult, !iterator.hasNext());
                                    while (!adLoadResult.isCompleted()) {
                                    }
                                    if (adLoadResult.isLoaded()) {
                                        bannerAD = bannerAds.get(adp.getId());
                                        adContainer.addView(bannerAD.getAdView());
                                        adPlatform = adp;
                                        break;
                                    }
                                }

                                ApiService.getInstance().getSdkAdsByCPM(adSlotSetting.getAppId(), adSlotSetting.getPosId(),
                                        new ApiService.GetSdkAdsCallback() {
                                    @Override
                                    public void onApiResponse(List<AdPlatform> adPlatforms) {

                                    }

                                    @Override
                                    public void onFail() {

                                    }
                                });
                            }
                        }).start();
                    }
                });
    }

    private void loadAD(AdPlatform adp, AdLoadResult adLoadResult, boolean stop) {
        adLoadResult.reset();
        ADFactory adFactory = ADFactorys.getADFactory(adp.getId());
        AdSlotSetting adSlot = AdSlotSetting.newBuilder().appId(adp.getAppId()).posId(adp.getPosId()).build();

        BannerAD bannerAD = adFactory.createBannerAD(activity, adContainer, adSlot, new AggrBannerADListener(adLoadResult, stop));
        if (bannerAD == null) return;
        bannerAds.put(adp.getId(), bannerAD);
        bannerAD.loadAD();
    }

    @Override
    public void destroy() {
        if (bannerAD != null) bannerAD.destroy();
    }

    @Override
    public void setAdListener(BannerAdListener listener) {

    }

    @Override
    public View getAdView() {
        return bannerAD.getAdView();
    }

    @Override
    public AdPlatform getAdPlatform() {
        return this.adPlatform;
    }

    private class AggrBannerADListener implements BannerAdListener {
        private AdLoadResult adLoadResult;
        private boolean stop;

        public AggrBannerADListener(AdLoadResult adLoadResult, boolean stop) {
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

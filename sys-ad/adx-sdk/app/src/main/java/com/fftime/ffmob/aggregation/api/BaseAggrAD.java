package com.fftime.ffmob.aggregation.api;

import android.util.Log;

import com.fftime.ffmob.aggregation.ads.AggrAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.AdListener;
import com.fftime.ffmob.aggregation.model.AdLoadResult;
import com.fftime.ffmob.aggregation.model.AdPlatform;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.common.adservices.ApiService;

import java.util.Iterator;
import java.util.List;

public abstract class BaseAggrAD implements AggrAD {
    protected AdListener adListener;
    protected AdSlotSetting adSlotSetting;
    protected AdPlatform adPlatform;

    public final void loadAggrAD() {
        //包段->订单->包量->打底广告
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    _loadAggrAD();
                } catch (Throwable ex) {

                }
            }
        }, "AggrADLoader-Thread").start();
    }

    private void _loadAggrAD() {
        AdLoadResult adLoadResult = new AdLoadResult();
        cpt(adLoadResult);//sdk包段
        adLoadResult.await();
        if (!adLoadResult.isLoaded()) {
            order(adLoadResult);//原生直投
            adLoadResult.await();
        }
        if (!adLoadResult.isLoaded()) {
            cpm(adLoadResult);//包量
            adLoadResult.await();
        }
        if (!adLoadResult.isLoaded()) {
            def(adLoadResult);//打底
            adLoadResult.await();
        }

        if (!adLoadResult.isLoaded()) {
            Log.i("BaseAggrAD", "加载聚合广告失败");
            adListener.onAdFailed(new FFAdError(-1, "load ad failed"));
        } else {
//            onAdLoaded();
        }
    }

    public void cpt(final AdLoadResult adLoadResult) {
        ApiService.getInstance().getSdkAdsByCPT(adSlotSetting.getAppId(), adSlotSetting.getPosId(), new ApiService.GetSdkAdsCallback() {
            @Override
            public void onApiResponse(final List<AdPlatform> adPlatforms) {
                final Iterator<AdPlatform> iterator = adPlatforms.iterator();

                AdLoadResult adlr = new AdLoadResult();
                while (iterator.hasNext()) {
                    AdPlatform adp = iterator.next();
                    loadAD(adp, adlr, !iterator.hasNext());
                    adlr.await();

                    if (adlr.isLoaded()) {
                        adLoadResult.setLoaded(true);
                        adPlatform = adp;
                        Log.i("BaseAggrAD","加载sdk包段广告成功");
                        break;
                    }
                }
                adLoadResult.setCompleted(true);
            }

            @Override
            public void onFail() {
                adLoadResult.setCompleted(true);
            }
        });
    }

    public void cpm(final AdLoadResult adLoadResult) {
        adLoadResult.reset();
        ApiService.getInstance().getSdkAdsByCPM(adSlotSetting.getAppId(), adSlotSetting.getPosId(), new ApiService.GetSdkAdsCallback() {
            @Override
            public void onApiResponse(final List<AdPlatform> adPlatforms) {
                final Iterator<AdPlatform> iterator = adPlatforms.iterator();

                AdLoadResult adlr = new AdLoadResult();
                while (iterator.hasNext()) {
                    AdPlatform adp = iterator.next();
                    loadAD(adp, adlr, !iterator.hasNext());//这个请求失败 也导致加载sdk包量广告失败
                    adlr.await();

                    if (adlr.isLoaded()) {
                        adLoadResult.setLoaded(true);
                        adPlatform = adp;
                        Log.i("BaseAggrAD","加载sdk包量广告成功");
                        break;
                    }
                }
                adLoadResult.setCompleted(true);
            }

            @Override
            public void onFail() {
                Log.e("BaseAggrAD","加载sdk包量广告失败");
                adLoadResult.setCompleted(true);
            }
        });
    }

    public abstract void loadAD(AdPlatform adPlatform, AdLoadResult adlr, boolean stop);

    public abstract void order(AdLoadResult adLoadResult);

    public abstract void def(AdLoadResult adLoadResult);

    public abstract void onAdLoaded();
}

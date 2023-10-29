package com.fftime.ffmob.aggregation.gdt.nativead;

import android.app.Activity;

import com.fftime.ffmob.aggregation.ads.NativeVideoAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.NativeVideoAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.aggregation.model.NativeVideoAdData;
import com.qq.e.ads.nativ.NativeMediaAD;
import com.qq.e.ads.nativ.NativeMediaADData;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;

import java.util.List;

public class GDTNativeVideoAD implements NativeVideoAD {
    private NativeMediaAD nativeMediaAD;

    public GDTNativeVideoAD(Activity activity, AdSlotSetting adSlotSetting, final NativeVideoAdListener listener) {
        nativeMediaAD = new NativeMediaAD(activity, adSlotSetting.getAppId(), adSlotSetting.getPosId(), new NativeMediaAD.NativeMediaADListener() {
            @Override
            public void onADLoaded(List<NativeMediaADData> list) {
                NativeMediaADData nativeMediaADData = list.get(0);
                int patternType = nativeMediaADData.getAdPatternType();
                if (patternType != AdPatternType.NATIVE_VIDEO) {
                    listener.onAdFailed(new FFAdError(-1, "Not native video ad"));
                } else {
                    //listener.onAdSuccess();
                    nativeMediaADData.preLoadVideo();
                }
            }

            @Override
            public void onNoAD(AdError adError) {
                listener.onAdFailed(new FFAdError(adError.getErrorCode(), adError.getErrorMsg()));
            }

            @Override
            public void onADStatusChanged(NativeMediaADData nativeMediaADData) {
            }

            @Override
            public void onADError(NativeMediaADData nativeMediaADData, AdError adError) {
                listener.onAdFailed(new FFAdError(adError.getErrorCode(), adError.getErrorMsg()));
            }

            @Override
            public void onADVideoLoaded(NativeMediaADData nativeMediaADData) {
                listener.onAdSuccess();
                listener.onVideoAdLoaded(toNativeVideoAdData(nativeMediaADData));

            }

            @Override
            public void onADExposure(NativeMediaADData nativeMediaADData) {
                listener.onAdExposure();
            }

            @Override
            public void onADClicked(NativeMediaADData nativeMediaADData) {
                listener.onAdClick();
            }
        });
    }

    private NativeVideoAdData toNativeVideoAdData(NativeMediaADData nativeMediaADData) {
        return null;
    }

    @Override
    public void loadAD() {
        nativeMediaAD.loadAD(1);
    }
}

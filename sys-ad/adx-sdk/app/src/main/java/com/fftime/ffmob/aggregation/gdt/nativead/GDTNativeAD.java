package com.fftime.ffmob.aggregation.gdt.nativead;

import android.app.Activity;
import android.view.View;

import com.fftime.ffmob.aggregation.ads.NativeAD;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.aggregation.model.NativeAdData;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.comm.util.AdError;

import java.util.List;

public class GDTNativeAD implements NativeAD {
    private com.qq.e.ads.nativ.NativeAD nativeAD;

    public GDTNativeAD(Activity activity, String appId, String posId, final NativeAdListener listener) {
        nativeAD = new com.qq.e.ads.nativ.NativeAD(activity, appId, posId, new com.qq.e.ads.nativ.NativeAD.NativeAdListener() {
            @Override
            public void onADLoaded(List<NativeADDataRef> list) {
                listener.onAdSuccess(buildNativeAdData(list));
            }

            @Override
            public void onNoAD(AdError adError) {
                listener.onAdFailed(new FFAdError(adError.getErrorCode(), adError.getErrorMsg()));
            }

            @Override
            public void onADStatusChanged(NativeADDataRef nativeADDataRef) {
            }

            @Override
            public void onADError(NativeADDataRef nativeADDataRef, AdError adError) {
                listener.onAdFailed(new FFAdError(adError.getErrorCode(), adError.getErrorMsg()));
            }
        });
    }

    private NativeAdData buildNativeAdData(List<NativeADDataRef> list) {
        return new GDTNativeAdData(list);
    }

    @Override
    public void loadAD() {
        nativeAD.loadAD(1);
    }

    static class GDTNativeAdData implements NativeAdData {
        private final NativeADDataRef gdtNativeAd;

        public GDTNativeAdData(List<NativeADDataRef> list) {
            this.gdtNativeAd=list.get(0);
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getTitle() {
            return gdtNativeAd.getTitle();
        }

        @Override
        public String getIcon() {
            return gdtNativeAd.getIconUrl();
        }

        @Override
        public String getDesc() {
            return gdtNativeAd.getDesc();
        }

        @Override
        public String getDesc2() {
            return null;
        }

        @Override
        public String getImg() {
            return gdtNativeAd.getImgUrl();
        }

        @Override
        public String getTmid() {
            return null;
        }

        @Override
        public String getSource() {
            return null;
        }

        @Override
        public String getCtatext() {
            return null;
        }

        @Override
        public List<String> getImgList() {
            return gdtNativeAd.getImgList();
        }

        @Override
        public void imp(View v) {
            gdtNativeAd.onExposured(v);
        }

        @Override
        public void click(View v) {
            gdtNativeAd.onClicked(v);
        }
    }
}

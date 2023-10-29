package com.fftime.ffmob.aggregation.fftime.nativead;

import android.app.Activity;
import android.view.View;

import com.fftime.ffmob.aggregation.ads.NativeAD;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.aggregation.model.NativeAdData;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.nativead.NativeADListener;

import java.lang.annotation.Native;
import java.util.List;

public class FFTNativeAD implements NativeAD {
    private com.fftime.ffmob.nativead.NativeAD nativeAD;
    private NativeAdListener listener;
    private final Activity activity;

    public FFTNativeAD(Activity activity, String appId, String posId, NativeAdListener listener){
        nativeAD = new com.fftime.ffmob.nativead.NativeAD(activity,appId,posId);
        this.listener=listener;
        this.activity = activity;
    }

    @Override
    public void loadAD() {
        nativeAD.loadAD(new NativeADListener() {
            @Override
            public void onSucc(NatiAd natiAd) {
                listener.onAdSuccess(buildNativeAdData(natiAd));
            }

            @Override
            public void onFail(int errCode, String msg) {
                listener.onAdFailed(new FFAdError(errCode,msg));
            }
        });
    }

    private NativeAdData buildNativeAdData(NatiAd natiAd) {
       return new FFTNativeAdData(natiAd,this.activity,listener);
    }

    public static class FFTNativeAdData implements NativeAdData{
        private NatiAd natiAd;
        private Activity activity;
        private NativeAdListener listener;

        public FFTNativeAdData(NatiAd natiAd, Activity activity, NativeAdListener listener) {
            this.natiAd=natiAd;
            this.activity=activity;
            this.listener=listener;
        }

        @Override
        public String getName() {
            return natiAd.getName();
        }

        @Override
        public String getTitle() {
            return natiAd.getTitle();
        }

        @Override
        public String getIcon() {
            return natiAd.getIcon();
        }

        @Override
        public String getDesc() {
            return natiAd.getDesc();
        }

        @Override
        public String getDesc2() {
            return null;
        }

        @Override
        public String getImg() {
            return natiAd.getImg();
        }

        @Override
        public String getTmid() {
            return natiAd.getTmid();
        }

        @Override
        public String getSource() {
            return natiAd.getSource();
        }

        @Override
        public String getCtatext() {
            return natiAd.getCtatext();
        }

        @Override
        public List<String> getImgList() {
            return natiAd.getImgs();
        }

        public void clk(View view){
            if(natiAd != null){
                natiAd.clk(activity,view);
            }
        }
        @Override
        public void imp(View v) {
            listener.onAdExposure();
            natiAd.display();
        }

        @Override
        public void click(View v) {
            listener.onAdClick();
            natiAd.click(activity);
        }
    }
}

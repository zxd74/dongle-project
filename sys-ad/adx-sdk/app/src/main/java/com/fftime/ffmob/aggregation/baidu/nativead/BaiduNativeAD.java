package com.fftime.ffmob.aggregation.baidu.nativead;


import android.app.Activity;
import android.view.View;

import com.baidu.mobad.feeds.BaiduNative;
import com.baidu.mobad.feeds.NativeErrorCode;
import com.baidu.mobad.feeds.NativeResponse;
import com.baidu.mobad.feeds.RequestParameters;
import com.fftime.ffmob.aggregation.ads.NativeAD;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.aggregation.model.NativeAdData;

import java.util.List;

public class BaiduNativeAD implements NativeAD {
    private final BaiduNative baiduNative;
    //private NativeAdListener listener;

    public BaiduNativeAD(Activity activity, String appId, String posId, final NativeAdListener listener) {
        BaiduNative.setAppSid(activity, appId);
        baiduNative = new BaiduNative(activity, posId, new BaiduNative.BaiduNativeNetworkListener() {
            @Override
            public void onNativeLoad(List<NativeResponse> list) {
                listener.onAdSuccess(new BaiduNativeAdData(list.get(0),listener));
            }

            @Override
            public void onNativeFail(NativeErrorCode nativeErrorCode) {
                listener.onAdFailed(new FFAdError(-1, "load native failed"));
            }
        });
    }

    @Override
    public void loadAD() {
        RequestParameters reqParameters = new RequestParameters.Builder()
                .downloadAppConfirmPolicy(RequestParameters.DOWNLOAD_APP_CONFIRM_ONLY_MOBILE)
                .build();
        baiduNative.makeRequest(reqParameters);
    }

    public static class BaiduNativeAdData implements NativeAdData {
        private NativeResponse baiduNativeResponse;
        private final NativeAdListener listener;

        private BaiduNativeAdData(NativeResponse nativeResponse,NativeAdListener listener) {
            this.baiduNativeResponse = nativeResponse;
            this.listener=listener;
        }

        @Override
        public String getName() {
            return baiduNativeResponse.getBrandName();
        }

        @Override
        public String getTitle() {
            return baiduNativeResponse.getTitle();
        }

        @Override
        public String getIcon() {
            return baiduNativeResponse.getIconUrl();
        }

        @Override
        public String getDesc() {
            return baiduNativeResponse.getDesc();
        }

        @Override
        public String getDesc2() {
            return null;
        }

        @Override
        public String getImg() {
            return baiduNativeResponse.getImageUrl();
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
            return baiduNativeResponse.getMultiPicUrls();
        }

        @Override
        public void imp(View v) {
            listener.onAdExposure();
            baiduNativeResponse.recordImpression(v);
        }

        @Override
        public void click(View v) {
            listener.onAdClick();
            baiduNativeResponse.handleClick(v);
        }
    }
}

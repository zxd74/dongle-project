package com.fftime.ffmob.nativead;

import android.app.Activity;
import android.content.Context;

import com.fftime.ffmob.basead.BaseADListener;
import com.fftime.ffmob.common.AdSize;
import com.fftime.ffmob.common.adservices.LoadCallback;
import com.fftime.ffmob.common.adservices.LoadRequest;
import com.fftime.ffmob.common.adservices.LoadService;
import com.fftime.ffmob.common.spy.Spy;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.util.StringUtil;
import com.fftime.ffmob.video.VideoWindowsAdView;

import org.json.JSONArray;
import org.json.JSONObject;

public class NativeAD {

    //private final NatiAd natiAd;
    private final String appID;
    private final String posID;
    private final Activity activity;
    private VideoWindowsAdView videoAdView;

    public NativeAD(Activity activity, String appID, String posID) {
        //this.core = new BaseAD(context, appID, posID);
        this.activity = activity;
        this.appID = appID;
        this.posID = posID;
    }

    public void loadAD(final NativeADListener listener) {
        StatusManager.getInstance().init(activity);
        final LoadRequest request = new LoadRequest(appID, posID, AdSize.FEEDS_1000.getWidth(), AdSize.FEEDS_1000.getHeight(),1);
        loadNativeAd(listener, request);
    }

    private void loadNativeAd(final NativeADListener listener, LoadRequest lreq) {
        LoadService.getInstance().loadAD(lreq, new LoadCallback() {
            @Override
            public void onSucc(JSONObject data) {
                if (data.optInt("ret") != 0) {
                    listener.onFail(BaseADListener.ERROR_RESPONSE, data.optString("msg"));
                } else {
                    JSONObject adData, posData;
                    if ((adData = data.optJSONObject("data")) == null || (posData = adData.optJSONObject(posID)) == null) {
                        listener.onFail(BaseADListener.ERROR_RESPONSE, "广告返回为空");
                    } else {
                        if (posData.optInt("ret") != 0) {
                            listener.onFail(posData.optInt("ret"), posData.optString("msg"));
                        } else {
                            JSONArray adArray = posData.optJSONArray("list");
                            JSONObject ad = adArray.optJSONObject(0);
                            NatiAd natiAd = new NatiAd(ad);
                            if(!StringUtil.isEmpty(natiAd.getVideo())){
                                videoAdView = new VideoWindowsAdView(activity, natiAd, false, true,posID);
                                natiAd.setVideoAdView(videoAdView);
                            }
                            listener.onSucc(natiAd);
                        }
                    }
                }
            }

            @Override
            public void onError() {
                listener.onFail(BaseADListener.ERROR_NETWORK, "网络或服务器异常，请检查设备网络");
            }
        });
    }

    public void destroy(){
        if(videoAdView != null){
            videoAdView.destroy();
        }
    }
//    public void showAD() {
//        videoAdView.show();
//    }
}

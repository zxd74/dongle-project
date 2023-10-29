package com.fftime.ffmob.rewardvideo;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.fftime.ffmob.common.AdSize;
import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.adservices.LoadCallback;
import com.fftime.ffmob.common.adservices.LoadRequest;
import com.fftime.ffmob.common.adservices.LoadService;
import com.fftime.ffmob.common.spy.Spy;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.video.VideoADListener;
import com.fftime.ffmob.video.VideoPlayListener;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by duan .
 */

public class RewardVideoAD {
    public static final int VIDEOREQUEST = 333;
    private final Activity activity;
    private String appID;
    private String posID;
    private String data;

    public RewardVideoAD(Activity activity, String appID, String posID) {
        super();
        StatusManager.getInstance().init(activity);
        //Spy.work(activity);
        this.appID = appID;
        this.posID = posID;
        this.activity = activity;
    }

    public void loadAD(int count, final VideoADListener listener) {
        LoadRequest lreq = new LoadRequest(appID, posID, AdSize.FEEDS_1000.getWidth(), AdSize.FEEDS_1000.getHeight(),
                count);
        LoadService.getInstance().loadAD(lreq, new LoadCallback() {
            @Override
            public void onSucc(JSONObject data) {
                String videoAdData = data.toString();
                int resultCode = data.optInt("ret");
                if (resultCode != 0) {
                    listener.onLoadFail();
                } else {
                    RewardVideoAD.this.data = videoAdData;
                    listener.onLoadFinish();
                }
            }

            @Override
            public void onError() {
                listener.onLoadFail();
            }
        });
    }

    public void fetchAndShow(VideoPlayListener listener) {
        if (!TextUtils.isEmpty(data)) {
            FFRewardVideoActivity.playListener = new WeakReference<VideoPlayListener>(listener);
            Intent intent = new Intent();
            intent.putExtra("data", data);
            intent.putExtra("posID", posID);
            intent.setClass(activity, FFRewardVideoActivity.class);
            activity.startActivity(intent);
        } else {
            FFTLoger.d("error", "data为空");
        }
    }

}

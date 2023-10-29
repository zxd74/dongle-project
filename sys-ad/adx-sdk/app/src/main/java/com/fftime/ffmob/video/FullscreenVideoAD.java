package com.fftime.ffmob.video;

import android.app.Activity;
import android.content.Intent;

public class FullscreenVideoAD implements VideoADListener {
    private VideoAD videoAD;
    private Activity activity;
    private VideoADListener listener;
    private String appId;
    private String posId;
    private boolean isPreload;

    private static Activity videoActivity;

    public static void setVideoActivity(Activity activity) {
        videoActivity = activity;
    }


    public FullscreenVideoAD(Activity activity, String appId, String posId, VideoADListener listener, boolean isPreload) {
        this.activity = activity;
        this.listener = listener;
        this.appId = appId;
        this.posId = posId;
        this.isPreload = isPreload;
    }

    @Override
    public void onLoadFinish() {
        this.listener.onLoadFinish();
    }

    @Override
    public void onLoadFail() {
        this.listener.onLoadFail();
    }

    @Override
    public void onVideoLoaded() {
        listener.onVideoLoaded();
    }

    @Override
    public void onStart() {
        listener.onStart();
    }

    @Override
    public void onCompletion() {
        //if (videoActivity != null) videoActivity.finish();
        listener.onCompletion();
    }

    @Override
    public void onClose() {
        if (videoActivity != null) videoActivity.finish();
        listener.onClose();
    }

    public void loadAD() {
        videoAD = new VideoAD(this.activity, null, appId, posId, this, isPreload);
        videoAD.loadAD();
    }

    public void showAD() {
        FullscreenVideoADActivity.videoAD = videoAD;
        Intent intent = new Intent();
        intent.setClass(activity, FullscreenVideoADActivity.class);

        activity.startActivity(intent);
    }
}

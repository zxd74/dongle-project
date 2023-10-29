package com.fftime.ffmob.video;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.nativead.NativeAD;
import com.fftime.ffmob.nativead.NativeADListener;
import com.fftime.ffmob.util.SizeUtil;

public class VideoAD {
    private static final String TAG = VideoAD.class.getSimpleName();

    private NativeAD nativeAD;
    private Activity activity;
    private VideoADListener listener;

    private ViewGroup adContainer;
    private VideoADView videoADView;
    private boolean isPreload;
    private boolean isFullscreen;

    public ViewGroup getAdContainer() {
        return adContainer;
    }

    public VideoAD(Activity activity, ViewGroup adContainer, String appId, String posId, VideoADListener listener) {
        this(activity, adContainer, appId, posId, listener, true, false);
    }

    public VideoAD(Activity activity, ViewGroup adContainer, String appId, String posId, VideoADListener listener, boolean isPreload) {
        this(activity, adContainer, appId, posId, listener, true, isPreload);
    }

    public VideoAD(Activity activity, ViewGroup adContainer, String appId, String posId, VideoADListener listener, boolean isFullscreen, boolean isPreload) {
        StatusManager.getInstance().init(activity);
        if (adContainer == null) this.adContainer = new RelativeLayout(activity);
        this.activity = activity;
        this.listener = listener;
        this.adContainer = adContainer;
        this.nativeAD = new NativeAD(activity, appId, posId);
        this.isPreload = isPreload;
        this.isFullscreen = isFullscreen;
        if (adContainer == null) {
            this.adContainer = new RelativeLayout(activity);
        } else {
            this.adContainer = adContainer;
        }

        Log.i(TAG, "dpi type: " + SizeUtil.dpiType(activity));
    }

    public void loadAD() {
        nativeAD.loadAD(new NativeADListener() {
            @Override
            public void onSucc(NatiAd natiAd) {
                videoADView = new VideoADView(activity, adContainer, natiAd, listener, isFullscreen, isPreload);
                listener.onLoadFinish();
            }

            @Override
            public void onFail(int errCode, String msg) {
                Log.i(TAG, "load Ad failed");
                listener.onLoadFail();
            }
        });
    }

    public void showAD() {
        videoADView.show();
    }

    public void destroy() {
        videoADView.destroy();
    }

    public void pause() {
        videoADView.getVideoView().pause();
    }
}

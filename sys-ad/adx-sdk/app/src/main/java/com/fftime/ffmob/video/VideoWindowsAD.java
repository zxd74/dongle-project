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

public class VideoWindowsAD {
    private static final String TAG = VideoWindowsAD.class.getSimpleName();

    private NativeAD nativeAD;
    private Activity activity;
    private VideoWindowsAdListener listener;
    private VideoWindowsAdView videoADView;
    private boolean isPreload;
    private boolean isFullscreen;
    public VideoWindowsAD(Activity activity,String appId, String posId, VideoWindowsAdListener listener) {
        this(activity,appId, posId, listener, true, false);
    }

    public VideoWindowsAD(Activity activity, String appId, String posId, VideoWindowsAdListener listener, boolean isPreload) {
        this(activity,appId, posId, listener, true, isPreload);
    }

    public VideoWindowsAD(Activity activity,String appId, String posId, VideoWindowsAdListener listener, boolean isFullscreen, boolean isPreload) {
        StatusManager.getInstance().init(activity);
        this.activity = activity;
        this.listener = listener;
        this.nativeAD = new NativeAD(activity, appId, posId);
        this.isPreload = isPreload;
        this.isFullscreen = isFullscreen;
        Log.i(TAG, "dpi type: " + SizeUtil.dpiType(activity));
    }

    public void loadAD() {
        nativeAD.loadAD(new NativeADListener() {
            @Override
            public void onSucc(NatiAd natiAd) {
                videoADView = new VideoWindowsAdView(activity, natiAd,isFullscreen, isPreload,"");
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
        if(videoADView != null){
            videoADView.destroy();
        }
    }

}

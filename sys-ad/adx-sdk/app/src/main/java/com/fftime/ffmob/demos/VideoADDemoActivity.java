package com.fftime.ffmob.demos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fftime.ffmob.R;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.nativead.NativeAD;
import com.fftime.ffmob.nativead.NativeADListener;
import com.fftime.ffmob.util.PermissionUtils;
import com.fftime.ffmob.util.StringUtil;
import com.fftime.ffmob.video.VideoAD;
import com.fftime.ffmob.video.VideoADListener;
import com.fftime.ffmob.video.VideoWindowsAD;
import com.fftime.ffmob.video.VideoWindowsAdListener;
import com.fftime.ffmob.video.VideoWindowsAdView;

public class VideoADDemoActivity extends Activity {
    private Button button;
    private NativeAD nativeAD;
    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_demo_videoad);
        PermissionUtils.getPersimmions(this);
        button = findViewById(R.id.btn_play);
        ViewGroup adContainer = findViewById(R.id.videoad_container);
        findViewById(R.id.btn_clear).setOnClickListener(v -> nativeAD.destroy());
        button.setOnClickListener(v -> adContainer.setVisibility(View.VISIBLE));
        final String appid = "EzqIze";
        final String posid = "JVZzei";

        loadVideoAd(adContainer, appid, posid);
    }

    private void loadVideoAd(ViewGroup adContainer, String appid, String posid) {
        nativeAD = new NativeAD(VideoADDemoActivity.this,appid,posid);
        nativeAD.loadAD(new NativeADListener() {
            @Override
            public void onSucc(NatiAd natiAd) {
                if(natiAd != null && !StringUtil.isEmpty(natiAd.getVideo())){
                    natiAd.clk(VideoADDemoActivity.this,adContainer);
                    VideoWindowsAdView windowsAdView = natiAd.getVideoAdView();
                    if(windowsAdView != null){
                        windowsAdView.setListener(new VideoWindowsAdListener() {
                            @Override
                            public void onLoadFinish() {
                                adContainer.removeAllViews();
                                adContainer.addView(windowsAdView.getVadContainer());
                                Log.e("guoxl","onLoadFinish");
                                adContainer.setOnClickListener(v -> natiAd.click(VideoADDemoActivity.this));
                            }

                            @Override
                            public void onLoadFail() {
                                Log.e("guoxl","onLoadFail");
                            }

                            @Override
                            public void onVideoLoaded() {
                                Log.e("guoxl","onVideoLoaded");
                            }

                            @Override
                            public void onStart() {
                                Log.e("guoxl","onStart");
                            }

                            @Override
                            public void onCompletion() {
                                Log.e("guoxl","onCompletion");
                            }
                        });
                    }

                }
            }

            @Override
            public void onFail(int errCode, String msg) {

            }
        });
    }
}

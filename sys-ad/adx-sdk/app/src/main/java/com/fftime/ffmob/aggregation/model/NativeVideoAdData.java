package com.fftime.ffmob.aggregation.model;

import android.content.res.Configuration;
import android.view.View;

import com.qq.e.ads.nativ.MediaListener;
import com.qq.e.ads.nativ.MediaView;
import com.qq.e.ads.nativ.NativeMediaADData;

import java.util.List;

public interface NativeVideoAdData {
    String getTitle();

    String getDesc();

    String getIconUrl();

    String getImgUrl();

    int getAdPatternType();

    List<String> getImgList();

    boolean isAPP();

    void onExposured(View var1);

    void onClicked(View var1);

    int getAPPStatus();

    int getProgress();

    long getDownloadCount();

    int getAPPScore();

    double getAPPPrice();

    boolean equalsAdData(NativeMediaADData var1);

    /** @deprecated */
    @Deprecated
    boolean isVideoAD();

    void bindView(MediaView var1, boolean var2);

    void destroy();

    void setMediaListener(MediaListener var1);

    void preLoadVideo();

    boolean isVideoLoaded();

    void play();

    void onScroll(int var1, View var2);

    void stop();

    boolean isPlaying();

    int getDuration();

    int getCurrentPosition();

    void onConfigurationChanged(Configuration var1);

    void resume();

    void setVolumeOn(boolean var1);
}

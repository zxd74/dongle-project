package com.fftime.ffmob.video;

import com.fftime.ffmob.model.NatiAd;

public interface VideoWindowsAdListener {
    void onLoadFinish();
    void onLoadFail();
    //视频预加载完成
    void onVideoLoaded();
    //视频开始播放
    void onStart();
    //视频播放结束
    void onCompletion();
}

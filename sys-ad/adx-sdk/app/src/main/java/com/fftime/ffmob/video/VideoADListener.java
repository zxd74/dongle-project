package com.fftime.ffmob.video;

import java.io.Serializable;

/**
 * Created by duan .
 */

public interface VideoADListener extends Serializable {
    void onLoadFinish();

    void onLoadFail();
    //视频预加载完成
    void onVideoLoaded();
    //视频开始播放
    void onStart();
    //视频播放结束
    void onCompletion();
    //广告关闭
    void onClose();
}

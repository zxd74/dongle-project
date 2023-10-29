package com.fftime.ffmob.aggregation.model;

import android.view.View;

import java.util.List;

public interface NativeAdData {
    String getName();
    String getTitle();
    String getIcon();
    String getDesc();
    String getDesc2();
    String getImg();
    String getTmid();
    String getSource();

    String getCtatext();
    List<String> getImgList();

    //原生广告曝光
    void imp(View v);
    //原生广告点击
    void click(View v);
}

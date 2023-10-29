package com.fftime.ffmob.aggregation.base.listener;

import com.fftime.ffmob.aggregation.model.NativeAdData;

public interface NativeAdListener extends AdListener {
    void onAdSuccess(NativeAdData nativeAdData);
}

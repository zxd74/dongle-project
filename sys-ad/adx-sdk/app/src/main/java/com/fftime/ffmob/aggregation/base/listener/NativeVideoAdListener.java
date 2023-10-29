package com.fftime.ffmob.aggregation.base.listener;

import com.fftime.ffmob.aggregation.model.NativeVideoAdData;

public interface NativeVideoAdListener extends AdListener {
    void onVideoAdLoaded(NativeVideoAdData nativeVideoAdData);
}

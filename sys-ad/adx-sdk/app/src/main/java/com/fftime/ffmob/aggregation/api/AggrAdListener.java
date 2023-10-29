package com.fftime.ffmob.aggregation.api;

import com.fftime.ffmob.aggregation.base.listener.BannerAdListener;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeExpressAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeVideoAdListener;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;
import com.fftime.ffmob.aggregation.base.listener.VideoAdListener;

public interface AggrAdListener extends BannerAdListener, InterstitialAdListener, NativeAdListener, VideoAdListener, SplashAdListener, NativeVideoAdListener, NativeExpressAdListener {
}

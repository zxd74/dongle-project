package com.fftime.ffmob.aggregation.base.listener;

import com.fftime.ffmob.aggregation.model.FFAdError;

/**
 * Created by tj on 2019/4/17.
 */
public interface AdListener {
    void onAdFailed(FFAdError error);
    void onAdSuccess();
    void onAdClick();
    void onAdExposure();
}

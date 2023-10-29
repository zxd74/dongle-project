package com.fftime.ffmob.aggregation.toutiao;

import android.content.Context;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.fftime.ffmob.common.status.APPStatus;
import com.fftime.ffmob.common.status.StatusManager;

public class TTAdSdkInitializer {
    private static volatile boolean ttAdSdkInited;

    public static synchronized void initTTAdSdk(Context context) {
        if (!ttAdSdkInited) {
            StatusManager.getInstance().init(context);
            APPStatus appStatus = StatusManager.getInstance().getAppStatus();
            TTAdConfig ttAdConfig = new TTAdConfig.Builder().appId(appStatus.getAppId()).appName(appStatus.getAppName()).build();
            TTAdSdk.init(context.getApplicationContext(), ttAdConfig);

            ttAdSdkInited = true;
        }
    }
}

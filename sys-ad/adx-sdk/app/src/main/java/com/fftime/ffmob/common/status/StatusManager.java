package com.fftime.ffmob.common.status;

import android.app.Activity;
import android.content.Context;

public class StatusManager {
    private static class ActivityHolder {
        private static Activity CURRENT = null;
    }

    private static class Holder {
        private static final StatusManager INSTANCE = new StatusManager();
    }

    public static StatusManager getInstance() {
        return Holder.INSTANCE;
    }

    private DeviceStatus deviceStatus;
    private APPStatus appStatus;
    private boolean inited;

    private StatusManager() {

    }

    public static Activity currentActivity(){
        return ActivityHolder.CURRENT;
    }

    public void init(Context context) {
        if (context instanceof Activity) {
            ActivityHolder.CURRENT = (Activity) context;
        }

        if (!inited) {
            this.deviceStatus = new DeviceStatus(context);
            this.appStatus = new APPStatus(context);
        }
        inited = true;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }

    public APPStatus getAppStatus() {
        return appStatus;
    }

}

package com.fftime.ffmob.video;

import java.io.Serializable;

class VideoADOption implements Serializable {
    private String appId;
    private String posId;
    private boolean isPreload;

    public VideoADOption(){}

    public VideoADOption(String appId, String posId, boolean isPreload) {
        this.appId = appId;
        this.posId = posId;
        this.isPreload = isPreload;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public boolean isPreload() {
        return isPreload;
    }

    public void setPreload(boolean preload) {
        isPreload = preload;
    }
}

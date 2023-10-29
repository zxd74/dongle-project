package com.fftime.ffmob.aggregation.model;

import com.fftime.ffmob.aggregation.ads.ADType;

public class AdPlatform {
    private String id;
    private String appId;
    private String posId;
    //广告位类型
    private ADType adType;

    public AdPlatform() {
    }

    public ADType getAdType() {
        return adType;
    }

    public void setAdType(ADType adType) {
        this.adType = adType;
    }

    public AdPlatform(String id, String appId, String posId) {
        this.id = id;
        this.appId = appId;
        this.posId = posId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

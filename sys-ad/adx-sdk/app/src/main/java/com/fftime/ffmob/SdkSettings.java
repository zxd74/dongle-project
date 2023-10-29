package com.fftime.ffmob;

import com.fftime.ffmob.util.Constants;
import com.fftime.ffmob.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SdkSettings {
    private static final SdkSettings instance = new SdkSettings();

    private SdkSettings() {
    }

    public static SdkSettings getInstance() {
        return instance;
    }

    private String apiDomain = Constants.DEFAULT_API_DOMAIN;
    private String channel = Constants.DEFAULT_CHANNEL;
    private Map<String, List<String>> adslotSettings = new HashMap<>();
    private String appId;
    private String oaid;

    public String getOaid() {
        return oaid;
    }

    public SdkSettings oaid(String oaid) {
        this.oaid = oaid;
        return this;
    }

    public String getApiDomain() {
        return apiDomain;
    }

    public String getChannel() {
        return channel;
    }

    public String getAppId() {
        return appId;
    }

    public SdkSettings apiDomain(String apiDomain) {
        this.apiDomain = apiDomain;
        return this;
    }

    public SdkSettings appId(String appId){
        this.appId = appId;
        return this;
    }

    public Map<String, List<String>> getAdslotSettings() {
        return adslotSettings;
    }

    public String getSupportTmidsAsString(String posId) {
        if (adslotSettings == null || adslotSettings.isEmpty()) return StringUtil.EMPTY;
        if (!adslotSettings.containsKey(posId)) return StringUtil.EMPTY;

        List<String> adslotSetting = adslotSettings.get(posId);
        if (adslotSetting == null || adslotSetting.isEmpty()) return StringUtil.EMPTY;

        StringBuffer tmids = new StringBuffer();
        for (String tmid : adslotSetting) {
            tmids.append(",").append(tmid);
        }
        if (tmids.length() > 0)
            return tmids.substring(1);
        return StringUtil.EMPTY;
    }

    public SdkSettings channel(String channel) {
        this.channel = channel;
        return this;
    }

    public SdkSettings adslotSettings(Map<String, List<String>> adslotSettings) {
        this.adslotSettings = adslotSettings;
        return this;
    }

    public static void main(String[] args) throws Exception {
        //SdkSettings.getInstance().supportTmids(Arrays.asList("1001", "10002"));
    }
}

package com.fftime.ffmob.common.adservices;

import java.util.HashMap;
import java.util.Map;

public class LoadRequest {
    private final String appId;
    private final String posId;
    private final int posW;
    private final int posH;
    private final int count;
    private Map<String, String> parameters = new HashMap<>();

    public LoadRequest(String appId, String posId, int posW, int posH, int count) {
        this.appId = appId;
        this.posId = posId;
        this.posW = posW;
        this.posH = posH;
        this.count = count;
    }

    public String getPosId() {
        return posId;
    }

    public int getPosW() {
        return posW;
    }

    public int getPosH() {
        return posH;
    }

    public int getCount() {
        return count;
    }

    public String getAppId() {
        return appId;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void addParameter(String name, String value) {
        parameters.put(name, value);
    }
}

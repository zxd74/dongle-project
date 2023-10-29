package com.fftime.ffmob.model;

import org.json.JSONObject;

public class GdtDlInfo {
    private String dstlink;
    private String clickid;

    public GdtDlInfo(){}

    public GdtDlInfo(JSONObject dlInfoJSON){
        this.dstlink=dlInfoJSON.optString("dstlink");
        this.clickid=dlInfoJSON.optString("clickid");
    }

    public String getDstlink() {
        return dstlink;
    }

    public void setDstlink(String dstlink) {
        this.dstlink = dstlink;
    }

    public String getClickid() {
        return clickid;
    }

    public void setClickid(String clickid) {
        this.clickid = clickid;
    }

    @Override
    public String toString() {
        return "GdtDlInfo{" +
                "dstlink='" + dstlink + '\'' +
                ", clickid='" + clickid + '\'' +
                '}';
    }
}

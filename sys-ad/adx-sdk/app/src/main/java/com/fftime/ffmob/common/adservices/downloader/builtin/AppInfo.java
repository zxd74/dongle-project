package com.fftime.ffmob.common.adservices.downloader.builtin;

import com.fftime.ffmob.util.StringUtil;

import org.json.JSONArray;

public class AppInfo {
    private String appName;
    private String pkgName;
    private String downloadUrl;
    private String mimetype;
    private JSONArray dlcmArray;
    private JSONArray dlfmArray;

    public JSONArray getDlfmArray() {
        return dlfmArray;
    }

    public void setDlfmArray(JSONArray dlfmArray) {
        this.dlfmArray = dlfmArray;
    }

    public JSONArray getDlcmArray() {
        return dlcmArray;
    }

    public void setDlcmArray(JSONArray dlcmArray) {
        this.dlcmArray = dlcmArray;
    }

    public JSONArray getInstalledArray() {
        return installedArray;
    }

    public void setInstalledArray(JSONArray installedArray) {
        this.installedArray = installedArray;
    }

    private JSONArray installedArray;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    private AppInfo(String appName, String pkgName, String downloadUrl) {
        this.appName = appName;
        this.pkgName = pkgName;
        this.downloadUrl = downloadUrl;
    }
    private AppInfo(String appName, String pkgName, String downloadUrl,JSONArray dlcmArray,JSONArray dlfmArray,JSONArray installedArray) {
        this.appName = appName;
        this.pkgName = pkgName;
        this.downloadUrl = downloadUrl;
        this.dlcmArray = dlcmArray;
        this.dlfmArray = dlfmArray;
        this.installedArray = installedArray;
    }

    private AppInfo(String appName, String pkgName, String downloadUrl,String mimetype,JSONArray dlcmArray,JSONArray dlfmArray,JSONArray installedArray) {
        this.appName = appName;
        this.pkgName = pkgName;
        this.downloadUrl = downloadUrl;
        this.mimetype=mimetype;
        this.dlfmArray = dlfmArray;
        this.dlcmArray = dlcmArray;
        this.installedArray = installedArray;
    }

    public static AppInfo create(String appName,String pkgName,String downloadUrl){
      return new AppInfo(appName,pkgName,downloadUrl);
    }
    public static AppInfo create(String appName,String pkgName,String downloadUrl,JSONArray dlcmArray,JSONArray dlfmArray,JSONArray installedArray){
        return new AppInfo(appName,pkgName,downloadUrl,dlcmArray,dlfmArray,installedArray);
    }

    public static AppInfo create(String appName,String pkgName,String downloadUrl,String mimetype,JSONArray dlcmArray,JSONArray dlfmArray,JSONArray installedArray){
        return new AppInfo(appName,pkgName,downloadUrl,mimetype,dlcmArray,dlfmArray,installedArray);
    }

    public String getAppName() {
        return StringUtil.isEmpty(appName) ? pkgName : appName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}

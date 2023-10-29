package com.fftime.ffmob.model;

import java.io.Serializable;
import java.util.List;

public class BaseAd implements Serializable {
    protected String ldp;
    protected List<String> exp;
    protected List<String> clk;
    protected String img;
    protected String downloadUrl;
    protected String fallback;
    protected String source; //增加广告来源说明
    protected String tmid; //广告模版id
    protected int skipAdTime; //几秒跳过视频广告
    protected boolean mute;
    protected String video;
    protected List<String> dlm; //下载开始监测
    protected List<String> dlcm; //下载完成监测
    protected List<String> dpsm; //deeplink成功打开监测
    protected List<String> vasm; //视频自动开始播放
    protected List<String> vcm; //视频播放完成

    public List<String> getVasm() {
        return vasm;
    }

    public void setVasm(List<String> vasm) {
        this.vasm = vasm;
    }

    public List<String> getVcm() {
        return vcm;
    }

    public void setVcm(List<String> vcm) {
        this.vcm = vcm;
    }

    public List<String> getDlcm() {
        return dlcm;
    }

    public void setDlcm(List<String> dlcm) {
        this.dlcm = dlcm;
    }

    public boolean isGdtAd() {
        return isGdtAd;
    }

    public void setGdtAd(boolean gdtAd) {
        isGdtAd = gdtAd;
    }

    protected List<String> dpfm; //deeplink打开失败监测

    protected boolean isGdtAd; //是否广点通广告

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    public String getLdp() {
        return ldp;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImg() {
        return img;
    }

    public int getSkipAdTime() {
        return skipAdTime;
    }

    public void setSkipAdTime(int skipAdTime) {
        this.skipAdTime = skipAdTime;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTmid() {
        return tmid;
    }

    public void setTmid(String tmid) {
        this.tmid = tmid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setLdp(String ldp) {
        this.ldp = ldp;
    }

    public List<String> getDlm() {
        return dlm;
    }

    public void setDlm(List<String> dlm) {
        this.dlm = dlm;
    }

    public List<String> getDpsm() {
        return dpsm;
    }

    public void setDpsm(List<String> dpsm) {
        this.dpsm = dpsm;
    }

    public List<String> getDpfm() {
        return dpfm;
    }

    public void setDpfm(List<String> dpfm) {
        this.dpfm = dpfm;
    }

    public List<String> getExp() {
        return exp;
    }

    public void setExp(List<String> exp) {
        this.exp = exp;
    }

    public List<String> getClk() {
        return clk;
    }

    public void setClk(List<String> clk) {
        this.clk = clk;
    }
}

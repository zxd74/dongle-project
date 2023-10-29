package com.iwanvi.adserv.ngx.router.broker.meishu;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: 郑晓东
 * @date: 2019-07-09 16:41
 * @version: v1.0
 * @Description: 美数广告响应类 N 非必填，Y必填
 */
public class BidResponse {

    /**
     * 应答对应的请求标识
     * N
     */
    @JSONField(name = "req_id")
    private String reqId;
    /**
     * ssp 平台广告位 ID
     * N
     */
    private String pid;
    /**
     * ssp 平台创意 ID（保留字段）
     * N
     */
    private String cid;
    /**
     * ssp 平台广告主 ID（保留字段）
     */
    @JSONField(name = "ader_id")
    private String aderId;
    /**
     * 广告位高
     * Y
     */
    private Integer height;
    /**
     * 广告位宽
     * Y
     */
    private Integer width;
    /**
     * 创意类型
     * N
     */
    @JSONField(name = "creative_type")
    private Integer creativeType;
    /**
     * 广告交互类型
     * N
     */
    @JSONField(name = "target_type")
    private Integer targetType;
    /**
     * 落地页地址
     * N
     */
    @JSONField(name = "dUrl")
    private String[] loadUrl;
    /**
     * dp地址
     */
    @JSONField(name = "deep_link")
    private String deepLink;
    /**
     * 物料地址
     */
    private String[] srcUrls;
    /**
     * 音视频时长（单位/秒）
     */
    @JSONField(name = "video_duration")
    private Integer videoDuration;
    /**
     * 音视频封面图地址
     */
    @JSONField(name="video_cover")
    private String videoCover;
    /**
     * 标题
     */
    private String title;
    /**
     * 摘要，描述
     */
    private String content;
    /**
     * icon,logo
     */
    private String icon;
    /**
     * 角标标题
     */
    @JSONField(name = "icon_title")
    private String iconTitle;
    /**
     * 广告来源logo
     */
    @JSONField(name = "from_logo")
    private String fromLogo;
    /**
     * 广告来源(GDT-广点通)
     */
    private String from;
    /**
     * 应用名称
     */
    @JSONField(name = "app_name")
    private String appName;
    /**
     * 包名
     */
    @JSONField(name = "package_name")
    private String packageName;
    /**
     * 广告曝光时必须触发上报
     * Y
     */
    private String[] monitorUrl;
    /**
     * 广告被点击时必须触发上报
     * Y
     */
    private String[] clickUrl;
    /**
     * 下载类广告（下载开始时上报，广点通广告
     * 需替换相应宏
     */
    @JSONField(name = "dn_start")
    private String[] dnStart;
    /**
     * 下载类广告（下载成功时上报，广点通广告
     * 需替换相应宏
     */
    @JSONField(name = "dn_succ")
    private String[] dnSucc;
    /**
     * 下载类广告（安装开始时上报）
     */
    @JSONField(name = "dn_inst_start")
    private String[] dnInstStart;
    /**
     * 下载类广告（安装成功时上报，广点通广告
     * 需替换相应宏
     */
    @JSONField(name = "dn_inst_succ")
    private String[] dnInstSucc;
    /**
     * 下载类广告（安装完成并打开应用时上报）
     */
    @JSONField(name = "dn_active")
    private String[] dnActive;
    /**
     * deep_link 字段非空时，直接唤起类（尝试
     * 唤起时上报）
     */
    @JSONField(name = "dp_start")
    private String[] dpStart;
    /**
     * deep_link 字段非空时，直接唤起类（唤起
     * 成功时上报）
     */
    @JSONField(name = "dp_succ")
    private String[] dpSucc;
    /**
     * deep_link 字段非空时，直接唤起类（唤起
     * 失败时上报）
     */
    @JSONField(name = "dp_fail")
    private String[] dpFail;
    /**
     * 音/视频广告（播放开始时上报）
     */
    @JSONField(name = "video_start")
    private String[] videoStart;
    /**
     * 音/视频广告（播放了 25%时上报）
     */
    @JSONField(name = "video_one_quarter")
    private String[] videoOneQuarter;
    /**
     * 音/视频广告（播放了 50%时上报）
     */
    @JSONField(name = "video_one_half ")
    private String[] videoOneHalf;
    /**
     * 音/视频广告（播放了 75%时上报）
     */
    @JSONField(name = "video_three_quarter")
    private String[] videoThreeQuarter;
    /**
     * 音/视频广告（播放完成时上报）
     */
    @JSONField(name = "video_complete")
    private String[] videoComplete;
    /**
     * 音/视频广告（用户点击暂停按钮时上报）
     */
    @JSONField(name = "video_pause")
    private String[] videoPause;
    /**
     * 音/视频广告（被暂停或被停止之后，主动
     * 继续播放时上报）
     */
    @JSONField(name = "video_resume")
    private String[] videoResume;
    /**
     * 音/视频广告（用户点击跳过按钮时上报）
     */
    @JSONField(name = "video_skip")
    private String[] videoSkip;
    /**
     * 音/视频广告（用户主动关闭声音时上报）
     */
    @JSONField(name = "video_mute")
    private String[] videoMute;
    /**
     * 音/视频广告（用户主动开启声音时上报）
     */
    @JSONField(name = "video_unmute")
    private String[] videoUnmute;
    /**
     * 音/视频广告（重播时上报）
     */
    @JSONField(name = "video_replay")
    private String[] videoReplay;
    /**
     * 音/视频广告（关闭时上报）
     */
    @JSONField(name = "video_close")
    private String[] videoClose;
    /**
     * 视频广告（全屏时上报）
     */
    @JSONField(name = "video_full")
    private String[] videoFull;
    /**
     * 视频广告（退出全屏时上报）
     */
    @JSONField(name = "video_exit_full")
    private String[] videoExitFull;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAderId() {
        return aderId;
    }

    public void setAderId(String aderId) {
        this.aderId = aderId;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getCreativeType() {
        return creativeType;
    }

    public void setCreativeType(Integer creativeType) {
        this.creativeType = creativeType;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public String[] getLoadUrl() {
        return loadUrl;
    }

    public void setLoadUrl(String[] loadUrl) {
        this.loadUrl = loadUrl;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public String[] getSrcUrls() {
        return srcUrls;
    }

    public void setSrcUrls(String[] srcUrls) {
        this.srcUrls = srcUrls;
    }

    public Integer getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Integer videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoCover() {
        return videoCover;
    }

    public void setVideoCover(String videoCover) {
        this.videoCover = videoCover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconTitle() {
        return iconTitle;
    }

    public void setIconTitle(String iconTitle) {
        this.iconTitle = iconTitle;
    }

    public String getFromLogo() {
        return fromLogo;
    }

    public void setFromLogo(String fromLogo) {
        this.fromLogo = fromLogo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String[] getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String[] monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public String[] getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String[] clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String[] getDnStart() {
        return dnStart;
    }

    public void setDnStart(String[] dnStart) {
        this.dnStart = dnStart;
    }

    public String[] getDnSucc() {
        return dnSucc;
    }

    public void setDnSucc(String[] dnSucc) {
        this.dnSucc = dnSucc;
    }

    public String[] getDnInstStart() {
        return dnInstStart;
    }

    public void setDnInstStart(String[] dnInstStart) {
        this.dnInstStart = dnInstStart;
    }

    public String[] getDnInstSucc() {
        return dnInstSucc;
    }

    public void setDnInstSucc(String[] dnInstSucc) {
        this.dnInstSucc = dnInstSucc;
    }

    public String[] getDnActive() {
        return dnActive;
    }

    public void setDnActive(String[] dnActive) {
        this.dnActive = dnActive;
    }

    public String[] getDpStart() {
        return dpStart;
    }

    public void setDpStart(String[] dpStart) {
        this.dpStart = dpStart;
    }

    public String[] getDpSucc() {
        return dpSucc;
    }

    public void setDpSucc(String[] dpSucc) {
        this.dpSucc = dpSucc;
    }

    public String[] getDpFail() {
        return dpFail;
    }

    public void setDpFail(String[] dpFail) {
        this.dpFail = dpFail;
    }

    public String[] getVideoStart() {
        return videoStart;
    }

    public void setVideoStart(String[] videoStart) {
        this.videoStart = videoStart;
    }

    public String[] getVideoOneQuarter() {
        return videoOneQuarter;
    }

    public void setVideoOneQuarter(String[] videoOneQuarter) {
        this.videoOneQuarter = videoOneQuarter;
    }

    public String[] getVideoOneHalf() {
        return videoOneHalf;
    }

    public void setVideoOneHalf(String[] videoOneHalf) {
        this.videoOneHalf = videoOneHalf;
    }

    public String[] getVideoThreeQuarter() {
        return videoThreeQuarter;
    }

    public void setVideoThreeQuarter(String[] videoThreeQuarter) {
        this.videoThreeQuarter = videoThreeQuarter;
    }

    public String[] getVideoComplete() {
        return videoComplete;
    }

    public void setVideoComplete(String[] videoComplete) {
        this.videoComplete = videoComplete;
    }

    public String[] getVideoPause() {
        return videoPause;
    }

    public void setVideoPause(String[] videoPause) {
        this.videoPause = videoPause;
    }

    public String[] getVideoResume() {
        return videoResume;
    }

    public void setVideoResume(String[] videoResume) {
        this.videoResume = videoResume;
    }

    public String[] getVideoSkip() {
        return videoSkip;
    }

    public void setVideoSkip(String[] videoSkip) {
        this.videoSkip = videoSkip;
    }

    public String[] getVideoMute() {
        return videoMute;
    }

    public void setVideoMute(String[] videoMute) {
        this.videoMute = videoMute;
    }

    public String[] getVideoUnmute() {
        return videoUnmute;
    }

    public void setVideoUnmute(String[] videoUnmute) {
        this.videoUnmute = videoUnmute;
    }

    public String[] getVideoReplay() {
        return videoReplay;
    }

    public void setVideoReplay(String[] videoReplay) {
        this.videoReplay = videoReplay;
    }

    public String[] getVideoClose() {
        return videoClose;
    }

    public void setVideoClose(String[] videoClose) {
        this.videoClose = videoClose;
    }

    public String[] getVideoFull() {
        return videoFull;
    }

    public void setVideoFull(String[] videoFull) {
        this.videoFull = videoFull;
    }

    public String[] getVideoExitFull() {
        return videoExitFull;
    }

    public void setVideoExitFull(String[] videoExitFull) {
        this.videoExitFull = videoExitFull;
    }
}

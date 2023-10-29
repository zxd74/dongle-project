package com.iwanvi.adserv.ngx.router.broker.youdao;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-05-09 18:34
 * @version: v1.0
 * @Description: 有道响应类
 */
public class BidResponse {

    /**
     * 广告 id
     * 必填
     */
    private Integer creativeId;
    /**
     * 点击链接
     * 必填
     */
    private String clk;
    /**
     * 点击跟踪链接数组
     * 必填
     */
    private String[] clktrackers;
    /**
     * 展示跟踪链接数组
     * 必填
     */
    private String[] imptracker;
    /**
     * URI schemes 形式的 deep link 链接。如果广告商设置了此URL 将会返回。
     * 选填
     */
    private String deeplink;
    /**
     * deep link 点击跟踪链接数组。当返回 deeplink 字段时返回此字段。
     * 选填
     */
    private String[] dptrackers;
    /**
     * 广告类型，0：落地页广告；1：下载类型广告
     * 必填
     */
    private Integer ydAdType;
    /**
     * 广告样式名称
     * 必填
     */
    private String styleName;
    private String iconimage;
    private String mainimage;
    private String text;
    private String title;
    /**
     * 当广告类型为下载类型时，并且 appName 不为空时，会返回此字段。该字段会将样式中的同名字段覆盖
     * 下载类广告必填
     */
    private String appName;
    /**
     * 当广告类型为下载类型时，并且 packageName 不为空时，会返回此字段。该字段会将样式中的同名字段覆盖
     * 下载类广告必填
     */
    private String packageName;


    // 组图模式（三组图）
    private String mainimage1;
    private String mainimage2;
    private String mainimage3;

    // 视频响应字段
    /**
     * 广告视频地址。
     */
    @JSONField(name = "videourl")
    private String videoUrl;
    /**
     * 客户端是否预先加载广告视频。
     */
    private boolean prefetch;
    /**
     * 视频播放完成后显示的 endCardHTML 页面。
     */
    @JSONField(name = "endcardhtml")
    private String endCardHTML;
    /**
     * 视频的宽度，单位 px
     */
    @JSONField(name = "videowidth")
    private int videoWidth;
    /**
     * 视频的高度，单位 px
     */
    @JSONField(name = "videoheight")
    private int videoHeight;
    /**
     * 视频时长，格式为 HH:mm:ss.SSS,
     */
    @JSONField(name = "videoduration")
    private String videoDuration;
    /**
     * 视频文件大小，单位 byte
     */
    @JSONField(name = "videosize")
    private long videoSize;
    /**
     * 服务器激励回调上报 URL。此字段只有在使用服务器激励回调时会返回。
     *
     */
    private String[] callbackTrackers;
    /**
     * 广告视频加载成功追踪 URL。
     */
    @JSONField(name = "videoloaded")
    private String[] videoLoaded;
    /**
     * 播放视频错误追踪 URL。
     */
    private String[] error;
    /**
     * 播放事件追踪 URL。
     */
    @JSONField(name = "playtrackers")
    private PlayTracker playTrackers;

    // 信息流视频
    /**
     * 有道自有数据，需用其内容替换所有返回的 xxxtrackers 中的 [YD_EXT]宏
     *
     */
    @JSONField(name = "yd_ext")
    private String ydExt;
    /**
     * cta 按钮所显示的文字内容，若没有，则 ctaText 按钮默认显示“查看详情”
     */
    private String ctaText;
    /**
     * 点击 cta 按钮时需上报本 trackers 内所有 tracker（没有 ctaText 则不返回此字段）
     */
    @JSONField(name = "ctatrackers")
    private String[] ctaTrackers;

    @JSONField(name = "coverimage")
    private String coverImage;

    /**
     * 广告类型：激励视频、信息流视频、原生信息
     */
    private String adCat;

    public Integer getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(Integer creativeId) {
        this.creativeId = creativeId;
    }

    public String getClk() {
        return clk;
    }

    public void setClk(String clk) {
        this.clk = clk;
    }

    public String[] getClktrackers() {
        return clktrackers;
    }

    public void setClktrackers(String[] clktrackers) {
        this.clktrackers = clktrackers;
    }

    public String[] getImptracker() {
        return imptracker;
    }

    public void setImptracker(String[] imptracker) {
        this.imptracker = imptracker;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String[] getDptrackers() {
        return dptrackers;
    }

    public void setDptrackers(String[] dptrackers) {
        this.dptrackers = dptrackers;
    }

    public Integer getYdAdType() {
        return ydAdType;
    }

    public void setYdAdType(Integer ydAdType) {
        this.ydAdType = ydAdType;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getIconimage() {
        return iconimage;
    }

    public void setIconimage(String iconimage) {
        this.iconimage = iconimage;
    }

    public String getMainimage() {
        return mainimage;
    }

    public void setMainimage(String mainimage) {
        this.mainimage = mainimage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMainimage1() {
        return mainimage1;
    }

    public void setMainimage1(String mainimage1) {
        this.mainimage1 = mainimage1;
    }

    public String getMainimage2() {
        return mainimage2;
    }

    public void setMainimage2(String mainimage2) {
        this.mainimage2 = mainimage2;
    }

    public String getMainimage3() {
        return mainimage3;
    }

    public void setMainimage3(String mainimage3) {
        this.mainimage3 = mainimage3;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public boolean isPrefetch() {
        return prefetch;
    }

    public void setPrefetch(boolean prefetch) {
        this.prefetch = prefetch;
    }

    public String getEndCardHTML() {
        return endCardHTML;
    }

    public void setEndCardHTML(String endCardHTML) {
        this.endCardHTML = endCardHTML;
    }

    public int getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(int videoWidth) {
        this.videoWidth = videoWidth;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(int videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public long getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(long videoSize) {
        this.videoSize = videoSize;
    }

    public String[] getCallbackTrackers() {
        return callbackTrackers;
    }

    public void setCallbackTrackers(String[] callbackTrackers) {
        this.callbackTrackers = callbackTrackers;
    }

    public String[] getVideoLoaded() {
        return videoLoaded;
    }

    public void setVideoLoaded(String[] videoLoaded) {
        this.videoLoaded = videoLoaded;
    }

    public String[] getError() {
        return error;
    }

    public void setError(String[] error) {
        this.error = error;
    }

    public PlayTracker getPlayTrackers() {
        return playTrackers;
    }

    public void setPlayTrackers(PlayTracker playTrackers) {
        this.playTrackers = playTrackers;
    }

    public String getYdExt() {
        return ydExt;
    }

    public void setYdExt(String ydExt) {
        this.ydExt = ydExt;
    }

    public String getCtaText() {
        return ctaText;
    }

    public void setCtaText(String ctaText) {
        this.ctaText = ctaText;
    }

    public String[] getCtaTrackers() {
        return ctaTrackers;
    }

    public void setCtaTrackers(String[] ctaTrackers) {
        this.ctaTrackers = ctaTrackers;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getAdCat() {
        return adCat;
    }

    public void setAdCat(String adCat) {
        this.adCat = adCat;
    }

    /**
     * 播放事件追踪 URL
     */
    public static class PlayTracker{
        /**
         * 静音事件上报 URLs。
         */
        private String[] mute;
        /**
         * 关闭静音事件上报 URLs。
         */
        private String[] unmute;
        /**
         * 关闭视频事件上报 URLs。
         */
        private String[] videoclose;
        /**
         * 播放进度追踪 URLs
         */
        private PlayPercentage[] playpercentages;

        // 信息流视频播放事件追踪url字段
        /**
         * 播放事件上报 URLs
         */
        private String[] play;
        /**
         *暂停事件上报 URLs
         */
        private String[] pause;
        /**
         * 重新播放事件上报 URLs
         */
        private String[] replay;
        /**
         * 全屏事件上报 URLs
         */
        private String[] fullscreen;
        /**
         * 退出全屏事件上报 URLs
         */
        private String[] unfullscreen;
        /**
         * 上滑事件上报 URLs
         */
        @JSONField(name = "upscroll")
        private String[] upScroll;
        /**
         * 下滑事件上报 URLs
         */
        @JSONField(name = "downscroll")
        private String[] downScroll;
        /**
         * 播放进度追踪 URLs
         */
        @JSONField(name = "playpercent")
        private String[] playPercent;

        public String[] getMute() {
            return mute;
        }

        public void setMute(String[] mute) {
            this.mute = mute;
        }

        public String[] getUnmute() {
            return unmute;
        }

        public void setUnmute(String[] unmute) {
            this.unmute = unmute;
        }

        public String[] getVideoclose() {
            return videoclose;
        }

        public void setVideoclose(String[] videoclose) {
            this.videoclose = videoclose;
        }

        public PlayPercentage[] getPlaypercentages() {
            return playpercentages;
        }

        public void setPlaypercentages(PlayPercentage[] playpercentages) {
            this.playpercentages = playpercentages;
        }

        public String[] getPlay() {
            return play;
        }

        public void setPlay(String[] play) {
            this.play = play;
        }

        public String[] getPause() {
            return pause;
        }

        public void setPause(String[] pause) {
            this.pause = pause;
        }

        public String[] getReplay() {
            return replay;
        }

        public void setReplay(String[] replay) {
            this.replay = replay;
        }

        public String[] getFullscreen() {
            return fullscreen;
        }

        public void setFullscreen(String[] fullscreen) {
            this.fullscreen = fullscreen;
        }

        public String[] getUnfullscreen() {
            return unfullscreen;
        }

        public void setUnfullscreen(String[] unfullscreen) {
            this.unfullscreen = unfullscreen;
        }

        public String[] getUpScroll() {
            return upScroll;
        }

        public void setUpScroll(String[] upScroll) {
            this.upScroll = upScroll;
        }

        public String[] getDownScroll() {
            return downScroll;
        }

        public void setDownScroll(String[] downScroll) {
            this.downScroll = downScroll;
        }

        public String[] getPlayPercent() {
            return playPercent;
        }

        public void setPlayPercent(String[] playPercent) {
            this.playPercent = playPercent;
        }
    }

    /**
     * 播放进度追踪 URLs，客户端在视频播放进度为 checkpoint 时上报对应的所有 URLs。
     */
    public static class PlayPercentage{
        /**
         * 视频播放进度
         */
        private double checkpoint;
        /**
         * 上报 URLs。
         */
        private String[] urls;

        public double getCheckpoint() {
            return checkpoint;
        }

        public void setCheckpoint(double checkpoint) {
            this.checkpoint = checkpoint;
        }

        public String[] getUrls() {
            return urls;
        }

        public void setUrls(String[] urls) {
            this.urls = urls;
        }
    }

    /**
     * 广告类型
     *      LANDING == 0 落地页广告
     *      DOWNLOAD == 1 下载类型广告
     */
    public enum AdType{
        LANDING(0),DOWNLOAD(1);
        private Integer value;
        AdType(Integer value){
            this.value = value;
        }
        public Integer getValue(){
            return this.value;
        }
    }

    /**
     * 广告类型
     *      NATIVE == 原生广告
     *      NATIVE_VIDEO == 原生视频广告
     *      REWARD_VIDEO == 激励视频广告
     */
    public enum AdCat{
        NATIVE_VIDEO("NATIVE_VIDEO"),REWARD_VIDEO("REWARD_VIDEO"),NATIVE("NATIVE");
        private String value;
        AdCat(String value){
            this.value = value;
        }
        public String getValue(){
            return this.value;
        }
    }
}

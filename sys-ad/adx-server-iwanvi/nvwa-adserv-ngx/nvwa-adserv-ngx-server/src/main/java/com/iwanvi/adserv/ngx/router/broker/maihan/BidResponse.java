package com.iwanvi.adserv.ngx.router.broker.maihan;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: 郑晓东
 * @date: 2019-06-03 17:03
 * @version: v1.0
 * @Description: 智云众响应类
 */
public class BidResponse {

    @JSONField(name = "ad_id")
    private String adId;
    @JSONField(name = "impression_link")
    private String[] impressionLink;
    @JSONField(name = "click_link")
    private String clickLink;
    @JSONField(name = "click_link2")
    private String[] clickTrack;
    @JSONField(name = "interact_type")
    private Integer interactType;
    @JSONField(name = "ad_tracking")
    private AdTracking[] adTracking;
    @JSONField(name = "platform_type")
    private Integer platformType;
    @JSONField(name = "mob_adtext")
    private String mobAdtext;
    @JSONField(name = "mob_adlog")
    private String mobAdlog;
    @JSONField(name = "ad_spec")
    private Integer adSpec;
    @JSONField(name = "img_url")
    private String[] imgUrl;
    @JSONField(name = "img2_url")
    private String[] icon;
    private String title;
    private String[] description;
    @JSONField(name = "video_url")
    private String videoUrl;
    @JSONField(name = "video_duration")
    private Integer videoDuration;
    @JSONField(name = "video_play_type")
    private Integer videoPlayType;
    @JSONField(name = "app_package")
    private String appPackage;
    @JSONField(name = "app_size")
    private Integer appSize;
    @JSONField(name = "deeplink_url")
    private String deeplinkUrl;
    @JSONField(name = "fallback_type")
    private Integer fallbackType;
    @JSONField(name = "fallback_url")
    private String fallbackUrl;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String[] getImpressionLink() {
        return impressionLink;
    }

    public void setImpressionLink(String[] impressionLink) {
        this.impressionLink = impressionLink;
    }

    public String getClickLink() {
        return clickLink;
    }

    public void setClickLink(String clickLink) {
        this.clickLink = clickLink;
    }

    public String[] getClickTrack() {
        return clickTrack;
    }

    public void setClickTrack(String[] clickTrack) {
        this.clickTrack = clickTrack;
    }

    public Integer getInteractType() {
        return interactType;
    }

    public void setInteractType(Integer interactType) {
        this.interactType = interactType;
    }

    public AdTracking[] getAdTracking() {
        return adTracking;
    }

    public void setAdTracking(AdTracking[] adTracking) {
        this.adTracking = adTracking;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getMobAdtext() {
        return mobAdtext;
    }

    public void setMobAdtext(String mobAdtext) {
        this.mobAdtext = mobAdtext;
    }

    public String getMobAdlog() {
        return mobAdlog;
    }

    public void setMobAdlog(String mobAdlog) {
        this.mobAdlog = mobAdlog;
    }

    public Integer getAdSpec() {
        return adSpec;
    }

    public void setAdSpec(Integer adSpec) {
        this.adSpec = adSpec;
    }

    public String[] getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String[] getIcon() {
        return icon;
    }

    public void setIcon(String[] icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String[] description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Integer videoDuration) {
        this.videoDuration = videoDuration;
    }

    public Integer getVideoPlayType() {
        return videoPlayType;
    }

    public void setVideoPlayType(Integer videoPlayType) {
        this.videoPlayType = videoPlayType;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public Integer getAppSize() {
        return appSize;
    }

    public void setAppSize(Integer appSize) {
        this.appSize = appSize;
    }

    public String getDeeplinkUrl() {
        return deeplinkUrl;
    }

    public void setDeeplinkUrl(String deeplinkUrl) {
        this.deeplinkUrl = deeplinkUrl;
    }

    public Integer getFallbackType() {
        return fallbackType;
    }

    public void setFallbackType(Integer fallbackType) {
        this.fallbackType = fallbackType;
    }

    public String getFallbackUrl() {
        return fallbackUrl;
    }

    public void setFallbackUrl(String fallbackUrl) {
        this.fallbackUrl = fallbackUrl;
    }

    /**
     * 广告事件对象
     */
    public static class AdTracking{
        @JSONField(name = "tracking_event")
        private Integer trackingEvent;
        @JSONField(name = "tracking_url")
        private String[] trackingUrl;

        public Integer getTrackingEvent() {
            return trackingEvent;
        }

        public void setTrackingEvent(Integer trackingEvent) {
            this.trackingEvent = trackingEvent;
        }

        public String[] getTrackingUrl() {
            return trackingUrl;
        }

        public void setTrackingUrl(String[] trackingUrl) {
            this.trackingUrl = trackingUrl;
        }
    }

    /**
     * 交互方式
     *      LINK == 0 跳转
     *      DOWNLOAD == 1 App下载
     *      DEEP_LINK == 2 Deeplink
     */
    public enum InteractType{
        LINK(0),DOWNLOAD(1),DEEP_LINK(2);
        private Integer value;
        InteractType(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }
    /**
     * 广告规格
     * 1：左文右图（图片尺寸 480*320）
     * 2：左图右文（图片尺寸 480*320）
     * 3：上文下三图（图片尺寸 480*320）
     * 4：上文下图（图片尺寸 1280*720）
     * 5：上文下视频（视频尺寸 1280*720）
     */
    public enum AdSpecType{
        LEFT_CON_RIGHT_IMG(1),LEFT_IMG_RIGHT_CON(2),UP_CON_DOWN_IMGS(3),UP_CON_DOWN_IMG(4),UP_CON_DOWN_VIDEO(5);
        private Integer value;
        AdSpecType(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }

    /**
     * 视频广告播放类型
     *      AUTO == 1 自动播放（默认）
     *      CLICK == 0 点击播放
     */
    public enum VideoPlayType{
        AUTO(1),CLICK(0);
        private Integer value;
        VideoPlayType(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }

    /**
     * Deeplink 唤醒广告退化类型
     *      BROWSER == 1 浏览器打开页面
     *      DOWN == 2 APP 下载
     */
    public enum FallbackType{
        BROWSER(1),DOWN(0);
        private Integer value;
        FallbackType(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }

    /**
     * AdTrackType 广告事件类型
     *      CLOSE == 2 广告关闭
     *      VIDEO_START == 101000 视频广告开始播放
     *      VIDEO_FULL_SCREEN == 101001 视频广告全屏播放
     *      VIDEO_CLOSE == 101002 视频广告结束
     *      VIDEO_CLICK_PLAY == 101003 点击预览图播放视频
     *      APP_DOWNLOADED == 102000 APP 下载完成
     *      APP_INSTALLED == 102001 APP 安装完成
     *      APP_INIT == 102002 APP 激活
     *      APP_DOWNLOAD == 102003 APP 下载开始
     */
    public enum AdTrackType{
        CLOSE(2),VIDEO_START(101000),VIDEO_FULL_SCREEN(101001),VIDEO_CLOSE(101002),VIDEO_CLICK_PLAY(101003),APP_DOWNLOADED(102000),APP_INSTALLED(102001),APP_INIT(102002),APP_DOWNLOAD(102003);
        private Integer value;
        AdTrackType(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }
}

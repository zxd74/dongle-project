package com.iwanvi.adserv.ngx.router.broker.yungao;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-09 13:48
 */
public class BidResponse {

    /**
     * 广告请求 ID
     */
    @JSONField(name = "request_id")
    private String requestId;
    /**
     * 错误代码
     */
    @JSONField(name = "error_code")
    private Integer errorCode;
    /**
     * 响应说明
     */
    private String message;
    /**
     * 广告清单
     */
    private Ad[] ads;
    /**
     * 广告清单个数
     */
    @JSONField(name = "total_num")
    private Integer totalNum;
    /**
     * 广告清单过期时间戳
     */
    @JSONField(name = "expiration_time")
    private Integer expirationTime;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Ad[] getAds() {
        return ads;
    }

    public void setAds(Ad[] ads) {
        this.ads = ads;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }

    public static class Ad{
        /**
         * 广告签名
         */
        @JSONField(name = "ad_key")
        private String adKey;
        /**
         * HTML 片段
         */
        @JSONField(name = "html_snippet")
        private String htmlSnippet;
        /**
         * “广告”图标
         */
        @JSONField(name = "mob_adtext")
        private String mobAdtext;
        /**
         * 图标
         */
        @JSONField(name = "mob_adlogo")
        private String mobAdLogo;
        /**
         * 广告推广标题
         */
        private String title;
        /**
         * 广告品牌名称
         */
        @JSONField(name = "brand_name")
        private String brandName;
        /**
         * 广告描述
         */
        private String description;
        /**
         * 图片地址
         */
        @JSONField(name = "image_src")
        private String[] imageSrcs;
        /**
         * 图标地址
         */
        @JSONField(name = "icon_src")
        private String iconSrc;
        /**
         * 交互类型
         */
        @JSONField(name = "interaction_type")
        private String interactionType;
        /**
         * 是否下载中间页
         */
        @JSONField(name = "is_download_middle_page")
        private String isDownloadMiddlePage;
        /**
         * 是否特殊下载类；如果是特殊下载类，需要请求 click_url，获取最终的下载地址(GDT)
         */
        @JSONField(name = "special_download")
        private String specialDownload;
        /**
         * 点击行为地址
         */
        @JSONField(name = "click_url")
        private String clickUrl;
        /**
         * 名称
         */
        @JSONField(name = "app_name")
        private String appName;
        /**
         * 包名
         */
        @JSONField(name = "app_package")
        private String appPackage;
        /**
         * 应用文件大小
         */
        @JSONField(name = "app_size")
        private String appSize;
        /**
         * 广告视频物料地址
         */
        @JSONField(name = "video_url")
        private String videoUrl;
        /**
         * 客户端是否预先加载广告视频。
         */
        private String prefetch;
        /**
         * 广告视频物料时长
         */
        @JSONField(name = "video_duration")
        private String videoDuration;
        /**
         * deeplink 唤醒广告打开页面
         */
        @JSONField(name = "deeplink_url")
        private String deeplinkUrl;
        /**
         * 点击上报 URLs，包含视频广告点击上报
         */
        @JSONField(name = "report_click")
        private String[] reportClick;
        /**
         * 展示上报 URLs
         */
        @JSONField(name = "report_impress")
        private String[] reportImpress;
        /**
         * deeplink 点击上报 URLs
         */
        @JSONField(name = "report_deeplink_click")
        private String[] reportDeeplinkClick;
        /**
         * deeplink 唤醒成功
         */
        @JSONField(name = "report_deeplink_success")
        private String[] reportDeeplinkSuccess;
        /**
         * deeplink 唤醒失败
         */
        @JSONField(name = "report_deeplink_fail")
        private String[] reportDeeplinkFail;
        /**
         * 下载开始上报 URLs
         */
        @JSONField(name = "report_startdown")
        private String[] reportStartDown;
        /**
         * 下载完成上报 URLs
         */
        @JSONField(name = "report_downsucc")
        private String[] reportDownSucc;
        /**
         * 安装开始上报 URLs
         */
        @JSONField(name = "report_startinstall")
        private String[] reportStartInstall;
        /**
         * 安装完成上报 URLs
         */
        @JSONField(name = "report_installsucc")
        private String[] reportInstallSucc;
        /**
         * 应用打开上报 URLs
         */
        @JSONField(name = "report_appactive")
        private String[] reportAppActive;
        /**
         * 静音事件上报 URLs（激励视频）
         */
        @JSONField(name = "report_video_mute")
        private String[] reportVideoMute;
        /**
         * 关闭静音事件上报 URLs（激励视频）
         */
        @JSONField(name = "report_video_unmute")
        private String[] reportVideoUnmute;
        /**
         * 关闭视频事件上报 URLs（激励视频）
         */
        @JSONField(name = "report_video_close")
        private String[] reportVideoClose;
        /**
         * 视频加载成功上报 URLs
         */
        @JSONField(name = "report_video_load")
        private String[] reportVideoLoad;
        /**
         * 视频播放事件上报 URLs
         */
        @JSONField(name = "report_video_play")
        private String[] reportVideoPlay;
        /**
         * 视频暂定事件上报 URLs
         */
        @JSONField(name = "report_video_pause")
        private String[] reportVideoPause;
        /**
         * 视频再次播放上报 URLS
         */
        @JSONField(name = "report_video_continue")
        private String[] reportVideoContinue;
        /**
         * 视频全屏事件上报 URLs
         */
        @JSONField(name = "report_video_fullscreen")
        private String[] reportVideoFullscreen;
        /**
         * 视频退出全屏事件上报 URLs
         */
        @JSONField(name = "report_video_unfullscreen")
        private String[] reportVideoUnfullscreen;
        /**
         * 视频播放完成上报 URLs
         */
        @JSONField(name = "report_video_complete")
        private String[] reportVideoComplete;
        /**
         * 视频中途关闭上报 URLs
         */
        @JSONField(name = "report_video_interrupt")
        private String[] reportVideoInterrupt;
        /**
         * 中间页被关闭上报 URLs
         */
        @JSONField(name = "report_video_pageclose")
        private String[] reportVideoPageclose;
        /**
         * 视频跳过上报 URLs
         */
        @JSONField(name = "report_video_skip")
        private String[] reportVideoSkip;
        /**
         * 视频播放过程上报 URLs
         */
        @JSONField(name = "report_video_play_percentage")
        private VideoReport[] reportVideoPlayPercentage;
        /**
         * 播放视频出错上报 URLs
         */
        @JSONField(name = "report_video_error")
        private String[] reportVideoError;
        /**
         * 返回视频 vast 模板
         */
        private String vast;
        /**
         * 广告想用的扩展字段
         */
        private String ext;

        public String getAdKey() {
            return adKey;
        }

        public void setAdKey(String adKey) {
            this.adKey = adKey;
        }

        public String getHtmlSnippet() {
            return htmlSnippet;
        }

        public void setHtmlSnippet(String htmlSnippet) {
            this.htmlSnippet = htmlSnippet;
        }

        public String getMobAdtext() {
            return mobAdtext;
        }

        public void setMobAdtext(String mobAdtext) {
            this.mobAdtext = mobAdtext;
        }

        public String getMobAdLogo() {
            return mobAdLogo;
        }

        public void setMobAdLogo(String mobAdLogo) {
            this.mobAdLogo = mobAdLogo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String[] getImageSrcs() {
            return imageSrcs;
        }

        public void setImageSrcs(String[] imageSrcs) {
            this.imageSrcs = imageSrcs;
        }

        public String getIconSrc() {
            return iconSrc;
        }

        public void setIconSrc(String iconSrc) {
            this.iconSrc = iconSrc;
        }

        public String getInteractionType() {
            return interactionType;
        }

        public void setInteractionType(String interactionType) {
            this.interactionType = interactionType;
        }

        public String getIsDownloadMiddlePage() {
            return isDownloadMiddlePage;
        }

        public void setIsDownloadMiddlePage(String isDownloadMiddlePage) {
            this.isDownloadMiddlePage = isDownloadMiddlePage;
        }

        public String getSpecialDownload() {
            return specialDownload;
        }

        public void setSpecialDownload(String specialDownload) {
            this.specialDownload = specialDownload;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppPackage() {
            return appPackage;
        }

        public void setAppPackage(String appPackage) {
            this.appPackage = appPackage;
        }

        public String getAppSize() {
            return appSize;
        }

        public void setAppSize(String appSize) {
            this.appSize = appSize;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getPrefetch() {
            return prefetch;
        }

        public void setPrefetch(String prefetch) {
            this.prefetch = prefetch;
        }

        public String getVideoDuration() {
            return videoDuration;
        }

        public void setVideoDuration(String videoDuration) {
            this.videoDuration = videoDuration;
        }

        public String getDeeplinkUrl() {
            return deeplinkUrl;
        }

        public void setDeeplinkUrl(String deeplinkUrl) {
            this.deeplinkUrl = deeplinkUrl;
        }

        public String[] getReportClick() {
            return reportClick;
        }

        public void setReportClick(String[] reportClick) {
            this.reportClick = reportClick;
        }

        public String[] getReportImpress() {
            return reportImpress;
        }

        public void setReportImpress(String[] reportImpress) {
            this.reportImpress = reportImpress;
        }

        public String[] getReportDeeplinkClick() {
            return reportDeeplinkClick;
        }

        public void setReportDeeplinkClick(String[] reportDeeplinkClick) {
            this.reportDeeplinkClick = reportDeeplinkClick;
        }

        public String[] getReportDeeplinkSuccess() {
            return reportDeeplinkSuccess;
        }

        public void setReportDeeplinkSuccess(String[] reportDeeplinkSuccess) {
            this.reportDeeplinkSuccess = reportDeeplinkSuccess;
        }

        public String[] getReportDeeplinkFail() {
            return reportDeeplinkFail;
        }

        public void setReportDeeplinkFail(String[] reportDeeplinkFail) {
            this.reportDeeplinkFail = reportDeeplinkFail;
        }

        public String[] getReportStartDown() {
            return reportStartDown;
        }

        public void setReportStartDown(String[] reportStartDown) {
            this.reportStartDown = reportStartDown;
        }

        public String[] getReportDownSucc() {
            return reportDownSucc;
        }

        public void setReportDownSucc(String[] reportDownSucc) {
            this.reportDownSucc = reportDownSucc;
        }

        public String[] getReportStartInstall() {
            return reportStartInstall;
        }

        public void setReportStartInstall(String[] reportStartInstall) {
            this.reportStartInstall = reportStartInstall;
        }

        public String[] getReportInstallSucc() {
            return reportInstallSucc;
        }

        public void setReportInstallSucc(String[] reportInstallSucc) {
            this.reportInstallSucc = reportInstallSucc;
        }

        public String[] getReportAppActive() {
            return reportAppActive;
        }

        public void setReportAppActive(String[] reportAppActive) {
            this.reportAppActive = reportAppActive;
        }

        public String[] getReportVideoMute() {
            return reportVideoMute;
        }

        public void setReportVideoMute(String[] reportVideoMute) {
            this.reportVideoMute = reportVideoMute;
        }

        public String[] getReportVideoUnmute() {
            return reportVideoUnmute;
        }

        public void setReportVideoUnmute(String[] reportVideoUnmute) {
            this.reportVideoUnmute = reportVideoUnmute;
        }

        public String[] getReportVideoClose() {
            return reportVideoClose;
        }

        public void setReportVideoClose(String[] reportVideoClose) {
            this.reportVideoClose = reportVideoClose;
        }

        public String[] getReportVideoLoad() {
            return reportVideoLoad;
        }

        public void setReportVideoLoad(String[] reportVideoLoad) {
            this.reportVideoLoad = reportVideoLoad;
        }

        public String[] getReportVideoPlay() {
            return reportVideoPlay;
        }

        public void setReportVideoPlay(String[] reportVideoPlay) {
            this.reportVideoPlay = reportVideoPlay;
        }

        public String[] getReportVideoPause() {
            return reportVideoPause;
        }

        public void setReportVideoPause(String[] reportVideoPause) {
            this.reportVideoPause = reportVideoPause;
        }

        public String[] getReportVideoContinue() {
            return reportVideoContinue;
        }

        public void setReportVideoContinue(String[] reportVideoContinue) {
            this.reportVideoContinue = reportVideoContinue;
        }

        public String[] getReportVideoFullscreen() {
            return reportVideoFullscreen;
        }

        public void setReportVideoFullscreen(String[] reportVideoFullscreen) {
            this.reportVideoFullscreen = reportVideoFullscreen;
        }

        public String[] getReportVideoUnfullscreen() {
            return reportVideoUnfullscreen;
        }

        public void setReportVideoUnfullscreen(String[] reportVideoUnfullscreen) {
            this.reportVideoUnfullscreen = reportVideoUnfullscreen;
        }

        public String[] getReportVideoComplete() {
            return reportVideoComplete;
        }

        public void setReportVideoComplete(String[] reportVideoComplete) {
            this.reportVideoComplete = reportVideoComplete;
        }

        public String[] getReportVideoInterrupt() {
            return reportVideoInterrupt;
        }

        public void setReportVideoInterrupt(String[] reportVideoInterrupt) {
            this.reportVideoInterrupt = reportVideoInterrupt;
        }

        public String[] getReportVideoPageclose() {
            return reportVideoPageclose;
        }

        public void setReportVideoPageclose(String[] reportVideoPageclose) {
            this.reportVideoPageclose = reportVideoPageclose;
        }

        public String[] getReportVideoSkip() {
            return reportVideoSkip;
        }

        public void setReportVideoSkip(String[] reportVideoSkip) {
            this.reportVideoSkip = reportVideoSkip;
        }

        public VideoReport[] getReportVideoPlayPercentage() {
            return reportVideoPlayPercentage;
        }

        public void setReportVideoPlayPercentage(VideoReport[] reportVideoPlayPercentage) {
            this.reportVideoPlayPercentage = reportVideoPlayPercentage;
        }

        public String[] getReportVideoError() {
            return reportVideoError;
        }

        public void setReportVideoError(String[] reportVideoError) {
            this.reportVideoError = reportVideoError;
        }

        public String getVast() {
            return vast;
        }

        public void setVast(String vast) {
            this.vast = vast;
        }

        public String getExt() {
            return ext;
        }

        public void setExt(String ext) {
            this.ext = ext;
        }
    }

    public static class VideoReport{
        /**
         * 播放时间，秒
         */
        private Integer second;
        /**
         * 视频播放过程上报 URLS
         */
        private String[] urls;

        public Integer getSecond() {
            return second;
        }

        public void setSecond(Integer second) {
            this.second = second;
        }

        public String[] getUrls() {
            return urls;
        }

        public void setUrls(String[] urls) {
            this.urls = urls;
        }
    }
}

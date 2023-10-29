package com.iwanvi.adserv.ngx.router.broker.oneway;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.sun.org.apache.bcel.internal.generic.IUSHR;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-02 15:08
 */
public class BidResponse {

    /**
     * 状态值，true代表正常，false代表错误
     */
    private boolean success;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 状态码
     */
    private Integer errorCode;
    /**
     * 广告信息
     */
    private Data data;
    /**
     * 下一次请求间隔时间 单位: 秒
     */
    private Integer timeLag;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getTimeLag() {
        return timeLag;
    }

    public void setTimeLag(Integer timeLag) {
        this.timeLag = timeLag;
    }

    public static class Data {
        /**
         * 唯一回话ID
         */
        private String sessionId;
        /**
         * 推广应用图标URL
         */
        private String appIcon;
        /**
         * 推广ID
         */
        private Long campaignId;
        /**
         * 推广应用名称
         */
        private String appName;
        /**
         * 推广应用平均评分
         */
        private Float rating;
        /**
         * 推广应用评价数
         */
        private Integer ratingCount;
        /**
         * 应用包名
         */
        private String appStoreId;
        /**
         * 推广标题
         */
        private String title;
        /**
         * 下载类型
         */
        private Integer downloadType;
        /**
         * 推广应用素材横竖屏方向
         */
        private String orientation;

        /**
         * 点击跳转链接
         */
        private String clickUrl;
        /**
         * 深度链接
         */
        private String deeplink;
        /**
         * 落地页链接 (可选)
         */
        private String landingPageUrl;
        /**
         * 事件跟踪上报, 必须实时处理上报否则会影响收益情况  注意宏替换参数
         */
        private TrackingEvent trackingEvents;
        /**
         * POST上报事件跟踪
         */
        private PostEvent postTrackingEvents;
        /**
         * 点击模式
         */
        private Integer clickMode;
        /**
         * 秒数, 显示多少秒之后才可跳过
         */
        private Integer skipSeconds;
        /**
         * 视频素材
         */
        /**
         * 视频广告
         */
        private Video video;

        /**
         * 图片素材
         */
        private Image[] images;
        /**
         * 是否是自动跳转; 如果为true 则视频播放完成自动跳转clickUrl
         */
        private boolean autoLanding;
        /**
         * 如果有返回值且autoLanding=false 则视频播放完成加载该html
         */
        private String endhtml;
        /**
         * 广告有效时间(单位: 秒), 过期判定为无效上报
         */
        private String expirationTime;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(String appIcon) {
            this.appIcon = appIcon;
        }

        public Long getCampaignId() {
            return campaignId;
        }

        public void setCampaignId(Long campaignId) {
            this.campaignId = campaignId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public Float getRating() {
            return rating;
        }

        public void setRating(Float rating) {
            this.rating = rating;
        }

        public Integer getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(Integer ratingCount) {
            this.ratingCount = ratingCount;
        }

        public String getAppStoreId() {
            return appStoreId;
        }

        public void setAppStoreId(String appStoreId) {
            this.appStoreId = appStoreId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getDownloadType() {
            return downloadType;
        }

        public void setDownloadType(Integer downloadType) {
            this.downloadType = downloadType;
        }

        public String getOrientation() {
            return orientation;
        }

        public void setOrientation(String orientation) {
            this.orientation = orientation;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

        public String getDeeplink() {
            return deeplink;
        }

        public void setDeeplink(String deeplink) {
            this.deeplink = deeplink;
        }

        public String getLandingPageUrl() {
            return landingPageUrl;
        }

        public void setLandingPageUrl(String landingPageUrl) {
            this.landingPageUrl = landingPageUrl;
        }


        public TrackingEvent getTrackingEvents() {
            return trackingEvents;
        }

        public void setTrackingEvents(TrackingEvent trackingEvents) {
            this.trackingEvents = trackingEvents;
        }

        public PostEvent getPostTrackingEvents() {
            return postTrackingEvents;
        }

        public void setPostTrackingEvents(PostEvent postTrackingEvents) {
            this.postTrackingEvents = postTrackingEvents;
        }

        public Integer getClickMode() {
            return clickMode;
        }

        public void setClickMode(Integer clickMode) {
            this.clickMode = clickMode;
        }

        public Integer getSkipSeconds() {
            return skipSeconds;
        }

        public void setSkipSeconds(Integer skipSeconds) {
            this.skipSeconds = skipSeconds;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public Image[] getImages() {
            return images;
        }

        public void setImages(Image[] images) {
            this.images = images;
        }

        public boolean isAutoLanding() {
            return autoLanding;
        }

        public void setAutoLanding(boolean autoLanding) {
            this.autoLanding = autoLanding;
        }

        public String getEndhtml() {
            return endhtml;
        }

        public void setEndhtml(String endhtml) {
            this.endhtml = endhtml;
        }

        public String getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(String expirationTime) {
            this.expirationTime = expirationTime;
        }
    }

    public static class Video {
        private String url;
        private Float duration;
        private String coverUrl;
        /**
         * 视频来源
         */
        private String sourceUrl;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Float getDuration() {
            return duration;
        }

        public void setDuration(Float duration) {
            this.duration = duration;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public String getSourceUrl() {
            return sourceUrl;
        }

        public void setSourceUrl(String sourceUrl) {
            this.sourceUrl = sourceUrl;
        }
    }

    public static class Image {
        /**
         * 素材地址
         */
        private String url;
        private Integer w;
        private Integer h;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getW() {
            return w;
        }

        public void setW(Integer w) {
            this.w = w;
        }

        public Integer getH() {
            return h;
        }

        public void setH(Integer h) {
            this.h = h;
        }
    }

    public static class TrackingEvent {
        // 视频广告检测
        private String[] start;
        private String[]  end;
        private String[] skip;
        private String[] firstQuartile;
        private String[] midpoint;
        private String[] thirdQuartile;

        // 广告检测
        private String[] click;
        private String[] show;
        private String[] close;

        // 下载广告检测
        private String[] apkDownloadStart;
        private String[] apkDownloadFinish;
        private String[] apkDownloadError;
        private String[] packageAdded;

        public String[] getStart() {
            return start;
        }

        public void setStart(String[] start) {
            this.start = start;
        }

        public String[] getEnd() {
            return end;
        }

        public void setEnd(String[] end) {
            this.end = end;
        }

        public String[] getSkip() {
            return skip;
        }

        public void setSkip(String[] skip) {
            this.skip = skip;
        }

        public String[] getFirstQuartile() {
            return firstQuartile;
        }

        public void setFirstQuartile(String[] firstQuartile) {
            this.firstQuartile = firstQuartile;
        }

        public String[] getMidpoint() {
            return midpoint;
        }

        public void setMidpoint(String[] midpoint) {
            this.midpoint = midpoint;
        }

        public String[] getThirdQuartile() {
            return thirdQuartile;
        }

        public void setThirdQuartile(String[] thirdQuartile) {
            this.thirdQuartile = thirdQuartile;
        }

        public String[] getClick() {
            return click;
        }

        public void setClick(String[] click) {
            this.click = click;
        }

        public String[] getShow() {
            return show;
        }

        public void setShow(String[] show) {
            this.show = show;
        }

        public String[] getClose() {
            return close;
        }

        public void setClose(String[] close) {
            this.close = close;
        }

        public String[] getApkDownloadStart() {
            return apkDownloadStart;
        }

        public void setApkDownloadStart(String[] apkDownloadStart) {
            this.apkDownloadStart = apkDownloadStart;
        }

        public String[] getApkDownloadFinish() {
            return apkDownloadFinish;
        }

        public void setApkDownloadFinish(String[] apkDownloadFinish) {
            this.apkDownloadFinish = apkDownloadFinish;
        }

        public String[] getApkDownloadError() {
            return apkDownloadError;
        }

        public void setApkDownloadError(String[] apkDownloadError) {
            this.apkDownloadError = apkDownloadError;
        }

        public String[] getPackageAdded() {
            return packageAdded;
        }

        public void setPackageAdded(String[] packageAdded) {
            this.packageAdded = packageAdded;
        }
    }

    public static class PostEvent{
        private PostEventData[] show;
        private PostEventData[] click;
        private Integer downloadType;
        private Integer clickMode;

        public PostEventData[] getShow() {
            return show;
        }

        public void setShow(PostEventData[] show) {
            this.show = show;
        }

        public PostEventData[] getClick() {
            return click;
        }

        public void setClick(PostEventData[] click) {
            this.click = click;
        }

        public Integer getDownloadType() {
            return downloadType;
        }

        public void setDownloadType(Integer downloadType) {
            this.downloadType = downloadType;
        }

        public Integer getClickMode() {
            return clickMode;
        }

        public void setClickMode(Integer clickMode) {
            this.clickMode = clickMode;
        }
    }
    public static class PostEventData{
        /**
         * 检测地址
         */
        private String[] url;
        /**
         * 参数信息
         */
        private EventParam data;

        public String[] getUrl() {
            return url;
        }

        public void setUrl(String[] url) {
            this.url = url;
        }

        public EventParam getData() {
            return data;
        }

        public void setData(EventParam data) {
            this.data = data;
        }
    }

    public static class EventParam {
        /**
         * 事件类型
         */
        private String type;
        /**
         * androidId
         */
        private String androidId;
        /**
         * clientIp
         */
        private String clientIp;
        /**
         * 时间戳宏替换参数 {TIMESTAMP}
         */
        private String timestamp;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

}

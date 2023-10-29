package com.iwanvi.adserv.ngx.router.broker.sogou;


/**
 * @author: 郑晓东
 * @date: 2019-07-05 09:04
 * @version: v1.0
 * @Description: 搜狗响应数据类
 */
public class BidResponse {
    /**
     * 系统响应码，0为正常，其他为异常。详⻅见系统响应码表
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String msg;
    /**
     * ⼴广告结果信息
     */
    private Data data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        /**
         * 请求的requestId
         */
        private String requestId;
        /**
         * 返回广告时间戳
         */
        private Long ts;
        /**
         * 广告组列列表，所有规格广告列表
         */
        private Group[] groups;

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public Long getTs() {
            return ts;
        }

        public void setTs(Long ts) {
            this.ts = ts;
        }

        public Group[] getGroups() {
            return groups;
        }

        public void setGroups(Group[] groups) {
            this.groups = groups;
        }
    }

    public static class Group{
        /**
         * impId，对应请求中imps结构中的id
         */
        private Integer impId;
        /**
         * 对应某种规格下的⼴广告列列表
         */
        private Ad[] ads;

        public Integer getImpId() {
            return impId;
        }

        public void setImpId(Integer impId) {
            this.impId = impId;
        }

        public Ad[] getAds() {
            return ads;
        }

        public void setAds(Ad[] ads) {
            this.ads = ads;
        }
    }

    public static class Ad{
        /**
         * 广告序号
         */
        private Integer id;
        /**
         * 广告唯⼀一ID
         */
        private Long creativeId;
        /**
         * urlencode之后的标题信息
         */
        private String title;
        /**
         * urlencode之后的描述信息
         */
        private String summary;
        /**
         * 广告链接地址或者是app下载地址
         */
        private String link;
        /**
         * 广告链接点击后的回调地址数组，⾥里里⾯面的地址需全部
         * 调用
         */
        private String[] clickTrackUrls;
        /**
         * 广告展示后的回调地址，⾥里里⾯面的地址需全部调⽤
         */
        private String[] impTrackUrls;
        /**
         * 广告位宽度
         */
        private Double adWidth;
        /**
         * 广告位⾼高度
         */
        private Double adHeight;
        /**
         * 表示广告的展示类型
         */
        private Integer adType;
        /**
         * 表示是否是下载类广告
         */
        private Integer downloadAd;
        /**
         * 表示广告来源。urlEncode之后的字符串串。广告主填写该字段，则填充为广告主填写的字段，否则为默认值"搜狗奇点"
         */
        private String source;
        /**
         * nativeAd_type: 原生广告素材类型
         */
        private Integer nativeAdType;
        /**
         * 广告价格，单位为千分之一分
         */
        private Integer price;
        /**
         * 广告图片信息
         */
        private Image[] imgs;
        /**
         * app相关信息
         */
        private App appInfo;
        /**
         * 开屏广告信息
         */
        private Splash splashInfo;
        /**
         * 视频信息
         */
        private Video video;
        /**
         * 广告图标地址，广告主或平台对应的广告标识icon
         */
        private String adIcon;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Long getCreativeId() {
            return creativeId;
        }

        public void setCreativeId(Long creativeId) {
            this.creativeId = creativeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String[] getClickTrackUrls() {
            return clickTrackUrls;
        }

        public void setClickTrackUrls(String[] clickTrackUrls) {
            this.clickTrackUrls = clickTrackUrls;
        }

        public String[] getImpTrackUrls() {
            return impTrackUrls;
        }

        public void setImpTrackUrls(String[] impTrackUrls) {
            this.impTrackUrls = impTrackUrls;
        }

        public Double getAdWidth() {
            return adWidth;
        }

        public void setAdWidth(Double adWidth) {
            this.adWidth = adWidth;
        }

        public Double getAdHeight() {
            return adHeight;
        }

        public void setAdHeight(Double adHeight) {
            this.adHeight = adHeight;
        }

        public Integer getAdType() {
            return adType;
        }

        public void setAdType(Integer adType) {
            this.adType = adType;
        }

        public Integer getDownloadAd() {
            return downloadAd;
        }

        public void setDownloadAd(Integer downloadAd) {
            this.downloadAd = downloadAd;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Integer getNativeAdType() {
            return nativeAdType;
        }

        public void setNativeAdType(Integer nativeAdType) {
            this.nativeAdType = nativeAdType;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Image[] getImgs() {
            return imgs;
        }

        public void setImgs(Image[] imgs) {
            this.imgs = imgs;
        }

        public App getAppInfo() {
            return appInfo;
        }

        public void setAppInfo(App appInfo) {
            this.appInfo = appInfo;
        }

        public Splash getSplashInfo() {
            return splashInfo;
        }

        public void setSplashInfo(Splash splashInfo) {
            this.splashInfo = splashInfo;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public String getAdIcon() {
            return adIcon;
        }

        public void setAdIcon(String adIcon) {
            this.adIcon = adIcon;
        }
    }

    public static class Image{
        /**
         * 广告图⽚片的url
         */
        private String url;
        /**
         * 图片宽度
         */
        private Double width;
        /**
         * 图片⾼高度
         */
        private Double height;
        /**
         * urlencode之后的图片描述，没有留留空
         */
        private String desc;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Double getWidth() {
            return width;
        }

        public void setWidth(Double width) {
            this.width = width;
        }

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    public static class App{
        /**
         * 表示下载类广告应⽤用的图标图片地址
         */
        private String icon;
        /**
         * Apk广告下载包包名
         */
        private String pkgName;
        /**
         * 包大小
         */
        private Long size;
        /**
         * apk包MD5值
         */
        private String md5;
        /**
         * 版本名和版本号使用，拼接而成，例如1.4.0.801,60
         */
        private String version;
        /**
         * 下载开始回调地址，非下载类广告该字段为空
         */
        private String dsUrl;
        /**
         * 下载完成回调地址，非下载类广告该字段为空
         */
        private String dfUrl;
        /**
         * 安装完成回调地址，非下载类广告该字段为空
         */
        private String sfUrl;
        /**
         * app唤醒地址,如果媒体端⽀支持deeplink，且⽤用户安装有对
         * 应的app，则点击时使⽤用此字段调起。如果deeplink有
         * 值，点击⼴广告优先使⽤用deeplink，调起失败再使⽤用link字
         * 段。
         */
        private String deepLink;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getPkgName() {
            return pkgName;
        }

        public void setPkgName(String pkgName) {
            this.pkgName = pkgName;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDsUrl() {
            return dsUrl;
        }

        public void setDsUrl(String dsUrl) {
            this.dsUrl = dsUrl;
        }

        public String getDfUrl() {
            return dfUrl;
        }

        public void setDfUrl(String dfUrl) {
            this.dfUrl = dfUrl;
        }

        public String getSfUrl() {
            return sfUrl;
        }

        public void setSfUrl(String sfUrl) {
            this.sfUrl = sfUrl;
        }

        public String getDeepLink() {
            return deepLink;
        }

        public void setDeepLink(String deepLink) {
            this.deepLink = deepLink;
        }
    }

    public static class Splash{
        /**
         * 开屏广告建议倒计时时间（单位秒），非开屏广告为-1
         */
        private Integer countdown;
        /**
         * 开屏广告建议一天重复显示最大次数，非开屏广告为-1
         */
        private Integer maxPerDay;
        /**
         * 开屏广告建议两次相同广告出现最小间隔（单位秒），非开屏广告为-1
         */
        private Integer interval;
        /**
         * 开屏广告计划开始时间，此时间前不要展示此广告，值为时间
         * 戳（从1970.1.1至今毫秒数），非开屏广告为-1
         */
        private Long startTime;
        /**
         * 开屏广告计划结束时间，此时间前不要展示此广告，值为时间
         * 戳（从1970.1.1至今毫秒数），非开屏广告为-1
         */
        private Long endTime;
        /**
         * 开屏广告类型 1 全屏 2 半屏，非开屏广告为-1
         */
        private Integer type;

        public Integer getCountdown() {
            return countdown;
        }

        public void setCountdown(Integer countdown) {
            this.countdown = countdown;
        }

        public Integer getMaxPerDay() {
            return maxPerDay;
        }

        public void setMaxPerDay(Integer maxPerDay) {
            this.maxPerDay = maxPerDay;
        }

        public Integer getInterval() {
            return interval;
        }

        public void setInterval(Integer interval) {
            this.interval = interval;
        }

        public Long getStartTime() {
            return startTime;
        }

        public void setStartTime(Long startTime) {
            this.startTime = startTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }
    public static class Video{
        /**
         * 视频的播放时长，以毫秒为单位
         */
        private Integer duration;
        /**
         * 视频类型
         */
        private Integer mimeType;
        /**
         * 视频文件宽度
         */
        private Integer width;
        /**
         * 视频文件⾼高度
         */
        private Integer height;
        /**
         * 视频文件的url，流视频文件为转码以后的地址
         */
        private String videoUrl;
        /**
         * 视频文件的封面图片url
         */
        private String coverUrl;
        /**
         * 视频大小，以KB为单位
         */
        private Integer length;
        /**
         * 事件监测url
         */
        private EventTrack[] eventTracks;
        /**
         * 视频类型
         */
        private Integer videoType;
        /**
         * 在播放时间到达最小播放时长后是否允许出现跳过
         */
        private Integer skip;
        /**
         * 视频允许跳过最小播放时长
         */
        private Integer skipMinTime;
        /**
         * 激励视频预加载后的有效时间
         */
        private Integer preloadTtl;
        /**
         * 视频播放完后需要展示的endcard图片地址
         */
        private String endcardUrl;

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Integer getMimeType() {
            return mimeType;
        }

        public void setMimeType(Integer mimeType) {
            this.mimeType = mimeType;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public EventTrack[] getEventTracks() {
            return eventTracks;
        }

        public void setEventTracks(EventTrack[] eventTracks) {
            this.eventTracks = eventTracks;
        }

        public Integer getVideoType() {
            return videoType;
        }

        public void setVideoType(Integer videoType) {
            this.videoType = videoType;
        }

        public Integer getSkip() {
            return skip;
        }

        public void setSkip(Integer skip) {
            this.skip = skip;
        }

        public Integer getSkipMinTime() {
            return skipMinTime;
        }

        public void setSkipMinTime(Integer skipMinTime) {
            this.skipMinTime = skipMinTime;
        }

        public Integer getPreloadTtl() {
            return preloadTtl;
        }

        public void setPreloadTtl(Integer preloadTtl) {
            this.preloadTtl = preloadTtl;
        }

        public String getEndcardUrl() {
            return endcardUrl;
        }

        public void setEndcardUrl(String endcardUrl) {
            this.endcardUrl = endcardUrl;
        }
    }
    public static class EventTrack{
        /**
         * 事件类型
         */
        private Integer eventType;
        /**
         * 事件监测url链接列列表
         */
        private String[] eventTrackUrls;

        public Integer getEventType() {
            return eventType;
        }

        public void setEventType(Integer eventType) {
            this.eventType = eventType;
        }

        public String[] getEventTrackUrls() {
            return eventTrackUrls;
        }

        public void setEventTrackUrls(String[] eventTrackUrls) {
            this.eventTrackUrls = eventTrackUrls;
        }
    }
}

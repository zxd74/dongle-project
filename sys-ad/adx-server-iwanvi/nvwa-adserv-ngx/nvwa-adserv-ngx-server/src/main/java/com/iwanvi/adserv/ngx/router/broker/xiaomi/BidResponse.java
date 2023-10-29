package com.iwanvi.adserv.ngx.router.broker.xiaomi;

/**
 * @author: 郑晓东
 * @date: 2019-07-17 09:55
 * @version: v1.0
 * @Description: 米盟响应类
 */
public class BidResponse {
    /**
     * 错误码，0正常
     */
    private Integer code;
    /**
     * 返回的广告信息
     */
    private AdInfo[] adInfos;
    /**
     * 预先下发的广告素材信息，客户端提前缓存
     */
    private CacheAsset[] cacheAssets;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public AdInfo[] getAdInfos() {
        return adInfos;
    }

    public void setAdInfos(AdInfo[] adInfos) {
        this.adInfos = adInfos;
    }

    public CacheAsset[] getCacheAssets() {
        return cacheAssets;
    }

    public void setCacheAssets(CacheAsset[] cacheAssets) {
        this.cacheAssets = cacheAssets;
    }

    public static class AdInfo{

        /**
         * 广告唯一标示 id(必填)
         */
        private Long id;
        /**
         * 广告标题(必填)
         */
        private String title;
        /**
         * 广告描述语(必填)
         */
        private String summary;
        /**
         * 广告品牌
         */
        private String brand;
        /**
         * 广告素材物料列表(必填)
         */
        private Asset[] assets;
        /**
         * 广告标内容，非空必须展示，为空则不展示
         */
        private String adMark;
        /**
         * 广告形式，用来确定展示方式，(必填)
         */
        private Integer adStyle;
        /**
         * 广告交互类型，(必填)
         */
        private Integer targetType;
        /**
         * deeplink 地址
         */
        private String deeplink;
        /**
         * 渠道包标识
         */
        private String appChannel;
        /**
         * 广告点击目标 Url; 有可能包含 302 跳转
         */
        private String landingPageUrl;
        /**
         * 应用下载地址，只对应用下载类广告有效，
         */
        private String actionUrl;
        /**
         * 应用图标地址，只对应用下载类广告有效
         */
        private String iconUrl;
        /**
         * 应用包名，只对应用下载类广告有效
         */
        private String packageName;
        /**
         * 推广应用已经被多少人下载，只对应用下载类广告有效
         */
        private Integer totalDownloadNum;
        /**
         * 广告控制信息
         */
        private AdControl adControl;
        /**
         * 广告曝光监控地址，(必填)
         */
        private String[] viewMonitorUrls;
        /**
         * 广告点击监控地址，(必填)
         */
        private String[] clickMonitorUrls;
        /**
         * 广告跳过监控地址
         */
        private String[] skipMonitorUrls;
        /**
         * 广告开始下载监控地址
         */
        private String[] startDownloadMonitorUrls;
        /**
         * 广告下载完成监控地址
         */
        private String[] finishDownloadMonitorUrls;
        /**
         * 应用开始安装监控地址
         */
        private String[] startInstallMonitorUrls;
        /**
         * 应用安装成功监控地址
         */
        private String[] finishInstallMonitorUrls;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public Asset[] getAssets() {
            return assets;
        }

        public void setAssets(Asset[] assets) {
            this.assets = assets;
        }

        public String getAdMark() {
            return adMark;
        }

        public void setAdMark(String adMark) {
            this.adMark = adMark;
        }

        public Integer getAdStyle() {
            return adStyle;
        }

        public void setAdStyle(Integer adStyle) {
            this.adStyle = adStyle;
        }

        public Integer getTargetType() {
            return targetType;
        }

        public void setTargetType(Integer targetType) {
            this.targetType = targetType;
        }

        public String getDeeplink() {
            return deeplink;
        }

        public void setDeeplink(String deeplink) {
            this.deeplink = deeplink;
        }

        public String getAppChannel() {
            return appChannel;
        }

        public void setAppChannel(String appChannel) {
            this.appChannel = appChannel;
        }

        public String getLandingPageUrl() {
            return landingPageUrl;
        }

        public void setLandingPageUrl(String landingPageUrl) {
            this.landingPageUrl = landingPageUrl;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public Integer getTotalDownloadNum() {
            return totalDownloadNum;
        }

        public void setTotalDownloadNum(Integer totalDownloadNum) {
            this.totalDownloadNum = totalDownloadNum;
        }

        public AdControl getAdControl() {
            return adControl;
        }

        public void setAdControl(AdControl adControl) {
            this.adControl = adControl;
        }

        public String[] getViewMonitorUrls() {
            return viewMonitorUrls;
        }

        public void setViewMonitorUrls(String[] viewMonitorUrls) {
            this.viewMonitorUrls = viewMonitorUrls;
        }

        public String[] getClickMonitorUrls() {
            return clickMonitorUrls;
        }

        public void setClickMonitorUrls(String[] clickMonitorUrls) {
            this.clickMonitorUrls = clickMonitorUrls;
        }

        public String[] getSkipMonitorUrls() {
            return skipMonitorUrls;
        }

        public void setSkipMonitorUrls(String[] skipMonitorUrls) {
            this.skipMonitorUrls = skipMonitorUrls;
        }

        public String[] getStartDownloadMonitorUrls() {
            return startDownloadMonitorUrls;
        }

        public void setStartDownloadMonitorUrls(String[] startDownloadMonitorUrls) {
            this.startDownloadMonitorUrls = startDownloadMonitorUrls;
        }

        public String[] getFinishDownloadMonitorUrls() {
            return finishDownloadMonitorUrls;
        }

        public void setFinishDownloadMonitorUrls(String[] finishDownloadMonitorUrls) {
            this.finishDownloadMonitorUrls = finishDownloadMonitorUrls;
        }

        public String[] getStartInstallMonitorUrls() {
            return startInstallMonitorUrls;
        }

        public void setStartInstallMonitorUrls(String[] startInstallMonitorUrls) {
            this.startInstallMonitorUrls = startInstallMonitorUrls;
        }

        public String[] getFinishInstallMonitorUrls() {
            return finishInstallMonitorUrls;
        }

        public void setFinishInstallMonitorUrls(String[] finishInstallMonitorUrls) {
            this.finishInstallMonitorUrls = finishInstallMonitorUrls;
        }
    }

    public static class CacheAsset{
        /**
         * 素材地址，(必填)
         */
        private String url;
        /**
         * 素材内容的 md5 摘要，小写，客户端可用其对素材进行校验，(必填)
         */
        private String digest;
        /**
         * 素材类型，(必填)
         */
        private Integer materialType;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public Integer getMaterialType() {
            return materialType;
        }

        public void setMaterialType(Integer materialType) {
            this.materialType = materialType;
        }
    }

    public static class Asset{
        /**
         * 素材地址，(必填)
         */
        private String url;
        /**
         * 素材内容的 md5 摘要，小写，客户端可用其对素材进行校验，(必填)
         */
        private String digest;
        /**
         * 素材类型，(必填)
         */
        private Integer materialType;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public Integer getMaterialType() {
            return materialType;
        }

        public void setMaterialType(Integer materialType) {
            this.materialType = materialType;
        }
    }

    public static class AdControl{
        /**
         * 广告时长，从广告渲染成功并对用户可见起算，单位：毫秒
         */
        private Long duration;
        /**
         * 仅离线广告有效，广告有效性的起始时间，单位：毫秒
         */
        private Long startTimeInMills;
        /**
         * 仅离线广告有效，广告有效性的截止时间，单位：毫秒
         */
        private Long endTimeInMills;

        public Long getDuration() {
            return duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public Long getStartTimeInMills() {
            return startTimeInMills;
        }

        public void setStartTimeInMills(Long startTimeInMills) {
            this.startTimeInMills = startTimeInMills;
        }

        public Long getEndTimeInMills() {
            return endTimeInMills;
        }

        public void setEndTimeInMills(Long endTimeInMills) {
            this.endTimeInMills = endTimeInMills;
        }
    }
}

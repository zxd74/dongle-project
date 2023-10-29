package com.iwanvi.adserv.ngx.router.broker.zhihemobi;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: 郑晓东
 * @date: 2019-06-11 15:11
 * @version: v1.0
 * @Description: 智合移动响应类
 */
public class BidResponse {
    /**
     * 返回值，异常码，非 0 表示出错（数值参见 code 说明）
     */
    @JSONField(name = "error_code")
    private Integer errorCode;
    /**
     * 广告请求 ID
     */
    @JSONField(name = "request_id")
    private String requestId;
    /**
     * AD 对象的数组格式
     * 固定值“json”，返回 json 格式数据
     */
    private String ads;
    /**
     * 广告过期时间戳，单位秒，过期后展示无效
     */
    @JSONField(name = "expiration_time")
    private Long expirationTime;
    /**
     * 标记 key，当次请求的唯一 ID
     */
    @JSONField(name = "search_key")
    private String searchKey;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    /**
     * 广告Ad
     */
    public static final class Ad{
        /**
         * 广告位 ID
         */
        @JSONField(name = "adslot_id")
        private String adslotTd;
        /**
         * 对于支持 Html 或者 Js 的 App，如果返回了数据，则使用webview 渲染此内容，对应的 creative_type=5
         */
        @JSONField(name = "html_snippet")
        private String htmlSnippet;
        /**
         * 单次广告的唯一标识
         */
        @JSONField(name = "ad_key")
        private String adKey;
        /**
         * 事 件 跟 踪 对 象 列 表 （ app 下 载 和 视 频 时 启 用 。 当interaction_type=2 或 creative_type=4 时，需要判断是否有上报 url，如存在，必须进行上报，否则影响收益。）
         */
        @JSONField(name = "ad_trackings")
        private Tracking[] adTrackings;
        /**
         * 广告元素列表
         */
        @JSONField(name = "meta_groups")
        private MetaGroup[] metaGroups;
        /**
         * 广告法规定添加广告图标的图标地址
         */
        @JSONField(name = "mob_adtext")
        private String mobAdText;
        /**
         * 百度熊掌图标地址
         */
        @JSONField(name = "mob_adlogo")
        private String mobAdLogo;

        public String getAdslotTd() {
            return adslotTd;
        }

        public void setAdslotTd(String adslotTd) {
            this.adslotTd = adslotTd;
        }

        public String getHtmlSnippet() {
            return htmlSnippet;
        }

        public void setHtmlSnippet(String htmlSnippet) {
            this.htmlSnippet = htmlSnippet;
        }

        public String getAdKey() {
            return adKey;
        }

        public void setAdKey(String adKey) {
            this.adKey = adKey;
        }

        public Tracking[] getAdTrackings() {
            return adTrackings;
        }

        public void setAdTrackings(Tracking[] adTrackings) {
            this.adTrackings = adTrackings;
        }

        public MetaGroup[] getMetaGroups() {
            return metaGroups;
        }

        public void setMetaGroups(MetaGroup[] metaGroups) {
            this.metaGroups = metaGroups;
        }

        public String getMobAdText() {
            return mobAdText;
        }

        public void setMobAdText(String mobAdText) {
            this.mobAdText = mobAdText;
        }

        public String getMobAdLogo() {
            return mobAdLogo;
        }

        public void setMobAdLogo(String mobAdLogo) {
            this.mobAdLogo = mobAdLogo;
        }
    }

    /**
     * 事件
     */
    public static final class Tracking{
        /**
         * 监测项针对的元数据索引值
         */
        private Integer materialmetaIndex;
        /**
         * 事件类型
         */
        @JSONField(name = "trackingeventtype")
        private Integer trackingEventType;
        /**
         * 监测链接
         */
        @JSONField(name = "trackingurls")
        private String[] trackingUrls;
        /**
         * 针对 post 上报,该字段为请求体,存在宏 字段替换,请注意.
         */
        private String extdata;
        /**
         * 上报地址请求⽅方法类型 0:get请求 1:post请求 该值为0或者不不存在默认为 get 请求
         */
        private Integer methodtype;

        public Integer getMaterialmetaIndex() {
            return materialmetaIndex;
        }

        public void setMaterialmetaIndex(Integer materialmetaIndex) {
            this.materialmetaIndex = materialmetaIndex;
        }

        public Integer getTrackingEventType() {
            return trackingEventType;
        }

        public void setTrackingEventType(Integer trackingEventType) {
            this.trackingEventType = trackingEventType;
        }

        public String[] getTrackingUrls() {
            return trackingUrls;
        }

        public void setTrackingUrls(String[] trackingUrls) {
            this.trackingUrls = trackingUrls;
        }

        public String getExtdata() {
            return extdata;
        }

        public void setExtdata(String extdata) {
            this.extdata = extdata;
        }

        public Integer getMethodtype() {
            return methodtype;
        }

        public void setMethodtype(Integer methodtype) {
            this.methodtype = methodtype;
        }
    }

    /**
     * 广告元素
     */
    public static final class MetaGroup{
        /**
         * 创意类型
         */
        @JSONField(name = "creative_type")
        private Integer creativeType;
        /**
         * 交互类型
         */
        @JSONField(name = "interaction_type")
        private Integer interactionType;
        /**
         * url 的数组，用于广告展现后曝光通知的回调。如果存在多个回调地址，需要依依回调完成。如有遗漏会影响收益及数据。
         */
        @JSONField(name = "win_notice_urls")
        private String[] winNoticeUrls;
        /**
         * 点击行为跳转地址
         * 对于支持宏替换的广告位，需要进行宏替换
         *  经纬度: 无法获取时，需替换为-999;未替换：流量无收益; 替换为无效值（非数字格式或取值异常）：严重影响流量收益
         */
        @JSONField(name = "click_url")
        private String clickUrl;
        /**
         * 点击上报。 Array 格式
         * 对于支持宏替换的广告位，需要进行宏替换
         * 经纬度: 无法获取时，需替换为-999;未替换：流量无收益; 替换为无效值（非数字格式或取值异常）：严重影响流量收益
         */
        @JSONField(name = "click_track")
        private String[] clickTrack;
        /**
         * 广告推广标题
         */
        @JSONField(name = "ad_title")
        private String adTitle;
        /**
         * 广告文本展示文案
         */
        private String[] descriptions;
        /**
         * 广告图标地址，单个广告可能存在多个广告图标
         */
        @JSONField(name = "icon_srcs")
        private String[] iconSrcs;
        /**
         * 广告图片地址，单个广告可能存在多个图片地址
         */
        @JSONField(name = "image_srcs")
        private String[] imageSrcs;
        /**
         * 下载类应用包名
         */
        @JSONField(name = "app_package")
        private String appPackage;
        /**
         * 下载类应用的大小
         */
        @JSONField(name = "app_size")
        private Integer appSize;
        /**
         * 广告品牌名称，下载类为 app 名，非下载类为推广品牌名
         */
        @JSONField(name = "brand_name")
        private String brandName;
        /**
         * 视频广告物料地址
         */
        @JSONField(name = "video_url")
        private String videoUrl;
        /**
         * 视频广告播放长度，单位秒。 仅视频广告此字段返回
         */
        @JSONField(name = "video_duration")
        private Integer videoDuration;
        /**
         * 视频强制观看预估时长，单位秒。 仅视频广告此字段返回
         */
        @JSONField(name = "video_keep_duration")
        private Integer videoKeepDuration;
        /**
         * 封⾯图，激励视频请⽤于渲染 endcard
         */
        @JSONField(name = "video_cover_url")
        private String videoCoverUrl;
        /**
         * cover 宽，单位像素
         */
        @JSONField(name = "video_cover_width")
        private Integer videoCoverWidth;
        /**
         * cover⾼，单位像素
         */
        @JSONField(name = "video_cover_height")
        private Integer videoCoverheight;
        /**
         * 当是信息流视频时，视频暂停，视频下方显示的广告文字（或视频下按钮文字）
         */
        @JSONField(name = "video_ad_text")
        private Integer videoAdText;
        /**
         * app 端 deeplink 链接，若用户端调用失败将使用click_url 字段打开落地页
         */
        private String deeplink;
        /**
         * 广告元数据组索引结构。一条广告可能包含多个物料元信息,
         * 我们统称这些元信息为广告元数据组。
         * 返回广告时
         *      total_num 表 明 当 前 广 告 包 含 的 物 料 元 数 据 个 数 ，
         *      current_index 表明当前的物料元数据在元数据组中的索引。
         * 请求多个广告返回时，
         *      ad_key 唯一标识一个广告元数据组(一个广告)，
         *      MetaIndex 标识一个元数据组中的每个元数据信息。
         */
        @JSONField(name = "meta_index")
        private MetaIndex metaIndex;
        /**
         * 物料的宽度，
         *      如果是图片，标识图片的宽度；
         *      如果是视频（含有视频截图），则为视频宽度；
         *      如果是图文或文本，则不会填充此字段。
         */
        @JSONField(name = "material_width")
        private Integer materialWidth;
        /**
         * 物料的高度，
         *      如果是图片，标识图片的高度；
         *      如果是视频（含有视频截图），则为视频高度；
         *      如果是图文或文本，则不会填充此字段。
         */
        @JSONField(name = "material_height")
        private Integer materialHeight;

        public Integer getCreativeType() {
            return creativeType;
        }

        public void setCreativeType(Integer creativeType) {
            this.creativeType = creativeType;
        }

        public Integer getInteractionType() {
            return interactionType;
        }

        public void setInteractionType(Integer interactionType) {
            this.interactionType = interactionType;
        }

        public String[] getWinNoticeUrls() {
            return winNoticeUrls;
        }

        public void setWinNoticeUrls(String[] winNoticeUrls) {
            this.winNoticeUrls = winNoticeUrls;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }

        public String[] getClickTrack() {
            return clickTrack;
        }

        public void setClickTrack(String[] clickTrack) {
            this.clickTrack = clickTrack;
        }

        public String getAdTitle() {
            return adTitle;
        }

        public void setAdTitle(String adTitle) {
            this.adTitle = adTitle;
        }

        public String[] getDescriptions() {
            return descriptions;
        }

        public void setDescriptions(String[] descriptions) {
            this.descriptions = descriptions;
        }

        public String[] getIconSrcs() {
            return iconSrcs;
        }

        public void setIconSrcs(String[] iconSrcs) {
            this.iconSrcs = iconSrcs;
        }

        public String[] getImageSrcs() {
            return imageSrcs;
        }

        public void setImageSrcs(String[] imageSrcs) {
            this.imageSrcs = imageSrcs;
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

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
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

        public Integer getVideoKeepDuration() {
            return videoKeepDuration;
        }

        public void setVideoKeepDuration(Integer videoKeepDuration) {
            this.videoKeepDuration = videoKeepDuration;
        }

        public String getVideoCoverUrl() {
            return videoCoverUrl;
        }

        public void setVideoCoverUrl(String videoCoverUrl) {
            this.videoCoverUrl = videoCoverUrl;
        }

        public Integer getVideoCoverWidth() {
            return videoCoverWidth;
        }

        public void setVideoCoverWidth(Integer videoCoverWidth) {
            this.videoCoverWidth = videoCoverWidth;
        }

        public Integer getVideoCoverheight() {
            return videoCoverheight;
        }

        public void setVideoCoverheight(Integer videoCoverheight) {
            this.videoCoverheight = videoCoverheight;
        }

        public Integer getVideoAdText() {
            return videoAdText;
        }

        public void setVideoAdText(Integer videoAdText) {
            this.videoAdText = videoAdText;
        }

        public String getDeeplink() {
            return deeplink;
        }

        public void setDeeplink(String deeplink) {
            this.deeplink = deeplink;
        }

        public MetaIndex getMetaIndex() {
            return metaIndex;
        }

        public void setMetaIndex(MetaIndex metaIndex) {
            this.metaIndex = metaIndex;
        }

        public Integer getMaterialWidth() {
            return materialWidth;
        }

        public void setMaterialWidth(Integer materialWidth) {
            this.materialWidth = materialWidth;
        }

        public Integer getMaterialHeight() {
            return materialHeight;
        }

        public void setMaterialHeight(Integer materialHeight) {
            this.materialHeight = materialHeight;
        }
    }

    /**
     * 广告元数据组索引结构
     */
    public static class MetaIndex{
        /**
         *  当 前 广 告 包 含 的 物 料 元 数 据 个 数
         */
        @JSONField(name = "total_num")
        private Integer totalNum;
        /**
         * 当前的物料元数据在元数据组中的索引
         */
        @JSONField(name = "current_index")
        private Integer currentIndex;

        public Integer getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(Integer totalNum) {
            this.totalNum = totalNum;
        }

        public Integer getCurrentIndex() {
            return currentIndex;
        }

        public void setCurrentIndex(Integer currentIndex) {
            this.currentIndex = currentIndex;
        }
    }

    /**
     * 创意类型: 0:无创意广告
     * 1:纯文字广告
     * 2:纯图片广告
     * 3:图文广告
     * 4:视频广告
     * 5:Html（对应 html_snippet ）
     * 6:互动、动画（gif）
     * 7:原生
     * 100:推广图标广告
     */
    public enum CreativeType{
        NO(0),TEXT(1),IMAGE(2),IMAGE_TEXT(3),VIDEO(4),HTML(5),GIF(6),NATIVE(7),ICON(100);
        private Integer value;
        CreativeType(Integer value){this.value = value;}
        public Integer getValue(){return value;}
    }

    /**
     * 交互类型
     * 0:无动作
     * 1 使用浏览器打开网页
     * 2:下载应用（需要解析 ad_trackings ）
     * 3:拨打电话
     * 4:发送短信
     * 5:发送邮件
     * 6:根据 deeplink 打开应用
     * 99:广点通，下载类，需要处理广点通宏替换 （需要解析ad_trackings ）
     * 100:未知
     */
    public enum InteractionType{
        NO(0),BROWSER(1),DOWNLOAD(2),CALL(3),MESSAGE(4),MAIL(5),DEEP_LINK(6),GDT_DOWNLOAD(99),UNKNOWN(100);
        private Integer value;
        InteractionType(Integer value){this.value = value;}
        public Integer getValue(){return value;}
    }

    /**
     * 事件类型
     * 100:视频开始播放
     * 101:视频全屏
     * 102:视频播放结束
     * 103:点击预览图播放视频
     * 104:视频静音 VIDEO_SILENCE
     * 105:视频静音后又开始声音   VIDEO_SILENCE_VOICE
     * 106:视频中途关闭
     * 107:视频广告跳过
     * 108:视频加载成功
     * 109:视频暂停播放
     * 110:视频重新播放
     * 111:视频退出全屏
     * 112:上滑事件 （视频）
     * 113:下滑事件 （视频）
     * 1000:开始下载推广 App （需要进行宏替换，详见附录 2）
     * 1001:下载完成推广 App（需要进行宏替换，详见附录 2）
     * 1002:开始安装推广 App（需要进行宏替换，详见附录 2）
     * 1003:安装完成推广 App（需要进行宏替换，详见附录 2）
     * 1004:激活推广 App
     * 10000:deeplink 被点击
     */
    public enum TrackingEventType{
        VIDEO_START(100),VIDEO_FULL_SCREEN(101),VIDEO_END(102),VIDEO_COVER_CLICK(103),VIDEO_SILENCE(104),VIDEO_SILENCE_VOICE(105),
        VIDEO_CLOSE(106),VIDEO_SKIP(107),VIDEO_LOAD_SUCCESS(108),VIDEO_PAUSE(109),VIDEO_REPLAY(110),VIDEO_EXIT(111),
        UP_GLIDE(112),DOWN_GLIDE(113),DOWNLOAD_START(1000),DOWNLOAD_END(1001),INSTALL_START(1002),INSTALL_END(1003),
        INSTALL_ACTIVE(1004),DEEP_LINK_CLICK(10000);
        private Integer value;
        TrackingEventType(Integer value){this.value = value;}
        public Integer getValue(){return value;}
    }
}

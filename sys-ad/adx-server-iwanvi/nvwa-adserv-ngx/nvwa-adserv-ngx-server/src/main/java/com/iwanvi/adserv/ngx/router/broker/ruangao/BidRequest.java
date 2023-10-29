package com.iwanvi.adserv.ngx.router.broker.ruangao;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-05-05 11:40
 * @version: v1.0
 * @Description: 软告云请求数据结构
 */
public class BidRequest {

    /**
     * bid request 唯一ID
     * 必须
     */
    private String id;
    /**
     * 对接接口协议本版本号
     * 必须
     */
    private String version;
    /**
     * Site对象，描述媒体站点信息
     * 必须
     */
    private Site site;
    /**
     * Impression对象，仅包含一个Impression对象，描述广告位
     * 必须
     */
    private Imp imp;
    /**
     * App对象，描述App信息
     * 必须（移动端）
     */
    private App app;
    /**
     * Device对象，描述设备信息
     * 必须
     */
    private Device device;
    /**
     * User对象，描述用户信息
     * 必须（PC端）
     */
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Imp getImp() {
        return imp;
    }

    public void setImp(Imp imp) {
        this.imp = imp;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BidRequest(){}

    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this.id = builder.id;
        this.version = builder.version;
        this.site = builder.site;
        this.imp = builder.imp;
        this.app = builder.app;
        this.device = builder.device;
        this.user = builder.user;
    }

    @Generated("SparkTools")
    public static Builder builder(){
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder{
        private String id;
        private String version;
        private Site site;
        private Imp imp;
        private App app;
        private Device device;
        private User user;

        public Builder withId(String id){
            this.id = id;
            return this;
        }
        public Builder withVersion(String version){
            this.version = version;
            return this;
        }
        public Builder withSite(Site site){
            this.site = site;
            return this;
        }
        public Builder withImp(Imp imp){
            this.imp = imp;
            return this;
        }
        public Builder withApp(App app){
            this.app = app;
            return this;
        }
        public Builder withDevice(Device device){
            this.device = device;
            return this;
        }
        public Builder withUser(User user){
            this.user = user;
            return this;
        }

        public Builder(){}

        public BidRequest build(){return new BidRequest(this);}
    }

    /**
     * Site对象，描述媒体站点信息
     */
    public static class Site{
        /**
         * 媒体ID，由软告提供
         * 必须
         */
        private String id;
        /**
         * 媒体名称
         * 必须
         */
        private String name;
        /**
         * 当前页面URL
         * 必须（PC端）
         */
        private String page;
        /**
         * 广告位所在页面的referer
         * 必须（PC端）
         */
        private String ref;
        /**
         * 视频的内容相关信息，只有视频贴片类型的广告位才有该字段
         * 可选
         */
        private Content content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public Site(){}

        @Generated("SparkTools")
        private Site(Builder builder){
            this.id = builder.id;
            this.name = builder.name;
            this.page = builder.page;
            this.ref = builder.ref;
            this.content = builder.content;
        }

        @Generated("SparkTools")
        public static Builder builder(){
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String id;
            private String name;
            private String page;
            private String ref;
            private Content content;

            public Builder withId(String id){
                this.id = id;
                return this;
            }
            public Builder withName(String name){
                this.name = name;
                return this;
            }
            public Builder withPage(String page){
                this.page = page;
                return this;
            }
            public Builder withRef(String ref){
                this.ref = ref;
                return this;
            }
            public Builder withContent(Content content){
                this.content = content;
                return this;
            }

            public Builder(){}

            public Site build(){return new Site(this);}
        }
    }

    /**
     * Impression对象，仅包含一个Impression对象，描述广告位
     */
    public static class Imp{

        /**
         * Imp对象的唯一ID
         * 必须
         */
        private String id;
        /**
         * 广告位ID，由软告提供
         * 必须
         */
        @JSONField(name = "tagid")
        private String tagId;
        /**
         * banner类型的广告位（banner和信息流都使用此对象）
         * 必须（Banner和信息流类型）
         */
        private Banner banner;
        /**
         * video类型的广告位
         * 必须（Video类型）
         */
        private Video video;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public Banner getBanner() {
            return banner;
        }

        public void setBanner(Banner banner) {
            this.banner = banner;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public Imp(){}

        @Generated("SparkTools")
        private Imp(Builder builder){
            this.id = builder.id;
            this.tagId = builder.tagId;
            this.banner = builder.banner;
            this.video = builder.video;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private String id;
            private String tagId;
            private Banner banner;
            private Video video;

            public Builder withId(String id){
                this.id = id;
                return this;
            }
            public Builder withTagId(String tagId){
                this.tagId = tagId;
                return this;
            }
            public Builder withBanner(Banner banner){
                this.banner = banner;
                return this;
            }
            public Builder withVideo(Video video){
                this.video = video;
                return this;
            }
            public Builder(){}

            public Imp build(){return new Imp(this);}
        }
    }

    /**
     * App对象，描述App信息
     */
    public static class App{
        /**
         * App唯一ID，填app包名
         * 必须
         */
        private String id;
        /**
         * App名称
         * 必须
         */
        private String name;
        /**
         * App版本
         * 必须
         */
        private String ver;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public App(){}

        @Generated("SparkTools")
        private App(Builder builder){
            this.id = builder.id;
            this.name = builder.name;
            this.ver = builder.ver;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private String id;
            private String name;
            private String ver;

            public Builder withId(String id){
                this.id = id;
                return this;
            }
            public Builder withName(String name){
                this.name = name;
                return this;
            }
            public Builder withVer(String ver){
                this.ver = ver;
                return this;
            }
            public Builder(){}

            public App build(){return new App(this);}
        }
    }

    /**
     * Device对象，描述设备信息
     */
    public static class Device{

        /**
         * 安卓设备填安卓ID或imei，优先安卓ID；ios设备填idfa
         * 移动端必须
         */
        private String id;
        /**
         * IMEI（android）或IDFA（iphone,ipad）
         * 移动端必须
         */
        @JSONField(name = "dpid")
        private String dpId;
        /**
         * 设备浏览器的User-Agent字符串
         * 必须
         */
        private String ua;
        /**
         * IP地址
         * 必须
         */
        private String ip;
        /**
         * 设备使用的运营商
         * 可选
         */
        private String carrier;
        /**
         * 设备型号
         * 必须
         */
        private String model;
        /**
         * 设备操作系统
         * 必须
         */
        private String os;
        /**
         * 设备操作系统版本号
         * 必须
         */
        private String osv;
        /**
         * 设备联网方式，0=无；1=wifi；2=2/3/4G
         * 必须
         */
        @JSONField(name = "connectiontype")
        private Integer connectionType;
        /**
         * 设 备 类 型 ： PC 端 为 0; 1=iPhone ； 2=iPad ；3=android；4=android_pad
         *
         * 必须
         */
        @JSONField(name = "devicetype")
        private Integer deviceType;
        /**
         * Mac地址
         * 必须
         */
        private String mac;
        /**
         * 用户当前位置信息（此字段尽量传，可提升填充率）
         * 可选
         */
        private Geo geo;
        /**
         * 屏幕分辨率高度，单位像素
         * 必须
         */
        private Integer w;
        /**
         * 屏幕分辨率高度，单位像素
         * 必须
         */
        private Integer h;
        /**
         * 横竖屏状态， 0-未知， 1-竖屏， 2-横屏
         * 可选
         */
        @JSONField(name = "screenorientation")
        private Integer screenorIentation;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDpId() {
            return dpId;
        }

        public void setDpId(String dpId) {
            this.dpId = dpId;
        }

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOsv() {
            return osv;
        }

        public void setOsv(String osv) {
            this.osv = osv;
        }

        public Integer getConnectionType() {
            return connectionType;
        }

        public void setConnectionType(Integer connectionType) {
            this.connectionType = connectionType;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
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

        public Integer getScreenorIentation() {
            return screenorIentation;
        }

        public void setScreenorIentation(Integer screenorIentation) {
            this.screenorIentation = screenorIentation;
        }

        public Device(){}

        @Generated("SparkTools")
        private Device(Builder builder){
            this.id = builder.id;
            this.dpId = builder.dpId;
            this.ua = builder.ua;
            this.ip = builder.ip;
            this.carrier = builder.carrier;
            this.model = builder.model;
            this.os = builder.os;
            this.osv = builder.osv;
            this.connectionType = builder.connectionType;
            this.deviceType = builder.deviceType;
            this.mac = builder.mac;
            this.geo = builder.geo;
            this.w = builder.w;
            this.h = builder.h;
            this.screenorIentation = builder.screenorIentation;
        }

        @Generated("SparkTools")
        public static Builder builder(){
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String id;
            @JSONField(name = "dpid")
            private String dpId;
            private String ua;
            private String ip;
            private String carrier;
            private String model;
            private String os;
            private String osv;
            @JSONField(name = "connectiontype")
            private Integer connectionType;
            @JSONField(name = "devicetype")
            private Integer deviceType;
            private String mac;
            private Geo geo;
            private Integer w;
            private Integer h;
            @JSONField(name = "screenorientation")
            private Integer screenorIentation;

            public Builder withId(String id){
                this.id = id;
                return this;
            }
            public Builder withdpId(String dpId){
                this.dpId = dpId;
                return this;
            }
            public Builder withUa(String ua){
                this.ua = ua;
                return this;
            }
            public Builder withIp(String ip){
                this.ip = ip;
                return this;
            }
            public Builder withCarrier(String carrier){
                this.carrier = carrier;
                return this;
            }
            public Builder withModel(String model){
                this.model = model;
                return this;
            }
            public Builder withOs(String os){
                this.os = os;
                return this;
            }
            public Builder withOsv(String osv){
                this.osv = osv;
                return this;
            }
            public Builder withConnectionType(Integer connectionType){
                this.connectionType = connectionType;
                return this;
            }
            public Builder withDeviceType(Integer deviceType){
                this.deviceType = deviceType;
                return this;
            }
            public Builder withMac(String mac){
                this.mac = mac;
                return this;
            }
            public Builder withGeo(Geo geo){
                this.geo = geo;
                return this;
            }
            public Builder withWidth(Integer w){
                this.w = w;
                return this;
            }
            public Builder withHeight(Integer h){
                this.h = h;
                return this;
            }

            public Builder(){}

            public Device build(){return new Device(this);}
        }
    }

    /**
     * User对象，描述用户信息
     */
    public static class User{

        /**
         * 用户Cookie
         */
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public User(){}

        @Generated("SparkTools")
        private User(Builder builder){
            this.id = builder.id;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private String id;

            public Builder withId(String id){
                this.id = id;
                return this;
            }

            public Builder(){}

            public User build(){return new User(this);}
        }
    }

    /**
     * 视频的内容相关信息，只有视频贴片类型的广告位才有
     */
    public static class Content{
        /**
         * 视频ID
         * 可选
         */
        private String id;
        /**
         * 视频标题名称
         * 可选
         */
        private String title;
        /**
         * 视频链接
         * 可选
         */
        private String url;
        /**
         * 视频时长
         * 可选
         */
        private Integer len;
        /**
         * 视频标签关键字，如果由多个，逗号分隔
         * 可选
         */
        private String keywords;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getLen() {
            return len;
        }

        public void setLen(Integer len) {
            this.len = len;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public Content(){}

        @Generated("SparkTools")
        private Content(Builder builder){
            this.id = builder.id;
            this.title = builder.title;
            this.url = builder.url;
            this.len = builder.len;
            this.keywords = builder.keywords;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private String id;
            private String title;
            private String url;
            private Integer len;
            private String keywords;

            public Builder withId(String id){
                this.id = id;
                return this;
            }
            public Builder withTitle(String title){
                this.title = title;
                return this;
            }
            public Builder withUrl(String url){
                this.url = url;
                return this;
            }
            public Builder withLen(Integer len){
                this.len = len;
                return this;
            }
            public Builder withKeywords(String keywords){
                this.keywords = keywords;
                return this;
            }

            public Builder(){}

            public Content build(){return new Content(this);}

        }
    }

    /**
     * banner类型的广告位（banner和信息流都使用此对象）
     */
    public static class Banner{
        /**
         * 广告位宽度
         * 必须
         */
        private Integer w;
        /**
         * 广告位高度
         * 必须
         */
        private Integer h;
        /**
         * 广告位位置
         * 必须
         */
        private Integer pos;

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

        public Integer getPos() {
            return pos;
        }

        public void setPos(Integer pos) {
            this.pos = pos;
        }

        public Banner(){}

        @Generated("SparkTools")
        public Banner(Builder builder){
            this.w = builder.w;
            this.h = builder.h;
            this.pos = builder.pos;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private Integer w;
            private Integer h;
            private Integer pos;

            public Builder withWidth(Integer w){
                this.w = w;
                return this;
            }
            public Builder withHeight(Integer h){
                this.h = h;
                return this;
            }
            public Builder withPos(Integer pos){
                this.pos = pos;
                return this;
            }

            public Builder(){}

            public Banner build(){return new Banner(this);}
        }
    }

    /**
     * video类型的广告位
     */
    public static class Video{
        /**
         * 广告位宽度
         * 必须
         */
        private Integer w;
        /**
         * 广告位高度
         * 必须
         */
        private Integer h;
        /**
         * 广告位位置
         * 必须
         */
        private Integer pos;
        /**
         * 视频类广告最短播放时长，单位是秒
         * 必须
         */
        private Integer minduration;
        /**
         * 视频类广告最长播放时长，单位是秒
         * 必须
         */
        private Integer maxduration;

        public Video(){}

        @Generated("SparkTools")
        public Video(Builder builder){
            this.w = builder.w;
            this.h = builder.h;
            this.pos = builder.pos;
            this.maxduration = builder.maxduration;
            this.minduration = builder.minduration;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private Integer w;
            private Integer h;
            private Integer pos;
            private Integer minduration;
            private Integer maxduration;

            public Builder withWidth(Integer w){
                this.w = w;
                return this;
            }
            public Builder withHeight(Integer h){
                this.h = h;
                return this;
            }
            public Builder withPos(Integer pos){
                this.pos = pos;
                return this;
            }
            public Builder withMinduration(Integer minduration){
                this.minduration = minduration;
                return this;
            }
            public Builder withMaxduration(Integer maxduration){
                this.maxduration = maxduration;
                return this;
            }

            public Builder(){}

            public Video build(){return new Video(this);}
        }
    }

    /**
     * 用户当前位置信息
     */
    public static class Geo{
        /**
         * 维度
         * 可选
         */
        private float lat;
        /**
         * 经度
         * 可选
         */
        private float lon;
        /**
         * 城市识别码
         * 可选
         */
        private String country;

        public Geo(){}

        @Generated("SparkTools")
        private Geo(Builder builder){
            this.lat = builder.lat;
            this.lon = builder.lon;
            this.country = builder.country;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private float lat;
            private float lon;
            private String country;

            public Builder withLat(float lat){
                this.lat = lat;
                return this;
            }
            public Builder withLon(float lon){
                this.lon = lon;
                return this;
            }
            public Builder withCountry(String country){
                this.country = country;
                return this;
            }
            public Builder(){}

            public Geo build(){return new Geo(this);}
        }
    }

    /**
     * 运营商ID
     * 46000 中国移动 （GSM）== CHINA_MOBILE
     * 46001 中国联通 （GSM）== CHINA_UNICOM
     * 46002 中国移动 （TD-S）
     * 46003 中国电信（CDMA）== CHINA_TELECOM
     * 46004 空（似乎是专门用来做测试的）== UNKNOWN
     * 46005 中国电信 （CDMA）
     * 46006 中国联通 （WCDMA）
     * 46007 中国移动 （TD-S）
     * 46011 中国电信 （FDD-LTE）
     */
    public enum Carrier{
        CHINA_MOBILE(46000),CHINA_UNICOM(46001),CHINA_TELECOM(46003),UNKNOWN(46004);
        private Integer value;
        Carrier(Integer value){
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 设备联网方式，0=无；1=wifi；2=2/3/4G
     * NONE == 0 无
     * WIFI == 1
     * NET == 2
     */
    public enum ConnectionType{
        NONE(0),WIFI(1),NET(2);
        private Integer value;
        ConnectionType(Integer value){
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 设备类型
     * PC == 0 PC端
     * IPHONE == 1
     * IPAD == 2
     * ANDROID == 3
     * ANDROID_PAD == 4
     */
    public enum DeviceType{
        PC(0),IPHONE(1),IPAD(2),ANDROID(3),ANDROID_PAD(4);
        private Integer value;
        DeviceType(Integer value){
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 横竖屏状态
     * UNKNOWN == 0 未知
     * PORTRAIT == 1 竖屏
     * LANDSCAPE == 横屏
     */
    public enum ScreenorIentation{
        UNKNOWN(0),PORTRAIT(1),LANDSCAPE(2);
        private Integer value;
        ScreenorIentation(Integer value){
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 广告位位置
     *      PC_DEFAULT == 0  PC端默认位置
     *      CHANNEL_STREAM == 4 频道页信息流
     *      FOCUS == 6 焦点图
     *      FIRST_SCREEN == 7 开屏
     *      PRE_PATCH == 8 前贴
     *      FEED == 9 feed流
     *      CAROUSEL == 10 轮播
     *      INTERSTITIAL == 11 插屏
     *      MID_PATCH == 12 中贴
     *      BACK_PATCH == 13 后贴
     *      PAUSE == 14 暂停
     *      FLOAT_LAYER == 15 浮层
     *      HUGE_SCREEN == 16 巨幕
     *      BANNER == 17 通栏
     *      ICON == 18 图标
     *      LIVE == 19 直播
     *      CONTENT_HEAD_BANNER == 20 内容页头部Banner
     *      CONTENT_MIDDLE_BANNER == 21 内容页中部Banner
     *      CONTENT_FOOT_BANNER == 22 内容也底部Banner
     *      VIDEO_HEAD_BANNER == 23 视频播放页头部Banner
     *      VIDEO_MIDDLE_BANNER == 24 视频播放页中部Banner
     *      VIDEO_FOOT_BANNER == 25 视频播放页底部Banner
     *      CHANNEL_FOCUS == 26 频道页焦点图
     *      HOME_STREAM == 27 首页信息流
     *      HOME_HEAD_BANNER == 28  首页头部Banner
     *      HOME_FOOT_BANNER == 29 首页底部 banner
     *      QUERY_HEAD_BANNER == 30 搜索结果页头部 banner
     *      QUERY_MIDDLE_BANNER == 31 搜索结果页中部 banner
     *      QUERY_FOOT_BANNER == 32 搜索结果页底部 banner
     *      QUERY_STREAM == 33 搜索结果页信息流
     */
    public enum Pos{
        PC_DEFAULT(0),CHANNEL_STREAM(4),FOCUS(6),FIRST_SCREEN(7),PRE_PATCH(8),FEED(9),CAROUSEL(10),INTERSTITIAL(11),MID_PATCH(12),
        BACK_PATCH(13),PAUSE(14),FLOAT_LAYER(15),HUGE_SCREEN(16),BANNER(17),ICON(18),LIVE(19),CONTENT_HEAD_BANNER(20),
        CONTENT_MIDDLE_BANNER(21),CONTENT_FOOT_BANNER(22),VIDEO_HEAD_BANNER(23),VIDEO_MIDDLE_BANNER(24),VIDEO_FOOT_BANNER(25),CHANNEL_FOCUS(26),
        HOME_STREAM(27),HOME_HEAD_BANNER(28),HOME_FOOT_BANNER(29),QUERY_HEAD_BANNER(30),QUERY_MIDDLE_BANNER(31),QUERY_FOOT_BANNER(32),QUERY_STREAM(33);
        private Integer value;
        Pos(Integer value){
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 广告素材类型
     *      PIC == 0 图片素材（jpg、png、jpeg 和 gif 格式）
     *      VIDEO == 0 视频广告（mp4 和 flv 格式）
     *      FLASH == 2
     *      H5 == 4
     */
    public enum AdType{
        PIC(0),VIDEO(1),FLASH(2),H5(4);
        private Integer value;

        AdType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}

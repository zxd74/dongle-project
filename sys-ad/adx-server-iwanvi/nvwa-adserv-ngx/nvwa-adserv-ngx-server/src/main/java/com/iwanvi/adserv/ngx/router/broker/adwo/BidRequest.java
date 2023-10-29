package com.iwanvi.adserv.ngx.router.broker.adwo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-06-04 18:31
 * @version: v1.0
 * @Description: 安沃 BidRequest 请求类
 */
public class BidRequest {

    /**
     * 对外称为广告位 ID (ADWO 分配的 ID，32 位)
     * 必填
     */
    private String pid;
    /**
     * 广告信息返回格式 （3、feeds 4、video）
     * 必填
     */
    private Integer format;
    /**
     * 请求广告位的 width（当 forma=4 时此字段必填）
     * 选填
     */
    private Integer w;
    /**
     * 请求广告位的 height（当 forma=4 时此字段必填）
     * 选填
     */
    private Integer h;
    /**
     * 程序信息
     * 必填
     */
    private App app;
    /**
     * 设备信息
     * 必填
     */
    private Device dev;
    /**
     * 当 format=3 时必填
     * 选填
     */
    private Feed feeds;
    /**
     * 标签 id
     * 需要有标签关联的广告时传递，另外需要提供标签表（传了该字段值则只有与之对应的广告才会下发）
     * 选填
     */
    private Integer tagid;
    /**
     * 是否返回 https 资源
     * 1 返回 0 返回 http
     * 图片资源、统计接口
     * 选填
     */
    private Integer ihttps;
    /**
     * 经纬度对象
     * 选填
     */
    private Geo geo;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Device getDev() {
        return dev;
    }

    public void setDev(Device dev) {
        this.dev = dev;
    }

    public Feed getFeeds() {
        return feeds;
    }

    public void setFeeds(Feed feeds) {
        this.feeds = feeds;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public Integer getIhttps() {
        return ihttps;
    }

    public void setIhttps(Integer ihttps) {
        this.ihttps = ihttps;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public BidRequest(){}
    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this.pid = builder.pid;
        this.format = builder.format;
        this.w = builder.w;
        this.h = builder.h;
        this.app = builder.app;
        this.dev = builder.dev;
        this.feeds = builder.feeds;
        this.tagid = builder.tagid;
        this.ihttps = builder.ihttps;
        this.geo = builder.geo;
    }
    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}
    @Generated("SparkTools")
    public static final class Builder{
        private String pid;
        private Integer format;
        private Integer w;
        private Integer h;
        private App app;
        private Device dev;
        private Feed feeds;
        private Integer tagid;
        private Integer ihttps;
        private Geo geo;

        public Builder withPid(String pid){
            this.pid = pid;
            return this;
        }
        public Builder withFormat(Integer format){
            this.format = format;
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
        public Builder withApp(App app){
            this.app = app;
            return this;
        }
        public Builder withDevice(Device dev){
            this.dev = dev;
            return this;
        }
        public Builder withFeed(Feed feeds){
            this.feeds = feeds;
            return this;
        }
        public Builder withTagId(Integer tagid){
            this.tagid = tagid;
            return this;
        }
        public Builder withIHttps(Integer ihttps){
            this.ihttps = ihttps;
            return this;
        }
        public Builder withGeo(Geo geo){
            this.geo = geo;
            return this;
        }
        public Builder(){}
        public BidRequest build(){return new BidRequest(this);}
    }

    /**
     *   应用 app  对象
     */
    public static class App{
        /**
         * App 名字
         * 选填
         */
        private String name;
        /**
         * App 包名，IOS位BundleID
         * 必填
         */
        private String pn;
        /**
         * App 版本
         * 必填
         */
        private String ver;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPn() {
            return pn;
        }

        public void setPn(String pn) {
            this.pn = pn;
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
            this.name = builder.name;
            this.pn = builder.pn;
            this.ver = builder.ver;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String name;
            private String pn;
            private String ver;

            public Builder withName(String name){
                this.name = name;
                return this;
            }
            public Builder withPackage(String pn){
                this.pn = pn;
                return this;
            }
            public Builder withVersion(String ver){
                this.ver = ver;
                return this;
            }

            public Builder(){}
            public App build(){return new App(this);}
        }
    }

    /**
     * 设备对象
     */
    public static class Device{
        /**
         * 用户手机 IP 地址（公网 IP）
         * 必填
         */
        private String ip;
        /**
         * Android 设备的 imei
         * Android 时，2 个参数至少有 1个
         * 必填
         */
        private String imei;
        /**
         * Android 设备的 Androidid
         * Android 时，2 个参数至少有 1个
         * 必填
         */
        private String androidid;
        /**
         * IOS idfa
         * iOS 时至少有 1 个，当操作系统为 6.0 以上必选为 IDFA
         * 必填
         */
        private String idfa;
        /**
         * MAC
         * iOS 时至少有 1 个，当操作系统为 6.0 以上必选为 IDFA
         * 必填
         */
        private String mac;
        /**
         * iOS 设备屏幕的 scale
         * Android 设备屏幕的密度
         * 必填
         */
        private Float s;
        /**
         * 设备的 UserAgent
         * UrlEncode 编码
         * 选填
         */
        private String ua;
        /**
         * 网络类型
         *      1 代表为 WIFI
         *      0 代表不是 WIFI
         * 必填
         */
        private Integer net;
        /**
         * 系统版本
         * 必填
         */
        private String osv;
        /**
         * 设备厂商
         * UrlEncode 编码
         * 选填
         */
        private String manu;
        /**
         * 设备型号
         * UrlEncode 编码
         * 必填
         */
        private String bn;
        /**
         * 运营商
         * 选填
         */
        @JSONField(name = "mcc_mnc")
        private String mccMnc;
        /**
         * 屏幕分辨率的 width
         * 必填
         */
        private Integer sw;
        /**
         * 屏幕分辨率的 height
         * 必填
         */
        private Integer sh;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getAndroidid() {
            return androidid;
        }

        public void setAndroidid(String androidid) {
            this.androidid = androidid;
        }

        public String getIdfa() {
            return idfa;
        }

        public void setIdfa(String idfa) {
            this.idfa = idfa;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public Float getS() {
            return s;
        }

        public void setS(Float s) {
            this.s = s;
        }

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public Integer getNet() {
            return net;
        }

        public void setNet(Integer net) {
            this.net = net;
        }

        public String getOsv() {
            return osv;
        }

        public void setOsv(String osv) {
            this.osv = osv;
        }

        public String getManu() {
            return manu;
        }

        public void setManu(String manu) {
            this.manu = manu;
        }

        public String getBn() {
            return bn;
        }

        public void setBn(String bn) {
            this.bn = bn;
        }

        public String getMccMnc() {
            return mccMnc;
        }

        public void setMccMnc(String mccMnc) {
            this.mccMnc = mccMnc;
        }

        public Integer getSw() {
            return sw;
        }

        public void setSw(Integer sw) {
            this.sw = sw;
        }

        public Integer getSh() {
            return sh;
        }

        public void setSh(Integer sh) {
            this.sh = sh;
        }

        public Device(){}
        @Generated("SparkTools")
        private Device(Builder builder){
            this.ip = builder.ip;
            this.imei = builder.imei;
            this.androidid = builder.androidid;
            this.idfa = builder.idfa;
            this.mac = builder.mac;
            this.s = builder.s;
            this.ua = builder.ua;
            this.net = builder.net;
            this.osv = builder.osv;
            this.manu = builder.manu;
            this.bn = builder.bn;
            this.mccMnc = builder.mccMnc;
            this.sw = builder.sw;
            this.sh = builder.sh;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String ip;
            private String imei;
            private String androidid;
            private String idfa;
            private String mac;
            private Float s;
            private String ua;
            private Integer net;
            private String osv;
            private String manu;
            private String bn;
            private String mccMnc;
            private Integer sw;
            private Integer sh;

            public Builder withIp(String ip){
                this.ip = ip;
                return this;
            }
            public Builder withImei(String imei){
                this.imei = imei;
                return this;
            }
            public Builder withAndroidId(String androidid){
                this.androidid = androidid;
                return this;
            }
            public Builder withIdfa(String idfa){
                this.idfa = idfa;
                return this;
            }
            public Builder withMac(String mac){
                this.mac = mac;
                return this;
            }
            public Builder withDensity(Float s){
                this.s = s;
                return this;
            }
            public Builder withUa(String ua){
                this.ua = ua;
                return this;
            }
            public Builder withNet(Integer net){
                this.net = net;
                return this;
            }
            public Builder withOsv(String osv){
                this.osv = osv;
                return this;
            }
            public Builder withManu(String manu){
                this.manu = manu;
                return this;
            }
            public Builder withBn(String bn){
                this.bn = bn;
                return this;
            }
            public Builder withMccMnc(String mccMnc){
                this.mccMnc = mccMnc;
                return this;
            }
            public Builder withScreenWidth(Integer sw){
                this.sw = sw;
                return this;
            }
            public Builder withScreenHeight(Integer sh){
                this.sh = sh;
                return this;
            }

            public Builder(){}

            public Device build(){return new Device(this);}
        }
    }

    /**
     * 信息流对象
     */
    public static class Feed{
        /**
         * 大图宽尺寸
         * 当 format=3 并且需要大图时必填
         */
        private Integer imgw;
        /**
         * 大图高尺寸
         * 当 format=3 并且需要大图时必填
         */
        private Integer imgh;
        /**
         * 标题内容长度
         */
        private Integer tlen;
        /**
         * icon 宽
         * 当 format=3 并且需要大图时必填
         */
        private Integer icow;
        /**
         * icon 高
         * 当 format=3 并且需要大图时必填
         */
        private Integer icoh;
        /**
         * 简介内容长度
         */
        private Integer desclen;
        /**
         * 描述内容长度
         */
        private Integer sumlen;

        public Integer getImgw() {
            return imgw;
        }

        public void setImgw(Integer imgw) {
            this.imgw = imgw;
        }

        public Integer getImgh() {
            return imgh;
        }

        public void setImgh(Integer imgh) {
            this.imgh = imgh;
        }

        public Integer getTlen() {
            return tlen;
        }

        public void setTlen(Integer tlen) {
            this.tlen = tlen;
        }

        public Integer getIcow() {
            return icow;
        }

        public void setIcow(Integer icow) {
            this.icow = icow;
        }

        public Integer getIcoh() {
            return icoh;
        }

        public void setIcoh(Integer icoh) {
            this.icoh = icoh;
        }

        public Integer getDesclen() {
            return desclen;
        }

        public void setDesclen(Integer desclen) {
            this.desclen = desclen;
        }

        public Integer getSumlen() {
            return sumlen;
        }

        public void setSumlen(Integer sumlen) {
            this.sumlen = sumlen;
        }

        public Feed(){}
        @Generated("SparkTools")
        private Feed(Builder builder){
            this.imgw = builder.imgw;
            this.imgh = builder.imgh;
            this.tlen = builder.tlen;
            this.icow = builder.icow;
            this.icoh = builder.icoh;
            this.desclen = builder.desclen;
            this.sumlen = builder.sumlen;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private Integer imgw;
            private Integer imgh;
            private Integer tlen;
            private Integer icow;
            private Integer icoh;
            private Integer desclen;
            private Integer sumlen;
            public Builder withImgWidth(Integer imgw){
                this.imgw = imgw;
                return this;
            }
            public Builder withImgHeight(Integer imgh){
                this.imgh = imgh;
                return this;
            }
            public Builder withTitleLen(Integer tlen){
                this.tlen = tlen;
                return this;
            }
            public Builder withIconWidth(Integer icow){
                this.icow = icow;
                return this;
            }
            public Builder withIconHeight(Integer icoh){
                this.icoh = icoh;
                return this;
            }
            public Builder withDescLen(Integer desclen){
                this.desclen = desclen;
                return this;
            }
            public Builder withSumLen(Integer sumlen){
                this.sumlen = sumlen;
                return this;
            }
            public Builder(){}
            public Feed build(){return new Feed(this);}
        }
    }

    /**
     * 经纬度信息
     */
    public static class Geo{
        /**
         * 经度
         * 必填
         */
        private Double lon;
        /**
         * 纬度
         * 必填
         */
        private Double lat;
        /**
         * 时间戳，单位秒
         * 必填
         */
        private Long timestamp;
        /**
         * 坐标类型。
         *      1：全球卫星定位系统坐标系，
         *      2：国家测绘局坐标系，
         *      3：百度坐标系，
         *      4：其他坐标
         * 系
         * 必填
         */
        private Integer coordinate;

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public Integer getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(Integer coordinate) {
            this.coordinate = coordinate;
        }

        public Geo(){}
        @Generated("SparkTools")
        private Geo(Builder builder){
            this.lon = builder.lon;
            this.lat = builder.lat;
            this.timestamp = builder.timestamp;
            this.coordinate = builder.coordinate;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private Double lon;
            private Double lat;
            private Long timestamp;
            private Integer coordinate;

            public Builder withLon(Double lon){
                this.lon = lon;
                return this;
            }
            public Builder withLat(Double lat){
                this.lat = lat;
                return this;
            }
            public Builder withTimestamp(Long timestamp){
                this.timestamp = timestamp;
                return this;
            }
            public Builder withCoordinate(Integer coordinate){
                this.coordinate = coordinate;
                return this;
            }

            public Builder(){}
            public Geo build(){return new Geo(this);}
        }
    }

    /**
     * 广告信息返回格式
     *      FEEDS == 3
     *      VIDEO == 4
     */
    public enum FormatType{
        FEEDS(3),VIDEO(4);
        private Integer value;
        FormatType(Integer value){this.value = value;}
        public Integer getValue(){return this.value;}
    }
    /**
     * 是否返回 https 资源
     *      HTTPS == 1
     *      HTTP == 0
     */
    public enum IHttpsType{
        HTTPS(1),HTTP(0);
        private Integer value;
        IHttpsType(Integer value){this.value = value;}
        public Integer getValue(){return this.value;}
    }
    /**
     * 坐标类型
     *      WORLD == 1 全球卫星定位系统坐标系
     *      NATIVE == 2 国家测绘局坐标系
     *      BAIDU == 3 百度坐标系
     *      OTHER == 4 其他坐标系
     */
    public enum CoordinateType{
        WORLD(1),NATIVE(2),BAIDU(3),OTHER(4);
        private Integer value;
        CoordinateType(Integer value){this.value = value;}
        public Integer getValue(){return this.value;}
    }
    /**
     * 网络类型
     *      WIFI == 1
     *      NO_WIFI == 0
     */
    public enum NetType{
        WIFI(0),NO_WIFI(1);
        private Integer value;
        NetType(Integer value){this.value = value;}
        public Integer getValue(){return this.value;}
    }
}

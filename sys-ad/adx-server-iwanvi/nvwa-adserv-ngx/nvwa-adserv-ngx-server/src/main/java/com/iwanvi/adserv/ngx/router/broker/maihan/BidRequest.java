package com.iwanvi.adserv.ngx.router.broker.maihan;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.graph.Network;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-06-03 15:37
 * @version: v1.0
 * @Description:  智云众请求类
 */
public class BidRequest {

    /**
     * API 版本号
     * 必填
     */
    @JSONField(name = "api_version")
    private String apiVersion;
    /**
     * 广告位对象
     * 必填
     */
    private Pos pos;
    /**
     * 媒体对象
     * 必填
     */
    private Media media;
    /**
     * 设备对象
     * 必填
     */
    private Device device;
    /**
     * 网络环境对象
     * 必填
     */
    private Network network;
    /**
     * 地理位置对象
     * 选填
     */
    private Geo geo;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
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

        this.apiVersion = builder.apiVersion;
        this.pos = builder.pos;
        this.media = builder.media;
        this.device = builder.device;
        this.network = builder.network;
        this.geo = builder.geo;
    }

    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}

    @Generated("SparkTools")
    public static final class Builder{
        private String apiVersion;
        private Pos pos;
        private Media media;
        private Device device;
        private Network network;
        private Geo geo;

        public Builder withApiVersion(String apiVersion){
            this.apiVersion = apiVersion;
            return this;
        }
        public Builder withPos(Pos pos){
            this.pos = pos;
            return this;
        }
        public Builder withMedia(Media media){
            this.media = media;
            return this;
        }
        public Builder withDevice(Device device){
            this.device = device;
            return this;
        }
        public Builder withNetwork(Network network){
            this.network = network;
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
     *  广告位对象
     */
    public static class Pos{
        /**
         * 广告位 ID，由 MH 广告平台提供
         * 必填
         */
        private String id;
        /**
         * 是否请求渲染过的广告，默认：false ；当为 true 时，后台返回 html 片段
         * 选填
         */
        @JSONField(name = "need_rendered_ad")
        private Boolean needRenderedAd;
        /**
         * req_times 用于标识同一个信息流广告位在当前频道下，信息流从上拉、从下拉发起广告请求的次数。
         * 信息流广告位的第一次广告请求编号为 1；
         * 若之后信息流从下拉（即用户手指上滑、从下方加载数据）过程中，触发该广告位的广告请求，则从2 开始编号，不断递加；
         * 若之后信息流从上拉（即用户手指下滑、从上方加载数据）过程中，触发该广告位的广告请求，则从-2 开始编号，不断递减。
         * 无符号代表用户手指上滑，从下方加载数据；有负号代表代表用户手指下滑，从上方加载数据，两种行为分别计数
         * 选填
         */
        @JSONField(name = "req_times")
        private Integer reqTimes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Boolean getNeedRenderedAd() {
            return needRenderedAd;
        }

        public void setNeedRenderedAd(Boolean needRenderedAd) {
            this.needRenderedAd = needRenderedAd;
        }

        public Integer getReqTimes() {
            return reqTimes;
        }

        public void setReqTimes(Integer reqTimes) {
            this.reqTimes = reqTimes;
        }

        public Pos(){}

        @Generated("SparkTools")
        private Pos(Builder builder){
            this.id = builder.id;
            this.needRenderedAd = builder.needRenderedAd;
            this.reqTimes = builder.reqTimes;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private String id;
            private Boolean needRenderedAd;
            private Integer reqTimes;

            public Builder withId(String id){
                this.id = id;
                return this;
            }
            public Builder withNeedRenderedAd(Boolean needRenderedAd){
                this.needRenderedAd = needRenderedAd;
                return this;
            }
            public Builder withReqTimes(Integer reqTimes){
                this.reqTimes = reqTimes;
                return this;
            }

            public Builder(){}

            public Pos build(){return new Pos(this);}
        }
    }

    /**
     *  媒体对象
     */
    public static class Media{
        /**
         * 媒体 ID，由 MH 广告平台提供
         * 必填
         */
        @JSONField(name = "app_id")
        private String appId;
        /**
         * 应用包名，需要与 MH 广告平台填写的保持一致
         * 必填
         */
        @JSONField(name = "app_bundle_id")
        private String appBundleId;
        /**
         * 媒体版本号
         * 选填
         */
        @JSONField(name = "app_version")
        private String appVersion;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppBundleId() {
            return appBundleId;
        }

        public void setAppBundleId(String appBundleId) {
            this.appBundleId = appBundleId;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public Media(){}

        @Generated("SparkTools")
        private Media(Builder builder){
            this.appId = builder.appId;
            this.appBundleId = builder.appBundleId;
            this.appVersion = builder.appVersion;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private String appId;
            @JSONField(name = "app_bundle_id")
            private String appBundleId;
            @JSONField(name = "app_version")
            private String appVersion;

            public Builder withAppId(String appId){
                this.appId = appId;
                return this;
            }
            public Builder withAppBundleId(String appBundleId){
                this.appBundleId = appBundleId;
                return this;
            }
            public Builder withAppVersion(String appVersion){
                this.appVersion = appVersion;
                return this;
            }

            public Builder(){}

            public Media build(){return new Media(this);}
        }
    }

    /**
     * 设备对象
     */
    public static class Device{
        /**
         * IOS 设备唯一标识码
         * 必填
         */
        private String idfa;
        /**
         * IOS openudid
         * 必填
         */
        private String openudid;
        /**
         * Android 设备唯一标识码
         * 必填
         */
        private String imei;
        /**
         * Android 设备系统 ID
         * 必填
         */
        @JSONField(name = "android_id")
        private String androidId;
        /**
         * 设备 WiFi 网卡 MAC 地址
         * 必填
         */
        private String mac;
        /**
         * Android 设 备 的 Android AdvertisingID，保留原始值
         * 选填
         */
        @JSONField(name = "android_ad_id")
        private String androidAdId;
        /**
         * idfa 经过 MD5 加密，建议优先传明文，否则影响用户定向精准度
         * 选填
         */
        @JSONField(name = "idfa_md5")
        private String idfaMd5;
        /**
         * imei 经过 MD5 加密，建议优先传明文，否则影响用户定向精准度
         * 选填
         */
        @JSONField(name = "imei_md5")
        private String imeiMd5;
        /**
         * androidid 经过 MD5 加密，建议优先传明文，否则影响用户定向精准度
         * 选填
         */
        @JSONField(name = "androidid_md5")
        private String androididMd5;
        /**
         * 设备型号
         * 必填
         */
        private String model;
        /**
         * 设备品牌
         * 必填
         */
        private String manufacturer;
        /**
         * 设备类型
         * 必填
         */
        @JSONField(name = "device_type")
        private Integer deviceType;
        /**
         * 操作系统
         * 必填
         */
        private String os;
        /**
         * 设备系统版本号，注意至少需要填写主版本号 major 和副版本号 minor
         * 必填
         */
        @JSONField(name = "os_version")
        private String osVersion;
        /**
         * 设备竖屏状态时的屏幕宽
         * 必填
         */
        @JSONField(name = "screen_width")
        private Integer screenWidth;
        /**
         * 设备竖屏状态时的屏幕高
         * 必填
         */
        @JSONField(name = "screen_height")
        private Integer screenHeight;
        /**
         * 横竖屏 ：0：竖屏；90：横屏
         * 必填
         */
        private Integer orientation;
        /**
         * 客户端浏览器 user-agent
         * 必填
         */
        private String ua;
        /**
         * 屏幕密度
         * 选填
         */
        private Integer dpi;
        /**
         * 用户 SIM 卡的 imsi 号
         * 选填
         */
        private String imsi;
        /**
         * 当前剩余电量的百分比，取值范围 0-100
         * 选填
         */
        private Integer battery;
        /**
         * 用户安装应用列表，传包名
         * 选填
         */
        @JSONField(name = "ins_pkg")
        private String[] insPkg;

        public String getIdfa() {
            return idfa;
        }

        public void setIdfa(String idfa) {
            this.idfa = idfa;
        }

        public String getOpenudid() {
            return openudid;
        }

        public void setOpenudid(String openudid) {
            this.openudid = openudid;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getAndroidAdId() {
            return androidAdId;
        }

        public void setAndroidAdId(String androidAdId) {
            this.androidAdId = androidAdId;
        }

        public String getIdfaMd5() {
            return idfaMd5;
        }

        public void setIdfaMd5(String idfaMd5) {
            this.idfaMd5 = idfaMd5;
        }

        public String getImeiMd5() {
            return imeiMd5;
        }

        public void setImeiMd5(String imeiMd5) {
            this.imeiMd5 = imeiMd5;
        }

        public String getAndroididMd5() {
            return androididMd5;
        }

        public void setAndroididMd5(String androididMd5) {
            this.androididMd5 = androididMd5;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public Integer getScreenWidth() {
            return screenWidth;
        }

        public void setScreenWidth(Integer screenWidth) {
            this.screenWidth = screenWidth;
        }

        public Integer getScreenHeight() {
            return screenHeight;
        }

        public void setScreenHeight(Integer screenHeight) {
            this.screenHeight = screenHeight;
        }

        public Integer getOrientation() {
            return orientation;
        }

        public void setOrientation(Integer orientation) {
            this.orientation = orientation;
        }

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public Integer getDpi() {
            return dpi;
        }

        public void setDpi(Integer dpi) {
            this.dpi = dpi;
        }

        public String getImsi() {
            return imsi;
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        public Integer getBattery() {
            return battery;
        }

        public void setBattery(Integer battery) {
            this.battery = battery;
        }

        public String[] getInsPkg() {
            return insPkg;
        }

        public void setInsPkg(String[] insPkg) {
            this.insPkg = insPkg;
        }

        public Device(){}
        @Generated("SparkTools")
        private Device(Builder builder){
            this.idfa = builder.idfa;
            this.openudid = builder.openudid;
            this.imei = builder.imei;
            this.androidId = builder.androidId;
            this.mac = builder.mac;
            this.androidAdId = builder.androidAdId;
            this.idfaMd5 = builder.idfaMd5;
            this.imeiMd5 = builder.imeiMd5;
            this.androididMd5 = builder.androididMd5;
            this.model = builder.model;
            this.manufacturer = builder.manufacturer;
            this.deviceType = builder.deviceType;
            this.os = builder.os;
            this.osVersion = builder.osVersion;
            this.screenWidth = builder.screenWidth;
            this.screenHeight = builder.screenHeight;
            this.orientation = builder.orientation;
            this.ua = builder.ua;
            this.dpi = builder.dpi;
            this.imsi = builder.imsi;
            this.battery = builder.battery;
            this.insPkg = builder.insPkg;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String idfa;
            private String openudid;
            private String imei;
            private String androidId;
            private String mac;
            private String androidAdId;
            private String idfaMd5;
            private String imeiMd5;
            private String androididMd5;
            private String model;
            private String manufacturer;
            private Integer deviceType;
            private String os;
            private String osVersion;
            private Integer screenWidth;
            private Integer screenHeight;
            private Integer orientation;
            private String ua;
            private Integer dpi;
            private String imsi;
            private Integer battery;
            private String[] insPkg;

            public Builder withIdfa(String idfa){
                this.idfa = idfa;
                return this;
            }
            public Builder withOpenudid(String openudid){
                this.openudid = openudid;
                return this;
            }
            public Builder withImei(String imei){
                this.imei = imei;
                return this;
            }
            public Builder withAndroidId(String androidId){
                this.androidId = androidId;
                return this;
            }
            public Builder withMac(String mac){
                this.mac = mac;
                return this;
            }
            public Builder withAndroidAdId(String androidAdId){
                this.androidAdId = androidAdId;
                return this;
            }
            public Builder withIdfaMd5(String idfaMd5){
                this.idfaMd5 = idfaMd5;
                return this;
            }
            public Builder withImeiMd5(String imeiMd5){
                this.imeiMd5 = imeiMd5;
                return this;
            }
            public Builder withAndroididMd5(String androididMd5){
                this.androididMd5 = androididMd5;
                return this;
            }
            public Builder withModel(String model){
                this.model = model;
                return this;
            }
            public Builder withManufacturer(String manufacturer){
                this.manufacturer = manufacturer;
                return this;
            }
            public Builder withDeviceType(Integer deviceType){
                this.deviceType = deviceType;
                return this;
            }
            public Builder withOs(String os){
                this.os = os;
                return this;
            }
            public Builder withOsVersion(String osVersion){
                this.osVersion = osVersion;
                return this;
            }
            public Builder withScreenWidth(Integer screenWidth){
                this.screenWidth = screenWidth;
                return this;
            }
            public Builder withScreenHeight(Integer screenHeight){
                this.screenHeight = screenHeight;
                return this;
            }
            public Builder withOrientation(Integer orientation){
                this.orientation = orientation;
                return this;
            }
            public Builder withUa(String ua){
                this.ua = ua;
                return this;
            }
            public Builder withDpi(Integer dpi){
                this.dpi = dpi;
                return this;
            }
            public Builder withImsi(String imsi){
                this.imsi = imsi;
                return this;
            }
            public Builder withBattery(Integer battery){
                this.battery = battery;
                return this;
            }
            public Builder withInsPkg(String[] insPkg){
                this.insPkg = insPkg;
                return this;
            }

            public Builder(){}

            public Device build(){return new Device(this);}
        }
    }

    /**
     * 网络环境对象
     */
    public static class Network{
        /**
         * 联网方式
         */
        @JSONField(name = "connect_type")
        private Integer connectType;
        /**
         * 运营商
         */
        private Integer carrier;

        public Integer getConnectType() {
            return connectType;
        }

        public void setConnectType(Integer connectType) {
            this.connectType = connectType;
        }

        public Integer getCarrier() {
            return carrier;
        }

        public void setCarrier(Integer carrier) {
            this.carrier = carrier;
        }

        public Network(){}

        @Generated("SparkTools")
        private Network(Builder builder){
            this.connectType = builder.connectType;
            this.carrier = builder.carrier;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private Integer connectType;
            private Integer carrier;

            public Builder withConnectType(Integer connectType){
                this.connectType = connectType;
                return this;
            }
            public Builder withCarrier(Integer carrier){
                this.carrier = carrier;
                return this;
            }

            public Builder(){}

            public Network build(){return new Network(this);}
        }

    }

    /**
     * 地理位置对象
     */
    public static class Geo{
        /**
         * 用户原始 GPS 坐标的纬度*1,000,000
         * 选填
         */
        private Integer lat;
        /**
         * 用户原始 GPS 坐标的经度*1,000,000
         * 选填
         */
        private Integer lng;
        /**
         * 经纬度精度半径，单位：米
         * 选填
         */
        @JSONField(name = "location_accuracy")
        private Float locationAccuracy;
        /**
         * 获取经纬度（lat/lng）的时间。其值为从GMT 1970-01-01 00:00:00 至今的毫秒值
         * 选填
         */
        @JSONField(name = "coord_time")
        private Integer coordTime;

        public Integer getLat() {
            return lat;
        }

        public void setLat(Integer lat) {
            this.lat = lat;
        }

        public Integer getLng() {
            return lng;
        }

        public void setLng(Integer lng) {
            this.lng = lng;
        }

        public Float getLocationAccuracy() {
            return locationAccuracy;
        }

        public void setLocationAccuracy(Float locationAccuracy) {
            this.locationAccuracy = locationAccuracy;
        }

        public Integer getCoordTime() {
            return coordTime;
        }

        public void setCoordTime(Integer coordTime) {
            this.coordTime = coordTime;
        }

        public Geo(){}
        @Generated("SparkTools")
        private Geo(Builder builder){
            this.lat = builder.lat;
            this.lng = builder.lng;
            this.locationAccuracy = builder.locationAccuracy;
            this.coordTime = builder.coordTime;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private Integer lat;
            private Integer lng;
            private Float locationAccuracy;
            private Integer coordTime;

            public Builder withLat(Integer lat){
                this.lat = lat;
                return this;
            }
            public Builder withLng(Integer lng){
                this.lng = lng;
                return this;
            }
            public Builder withLocationAccuracy(Float locationAccuracy){
                this.locationAccuracy = locationAccuracy;
                return this;
            }
            public Builder withCoordTime(Integer coordTime){
                this.coordTime = coordTime;
                return this;
            }

            public Builder(){}

            public Geo build(){return new Geo(this);}
        }
    }

    /**
     * 联网方式
     *      UNKNOWN == 0：未知
     *      WIFI == 1：wifi
     *      K2G == 2：2G
     *      K3G == 3：3G
     *      K4G == 4：4G
     */
    public enum ConnectType{
        UNKNOWN(0),WIFI(1),K2G(2),K3G(3),K4G(4);
        private Integer value;
        ConnectType(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }
    /**
     * 运营商
     *      UNKNOWN == 0 未知
     *      CHINA_MOBILE == 1 移动
     *      CHINA_UNICOM == 2 联通
     *      CHINA_TELECOM == 3 电信
     */
    public enum Carrier{
        UNKNOWN(0),CHINA_MOBILE(1),CHINA_UNICOM(2),CHINA_TELECOM(3);
        private Integer value;
        Carrier(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }
    /**
     * 设备类型
     *    UNKNOWN == 0  未知
     *    MOBILE == 1 手机
     *    PHABLET == 2 平板
     */
    public enum DeviceType{
        UNKNOWN(0),MOBILE(1),PHABLET(2);
        private Integer value;
        DeviceType(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }

    /**
     * 操作系统
     *      IOS == ios
     *      ANDROID == android
     */
    public enum OSType{
        IOS("ios"),ANDROID("android");
        private String value;
        OSType(String value){
            this.value = value;
        }
        public String getValue(){return this.value;}
    }

    /**
     * 横竖屏
     *      PORTRAIT == 0 竖屏
     *      LANDSCAPE == 90 横屏
     */
    public enum Orientation{
        PORTRAIT(0),LANDSCAPE(90);
        private Integer value;
        Orientation(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }

}



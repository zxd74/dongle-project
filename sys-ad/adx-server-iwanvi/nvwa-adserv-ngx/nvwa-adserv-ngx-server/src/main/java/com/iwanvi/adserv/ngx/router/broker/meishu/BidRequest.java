package com.iwanvi.adserv.ngx.router.broker.meishu;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-07-09 16:41
 * @version: v1.0
 * @Description:  美数广告请求类  注意必填项
 */
public class BidRequest {

    /**
     * 版本号：（3.0）
     * 必填
     */
    private String version;
    /**
     * 请求唯一标识(具有唯一性,如不传 SSP 会自动给生成一个）
     */
    private String req_id;
    /**
     * 广告位 ID，在 SSP 注册的时候申请
     * 必填
     */
    private String pid;
    /**
     * 标识是否移动流量：(移动流量:1，pc:0)默认=1）
     * 必填
     */
    private Integer is_mobile;
    /**
     * http、https 协议支持
     */
    private String secure;
    /**
     * 性别：（男:01、女:10、-1:未知）
     */
    private String gender;
    /**
     * 年龄（Get 请求需做 urlencode）
     */
    private String age;
    /**
     * 用户感兴趣关键词多个用英文逗号隔（Get 请求需做urlencode）
     */
    private String keywords;
    /**
     * 禁止投放的创意 ID（多个用英文逗号”,” 分隔，例
     * 如”deny_cids=123,456” 注：此 ID 为 SSP 平台 ID）
     */
    private String deny_cids;
    /**
     * 禁止投放的广告主 ID(多个用英文逗号”,” 分隔，例
     * 如” deny_ader_ids =123,456” 注：此 ID 为 SSP 平台
     * ID)
     */
    private String deny_ader_ids;

    // PC 流量参数
    /**
     * 当前页 （Get 请求需做 urlencode）
     * 必填
     */
    private String src;
    /**
     * 来源上一页 （Get 请求需做 urlencode）
     */
    private String ref;
    /**
     * 客户端 ip, server to server 对接此字段必填
     */
    private String ip;
    /**
     * 当需要返回jsonp数据时可以传入此字段callback=func，
     * 返回的应答格式为 func(json 数据)
     */
    private String callback;

    // 移动流量参数
    /**
     * 应用包名
     * 必填
     */
    private String app_package;
    /**
     * 开发者应用 id（流量源 ID）
     * 必填
     */
    private String app_id;
    /**
     * APP 应用名称
     * 必填
     */
    private String app_name;
    /**
     * App 应用本身版本 eg:5.0.1
     * 必填
     */
    private String app_ver;
    /**
     * GPS 纬度（-90-90）
     * 必填
     */
    private Double device_geo_lat;
    /**
     * GPS 经度（-180-180）
     * 必填
     */
    private Double device_geo_lon;
    /**
     * IMEI 号（IOS 不填）
     * 必填
     */
    private String device_imei;
    /**
     * 如果是安卓为 android id, ios 则为 idfa
     * 必填
     */
    private String device_adid;
    /**
     * 苹果设备唯一标识号 ; 安卓系统不必填写
     * 推荐
     */
    private String device_openudid;
    /**
     * ios idfv  for iOS(>=iOS6)  安卓系统不必填写
     * 推荐
     */
    private String device_idfv;
    /**
     * 设备屏幕像素密度:286ppi
     * 推荐
     */
    private Double device_ppi;
    /**
     * MAC 地址：（ 00:23:5A:15:99:42）
     * 必填
     */
    private String device_mac;
    /**
     * 操作系统版本（8.0.1）
     * 必填
     */
    private String device_type_os;
    /**
     * 设备类型（-1:未知, 0:phone, 1:pad, 2:pc, 3:tv 4:wap）
     * 必填
     */
    private Integer device_type;
    /**
     * 设备品牌、生产厂商（"HUAWEI"、"samsung"、"Apple"、Xiaomi"等）
     * 必填
     */
    private String device_brand;
    /**
     * 手机型号（"MHA-AL00"、"SM-G9280"、"iPhone8"、"MIX 2S"等）
     * 必填
     */
    private String device_model;
    /**
     * 屏幕宽度
     * 必填
     */
    private Integer device_width;
    /**
     * 屏幕高度
     * 必填
     */
    private Integer device_height;
    /**
     * 网络运营商代码
     * 必填
     */
    private String device_imsi;
    /**
     * 网络类型
     * 必填
     */
    private Integer device_network;
    /**
     * Android/iOS/WP/Others 字符串，注意大小写
     * 必填
     */
    private String device_os;
    /**
     * 屏 幕 分 辨 率 值 如 :3.0. Android 平 台 参 考
     * DisplayMetrics.density, iOS 平台参考 UIScreen.scale
     */
    private Double device_density;
    /**
     * 客户端 ip（必须是外网可访问 IP，客户端直接）
     * 必填
     */
    private String device_ip;
    /**
     * User-Agent(GET 请求须进行一次 urlencode)必须是标准
     * Webview UA 而非自定义 UA
     * 必填
     */
    private String device_ua;
    /**
     * 横竖屏
     */
    private Integer device_orientation;
    /**
     * 目前使用的语言国家 zh-CN
     */
    private String device_lan = "zh-CN";
    /**
     * Android 设备是否 ROOT。
     */
    private String device_isroot;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getIs_mobile() {
        return is_mobile;
    }

    public void setIs_mobile(Integer is_mobile) {
        this.is_mobile = is_mobile;
    }

    public String getSecure() {
        return secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDeny_cids() {
        return deny_cids;
    }

    public void setDeny_cids(String deny_cids) {
        this.deny_cids = deny_cids;
    }

    public String getDeny_ader_ids() {
        return deny_ader_ids;
    }

    public void setDeny_ader_ids(String deny_ader_ids) {
        this.deny_ader_ids = deny_ader_ids;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getApp_package() {
        return app_package;
    }

    public void setApp_package(String app_package) {
        this.app_package = app_package;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_ver() {
        return app_ver;
    }

    public void setApp_ver(String app_ver) {
        this.app_ver = app_ver;
    }

    public Double getDevice_geo_lat() {
        return device_geo_lat;
    }

    public void setDevice_geo_lat(Double device_geo_lat) {
        this.device_geo_lat = device_geo_lat;
    }

    public Double getDevice_geo_lon() {
        return device_geo_lon;
    }

    public void setDevice_geo_lon(Double device_geo_lon) {
        this.device_geo_lon = device_geo_lon;
    }

    public String getDevice_imei() {
        return device_imei;
    }

    public void setDevice_imei(String device_imei) {
        this.device_imei = device_imei;
    }

    public String getDevice_adid() {
        return device_adid;
    }

    public void setDevice_adid(String device_adid) {
        this.device_adid = device_adid;
    }

    public String getDevice_openudid() {
        return device_openudid;
    }

    public void setDevice_openudid(String device_openudid) {
        this.device_openudid = device_openudid;
    }

    public String getDevice_idfv() {
        return device_idfv;
    }

    public void setDevice_idfv(String device_idfv) {
        this.device_idfv = device_idfv;
    }

    public Double getDevice_ppi() {
        return device_ppi;
    }

    public void setDevice_ppi(Double device_ppi) {
        this.device_ppi = device_ppi;
    }

    public String getDevice_mac() {
        return device_mac;
    }

    public void setDevice_mac(String device_mac) {
        this.device_mac = device_mac;
    }

    public String getDevice_type_os() {
        return device_type_os;
    }

    public void setDevice_type_os(String device_type_os) {
        this.device_type_os = device_type_os;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public Integer getDevice_width() {
        return device_width;
    }

    public void setDevice_width(Integer device_width) {
        this.device_width = device_width;
    }

    public Integer getDevice_height() {
        return device_height;
    }

    public void setDevice_height(Integer device_height) {
        this.device_height = device_height;
    }

    public String getDevice_imsi() {
        return device_imsi;
    }

    public void setDevice_imsi(String device_imsi) {
        this.device_imsi = device_imsi;
    }

    public Integer getDevice_network() {
        return device_network;
    }

    public void setDevice_network(Integer device_network) {
        this.device_network = device_network;
    }

    public String getDevice_os() {
        return device_os;
    }

    public void setDevice_os(String device_os) {
        this.device_os = device_os;
    }

    public Double getDevice_density() {
        return device_density;
    }

    public void setDevice_density(Double device_density) {
        this.device_density = device_density;
    }

    public String getDevice_ip() {
        return device_ip;
    }

    public void setDevice_ip(String device_ip) {
        this.device_ip = device_ip;
    }

    public String getDevice_ua() {
        return device_ua;
    }

    public void setDevice_ua(String device_ua) {
        this.device_ua = device_ua;
    }

    public Integer getDevice_orientation() {
        return device_orientation;
    }

    public void setDevice_orientation(Integer device_orientation) {
        this.device_orientation = device_orientation;
    }

    public String getDevice_lan() {
        return device_lan;
    }

    public void setDevice_lan(String device_lan) {
        this.device_lan = device_lan;
    }

    public String getDevice_isroot() {
        return device_isroot;
    }

    public void setDevice_isroot(String device_isroot) {
        this.device_isroot = device_isroot;
    }

    public BidRequest(){}

    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this.version = builder.version;
        this.req_id = builder.reqId;
        this.pid = builder.pid;
        this.is_mobile = builder.isMobile;
        this.secure = builder.secure;
        this.gender = builder.gender;
        this.age = builder.age;
        this.keywords = builder.keywords;
        this.deny_cids = builder.denyCids;
        this.deny_ader_ids = builder.denyAderIds;
        this.src = builder.src;
        this.ref = builder.ref;
        this.ip = builder.ip;
        this.callback = builder.callback;
        this.app_package = builder.appPackage;
        this.app_id = builder.appId;
        this.app_name = builder.appName;
        this.app_ver = builder.appVer;
        this.device_geo_lat = builder.deviceGeoLat;
        this.device_geo_lon = builder.deviceGeoLon;
        this.device_imei = builder.deviceImei;
        this.device_adid = builder.deviceAdId;
        this.device_openudid = builder.deviceOpenudid;
        this.device_idfv = builder.deviceIdfv;
        this.device_ppi = builder.devicePpi;
        this.device_mac = builder.deviceMac;
        this.device_type_os = builder.deviceTypeOs;
        this.device_type = builder.deviceType;
        this.device_brand = builder.deviceBrand;
        this.device_model = builder.deviceModel;
        this.device_width = builder.deviceWidth;
        this.device_height = builder.deviceHeight;
        this.device_imsi = builder.deviceImsi;
        this.device_network = builder.deviceNetwork;
        this.device_os = builder.deviceOs;
        this.device_density = builder.deviceDensity;
        this.device_ip = builder.deviceIp;
        this.device_ua = builder.deviceUa;
        this.device_orientation = builder.deviceOrientation;
        this.device_lan = builder.deviceLan;
        this.device_isroot = builder.deviceIsRoot;
    }

    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}

    @Generated("SparkTools")
    public static final class Builder{
        private String version;
        private String reqId;
        private String pid;
        private Integer isMobile;
        private String secure;
        private String gender;
        private String age;
        private String keywords;
        private String denyCids;
        private String denyAderIds;
        private String src;
        private String ref;
        private String ip;
        private String callback;
        private String appPackage;
        private String appId;
        private String appName;
        private String appVer;
        private Double deviceGeoLat;
        private Double deviceGeoLon;
        private String deviceImei;
        private String deviceAdId;
        private String deviceOpenudid;
        private String deviceIdfv;
        private Double devicePpi;
        private String deviceMac;
        private String deviceTypeOs;
        private Integer deviceType;
        private String deviceBrand;
        private String deviceModel;
        private Integer deviceWidth;
        private Integer deviceHeight;
        private String deviceImsi;
        private Integer deviceNetwork;
        private String deviceOs;
        private Double deviceDensity;
        private String deviceIp;
        private String deviceUa;
        private Integer deviceOrientation;
        private String deviceLan="zh-CN";
        private String deviceIsRoot;

        public Builder withVersion(String version){
            this.version = version;
            return this;
        }
        public Builder withReqId(String reqId){
            this.reqId = reqId;
            return this;
        }
        public Builder withPid(String pid){
            this.pid = pid;
            return this;
        }
        public Builder withIsMobile(Integer isMobile){
            this.isMobile = isMobile;
            return this;
        }
        public Builder withSecure(String secure){
            this.secure = secure;
            return this;
        }
        public Builder withGender(String gender){
            this.gender = gender;
            return this;
        }
        public Builder withAge(String age){
            this.age = age;
            return this;
        }
        public Builder withKeywords(String keywords){
            this.keywords = keywords;
            return this;
        }
        public Builder withDenyCids(String denyCids){
            this.denyCids = denyCids;
            return this;
        }
        public Builder withDenyAderIds(String denyAderIds){
            this.denyAderIds = denyAderIds;
            return this;
        }
        public Builder withSrc(String src){
            this.src = src;
            return this;
        }
        public Builder withRef(String ref){
            this.ref = ref;
            return this;
        }
        public Builder withIp(String ip){
            this.ip = ip;
            return this;
        }
        public Builder withCallback(String callback){
            this.callback = callback;
            return this;
        }
        public Builder withAppPackage(String appPackage){
            this.appPackage = appPackage;
            return this;
        }
        public Builder withAppId(String appId){
            this.appId = appId;
            return this;
        }
        public Builder withAppName(String appName){
            this.appName = appName;
            return this;
        }
        public Builder withAppVer(String appVer){
            this.appVer = appVer;
            return this;
        }
        public Builder withDeviceGeoLat(Double deviceGeoLat){
            this.deviceGeoLat = deviceGeoLat;
            return this;
        }
        public Builder withDeviceGeoLon(Double deviceGeoLon){
            this.deviceGeoLon = deviceGeoLon;
            return this;
        }
        public Builder withDeviceImei(String deviceImei){
            this.deviceImei = deviceImei;
            return this;
        }
        public Builder withDeviceAdId(String deviceAdId){
            this.deviceAdId = deviceAdId;
            return this;
        }
        public Builder withDeviceOpenudid(String deviceOpenudid){
            this.deviceOpenudid = deviceOpenudid;
            return this;
        }
        public Builder withDeviceIdfv(String deviceIdfv){
            this.deviceIdfv = deviceIdfv;
            return this;
        }
        public Builder withDevicePpi(Double devicePpi){
            this.devicePpi = devicePpi;
            return this;
        }
        public Builder withdeviceMac(String deviceMac){
            this.deviceMac = deviceMac;
            return this;
        }
        public Builder withDeviceTypeOs(String deviceTypeOs){
            this.deviceTypeOs = deviceTypeOs;
            return this;
        }
        public Builder withDeviceType(Integer deviceType){
            this.deviceType = deviceType;
            return this;
        }
        public Builder withDeviceBrand(String deviceBrand){
            this.deviceBrand = deviceBrand;
            return this;
        }
        public Builder withDeviceModel(String deviceModel){
            this.deviceModel = deviceModel;
            return this;
        }
        public Builder withDeviceWidth(Integer deviceWidth){
            this.deviceWidth = deviceWidth;
            return this;
        }
        public Builder withDeviceHeight(Integer deviceHeight){
            this.deviceHeight = deviceHeight;
            return this;
        }
        public Builder withDeviceImsi(String deviceImsi){
            this.deviceImsi = deviceImsi;
            return this;
        }
        public Builder withDeviceNetwork(Integer deviceNetwork){
            this.deviceNetwork = deviceNetwork;
            return this;
        }
        public Builder withDeviceOs(String deviceOs){
            this.deviceOs = deviceOs;
            return this;
        }
        public Builder withDeviceDensity(Double deviceDensity){
            this.deviceDensity = deviceDensity;
            return this;
        }
        public Builder withDeviceIp(String deviceIp){
            this.deviceIp = deviceIp;
            return this;
        }
        public Builder withDeviceUa(String deviceUa){
            this.deviceUa = deviceUa;
            return this;
        }
        public Builder withDeviceOrientation(Integer deviceOrientation){
            this.deviceOrientation = deviceOrientation;
            return this;
        }
        public Builder withDeviceLan(String deviceLan){
            this.deviceLan = deviceLan;
            return this;
        }
        public Builder withDeviceIsRoot(String deviceIsRoot){
            this.deviceIsRoot = deviceIsRoot;
            return this;
        }
        public Builder(){}
        public BidRequest build(){return new BidRequest(this);}

    }

}

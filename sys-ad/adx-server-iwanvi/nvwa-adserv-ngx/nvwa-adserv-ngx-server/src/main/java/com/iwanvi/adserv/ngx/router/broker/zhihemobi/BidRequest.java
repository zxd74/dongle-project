package com.iwanvi.adserv.ngx.router.broker.zhihemobi;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-06-11 15:11
 * @version: v1.0
 * @Description: 智合移动请求类
 */
public class BidRequest {

    /**
     * 广告请求 ID，接入方自定义，确保唯一性。推荐：md5(app_id+ adslot_id+随机几位 key)
     */
    @JSONField(name = "request_id")
    private String requestId;
    /**
     * app_id 平台生成
     * 必填
     */
    @JSONField(name = "app_id")
    private String appId;
    /**
     * APP 名称
     * 必填
     */
    @JSONField(name = "app_name")
    private String appName;
    /**
     * APP 应用包名
     * 必填
     */
    @JSONField(name = "app_package")
    private String appPackage;
    /**
     * APP 版本号
     * 必填
     */
    @JSONField(name = "app_version")
    private String appVersion;
    /**
     * 请求的 unix 时间戳，精确到毫秒（13 位）
     * 必填
     */
    private Long time;
    /**
     * Api 版本
     * 必填
     */
    private String vapi;
    /**
     * 验证信息 【md5( request_id+time )】
     * 必填
     */
    private String token;
    /**
     * 广告位 id （智合移动平台分配）
     * 必填
     */
    @JSONField(name = "adslot_id")
    private Integer adslotId;
    /**
     * 映射广告位 id
     */
    @JSONField(name = "adslot_map_id")
    private String adslotMapId;
    /**
     * 当前代码位宽度
     * 必填
     */
    @JSONField(name = "adslot_width")
    private Integer adslotWidth;
    /**
     * 当前代码位高度
     * 必填
     */
    @JSONField(name = "adslot_height")
    private Integer adslotHeight;
    /**
     * 广告类型
     * 必填
     */
    private Integer adtype;
    /**
     * 广告位支持的物料类型,可以多选，英文,分隔
     * 必填
     */
    private String admimes;
    /**
     * 视频广告位允许
     * 最短时长(s)
     */
    @JSONField(name = "video_duration_min")
    private Integer videoDurationMin;
    /**
     * 视频广告位允许最大时长(s)
     */
    @JSONField(name = "video_duration_max")
    private Integer videoDurationMax;
    /**
     * 当前视频的标题
     * (视频类广告位)
     */
    @JSONField(name = "video_title")
    private String videoTitle;
    /**
     * 当前视频内容长度(单位秒,视频类广告位)
     */
    @JSONField(name = "video_length")
    private Integer videoLength;
    /**
     * 设备型号
     * 必填
     */
    private String model;
    /**
     * 设备品牌
     * 必填
     */
    private String vendor;
    /**
     * 操作系统类型
     * 必填
     */
    @JSONField(name = "os_type")
    private Integer osType;
    /**
     * 操作系统版本
     * 必填
     */
    @JSONField(name = "os_version")
    private String osVersion;
    /**
     * 设备横竖屏
     * 必填
     */
    private Integer cori;
    /**
     * 设备类型
     * 必填
     */
    @JSONField(name = "device_type")
    private Integer deviceType;
    /**
     * 屏幕宽，取设备物理像素
     * 必填
     */
    @JSONField(name = "screen_width")
    private Integer screenWidth;
    /**
     * 屏幕高，取设备物理像素
     * 必填
     */
    @JSONField(name = "screen_height")
    private Integer screenHeight;
    /**
     * 客户端  User-Agent
     * 必填
     */
    private String ua;
    /**
     * 安卓设备 IMEI
     */
    private String imei;
    /**
     * 安卓设备唯一标识（不存在传空字符串）
     * （安卓必填）
     */
    private String imsi;
    /**
     * 安卓android_id
     * （安卓必填）
     */
    private String androidid;
    /**
     * IOS 设备的 idfa
     * （ios 必填）
     */
    private String idfa;
    /**
     * IOS 设备的 idfv（不存在传空字符串）
     * （ios 必填）
     */
    private String idfv;
    /**
     * IOS 设备的openUdid 值（不存在传空字符串）
     * （ios 必填）
     */
    private String openUdid;
    /**
     * MAC 地址
     * （android 必填）
     */
    private String mac;
    /**
     * IP地址
     * 必填
     */
    private String ip;
    /**
     * 手机网络运营商
     * 必填
     */
    @JSONField(name = "operator_type")
    private Integer operatorType;
    /**
     * 联网方式
     * 必填
     */
    @JSONField(name = "connection_type")
    private Integer connectionType;
    /**
     * 基站 ID
     * 当前连接运营商基站 Id
     */
    @JSONField(name = "cellular_id")
    private String cellularId;
    /**
     * 屏幕密度
     */
    private String density;
    /**
     * GPS 坐标类型
     */
    @JSONField(name = "coordinate_type")
    private Integer coordinateType;
    /**
     * GPS 纬度
     */
    private Double latitude;
    /**
     * GPS 精度
     */
    private Double longitude;
    /**
     * GPS 时间戳信息
     */
    private Long timestamp;
    /**
     * 当前使用语言
     */
    private String language = "zh-CN";
    /**
     * 热点 mac 地址
     * 重要参数，建议选填
     */
    @JSONField(name = "ap_mac")
    private String apMac;
    /**
     * 热点信号强度
     * 重要参数，建议选填
     */
    private String rssi;
    /**
     * 热点名称
     * 用户识别用户所处场所，用于精准定向
     */
    @JSONField(name = "ap_name")
    private String apName;
    /**
     * 是否当前连接热点
     * 该参数配合热点名称
     */
    @JSONField(name = "is_connected")
    private Boolean isConnected;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getVapi() {
        return vapi;
    }

    public void setVapi(String vapi) {
        this.vapi = vapi;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getAdslotId() {
        return adslotId;
    }

    public void setAdslotId(Integer adslotId) {
        this.adslotId = adslotId;
    }

    public String getAdslotMapId() {
        return adslotMapId;
    }

    public void setAdslotMapId(String adslotMapId) {
        this.adslotMapId = adslotMapId;
    }

    public Integer getAdslotWidth() {
        return adslotWidth;
    }

    public void setAdslotWidth(Integer adslotWidth) {
        this.adslotWidth = adslotWidth;
    }

    public Integer getAdslotHeight() {
        return adslotHeight;
    }

    public void setAdslotHeight(Integer adslotHeight) {
        this.adslotHeight = adslotHeight;
    }

    public Integer getAdtype() {
        return adtype;
    }

    public void setAdtype(Integer adtype) {
        this.adtype = adtype;
    }

    public String getAdmimes() {
        return admimes;
    }

    public void setAdmimes(String admimes) {
        this.admimes = admimes;
    }

    public Integer getVideoDurationMin() {
        return videoDurationMin;
    }

    public void setVideoDurationMin(Integer videoDurationMin) {
        this.videoDurationMin = videoDurationMin;
    }

    public Integer getVideoDurationMax() {
        return videoDurationMax;
    }

    public void setVideoDurationMax(Integer videoDurationMax) {
        this.videoDurationMax = videoDurationMax;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public Integer getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(Integer videoLength) {
        this.videoLength = videoLength;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getOsType() {
        return osType;
    }

    public void setOsType(Integer osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public Integer getCori() {
        return cori;
    }

    public void setCori(Integer cori) {
        this.cori = cori;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
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

    public String getIdfv() {
        return idfv;
    }

    public void setIdfv(String idfv) {
        this.idfv = idfv;
    }

    public String getOpenUdid() {
        return openUdid;
    }

    public void setOpenUdid(String openUdid) {
        this.openUdid = openUdid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public Integer getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(Integer connectionType) {
        this.connectionType = connectionType;
    }

    public String getCellularId() {
        return cellularId;
    }

    public void setCellularId(String cellularId) {
        this.cellularId = cellularId;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public Integer getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(Integer coordinateType) {
        this.coordinateType = coordinateType;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getApMac() {
        return apMac;
    }

    public void setApMac(String apMac) {
        this.apMac = apMac;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    public BidRequest(){}
    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this.requestId = builder.requestId;
        this.appId = builder.appId;
        this.appName = builder.appName;
        this.appPackage = builder.appPackage;
        this.appVersion = builder.appVersion;
        this.time = builder.time;
        this.vapi = builder.vapi;
        this.token = builder.token;
        this.adslotId = builder.adslotId;
        this.adslotWidth = builder.adslotWidth;
        this.adslotHeight = builder.adslotHeight;
        this.adtype = builder.adtype;
        this.admimes = builder.admimes;
        this.videoDurationMin = builder.videoDurationMin;
        this.videoDurationMax = builder.videoDurationMax;
        this.videoTitle = builder.videoTitle;
        this.videoLength = builder.videoLength;
        this.model = builder.model;
        this.vendor = builder.vendor;
        this.osType = builder.osType;
        this.osVersion = builder.osVersion;
        this.cori = builder.cori;
        this.deviceType = builder.deviceType;
        this.screenWidth = builder.screenWidth;
        this.screenHeight = builder.screenHeight;
        this.ua = builder.ua;
        this.imei = builder.imei;
        this.imsi = builder.imsi;
        this.androidid = builder.androidid;
        this.idfa = builder.idfa;
        this.idfv = builder.idfv;
        this.openUdid = builder.openUdid;
        this.mac = builder.mac;
        this.ip = builder.ip;
        this.operatorType = builder.operatorType;
        this.connectionType = builder.connectionType;
        this.cellularId = builder.cellularId;
        this.density = builder.density;
        this.coordinateType = builder.coordinateType;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.timestamp = builder.timestamp;
        this.language = builder.language;
        this.apMac = builder.apMac;
        this.rssi = builder.rssi;
        this.apName = builder.apName;
        this.isConnected = builder.isConnected;
    }
    @Generated("SparkTools")
    public static Builder builder(){return  new Builder();}
    @Generated("SparkTools")
    public static final class Builder{
        private String requestId;
        private String appId;
        private String appName;
        private String appPackage;
        private String appVersion;
        private Long time;
        private String vapi;
        private String token;
        private Integer adslotId;
        private String adslotMapId;
        private Integer adslotWidth;
        private Integer adslotHeight;
        private Integer adtype;
        private String admimes;
        private Integer videoDurationMin;
        private Integer videoDurationMax;
        private String videoTitle;
        private Integer videoLength;
        private String model;
        private String vendor;
        private Integer osType;
        private String osVersion;
        private Integer cori;
        private Integer deviceType;
        private Integer screenWidth;
        private Integer screenHeight;
        private String ua;
        private String imei;
        private String imsi;
        private String androidid;
        private String idfa;
        private String idfv;
        private String openUdid;
        private String mac;
        private String ip;
        private Integer operatorType;
        private Integer connectionType;
        private String cellularId;
        private String density;
        private Integer coordinateType;
        private Double latitude;
        private Double longitude;
        private Long timestamp;
        private String language;
        private String apMac;
        private String rssi;
        private String apName;
        private Boolean isConnected;

        public Builder withRequestId(String requestId){
            this.requestId = requestId;
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
        public Builder withAppPackage(String appPackage){
            this.appPackage = appPackage;
            return this;
        }
        public Builder withAppVersion(String appVersion){
            this.appVersion = appVersion;
            return this;
        }
        public Builder withTime(Long time){
            this.time = time;
            return this;
        }
        public Builder withApiVersion(String vapi){
            this.vapi = vapi;
            return this;
        }
        public Builder withToken(String token){
            this.token = token;
            return this;
        }
        public Builder withAdslotId(Integer adslotId){
            this.adslotId = adslotId;
            return this;
        }
        public Builder withAdslotMapId(String adslotMapId){
            this.adslotMapId = adslotMapId;
            return this;
        }
        public Builder withAdslotWidth(Integer adslotWidth){
            this.adslotWidth = adslotWidth;
            return this;
        }
        public Builder withAdslotHeight(Integer adslotHeight ){
            this.adslotHeight = adslotHeight;
            return this;
        }
        public Builder withAdType(Integer adtype){
            this.adtype = adtype;
            return this;
        }
        public Builder withAdmimes(String admimes){
            this.admimes = admimes;
            return this;
        }
        public Builder withVideoDurationMin(Integer videoDurationMin){
            this.videoDurationMin = videoDurationMin;
            return this;
        }
        public Builder withVideoDurationMax(Integer videoDurationMax){
            this.videoDurationMax = videoDurationMax;
            return this;
        }
        public Builder withVideoTitle(String videoTitle){
            this.videoTitle = videoTitle;
            return this;
        }
        public Builder withVideoLength(Integer videoLength){
            this.videoLength = videoLength;
            return this;
        }
        public Builder withModel(String model){
            this.model = model;
            return this;
        }
        public Builder withVendor(String vendor){
            this.vendor = vendor;
            return this;
        }
        public Builder withOsType(Integer osType){
            this.osType = osType;
            return this;
        }
        public Builder withOsVersion(String osVersion){
            this.osVersion = osVersion;
            return this;
        }
        public Builder withCori(Integer cori){
            this.cori = cori;
            return this;
        }
        public Builder withDeviceType(Integer deviceType){
            this.deviceType = deviceType;
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
        public Builder withUa(String ua){
            this.ua = ua;
            return this;
        }
        public Builder withImei(String imei){
            this.imei = imei;
            return this;
        }
        public Builder withImsi(String imsi){
            this.imsi = imsi;
            return this;
        }
        public Builder withAndroidid(String androidid){
            this.androidid = androidid;
            return this;
        }
        public Builder withIdfa(String idfa){
            this.idfa = idfa;
            return this;
        }
        public Builder withIdfv(String idfv){
            this.idfv = idfv;
            return this;
        }
        public Builder withOpenUdid(String openUdid){
            this.openUdid = openUdid;
            return this;
        }
        public Builder withMac(String mac){
            this.mac = mac;
            return this;
        }
        public Builder withIp(String ip){
            this.ip = ip;
            return this;
        }
        public Builder withOperatorType(Integer operatorType){
            this.operatorType = operatorType;
            return this;
        }
        public Builder withConnectionType(Integer connectionType){
            this.connectionType = connectionType;
            return this;
        }
        public Builder withCellularId(String cellularId){
            this.cellularId = cellularId;
            return this;
        }
        public Builder withDensity(String density){
            this.density = density;
            return this;
        }
        public Builder withCoordinateType(Integer coordinateType){
            this.coordinateType = coordinateType;
            return this;
        }
        public Builder withLatitude(Double latitude){
            this.latitude = latitude;
            return this;
        }
        public Builder withLongitude(Double longitude){
            this.longitude = longitude;
            return this;
        }
        public Builder withTimestamp(Long timestamp){
            this.timestamp = timestamp;
            return this;
        }
        public Builder withLanguage(String language){
            this.language = language;
            return this;
        }
        public Builder withApMac(String apMac){
            this.apMac = apMac;
            return this;
        }
        public Builder withRssi(String rssi){
            this.rssi = rssi;
            return this;
        }
        public Builder withApName(String apName){
            this.apName = apName;
            return this;
        }
        public Builder withIsConnected(Boolean isConnected ){
            this.isConnected = isConnected;
            return this;
        }

        public Builder(){}
        public BidRequest build(){return new BidRequest(this);}
    }

    /**
     * 广告类型
     *      BANNER == 1
     *      SCREEN == 2 插页
     *      NATIVE == 3
     *      FULL_SCREEN == 4 开屏
     *      NATIVE_VIDEO == 5
     *      INCENTIVE_NATIVE == 6 激励视频
     */
    public enum AdType{
        BANNER(1),SCREEN(2),NATIVE(3),FULL_SCREEN(4),NATIVE_VIDEO(5),INCENTIVE_NATIVE(6);
        private Integer value;
        AdType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    /**
     * 广告位支持的物料类型
     *      TEXT_LINK == "1" 文字链
     *      IMAGE == "2" 图片
     *      IMAGE_TEXT == "3" 图文
     *      RICH_TEXT == "4" 富文本
     */
    public enum AdMimeType{
        TEXT_LINK("1"),IMAGE("2"),IMAGE_TEXT("3"),RICH_TEXT("4");
        private String value;
        AdMimeType(String value){this.value = value;}
        public String getValue() {
            return value;
        }
    }
    /**
     * 操作系统类型
     */
    public enum OsType{
        ANDROID(1),IOS(2);
        private Integer value;
        OsType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    /**
     * 横竖屏
     *      PORTRAIT_SCREEN == 0 竖屏
     *      LANDSCAPE == 1  横屏
     */
    public enum CoriType{
        PORTRAIT_SCREEN(0),LANDSCAPE(1);
        private Integer value;
        CoriType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    /**
     * 设备类型
     *      PHONE == 1
     *      TABLE == 2 平白
     *      TV == 3 智能TV
     *      OUTDOOR_SCREEN == 4 户外屏
     */
    public enum DeviceType{
        PHONE(1),TABLE(2),TV(3),OUTDOOR_SCREEN(4);
        private Integer value;
        DeviceType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    /**
     * 网络运营商
     */
    public enum OperatorType{
        UNKNOWN(0),CHINA_MOBILE(1),CHINA_UNICOM(2),CHINA_TELECOM(3),OTHER(99);
        private Integer value;
        OperatorType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    /**
     * 联网方式
     *      ETHERNET == 101 以太网
     *      UNKNOWN_G == 1 未知蜂窝数据网络
     *      UNKNOWN_NEW == 999 未知新类型
     */
    public enum ConnectionType{
        UNKNOWN(0),UNKNOWN_G(1),K2G(2),K3G(3),K4G(4),K5G(5),WIFI(100),ETHERNET(101),UNKNOWN_NEW(999);
        private Integer value;
        ConnectionType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    /**
     * GPS坐标类型
     *      WORLD == 1 全球卫星定位系统坐标系
     *      NATIVE == 2 国家测绘局坐标系
     *      BAIDU == 3 百度坐标系
     */
    public enum GPSType{
        WORLD(1),NATIVE(2),BAIDU(3);
        private Integer value;
        GPSType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
}

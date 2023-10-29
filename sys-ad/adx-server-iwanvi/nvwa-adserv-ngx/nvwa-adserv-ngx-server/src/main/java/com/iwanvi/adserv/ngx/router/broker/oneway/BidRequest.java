package com.iwanvi.adserv.ngx.router.broker.oneway;

import javax.annotation.Generated;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-02 15:08
 */
public class BidRequest {

    /**
     * 对接 API 版本
     */
    private String apiVersion;
    /**
     * 广告位 ID  在平台获取
     */
    private String placementId;
    /**
     * 指定创意类型
     */
    private Integer creativeType;
    /**
     * 应用包名
     */
    private String bundleId;
    /**
     * 应用版本
     */
    private String bundleVersion;
    /**
     * 设备广告ID (iOS填IDFA, Android填GAID, Android 获取不到可不填 )
     */
    private String deviceId;
    /**
     * Android必填, iOS 获取不到可不填
     */
    private String imei;
    /**
     * Android必填
     */
    private String androidId;
    /**
     * Android API level (iOS不填)
     */
    private Integer apiLevel;
    /**
     * 操作系统版本
     */
    private String osVersion;
    /**
     * 网络连接类型
     */
    private String connectionType;
    /**
     * 网络类型  值为0会影响填充
     */
    private Integer networkType;
    /**
     * 移动网络运营商编码
     */
    private String networkOperator;
    /**
     * SIM卡运营商编码
     */
    private String simOperator;
    /**
     * MSI 国际移动用户识别码, 格式一定要正确不然会影响填充 格式: MCC+MNC+MSIN 组成
     */
    private String imsi;
    /**
     * 设备产商
     */
    private String deviceMake;
    /**
     * 设备型号
     */
    private String deviceModel;
    /**
     * 设备序列号, 全部大写
     */
    private String serialno;
    /**
     * 设备MAC地址
     */
    private String mac;
    /**
     * WIFI MAC地址
     */
    private String wifiBSSID;
    /**
     * WIFI用户名
     */
    private String wifiSSID;
    /**
     * 设备屏宽
     */
    private Integer screenWidth;
    /**
     * 设备屏高
     */
    private Integer screenHeight;
    /**
     * 屏幕密度
     */
    private Integer screenDensity;
    /**
     * 客户端webview User-Agent 格式可查看请求参数示例 请确保上报参数userAgent, 上报请求Header User-Agent 和广告请求参数userAgent信息一致以免判定为无效上报
     */
    private String userAgent;
    /**
     * 客户端 ip
     */
    private String ip;
    /**
     * 语言
     */
    private String language = "zh_CN";
    /**
     * 时区
     */
    private String timeZone = "GMT+08:00";
    /**
     * 经度
     */
    private Float longitude;
    /**
     * 纬度
     */
    private Float latitude;
    /**
     * 系统已安装包名, 多个用逗号隔开
     */
    private String installedApp;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getPlacementId() {
        return placementId;
    }

    public void setPlacementId(String placementId) {
        this.placementId = placementId;
    }

    public Integer getCreativeType() {
        return creativeType;
    }

    public void setCreativeType(Integer creativeType) {
        this.creativeType = creativeType;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getBundleVersion() {
        return bundleVersion;
    }

    public void setBundleVersion(String bundleVersion) {
        this.bundleVersion = bundleVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public Integer getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(Integer apiLevel) {
        this.apiLevel = apiLevel;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public Integer getNetworkType() {
        return networkType;
    }

    public void setNetworkType(Integer networkType) {
        this.networkType = networkType;
    }

    public String getNetworkOperator() {
        return networkOperator;
    }

    public void setNetworkOperator(String networkOperator) {
        this.networkOperator = networkOperator;
    }

    public String getSimOperator() {
        return simOperator;
    }

    public void setSimOperator(String simOperator) {
        this.simOperator = simOperator;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getDeviceMake() {
        return deviceMake;
    }

    public void setDeviceMake(String deviceMake) {
        this.deviceMake = deviceMake;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getWifiBSSID() {
        return wifiBSSID;
    }

    public void setWifiBSSID(String wifiBSSID) {
        this.wifiBSSID = wifiBSSID;
    }

    public String getWifiSSID() {
        return wifiSSID;
    }

    public void setWifiSSID(String wifiSSID) {
        this.wifiSSID = wifiSSID;
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

    public Integer getScreenDensity() {
        return screenDensity;
    }

    public void setScreenDensity(Integer screenDensity) {
        this.screenDensity = screenDensity;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getInstalledApp() {
        return installedApp;
    }

    public void setInstalledApp(String installedApp) {
        this.installedApp = installedApp;
    }

    public BidRequest() {
    }

    @Generated("SparkTools")
    private BidRequest(Builder builder) {
        this.apiVersion = builder.apiVersion;
        this.placementId = builder.placementId;
        this.creativeType = builder.creativeType;
        this.bundleId = builder.bundleId;
        this.bundleVersion = builder.bundleVersion;
        this.deviceId = builder.deviceId;
        this.imei = builder.imei;
        this.androidId = builder.androidId;
        this.apiLevel = builder.apiLevel;
        this.osVersion = builder.osVersion;
        this.connectionType = builder.connectionType;
        this.networkType = builder.networkType;
        this.networkOperator = builder.networkOperator;
        this.simOperator = builder.simOperator;
        this.imsi = builder.imsi;
        this.deviceMake = builder.deviceMake;
        this.deviceModel = builder.deviceModel;
        this.serialno = builder.serialno;
        this.mac = builder.mac;
        this.wifiBSSID = builder.wifiBSSID;
        this.wifiSSID = builder.wifiSSID;
        this.screenWidth = builder.screenWidth;
        this.screenHeight = builder.screenHeight;
        this.screenDensity = builder.screenDensity;
        this.userAgent = builder.userAgent;
        this.ip = builder.ip;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.installedApp = builder.installedApp;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String apiVersion;
        private String placementId;
        private Integer creativeType;
        private String bundleId;
        private String bundleVersion;
        private String deviceId;
        private String imei;
        private String androidId;
        private Integer apiLevel;
        private String osVersion;
        private String connectionType;
        private Integer networkType;
        private String networkOperator;
        private String simOperator;
        private String imsi;
        private String deviceMake;
        private String deviceModel;
        private String serialno;
        private String mac;
        private String wifiBSSID;
        private String wifiSSID;
        private Integer screenWidth;
        private Integer screenHeight;
        private Integer screenDensity;
        private String userAgent;
        private String ip;
        private Float longitude;
        private Float latitude;
        private String installedApp;

        public Builder withApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        public Builder withPlacementId(String placementId) {
            this.placementId = placementId;
            return this;
        }

        public Builder withCreativeType(Integer creativeType) {
            this.creativeType = creativeType;
            return this;
        }

        public Builder withBundleId(String bundleId) {
            this.bundleId = bundleId;
            return this;
        }

        public Builder withBundleVersion(String bundleVersion) {
            this.bundleVersion = bundleVersion;
            return this;
        }

        public Builder withDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Builder withImei(String imei) {
            this.imei = imei;
            return this;
        }

        public Builder withAndroidId(String androidId) {
            this.androidId = androidId;
            return this;
        }

        public Builder withApiLevel(Integer apiLevel) {
            this.apiLevel = apiLevel;
            return this;
        }

        public Builder withOsVersion(String osVersion) {
            this.osVersion = osVersion;
            return this;
        }

        public Builder withConnectionType(String connectionType) {
            this.connectionType = connectionType;
            return this;
        }

        public Builder withNetworkType(Integer networkType) {
            this.networkType = networkType;
            return this;
        }

        public Builder withNetworkOperator(String networkOperator) {
            this.networkOperator = networkOperator;
            return this;
        }

        public Builder withSimOperator(String simOperator) {
            this.simOperator = simOperator;
            return this;
        }

        public Builder withImsi(String imsi) {
            this.imsi = imsi;
            return this;
        }

        public Builder withDeviceMake(String deviceMake) {
            this.deviceMake = deviceMake;
            return this;
        }

        public Builder withDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
            return this;
        }

        public Builder withSerialno(String serialno) {
            this.serialno = serialno;
            return this;
        }

        public Builder withMac(String mac) {
            this.mac = mac;
            return this;
        }

        public Builder withWifiBSSID(String wifiBSSID) {
            this.wifiBSSID = wifiBSSID;
            return this;
        }

        public Builder withWifiSSID(String wifiSSID) {
            this.wifiSSID = wifiSSID;
            return this;
        }

        public Builder withScreenWidth(Integer screenWidth) {
            this.screenWidth = screenWidth;
            return this;
        }

        public Builder withScreenHeight(Integer screenHeight) {
            this.screenHeight = screenHeight;
            return this;
        }

        public Builder withScreenDensity(Integer screenDensity) {
            this.screenDensity = screenDensity;
            return this;
        }

        public Builder withUserAgentn(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withLongitude(Float longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withLatitude(Float latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withInstalledApp(String installedApp) {
            this.installedApp = installedApp;
            return this;
        }

        public BidRequest build() {
            return new BidRequest(this);
        }
    }
}

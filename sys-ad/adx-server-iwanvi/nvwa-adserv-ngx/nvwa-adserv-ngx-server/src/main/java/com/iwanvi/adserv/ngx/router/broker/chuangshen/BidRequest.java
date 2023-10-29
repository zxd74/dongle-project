package com.iwanvi.adserv.ngx.router.broker.chuangshen;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-06-13 09:22
 * @version: v1.0
 * @Description: 创神请求类
 */
public class BidRequest {

    /**
     * 广告位posId,由我方平台分配
     */
    private Integer posId;
    /**
     * 广告素材宽度
     */
    private Integer thumbWidth;
    /**
     * 广告素材高度
     */
    private Integer thumbHeight;
    /**
     * 请求时间戳,秒为单位的时间戳
     */
    private Long timestamp;
    /**
     * 平台  1 IOS 2 安卓
     */
    private Integer platform;
    /**
     * 设备号 苹果idfa或者安卓imei
     */
    private String device;
    /**
     * Api版本号，这里固定值 3
     */
    private String version;
    /**
     * 终端设备类型 0手机 1平板
     */
    private Integer clientType;
    /**
     * 终端操作系统 0 Android 1 IOS
     */
    private Integer clientOsType;
    /**
     * 客户端的ip地址，这里请不要使用服务端ip
     */
    private String ip;
    /**
     * 终端操作系统版本号
     */
    private String clientOsVersion;
    /**
     * 客户端userAgent字符串
     */
    private String ua;
    /**
     * 屏幕宽度
     */
    private Integer screenWidth;
    /**
     * 屏幕高度
     */
    private Integer screenHeight;
    /**
     * 设备屏幕密度
     */
    private Double screenDensity;

    private String andid;
    private String idfa;
    private String idfv;
    private String mac;
    /**
     * 设备联网类型 wifi/2g/3g/4g
     */
    private String netStatus;
    /**
     * 手机运营商代号  46000,46002,46007中国移动 46001,46006中国联通 46003,46005中国电信
     */
    private String netIsp;
    /**
     * 签名, 算法
     * md5(“posId=xxx&thumbWidth=xxx&thumbHeight=xxx&platform=xxx&device=xxx&vt=xxx&secret=”+SECRET), SECRET由我方平台给出
     */
    private String signature;
    /**
     * 设备品牌
     */
    private String clientVendor;
    /**
     * 设备型号
     */
    private String clientModel;

    public Integer getPosId() {
        return posId;
    }

    public void setPosId(Integer posId) {
        this.posId = posId;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getClientOsType() {
        return clientOsType;
    }

    public void setClientOsType(Integer clientOsType) {
        this.clientOsType = clientOsType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientOsVersion() {
        return clientOsVersion;
    }

    public void setClientOsVersion(String clientOsVersion) {
        this.clientOsVersion = clientOsVersion;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
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

    public Double getScreenDensity() {
        return screenDensity;
    }

    public void setScreenDensity(Double screenDensity) {
        this.screenDensity = screenDensity;
    }

    public String getAndid() {
        return andid;
    }

    public void setAndid(String andid) {
        this.andid = andid;
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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(String netStatus) {
        this.netStatus = netStatus;
    }

    public String getNetIsp() {
        return netIsp;
    }

    public void setNetIsp(String netIsp) {
        this.netIsp = netIsp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getClientVendor() {
        return clientVendor;
    }

    public void setClientVendor(String clientVendor) {
        this.clientVendor = clientVendor;
    }

    public String getClientModel() {
        return clientModel;
    }

    public void setClientModel(String clientModel) {
        this.clientModel = clientModel;
    }

    public BidRequest(){}
    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this.posId = builder.posId;
        this.thumbWidth = builder.thumbWidth;
        this.thumbHeight = builder.thumbHeight;
        this.timestamp = builder.timestamp;
        this.platform = builder.platform;
        this.device = builder.device;
        this.version = builder.version;
        this.clientType = builder.clientType;
        this.clientOsType = builder.clientOsType;
        this.ip = builder.ip;
        this.clientOsVersion = builder.clientOsVersion;
        this.ua = builder.ua;
        this.screenWidth = builder.screenWidth;
        this.screenHeight = builder.screenHeight;
        this.andid = builder.andid;
        this.idfa = builder.idfa;
        this.idfv = builder.idfv;
        this.mac = builder.mac;
        this.netStatus = builder.netStatus;
        this.netIsp = builder.netIsp;
        this.signature = builder.signature;
        this.clientVendor = builder.clientVendor;
        this.clientModel = builder.clientModel;
    }
    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}
    @Generated("SparkTools")
    public static final class Builder{
        private Integer posId;
        private Integer thumbWidth;
        private Integer thumbHeight;
        private Long timestamp;
        private Integer platform;
        private String device;
        private String version;
        private Integer clientType;
        private Integer clientOsType;
        private String ip;
        private String clientOsVersion;
        private String ua;
        private Integer screenWidth;
        private Integer screenHeight;
        private Double screenDensity;
        private String andid;
        private String idfa;
        private String idfv;
        private String mac;
        private String netStatus;
        private String netIsp;
        private String signature;
        private String clientVendor;
        private String clientModel;

        public Builder withPosId(Integer posId){
            this.posId = posId;
            return this;
        }
        public Builder withThumbWidth(Integer thumbWidth){
            this.thumbWidth = thumbWidth;
            return this;
        }
        public Builder withThumbHeight(Integer thumbHeight){
            this.thumbHeight = thumbHeight;
            return this;
        }
        public Builder withTimestamp(Long timestamp){
            this.timestamp = timestamp;
            return this;
        }
        public Builder withPlatform(Integer platform){
            this.platform = platform;
            return this;
        }
        public Builder withDevice(String device){
            this.device = device;
            return this;
        }
        public Builder withVersion(String version){
            this.version = version;
            return this;
        }
        public Builder withClientType(Integer clientType){
            this.clientType = clientType;
            return this;
        }
        public Builder withClientOsType(Integer clientOsType){
            this.clientOsType = clientOsType;
            return this;
        }
        public Builder withIp(String ip){
            this.ip = ip;
            return this;
        }
        public Builder withClientOsVersion(String clientOsVersion){
            this.clientOsVersion = clientOsVersion;
            return this;
        }
        public Builder withUa(String ua){
            this.ua = ua;
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
        public Builder withScreenDensity(Double screenDensity){
            this.screenDensity = screenDensity;
            return this;
        }
        public Builder withAndid(String andid){
            this.andid = andid;
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
        public Builder withMac(String mac){
            this.mac = mac;
            return this;
        }
        public Builder withNetStatus(String netStatus){
            this.netStatus = netStatus;
            return this;
        }
        public Builder withNetIsp(String netIsp){
            this.netIsp = netIsp;
            return this;
        }
        public Builder withSignature(String signature){
            this.signature = signature;
            return this;
        }
        public Builder withClientVendor(String clientVendor){
            this.clientVendor = clientVendor;
            return this;
        }
        public Builder withClientModel(String clientModel){
            this.clientModel = clientModel;
            return this;
        }

        public Builder(){}

        public BidRequest build(){return new BidRequest(this);}
    }

    public enum PlatformType{
        IOS(1),ANDROID(2);
        private Integer value;
        PlatformType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    public enum DeviceType{
        PHONE(0),TABLE(1);
        private Integer value;
        DeviceType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    public enum OSType{
        IOS(1),ANDROID(0);
        private Integer value;
        OSType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }
    public enum NetType{
        WIFI("WIFI"),K2G("2g"),K3G("3g"),K4G("4g"),K5G("5g");
        private String value;
        NetType(String value){this.value = value;}
        public String getValue() {
            return value;
        }
    }

    /**
     * 运营商代号
     */
    public enum CarrierType{
        MOBILE_GSM("46000"),MOBILE_TD_S2("46002"),MOBILE_TD_S7("46007"),UNION_GSM("46001"),TELECOM_CDMA("46003"),TELECOM_CDMA5("46005"),UNION_WCDMA("46006");
        private String value;
        CarrierType(String value){this.value = value;}
        public String getValue() {
            return value;
        }
    }
}

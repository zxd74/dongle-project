package com.iwanvi.adserv.ngx.router.broker.taido;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-01 17:13
 */
public class BidRequest {

    /**
     * 广告位信息 ：必填
     */
    @JSONField(name = "AdInfo")
    private AdInfo adInfo;
    /**
     * 客户端信息 ：必填
     */
    @JSONField(name = "ClientInfo")
    private ClientInfo clientInfo;
    /**
     * 用户信息 ：必填
     */
    @JSONField(name = "UserInfo")
    private UserInfo userInfo;
    /**
     * api版本 ：必填
     */
    private String apiVer;

    public AdInfo getAdInfo() {
        return adInfo;
    }

    public void setAdInfo(AdInfo adInfo) {
        this.adInfo = adInfo;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getApiVer() {
        return apiVer;
    }

    public void setApiVer(String apiVer) {
        this.apiVer = apiVer;
    }

    public BidRequest(){}
    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this.adInfo = builder.adInfo;
        this.clientInfo = builder.clientInfo;
        this.userInfo = builder.userInfo;
        this.apiVer = builder.apiVer;
    }
    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}
    @Generated("SparkTools")
    public static final class Builder{
        private AdInfo adInfo;
        private ClientInfo clientInfo;
        private UserInfo userInfo;
        private String apiVer;

        public Builder withAdInfo(AdInfo adInfo){
            this.adInfo = adInfo;
            return this;
        }
        public Builder withClientInfo(ClientInfo clientInfo){
            this.clientInfo = clientInfo;
            return this;
        }
        public Builder withUserInfo(UserInfo userInfo){
            this.userInfo = userInfo;
            return this;
        }
        public Builder withApiVersion(String apiVer){
            this.apiVer = apiVer;
            return this;
        }

        public Builder(){}

        public BidRequest build(){return new BidRequest(this);}
    }

    /**
     * 广告位信息
     */
    public static class AdInfo{
        /**
         * 广告位id ：必填
         */
        private String adid;
        /**
         * 广告位类型 ：必填
         */
        private Integer adtype;
        /**
         * 广告位类型 ：必填
         */
        private Integer adnum;
        /**
         * 广告位的宽度 ：必填
         */
        private Integer adwidth;
        /**
         * 广告位的高度 ：必填
         */
        private Integer adheight;

        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public Integer getAdtype() {
            return adtype;
        }

        public void setAdtype(Integer adtype) {
            this.adtype = adtype;
        }

        public Integer getAdnum() {
            return adnum;
        }

        public void setAdnum(Integer adnum) {
            this.adnum = adnum;
        }

        public Integer getAdwidth() {
            return adwidth;
        }

        public void setAdwidth(Integer adwidth) {
            this.adwidth = adwidth;
        }

        public Integer getAdheight() {
            return adheight;
        }

        public void setAdheight(Integer adheight) {
            this.adheight = adheight;
        }

        public AdInfo(){}
        @Generated("SparkTools")
        private AdInfo(Builder builder){
            this.adid = builder.adid;
            this.adtype = builder.adtype;
            this.adnum = builder.adnum;
            this.adwidth = builder.adwidth;
            this.adheight = builder.adheight;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String adid;
            private Integer adtype;
            private Integer adnum;
            private Integer adwidth;
            private Integer adheight;

            public Builder withAdId(String adId){
                this.adid = adId;
                return this;
            }
            public Builder withAdType(Integer adtype){
                this.adtype = adtype;
                return this;
            }
            public Builder withAdNum(Integer adnum){
                this.adnum = adnum;
                return this;
            }
            public Builder withAdWidth(Integer adwidth){
                this.adwidth = adwidth;
                return this;
            }
            public Builder withAdHeight(Integer adheight){
                this.adheight = adheight;
                return this;
            }
            public Builder(){}

            public AdInfo build(){return new AdInfo(this);}
        }
    }
    public static class ClientInfo{
        /**
         * 移动设备品牌 ：必填
         */
        private String brand;
        /**
         * 移动设备的型号 ：必填
         */
        private String model;
        /**
         * 终端用户设备的 UA ：必填
         */
        private String ua;
        /**
         * 操作系统版本 ：必填
         */
        private String osversion;
        /**
         * 操作系统 ：必填
         */
        private Integer os;
        /**
         * 操作系统 ：必填
         */
        private String mac;
        /**
         * android_id，对广告填充有影响 。安卓系统必填
         */
        private String anid;
        /**
         * 移动终端标识，明文标识。安卓系统必填
         */
        private String imei;
        /**
         * 移动终端标识，明文标识, ios系统必填，
         */
        private String idfa;
        /**
         * 联网方式 ：必填
         */
        private Integer net;
        /**
         * 运营商 ：必填
         */
        private Integer carrier;
        /**
         * 设备类型 ：必填
         */
        private Integer type;
        /**
         * 横竖屏 ：必填
         */
        private Integer direct;
        /**
         * 屏幕宽,取设备物理像素 ：必填
         */
        private Integer sw;
        /**
         * 屏幕高,设备物理像素。 ：必填
         */
        private Integer sh;
        /**
         * 屏幕密度,尽量填写
         */
        private Double density;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public String getOsversion() {
            return osversion;
        }

        public void setOsversion(String osversion) {
            this.osversion = osversion;
        }

        public Integer getOs() {
            return os;
        }

        public void setOs(Integer os) {
            this.os = os;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getAnid() {
            return anid;
        }

        public void setAnid(String anid) {
            this.anid = anid;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getIdfa() {
            return idfa;
        }

        public void setIdfa(String idfa) {
            this.idfa = idfa;
        }

        public Integer getNet() {
            return net;
        }

        public void setNet(Integer net) {
            this.net = net;
        }

        public Integer getCarrier() {
            return carrier;
        }

        public void setCarrier(Integer carrier) {
            this.carrier = carrier;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getDirect() {
            return direct;
        }

        public void setDirect(Integer direct) {
            this.direct = direct;
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

        public Double getDensity() {
            return density;
        }

        public void setDensity(Double density) {
            this.density = density;
        }

        public ClientInfo(){}
        @Generated("SparkTools")
        private ClientInfo(Builder builder){
            this.brand = builder.brand;
            this.model = builder.model;
            this.ua = builder.ua;
            this.osversion = builder.osversion;
            this.os = builder.os;
            this.mac = builder.mac;
            this.anid = builder.anid;
            this.imei = builder.imei;
            this.idfa = builder.idfa;
            this.net = builder.net;
            this.carrier = builder.carrier;
            this.type = builder.type;
            this.direct = builder.direct;
            this.sw = builder.sw;
            this.sh = builder.sh;
            this.density = builder.density;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String brand;
            private String model;
            private String ua;
            private String osversion;
            private Integer os;
            private String mac;
            private String anid;
            private String imei;
            private String idfa;
            private Integer net;
            private Integer carrier;
            private Integer type;
            private Integer direct;
            private Integer sw;
            private Integer sh;
            private Double density;

            public Builder withBrand(String brand){
                this.brand = brand;
                return this;
            }
            public Builder withModel(String model){
                this.model = model;
                return this;
            }
            public Builder withUa(String ua){
                this.ua = ua;
                return this;
            }
            public Builder withOsVersion(String osversion){
                this.osversion = osversion;
                return this;
            }
            public Builder withOs(Integer os){
                this.os = os;
                return this;
            }
            public Builder withMac(String mac){
                this.mac = mac;
                return this;
            }
            public Builder withAndroidId(String anid){
                this.anid = anid;
                return this;
            }
            public Builder withImei(String imei){
                this.imei = imei;
                return this;
            }
            public Builder withIdfa(String idfa){
                this.idfa = idfa;
                return this;
            }
            public Builder withNet(Integer net){
                this.net = net;
                return this;
            }
            public Builder withCarrier(Integer carrier){
                this.carrier = carrier;
                return this;
            }
            public Builder withType(Integer type){
                this.type = type;
                return this;
            }
            public Builder withDirect(Integer direct){
                this.direct = direct;
                return this;
            }
            public Builder withScreenWidth(Integer sw){
                this.sw = sw;
                return this;
            }
            public Builder withScreenHeightd(Integer sh){
                this.sh = sh;
                return this;
            }
            public Builder withDensity(Double density){
                this.density = density;
                return this;
            }

            public Builder(){}

            public ClientInfo build(){return new ClientInfo(this);}
        }
    }
    public static class UserInfo{
        /**
         * 客户端访问ip ：必填
         */
        private String ip;
        /**
         * 纬度
         */
        private Double lat;
        /**
         * 经度
         */
        private Double lng;
        /**
         * 当前还有历史使用的热点wifi名称,多个返回用||间隔
         */
        private String wifi;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLng() {
            return lng;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public String getWifi() {
            return wifi;
        }

        public void setWifi(String wifi) {
            this.wifi = wifi;
        }

        public UserInfo(){}
        @Generated("SparkTools")
        private UserInfo(Builder builder){
            this.ip = builder.ip;
            this.lat = builder.lat;
            this.lng = builder.lng;
            this.wifi = builder.wifi;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String ip;
            private Double lat;
            private Double lng;
            private String wifi;

            public Builder withIp(String ip){
                this.ip = ip;
                return this;
            }
            public Builder withLat(Double lat){
                this.lat = lat;
                return this;
            }
            public Builder withLng(Double lng){
                this.lng = lng;
                return this;
            }
            public Builder withWifi(String wifi){
                this.wifi = wifi;
                return this;
            }
            public Builder(){}

            public UserInfo build(){return new UserInfo(this);}
        }
    }
}

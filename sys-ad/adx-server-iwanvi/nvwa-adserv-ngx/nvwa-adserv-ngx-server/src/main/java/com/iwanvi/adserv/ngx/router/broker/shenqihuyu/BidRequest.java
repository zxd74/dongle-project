package com.iwanvi.adserv.ngx.router.broker.shenqihuyu;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-06-13 12:30
 * @version: v1.0
 * @Description: 神奇互娱请求类
 */
public class BidRequest {
    /**
     * *唯一key 在Shenqi开发者平台注册广告位时生成
     * 必填
     * 参数名： magic_key
     */
    private String magic_key;
    /**
     * *包名
     * 必填
     * 参数名：package
     */
    private String packageName;
    /**
     * 应用名称
     * 参数名：app_name
     */
    private String app_name;
    /**
     * 应用版本
     * 参数名：app_ver
     */
    private String app_ver;
    /**
     * *宽
     * 必填
     */
    private Integer w;
    /**
     * 高
     * 必填
     */
    private Integer h;
    /**
     * 广告类型
     * 必填
     */
    private Integer type;
    /**
     * 客户端ip地址
     * 必填
     */
    private String ip;
    /**
     * 客户端ua
     * 必填
     */
    private String ua;
    /**
     * mac地址
     * 必填
     */
    private String mac;
    /**
     * 如果platform=android则必须传imei
     */
    private String imei;
    /**
     * 如果platform=android则必须传imsi
     */
    private String imsi;
    /**
     * 如果platform=android则必须传androidid
     */
    private String aid;
    /**
     * 如果platform=ios 则必须传值
     */
    private String idfa;
    /**
     * 如果platform=ios 则必须传值
     */
    private String idfv;
    /**
     * 系统 android, ios
     * 必填
     */
    private String platform;
    /**
     * 系统版本号 6.0.0
     * 必填
     * 参数名：os_vn
     */
    private String os_vn;
    /**
     * 网络类型
     * 必填
     */
    private Integer nt;
    /**
     * 运营商
     */
    private String carrier;
    /**
     * 纬度
     */
    private Float lat;
    /**
     * 经度
     */
    private Float lon;
    /**
     * 设备机型
     */
    private String model;
    /**
     * 设备品牌
     */
    private String brand;
    /**
     * 横竖屏 0代表竖屏 1代表横屏
     */
    private Integer orientation;

    public BidRequest(){}
    @Generated("SparksTools")
    private BidRequest(Builder builder){
        this.magic_key = builder.magicKey;
        this.packageName = builder.packageName;
        this.app_name = builder.appName;
        this.w = builder.w;
        this.h = builder.h;
        this.type = builder.type;
        this.ip = builder.ip;
        this.ua = builder.ua;
        this.mac = builder.mac;
        this.imei = builder.imei;
        this.imsi = builder.imsi;
        this.aid = builder.aid;
        this.idfa = builder.idfa;
        this.idfv = builder.idfv;
        this.platform = builder.platform;
        this.os_vn = builder.osVn;
        this.nt = builder.nt;
        this.carrier = builder.carrier;
        this.lat = builder.lat;
        this.lon = builder.lon;
        this.model = builder.model;
        this.brand = builder.brand;
        this.orientation = builder.orientation;
    }
    @Generated("SparksTools")
    public static Builder builder(){return new Builder();}
    @Generated("SparksTools")
    public static final class Builder{
        private String magicKey;
        private String packageName;
        private String appName;
        private String appVer;
        private Integer w;
        private Integer h;
        private Integer type;
        private String ip;
        private String ua;
        private String mac;
        private String imei;
        private String imsi;
        private String aid;
        private String idfa;
        private String idfv;
        private String platform;
        private String osVn;
        private Integer nt;
        private String carrier;
        private Float lat;
        private Float lon;
        private String model;
        private String brand;
        private Integer orientation;

        public Builder withMagicKey(String magicKey){
            this.magicKey = magicKey;
            return this;
        }
        public Builder withPackageName(String packageName){
            this.packageName = packageName;
            return this;
        }
        public Builder withAppName(String appName){
            this.appName = appName;
            return this;
        }
        public Builder withAppVersion(String appVer){
            this.appVer = appVer;
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
        public Builder withType(Integer type){
            this.type = type;
            return this;
        }
        public Builder withIP(String ip){
            this.ip = ip;
            return this;
        }
        public Builder withUa(String ua){
            this.ua = ua;
            return this;
        }
        public Builder withMac(String mac){
            this.mac = mac;
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
        public Builder withAid(String aid){
            this.aid = aid;
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
        public Builder withPlatform(String platform){
            this.platform = platform;
            return this;
        }
        public Builder withOsVersion(String osVn){
            this.osVn = osVn;
            return this;
        }
        public Builder withNetType(Integer nt){
            this.nt = nt;
            return this;
        }
        public Builder withCarrier(String carrier){
            this.carrier = carrier;
            return this;
        }
        public Builder withLat(Float lat){
            this.lat = lat;
            return this;
        }
        public Builder withLon(Float lon){
            this.lon = lon;
            return this;
        }
        public Builder withModel(String model){
            this.model = model;
            return this;
        }
        public Builder withBrand(String brand){
            this.brand = brand;
            return this;
        }
        public Builder withOrientation(Integer orientation){
            this.orientation = orientation;
            return this;
        }

        public Builder(){}

        public BidRequest build(){return new BidRequest(this);}

    }


}

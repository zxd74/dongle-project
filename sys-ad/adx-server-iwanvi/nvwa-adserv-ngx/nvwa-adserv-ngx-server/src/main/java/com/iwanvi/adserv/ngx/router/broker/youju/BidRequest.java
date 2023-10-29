package com.iwanvi.adserv.ngx.router.broker.youju;

import javax.annotation.Generated;
import java.util.stream.Stream;

/**
 * @author: 郑晓东
 * @date: 2019-07-31 10:20
 * @version: v1.0
 * @Description: 优聚请求类
 */
public class BidRequest {

    /**
     * 媒体id，标识请求的媒体 ：必填
     */
    private Integer mid;
    /**
     * 媒体应用id，标识请求的媒体应用 ：必填
     */
    private Integer aid;
    /**
     * 媒体应用请求广告位信息：必填
     * ‘{"25":{"w":100,"h":"200"},"26":[]}’，该请求含义为广告位25指定获取100*200尺寸素材广告，广告位26获取所配置默认尺寸素材广告
     */
    private String tagid;
    /**
     * 客户端IP地址：必填
     */
    private String ip;
    /**
     * 网卡MAC地址 ：安卓必填
     */
    private String mac;
    /**
     * 安卓手机设备的系统ID ：安卓必填
     */
    private String android_id;
    /**
     * 安卓手机设备的imei ：安卓必填
     */
    private String imei;
    /**
     * iOS设备的IDFA ：ios必填
     */
    private String idfa;
    /**
     * 媒体方用户ID ：必填
     */
    private String uid="";
    /**
     * 网络连接方式 ：必填
     */
    private Integer ct;
    /**
     * 运营商类型 ：必填
     */
    private Integer ca;
    /**
     * 操作系统类型 ：必填
     */
    private Integer ot;
    /**
     * 操作系统版本 ：必填
     * 需要urlencode
     */
    private String ov;
    /**
     * 设备类型 ：必填
     */
    private Integer dt;
    /**
     * 设备品牌 ：必填
     * 需要urlencode
     */
    private String db;
    /**
     * 设备型号 ：必填
     * 需要urlencode
     */
    private String dm;
    /**
     * User-Agent ：必填
     * 需要base64编码
     */
    private String ua;
    /**
     * 经度
     */
    private Double lgt;
    /**
     * 纬度
     */
    private Double lat;
    /**
     * 应用名称
     * 需要urlencode
     */
    private String an;
    /**
     * 应用版本
     * 需要urlencode
     */
    private String av;
    /**
     * 应用包名
     */
    private String ap;
    /**
     * 媒体是否支持DeepLink,默认不支持
     * （adx系统支持dp）
     */
    private Integer dl = 1;
    /**
     * 用户信息
     */
    private String tag;
    /**
     * 访问令牌 ：必填
     */
    private String token;
    /**
     * 请求签名 ：必填
     * 请求参数按照key正序排序，
     * 参数赋值拼接字符串
     * 拼接密钥
     * md5拼接的字符串为本次请求的签名
     */
    private String sign;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getCt() {
        return ct;
    }

    public void setCt(Integer ct) {
        this.ct = ct;
    }

    public Integer getCa() {
        return ca;
    }

    public void setCa(Integer ca) {
        this.ca = ca;
    }

    public Integer getOt() {
        return ot;
    }

    public void setOt(Integer ot) {
        this.ot = ot;
    }

    public String getOv() {
        return ov;
    }

    public void setOv(String ov) {
        this.ov = ov;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public Double getLgt() {
        return lgt;
    }

    public void setLgt(Double lgt) {
        this.lgt = lgt;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public Integer getDl() {
        return dl;
    }

    public void setDl(Integer dl) {
        this.dl = dl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public BidRequest() {
    }

    @Generated("SparkTools")
    private BidRequest(Builder builder) {
        this.mid = builder.mid;
        this.aid = builder.aid;
        this.tagid = builder.tagid;
        this.ip = builder.ip;
        this.mac = builder.mac;
        this.android_id = builder.android_id;
        this.imei = builder.imei;
        this.idfa = builder.idfa;
        this.uid = builder.uid;
        this.ct = builder.ct;
        this.ca = builder.ca;
        this.ot = builder.ot;
        this.ov = builder.ov;
        this.dt = builder.dt;
        this.db = builder.db;
        this.dm = builder.dm;
        this.ua = builder.ua;
        this.lgt = builder.lgt;
        this.lat = builder.lat;
        this.an = builder.an;
        this.av = builder.av;
        this.ap = builder.ap;
        this.dl = builder.dl;
        this.tag = builder.tag;
        this.token = builder.token;
        this.sign = builder.sign;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private Integer mid;
        private Integer aid;
        private String tagid;
        private String ip;
        private String mac;
        private String android_id;
        private String imei;
        private String idfa;
        private String uid;
        private Integer ct;
        private Integer ca;
        private Integer ot;
        private String ov;
        private Integer dt;
        private String db;
        private String dm;
        private String ua;
        private Double lgt;
        private Double lat;
        private String an;
        private String av;
        private String ap;
        private Integer dl;
        private String tag;
        private String token;
        private String sign;

        public Builder withMid(Integer mid) {
            this.mid = mid;
            return this;
        }

        public Builder withAid(Integer aid) {
            this.aid = aid;
            return this;
        }

        public Builder withTagid(String tagid) {
            this.tagid = tagid;
            return this;
        }

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder withMac(String mac) {
            this.mac = mac;
            return this;
        }

        public Builder withAndroidId(String android_id) {
            this.android_id = android_id;
            return this;
        }

        public Builder withImei(String imei) {
            this.imei = imei;
            return this;
        }

        public Builder withIdfa(String idfa) {
            this.idfa = idfa;
            return this;
        }

        public Builder withUid(String uid) {
            this.uid = uid;
            return this;
        }

        public Builder withNetType(Integer ct) {
            this.ct = ct;
            return this;
        }

        public Builder withCarrier(Integer ca) {
            this.ca = ca;
            return this;
        }

        public Builder withOsType(Integer ot) {
            this.ot = ot;
            return this;
        }

        public Builder withOsVersion(String ov) {
            this.ov = ov;
            return this;
        }

        public Builder withDeviceType(Integer dt) {
            this.dt = dt;
            return this;
        }

        public Builder withBrand(String db) {
            this.db = db;
            return this;
        }

        public Builder withModel(String dm) {
            this.dm = dm;
            return this;
        }

        public Builder withUa(String ua) {
            this.ua = ua;
            return this;
        }

        public Builder withLgt(Double lgt) {
            this.lgt = lgt;
            return this;
        }

        public Builder withLat(Double lat) {
            this.lat = lat;
            return this;
        }

        public Builder withAppName(String an) {
            this.an = an;
            return this;
        }

        public Builder withAppVersion(String av) {
            this.av = av;
            return this;
        }

        public Builder withAppPackage(String ap) {
            this.ap = ap;
            return this;
        }

        public Builder withDeepLink(Integer dl) {
            this.dl = dl;
            return this;
        }

        public Builder withTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder withToken(String token) {
            this.token = token;
            return this;
        }

        public Builder withSign(String sign) {
            this.sign = sign;
            return this;
        }

        public Builder() {
        }

        public BidRequest build() {
            return new BidRequest(this);
        }
    }

    /**
     * 广告位信息
     */
    public static class TagId {
        private PosId posId;

        public TagId(PosId posId) {
            this.posId = posId;
        }
    }

    /**
     * 广告位信息
     */
    public static class PosId {
        private Integer w;
        private Integer h;

        public PosId(Integer w, Integer h) {
            this.w = w;
            this.h = h;
        }
    }

    /**
     * 用户信息
     */
    public static class Tag {
        /**
         * 手机号
         */
        private String p;
        /**
         * 性别
         */
        private Integer sex;
        /**
         * 年龄
         */
        private Integer age;
        /**
         * 地区
         */
        private String area;
        /**
         * 栏目
         */
        private String c;
        /**
         * 子栏目
         */
        private String sc;

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public Tag() {
        }

        @Generated("SparkTools")
        private Tag(Builder builder) {
            this.p = builder.p;
            this.sex = builder.sex;
            this.age = builder.age;
            this.area = builder.area;
            this.c = builder.c;
            this.sc = builder.sc;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String p;
            private Integer sex;
            private Integer age;
            private String area;
            private String c;
            private String sc;

            public Builder withPhone(String p) {
                this.p = p;
                return this;
            }

            public Builder withSex(Integer sex) {
                this.sex = sex;
                return this;
            }

            public Builder withAge(Integer age) {
                this.age = age;
                return this;
            }

            public Builder withArea(String area) {
                this.area = area;
                return this;
            }

            public Builder withCat(String c) {
                this.c = c;
                return this;
            }

            public Builder withSubCat(String sc) {
                this.sc = sc;
                return this;
            }

            public Builder() {
            }

            public Tag build() {
                return new Tag(this);
            }
        }
    }
}

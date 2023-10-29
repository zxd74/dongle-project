package com.iwanvi.adserv.ngx.router.broker.youdao;

import javax.annotation.Generated;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-05-09 17:10
 * @version: v1.0
 * @Description: 有道请求类
 */
public class BidRequest {

    // 必填
    /**
     * 移动广告位 ID
     * 必填
     */
    private String id;
    /**
     * 当前应用的真实版本号，格式如：v1.2.3 或 1.2.3v 或 1.2.3等。需要大于等于在“开发者系统”–“样式信息”–“支持版本”输入框内配置的值，才会有广告返回
     * 必填
     */
    private String av;
    /**
     * 网络连接类型，（UNKNOWN, ETHERNET, WIFI, MO-BILE;），值可能为 0,1,2,3，分别以上几种广告连接状态
     * 必填
     */
    private Integer ct;
    /**
     * 子网络连接类型。当 ct 字段为 MOBILE，子网络为 2G 时为 11，3G 时值为 12，4G 时为 13；其它情况统一传入 0
     * 必填
     */
    private Integer dct;
    /**
     * 设备 ID，如 AndroidID 或 IDFA，要求明文大写
     * 必填
     */
    private String udid;
    /**
     * MD5 后的 AndroidID，MD5 算法可以使用 DigestU-tils.md5Hex，明文大写后 MD5，再大写
     * 必填
     */
    private String auidmd5;
    /**
     * IMEI（International Mobile Equipment Identity）是移动设备国际身份码的缩写
     * 必填
     */
    private String imei;
    /**
     * MD5 后的 IMEI，要求明文 MD5 后，再大写。udid，auidmd5，imei，imeimd5, aaid 至少传入一个
     * 必填
     */
    private String imeimd5;
    /**
     * 设备广告 id，AdvertisingID。udid，auidmd5，imei，imeimd5, aaid 至少传入一个
     * 必填
     */
    private String aaid;
    /**
     * 用户原始 IP 地址，当通过服务器请求广告时，必须传入此参数。客户端请求广告，无需传入此参数
     * 必填
     */
    private String rip;
    /**
     * 位置信息，GPS 或者网络位置，经纬度逗号分隔（经度在前，纬度在后），WGS-84 坐标系
     * 必填
     */
    private String ll;
    /**
     * 经纬度精确度，单位：米。若当前获取的 ll（位置信息）为小数点后 3 位/4 位小数，则填为 100 米/10 米，以此类推
     * 必填
     */
    private String lla;
    /**
     * 获取位置信息的时间与发起广告请求的时间差，单位为分钟
     * 必填
     */
    private String llt;
    /**
     * 定位所用的 provider，n(network) 为网络定位，g(gps) 为gps 定位,p(passive) 为其他 app 里面的定位信息，f(fused)为系统返回的最佳定位
     * 必填
     */
    private String llp;
    /**
     * wifi 信息，用户将当前连接或者附近的 wifi 的 ssid 和 mac传送过来，非当前连接无法获取 mac，格式上，
     *      第一个参数为当前 wifi 的 mac，第二个为当前 wifi 的 ssid，第三个以后就是其他网络的 mac 和 ssid，参数以逗号分隔，
     *      如：ac:f7:f3:a4:bc:5a，NetEase，ac:f7:f3:a4:bc:5a，liaofan，ac:f7:f3:a4:bc:5a
     * 必填
     */
    private String wifi;

    // 选填
    /**
     * 加密请求时使用此字段，其值为整个加密后的字段
     * 选填
     */
    private String s;
    /**
     * 加密请求时使用此字段，表明具体使用的加密方法
     * 选填
     */
    private Integer ydet;
    /**
     * 设备信息，格式为“Build.MANUFACTURER,Build.MODEL,Build.PRODUCT”，如 samsung,GT-S5830,GT-S5830
     * 选填
     */
    private String dn;
    /**
     * 时区，如：+0800
     * 选填
     */
    private String z;
    /**
     * 广告物料是否需要满足 HTTPS URL, 如果值为 1，表示需要返回物料 URL 为 HTTPS 协议 (如落地页、素材、展示点击 tracker 等 URL); 如果无此参数，表示无 HTTPS 要求，但广告物料 URL 可能为 HTTPS
     * 选填
     */
    private String isSecure;
    /**
     * 操作系统版本信息，如：3.1.2
     * 选填
     */
    private String osv;
    /**
     * 竖 屏 横 屏, 可 能 值 分 别 为:p,l,s,u; u: 未知,p:portrait,l:landscape,s:square
     * 选填
     */
    private String o;
    /**
     * 国家类型，如中国 460, 参考Mobilecountry code
     * 选填
     */
    private String mcc;
    /**
     * 运营商，如移动 00 , 联通 01, 参考Mobilecountry code
     * 选填
     */
    private String mnc;
    /**
     * 国家代号，值如 cn
     * 选填
     */
    private String iso;
    /**
     * 运营商名，值可能为‘中国联通’
     * 选填
     */
    private String cn;
    /**
     * 小区码
     * 选填
     */
    private String lac;
    /**
     * 基站码，更加准确定位位置
     * 选填
     */
    private String cid;
    /**
     *  一次请求的广告数量，默认值为 1。如果大于 1 为批量广告 请求（请注意广告返回格式），建议一次不要请求太多广告，如果有需求可以分批取
     * 选填
     */
    private Integer ran;
    /**
     * 在同一个信息流广告会话中，会将最新加载的广告推广创 意的 ID 都传送给服务端，以便服务端进行广告去重。
     *      示例： cids=1,2,3。那么本次请求将不会返回变体 id 为 1, 或者 2 或者 3 的广告
     * 选填
     */
    private List<String> cids;
    /**
     * 当希望访问 clktracker 后跳转到 clk，即通过点击跟踪链接 由有道的服务跳转到落地页时，可以通过 isrd=1 来启用此 参数。如果启用此参数，无须通过访问 clk 进入落地页
     * 选填
     */
    private Integer isrd;
    /**
     * 设备屏幕物理尺寸高度，单位：像素。
     * 选填
     */
    private Integer sc_h;
    /**
     * 设备屏幕物理尺寸宽度，单位：像素。
     * 选填
     */
    private Integer sc_w;
    /**
     * 屏 幕 分 辨 率， 值 如：1.0. Android 平 台 参考DisplayMetrics.density, iOS 平台参考UIScreen.scale
     * 选填
     */
    private Double sc_a;
    /**
     * 设备屏幕像素密度。
     * 选填
     */
    private Integer sc_ppi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }

    public Integer getCt() {
        return ct;
    }

    public void setCt(Integer ct) {
        this.ct = ct;
    }

    public Integer getDct() {
        return dct;
    }

    public void setDct(Integer dct) {
        this.dct = dct;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getAuidmd5() {
        return auidmd5;
    }

    public void setAuidmd5(String auidmd5) {
        this.auidmd5 = auidmd5;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImeimd5() {
        return imeimd5;
    }

    public void setImeimd5(String imeimd5) {
        this.imeimd5 = imeimd5;
    }

    public String getAaid() {
        return aaid;
    }

    public void setAaid(String aaid) {
        this.aaid = aaid;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getLl() {
        return ll;
    }

    public void setLl(String ll) {
        this.ll = ll;
    }

    public String getLla() {
        return lla;
    }

    public void setLla(String lla) {
        this.lla = lla;
    }

    public String getLlt() {
        return llt;
    }

    public void setLlt(String llt) {
        this.llt = llt;
    }

    public String getLlp() {
        return llp;
    }

    public void setLlp(String llp) {
        this.llp = llp;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Integer getYdet() {
        return ydet;
    }

    public void setYdet(Integer ydet) {
        this.ydet = ydet;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getIsSecure() {
        return isSecure;
    }

    public void setIsSecure(String isSecure) {
        this.isSecure = isSecure;
    }

    public String getOsv() {
        return osv;
    }

    public void setOsv(String osv) {
        this.osv = osv;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getRan() {
        return ran;
    }

    public void setRan(Integer ran) {
        this.ran = ran;
    }

    public List<String> getCids() {
        return cids;
    }

    public void setCids(List<String> cids) {
        this.cids = cids;
    }

    public Integer getIsrd() {
        return isrd;
    }

    public void setIsrd(Integer isrd) {
        this.isrd = isrd;
    }

    public Integer getSc_h() {
        return sc_h;
    }

    public void setSc_h(Integer sc_h) {
        this.sc_h = sc_h;
    }

    public Integer getSc_w() {
        return sc_w;
    }

    public void setSc_w(Integer sc_w) {
        this.sc_w = sc_w;
    }

    public Double getSc_a() {
        return sc_a;
    }

    public void setSc_a(Double sc_a) {
        this.sc_a = sc_a;
    }

    public Integer getSc_ppi() {
        return sc_ppi;
    }

    public void setSc_ppi(Integer sc_ppi) {
        this.sc_ppi = sc_ppi;
    }

    public BidRequest(){}

    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this.id = builder.id;
        this.av = builder.av;
        this.ct = builder.ct;
        this.dct = builder.dct;
        this.udid = builder.udid;
        this.auidmd5 = builder.auidmd5;
        this.imei = builder.imei;
        this.imeimd5 = builder.imeimd5;
        this.aaid = builder.aaid;
        this.rip = builder.rip;
        this.ll = builder.ll;
        this.lla = builder.lla;
        this.llt = builder.llt;
        this.llp = builder.llp;
        this.wifi = builder.wifi;
        this.dn = builder.dn;
        this.z = builder.z;
        this.isSecure = builder.isSecure;
        this.osv = builder.osv;
        this.o = builder.o;
        this.mcc = builder.mcc;
        this.mnc = builder.mnc;
        this.iso = builder.iso;
        this.cn = builder.cn;
        this.lac = builder.lac;
        this.cid = builder.cid;
        this.ran = builder.ran;
        this.cids = builder.cids;
        this.isrd = builder.isrd;
        this.sc_h = builder.sc_h;
        this.sc_w = builder.sc_w;
        this.sc_a = builder.sc_a;
        this.sc_ppi = builder.sc_ppi;
    }

    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}

    @Generated("SparkTools")
    public static final class Builder{
        private String id;
        private String av;
        private Integer ct;
        private Integer dct;
        private String udid;
        private String auidmd5;
        private String imei;
        private String imeimd5;
        private String aaid;
        private String rip;
        private String ll;
        private String lla;
        private String llt;
        private String llp;
        private String wifi;

        private String dn;
        private String z;
        private String isSecure;
        private String osv;
        private String o;
        private String mcc;
        private String mnc;
        private String iso;
        private String cn;
        private String lac;
        private String cid;
        private Integer ran;
        private List<String> cids;
        private Integer isrd;
        private Integer sc_h;
        private Integer sc_w;
        private Double sc_a;
        private Integer sc_ppi;

        public Builder withId(String id){
            this.id = id;
            return this;
        }
        public Builder withAv(String av){
            this.av = av;
            return this;
        }
        public Builder withCt(Integer ct){
            this.ct = ct;
            return this;
        }
        public Builder withDct(Integer dct){
            this.dct = dct;
            return this;
        }
        public Builder withUdid(String udid){
            this.udid = udid;
            return this;
        }
        public Builder withAuidMD5(String auidmd5){
            this.auidmd5 = auidmd5;
            return this;
        }
        public Builder withIMEI(String imei){
            this.imei = imei;
            return this;
        }
        public Builder withIMEIMD5(String imeimd5){
            this.imeimd5 = imeimd5;
            return this;
        }
        public Builder withAaid(String aaid){
            this.aaid = aaid;
            return this;
        }
        public Builder withRip(String rip){
            this.rip = rip;
            return this;
        }
        public Builder withLl(String ll){
            this.ll = ll;
            return this;
        }
        public Builder withLla(String lla){
            this.lla = lla;
            return this;
        }
        public Builder withLlt(String llt){
            this.llt = llt;
            return this;
        }
        public Builder withLlp(String llp){
            this.llp = llp;
            return this;
        }
        public Builder withWifi(String wifi){
            this.wifi = wifi;
            return this;
        }

        public Builder withDn(String dn){
            this.dn = dn;
            return this;
        }
        public Builder withZ(String z){
            this.z = z;
            return this;
        }
        public Builder withIsSecure(String isSecure){
            this.isSecure = isSecure;
            return this;
        }
        public Builder withOsv(String osv){
            this.osv = osv;
            return this;
        }
        public Builder withO(String o){
            this.o = o;
            return this;
        }
        public Builder withMcc(String mcc){
            this.mcc = mcc;
            return this;
        }
        public Builder withMnc(String mnc){
            this.mnc = mnc;
            return this;
        }
        public Builder withIso(String iso){
            this.iso = iso;
            return this;
        }
        public Builder withCn(String cn){
            this.cn = cn;
            return this;
        }
        public Builder withLac(String lac){
            this.lac = lac;
            return this;
        }
        public Builder withCid(String cid){
            this.cid = cid;
            return this;
        }
        public Builder withRan(Integer ran){
            this.ran = ran;
            return this;
        }
        public Builder withCids(List<String> cids){
            this.cids = cids;
            return this;
        }
        public Builder withIsrd(Integer isrd){
            this.isrd = isrd;
            return this;
        }
        public Builder withScHeight(Integer sc_h){
            this.sc_h = sc_h;
            return this;
        }
        public Builder withScWidth(Integer sc_w){
            this.sc_w = sc_w;
            return this;
        }
        public Builder withScA(Double sc_a){
            this.sc_a = sc_a;
            return this;
        }
        public Builder withScPpi(Integer sc_ppi){
            this.sc_ppi = sc_ppi;
            return this;
        }

        public Builder(){}

        public BidRequest build(){return new BidRequest(this);}
    }

    /**
     * 运营商名
     */
    public enum Carrier{
        CHINA_MOBILE("中国移动"),CHINA_TELECOM("中国电信"),CHINA_UNICOM("中国联通");
        private String value;
        Carrier(String value){this.value = value;}
        public String getValue(){
            return this.value;
        }
    }

    /**
     * 网络连接类型
     *      UNKNOWN == 0 未知
     *      ETHERNET == 1 以太网
     *      WIFI == 2 WIFI
     *      MOBILE == 3 移动
     */
    public enum NetType{
        UNKNOWN(0), ETHERNET(1),WIFI(2),MOBILE(3);
        private Integer value;
        NetType(Integer value){this.value = value;}
        public Integer getValue(){return this.value;}
    }

    /**
     * 子网类型
     *      K_2G == 11 2G
     *      K_3G == 12 3G
     *      K_4G == 13 4G
     *      K_UNKNOWN == 0 其它
     */
    public enum SubNetType{
        K_2G(11),K_3G(12),K_4G(13),K_UNKNOWN(0);
        private Integer value;
        SubNetType(Integer value){this.value = value;}
        public Integer getValue(){return this.value;}
    }
}

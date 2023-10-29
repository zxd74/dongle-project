package com.iwanvi.adserv.ngx.router.broker.mifu;

import javax.annotation.Generated;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-06 09:55
 */
public class BidRequest {

    /**
     * 请求类型，固定为24
     */
    private Integer _t = 24;
    /**
     * 注册在MT系统的广告位ID 必填
     */
    private String _a;
    private String _pgn;
    private String _appname;
    private String _appversion;
    private String _ua;
    /**
     * 网络类型。
     */
    private Integer _nt;
    /**
     * 运营商类型。
     */
    private Integer _o;
    private Integer _os;
    private String _osv;
    /**
     * 设备品牌 brand
     */
    private String _dev;
    /**
     * 设备型号 model
     */
    private String _md;
    /**
     *  Android操作系统原始设备号MD5加密 android必填
     */
    private String _imei;
    /**
     * Android操作系统原始设备号  android必填
     */
    private String _imeio;
    /**
     * 硬件终端地址。 MAC  android必填
     */
    private String _mc;
    /**
     * AndroidID 必填
     */
    private String _aid;
    /**
     * Android Advertiser ID
     */
    private String _aaid;
    /**
     * 必填
     */
    private String _idfa;
    /**
     * iOS 终端设备的OpenUDID 推荐
     */
    private String _oid;
    private Integer _w;
    private Integer _h;
    /**
     * 经度
     */
    private Double _lon;
    /**
     * 纬度
     */
    private Double _lat;
    /**
     * 设备的ip地址。
     */
    private String _ip;
    /**
     * 安全流量与否 （http|https）
     */
    private Integer _sc;
    /**
     * 屏幕密度 非必填
     */
    private Double _pr;
    /**
     * 屏幕方向，0-未知，1-竖屏，2-横屏 非必填
     */
    private Integer _ort = 1;
    /**
     * 时区，用户所处时区的分钟偏移量，默认480：非必填
     */
    private Integer _tz = 480;

    public Integer get_t() {
        return _t;
    }

    public void set_t(Integer _t) {
        this._t = _t;
    }

    public String get_a() {
        return _a;
    }

    public void set_a(String _a) {
        this._a = _a;
    }

    public String get_pgn() {
        return _pgn;
    }

    public void set_pgn(String _pgn) {
        this._pgn = _pgn;
    }

    public String get_appname() {
        return _appname;
    }

    public void set_appname(String _appname) {
        this._appname = _appname;
    }

    public String get_appversion() {
        return _appversion;
    }

    public void set_appversion(String _appversion) {
        this._appversion = _appversion;
    }

    public String get_ua() {
        return _ua;
    }

    public void set_ua(String _ua) {
        this._ua = _ua;
    }

    public Integer get_nt() {
        return _nt;
    }

    public void set_nt(Integer _nt) {
        this._nt = _nt;
    }

    public Integer get_o() {
        return _o;
    }

    public void set_o(Integer _o) {
        this._o = _o;
    }

    public Integer get_os() {
        return _os;
    }

    public void set_os(Integer _os) {
        this._os = _os;
    }

    public String get_osv() {
        return _osv;
    }

    public void set_osv(String _osv) {
        this._osv = _osv;
    }

    public String get_dev() {
        return _dev;
    }

    public void set_dev(String _dev) {
        this._dev = _dev;
    }

    public String get_md() {
        return _md;
    }

    public void set_md(String _md) {
        this._md = _md;
    }

    public String get_imei() {
        return _imei;
    }

    public void set_imei(String _imei) {
        this._imei = _imei;
    }

    public String get_imeio() {
        return _imeio;
    }

    public void set_imeio(String _imeio) {
        this._imeio = _imeio;
    }

    public String get_mc() {
        return _mc;
    }

    public void set_mc(String _mc) {
        this._mc = _mc;
    }

    public String get_aid() {
        return _aid;
    }

    public void set_aid(String _aid) {
        this._aid = _aid;
    }

    public String get_aaid() {
        return _aaid;
    }

    public void set_aaid(String _aaid) {
        this._aaid = _aaid;
    }

    public String get_idfa() {
        return _idfa;
    }

    public void set_idfa(String _idfa) {
        this._idfa = _idfa;
    }

    public String get_oid() {
        return _oid;
    }

    public void set_oid(String _oid) {
        this._oid = _oid;
    }

    public Integer get_w() {
        return _w;
    }

    public void set_w(Integer _w) {
        this._w = _w;
    }

    public Integer get_h() {
        return _h;
    }

    public void set_h(Integer _h) {
        this._h = _h;
    }

    public Double get_lon() {
        return _lon;
    }

    public void set_lon(Double _lon) {
        this._lon = _lon;
    }

    public Double get_lat() {
        return _lat;
    }

    public void set_lat(Double _lat) {
        this._lat = _lat;
    }

    public String get_ip() {
        return _ip;
    }

    public void set_ip(String _ip) {
        this._ip = _ip;
    }

    public Integer get_sc() {
        return _sc;
    }

    public void set_sc(Integer _sc) {
        this._sc = _sc;
    }

    public Double get_pr() {
        return _pr;
    }

    public void set_pr(Double _pr) {
        this._pr = _pr;
    }

    public Integer get_ort() {
        return _ort;
    }

    public void set_ort(Integer _ort) {
        this._ort = _ort;
    }

    public Integer get_tz() {
        return _tz;
    }

    public void set_tz(Integer _tz) {
        this._tz = _tz;
    }

    public BidRequest(){}

    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this._a = builder._a;
        this._pgn = builder._pgn;
        this._appname = builder._appname;
        this._appversion = builder._appversion;
        this._ua = builder._ua;
        this._nt = builder._nt;
        this._o = builder._o;
        this._os = builder._os;
        this._osv = builder._osv;
        this._dev = builder._dev;
        this._md = builder._md;
        this._imei = builder._imei;
        this._imeio = builder._imeio;
        this._mc = builder._mc;
        this._aid = builder._aid;
        this._aaid = builder._aaid;
        this._idfa = builder._idfa;
        this._oid = builder._oid;
        this._w = builder._w;
        this._h = builder._h;
        this._lon = builder._lon;
        this._lat = builder._lat;
        this._ip = builder._ip;
        this._sc = builder._sc;
        this._pr = builder._pr;
    }

    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}

    @Generated("SparkTools")
    public static final class Builder{
        private String _a;
        private String _pgn;
        private String _appname;
        private String _appversion;
        private String _ua;
        private Integer _nt;
        private Integer _o;
        private Integer _os;
        private String _osv;
        private String _dev;
        private String _md;
        private String _imei;
        private String _imeio;
        private String _mc;
        private String _aid;
        private String _aaid;
        private String _idfa;
        private String _oid;
        private Integer _w;
        private Integer _h;
        private Double _lon;
        private Double _lat;
        private String _ip;
        private Integer _sc;
        private Double _pr;

        public Builder withPosId(String _a){
            this._a = _a;
            return this;
        }
        public Builder withPackage(String _pgn){
            this._pgn = _pgn;
            return this;
        }
        public Builder withAppName(String _appname){
            this._appname = _appname;
            return this;
        }
        public Builder withAppVersion(String _appversion){
            this._appversion = _appversion;
            return this;
        }
        public Builder withUa(String _ua){
            this._ua = _ua;
            return this;
        }
        public Builder withNetType(Integer _nt){
            this._nt = _nt;
            return this;
        }
        public Builder withCarrier(Integer _o){
            this._o = _o;
            return this;
        }
        public Builder withOs(Integer _os){
            this._os = _os;
            return this;
        }
        public Builder withOsVersion(String _osv){
            this._osv = _osv;
            return this;
        }
        public Builder withBrand(String _dev){
            this._dev = _dev;
            return this;
        }
        public Builder withModel(String _md){
            this._md = _md;
            return this;
        }
        public Builder withImeiMD5(String _imei){
            this._imei = _imei;
            return this;
        }
        public Builder withImei(String _imeio){
            this._imeio = _imeio;
            return this;
        }
        public Builder withMac(String _mc){
            this._mc = _mc;
            return this;
        }
        public Builder withAndroidId(String _aid){
            this._aid = _aid;
            return this;
        }
        public Builder withAaID(String _aaid){
            this._aaid = _aaid;
            return this;
        }
        public Builder withIdfa(String _idfa){
            this._idfa = _idfa;
            return this;
        }
        public Builder withOpenUdId(String _oid){
            this._oid = _oid;
            return this;
        }
        public Builder withWidth(Integer _w){
            this._w = _w;
            return this;
        }
        public Builder withHeight(Integer _h){
            this._h = _h;
            return this;
        }
        public Builder withLon(Double _lon){
            this._lon = _lon;
            return this;
        }
        public Builder withLat(Double _lat){
            this._lat = _lat;
            return this;
        }
        public Builder withIp(String _ip){
            this._ip = _ip;
            return this;
        }
        public Builder withSource(Integer _sc){
            this._sc = _sc;
            return this;
        }
        public Builder withDensity(Double _pr){
            this._pr = _pr;
            return this;
        }

        public BidRequest build(){return new BidRequest(this);}
    }
}

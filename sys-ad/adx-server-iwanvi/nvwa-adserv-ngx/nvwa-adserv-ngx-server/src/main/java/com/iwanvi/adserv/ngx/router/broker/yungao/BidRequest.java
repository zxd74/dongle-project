package com.iwanvi.adserv.ngx.router.broker.yungao;

import javax.annotation.Generated;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-09 13:47
 */
public class BidRequest {

    /**
     * 请求ID  推荐md5（app_id+adslot_id+13 位时间戳）
     */
    private String request_id;
    /**
     * api版本
     */
    private String api_version;
    /**
     * 媒体类型
     */
    private Integer media_type;
    /**
     * 请求协议，可选
     */
    private Integer request_protocol_type;
    /**
     * 请求时间戳
     */
    private String request_time;

    /**
     * 应用ID
     */
    private String app_id;
    /**
     * 应用版本
     */
    private String app_version;
    /**
     * 应用包名
     */
    private String app_package;

    /**
     * 广告位ID
     */
    private String adslot_id;

    /**
     * 设备类型
     */
    private Integer device_type;
    /**
     * 操作系统版本
     */
    private String os_version;
    /**
     * 设备厂商
     */
    private String vendor;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 设备屏幕高度
     */
    private Integer screen_height;
    /**
     * 设备屏幕宽度
     */
    private Integer screen_width;
    /**
     * user-agent(需要 urlencode)
     */
    private String ua;
    /**
     * 指纹
     */
    private String finger_print;

    /**
     * Android 设备系统 ID
     */
    private String android_id;
    /**
     * Serial Number AndroidSerial Number（获取不到可以传 8 个 0）
     */
    private String serial_number;
    /**
     * 移动设备国际身份码
     */
    private String imei;
    /**
     * 设 备 WiFi 网 卡 MAC 地址
     * 注:若获取不到请传空字符串。
     */
    private String mac;
    /**
     * 国际移动客户识别码
     * 注:若获取不到请传空字符串。
     */
    private String imsi;
    /**
     * iOS 设备标识码
     */
    private String idfa;
    /**
     * iOS 设备的 IDFV 值
     */
    private String idfv;
    /**
     * iOS 设 备 的 openudid 值
     */
    private String openudid;

    /**
     * ipv4 地址
     */
    private String ipv4;
    /**
     * 网络类型
     */
    private Integer connection_type;
    /**
     * 运营商 ID
     */
    private Integer operator_type;
    /**
     * 基站 ID，选填
     */
    private String cellular_id;

    /**
     * 热点 mac 地址，多个英文逗号隔开，选填
     */
    private String ap_mac;
    /**
     * 热点名称（ssid），多个英文逗号隔开，选填
     */
    private String ap_name;
    /**
     * 热点信号强度，多个英文逗号隔开，选填
     */
    private String rssi;
    /**
     * 是否当前连接热点，多个英文逗号隔开，选填
     */
    private String is_connected;

    /**
     * GPS 坐标经度，选填
     */
    private Float longitude;
    /**
     * GPS 坐标维度，选填
     */
    private Float latitude;

    /**
     * 视频标题 UTF-8 编码，选填（VAST接入视频必填）
     */
    private String video_title;
    /**
     * 视频内容长度，选填（VAST接入视频必填）
     */
    private String content_length;
    /**
     * 视频版权信息，选填（VAST接入视频必填）
     */
    private String copyright;

    /**
     * 内容联盟流量分润ID，选填（内容联盟流量必填）
     */
    private String ctkey;

    /**
     * 当前站点主域名，选填（H5 必填）
     */
    private String domain;
    /**
     * 当前页面 URL，选填（H5 必填）
     */
    private String url;
    /**
     * 页 面 标 题 ( 需 要urlencode)，选填
     */
    private String titile;
    /**
     * 当前页面的 metadata(需要 urlencode)，选填
     */
    private String keywords;
    /**
     * 内容来源 URL，选填
     */
    private String source_url;

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public Integer getMedia_type() {
        return media_type;
    }

    public void setMedia_type(Integer media_type) {
        this.media_type = media_type;
    }

    public Integer getRequest_protocol_type() {
        return request_protocol_type;
    }

    public void setRequest_protocol_type(Integer request_protocol_type) {
        this.request_protocol_type = request_protocol_type;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_package() {
        return app_package;
    }

    public void setApp_package(String app_package) {
        this.app_package = app_package;
    }

    public String getAdslot_id() {
        return adslot_id;
    }

    public void setAdslot_id(String adslot_id) {
        this.adslot_id = adslot_id;
    }

    public Integer getDevice_type() {
        return device_type;
    }

    public void setDevice_type(Integer device_type) {
        this.device_type = device_type;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getScreen_height() {
        return screen_height;
    }

    public void setScreen_height(Integer screen_height) {
        this.screen_height = screen_height;
    }

    public Integer getScreen_width() {
        return screen_width;
    }

    public void setScreen_width(Integer screen_width) {
        this.screen_width = screen_width;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getFinger_print() {
        return finger_print;
    }

    public void setFinger_print(String finger_print) {
        this.finger_print = finger_print;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
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

    public String getOpenudid() {
        return openudid;
    }

    public void setOpenudid(String openudid) {
        this.openudid = openudid;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public Integer getConnection_type() {
        return connection_type;
    }

    public void setConnection_type(Integer connection_type) {
        this.connection_type = connection_type;
    }

    public Integer getOperator_type() {
        return operator_type;
    }

    public void setOperator_type(Integer operator_type) {
        this.operator_type = operator_type;
    }

    public String getCellular_id() {
        return cellular_id;
    }

    public void setCellular_id(String cellular_id) {
        this.cellular_id = cellular_id;
    }

    public String getAp_mac() {
        return ap_mac;
    }

    public void setAp_mac(String ap_mac) {
        this.ap_mac = ap_mac;
    }

    public String getAp_name() {
        return ap_name;
    }

    public void setAp_name(String ap_name) {
        this.ap_name = ap_name;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getIs_connected() {
        return is_connected;
    }

    public void setIs_connected(String is_connected) {
        this.is_connected = is_connected;
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

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getContent_length() {
        return content_length;
    }

    public void setContent_length(String content_length) {
        this.content_length = content_length;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCtkey() {
        return ctkey;
    }

    public void setCtkey(String ctkey) {
        this.ctkey = ctkey;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public BidRequest() {
    }

    @Generated("SparkTools")
    private BidRequest(Builder builder) {
        this.request_id = builder.request_id;
        this.api_version = builder.api_version;
        this.media_type = builder.media_type;
        this.request_protocol_type = builder.request_protocol_type;
        this.request_time = builder.request_time;
        this.app_id = builder.app_id;
        this.app_version = builder.app_version;
        this.app_package = builder.app_package;
        this.adslot_id = builder.adslot_id;
        this.device_type = builder.device_type;
        this.os_version = builder.os_version;
        this.vendor = builder.vendor;
        this.model = builder.model;
        this.screen_height = builder.screen_height;
        this.screen_width = builder.screen_width;
        this.ua = builder.ua;
        this.finger_print = builder.finger_print;
        this.android_id = builder.android_id;
        this.serial_number = builder.serial_number;
        this.imei = builder.imei;
        this.mac = builder.mac;
        this.imsi = builder.imsi;
        this.idfa = builder.idfa;
        this.idfv = builder.idfv;
        this.openudid = builder.openudid;
        this.ipv4 = builder.ipv4;
        this.connection_type = builder.connection_type;
        this.operator_type = builder.operator_type;
        this.cellular_id = builder.cellular_id;
        this.ap_mac = builder.ap_mac;
        this.ap_name = builder.ap_name;
        this.rssi = builder.rssi;
        this.is_connected = builder.is_connected;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.video_title = builder.video_title;
        this.content_length = builder.content_length;
        this.copyright = builder.copyright;
        this.ctkey = builder.ctkey;
        this.domain = builder.domain;
        this.url = builder.url;
        this.titile = builder.titile;
        this.keywords = builder.keywords;
        this.source_url = builder.source_url;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String request_id;
        private String api_version;
        private Integer media_type;
        private Integer request_protocol_type;
        private String request_time;

        private String app_id;
        private String app_version;
        private String app_package;

        private String adslot_id;

        private Integer device_type;
        private String os_version;
        private String vendor;
        private String model;
        private Integer screen_height;
        private Integer screen_width;
        private String ua;
        private String finger_print;

        private String android_id;
        private String serial_number;
        private String imei;
        private String mac;
        private String imsi;
        private String idfa;
        private String idfv;
        private String openudid;

        private String ipv4;
        private Integer connection_type;
        private Integer operator_type;
        private String cellular_id;

        private String ap_mac;
        private String ap_name;
        private String rssi;
        private String is_connected;

        private Float longitude;
        private Float latitude;

        private String video_title;
        private String content_length;
        private String copyright;

        private String ctkey;

        private String domain;
        private String url;
        private String titile;
        private String keywords;
        private String source_url;

        public Builder withRequestId(String request_id) {
            this.request_id = request_id;
            return this;
        }

        public Builder withApiVersion(String api_version) {
            this.api_version = api_version;
            return this;
        }

        public Builder withMediaType(Integer media_type) {
            this.media_type = media_type;
            return this;
        }

        public Builder withRequestProtocolType(Integer request_protocol_type) {
            this.request_protocol_type = request_protocol_type;
            return this;
        }

        public Builder withRequestTime(String request_time) {
            this.request_time = request_time;
            return this;
        }

        public Builder withAppId(String app_id) {
            this.app_id = app_id;
            return this;
        }

        public Builder withAppVersion(String app_version) {
            this.app_version = app_version;
            return this;
        }

        public Builder withAppPackage(String app_package) {
            this.app_package = app_package;
            return this;
        }

        public Builder withAdslotId(String adslot_id) {
            this.adslot_id = adslot_id;
            return this;
        }

        public Builder withDeviceType(Integer device_type) {
            this.device_type = device_type;
            return this;
        }

        public Builder withoOsVersion(String os_version) {
            this.os_version = os_version;
            return this;
        }

        public Builder withVendor(String vendor) {
            this.vendor = vendor;
            return this;
        }

        public Builder withModel(String model) {
            this.model = model;
            return this;
        }

        public Builder withScreenWidth(Integer screen_width) {
            this.screen_width = screen_width;
            return this;
        }

        public Builder withScreenHeight(Integer screen_height) {
            this.screen_height = screen_height;
            return this;
        }

        public Builder withUa(String ua) {
            this.ua = ua;
            return this;
        }

        public Builder withFingerPrint(String finger_print) {
            this.finger_print = finger_print;
            return this;
        }

        public Builder withAndroidId(String android_id) {
            this.android_id = android_id;
            return this;
        }

        public Builder withSerialNumber(String serial_number) {
            this.serial_number = serial_number;
            return this;
        }

        public Builder withImei(String imei) {
            this.imei = imei;
            return this;
        }

        public Builder withMac(String mac) {
            this.mac = mac;
            return this;
        }

        public Builder withImsi(String imsi) {
            this.imsi = imsi;
            return this;
        }

        public Builder withIdfa(String idfa) {
            this.idfa = idfa;
            return this;
        }

        public Builder withIdfv(String idfv) {
            this.idfv = idfv;
            return this;
        }

        public Builder withOpenudId(String openudid) {
            this.openudid = openudid;
            return this;
        }

        public Builder withIpv4(String ipv4) {
            this.ipv4 = ipv4;
            return this;
        }

        public Builder withOperatorType(Integer operator_type) {
            this.operator_type = operator_type;
            return this;
        }

        public Builder withCellularId(String cellular_id) {
            this.cellular_id = cellular_id;
            return this;
        }

        public Builder withApMac(String ap_mac) {
            this.ap_mac = ap_mac;
            return this;
        }

        public Builder withApName(String ap_name) {
            this.ap_name = ap_name;
            return this;
        }

        public Builder withRssi(String rssi) {
            this.rssi = rssi;
            return this;
        }

        public Builder withIsConnected(String is_connected) {
            this.is_connected = is_connected;
            return this;
        }

        public Builder withLongitude(float longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withLatitude(float latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withVideoTitle(String video_title) {
            this.video_title = video_title;
            return this;
        }

        public Builder withContentLength(String content_length) {
            this.content_length = content_length;
            return this;
        }

        public Builder withCopyright(String copyright) {
            this.copyright = copyright;
            return this;
        }

        public Builder withCtkey(String ctkey) {
            this.ctkey = ctkey;
            return this;
        }

        public Builder withDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withTitle(String titile) {
            this.titile = titile;
            return this;
        }

        public Builder withKeywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public Builder withSourceurl(String source_url) {
            this.source_url = source_url;
            return this;
        }

        public Builder withConnectionType(Integer connection_type) {
            this.connection_type = connection_type;
            return this;
        }

        public BidRequest build() {
            return new BidRequest(this);
        }
    }
}

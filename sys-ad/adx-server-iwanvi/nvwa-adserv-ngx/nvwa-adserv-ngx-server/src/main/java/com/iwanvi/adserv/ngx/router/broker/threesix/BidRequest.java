package com.iwanvi.adserv.ngx.router.broker.threesix;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author: 郑晓东
 * @date: 2019-04-19 15:29
 * @version: v1.0
 * @Description:
 */
public class BidRequest {
    /**
     * 必填
     * 请求的唯一id，32个字符的小写字符串，由媒体端生成，需要保持唯一性。生成规则：任意设备号（比如IMEI）+APP包名+毫秒时间戳，MD5后取小写值
     */
    private String bid;

    /**
     * 当且仅当广告位类型为信息流,用于标识同一个信息流广告位在当前频道下,信息流从上拉、从下拉发起广告请求的次数。信息流广告位的第一次广告请求编号为1；若之后信息流从下拉（即用户手指上滑、从下方加载数据）过程中，触发该广告位的广告请求，则从2开始编号，不断递加；若之后信息流从上拉（即用户手指下滑、从上方加载数据）过程中，触发该广告位的广告请求，则从-2开始编号，不断递减。无符号代表用户手指上滑，从下方加载数据；有负号代表代表用户手指下滑，从上方加载数据，两种行为分别计数
     * 展示信息流，发送广告请求，填1；用户上滑，下方加载数据，填2；再次上滑，填3；用户下滑，信息流滚动到顶部，然后下滑，上方加载数据，填-2；用户再次上滑，下方加载数据，填4
     */
    @JSONField(name = "req_times")
    private Integer reqTimes;

    /**
     * App对象，客户端APP的信息，必须真实来源于客户端
     */
    private App app;

    /**
     * Device对象，用户的设备信息，必须真实来源于客户端
     */
    private Device device;

    /**
     * Adspace对象列表，广告位信息，必须真实来源于客户端，目前仅支持单个广告位
     */
    private List<Adspaces> adspaces;

    /**
     * // 标识用户浏览某个页面的行为，避免用户在同一页面不同位置上较大概率地看到相同的广告。32个字符的小写字符串，由媒体端生成，需要与广告位所在的页面属性相关，且不能与bid字段相同。生成规则：任意设备号（比如IMEI）+APP包名+媒体自定义页面属性（比如Activity/Fragment的hashCode，或者类似电影、动漫等），MD5后取小写值
     * // （当且仅当一个页面有多个广告请求时）
     * // d41d8cd98f00b204e9800998ecf8427e
     */
    private String uid;

    /**
     * // 当且仅当使用服务器发送广告请求时
     * // 客户端真实的外网IP地址，不能是代理服务器IP（比如透明代理，小区或城域网出口），不能是内网IP
     * // 	124.74.26.198
     */
    private String ip;

    /**
     * // 客户端的UserAgent。必须是客户端通过系统API获取的真实UA，不能自定义
     * // Android例子： Dalvik/1.6.0 (Linux; U; Android 4.3; SM-N7588V Build/JLS36C 或 Mozilla/5.0 (Linux; Android 5.0.1; SM-N7588V Build/JLS36C; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36
     * //iOS例子： Mozilla/5.0 (iPhone; CPU iPhone OS 8_3_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Mobile/13F69 NewsApp/11.1)
     */
    @JSONField(name = "user_agent")
    private String userAgent;

    /**
     * // 用户的兴趣标签，多个兴趣标签使用英文逗号分隔（不能有空格）
     * // 购物,游戏,娱乐
     */
    private String utag;

    /**
     * 必须区分WiFi和移动网络，如果不能区分234G，请统一填写2G，unknown只能用在无法识别5G的情况下
     */
    @JSONField(name = "network_type")
    private Integer networkType;

    /**
     * // 	地理位置，经度
     * // 	115.234521
     */
    private Double longitude;

    /**
     * // 地理位置，纬度
     * // 60.323456
     */
    private Double latitude;

    /**
     * 请求协议中没有列出的字段，以K-V对的形式给出
     * k1=v1;k2=v2 以分号分割
     */
    private Map<String, String> ext;

    /**
     * 用户的搜索关键词: 360浏览器
     */
    @JSONField(name = "search_word")
    private String searchWord;
    
    @Generated("SparkTools")
    private BidRequest(Builder builder) {
        this.bid = builder.bid;
        this.reqTimes = builder.reqTimes;
        this.app = builder.app;
        this.device = builder.device;
        this.adspaces = builder.adspaces;
        this.uid = builder.uid;
        this.ip = builder.ip;
        this.userAgent=builder.userAgent;
        this.utag = builder.utag;
        this.networkType = builder.networkType;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.ext = builder.ext;
        this.searchWord = builder.searchWord;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Integer getReqTimes() {
        return reqTimes;
    }

    public void setReqTimes(Integer reqTimes) {
        this.reqTimes = reqTimes;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public List<Adspaces> getAdspaces() {
        return adspaces;
    }

    public void setAdspaces(List<Adspaces> adspaces) {
        this.adspaces = adspaces;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUtag() {
        return utag;
    }

    public void setUtag(String utag) {
        this.utag = utag;
    }

    public Integer getNetworkType() {
        return networkType;
    }

    public void setNetworkType(Integer networkType) {
        this.networkType = networkType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Map<String, String> getExt() {
        return ext;
    }

    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public static class App {

        /**
         * // APP名称，与APP图标展示的名称相同
         * // 360影视大全
         */
        @JSONField(name = "app_name")
        private String appName;

        /**
         * APP包名  com.qihoo.video
         */
        @JSONField(name = "package_name")
        private String packageName;

        /**
         * APP版本号，Android需要获取versionName，而不是versionCode
         * 3.5.6
         */
        @JSONField(name = "app_version")
        private String appVersion;

        
        public String getAppName() {
            return appName;
        }

        public void setAppName( String appName) {
            this.appName = appName;
        }

        
        public String getPackageName() {
            return packageName;
        }

        public void setPackageName( String packageName) {
            this.packageName = packageName;
        }

        
        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion( String appVersion) {
            this.appVersion = appVersion;
        }

        public App(){}

        public App(Builder builder){
            this.appName = builder.appName;
            this.packageName = builder.packageName;
            this.appVersion = builder.appVersion;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder{
            private String appName;
            private String packageName;
            private String appVersion;

            public Builder(){}

            public Builder withAppName(String appName){
                this.appName = appName;
                return this;
            }
            public Builder withPackageName(String packageName){
                this.packageName = packageName;
                return this;
            }
            public Builder withAppVersion(String appVersion){
                this.appVersion = appVersion;
                return this;
            }

            public App build(){return new App(this);}
        }
    }

    public static class Device {
        // DeviceId对象列表，建议尽可能多填写，有助于提高广告变现能力
        @JSONField(name = "device_id")
        private List<DeviceId> deviceId;

        @JSONField(name = "os_type")
        private Integer osType;

        /**
         * // 操作系统版本号，需从系统API获取的原始数据
         * // Android例子： 4.4.4
         * // iOS例子： 9.2.1
         */
        @JSONField(name = "os_version")
        private String osVersion;

        /**
         * 设备品牌，Android取Build.BRAND，可用Build.MANUFACTURE代替，但效果会变差；iOS使用默认值Apple
         * Android例子： Honor
         * iOS例子： Apple
         */
        private String brand;

        /**
         * 设备型号，Android取Build.MODEL
         * Android例子： Che1-CL20
         * iOS例子： iPhone4,1
         */
        private String model;

        /**
         * 设备类型
         */
        @JSONField(name = "device_type")
        private Integer deviceType;

        /**
         * 设备屏幕的宽度，以像素为单位，与屏幕密度无关
         */
        @JSONField(name = "screen_width")
        private Integer screenWidth;

        /**
         * 设备屏幕的高度，以像素为单位，与屏幕密度无关
         */
        @JSONField(name = "screen_height")
        private Integer screenHeight;

        /**
         * 屏幕密度，如果传不了真实值，请统一填写1.0
         */
        @JSONField(name = "screen_density")
        private Double screenDensity;

        /**
         * 屏幕朝向
         */
        @JSONField(name = "screen_orientation")
        private Integer screenOrientation;

        /**
         * // 	运营商编码
         * //  注意不是客户端直接获取的460开头编码，需要转换
         * //  对应关系查询：Wiki，360百科
         * //  除移动电信联通外，其他情况均填写未知
         */
        @JSONField(name = "carrier_id")
        private Integer carrierId;

        /**
         * 当前剩余电量的百分比，取值范围为0至100
         */
        @JSONField(name = "battery_level")
        private Integer batteryLevel;

        public List<DeviceId> getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(List<DeviceId> deviceId) {
            this.deviceId = deviceId;
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

        public Double getScreenDensity() {
            return screenDensity;
        }

        public void setScreenDensity(Double screenDensity) {
            this.screenDensity = screenDensity;
        }

        public Integer getScreenOrientation() {
            return screenOrientation;
        }

        public void setScreenOrientation(Integer screenOrientation) {
            this.screenOrientation = screenOrientation;
        }

        public Integer getCarrierId() {
            return carrierId;
        }

        public void setCarrierId(Integer carrierId) {
            this.carrierId = carrierId;
        }

        public Integer getBatteryLevel() {
            return batteryLevel;
        }

        public void setBatteryLevel(Integer batteryLevel) {
            this.batteryLevel = batteryLevel;
        }

        @Generated("SparkTools")
        private Device(Builder builder) {
            this.deviceId = builder.deviceId;
            this.osType = builder.osType;
            this.osVersion = builder.osVersion;
            this.brand = builder.brand;
            this.model = builder.model;
            this.deviceType = builder.deviceType;
            this.screenWidth = builder.screenWidth;
            this.screenHeight = builder.screenHeight;
            this.screenDensity = builder.screenDensity;
            this.screenOrientation = builder.screenOrientation;
            this.carrierId = builder.carrierId;
            this.batteryLevel = builder.batteryLevel;
        }

        public Device() {}

        /**
         * Creates builder to build {@link Device}.
         * @return created builder
         */
        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        /**
         * Builder to build {@link Device}.
         */
        @Generated("SparkTools")
        public static final class Builder {
            private List<DeviceId> deviceId;
            private Integer osType;
            private String osVersion;
            private String brand;
            private String model;
            private Integer deviceType;
            private Integer screenWidth;
            private Integer screenHeight;
            private Double screenDensity;
            private Integer screenOrientation;
            private Integer carrierId;
            private Integer batteryLevel;

            private Builder() {
            }

            public Builder withDeviceId(List<DeviceId> deviceId) {
                this.deviceId = deviceId;
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
            public Builder withBrand(String brand){
                this.brand = brand;
                return this;
            }
            public Builder withModel(String model){
                this.model = model;
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
            public Builder withScreenDensity(Double screenDensity){
                this.screenDensity = screenDensity;
                return this;
            }
            public Builder withScreenOrientation(Integer screenOrientation){
                this.screenOrientation = screenOrientation;
                return this;
            }
            public Builder withCarrierId(Integer carrierId){
                this.carrierId = carrierId;
                return this;
            }
            public Builder withBatteryLevel(Integer batteryLevel){
                this.batteryLevel = batteryLevel;
                return this;
            }

            public Device build() {
                return new Device(this);
            }
        }
    }
    
    public static class DeviceId {
        /**
         * // 设备id值，小写的原始值或小写的MD5值，MD5前请先转成小写字符串
         * // 原始值： 867102028456aaa
         * //  MD5值： a007ea60fde1997944bd2ef62d106214
         */
        @JSONField(name = "device_id")
        private String deviceId;

        /**
         * //  设备ID类型
         * //  目前Android支持类型1,3,4,7,8,其中IMEI必填
         * //  同时推荐回传android id，mac，IMSI
         * //  目前iOS只允许传IDFA
         */
        @JSONField(name = "device_id_type")
        private Integer deviceIdType;

        /**
         * // 设备id值哈希类型
         * // 推荐原始
         */
        @JSONField(name = "hash_type")
        private Integer hashType;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getDeviceIdType() {
            return deviceIdType;
        }

        public void setDeviceIdType(Integer deviceIdType) {
            this.deviceIdType = deviceIdType;
        }

        public Integer getHashType() {
            return hashType;
        }

        public void setHashType(Integer hashType) {
            this.hashType = hashType;
        }

        @Generated("SparkTools")
        private DeviceId(Builder builder) {
            this.deviceId = builder.deviceId;
            this.deviceIdType = builder.deviceIdType;
            this.hashType = builder.hashType;
        }

        public DeviceId() {}

        /**
         * Creates builder to build {@link DeviceId}.
         * @return created builder
         */
        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        /**
         * Builder to build {@link Device}.
         */
        @Generated("SparkTools")
        public static final class Builder {
            private String deviceId;
            private Integer deviceIdType;
            private Integer hashType;

            private Builder() {
            }

            public Builder withDeviceId(String deviceId){
                this.deviceId = deviceId;
                return this;
            }
            public Builder withDeviceIdType(Integer deviceIdType){
                this.deviceIdType = deviceIdType;
                return this;
            }
            public Builder withHashType(Integer hashType){
                this.hashType = hashType;
                return this;
            }

            public DeviceId build() {
                return new DeviceId(this);
            }
        }

        /**
         * // 设备ID类型
         * UNKNOWN = 0; //未知
         * IMEI = 1; //imei
         * IDFA = 2; //idfa
         * AAID = 3; //android id
         * MAC = 4; //mac
         * IDFV = 5; //idfv
         * M2ID = 6; //
         * SERIALID = 7; //
         * IMSI = 8; //imsi
         */
        public enum DeviceIdType {
            UNKNOWN(0), IMEI(1), IDFA(2), AAID(3), MAC(4), IDFV(5), M2ID(6), SERIALID(7), IMSI(8);
            private final Integer value;

            DeviceIdType(Integer value) {
                this.value = value;
            }

            public Integer getValue() {
                return value;
            }
        }

        /**
         * 设备id值哈希类型
         * NONE = 0; //原始值
         * MD5 = 1; //MD5值
         */
        public enum HashType {
            NONE(0), MD5(1);
            private final Integer value;

            HashType(Integer value) {
                this.value = value;
            }

            public Integer getValue() {
                return value;
            }
        }
    }

    public static class Adspaces {
        /**
         * 广告位id，唯一标识一个广告位，由360移动APP广告平台提供
         * // 	5kLFGJ5S3J
         */
        @JSONField(name = "adspace_id")
        private String adspaceId;

        /**
         * 广告位类型
         */
        @JSONField(name = "adspace_type")
        private Integer adspaceType;

        /**
         * // 广告位位置
         * // 广告在页面上的展现位置，由媒体按照自己的定义真实填写
         */
        @JSONField(name = "adspace_position")
        private Integer adspacePosition;

        /**
         * //  由360渲染还是由媒体渲染
         * //      360渲染（HTML素材）：true
         * //      媒体渲染（图片素材或者原生素材）：false
         * //  true和false字母须全部小写
         * //  目前仅支持媒体渲染，请填false
         */
        @JSONField(name = "allowed_html")
        private boolean allowedHtml;

        /**
         * // 必填:当且仅当广告位类型为横幅，插屏，开屏 时
         * // 广告位宽度，以像素为单位，与屏幕密度无关
         * // 	720
         */
        private Integer width;

        /**
         * // 必填:当且仅当广告位类型为横幅，插屏，开屏 时
         * // 广告位高度，以像素为单位，与屏幕密度无关
         * // 405
         */
        private Integer height;

        /**
         * // 当前广告位一次请求返回的创意个数，最多支持5个，目前该属性会根据媒体需求在广告位上进行设置，故无需填写该字段，如果要填写，应与双方约定值一致
         * // 最多支持5个
         */
        @JSONField(name = "impression_num")
        private String impressionNum;

        /**
         * // 广告位所在页面的关键词信息，多个关键词使用英文逗号分隔（不能有空格）
         * // NBA,2012,总决赛,雷霆三少
         */
        private String keywords;

        /**
         * // 广告位所在的频道页
         * // 	体育
         */
        private String channel;

        /**
         * 媒体渲染的广告位支持的原生素材样式
         * 若媒体不填，则以360SSP平台的配置为准
         * 该字段决定BidResponse.Ads.Creative.Adm.Native结构的字段组合
         * {
         * SINGLE_IMAGE = 1; // 单图样式，MAX会返回img、title、desc、logo
         * LINKED_IMAGE = 2; // 联动双图样式，MAX会返回img、linked_img、title、desc、logo
         * MULTI_IMAGES = 3; // 多图样式，MAX会返回multi_imgs、title、desc、logo
         * VIDEO = 4; // 视频样式，MAX会返回video、img、title、desc、logo
         * }
         */
        @JSONField(name = "native_style")
        private List<Integer> nativeStyle;

        public String getAdspaceId() {
            return adspaceId;
        }

        public void setAdspaceId(String adspaceId) {
            this.adspaceId = adspaceId;
        }

        public Integer getAdspaceType() {
            return adspaceType;
        }

        public void setAdspaceType(Integer adspaceType) {
            this.adspaceType = adspaceType;
        }

        public Integer getAdspacePosition() {
            return adspacePosition;
        }

        public void setAdspacePosition(Integer adspacePosition) {
            this.adspacePosition = adspacePosition;
        }

        public boolean isAllowedHtml() {
            return allowedHtml;
        }

        public void setAllowedHtml(boolean allowedHtml) {
            this.allowedHtml = allowedHtml;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getImpressionNum() {
            return impressionNum;
        }

        public void setImpressionNum(String impressionNum) {
            this.impressionNum = impressionNum;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public List<Integer> getNativeStyle() {
            return nativeStyle;
        }

        public void setNativeStyle(List<Integer> nativeStyle) {
            this.nativeStyle = nativeStyle;
        }

        public Adspaces(){}

        public Adspaces(Builder builder){
            this.adspaceId = builder.adspaceId;
            this.adspaceType = builder.adspaceType;
            this.adspacePosition = builder.adspacePosition;
            this.allowedHtml = builder.allowedHtml;
            this.width = builder.width;
            this.height = builder.height;
            this.impressionNum = builder.impressionNum;
            this.keywords = builder.keywords;
            this.channel = builder.channel;
            this.nativeStyle = builder.nativeStyle;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder{
            private String adspaceId;
            private Integer adspaceType;
            private Integer adspacePosition;
            private boolean allowedHtml;
            private Integer width;
            private Integer height;
            private String impressionNum;
            private String keywords;
            private String channel;
            private List<Integer> nativeStyle;

            public Builder(){}

            public Builder withAdspaceId(String adspaceId){
                this.adspaceId = adspaceId;
                return this;
            }
            public Builder withAdspaceType(Integer adspaceType){
                this.adspaceType = adspaceType;
                return this;
            }
            public Builder withAdspacePosition(Integer adspacePosition){
                this.adspacePosition = adspacePosition;
                return this;
            }
            public Builder withAllowedHtml(boolean allowedHtml){
                this.allowedHtml = allowedHtml;
                return this;
            }
            public Builder withWidth(Integer width){
                this.width = width;
                return this;
            }
            public Builder withHeight(Integer height){
                this.height = height;
                return this;
            }
            public Builder withImpressionNum(String impressionNum){
                this.impressionNum = impressionNum;
                return this;
            }
            public Builder withKeywords(String keywords){
                this.keywords = keywords;
                return this;
            }
            public Builder withChannel(String channel){
                this.channel = channel;
                return this;
            }
            public Builder withNativeStyle(List<Integer> nativeStyle){
                this.nativeStyle = nativeStyle;
                return this;
            }

            public Adspaces build(){
                return new Adspaces(this);
            }
        }
    }

    /**
     * OS_IOS = 1;
     * OS_ANDROID = 2;
     */
    public enum OSTpye {
        OS_IOS(1), OS_ANDROID(2);
        private final Integer value;

        OSTpye(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * UNKNOWN = 0; //未知
     * TABLET = 1; //平板
     * PHONE = 2; //手机
     */
    public enum DeviceType {
        UNKNOWN(0), TABLET(1), PHONE(2);
        private final Integer value;

        DeviceType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * SCREEN_ORIENTATION_UNKNOWN = 0; //未知
     * SCREEN_ORIENTATION_PORTRAIT = 1; //竖屏
     * SCREEN_ORIENTATION_LANDSCAPE = 2; //横屏
     */
    public enum ScreenOrientation {
        SCREEN_ORIENTATION_UNKNOWN(0), SCREEN_ORIENTATION_PORTRAIT(1), SCREEN_ORIENTATION_LANDSCAPE(2);
        private final Integer value;

        ScreenOrientation(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * UNKNOWN = 0; //未知
     * CHINA_MOBILE = 70120; //中国移动
     * CHINA_TELECOM = 70121; //中国电信
     * UNICOM = 70123; //中国联通
     */
    public enum CarrierId {
        UNKNOWN(0), CHINA_MOBILE(70120), CHINA_TELECOM(70121), UNICOM(70123);
        private final Integer value;

        CarrierId(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * // 广告位类型
     * BANNER = 1; //横幅
     * INTERSTITIAL = 2; //插屏
     * VIDEO = 3; //视频
     * NATIVE = 4; //信息流
     * EMBEDDED = 5; //嵌入式
     * OPENING = 6; //开屏
     * FOCUS = 7; //焦点图
     * OPENING_LINK = 8; // 开屏联动
     */
    public enum AdspaceType {
        BANNER(1), INTERSTITIAL(2), VIDEO(3), NATIVE(4), EMBEDDED(5), OPENING(6), FOCUS(7), OPENING_LINK(8);
        private final Integer value;

        AdspaceType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * // 广告位位置
     * NONE = 0; //未知
     * FIRST_POS = 1; //首屏
     * OTHERS = 2; //非首屏
     */
    public enum AdspacePosition {
        NONE(0), FIRST_POS(1), OTHERS(2);
        private final Integer value;

        AdspacePosition(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * NET_UNKNOWN = 0; //未知
     * NET_WIFI = 1; //wifi
     * NET_2G =2 ; //2G
     * NET_3G =3 ; //3G
     * NET_4G = 4; //4G
     */
    public enum NetType {
        NET_UNKNOWN(0), NET_WIFI(1), NET_2G(2), NET_3G(3), NET_4G(4);
        private final Integer value;

        NetType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 媒体渲染的广告位支持的原生素材样式
     * 若媒体不填，则以360SSP平台的配置为准
     * 该字段决定BidResponse.Ads.Creative.Adm.Native结构的字段组合
     * {
     *      SINGLE_IMAGE = 1; // 单图样式，MAX会返回img、title、desc、logo
     *      LINKED_IMAGE = 2; // 联动双图样式，MAX会返回img、linked_img、title、desc、logo
     *      MULTI_IMAGES = 3; // 多图样式，MAX会返回multi_imgs、title、desc、logo
     *      VIDEO = 4; // 视频样式，MAX会返回video、img、title、desc、logo
     * }
     */
    public enum NativeStyle{
        SINGLE_IMAGE(1), LINKED_IMAGE(2), MULTI_IMAGES(3), VIDEO(4);
        private final Integer value;
        NativeStyle(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * Creates builder to build {@link BidRequest}.
     * @return created builder
     */
    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder to build {@link BidRequest}.
     */
    @Generated("SparkTools")
    public static final class Builder {
        private String bid;
        private Integer reqTimes;
        private App app;
        private Device device;
        private List<Adspaces> adspaces;
        private String uid;
        private String ip;
        private String userAgent;
        private String utag;

        private Integer networkType;
        private Double longitude;
        private Double latitude;
        private Map<String, String> ext;
        private String searchWord;

        private Builder() {
        }

        public Builder withBId(String bid) {
            this.bid = bid;
            return this;
        }
        public Builder withReqTimes(Integer reqTimes){
            this.reqTimes = reqTimes;
            return this;
        }
        public Builder withApp(App app){
            this.app=app;
            return this;
        }
        public Builder withDevice(Device device) {
            this.device = device;
            return this;
        }
        public Builder withAdspaces(List<Adspaces> adspaces){
            this.adspaces = adspaces;
            return this;
        }
        public Builder withUid(String uid){
            this.uid = uid;
            return this;
        }
        public Builder withIp(String ip){
            this.ip = ip;
            return this;
        }
        public Builder withUserAgent(String userAgent){
            this.userAgent = userAgent;
            return this;
        }
        public Builder withUtag(String utag){
            this.utag = utag;
            return this;
        }
        public Builder withNetType(Integer networkType){
            this.networkType = networkType;
            return this;
        }
        public Builder withLongitude(Double longitude){
            this.longitude = longitude;
            return this;
        }
        public Builder withLatitude(Double latitude){
            this.latitude = latitude;
            return this;
        }
        public Builder withExt(Map<String, String> ext) {
            this.ext = ext;
            return this;
        }
        public Builder withSearchWord(String searchWord){
            this.searchWord = searchWord;
            return this;
        }

        public BidRequest build() {
            return new BidRequest(this);
        }
    }
}

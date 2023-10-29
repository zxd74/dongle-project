package com.iwanvi.adserv.ngx.router.broker.sogou;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-07-05 09:04
 * @version: v1.0
 * @Description: 搜狗请求数据类
 */
public class BidRequest {
    /**
     * 请求ID：platformId+posId+timeStamp
     * 必填
     */
    private String requestId;
    /**
     * 鉴权字符串：大写
     * 必填
     */
    private String auth;
    /**
     * 设备相关信息
     * 必填
     */
    private Device device;
    /**
     * 广告位请求信息：一个⼴广告请求对应对应一个组⼴广告信息，至少应该填⼀一个，目前暂时仅⽀支持⼀一个⼴广告请求
     * 必填
     */
    private Imp[] imps;
    /**
     * app信息
     */
    private App app;
    /**
     * 渠道标识
     * 是媒体针对自己不同的流量量来源（例如不同的渠道包）进⾏行行区分的一个字段，奇点后续版本会在媒体的结算数据报表里增加按channel区分的结算数据
     */
    private String channel;
    /**
     * 奇点广告接口版本号
     */
    private String version;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Imp[] getImps() {
        return imps;
    }

    public void setImps(Imp[] imps) {
        this.imps = imps;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public BidRequest() {
    }

    @Generated("SparkTools")
    private BidRequest(Builder builder) {
        this.requestId = builder.requestId;
        this.auth = builder.auth;
        this.device = builder.device;
        this.imps = builder.imps;
        this.app = builder.app;
        this.channel = builder.channel;
        this.version = builder.version;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String requestId;
        private String auth;
        private Device device;
        private Imp[] imps;
        private App app;
        private String channel;
        private String version;

        public Builder withRequestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder withAuth(String auth) {
            this.auth = auth;
            return this;
        }

        public Builder withDevice(Device device) {
            this.device = device;
            return this;
        }

        public Builder withImp(Imp[] imps) {
            this.imps = imps;
            return this;
        }

        public Builder withApp(App app) {
            this.app = app;
            return this;
        }

        public Builder withChannel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder withVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder() {
        }

        public BidRequest build() {
            return new BidRequest(this);
        }
    }

    public static class Device {
        /**
         * 设备唯一ID
         * 必填
         */
        private String deviceId;
        /**
         * 用户所在网络环境
         * 必填
         */
        private Integer network;
        /**
         * 设备类型
         * 必填
         */
        private Integer deviceType;
        /**
         * 安卓设备的imei号明文：大写（与imeiMd5二选一）
         */
        private String imei;
        /**
         * imei使用md5加密：大写（与imei二选一）
         */
        private String imeiMd5;
        /**
         * 当os为ios设备时， idfa字段为必填
         */
        private String idfa;
        /**
         * 安卓id：大写（16位）
         */
        private String androidId;
        /**
         * 设备品牌
         */
        private String brand;
        /**
         * 设备型号
         */
        private String model;
        /**
         * 设备操作系统
         */
        private String os;
        /**
         * 设备操作系统版本号
         */
        private String osVersion;
        /**
         * 设备运营商信息
         */
        private Integer carrier;
        /**
         * 设备mac标识：大写明文
         * 必填
         */
        private String mac;
        /**
         * 请求的设备ip：设备的公网IP，不允许传输类似10打头的内网ip或服务器器ip
         * 必填
         */
        private String ip;
        /**
         * 设备的客户端UA
         * 必填
         */
        private String userAgent;
        /**
         * 地理理位置信息
         */
        private Geo geo;
        /**
         * 设备横竖屏
         */
        private Integer orientation;
        /**
         * 设备宽度
         */
        private Integer screenWidth;
        /**
         * 设备⾼高度
         */
        private Integer screenHeight;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getNetwork() {
            return network;
        }

        public void setNetwork(Integer network) {
            this.network = network;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getImeiMd5() {
            return imeiMd5;
        }

        public void setImeiMd5(String imeiMd5) {
            this.imeiMd5 = imeiMd5;
        }

        public String getIdfa() {
            return idfa;
        }

        public void setIdfa(String idfa) {
            this.idfa = idfa;
        }

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
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

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public Integer getCarrier() {
            return carrier;
        }

        public void setCarrier(Integer carrier) {
            this.carrier = carrier;
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

        public String getUserAgent() {
            return userAgent;
        }

        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }

        public Integer getOrientation() {
            return orientation;
        }

        public void setOrientation(Integer orientation) {
            this.orientation = orientation;
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

        public Device() {
        }

        @Generated("SparkTools")
        private Device(Builder builder) {
            this.deviceId = builder.deviceId;
            this.network = builder.network;
            this.deviceType = builder.deviceType;
            this.imei = builder.imei;
            this.imeiMd5 = builder.imeiMd5;
            this.idfa = builder.idfa;
            this.androidId = builder.androidId;
            this.brand = builder.brand;
            this.model = builder.model;
            this.os = builder.os;
            this.osVersion = builder.osVersion;
            this.carrier = builder.carrier;
            this.mac = builder.mac;
            this.ip = builder.ip;
            this.userAgent = builder.userAgent;
            this.geo = builder.geo;
            this.orientation = builder.orientation;
            this.screenWidth = builder.screenWidth;
            this.screenHeight = builder.screenHeight;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String deviceId;
            private Integer network;
            private Integer deviceType;
            private String imei;
            private String imeiMd5;
            private String idfa;
            private String androidId;
            private String brand;
            private String model;
            private String os;
            private String osVersion;
            private Integer carrier;
            private String mac;
            private String ip;
            private String userAgent;
            private Geo geo;
            private Integer orientation;
            private Integer screenWidth;
            private Integer screenHeight;

            public Builder withDeviceId(String deviceId) {
                this.deviceId = deviceId;
                return this;
            }

            public Builder withNetwork(Integer network) {
                this.network = network;
                return this;
            }

            public Builder withDeviceType(Integer deviceType) {
                this.deviceType = deviceType;
                return this;
            }

            public Builder withImei(String imei) {
                this.imei = imei;
                return this;
            }

            public Builder withImeiMd5(String imeiMd5) {
                this.imeiMd5 = imeiMd5;
                return this;
            }

            public Builder withIdfa(String idfa) {
                this.idfa = idfa;
                return this;
            }

            public Builder withAndroidId(String androidId) {
                this.androidId = androidId;
                return this;
            }

            public Builder withBrand(String brand) {
                this.brand = brand;
                return this;
            }

            public Builder withModel(String model) {
                this.model = model;
                return this;
            }

            public Builder withOs(String os) {
                this.os = os;
                return this;
            }

            public Builder withOsVersion(String osVersion) {
                this.osVersion = osVersion;
                return this;
            }

            public Builder withCarrier(Integer carrier) {
                this.carrier = carrier;
                return this;
            }

            public Builder withMac(String mac) {
                this.mac = mac;
                return this;
            }

            public Builder withIp(String ip) {
                this.ip = ip;
                return this;
            }

            public Builder withUserAgent(String userAgent) {
                this.userAgent = userAgent;
                return this;
            }

            public Builder withGeo(Geo geo) {
                this.geo = geo;
                return this;
            }

            public Builder withOrientation(Integer orientation) {
                this.orientation = orientation;
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

            public Builder() {
            }

            public Device build() {
                return new Device(this);
            }
        }
    }

    public static class Imp {
        /**
         * 广告位请求信息id(impId): 从1开始计数
         * 必填
         */
        private Integer id;
        /**
         * 当前请求的⼴广告条数
         * 不填默认为1，最多为50。超过50修正为50；小于1修正为1
         */
        private Integer count;
        /**
         * 视频⼴广告请求，视频⼴广告请求不不能为空
         */
        private Video video;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public Imp() {
        }

        @Generated("SparkTools")
        private Imp(Builder builder) {
            this.id = builder.id;
            this.count = builder.count;
            this.video = builder.video;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Integer id;
            private Integer count;
            private Video video;

            public Builder withId(Integer id) {
                this.id = id;
                return this;
            }

            public Builder withCount(Integer count) {
                this.count = count;
                return this;
            }

            public Builder withVideo(Video video) {
                this.video = video;
                return this;
            }

            public Builder() {
            }

            public Imp build() {
                return new Imp(this);
            }
        }
    }

    public static class App {
        /**
         * 请求⼴广告的app应⽤用包名信息
         */
        private String pkgName;
        /**
         * 请求⼴广告的app应⽤用名
         */
        private String appName;
        /**
         * 请求⼴广告的app类⽬目名称
         */
        private String category;

        public String getPkgName() {
            return pkgName;
        }

        public void setPkgName(String pkgName) {
            this.pkgName = pkgName;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public App() {
        }

        @Generated("SparkTools")
        private App(Builder builder) {
            this.pkgName = builder.pkgName;
            this.appName = builder.appName;
            this.category = builder.category;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String pkgName;
            private String appName;
            private String category;

            public Builder withPkgName(String pkgName) {
                this.pkgName = pkgName;
                return this;
            }

            public Builder withAppName(String appName) {
                this.appName = appName;
                return this;
            }

            public Builder withCategory(String category) {
                this.category = category;
                return this;
            }

            public Builder() {
            }

            public App build() {
                return new App(this);
            }
        }
    }

    public static class Geo {
        /**
         * 经度
         */
        private Double longitude;
        /**
         * 纬度
         */
        private Double latitude;

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

        public Geo() {
        }

        @Generated("SparkTools")
        private Geo(Builder builder) {
            this.longitude = builder.longitude;
            this.latitude = builder.latitude;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Double longitude;
            private Double latitude;

            public Builder withLongitude(Double longitude) {
                this.longitude = longitude;
                return this;
            }

            public Builder withLatitude(Double latitude) {
                this.latitude = latitude;
                return this;
            }

            public Builder() {
            }

            public Geo build() {
                return new Geo(this);
            }
        }
    }

    public static class Video {
        /**
         * 最⼩小的视频长度
         */
        private Integer minDuration;
        /**
         * 最⼤大的视频⻓长度
         */
        private Integer maxDuration;
        /**
         * 支持的视频类型
         */
        private String mimeType;
        /**
         * 允许的视频尺寸
         */
        private Size[] sizes;
        /**
         * 允许的视频最⼤大⻓长度
         */
        private Integer maxLength;
        /**
         * 请求视频类型: 默认为空不限制，多个请⽤用英⽂文逗号(,)分割
         * 必填
         */
        private Integer videoType;
        /**
         * 视频的加载方式
         */
        private Integer delivery;
        /**
         * 视频播放形式(屏幕方向)，激励视频必填
         */
        private Integer orientation;

        public Integer getMinDuration() {
            return minDuration;
        }

        public void setMinDuration(Integer minDuration) {
            this.minDuration = minDuration;
        }

        public Integer getMaxDuration() {
            return maxDuration;
        }

        public void setMaxDuration(Integer maxDuration) {
            this.maxDuration = maxDuration;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public Size[] getSizes() {
            return sizes;
        }

        public void setSizes(Size[] sizes) {
            this.sizes = sizes;
        }

        public Integer getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(Integer maxLength) {
            this.maxLength = maxLength;
        }

        public Integer getVideoType() {
            return videoType;
        }

        public void setVideoType(Integer videoType) {
            this.videoType = videoType;
        }

        public Integer getDelivery() {
            return delivery;
        }

        public void setDelivery(Integer delivery) {
            this.delivery = delivery;
        }

        public Integer getOrientation() {
            return orientation;
        }

        public void setOrientation(Integer orientation) {
            this.orientation = orientation;
        }

        public Video() {
        }

        @Generated("SparkTools")
        private Video(Builder builder) {
            this.minDuration = builder.minDuration;
            this.maxDuration = builder.maxDuration;
            this.mimeType = builder.mimeType;
            this.sizes = builder.sizes;
            this.maxLength = builder.maxLength;
            this.videoType = builder.videoType;
            this.delivery = builder.delivery;
            this.orientation = builder.orientation;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Integer minDuration;
            private Integer maxDuration;
            private String mimeType;
            private Size[] sizes;
            private Integer maxLength;
            private Integer videoType;
            private Integer delivery;
            private Integer orientation;

            public Builder withMinDuration(Integer minDuration) {
                this.minDuration = minDuration;
                return this;
            }

            public Builder withMaxDuration(Integer maxDuration) {
                this.maxDuration = maxDuration;
                return this;
            }

            public Builder withMimeType(String mimeType) {
                this.mimeType = mimeType;
                return this;
            }

            public Builder withSize(Size[] sizes) {
                this.sizes = sizes;
                return this;
            }

            public Builder withMaxLength(Integer maxLength) {
                this.maxLength = maxLength;
                return this;
            }

            public Builder withVideoType(Integer videoType) {
                this.videoType = videoType;
                return this;
            }

            public Builder withDelivery(Integer delivery) {
                this.delivery = delivery;
                return this;
            }

            public Builder withOrientation(Integer orientation) {
                this.orientation = orientation;
                return this;
            }

            public Builder() {
            }

            public Video build() {
                return new Video(this);
            }
        }
    }

    public static class Size {
        /**
         * 允许的视频尺⼨寸宽度，默认为0不不限制
         */
        private Integer width;
        /**
         * 允许的视频尺⼨寸⾼高度，默认为0不不限制
         */
        private Integer height;

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

        public Size() {
        }

        @Generated("SparkTools")
        private Size(Builder builder) {
            this.width = builder.width;
            this.height = builder.height;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Integer width;
            private Integer height;

            public Builder withWidth(Integer width) {
                this.width = width;
                return this;
            }

            public Builder withHeight(Integer height) {
                this.height = height;
                return this;
            }

            public Builder() {
            }

            public Size build() {
                return new Size(this);
            }
        }
    }

}

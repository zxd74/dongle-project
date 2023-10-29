package com.iwanvi.adserv.ngx.router.broker.xiaomi;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-07-16 16:57
 * @version: v1.0
 * @Description:  米盟广告请求 类   (必填) 注意
 */
public class BidRequest {

    /**
     * 广告位 Id(必填)
     */
    private String upId;
    /**
     * 版本号，(必填)
     */
    private String v;
    /**
     * 请求参数信息(必填)
     */
    private String clientInfo;

    public BidRequest(){}

    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    @Generated("SparkTools")
    private BidRequest(Builder builder){
        this.upId = builder.upId;
        this.v = builder.v;
        this.clientInfo = builder.clientInfo;
    }

    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}

    @Generated("SparkTools")
    public static final class Builder{
        private String upId;
        private String v;
        private String clientInfo;

        public Builder withUpId(String upId){
            this.upId = upId;
            return this;
        }
        public Builder withVersion(String v){
            this.v = v;
            return this;
        }
        public Builder withClientInfo(String clientInfo){
            this.clientInfo = clientInfo;
            return this;
        }
        public Builder(){}

        public BidRequest build(){return new BidRequest(this);}
    }

    public static class ClientInfo{
        /**
         * 设备信息 (必填)
         */
        private DeviceInfo deviceInfo;
        /**
         * 用户信息 (必填)
         */
        private UserInfo userInfo;
        /**
         * 应用信息 (必填)
         */
        private AppInfo appInfo;
        /**
         * 广告位信息 (必填)
         */
        private ImpRequests impRequests;
        /**
         * 广告位上下文内容信息，视频广告必填
         */
        private ContentInfo contentInfo;
        /**
         * 保留字段
         */
        private Context context;
        /**
         * 获取广告的模式，用于预缓存物料场景，默认0 - 实时请求
         */
        private Integer fetchMode = 0;

        public ClientInfo(){}

        public DeviceInfo getDeviceInfo() {
            return deviceInfo;
        }

        public void setDeviceInfo(DeviceInfo deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public AppInfo getAppInfo() {
            return appInfo;
        }

        public void setAppInfo(AppInfo appInfo) {
            this.appInfo = appInfo;
        }

        public ImpRequests getImpRequests() {
            return impRequests;
        }

        public void setImpRequests(ImpRequests impRequests) {
            this.impRequests = impRequests;
        }

        public ContentInfo getContentInfo() {
            return contentInfo;
        }

        public void setContentInfo(ContentInfo contentInfo) {
            this.contentInfo = contentInfo;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public Integer getFetchMode() {
            return fetchMode;
        }

        public void setFetchMode(Integer fetchMode) {
            this.fetchMode = fetchMode;
        }

        @Generated("SparkTools")
        private ClientInfo(Builder builder){
            this.deviceInfo = builder.deviceInfo;
            this.userInfo  = builder.userInfo;
            this.appInfo = builder.appInfo;
            this.impRequests = builder.impRequests;
            this.contentInfo  = builder.contentInfo;
            this.context = builder.context;
            this.fetchMode = builder.fetchMode;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private DeviceInfo deviceInfo;
            private UserInfo userInfo;
            private AppInfo appInfo;
            private ImpRequests impRequests;
            private ContentInfo contentInfo;
            private Context context;
            private Integer fetchMode;

            public Builder withDeviceInfo(DeviceInfo deviceInfo){
                this.deviceInfo = deviceInfo;
                return this;
            }
            public Builder withUserInfo(UserInfo userInfo){
                this.userInfo = userInfo;
                return this;
            }
            public Builder withAppInfo(AppInfo appInfo){
                this.appInfo = appInfo;
                return this;
            }
            public Builder withImpRequests(ImpRequests impRequests){
                this.impRequests = impRequests;
                return this;
            }
            public Builder withContentInfo(ContentInfo contentInfo){
                this.contentInfo = contentInfo;
                return this;
            }
            public Builder withContext(Context context){
                this.context = context;
                return this;
            }
            public Builder withFetchMode(Integer fetchMode){
                this.fetchMode = fetchMode;
                return this;
            }

            public Builder(){}

            public ClientInfo build(){return new ClientInfo(this);}
        }
    }

    public static class DeviceInfo{
        /**
         * 小米平台自研真机识别标识。小米平台必填。(必填)
         */
        private String tokenId;
        /**
         * 设备制造商 (必填)
         */
        private String make;
        /**
         * 操作系统 (必填)
         */
        private String os;
        /**
         * 设备型号 (必填)
         */
        private String model;
        /**
         * android 版本号
         */
        private String androidVersion;
        /**
         * ios 版本号
         */
        private String iosVersion;
        /**
         * miui 版本号
         */
        private String miuiVersion;

        public String getTokenId() {
            return tokenId;
        }

        public void setTokenId(String tokenId) {
            this.tokenId = tokenId;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getAndroidVersion() {
            return androidVersion;
        }

        public void setAndroidVersion(String androidVersion) {
            this.androidVersion = androidVersion;
        }

        public String getIosVersion() {
            return iosVersion;
        }

        public void setIosVersion(String iosVersion) {
            this.iosVersion = iosVersion;
        }

        public String getMiuiVersion() {
            return miuiVersion;
        }

        public void setMiuiVersion(String miuiVersion) {
            this.miuiVersion = miuiVersion;
        }

        public DeviceInfo(){}

        @Generated("SparkTools")
        private DeviceInfo(Builder builder){
            this.tokenId = builder.tokenId;
            this.make = builder.make;
            this.os = builder.os;
            this.model = builder.model;
            this.androidVersion = builder.androidVersion;
            this.iosVersion = builder.iosVersion;
            this.miuiVersion = builder.miuiVersion;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String tokenId;
            private String make;
            private String os;
            private String model;
            private String androidVersion;
            private String iosVersion;
            private String miuiVersion;

            public Builder withTokenId(String tokenId){
                this.tokenId = tokenId;
                return this;
            }
            public Builder withMake(String make){
                this.make = make;
                return this;
            }
            public Builder withOs(String os){
                this.os = os;
                return this;
            }
            public Builder withModel(String model){
                this.model = model;
                return this;
            }
            public Builder withAndroidVersion(String androidVersion){
                this.androidVersion = androidVersion;
                return this;
            }
            public Builder withIosVersion(String iosVersion){
                this.iosVersion = iosVersion;
                return this;
            }
            public Builder withMiuiVersion(String miuiVersion){
                this.miuiVersion = miuiVersion;
                return this;
            }


            public Builder(){}

            public DeviceInfo build(){return new DeviceInfo(this);}
        }
    }

    public static class UserInfo{
        /**
         * android 设备的 ID, 原始(必填)
         */
        private String androidId;
        /**
         * android 设备imei 的md5sum小写,(必填)
         */
        private String imei;
        /**
         * iOS 设备 idfa的md5sum小写,(必填)
         */
        private String idfa;
        /**
         * 网络连接类型，(必填)
         */
        private String connectionType;
        /**
         * 终端用户IPv4 地址，(必填)
         */
        private String ip;
        /**
         * mac 地址，小写
         */
        private String mac;

        public String getAndroidId() {
            return androidId;
        }

        public void setAndroidId(String androidId) {
            this.androidId = androidId;
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

        public String getConnectionType() {
            return connectionType;
        }

        public void setConnectionType(String connectionType) {
            this.connectionType = connectionType;
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

        public UserInfo(){}
        @Generated("SparkTools")
        private UserInfo(Builder builder){
            this.androidId = builder.androidId;
            this.imei = builder.imei;
            this.idfa = builder.idfa;
            this.connectionType = builder.connectionType;
            this.ip = builder.ip;
            this.mac = builder.mac;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String androidId;
            private String imei;
            private String idfa;
            private String connectionType;
            private String ip;
            private String mac;

            public Builder withAndroidId(String androidId){
                this.androidId = androidId;
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
            public Builder withConnectionType(String connectionType){
                this.connectionType = connectionType;
                return this;
            }
            public Builder withIp(String ip){
                this.ip = ip;
                return this;
            }
            public Builder withMac(String mac){
                this.mac = mac;
                return this;
            }

            public Builder(){}

            public UserInfo build(){return new UserInfo(this);}
        }
    }

    public static class AppInfo{
        /**
         * 应用包名 （必填）
         */
        private String packageName;
        /**
         * 版本
         */
        private String version;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public AppInfo(){}

        private AppInfo(Builder builder){
            this.packageName = builder.packageName;
            this.version = builder.version;
        }

        public static Builder builder(){return new Builder();}

        public static final class Builder{
            private String packageName;
            private String version;

            public Builder withPackageName(String packageName){
                this.packageName = packageName;
                return this;
            }
            public Builder withVersion(String version){
                this.version = version;
                return this;
            }

            public Builder(){}

            public AppInfo build(){return new AppInfo(this);}
        }
    }

    public static class ImpRequests{
        /**
         * 广告数量，默认1，（必填）
         */
        private Integer adsCount = 1;
        /**
         * 广告位宽度，（必填）
         */
        private Integer width;
        /**
         * 广告位高度，（必填）
         */
        private Integer height;

        public Integer getAdsCount() {
            return adsCount;
        }

        public void setAdsCount(Integer adsCount) {
            this.adsCount = adsCount;
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

        public ImpRequests(){}
        @Generated("SparkTools")
        private ImpRequests(Builder builder){
            this.adsCount = builder.adsCount;
            this.width = builder.width;
            this.height = builder.height;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private Integer adsCount;
            private Integer width;
            private Integer height;

            public Builder withAdsCount(Integer adsCount){
                this.adsCount = adsCount;
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

            public Builder(){}

            public ImpRequests build(){return new ImpRequests(this);}
        }
    }

    public static class ContentInfo{
        /**
         * 视频标题  （必填）
         */
        private String title;
        /**
         * 视频类别 （必填）
         */
        private String category;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public ContentInfo(){}
        @Generated("SparkTools")
        private ContentInfo(Builder builder){
            this.title = builder.title;
            this.category = builder.category;
        }
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String title;
            private String category;

            public Builder withTitle(String title){
                this.title = title;
                return this;
            }
            public Builder withCategory(String category){
                this.category = category;
                return this;
            }

            public Builder(){}

            public ContentInfo build(){return new ContentInfo(this);}
        }
    }

    public static class Context{
        /**
         * 渠道号（必填）
         */
        private String fictionId;

        public String getFictionId() {
            return fictionId;
        }

        public void setFictionId(String fictionId) {
            this.fictionId = fictionId;
        }

        public Context(){}
        @Generated("SparkTools")
        private Context(Builder builder){}
        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}
        @Generated("SparkTools")
        public static final class Builder{
            private String fictionId;
            public Builder withFictionId(String fictionId){
                this.fictionId = fictionId;
                return this;
            }
            public Builder(){}

            public Context build(){return new Context(this);}
        }
    }
}

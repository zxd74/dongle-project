package com.iwanvi.adserv.ngx.router.broker.liebao;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;

/**
 * @author: 郑晓东
 * @date: 2019-06-06 15:15
 * @version: v1.0
 * @Description: 猎豹请求类
 */
public class BidRequest {

    /**
     * 请求唯一ID，由流量方生成
     * 必填
     */
    private String id;
    /**
     * 广告位曝光的imp对象列表
     * 必填
     */
    private Imp[] imp;
    /**
     * 移动APP流量属性
     * 必填
     */
    private App app;
    /**
     * 用户设备信息
     * 必填
     */
    private Device device;
    /**
     * 受众信息
     * 选填（推荐）
     */
    private User user;
    /**
     * 0—生产模式 1—测试模式 默认0
     * 选填
     */
    private Integer test;
    /**
     * 1—GFP 2—GSP
     * 选填
     */
    private Integer at;
    /**
     * 超时时间，单位毫秒
     * 选填
     */
    private Integer tmax;
    /**
     * 货币目前只支持一种 ISO-4217标准
     * 国内为CNY
     * 海外为USD
     * 选填
     */
    private String[] cur;
    /**
     * 广告行业黑名单
     * 选填
     */
    private String[] bcat;
    /**
     * 域名黑名单，例如：(eg: test.com)
     * 选填
     */
    private String[] badv;
    /**
     * 扩展字段
     * 选填
     */
    private Ext ext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Imp[] getImp() {
        return imp;
    }

    public void setImp(Imp[] imp) {
        this.imp = imp;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    public Integer getAt() {
        return at;
    }

    public void setAt(Integer at) {
        this.at = at;
    }

    public Integer getTmax() {
        return tmax;
    }

    public void setTmax(Integer tmax) {
        this.tmax = tmax;
    }

    public String[] getCur() {
        return cur;
    }

    public void setCur(String[] cur) {
        this.cur = cur;
    }

    public String[] getBcat() {
        return bcat;
    }

    public void setBcat(String[] bcat) {
        this.bcat = bcat;
    }

    public String[] getBadv() {
        return badv;
    }

    public void setBadv(String[] badv) {
        this.badv = badv;
    }

    public Ext getExt() {
        return ext;
    }

    public void setExt(Ext ext) {
        this.ext = ext;
    }

    public BidRequest() {
    }

    @Generated("SparkTools")
    private BidRequest(Builder builder) {
        this.id = builder.id;
        this.imp = builder.imp;
        this.app = builder.app;
        this.device = builder.device;
        this.user = builder.user;
        this.test = builder.test;
        this.at = builder.at;
        this.tmax = builder.tmax;
        this.cur = builder.cur;
        this.bcat = builder.bcat;
        this.badv = builder.badv;
        this.ext = builder.ext;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder {
        private String id;
        private Imp[] imp;
        private App app;
        private Device device;
        private User user;
        private Integer test;
        private Integer at;
        private Integer tmax;
        private String[] cur;
        private String[] bcat;
        private String[] badv;
        private Ext ext;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withImps(Imp[] imp) {
            this.imp = imp;
            return this;
        }

        public Builder withApp(App app) {
            this.app = app;
            return this;
        }

        public Builder withDevice(Device device) {
            this.device = device;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withTest(Integer test) {
            this.test = test;
            return this;
        }

        public Builder withAt(Integer at) {
            this.at = at;
            return this;
        }

        public Builder withTMax(Integer tmax) {
            this.tmax = tmax;
            return this;
        }

        public Builder withCur(String[] cur) {
            this.cur = cur;
            return this;
        }

        public Builder withBCat(String[] bcat) {
            this.bcat = bcat;
            return this;
        }

        public Builder withBAdv(String[] badv) {
            this.badv = badv;
            return this;
        }

        public Builder withExt(Ext ext) {
            this.ext = ext;
            return this;
        }

        public Builder() {
        }

        public BidRequest build() {
            return new BidRequest(this);
        }

    }

    /**
     * 广告位曝光的imp对象列表
     */
    public static class Imp {
        /**
         * 曝光ID，通常从1开始
         * 必填
         */
        private String id;
        /**
         * 流量方在猎豹注册的广告位标识ID
         * 必填
         */
        @JSONField(name = "posid")
        private String posId;
        /**
         * 流量方广告位标识ID
         * 选填
         */
        @JSONField(name = "tagid")
        private String tagId;
        /**
         * 竞价底价， 单位：中国地区CNY（元），
         * 其他国家USD(美元)，默认 0.01/CPM
         * 必填
         */
        private Float bidfloor;
        /**
         * 货币，使用ISO-4217标准，暂支持一种
         * 国内：CNY
         * 海外：USD
         * 单位：元/千次展现
         * 选填
         */
        private String bidfloorcur;
        /**
         * Native对象（native/banner/video 3选1）
         * 选填
         */
        @JSONField(name = "native")
        private Native nat;
        /**
         * Banner对象（native/banner/video 3选1）
         * 选填
         */
        private Banner banner;
        /**
         * 视频对象（native/banner/video 3选1）
         * 选填
         */
        private Video video;
        /**
         * 私有交易
         * 选填
         */
        private Pmp pmp;
        /**
         * 扩展字段
         * 选填
         */
        private Ext ext;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosId() {
            return posId;
        }

        public void setPosId(String posId) {
            this.posId = posId;
        }

        public String getTagId() {
            return tagId;
        }

        public void setTagId(String tagId) {
            this.tagId = tagId;
        }

        public Float getBidfloor() {
            return bidfloor;
        }

        public void setBidfloor(Float bidfloor) {
            this.bidfloor = bidfloor;
        }

        public String getBidfloorcur() {
            return bidfloorcur;
        }

        public void setBidfloorcur(String bidfloorcur) {
            this.bidfloorcur = bidfloorcur;
        }

        public Native getNat() {
            return nat;
        }

        public void setNat(Native nat) {
            this.nat = nat;
        }

        public Banner getBanner() {
            return banner;
        }

        public void setBanner(Banner banner) {
            this.banner = banner;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public Pmp getPmp() {
            return pmp;
        }

        public void setPmp(Pmp pmp) {
            this.pmp = pmp;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Imp() {
        }

        @Generated("SparkTools")
        private Imp(Builder builder) {
            this.id = builder.id;
            this.posId = builder.posId;
            this.tagId = builder.tagId;
            this.bidfloor = builder.bidfloor;
            this.bidfloorcur = builder.bidfloorcur;
            this.nat = builder.nat;
            this.banner = builder.banner;
            this.video = builder.video;
            this.pmp = builder.pmp;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String id;
            private String posId;
            private String tagId;
            private Float bidfloor;
            private String bidfloorcur;
            private Native nat;
            private Banner banner;
            private Video video;
            private Pmp pmp;
            private Ext ext;

            public Builder withId(String id) {
                this.id = id;
                return this;
            }

            public Builder withPosId(String posId) {
                this.posId = posId;
                return this;
            }

            public Builder withTagId(String tagId) {
                this.tagId = tagId;
                return this;
            }

            public Builder withBidfloor(Float bidfloor) {
                this.bidfloor = bidfloor;
                return this;
            }

            public Builder withBidfloorCur(String bidfloorcur) {
                this.bidfloorcur = bidfloorcur;
                return this;
            }

            public Builder withNative(Native nat) {
                this.nat = nat;
                return this;
            }

            public Builder withBanner(Banner banner) {
                this.banner = banner;
                return this;
            }

            public Builder withVideo(Video video) {
                this.video = video;
                return this;
            }

            public Builder withPmp(Pmp pmp) {
                this.pmp = pmp;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Imp build() {
                return new Imp(this);
            }
        }
    }

    /**
     * Native对象
     */
    public static class Native {
        /**
         * NATIVE接口版本号，默认1.1
         * 选填
         */
        private String ver;
        /**
         * 请求信息,遵循NATIVE请求接口
         * 必填
         */
        private String request;
        /**
         * 扩展字段
         * 选填
         */
        private Ext ext;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getRequest() {
            return request;
        }

        public void setRequest(String request) {
            this.request = request;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Native() {
        }

        @Generated("SparkTools")
        private Native(Builder builder) {
            this.ver = builder.ver;
            this.request = builder.request;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String ver;
            private String request;
            private Ext ext;

            public Builder withVer(String ver) {
                this.ver = ver;
                return this;
            }

            public Builder withRequest(String request) {
                this.request = request;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Native build() {
                return new Native(this);
            }
        }
    }

    /**
     * NATIVE请求接口描述
     * 转换成json字符串填充Native.request
     */
    public static class NativeRequst{
        @JSONField(name = "native")
        private NativeTopLevel nativeTopLevel;

        public NativeRequst( NativeTopLevel nativeTopLevel){this.nativeTopLevel = nativeTopLevel;}

        public NativeTopLevel getNativeTopLevel() {
            return nativeTopLevel;
        }

        public void setNativeTopLevel(NativeTopLevel nativeTopLevel) {
            this.nativeTopLevel = nativeTopLevel;
        }
    }

    /**
     * NATIVE请求接口描述
     * 转换成json字符串填充NativeRequst
     */
    public static class NativeTopLevel{

        /**
         * 请求位置的信息
         * 必填
         */
        private Asset[] assets;

        public NativeTopLevel(){}

        public NativeTopLevel(Asset[] assets){this.assets = assets;}

        public Asset[] getAssets() {
            return assets;
        }

        public void setAssets(Asset[] assets) {
            this.assets = assets;
        }

    }

    /**
     * 请求位置的信息
     */
    public static class Asset {
        /**
         * 位置ID，一个ID对应一个属性
         * 可选
         */
        private Integer id;
        /**
         * 1—需要此asset 0—不需要
         * 可选
         */
        private Integer required;
        /**
         * 图片属性，用于样式区别
         * 可选
         */
        private Img img;
        /**
         * 标题属性
         * 可选
         */
        private Title title;
        /**
         * 描述属性
         * 可选
         */
        private Data data;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getRequired() {
            return required;
        }

        public void setRequired(Integer required) {
            this.required = required;
        }

        public Img getImg() {
            return img;
        }

        public void setImg(Img img) {
            this.img = img;
        }

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Asset() {
        }

        @Generated("SparkTools")
        private Asset(Builder builder) {
            this.id = builder.id;
            this.required = builder.required;
            this.img = builder.img;
            this.title = builder.title;
            this.data = builder.data;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Integer id;
            private Integer required;
            private Img img;
            private Title title;
            private Data data;

            public Builder withId(Integer id) {
                this.id = id;
                return this;
            }

            public Builder withRequired(Integer required) {
                this.required = required;
                return this;
            }

            public Builder withImg(Img img) {
                this.img = img;
                return this;
            }

            public Builder withTitle(Title title) {
                this.title = title;
                return this;
            }

            public Builder withData(Data data) {
                this.data = data;
                return this;
            }

            public Builder() {
            }

            public Asset build() {
                return new Asset(this);
            }
        }

    }

    /**
     * Img 对象
     */
    public static class Img {
        /**
         * 图片类型
         */
        private Integer type;
        /**
         * 图片宽
         */
        private Integer w;
        /**
         * 图片高
         */
        private Integer h;
        /**
         * 图片最小宽
         */
        private Integer wmin;
        /**
         * 图片最小高
         */
        private Integer hmin;
        /**
         * 图片扩展类型，支持的MIME type，例如image/jpeg
         */
        private String[] mimes;
        /**
         * 扩展字段
         */
        private Ext ext;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getW() {
            return w;
        }

        public void setW(Integer w) {
            this.w = w;
        }

        public Integer getH() {
            return h;
        }

        public void setH(Integer h) {
            this.h = h;
        }

        public Integer getWmin() {
            return wmin;
        }

        public void setWmin(Integer wmin) {
            this.wmin = wmin;
        }

        public Integer getHmin() {
            return hmin;
        }

        public void setHmin(Integer hmin) {
            this.hmin = hmin;
        }

        public String[] getMimes() {
            return mimes;
        }

        public void setMimes(String[] mimes) {
            this.mimes = mimes;
        }

        public Ext getExt() {
            return ext;
        }

        public void setEx(Ext ext) {
            this.ext = ext;
        }

        public Img() {
        }

        @Generated("SparkTools")
        private Img(Builder builder) {
            this.type = builder.type;
            this.w = builder.w;
            this.h = builder.h;
            this.wmin = builder.wmin;
            this.hmin = builder.hmin;
            this.mimes = builder.mimes;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Integer type;
            private Integer w;
            private Integer h;
            private Integer wmin;
            private Integer hmin;
            private String[] mimes;
            private Ext ext;

            public Builder withType(Integer type) {
                this.type = type;
                return this;
            }

            public Builder withWidth(Integer w) {
                this.w = w;
                return this;
            }

            public Builder withHeight(Integer h) {
                this.h = h;
                return this;
            }

            public Builder withWidthMin(Integer wmin) {
                this.wmin = wmin;
                return this;
            }

            public Builder withHeightMin(Integer hmin) {
                this.hmin = hmin;
                return this;
            }

            public Builder withMimes(String[] mimes) {
                this.mimes = mimes;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Img build() {
                return new Img(this);
            }
        }
    }

    /**
     * Title对象
     */
    public static class Title {
        /**
         * 小于等于100字符
         * 选填
         */
        private Integer len;

        public Title() {
        }

        public Title(Integer len) {
            this.len = len;
        }

        public Integer getLen() {
            return len;
        }

        public void setLen(Integer len) {
            this.len = len;
        }
    }

    /**
     * 描述Data对象
     */
    public static class Data {
        /**
         * Data 类型，见DataType
         * 可选
         */
        private Integer type;
        /**
         * 长度小于等于500
         * 可选
         */
        private Integer len;

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getLen() {
            return len;
        }

        public void setLen(Integer len) {
            this.len = len;
        }

        public Data() {
        }

        @Generated("SparkTools")
        private Data(Builder builder) {
            this.type = builder.type;
            this.len = builder.len;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Integer type;
            private Integer len;

            public Builder withType(Integer type) {
                this.type = type;
                return this;
            }

            public Builder withLen(Integer len) {
                this.len = len;
                return this;
            }

            public Builder() {
            }

            public Data build() {
                return new Data(this);
            }
        }
    }

    /**
     * Banner
     */
    public static class Banner {
        /**
         * 代表banner允许的尺寸
         * 推荐
         */
        private Format[] format;
        /**
         * 展示的宽度：像素
         * 推荐
         */
        private Integer w;
        /**
         * 展示的高度：像素
         * 推荐
         */
        private Integer h;
        /**
         * 展示宽度的最小值：像素
         */
        private Integer wmin;
        /**
         * 展示高度的最小值：像素
         */
        private Integer hmin;
        /**
         * 支持的mime-type (image/jpeg)
         */
        private String[] mimes;
        /**
         * 扩展字段
         */
        private Ext ext;

        public Format[] getFormat() {
            return format;
        }

        public void setFormat(Format[] format) {
            this.format = format;
        }

        public Integer getW() {
            return w;
        }

        public void setW(Integer w) {
            this.w = w;
        }

        public Integer getH() {
            return h;
        }

        public void setH(Integer h) {
            this.h = h;
        }

        public Integer getWmin() {
            return wmin;
        }

        public void setWmin(Integer wmin) {
            this.wmin = wmin;
        }

        public Integer getHmin() {
            return hmin;
        }

        public void setHmin(Integer hmin) {
            this.hmin = hmin;
        }

        public String[] getMimes() {
            return mimes;
        }

        public void setMimes(String[] mimes) {
            this.mimes = mimes;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Banner() {
        }

        @Generated("SparkTools")
        private Banner(Builder builder) {
            this.format = builder.format;
            this.w = builder.w;
            this.h = builder.h;
            this.wmin = builder.wmin;
            this.hmin = builder.hmin;
            this.mimes = builder.mimes;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Format[] format;
            private Integer w;
            private Integer h;
            private Integer wmin;
            private Integer hmin;
            private String[] mimes;
            private Ext ext;

            public Builder withFormats(Format[] format) {
                this.format = format;
                return this;
            }

            public Builder withWidth(Integer w) {
                this.w = w;
                return this;
            }

            public Builder withHeight(Integer h) {
                this.h = h;
                return this;
            }

            public Builder withWidthMin(Integer wmin) {
                this.wmin = wmin;
                return this;
            }

            public Builder withHeightMin(Integer hmin) {
                this.hmin = hmin;
                return this;
            }

            public Builder withMimes(String[] mimes) {
                this.mimes = mimes;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Banner build() {
                return new Banner(this);
            }
        }
    }

    /**
     * banner 规格
     */
    public static class Format {
        /**
         * 展示的宽度：像素
         * 推荐
         */
        private Integer w;
        /**
         * 展示的高度：像素
         * 推荐
         */
        private Integer h;

        public Integer getW() {
            return w;
        }

        public void setW(Integer w) {
            this.w = w;
        }

        public Integer getH() {
            return h;
        }

        public void setH(Integer h) {
            this.h = h;
        }

        public Format() {
        }

        private Format(Builder builder) {
            this.w = builder.w;
            this.h = builder.h;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static final class Builder {
            private Integer w;
            private Integer h;

            public Builder withWidth(Integer w) {
                this.w = w;
                return this;
            }

            public Builder withHeight(Integer h) {
                this.h = h;
                return this;
            }

            public Builder() {
            }

            public Format build() {
                return new Format(this);
            }
        }
    }

    /**
     * Video对象
     */
    public static class Video {
        /**
         * 视频类型 见VideoType
         * 必填
         */
        @JSONField(name = "video_type")
        private Integer videoType;
        /**
         * 支持的视频格式（video/mp4,video/x-ms-wmv）
         * 必填
         */
        private String[] mimes;
        /**
         * 视频最短时长（单位：秒）
         */
        @JSONField(name = "minDuration")
        private Integer minDuration;
        /**
         * 支持的vast video协议
         */
        @JSONField(name = "minDuration")
        private Integer maxDuration;
        /**
         * 支持的vast video协议，见ProtocolType
         * 必填
         */
        private Integer[] protocols;
        /**
         * 播放器宽（广告位尺寸）
         * 必填
         */
        private Integer w;
        /**
         * 播放器高（广告位尺寸）
         * 必填
         */
        private Integer h;
        /**
         * 播放器是否允许视频可跳过（1=Yes， 0= No），见Skip
         */
        private Integer skip;
        /**
         * 广告在屏幕上曝光位置，见PosType
         */
        private Integer pos;
        /**
         * 视频广告以linear 或 nonlinear方式展现
         * 必填
         */
        private Integer linearity;
        /**
         * 扩展字段
         */
        private Ext ext;

        public Integer getVideoType() {
            return videoType;
        }

        public void setVideoType(Integer videoType) {
            this.videoType = videoType;
        }

        public String[] getMimes() {
            return mimes;
        }

        public void setMimes(String[] mimes) {
            this.mimes = mimes;
        }

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

        public Integer[] getProtocols() {
            return protocols;
        }

        public void setProtocols(Integer[] protocols) {
            this.protocols = protocols;
        }

        public Integer getW() {
            return w;
        }

        public void setW(Integer w) {
            this.w = w;
        }

        public Integer getH() {
            return h;
        }

        public void setH(Integer h) {
            this.h = h;
        }

        public Integer getSkip() {
            return skip;
        }

        public void setSkip(Integer skip) {
            this.skip = skip;
        }

        public Integer getPos() {
            return pos;
        }

        public void setPos(Integer pos) {
            this.pos = pos;
        }

        public Integer getLinearity() {
            return linearity;
        }

        public void setLinearity(Integer linearity) {
            this.linearity = linearity;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Video() {
        }

        @Generated("SparkTools")
        private Video(Builder builder) {
            this.videoType = builder.videoType;
            this.mimes = builder.mimes;
            this.minDuration = builder.minduration;
            this.maxDuration = builder.maxduration;
            this.protocols = builder.protocols;
            this.w = builder.w;
            this.h = builder.h;
            this.skip = builder.skip;
            this.pos = builder.pos;
            this.linearity = builder.linearity;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Integer videoType;
            private String[] mimes;
            private Integer minduration;
            private Integer maxduration;
            private Integer[] protocols;
            private Integer w;
            private Integer h;
            private Integer skip;
            private Integer pos;
            private Integer linearity;
            private Ext ext;

            public Builder withVideoType(Integer videoType) {
                this.videoType = videoType;
                return this;
            }

            public Builder withMimes(String[] mimes) {
                this.mimes = mimes;
                return this;
            }

            public Builder withMinDuration(Integer minduration) {
                this.minduration = minduration;
                return this;
            }

            public Builder withMaxDuration(Integer maxduration) {
                this.maxduration = maxduration;
                return this;
            }

            public Builder withProtocols(Integer[] protocols) {
                this.protocols = protocols;
                return this;
            }

            public Builder withWidth(Integer w) {
                this.w = w;
                return this;
            }

            public Builder withHeight(Integer h) {
                this.h = h;
                return this;
            }

            public Builder withSkip(Integer skip) {
                this.skip = skip;
                return this;
            }

            public Builder withPos(Integer pos) {
                this.pos = pos;
                return this;
            }

            public Builder withLinearity(Integer linearity) {
                this.linearity = linearity;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Video build() {
                return new Video(this);
            }
        }
    }

    /**
     * 移动APP流量属性
     */
    public static class App {
        /**
         * 流量方设置的APPID
         * 推荐
         */
        private String id;
        /**
         * 应用名称
         */
        private String name;
        /**
         * 包名
         * 必填
         */
        private String bundle;
        /**
         * App对应的域名
         */
        private String domain;
        /**
         * 应用商店地址
         */
        private String storeurl;
        /**
         * 对应类别
         */
        private String[] cat;
        /**
         * 应用的当前部分的分类
         */
        private String[] sectioncat;
        /**
         * 版本号
         */
        private String ver;
        /**
         * 0—免费 1—付费
         */
        private Integer paid;
        /**
         * 流量信息
         */
        private Publisher publisher;
        /**
         * App关键词描述
         */
        private String keyworks;
        /**
         * 保留扩展字段
         */
        private Ext ext;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBundle() {
            return bundle;
        }

        public void setBundle(String bundle) {
            this.bundle = bundle;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getStoreurl() {
            return storeurl;
        }

        public void setStoreurl(String storeurl) {
            this.storeurl = storeurl;
        }

        public String[] getCat() {
            return cat;
        }

        public void setCat(String[] cat) {
            this.cat = cat;
        }

        public String[] getSectioncat() {
            return sectioncat;
        }

        public void setSectioncat(String[] sectioncat) {
            this.sectioncat = sectioncat;
        }

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public Integer getPaid() {
            return paid;
        }

        public void setPaid(Integer paid) {
            this.paid = paid;
        }

        public Publisher getPublisher() {
            return publisher;
        }

        public void setPublisher(Publisher publisher) {
            this.publisher = publisher;
        }

        public String getKeyworks() {
            return keyworks;
        }

        public void setKeyworks(String keyworks) {
            this.keyworks = keyworks;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public App() {
        }

        @Generated("SparkTools")
        private App(Builder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.bundle = builder.bundle;
            this.domain = builder.domain;
            this.storeurl = builder.storeurl;
            this.cat = builder.cat;
            this.sectioncat = builder.sectioncat;
            this.ver = builder.ver;
            this.paid = builder.paid;
            this.publisher = builder.publisher;
            this.keyworks = builder.keyworks;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String id;
            private String name;
            private String bundle;
            private String domain;
            private String storeurl;
            private String[] cat;
            private String[] sectioncat;
            private String ver;
            private Integer paid;
            private Publisher publisher;
            private String keyworks;
            private Ext ext;

            public Builder withId(String id) {
                this.id = id;
                return this;
            }

            public Builder withName(String name) {
                this.name = name;
                return this;
            }

            public Builder withBundle(String bundle) {
                this.bundle = bundle;
                return this;
            }

            public Builder withDomain(String domain) {
                this.domain = domain;
                return this;
            }

            public Builder withStoreUrl(String storeurl) {
                this.storeurl = storeurl;
                return this;
            }

            public Builder withCat(String[] cat) {
                this.cat = cat;
                return this;
            }

            public Builder withSectionCat(String[] sectioncat) {
                this.sectioncat = sectioncat;
                return this;
            }

            public Builder withVer(String ver) {
                this.ver = ver;
                return this;
            }

            public Builder withPaId(Integer paid) {
                this.paid = paid;
                return this;
            }

            public Builder withPublisher(Publisher publisher) {
                this.publisher = publisher;
                return this;
            }

            public Builder withKeyworks(String keyworks) {
                this.id = keyworks;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public App build() {
                return new App(this);
            }
        }
    }

    /**
     * 流量信息
     */
    public static class Publisher {
        /**
         * 平台设置的publisherid
         * 推荐
         */
        private String id;
        /**
         * publisher名称
         */
        private String name;
        /**
         * 对应类别，暂为空
         */
        private String cat;
        /**
         * Publisher最高层次的域名
         */
        private String domain;
        /**
         * 扩展字段
         */
        private Ext ext;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCat() {
            return cat;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Publisher() {
        }

        @Generated("SparkTools")
        private Publisher(Builder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.cat = builder.cat;
            this.domain = builder.domain;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String id;
            private String name;
            private String cat;
            private String domain;
            private Ext ext;

            public Builder withId(String id) {
                this.id = id;
                return this;
            }

            public Builder withName(String name) {
                this.name = name;
                return this;
            }

            public Builder withCat(String cat) {
                this.cat = cat;
                return this;
            }

            public Builder withDomain(String domain) {
                this.domain = domain;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Publisher build() {
                return new Publisher(this);
            }
        }
    }

    /**
     * 用户设备信息
     */
    public static class Device {
        /**
         * Browser User Agent
         * 推荐
         */
        private String ua;
        /**
         * 设备位置信息
         * 推荐
         */
        private Geo geo;
        /**
         * 用户的IPv4地址
         */
        private String ip;
        /**
         * 设备类型，见DeviceType
         */
        private Integer devicetype;
        /**
         * 设备厂商
         */
        private String make;
        /**
         * 设备型号
         */
        private String model;
        /**
         * 操作系统 (ios/android)
         */
        private String os;
        /**
         * 操作系统版本
         */
        private String osv;
        /**
         * 屏幕宽：像素
         */
        private Integer w;
        /**
         * 屏幕高：像素
         */
        private Integer h;
        /**
         * 每英寸上的像素数
         */
        private Integer ppi;
        /**
         * 是否支持js， 0-不支持 1-支持 默认0
         */
        private Integer js;
        /**
         * 语言简称，I​ SO-639-1-alpha-2 标准
         */
        private String language;
        /**
         * 运营商
         */
        private String carrier;
        /**
         * 联网方式 见ConnectionType
         */
        private Integer connectiontype;
        /**
         * 安卓ID或IDFA 的MD5值
         */
        private String dpidmd5;
        /**
         * IOS设备为IDFA，中国android设备为安卓id，国外android设备为gaid
         */
        private String ifa;
        /**
         * 手机imei，明文
         * 必需
         */
        private String imei;
        /**
         * 扩展字段
         */
        private Ext ext;

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getDevicetype() {
            return devicetype;
        }

        public void setDevicetype(Integer devicetype) {
            this.devicetype = devicetype;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
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

        public String getOsv() {
            return osv;
        }

        public void setOsv(String osv) {
            this.osv = osv;
        }

        public Integer getW() {
            return w;
        }

        public void setW(Integer w) {
            this.w = w;
        }

        public Integer getH() {
            return h;
        }

        public void setH(Integer h) {
            this.h = h;
        }

        public Integer getPpi() {
            return ppi;
        }

        public void setPpi(Integer ppi) {
            this.ppi = ppi;
        }

        public Integer getJs() {
            return js;
        }

        public void setJs(Integer js) {
            this.js = js;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCarrier() {
            return carrier;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public Integer getConnectiontype() {
            return connectiontype;
        }

        public void setConnectiontype(Integer connectiontype) {
            this.connectiontype = connectiontype;
        }

        public String getDpidmd5() {
            return dpidmd5;
        }

        public void setDpidmd5(String dpidmd5) {
            this.dpidmd5 = dpidmd5;
        }

        public String getIfa() {
            return ifa;
        }

        public void setIfa(String ifa) {
            this.ifa = ifa;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Device() {
        }

        @Generated("SparkTools")
        private Device(Builder builder) {
            this.ua = builder.ua;
            this.geo = builder.geo;
            this.ip = builder.ip;
            this.devicetype = builder.devicetype;
            this.make = builder.make;
            this.model = builder.model;
            this.os = builder.os;
            this.osv = builder.osv;
            this.w = builder.w;
            this.h = builder.h;
            this.ppi = builder.ppi;
            this.js = builder.js;
            this.language = builder.language;
            this.carrier = builder.carrier;
            this.connectiontype = builder.connectiontype;
            this.dpidmd5 = builder.dpidmd5;
            this.ifa = builder.ifa;
            this.imei = builder.imei;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String ua;
            private Geo geo;
            private String ip;
            private Integer devicetype;
            private String make;
            private String model;
            private String os;
            private String osv;
            private Integer w;
            private Integer h;
            private Integer ppi;
            private Integer js;
            private String language;
            private String carrier;
            private Integer connectiontype;
            private String dpidmd5;
            private String ifa;
            private String imei;
            private Ext ext;

            public Builder withUa(String ua) {
                this.ua = ua;
                return this;
            }

            public Builder withGeo(Geo geo) {
                this.geo = geo;
                return this;
            }

            public Builder withIp(String ip) {
                this.ip = ip;
                return this;
            }

            public Builder withDeviceType(Integer devicetype) {
                this.devicetype = devicetype;
                return this;
            }

            public Builder withMake(String make) {
                this.make = make;
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

            public Builder withOsv(String osv) {
                this.osv = osv;
                return this;
            }

            public Builder withWidth(Integer w) {
                this.w = w;
                return this;
            }

            public Builder withHeight(Integer h) {
                this.h = h;
                return this;
            }

            public Builder withPpi(Integer ppi) {
                this.ppi = ppi;
                return this;
            }

            public Builder withJs(Integer js) {
                this.js = js;
                return this;
            }

            public Builder withLanguage(String language) {
                this.language = language;
                return this;
            }

            public Builder withCarrier(String carrier) {
                this.carrier = carrier;
                return this;
            }

            public Builder withConnectionType(Integer connectiontype) {
                this.connectiontype = connectiontype;
                return this;
            }

            public Builder withDpIdMD5(String dpidmd5) {
                this.dpidmd5 = dpidmd5;
                return this;
            }

            public Builder withIdfa(String ifa) {
                this.ifa = ifa;
                return this;
            }

            public Builder withImei(String imei) {
                this.imei = imei;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Device build() {
                return new Device(this);
            }
        }
    }

    /**
     * 地理位置信息
     */
    public static class Geo {
        /**
         * 纬度
         */
        private Double lat;
        /**
         * 经度
         */
        private Double lon;
        /**
         * 国家，​ SO-3166-1-alpha-3 标准
         */
        private String country;
        /**
         * 定位类型 1—GPS 2---IP
         */
        private Integer type;
        /**
         * 地区
         */
        private String region;
        /**
         * 城市
         */
        private String city;
        /**
         * 时区
         */
        private Integer utcoffset;
        /**
         * 扩展字段
         */
        private Ext ext;

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Integer getUtcoffset() {
            return utcoffset;
        }

        public void setUtcoffset(Integer utcoffset) {
            this.utcoffset = utcoffset;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Geo() {
        }

        @Generated("SparkTools")
        private Geo(Builder builder) {
            this.lat = builder.lat;
            this.lon = builder.lon;
            this.country = builder.country;
            this.type = builder.type;
            this.region = builder.region;
            this.city = builder.city;
            this.utcoffset = builder.utcoffset;
            this.ext = ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Double lat;
            private Double lon;
            private String country;
            private Integer type;
            private String region;
            private String city;
            private Integer utcoffset;
            private Ext ext;

            public Builder withLat(Double lat) {
                this.lat = lat;
                return this;
            }

            public Builder withLon(Double lon) {
                this.lon = lon;
                return this;
            }

            public Builder withCountry(String country) {
                this.country = country;
                return this;
            }

            public Builder withType(Integer type) {
                this.type = type;
                return this;
            }

            public Builder withRegion(String region) {
                this.region = region;
                return this;
            }

            public Builder withCity(String city) {
                this.city = city;
                return this;
            }

            public Builder withUtcOffset(Integer utcoffset) {
                this.utcoffset = utcoffset;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Geo build() {
                return new Geo(this);
            }
        }
    }

    /**
     * 受众信息
     */
    public static class User {
        /**
         * 买方提供的ID
         * 推荐
         */
        private String buyerid;
        /**
         * 4位出生年
         */
        private Integer yob;
        /**
         * 性别 M—男 F—女 O—未知
         */
        private String gender;
        /**
         * 兴趣，逗号分隔
         */
        private String keywords;
        /**
         * 扩展字段
         */
        private Ext ext;

        public String getBuyerid() {
            return buyerid;
        }

        public void setBuyerid(String buyerid) {
            this.buyerid = buyerid;
        }

        public Integer getYob() {
            return yob;
        }

        public void setYob(Integer yob) {
            this.yob = yob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public User() {
        }

        @Generated("SparkTools")
        private User(Builder builder) {
            this.buyerid = builder.buyerid;
            this.yob = builder.yob;
            this.gender = builder.gender;
            this.keywords = builder.keywords;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String buyerid;
            private Integer yob;
            private String gender;
            private String keywords;
            private Ext ext;

            public Builder withBuyerid(String buyerid) {
                this.buyerid = buyerid;
                return this;
            }

            public Builder withYob(Integer yob) {
                this.yob = yob;
                return this;
            }

            public Builder withGender(String gender) {
                this.gender = gender;
                return this;
            }

            public Builder withKeywords(String keywords) {
                this.keywords = keywords;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public User build() {
                return new User(this);
            }
        }
    }

    /**
     * pmp对象
     */
    public static class Pmp {
        /**
         * 标识在Deal对象中指明的席位的竞拍合格标准， 0标识接受所有竞拍， 1标识竞拍受deals属性中描述的规则的限制
         */
        @JSONField(name = "private_auction")
        private Integer privateAuction;
        /**
         * 一组Deal对象， 用于传输适用于本次展示的交易信息
         */
        private Deal[] deals;
        /**
         * 扩展字段
         */
        private Ext ext;

        public Integer getPrivateAuction() {
            return privateAuction;
        }

        public void setPrivateAuction(Integer privateAuction) {
            this.privateAuction = privateAuction;
        }

        public Deal[] getDeals() {
            return deals;
        }

        public void setDeals(Deal[] deals) {
            this.deals = deals;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Pmp() {
        }

        @Generated("SparkTools")
        private Pmp(Builder builder) {
            this.privateAuction = builder.privateAuction;
            this.deals = builder.deals;
            this.ext = builder.ext;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private Integer privateAuction;
            private Deal[] deals;
            private Ext ext;

            public Builder withPrivateAuction(Integer privateAuction) {
                this.privateAuction = privateAuction;
                return this;
            }

            public Builder withDeals(Deal[] deals) {
                this.deals = deals;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Pmp build() {
                return new Pmp(this);
            }
        }
    }

    /**
     * 交易信息
     */
    public static class Deal {
        /**
         * 直接交易的唯一ID
         */
        private String id;
        /**
         * 竞价底价， 单位：中国地区CNY（元），
         * 其他国家USD(美元)，默认 0.01/CPM
         * 必填
         */
        private Float bidfloor;
        /**
         * 货币，使用ISO-4217标准，暂支持一种
         * 国内：CNY
         * 海外：USD
         * 单位：元/千次展现
         * 选填
         */
        private String bidfloorcur;
        /**
         * 1—GFP 2—GSP
         */
        private Integer at;
        /**
         * 扩展字段
         */
        private Ext ext;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Float getBidfloor() {
            return bidfloor;
        }

        public void setBidfloor(Float bidfloor) {
            this.bidfloor = bidfloor;
        }

        public String getBidfloorcur() {
            return bidfloorcur;
        }

        public void setBidfloorcur(String bidfloorcur) {
            this.bidfloorcur = bidfloorcur;
        }

        public Integer getAt() {
            return at;
        }

        public void setAt(Integer at) {
            this.at = at;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }

        public Deal() {
        }

        @Generated("SparkTools")
        private Deal(Builder builder) {
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder {
            private String id;
            private Float bidfloor;
            private String bidfloorcur;
            private Integer at;
            private Ext ext;

            public Builder withId(String id) {
                this.id = id;
                return this;
            }

            public Builder withBidfloor(Float bidfloor) {
                this.bidfloor = bidfloor;
                return this;
            }

            public Builder withBidfloorCur(String bidfloorcur) {
                this.bidfloorcur = bidfloorcur;
                return this;
            }

            public Builder withAt(Integer at) {
                this.at = at;
                return this;
            }

            public Builder withExt(Ext ext) {
                this.ext = ext;
                return this;
            }

            public Builder() {
            }

            public Deal build() {
                return new Deal(this);
            }
        }

    }

    /**
     * 扩展字段
     */
    public static class Ext {
    }

    /**
     * 货币类型 使用ISO-4217标准，暂支持一种
     * CNY == "CNY" 国内货币
     * USD == "USD" 海外货币（美元）
     */
    public enum CurType {
        CNY("CNY"), USD("USD");
        private String value;

        CurType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * Img类型
     * ICON == 1
     * LOGO == 2
     * MAIN_IMG == 3 暂时不用
     * VAST_VIDEO == 4 vast视频
     */
    public enum ImgType {
        ICON(1), LOGO(2), MAIN_IMG(3), VAST_VIDEO(4);
        private Integer value;

        ImgType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * Data类型
     * SPONSORED == 1 text
     * DESC == 2 text
     */
    public enum DataType {
        SPONSORED(1), DESC(2);
        private Integer value;

        DataType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 是否需要此assert
     * NEED == 1 需要
     * NO_NEED == 0 不需要
     */
    public enum RequiredAssert {
        NEED(1), NO_NEED(0);
        private Integer value;

        RequiredAssert(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * App付费类型
     * PAY == 1 付费
     * NO_PAY == 0 免费
     */
    public enum PaIdType {
        PAY(1), NO_PAY(0);
        private Integer value;

        PaIdType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 定位类型
     * GPS == 1
     * IP == 2
     * GFP == 1 (这个enum 混用)
     */
    public enum GeoType {
        GPS(1), IP(2), GFP(1);
        private Integer value;

        GeoType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 性别类型
     * MAN == M  男
     * FEMALE == F 女
     * UNKNOWN == O 未知
     */
    public enum GenderType {
        MAN("M"), FEMALE("F"), UNKNOWN("O");
        private String value;

        GenderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * 视频类型
     * COMMON == 1 普通视频
     * INCENTIVE == 2 激励视频
     */
    public enum VideoType {
        COMMON(1), INCENTIVE(2);
        private Integer value;

        VideoType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 是否允许跳过
     * YES == 1 允许
     * NO == 0 不允许
     */
    public enum Skip {
        YES(1), NO(0);
        private Integer value;

        Skip(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 视频展现方式
     *      LINEAR_IN_STREAM == 1 线性交互式（视频线源性分段）
     *      NON_LINEAR_OVERLAY == 2 非线性交互式（视频源非线性分段）
     */
    public enum LinearityType {
        //TODO 视频展现方式
        LINEAR_IN_STREAM(1), NON_LINEAR_OVERLAY(2);
        private Integer value;

        LinearityType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 标识在Deal对象中指明的席位的竞拍合格标准
     * ALL == 0 接受所有竞拍
     * DEAL == 1 竞拍受deals属性中描述的规则的限制
     */
    public enum PrivateAuction {
        ALL(0), DEAL(1);
        private Integer value;

        PrivateAuction(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * VAST协议类型
     */
    public enum VastType {
        VAST1(1), VAST2(2), VAST3(3), VAST1_WRAPPER(4), VAST2_WRAPPER(5), VAST3_WRAPPER(6);
        private Integer value;

        VastType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 视频广告位类型
     */
    public enum VideoPosType {
        IN_STREAM(1), IN_BANNER(2), IN_ARTICLE(3), IN_FEED(4), INTER_SLIDER_FLOATING(5);
        private Integer value;

        VideoPosType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 广告位屏幕位置
     */
    public enum PosType {
        UNKNOWN(0), TOP(4), FOOT(5), SIDE(6), FULL_SCREEN(7);
        private Integer value;

        PosType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 设备类型
     * UNKNOWN == 0 未知
     * PHONE == 4 手机
     * TABLET == 5 平板
     */
    public enum DeviceType {
        UNKNOWN(0), PHONE(4), TABLET(5);
        private Integer value;

        DeviceType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 联网方式
     * UNKNOWN == 0
     * ETHERNET == 1
     * WIFI == 2
     * UNKNOWN_G == 3 未知G
     * K2G == 4
     * K3G = 5
     * K4G = 6
     */
    public enum ConnectionType {
        UNKNOWN(0), ETHERNET(1), WIFI(2), UNKNOWN_G(3), K2G(4), K3G(5), K4G(6);
        private Integer value;

        ConnectionType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * 是否支持JS
     */
    public enum JSType {
        YES(1), NO(0);
        private Integer value;

        JSType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return this.value;
        }
    }
}

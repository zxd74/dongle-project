package com.iwanvi.adserv.ngx.router.broker.ruangao;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-05-05 11:40
 * @version: v1.0
 * @Description: 软告云响应数据结构
 */
public class BidResponse {

    /**
     * 响应的Bid Request的唯一ID
     * 必须
     */
    private String id;
    /**
     * 广告标题
     * 可选
     */
    private String title;
    /**
     * 广告描述
     * 可选
     */
    private String desc;
    /**
     * 物料地址（广告地址）
     * 必须
     */
    @JSONField(name = "ad_url")
    private String adUrl;
    /**
     * 广告宽度
     * 必须
     */
    private Integer width;
    /**
     * 广告高度
     * 必须
     */
    private Integer height;
    /**
     * 素材类型
     * 必须
     */
    @JSONField(name = "materialtype")
    private String materialType;
    /**
     * 广告展现报数链接，
     * 可选
     */
    private List<EventTrack> pv;
    /**
     * 点击报数地址
     * 可选
     */
    private List<EventTrack> click;
    /**
     * 点击落地页
     * 必须
     */
    private String target;
    /**
     * Deeplink url，用于呼起广告主app，呼起失败时跳转落地页
     * 可选
     */
    private String dplurl;
    /**
     * 创意ID
     */
    private String crid;
    /**
     * APP应用名称
     * 可选
     */
    private String apkname;
    /**
     * 视频广告时长，单位为秒，非视频类素材填0
     * 可选
     */
    private Integer duration;
    /**
     * 0代表普通广告，1代表app下载
     * 可选
     */
    @JSONField(name = "ad_type")
    private Integer adType;
    /**
     * 安装包名称：ad_type=1时必填，用以判断本地是否已安装该应用；【只android端】开发项目的包名com.storm.smart
     * 可选
     */
    @JSONField(name = "package")
    private String packageName;
    /**
     * App的唯一id号：ad_type=1时必填，用以应用内下载app【只ios端】
     * 可选
     */
    @JSONField(name = "app_store_id")
    private String appStoreId;
    /**
     * 图标url
     * 可选
     */
    @JSONField(name = "icon_url")
    private String iconUrl;
    /**
     * 按钮文字
     * 可选
     */
    @JSONField(name = "buttontext")
    private String buttonText;
    /**
     * 安装量数值。针对下载类广告
     * 可选
     */
    @JSONField(name = "downlodcount")
    private Integer downloadCount;
    /**
     * 星级推荐。针对下载类广告
     * 可选
     */
    @JSONField(name = "starcount")
    private float starCount;
    /**
     * 千次展现金额，最高竞标价格，单位为分
     * 可选
     */
    private String price;
    /**
     * 获胜通知，需要支持宏替换
     * 可选
     */
    private String nurl;
    /**
     * 视频的静态封面素材
     * 可选
     */
    private String covering;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
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

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public List<EventTrack> getPv() {
        return pv;
    }

    public void setPv(List<EventTrack> pv) {
        this.pv = pv;
    }

    public List<EventTrack> getClick() {
        return click;
    }

    public void setClick(List<EventTrack> click) {
        this.click = click;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDplurl() {
        return dplurl;
    }

    public void setDplurl(String dplurl) {
        this.dplurl = dplurl;
    }

    public String getCrid() {
        return crid;
    }

    public void setCrid(String crid) {
        this.crid = crid;
    }

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppStoreId() {
        return appStoreId;
    }

    public void setAppStoreId(String appStoreId) {
        this.appStoreId = appStoreId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public float getStarCount() {
        return starCount;
    }

    public void setStarCount(float starCount) {
        this.starCount = starCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNurl() {
        return nurl;
    }

    public void setNurl(String nurl) {
        this.nurl = nurl;
    }

    public String getCovering() {
        return covering;
    }

    public void setCovering(String covering) {
        this.covering = covering;
    }

    public BidResponse(){}

    @Generated("SparkTools")
    private BidResponse(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.desc = builder.desc;
        this.adUrl = builder.adUrl;
        this.width = builder.width;
        this.height = builder.height;
        this.materialType = builder.materialType;
        this.pv = builder.pv;
        this.click = builder.click;
        this.target = builder.target;
        this.dplurl = builder.dplurl;
        this.apkname = builder.apkname;
        this.duration = builder.duration;
        this.adType = builder.adType;
        this.packageName = builder.packageName;
        this.appStoreId = builder.appStoreId;
        this.iconUrl = builder.iconUrl;
        this.buttonText = builder.buttonText;
        this.downloadCount = builder.downloadCount;
        this.starCount = builder.starCount;
        this.price = builder.price;
        this.nurl = builder.nurl;
        this.covering = builder.covering;
    }

    @Generated("SparkTools")
    public static Builder builder(){return new Builder();}

    @Generated("SparkTools")
    public static final class Builder{
        private String id;
        private String title;
        private String desc;
        private String adUrl;
        private Integer width;
        private Integer height;
        private String materialType;
        private List<EventTrack> pv;
        private List<EventTrack> click;
        private String target;
        private String dplurl;
        private String apkname;
        private Integer duration;
        private Integer adType;
        private String packageName;
        private String appStoreId;
        private String iconUrl;
        private String buttonText;
        private Integer downloadCount;
        private float starCount;
        private String price;
        private String nurl;
        private String covering;

        public Builder withId(String id){
            this.id = id;
            return this;
        }
        public Builder withTitle(String title){
            this.title = title;
            return this;
        }
        public Builder withDesc(String desc){
            this.desc = desc;
            return this;
        }
        public Builder withAdUrl(String adUrl){
            this.adUrl = adUrl;
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
        public Builder withMaterialType(String materialType){
            this.materialType = materialType;
            return this;
        }
        public Builder withPV(List<EventTrack> pv){
            this.pv = pv;
            return this;
        }
        public Builder withClick(List<EventTrack> click){
            this.click = click;
            return this;
        }
        public Builder withTarget(String target){
            this.target = target;
            return this;
        }
        public Builder withDplurl(String dplurl){
            this.dplurl = dplurl;
            return this;
        }
        public Builder withApkname(String apkname){
            this.apkname = apkname;
            return this;
        }
        public Builder withDuration(Integer duration){
            this.duration = duration;
            return this;
        }
        public Builder withAdType(Integer adType){
            this.adType = adType;
            return this;
        }
        public Builder withPackageName(String packageName){
            this.packageName = packageName;
            return this;
        }
        public Builder withAppStoreId(String appStoreId){
            this.appStoreId = appStoreId;
            return this;
        }
        public Builder withIconUrl(String iconUrl){
            this.iconUrl = iconUrl;
            return this;
        }
        public Builder withButtonText(String buttonText){
            this.buttonText = buttonText;
            return this;
        }
        public Builder withId(Integer downloadCount){
            this.downloadCount = downloadCount;
            return this;
        }
        public Builder withStarCount(float starCount){
            this.starCount = starCount;
            return this;
        }
        public Builder withPrice(String price){
            this.price = price;
            return this;
        }
        public Builder withNurl(String nurl){
            this.nurl = nurl;
            return this;
        }
        public Builder withCovering(String covering){
            this.covering = covering;
            return this;
        }

        public Builder(){}

        public BidResponse build(){return new BidResponse(this);}
    }

    /**
     * 广告上报数据对象
     */
    public static class EventTrack{
        /**
         * 上报是否调用mma SDK，0步调用，1调用；mma包括秒针和admaster，需要支持宏替换
         * 必须
         */
        @JSONField(name = "type_mma")
        private Integer typeMma;
        /**
         * 上报地址
         * 必须
         */
        private String url;
        /**
         * time的值为该链接报数时间。可以有多个。
         * 可选
         */
        private Integer time;

        public Integer getTypeMma() {
            return typeMma;
        }

        public void setTypeMma(Integer typeMma) {
            this.typeMma = typeMma;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public EventTrack(){}

        @Generated("SparkTools")
        private EventTrack(Builder builder){
            this.typeMma = builder.typeMma;
            this.url = builder.url;
            this.time = builder.time;
        }

        @Generated("SparkTools")
        public static Builder builder(){return new Builder();}

        @Generated("SparkTools")
        public static final class Builder{
            private Integer typeMma;
            private String url;
            private Integer time;

            public Builder withTypeMma(Integer typeMma){
                this.typeMma = typeMma;
                return this;
            }
            public Builder withUrl(String url){
                this.url = url;
                return this;
            }
            public Builder withTime(Integer time){
                this.time = time;
                return this;
            }

            public Builder(){}

            public EventTrack build(){return new EventTrack(this);}
        }
    }

    /**
     * 0代表普通广告，1代表app下载
     * COMMON == 0
     * DOWNLOAD == 1
     */
    public enum AdType {
        COMMON(0), DOWNLOAD(1);
        private Integer value;

        AdType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}

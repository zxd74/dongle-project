package com.iwanvi.adserv.ngx.router.broker.threesix;

import com.alibaba.fastjson.annotation.JSONField;

import javax.annotation.Generated;
import java.lang.annotation.Native;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-04-19 17:34
 * @version: v1.0
 * @Description: 360 响应内容
 */
public class BidResponse {

    /**
     * // 与BidRequest中的bid保持一致，32字节的字符串
     * // 7e6e7fe084d7d1af1c51eb2f315712a8
     */
    private String bid;

    /**
     * Ads对象列表，360服务器返回的广告列表 选择BidRequest.adspaces中的一个或者多个广告位填充广告，若没有选出广告，则本字段不会填充
     */
    private List<Ad> ads;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    @Generated("SparkTools")
    public BidResponse(){}

    @Generated("SparkTools")
    public BidResponse(Builder builder){
        this.bid = builder.bid;
        this.ads = builder.ads;
    }

    @Generated("SparkTools")
    public static Builder builder() {
        return new Builder();
    }

    @Generated("SparkTools")
    public static final class Builder{
        private String bid;
        private List<Ad> ads;
        public Builder withBid(String bid){
            this.bid = bid;
            return this;
        }
        public Builder withAds(List<Ad> ads){
            this.ads = ads;
            return this;
        }
        public Builder(){}

        public BidResponse builder(){return new BidResponse(this);}
    }

    /**
     * Ad对象,广告内容
     */
    public static class Ad {
        /**
         * //  标记该广告对应的广告位id
         * // 5kLFGJ5S3J
         */
        @JSONField(name = "adspace_id")
        private String adspaceId;

        /**
         * // Creative对象列表，360服务器返回的创意列表。如果广告位请求多个创意，那么360服务器会填充多个创意
         * // 同一个请求中返回的多个广告创意不会重复
         */
        private List<Creative> creative;

        public String getAdspaceId() {
            return adspaceId;
        }

        public void setAdspaceId(String adspaceId) {
            this.adspaceId = adspaceId;
        }

        public List<Creative> getCreative() {
            return creative;
        }

        public void setCreative(List<Creative> creative) {
            this.creative = creative;
        }

        @Generated("SparkTools")
        public Ad(){}

        @Generated("SparkTools")
        public Ad(Builder builder){
            this.adspaceId = builder.adspaceId;
            this.creative = builder.creative;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String adspaceId;
            private List<Creative> creative;
            public Builder withAdspaceId(String adspaceId){
                this.adspaceId = adspaceId;
                return this;
            }
            public Builder withCreative(List<Creative> creative){
                this.creative = creative;
                return this;
            }
            public Builder(){}

            public Ad builder(){return new Ad(this);}
        }
    }

    /**
     * Creative对象，360服务器返回的创意列表
     */
    public static class Creative {
        /**
         * // 创意id 目前 外部DSP创意全部默认为0
         * // 1029837
         */
        @JSONField(name = "banner_id")
        private int bannerId;

        /**
         * // 广告位槽位id，如果广告位请求多条创意，360服务器会对返回的多条创意进行编号，从1开始
         * // 目前有bug，推荐直接使用object在list中的编号
         */
        @JSONField(name = "adspace_slot_seq")
        private int adspaceSlotSeq;

        /**
         * 必填：当为图片素材或原生素材时
         */
        @JSONField(name = "interaction_type")
        private String interactionType;

        /**
         * 落地页打开类型
         */
        @JSONField(name = "open_type")
        private String openType;

        /**
         * // InteractionObject对象，用于描述与用户交互动作，跟interaction_type相对应
         * // 必填： 当位图片素材或原生素材时
         */
        @JSONField(name = "interaction_object")
        private Interaction interaction;

        /**
         * // 广告的APP的包名
         * // 注意：浏览类广告无，下载类广告部分有
         * // com.qihoo.video
         */
        @JSONField(name = "package_name")
        private String packageName;

        /**
         * // 广告的APP的versionCode
         * // 浏览类广告无，下载类广告部分有
         * // 1123
         */
        @JSONField(name = "version_code")
        private int versionCode;

        /**
         * 广告素材类型
         */
        @JSONField(name = "adm_type")
        private String admType;

        /**
         * 广告素材对象
         */
        private Adm adm;

        /**
         * // 广告素材的有效日期（有效时间为1天），格式为年-月-日，参见示例
         * // 注意：当且仅当广告位类型为开屏联动，必填
         * // 2016-09-23
         */
        @JSONField(name = "valid_date")
        private String validDate;

        /**
         * // EventTrack对象列表，用于各种事件的追踪
         * // 必填
         */
        @JSONField(name = "event_track")
        private List<EventTrack> eventTrack;

        /**
         * // ComponentInfo对象，用于描述创意组件信息
         * // ComponentInfo的详细组件定义参考2.2.14 ComponentInfo。
         * // 含有组件的创意、组件的追踪url中的宏__COMPONENT_TYPE__需要替换后发送，宏替换规则参考含有组件的创意、组件监测说明
         */
        @JSONField(name = "component_info")
        private ComponentInfo componentInfo;

        public int getBannerId() {
            return bannerId;
        }

        public void setBannerId(int bannerId) {
            this.bannerId = bannerId;
        }

        public int getAdspaceSlotSeq() {
            return adspaceSlotSeq;
        }

        public void setAdspaceSlotSeq(int adspaceSlotSeq) {
            this.adspaceSlotSeq = adspaceSlotSeq;
        }

        public String getInteractionType() {
            return interactionType;
        }

        public void setInteractionType(String interactionType) {
            this.interactionType = interactionType;
        }

        public String getOpenType() {
            return openType;
        }

        public void setOpenType(String openType) {
            this.openType = openType;
        }

        public Interaction getInteraction() {
            return interaction;
        }

        public void setInteraction(Interaction interaction) {
            this.interaction = interaction;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }


        public String getAdmType() {
            return admType;
        }

        public void setAdmType(String admType) {
            this.admType = admType;
        }


        public Adm getAdm() {
            return adm;
        }

        public void setAdm(Adm adm) {
            this.adm = adm;
        }

        public String getValidDate() {
            return validDate;
        }

        public void setValidDate(String validDate) {
            this.validDate = validDate;
        }

        public List<EventTrack> getEventTrack() {
            return eventTrack;
        }

        public void setEventTrack(List<EventTrack> eventTrack) {
            this.eventTrack = eventTrack;
        }

        public ComponentInfo getComponentInfo() {
            return componentInfo;
        }

        public void setComponentInfo(ComponentInfo componentInfo) {
            this.componentInfo = componentInfo;
        }

        @Generated("SparkTools")
        public Creative(){}

        @Generated("SparkTools")
        public Creative(Builder builder){
            this.bannerId = builder.bannerId;
            this.adspaceSlotSeq = builder.adspaceSlotSeq;
            this.interactionType = builder.interactionType;
            this.openType = builder.openType;
            this.interaction = builder.interaction;
            this.packageName = builder.packageName;
            this.versionCode = builder.versionCode;
            this.admType = builder.admType;
            this.validDate = builder.validDate;
            this.eventTrack = builder.eventTrack;
            this.componentInfo = builder.componentInfo;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private int bannerId;
            private int adspaceSlotSeq;
            private String interactionType;
            private String openType;
            private Interaction interaction;
            private String packageName;
            private int versionCode;
            private String admType;
            private Adm adm;
            private String validDate;
            private List<EventTrack> eventTrack;
            private ComponentInfo componentInfo;

            public Builder withBannerId(int bannerId){
                this.bannerId = bannerId;
                return this;
            }
            public Builder withAdspaceSlotSeq(int adspaceSlotSeq){
                this.adspaceSlotSeq = adspaceSlotSeq;
                return this;
            }
            public Builder withInteractionType(String interactionType){
                this.interactionType = interactionType;
                return this;
            }
            public Builder withInteraction(Interaction interaction){
                this.interaction = interaction;
                return this;
            }
            public Builder withPackageName(String packageName){
                this.packageName = packageName;
                return this;
            }
            public Builder withVersionCode(int versionCode){
                this.versionCode = versionCode;
                return this;
            }
            public Builder withAdmType(String admType){
                this.admType = admType;
                return this;
            }
            public Builder withAdm(Adm adm){
                this.adm = adm;
                return this;
            }
            public Builder withValidDate(String validDate){
                this.validDate = validDate;
                return this;
            }
            public Builder withEventTrack(List<EventTrack> eventTrack){
                this.eventTrack = eventTrack;
                return this;
            }
            public Builder withComponentInfo(ComponentInfo componentInfo){
                this.componentInfo = componentInfo;
                return this;
            }

            public Builder(){}

            public Creative builder(){return new Creative(this);}
        }
    }

    /**
     * 广告素材对象
     */
    public static class Adm {
        /**
         * // 注意：图片素材或者HTML素材 必填
         * // 360服务器返回图片素材或者HTML素材，如果Creative. adm_type值为0或1，该字段会被填充，否则不会被填充
         */
        private String source;

        /**
         * // 注意：原生素材 必填
         * // Native对象，360服务器返回原生素材，如果Creative. adm_type值为3，该字段会被填充，否则不会被填充
         */
        @JSONField(name = "native")
        private Native nat;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public Native getNat() {
            return nat;
        }

        public void setNat(Native nat) {
            this.nat = nat;
        }

        @Generated("SparkTools")
        public Adm(){}

        @Generated("SparkTools")
        public Adm(Builder builder){
            this.source = builder.source;
            this.nat = builder.nat;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String source;
            private Native nat;

            public Builder withSource(String source){
                this.source = source;
                return this;
            }
            public Builder withNative(Native nat){
                this.nat = nat;
                return this;
            }
            public Builder(){}

            public Adm builder(){return new Adm(this);}
        }
    }

    /**
     * EventTrack对象，用于各种事件的追踪
     */
    public static class EventTrack {

        /**
         * // 事件类型
         * // 类型1，2必须发送，类型3，5，6，7，8，9可选择发送，对Android下载类广告类型4必须发送
         */
        @JSONField(name = "event_type")
        private String eventType;

        // 事件url，HTTP GET请求，须支持HTTP和HTTPS，禁止修改URL后发送（比如添加前置跳转，添加query字段，HTTPS改成HTTP发送等）。部分key可能不存在，或者对应value为空，发送前请做判断
        @JSONField(name = "notify_url")
        private List<String> notifyUrl;

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public List<String> getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(List<String> notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        @Generated("SparkTools")
        public EventTrack(){}

        @Generated("SparkTools")
        public EventTrack(Builder builder){
            this.eventType = builder.eventType;
            this.notifyUrl = builder.notifyUrl;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String eventType;
            private List<String> notifyUrl;

            public Builder withEventType(String eventType){
                this.eventType = eventType;
                return this;
            }
            public Builder withNotifyUrl(List<String> notifyUrl){
                this.notifyUrl = notifyUrl;
                return this;
            }
            public Builder(){}

            public EventTrack builder(){return new EventTrack(this);}
        }

        /* 注意事项
            事件类型会有追加，请对接方不要对应答中的类型数量进行强校验，而只需取出需要的监测进行发送
            SHOW、CLICK事件需在展示、点击发生时由客户端实时上报，其他事件在监测到用户后续相关行为后实时上报。
            所有监测的发送应为异步调用，不建议与对接方自身业务请求放入同一队列，因为这样做会导致发送延迟（非实时上报），最终可能导致监测被过滤
            对于所有曝光，点击及后续监测，可替换__EVENT_TIME_START__，__EVENT_TIME_END__宏，对于点击监测，可替换__OFFSET_X__，__OFFSET_Y__宏，宏替换规则参考落地页与监测宏替换说明。
        */
    }

    /**
     * ComponentInfo对象，用于描述创意组件信息，ComponentInfo的详细组件定义参考2.2.14 ComponentInfo。含有组件的创意、组件的追踪url中的宏__COMPONENT_TYPE__需要替换后发送，宏替换规则参考含有组件的创意、组件监测说明
     */
    public static class ComponentInfo {

        /**
         * // 	组件文案
         * //  电话咨询
         */
        @JSONField(name = "component_tex")
        private String componentTex;

        /**
         * // 枚举对象，用于描述组件类型，详细类型描述见interaction_type,目前只支持电话
         * // “DIALING”
         */
        @JSONField(name = "interaction_type")
        private String interactionType;

        /**
         * // InteractionObject对象，用于描述组件内容，详细描述见InteractionObject
         */
        @JSONField(name = "interaction_object")
        private Interaction interaction;

        public String getComponentTex() {
            return componentTex;
        }

        public void setComponentTex(String componentTex) {
            this.componentTex = componentTex;
        }

        public String getInteractionType() {
            return interactionType;
        }

        public void setInteractionType(String interactionType) {
            this.interactionType = interactionType;
        }

        public Interaction getInteraction() {
            return interaction;
        }

        public void setInteraction(Interaction interaction) {
            this.interaction = interaction;
        }

        @Generated("SparkTools")
        public ComponentInfo(){}

        @Generated("SparkTools")
        public ComponentInfo(Builder builder){
            this.componentTex = builder.componentTex;
            this.interactionType = builder.interactionType;
            this.interaction = builder.interaction;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String componentTex;
            private String interactionType;
            private Interaction interaction;

            public Builder withComponentTex(String componentTex){
                this.componentTex = componentTex;
                return this;
            }
            public Builder withInteractionType(String interactionType){
                this.interactionType = interactionType;
                return this;
            }
            public Builder withInteraction(Interaction interaction){
                this.interaction = interaction;
                return this;
            }
            public Builder(){}

            public ComponentInfo builder(){return new ComponentInfo(this);}
        }
    }

    /**
     * InteractionObject对象，用于描述与用户交互动作
     */
    public static class Interaction {
        /**
         * // 注意：浏览或者下载类广告 必填
         * // 落地页地址，目前均会填充，须支持HTTP和HTTPS,可选择替换__EVENT_TIME_START__,__EVENT_TIME_END__,__OFFSET_X__，__OFFSET_Y__宏，宏替换规则参考落地页与点击监测宏替换说明。对于视频广告，需要替换__VIDEO_DURATION__宏，表示用户点击时，视频已播放时长秒数
         * // http://www.360.cn
         */
        private String url;

        /**
         * // app deep link地址，网页类或下载类广告均有可能填充，处理优先级应高于url，大部分电商广告会填充
         * // openapp.jdmobile://product_page
         */
        @JSONField(name = "deep_link")
        private String deepLink;

        /**
         * // 注意：电话或者短信类广告,必填
         * // 目的电话号码
         * // 188xxxxxxxx
         */
        private String phone;

        /**
         * // 注意： 邮件类广告 必填
         * // 目的邮箱地址
         * // 	xxx@360.cn
         */
        private String mail;

        /**
         * // 注意：短信或者邮件类广告 必填
         * // 欢迎访问360移动APP广告平台！
         */
        private String msg;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDeepLink() {
            return deepLink;
        }

        public void setDeepLink(String deepLink) {
            this.deepLink = deepLink;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Generated("SparkTools")
        public Interaction(){}

        @Generated("SparkTools")
        public Interaction(Builder builder){
            this.url = builder.url;
            this.deepLink = builder.deepLink;
            this.phone = builder.phone;
            this.mail = builder.mail;
            this.msg = builder.msg;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String url;
            private String deepLink;
            private String phone;
            private String mail;
            private String msg;

            public Builder withUrl(String url){
                this.url = url;
                return this;
            }
            public Builder withDeepLink(String deepLink){
                this.deepLink = deepLink;
                return this;
            }
            public Builder withPhone(String phone){
                this.phone = phone;
                return this;
            }
            public Builder withMail(String mail){
                this.mail = mail;
                return this;
            }
            public Builder withMsg(String msg){
                this.msg = msg;
                return this;
            }
            public Builder(){}

            public Interaction builder(){return new Interaction(this);}
        }

        // 对于deep_link字段,如果deep_link字段不为空的话，应优先尝试使用deep_link地址打开广告主app，若deep link失败，再打开url字段的页面。这样有助于提高广告变现能力。
    }

    // 	Native对象
    public static class Native {
        /**
         * // Img对象，360服务器返回的图片的详细信息，广告展示必须包含其中的图片
         */
        private Img img;
        /**
         * // 注意：当且仅当广告位类型为开屏联动 必填
         * //  Linked_Img对象，类似Img对象
         */
        @JSONField(name = "linked_img")
        private LinkedImg linkedImg;
        /**
         * // 注意：当且仅当广告位设置了多图创意 必填
         * // Img对象列表
         */
        @JSONField(name = "multi_imgs")
        private List<Img> multiImgs;
        /**
         * // Title对象，广告标题
         */
        private Title title;
        /**
         * // 广告来源，媒体不可使用非该字段之外的来源进行渠道标记
         * // 今日头条
         */
        @JSONField(name = "sub_title")
        private String subTitle;
        /**
         * // logo的url，目前类型包括jpg,jpeg,png等，须支持HTTP和HTTPS
         */
        private String logo;
        /**
         * // 描述，一般为20个字以内，不排除更长的可能性，请注意客户端的展示
         */
        private String desc;
        /**
         * // 注意：当且仅当广告位类型为视频 必填
         * // 视频广告的素材和监测
         */
        private Video video;

        public Img getImg() {
            return img;
        }

        public void setImg(Img img) {
            this.img = img;
        }

        public LinkedImg getLinkedImg() {
            return linkedImg;
        }

        public void setLinkedImg(LinkedImg linkedImg) {
            this.linkedImg = linkedImg;
        }

        public List<Img> getMultiImgs() {
            return multiImgs;
        }

        public void setMultiImgs(List<Img> multiImgs) {
            this.multiImgs = multiImgs;
        }

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        @Generated("SparkTools")
        public Native(){}

        @Generated("SparkTools")
        public Native(Builder builder){
            this.img = builder.img;
            this.linkedImg = builder.linkedImg;
            this.multiImgs = builder.multiImgs;
            this.title = builder.title;
            this.subTitle = builder.subTitle;
            this.logo = builder.logo;
            this.desc = builder.desc;
            this.video = builder.video;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private Img img;
            private LinkedImg linkedImg;
            private List<Img> multiImgs;
            private Title title;
            private String subTitle;
            private String logo;
            private String desc;
            private Video video;

            public Builder withImg(Img img){
                this.img = img;
                return this;
            }
            public Builder withLinkedImg(LinkedImg linkedImg){
                this.linkedImg = linkedImg;
                return this;
            }
            public Builder withMultiImgs(List<Img> multiImgs){
                this.multiImgs = multiImgs;
                return this;
            }
            public Builder withTitle(Title title){
                this.title = title;
                return this;
            }
            public Builder withSubTitle(String subTitle){
                this.subTitle = subTitle;
                return this;
            }
            public Builder withLogo(String logo){
                this.logo = logo;
                return this;
            }
            public Builder withDesc(String desc){
                this.desc = desc;
                return this;
            }
            public Builder withVideo(Video video){
                this.video = video;
                return this;
            }
            public Builder(){}

            public Native builder(){return new Native(this);}
        }
    }

    public static class Img {
        /**
         * // 图片的url，必须展示，类型包括jpg，jpeg，png等，与广告主设置有关，须支持HTTP和HTTPS
         */
        private String url;
        /**
         * // 图片的宽，目前未设置，默认为0
         */
        private int width;
        /**
         * // 图片的高，目前未设置，默认为0
         */
        private int height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Generated("SparkTools")
        public Img(){}

        @Generated("SparkTools")
        public Img(Builder builder){
            this.url = builder.url;
            this.width = builder.width;
            this.height = builder.height;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String url;
            /**
             * // 图片的宽，目前未设置，默认为0
             */
            private int width;
            /**
             * // 图片的高，目前未设置，默认为0
             */
            private int height;

            public Builder withUrl(String url){
                this.url = url;
                return this;
            }
            public Builder withWidth(int width){
                this.width = width;
                return this;
            }
            public Builder withHeight(int height){
                this.height = height;
                return this;
            }

            public Builder(){}

            public Img builder(){return new Img(this);}
        }
    }

    public static class LinkedImg {
        /**
         * // 图片的url，必须展示，类型包括jpg，jpeg，png等，与广告主设置有关，须支持HTTP和HTTPS
         */
        private String url;
        /**
         * // 图片的宽，目前未设置，默认为0
         */
        private int width;
        /**
         * // 图片的高，目前未设置，默认为0
         */
        private int height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Generated("SparkTools")
        public LinkedImg(){}

        @Generated("SparkTools")
        public LinkedImg(Builder builder){
            this.url = builder.url;
            this.width = builder.width;
            this.height = builder.height;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String url;
            /**
             * // 图片的宽，目前未设置，默认为0
             */
            private int width;
            /**
             * // 图片的高，目前未设置，默认为0
             */
            private int height;

            public Builder withUrl(String url){
                this.url = url;
                return this;
            }
            public Builder withWidth(int width){
                this.width = width;
                return this;
            }
            public Builder withHeight(int height){
                this.height = height;
                return this;
            }

            public Builder(){}

            public LinkedImg builder(){return new LinkedImg(this);}
        }
    }

    public static class Title {
        /**
         * // 标题内容，一般为10个字以内，不排除更长的可能性，请注意客户端的展示
         */
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Generated("SparkTools")
        public Title(){}

        @Generated("SparkTools")
        public Title(Builder builder){
            this.text = builder.text;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String text;

            public Builder withText(String text){
                this.text = text;
                return this;
            }

            public Builder(){}

            public Title builder(){return new Title(this);}
        }
    }

    public static class Video {
        /**
         * // 视频地址URL
         */
        private String url;
        /**
         * // 视频时长秒数
         */
        private int duration;
        /**
         * 视频文件的MD5值
         */
        @JSONField(name = "video_md5")
        private String videoMd5;
        /**
         * // 视频文件的大小，单位是字节
         */
        @JSONField(name = "video_size")
        private int videoSize;
        /**
         * // 视频开始播放监测URL列表
         */
        @JSONField(name = "start_tracks")
        private List<String> startTracks;
        /**
         * // 视频暂停播放监测URL列表
         */
        @JSONField(name = "pause_tracks")
        private List<String> pauseTracks;
        /**
         * // 视频继续播放监测URL列表
         */
        @JSONField(name = "conti_tracks")
        private List<String> contiTracks;
        /**
         * // 视频关闭播放监测URL列表
         */
        @JSONField(name = "exit_tracks")
        private List<String> exitTracks;
        /**
         * // 视频完成播放监测URL列表
         */
        @JSONField(name = "comp_tracks")
        private List<String> compTracks;
        /**
         * // 视频已播放时长秒数监测，注意可能会有多个
         */
        @JSONField(name = "TShowTrack")
        private List<TShowTrack> TShowTrack;

        // 注意：对以上监测（tracks），需要替换__VIDEO_DURATION__宏，表示事件触发时，视频已播放时长秒数

        // 监测URL请使用HTTP GET请求，须支持HTTP和HTTPS，禁止修改URL后发送（比如添加前置跳转，添加query字段，HTTPS改成HTTP发送等）。列表可能为空。

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getVideoMd5() {
            return videoMd5;
        }

        public void setVideoMd5(String videoMd5) {
            this.videoMd5 = videoMd5;
        }

        public int getVideoSize() {
            return videoSize;
        }

        public void setVideoSize(int videoSize) {
            this.videoSize = videoSize;
        }

        public List<String> getStartTracks() {
            return startTracks;
        }

        public void setStartTracks(List<String> startTracks) {
            this.startTracks = startTracks;
        }

        public List<String> getPauseTracks() {
            return pauseTracks;
        }

        public void setPauseTracks(List<String> pauseTracks) {
            this.pauseTracks = pauseTracks;
        }

        public List<String> getContiTracks() {
            return contiTracks;
        }

        public void setContiTracks(List<String> contiTracks) {
            this.contiTracks = contiTracks;
        }

        public List<String> getExitTracks() {
            return exitTracks;
        }

        public void setExitTracks(List<String> exitTracks) {
            this.exitTracks = exitTracks;
        }

        public List<String> getCompTracks() {
            return compTracks;
        }

        public void setCompTracks(List<String> compTracks) {
            this.compTracks = compTracks;
        }

        public List<TShowTrack> getTShowTrack() {
            return TShowTrack;
        }

        public void setTShowTrack(List<TShowTrack> TShowTrack) {
            this.TShowTrack = TShowTrack;
        }

        @Generated("SparkTools")
        public Video(){}

        @Generated("SparkTools")
        public Video(Builder builder){
            this.url = builder.url;
            this.duration = builder.duration;
            this.videoMd5 = builder.videoMd5;
            this.videoSize = builder.videoSize;
            this.startTracks = builder.startTracks;
            this.pauseTracks = builder.pauseTracks;
            this.contiTracks = builder.contiTracks;
            this.exitTracks = builder.exitTracks;
            this.compTracks = builder.compTracks;
            this.TShowTrack = builder.TShowTrack;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private String url;
            private int duration;
            private String videoMd5;
            private int videoSize;
            private List<String> startTracks;
            private List<String> pauseTracks;
            private List<String> contiTracks;
            private List<String> exitTracks;
            private List<String> compTracks;
            private List<TShowTrack> TShowTrack;

            public Builder withUrl(String url){
                this.url = url;
                return this;
            }
            public Builder withDuration(int duration){
                this.duration = duration;
                return this;
            }
            public Builder withVideoMd5(String videoMd5){
                this.videoMd5 = videoMd5;
                return this;
            }
            public Builder withVideoSize(int videoSize){
                this.videoSize = videoSize;
                return this;
            }
            public Builder withStartTracks(List<String> startTracks){
                this.startTracks = startTracks;
                return this;
            }
            public Builder withPauseTracks(List<String> pauseTracks){
                this.pauseTracks = pauseTracks;
                return this;
            }
            public Builder withContiTracks(List<String> contiTracks){
                this.contiTracks = contiTracks;
                return this;
            }
            public Builder withExitTracks(List<String> exitTracks){
                this.exitTracks = exitTracks;
                return this;
            }
            public Builder withCompTracks(List<String> compTracks){
                this.compTracks = compTracks;
                return this;
            }
            public Builder withTShowTrack(List<TShowTrack> TShowTrack){
                this.TShowTrack = TShowTrack;
                return this;
            }

            public Builder(){}

            public Video builder(){return new Video(this);}
        }


    }

    public static class TShowTrack {
        /**
         * //	视频播放t秒后，发送对应监测
         */
        private int t;
        /**
         * // 	视频播放t秒后，对应的监测URL列表
         */
        private List<String> url;

        public int getT() {
            return t;
        }
        public void setT(int t) {
            this.t = t;
        }
        public List<String> getUrl() {
            return url;
        }
        public void setUrl(List<String> url) {
            this.url = url;
        }

        @Generated("SparkTools")
        public TShowTrack(){}

        @Generated("SparkTools")
        public TShowTrack(Builder builder){
            this.t = builder.t;
            this.url = builder.url;
        }

        @Generated("SparkTools")
        public static Builder builder() {
            return new Builder();
        }

        @Generated("SparkTools")
        public static final class Builder{
            private int t;
            /**
             * // 	视频播放t秒后，对应的监测URL列表
             */
            private List<String> url;

            public Builder withT(int t){
                this.t = t;
                return this;
            }
            public Builder withUrl(List<String> url){
                this.url = url;
                return this;
            }

            public Builder(){}

            public TShowTrack builder(){return new TShowTrack(this);}
        }
    }

    /**
     * // 落地页打开类型
     * // 仅针对浏览的交互类型
     * // 目前仅返回单个值，内开或者外开
     * ALL = 0; //内开、外开由媒体决定
     * INNER = 1; //内开，由媒体WebView打开
     * OUTER = 2; //外开，由浏览器打开
     */
    public enum OpenType {
        ALL("ALL"), INNER("INNER"), OUTER("OUTER");
        private final String value;

        OpenType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * // 广告素材类型
     * // 目前仅支持原生素材
     * PIC = 0; //图片素材
     * HTML = 1; //HTML素材
     * NATIVE = 3; //原生素材
     */
    public enum AdmType {
        PIC("PIC"), HTML("HTML"), NATIVE("NATIVE");
        private final String value;

        AdmType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    /**
     * ANY = 0; //任何一种
     * NO_INTERACTION = 1; //不交互
     * BROWSE= 2; //浏览
     * DOWNLOAD = 3; //下载
     * DIALING = 4; //电话
     * MESSAGE = 5; //短信
     * MAIL = 6; //邮件
     */
    public enum InteractionType {
        ANY("ANY"), NO_INTERACTION("NO_INTERACTION"), BROWSE("BROWSE"), DOWNLOAD("DOWNLOAD"), DIALING("DIALING"), MESSAGE("MESSAGE"), MAIL("MAIL");
        private final String value;

        InteractionType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * // 事件类型
     * SHOW = 1; //素材展示监测，仅首次可视时发送
     * CLICK = 2; //素材点击监测，对于视频广告，还需要替换__VIDEO_DURATION__宏，表示用户点击时，视频已播放时长秒数
     * OPEN = 3; //Android下载后续监测，点击广告后，判断广告主App已安装，直接打开广告主App，发送打开的监测
     * BEGIN_DOWNLOAD = 4; //Android下载后续监测，下载开始时，发送开始下载的监测，Android下载类广告必须发送
     * DOWNLOAD=5; //Android下载后续监测，下载完成后，调起安装界面，并发送下载完成的监测
     * INSTALL = 6; //Android下载后续监测，安装完成后，界面显示“应用安装完成”，此时发送安装完成的监测
     * ACTIVE = 7; //Android下载后续监测，在安装完成的界面上点击“打开”按钮，用户打开广告主App，发送激活的监测
     * CLOSE = 8; //当用户点击关闭广告后，发送广告关闭的监测，双方约定后才会返回，详情见表格下方说明
     * SHOW_SLIDE = 9; // 对于开屏联动广告，关联图片曝光则触发关联曝光的监测，双方约定后才会返回
     */
    public enum EventType {
        SHOW("SHOW"), CLICK("CLICK"), OPEN("OPEN"), BEGIN_DOWNLOAD("BEGIN_DOWNLOAD"), DOWNLOAD("DOWNLOAD"), INSTALL("INSTALL"), ACTIVE("ACTIVE"), CLOSE("CLOSE"), SHOW_SLIDE("SHOW_SLIDE");
        private final String value;

        EventType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

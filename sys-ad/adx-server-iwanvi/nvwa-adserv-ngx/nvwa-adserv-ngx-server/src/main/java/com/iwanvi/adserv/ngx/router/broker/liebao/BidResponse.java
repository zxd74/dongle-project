package com.iwanvi.adserv.ngx.router.broker.liebao;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: 郑晓东
 * @date: 2019-06-06 15:15
 * @version: v1.0
 * @Description: 猎豹响应类
 */
public class BidResponse {
    /**
     * 对应的bidrequest id
     */
    private String id;
    /**
     * Setabid对象，支持一个
     * 必填
     */
    @JSONField(name = "seatbid")
    private SeatBid[] seatBid;
    /**
     * Bidder 响应 ID，可用于日志跟踪
     */
    private String bidid;
    /**
     * 货币标示，ISO-4217标准，支持一种
     * 国内：CNY
     * 海外：USD
     * 必填
     */
    private String cur;
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

    public SeatBid[] getSeatBid() {
        return seatBid;
    }

    public void setSeatBid(SeatBid[] seatBid) {
        this.seatBid = seatBid;
    }

    public String getBidid() {
        return bidid;
    }

    public void setBidid(String bidid) {
        this.bidid = bidid;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public Ext getExt() {
        return ext;
    }

    public void setExt(Ext ext) {
        this.ext = ext;
    }

    /**
     * seatbid对象
     */
    public static class SeatBid {
        /**
         * 与impression对应
         */
        private Bid[] bid;
        /**
         * Bidder seat name
         */
        private String seat;
        /**
         * 0—独赢 1-组赢，暂只支持0
         */
        private Integer group;
        /**
         * 扩展字段
         */
        private Ext ext;

        public Bid[] getBid() {
            return bid;
        }

        public void setBid(Bid[] bid) {
            this.bid = bid;
        }

        public String getSeat() {
            return seat;
        }

        public void setSeat(String seat) {
            this.seat = seat;
        }

        public Integer getGroup() {
            return group;
        }

        public void setGroup(Integer group) {
            this.group = group;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }
    }

    /**
     * bid对象
     */
    public static class Bid {
        /**
         * Bidder定义的ID，用于日志跟踪
         */
        private String id;
        /**
         * 对应imp对象中的id
         */
        @JSONField(name = "impid")
        private String impId;
        /**
         * 竞价价格，单位：元/千次展现
         * 必填
         */
        private Float price;
        /**
         * 广告ID
         */
        private String adid;
        /**
         * 包名
         */
        private String bundle;
        /**
         * 广告域名，用于黑名单检查
         */
        @JSONField(name = "addomain")
        private String[] adDomain;
        /**
         * 广告描述
         * 必填
         */
        private String adm;
        /**
         * 创意分类
         */
        private String[] cat;
        /**
         * 创意属性
         */
        private Integer[] attr;
        /**
         * 私有交易ID
         */
        @JSONField(name = "dealid")
        private String dealId;
        /**
         * 创意宽
         */
        private Integer w;
        /**
         * 创意高
         */
        private Integer h;
        /**
         * Win url
         */
        private String nurl;
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

        public String getImpId() {
            return impId;
        }

        public void setImpId(String impId) {
            this.impId = impId;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public String getBundle() {
            return bundle;
        }

        public void setBundle(String bundle) {
            this.bundle = bundle;
        }

        public String[] getAdDomain() {
            return adDomain;
        }

        public void setAdDomain(String[] adDomain) {
            this.adDomain = adDomain;
        }

        public String getAdm() {
            return adm;
        }

        public void setAdm(String adm) {
            this.adm = adm;
        }

        public String[] getCat() {
            return cat;
        }

        public void setCat(String[] cat) {
            this.cat = cat;
        }

        public Integer[] getAttr() {
            return attr;
        }

        public void setAttr(Integer[] attr) {
            this.attr = attr;
        }

        public String getDealId() {
            return dealId;
        }

        public void setDealId(String dealId) {
            this.dealId = dealId;
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

        public String getNurl() {
            return nurl;
        }

        public void setNurl(String nurl) {
            this.nurl = nurl;
        }

        public Ext getExt() {
            return ext;
        }

        public void setExt(Ext ext) {
            this.ext = ext;
        }
    }

    public static class Ext {
    }

    /**
     * Native描述（用于adm）
     */
    public static class Adm {
        @JSONField(name = "native")
        private Native nat;

        private Banner banner;

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
    }

    public static class Native {
        /**
         * 展现上报链接
         * 必填
         */
        @JSONField(name = "imptrackers")
        private String[] impTrackers;
        /**
         * 点击跳转
         * 必填
         */
        private Link link;
        /**
         * 广告素材
         * 必填
         */
        private String assets;

        public String[] getImpTrackers() {
            return impTrackers;
        }

        public void setImpTrackers(String[] impTrackers) {
            this.impTrackers = impTrackers;
        }

        public Link getLink() {
            return link;
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public String getAssets() {
            return assets;
        }

        public void setAssets(String assets) {
            this.assets = assets;
        }
    }

    public static class Link {
        /**
         * 点击上报链接
         */
        @JSONField(name = "clicktrackers")
        private String[] clickTrackers;
        /**
         * 下载地址/落地页URL
         */
        private String url;
        /**
         * 此字段仅在deeplink投放时使用，填写h5落地页url，用于用户未安装app无法进行deeplink拉起时，进行H5跳转
         */
        @JSONField(name = "landurl")
        private String landUrl;
        /**
         * 此字段仅在deeplink广告投放时使用，如果存在该字段，且不为空，则会认为该广告是deeplink广告。
         */
        private String fallback;

        public String[] getClickTrackers() {
            return clickTrackers;
        }

        public void setClickTrackers(String[] clickTrackers) {
            this.clickTrackers = clickTrackers;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLandUrl() {
            return landUrl;
        }

        public void setLandUrl(String landUrl) {
            this.landUrl = landUrl;
        }

        public String getFallback() {
            return fallback;
        }

        public void setFallback(String fallback) {
            this.fallback = fallback;
        }
    }

    /**
     * 广告素材
     */
    public static class Asset {
        /**
         * 对应请求时的assets id
         * 必填
         */
        private Integer id;
        /**
         * 标题（JSON）
         */
        private Title title;
        /**
         * 描述（JSON）
         */
        private Data data;
        /**
         * 跳转地址（JSON）
         */
        private Link link;
        /**
         * 图片地址（JSON）
         */
        private Img img;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public Link getLink() {
            return link;
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public Img getImg() {
            return img;
        }

        public void setImg(Img img) {
            this.img = img;
        }
    }

    public static class Img {
        private String url;
        private Integer w;
        private Integer h;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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
    }

    public static class Title {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class Data {
        /**
         * 数据类型，目前只有描述”desc” 见LabelType
         */
        private String label;
        /**
         * 描述
         */
        private String value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Banner {
        @JSONField(name = "imptrackers")
        private String[] impTrackers;
        private Link link;
        private Img img;

        public String[] getImpTrackers() {
            return impTrackers;
        }

        public void setImpTrackers(String[] impTrackers) {
            this.impTrackers = impTrackers;
        }

        public Link getLink() {
            return link;
        }

        public void setLink(Link link) {
            this.link = link;
        }

        public Img getImg() {
            return img;
        }

        public void setImg(Img img) {
            this.img = img;
        }
    }

    /**
     * Label数据格式名称
     */
    public enum LabelType {
        SPONSORED("text"), DESC("text"), RATING("text"), LIKES("text"), DOWNLOADS("text");
        private String value;

        LabelType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }



    public static class VAST{

    }
}

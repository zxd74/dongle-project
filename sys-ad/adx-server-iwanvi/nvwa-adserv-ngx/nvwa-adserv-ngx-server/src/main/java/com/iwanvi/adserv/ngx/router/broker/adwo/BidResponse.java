package com.iwanvi.adserv.ngx.router.broker.adwo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: 郑晓东
 * @date: 2019-06-04 18:32
 * @version: v1.0
 * @Description: 安沃 BidResponse 响应类
 */
public class BidResponse {

    /**
     * 请求广告状态
     * 1 代表请求成功。其它均失败
     * 必填
     */
    private Integer resultcode;

    /**
     * 错误信息
     * 只有 resultcode 不为 1 时才有
     * 选填
     */
    private String errorinfo;
    /**
     * 广告信息对象 ad object
     * 广告对象
     * 必填
     */
    private Ad ad;

    public Integer getResultcode() {
        return resultcode;
    }

    public void setResultcode(Integer resultcode) {
        this.resultcode = resultcode;
    }

    public String getErrorinfo() {
        return errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public static class Ad{
        /**
         * 广告 ID
         */
        private Integer adid;
        /**
         * 广告点击类型
         *      1 代表打开网页类
         *      3 代表下载类
         *      15 代表视频播放
         */
        @JSONField(name = "actiontype")
        private Integer actionType;
        /**
         * 视频物料对象
         *      Format=4 时必有
         */
        private Video video;
        /**
         * 信息流物料对象
         *      Format=3 时必有
         */
        private Feed feeds;
        /**
         * 广告推广地址
         */
        private String target;
        /**
         * 下载广告包名
         */
        @JSONField(name = "pk_name")
        private String pkName;
        /**
         * 下载广告版本
         */
        @JSONField(name = "pk_ver")
        private String pkVer;
        /**
         * 展示监测地址
         */
        @JSONField(name = "showbeacons")
        private String[] showBeacons;
        /**
         * 点击监测地址
         */
        private String[] beacons;
        /**
         * deeplink 链接
         */
        private String deeplink;
        /**
         * 响应价格
         * 必填
         */
        private Double pushmoney;

        public Integer getAdid() {
            return adid;
        }

        public void setAdid(Integer adid) {
            this.adid = adid;
        }

        public Integer getActionType() {
            return actionType;
        }

        public void setActionType(Integer actionType) {
            this.actionType = actionType;
        }

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        public Feed getFeeds() {
            return feeds;
        }

        public void setFeeds(Feed feeds) {
            this.feeds = feeds;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getPkName() {
            return pkName;
        }

        public void setPkName(String pkName) {
            this.pkName = pkName;
        }

        public String getPkVer() {
            return pkVer;
        }

        public void setPkVer(String pkVer) {
            this.pkVer = pkVer;
        }

        public String[] getShowBeacons() {
            return showBeacons;
        }

        public void setShowBeacons(String[] showBeacons) {
            this.showBeacons = showBeacons;
        }

        public String[] getBeacons() {
            return beacons;
        }

        public void setBeacons(String[] beacons) {
            this.beacons = beacons;
        }

        public String getDeeplink() {
            return deeplink;
        }

        public void setDeeplink(String deeplink) {
            this.deeplink = deeplink;
        }

        public Double getPushmoney() {
            return pushmoney;
        }

        public void setPushmoney(Double pushmoney) {
            this.pushmoney = pushmoney;
        }
    }

    /**
     *   视频物料对象
     */
    public static class Video{
        /**
         * 视频播放宽
         *      根据请求参数宽高决定
         */
        private Integer w;
        /**
         * 视频播放高
         *      根据请求参数宽高决定
         */
        private Integer h;
        /**
         * 视频播放地址
         * 必填
         */
        private String url;
        /**
         * 视频播放时长 单位：秒
         */
        private Integer t;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getT() {
            return t;
        }

        public void setT(Integer t) {
            this.t = t;
        }
    }

    /**
     * 信息流物料对象
     *      大图、icon、标题至少有 1 项，根据请求里的参数来决定
     */
    public static class Feed{
        /**
         * 大图宽
         */
        private Integer imgw;
        /**
         * 大图高
         */
        private Integer imgh;
        /**
         * 大图地址
         */
        private String img;
        /**
         * Icon 宽
         */
        private Integer icow;
        /**
         * Icon 高
         */
        private Integer icoh;
        /**
         * Icon 图片地址
         */
        private String ico;
        /**
         * 标题
         */
        private String txt;
        /**
         * 描述
         */
        private String desc;
        /**
         * 简介
         */
        private String sum;

        public Integer getImgw() {
            return imgw;
        }

        public void setImgw(Integer imgw) {
            this.imgw = imgw;
        }

        public Integer getImgh() {
            return imgh;
        }

        public void setImgh(Integer imgh) {
            this.imgh = imgh;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Integer getIcow() {
            return icow;
        }

        public void setIcow(Integer icow) {
            this.icow = icow;
        }

        public Integer getIcoh() {
            return icoh;
        }

        public void setIcoh(Integer icoh) {
            this.icoh = icoh;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }
    }
    /**
     * 广告点击类型
     *      OPEN == 1 代表打开网页类
     *      DOWNLOAD == 3 代表下载类
     *      DEEPLINK == 15 代表视频播放
     */
    public enum ActionType{
        OPEN(1),DOWNLOAD(3),DEEPLINK(15);
        private Integer value;
        ActionType(Integer value){this.value = value;}
        public Integer getValue(){return this.value;}
    }
}

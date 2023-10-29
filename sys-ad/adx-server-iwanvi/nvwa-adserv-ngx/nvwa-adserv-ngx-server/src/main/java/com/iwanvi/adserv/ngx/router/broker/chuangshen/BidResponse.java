package com.iwanvi.adserv.ngx.router.broker.chuangshen;

import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-06-13 09:22
 * @version: v1.0
 * @Description: 创神响应类
 */
public class BidResponse {

    /**
     * true表示成功获取到广告，
     * fail(false)表示发生错误或者没有可以展示的广告
     */
    private Boolean succ;
    /**
     * 消息提示，获取到广告为固定值”ok”，其余情况是错误信息
     */
    private String msg;
    /**
     * 获取到的广告json数据,succ=true才会有返回, 结构定义见下面的说明
     */
    private String ads;

    public Boolean getSucc() {
        return succ;
    }

    public void setSucc(Boolean succ) {
        this.succ = succ;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }

    public static final class Ad{
        /**
         * 广告创意id,可以作为广告的唯一标识
         */
        private Integer creativeId;
        /**
         * 广告id
         */
        private Integer adsId;
        /**
         * 广告素材地址
         */
        private String image;
        /**
         * 素材宽度
         */
        private Integer width;
        /**
         * 素材高度
         */
        private Integer height;
        /**
         * 广告去向地址, 这里需要开发者识别下载地址或者是落地页地址，落地页地址存在”#page”后缀，开发者实施跳转时需要去除此标记
         * 即落地页地址
         */
        private String gotourl;
        /**
         * 广告标题
         */
        private String words;
        /**
         * icon 图片地址
         */
        private String icon;
        /**
         * 横屏展示的素材地址
         */
        private String image2;
        /**
         * 大段的文字描述
         */
        private String words2;
        // TODO 如何接收待考虑
        /**
         * 这里是一个二维数组，key表示延迟时间，单位：秒 需要根据延迟时间执行上报哦, 可能存在替换宏 _TIMESTAMP_ 需要开发者替换为以秒为单位的时间戳传递过来
         * 即曝光监测地址
         */
        private List<List<String>> showReport;
        /**
         * 点击上报地址，这里是个数组，立即上报; 可能存在替换宏 _TIMESTAMP_ 需要开发者替换为以秒为单位的时间戳传递过来
         */
        private String[] clickReport;

        public Integer getCreativeId() {
            return creativeId;
        }

        public void setCreativeId(Integer creativeId) {
            this.creativeId = creativeId;
        }

        public Integer getAdsId() {
            return adsId;
        }

        public void setAdsId(Integer adsId) {
            this.adsId = adsId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getGotourl() {
            return gotourl;
        }

        public void setGotourl(String gotourl) {
            this.gotourl = gotourl;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getWords2() {
            return words2;
        }

        public void setWords2(String words2) {
            this.words2 = words2;
        }

        public List<List<String>> getShowReport() {
            return showReport;
        }

        public void setShowReport(List<List<String>> showReport) {
            this.showReport = showReport;
        }

        public String[] getClickReport() {
            return clickReport;
        }

        public void setClickReport(String[] clickReport) {
            this.clickReport = clickReport;
        }
    }

}

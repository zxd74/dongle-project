package com.iwanvi.adserv.ngx.router.broker.taido;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-01 17:13
 */
public class BidResponse {
    /**
     * 返回值：0正常，-1异常 ：必填
     */
    private Integer res;
    /**
     * 异常的补充说明
     */
    private String remark;
    /**
     * 返回的广告
     */
    private AdDetail[] addetail;

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AdDetail[] getAddetail() {
        return addetail;
    }

    public void setAddetail(AdDetail[] addetail) {
        this.addetail = addetail;
    }

    public static class AdDetail{
        /**
         * 广告类型
         */
        private Integer adtype;
        /**
         * 素材
         */
        private String[] images;
        /**
         * 广告标题
         */
        private String title;
        /**
         * 广告文字素材
         */
        private String remark;
        /**
         * 应用图标地址
         */
        private String icon;
        /**
         * 返回的html代码的base64编码
         */
        private String html;
        /**
         * 广告曝光监控地址，返回的地址依次调用, 如果链接里面有宏则需要予以替换
         */
        private String[] pvurls;
        /**
         * 即为deeplink，在返回的情况下点击先调起deeplink，调起失败则打开广告点击常规地址
         */
        private String dlink;
        /**
         * 广告点击的常规地址  有可能包含302 跳转； 如果链接里面有宏则需要予以替换
         */
        private String clickurl;
        /**
         * 广告点击监控地址，返回的地址依次调用, 如果链接里面有宏则需要予以替换
         */
        private String[] ckurls;
        /**
         * 如果是下载的话,为下载的包名
         */
        private String pack_name;
        /**
         * 下载包的大小
         */
        private String pack_size;
        /**
         * 广告开始下载监控地址,返回的地址依次调用, 如果链接里面有宏则需要予以替换
         */
        private String[] startdown;
        /**
         * 广告下载完成监控地址，返回的地址依次调用, 如果链接里面有宏则需要予以替换
         */
        private String[] overdown;
        /**
         * 广告开始安装监控地址，返回的地址依次调用 , 如果链接里面有宏则需要予以替换
         */
        private String[] startinstall;
        /**
         * 广告安装完成监控地址，返回的地址依次调用, 如果链接里面有宏则需要予以替换
         */
        private String[] overinstall;
        /**
         * 小程序链接信息(见下表),在adtype=3时候返回,目前只支持微信小程序
         */
        private String mini_app;
        /**
         * 广告交互类型
         */
        private Integer type;
        /**
         * 音频视频类的广告素材
         */
        private String video;
        /**
         * 视频播放开始监控地址，音频视频来广告才会返回，返回的地址依次调用(数组) , 如果链接里面有宏则需要予以替换
         */
        private String[] videostart;
        /**
         * 视频暂停监测地址(数组) , 如果链接里面有宏则需要予以替换
         */
        private String[] videopaush;
        /**
         * 视频播放结束监控地址，，音频视频来广告才会返回，返回的地址依次调用(数组) , 如果链接里面有宏则需要予以替换
         */
        private String[] videoend;

        public Integer getAdtype() {
            return adtype;
        }

        public void setAdtype(Integer adtype) {
            this.adtype = adtype;
        }

        public String[] getImages() {
            return images;
        }

        public void setImages(String[] images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String[] getPvurls() {
            return pvurls;
        }

        public void setPvurls(String[] pvurls) {
            this.pvurls = pvurls;
        }

        public String getDlink() {
            return dlink;
        }

        public void setDlink(String dlink) {
            this.dlink = dlink;
        }

        public String getClickurl() {
            return clickurl;
        }

        public void setClickurl(String clickurl) {
            this.clickurl = clickurl;
        }

        public String[] getCkurls() {
            return ckurls;
        }

        public void setCkurls(String[] ckurls) {
            this.ckurls = ckurls;
        }

        public String getPack_name() {
            return pack_name;
        }

        public void setPack_name(String pack_name) {
            this.pack_name = pack_name;
        }

        public String getPack_size() {
            return pack_size;
        }

        public void setPack_size(String pack_size) {
            this.pack_size = pack_size;
        }

        public String[] getStartdown() {
            return startdown;
        }

        public void setStartdown(String[] startdown) {
            this.startdown = startdown;
        }

        public String[] getOverdown() {
            return overdown;
        }

        public void setOverdown(String[] overdown) {
            this.overdown = overdown;
        }

        public String[] getStartinstall() {
            return startinstall;
        }

        public void setStartinstall(String[] startinstall) {
            this.startinstall = startinstall;
        }

        public String[] getOverinstall() {
            return overinstall;
        }

        public void setOverinstall(String[] overinstall) {
            this.overinstall = overinstall;
        }

        public String getMini_app() {
            return mini_app;
        }

        public void setMini_app(String mini_app) {
            this.mini_app = mini_app;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String[] getVideostart() {
            return videostart;
        }

        public void setVideostart(String[] videostart) {
            this.videostart = videostart;
        }

        public String[] getVideopaush() {
            return videopaush;
        }

        public void setVideopaush(String[] videopaush) {
            this.videopaush = videopaush;
        }

        public String[] getVideoend() {
            return videoend;
        }

        public void setVideoend(String[] videoend) {
            this.videoend = videoend;
        }
    }

}

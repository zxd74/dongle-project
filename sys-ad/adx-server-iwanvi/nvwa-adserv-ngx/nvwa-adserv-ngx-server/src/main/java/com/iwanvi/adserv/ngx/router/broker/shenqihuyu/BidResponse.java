package com.iwanvi.adserv.ngx.router.broker.shenqihuyu;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: 郑晓东
 * @date: 2019-06-13 12:30
 * @version: v1.0
 * @Description: 神奇互娱响应类
 */
public class BidResponse {

    /**
     * 0 正常
     */
    private Integer code;
    /**
     *  信息
     */
    private String msg;
    /**
     * 广告
     */
    private Ad ad;
    /**
     * 视频广告
     */
    private Video video;
    /**
     * 下载类广告
     */
    private AdExt adext;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public AdExt getAdext() {
        return adext;
    }

    public void setAdext(AdExt adext) {
        this.adext = adext;
    }

    public static class Ad{
        /**
         * 点击后跳转地址
         */
        private String lpg;
        /**
         * deeplink地址
         */
        @JSONField(name = "deep_link")
        private String deepLink;
        /**
         * 广告类型：下载|非下载
         */
        private String adtype;
        /**
         * 非视频广告必传，宽
         */
        private Integer adwidth;
        /**
         * 非视频广告必传，高
         */
        private Integer adheight;
        /**
         * 非视频广告必传 adm- html段
         */
        private String html;
        /**
         * 非视频广告必传，图片url
         */
        @JSONField(name = "img_url")
        private String imgUrl;
        /**
         * 非视频广告必传，追踪展示链接数组
         */
        private String[] imp;
        /**
         * 非视频广告必传，追踪点击链接数组
         */
        private String[] click;

        public String getLpg() {
            return lpg;
        }

        public void setLpg(String lpg) {
            this.lpg = lpg;
        }

        public String getDeepLink() {
            return deepLink;
        }

        public void setDeepLink(String deepLink) {
            this.deepLink = deepLink;
        }

        public String getAdtype() {
            return adtype;
        }

        public void setAdtype(String adtype) {
            this.adtype = adtype;
        }

        public Integer getAdwidth() {
            return adwidth;
        }

        public void setAdwidth(Integer adwidth) {
            this.adwidth = adwidth;
        }

        public Integer getAdheight() {
            return adheight;
        }

        public void setAdheight(Integer adheight) {
            this.adheight = adheight;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String[] getImp() {
            return imp;
        }

        public void setImp(String[] imp) {
            this.imp = imp;
        }

        public String[] getClick() {
            return click;
        }

        public void setClick(String[] click) {
            this.click = click;
        }
    }

    public static class Video{
        /**
         * 视频广告必传, 视频文件url
         */
        @JSONField(name = "video_url")
        private String videoUrl;
        /**
         * 视频文件md5值
         */
        @JSONField(name = "video_md5")
        private String videoMD5;
        /**
         * 图标
         */
        @JSONField(name = "icon_url")
        private String iconUrl;
        /**
         * 大图地址
         */
        @JSONField(name = "img_url")
        private String imgUrl;
        /**
         * 标题
         */
        private String title;
        /**
         * 说明
         */
        private String desc;
        /**
         * 视频广告必传, 播放时长
         */
        private Integer duration;
        /**
         * 横竖屏方向 0 竖屏， 1 横屏
         */
        private Integer orientation;
        /**
         * 视频广告必传, 视频播放完成后加载页H5代码
         */
        private String html;
        /**
         * 视频广告必传, 视频播放完成后加载页地址
         * (与video.html二选一即可)
         */
        @JSONField(name = "video_hurl")
        private String videoHUrl;
        /**
         * 视频广告必传, 视频播放开始监控地址数组
         */
        @JSONField(name = "video_start")
        private String[] videoStart;
        /**
         * 视频广告必传, 视频播放完成后加载页展示监控地址数组
         */
        private String[] imp;
        /**
         * 视频广告必传, 视频播放完成后加载页点击监控地址数组
         */
        private String[] click;

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getVideoMD5() {
            return videoMD5;
        }

        public void setVideoMD5(String videoMD5) {
            this.videoMD5 = videoMD5;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
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

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Integer getOrientation() {
            return orientation;
        }

        public void setOrientation(Integer orientation) {
            this.orientation = orientation;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getVideoHUrl() {
            return videoHUrl;
        }

        public void setVideoHUrl(String videoHUrl) {
            this.videoHUrl = videoHUrl;
        }

        public String[] getVideoStart() {
            return videoStart;
        }

        public void setVideoStart(String[] videoStart) {
            this.videoStart = videoStart;
        }

        public String[] getImp() {
            return imp;
        }

        public void setImp(String[] imp) {
            this.imp = imp;
        }

        public String[] getClick() {
            return click;
        }

        public void setClick(String[] click) {
            this.click = click;
        }
    }

    public static class AdExt{
        /**
         * 包名
         */
        @JSONField(name = "package")
        private String packageName;
        /**
         * 应用名称
         */
        @JSONField(name = "app_name")
        private String appName;
        /**
         * 下载成功链接数组
         */
        @JSONField(name = "down_succ")
        private String[] downSucc;
        /**
         * 安装成功链接数组
         */
        @JSONField(name = "install_succ")
        private String[] installSucc;
        /**
         * 下载开始链接数组
         */
        @JSONField(name = "down_start")
        private String[] downStart;
        /**
         * 安装开始链接数组
         */
        @JSONField(name = "install_start")
        private String[] installStart;
        /**
         * 激活链接数组
         */
        private String[] active;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String[] getDownSucc() {
            return downSucc;
        }

        public void setDownSucc(String[] downSucc) {
            this.downSucc = downSucc;
        }

        public String[] getInstallSucc() {
            return installSucc;
        }

        public void setInstallSucc(String[] installSucc) {
            this.installSucc = installSucc;
        }

        public String[] getDownStart() {
            return downStart;
        }

        public void setDownStart(String[] downStart) {
            this.downStart = downStart;
        }

        public String[] getInstallStart() {
            return installStart;
        }

        public void setInstallStart(String[] installStart) {
            this.installStart = installStart;
        }

        public String[] getActive() {
            return active;
        }

        public void setActive(String[] active) {
            this.active = active;
        }
    }
}

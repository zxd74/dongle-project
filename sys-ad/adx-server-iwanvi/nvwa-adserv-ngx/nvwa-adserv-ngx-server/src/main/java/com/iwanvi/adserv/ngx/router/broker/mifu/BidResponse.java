package com.iwanvi.adserv.ngx.router.broker.mifu;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-06 09:55
 */
public class BidResponse {
    /**
     * 流量唯一标识，impression id
     */
    private String adid;
    /**
     * 广告类型
     */
    private String adtype;
    /**
     * 子广告类型
     */
    private String subadtype;
    /**
     * 创意素材文件/移动视频信息流封面图
     */
    private String[] img;
    /**
     * 移动视频信息流的视频素材
     */
    private String mediafile;
    /**
     * 广告title
     */
    private String title;
    /**
     * 广告描述
     */
    private String desc;
    /**
     * 目标地址(落地页)
     */
    private String Ip;
    /**
     * 曝光监测
     */
    private String[] pm;
    /**
     * 点击监测
     */
    private String[] cm;
    /**
     * 创意展示格式
     */
    private Integer adformat;
    /**
     * 信息流创意的图标
     */
    private String icon;
    /**
     * 视频素材的播放时长
     */
    private Integer duration;

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public String getSubadtype() {
        return subadtype;
    }

    public void setSubadtype(String subadtype) {
        this.subadtype = subadtype;
    }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public String getMediafile() {
        return mediafile;
    }

    public void setMediafile(String mediafile) {
        this.mediafile = mediafile;
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

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String[] getPm() {
        return pm;
    }

    public void setPm(String[] pm) {
        this.pm = pm;
    }

    public String[] getCm() {
        return cm;
    }

    public void setCm(String[] cm) {
        this.cm = cm;
    }

    public Integer getAdformat() {
        return adformat;
    }

    public void setAdformat(Integer adformat) {
        this.adformat = adformat;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}

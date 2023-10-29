package com.iwanvi.adserv.ngx.router.broker.youju;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author: 郑晓东
 * @date: 2019-07-31 10:20
 * @version: v1.0
 * @Description: 优聚响应类
 */
public class BidResponse {

    private Integer code;
    private String msg;
    private Data[] data;

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

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public static class Data{
        private String tagid;
        private Img[] img;
        private String landingurl;
        @JSONField(name = "exposure_url")
        private String[] exposureUrl;
        @JSONField(name = "click_url")
        private String[] clickUrl;

        public String getTagid() {
            return tagid;
        }

        public void setTagid(String tagid) {
            this.tagid = tagid;
        }

        public Img[] getImg() {
            return img;
        }

        public void setImg(Img[] img) {
            this.img = img;
        }

        public String getLandingurl() {
            return landingurl;
        }

        public void setLandingurl(String landingurl) {
            this.landingurl = landingurl;
        }

        public String[] getExposureUrl() {
            return exposureUrl;
        }

        public void setExposureUrl(String[] exposureUrl) {
            this.exposureUrl = exposureUrl;
        }

        public String[] getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String[] clickUrl) {
            this.clickUrl = clickUrl;
        }
    }
    public static class Img{
        private String url;
        private String width;
        private String height;
        private String title;
        private String desc;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
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
    }
}

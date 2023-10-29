package com.iwanvi.adserv.ngx.router.broker.mifu;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-06 10:29
 */
public class MiFuUtil {

    /**
     * 广告创意展示格式
     */
    public enum AdFormat{
        STATIC_PIC(1),DYNAMIC_PIC(2),MP4(6);
        private Integer value;

        AdFormat(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 广告类型
     */
    public enum AdType{
        BANNER(0),FIRST_SCREEN(1),SCREEN(2),NATIVE(6);
        private Integer value;

        AdType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 子广告类型
     *      PIC_VIDEO 大图和视频
     *      PICS 组图（三图）
     */
    public enum SubAdType{
        PIC_TEXT(0),PIC_VIDEO(1),PICS(2);
        private Integer value;

        SubAdType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 屏幕方向
     */
    public enum Orientation{
        UNKNOWN(0),PORTRAIT(1),LANDSCAPE(2);
        private Integer value;

        Orientation(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 安全流量与否
     */
    public enum Source{
        HTTP(0),HTTPS(1);
        private Integer value;

        Source(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 操作系统类型
     */
    public enum OsType{
        OTHER(0),ANDROID(1),IOS(2);
        private Integer value;

        OsType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 运营商
     */
    public enum Carrier{
        UNKNOWN(0),CHINA_MOBILE(1),CHINA_UNICOM(2),CHINA_TELECOM(3);
        private Integer value;
        Carrier(Integer value){
            this.value = value;
        }
        public Integer getValue(){return this.value;}
    }
    /**
     * 网络环境： 1 unknown,2 2g,3 3g,4 4g, 5 5g, 6 wifi,
     */
    public enum NetType {
        UNKNOWN(0), WIFI(1), K2G(2), K3G(3), K4G(4);

        private Integer value;

        NetType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}

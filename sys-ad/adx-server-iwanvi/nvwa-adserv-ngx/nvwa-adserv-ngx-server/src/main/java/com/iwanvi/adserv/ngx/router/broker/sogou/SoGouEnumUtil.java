package com.iwanvi.adserv.ngx.router.broker.sogou;

/**
 * @author: 郑晓东
 * @date: 2019-07-05 09:47
 * @version: v1.0
 * @Description:
 */
public class SoGouEnumUtil {

    /**
     * 网络环境： 1 unknown,2 2g,3 3g,4 4g, 5 5g, 6 wifi,
     */
    public enum NetType {
        UNKNOWN(1), K2G(2), K3G(3), K4G(4), K5G(5), WIFI(6);

        private Integer value;

        NetType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 设备类型 1 手机 2 平板 3 OTT终端 4 PC 9 其他
     * OOT 终端包括互联网电视和电视机顶盒
     */
    public enum DeviceType {
        PHONE(1), PLATE(2), OTT(3), PC(4), OTHER(9);

        private Integer value;

        DeviceType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 操作系统
     */
    public enum OsType{
        ANDROID("android"),IOS("ios");
        private String value;
        OsType(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    /**
     * 1 中国移动 2 中国联通 3 中国电信 9 其他
     */
    public enum Carrier {
        CHAIN_MOBILE(1), CHAIN_UNION(2), CHAIN_TELECOM(3), OTHER(9);

        private Integer value;

        Carrier(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 设备横竖屏，1：竖屏，2：横屏。
     */
    public enum Orientation {
        ORIENTATION_PORTRAIT(1), ORIENTATION_LANDSCAPE(2);

        private Integer value;

        Orientation(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 视频类型
     * video/mp4, mp4格式
     * video/3gpp, 3gp格式
     * video/x-msvideo, avi格式
     * video/x-flv, flv格式
     * video/x-ms-wmv,wmv格式
     * video/quicktime, mov格式
     */
    public enum MimeType {
        VIDEO_MP4(1), VIDEO_3GPP(2), VIDEO_AVI(3), VIDEO_X_FLV(4), VIDEO_X_MS_WMV(5), VIDEO_QUICKTIME(6);

        private Integer value;

        MimeType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 视频加载方式
     * DEFAULT 不限
     * REAL_TIME 实时
     * PRELOAD 预加载
     */
    public enum Delivery {
        DEFAULT(0), REAL_TIME(1), PRELOAD(2);
        private Integer value;

        Delivery(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 广告类型
     * NATIVE_IMG_TEXT 原生图文
     * BANNER
     * FIRST_SCREEN 开屏
     * NATIVE_VIDEO 原生视频
     * INCENTIVE_VIDEO 激励视频
     * OTHER  其它
     */
    public enum AdType {
        NATIVE_IMG_TEXT(1), BANNER(2), FIRST_SCREEN(3), INTERSTITIAL(4), NATIVE_VIDEO(5), INCENTIVE_VIDEO(6), OTHER(9);
        private Integer value;

        AdType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 原生图文广告时，图片类型
     * SMALL 小图
     * BIG 大图
     * GROUP 大图
     * OTHER 其它
     */
    public enum NativeAdType {
        SMALL(1), BIG(2), GROUP(3), OTHER(9);
        private Integer value;

        NativeAdType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 开屏方式
     * FULL_SCREEN 全屏
     * MID_SCREEN 半屏
     * NO_SCREEN 非开屏
     */
    public enum Screen {
        FULL_SCREEN(1), MID_SCREEN(2), NO_SCREEN(-1);
        private Integer value;

        Screen(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 事件类型
     * start:  视频播放开始
     * firstQuartile: 视频播放至25%时
     * midpoint: 视频播放50%时
     * thirdQuartile: 视频播放至75%时
     * end: 视频播放完成时
     * mute: 视频静音时上报
     * skip: 跳过视频时
     * close: 关闭视频时
     */
    public enum EventType {
        START(1), FIRSTQUARTILE(2), MIDPOINT(3), THIRDQUARTILE(4), END(5), MUTE(6), SKIP(7), CLOSE(8);
        private Integer value;

        EventType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 视频跳过
     */
    public enum Skip {
        YES(1), NO(0);
        private Integer value;

        Skip(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}

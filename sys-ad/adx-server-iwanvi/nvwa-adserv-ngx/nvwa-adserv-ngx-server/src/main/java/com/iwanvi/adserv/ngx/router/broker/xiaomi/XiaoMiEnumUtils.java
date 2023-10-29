package com.iwanvi.adserv.ngx.router.broker.xiaomi;

/**
 * @author: 郑晓东
 * @date: 2019-07-17 11:42
 * @version: v1.0
 * @Description: 米盟 Enum 汇总
 */
public class XiaoMiEnumUtils {

    /**
     * 操作系统类型
     *      ANDROID 安卓
     *      IOS  ios
     */
    public enum OsType {
        ANDROID("android"), IOS("ios");
        private String value;

        OsType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 获取广告的模式
     * REAL_TIME 实时
     * ASYN  异步
     * FIXED_TIME 定时
     */
    public enum FetchMode {
        REAL_TIME(0), ASYN(1), FIXED_TIME(2);
        private Integer value;

        FetchMode(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 网络连接类型
     */
    public enum NetType {
        UNKNOWN("unknown"), WIFI("wifi"), K2G("2g"),K3G("3g"),K4G("4g");
        private String value;

        NetType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 广告类型
     *      NATIVE_BIG_IMG 信息流大图
     *      NATIVE_SMALL_IMG 信息流小图
     *      NATIVE_IMGS 信息流组图（三图）
     *      FIRST_SCREEN 竖版开屏
     *      INTERSTITIAL 插屏
     *      BANNER 横幅
     */
    public enum AdStyle{
        NATIVE_BIG_IMG(4),NATIVE_SMALL_IMG(6),NATIVE_IMGS(7),FIRST_SCREEN(40),INTERSTITIAL(20),BANNER(50);
        private Integer value;
        AdStyle(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 广告交互类型
     *      LINK 外链
     *      DOWNLOAD 应用下载
     */
    public enum TargetType{
        LINK(1),DOWNLOAD(2);
        private Integer value;
        TargetType(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 素材类型
     *      PIC 图片
     *      GIF 动图
     *      VIDEO 视频
     *      AUDIO 音频
     */
    public enum MaterialType{
        PIC(1),GIF(2),VIDEO(3),AUDIO(4);
        private Integer value;
        MaterialType(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }
}

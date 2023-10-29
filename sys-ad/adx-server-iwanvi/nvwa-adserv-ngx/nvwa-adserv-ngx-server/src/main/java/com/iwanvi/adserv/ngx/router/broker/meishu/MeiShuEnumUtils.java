package com.iwanvi.adserv.ngx.router.broker.meishu;

/**
 * @author: 郑晓东
 * @date: 2019-07-09 17:22
 * @version: v1.0
 * @Description: 美数 枚举 公共类
 */
public class MeiShuEnumUtils {

    /**
     * Android 设备是否 ROOT。1--是, 0--否/ 未知(默认)
     */
    public enum IsRoot{
        YES(1),NO(0);
        private Integer value;
        IsRoot(Integer value){
            this.value =value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 设备横竖屏，0：竖屏，1：横屏。
     */
    public enum Orientation{
        ORIENTATION_PORTRAIT(0), ORIENTATION_LANDSCAPE(1);
        private Integer value;
        Orientation(Integer value){
            this.value =value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 操作系统类型
     */
    public enum OsType{
        ANDROID("Android"),IOS("iOS"),WP("WP"),OTHERS("Others");
        private String value;
        OsType(String value){
            this.value =value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 网络环境： 5 other,4 2g,3 3g,2 4g, 6 5g, 1 wifi,
     */
    public enum NetType {
        OTHER(5), K2G(4), K3G(3), K4G(2), K5G(6), WIFI(1);
        private Integer value;
        NetType(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 46000 中国移动 46001 中国联通 46003 中国电信 46004 未知
     */
    public enum Carrier {
        CHAIN_MOBILE("46000"), CHAIN_UNION("46001"), CHAIN_TELECOM("46003"), OTHER("46004");

        private String value;

        Carrier(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 设备类型 0 手机 1 平板 2 PC 3 TV 4 WAP  -1 未知
     */
    public enum DeviceType {
        PHONE(0), PAD(1), TV(4), PC(2), WAP(4),UNKNOWN(-1);
        private Integer value;
        DeviceType(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * http协议类型  0 http；1 https ； 2 都支持（默认）
     */
    public enum HttpType{
        HTTP(0),HTTPS(1),BOTH(2);
        private Integer value;
        HttpType(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 广告交互类型(0:网页跳转,1:下载) 默认0
     */
    public enum TargetType{
        PAGE(0),DOWNLOAD(1);
        private Integer value;
        TargetType(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 创意类型  （1:图片、2:视频、3：音频） 默认1
     */
    public enum CreativeType{
        PIC(1),VIDEO(2),AUDIO(3);
        private Integer value;
        CreativeType(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }
}

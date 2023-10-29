package com.iwanvi.adserv.ngx.router.broker.shenqihuyu;

/**
 * @author: 郑晓东
 * @date: 2019-06-17 17:42
 * @version: v1.0
 * @Description:
 */
public class ShenQiHuYuUtil {

    /**
     * 广告类型
     */
    public enum AdType{
        VIDEO(1),BANNER(2),INTERSTITIAL(3),FULLSCREEN(4),DOWNLOAD(1),UN_DOWNLOAD(2);
        private Integer value;
        AdType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 网络类型
     *      CELLULAR == 2 网络类型
     */
    public enum NetType{
        UNKNOWN(0),WIFI(1),CELLULAR(2);
        private Integer value;
        NetType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 横竖屏
     *      PORTRAIT == 0 竖屏
     *      LANDSCAPE == 1 横屏
     */
    public enum OrientationType{
        PORTRAIT(0),LANDSCAPE(1);
        private Integer value;
        OrientationType(Integer value){this.value = value;}
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 操作系统
     */
    public enum PlatformType{
        ANDROID("android"),IOS("ios");
        private String value;
        PlatformType(String value){this.value = value;}
        public String getValue() {
            return value;
        }
    }

    /**
     * 运营商ID
     * 46000 中国移动 （GSM）== CHINA_MOBILE
     * 46001 中国联通 （GSM）== CHINA_UNICOM
     * 46002 中国移动 （TD-S）
     * 46003 中国电信（CDMA）== CHINA_TELECOM
     * 46004 空（似乎是专门用来做测试的）== UNKNOWN
     * 46005 中国电信 （CDMA）
     * 46006 中国联通 （WCDMA）
     * 46007 中国移动 （TD-S）
     * 46011 中国电信 （FDD-LTE）
     */
    public enum CarrierType{
        CHINA_MOBILE("46000"),CHINA_UNICOM("46001"),CHINA_TELECOM("46003"),UNKNOWN("46004");
        private String value;
        CarrierType(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

}

package com.iwanvi.adserv.ngx.router.broker.taido;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-01 18:08
 */
public class TaiDoUtils {

    /**
     * 广告位类型
     * 广告类型
     */
    public enum AdType{
        BANNER(1),SCREEN(2),NATIVE(3),FIRST_SCREEN(4),
        TEXT(1),PIC(2),PIC_TEXT(3),VIDEO(4),HTML(5);
        private Integer value;
        AdType(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 操作系统类型
     */
    public enum OsType{
        ANDROID(1),IOS(2);
        private Integer value;
        OsType(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 网络类型
     */
    public enum NetType{
        WIFI(1),K2G(2),K3G(3),K4G(4),K5G(5),UNKNOWN(6);
        private Integer value;
        NetType(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 运营商类型
     */
    public enum Carrier{
        CHINA_MOBILE(1),CHINA_UNICOM(2),CHINA_TELETCOM(3),UNKNOWN(4);
        private Integer value;
        Carrier(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 设别类型
     */
    public enum DeviceType{
        PHONE(1),TABLET(2);
        private Integer value;
        DeviceType(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 横竖屏
     */
    public enum Direct{
        PORTRAIT(0), LANDSCAPE(1);
        private Integer value;
        Direct(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 交互类型
     *      OUT_LINK 外链 （落地页H5页面）
     *      MINI_APP 小程序连接打开
     */
    public enum InteractType{
        OUT_LINK(1),DOWNLOAD(2),MINI_APP(3),DEEP_LINK(4);
        private Integer value;
        InteractType(Integer value){this.value = value;}

        public Integer getValue() {
            return value;
        }
    }
}

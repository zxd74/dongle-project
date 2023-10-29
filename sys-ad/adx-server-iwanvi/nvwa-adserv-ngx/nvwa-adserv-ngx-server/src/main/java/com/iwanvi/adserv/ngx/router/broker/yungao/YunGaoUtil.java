package com.iwanvi.adserv.ngx.router.broker.yungao;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-09 13:52
 */
public class YunGaoUtil {

    /**
     * 媒体类别
     */
    public enum MediaType{
        APP(1),H5(2);
        private Integer value;

        MediaType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 协议类别
     */
    public enum ProtocolType{
        HTTP(1),HTPPS(2);
        private Integer value;

        ProtocolType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 设备类别
     *      OUTDOOR_SCREEN 户外屏
     */
    public enum DeviceType{
        PHONE(1),TABLET(2),TV(3),OUTDOOR_SCREEN(4);
        private Integer value;

        DeviceType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 网络连接类别
     */
    public enum NetType{
        CONNECTION_UNKNOWN(0),CELL_UNKNOWN(1),CELL_2G(2),CELL_3G(3),CELL_4G(4),CELL_5G(5),WIFI(100),ETHERNET(101),NEW_TYPE(999);
        private Integer value;

        NetType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 运营商类别
     */
    public enum Carrier{
        UNKNOWN_OPERATOR(0),CHINA_MOBILE (1),CHINA_TELECOM(2),CHINA_UNICOM(3),OTHER_OPERATOR(4);
        private Integer value;

        Carrier(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 运营商类别
     *      NO_INTERACTION 其他
     *      SURFING 打开网页
     *      DOWNLOAD
     *      DEEPLINK
     *      NOTIFY 通知栏推送
     */
    public enum InteractionType{
        NO_INTERACTION(0),SURFING (1),DOWNLOAD(2),DEEPLINK(3),NOTIFY(4);
        private Integer value;

        InteractionType(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}

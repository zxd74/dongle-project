package com.iwanvi.adserv.ngx.router.broker.oneway;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-05 09:52
 */
public class OneWayUtils {

    /**
     * 创意类型
     */
    public enum CreativeType{
        VIDEO(1),PIC(2);
        private Integer value;
        CreativeType(Integer value){this.value=value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * NONE无网络, WIFI网络,CALLULAR移动网络
     */
    public enum ConnnectType{
        NONE("none"),WIFI("wifi"),CALLULAR("cellular");
        private String value;
        ConnnectType(String value){this.value=value;}

        public String getValue() {
            return value;
        }
    }

    /**
     * 网络类型
     *      NETWORK_TYPE_UNKNOWN
     *      2g: NETWORK_TYPE_GPRS(联通) NETWORK_TYPE_EDGE(移动) NETWORK_TYPE_CDMA(电信)
     *      3g: NETWORK_TYPE_TD_SCDMA(移动), NETWORK_TYPE_HSPA(联通), NETWORK_TYPE_EVDO_A(电信)
     *      4g/wifi: NETWORK_TYPE_LTE
     */
    public enum NetType{
        NETWORK_TYPE_UNKNOWN(0),NETWORK_TYPE_GPRS(1),NETWORK_TYPE_EDGE(2),
        NETWORK_TYPE_UMTS(3),NETWORK_TYPE_CDMA(4),
        NETWORK_TYPE_EVDO_0(5),NETWORK_TYPE_EVDO_A(6),NETWORK_TYPE_1xRTT(7),
        NETWORK_TYPE_HSDPA(8),NETWORK_TYPE_HSUPA(9),NETWORK_TYPE_HSPA(10),
        NETWORK_TYPE_IDEN(11),NETWORK_TYPE_EVDO_B(12),NETWORK_TYPE_LTE(13),
        NETWORK_TYPE_EHRPD(14),NETWORK_TYPE_HSPAP(15),NETWORK_TYPE_GSM(16),
        NETWORK_TYPE_TD_SCDMA(17),NETWORK_TYPE_IWLAN(18);
        private Integer value;
        NetType(Integer value){this.value=value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 46000 中国移动 46001 中国联通 46003 中国电信 46004 未知
     */
    public enum Carrier {
        CHAIN_MOBILE("46000"), CHAIN_UNICOM("46001"), CHAIN_TELECOM("46003"), OTHER("46004");
        private String value;
        Carrier(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    /**
     * 下载类型（广告类型）
     */
    public enum DownloadType{
        DEEP_LINK(0),LOADPAGE(1),DOWNLOAD(2);
        private Integer value;
        DownloadType(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
    }
}

package com.iwanvi.adserv.ngx.router.broker.youju;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: 郑晓东
 * @createtime: 2019-07-31 11:10
 * @version: v1.0
 * @description: 优聚枚举类集合
 */
public class YouJuEnumUtils {

    /**
     * 网络连接类型
     */
    public enum NetType{
        UNKNOWN(1),K2G(2),K3G(3),K4G(4),K5G(5),WIFI(20),ETHERNET(21);
        private Integer value;
        NetType(Integer value) {this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 运营商类型
     */
    public enum Carrier{
        UNKNOWN(0),CHINA_MOBILE(1),CHINA_TELECOM(2),CHINA_UNICOM(3),OTHER(20);
        private Integer value;
        Carrier(Integer value) {this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 操作系统类型
     */
    public enum OsType{
        ANDROID(1),IOS(2),OTHER(10);
        private Integer value;
        OsType(Integer value) {this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 设备类型
     */
    public enum DeviceType{
        PHONE(1),TABLET(2),OTHER(3);
        private Integer value;
        DeviceType(Integer value) {this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    /**
     * 是否支持DP
     */
    public enum IsDP{
        YES(1),NO(0);
        private Integer value;
        IsDP(Integer value) {this.value = value;}

        public Integer getValue() {
            return value;
        }
    }

    public static String kSort(Map<String, Object> params){
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key).toString();
            if (i == keys.size() - 1) {
                // 拼接时，最后一个不包括&字符
                builder.append(key + "=" + value);
            } else {
                builder.append(key + "=" + value + "&");
            }
        }
        return builder.toString();
    }
}

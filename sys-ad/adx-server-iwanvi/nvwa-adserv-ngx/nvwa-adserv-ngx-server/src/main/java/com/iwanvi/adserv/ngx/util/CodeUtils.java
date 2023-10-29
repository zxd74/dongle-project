package com.iwanvi.adserv.ngx.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 郑晓东
 * @version : v1.0
 * @since : 2019-08-14 17:51
 */
public class CodeUtils {

    public static final String BID_WIN = "300000";

    public static final String REQUEST_PARAM_ERROR = "300001";

    public static final String BID_ERROR ="300002";
    public static final String DSP_ERROR="300003";
    public static final String DSP_OVER_QPS="300004";
    public static final String APP_MAP_ERROR="300005";
    public static final String POS_MAP_ERROR="300006";

    public static final String REQUEST_URL_ERROR="300007";
    public static final String REQUEST_TIMEOUT="300008";

    public static final String RESPONSE_NOT_BID="300009";
    public static final String RESPONSE_OTHER="300010";
    public static final String RESPONSE_NOT_AD="300011";
    public static final String RESPONSE_NOT_REQUEST="300012";
    public static final String BID_LOWER_FLOOR_PRICE ="300020";
    public static final String BID_LOWER_OTHER_PRICE ="300021";
    public static final String RESPONSE_HANDLER_NOT_AD="300031";
    public static final String AD_WORDS_FILTER="300030";

    public static final String AD_NOT_CREATIVE="300013";
    public static final String AD_TEMPLATE_ERROR ="300014";
    public static final String AD_NOT_VIDEO="300016";
    public static final String AD_NOT_GROUP_IMAGE ="300017";
    public static final String AD_NOT_IMG="300018";
    public static final String AD_PRICE_ERROR="300019";
    public static final String AD_DOWNLOAD_ERROR ="300022";
    public static final String AD_HANDLER_ERROR="300029";

    private static final Map<String,String> codeDesc = new ConcurrentHashMap<>();

    static {
        codeDesc.put(BID_WIN,"竞价胜出。");
        codeDesc.put(REQUEST_PARAM_ERROR,"请求参数异常。");
        codeDesc.put(BID_ERROR,"广告竞价异常。");
        codeDesc.put(DSP_ERROR,"DSP信息获取失败。");
        codeDesc.put(DSP_OVER_QPS,"DSP广告请求超过QPS。");
        codeDesc.put(APP_MAP_ERROR,"App无映射数据。");
        codeDesc.put(POS_MAP_ERROR,"Pos无映射数据。");
        codeDesc.put(REQUEST_URL_ERROR,"请求接口异常。");
        codeDesc.put(REQUEST_TIMEOUT,"请求接口超时。");

        codeDesc.put(RESPONSE_NOT_BID,"响应204不竞价。");
        codeDesc.put(RESPONSE_OTHER,"响应其他状态码（非200/204）。");
        codeDesc.put(RESPONSE_NOT_AD,"响应200无广告内容。");
        codeDesc.put(RESPONSE_HANDLER_NOT_AD,"广告响应处理无合规广告");
        codeDesc.put(RESPONSE_NOT_REQUEST,"广告请求ID和曝光ID不符。");
        codeDesc.put(BID_LOWER_FLOOR_PRICE,"广告出价低于底价。");
        codeDesc.put(BID_LOWER_OTHER_PRICE,"广告出价低于他家。");
        codeDesc.put(AD_DOWNLOAD_ERROR,"广告是直接下载广告，但地址无效。");
        codeDesc.put(AD_HANDLER_ERROR,"广告响应处理异常。");
        codeDesc.put(AD_WORDS_FILTER,"竞品敏感词过滤。");

        codeDesc.put(AD_NOT_CREATIVE,"平台广告创意不存在（提审/先投后审）。");
        codeDesc.put(AD_TEMPLATE_ERROR,"原生广告模板不匹配（非请求中的）。");
        codeDesc.put(AD_NOT_VIDEO,"广告素材视频无效。");
        codeDesc.put(AD_NOT_GROUP_IMAGE,"广告素材组图无效。");
        codeDesc.put(AD_NOT_IMG,"广告素材图片无效。");
        codeDesc.put(AD_PRICE_ERROR,"广告价格处理异常。");

    }

    public static String getCodeDesc(String code){
        if (codeDesc.containsKey(code)){
            return codeDesc.get(code);
        }
        return null;
    }
}

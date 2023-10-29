package com.dongle.adx.common.util;

import com.alibaba.fastjson.JSON;

/**
 * @author : 郑晓东
 * @version : v1.0
 * @description ：接口响应类型
 * @since : 2019-10-14 18:19
 */
public class ResponseUtils {

    private String code;
    private String msg;
    private Object data;

    private static String response(ResponseUtils response) {
        return JSON.toJSONString(response);
    }

    public static String OK() {
        ResponseUtils response = new ResponseUtils();
        response.code = StaticUtils.OK;
        response.msg = StaticUtils.OK_MSG;
        return response(response);
    }

    public static String OK(Object data) {
        ResponseUtils response = new ResponseUtils();
        response.code = StaticUtils.OK;
        response.msg = StaticUtils.OK_MSG;
        response.data = data;
        return response(response);
    }

    public static String ERROR() {
        ResponseUtils response = new ResponseUtils();
        response.code = StaticUtils.ERROR;
        response.msg = StaticUtils.ERROR_MSG;
        return response(response);
    }

    public static String ERROR(String msg) {
        ResponseUtils response = new ResponseUtils();
        response.code = StaticUtils.ERROR;
        response.msg = msg;
        return response(response);
    }

    public static String ERROR(String code, String msg) {
        ResponseUtils response = new ResponseUtils();
        response.code = code;
        response.msg = msg;
        return response(response);
    }
}

package com.dongle.question.utils;

import lombok.Data;

public class ResponseUtils {

    public final static String DEFAULT_SUCCESS_MESSAGE = "访问成功！";
    public final static String DEFAULT_ERROR_MESSAGE = "发生未知或未处理异常！";
    @Data
    public static class ResponseData{
        Object data;
        String msg;
        int code;
    }
    public enum ResponseCode{
        SUCCESS(0),
        ERROR(-1),
        REQUEST_ERROR(10000),
        PARAM_ERROR(20000),
        HANDLE_ERROR(30000),
        RESPONSE_ERROR(40000);
        private final int code;

        ResponseCode(int code){
            this.code = code;
        }
    }

    public static ResponseData success(){
        return response(ResponseCode.SUCCESS,DEFAULT_SUCCESS_MESSAGE,null);
    }
    public static ResponseData success(String message){
        return response(ResponseCode.SUCCESS,message,null);
    }
    public static ResponseData success(String message,Object data){
        return response(ResponseCode.SUCCESS,message,data);
    }

    public static ResponseData error(){
        return response(ResponseCode.ERROR,DEFAULT_ERROR_MESSAGE,null);
    }
    public static ResponseData error(String message){
        return response(ResponseCode.ERROR,message,null);
    }
    public static ResponseData error(String message,Object data){
        return response(ResponseCode.ERROR,message,data);
    }

    public static ResponseData response(ResponseCode code,String message,Object data){
        ResponseData responseData = new ResponseData();
        responseData.msg = message;
        responseData.code = code.code;
        responseData.data = data;
        return responseData;
    }
}

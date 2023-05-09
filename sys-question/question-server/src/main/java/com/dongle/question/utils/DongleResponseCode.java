package com.dongle.question.utils;

public enum DongleResponseCode {
    SUCCESS(0,"请求成功"),
    ERROR(-1,"请求失败"),
    REQUEST_ERROR(10000,"请求异常"),
    PARAM_ERROR(20000,"参数错误"),
    HANDLE_ERROR(30000,"业务处理错误"),
    RESPONSE_ERROR(40000,"响应错误");

    private final int code;
    private final String msg;

    DongleResponseCode(int code,String msg) {
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

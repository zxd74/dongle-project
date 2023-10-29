package com.fftime.ffmob.aggregation.model;

public class FFAdError {
    private int errorCode;
    private String errorMes;

    public FFAdError(int errorCode, String errorMes) {
        this.errorCode = errorCode;
        this.errorMes = errorMes;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMes() {
        return errorMes;
    }

    public void setErrorMes(String errorMes) {
        this.errorMes = errorMes;
    }

    /**
     * key code 映射
     */
    public static final int CODE_1000 = 1000;
    public static final int CODE_1001 = 1001;
    public static final int CODE_1002 = 1002;

    public static final String TEXT_FETCH_SETTING_ERROR = "fetch ad setting error";
    public static final String TEXT_FETCH_NO_POSID_ERROR = "fetch ad has no posid";
    public static final String TEXT_FETCH_LISTENER_NO_MATCH = "fetch ad listener is not match";
}

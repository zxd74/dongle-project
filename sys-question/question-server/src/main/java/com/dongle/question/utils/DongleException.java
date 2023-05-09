package com.dongle.question.utils;

public class DongleException extends RuntimeException{

    private final DongleResponseCode code;

    public DongleException(DongleResponseCode code) {
        this.code = code;
    }

    public DongleException(Throwable cause,DongleResponseCode code){
        super(cause);
        this.code = code;
    }

    public DongleResponseCode getCode() {
        return code;
    }
}

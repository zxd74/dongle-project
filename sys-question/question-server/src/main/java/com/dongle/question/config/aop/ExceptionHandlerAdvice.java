package com.dongle.question.config.aop;


import com.dongle.question.utils.DongleException;
import com.dongle.question.utils.ResponseUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({Exception.class,RuntimeException.class})
    public ResponseUtils.ResponseData handleException(Exception ex){
        ex.printStackTrace();
        // TODO
        return ResponseUtils.error();
    }

    @ExceptionHandler(DongleException.class)
    public ResponseUtils.ResponseData handleException(DongleException ex){
        ex.printStackTrace();
        // TODO
        return ResponseUtils.error(ex.getCode().getMsg());
    }
}

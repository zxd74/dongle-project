package com.iwanvi.nvwa.web.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionAdvice {
    static Logger LOG = LoggerFactory.getLogger("ExceptionAdvice");

    @ResponseBody
    @ExceptionHandler(value = AuthException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> authExceptionHandler(AuthException ex) {
        //LOG.error("exception happen, cause: " + ex.getMessage(), ex);
        Map<String, Object> map = new HashMap<>();
      
        map.put("code", "E000000"); 
        map.put("timestamp", System.currentTimeMillis());
        map.put("message", "未授权的访问");
        return map;
    }   
    
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> errorHandler(Exception ex) {
        LOG.error("exception happen, cause: " + ex.getMessage(), ex);
        Map<String, Object> map = new HashMap<>();
        
        map.put("code", "E000000");
        map.put("timestamp", System.currentTimeMillis());
        map.put("message", ex.getMessage());
        return map;
    }   
}

package com.dongle.question.config.aop;

import com.dongle.question.config.annotation.DongleResponse;
import com.dongle.question.utils.ResponseUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应处理
 * @see DongleResponse
 */
@ControllerAdvice(annotations = DongleResponse.class)
public class DongleResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (MediaType.APPLICATION_JSON.equals(selectedContentType)){
            if (!(body instanceof ResponseUtils.ResponseData)){
                return ResponseUtils.success(body);
            }
        }
        return body;
    }
}

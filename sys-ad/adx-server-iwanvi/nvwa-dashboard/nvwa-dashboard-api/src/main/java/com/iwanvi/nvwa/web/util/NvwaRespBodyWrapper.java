/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.web.util;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 
 * @author wangwp
 */
public class NvwaRespBodyWrapper implements HandlerMethodReturnValueHandler {
	private final HandlerMethodReturnValueHandler delegate;

	public NvwaRespBodyWrapper(HandlerMethodReturnValueHandler delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return returnType.getMethodAnnotation(NvwaRespBody.class) != null || delegate.supportsReturnType(returnType);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		Object wrapReturnValue = returnValue;
		if (returnType.getMethodAnnotation(NvwaRespBody.class) != null) {
			wrapReturnValue = new NvwaResp("OK", returnValue);
		}
		delegate.handleReturnValue(wrapReturnValue, returnType, mavContainer, webRequest);
	}
}

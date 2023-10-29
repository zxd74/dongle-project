/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.openapi.adx.model.ApiRespBody;

import ai.houyi.dorado.rest.annotation.ExceptionAdvice;
import ai.houyi.dorado.rest.annotation.ExceptionType;
import ai.houyi.dorado.rest.util.ExceptionUtils;

/**
 * 全局异常处理器
 * 
 * @author wangwp
 */
@ExceptionAdvice
public class ApiExceptionAdvicor {
	private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionAdvicor.class);

	@ExceptionType(NullPointerException.class)
	public ApiRespBody handleException(NullPointerException ex) {
		logError(ex);
		return ApiRespBody.builder().withCode(1).withMsg(ExceptionUtils.toString(ex)).build();
	}

	@ExceptionType(Exception.class)
	public ApiRespBody handleException(Exception ex) {
		logError(ex);
		return ApiRespBody.builder().withCode(1).withMsg(ex.getMessage()).build();
	}

	private void logError(Throwable ex) {
		LOG.error("Api exception: " + ex.getMessage(), ex);
	}

	@ExceptionType(Error.class)
	public ApiRespBody handleException(Error ex) {
		logError(ex);
		return ApiRespBody.builder().withCode(1).withMsg(ex.getMessage()).build();
	}

	@ExceptionType(Throwable.class)
	public ApiRespBody handleException(Throwable ex) {
		logError(ex);
		return ApiRespBody.builder().withCode(1).withMsg(ex.getMessage()).build();
	}
}

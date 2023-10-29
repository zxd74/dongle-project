/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

import org.springframework.stereotype.Component;

import com.iwanvi.nvwa.openapi.adx.model.ApiRespBody;

import ai.houyi.dorado.rest.http.MethodReturnValueHandler;
import ai.houyi.dorado.rest.util.MethodDescriptor;

/**
 * 对接口响应结果进行统一处理
 * 
 * @author wangwp
 */
@Component
public class ApiRespWrapper implements MethodReturnValueHandler {

	@Override
	public Object handleMethodReturnValue(Object value, MethodDescriptor methodDescriptor) {
		return ApiRespBody.builder().withCode(0).withMsg("success").withData(value).build();
	}

	@Override
	public boolean supportsReturnType(MethodDescriptor returnType) {
		// TODO Auto-generated method stub
		return true;
	}
}

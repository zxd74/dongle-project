/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

/**
 * 
 * @author wangwp
 */
public final class ExceptionUtils {
	
	public static void throwOpenApiException(String msg) {
		throw new ApiException(msg);
	}
}

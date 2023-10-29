/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.web.util;

import com.iwanvi.nvwa.common.exception.ServiceException;

public final class ExceptionUtils {

	public static void throwException(String code, String msg) {
		throw new ControllerException(code, msg);
	}

	public static void throwException(String msg) {
		throw new ControllerException(msg);
	}

	public static void throwException(RuntimeException ex) {
		throw ex;
	}
	
	public static void throwServiceException(String msg) {
		throw new ServiceException(msg);
	}
}

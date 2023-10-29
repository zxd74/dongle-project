/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

import com.iwanvi.nvwa.dao.model.FlowConsumer;

/**
 * 
 * @author wangwp
 */
public class AuthContext {
	private static final AuthContext AUTH_CONTEXT = new AuthContext();

	private final ThreadLocal<FlowConsumer> dspHolder = new ThreadLocal<>();

	private AuthContext() {
	}

	public static AuthContext get() {
		return AUTH_CONTEXT;
	}

	public FlowConsumer getDsp() {
		return dspHolder.get();
	}

	public void setDsp(FlowConsumer dsp) {
		dspHolder.set(dsp);
	}
}

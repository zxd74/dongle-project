/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import com.iwanvi.adserv.ngx.BiddingContext;

/**
 * 
 * @author wangwp
 */
public class ContextHolder {
	private final ThreadLocal<BiddingContext> context_holder;

	private static final ContextHolder _instance = new ContextHolder();

	private ContextHolder() {
		this.context_holder = new ThreadLocal<BiddingContext>();
	}

	public static ContextHolder getInstance() {
		return _instance;
	}

	public BiddingContext context() {
		return context_holder.get();
	}

	public void setContext(BiddingContext context) {
		context_holder.set(context);
	}
}

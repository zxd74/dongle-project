/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.index;

import com.iwanvi.adserv.ngx.util.ServiceLoaderUtils;

/**
 * 
 * @author wangwp
 */
public class ReverseIndexFactory {
	private static ReverseIndex<?> _reverse_index = ServiceLoaderUtils.loadService(ReverseIndex.class);

	@SuppressWarnings("unchecked")
	public static <T> ReverseIndex<T> getReverseIndex() {
		return (ReverseIndex<T>) _reverse_index;
	}
}

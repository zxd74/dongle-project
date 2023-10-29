/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

/**
 * @author wangwp
 *
 */
public interface ExpiredCallback {
	void expired(int key, ExpiredIntIntHashMap expiredMap);
}

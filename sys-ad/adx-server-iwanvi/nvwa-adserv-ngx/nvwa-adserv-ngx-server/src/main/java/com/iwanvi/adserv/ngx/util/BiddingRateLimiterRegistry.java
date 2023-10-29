/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author wangwp
 */
public class BiddingRateLimiterRegistry {
	private static final ConcurrentHashMap<String, RateLimiter> _REGISTRY = new ConcurrentHashMap<>();

	public static RateLimiter getRateLimiter(String bucket) {
		return _REGISTRY.get(bucket);
	}

	public static void register(RateLimiter rateLimiter) {
		_REGISTRY.put(rateLimiter.getBucket(), rateLimiter);
	}

	public static void unregister(int adUnitId) {
		_REGISTRY.remove(String.format("%s%d", Constants.REDIS_KEY_PREFIX_RATE_LIMITER, adUnitId));
	}
}

/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author wangwp
 */
public interface RedisCallback {
	Object doRedis(Jedis jedis);
}

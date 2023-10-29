/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author wangwp
 */
public class RedisTemplate {

	public static Object execute(RedisCallback callback) {
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getRedisClient();
			return callback.doRedis(jedis);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if (jedis != null) {
					jedis.close();
				}
			} catch (Exception ex) {
				// ignore this exception
			}
		}
	}

	public static Object executeQuietly(RedisCallback callback) {
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getRedisClient();
			return callback.doRedis(jedis);
		} catch (Exception ex) {
			// throw new RuntimeException(ex);
		} finally {
			try {
				if (jedis != null) {
					jedis.close();
				}
			} catch (Exception ex) {
				// ignore this exception
			}
		}
		return null;
	}
	
	public static String get(String key) {
		return (String) executeQuietly((r)-> {
			return r.get(key);
		});
	}
} 

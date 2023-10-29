/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @author wangwp
 */
public class RedisTemplate {
	private final JedisPool redisPool;

	public RedisTemplate(JedisPool redisPool) {
		this.redisPool = redisPool;
	}

	public void lpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = redisPool.getResource();
			jedis.lpush(key, value);
		} catch (Exception ex) {
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}
}

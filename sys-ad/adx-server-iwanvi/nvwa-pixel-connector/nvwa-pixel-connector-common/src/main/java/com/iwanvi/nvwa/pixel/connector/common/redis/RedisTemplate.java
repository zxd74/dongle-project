/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.nvwa.pixel.connector.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.pixel.connector.common.utils.JedisUtil;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author wangwp
 */
public class RedisTemplate {
	private static final Logger LOG = LoggerFactory.getLogger("redisTemplate");

	public static Object execute(RedisCallback callback) {
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			return callback.doRedis(jedis);
		} catch (Exception ex) {
			LOG.error("", ex);
			JedisUtil.releaseBroken(jedis);
			throw new RuntimeException(ex);
		} finally {
			try {
				if (jedis != null) {
					JedisUtil.release(jedis);
				}
			} catch (Exception ex) {
				// ignore this exception
			}
		}
	}
}

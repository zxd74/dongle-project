/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.nvwa.pixel.connector.common.utils;

import com.iwanvi.nvwa.common.utils.PropertyGetter;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis客户端工具类
 * 
 * @author wangwp
 */
public class JedisUtil {
	static class JedisPoolHolder {
		static JedisPoolConfig poolConfig = new JedisPoolConfig();
		static JedisPool instance;
		static JedisPool instanceSmoothBudgetDelivery;

		static {
			poolConfig.setMaxTotal(PropertyGetter.getInt("redis.pool.maxTotal", 200));
			poolConfig.setMaxIdle(PropertyGetter.getInt("redis.pool.maxIdle", 200));
			poolConfig.setTestOnBorrow(false);
			poolConfig.setTimeBetweenEvictionRunsMillis(PropertyGetter
					.getLong("redis.pool.timeBetweenEvictionRunsMillis"));
			poolConfig.setMinEvictableIdleTimeMillis(PropertyGetter.getLong("redis.pool.minEvictableIdleTimeMillis"));
			// poolConfig.setTestWhileIdle(true);
			poolConfig.setMaxWaitMillis(1000);

			String redisHost = PropertyGetter.getString("redis.host");
			int redisPort = PropertyGetter.getInt("redis.port");
			
			instance = new JedisPool(poolConfig, redisHost, redisPort, PropertyGetter.getInt("redis.timeout", 1000),
					PropertyGetter.getString("redis.password"), PropertyGetter.getInt("redis.default.db"));
		}
	}

	public static Jedis getJedis() {
		return JedisPoolHolder.instance.getResource();
	}
	
	public static Jedis getSmoothBudgetDeliveryJedis() {
		return JedisPoolHolder.instanceSmoothBudgetDelivery.getResource();
	}

	public static void release(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	public static void releaseBroken(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}
}

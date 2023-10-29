/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author wangwp
 */
@Configuration
@EnableConfigurationProperties(RedisConfigProperties.class)
public class RedisConfiguration {
	@Autowired
	private RedisConfigProperties redisConfig;

	@Bean
	public JedisPool jedis() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(redisConfig.getPoolSize());
		poolConfig.setMaxIdle(redisConfig.getPoolSize());

		JedisPool jedisPool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
				redisConfig.getTimeout(), redisConfig.getAuth(), redisConfig.getDatabase(), false, null, null, null);
		return jedisPool;
	}

	@Bean
	public RedisTemplate redisTemplate(JedisPool pool) {
		return new RedisTemplate(pool);
	}
}

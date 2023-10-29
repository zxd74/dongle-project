/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Pool;

/**
 * @author weiping wang
 *
 */
public class RedisUtils {
	private static final Logger LOG = LoggerFactory.getLogger(RedisUtils.class);

	private static final Map<String, Pool<?>> redis_pool_holder = new HashMap<>();
	private static JedisPool default_pool;
	private static JedisPool ratelimit_pool;

	static {
		RedisConfiguration config = RedisConfiguration.create();
		config.getRedisConfigList().forEach(rc -> {
			redis_pool_holder.put(rc.getId(), initRedisPool(rc));
		});
		default_pool = (JedisPool) redis_pool_holder.get("default");
		ratelimit_pool = (JedisPool) redis_pool_holder.get("ratelimit");
	}

	private static Pool<?> initRedisPool(RedisConfig rc) {
		try {
			Pool<?> redisPool = null;
			GenericObjectPoolConfig poolConfig = buildPoolConfig(rc);
			boolean isSharding = rc.isSharding();
			if (isSharding) {
				redisPool = new ShardedJedisPool(poolConfig, buildShards(rc));
			} else {
				HostAndPort hap = HostAndPort.parseString(rc.getAddress());
				redisPool = new JedisPool(poolConfig, hap.getHost(), hap.getPort(), rc.getConnectTimeout(),
						rc.getSoTimeout(), rc.getAuth(), rc.getDb(), null, false, null, null, null);
			}
			return redisPool;
		} catch (Throwable ex) {
			LOG.error("init redis pool error", ex);
		}
		return null;
	}

	private static List<JedisShardInfo> buildShards(RedisConfig rc) {
		String[] redisServers = StringUtils.split(rc.getAddress(), Constants.SIGN_COMMA);
		List<JedisShardInfo> shards = new ArrayList<>();
		for (String redisServer : redisServers) {
			JedisShardInfo shard = new JedisShardInfo(String.format("redis://%s/%d", redisServer, rc.getDb()));
			shard.setConnectionTimeout(rc.getConnectTimeout());
			shard.setSoTimeout(rc.getSoTimeout());
			if (StringUtils.isNotBlank(rc.getAuth())) {
				shard.setPassword(rc.getAuth());
			}
			shards.add(shard);
		}
		return shards;
	}

	private static GenericObjectPoolConfig buildPoolConfig(RedisConfig rc) {
		GenericObjectPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(rc.getMaxActive());
		poolConfig.setMaxIdle(rc.getMaxActive());
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestWhileIdle(true);

		return poolConfig;
	}

	public static Jedis getRedisClient() {
		return default_pool.getResource();
	}

	public static Jedis getRateLimitRedisClient() {
		if (ratelimit_pool == null)
			return default_pool.getResource();
		return ratelimit_pool.getResource();
	}

	public static void main(String[] args) throws Exception {
		Jedis rc = getRedisClient();
		if (rc == null) {
			System.out.println("get redis client error");
		}
	}
}

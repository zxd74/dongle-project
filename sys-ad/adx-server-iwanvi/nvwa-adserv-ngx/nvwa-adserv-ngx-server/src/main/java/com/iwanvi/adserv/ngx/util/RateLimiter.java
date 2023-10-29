/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * 基于redis的分布式竞价速率控制器
 * 
 * @author wangwp
 */
public class RateLimiter {
	private static final Logger LOGGER = LoggerFactory.getLogger(RateLimiter.class);

	static final String LUA_RATELIMITER = IOUtils.toString(ClassLoaderUtils.getStream("lua/ratelimit.lua"));

	private int timeInterval = 1;
	private String stringTimeInterval;
	private int limit;
	private String luaSha1;
	private final String bucket;

	private RateLimiter(int limit, String bucket, int timeInterval) {
		this.limit = limit;
		this.bucket = bucket;
		this.timeInterval = timeInterval;
		this.stringTimeInterval = String.valueOf(timeInterval);

		BiddingRateLimiterRegistry.register(this);
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getRedisClient();
			luaSha1 = jedis.scriptLoad(LUA_RATELIMITER);
		} catch (Throwable ex) {
			LOGGER.error("", ex);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public static RateLimiter create(int limit, String bucket) {
		return new RateLimiter(limit, bucket, 1);
	}

	public static RateLimiter create(int permits, String bucket, int timeInterval) {
		return new RateLimiter(permits, bucket, timeInterval);
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
		this.stringTimeInterval = String.valueOf(timeInterval);
	}

	public int getTimeInterval() {
		return this.timeInterval;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getBucket() {
		return this.bucket;
	}

	public int getLimit() {
		return this.limit;
	}

	public boolean tryAcquire() {
		Jedis redis = null;
		Object evalResult = null;
		try {
			redis = RedisUtils.getRateLimitRedisClient();

			if (StringUtils.isBlank(luaSha1)) {
				LOGGER.info("==lua script not cached, first load it==");
				luaSha1 = redis.scriptLoad(LUA_RATELIMITER);
				evalResult = redis.evalsha(luaSha1, 1, bucket, stringTimeInterval, String.valueOf(limit));
			} else {
				evalResult = redis.evalsha(luaSha1, 1, bucket, stringTimeInterval, String.valueOf(limit));
			}

			if (evalResult != null && (Long) evalResult == 1) {
				return false;
			}
		} catch (Throwable ex) {
			LOGGER.error("==ratelimit redis error==, cause: {}", ex.getMessage());
		} finally {
			try {
				if (redis != null) {
					redis.close();
				}
			} catch (Exception ex) {
				// ignore this exception
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		Jedis redis = new Jedis("123.56.87.133", 7192);
		redis.auth("cfplhyszgyfjch");

		String sha1 = redis.scriptLoad(LUA_RATELIMITER);
		System.out.println(sha1);

		for (int i = 0; i < 100; i++) {
			long evalResult = (Long) redis.evalsha(sha1, 1, "minerva:ratelimiter:5000", "1");
			System.out.println(evalResult);
		}
		redis.close();
	}
}

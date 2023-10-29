package com.iwanvi.nvwa.common.redis;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class ShardedJedisUtils {

	private static final Logger LOG = LoggerFactory.getLogger("ShardedJedisUtils");
	
	private static ShardedJedisUtils instance = null;
	
	private static final int REDIS_DB = PropertyGetter.getInt("redis.db", 7);
	private static final int REDIS_TIMEOUT = PropertyGetter.getInt("redis.timeout", 3000);
	private static final String REDIS_PASSWORD = PropertyGetter.getString("redis.password");
	private static final String REDIS_SERVERS = PropertyGetter.getString("redis.servers");

	private static final String REDIS_SERVERS_URI = "redis://:{0}@{1}/{2}";
	
	private static ShardedJedisPool pool = null;
	
	private ShardedJedisUtils(int db){
		init(db);
	}

	private void init(int db){
		try {
			LOG.info("ShardedJedisUtils init.");
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(500);
			config.setMaxIdle(100);
			config.setTestOnBorrow(false);
			config.setTimeBetweenEvictionRunsMillis(30000);
			config.setMinEvictableIdleTimeMillis(30000);
			config.setMaxWaitMillis(1000);
			
			List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
			String[] array = REDIS_SERVERS.split(Constants.SIGN_COMMA);
			JedisShardInfo jedisShardInfo = null;
			String host = StringUtils.EMPTY;
			for (int i = 0; i < array.length; i++) {
				host = StringUtils.buildString(REDIS_SERVERS_URI, REDIS_PASSWORD, array[i], db);
				
				jedisShardInfo = new JedisShardInfo(host);
				jedisShardInfo.setPassword(REDIS_PASSWORD);
				jedisShardInfo.setConnectionTimeout(REDIS_TIMEOUT);
				jedisShardInfo.setSoTimeout(REDIS_TIMEOUT);
				
				list.add(jedisShardInfo);
			}
			
			pool = new ShardedJedisPool(config, list);
		} catch (Exception e) {
			LOG.error("init error. ", e);
		}
	}
	
	public static ShardedJedisUtils getInstance(){
		if(instance == null){
			instance = new ShardedJedisUtils(REDIS_DB);
		}
		return instance;
	}
	
	public static ShardedJedisUtils getInstance(Integer db){
		if(db == null){
			db = Constants.INTEGER_0;
		}
		if(instance == null){
			instance = new ShardedJedisUtils(db);
		}
		return instance;
	}
	
	public void incr(String key, int seconds) {
		ShardedJedis shardedJedis = pool.getResource();
		try {
			if (shardedJedis == null) {
				return;
			}
			long value = shardedJedis.incr(key);
			if(value == Constants.INTEGER_1){
				shardedJedis.expire(key, seconds);
			}
			String host = shardedJedis.getShardInfo(key).getHost();
			LOG.info("incr host: {}, key: {}", host, key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shardedJedis.close();
		}
	}
	
	public void set(String key, String value) {
		ShardedJedis shardedJedis = pool.getResource();
		try {
			if (shardedJedis == null) {
				return;
			}
			shardedJedis.set(key, value);
			
			String host = shardedJedis.getShardInfo(key).getHost();
			LOG.info("set host: {}, key: {}", host, key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shardedJedis.close();
		}
	}
	
	public void zincrby(String key, String value, double score) {
		ShardedJedis shardedJedis = pool.getResource();
		try {
			if (shardedJedis == null) {
				return;
			}
			shardedJedis.zincrby(key, score, value);
			String host = shardedJedis.getShardInfo(key).getHost();
			LOG.info("set host: {}, key: {}", host, key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shardedJedis.close();
		}
	}
	
	public long zcard(String key) {
		long result = 0l;
		ShardedJedis shardedJedis = pool.getResource();
		try {
			if (shardedJedis == null) {
				return result;
			}
			result = shardedJedis.zcard(key);
			String host = shardedJedis.getShardInfo(key).getHost();
			LOG.info("set host: {}, key: {}", host, key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shardedJedis.close();
		}
		return result;
	}
	
	public String get(String key){
		String result = StringUtils.EMPTY;
		ShardedJedis shardedJedis = pool.getResource();
		try {
			if (shardedJedis == null) {
				return result;
			}
			result = shardedJedis.get(key);
			String host = shardedJedis.getShardInfo(key).getHost();
			LOG.info("get host: {}, key: {}", host, key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shardedJedis.close();
		}
		return result;
	}
	
	public void hset(String key, String field, String value, Integer expired) {
		ShardedJedis shardedJedis = pool.getResource();
		try {
			if (shardedJedis == null) {
				return;
			}
			shardedJedis.hset(key, field, value);
			if(expired != null){
				shardedJedis.expire(key, expired);
			}
			
			String host = shardedJedis.getShardInfo(key).getHost();
			int port = shardedJedis.getShardInfo(key).getPort();
			LOG.info("hset. host: {}:{}, key: {}", host, port, key);
		} catch (Exception e) {
			LOG.error("hset error.", e);
		} finally {
			shardedJedis.close();
		}
	}
	
	public String hget(String key, String field){
		String result = StringUtils.EMPTY;
		ShardedJedis shardedJedis = pool.getResource();
		try {
			if (shardedJedis == null) {
				return result;
			}
			result = shardedJedis.hget(key, field);
			String host = shardedJedis.getShardInfo(key).getHost();
			int port = shardedJedis.getShardInfo(key).getPort();
			LOG.info("hget. host: {}:{}, key: {}", host, port, key);
		} catch (Exception e) {
			LOG.error("hget error.", e);
		} finally {
			shardedJedis.close();
		}
		return result;
	}
	
	public Long incrR(String key, int seconds) {
		ShardedJedis shardedJedis = pool.getResource();
		try {
			if (shardedJedis == null) {
				return null;
			}
			long value = shardedJedis.incr(key);
			if(value == Constants.INTEGER_1){
				shardedJedis.expire(key, seconds);
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shardedJedis.close();
		}
		return null;
	}
}

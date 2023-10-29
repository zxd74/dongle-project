package com.iwanvi.nvwa.common.redis.server.impl;

import com.iwanvi.nvwa.common.redis.server.RedisDao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository("redisDao")
public class RedisDaoImpl implements RedisDao {

	/**
	 * ValueOperations：简单K-V操作 SetOperations：set类型数据操作 ZSetOperations：zset类型数据操作
	 * HashOperations：针对map类型的数据操作 ListOperations：针对list类型的数据操作
	 */

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valOps;

	@Resource(name = "redisTemplate")
	private SetOperations<String, String> setOps;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOps;

	@Resource(name="redisTemplate")
    private ZSetOperations<String, String> zsetOps;
	
	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Long pfAdd(String key, String...values) {
		return redisTemplate.opsForHyperLogLog().add(key, values);
	}

	@Override
	public Long pfCont(String key) {
		return redisTemplate.opsForHyperLogLog().size(key);
	}

	@Override
	public Set<String> getZSetPaged(String key, long start, long end){
		return zsetOps.range(key, start, end);
	}

	@Override
	public boolean zsetAdd(String key, String value, double score){
		return zsetOps.add(key, value, score);
	}

	@Override
	public Long zsetSize(String key){
		return zsetOps.size(key);
	}
	
	@Override
	public void del(String key) {
		valOps.getOperations().delete(key);
	}

	@Override
	public boolean hasKey(String key) {
		return valOps.getOperations().hasKey(key);
	}

	@Override
	public void incr(String key) {
		valOps.increment(key, 1L);
	}

	@Override
	public void incr(String key, long value) {
		valOps.increment(key, value);
	}

	@Override
	public void set(String key, String value, long expired) {
		valOps.set(key, value, expired, TimeUnit.SECONDS);
	}
	
	@Override
	public void set(String key, String value) {
		valOps.set(key, value);
	}

	@Override
	public String get(String key) {
		return valOps.get(key);
	}
	
	@Override
	public List<String> multiGet(Collection<String> keys) {
		return valOps.multiGet(keys);
	}

	@Override
	public Set<String> getKeys(String pattern) {
		return valOps.getOperations().keys(pattern);
	}

	@Override
	public Long getIncr(String key) {
		Object value = valOps.get(key);
		if(value == null){
			return 0L;
		}
		return Long.valueOf(valOps.get(key).toString());
	}

	@Override
	public void setAdd(String key, String value) {
		setOps.add(key, value);
	}

	@Override
	public boolean isSetMember(String key, String value) {
		return setOps.isMember(key, value);
	}
	
	@Override
	public long setRemove(String key, String value){
		return setOps.remove(key, value);
	}

	@Override
	public Long setSize(String key) {
		return setOps.size(key);
	}
	
	public void hashAdd(String key, String hashKey, String value) {
		hashOps.put(key, hashKey, value);
	}
	
	public void hashRemove(String key, String hashKey){
		hashOps.delete(key, hashKey);
	}
	
	public boolean isHashKey(String key, String hashKey){
		return hashOps.hasKey(key, hashKey);
	}
	
	public String getHashValue(String key, String hashKey){
		return hashOps.get(key, hashKey);
	}

	@Override
	public Long hashSize(String key) {
		return hashOps.size(key);
	}

	@Override
	public void leftPush(String key, String value) {
		listOps.leftPush(key, value);
	}

	@Override
	public String rightPop(String key) {
		return listOps.rightPop(key);
	}
	
	@Override
	public Long listSize(String key){
		return listOps.size(key);
	}

	@Override
	public Set<String> getMembers(String key) {
		return setOps.members(key);
	}

	@Override
	public Set<String> setMembers(String key) {
		return setOps.members(key);
	}
	
	@Override
	public long setRemove(String key,Object...values){
		return setOps.remove(key, values);
	}
	
	@Override
	public void leftPushAll(String key, String... values) {
		listOps.leftPushAll(key, values);
	}
	
	@Override
	public List<String> range(String key, long start, long end) {
		return listOps.range(key, start, end);
	}
	
	@Override
	public void pub(String channel, String message){
		redisTemplate.convertAndSend(channel, message);
	}
	
	public Long hashIncr(String key, String hashKey) {
		return hashOps.increment(key, hashKey, 1l);
	}
	public Long hashIncr(String key, String hashKey, long delta) {
		return hashOps.increment(key, hashKey, delta);
	}
	public Set<String> hashKeys(String key){
		return hashOps.keys(key);
	}
	public List<String> hashMultiGet(String key, List<String> hashKeys) {
		return hashOps.multiGet(key, hashKeys);
	}
	
	public String execute(String lua, List<String> keys, List<String> args) {
		return (String)redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                Object nativeConnection = connection.getNativeConnection();
                if (nativeConnection instanceof JedisCluster) {// 集群
                    return (String) ((JedisCluster) nativeConnection).eval(lua, keys, args);
                } else if (nativeConnection instanceof Jedis) {// 单点
                    return (String) ((Jedis) nativeConnection).eval(lua, keys, args);
                }
                return null;
            }
        });
	}
	
	public void expire(String key, long timeout, TimeUnit unit) {
		redisTemplate.expire(key, timeout, unit);
	}
}

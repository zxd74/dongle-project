package com.iwanvi.nvwa.common.redis.server;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisDao {

	void del(String key);

	boolean hasKey(String key);
	
	void incr(String key);

	void incr(String key, long value);
	/**
	 * 
	 * @param key
	 * @param value
	 * @param expired TimeUnit.SECONDS
	 */
	void set(String key, String value, long expired);
	
	void set(String key, String value);
	
	String get(String key);

	List<String> multiGet(Collection<String> keyList);
	
	Set<String> getKeys(String pattern);

	Long getIncr(String key);
	
	void setAdd(String key, String value);
	
	boolean isSetMember(String key, String value);
	
	long setRemove(String key, String value);

	long setRemove(String key, Object... values);
	
	Long setSize(String key);
	
	void hashAdd(String key, String hashKey, String value);
	
	void hashRemove(String key, String hashKey);
	
	boolean isHashKey(String key, String hashKey);
	
	String getHashValue(String key, String hashKey);
	
	Long hashSize(String key);

	Long hashIncr(String key, String hashKey);
	
	Long hashIncr(String key, String hashKey, long delta);

	Set<String> hashKeys(String key);
	
	List<String> hashMultiGet(String key, List<String> hashKeys);
	
	Set<String> getZSetPaged(String key, long start, long end);
	
	boolean zsetAdd(String key, String value, double score);
	
	Long zsetSize(String key);
	
	void leftPush(String key, String value);
	
	void leftPushAll(String key, String... values);
	
	String rightPop(String key);
	
	Long listSize(String key);
	
	List<String> range(String key, long start, long end);

	Set<String> getMembers(String key);

	void pub(String channel, String message);

	Set<String> setMembers(String key);
	
	Long pfAdd(String key, String...values);
	
	Long pfCont(String key);
	
	String execute(String lua, List<String> keys, List<String> args);
	
	void expire(String key, long timeout, TimeUnit unit);
}

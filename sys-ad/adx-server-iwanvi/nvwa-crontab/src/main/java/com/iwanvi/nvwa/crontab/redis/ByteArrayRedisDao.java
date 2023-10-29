package com.iwanvi.nvwa.crontab.redis;

public interface ByteArrayRedisDao {

	byte[] get(String key);

	void set(String key, byte[] value);

	void leftPush(String key, byte[] value);

	byte[] rightPop(String key);

	void setWithNeverExpired(String key, byte[] value);
	
	void setWithExpiredByDays(String key, byte[] value, int expiredDays);
}

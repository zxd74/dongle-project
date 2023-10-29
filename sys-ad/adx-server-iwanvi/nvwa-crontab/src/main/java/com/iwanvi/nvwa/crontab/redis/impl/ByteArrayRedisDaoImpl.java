package com.iwanvi.nvwa.crontab.redis.impl;

import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Repository("byteArrayRedisDao")
public class ByteArrayRedisDaoImpl implements ByteArrayRedisDao {

	@Resource(name = "redisTemplateType")
	private ValueOperations<String, byte[]> valOps;

	@Resource(name = "redisTemplateType")
	private ListOperations<String, byte[]> listOps;

	@Override
	public byte[] get(String key) {
		byte[] result = null;
		try {
			result = valOps.get(key);
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public void set(String key, byte[] value) {
		try {
			valOps.set(key, value, 1, TimeUnit.HOURS);
		} catch (Exception e) {
		}
	}

	@Override
	public void leftPush(String key, byte[] value) {
		listOps.leftPush(key, value);
	}

	@Override
	public byte[] rightPop(String key) {
		return listOps.rightPop(key);
	}

	@Override
	public void setWithNeverExpired(String key, byte[] value) {
		try {
			valOps.set(key, value);
		} catch (Exception ex) {
			// DO NOTHING
		}
	}

	public void setWithExpiredByDays(String key, byte[] value, int expiredDays) {
		try {
			valOps.set(key, value, expiredDays, TimeUnit.DAYS);
		}catch(Exception ex) {
			//DO NOTHING
		}
	}
}

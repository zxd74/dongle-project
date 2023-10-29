/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import static com.iwanvi.adserv.ngx.util.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotsearch.hppc.ObjectLongHashMap;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author oyy
 */
public class CpaCappingDataHolder {
	private static final Logger LOG = LoggerFactory.getLogger(CpaCappingDataHolder.class);

	private static final List<Integer> cpa_capping_ads = new ArrayList<>();
	public static final ObjectLongHashMap<String> ad_app_cpa_data_holder = new ObjectLongHashMap<>();

	static {
		ThreadPools.schedule(new CpaCappingDataFetcher(), 30, 10, TimeUnit.SECONDS);
	}

	private static class CpaCappingDataFetcher implements Runnable {
		@Override
		public void run() {
			RedisTemplate.execute(new RedisCallback() {
				@Override
				public Object doRedis(Jedis jedis) {
					fetchAppCpaData(jedis);
					return null;
				}
			});
		}

		void fetchAppCpaData(Jedis jedis) {
			try {
				for (Integer adUnitId : cpa_capping_ads) {
					Set<String> targetAppIds = jedis.smembers(String.format(APP_IDS, adUnitId));
					if (targetAppIds == null || targetAppIds.isEmpty()) {
						continue;
					}
					List<String> appCpaDataKeys = new ArrayList<>(targetAppIds.size());
					for (String targetAppId : targetAppIds) {
						appCpaDataKeys.add(String.format(APP_CONSUME, adUnitId, targetAppId));
						appCpaDataKeys.add(String.format(APP_ACTIVE, adUnitId, targetAppId));
					}

					List<String> appCpaDataValues = jedis.mget(appCpaDataKeys.toArray(new String[] {}));
					for (int i = 0; i < appCpaDataValues.size(); i++) {
						long value = NumberUtils.toLong(appCpaDataValues.get(i));
						ad_app_cpa_data_holder.put(appCpaDataKeys.get(i), value);
					}
				}
			} catch (Exception ex) {
				LOG.error("", ex);
			}
		}
	}

	public static long getCost(String appId, int adUnitId) {
		return ad_app_cpa_data_holder.get(String.format(APP_CONSUME, adUnitId, appId)) / 1000;
	}

	public static long getActiveNums(String appId, int adUnitId) {
		return ad_app_cpa_data_holder.get(String.format(APP_ACTIVE, adUnitId, appId));
	}

	public static void clear() {
		cpa_capping_ads.clear();
		ad_app_cpa_data_holder.clear();
	}

	public static void registerCpaAdunit(AdUnit adUnit) {
		if (!adUnit.hasCpaCapping() || !adUnit.getCpaCapping().getCpaCapping()) {
			return;
		}

		if (!cpa_capping_ads.contains(adUnit.getUnitId()))
			cpa_capping_ads.add(adUnit.getUnitId());
	}

	public static void unregisterCpaAdunit(int adUnitId) {
		cpa_capping_ads.remove(Integer.valueOf(adUnitId));
	}
	
	public static int cpaCappingAdCount(){
		return cpa_capping_ads.size();
	}
}

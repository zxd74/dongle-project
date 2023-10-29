/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Creative;
import com.iwanvi.nvwa.proto.AdModelsProto.SBDStat;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.DeliveryMode;

import redis.clients.jedis.Jedis;

/**
 * 所有平滑预算投放的单元投放统计信息管理
 * 
 * @author wangwp
 */
public class SBDStatHolder {
	private static final Logger LOGGER = LoggerFactory.getLogger(SBDStatHolder.class);

	private static final ConcurrentMap<Integer, SBDStat.Builder> _SBD_AD_STAT_HOLDER = new ConcurrentHashMap<>();

	private static final ScheduledExecutorService _SCHEDULER = Executors.newScheduledThreadPool(1);

	private static final Repository repo = RepositoryFactory.getRepository();

	static {
		_SCHEDULER.scheduleAtFixedRate(new AdUnitStatTask(), 30, 30, TimeUnit.SECONDS);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				_SCHEDULER.shutdownNow();
			}
		}));
	}

	static class AdUnitStatTask implements Runnable {
		@Override
		public void run() {
			try {
				Set<Integer> adUnitIds = _SBD_AD_STAT_HOLDER.keySet();
				for (Integer adUnitId : adUnitIds) {
					buildSBDStatBuilder(adUnitId);
					RepositoryFactory.getRepository().saveOrUpdateSBDStat(_SBD_AD_STAT_HOLDER.get(adUnitId).build());
					BiddingRateLimiterFactory
							.createOrUpdateRateLimiter(RepositoryFactory.getRepository().loadAdUnit(adUnitId));
				}
			} catch (Throwable ex) {
				LOGGER.error("", ex);
			}
		}
	}

	public static void buildSBDStatBuilder(Integer adUnitId) {
		AdUnit adUnit = RepositoryFactory.getRepository().loadAdUnit(adUnitId);
		if (adUnit == null) {
			LOGGER.warn("单元为空, adUnitId: {}", adUnitId);
			return;
		}

		if (adUnit.getCreativesCount() == 0) {
			LOGGER.warn("单元下面没有创意, adUnitId: {}", adUnitId);
			return;
		}
		String now = DateFormatUtils.format(new Date(), Constants.DEFAULT_DATE_FORMAT);
		final List<String> adImpRedisKeys = new ArrayList<>();
		final List<String> adBidRedisKeys = new ArrayList<>();

		for (Creative creative : adUnit.getCreativesList()) {
			if (adUnit.getCostType() == CostType.kCpfc) {
				adImpRedisKeys.add(String.format(Constants.REDIS_KEY_PREFIX_AD_CLK, creative.getCreativeId(), now));
			} else {
				adImpRedisKeys.add(String.format(Constants.REDIS_KEY_PREFIX_AD_IMP, creative.getCreativeId(), now));
			}
			adBidRedisKeys.add(String.format(Constants.REDIS_KEY_PREFIX_AD_BID, creative.getCreativeId(), now));
		}

		Jedis redis = null;
		try {
			redis = RedisUtils.getRedisClient();
			List<String> impResults = redis.mget(adImpRedisKeys.toArray(new String[] {}));
			List<String> bidResults = redis.mget(adBidRedisKeys.toArray(new String[] {}));

			SBDStat.Builder sbdStatBuilder = _SBD_AD_STAT_HOLDER.get(adUnitId);

			if (sbdStatBuilder == null) {
				sbdStatBuilder = SBDStat.newBuilder().setUnitId(adUnitId);
				_SBD_AD_STAT_HOLDER.put(adUnitId, sbdStatBuilder);
			}
			int imps = 0;
			int bids = 0;

			for (String adImpCount : impResults) {
				imps += NumberUtils.toInt(adImpCount, 0);
			}

			for (String adBidCount : bidResults) {
				bids += NumberUtils.toInt(adBidCount, 0);
			}

			long halfHours = adUnit.getTimeTarget().getHalfHours();
			BitSet bits = BitSetUtils.long2BitSet(halfHours);

			// 清空投放时间字段
			sbdStatBuilder.clearDeliveryHours();
			for (int i = 0; i < bits.length() / 2; i++) {
				if (bits.get(i * 2)) {
					sbdStatBuilder.addDeliveryHours(i);
				}
			}
			sbdStatBuilder.setBiddingCount(bids).setImpCount(imps);
		} catch (Exception ex) {
			LOGGER.error("", ex);
		} finally {
			try {
				if (redis != null)
					redis.close();
			} catch (Exception ex) {
				// ignore this exception
			}
		}
	}

	public static int getImpCount(AdUnit adUnit) {
		SBDStat sbdStat = repo.loadSBDStat(adUnit.getUnitId());
		return sbdStat == null ? 1 : sbdStat.getImpCount();
	}

	/**
	 * 计算单元的曝光率, 计算方式: 根据截止到目前为止的单元的曝光数/总竞价数, 如果还没有曝光的话 采用默认的曝光率0.9
	 * 
	 * @param adUnit
	 * @return
	 */
	public static float getImpRate(AdUnit adUnit) {
		SBDStat sbdStat = repo.loadSBDStat(adUnit.getUnitId());

		// 如果广告曝光为0或者参与竞价数为0的话都直接返回默认的曝光率
		if (sbdStat == null || sbdStat.getImpCount() == Constants.INT_ZERO
				|| sbdStat.getBiddingCount() == Constants.INT_ZERO) {
			return Constants.COLDSTART_IMP_RATE;
		}

		int imps = sbdStat.getImpCount();
		int bids = sbdStat.getBiddingCount();

		float impRate = (float) imps / bids;
		return impRate;
	}

	public static void clear() {
		_SBD_AD_STAT_HOLDER.clear();
	}

	public static void registerSBDAdUnit(AdUnit adUnit) {
		if (adUnit.getDeliveryMode() != DeliveryMode.kSmoothBudgetDelivery) {
			return;
		}

		SBDStat.Builder builder = SBDStat.newBuilder().setUnitId(adUnit.getUnitId());
		_SBD_AD_STAT_HOLDER.putIfAbsent(adUnit.getUnitId(), builder);
	}

	public static void unregisterSBDAdUnit(int adUnitId) {
		_SBD_AD_STAT_HOLDER.remove(adUnitId);
	}

	public static SBDStat getSBDStat(int unitId) {
		return RepositoryFactory.getRepository().loadSBDStat(unitId);
	}
}

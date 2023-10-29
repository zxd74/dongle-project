/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import static com.iwanvi.adserv.ngx.util.Constants.*;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.SBDStat;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.DeliveryMode;
import com.iwanvi.nvwa.proto.CommonProto.PutType;

import redis.clients.jedis.Jedis;

/**
 * 竞价速率控制器工厂
 * 
 * @author wangwp
 */
public class BiddingRateLimiterFactory {
	static final Logger LOG = LoggerFactory.getLogger(BiddingRateLimiterFactory.class);

	public static synchronized RateLimiter createOrUpdateRateLimiter(AdUnit adUnit) {
		if (adUnit == null)
			return null;
		if (adUnit.getDeliveryMode() != DeliveryMode.kSmoothBudgetDelivery) {
			return null;
		}

		// 如果cpc为0且不存在投放类型的话不支持匀速投放, 或者在rtb投放情况下，没有设置cpc也不支持匀速投放
		if ((adUnit.getCpc() == 0 && !adUnit.hasPutType())
				|| (adUnit.getCpc() == 0 && adUnit.getPutType() == PutType.kPutTypeRtb)) {
			return null;
		}
		// cpt计费情况下不支持匀速投放
		if (adUnit.getCostType() == CostType.kCpt) {
			return null;
		}
		// minerva:ratelimiter:单元id
		String bucket = String.format("%s%d", REDIS_KEY_PREFIX_RATE_LIMITER, adUnit.getUnitId());
		LOG.info("ratelimiter bucket: {}, ad_unit's id: {}", bucket, adUnit.getUnitId());
		RateLimiter rateLimiter = BiddingRateLimiterRegistry.getRateLimiter(bucket);

		// 如果竞价速率控制器不存在的话则创建一个新的限速控制器
		if (rateLimiter == null) {
			LOG.info("create bidding ratelimiter, ad_unit's id: {}", adUnit.getUnitId());
			SBDStatHolder.registerSBDAdUnit(adUnit);
			rateLimiter = createBiddingRateLimiter(adUnit, bucket);
		} else {
			updateBiddingRateLimiter(adUnit, bucket);
		}
		return rateLimiter;
	}

	public static double calBiddingRate(AdUnit adUnit) {
		int impressions = getRemainingImpressions(adUnit);
		float impRate = SBDStatHolder.getImpRate(adUnit);
		int bids = (int) Math.ceil(impressions / impRate);

		// 获得从当前时间开始到投放结束的总投放时间
		int totalDeliveryTimes = getTotalDeliveryTimes(adUnit);
		double bidRate = bids / (totalDeliveryTimes * 1.0d);

		LOG.info("impressions: {}, impRate: {}, bids: {}, bidRate: {}, adUnitId: {},totalDeliveryTimes: {}",
				impressions, impRate, bids, bidRate, adUnit.getUnitId(), totalDeliveryTimes);
		return bidRate;
	}

	private static int getRemainingImpressions(AdUnit adUnit) {
		int impressions = 0;
		if (Constants.COST_TYPES_RTB.contains(adUnit.getCostType())) {
			int consume = getAdUnitConsume(adUnit);
			int cpc = adUnit.getCpc() == 0 ? 1 : adUnit.getCpc();
			impressions = ((adUnit.getLimit() - consume) * 1000) / cpc;
			return impressions;
		}

		CostType costType = adUnit.getCostType();
		if (CostType.kCpfm == costType || CostType.kCpfc == costType) {
			return adUnit.getLimit() - SBDStatHolder.getImpCount(adUnit);
		}
		return 0;
	}

	private static int getTotalDeliveryTimes(AdUnit adUnit) {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		int seconds = cal.get(Calendar.SECOND);

		SBDStat stat = SBDStatHolder.getSBDStat(adUnit.getUnitId());
		if (stat == null) {
			LOG.warn("SBDStat is null, adUnit's id: {}", adUnit.getUnitId());
			return adUnit.getTimeSlots() * SECONDS_ONE_HOUR;
		}

		long halfHours = adUnit.getTimeTarget().getHalfHours();
		BitSet bits = BitSetUtils.long2BitSet(halfHours);

		List<Integer> deliveryHours = new ArrayList<Integer>(bits.length());
		for (int i = 0; i < bits.length() / 2; i++) {
			if (bits.get(i * 2)) {
				deliveryHours.add(i);
			}
		}

		int index = 0;
		for (int i = 0; i < deliveryHours.size(); i++) {
			if (deliveryHours.get(i) >= hour) {
				index = i;
				break;
			}
		}

		int totalDeliveryTimes = adUnit.getTimeSlots() * SECONDS_ONE_HOUR - (index * SECONDS_ONE_HOUR)
				- (minutes * SECONDS_ONE_MINUTE + seconds);
		return totalDeliveryTimes;
	}

	private static int getAdUnitConsume(AdUnit adUnit) {
		String now = DateFormatUtils.format(new Date(), DATE_FORMAT_DAY);
		final String unitDayConsumeKey = String.format(REDIS_KEY_PREFIX_AD_CONSUME, adUnit.getUnitId(), now);

		int unitDayConsume = (Integer) RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String consume = jedis.get(unitDayConsumeKey);
				return (int) (NumberUtils.toLong(consume, 0L) / 1000);
			}
		});

		LOG.info("单元消耗： {}, 单元id: {}", unitDayConsume, adUnit.getUnitId());
		return unitDayConsume;
	}

	private static void updateBiddingRateLimiter(AdUnit adUnit, String bucket) {
		RateLimiter rateLimiter = BiddingRateLimiterRegistry.getRateLimiter(bucket);
		int timeInterval = Constants.DEFAULT_RATELIMIT_TIME_INTERVAL;

		double bidRate = calBiddingRate(adUnit);
		if (bidRate < Constants.BID_RATE_MIN && bidRate > Constants.INT_ZERO) {
			timeInterval = (int) Math.round(1 / bidRate);
			bidRate = Constants.BID_RATE_MIN;
		}

		int limit = (int) Math.round(bidRate);

		LOG.info("update bidRate for adUnit: {}, timeInterval: {},bidRate: {}, limit: {}", adUnit.getUnitId(),
				timeInterval, bidRate, limit);
		rateLimiter.setLimit(limit);
		rateLimiter.setTimeInterval(timeInterval);
	}

	private static RateLimiter createBiddingRateLimiter(AdUnit adUnit, String bucket) {
		double bidRate = calBiddingRate(adUnit);
		// 如果平均出价速度低于每秒1个的时候，需要计算缓存失效时间(即多少秒1个)
		int timeInterval = Constants.DEFAULT_RATELIMIT_TIME_INTERVAL;
		if (bidRate < Constants.BID_RATE_MIN && bidRate > Constants.INT_ZERO) {
			timeInterval = (int) Math.round(1 / bidRate);
			bidRate = Constants.BID_RATE_MIN;
		}

		int limit = (int) Math.round(bidRate);
		LOG.info("create biddingRateLimiter, adUnit: {}, timeInterval: {}, limit: {}", adUnit.getUnitId(), timeInterval,
				limit);
		return RateLimiter.create(limit, bucket, timeInterval);
	}
}

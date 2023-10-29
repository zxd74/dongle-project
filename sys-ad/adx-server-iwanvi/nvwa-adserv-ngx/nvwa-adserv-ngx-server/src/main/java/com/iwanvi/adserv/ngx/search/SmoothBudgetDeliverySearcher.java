/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import static com.iwanvi.adserv.ngx.util.Constants.REDIS_KEY_PREFIX_RATE_LIMITER;
import static com.iwanvi.adserv.ngx.util.MinervaStatHolder.statInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.util.RateLimiter;
import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.BiddingRateLimiterFactory;
import com.iwanvi.adserv.ngx.util.BiddingRateLimiterRegistry;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.DeliveryMode;

/**
 * 广告匀速投放控制器
 * 
 * @author wangwp
 */
public class SmoothBudgetDeliverySearcher extends AbstractSearcher {
	private static final Logger LOG = LoggerFactory.getLogger(SmoothBudgetDeliverySearcher.class.getSimpleName());

	public SmoothBudgetDeliverySearcher() {
	}

	@Override
	public void doSearch(BiddingContext context) {
		long start = System.currentTimeMillis();
		boolean isTest = context.getBiddingReq().getIsTest();
		// 如果是测试请求或者debug请求, 则关闭匀速花费相关功能
		if (isTest || context.isDebug()) {
			return;
		}
		if (context.isSkipSmoothBudgetDelivery()) {
			return;
		}

		List<SearchItem.Builder> searchResults = context.getSearchResults();
		List<SearchItem.Builder> filteredResults = new ArrayList<>(searchResults.size());

		try {
			for (int i = 0, size = searchResults.size(); i < size; i++) {
				SearchItem.Builder searchItem = searchResults.get(i);
				AdUnit adUnit = searchItem.getAdUnit();

				if (unsupportedSmoothBudgetDelivery(adUnit)) {
					filteredResults.add(searchItem);
					continue;
				}
				statInfo().smoothBudgetDeliveryInvokeCount.incrementAndGet();
				int ad_unit_id = adUnit.getUnitId();

				RateLimiter rateLimiter = BiddingRateLimiterRegistry
						.getRateLimiter(String.format("%s%d", REDIS_KEY_PREFIX_RATE_LIMITER, ad_unit_id));
				if (rateLimiter == null) {
					LOG.warn("The bidding ratelimiter not exists, create it, adUnit's id: {}", ad_unit_id);
					rateLimiter = BiddingRateLimiterFactory.createOrUpdateRateLimiter(adUnit);
					if (rateLimiter == null)
						continue;
				}

				if (rateLimiter.tryAcquire()) {
					filteredResults.add(searchItem);
					continue;
				}

				Calendar cal = Calendar.getInstance();
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				int minutes = cal.get(Calendar.MINUTE);

				// 获得匀速投放时间段最后一个小时，整体投放时间的1/48时间不走匀速正常投放, 防止预算无法花完
				int lastHour = adUnit.getDeliveryHours(adUnit.getDeliveryHoursCount() - 1);
				int notSmoothMinutes = 60 - Math.round(adUnit.getDeliveryHoursCount() * 60 / 48.0F);

				if (hour == lastHour && minutes >= notSmoothMinutes) {
					filteredResults.add(searchItem);
				}
			}
			searchResults = null; // GC helper
			context.setSearchResults(filteredResults);
		} finally {
			long elapsed = System.currentTimeMillis() - start;
			statInfo().smoothBudgetDeliveryCostTime.addAndGet(elapsed);
		}
	}

	private boolean unsupportedSmoothBudgetDelivery(AdUnit adUnit) {
		if (adUnit.getDeliveryMode() != DeliveryMode.kSmoothBudgetDelivery)
			return true;

		if (adUnit.getCostType() == CostType.kCpt)
			return true;

		if (adUnit.getLimit() == 0)
			return true;

		if (Constants.COST_TYPES_RTB.contains(adUnit.getCostType()) && adUnit.getCpc() == 0) {
			return true;
		}
		return false;
	}
}

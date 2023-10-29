/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import static com.iwanvi.adserv.ngx.util.MinervaStatHolder.*;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.repo.ImpRepository;
import com.iwanvi.adserv.ngx.repo.ImpRepositoryFactory;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.MurmurHash;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.CommonProto.FrequencyCapping;
import com.iwanvi.nvwa.proto.CommonProto.FrequencyPeriod;

/**
 * 广告频次控制过滤器实现
 * 
 * @author wangwp
 *
 */
public class FrequencyCappingFilter implements AdFilter {
	static final Logger LOG = LoggerFactory.getLogger(FrequencyCappingFilter.class);

	static final String USER_ID_PATTERN = "%d:%s";

	static final ImpRepository impressionRepository = ImpRepositoryFactory.getRepository();

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit ad) {
		long start = System.nanoTime();
		boolean isTest = ctx.getBiddingReq().getIsTest();
		if (isTest) {
			return false;
		}
		try {
			if (!ad.hasFrequencyCapping())
				return false;

			if (!ad.getFrequencyCapping().getFrequencyCapping())
				return false;

			statInfo().frequencyCappingInvokeCount.incrementAndGet();
			FrequencyCapping frequencyCapping = ad.getFrequencyCapping();

			FrequencyPeriod period = frequencyCapping.getFrequencyPeriod();
			int frequency = frequencyCapping.getFrequency();

			String key = String.format(USER_ID_PATTERN, ad.getUnitId(), ctx.getUserInfo().getMuid().toStringUtf8());
			int user = MurmurHash.hash32(key, (int) Constants.HASHING_SEED);

			int impressions = 0;
			switch (period) {
			case kFrequencyPeriodDay:
				impressions = impressionRepository.getDaily(user);
				break;
			case kFrequencyPeriodWeek:
				impressions = impressionRepository.getWeekly(user);
				break;
			case kFrequencyPeriodMonth:
				impressions = impressionRepository.getMonthly(user);
				break;
			default:
				return false;
			}

			if (impressions >= frequency) {
				statInfo().frequencyCappingFilteredRequests.incrementAndGet();
				//LOG.warn("==超过频次控制限制, userid: {}, frequency: {}==", key, frequency);
				return true;
			}
		} finally {
			long cost = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
			statInfo().frequencyCappingFilterCostTime.addAndGet(cost);
		}
		return false;
	}

}

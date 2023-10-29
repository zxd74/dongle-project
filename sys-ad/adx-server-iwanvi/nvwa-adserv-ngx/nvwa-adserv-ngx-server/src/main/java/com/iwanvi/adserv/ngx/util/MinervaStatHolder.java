/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.iwanvi.adserv.ngx.index.ReverseIndexFactory;
import com.iwanvi.adserv.ngx.repo.ImpRepositoryFactory;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetBiddingCreativesResp.BiddingCreative;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.Stat;
import com.iwanvi.nvwa.proto.CommonProto.DeliveryMode;

/**
 * @author wangwp
 *
 */
public class MinervaStatHolder {
	private static final HashSet<BiddingCreative.Builder> bidding_creatives_set = new HashSet<>();

	private static StatInfo _stat_info = new StatInfo();

	static {
		ExpiredIntIntHashMap expired_signal = new ExpiredIntIntHashMap(new ExpiredCallback() {
			@Override
			public void expired(int key, ExpiredIntIntHashMap expiredMap) {
				_stat_info = new StatInfo();
				expiredMap.put(key, 1, timeout(), TimeUnit.MILLISECONDS);
			}
		});
		expired_signal.put(1, 1, timeout(), TimeUnit.MILLISECONDS);

		// 定时任务，每隔30s清空一次出价创意集合
		ThreadPools.schedule(new Runnable() {
			@Override
			public void run() {
				bidding_creatives_set.clear();
			}
		}, 5, 30, TimeUnit.SECONDS);
	}

	private static long tomorrow() {
		Calendar cal = Calendar.getInstance();

		cal.set(DAY_OF_MONTH, cal.get(DAY_OF_MONTH) + 1);
		cal.set(HOUR_OF_DAY, 0);
		cal.set(MINUTE, 0);
		cal.set(SECOND, 0);
		cal.set(MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	private static long timeout() {
		return tomorrow() - System.currentTimeMillis();
	}

	public static Stat stat() {
		Stat.Builder builder = Stat.newBuilder();

		RepositoryFactory.getRepository().stat(_stat_info);
		ImpRepositoryFactory.getRepository().stat(_stat_info);
		ReverseIndexFactory.getReverseIndex().stat(_stat_info);

		StatInfo stat = _stat_info;

		int avgBiddingCostTime = stat.biddingRequestCount.get() == 0 ? 0
				: (int) (stat.biddingTotalCostTime.get() / stat.biddingRequestCount.get());
		builder.setAvgBiddingCostTime(avgBiddingCostTime);

		builder.setExpiredImpressionQueueSize(stat.expiredImpressionQueueSize);
		builder.setReverseIndexSize(stat.reverseIndexSize.get()).setBiddingRequestCount(stat.biddingRequestCount.get());
		builder.setBiddingTimeoutCount(stat.biddingTimeoutCount.get())
				.setBiddingCostLe5MsCount(stat.biddingCost_le_5msCount.get());
		builder.setBiddingCost510MsCount(stat.biddingCost_5_10msCount.get())
				.setBiddingCost1015MsCount(stat.biddingCost_10_15msCount.get());
		builder.setBiddingCost1520MsCount(stat.biddingCost_15_20msCount.get());
		builder.setBiddingCost2025MsCount(stat.biddingCost_20_25msCount.get());
		builder.setBiddingCost2530MsCount(stat.biddingCost_25_30msCount.get());
		builder.setBiddingCostGt30MsCount(stat.biddingCost_gt_30msCount.get());

		builder.setFrequencyCappingFilteredRequests(stat.frequencyCappingFilteredRequests.get());
		builder.setCpaCappingFilteredRequests(stat.cpaCappingFilteredRequests.get());

		int avgFrequencyCappingFilterCostTime = stat.frequencyCappingInvokeCount.get() == 0 ? 0
				: (int) (stat.frequencyCappingFilterCostTime.get() / stat.frequencyCappingInvokeCount.get());
		builder.setAvgFrequencyCappingFilterCostTime(avgFrequencyCappingFilterCostTime);

		builder.setValidAdunitCount(stat.validAdunitCount);
		builder.setValidAdplanCount(stat.validAdplanCount);
		builder.setValidAdvertiserCount(stat.validAdvertiserCount);
		builder.setValidAgentCount(stat.validAgentCount);
		builder.setValidAgentFloorCount(stat.validAgentFloorCount);
		builder.setReverseIndexValueSize(stat.reverseIndexValuesSize);
		builder.setCpaCappingAdCount(stat.cpaCappingAdCount);
		builder.setFrequencyCappingAdCount(stat.frequencyCappingAdCount);
		builder.setSmoothBudgetDeliveryAdCount(stat.smoothBudgetDeliveryAdCount);
		builder.setInvalidIdfaFilteredRequests(stat.invalidIdfaFilteredRequests.get());
		builder.setSmoothBudgetDeliveryInvokeCount(stat.smoothBudgetDeliveryInvokeCount.get());

		if (stat.smoothBudgetDeliveryInvokeCount.get() == 0) {
			builder.setAvgSmoothBudgetDeliveryCostTime(0);
		} else {
			builder.setAvgSmoothBudgetDeliveryCostTime(
					(int) (stat.smoothBudgetDeliveryCostTime.get() / stat.smoothBudgetDeliveryInvokeCount.get()));
		}

		return builder.build();
	}

	public static StatInfo statInfo() {
		return _stat_info;
	}

	public static void addBiddingCreative(BiddingCreative.Builder bidding_creative) {
		bidding_creatives_set.add(bidding_creative);
	}

	public static List<BiddingCreative> biddingCreatives() {
		List<BiddingCreative> bidding_creatives = new ArrayList<>();

		for (BiddingCreative.Builder builder : bidding_creatives_set) {
			int adunit_id = builder.getUnitId();
			AdUnit adunit = RepositoryFactory.getRepository().loadAdUnit(adunit_id);
			builder.setIsSmoothBudgetDelivery(adunit.getDeliveryMode() == DeliveryMode.kSmoothBudgetDelivery);
			bidding_creatives.add(builder.build());
		}

		return bidding_creatives;
	}

	public static class StatInfo implements Cloneable {
		public AtomicInteger reverseIndexSize = new AtomicInteger();
		public AtomicInteger biddingRequestCount = new AtomicInteger();

		public AtomicInteger biddingTimeoutCount = new AtomicInteger();
		public AtomicInteger biddingCost_le_5msCount = new AtomicInteger();
		public AtomicInteger biddingCost_5_10msCount = new AtomicInteger();
		public AtomicInteger biddingCost_10_15msCount = new AtomicInteger();
		public AtomicInteger biddingCost_15_20msCount = new AtomicInteger();
		public AtomicInteger biddingCost_20_25msCount = new AtomicInteger();
		public AtomicInteger biddingCost_25_30msCount = new AtomicInteger();
		public AtomicInteger biddingCost_gt_30msCount = new AtomicInteger();

		public int avgBiddingCostTime;

		public AtomicInteger frequencyCappingFilteredRequests = new AtomicInteger();
		public AtomicInteger cpaCappingFilteredRequests = new AtomicInteger();
		public AtomicLong frequencyCappingFilterCostTime = new AtomicLong();
		public AtomicLong biddingTotalCostTime = new AtomicLong();
		public AtomicInteger frequencyCappingInvokeCount = new AtomicInteger();
		public AtomicInteger invalidIdfaFilteredRequests = new AtomicInteger();
		public AtomicInteger smoothBudgetDeliveryInvokeCount = new AtomicInteger();
		public AtomicLong smoothBudgetDeliveryCostTime = new AtomicLong();

		public int avgSmoothBudgetDeliveryCostTime;
		public int validAdunitCount;
		public int validAdvertiserCount;
		public int validAdplanCount;
		public int validAgentCount;
		public int validAgentFloorCount;
		public int reverseIndexValuesSize;
		public int smoothBudgetDeliveryAdCount;
		public int cpaCappingAdCount;
		public int frequencyCappingAdCount;
		public int competingKeywordAdCount;
		public int expiredImpressionQueueSize;
	}

	public static void main(String[] args) throws Exception {
		long timeSlotQpsKey = NumberUtils.toLong(DateFormatUtils.format(new Date(), "HHmmss"));
		System.out.println(timeSlotQpsKey);
	}
}

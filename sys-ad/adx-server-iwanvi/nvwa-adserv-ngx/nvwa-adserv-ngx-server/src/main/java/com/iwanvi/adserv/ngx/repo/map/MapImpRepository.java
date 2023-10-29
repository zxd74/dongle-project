/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.repo.map;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carrotsearch.hppc.IntIntHashMap;
import com.iwanvi.adserv.ngx.repo.ImpRepository;
import com.iwanvi.adserv.ngx.util.ExpiredIntIntHashMap;
import com.iwanvi.adserv.ngx.util.MinervaStatHolder.StatInfo;

/**
 * 基于高性能的HPPC框架实现的用户曝光存储器
 * 
 * @author wangwp
 *
 */
public class MapImpRepository implements ImpRepository {
	static final Logger LOG = LoggerFactory.getLogger(MapImpRepository.class);

	private static final ExpiredIntIntHashMap _daily_impression_holder = new ExpiredIntIntHashMap();
	private static final ExpiredIntIntHashMap _weekly_impression_holder = new ExpiredIntIntHashMap();
	private static final ExpiredIntIntHashMap _monthly_impression_holder = new ExpiredIntIntHashMap();

	private static final long DAY_TIMEMILLIS = MILLISECONDS.convert(1, DAYS);
	private static final long WEEK_TIMEMILLIS = MILLISECONDS.convert(7, DAYS);
	private static final long MONTH_TIMEMILLIS = MILLISECONDS.convert(30, DAYS);

	@Override
	public void putDaily(int user, int impressions) {
		_daily_impression_holder.put(user, impressions, DAY_TIMEMILLIS);
	}

	@Override
	public void putWeekly(int user, int impressions) {
		_weekly_impression_holder.put(user, impressions, WEEK_TIMEMILLIS);
	}

	@Override
	public void putMonthly(int user, int impressions) {
		_monthly_impression_holder.put(user, impressions, MONTH_TIMEMILLIS);
	}

	@Override
	public int getDaily(int user) {
		return _daily_impression_holder.get(user);
	}

	@Override
	public int getWeekly(int user) {
		return _weekly_impression_holder.get(user);
	}

	@Override
	public int getMonthly(int user) {
		return _monthly_impression_holder.get(user);
	}

	public static void main(String[] args) throws Exception {
		// System.gc();
		IntIntHashMap ints = new IntIntHashMap(10000000);
		int startFreeMem = (int) (Runtime.getRuntime().freeMemory() / 1000000);
		for (int i = 0; i < 6000000; i++) {
			ints.put(i, i);
		}
		int endFreeMem = (int) (Runtime.getRuntime().freeMemory() / 1000000);
		System.out.println(startFreeMem - endFreeMem);
		// System.gc();

	}

	@Override
	public void stat(StatInfo stat) {
		int impression_total = _daily_impression_holder.size() + _weekly_impression_holder.size()
				+ _monthly_impression_holder.size();
		stat.expiredImpressionQueueSize = impression_total;
	}
}

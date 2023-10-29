/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import java.util.Calendar;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

/**
 * 投放时间段过滤器
 * 
 * @author wangwp
 */
public class TimeAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit adUnit) {
		if (!adUnit.hasTimeTarget()) {
			return false;
		}

		long now = System.currentTimeMillis() / 1000;

		// 如果投放时间不在指定日期范围，则过滤掉
		if (!(now >= adUnit.getTimeTarget().getBeginTime() && now <= adUnit.getTimeTarget().getEndTime())) {
			return true;
		}

		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);

		int halfHour = hour * 2 + minutes / 30;
		if (((adUnit.getTimeTarget().getHalfHours() >> halfHour) & 1) > 0) {
			return false;
		}
		return true;
	}
}

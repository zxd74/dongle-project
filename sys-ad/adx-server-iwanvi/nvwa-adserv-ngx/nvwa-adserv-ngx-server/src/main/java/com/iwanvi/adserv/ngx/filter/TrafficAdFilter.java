/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.TrafficType;

/**
 * 流量定向过滤
 * 
 * @author wangwp
 */
public class TrafficAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit adUnit) {
		if (!adUnit.hasTrafficTarget()) {
			return false;
		}

		UserInfo userInfo = ctx.getBiddingReq().getUserInfo();

		TrafficType trafficType = userInfo.getTrafficType();
		for (int i = 0; i < adUnit.getTrafficTarget().getTrafficCount(); i++) {
			if (trafficType == adUnit.getTrafficTarget().getTraffic(i)) {
				return false;
			}
		}
		return true;
	}

}

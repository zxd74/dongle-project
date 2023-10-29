/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;

/**
 * 广告位过滤器，主要用来过滤同一个媒体相同规格的多个不同广告位以及原生广告
 * 
 * @author wangwp
 */
public class AdPositionAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext context, AdUnit adUnit) {
		PosInfo posInfo = context.getBiddingReq().getPosInfo(0);
		if (!posInfo.hasAdTypeId()) {
			return false;
		}

		String[] adxPosIds = StringUtils.split(adUnit.getAdxAdTypeId(), Constants.SIGN_COMMA);
		for (String adxPosId : adxPosIds) {
			if (adxPosId.equals(posInfo.getAdTypeId())) {
				return false;
			}
		}
		return true;
	}
}

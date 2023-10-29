/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

/**
 * @author wangwp
 *
 */
public class PmpAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit ad) {
		if (!ctx.isPmp()) {
			if (ad.hasPmp())
				return true;
			return false;
		}
		
		// 如果是pmp请求且单元非pmp投放，则滤掉
		if (!ad.hasPmp()) {
			return true;
		}

		if (StringUtils.isBlank(ad.getPmp().getDealId())) {
			return true;
		}

		List<String> dealids = ctx.getPosInfo().getDealidList();
		String adDealid = ad.getPmp().getDealId();
		String[] ad_dealids = StringUtils.split(adDealid, Constants.SIGN_COMMA);

		for (String ad_dealid : ad_dealids) {
			if (dealids.contains(ad_dealid)) {
				return false;
			}
		}
		return true;
	}
}

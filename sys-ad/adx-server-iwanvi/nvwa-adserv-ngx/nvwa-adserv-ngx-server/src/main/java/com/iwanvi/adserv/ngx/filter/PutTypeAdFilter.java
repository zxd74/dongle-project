/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.AdUnitUtils;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.CommonProto.PutType;

/**
 * 
 * @author wangwp
 */
public class PutTypeAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit ad) {
		int putTypeValue = ctx.getUserExtPropertyAsInt(Constants.PUT_TYPE_EXT_KEY);
		PutType putType = PutType.forNumber(putTypeValue);
		PutType putTypeForAd = ad.getPutType();

		if (putTypeForAd == null)
			putTypeForAd = AdUnitUtils.getPutTypeByCostType(ad.getCostType());

		if (putType == null) {
			return false;
		}

		if (putType == putTypeForAd) {
			return false;
		}
		return true;
	}
}

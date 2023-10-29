/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import java.util.List;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.CommonProto.CostType;

/**
 * 付费类型过滤器
 * 
 * @author wangwp
 */
public class CostTypeAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext context, AdUnit adUnit) {
		CostType costType = adUnit.getCostType();
		List<CostType> costTypes = context.getPosInfo().getCostTypeList();
		for (int i = 0; i < costTypes.size(); i++) {
			if (costType == costTypes.get(i)) {
				return false;
			}
		}
		return true;
	}

}

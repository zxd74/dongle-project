/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.PutType;

/**
 * 
 * @author wangwp
 */
public final class AdUnitUtils {

	public static PutType getPutTypeByCostType(CostType costType) {
		if (Constants.COST_TYPES_CPT.contains(costType)) {
			return PutType.kPutTypeOrder;
		}

		if (Constants.COST_TYPES_RTB.contains(costType)) {
			return PutType.kPutTypeRtb;
		}

		return null;
	}
}

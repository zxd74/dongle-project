/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.ServiceLoaderUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Creative;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;

/**
 * 
 * @author wangwp
 */
public class AdFilters {
	private static final Logger LOGGER = LoggerFactory.getLogger("AdFilterChain");

	private static final List<AdFilter> adFilters = ServiceLoaderUtils.loadServices(AdFilter.class);

	public static boolean filterAd(BiddingContext context, AdUnit adUnit) {
		if (context.isOttAdReq()) {
			return filterOttAd(context, adUnit);
		}
		AdFilter adFilter = null;

		long startGlobal = System.currentTimeMillis();
		long start = 0L;
		String filterName = null;

		for (int i = 0, size = adFilters.size(); i < size; i++) {
			adFilter = adFilters.get(i);
			try {
				filterName = adFilter.getClass().getSimpleName().toLowerCase();
				start = System.currentTimeMillis();
				if (adFilter.filterAd(context, adUnit)) {
					context.trace("Filter ad by: [{}], adUnit id: {}", filterName, adUnit.getUnitId());
					return true;
				}
			} finally {
				long elapsed = System.currentTimeMillis() - start;
				if (elapsed > 3) {
					LOGGER.info("Inovke ad filter [{}] cost: {}ms", filterName, elapsed);
				}
			}
		}

		long elapsedGlobal = System.currentTimeMillis() - startGlobal;
		if (elapsedGlobal > 5) {
			LOGGER.info("Invoke ad filters cost: {}ms", elapsedGlobal);
		}
		return false;
	}

	private static boolean filterOttAd(BiddingContext context, AdUnit adUnit) {
		for (Creative creative : adUnit.getCreativesList()) {
			CreativeType creativeType = creative.getCreativeType();
			for (CreativeType _creativeType : context.getPosInfo().getCreativeTypeList()) {
				if (creativeType == _creativeType) {
					if (isMatchAdSpec(context.getPosInfo(), creative)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private static boolean isMatchAdSpec(PosInfo posInfo, Creative creative) {
		CreativeType creativeType = creative.getCreativeType();
		switch (creativeType) {
		case kPic:
			return posInfo.getWidth() == creative.getWidth() && posInfo.getHeight() == creative.getHeight();
		case kVideo:
			return creative.getDuration() >= posInfo.getMinDuration()
					&& creative.getDuration() <= posInfo.getMaxDuration();
		default:
			break;
		}
		return false;
	}
}

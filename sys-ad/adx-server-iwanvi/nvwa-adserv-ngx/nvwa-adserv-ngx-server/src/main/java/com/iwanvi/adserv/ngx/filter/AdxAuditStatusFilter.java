/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Creative;
import com.iwanvi.nvwa.proto.CommonProto.AdxInfo;
import com.iwanvi.nvwa.proto.CommonProto.AdxType;

/**
 * Ad-Exchange审核状态过滤器，过滤请求Ad-Exchange没有审核通过的推广单元
 * 
 * @author wangwp
 */
public class AdxAuditStatusFilter implements AdFilter {
	static final Logger LOGGER = LoggerFactory.getLogger("adFilter");

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit ad) {

		if (!ctx.getUserInfo().hasAdxType()) {
			return false;
		}

		// 请求adx类型
		AdxType adxType = ctx.getUserInfo().getAdxType();

		if (adxType == AdxType.kAdxTypeUnKnown) {
			return false;
		}

		for (int i = 0, size = ad.getCreativesCount(); i < size; i++) {
			Creative creative = ad.getCreatives(i);
			for (AdxInfo adxInfo : creative.getAdxTarget().getAdxInfoList()) {
				if (adxInfo.getAdxType() == adxType) {
					return false;
				}
			}
		}
		return true;
	}
}

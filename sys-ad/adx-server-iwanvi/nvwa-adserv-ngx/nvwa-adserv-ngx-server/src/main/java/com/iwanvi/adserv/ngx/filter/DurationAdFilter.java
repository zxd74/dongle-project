/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;

/**
 * 贴片时长过滤
 * 
 * @author wangwp
 */
public class DurationAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext context, AdUnit adUnit) {
		// 如果非贴片广告请求则直接返回
		if (!context.isVideoReq()) {
			return false;
		}

		// 如果按照最新请求(即最小时长和最大时长2个参数都存在的情况)来处理走这个分支否则兼容原来的流程
		if (enableRangedDurationsFilter(context)) {
			return isFilterByRangedDurations(context, adUnit);
		}

		// 对于视频贴片广告来说，只要广告时长小于等于广告位要求时长都匹配
		if (adUnit.getDuration() <= context.getPosInfo().getDuration()) {
			return false;
		}
		return true;
	}

	private boolean enableRangedDurationsFilter(BiddingContext context) {
		PosInfo posInfo = context.getPosInfo();

		if (posInfo.hasMaxDuration() && posInfo.hasMinDuration()) {
			return true;
		}
		return false;
	}

	private boolean isFilterByRangedDurations(BiddingContext context, AdUnit adUnit) {
		PosInfo posInfo = context.getPosInfo();

		int minDuration = posInfo.getMinDuration();
		int maxDuration = posInfo.getMaxDuration();

		int adDuration = adUnit.getDuration();
		// 如果广告时长小于最小时长或者大于最大时长的话则不匹配
		if (adDuration < minDuration || adDuration > maxDuration) {
			return true;
		}

		int mod = maxDuration % 15;
		// 如果支持5s贴片广告位，判断广告是否匹配
		if (mod == 5 || mod == 10) {
			return !isMatched5sVideoAd(adUnit);
		}
		return false;
	}

	private final boolean isMatched5sVideoAd(AdUnit adUnit) {
		return adUnit.getDuration() == 5;
	}
}

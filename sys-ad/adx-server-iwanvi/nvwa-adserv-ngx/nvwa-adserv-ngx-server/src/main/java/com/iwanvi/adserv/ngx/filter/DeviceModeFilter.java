/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

/**
 * @author wangwp
 *
 */
public class DeviceModeFilter implements AdFilter {

	private static final String TARGET_MODEL = "dx_model";

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit ad) {
		String targetMake = ctx.getAdExtProperty(TARGET_MODEL);
		String make = ctx.getUserExtProperty(TARGET_MODEL);

		// 如果没有设备型号定向不过滤广告
		if (StringUtils.isBlank(targetMake)) {
			return false;
		}
		// 如果广告请求设备型号为空则不过滤广告
		if (StringUtils.isBlank(make)) {
			return false;
		}

		// 如果请求设备型号和过滤设备型号匹配则过滤广告
		if (make.toLowerCase().contains(targetMake.toLowerCase())) {
			return true;
		}
		return false;
	}

}

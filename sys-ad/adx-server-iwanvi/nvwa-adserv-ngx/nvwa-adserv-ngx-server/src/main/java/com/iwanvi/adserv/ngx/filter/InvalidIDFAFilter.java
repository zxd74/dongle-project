/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import static com.iwanvi.adserv.ngx.util.MinervaStatHolder.*;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.CommonProto.OSType;

/**
 * 非法的IDFA过滤器
 * 
 * @author wangweiping
 *
 */
public class InvalidIDFAFilter implements AdFilter {
	private static final String DEFAULT_IDFA_IOS10_LATER = "00000000-0000-0000-0000-000000000000";

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit ad) {
		// 如果不过滤非法的IDFA的话，直接返回
		if (!ad.getInvalidDeviceFiltering()) {
			return false;
		}
		OSType os = ctx.getUserInfo().getOs();
		if (os != OSType.kIOS) {
			return false;
		}

		String idfa = ctx.getUserInfo().getMuid().toStringUtf8();
		if (isInvalidIDFA(idfa)) {
			statInfo().invalidIdfaFilteredRequests.incrementAndGet();
			return true;
		}
		return false;
	}

	private boolean isInvalidIDFA(String idfa) {
		if (StringUtils.isBlank(idfa) || idfa.length() != 36) {
			return true;
		}

		if (!idfa.contains(Constants.SIGN_HYPHEN)) {
			return true;
		}

		if (StringUtils.equals(idfa, DEFAULT_IDFA_IOS10_LATER)) {
			return true;
		}
		return false;
	}
}

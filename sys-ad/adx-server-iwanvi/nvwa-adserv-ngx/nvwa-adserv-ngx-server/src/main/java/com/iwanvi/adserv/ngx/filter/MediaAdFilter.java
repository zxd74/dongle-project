/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.MediaTarget;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;

/**
 * 媒体定向过滤
 * 
 * @author wangwp
 */
public class MediaAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit adUnit) {
		// 如果单元没有媒体定向，允许此广告投放
		if (!adUnit.hasMediaTarget()) {
			return false;
		}

		UserInfo userInfo = ctx.getUserInfo();

		String mediaUuid = userInfo.getMediaUuid();
		MediaTarget mediaTarget = adUnit.getMediaTarget();
		for (int i = 0; i < mediaTarget.getMediaUuidCount(); i++) {
			if (mediaUuid.equals(mediaTarget.getMediaUuid(i))) {
				return false;
			}
		}
		return true;
	}
}

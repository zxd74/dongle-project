/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import org.apache.commons.codec.digest.DigestUtils;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

/**
 * 设备定向，用来定向刷广告以及设备重定向使用
 * 
 * @author wangwp
 *
 */
public class DeviceAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit ad) {
		if (!ad.hasDeviceTarget()) {
			return false;
		}

		// 如果没有定向设备文件并且设备id列表为空的话，则不需要设备定向
		if (!ad.getDeviceTarget().hasDidFileUrl() && ad.getDeviceTarget().getDidList().isEmpty()) {
			return false;
		}

		String did = ctx.getUserInfo().getMuid().toStringUtf8();
		String didmd5 = DigestUtils.md5Hex(did);

		for (String targetDid : ad.getDeviceTarget().getDidList()) {
			if (targetDid.equalsIgnoreCase(did) || targetDid.equalsIgnoreCase(didmd5)) {
				return false;
			}
		}
		return true;
	}
}

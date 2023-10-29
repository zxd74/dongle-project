/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.TerminalTarget;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.TerminalType;

/**
 * 终端定向过滤
 * 
 * @author wangwp
 */
public class TerminalAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit adUnit) {
		if (!adUnit.hasTerminalTarget()) {
			return false;
		}

		UserInfo userInfo = ctx.getUserInfo();

		TerminalType terminal = userInfo.getTerminalType();
		TerminalTarget terminalTarget = adUnit.getTerminalTarget();

		for (int i = 0; i < terminalTarget.getTerminalCount(); i++) {
			if (terminal == terminalTarget.getTerminal(i)) {
				return false;
			}
		}
		return true;
	}
}

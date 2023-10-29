/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import java.util.List;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.BehaviorTarget;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;

/**
 * 行为定向过滤器
 * 
 * @author wangwp
 */
public class BehaviorAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext context, AdUnit adUnit) {
		UserInfo userInfo = context.getUserInfo();

		// 如果广告没有行为定向的话则允许出此广告
		if (!adUnit.hasBehaviorTarget()) {
			return false;
		}

		// 如果广告有行为定向但是用户没有行为数据则不允许此用户出广告
		if (!userInfo.hasBehavior() && userInfo.getBehaviorsCount() == 0) {
			return true;
		}

		BehaviorTarget behaviorTarget = adUnit.getBehaviorTarget();
		if (userInfo.hasBehavior()) {
			int behavior = userInfo.getBehavior();

			for (int i = 0; i < behaviorTarget.getBehaviorCount(); i++) {
				if (behavior == behaviorTarget.getBehavior(i)) {
					return false;
				}
			}
		} else if (userInfo.getBehaviorsCount() > 0) {
			List<Integer> behaviors = userInfo.getBehaviorsList();
			for (Integer behavior : behaviors) {
				for (int i = 0; i < behaviorTarget.getBehaviorCount(); i++) {
					if (behavior == behaviorTarget.getBehavior(i)) {
						return false;
					}
				}
			}
		}
		return true;
	}

}

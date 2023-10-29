/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.CarrierTarget;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;

/**
 * 
 * @author wangwp
 */
public class CarrierAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext context, AdUnit adUnit) {
		// 如果没做运营商定向的话，则过滤
		if (!adUnit.hasCarrierTarget()) {
			return false;
		}

		// 如果广告有运营商定向但是用户没有指定运营商的话，则此广告不能参与竞价
		UserInfo userInfo = context.getUserInfo();
		if (!userInfo.hasCarrier()) {
			return true;
		}

		Carrier carrier = userInfo.getCarrier();
		CarrierTarget carrierTarget = adUnit.getCarrierTarget();
		for (int i = 0; i < carrierTarget.getCarrierCount(); i++) {
			if (carrier == carrierTarget.getCarrier(i)) {
				return false;
			}
		}
		return true;
	}

}

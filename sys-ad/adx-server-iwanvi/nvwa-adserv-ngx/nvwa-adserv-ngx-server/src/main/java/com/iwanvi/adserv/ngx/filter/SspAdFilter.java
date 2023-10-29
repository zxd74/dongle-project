/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.CommonProto.AdxType;

/**
 * @author wangwp
 *
 */
public class SspAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext ctx, AdUnit ad) {
		AdxType adxType = ctx.getUserInfo().getAdxType();

		if (adxType != AdxType.kAdxTypeUnKnown) {
			return false;
		}

		Repository repo = RepositoryFactory.getRepository();
		String targetAppId = ctx.getUserExtProperty(Constants.EXT_APP_ID);
		if (StringUtils.isBlank(targetAppId)) {
			targetAppId = ctx.getPosInfo().getAppId().toStringUtf8();
		}
		App sspapp = repo.loadApp(targetAppId);
		if (sspapp == null) {
			ctx.trace("Not target app, filter ad: {}, app: {}", ad.getUnitId(), targetAppId);
			return true;
		}

		if (sspapp.getStatus() == 0) {
			return true;
		}

		String sspAdPosId = ctx.getPosInfo().getPosId().toStringUtf8();
		SspAdPosition adPosition = repo.loadSspAdPosition(sspAdPosId);

		if (!ad.getAdxAdTypeId().equals(sspAdPosId)) {
			ctx.trace("请求广告位与投放广告位不匹配, 请求广告位: {}, 投放广告位: {}", sspAdPosId, ad.getAdxAdTypeId());
			return true;
		}
		if (adPosition == null) {
			ctx.trace("Not exists ssp ad placement, placement's id: {}, unitId: {}", sspAdPosId, ad.getUnitId());
			return true;
		}

		if (adPosition.getStatus() == 0) {
			return true;
		}

		if (Constants.COST_TYPES_CPT.contains(ad.getCostType())) {
			return false;
		}

		// ssp广告位底价过滤
		int adPositionFloor = adPosition.getBidfloor();
		if (ad.getCpc() < adPositionFloor) {
			return true;
		}
		return false;
	}

}

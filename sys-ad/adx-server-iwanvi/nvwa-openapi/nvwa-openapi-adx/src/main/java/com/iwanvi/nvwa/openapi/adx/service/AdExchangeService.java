/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.service;

import java.util.List;

import com.iwanvi.nvwa.openapi.adx.model.Advertiser;
import com.iwanvi.nvwa.openapi.adx.model.AdvertiserAuditResult;
import com.iwanvi.nvwa.openapi.adx.model.Creative;
import com.iwanvi.nvwa.openapi.adx.model.CreativeAuditResult;

/**
 * 
 * @author wangwp
 */
public interface AdExchangeService {

	void updateCreative(Creative creative);

	void addCreative(Creative creative);

	void addAdvertiser(Advertiser advertiser);

	void updateAdvertiser(Advertiser advertiser);

	List<AdvertiserAuditResult> getAdvertiserAuditResult(int flowConsumerId, List<String> advertiserIds);

	List<CreativeAuditResult> getCreativeAuditResults(int flowConsumerId, List<String> creativeIds);
}

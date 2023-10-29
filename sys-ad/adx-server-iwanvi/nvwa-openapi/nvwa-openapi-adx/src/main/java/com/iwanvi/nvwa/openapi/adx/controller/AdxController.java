/*
 * Copyright 2017 The OpenDSP Project
 *
 * The OpenDSP Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.iwanvi.nvwa.openapi.adx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.iwanvi.nvwa.openapi.adx.helper.AuthContext;
import com.iwanvi.nvwa.openapi.adx.helper.BeanValidator;
import com.iwanvi.nvwa.openapi.adx.helper.ValidatorGroup;
import com.iwanvi.nvwa.openapi.adx.model.Advertiser;
import com.iwanvi.nvwa.openapi.adx.model.AdvertiserAuditResult;
import com.iwanvi.nvwa.openapi.adx.model.Creative;
import com.iwanvi.nvwa.openapi.adx.model.CreativeAuditResult;
import com.iwanvi.nvwa.openapi.adx.service.AdExchangeService;

import ai.houyi.dorado.rest.annotation.Controller;
import ai.houyi.dorado.rest.annotation.POST;
import ai.houyi.dorado.rest.annotation.Path;
import ai.houyi.dorado.rest.annotation.RequestBody;
import io.swagger.annotations.Api;

/**
 * 
 * @author wangwp
 */
@Controller
@Path("/adx/v1")
@Api(tags = { "dsp平台广告主&素材管理接口" })
public class AdxController {
	@Autowired
	private AdExchangeService adExchangeService;
	@Autowired
	private BeanValidator beanValidator;

	@POST
	@Path("/advertiser")
	public void addAdvertiser(Advertiser advertiser) {
		advertiser.setDsp(AuthContext.get().getDsp());

		beanValidator.validate(advertiser);
		adExchangeService.addAdvertiser(advertiser);
	}

	@POST
	@Path("/advertiser/update")
	public void updateAdvertisers(Advertiser advertiser) {
		advertiser.setDsp(AuthContext.get().getDsp());

		beanValidator.validate(advertiser, ValidatorGroup.DataUpdateGroup.class);
		adExchangeService.updateAdvertiser(advertiser);
	}

	@POST
	@Path("/creative")
	public void addCreative(Creative creative) {
		creative.setDsp(AuthContext.get().getDsp());

		beanValidator.validate(creative);
		adExchangeService.addCreative(creative);
	}

	@POST
	@Path("/creative/update")
	public void updateCreative(Creative creative) {
		creative.setDsp(AuthContext.get().getDsp());

		beanValidator.validate(creative, ValidatorGroup.DataUpdateGroup.class);
		adExchangeService.updateCreative(creative);
	}

	@POST
	@Path("/advertiser/audit_status")
	public List<AdvertiserAuditResult> getAdvertiserAuditResults(@RequestBody List<String> advertiserIds) {
		return adExchangeService.getAdvertiserAuditResult(AuthContext.get().getDsp().getId(), advertiserIds);
	}

	@POST
	@Path("/creative/audit_status")
	public List<CreativeAuditResult> getCreativeAuditResults(@RequestBody List<String> creativeIds) {
		return adExchangeService.getCreativeAuditResults(AuthContext.get().getDsp().getId(), creativeIds);
	}
}

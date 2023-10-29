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
package com.iwanvi.nvwa.openapi.adx.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.iwanvi.nvwa.dao.model.DspAdvertiser;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.openapi.adx.helper.ValidatorGroup;

/**
 * 
 * @author wangwp
 */
public class Advertiser {
	@NotBlank(message = "广告主id不能为空", groups = { ValidatorGroup.DataAddGroup.class,
			ValidatorGroup.DataUpdateGroup.class })
	private String advertiserId;

	@NotNull(message = "广告主名称不能为空")
	private String name;

	@NotBlank(message = "营业执照不能为空")
	private String businessLicense;

	private String qualification;

	@NotNull(message = "行业类型不能为空")
	private Integer industryType;

	private String address;
	private FlowConsumer dsp;

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Integer getIndustryType() {
		return industryType;
	}

	public void setIndustryType(Integer industryType) {
		this.industryType = industryType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdvertiserId() {
		return advertiserId;
	}

	public FlowConsumer getDsp() {
		return dsp;
	}

	public void setDsp(FlowConsumer dsp) {
		this.dsp = dsp;
	}

	public void setAdvertiserId(String id) {
		this.advertiserId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DspAdvertiser toDspAdvertiser() {
		DspAdvertiser advertiser = new DspAdvertiser();
		advertiser.setAddress(address);
		advertiser.setFlowConsumerId(dsp.getId());
		advertiser.setDspAdvertiserId(advertiserId);
		advertiser.setBusinesslicense(businessLicense);
		advertiser.setQualifications(qualification);
		advertiser.setIndustryType(industryType);
		advertiser.setName(name);
		advertiser.setExaminesStatus(3); // TODO magic number
		advertiser.setStatus(1);

		return advertiser;
	}
}

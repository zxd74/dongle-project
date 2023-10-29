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

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.iwanvi.nvwa.dao.model.DspCreative;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.openapi.adx.helper.ExceptionUtils;
import com.iwanvi.nvwa.openapi.adx.helper.ValidatorGroup.DataAddGroup;
import com.iwanvi.nvwa.openapi.adx.helper.ValidatorGroup.DataUpdateGroup;

/**
 * 
 * @author wangwp
 */
public class Creative {
	@NotBlank(message = "创意id不能为空", groups = { DataAddGroup.class, DataUpdateGroup.class })
	private String creativeId;

	@NotBlank(message = "广告主id不能为空")
	private String advertiserId;

	// @NotBlank(message = "素材地址不能为空")
	private String creativeUrl; // 素材地址,素材类型为图片或视频

	private String clickUrl;

	@NotBlank(message = "广告落地页不能为空")
	private String landingPage;

	@Valid
	private NativeAd nativeAd;

	@NotNull(message = "创意类型不能为空")
	private Integer creativeType; // 创意类型

	@NotBlank(message = "创意名称不能为空")
	private String name;

	private FlowConsumer dsp;

	public FlowConsumer getDsp() {
		return dsp;
	}

	public void setDsp(FlowConsumer dsp) {
		this.dsp = dsp;
	}

	public String getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(String id) {
		this.creativeId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdvertiserId() {
		return advertiserId;
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}

	public String getCreativeUrl() {
		return creativeUrl;
	}

	public void setCreativeUrl(String creativeUrl) {
		this.creativeUrl = creativeUrl;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public String getLandingPage() {
		return landingPage;
	}

	public void setLandingPage(String landingPage) {
		this.landingPage = landingPage;
	}

	public NativeAd getNativeAd() {
		return nativeAd;
	}

	public void setNativeAd(NativeAd nativeAd) {
		this.nativeAd = nativeAd;
	}

	public Integer getCreativeType() {
		return creativeType;
	}

	public void setCreativeType(Integer creativeType) {
		this.creativeType = creativeType;
	}

	public DspCreative toDspCreative() {
		DspCreative creative = new DspCreative();
		// 对于图片和视频类广告，必须提供创意地址
		if (creativeType == 2 || creativeType == 5) {
			if (StringUtils.isBlank(creativeUrl)) {
				ExceptionUtils.throwOpenApiException("创意地址creativeUrl不能为空");
			}
		}

		creative.setUpdateTime(new Date());
		creative.setEntitytype(creativeType);
		creative.setEntityurl(creativeUrl);
		creative.setLandpage(landingPage);
		creative.setDspAdvertiserId(advertiserId);
		creative.setExaminesStatus(3); // TODO magic number,待机审
		creative.setName(name);
		creative.setDspCreativeId(creativeId);
		creative.setConsumerId(dsp.getId());
		// creative.setPositionId(adPositionId);
		creative.setStatus(1);
		creative.setExpireDate(DateFormatUtils.format(DateUtils.addMonths(new Date(), 6), "yyyy-MM-dd"));

		if (nativeAd != null) {
			creative.setThreadTitle(nativeAd.getTitle());
			creative.setUserPortrait(nativeAd.getIcon());
			creative.setThreadContent(nativeAd.getDesc());
			creative.setCtatext(nativeAd.getCtatext());

			String methodNameFormat = "setThreadPic%s";
			if (nativeAd.getImages() != null & nativeAd.getImages().length > 0) {
				String[] images = nativeAd.getImages();
				for (int i = 0, length = images.length > 9 ? 9 : images.length; i < length; i++) {
					try {
						MethodUtils.invokeMethod(creative, true, String.format(methodNameFormat, i + 1), images[i]);
					} catch (Throwable ex) {

					}
				}
			}
		}
		return creative;
	}

	public static void main(String[] args) {
		System.out.println(String.format("setThreadPic%s", 1));
	}
}

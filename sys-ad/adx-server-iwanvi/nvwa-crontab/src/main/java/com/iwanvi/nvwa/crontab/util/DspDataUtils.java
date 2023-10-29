/*
 * Copyright 2017 The OpenAds Project
 *
 * The OpenAds Project licenses this file to you under the Apache License,
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
package com.iwanvi.nvwa.crontab.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;

/**
 * @author weiping wang
 *
 */
public final class DspDataUtils {
	/**
	 * 将平台dsp创意转换为引擎DspCreative对象
	 * 
	 * @param dspCreative 平台dsp创意实体
	 * @return 引擎dsp创意实体
	 */
	public static DspCreative convertToNgxDspCreative(com.iwanvi.nvwa.dao.model.DspCreative c) {
		DspCreative.Builder creative = DspCreative.newBuilder().setAdvertiserId(c.getDspAdvertiserId())
				.setCreativeId(c.getDspCreativeId()).setLandingPage(StringUtils.defaultString(c.getLandpage()))
				.setId(c.getId());

		List<String> images = new ArrayList<>();
		if (StringUtils.isNotBlank(c.getEntityurl())) {
			creative.setCreativeUrl(c.getEntityurl());
		}
		if (c.getThreadPic1() != null) {
			images.add(c.getThreadPic1());
		}
		if (c.getThreadPic2() != null) {
			images.add(c.getThreadPic2());
		}
		if (c.getThreadPic3() != null) {
			images.add(c.getThreadPic3());
		}
		if (c.getThreadPic4() != null) {
			images.add(c.getThreadPic4());
		}

		if (c.getThreadPic5() != null) {
			images.add(c.getThreadPic5());
		}
		if (c.getThreadPic6() != null) {
			images.add(c.getThreadPic6());
			creative.setImages(5, c.getThreadPic6());
		}
		if (c.getThreadPic7() != null) {
			images.add(c.getThreadPic7());
		}
		if (c.getThreadPic8() != null) {
			images.add(c.getThreadPic8());
		}
		if (c.getThreadPic9() != null) {
			images.add(c.getThreadPic9());
		}
		images.addAll(images);
		if (StringUtils.isNotBlank(c.getThreadTitle())) {
			creative.setTitle(c.getThreadTitle());
		}

		if (StringUtils.isNotBlank(c.getUserPortrait())) {
			creative.setIcon(c.getUserPortrait());
		}

		if (StringUtils.isNotBlank(c.getThreadContent())) {
			creative.setDesc(c.getThreadContent());
		}
		if (StringUtils.isNotBlank(c.getCtatext())) {
			creative.setCtatext(c.getCtatext());
		}
		return creative.build();
	}

	/**
	 * 转换流量使用方到引擎Dsp实体
	 * 
	 * @param flowConsumer 流量消费方
	 * @return 引擎dsp实体
	 */
	public static Dsp convertToNgxDsp(FlowConsumer f) {
		Dsp.Builder dsp = Dsp.newBuilder();
		dsp.setDspId(f.getDspId()).setQps(f.getQps()).setRtbUrl(f.getRtbUrl()).setToken(f.getToken());

		if (StringUtils.isBlank(f.getConsumerMark())) {
			dsp.setSource(f.getConsumerName());
		} else {
			dsp.setSource(f.getConsumerMark());
		}
		if (Constants.INTEGER_1.equals(f.getIsTest())) {
			dsp.setIsTest(true);
		} else {
			dsp.setIsTest(false);
		}
		if (CrontabConstants.IS_NOT_PRIVATE.equals(f.getIsPrivate())) {
			dsp.setIsStandardProtocol(true);
		} else {
			dsp.setIsStandardProtocol(false);
		}

		return dsp.build();
	}
}

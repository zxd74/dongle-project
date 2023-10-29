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
package com.iwanvi.nvwa.crontab.service;

import java.util.Date;
import java.util.List;

import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;

/**
 * @author weiping wang
 *
 */
public interface DspDataService {
	/**
	 * 获得所有有效的第三方广告平台
	 * 
	 * @return 所有有效的广告平台
	 */
	List<Dsp> getAllValidDsps();

	/**
	 * 获得指定日期之后更新过的所有dsp创意
	 * 
	 * @param date 更新日期
	 * @return 指定日期之后更新过的所有dsp创意
	 */
	List<DspCreative> getUpdatedDspCreatives(Date date);

	/**
	 * 根据流量消费方id获得第三方dsp平台信息
	 * 
	 * @param flowConsumerId 第三方广告平台id
	 * @return 引擎Dsp对象
	 */
	Dsp getDspByFlowConsumerId(Integer flowConsumerId);

	/**
	 * 根据平台创意id(自增id,无意义)返回dsp平台创意信息
	 * 
	 * @param creativeId 平台自增id
	 * @return 引擎dsp创意实体
	 */
	DspCreative getDspCreativeById(Integer creativeId);
}

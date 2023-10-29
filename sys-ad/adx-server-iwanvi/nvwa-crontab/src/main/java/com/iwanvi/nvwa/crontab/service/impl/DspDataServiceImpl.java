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
package com.iwanvi.nvwa.crontab.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.service.DspDataService;
import com.iwanvi.nvwa.crontab.util.DspDataUtils;
import com.iwanvi.nvwa.dao.DspCreativeMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.FlowConsumerExample;
import com.iwanvi.nvwa.dao.model.DspCreativeExample;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;

/**
 * @author weiping wang
 *
 */
@Service
public class DspDataServiceImpl implements DspDataService {
	@Autowired
	private FlowConsumerMapper flowConsumerMapper;
	@Autowired
	private DspCreativeMapper dspCreativeMapper;

	@Override
	public List<Dsp> getAllValidDsps() {
		FlowConsumerExample example = new FlowConsumerExample();
		example.createCriteria().andConsumerStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID);

		List<FlowConsumer> flowConsumers = flowConsumerMapper.selectByExample(example);
		List<Dsp> ngxDspList = new ArrayList<>();

		for (FlowConsumer f : flowConsumers) {
			ngxDspList.add(DspDataUtils.convertToNgxDsp(f));
		}

		return ngxDspList;
	}

	@Override
	public List<DspCreative> getUpdatedDspCreatives(Date date) {
		DspCreativeExample example = new DspCreativeExample().createCriteria().andUpdateTimeGreaterThan(date).example();
		List<com.iwanvi.nvwa.dao.model.DspCreative> updatedCreativeList = dspCreativeMapper.selectByExample(example);

		List<DspCreative> ngxDspCreativeList = new ArrayList<>();
		updatedCreativeList.forEach(c -> {
			ngxDspCreativeList.add(DspDataUtils.convertToNgxDspCreative(c));
		});
		return ngxDspCreativeList;
	}

	@Override
	public Dsp getDspByFlowConsumerId(Integer flowConsumerId) {
		FlowConsumer f = flowConsumerMapper.selectByPrimaryKey(flowConsumerId);
		return f == null ? null : DspDataUtils.convertToNgxDsp(f);
	}

	@Override
	public DspCreative getDspCreativeById(Integer creativeId) {
		// TODO Auto-generated method stub
		com.iwanvi.nvwa.dao.model.DspCreative dspEntity = dspCreativeMapper.selectByPrimaryKey(creativeId);
		
		return dspEntity==null?null:DspDataUtils.convertToNgxDspCreative(dspEntity);
	}

}

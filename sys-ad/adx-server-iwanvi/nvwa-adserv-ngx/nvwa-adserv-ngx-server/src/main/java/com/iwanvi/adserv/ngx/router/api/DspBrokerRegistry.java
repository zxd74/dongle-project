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
package com.iwanvi.adserv.ngx.router.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.iwanvi.adserv.ngx.util.ServiceLoaderUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;

/**
 * 
 * @author wangwp
 */
public class DspBrokerRegistry {
	private static final Map<String, DspBroker> _dsp_broker_holder = new ConcurrentHashMap<>();

	static {
		List<DspBroker> dspBrokers = ServiceLoaderUtils.loadServices(DspBroker.class);
		dspBrokers.forEach(dsp -> {
			if (dsp.getDspId() != null)
				_dsp_broker_holder.put(dsp.getDspId(), dsp);
		});
	}

	public static void registerDspBroker(DspBroker dspBroker) {
		_dsp_broker_holder.put(dspBroker.getDspId(), dspBroker);
	}

	public static DspBroker getDspBroker(String dspId) {
		return _dsp_broker_holder.get(dspId);
	}

	public static boolean hasDsp(String dspId) {
		return _dsp_broker_holder.containsKey(dspId);
	}

	public static DspBroker getDspBroker(Dsp dsp) {
		DspBroker broker = _dsp_broker_holder.get(dsp.getDspId());
		if (broker != null)
			broker.setDsp(dsp);
		return broker;
	}
}

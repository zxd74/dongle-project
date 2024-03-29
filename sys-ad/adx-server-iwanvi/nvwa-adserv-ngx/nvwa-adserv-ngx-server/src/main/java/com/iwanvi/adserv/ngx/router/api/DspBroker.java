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

import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

/**
 * 
 * @author wangwp
 */
public interface DspBroker extends DspAware {
	enum HttpMethod {
		GET, POST
	}

	String getDspId();

	default byte[] toDspRequest(BiddingReq biddingReq) {
		return null;
	}

	String getRequestContentType();

	List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody);

	WinPriceCodec getWinPriceCodec();

	default HttpMethod requestMethod() {
		return HttpMethod.POST;
	}

	default String toQueryString(BiddingReq biddingReq) {
		return null;
	}

	default void setDsp(Dsp dsp) {
	}

	default Map<String, String> getHeaders(BiddingReq biddingReq) {
		return null;
	}

	default String getDspUrl(String url,BiddingReq biddingReq){return url;}
}

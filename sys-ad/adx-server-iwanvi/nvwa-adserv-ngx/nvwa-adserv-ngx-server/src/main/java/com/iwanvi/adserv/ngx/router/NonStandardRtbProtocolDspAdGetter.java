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
package com.iwanvi.adserv.ngx.router;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.iwanvi.adserv.ngx.util.CodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.router.DspRouter.DspBid;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.api.DspBrokerRegistry;
import com.iwanvi.adserv.ngx.service.BidFloorResolver;
import com.iwanvi.adserv.ngx.service.impl.PrivateProtoDspFloorResolver;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.OkHttpUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition.AdPositionMapping;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.PutType;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * @author wangwp
 */
public class NonStandardRtbProtocolDspAdGetter implements Callable<List<DspBid>> {
	private static final Logger LOG = LoggerFactory.getLogger(NonStandardRtbProtocolDspAdGetter.class);

	private final Dsp dsp;
	private final BiddingReq biddingReq;
	private final DspBroker broker;

	private static final OkHttpClient httpClient = OkHttpUtils.dspHttpClient;

	private static final BidFloorResolver DEFAULT_BIDFLOOR_RESOLVER = new PrivateProtoDspFloorResolver();
	public static BidFloorResolver bidFloorResolver = DEFAULT_BIDFLOOR_RESOLVER;

	public NonStandardRtbProtocolDspAdGetter(Dsp dsp, BiddingReq biddingReq) {
		this.dsp = dsp;
		this.biddingReq = biddingReq;
		this.broker = DspBrokerRegistry.getDspBroker(dsp);
	}

	public static void setBidFloorResolver(BidFloorResolver bfr) {
		bidFloorResolver = bfr;
	}

	@Override
	public List<DspBid> call(){
		long s = System.currentTimeMillis();
		try {
			Request.Builder reqBuilder = new Request.Builder();
			DspBroker.HttpMethod method = broker.requestMethod();
			if (method == DspBroker.HttpMethod.GET) {
				reqBuilder.url(String.format("%s?%s", dsp.getRtbUrl(), broker.toQueryString(biddingReq))).get().build();
			} else {
				reqBuilder.url(broker.getDspUrl(dsp.getRtbUrl(),biddingReq)).post(RequestBody.create(MediaType.get(broker.getRequestContentType()),
						broker.toDspRequest(biddingReq))).build();
			}

			Map<String, String> extHeaders = broker.getHeaders(biddingReq);
			if (extHeaders != null && !extHeaders.isEmpty()) {
				extHeaders.forEach((k, v) -> reqBuilder.addHeader(k, v));
			}
			Request adRequest = reqBuilder.build();

			DspRateLimiter qpsRateLimiter = DspRateLimiters.get(dsp);
			// 如果超过平台qps限制则不发送请求，然后记录dsp平台qps超限请求次数
			if (qpsRateLimiter != null && !qpsRateLimiter.tryAcquire()) {
				LOG.warn("qps超出限制：{}", dsp.getQps());
				DspLoggers.code(biddingReq,dsp.getDspId(), CodeUtils.DSP_OVER_QPS);
				DspLoggers.overQps(biddingReq, dsp.getDspId());
				return null;
			}

			long s1 = System.currentTimeMillis();
			try (Response response = httpClient.newCall(adRequest).execute()) {
				List<DspBid> dspBids = handleBidResponse(this.biddingReq, response);
				return dspBids;
			} catch (Exception ex) {
				throw ex;
			} finally {
				long cost = System.currentTimeMillis() - s1;
				DspLoggers.time(biddingReq, dsp.getDspId(), cost);
				LOG.debug("http请求耗时：{}ms, url: {}", cost, dsp.getRtbUrl());
			}
		} catch (Throwable ex) {
			LOG.error("请求dsp平台接口异常, dspId: " + dsp.getDspId() + ", rtbUrl: " + dsp.getRtbUrl() + ", cause: "
					+ ex.getMessage());
			if (ex instanceof InterruptedIOException) {
				DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.REQUEST_TIMEOUT);
				DspLoggers.timeout(biddingReq, dsp.getDspId());
			} else if (!(ex instanceof AdNgxException)) {
				DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.REQUEST_URL_ERROR);
				DspLoggers.error(biddingReq, dsp.getDspId());
			}
		} finally {
			LOG.debug("call private dsp cost: {}ms", System.currentTimeMillis() - s);
		}
		return null;
	}

	private List<DspBid> handleBidResponse(BiddingReq biddingReq, Response response) {
		final List<DspBid> dspBids = new ArrayList<>();
		int statusCode = response.code();
		if (statusCode == 200) {
			try {
				List<AdMsg> adMsgs = broker.toAdMsgs(biddingReq, response.body().bytes());
				if (CollectionUtils.isNotEmpty(adMsgs)) {
					DspLoggers.bid(biddingReq, dsp.getDspId());
				} else {
					DspLoggers.nobid(biddingReq, dsp.getDspId());
					return null;
				}

				adMsgs.forEach(bid -> {
					AdMsg.Builder adMsg = AdMsg.newBuilder(bid).setCostType(CostType.kCpm)
							.setPutType(PutType.kPutTypeRtb).setSource(dsp.getSource());
					RtbUtils.handleAdMsg(adMsg, biddingReq);
					if (dsp.getIsFixedPrice()) {
						int fixPrice = RtbUtils.getDspFixedPrice(biddingReq, dsp);
						adMsg.setBidPrice(fixPrice).setAdxBidPrice(fixPrice).setPaidCpc(fixPrice);
					}

					AdPositionMapping adslotMapping = RtbUtils.getMappingDspAdPosition(biddingReq, dsp.getDspId());
					if (adslotMapping != null && adslotMapping.hasTemplateId()) {
						adMsg.getNativeAdBuilder().setTemplateId(adslotMapping.getTemplateId());
					}
					DspLoggers.crid(biddingReq,dsp.getDspId(),adMsg.getCrid());
					DspLoggers.bidPrice(biddingReq, dsp.getDspId(), adMsg.getBidPrice());
					double bidfloor = RtbUtils.getBidfloor(dsp, biddingReq);
					if (bidPriceIsGreaterThanFloorPrice(bidfloor, adMsg.getBidPrice())) {
						dspBids.add(DspBid.create(dsp, adMsg.setImpid(biddingReq.getPosInfo(0).getId()), bidfloor));
					} else {
						DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.BID_LOWER_FLOOR_PRICE);
						DspLoggers.lowerFloor(biddingReq, dsp.getDspId());
					}
				});
			} catch (Exception ex) {
				DspLoggers.error(biddingReq, dsp.getDspId());
				LOG.error("处理dsp平台竞价响应异常, dspId: " + dsp.getDspId() + ", cause: " + ex.getMessage());
			}
		} else if (statusCode == 204) {
			LOG.debug("广告平台不参与竞价, dspid: " + dsp.getDspId());
			DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.RESPONSE_NOT_BID);
			DspLoggers.nobid(biddingReq, dsp.getDspId());
		} else {
			LOG.warn("dsp平台出现异常, statusCode: {}, dsp: {}", statusCode, dsp.getDspId());
			// 非200/204状态码，直接记录异常竞价数
			DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.RESPONSE_OTHER);
			DspLoggers.error(biddingReq, dsp.getDspId());
		}
		return dspBids;
	}

	private boolean bidPriceIsGreaterThanFloorPrice(double floor, int bidPrice) {
		LOG.debug("==dsp平台出价：{}, 媒体广告位底价：{}==", bidPrice, floor);
		return bidPrice >= floor;
	}
}

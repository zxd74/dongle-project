/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.router;

import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.base.Charsets;
import com.iwanvi.adserv.ngx.util.*;
import com.iwanvi.nvwa.proto.CommonProto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.api.DspBrokerRegistry;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Imp;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidResponse;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * dsp流量分发
 *
 * @author wangwp
 */
public class DspRouter {
	private static final Logger LOG = LoggerFactory.getLogger(DspRouter.class);

	private static final MediaType contentType = MediaType.parse("application/x-protobuf");

	private static final DspRouter INST = new DspRouter();
	private static final int timeout = MinervaCfg.get().getDspTimeout();
	private static final int adGetterCallTimeout = timeout + 10; // 增加10ms队列等待时间

	private static final OkHttpClient httpClient = OkHttpUtils.dspHttpClient;
	private static final ExecutorService dsp_router_pool = ThreadPools.getDspRouterExecutor();

	private DspRouter() {
	}

	public static DspRouter get() {
		return INST;
	}

	public List<AdMsg> route(final BiddingReq biddingReq) {
		List<Dsp> dspList = DspIndexUtils.getDspListByBiddingReq(biddingReq);
		// 如果没有匹配当前流量的dsp, 直接从内部系统获取符合条件的广告即可
		if (CollectionUtils.isEmpty(dspList)) {
			LOG.warn("流量没有匹配到dsp平台, requestId: {},tagId: {}", biddingReq.getId(),
					biddingReq.getPosInfo(0).getPosId().toStringUtf8());
			return Collections.emptyList();
		}
		List<String> dspIdList = dspList.stream().map(e -> e.getDspId()).collect(Collectors.toList());
		LOG.info("流量匹配dsp平台列表：{}", dspIdList);

		List<Callable<List<DspBid>>> tasks = new ArrayList<>();
		addDspAdGettersToTasks(tasks, dspList, biddingReq);

		List<DspBid> dspBids = new ArrayList<>(); // 所有dsp参与竞价响应
		List<AdMsg> allSeatbids = new ArrayList<>(); // 所有获胜竞价列表, 每个广告位最多一个dsp竞价成功

		long s = System.currentTimeMillis();
		try {
			DspLoggers.registerLogger(biddingReq);
			DspLoggers.createOrGetDspLog(biddingReq,dspIdList);
			List<Future<List<DspBid>>> futureList = dsp_router_pool.invokeAll(tasks, adGetterCallTimeout,
					TimeUnit.MILLISECONDS);
			for (Future<List<DspBid>> f : futureList) {
				if (f.isDone()) {
					try {
						List<DspBid> dspBidsList = f.get();
						if (dspBidsList != null) {
							dspBids.addAll(dspBidsList);
						}
					} catch (Throwable ex) {
						// DO NOTHING
						// LOG.error(ex.getMessage());
					}

				}
			}
			// 分别获取每个广告位的dsp参与竞价信息,然后选出出价最高dsp赢得此次竞价
			List<PosInfo> impList = biddingReq.getPosInfoList();
			for (PosInfo imp : impList) {
				// 获得某一个广告位的所有参与竞价
				List<DspBid> seatbids = filterBidsByImp(dspBids, imp,biddingReq);
				seatbids = filterBidsByCompetingKeyword(seatbids);
				DspBid winBid = selectWinBid(seatbids);
                for (DspBid bid:seatbids){
                    if (!bid.getDspId().equals(winBid.getDspId())){
                        DspLoggers.code(biddingReq,bid.getDspId(), CodeUtils.BID_LOWER_OTHER_PRICE);
                    }
                }
				if (winBid != null) {
					// 这里进行广告曝光监测、点击监测以及落地页宏替换，支持的宏参见DspMacros类中定义
					replaceAllMacrosForDspUrls(biddingReq, winBid);

					// 判断下载地址是否正常（排除下载地址是dp地址）
					if (winBid.getBid().hasExtensionType()
							&& winBid.getBid().getExtensionType() == CommonProto.ExtensionType.kExtAndroid
							&& !winBid.getBid().getLandUrl().toUpperCase().startsWith("HTTP")) {

						LOG.warn("!WARN: download url is error ：Dsp：{}，异常地址：{}", winBid.getDspId(),
								winBid.getBid().getLandUrl());
                        DspLoggers.code(biddingReq,winBid.getDspId(), CodeUtils.AD_DOWNLOAD_ERROR);
						// 下载地址不是HTTP开头的，很有可能是dp
						winBid.getBid().clearExtensionType();
					}

					allSeatbids.add(winBid.bid.build());
					DspLoggers.win(biddingReq, winBid, winBid.dspId, winBid.bid.getBidPrice());
					DspLoggers.landUrl(biddingReq, winBid.dspId, winBid.bid.getLandUrl());
                    DspLoggers.code(biddingReq,winBid.getDspId(), CodeUtils.BID_WIN);
				}
			}
		} catch (Throwable ex) {
			LOG.error(ex.getMessage(), ex);
		} finally {
			LOG.debug("route dsp cost: {}ms", System.currentTimeMillis() - s);
			DspLoggers.printLog(biddingReq);
		}
		return allSeatbids;
	}

	private void replaceAllMacrosForDspUrls(BiddingReq req, DspBid win) {
		AdMsg.Builder bid = win.bid;

		List<String> pm = bid.getImpMonitorUrlList().stream().map(url -> DspMacros.replaceMacros(url, req, win))
				.collect(Collectors.toList());
		List<String> cm = bid.getClkMonitorUrlList().stream().map(url -> DspMacros.replaceMacros(url, req, win))
				.collect(Collectors.toList());
		String landUrl = DspMacros.replaceMacros(bid.getLandUrl(), req, win);

		// 处理无法替换的带有{}的宏
		String repx = "\\{[A-Z0-9_]*\\}";
		pm = pm.stream().map(url -> url.replaceAll(repx, "")).collect(Collectors.toList());
		cm = cm.stream().map(url -> url.replaceAll(repx, "")).collect(Collectors.toList());

		bid.clearClkMonitorUrl().clearImpMonitorUrl();
		bid.addAllClkMonitorUrl(cm).addAllImpMonitorUrl(pm).setLandUrl(landUrl);
	}

	private static boolean contains(List<String> keywords, String str) {
		boolean hit = false;
		for (String keyword : keywords) {
			if (str.contains(keyword)) {
				hit = true;
				break;
			}
		}
		return hit;
	}

	private static List<DspBid> filterBidsByCompetingKeyword(List<DspBid> dspBids) {
		if (CollectionUtils.isEmpty(dspBids))
			return Collections.emptyList();
		List<DspBid> newBids = new ArrayList<DspRouter.DspBid>();
		List<String> keywords = RepositoryFactory.getRepository().getAllCompetingKeyword();
		for (DspBid dspBid : dspBids) {
			AdMsg.Builder bid = dspBid.getBid();
			if (bid.hasNativeAd()) {
				NativeAd nativeAd = bid.getNativeAd();
				if (nativeAd.hasThreadTitle()) {
					String title = nativeAd.getThreadTitle();
					if (contains(keywords, title)) {
						// 如果title 中包含竞品关键字，则不能参与竞价。
						// TODO： 竞价日志如何记录被过滤错误码
						LOG.error("competing, dspid: {}, crid: {} title: {}", dspBid.dspId, dspBid.bid.getCrid(),
								title);
						continue;
					}
				}
				if (nativeAd.hasThreadContent()) {
					String desc = nativeAd.getThreadContent();
					if (contains(keywords, desc)) {
						LOG.error("competing, dspid: {}, crid: {} desc: {}", dspBid.dspId, dspBid.bid.getCrid(), desc);
						continue;
					}
				}
			}
			if (bid.hasTitle()) {
				String title = bid.getTitle().toStringUtf8();
				if (contains(keywords, title)) {
					// 如果title 中包含竞品关键字，则不能参与竞价。
					// todo： 竞价日志如何记录被过滤错误码
					LOG.error("competing, dspid: {}, crid: {} title: {}", dspBid.dspId, dspBid.bid.getCrid(), title);
					continue;
				}
			}
			if (bid.hasDescription()) {
				String desc = bid.getDescription();
				if (contains(keywords, desc)) {
					// 如果description 中包含竞品关键字，则不能参与竞价。
					LOG.error("competing, dspid: {}, crid: {} desc: {}", dspBid.dspId, dspBid.bid.getCrid(), desc);
					continue;
				}
			}
			newBids.add(dspBid);

		}
		return newBids;
	}

	private void addDspAdGettersToTasks(List<Callable<List<DspBid>>> tasks, List<Dsp> dspList, BiddingReq biddingReq) {
		if (dspList == null || dspList.isEmpty())
			return;
		BidRequest bidRequest = RtbUtils.toBidRequest(biddingReq);
		dspList.forEach(dsp -> {
			if (DspBrokerRegistry.getDspBroker(dsp.getDspId()) == null) {
				if (!dsp.getIsStandardProtocol()) {
					LOG.error("==没有找到对应dsp平台的Broker实现==");
					return;
				}
				// 如果dsp为测试模式，则设置底价为0，避免由于dsp平台出价低而过滤
				if (dsp.getIsTest()) {
					BidRequest.Builder bidrBd = BidRequest.newBuilder(bidRequest);
					bidrBd.getImpBuilderList().forEach(imp -> imp.setBidfloor(0F));
					tasks.add(new DspAdGetter(dsp, bidrBd.build(), biddingReq));
				} else {
					tasks.add(new DspAdGetter(dsp, bidRequest, biddingReq));
				}
			} else {
				tasks.add(new NonStandardRtbProtocolDspAdGetter(dsp, biddingReq));
			}
		});
	}

	private List<DspBid> filterBidsByImp(List<DspBid> dspBids, PosInfo imp, BiddingReq biddingReq) {
		if (CollectionUtils.isEmpty(dspBids))
			return Collections.emptyList();
		return dspBids.stream().filter(dspBid -> {
			if(!dspBid.bid.getImpid().equals(imp.getId())){
				DspLoggers.code(biddingReq,dspBid.getDspId(), CodeUtils.RESPONSE_NOT_REQUEST);
				return false;
			}
			return true;
		}).collect(Collectors.toList());
	}

	private DspBid selectWinBid(List<DspBid> seatbids) {
		// 选择出价获胜的一方，如果只有一方出价，计费价格按照底价计费
		List<DspBid> dspBids = new ArrayList<DspRouter.DspBid>();
		DspBid defWinBid = null;
		for (DspBid dspBid : seatbids) {
			String dealid = dspBid.bid.getPmp().getDealId().trim();
			if (!StringUtils.equals(Constants.PMP_DEALID, dealid)) {
				dspBids.add(dspBid);
			} else {
				defWinBid = dspBid;
			}
		}

		seatbids = dspBids;
		if (seatbids.isEmpty()) {
			if (defWinBid != null)
				return defWinBid;
			return null;
		}

		DspBid winBid = null;
		if (seatbids.size() == 1) {
			winBid = seatbids.get(0);
			if (winBid.dsp.getIsTest() || winBid.dsp.getIsFixedPrice()) {
				LOG.debug("win dspId: {}, winPrice: {}", winBid.dspId, winBid.bid.getBidPrice());
				return winBid;
			}
			// 如果只有一个匹配的广告，则计费价格设置为系统底价
			int bidfloor = (int) seatbids.get(0).bidfloor;
			seatbids.get(0).bid.setAdxBidPrice(bidfloor).setBidPrice(bidfloor);
			return seatbids.get(0);
		}
		// 如果多于一方出价，对多方出价按照出价从高到低进行排序，计费价格按照次高价进行计费
		LOG.debug("==对dsp平台竞价结果进行排序==");
		seatbids.sort((a, b) -> (int) (b.bid.getBidPrice() - a.bid.getBidPrice()));

		winBid = seatbids.get(0);
		// 如果dsp平台是固定价格售卖，则直接返回固定售卖价格
		if (winBid.dsp.getIsFixedPrice()) {
			return winBid;
		}

		// 如果是测试模式，直接按照一价扣费
		if (winBid.dsp.getIsTest()) {
			LOG.debug("win dspId: {}, winPrice: {}", winBid.dspId, winBid.bid.getBidPrice());
			return winBid;
		}
		// 这里选择次高价出价信息，也就是低于第一名出价的出价方
		for (int i = 1; i < seatbids.size(); i++) {
			if (seatbids.get(i).bid.getBidPrice() < winBid.bid.getBidPrice()) {
				winBid.next = seatbids.get(i);
				break;
			}
		}
		// 获胜价格取次高价，如果次高价不存在，则取媒体广告位底价
		int winPrice = winBid.next == null ? (int) winBid.bidfloor : winBid.next.bid.getBidPrice();

		// 如果次高价小于胜家底价，则取媒体广告位底价
		winPrice = winPrice < winBid.bidfloor ? (int) winBid.bidfloor : winPrice;

		winBid.bid.setAdxBidPrice(winPrice).setBidPrice(winPrice);
		LOG.debug("winBid: {}, dspId: {}", winBid.bid, winBid.dspId);
		return winBid;
	}

	static class DspBid {
		private String dspId;
		private AdMsg.Builder bid;
		private DspBid next;
		private double bidfloor;
		private Dsp dsp;

		private DspBid(String dspId, AdMsg.Builder adMsg) {
			this.dspId = dspId;
			this.bid = adMsg;
		}

		private DspBid(Dsp dsp, AdMsg.Builder adMsg) {
			this.dspId = dsp.getDspId();
			this.bid = adMsg;
			this.dsp = dsp;
		}

		private DspBid(Dsp dsp, AdMsg.Builder adMsg, double bidfloor) {
			this.dspId = dsp.getDspId();
			this.bid = adMsg;
			this.bidfloor = bidfloor;
			this.dsp = dsp;
		}

		public Dsp getDsp() {
			return dsp;
		}

		private DspBid(String dspId, AdMsg.Builder adMsg, double bidfloor) {
			this.dspId = dspId;
			this.bid = adMsg;
			this.bidfloor = bidfloor;
		}

		public static DspBid create(String dspId, AdMsg.Builder bid) {
			return new DspBid(dspId, bid);
		}

		public static DspBid create(String dspId, AdMsg.Builder bid, double bidfloor) {
			return new DspBid(dspId, bid, bidfloor);
		}

		public static DspBid create(Dsp dsp, AdMsg.Builder bid) {
			return new DspBid(dsp, bid);
		}

		public static DspBid create(Dsp dsp, AdMsg.Builder bid, double bidfloor) {
			return new DspBid(dsp, bid, bidfloor);
		}

		public void setNext(DspBid next) {
			this.next = next;
		}

		public String getDspId() {
			return dspId;
		}

		public AdMsg.Builder getBid() {
			return bid;
		}

		public double getBidfloor() {
			return bidfloor;
		}

		public void setBidfloor(double bidfloor) {
			this.bidfloor = bidfloor;
		}

		public DspBid getNext() {
			return next;
		}
	}

	static class DspAdGetter implements Callable<List<DspBid>> {
		private final BidRequest bidRequest;
		private final Dsp dsp;
		// <impid,adposid>
		private final Map<String, String> adPosIdMapper;
		private final BiddingReq biddingReq;
		private final boolean isDebug;

		public DspAdGetter(Dsp dsp, BidRequest bidRequest, BiddingReq biddingReq) {
			this.bidRequest = bidRequest;
			this.dsp = dsp;
			this.biddingReq = biddingReq;
			adPosIdMapper = new HashMap<>();
			bidRequest.getImpList().forEach(imp -> {
				adPosIdMapper.put(imp.getId(), imp.getTagid());
			});
			this.isDebug = biddingReq.getIsDebug();
		}

		@Override
		public List<DspBid> call() throws Exception {
			long s = System.currentTimeMillis();
			try {
				if (isDebug) {
					BiddingTracer.trace(isDebug, "标准dsp平台请求：{}, dspid: {}", TextFormat.printToUnicodeString(bidRequest),
							dsp.getDspId());
				}
				Request getAdReq = new Request.Builder().url(dsp.getRtbUrl())
						.post(RequestBody.create(contentType, bidRequest.toByteArray())).build();

				DspRateLimiter qpsRateLimiter = DspRateLimiters.get(dsp);
				// 如果超过平台qps限制则不发送请求，然后记录dsp平台qps超限请求次数
				if (qpsRateLimiter != null && !qpsRateLimiter.tryAcquire()) {
					DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.DSP_OVER_QPS);
					DspLoggers.overQps(biddingReq, dsp.getDspId());
					return null;
				}

				long s1 = System.currentTimeMillis();
				try (Response response = httpClient.newCall(getAdReq).execute()) {
					List<DspBid> dspBids = handleBidResponse(response);
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
				} else {
					DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.REQUEST_URL_ERROR);
					DspLoggers.error(biddingReq, dsp.getDspId());
				}
			} finally {
				LOG.debug("call dsp cost: {}ms", System.currentTimeMillis() - s);
			}
			return null;
		}

		private List<DspBid> handleBidResponse(Response response) {
			final List<DspBid> dspBids = new ArrayList<>();
			int statusCode = response.code();
			if (statusCode == 200) {
				try {
					if (response.body() == null || response.body().contentLength() == 0) {
						LOG.debug("广告平台不参与竞价, dspid: " + dsp.getDspId());
						DspLoggers.nobid(biddingReq, dsp.getDspId());
                        DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.RESPONSE_NOT_AD);
						return dspBids;
					}

					BidResponse.Builder bidResponse = BidResponse.newBuilder().mergeFrom(response.body().bytes());

					bidResponse.getSeatbidList().forEach(seatbid -> {
						seatbid.getBidList().forEach(bid -> {
							AdMsg.Builder adMsg = RtbUtils.toAdMsg(biddingReq, bid, dsp);
							if (adMsg == null) {
								LOG.warn("==dsp平台创意不存在, id: {}, dspId: {}==", bid.getCrid(), dsp.getDspId());
								DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.AD_NOT_CREATIVE);
								return;
							}

							String tmid = adMsg.getNativeAd().getTemplateId();
							if (StringUtils.isNotBlank(tmid)) {
								Imp imp = RtbUtils.getImp(bidRequest, bid);
								List<String> tmids = imp.getNativeAd().getTemplateIdList();

								if (!tmids.contains(tmid)) {
									LOG.error("原生广告模版不匹配, req tmids: {}, rsp tmid: {}", tmids, tmid);
									DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.AD_TEMPLATE_ERROR);
									return;
								}
							}
							adMsg.setSource(dsp.getSource());
							if (isDebug) {
								BiddingTracer.trace(isDebug, "标准dsp平台响应：{},dspid: {}",
										TextFormat.printToUnicodeString(adMsg), dsp.getDspId());
							}
							RtbUtils.handleAdMsg(adMsg, biddingReq);
							if (dsp.getIsFixedPrice()) {
								int fixedPrice = RtbUtils.getDspFixedPrice(biddingReq, dsp);
								adMsg.setBidPrice(fixedPrice).setPaidCpc(fixedPrice).setAdxBidPrice(fixedPrice);
							}
							DspLoggers.crid(biddingReq, dsp.getDspId(), adMsg.getCrid());
							DspLoggers.bidPrice(biddingReq, dsp.getDspId(), adMsg.getBidPrice());
							// 判断出价是否低于媒体广告位底价，如果低于底价则丢弃
							double bidfloor = RtbUtils.getBidfloor(dsp, biddingReq);
							if (bidPriceIsGreaterThanFloorPrice(bidfloor, adMsg.getBidPrice())) {
								dspBids.add(DspBid.create(dsp, adMsg, bidfloor));
							} else {
								DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.BID_LOWER_FLOOR_PRICE);
								DspLoggers.lowerFloor(biddingReq, dsp.getDspId());
							}
						});
					});
					if (CollectionUtils.isEmpty(dspBids)) {
						LOG.debug("广告平台响应处理异常,  dspid: {},响应内容：{}", dsp.getDspId(),
								StringUtils.toEncodedString(response.body().bytes(), Charsets.UTF_8));
						DspLoggers.nobid(biddingReq, dsp.getDspId());
                        DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.AD_HANDLER_ERROR);
					} else {
						logBids(biddingReq, dsp.getDspId(), dspBids);
					}
				} catch (Exception ex) {
					DspLoggers.error(biddingReq, dsp.getDspId());
					LOG.error("处理dsp平台竞价响应异常, cause: " + ex.getMessage());
				}
			} else if (statusCode == 204) {
				LOG.debug("广告平台不参与竞价, dspid: " + dsp.getDspId());
				DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.RESPONSE_NOT_BID);
				DspLoggers.nobid(biddingReq, dsp.getDspId());
			} else {
				LOG.warn("dsp平台出现异常, statusCode: {}, dspId: {}, rtbUrl: {}", statusCode, dsp.getDspId(),
						dsp.getRtbUrl());
				DspLoggers.code(biddingReq,dsp.getDspId(),CodeUtils.RESPONSE_OTHER);
				DspLoggers.error(biddingReq, dsp.getDspId());
			}
			return dspBids;
		}
	}

	private static boolean bidPriceIsGreaterThanFloorPrice(double floor, int bidPrice) {
		LOG.debug("==dsp平台出价：{}, 媒体广告位底价：{}==", bidPrice, floor);
		return bidPrice >= floor;
	}

	private static void logBids(BiddingReq biddingReq, String dspId, List<DspBid> dspBids) {
		DspBid dspBid = dspBids.get(0);
		String dealid = dspBid.getBid().getPmp().getDealId().trim();

		if (Constants.PMP_DEALID.equals(dealid)) {
			DspLoggers.nobid(biddingReq, dspId);
		} else {
			DspLoggers.bid(biddingReq, dspId);
		}
	}

}

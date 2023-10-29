/*
   * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.service;

import static com.iwanvi.adserv.ngx.util.Constants.COST_TYPES_ALL;
import static com.iwanvi.adserv.ngx.util.Constants.TIME_MILLIS_10;
import static com.iwanvi.adserv.ngx.util.Constants.TIME_MILLIS_15;
import static com.iwanvi.adserv.ngx.util.Constants.TIME_MILLIS_20;
import static com.iwanvi.adserv.ngx.util.Constants.TIME_MILLIS_25;
import static com.iwanvi.adserv.ngx.util.Constants.TIME_MILLIS_30;
import static com.iwanvi.adserv.ngx.util.Constants.TIME_MILLIS_5;
import static com.iwanvi.adserv.ngx.util.MinervaStatHolder.statInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.router.DspRouter;
import com.iwanvi.adserv.ngx.search.AdSearchers;
import com.iwanvi.adserv.ngx.search.ContextHolder;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.MinervaStatHolder.StatInfo;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingRsp;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingService.BlockingInterface;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingService.Interface;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;
import com.iwanvi.nvwa.proto.CommonProto.PutType;

/**
 * 广告实时竞价服务实现
 * 
 * @author wangwp
 */
public class BiddingServiceImpl implements BlockingInterface, Interface {
	private static final Logger LOG = LoggerFactory.getLogger("AdservingNGX");
	private static final AtomicInteger seq = new AtomicInteger();

	public BiddingServiceImpl() {
	}

	@Override
	public void search(RpcController controller, BiddingReq request, RpcCallback<BiddingRsp> done) {
		try {
			done.run(search(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	public BiddingRsp search(RpcController controller, BiddingReq request) throws ServiceException {
		long start = System.currentTimeMillis();
		try {
			PosInfo posInfo = request.getPosInfo(0);

			BiddingRsp.Builder biddingRspBuilder = BiddingRsp.newBuilder().setId(request.getId());
			BiddingReq.Builder cptOrderAdReq = BiddingReq.newBuilder(request).clearPosInfo()
					.addPosInfo(PosInfo.newBuilder(posInfo)
							.addExt(MapEntry.newBuilder().setKey(Constants.PUT_TYPE_EXT_KEY)
									.setValue(String.valueOf(PutType.kPutTypeOrder_VALUE)))
							.clearCostType().addCostType(CostType.kCpt));
			List<AdMsg> adMsgs = getAdMsgs(cptOrderAdReq.build());
			LOG.debug("获取cpt订单广告耗时：{}ms", System.currentTimeMillis() - start);
			if (CollectionUtils.isNotEmpty(adMsgs)) {
				biddingRspBuilder.addAllAdImps(adMsgs);
				return biddingRspBuilder.build();
			}

			long s1 = System.currentTimeMillis();
			BiddingReq.Builder nonCptOrderAdReq = BiddingReq.newBuilder(request).clearPosInfo().addPosInfo(PosInfo
					.newBuilder(posInfo).clearCostType().addAllCostType(Arrays.asList(CostType.kCpfm, CostType.kCpfc)));
			adMsgs = getAdMsgs(nonCptOrderAdReq.build());
			LOG.debug("获取非cpt订单广告耗时：{}ms", System.currentTimeMillis() - s1);
			if (CollectionUtils.isNotEmpty(adMsgs)) {
				biddingRspBuilder.addAllAdImps(adMsgs);
				return biddingRspBuilder.build();
			}

			s1 = System.currentTimeMillis();
			adMsgs = DspRouter.get().route(request);
			LOG.debug("获取RTB广告耗时：{}ms", System.currentTimeMillis() - s1);
			if (CollectionUtils.isNotEmpty(adMsgs)) {
				biddingRspBuilder.addAllAdImps(adMsgs);
				return biddingRspBuilder.build();
			}

			s1 = System.currentTimeMillis();
			PosInfo.Builder _posInfo = PosInfo.newBuilder(posInfo).clearCostType().addAllCostType(COST_TYPES_ALL)
					.addExt(MapEntry.newBuilder().setKey(Constants.PUT_TYPE_EXT_KEY)
							.setValue(String.valueOf(PutType.kPutTypeDefault_VALUE)));
			BiddingReq.Builder defaultAdReq = BiddingReq.newBuilder(request).clearPosInfo().addPosInfo(_posInfo);
			adMsgs = getAdMsgs(defaultAdReq.build());
			LOG.debug("获取抄底广告耗时：{}ms", System.currentTimeMillis() - s1);
			if (CollectionUtils.isNotEmpty(adMsgs)) {
				biddingRspBuilder.addAllAdImps(adMsgs);
				return biddingRspBuilder.build();
			}
			return biddingRspBuilder.build();
		} finally {
			if (seq.incrementAndGet() % 100 == 0)
				LOG.info("AdNgx search ad cost: {}ms", System.currentTimeMillis() - start);
		}
	}

	// @Override
	public BiddingRsp search1(RpcController controller, BiddingReq request) throws ServiceException {
		long start = System.currentTimeMillis();
		try {
			BiddingRsp.Builder biddingRspBuilder = BiddingRsp.newBuilder().setId(request.getId());

			Map<String, PosInfo> impHolder = new HashMap<>();
			request.getPosInfoList().forEach(imp -> impHolder.put(imp.getId(), imp));

			BiddingReq.Builder nonCptOrderBuilder = BiddingReq.newBuilder(request).clearPosInfo();

			for (PosInfo posInfo : request.getPosInfoList()) {
				PosInfo.Builder _posInfo = PosInfo.newBuilder(posInfo)
						.addExt(MapEntry.newBuilder().setKey(Constants.PUT_TYPE_EXT_KEY)
								.setValue(String.valueOf(PutType.kPutTypeOrder_VALUE)))
						.clearCostType().addCostType(CostType.kCpt);

				BiddingReq.Builder biddingReqBuilder = BiddingReq.newBuilder(request).clearPosInfo()
						.addPosInfo(_posInfo);
				BiddingTracer.trace(request.getIsDebug(), "==优先获取直投CPT订单广告==");
				List<AdMsg> adMsgs = getAdMsgs(biddingReqBuilder.build());
				if (CollectionUtils.isNotEmpty(adMsgs)) {
					impHolder.remove(posInfo.getId());
					biddingRspBuilder.addAllAdImps(adMsgs);
				} else {
					// 非包段的CPT广告
					nonCptOrderBuilder.addPosInfo(PosInfo.newBuilder(posInfo).clearCostType()
							.addAllCostType(Arrays.asList(CostType.kCpfm, CostType.kCpfc)));
				}
			}

			if (CollectionUtils.isNotEmpty(nonCptOrderBuilder.getPosInfoList())) {
				BiddingReq nonCptReq = nonCptOrderBuilder.build();
				BiddingTracer.trace(request.getIsDebug(), "==cpt方式订单广告未填充，获取非cpt计费方式的订单广告==");
				BiddingTracer.trace(request.getIsDebug(), "订单广告请求：{}", nonCptReq);
				List<AdMsg> adMsgs = getAdMsgs(nonCptReq);
				if (CollectionUtils.isNotEmpty(adMsgs)) {
					adMsgs.forEach(adMsg -> impHolder.remove(adMsg.getImpid()));
					biddingRspBuilder.addAllAdImps(adMsgs);
				}
			}

			if (!impHolder.isEmpty()) {
				BiddingTracer.trace(request.getIsDebug(), "==使用rtb广告来填充广告位==");
			}

			List<String> rtbImpidList = new ArrayList<>();
			impHolder.forEach((impid, imp) -> {
				BiddingReq.Builder biddingReq = BiddingReq.newBuilder(request).clearPosInfo().addPosInfo(imp);
				List<AdMsg> adMsgs = DspRouter.get().route(biddingReq.build());
				if (CollectionUtils.isNotEmpty(adMsgs)) {
					adMsgs.forEach(adMsg -> rtbImpidList.add(adMsg.getImpid()));
					biddingRspBuilder.addAllAdImps(adMsgs);
				}
			});

			rtbImpidList.forEach(impid -> impHolder.remove(impid));
			if (!impHolder.isEmpty()) {
				LOG.debug("==使用系统抄底广告来填充广告位==");
			}
			// 如果还有其他广告位没有填充，再判断是否有符合此广告位的抄底广告
			impHolder.forEach((impid, imp) -> {
				PosInfo.Builder _posInfo = PosInfo.newBuilder(imp).clearCostType().addAllCostType(COST_TYPES_ALL)
						.addExt(MapEntry.newBuilder().setKey(Constants.PUT_TYPE_EXT_KEY)
								.setValue(String.valueOf(PutType.kPutTypeDefault_VALUE)));
				BiddingReq.Builder biddingReq = BiddingReq.newBuilder(request).clearPosInfo().addPosInfo(_posInfo);
				List<AdMsg> adMsgs = getAdMsgs(biddingReq.build());
				if (adMsgs != null) {
					biddingRspBuilder.addAllAdImps(adMsgs);
				}
			});
			return biddingRspBuilder.build();
		} finally {
			long cost = System.currentTimeMillis() - start;
			StatInfo stat = statInfo();
			setBiddingTimeStatProperties(cost, stat);
			LOG.debug("AdNgx search ad cost: {}ms", System.currentTimeMillis() - start);
		}
	}

	private List<AdMsg> getAdMsgs(BiddingReq request) {
		BiddingContext context = BiddingContext.create(request);
		//context.trace("bidding request: {}", TextFormat.printToUnicodeString(request));
		ContextHolder.getInstance().setContext(context);

		try {
			AdSearchers.search(context);
			if (context.getBiddingRsp() == null) {
				return null;
			}
			BiddingRsp bidding_resp = context.getBiddingRsp();
			if (bidding_resp.getAdImpsList() == null || bidding_resp.getAdImpsList().isEmpty()) {
				return null;
			}
			//context.trace("bidding response: {}", TextFormat.printToUnicodeString(bidding_resp));
			return bidding_resp.getAdImpsList();
		} catch (Throwable ex) {
			LOG.error("", ex);
		} finally {
			// StatInfo stat = statInfo();
			// stat.biddingRequestCount.incrementAndGet();
		}
		return null;
	}

	@Override
	public void bid(RpcController controller, BiddingReq request, RpcCallback<BiddingRsp> done) {
		try {
			done.run(bid(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public BiddingRsp bid(RpcController controller, BiddingReq request) throws ServiceException {
		long start = System.nanoTime();
		BiddingContext context = new BiddingContext(request);
		context.trace("bidding request: {}", TextFormat.printToUnicodeString(request));
		ContextHolder.getInstance().setContext(context);

		try {
			AdSearchers.search(context);
			if (context.getBiddingRsp() == null) {
				return BiddingRsp.newBuilder().build();
			}

			BiddingRsp bidding_resp = context.getBiddingRsp();
			context.trace("bidding response: {}", TextFormat.printToUnicodeString(bidding_resp));
			return bidding_resp;
		} catch (Throwable ex) {
			LOG.error("", ex);
		} finally {
			long cost = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
			StatInfo stat = statInfo();
			stat.biddingRequestCount.incrementAndGet();
			setBiddingTimeStatProperties(cost, stat);
		}
		return BiddingRsp.newBuilder().build();
	}

	private void setBiddingTimeStatProperties(long cost, StatInfo stat) {
		stat.biddingTotalCostTime.addAndGet(cost);
		if (cost > TIME_MILLIS_30) {
			stat.biddingTimeoutCount.incrementAndGet();
			stat.biddingCost_gt_30msCount.incrementAndGet();
		}
		if (cost > TIME_MILLIS_25 && cost <= TIME_MILLIS_30) {
			stat.biddingCost_25_30msCount.incrementAndGet();
		}
		if (cost > TIME_MILLIS_20 && cost <= TIME_MILLIS_25) {
			stat.biddingCost_20_25msCount.incrementAndGet();
		}
		if (cost > TIME_MILLIS_15 && cost <= TIME_MILLIS_20) {
			stat.biddingCost_15_20msCount.incrementAndGet();
		}
		if (cost > TIME_MILLIS_10 && cost <= TIME_MILLIS_15) {
			stat.biddingCost_10_15msCount.incrementAndGet();
		}
		if (cost > TIME_MILLIS_5 && cost <= TIME_MILLIS_10) {
			stat.biddingCost_5_10msCount.incrementAndGet();
		}
		if (cost <= TIME_MILLIS_5) {
			stat.biddingCost_le_5msCount.incrementAndGet();
		}
	}
}

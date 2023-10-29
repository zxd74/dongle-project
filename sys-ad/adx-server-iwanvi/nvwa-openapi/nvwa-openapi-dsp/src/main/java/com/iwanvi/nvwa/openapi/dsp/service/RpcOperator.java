package com.iwanvi.nvwa.openapi.dsp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.f2time.albatross.rpc.client.AlbatrossRpcChannel;
import com.f2time.albatross.rpc.client.AlbatrossRpcClients;
import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest;
import com.googlecode.protobuf.format.JsonFormat;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq.Builder;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.BuiltinService;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAdReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAdResp;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingRsp;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingService;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;

public class RpcOperator {

	private static final Logger LOG = LoggerFactory.getLogger("RpcOperator");

	private static final String[] RPC_SERVER = PropertyGetter.getStringArray("rpc.server");
	private static final int RPC_TIMEOUT = PropertyGetter.getInt("rpc.timeout", 80);
	private static final int SOFA_RPC_POOL_SIZE = PropertyGetter.getInt("rpc.pool.size", 5);

	private static RpcOperator instance = null;
	private BiddingService.BlockingInterface service = null;
	private AlbatrossRpcChannel channel = null;

	private RpcOperator() {
		try {
			channel = AlbatrossRpcClients.newMultiRpcChannel(RPC_SERVER, SOFA_RPC_POOL_SIZE);
			service = BiddingService.newBlockingStub(channel);
		} catch (Exception e) {
			LOG.warn("RpcOperator init failed: ", e);
		}
	}

	public static synchronized RpcOperator getInstance() {
		if (instance == null) {
			instance = new RpcOperator();
		}
		return instance;
	}

	public List<AdMsg> getAdMsgs(String reqId, List<PosInfo> posInfos, UserInfo userInfo) {
		return getAdMsgs(reqId, false, posInfos, userInfo);
	}
	
	public List<AdMsg> getAdMsgs(String reqId, boolean debugEnabled,List<PosInfo> posInfos, UserInfo userInfo) {
		List<AdMsg> adMsgs = null;
		long l1 = System.currentTimeMillis();
		long invokeRpcCostTime = 0L;
		try {
			Builder builder = BiddingReq.newBuilder().setIsDebug(debugEnabled);
			builder.setId(reqId).setUserInfo(userInfo);
			for (PosInfo posInfo : posInfos) {
				builder.addPosInfo(posInfo);
			}
			BiddingReq req = builder.build();
			LOG.info("getAdMsgs req: {}", JsonFormat.printToString(req));

			BiddingRsp rsp = service.search(AlbatrossRpcClients.newRpcController(RPC_TIMEOUT), req);
			if(rsp == null){
				return null;
			}
			LOG.info("getAdMsgs rsp: {}", JsonFormat.printToString(rsp));
			adMsgs = rsp.getAdImpsList();
		} catch (Exception e) {
			LOG.error("RpcOperator getAdMsgs error. elapsed: {}", (System.currentTimeMillis() - l1), e);
		}
		long elapsed = System.currentTimeMillis() - l1;
		if (elapsed > RPC_TIMEOUT) {
			LOG.error("RpcOperator getAdMsgs warnning. elapsed: {}, real invokeRpcService cost: {}", elapsed,
					invokeRpcCostTime);
		}
		return adMsgs;
	}
	
	public String getEngineData(int unitId) {
		String result = null;
		try {
			BuiltinService.BlockingInterface builtinService = BuiltinService.newBlockingStub(channel);
			GetAdResp resp = builtinService.getAd(
					AlbatrossRpcClients.newRpcController(RPC_TIMEOUT), GetAdReq.newBuilder().setUnitId(unitId).build());
			
			LOG.info("getEngineData resp: {}", resp);
			
			if (resp == null || StringUtils.isBlank(resp.toString())) {
				return null;
			}
			result = JsonFormat.printToString(resp);
		} catch (Exception e) {
			LOG.error("getEngineData error. unitId: {}", unitId, e);
		}
		return result;
	}

	public void test(PosInfo posInfo, UserInfo userInfo) {
		try {
			AlbatrossRpcChannel channel = AlbatrossRpcClients.newSingleRpcChannel("123.57.145.7:13321");

			BiddingService.BlockingInterface service = BiddingService.newBlockingStub(channel);
			BiddingReq reqAd = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).build();

			BiddingRsp rsp = service.search(AlbatrossRpcClients.newRpcController(), reqAd);

			List<AdMsg> adMsgs = rsp.getAdImpsList();
			System.out.println("=================" + adMsgs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<AdMsg> getAdMsgs(AdRequest adRequest, List<PosInfo> posInfos, UserInfo userInfo) {
		return getAdMsgs(adRequest.getId(), adRequest.isDebugOn(), posInfos, userInfo);
	}
	
}

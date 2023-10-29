/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.boot.test;

import com.f2time.albatross.rpc.client.AlbatrossRpcChannel;
import com.f2time.albatross.rpc.client.AlbatrossRpcClients;
import com.google.protobuf.ByteString;
import com.google.protobuf.RpcController;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingRsp;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingService;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.BuiltinService;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAdReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAdResp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspCreativeReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspCreativeRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspRsp;
import com.iwanvi.nvwa.proto.CommonProto.AdxType;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.OSType;
import com.iwanvi.nvwa.proto.CommonProto.TerminalType;

/**
 * 
 * @author wangwp
 */
public class AdServNgxTest {
	public static void main(String[] args) {
	
		AlbatrossRpcChannel channel = AlbatrossRpcClients.newSingleRpcChannel("52.83.111.62:13321");
		//AlbatrossRpcChannel channel = AlbatrossRpcClients.newSingleRpcChannel("123.57.145.7:13321");
		BiddingService.BlockingInterface biddingService = BiddingService.newBlockingStub(channel);

		RpcController ctl = AlbatrossRpcClients.newRpcController(5000);
		UserInfo.Builder user = UserInfo.newBuilder().setAdxType(AdxType.kAdxTypeUnKnown).setIp("39.106.2.151")
				.setMuid(ByteString.copyFromUtf8("1741000000000670")).setMediaUuid("M7nuyi")
				.setTerminalType(TerminalType.kMobileTerminal).setOs(OSType.kAndroid).setAreaCode(110000).setConnectType(ConnectType.kWifi);
		
		
		PosInfo.Builder pos = PosInfo.newBuilder().setPosId(ByteString.copyFromUtf8("7nuMbi"))
				.addCreativeType(CreativeType.kPic).setAppId(ByteString.copyFromUtf8("iaQv6n")).setWidth(640)
				.setHeight(320).setId(UUIDUtils.uuid()).setAdNum(2);

		BiddingReq.Builder bidding_req = BiddingReq.newBuilder().setId(UUIDUtils.uuid()).addPosInfo(pos.build())
				.setUserInfo(user.build());

		BuiltinService.BlockingInterface builtinService = BuiltinService.newBlockingStub(channel);
		try {
			GetAdResp resp = builtinService.getAd(ctl, GetAdReq.newBuilder().setUnitId(97).build());
			System.out.println(resp);
//			GetDspRsp getDspRsp = builtinService.getDsp(ctl, GetDspReq.newBuilder().setDspId("juUfmm").build());
//			GetDspCreativeRsp dspCreativeRsp = builtinService.getDspCreative(ctl,
//					GetDspCreativeReq.newBuilder().setCreativeId("rtb-test-creative").setDspId("juUfmm").build());
//
			BiddingRsp biddingRsp = biddingService.search(ctl, bidding_req.build());
//
			System.out.println(biddingRsp.toString());
//			System.out.println("dsp: " + getDspRsp.toString());
//			System.out.println("dsp creative: " + dspCreativeRsp.toString());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}

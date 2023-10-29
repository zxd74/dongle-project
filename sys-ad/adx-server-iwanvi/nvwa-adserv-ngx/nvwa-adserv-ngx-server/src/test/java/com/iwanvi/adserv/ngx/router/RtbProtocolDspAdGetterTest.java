package com.iwanvi.adserv.ngx.router;

import static org.junit.Assert.*;

import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.f2time.albatross.rpc.client.AlbatrossRpcClients;
import com.google.protobuf.ByteString;
import com.google.protobuf.RpcController;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingRsp;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingService;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.DeviceType;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;
import com.iwanvi.nvwa.proto.CommonProto.OSType;

public class RtbProtocolDspAdGetterTest {
	static BiddingService.BlockingInterface biddingService;
	static RpcController controller;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		biddingService = BiddingService.newBlockingStub(AlbatrossRpcClients.newSingleRpcChannel("39.96.76.10:13321"));
		controller = AlbatrossRpcClients.newRpcController(10000);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.exit(0);
	}

	public BiddingReq buildBannerAdReq(String tagId, int w, int h) {
		return null;
	}

	public BiddingReq buildNativeAdReq(String tagId) {
		BiddingReq.Builder builder = BiddingReq.newBuilder().setId(UUIDUtils.uuid()).setIsDebug(true);
		// PosInfo.Builder p = PosInfo.newBuilder();
		// UserInfo.Builder u = UserInfo.newBuilder();

		PosInfo posInfo = PosInfo.newBuilder().setHeight(210).setWidth(320).addCreativeType(CreativeType.kNative)
				.setId(UUIDUtils.uuid()).setAppName("免费追书").setAppVersion("1.0.0")
				.setPosId(ByteString.copyFromUtf8(tagId)).setAppId(ByteString.copyFromUtf8("7r2MNf"))
				.setBundle("com.mianfeizs.book").setHeight(1280).setWidth(720).build();

		UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
				.setDeviceType(DeviceType.kPhone).setIp("203.100.81.50").setOs(OSType.kAndroid)
				.setMuid(ByteString.copyFromUtf8("862791034585233")).setMediaUuid("e6jaAn")
				.setDeviceBrand(ByteString.copyFromUtf8("huawei")).setDeviceModel(ByteString.copyFromUtf8("huawei p30"))
				.setMac("02:00:00:00:00:00").addExt(MapEntry.newBuilder().setKey("dx_channel").setValue("000000"))
				.addExt(MapEntry.newBuilder().setKey("dx_app").setValue("7r2MNf"))
				.setUa("Mozilla/5.0 (Linux; Android 7.0; BND-TL10 Build/HONORBND-TL10; wv) AppleWebKit/537.36 "
						+ "(KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36 MicroMessenger/7.0.3.1400(0x2700033C) "
						+ "Process/appbrand0 NetType/4G Language/zh_CN")
				.build();

		builder.addPosInfo(posInfo).setUserInfo(userInfo);
		return builder.build();
	}

//	@Test
	public void testYimaDsp() throws Exception {
		BiddingReq biddingReq = buildNativeAdReq("qM7Jza");
		BiddingRsp biddingRsp = biddingService.search(controller, biddingReq);

		System.out.println(TextFormat.printToUnicodeString(biddingRsp));
	}

//	@Test
	public void printBidrequest(){
		BiddingReq biddingReq = buildNativeAdReq("qM7Jza");
		AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder()
				.addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("7B3A4B068DEF").setDspId("test"))
				.setAdPosId("qM7Jza").setBidfloor(400).setAdType(CreativeType.kNative).build();
		RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
		NvwaRtb.BidRequest bidRequest = RtbUtils.toBidRequest(biddingReq);
		StringBuilder stringBuilder = new StringBuilder();
		for (byte b:bidRequest.toByteArray()){
			stringBuilder.append(b);
		}
		System.out.println(stringBuilder.toString());
		System.out.println(TextFormat.printToString(bidRequest));
	}
}

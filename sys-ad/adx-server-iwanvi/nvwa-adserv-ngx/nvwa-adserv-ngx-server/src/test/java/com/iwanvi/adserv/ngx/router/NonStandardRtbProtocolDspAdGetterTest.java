/**
 *
 */
package com.iwanvi.adserv.ngx.router;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.f2time.albatross.rpc.client.AlbatrossRpcChannel;
import com.f2time.albatross.rpc.client.AlbatrossRpcClients;
import com.f2time.albatross.rpc.client.AlbatrossRpcController;
import com.google.protobuf.ByteString;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.DspRouter.DspBid;
import com.iwanvi.adserv.ngx.router.api.DspBrokerRegistry;
import com.iwanvi.adserv.ngx.router.broker.adview.AdViewDspBroker;
import com.iwanvi.adserv.ngx.service.BidFloorResolver;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.AdModelsProto.App.AppMapping;
import com.iwanvi.nvwa.proto.AdModelsProto.ChannelTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp.DspAdPositionPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.DspTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition.AdPositionMapping;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingRsp;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingService;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.BuiltinService;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspCreativeReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetSspAdPositionReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetSspAdPositionRsp;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.DeviceType;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;
import com.iwanvi.nvwa.proto.CommonProto.OSType;

/**
 * @author wangweiping
 *
 */
public class NonStandardRtbProtocolDspAdGetterTest {

    /**
     * Test method for
     * {@link com.iwanvi.adserv.ngx.router.NonStandardRtbProtocolDspAdGetter#call()}.
     */
//    @Test
    public void testAdview() throws Exception {
        Dsp.Builder builder = Dsp.newBuilder().setDspId("6jE3Mv").setQps(0)
                .setRtbUrl("http://open.adview.cn/agent/openRequest.do").setStatus(true)
                .setDspTarget(DspTarget.newBuilder()
                        .addAdPosTargets(AdPositionTarget.newBuilder().addAllTagids(Arrays.asList("Ab67Rv")))
                        .setChannelTarget(ChannelTarget.newBuilder().addChannelIds("1056")))
                .setIsFixedPrice(true)
                .addDspAdPositionPrice(DspAdPositionPrice.newBuilder().setPrice(400).setTagid("Ab67Rv").build());

        SspAdPosition sspAdPosition = SspAdPosition.newBuilder()
                .addAdPositionMappings(AdPositionMapping.newBuilder().setDspAdPositionId("SDK201914280206511ro8s4zpisnggj2_POSIDpk3kmo9owxg2")
                        .setDspId(builder.getDspId()).setDspAdType(CreativeType.kScreen))
                .setAdPosId("Ab67Rv").setBidfloor(400).setAdType(CreativeType.kNative).build();

        App app = App.newBuilder().setAppId("qqInyy")
                .addAppMapping(
                        AppMapping.newBuilder().setDspAppid("SDK201914280206511ro8s4zpisnggj2").setDspid("6jE3Mv"))
                .build();

        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
        RepositoryFactory.getRepository().saveOrUpdateApp(app);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(210).setWidth(320).addCreativeType(CreativeType.kNative)
                .setId(UUIDUtils.uuid()).setAppName("爱看书").setAppVersion("5.1.0")
                .setPosId(ByteString.copyFromUtf8("Ab67Rv")).setAppId(ByteString.copyFromUtf8("7r2MNf")).build();

        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setDeviceType(DeviceType.kPhone).setIp("203.100.81.50").setOs(OSType.kAndroid)
                .setMuid(ByteString.copyFromUtf8("862791034585233")).setMediaUuid("e6jaAn")
                .setDeviceBrand(ByteString.copyFromUtf8("huawei")).setDeviceModel(ByteString.copyFromUtf8("huawei p30"))
                .setMac("02:00:00:00:00:00").addExt(MapEntry.newBuilder().setKey("dx_channel").setValue("10021"))
                .addExt(MapEntry.newBuilder().setKey("dx_app").setValue("7r2MNf"))
                .setUa("Mozilla/5.0 (Linux; Android 7.0; BND-TL10 Build/HONORBND-TL10; wv) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36 MicroMessenger/7.0.3.1400(0x2700033C) "
                        + "Process/appbrand0 NetType/4G Language/zh_CN")
                .build();

        DspBrokerRegistry.registerDspBroker(new AdViewDspBroker());
        BiddingReq.Builder brb = BiddingReq.newBuilder().setIsDebug(false).addPosInfo(posInfo).setUserInfo(userInfo)
                .setId(UUIDUtils.uuid());

        NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),
                brb.build());
        List<DspBid> dspBids = adGetter.call();
        if (dspBids != null) {
            dspBids.forEach(dspBid -> {
                System.out.println(TextFormat.printToUnicodeString(dspBid.getBid()));
            });
        }
    }

    //	@Test
    public void testInmobi() throws Exception {
        AlbatrossRpcChannel channel = AlbatrossRpcClients.newSingleRpcChannel("39.96.76.10:13321");
        BiddingService.BlockingInterface biddingService = BiddingService.newBlockingStub(channel);

        BuiltinService.BlockingInterface builtinService = BuiltinService.newBlockingStub(channel);
        AlbatrossRpcController controller = AlbatrossRpcClients.newRpcController(50000);

        System.out.println("获取开屏广告位映射");
        GetSspAdPositionRsp rsp = builtinService.getSspAdPosition(controller,
                GetSspAdPositionReq.newBuilder().setAdslotId("VRBf6n").build());
        System.out.println(rsp);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(210).setWidth(320).addCreativeType(CreativeType.kNative)
                .setId(UUIDUtils.uuid()).setAppName("免费追书").setAppVersion("4.6.0")
                .setPosId(ByteString.copyFromUtf8("Bz2am2")).setAppId(ByteString.copyFromUtf8("7r2MNf"))
                .setBundle("com.mianfeizs.book").build();

        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setDeviceType(DeviceType.kPhone).setIp("203.100.81.50").setOs(OSType.kAndroid)
                .setMuid(ByteString.copyFromUtf8("862791034585233")).setMediaUuid("e6jaAn")
                .setDeviceBrand(ByteString.copyFromUtf8("huawei")).setDeviceModel(ByteString.copyFromUtf8("huawei p30"))
                .setMac("02:00:00:00:00:00").addExt(MapEntry.newBuilder().setKey("dx_channel").setValue("10008"))
                .addExt(MapEntry.newBuilder().setKey("dx_app").setValue("7r2MNf"))
                .setUa("Mozilla/5.0 (Linux; Android 7.0; BND-TL10 Build/HONORBND-TL10; wv) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36 MicroMessenger/7.0.3.1400(0x2700033C) "
                        + "Process/appbrand0 NetType/4G Language/zh_CN")
                .build();

        System.out.println("==获取inmobi开屏广告==");
        BiddingRsp biddingRsp = biddingService.search(controller,
                BiddingReq.newBuilder().setUserInfo(userInfo).addPosInfo(posInfo).setId(UUIDUtils.uuid()).build());
        System.out.println(TextFormat.printToUnicodeString(biddingRsp));
    }

    //	@Test
    public void testIflytek() throws Exception {
        Integer haha = 235;
        int kk = 235;

        System.out.println(haha == kk);
        Dsp.Builder builder = Dsp.newBuilder().setDspId("2MFfYj").setQps(0).setRtbUrl("http://mt.voiceads.cn/s/req")
                .setStatus(true)
                .setDspTarget(DspTarget.newBuilder()
                        .addAdPosTargets(AdPositionTarget.newBuilder().addAllTagids(Arrays.asList("zyqEne")))
                        .setChannelTarget(ChannelTarget.newBuilder().addChannelIds("10024")));

        DspIndexUtils.indexDsp(builder.build());
        List<Long> dspIndexKeys = DspIndexUtils.indexKeys(builder.build());
        System.out.println(dspIndexKeys);

        SspAdPosition sspAdPosition = SspAdPosition.newBuilder()
                .addAdPositionMappings(AdPositionMapping.newBuilder()
                        .setDspAdPositionId("F10C86B422E218C08B1BD82A75D439FC").setDspId(builder.getDspId()))
                .setAdPosId("zyqEne").build();

        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(210).setWidth(320).addCreativeType(CreativeType.kNative)
                .setId(UUIDUtils.uuid()).setAppName("免费追书").setAppVersion("1.0.0")
                .setPosId(ByteString.copyFromUtf8("e6jaAn")).setAppId(ByteString.copyFromUtf8("7r2MNf")).build();

        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setDeviceType(DeviceType.kPhone).setIp("203.100.81.50").setOs(OSType.kAndroid)
                .setMuid(ByteString.copyFromUtf8("862791034585233")).setMediaUuid("e6jaAn")
                .setDeviceBrand(ByteString.copyFromUtf8("huawei")).setDeviceModel(ByteString.copyFromUtf8("huawei p30"))
                .setMac("02:00:00:00:00:00").addExt(MapEntry.newBuilder().setKey("dx_channel").setValue("10021"))
                .addExt(MapEntry.newBuilder().setKey("dx_app").setValue("7r2MNf"))
                .setUa("Mozilla/5.0 (Linux; Android 7.0; BND-TL10 Build/HONORBND-TL10; wv) AppleWebKit/537.36 "
                        + "(KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36 MicroMessenger/7.0.3.1400(0x2700033C) "
                        + "Process/appbrand0 NetType/4G Language/zh_CN")
                .build();

        BiddingReq.Builder brb = BiddingReq.newBuilder().setIsDebug(true).addPosInfo(posInfo).setUserInfo(userInfo)
                .setId(UUIDUtils.uuid());

        List<Long> keys = DspIndexUtils.genIndexKeysByBiddingReq(brb.build());
        System.out.println("bidding request index keys: " + keys);

        AlbatrossRpcChannel channel = AlbatrossRpcClients.newSingleRpcChannel("39.96.76.10:13321");
        BiddingService.BlockingInterface biddingService = BiddingService.newBlockingStub(channel);

        BuiltinService.BlockingInterface builtinService = BuiltinService.newBlockingStub(channel);

        GetDspRsp getDspRsp = builtinService.getDsp(AlbatrossRpcClients.newRpcController(1000000),
                GetDspReq.newBuilder().setDspId("e6jaAn").build());
        System.out.println(getDspRsp);

        GetDspCreativeReq dcr = GetDspCreativeReq.newBuilder().setCreativeId("1000001100024808").setDspId("e6jaAn")
                .build();
        System.out.println(builtinService.getDspCreative(AlbatrossRpcClients.newRpcController(5000), dcr));

        BiddingRsp biddingRsp = biddingService.search(AlbatrossRpcClients.newRpcController(10000), brb.build());
        System.out.println(TextFormat.printToUnicodeString(biddingRsp));

//		NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),
//				brb.build());
//		NonStandardRtbProtocolDspAdGetter.setBidFloorResolver(new BidFloorResolver() {
//			@Override
//			public double getBidFloor(BiddingReq biddingReq, AdMsg bid, String dspId) {
//				return 40;
//			}
//		});
//
//		try {
//
//			List<DspBid> dspBids = adGetter.call();
//			if (dspBids != null) {
//				dspBids.forEach(dspBid -> {
//					System.out.println(TextFormat.printToUnicodeString(dspBid.getBid()));
//				});
//			}
//		} catch (Exception ex) {
//
//		}
    }

    //	@Test
    public void testCall() {
        Dsp.Builder builder = Dsp.newBuilder().setDspId("2aqe6n").setQps(0).setRtbUrl("http://mt.voiceads.cn/s/req")
                .setStatus(true);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(500).setWidth(600).addCreativeType(CreativeType.kPic)
                .setId(UUIDUtils.uuid()).setToutiaoCreativeType(1).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setIp("203.100.81.50").setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("862791034585233"))
                .setUa("Mozilla/5.0 (Linux; Android 7.0; BND-TL10 Build/HONORBND-TL10; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36 MicroMessenger/7.0.3.1400(0x2700033C) Process/appbrand0 NetType/4G Language/zh_CN")
                .build();

        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo)
                .setId(UUIDUtils.uuid());
        NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),
                brb.build());

        /// 注意这个地方在测试的时候需要设置一个测试专用的BidFloorResolver, 直接返回一个固定底价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver(new BidFloorResolver() {
            @Override
            public double getBidFloor(BiddingReq biddingReq, AdMsg bid, String dspId) {
                return 1;
            }
        });

        try {
            List<DspBid> dspBids = adGetter.call();
            if (dspBids != null) {
                dspBids.forEach(dspBid -> {
                    System.out.println(dspBid.getBid().build().toString());
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // @Test
    public void testNativeAd() {
        Dsp.Builder builder = Dsp.newBuilder().setDspId("2aqe6n").setQps(0)
                .setRtbUrl("https://dsp.toutiao.com/api/common/ads/fetch").setStatus(true);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(180).setWidth(240).addCreativeType(CreativeType.kNative)
                .setAdType(CreativeType.kNative).setId(UUIDUtils.uuid()).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setIp("203.100.81.50").setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("862791034585233"))
                .setUa("Mozilla/5.0 (Linux; Android 7.0; BND-TL10 Build/HONORBND-TL10; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36 MicroMessenger/7.0.3.1400(0x2700033C) Process/appbrand0 NetType/4G Language/zh_CN")
                .build();

        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo)
                .setId(UUIDUtils.uuid());
        NonStandardRtbProtocolDspAdGetter toutiaoAdGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),
                brb.build());

        try {
            List<DspBid> dspBids = toutiaoAdGetter.call();
            dspBids.forEach(dspBid -> {
                System.out.println(dspBid.getBid().build().toString());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import org.junit.Test;

import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author: 郑晓东
 * @createtime: 2019-07-31 14:49
 * @version: v1.0
 * @description:
 */
public class YouJuDspTest {

//    @Test
    public void YouJuAdTest() throws Exception {

        Dsp.Builder builder = Dsp.newBuilder().setDspId("ZrYNvq").setQps(0).setRtbUrl("http://sspapi.uxinad.com/ssp/apiout/out").setStatus(true);
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("JVZzei").setPrice(600));
        // DSP渠道
        builder.setDspTarget(DspTarget.newBuilder().addAdPosTargets(AdPositionTarget.newBuilder().addAllTagids(Arrays.asList("JVZzei"))).setChannelTarget(ChannelTarget.newBuilder().addChannelIds("1070")));
        // 关系映射本地测试需要配置
        AdModelsProto.App app = AdModelsProto.App.newBuilder().addAppMapping(AdModelsProto.App.AppMapping.newBuilder().setDspAppid("199").setDspid("ZrYNvq").build()).setAppId("EzqIze").build();
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("183").setDspId("ZrYNvq")).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
        RepositoryFactory.getRepository().saveOrUpdateApp(app);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(320).setWidth(480).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("5.1.0").setBundle("com.mianfeia.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("JVZzei")).setAdType(CreativeType.kScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi).setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setOsv("8.0.0").setAdid("dec9ec889d463a55").setScreenHeight(1920).setScreenWidth(1080).setIp("123.12.33.44")
                .setDeviceModel(ByteString.copyFromUtf8("Redmi Note 3")).setDeviceBrand(ByteString.copyFromUtf8("xiaomi"))
                .setDensity(480).setLat(39.932859).setLon(116.411144).setDeviceType(DeviceType.kPhone).setMac("10:2a:b3:a7:8d:3a")
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setId(UUIDUtils.uuid()).setIsDebug(true);

        // 设置mock的 BidFloorResolver 直接返回低价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);
        NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),brb.build());
        DspLoggers.registerLogger(brb.build());
        List<DspRouter.DspBid> dspBids = adGetter.call();
        assert dspBids == null;
//        dspBids.forEach(dspBid ->System.out.println(dspBid.getBid().build().toString()));
    }
}

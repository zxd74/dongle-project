package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.junit.Test;

import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-06-05 14:53
 * @version: v1.0
 * @Description: 安沃单元测试
 */
public class AdwoDspTest {

//    @Test
    public void call(){
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);

        Dsp.Builder builder = Dsp.newBuilder().setDspId("nmiqIb").setQps(0).setRtbUrl("http://a-ad.adwo.com:18080/rapi").setStatus(true);

//        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("Bz2am2").setPrice(800));

        SspAdPosition sspAdPosition = SspAdPosition.newBuilder()
                .addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("81299d1997d7472fb58f944f04674a2a").setDspId(builder.getDspId()).setWidth(640).setHeight(960))
                .setAdPosId("Bz2am2").setBidfloor(1).setAdType(CreativeType.kNative).build();

        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(960).setWidth(640).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("7r2MNf")).setAppName("免费追书").setAppVersion("4.6.1").setBundle("com.mianfeizs.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("Bz2am2")).setAdType(CreativeType.kFirstScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setIp("125.33.24.122")
                .setOsv("8.0.0")
                .setScreenHeight(1920)
                .setScreenWidth(1080)
                .setDensity(1.0)
                .setAdid("25c9784052ef298e")
                .setTerminalType(TerminalType.kMobileTerminal)
                .setDeviceType(DeviceType.kPhone)
                .setDeviceModel(ByteString.copyFromUtf8("Xiaomi"))
                .setDeviceBrand(ByteString.copyFromUtf8("MI 6"))
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setIsDebug(true);
        NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),brb.build());
        DspLoggers.registerLogger(brb.build());
        try {
            List<DspRouter.DspBid> dspBids = adGetter.call();
            dspBids.forEach(dspBid -> {
                System.out.println(dspBid.getBid().build().toString());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

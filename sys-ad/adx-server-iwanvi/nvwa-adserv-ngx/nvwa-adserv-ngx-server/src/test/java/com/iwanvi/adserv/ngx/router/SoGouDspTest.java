package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.UUIDUtils;

import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;
import com.iwanvi.nvwa.proto.CommonProto.OSType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-07-05 14:06
 * @version: v1.0
 * @Description:
 */
public class SoGouDspTest {

//    @Test
//    @ExceptionType(Exception.class)
    public void call() throws Exception {
        // 设置mock的 BidFloorResolver 直接返回低价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);
        Dsp.Builder builder = Dsp.newBuilder().setDspId("muuIBj").setQps(0).setRtbUrl("https://e.sogou.com/ads").setStatus(true);
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("JVZzei").setPrice(600));
        // DSP渠道
        builder.setDspTarget(DspTarget.newBuilder()
                .addAdPosTargets(AdPositionTarget.newBuilder().addAllTagids(Arrays.asList("JVZzei")))
                .setChannelTarget(ChannelTarget.newBuilder().addChannelIds("1070")));

        // 关系映射本地测试需要配置
        SspAdPosition sspAdPosition = SspAdPosition.newBuilder().addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("0004211").setDspId(builder.getDspId())).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(960).setWidth(640).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("5.0.2").setBundle("com.mianfeia.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("JVZzei")).setAdType(CreativeType.kFirstScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setOsv("8.0.0").setAdid("25c9784052ef298e")
                .setScreenHeight(1920).setScreenWidth(1080)
                .setDeviceModel(ByteString.copyFromUtf8("Xiaomi"))
                .setDeviceBrand(ByteString.copyFromUtf8("MI 6")).setDensity(480).setLat(39.932859).setLon(116.411144)
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setIsDebug(true);
        NonStandardRtbProtocolDspAdGetter threeSixAdGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),brb.build());
        DspLoggers.registerLogger(brb.build());
        List<DspRouter.DspBid> dspBids = threeSixAdGetter.call();
        dspBids.forEach(dspBid -> {
            System.out.println(dspBid.getBid().build().toString());
        });
    }
}

package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import org.junit.Test;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-06-19 11:02
 * @version: v1.0
 * @Description: 神奇互娱DSP 对接测试
 */
public class SQHYDspTest {

//    @Test
    public void call() throws Exception {
        Dsp.Builder builder = Dsp.newBuilder().setDspId("iMviQv").setQps(0).setRtbUrl("http://api.isqhy.com/open-api").setStatus(true);
        SspAdPosition sspAdPosition = SspAdPosition.newBuilder().addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("4668024d641c3c0c4f69be69b312684b").setDspId(builder.getDspId()))
                .setAdPosId("JVZzei").setBidfloor(1).setAdType(CreativeType.kNative).build();

        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("JVZzei").setPrice(20));
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(buildPosInfo()).setUserInfo(buildUserInfo()).setIsDebug(true).setId(UUIDUtils.uuid());
        NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),brb.build());
        DspLoggers.registerLogger(brb.build());

        List<DspRouter.DspBid> dspBids = adGetter.call();
        dspBids.forEach(dspBid -> System.out.println(dspBid.getBid().build().toString()));
    }

    private UserInfo buildUserInfo(){
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi).setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setIp("125.33.24.122").setOsv("8.0.0").setScreenHeight(1920).setScreenWidth(1080).setDensity(1.0).setAdid("25c9784052ef298e").setTerminalType(TerminalType.kMobileTerminal).setDeviceType(DeviceType.kPhone)
                .setDeviceModel(ByteString.copyFromUtf8("Xiaomi")).setDeviceBrand(ByteString.copyFromUtf8("MI 6")).setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        return userInfo;
    }
    private PosInfo buildPosInfo(){
        PosInfo posInfo = PosInfo.newBuilder().setHeight(720).setWidth(1280).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("4.6.1").setBundle("com.mianfei.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("JVZzei")).setAdType(CreativeType.kFocusPic).build();
        return posInfo;
    }
}

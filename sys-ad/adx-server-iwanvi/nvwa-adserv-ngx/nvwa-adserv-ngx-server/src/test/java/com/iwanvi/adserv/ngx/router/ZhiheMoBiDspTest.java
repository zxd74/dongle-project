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
 * @date: 2019-06-13 09:10
 * @version: v1.0
 * @Description: 智合移动测试类：固价，需要app、pos映射
 */
public class ZhiheMoBiDspTest {


//    @Test
    public void call(){

        Dsp.Builder builder = Dsp.newBuilder().setDspId("aya6Jv").setQps(0).setRtbUrl("http://api.zhihemobi.com/v2/getjson").setStatus(true);
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("7b2aia").setPrice(600));
        SspAdPosition sspAdPosition = SspAdPosition.newBuilder()
                .addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("5062968").setDspId(builder.getDspId()).setWidth(640).setHeight(960))
                .setAdPosId("7b2aia").setBidfloor(1).setAdType(CreativeType.kNative).build();
        App app = App.newBuilder().setAppId("EzqIze").addAppMapping(App.AppMapping.newBuilder().setDspAppid("e359a3bcbf1972668e05b9a44f574538").setDspid("aya6Jv")).build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
        RepositoryFactory.getRepository().saveOrUpdateApp(app);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(960).setWidth(640).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("4.6.1").setBundle("com.mianfei.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("7b2aia")).setAdType(CreativeType.kFirstScreen).build();
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
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setIsDebug(true).setId(UUIDUtils.uuid());
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

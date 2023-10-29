package com.iwanvi.adserv.ngx.router;

import com.alibaba.fastjson.util.UTF8Decoder;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-05-07 09:31
 * @version: v1.0
 * @Description:
 */
public class RunGaoDspTest {

//    @Test
    public void testCall(){

        // 设置mock的 BidFloorResolver 直接返回低价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);

        Dsp.Builder builder = Dsp.newBuilder().setDspId("NFbqiu").setQps(0).setRtbUrl("http://uat1.bfsspadserver.8le8le.com/common/adrequest").setStatus(true);

        // 固价设置
//        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("JVZzei").setPrice(20));

        // 关系映射本地测试需要配置:  免费电子书-书架页-顶部-通栏 JVZzei -> 1002098  （320*210）
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("1002154").setDspId(builder.getDspId())).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder()
                .setHeight(210).setWidth(320)
                .addCreativeType(CreativeType.kPic)
                .setAppId(ByteString.copyFromUtf8("com.mianfeia.book"))
                .setAppName("免费电子书").setAppVersion("v1.0").setBundle("com.mianfeia.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("JVZzei"))
                .setAdType(CreativeType.kFirstScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setIp("125.33.24.122").setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setOsv("5.0.1").setMac("02:00:00:00:00:00")
                .setDeviceModel(ByteString.copyFromUtf8("MI 6")).setDeviceBrand(ByteString.copyFromUtf8("Honor")).setTerminalType(TerminalType.kMobileTerminal)
                .setScreenWidth(1080).setScreenHeight(1920)
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setId(UUIDUtils.uuid());
        NonStandardRtbProtocolDspAdGetter toutiaoAdGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),brb.build());
        DspLoggers.registerLogger(brb.build());
        try {
            List<DspRouter.DspBid> dspBids = toutiaoAdGetter.call();
            if (CollectionUtils.isEmpty(dspBids)){return;}
            dspBids.forEach(dspBid -> {
                System.out.println(dspBid.getBid().build().toString());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

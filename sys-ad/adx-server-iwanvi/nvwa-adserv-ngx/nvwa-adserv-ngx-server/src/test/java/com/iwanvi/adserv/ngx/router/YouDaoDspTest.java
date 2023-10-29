package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-05-17 14:10
 * @version: v1.0
 * @Description:
 */
public class YouDaoDspTest {

//    @Test
    public void tectCall(){
        // 设置mock的 BidFloorResolver 直接返回低价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);
        Dsp.Builder builder = Dsp.newBuilder().setDspId("qiU3U3").setQps(0).setRtbUrl("http://gorgon.youdao.com/gorgon/request.s").setStatus(true);

        // 固价设置
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("zyqEne").setPrice(20));

        // DSP渠道
        builder.setDspTarget(DspTarget.newBuilder()
                .addAdPosTargets(AdPositionTarget.newBuilder().addAllTagids(Arrays.asList("zyqEne")))
                .setChannelTarget(ChannelTarget.newBuilder().addChannelIds("10023")));

        // 关系映射本地测试需要配置
        SspAdPosition sspAdPosition = SspAdPosition.newBuilder().addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("cd533407fdc7afb46678c74fa443c7aa").setDspId(builder.getDspId())).setAdPosId("zyqEne").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(960).setWidth(640).addCreativeType(CreativeType.kNative).setAppName("免费电子书").setAppVersion("4.6.0").setBundle("com.mianfeia.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("zyqEne")).setAdType(CreativeType.kFirstScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setIp("125.33.24.122").setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setOsv("8.0.0")
                .setScreenHeight(1920)
                .setScreenWidth(1080)
                .setDeviceModel(ByteString.copyFromUtf8("Xiaomi"))
                .setDeviceBrand(ByteString.copyFromUtf8("MI 6"))
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setIsDebug(true);
        NonStandardRtbProtocolDspAdGetter threeSixAdGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),brb.build());
        DspLoggers.registerLogger(brb.build());
        try {
            List<DspRouter.DspBid> dspBids = threeSixAdGetter.call();
            dspBids.forEach(dspBid -> {
                System.out.println(dspBid.getBid().build().toString());
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    @Test
    public void dateTest(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
            Date start = sdf.parse("00:00:00.000");
            Date date = sdf.parse(" 00:02:30.000");
            System.out.println((date.getTime()-start.getTime())/1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}

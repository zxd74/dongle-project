package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.broker.mifu.MiFuDspBroker;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import org.junit.Test;

import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;

import java.util.List;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-06 14:13
 */
public class MiFuDspTest {


    private DspBroker doDspBroker = new MiFuDspBroker();
    private static final BiddingReq biddingReq;

    static {
        PosInfo posInfo = PosInfo.newBuilder().setHeight(320).setWidth(480).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("5.1.0").setBundle("com.mianfeia.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("JVZzei")).setAdType(CreativeType.kScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi).setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setOsv("8.0.0").setAdid("dec9ec889d463a55").setScreenHeight(1920).setScreenWidth(1080).setIp("123.12.33.44")
                .setDeviceModel(ByteString.copyFromUtf8("Redmi Note 3")).setDeviceBrand(ByteString.copyFromUtf8("xiaomi"))
                .setDensity(480).setLat(39.932859).setLon(116.411144).setDeviceType(DeviceType.kPhone).setMac("10:2a:b3:a7:8d:3a")
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setId(UUIDUtils.uuid()).setIsDebug(true);
        biddingReq = brb.build();
    }

//    @Test
    public void call(){

        Dsp.Builder builder = Dsp.newBuilder().setDspId("A7fmUn").setQps(0).setRtbUrl("http://61.174.52.56/test_magic").setStatus(true);
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("JVZzei").setPrice(600));

        // 关系映射本地测试需要配置
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("2611259681").setDspId("A7fmUn")).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        // 设置mock的 BidFloorResolver 直接返回低价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);
        NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),biddingReq);
        DspLoggers.registerLogger(biddingReq);
        List<DspRouter.DspBid> dspBids = adGetter.call();
        dspBids.forEach(x-> System.out.println(x.getBid().build().toString()));
    }

//    @Test
    public void request(){
        // 关系映射本地测试需要配置
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("2261800528").setDspId("A7fmUn")).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
        doDspBroker.toQueryString(biddingReq);
    }

//    @Test
    public void response(){
        String respBody="{\"adid\":\"abcdefg1234567\",\"adtype\":\"3\",\"subadtype\":\"0\",\"img\":[\"img_url1\"],\"title\":\"title\",\"desc\":\"test\",\"lp\":\"http://dsp.com/t?landingpage\",\"pm\":[\"http://pm1\",\"http://pm2\"],\"cm\":[\"http://cm1\",\"http://cm2\"],\"adformat\":1,\"icon\":\"icon url\"}";
        List<AdMsg> list = doDspBroker.toAdMsgs(biddingReq,respBody.getBytes());
        list.forEach(x-> System.out.println(TextFormat.printToUnicodeString(x)));
    }
}

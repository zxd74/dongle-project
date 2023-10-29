package com.iwanvi.adserv.ngx.router;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.broker.maihan.BidResponse;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: 郑晓东
 * @date: 2019-06-03 17:20
 * @version: v1.0
 * @Description:
 */
public class MaiHanDspTest {

//    @Test
    public void call(){
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);

        Dsp.Builder builder = Dsp.newBuilder().setDspId("neQj22").setQps(0).setRtbUrl("http://api.ssp.myhayo.com/adx").setStatus(true);

        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("7b2aia").setPrice(600));

        SspAdPosition sspAdPosition = SspAdPosition.newBuilder()
                .addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("7B3A4B068DEF").setDspId(builder.getDspId()))
                .setAdPosId("7b2aia").setBidfloor(400).setAdType(CreativeType.kNative).build();

        App app = App.newBuilder().setAppId("EzqIze").addAppMapping(App.AppMapping.newBuilder().setDspAppid("75823FA73732").setDspid("neQj22")).build();

        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
        RepositoryFactory.getRepository().saveOrUpdateApp(app);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(960).setWidth(640).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("4.6.1").setBundle("com.mianfei.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("7b2aia")).setAdType(CreativeType.kFirstScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setOsv("8.0.0")
                .setScreenHeight(1920)
                .setScreenWidth(1080)
                .setTerminalType(TerminalType.kMobileTerminal)
                .setDeviceType(DeviceType.kPhone)
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
    public void jsontest(){
        String string = "{\n" +
                "    \"ret\": 0,\n" +
                "    \"msg\": \"\",\n" +
                "    \"data\": {\n" +
                "    }\n" +
                "}";
        BidResponse bidResponse = JSON.parseObject(string, BidResponse.class);

        LinkedHashMap<String, String> jsonMap = JSON.parseObject(string, new TypeReference<LinkedHashMap<String, String>>(){});
        if (jsonMap.get("data")!=null && !jsonMap.get("data").equals("{}")){
            System.out.println(jsonMap.get("data"));
            LinkedHashMap<String, String> data = JSON.parseObject(jsonMap.get("data"), new TypeReference<LinkedHashMap<String, String>>(){});
            if (data.get("A501075E0894")!=null){
                List<BidResponse> ads = JSON.parseArray(data.get("A501075E0894"),BidResponse.class);
                for (BidResponse ad:ads){

                }
            }
        }
    }

//    @Test
    public void strReplace(){
        String[] str = new String[]{"https://t.gdt.qq.com/conv/alliance/api/conv?client=6&action_id=__ACTION_ID__&click_id=__CLICK_ID__&product_id=100898297"};
        List<String> list = Arrays.stream(str).map(x->x.replace("__ACTION_ID__","{ACTION_ID}").replace("__TS__ ","{ACTION_TIME}").replace("_EVENT_TIME_START__","{ACTION_TIME}").replace("_EVENT_TIME_END__","{ACTION_TIME}")).collect(Collectors.toList());
        list.stream().forEach(x-> System.out.println(x));
    }
}

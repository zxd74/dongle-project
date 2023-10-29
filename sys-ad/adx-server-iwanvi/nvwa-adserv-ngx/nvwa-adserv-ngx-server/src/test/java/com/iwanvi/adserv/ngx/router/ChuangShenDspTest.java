package com.iwanvi.adserv.ngx.router;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.broker.chuangshen.BidResponse;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import org.junit.Test;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-06-13 16:28
 * @version: v1.0
 * @Description:
 */
public class ChuangShenDspTest {

//    @Test
    public void call(){
        Dsp.Builder builder = Dsp.newBuilder().setDspId("BFNb2y").setQps(0).setRtbUrl("http://openapi.toukeads.com/openapi/adservice/f.action").setStatus(true);
        SspAdPosition sspAdPosition = SspAdPosition.newBuilder()
                .addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("73").setDspId(builder.getDspId()))
                .setAdPosId("JVZzei").setBidfloor(1).setAdType(CreativeType.kNative).build();
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("JVZzei").setPrice(20));
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(1200).setWidth(800).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("4.6.1").setBundle("com.mianfei.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("JVZzei")).setAdType(CreativeType.kFocusPic).build();
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

//    @Test
    public void test(){
        String json = "{\"succ\":true,\"msg\":\"ok\",\"ads\":{\"creativeId\":\"1450\",\"adsId\":\"1371\",\"image\":\"http:\\/\\/images.cshudong.com\\/mf-xxlwz\\/1.jpg\",\"width\":\"1280\",\"height\":\"720\",\"gotourl\":\"http:\\/\\/www.hello360.cn\\/#page\",\"indices\":101,\"words\":\"\\u7f51\\u8d5a\",\"words2\":\"\\u8f7b\\u8f7b\\u70b9\\u51fb\\u624b\\u673a\\u5c4f\\u5e55\\uff0c\\u5373\\u53ef\\u8d5ahigh\\u5929~\",\"icon\":\"http:\\/\\/images.cshudong.com\\/icon-wz\\/2.png\",\"image2\":\"\",\"showReport\":[[\"http:\\/\\/stats.toukeads.com\\/openapi\\/analysis.action?g=b2e0S8%2BS4CmoilUkwFxVx73bKCLsMIrEwxhYCVX9jRjYtEkZBbsyks3zBflY%2FuV93K3iAfYz4w\",\"http:\\/\\/openapi.toukeads.com\\/openapi\\/stats.action?g=85d9pR40Uxv2LU8EZ06CdfkDutml%2FpPJT%2FVsQzT9zEgYidUONSdiWlQ%2FK7Wp1XyRcv1Ut6C3DFymRBS6HZ7X9PajuQio%2BEjGHsN9IJnB3JclNQ7S%2BstmKe182n0cXj5kxq9xCx9HYXMWyL3GYrMn6DTufAw8159EL2x4EFCbv0xIS65Q12k&vt=_TIMESTAMP_\",\"http:\\/\\/stats1.toukeads.com\\/openapi\\/analysis2.action?g=a1f9dt1X%2FK0Ajo3q%2FI6WXtHSa0pMcef2cv1uHGUfL9wpVzCjrZTancE%2BWUxS9miSE8cS1UQqOHjp6bfW4YWdAdV32Gv8xh%2Bz9PIT1Zwzv8xC%2B1VxHRPgLyYivMDnXTttyEkZ2a9Fgw9T8s7VmEB05AWGvRHXXMIFn%2BOFx6En54%2BM1yJqLjHO6K2kJAsJSdmE7ATCAMmUF0SvXrI\",\"http:\\/\\/stats1.toukeads.com\\/openapi\\/stats2.action?g=de44VB7I08HNVhykjkzxhHEBIlR5x4Ik3J4Z9NBTxap3NT88JPlRpvuel%2F3i83eW%2FFJRJZjAbE6JSffJFfAF29ZOVssTf2hpstZFzwbmEtxqJc7DCD7f88WQm4OfuM%2FfdH7ZaDWD9kHRNDXbg%2FWWThoy7pWDVIquHgfOtt5KGN6N47rrkWBeSRK10SqecYJ%2F7ngD&vt=_TIMESTAMP_\"]],\"clickReport\":[\"http:\\/\\/stats.toukeads.com\\/openapi\\/analysis.action?g=08bbthKdGJzR1DJBs7EmZ4lBwkD2GhWD8GVqD2DAOylIf1cpQ45ewL%2BlZwc0cFj2exeosv4CsM%2BZxg\",\"http:\\/\\/stats1.toukeads.com\\/openapi\\/analysis2.action?g=4e43uTUShrbHl%2BDDSlcvPPxJB6goAoA%2BccSyKVyMJDHC%2F0OzG3FZQ%2F1uQe3UnBtQQJDRZMqAI%2B5v7SjLWPm%2BSIyhI850Hsx5MasJPDpLW7VRrRQ2eionJZcqj1gpHPbz3pxqHI1qL%2BhDgpq5AkDAKawG6L9xq3vckTTStVRj74JHkYxNPKkdIxfRF1Z4tWg0nNyRfM6HOgyEE2Qm%2FA\"]}}";
        BidResponse bidResponse = JSON.parseObject(json,BidResponse.class);

        BidResponse.Ad ads = JSON.parseObject(bidResponse.getAds(), BidResponse.Ad.class);
        System.out.println(JSON.toJSONString(ads));
    }
}

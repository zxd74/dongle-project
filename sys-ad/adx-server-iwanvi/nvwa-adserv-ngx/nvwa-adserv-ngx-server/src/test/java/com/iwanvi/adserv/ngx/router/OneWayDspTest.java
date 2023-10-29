package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;

import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.broker.oneway.OneWayDspBroker;
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
 * @since : 2019-08-05 13:28
 */
public class OneWayDspTest {

    private DspBroker doDspBroker = new OneWayDspBroker();
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

        Dsp.Builder builder = Dsp.newBuilder().setDspId("bIFZFv").setQps(0).setRtbUrl("https://ads.oneway.mobi/getCampaign").setStatus(true);
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("JVZzei").setPrice(600));

        // 关系映射本地测试需要配置
        AdModelsProto.App app = AdModelsProto.App.newBuilder().addAppMapping(AdModelsProto.App.AppMapping.newBuilder().setDspAppid("ea48819c23a04d12_bb0460183dfe4e9b").setDspid("bIFZFv").build()).setAppId("EzqIze").build();
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("ZMQN7TA49Z67R9BN").setDspId("bIFZFv")).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
        RepositoryFactory.getRepository().saveOrUpdateApp(app);

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
        AdModelsProto.App app = AdModelsProto.App.newBuilder().addAppMapping(AdModelsProto.App.AppMapping.newBuilder().setDspAppid("ea48819c23a04d12_bb0460183dfe4e9b").setDspid("bIFZFv").build()).setAppId("EzqIze").build();
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("1").setDspId("bIFZFv")).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
        RepositoryFactory.getRepository().saveOrUpdateApp(app);
        doDspBroker.toDspRequest(biddingReq);
    }

//    @Test
    public void response(){
        String respBody="{\"data\":{\"sessionId\":\"ae4246ee01724260aa6086378c136527\",\"appIcon\":\"http://ad-cn-south.img-cn-shenzhen.aliyuncs.com/icon/icon.png\",\"appName\":\"御剑情缘-安卓\",\"title\":\"3D东方幻想飞仙手游\",\"appStoreId\":\"mobi.bIFZFv.ad\",\"rating\":4.3,\"ratingCount\":7019,\"campaignId\":629,\"expirationTime\":3600,\"video\":{\"duration\":15.0,\"url\":\"http://ad-video-hk.img-cn-hongkong.aliyuncs.com/advertiser/71/14804970912tY.mp4\"},\"images\":[{\"url\":\"http://ad-cn-south.img-cn-shenzhen.aliyuncs.com/0/1480496878eQa.jpg@800w_800h_80Q\"}],\"clickUrl\":\"https://tracking.bIFZFv.mobi/tl?a=44o=417s1={IDFA}s3={aff_click_id}s4={ip}sc={SOURCE}\",\"trackingEvents\":{\"start\":[\"http://track.abc.com?eventName=start&publishId=agjf0mnf0fsz1mk9&token=7eqt0gxr5koiatbl&ts=1526372033489&data=%7B%22androidId%22%3A%221c1b0d23fb232823%22%2C%22bundleId%22%3A%22mobi.bIFZFv.sdkdemo%22%2C%22connectionType%22%3A%22wifi%22%2C%22deviceId%22%3A%22635d37ff-1a71-4615-8b03-ecc66e249df2%22%2C%22deviceMake%22%3A%22HUAWEI%22%2C%22deviceModel%22%3A%22HUAWEI+GRA-CL10%22%2C%22deviceOS%22%3A%22Android%22%2C%22imei%22%3A%22864394010789800%22%2C%22ipAddress%22%3A%22127.0.0.1%22%2C%22language%22%3A%22zh_CN%22%2C%22orientation%22%3A%22V%22%2C%22osVersion%22%3A%224.4.2%22%2C%22placementId%22%3A%22saiu7pvpsdyvnty6%22%2C%22platform%22%3A1%2C%22pubId%22%3A75%2C%22regionCode%22%3A%22CN%22%2C%22screenHeight%22%3A1280%2C%22screenWidth%22%3A720%2C%22sessionId%22%3A%22002-a96326e54575aaea5697a7b70ff1%22%2C%22simulator%22%3Afalse%2C%22testMode%22%3Atrue%2C%22timeZone%22%3A%22GMT%2B08%3A00%22%2C%22timestamp%22%3A1526372033481%2C%22webviewUa%22%3A%22Mozilla%2F5.0+%28Linux%3B+Android+4.4.2%3B+HUAWEI+GRA-CL10+Build%2FHUAWEIGRA-CL10%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F30.0.0.0+Mobile+Safari%2F537.36%22%7D\"],\"end\":[\"http://track.abc.com?eventName=end&publishId=agjf0mnf0fsz1mk9&token=7eqt0gxr5koiatbl&ts=1526372033489&data=%7B%22androidId%22%3A%221c1b0d23fb232823%22%2C%22bundleId%22%3A%22mobi.bIFZFv.sdkdemo%22%2C%22connectionType%22%3A%22wifi%22%2C%22deviceId%22%3A%22635d37ff-1a71-4615-8b03-ecc66e249df2%22%2C%22deviceMake%22%3A%22HUAWEI%22%2C%22deviceModel%22%3A%22HUAWEI+GRA-CL10%22%2C%22deviceOS%22%3A%22Android%22%2C%22imei%22%3A%22864394010789800%22%2C%22ipAddress%22%3A%22127.0.0.1%22%2C%22language%22%3A%22zh_CN%22%2C%22orientation%22%3A%22V%22%2C%22osVersion%22%3A%224.4.2%22%2C%22placementId%22%3A%22saiu7pvpsdyvnty6%22%2C%22platform%22%3A1%2C%22pubId%22%3A75%2C%22regionCode%22%3A%22CN%22%2C%22screenHeight%22%3A1280%2C%22screenWidth%22%3A720%2C%22sessionId%22%3A%22002-a96326e54575aaea5697a7b70ff1%22%2C%22simulator%22%3Afalse%2C%22testMode%22%3Atrue%2C%22timeZone%22%3A%22GMT%2B08%3A00%22%2C%22timestamp%22%3A1526372033481%2C%22webviewUa%22%3A%22Mozilla%2F5.0+%28Linux%3B+Android+4.4.2%3B+HUAWEI+GRA-CL10+Build%2FHUAWEIGRA-CL10%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F30.0.0.0+Mobile+Safari%2F537.36%22%7D\"],\"click\":[\"http://track.abc.com?eventName=click&publishId=agjf0mnf0fsz1mk9&token=7eqt0gxr5koiatbl&ts=1526372033489&data=%7B%22androidId%22%3A%221c1b0d23fb232823%22%2C%22bundleId%22%3A%22mobi.bIFZFv.sdkdemo%22%2C%22connectionType%22%3A%22wifi%22%2C%22deviceId%22%3A%22635d37ff-1a71-4615-8b03-ecc66e249df2%22%2C%22deviceMake%22%3A%22HUAWEI%22%2C%22deviceModel%22%3A%22HUAWEI+GRA-CL10%22%2C%22deviceOS%22%3A%22Android%22%2C%22imei%22%3A%22864394010789800%22%2C%22ipAddress%22%3A%22127.0.0.1%22%2C%22language%22%3A%22zh_CN%22%2C%22orientation%22%3A%22V%22%2C%22osVersion%22%3A%224.4.2%22%2C%22placementId%22%3A%22saiu7pvpsdyvnty6%22%2C%22platform%22%3A1%2C%22pubId%22%3A75%2C%22regionCode%22%3A%22CN%22%2C%22screenHeight%22%3A1280%2C%22screenWidth%22%3A720%2C%22sessionId%22%3A%22002-a96326e54575aaea5697a7b70ff1%22%2C%22simulator%22%3Afalse%2C%22testMode%22%3Atrue%2C%22timeZone%22%3A%22GMT%2B08%3A00%22%2C%22timestamp%22%3A1526372033481%2C%22webviewUa%22%3A%22Mozilla%2F5.0+%28Linux%3B+Android+4.4.2%3B+HUAWEI+GRA-CL10+Build%2FHUAWEIGRA-CL10%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F30.0.0.0+Mobile+Safari%2F537.36%22%7D\"]},\"postTrackingEvents\":{\"show\":[{\"url\":[\"http://track.abc.com?et=1&publishId=agjf0mnf0fsz1mk9&token=7eqt0gxr5koiatbl&ts=1526372033489&data=%7B%22androidId%22%3A%221c1b0d23fb232823%22%2C%22bundleId%22%3A%22mobi.bIFZFv.sdkdemo%22%2C%22connectionType%22%3A%22wifi%22%2C%22deviceId%22%3A%22635d37ff-1a71-4615-8b03-ecc66e249df2%22%2C%22deviceMake%22%3A%22HUAWEI%22%2C%22deviceModel%22%3A%22HUAWEI+GRA-CL10%22%2C%22deviceOS%22%3A%22Android%22%2C%22imei%22%3A%22864394010789800%22%2C%22ipAddress%22%3A%22127.0.0.1%22%2C%22language%22%3A%22zh_CN%22%2C%22orientation%22%3A%22V%22%2C%22osVersion%22%3A%224.4.2%22%2C%22placementId%22%3A%22saiu7pvpsdyvnty6%22%2C%22platform%22%3A1%2C%22pubId%22%3A75%2C%22regionCode%22%3A%22CN%22%2C%22screenHeight%22%3A1280%2C%22screenWidth%22%3A720%2C%22sessionId%22%3A%22002-a96326e54575aaea5697a7b70ff1%22%2C%22simulator%22%3Afalse%2C%22testMode%22%3Atrue%2C%22timeZone%22%3A%22GMT%2B08%3A00%22%2C%22timestamp%22%3A1526372033481%2C%22webviewUa%22%3A%22Mozilla%2F5.0+%28Linux%3B+Android+4.4.2%3B+HUAWEI+GRA-CL10+Build%2FHUAWEIGRA-CL10%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F30.0.0.0+Mobile+Safari%2F537.36%22%7D\"],\"data\":{\"type\":\"show\",\"androidId\":\"a7fe374396c7a476\",\"clientIp\":\"118.251.40.168\",\"timestamp\":\"{TIMESTAMP}\"}}],\"click\":[{\"url\":[\"http://track.abc.com?et=10&publishId=agjf0mnf0fsz1mk9&token=7eqt0gxr5koiatbl&ts=1526372033489&data=%7B%22androidId%22%3A%221c1b0d23fb232823%22%2C%22bundleId%22%3A%22mobi.bIFZFv.sdkdemo%22%2C%22connectionType%22%3A%22wifi%22%2C%22deviceId%22%3A%22635d37ff-1a71-4615-8b03-ecc66e249df2%22%2C%22deviceMake%22%3A%22HUAWEI%22%2C%22deviceModel%22%3A%22HUAWEI+GRA-CL10%22%2C%22deviceOS%22%3A%22Android%22%2C%22imei%22%3A%22864394010789800%22%2C%22ipAddress%22%3A%22127.0.0.1%22%2C%22language%22%3A%22zh_CN%22%2C%22orientation%22%3A%22V%22%2C%22osVersion%22%3A%224.4.2%22%2C%22placementId%22%3A%22saiu7pvpsdyvnty6%22%2C%22platform%22%3A1%2C%22pubId%22%3A75%2C%22regionCode%22%3A%22CN%22%2C%22screenHeight%22%3A1280%2C%22screenWidth%22%3A720%2C%22sessionId%22%3A%22002-a96326e54575aaea5697a7b70ff1%22%2C%22simulator%22%3Afalse%2C%22testMode%22%3Atrue%2C%22timeZone%22%3A%22GMT%2B08%3A00%22%2C%22timestamp%22%3A1526372033481%2C%22webviewUa%22%3A%22Mozilla%2F5.0+%28Linux%3B+Android+4.4.2%3B+HUAWEI+GRA-CL10+Build%2FHUAWEIGRA-CL10%29+AppleWebKit%2F537.36+%28KHTML%2C+like+Gecko%29+Version%2F4.0+Chrome%2F30.0.0.0+Mobile+Safari%2F537.36%22%7D\"],\"data\":{\"type\":\"click\",\"androidId\":\"a7fe374396c7a476\",\"clientIp\":\"118.251.40.168\",\"timestamp\":\"{TIMESTAMP}\"}}]},\"downloadType\":0,\"clickMode\":0},\"message\":\"success\",\"success\":true,\"timeLag\":600}";
        List<AdMsg> list = doDspBroker.toAdMsgs(biddingReq,respBody.getBytes());
        list.forEach(x-> System.out.println(TextFormat.printToUnicodeString(x)));
    }
}

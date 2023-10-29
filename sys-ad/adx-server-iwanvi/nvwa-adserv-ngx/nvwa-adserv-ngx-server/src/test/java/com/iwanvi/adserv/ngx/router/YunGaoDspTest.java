package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.api.DspBroker;

import com.iwanvi.adserv.ngx.router.broker.yungao.YunGaoDspBroker;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.sun.org.apache.bcel.internal.generic.FADD;
import org.junit.Test;

import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author : 郑晓东
 * @version : v1.0
 * @since : 2019-08-12 12:01
 */
public class YunGaoDspTest {


    private DspBroker doDspBroker = new YunGaoDspBroker();
    private static final BiddingReq biddingReq;
    private static final Dsp dsp;

    static {
        // 设置mock的 BidFloorResolver 直接返回低价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);
        
        String appId = "EzqIze",posId="JVZzei",dspId="URjiiq",appName="免费电子书",appVer="5.1.0",appPkg="com.mianfeia.book",channel="000000",rtbUrl="http://api.yungao.mobi/ssp";
        String dspAppId="6D9H0B",dspPosId="U9UJ0D";
        Integer width=640,height=960,sh=1960,sw=1080;

        // 设置广告位数据
        PosInfo posInfo = PosInfo.newBuilder().setHeight(210).setWidth(320).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8(appId)).setAppName(appName).setAppVersion(appVer).setBundle(appPkg)
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8(posId)).setAdType(CreativeType.kPic).build();

        // 设置用户数据
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi).setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setOsv("8.0.0").setAdid("dec9ec889d463a55").setScreenHeight(1920).setScreenWidth(1080).setIp("123.12.33.44")
                .setDeviceModel(ByteString.copyFromUtf8("Redmi Note 3")).setDeviceBrand(ByteString.copyFromUtf8("xiaomi"))
                .setDensity(480).setLat(39.932859).setLon(116.411144).setDeviceType(DeviceType.kPhone).setMac("10:2a:b3:a7:8d:3a")
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setId(UUIDUtils.uuid()).setIsDebug(false);
        biddingReq = brb.build();

        // 关系映射本地测试需要配置
        App app = App.newBuilder().addAppMapping(App.AppMapping.newBuilder().setDspid(dspId).setDspAppid(dspAppId)).setAppId(appId).build();
        RepositoryFactory.getRepository().saveOrUpdateApp(app);
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId(dspPosId).setDspId(dspId)).setAdPosId(posId).build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        dsp = Dsp.newBuilder().setIsStandardProtocol(false).setDspId(dspId).setQps(0).setRtbUrl(rtbUrl).setStatus(true).setIsFixedPrice(true)
                .addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid(posId).setPrice(600))
                .setDspTarget(DspTarget.newBuilder().addAdPosTargets(AdPositionTarget.newBuilder().addAllTagids(Arrays.asList(posId)))).build();

    }

//    @Test
    public void request() {
        doDspBroker.toQueryString(biddingReq);
    }

//    @Test
    public void response() {
        String respBody = "{\"request_id\":\"23371afa64e14eb29dc3743a85ced76b\",\"error_code\":0,\"message\":\"NO_ERROR\",\"total_num\":1,\"expiration_time\":0,\"ads\":[{\"adslot_id\":\"U9U803\",\"ad_key\":\"102462\",\"title\":\"云告—为流量主提供最优质的解决方案\",\"description\":\"\",\"image_src\":[\"http://oss.public.yungao.mobi/b4d0647aeb43abfb/5dd36c11a7603a2d.png\"],\"icon_src\":\"https://online-yungao-public.oss-cn-beijing.aliyuncs.com/api/yungao.png\",\"interaction_type\":1,\"click_url\":\"http://yungao-dev.oss-cn-beijing.aliyuncs.com/test/test.png\",\"app_name\":\"\",\"app_package\":\"\",\"report_click\":[\"http://c.r.yungao.mobi/?s=YWs9MTAyNDYyJmJ0PTExMDEmdWk9NSZ1cWk9YjViYWY5OTk3NGE3NWQ0YWQzNDg4ZTU3NDBjYzM3MzAmdXNpPTEwMjQ2MiZhZHNsb3RfaWQ9VTlVODAzJmFuZHJvaWRfaWQ9ZGVjOWVjODg5ZDQ2M2E1NSZhcF9tYWM9JmFwX25hbWU9JmFwaV92ZXJzaW9uPVYxLjEuMCZhcHBfaWQ9NkQ5SDBCJmFwcF9uYW1lPSZhcHBfcGFja2FnZT1jb20ubWlhbmZlaWEuYm9vayZhcHBfdmVyc2lvbj01LjEuMCZjYWxsYmFja191cmw9JmNhbGxiYWNrX3VybF9zZWNyZXRfa2V5PSZjZWxsdWxhcl9pZD0mY29ubmVjdGlvbl90eXBlPTEwMCZjb250ZW50X2xlbmd0aD0mY29weXJpZ2h0PSZjdGtleT0mZGV2aWNlX3R5cGU9MSZkb21haW49JmZpbmdlcl9wcmludD0maWRmYT0maWRmdj0maW1laT04NjgwMzAwMzMyNjcwMDQmaW1zaT0maXB2ND0xMjMuMTIuMzMuNDQmaXNfY29ubmVjdGVkPSZrZXl3b3Jkcz0mbGF0aXR1ZGU9MzkuOTMyODU4MDEmbG9uZ2l0dWRlPTExNi40MTExNDAwMSZtYWM9MDI6MDA6MDA6MDA6MDA6MDAmbWVkaWFfdHlwZT0xJm1vZGVsPVJlZG1pIE5vdGUgMyZvcGVudWRpZD0mb3BlcmF0b3JfdHlwZT0xJm9zX3ZlcnNpb249OC4wLjAmcmVxdWVzdF9pZD1mNjVjNTkxOGM0ZGE0NmE3OTZhZWM1ZDljODA4YjVlYyZyZXF1ZXN0X3Byb3RvY29sX3R5cGU9MCZyZXF1ZXN0X3RpbWU9MjM4MjQ1ODE5NSZyc3NpPSZzY3JlZW5faGVpZ2h0PTE5MjAmc2NyZWVuX3dpZHRoPTEwODAmc2VyaWFsX251bWJlcj0mc291cmNlX3VybD0mdGl0bGU9JnVhPU1vemlsbGEvNS4wIChMaW51eDsgQW5kcm9pZCA4LjAuMDsgTUkgNiBCdWlsZC9PUFIxLjE3MDYyMy4wMjc7IHd2KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBWZXJzaW9uLzQuMCBDaHJvbWUvNzEuMC4zNTc4Ljk5IE1vYmlsZSBTYWZhcmkvNTM3LjM2JnVuaXF1ZV9pZD1hNTA3OTkwMGI4YjNmOTQ3YzFlZWQ0NTk4YzQwZjRkZSZ1cmw9JnZlbmRvcj14aWFvbWkmdmlkZW9fcHJlZmV0Y2g9MCZ2aWRlb190aXRsZT0mYWRfdGl0bGU95LqR5ZGK4oCU5Li65rWB6YeP5Li75o%2BQ5L6b5pyA5LyY6LSo55qE6Kej5Yaz5pa55qGI\\u0026adslot_id=U9U803\\u0026ak=102462\\u0026click_time=__CLICK_TIME__\\u0026down_x=__DOWN_X__\\u0026down_y=__DOWN_Y__\\u0026up_x=__UP_X__\\u0026up_y=__UP_Y__\"],\"report_impress\":[\"http://i.r.yungao.mobi/?s=YWs9MTAyNDYyJmJ0PTExMDEmdWk9NSZ1cWk9YjViYWY5OTk3NGE3NWQ0YWQzNDg4ZTU3NDBjYzM3MzAmdXNpPTEwMjQ2MiZhZHNsb3RfaWQ9VTlVODAzJmFuZHJvaWRfaWQ9ZGVjOWVjODg5ZDQ2M2E1NSZhcF9tYWM9JmFwX25hbWU9JmFwaV92ZXJzaW9uPVYxLjEuMCZhcHBfaWQ9NkQ5SDBCJmFwcF9uYW1lPSZhcHBfcGFja2FnZT1jb20ubWlhbmZlaWEuYm9vayZhcHBfdmVyc2lvbj01LjEuMCZjYWxsYmFja191cmw9JmNhbGxiYWNrX3VybF9zZWNyZXRfa2V5PSZjZWxsdWxhcl9pZD0mY29ubmVjdGlvbl90eXBlPTEwMCZjb250ZW50X2xlbmd0aD0mY29weXJpZ2h0PSZjdGtleT0mZGV2aWNlX3R5cGU9MSZkb21haW49JmZpbmdlcl9wcmludD0maWRmYT0maWRmdj0maW1laT04NjgwMzAwMzMyNjcwMDQmaW1zaT0maXB2ND0xMjMuMTIuMzMuNDQmaXNfY29ubmVjdGVkPSZrZXl3b3Jkcz0mbGF0aXR1ZGU9MzkuOTMyODU4MDEmbG9uZ2l0dWRlPTExNi40MTExNDAwMSZtYWM9MDI6MDA6MDA6MDA6MDA6MDAmbWVkaWFfdHlwZT0xJm1vZGVsPVJlZG1pIE5vdGUgMyZvcGVudWRpZD0mb3BlcmF0b3JfdHlwZT0xJm9zX3ZlcnNpb249OC4wLjAmcmVxdWVzdF9pZD1mNjVjNTkxOGM0ZGE0NmE3OTZhZWM1ZDljODA4YjVlYyZyZXF1ZXN0X3Byb3RvY29sX3R5cGU9MCZyZXF1ZXN0X3RpbWU9MjM4MjQ1ODE5NSZyc3NpPSZzY3JlZW5faGVpZ2h0PTE5MjAmc2NyZWVuX3dpZHRoPTEwODAmc2VyaWFsX251bWJlcj0mc291cmNlX3VybD0mdGl0bGU9JnVhPU1vemlsbGEvNS4wIChMaW51eDsgQW5kcm9pZCA4LjAuMDsgTUkgNiBCdWlsZC9PUFIxLjE3MDYyMy4wMjc7IHd2KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBWZXJzaW9uLzQuMCBDaHJvbWUvNzEuMC4zNTc4Ljk5IE1vYmlsZSBTYWZhcmkvNTM3LjM2JnVuaXF1ZV9pZD1hNTA3OTkwMGI4YjNmOTQ3YzFlZWQ0NTk4YzQwZjRkZSZ1cmw9JnZlbmRvcj14aWFvbWkmdmlkZW9fcHJlZmV0Y2g9MCZ2aWRlb190aXRsZT0mYWRfdGl0bGU95LqR5ZGK4oCU5Li65rWB6YeP5Li75o%2BQ5L6b5pyA5LyY6LSo55qE6Kej5Yaz5pa55qGI\\u0026adslot_id=U9U803\\u0026ak=102462\"]}],\"urt\":\"0.000\",\"mark\":\"yungao\",\"reuqi\":\"c16b902950a12694ae2f80b6e3c5aefd\"}";
        List<AdMsg> list = doDspBroker.toAdMsgs(biddingReq, respBody.getBytes());
        list.forEach(x -> System.out.println(TextFormat.printToUnicodeString(x)));
    }

//    @Test
    public void call() {
        NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(dsp, biddingReq);
        DspLoggers.registerLogger(biddingReq);
        List<DspRouter.DspBid> dspBids = adGetter.call();
        dspBids.forEach(x -> System.out.println(x.getBid().build().toString()));
    }

//    @Test
    public void router(){

        RepositoryFactory.getRepository().saveOrUpdateDsp(dsp);
        DspIndexUtils.indexDsp(dsp);

        DspRouter dspRouter = DspRouter.get();

        List<AdMsg> adMsgs = dspRouter.route(biddingReq);
//        adMsgs.forEach(x-> System.out.println(TextFormat.printToUnicodeString(x)));
    }
}

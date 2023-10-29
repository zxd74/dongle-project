package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.broker.taido.TaiDoDspBroker;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import org.junit.Test;

import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import com.iwanvi.nvwa.proto.AdModelsProto.*;

import java.util.List;


/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-02 14:30
 */
public class TaiDoDspTest {

    private TaiDoDspBroker doDspBroker = new TaiDoDspBroker();

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
    public void call() throws Exception {

        Dsp.Builder builder = Dsp.newBuilder().setDspId("RziUNf").setQps(0).setRtbUrl("http://114.55.28.158:8080/gad").setStatus(true);
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("JVZzei").setPrice(600));

        // 关系映射本地测试需要配置
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("198284").setDspId("RziUNf")).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        // 设置mock的 BidFloorResolver 直接返回低价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);
        NonStandardRtbProtocolDspAdGetter adGetter = new NonStandardRtbProtocolDspAdGetter(builder.build(),biddingReq);
        DspLoggers.registerLogger(biddingReq);
        List<DspRouter.DspBid> dspBids = adGetter.call();
        dspBids.forEach(dspBid ->System.out.println(dspBid.getBid().build().toString()));
    }

//    @Test
    public void requestTest(){
        // TODO 广告请求模拟测试
        // 关系映射本地测试需要配置
        AdModelsProto.App app = AdModelsProto.App.newBuilder().addAppMapping(AdModelsProto.App.AppMapping.newBuilder().setDspAppid("199").setDspid("RziUNf").build()).setAppId("EzqIze").build();
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("183").setDspId("RziUNf")).setAdPosId("JVZzei").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);
        RepositoryFactory.getRepository().saveOrUpdateApp(app);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(320).setWidth(480).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("5.1.0").setBundle("com.mianfeia.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("JVZzei")).setAdType(CreativeType.kScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi).setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setOsv("8.0.0").setAdid("dec9ec889d463a55").setScreenHeight(1920).setScreenWidth(1080).setIp("123.12.33.44")
                .setDeviceModel(ByteString.copyFromUtf8("Redmi Note 3")).setDeviceBrand(ByteString.copyFromUtf8("xiaomi"))
                .setDensity(480).setLat(39.932859).setLon(116.411144).setDeviceType(DeviceType.kPhone).setMac("10:2a:b3:a7:8d:3a")
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setId(UUIDUtils.uuid()).setIsDebug(true);
        BiddingReq biddingReq = brb.build();

        doDspBroker.toDspRequest(biddingReq);

        // TODO 广告响应模拟测试
        String json = "";
        List<AdMsg> adMsgs = doDspBroker.toAdMsgs(biddingReq,json.getBytes());

    }

//    @Test
    public void responseTest(){


        // TODO 广告响应模拟测试
        String json = "{\"res\":0,\"remark\":\"\",\"addetail\":[{\"type\":1,\"pvurls\":[\"http:\\/\\/et.w.inmobi.cn\\/c.asm\\/HDaOu_if46qyBRbG9QoUWBTIAhWqDBsDWAQoNmI3Y2QzMjExNmNhZmQxZmRiYTY0OWVkNTc3NGMzYWFjMTI1ZDE4YiAoOWM4ZTkyYTAzNGU4ZTA4M2EwZmFhOTcxMjk4MGQ4ZTRlY2NiNjAyMB4gOTg4Njc3ZDU0YjU2NTI1ZWFhNDYwMjA1ZDFlODU2YjccFQQVGBUCFdgEFQIlACgDZGlyABMAHBdxGw3gLfhDQBc9CtejcBldQAAVABggWTI5dExtNWxkMk5oY0dWakxtMXZZbWxzWlM1dVkzQX4WlMN3HBwW_7-f6Z_a_PThARaX_f__37ze_0sAABUeFwAAAAAAgExAJAAcEhkFABaUw3cYA1VTRBa-3ea-qFgZ9RwUvAOCG74-kk6CfaasAaqsAaysAa6sAbytAYCvAdK7Ada7AfrrAcLtAYrvAYL6AdKJAuaZApqaAvDBAuDXArz9AtyCA7aHA7iHA_KlAyEYTQwAAQsAAQAAACA3NWY3MDEyMjY0NGI0ZDk0OTk4MDFiMzkzNmIwM2RmYgoAAgAAAAAAE0csAAQAAj-GhysCDEm6CgAEAAAAAAAU1AIAOCIEAAFATIAAAAAAAAQAAj-EeuFHrhR7DAADCAABAAAAAQAAGDcDAAEBCAACAAAADQYABQKABgAGBHALAAcAAAAFMy4wLjALAAgAAAADQVBQCAAJAAAAAQIACgAAGMMBCgABRlJ5cR9wroMLAAIAAACwCwABAAAAF2NvbS5uZXdjYXBlYy5tb2JpbGUubmNwAwACAAIABAECAAUABgAGEBALAAcAAAAEUlRCRAsACQAAACA3NWY3MDEyMjY0NGI0ZDk0OTk4MDFiMzkzNmIwM2RmYggADgAAEPoLAA8AAAAJMTA4MFgxOTIwBgAQAAALABEAAAAgNzVmNzAxMjI2NDRiNGQ5NDk5ODAxYjM5MzZiMDNkZmIKABIAAAAAAF4w-AAAABj-ATa-3ea-qFioOGhCeWNUQ3lSWkZwUGN1N1lTRnhEZ1pwSEIvejl5dnZYYmhLNEpIWDBVdzF1a3QzdnVCZk9aUT09GAZOQVRJVkUcPBwW_7-f6Z_a_PThARaX_f__37ze_0sAFoa6hfej3LzSjAEiAAA59RyArwGCfYIbgvoBiu8Bkk4UmpoCpqwBqqwBrKwBrqwBtocDuIcDvK0BvP0CvAO-PsLtAdK7AdKJAta7AdyCA-DXAuaZAvDBAvKlA_rrARwAltalHRwVAAAsFQAAhQIRLBb_v5_pn9r89OEBFpf9_5zUvN6HSgAlygEVCjgJMTcwODA0NDk1NAZZFAIAGAE3AA\\/ea69418b?m=18&ts=$TS\",\"http:\\/\\/v2.gdt.qq.com\\/gdt_stats.fcg?viewid=vADWg3dmonOA28WnvDPjxDWsvYj!WZMqO9QcnmuOFW1Meyz99kMprRvThKzV7ks05COR8SuXGK3V0wF0YjUTgWSmSNZEQaeOVCgsv8A7fQ1QScO5n7TpEq0OAzUf2jmAx!e!sXG_E7EeBKObVGgkU!M8EIUj4ebxV6367NETHu6sDon4Zn0K9b86z_JwOEaJ047oXagTq1HQ9JD4x4x!VZ4csCy!uDqS&i=1&os=2&xp=2\",\"http:\\/\\/win.gdt.qq.com\\/win_notice.fcg?adx_id=8&viewid=vADWg3dmonOA28WnvDPjxDWsvYj!WZMqO9QcnmuOFW1Meyz99kMprRvThKzV7ks05COR8SuXGK3V0wF0YjUTgWSmSNZEQaeOVCgsv8A7fQ1QScO5n7TpEq0OAzUf2jmAx!e!sXG_E7EeBKObVGgkU!M8EIUj4ebxV6367NETHu6sDon4Zn0K9b86z_JwOEaJ047oXagTq1HQ9JD4x4x!VZ4csCy!uDqS&win_price=6.750039\",\"http:\\/\\/47.110.246.50:8085\\/zwshow.jsp?r=5d53b9184a6837063&ext=da56bEnWibreH\\/qX62M7Eto4tyuX3n3pCj3HBCwrIRdtLWrzLQ\"],\"overdown\":[\"http:\\/\\/et.w.inmobi.cn\\/c.asm\\/HDaOu_if46qyBRbG9QoUWBTIAhWqDBsDWAQoNmI3Y2QzMjExNmNhZmQxZmRiYTY0OWVkNTc3NGMzYWFjMTI1ZDE4YiAoOWM4ZTkyYTAzNGU4ZTA4M2EwZmFhOTcxMjk4MGQ4ZTRlY2NiNjAyMB4gOTg4Njc3ZDU0YjU2NTI1ZWFhNDYwMjA1ZDFlODU2YjccFQQVGBUCFdgEFQIlACgDZGlyABMAHBdxGw3gLfhDQBc9CtejcBldQAAVABggWTI5dExtNWxkMk5oY0dWakxtMXZZbWxzWlM1dVkzQX4WlMN3HBwW_7-f6Z_a_PThARaX_f__37ze_0sAABUeFwAAAAAAgExAJAAcEhkFABaUw3cYA1VTRBa-3ea-qFgZ9RwUvAOCG74-kk6CfaasAaqsAaysAa6sAbytAYCvAdK7Ada7AfrrAcLtAYrvAYL6AdKJAuaZApqaAvDBAuDXArz9AtyCA7aHA7iHA_KlAyEYTQwAAQsAAQAAACA3NWY3MDEyMjY0NGI0ZDk0OTk4MDFiMzkzNmIwM2RmYgoAAgAAAAAAE0csAAQAAj-GhysCDEm6CgAEAAAAAAAU1AIAOCIEAAFATIAAAAAAAAQAAj-EeuFHrhR7DAADCAABAAAAAQAAGDcDAAEBCAACAAAADQYABQKABgAGBHALAAcAAAAFMy4wLjALAAgAAAADQVBQCAAJAAAAAQIACgAAGMMBCgABRlJ5cR9wroMLAAIAAACwCwABAAAAF2NvbS5uZXdjYXBlYy5tb2JpbGUubmNwAwACAAIABAECAAUABgAGEBALAAcAAAAEUlRCRAsACQAAACA3NWY3MDEyMjY0NGI0ZDk0OTk4MDFiMzkzNmIwM2RmYggADgAAEPoLAA8AAAAJMTA4MFgxOTIwBgAQAAALABEAAAAgNzVmNzAxMjI2NDRiNGQ5NDk5ODAxYjM5MzZiMDNkZmIKABIAAAAAAF4w-AAAABj-ATa-3ea-qFioOGhCeWNUQ3lSWkZwUGN1N1lTRnhEZ1pwSEIvejl5dnZYYmhLNEpIWDBVdzF1a3QzdnVCZk9aUT09GAZOQVRJVkUcPBwW_7-f6Z_a_PThARaX_f__37ze_0sAFoa6hfej3LzSjAEiAAA59RyArwGCfYIbgvoBiu8Bkk4UmpoCpqwBqqwBrKwBrqwBtocDuIcDvK0BvP0CvAO-PsLtAdK7AdKJAta7AdyCA-DXAuaZAvDBAvKlA_rrARwAltalHRwVAAAsFQAAhQIRLBb_v5_pn9r89OEBFpf9_5zUvN6HSgAlygEVCjgJMTcwODA0NDk1NAZZFAIAGAE3AA\\/ea69418b?m=1&ts=$TS\"],\"clickurl\":\"http:\\/\\/c2.gdt.qq.com\\/gdt_mclick.fcg?viewid=vADWg3dmonOA28WnvDPjxDWsvYj!WZMqO9QcnmuOFW1Meyz99kMprRvThKzV7ks05COR8SuXGK3V0wF0YjUTgWSmSNZEQaeOVCgsv8A7fQ1QScO5n7TpEq0OAzUf2jmAx!e!sXG_E7EeBKObVGgkU!M8EIUj4ebxV6367NETHu6sDon4Zn0K9b86z_JwOEaJ047oXagTq1HQ9JD4x4x!VZ4csCy!uDqS&jtype=0&i=1&os=2&clklpp=__CLICK_LPP__&cdnxj=1&xp=2&acttype=35\",\"ckurls\":[\"http:\\/\\/et.w.inmobi.cn\\/c.asm\\/HDaOu_if46qyBRbG9QoUWBTIAhWqDBsDWAQoNmI3Y2QzMjExNmNhZmQxZmRiYTY0OWVkNTc3NGMzYWFjMTI1ZDE4YiAoOWM4ZTkyYTAzNGU4ZTA4M2EwZmFhOTcxMjk4MGQ4ZTRlY2NiNjAyMB4gOTg4Njc3ZDU0YjU2NTI1ZWFhNDYwMjA1ZDFlODU2YjccFQQVGBUCFdgEFQIlACgDZGlyABMAHBdxGw3gLfhDQBc9CtejcBldQAAVABggWTI5dExtNWxkMk5oY0dWakxtMXZZbWxzWlM1dVkzQX4WlMN3HBwW_7-f6Z_a_PThARaX_f__37ze_0sAABUeFwAAAAAAgExAJAAcEhkFABaUw3cYA1VTRBa-3ea-qFgZ9RwUvAOCG74-kk6CfaasAaqsAaysAa6sAbytAYCvAdK7Ada7AfrrAcLtAYrvAYL6AdKJAuaZApqaAvDBAuDXArz9AtyCA7aHA7iHA_KlAyEYTQwAAQsAAQAAACA3NWY3MDEyMjY0NGI0ZDk0OTk4MDFiMzkzNmIwM2RmYgoAAgAAAAAAE0csAAQAAj-GhysCDEm6CgAEAAAAAAAU1AIAOCIEAAFATIAAAAAAAAQAAj-EeuFHrhR7DAADCAABAAAAAQAAGDcDAAEBCAACAAAADQYABQKABgAGBHALAAcAAAAFMy4wLjALAAgAAAADQVBQCAAJAAAAAQIACgAAGMMBCgABRlJ5cR9wroMLAAIAAACwCwABAAAAF2NvbS5uZXdjYXBlYy5tb2JpbGUubmNwAwACAAIABAECAAUABgAGEBALAAcAAAAEUlRCRAsACQAAACA3NWY3MDEyMjY0NGI0ZDk0OTk4MDFiMzkzNmIwM2RmYggADgAAEPoLAA8AAAAJMTA4MFgxOTIwBgAQAAALABEAAAAgNzVmNzAxMjI2NDRiNGQ5NDk5ODAxYjM5MzZiMDNkZmIKABIAAAAAAF4w-AAAABj-ATa-3ea-qFioOGhCeWNUQ3lSWkZwUGN1N1lTRnhEZ1pwSEIvejl5dnZYYmhLNEpIWDBVdzF1a3QzdnVCZk9aUT09GAZOQVRJVkUcPBwW_7-f6Z_a_PThARaX_f__37ze_0sAFoa6hfej3LzSjAEiAAA59RyArwGCfYIbgvoBiu8Bkk4UmpoCpqwBqqwBrKwBrqwBtocDuIcDvK0BvP0CvAO-PsLtAdK7AdKJAta7AdyCA-DXAuaZAvDBAvKlA_rrARwAltalHRwVAAAsFQAAhQIRLBb_v5_pn9r89OEBFpf9_5zUvN6HSgAlygEVCjgJMTcwODA0NDk1NAZZFAIAGAE3AA\\/ea69418b?m=8&ts=$TS\",\"https:\\/\\/c.w.inmobi.cn\\/c.asm\\/HDaOu_if46qyBRbG9QoUWBTIAhWqDBsDWAQoNmI3Y2QzMjExNmNhZmQxZmRiYTY0OWVkNTc3NGMzYWFjMTI1ZDE4YiAoOWM4ZTkyYTAzNGU4ZTA4M2EwZmFhOTcxMjk4MGQ4ZTRlY2NiNjAyMB4gOTg4Njc3ZDU0YjU2NTI1ZWFhNDYwMjA1ZDFlODU2YjccFQQVGBUCFdgEFQIlACgDZGlyABMBHBdxGw3gLfhDQBc9CtejcBldQAAVABggWTI5dExtNWxkMk5oY0dWakxtMXZZbWxzWlM1dVkzQX4WlMN3HBwW_7-f6Z_a_PThARaX_f__37ze_0sAABUeFwAAAAAAgExAJAAcEhkFABaUw3cYA1VTRBa-3ea-qFgZ9RwUvAOCG74-kk6CfaasAaqsAaysAa6sAbytAYCvAdK7Ada7AfrrAcLtAYrvAYL6AdKJAuaZApqaAvDBAuDXArz9AtyCA7aHA7iHA_KlAyEYTQwAAQsAAQAAACA3NWY3MDEyMjY0NGI0ZDk0OTk4MDFiMzkzNmIwM2RmYgoAAgAAAAAAE0csAAQAAj-GhysCDEm6CgAEAAAAAAAU1AIAOCIEAAFATIAAAAAAAAQAAj-EeuFHrhR7DAADCAABAAAAAQAAGDcDAAEBCAACAAAADQYABQKABgAGBHALAAcAAAAFMy4wLjALAAgAAAADQVBQCAAJAAAAAQIACgAAGMMBCgABRlJ5cR9wroMLAAIAAACwCwABAAAAF2NvbS5uZXdjYXBlYy5tb2JpbGUubmNwAwACAAIABAECAAUABgAGEBALAAcAAAAEUlRCRAsACQAAACA3NWY3MDEyMjY0NGI0ZDk0OTk4MDFiMzkzNmIwM2RmYggADgAAEPoLAA8AAAAJMTA4MFgxOTIwBgAQAAALABEAAAAgNzVmNzAxMjI2NDRiNGQ5NDk5ODAxYjM5MzZiMDNkZmIKABIAAAAAAF4w-AAAABj-ATa-3ea-qFioOGhCeWNUQ3lSWkZwUGN1N1lTRnhEZ1pwSEIvejl5dnZYYmhLNEpIWDBVdzF1a3QzdnVCZk9aUT09GAZOQVRJVkUcPBwW_7-f6Z_a_PThARaX_f__37ze_0sAFoa6hfej3LzSjAEiAAA59RyArwGCfYIbgvoBiu8Bkk4UmpoCpqwBqqwBrKwBrqwBtocDuIcDvK0BvP0CvAO-PsLtAdK7AdKJAta7AdyCA-DXAuaZAvDBAvKlA_rrARwAltalHRwVAAAsFQAAhQIRLBb_v5_pn9r89OEBFpf9_5zUvN6HSgAlygEVCjgJMTcwODA0NDk1NAZZFAIAGAE3AA\\/63765f7e?at=0&am=7&ct=$TS\",\"http:\\/\\/47.110.246.50:8085\\/zwclick.jsp?r=5d53b9184a6837063&ext=da56bEnWibreH\\/qX6zloHdpuv3nFjX\\/uU23CUn0rIRdtLWrzLeD2nKIqGIudobjWt+dgC97WeY8zvzU2xpE8VTI3u+wcRQa7WiYcQ+IHllxMnjKYwwpDbhDK+tjy6Xt6uICnGl3PCewzGi1BfeetbdvBEAVItsjXFyNUF1XSuRfx3+djjV0rGwlnuS3L3De11f+6khJ+g+f68dnlhQ4F\\/tyLh0n8jnvsNzIfj2WhhWiRbMDNgNjtS0\\/AakGEvV+TMY0d+FHhE3+yyiJSu8XBiWQKUkOh\\/eoz1wV5E5igMa6l25xYa5b27zgedznVCxkH0VYZp2VQa342LbW2N30R7Ci4XO5cK2T6EQ6uuCX6snDRR3buePZCmpp4rU0gOnWdbFRmYghKlDfkb1KoQyr24KlImmm4sTdTB6\\/HHBj5PX86jJVxvqX9M2DibSmyG70SzLnjrhYaQutIglv0dSOkXDOHqLN9IifKWTsf+1yqzA\"],\"title\":\"\\u79d8\\u4e66\\u517b\\u6210\\u8bb0\",\"remark\":\"\\u6a21\\u62df\\u5f53\\u603b\\u88c1\\uff0c\\u9080\\u4f60\\u6765\\u9009\\u79d8\\u4e66\\uff01\",\"images\":[\"http:\\/\\/pgdt.ugdtimg.com\\/gdt\\/0\\/DAApCJGAQ4AeAAB3Bc61IaDnW2o86O.jpg\\/0?ck=8bde381fb7fb2a03f9dbf53aa8647a43\"],\"icon\":\"http:\\/\\/pgdt.ugdtimg.com\\/gdt\\/0\\/EAApCJGAEsAEsAAAKcLBc57GUDK2va1S9.jpg\\/0?ck=e739a5d9d9ec02c5e27d5318df39e1c9\",\"adtype\":3}]}";
        List<AdMsg> adMsgs = doDspBroker.toAdMsgs(biddingReq,json.getBytes());
    }
}

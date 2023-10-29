package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;
import com.iwanvi.nvwa.proto.CommonProto.OSType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-04-26 20:38
 * @version: v1.0
 * @Description:
 */
public class ThreeSixDspTest {

//    @Test
    public void testCall(){

        // 设置mock的 BidFloorResolver 直接返回低价
        NonStandardRtbProtocolDspAdGetter.setBidFloorResolver((biddingReq, bid, dspId) -> 1);

        Dsp.Builder builder = Dsp.newBuilder().setDspId("360").setQps(0).setRtbUrl("http://ad-test.mediav.com/apitest?ad_type=0&ad_num=1&ad_size=720x1080").setStatus(true);

        // 固价设置
        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("IRfUbi").setPrice(20));

        // DSP渠道
        builder.setDspTarget(AdModelsProto.DspTarget.newBuilder()
                .addAdPosTargets(AdModelsProto.AdPositionTarget.newBuilder().addAllTagids(Arrays.asList("IRfUbi")))
                .setChannelTarget(AdModelsProto.ChannelTarget.newBuilder().addChannelIds("1073")));

        // 关系映射本地测试需要配置
        AdModelsProto.SspAdPosition sspAdPosition = AdModelsProto.SspAdPosition.newBuilder().addAdPositionMappings(AdModelsProto.SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("5kacdJqAfx").setDspAdType(CreativeType.kScreen).setDspId(builder.getDspId())).setAdPosId("IRfUbi").build();
        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(960).setWidth(640).addCreativeType(CreativeType.kNative).setAppName("爱看书").setAppVersion("5.0.1").setBundle("com.mfyueduqi.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("IRfUbi")).setAdType(CreativeType.kScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
               .setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setIp("125.33.24.122").setOsv("8.0.0")
                .setScreenHeight(1920)
                .setScreenWidth(1080)
                .setTerminalType(CommonProto.TerminalType.kMobileTerminal)
                .setDeviceType(CommonProto.DeviceType.kPhone)
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

}

/*  请求demo
{
    "adspaces": [
        {
            "adspace_id": "a5ab5IOCEC",
            "adspace_position": 1,
            "adspace_type": 6,
            "allowed_html": false,
            "height": 500,
            "width": 600
        }
    ],
    "app": {
        "app_name": "免费电子书",
        "app_version": "v1.0",
        "package_name": "com.mianfeia.book"
    },
    "bid": "6dc12a6573cf28c1188d0eb99e107d34",
    "device": {
        "brand": "Honor",
        "carrier_id": 70120,
        "device_id": [
            {
                "device_id": "862791034585233",
                "device_id_type": 1,
                "hash_type": 0
            }
        ],
        "device_type": 2,
        "model": "Che1-CL20",
        "os_type": 2,
        "os_version": "5.0.1"
    },
    "ip": "203.100.81.50",
    "network_type": 1,
    "uid": "9c47f7755c932a8b40e79efd5bb08582",
    "user_agent": "Mozilla/5.0 (Linux; Android 5.0.1; SM-N7588V Build/JLS36C; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36"
}
*/

/* 响应demo
{
    "bid": "6dc12a6573cf28c1188d0eb99e107d34",
    "ads": [
        {
            "adspace_id": "a5ab5IOCEC",
            "creative": [
                {
                    "adm": {
                        "native": {
                            "desc": "广告类型: 0,请求序号:287643,ShowId:a5ab5IOCEC,#第_0_个广告",
                            "img": {
                                "height": 1080,
                                "url": "https://test-m.mediav.com/img/720x1080.jpg",
                                "width": 720
                            },
                            "logo": "https://test-m.mediav.com/img/logo.jpg",
                            "title": {
                                "text": "广告类型: 0,请求序号:287643,ShowId:a5ab5IOCEC,#第_0_个广告"
                            }
                        }
                    },
                    "adm_type": "NATIVE",
                    "adspace_slot_seq": 0,
                    "banner_id": 11,
                    "event_track": [
                        {
                            "event_type": "SHOW",
                            "notify_url": [
                                "http://ad-test.mediav.com/t/show/1/287643/a5ab5IOCEC/0/0/1?_ad_banner_type=11&cst=__EVENT_TIME_START__&cet=__EVENT_TIME_END__&cx=__OFFSET_X__&cy=__OFFSET_Y__&_macro_v=yes&_track_name=show_0",
                                "https://ad-test.mediav.com/t/show/1/287643/a5ab5IOCEC/0/1/0?_ad_banner_type=11&_macro_v=no&_track_name=show_0",
                                "http://ad-test.mediav.com/t/show/1/287643/a5ab5IOCEC/0/2/0?_ad_banner_type=11&_macro_v=no&_track_name=show_0"
                            ]
                        },
                        {
                            "event_type": "CLICK",
                            "notify_url": [
                                "http://ad-test.mediav.com/t/click/2/287643/a5ab5IOCEC/0/0/1?_ad_banner_type=11&cst=__EVENT_TIME_START__&cet=__EVENT_TIME_END__&cx=__OFFSET_X__&cy=__OFFSET_Y__&_macro_v=yes&_track_name=click_0",
                                "https://ad-test.mediav.com/t/click/2/287643/a5ab5IOCEC/0/1/0?_ad_banner_type=11&_macro_v=no&_track_name=click_0",
                                "http://ad-test.mediav.com/t/click/2/287643/a5ab5IOCEC/0/2/0?_ad_banner_type=11&_macro_v=no&_track_name=click_0"
                            ]
                        }
                    ],
                    "interaction_object": {
                        "url": "http://ad-test.mediav.com/t/curl/0/287643/a5ab5IOCEC/0/0/1?_ad_banner_type=11&cst=__EVENT_TIME_START__&cet=__EVENT_TIME_END__&cx=__OFFSET_X__&cy=__OFFSET_Y__&_macro_v=yes&_track_name=curl_0"
                    },
                    "interaction_type": "BROWSE",
                    "open_type": "ALL"
                }
            ]
        }
    ]
}
 */
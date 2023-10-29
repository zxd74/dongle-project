package com.iwanvi.adserv.ngx.router;

import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.broker.liebao.VAST;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import org.junit.Assert;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

/**
 * @author: 郑晓东
 * @date: 2019-06-10 19:14
 * @version: v1.0
 * @Description: 猎豹DSP测试
 */
public class LieBaoDspTest {

//    @Test
    public void call(){

        Dsp.Builder builder = Dsp.newBuilder().setDspId("2UvmIj").setQps(0).setRtbUrl("http://193.112.135.45/b?publisherid=11370").setStatus(true);
//.setWidth(200).setHeight(140)
        SspAdPosition sspAdPosition = SspAdPosition.newBuilder()
                .addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("3582103").setDspId(builder.getDspId()).setWidth(210).setHeight(210))
                .setAdPosId("7b2aia").setBidfloor(80).setAdType(CreativeType.kNative).build();

        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(210).setWidth(320).addCreativeType(CreativeType.kNative).setAppId(ByteString.copyFromUtf8("EzqIze")).setAppName("免费电子书").setAppVersion("4.6.1").setBundle("com.mianfei.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("7b2aia")).setAdType(CreativeType.kNative).build();
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
    public void videoCall(){
        Dsp.Builder builder = Dsp.newBuilder().setDspId("2UvmIj").setQps(0).setRtbUrl("http://193.112.135.45/b?publisherid=11370").setStatus(true);

//        builder.setIsFixedPrice(true).addDspAdPositionPrice(Dsp.DspAdPositionPrice.newBuilder().setTagid("7b2aia").setPrice(800));

        SspAdPosition sspAdPosition = SspAdPosition.newBuilder()
                .addAdPositionMappings(SspAdPosition.AdPositionMapping.newBuilder().setDspAdPositionId("3582104").setDspId(builder.getDspId()).setWidth(640).setHeight(960))
                .setAdPosId("7b2aia").setBidfloor(1).setAdType(CreativeType.kVideo).build();

        RepositoryFactory.getRepository().saveOrUpdateSspAdPosition(sspAdPosition);

        PosInfo posInfo = PosInfo.newBuilder().setHeight(960).setWidth(640).addCreativeType(CreativeType.kVideo).setAppId(ByteString.copyFromUtf8("7r2MNf")).setAppName("免费追书").setAppVersion("4.6.1").setBundle("com.mianfeizs.book")
                .setId(UUIDUtils.uuid()).setPosId(ByteString.copyFromUtf8("7b2aia")).setAdType(CreativeType.kFirstScreen).build();
        UserInfo userInfo = UserInfo.newBuilder().setCarrier(Carrier.kMobile).setConnectType(ConnectType.kWifi)
                .setOs(OSType.kAndroid).setMuid(ByteString.copyFromUtf8("868030033267004"))
                .setIp("125.33.24.122").setOsv("8.0.0").setScreenHeight(1920).setScreenWidth(1080).setDensity(1.0).setAdid("25c9784052ef298e")
                .setTerminalType(TerminalType.kMobileTerminal)
                .setDeviceType(DeviceType.kPhone).setDeviceModel(ByteString.copyFromUtf8("Xiaomi")).setDeviceBrand(ByteString.copyFromUtf8("MI 6"))
                .setUa("Mozilla/5.0 (Linux; Android 8.0.0; MI 6 Build/OPR1.170623.027; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/71.0.3578.99 Mobile Safari/537.36")
                .build();
        BiddingReq.Builder brb = BiddingReq.newBuilder().addPosInfo(posInfo).setUserInfo(userInfo).setIsDebug(false).setId(UUIDUtils.uuid());
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

    public static void main(String[] args) throws JAXBException {
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<VAST version=\"4.0\">" +
                "<Ad>" +
                "<InLine>" +
                "<AdSystem version=\"3.0\">Orion 3.0</AdSystem>" +
                "<AdTitle></AdTitle>" +
                "<Impression><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_q uWwbPWPWnhPWP×tamp={TIMESTAMP}&ac=50]]></Impression>" +
                "<Description>前888名下载 者立赠20000000金币，仅限今日！</Description>" +
                "<Extensions>" +
                "<Extension type=\"application/xml\">" +
                "<Button name=\"orion\">立即下载</Button>" +
                "<Caption name=\"desc\">前888名>下载者立赠20000000金币，仅限今日！ </Caption>" +
                "<MType>8</MType>" +
                "<Rating>0</Rating>" +
                "<DownloadNum></DownloadNum>" +
                "</Extension> " +
                "</Extensions>" +
                "<Creatives>" +
                "<Creative id=\"21314123\" sequence=\"1\">" +
                "<UniversalAdId idRegistry=\"unknown\" idValue=\"unknown\">21314123</UniversalAdId>" +
                "<Linear>" +
                "<Duration>00:00:14</Duration>" +
                "<MediaFiles>" +
                "<MediaFile delivery=\"progressive\" width=\"1280\" height=\"720\" type=\"video/mp4\" encode=\"mp4\">" +
                "<![CDATA[http://cdndownload.liehu.ijinshan.com/liehu/video/20171227/12.26-1_523 06201712271514358591_ori.mp4]]>" +
                "</MediaFile>" +
                "</MediaFiles>" +
                "<VideoClicks>" +
                "<ClickThrough><![CDATA[http://cdndownload.liehu.ijinshan.com/liehu/apk/liebao06_52306201712271514357125.apk]]></ClickThrough>" +
                "<ClickTracking><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_qu×tamp={TIMES TAMP}&ac=64]]></ClickTracking>" +
                "</VideoClicks>" +
                "<TrackingEvents>" +
                "<Tracking event=\"start\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPtimestamp={TIMESTAMP}& event=1&ac=54]]></Tracking>" +
                "<Tracking event=\"firstQuartile\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWtimestamp={TIME STAMP}&event=5&ac=54]]></Tracking>" +
                "<Tracking event=\"midpoint\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPtimestamp={TIMES TAMP}&event=6&ac=54]]></Tracking>" +
                "<Tracking event=\"thirdQuartile\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPtimestamp={TIMES TAMP}&event=7&ac=54]]></Tracking>" +
                "<Tracking event=\"complete\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWtimestamp={TIM ESTAMP}&event=2&ac=54]]></Tracking>" +
                "<Tracking event=\"mute\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWntimestamp={TIMES TAMP}&event=8&ac=54]]></Tracking>" +
                "<Tracking event=\"unmute\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPtimestamp={TIMEST AMP}&event=9&ac=54]]></Tracking>" +
                "<Tracking event=\"pause\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWtimestamp={TIMES TAMP}&event=4&ac=54]]></Tracking>" +
                "<Tracking event=\"resume\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWnhPW×tamp ={TIMESTAMP}&event=11&ac=54]]></Tracking>" +
                "<Tracking event=\"fullscreen\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWnhtimestamp={TI MESTAMP}&event=12&ac=54]]></Tracking>" +
                "<Tracking event=\"exitFullscreen\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWnhPtimesta mp={TIMESTAMP}&event=13&ac=54]]></Tracking>" +
                "<Tracking event=\"skip\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWnhPtimestamp={TIME STAMP}&event=23&ac=54]]></Tracking>" +
                "<Tracking event=\"progress\" offset=\"00:00:03\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWnhtimestamp={TI MESTAMP}&event=22&ac=54]]></Tracking>" +
                "</TrackingEvents>" +
                "<Icons><Icon>" +
                "<StaticResource type=\"image/png\"><![CDATA[http://cdnimg.liehu.ijinshan.com/liehu/20171227/15143587205drtt.png]]></StaticResource>" +
                "</Icon></Icons>" +
                "</Linear>" +
                "</Creative>" +
                "<Creative sequence=\"2\">" +
                "<CompanionAds required=\"any\">" +
                "<Companion height=\"250\" width=\"300\" id=\"pre-roll\">" +
                "<StaticResource type=\"image/jpg\"><![CDATA[http://cdnimg.liehu.ijinshan.com/liehu/20171227/1514358667hto8x.jpg]]></StaticResource>" +
                "<TrackingEvents>" +
                "<Tracking event=\"creativeView\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPWPWtimestamp={TIMESTAMP}&companion=preroll&event=20&ac=54]]></Tracking>" +
                "</TrackingEvents>" +
                "</Companion>" +
                "<Companion height=\"250\" width=\"300\" id=\"post-roll\">" +
                "<StaticResource type=\"image/jpg\"><![CDATA[http://cdnimg.liehu.ijinshan.com/liehu/20171227/1514358667hto8x.jpg]]></StaticResource>" +
                "<TrackingEvents>" +
                "<Tracking event=\"creativeView\"><![CDATA[https://rcv.mobad.ijinshan.com/rp/?IA_quWwbPtimestamp={TIMES TAMP}&companion=postroll&event=20&ac=54]]></Tracking>" +
                "</TrackingEvents>" +
                "<CompanionClickThrough><![CDATA[http://cdndownload.liehu.ijinshan.com/liehu/apk/liebao06_52306201712271514357125.apk]]></CompanionClickThrough>" +
                "<CompanionClickTracking><![CDATA[https://rcv.mobad.ijinshan .com/rp/?IA_quWwbPtimestamp={TIMESTAMP}&ac=64]]></CompanionClickTracking>" +
                "</Companion> " +
                "</CompanionAds>" +
                "</Creative>" +
                "</Creatives>" +
                "</InLine>" +
                "</Ad>" +
                "</VAST>";
        VAST vast = (VAST) xml2Obj(xml,VAST.class);
        Assert.assertTrue(vast!=null);
    }

    private static <T> Object xml2Obj(String xml,Class<T> clazz){
        Object obj = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller um = jaxbContext.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            obj = clazz.newInstance();
            obj = um.unmarshal(sr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

}

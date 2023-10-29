package com.iwanvi.adserv.ngx.router.broker.sogou;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.MD5Utils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 郑晓东
 * @date: 2019-07-05 09:04
 * @version: v1.0
 * @Description: 搜狗数据转换处理
 */
public class SoGouDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(SoGouDspBroker.class);
    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("qidian.dspid");
    private static final String API_VER = MinervaCfg.get().getConfigProperty("qidian.apiver");
    private static final String PLATFORMID = MinervaCfg.get().getConfigProperty("qidian.platformid");
    private static final String TOKEN = MinervaCfg.get().getConfigProperty("qidian.token");
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    private static final Map<ConnectType, Integer> netHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier, Integer> carrierHolder = new ConcurrentHashMap<>();

    static {
        netHolder.put(ConnectType.k5g, SoGouEnumUtil.NetType.K5G.getValue());
        netHolder.put(ConnectType.k4g, SoGouEnumUtil.NetType.K4G.getValue());
        netHolder.put(ConnectType.k3g, SoGouEnumUtil.NetType.K3G.getValue());
        netHolder.put(ConnectType.k2g, SoGouEnumUtil.NetType.K2G.getValue());
        netHolder.put(ConnectType.kWifi, SoGouEnumUtil.NetType.WIFI.getValue());
        netHolder.put(ConnectType.kConnectTypeUnKnown, SoGouEnumUtil.NetType.UNKNOWN.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, SoGouEnumUtil.Carrier.OTHER.getValue());
        carrierHolder.put(Carrier.kMobile, SoGouEnumUtil.Carrier.CHAIN_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, SoGouEnumUtil.Carrier.CHAIN_UNION.getValue());
        carrierHolder.put(Carrier.kTelecom, SoGouEnumUtil.Carrier.CHAIN_TELECOM.getValue());
    }

    @Override
    public String getDspId() {
        return DSP_ID;
    }

    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {
        BidRequest.Builder builder = BidRequest.builder();
        PosInfo posInfo = biddingReq.getPosInfo(0);
        CreativeType creativeType = posInfo.getCreativeType(0);
        UserInfo userInfo = biddingReq.getUserInfo();
        OSType osType = userInfo.getOs();

        boolean isDebug = biddingReq.getIsDebug();

        // 构建请求信息
        AdModelsProto.SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());
        if (positionMapping == null || !positionMapping.hasDspAdPositionId()) {
            throw new AdNgxException("搜狗奇点广告位映射ID获取异常,Pos Id 为:" + posInfo.getPosId());
        }
        String requestId = PLATFORMID + positionMapping.getDspAdPositionId() + System.currentTimeMillis();
        builder.withRequestId(requestId).withAuth(MD5Utils.md5Hex(requestId + TOKEN)).withVersion(API_VER);

        // 构建device
        BidRequest.Device.Builder device = BidRequest.Device.builder();
        if (osType == OSType.kAndroid) {
            // Android
            device.withDeviceId(MD5Utils.md5Hex(userInfo.getMuid().toStringUtf8()).toUpperCase())
                    .withDeviceType(SoGouEnumUtil.DeviceType.PHONE.getValue())
                    .withImei(userInfo.getMuid().toStringUtf8())
                    .withImeiMd5(MD5Utils.md5Hex(userInfo.getMuid().toStringUtf8()).toUpperCase())
                    .withAndroidId(userInfo.getAdid().toUpperCase()).withOs(SoGouEnumUtil.OsType.ANDROID.getValue());
        } else if (osType == OSType.kIOS) {
            // IOS
            device.withDeviceId(userInfo.getMuid().toStringUtf8().toUpperCase())
                    .withDeviceType(SoGouEnumUtil.DeviceType.PHONE.getValue())
                    .withIdfa(MD5Utils.md5Hex(userInfo.getMuid().toStringUtf8()).toUpperCase())
                    .withOs(SoGouEnumUtil.OsType.IOS.getValue());
        }
        String mac = "00:00:00:00:00:00";
        if (userInfo.hasMac() && userInfo.getMac() != null && !userInfo.getMac().trim().equals("")) {
            mac = userInfo.getMac();
        }
        device.withBrand(userInfo.getDeviceBrand().toStringUtf8()).withModel(userInfo.getDeviceModel().toStringUtf8()).withOsVersion(userInfo.getOsv())
                .withNetwork(netHolder.get(userInfo.getConnectType())).withCarrier(carrierHolder.get(userInfo.getCarrier()))
                .withMac(mac).withIp(userInfo.getIp()).withUserAgent(userInfo.getUa())
                .withGeo(BidRequest.Geo.builder().withLatitude(userInfo.getLat()).withLongitude(userInfo.getLon()).build())
                .withOrientation(SoGouEnumUtil.Orientation.ORIENTATION_PORTRAIT.getValue())
                .withScreenWidth(userInfo.getScreenWidth()).withScreenHeight(userInfo.getScreenHeight());
        builder.withDevice(device.build());

        // 构建App
        builder.withApp(BidRequest.App.builder().withPkgName(posInfo.getBundle()).withAppName(posInfo.getAppName()).build());

        // 构建Imp广告请求信息
        BidRequest.Imp.Builder imp = BidRequest.Imp.builder();
        imp.withId(1).withCount(1);
        if (creativeType == CreativeType.kVideo) {
            // Video请求
            BidRequest.Video.Builder video = BidRequest.Video.builder();
            video.withVideoType(SoGouEnumUtil.AdType.NATIVE_VIDEO.getValue()).withOrientation(SoGouEnumUtil.Orientation.ORIENTATION_PORTRAIT.getValue());
            imp.withVideo(video.build());
        }
        BidRequest.Imp[] imps = new BidRequest.Imp[1];
        imps[0] = imp.build();
        builder.withImp(imps);

        BiddingTracer.trace(isDebug, "搜狗奇点 请求信息: " + JSON.toJSONString(builder.build()));
        return JSON.toJSONBytes(builder.build());
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();

        if (responseBody == null) {
            return null;
        }
        String resBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "搜狗奇点广告请求响应内容：{}", resBody);
        BidResponse bidResponse = JSON.parseObject(resBody, BidResponse.class);
        if (bidResponse == null || bidResponse.getCode() != 0 || bidResponse.getData() == null) {
            BiddingTracer.trace(isDebug, "搜狗奇点无广告响应!");
            if (bidResponse != null) {
                LOG.error("搜狗奇点广告响应错误码：{}", bidResponse.getCode());
            }
            return null;
        }
        List<AdMsg> adMsgs = new ArrayList<>();
        for (BidResponse.Group group : bidResponse.getData().getGroups()) {
            BidResponse.Ad[] ads = group.getAds();
            for (BidResponse.Ad ad : ads) {
                AdMsg adMsg = null;
                try {
                    adMsg = buildAdMsg(ad);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (adMsg != null) {
                    adMsgs.add(adMsg);
                }
            }
        }
        return adMsgs;
    }

    private AdMsg buildAdMsg(BidResponse.Ad ad) throws Exception {
        AdMsg.Builder builder = AdMsg.newBuilder();

        builder.setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm).setLandUrl(ad.getLink()).setCrid(ad.getCreativeId().toString())
                .setTitle(ByteString.copyFromUtf8(URLDecoder.decode(ad.getTitle())))
                .setDescription(URLDecoder.decode(ad.getSummary()));

        // 广告类型处理
        if (ad.getVideo() != null) {
            // 视频广告
            BidResponse.Video video = ad.getVideo();
            builder.setCreativeType(CreativeType.kVideo).setAdDuration(video.getDuration()).setPicUrl(video.getVideoUrl());
        } else if (ad.getImgs() != null) {
            // 图片素材
            AdModelsProto.NativeAd.Builder nab = AdModelsProto.NativeAd.newBuilder();

            if (ad.getNativeAdType().equals(SoGouEnumUtil.NativeAdType.GROUP.getValue())) {
                // 组图处理
                String methodNameFormat = "setThreadPic%d";
                int imageIndex = 1;
                for (BidResponse.Image image : ad.getImgs()) {
                    MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), image.getUrl());
                    imageIndex++;
                }
            } else {
                // 单图处理
                nab.setThreadPic1(ad.getImgs()[0].getUrl());
            }
            nab.setThreadTitle(builder.getTitle().toStringUtf8()).setThreadContent(builder.getDescription()).setUserPortrait(ad.getAdIcon());
            builder.setPicUrl(nab.getThreadPic1()).setNativeAd(nab.build());
        }

        // 广告交互处理
        if (ad.getAppInfo() != null) {
            BidResponse.App app = ad.getAppInfo();
            if (StringUtils.isNoneBlank(app.getDeepLink())) {
                // DeepLink
                builder.setLandUrl(app.getDeepLink()).setFallback(ad.getLink());
            } else if (ad.getDownloadAd() == 1) {
                // 下载类广告
                builder.setExtensionType(ExtensionType.kExtAndroid).setPkgName(app.getPkgName());
            }
        }

        builder.addAllImpMonitorUrl(new ArrayList<>(Arrays.asList(ad.getImpTrackUrls()))).addAllClkMonitorUrl(new ArrayList<>(Arrays.asList(ad.getClickTrackUrls())));
        return builder.build();
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }

}

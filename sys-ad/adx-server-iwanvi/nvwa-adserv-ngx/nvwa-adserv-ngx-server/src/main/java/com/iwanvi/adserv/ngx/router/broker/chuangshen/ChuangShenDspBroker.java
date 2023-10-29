package com.iwanvi.adserv.ngx.router.broker.chuangshen;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.MD5Utils;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: 郑晓东
 * @date: 2019-06-13 09:23
 * @version: v1.0
 * @Description:
 */
public class ChuangShenDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(ChuangShenDspBroker.class);
    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("chuangshen.dspid");
    public static final String API_VER = MinervaCfg.get().getConfigProperty("chuangshen.apiver");
    public static final String SECRET = MinervaCfg.get().getConfigProperty("chuangshen.secret");
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

    private static final Map<ConnectType, String> connectHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier, String> carrierHolder = new ConcurrentHashMap<>();

    static {
        connectHolder.put(ConnectType.kWifi, BidRequest.NetType.WIFI.getValue());
        connectHolder.put(ConnectType.k2g, BidRequest.NetType.K2G.getValue());
        connectHolder.put(ConnectType.k3g, BidRequest.NetType.K3G.getValue());
        connectHolder.put(ConnectType.k4g, BidRequest.NetType.K4G.getValue());
        connectHolder.put(ConnectType.kConnectTypeUnKnown, BidRequest.NetType.WIFI.getValue());
        connectHolder.put(ConnectType.k5g, BidRequest.NetType.K5G.getValue());

        carrierHolder.put(Carrier.kMobile, BidRequest.CarrierType.MOBILE_GSM.getValue());
        carrierHolder.put(Carrier.kUnicom, BidRequest.CarrierType.UNION_GSM.getValue());
        carrierHolder.put(Carrier.kTelecom, BidRequest.CarrierType.TELECOM_CDMA.getValue());
    }

    @Override
    public String getDspId() {
        return DSP_ID;
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();
        String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "创神广告响应：{}", respBody);

        BidResponse bidResponse = JSON.parseObject(respBody, BidResponse.class);
        if (bidResponse == null || bidResponse.getSucc() == false) {
            BiddingTracer.trace(isDebug, "创神无广告响应");
            return null;
        }
        BidResponse.Ad ad = JSON.parseObject(bidResponse.getAds(), BidResponse.Ad.class);
        if (ad == null) {
            BiddingTracer.trace(isDebug, "创神无广告响应");
            return null;
        }

        List<AdMsg> adMsgs = new ArrayList<>();
        AdMsg adMsg = buildAdMsg(ad);
        if (adMsg != null) {
            adMsgs.add(adMsg);
        }
        return adMsgs;
    }

    private AdMsg buildAdMsg(BidResponse.Ad ad) {
        AdMsg.Builder builder = AdMsg.newBuilder();
        builder.setCrid(String.valueOf(ad.getCreativeId())).setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        if (StringUtils.isNoneBlank(ad.getImage())) {
            builder.setPicUrl(ad.getImage());
        } else if (StringUtils.isNoneBlank(ad.getImage2())) {
            builder.setPicUrl(ad.getImage2());
        }


        if (StringUtils.isNoneBlank(ad.getGotourl())) {
            builder.setLandUrl(ad.getGotourl());
        }

        if (ad.getGotourl().endsWith("#page")) {
            // 落地页处理
            String gotourl = ad.getGotourl();
            builder.setLandUrl(gotourl.substring(0, gotourl.lastIndexOf("#page")));
        } else {
            // 下载处理
            builder.setExtensionType(ExtensionType.kExtAndroid);
        }

        AdModelsProto.NativeAd.Builder nab = AdModelsProto.NativeAd.newBuilder();
        nab.setThreadPic1(builder.getPicUrl());
        if (StringUtils.isNoneBlank(ad.getIcon())) {
            nab.setUserPortrait(ad.getIcon());
        }
        if (StringUtils.isNoneBlank(ad.getWords2())) {
            nab.setThreadContent(ad.getWords2());
        }
        if (StringUtils.isNoneBlank(ad.getWords())) {
            nab.setThreadTitle(ad.getWords());
        }
        builder.setNativeAd(nab.build());

        builder.addAllImpMonitorUrl(ad.getShowReport().get(0).stream().map(x -> x.replace("_TIMESTAMP_", "{AUCTION_TIME")).collect(Collectors.toList())).addAllClkMonitorUrl(Arrays.stream(ad.getClickReport()).map(x -> x.replace("_TIMESTAMP_", "{AUCTION_TIME")).collect(Collectors.toList()));

        return builder.build();
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }

    @Override
    public HttpMethod requestMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String toQueryString(BiddingReq biddingReq) {
        BidRequest.Builder builder = BidRequest.builder();
        PosInfo posInfo = biddingReq.getPosInfo(0);
        UserInfo userInfo = biddingReq.getUserInfo();
        OSType osType = userInfo.getOs();

        // 请求时间戳
        Long time = System.currentTimeMillis();

        builder.withVersion(API_VER);

        // 广告位处理
        AdModelsProto.SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());
        if (positionMapping == null) {
            throw new AdNgxException("缺少创神媒体广告位映射配置: " + posInfo.getId());
        }
        builder.withPosId(Integer.valueOf(positionMapping.getDspAdPositionId())).withThumbWidth(positionMapping.getWidth())
                .withThumbHeight(positionMapping.getHeight()).withTimestamp(time);

        // 注意：device -》 设备号 苹果 idfa 或者安卓imei
        if (osType == OSType.kAndroid) {
            builder.withPlatform(BidRequest.PlatformType.ANDROID.getValue()).withAndid(userInfo.getAdid()).withDevice(userInfo.getMuid().toStringUtf8()).withClientType(BidRequest.DeviceType.PHONE.getValue()).withClientOsType(BidRequest.OSType.ANDROID.getValue());
        } else if (osType == OSType.kIOS) {
            builder.withPlatform(BidRequest.PlatformType.IOS.getValue()).withIdfa(userInfo.getMuid().toStringUtf8()).withIdfv("").withDevice(userInfo.getMuid().toStringUtf8()).withClientType(BidRequest.DeviceType.PHONE.getValue()).withClientOsType(BidRequest.OSType.IOS.getValue());
        }

        builder.withIp(userInfo.getIp()).withMac(userInfo.getMac()).withUa(userInfo.getUa()).withClientOsVersion(userInfo.getOsv())
                .withScreenWidth(userInfo.getScreenWidth()).withScreenHeight(userInfo.getScreenHeight())
                .withScreenDensity(userInfo.getDensity()).withClientModel(userInfo.getDeviceBrand().toStringUtf8()).withClientModel(userInfo.getDeviceModel().toStringUtf8());

        // 网络处理
        ConnectType connectType = userInfo.getConnectType();
        Carrier carrier = userInfo.getCarrier();
        if (connectHolder.containsKey(connectType)) {
            builder.withNetStatus(connectHolder.get(connectType));
        }
        if (carrierHolder.containsKey(carrier)) {
            builder.withNetIsp(carrierHolder.get(carrier));
        }

        BidRequest bidRequest = builder.build();

        // 签名处理 vt是 timestamp
        StringBuilder signature = new StringBuilder();
        signature.append("posId=" + bidRequest.getPosId()).append("&thumbWidth=" + bidRequest.getThumbWidth()).append("&thumbHeight=" + bidRequest.getThumbHeight())
                .append("&platform=" + bidRequest.getPlatform()).append("&device=" + bidRequest.getDevice()).append("&timestamp=" + time).append("&secret=" + SECRET);
        bidRequest.setSignature(MD5Utils.md5Hex(signature.toString()));
        QueryStringBuilder<BidRequest> qsBuilder = QueryStringBuilder.create(bidRequest);
        String request = qsBuilder.toQueryString();

        BiddingTracer.trace(biddingReq.getIsDebug(), "创神广告请求内容：{}", request);
        return request;
    }

}

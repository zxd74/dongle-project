package com.iwanvi.adserv.ngx.router.broker.maihan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.BiddingProto;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.CommonProto;

/**
 * @author: 郑晓东
 * @date: 2019-06-03 17:23
 * @version: v1.0
 * @Description: 智云众 DspBroker 请求和响应转换处理实现类
 */
public class MaiHanDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(MaiHanDspBroker.class);
    private static final String MAIHAN_DSP_ID = MinervaCfg.get().getConfigProperty("maihan.dspid");
    public static final String MAIHAN_API_VER = MinervaCfg.get().getConfigProperty("maihan.apiver");
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

    private static final Map<CommonProto.ConnectType, Integer> connectHolder = new ConcurrentHashMap<>();
    private static final Map<CommonProto.Carrier, Integer> carrierHolder = new ConcurrentHashMap<>();

    static {
        connectHolder.put(CommonProto.ConnectType.kConnectTypeUnKnown, BidRequest.ConnectType.UNKNOWN.getValue());
        connectHolder.put(CommonProto.ConnectType.kWifi, BidRequest.ConnectType.WIFI.getValue());
        connectHolder.put(CommonProto.ConnectType.k2g, BidRequest.ConnectType.K2G.getValue());
        connectHolder.put(CommonProto.ConnectType.k3g, BidRequest.ConnectType.K3G.getValue());
        connectHolder.put(CommonProto.ConnectType.k4g, BidRequest.ConnectType.K4G.getValue());
        connectHolder.put(CommonProto.ConnectType.k5g, BidRequest.ConnectType.UNKNOWN.getValue());

        carrierHolder.put(CommonProto.Carrier.kCarrierUnKnown, BidRequest.Carrier.UNKNOWN.getValue());
        carrierHolder.put(CommonProto.Carrier.kMobile, BidRequest.Carrier.CHINA_MOBILE.getValue());
        carrierHolder.put(CommonProto.Carrier.kUnicom, BidRequest.Carrier.CHINA_UNICOM.getValue());
        carrierHolder.put(CommonProto.Carrier.kTelecom, BidRequest.Carrier.CHINA_TELECOM.getValue());

    }

    @Override
    public String getDspId() {
        return MAIHAN_DSP_ID;
    }

    @Override
    public byte[] toDspRequest(BiddingProto.BiddingReq biddingReq) {

        PosInfo posInfo = biddingReq.getPosInfo(0);
        BiddingProto.UserInfo userInfo = biddingReq.getUserInfo();

        // APP映射处理
        AdModelsProto.App.AppMapping appMapping = RtbUtils.getMappingDspApp(getDspId(), posInfo.getAppId().toStringUtf8());
        if (appMapping == null) {
            throw new AdNgxException("缺少智云众媒体App映射配置");
        }
        BidRequest.Media.Builder media = BidRequest.Media.builder().withAppId(appMapping.getDspAppid()).withAppBundleId(posInfo.getBundle()).withAppVersion(posInfo.getAppVersion());

        // 广告位映射处理
        String posId = RtbUtils.getMappingDspAdSlotId(biddingReq, getDspId());
        if (posId == null) {
            throw new AdNgxException("缺少智云众广告位映射配置");
        }
        BidRequest.Pos.Builder pos = BidRequest.Pos.builder().withId(posId);

        // 网络处理
        BidRequest.Network.Builder network = BidRequest.Network.builder().withConnectType(connectHolder.get(userInfo.getConnectType())).withCarrier(carrierHolder.get(userInfo.getCarrier()));

        BidRequest.Builder builder = BidRequest.builder().withApiVersion(MAIHAN_API_VER).withPos(pos.build()).withMedia(media.build()).withDevice(buildDevice(biddingReq)).withNetwork(network.build());

        BiddingTracer.trace(biddingReq.getIsDebug(), "打印智云众的请求数据内容:{}", JSON.toJSONString(builder.build()));
        return JSON.toJSONBytes(builder.build());
    }

    /**
     * 设备对象处理
     *
     * @param biddingReq
     */
    private BidRequest.Device buildDevice(BiddingProto.BiddingReq biddingReq) {
        BiddingProto.UserInfo userInfo = biddingReq.getUserInfo();
        CommonProto.OSType os = userInfo.getOs();
        BidRequest.Device.Builder device = BidRequest.Device.builder();

        if (os == CommonProto.OSType.kAndroid) {
            device.withImei(userInfo.getMuid().toStringUtf8()).withDeviceType(BidRequest.DeviceType.MOBILE.getValue()).withOs(BidRequest.OSType.ANDROID.getValue()).withOsVersion(userInfo.getOsv()).withAndroidId(userInfo.getAdid());
        }else if (os == CommonProto.OSType.kIOS) {
            device.withIdfa(userInfo.getMuid().toStringUtf8()).withDeviceType(BidRequest.DeviceType.MOBILE.getValue()).withOs(BidRequest.OSType.IOS.getValue()).withOsVersion(userInfo.getOsv());
        }
        device.withManufacturer(userInfo.getDeviceBrand().toStringUtf8()).withModel(userInfo.getDeviceModel().toStringUtf8()).withMac(userInfo.getMac()).withScreenWidth(userInfo.getScreenWidth())
                .withScreenHeight(userInfo.getScreenHeight()).withUa(userInfo.getUa()).withDpi((int) userInfo.getDensity()).withOrientation(BidRequest.Orientation.PORTRAIT.getValue());

        return device.build();
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public Map<String, String> getHeaders(BiddingReq biddingReq) {
        Map<String, String> headers = new HashMap<>(1);
        headers.put("X-Forwarded-For", biddingReq.getUserInfo().getIp());
       
        return headers;
    }

    /**
     * 广告响应格式为：
     * {
     * "ret": 0,
     * "msg": "",
     * "data": {
     * "A501075E0894": [
     * {
     * "ad_id": "rHmsrj0sPBfb",
     * "impression_link": []
     * }
     * ]
     * }
     * 其中 A501075E0894 是广告位ID（对方ID）
     *
     * @param biddingReq
     * @param responseBody
     * @return
     */
    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();
        List<AdMsg> adList = new ArrayList<>();
        List<BidResponse> ads = new ArrayList<>();
        try {
            String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
            BiddingTracer.trace(isDebug, "智云众 dsp平台广告响应：{}", respBody);

            // 以TypeReference方式接收JSON动态key数据
            LinkedHashMap<String, String> jsonMap = JSON.parseObject(respBody, new TypeReference<LinkedHashMap<String, String>>() {});
            String dataValue = jsonMap.get("data");
            if (dataValue != null && dataValue != "{}") {
                // 以TypeReference方式接收JSON动态key数据
                LinkedHashMap<String, String> data = JSON.parseObject(dataValue, new TypeReference<LinkedHashMap<String, String>>() {
                });
                // 获取广告位ID
                String posId = RtbUtils.getMappingDspAdSlotId(biddingReq, getDspId());
                // 广告响应内容
                String adValue = data.get(posId);
                if (adValue != null) {
                    // 转换为BidResponse
                    ads = JSON.parseArray(adValue, BidResponse.class);
                    if (ads == null || ads.size() == 0) {
                        BiddingTracer.trace(isDebug, "智云众 dsp 返回广告为空");
                    }
                }
            }

            for (BidResponse ad : ads) {
                adList.add(toAdMsg(biddingReq, ad));
            }
        } catch (Throwable ex) {
            LOG.error("", ex);
        }
        return adList;
    }

    /**
     * 广告数据转换处理
     *
     * @param biddingReq
     * @param ad
     * @return
     */
    private AdMsg toAdMsg(BiddingReq biddingReq, BidResponse ad) throws Exception {

        PosInfo posInfo = biddingReq.getPosInfo(0);
        CommonProto.CreativeType creativeType = posInfo.getCreativeType(0);

        // 固定价格，无需做价格处理
        AdMsg.Builder builder = AdMsg.newBuilder().setCrid(ad.getAdId()).setDspId(getDspId()).setCreativeType(creativeType)
                .setDspType(CommonProto.DspType.kDspTypePublic).setLandUrl(ad.getClickLink()).setCostType(CommonProto.CostType.kCpm);

        // 广告素材处理
        builderAd(builder, ad);

        if (ad.getInteractType().equals(BidResponse.InteractType.DOWNLOAD.getValue())) {
            // Download处理
            builder.setExtensionType(CommonProto.ExtensionType.kExtAndroid);
            // 包名处理
            if (StringUtils.isNoneBlank(ad.getAppPackage())) {
                builder.setPkgName(ad.getAppPackage());
            }
        } else if (ad.getInteractType().equals(BidResponse.InteractType.DEEP_LINK.getValue())) {
            // DeepLink处理
            if (StringUtils.isNoneBlank(ad.getDeeplinkUrl())) {
                builder.setLandUrl(ad.getDeeplinkUrl());
            }
            // 唤醒失败下载处理处理
            if (ad.getFallbackType() != null && ad.getFallbackType().equals(BidResponse.FallbackType.DOWN.getValue())) {
                builder.setExtensionType(CommonProto.ExtensionType.kExtAndroid);
            }
            // 包名处理
            if (StringUtils.isNoneBlank(ad.getAppPackage())) {
                builder.setPkgName(ad.getAppPackage());
            }
            // 失败响应url
            if (StringUtils.isNoneBlank(ad.getFallbackUrl())) {
                builder.setFallback(ad.getFallbackUrl());
            } else {
                builder.setFallback(ad.getClickLink());
            }
        }

        // 曝光和点击监测事件url处理
        builder.addAllImpMonitorUrl(Arrays.asList(ad.getImpressionLink())).addAllClkMonitorUrl(Arrays.asList(ad.getClickTrack()));

        return builder.build();
    }

    /**
     * 广告素材处理
     *
     * @param builder
     * @param ad
     * @throws Exception
     */
    private void builderAd(AdMsg.Builder builder, BidResponse ad) throws Exception {

        if (ad.getAdSpec().equals(BidResponse.AdSpecType.UP_CON_DOWN_VIDEO.getValue())) {
            // 视频广告处理
            if (StringUtils.isNoneBlank(ad.getVideoUrl())) {
                builder.setPicUrl(ad.getVideoUrl()).setCreativeType(CommonProto.CreativeType.kVideo).setAdDuration(ad.getVideoDuration());
            }
        } else {
            // 图文广告处理
            AdModelsProto.NativeAd.Builder nab = AdModelsProto.NativeAd.newBuilder();
            if (StringUtils.isNoneBlank(ad.getTitle())) {
                nab.setThreadTitle(ad.getTitle());
            }
            if (ad.getDescription() != null && ad.getDescription().length != 0) {
                nab.setThreadContent(ad.getDescription()[0]);
            }
            if (ad.getIcon() != null && ad.getIcon().length != 0) {
                nab.setUserPortrait(ad.getIcon()[0]);
            }

            String methodNameFormat = "setThreadPic%d";
            int imageIndex = 1;
            if (ad.getImgUrl()==null || ad.getImgUrl().length==0){
                throw new AdNgxException("智云众图文广告无图片！");
            }
            String[] imgs = ad.getImgUrl();
            for (String img : imgs) {
                MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), img);
                imageIndex++;
            }
            // 开屏广告处理
            builder.setPicUrl(nab.getThreadPic1()).setNativeAd(nab.build());
        }
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }
}

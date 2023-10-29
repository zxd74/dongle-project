package com.iwanvi.adserv.ngx.router.broker.yungao;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.DspLoggers;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.CodeUtils;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 郑晓东
 * @version : v1.0
 * @since : 2019-08-09 13:52
 */
public class YunGaoDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(YunGaoDspBroker.class);

    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("yungao.dspid");
    private static final String API_VER = MinervaCfg.get().getConfigProperty("yungao.apiver");
    private static final Dsp DSP = RtbUtils.getDsp(DSP_ID) ;

    private static final Map<DeviceType, Integer> deviceTypeHolder = new ConcurrentHashMap<>();
    private static final Map<ConnectType, Integer> netTypeHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier, Integer> carrierHolder = new ConcurrentHashMap<>();


    static {
        deviceTypeHolder.put(DeviceType.kPhone, YunGaoUtil.DeviceType.PHONE.getValue());
        deviceTypeHolder.put(DeviceType.kTV, YunGaoUtil.DeviceType.TV.getValue());
        deviceTypeHolder.put(DeviceType.kPad, YunGaoUtil.DeviceType.TABLET.getValue());

        netTypeHolder.put(ConnectType.kConnectTypeUnKnown, YunGaoUtil.NetType.CONNECTION_UNKNOWN.getValue());
        netTypeHolder.put(ConnectType.k2g, YunGaoUtil.NetType.CELL_2G.getValue());
        netTypeHolder.put(ConnectType.k3g, YunGaoUtil.NetType.CELL_3G.getValue());
        netTypeHolder.put(ConnectType.k4g, YunGaoUtil.NetType.CELL_4G.getValue());
        netTypeHolder.put(ConnectType.k5g, YunGaoUtil.NetType.CELL_5G.getValue());
        netTypeHolder.put(ConnectType.kWifi, YunGaoUtil.NetType.WIFI.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, YunGaoUtil.Carrier.UNKNOWN_OPERATOR.getValue());
        carrierHolder.put(Carrier.kMobile, YunGaoUtil.Carrier.CHINA_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, YunGaoUtil.Carrier.CHINA_UNICOM.getValue());
        carrierHolder.put(Carrier.kTelecom, YunGaoUtil.Carrier.CHINA_TELECOM.getValue());
    }

    @Override
    public String getDspId() {
        return DSP_ID;
    }

    @Override
    public String getRequestContentType() {
        return null;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = false;

        if (biddingReq.getIsDebug() || DSP.getIsTest()){
            isDebug = biddingReq.getIsDebug();
        }

        String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "云告：{}，本次：{}，广告响应：{}", DSP_ID, biddingReq.getId(), respBody);

        BidResponse bidResponse = JSON.parseObject(respBody, BidResponse.class);
        if (bidResponse == null || bidResponse.getAds() == null || bidResponse.getAds().length == 0) {
            BiddingTracer.trace(isDebug, "云告：{}，本次：{}，无广告响应。", DSP_ID, biddingReq.getId());
            DspLoggers.code(biddingReq,getDspId(), CodeUtils.RESPONSE_NOT_AD);
            return null;
        }

        List<AdMsg> adMsgs = new ArrayList<>();
        try {
            for (BidResponse.Ad ad : bidResponse.getAds()) {
                AdMsg adMsg = buildAdMsg(ad);
                if (adMsg != null) {
                    adMsgs.add(adMsg);
                }
            }
        } catch (Exception ex) {
            DspLoggers.code(biddingReq,getDspId(), CodeUtils.AD_HANDLER_ERROR);
            LOG.error("", ex);
        }

        return adMsgs;
    }

    private AdMsg buildAdMsg(BidResponse.Ad ad) throws Exception {
        AdMsg.Builder builder = AdMsg.newBuilder();

        builder.setDspId(DSP_ID).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        if (StringUtils.isNoneBlank(ad.getAdKey())) {
            builder.setCrid(ad.getAdKey());
        }

        String landUrl = "", title = "", desc = "";
        if (StringUtils.isNoneBlank(ad.getClickUrl())) {
            landUrl = ad.getClickUrl();
        }
        if (StringUtils.isNoneBlank(ad.getTitle())) {
            title = ad.getTitle();
        }
        if (StringUtils.isNoneBlank(ad.getDescription())) {
            desc = ad.getDescription();
        }

        builder.setLandUrl(landUrl).setTitle(ByteString.copyFromUtf8(title)).setDescription(desc);

        if (StringUtils.isNoneBlank(ad.getVideoUrl())) {
            builder.setCreativeType(CreativeType.kVideo).setPicUrl(ad.getVideoUrl()).setAdDuration(Integer.valueOf(ad.getVideoDuration()));
        } else {
            NativeAd.Builder nab = NativeAd.newBuilder();
            nab.setThreadTitle(title).setThreadContent(desc);
            // 素材处理
            String methodNameFormat = "setThreadPic%d";
            int imageIndex = 1;
            for (String image : ad.getImageSrcs()) {
                MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), image);
                imageIndex++;
            }
            if (nab.hasThreadPic1()) {
                builder.setPicUrl(nab.getThreadPic1());
            }
            builder.setNativeAd(nab.build());
        }

        // 交互类型处理
        if (StringUtils.isNoneBlank(ad.getInteractionType())) {
            Integer interactionType = Integer.valueOf(ad.getInteractionType());
            if (interactionType.equals(YunGaoUtil.InteractionType.DEEPLINK.getValue())) {
                // DP
                if (StringUtils.isNoneBlank(ad.getDeeplinkUrl())) {
                    builder.setLandUrl(ad.getDeeplinkUrl()).setFallback(landUrl);
                }

                if (ad.getReportDeeplinkClick() != null) {
                    List<String> clkReports = new ArrayList<>(Arrays.asList(ad.getReportDeeplinkClick()));
                    if (!CollectionUtils.isEmpty(clkReports)) {
                        builder.addAllClkMonitorUrl(clkReports);
                    }
                }

                if (ad.getReportDeeplinkSuccess() != null) {
                    List<String> dpsReports = new ArrayList<>(Arrays.asList(ad.getReportDeeplinkSuccess()));
                    if (!CollectionUtils.isEmpty(dpsReports)) {
                        builder.addAllDpsMonitorUrl(dpsReports);
                    }
                }

                if (ad.getReportDeeplinkFail() != null) {
                    List<String> dplReports = new ArrayList<>(Arrays.asList(ad.getReportDeeplinkFail()));
                    if (!CollectionUtils.isEmpty(dplReports)) {
                        builder.addAllDpfMonitorUrl(dplReports);
                    }
                }

            } else if (interactionType.equals(YunGaoUtil.InteractionType.DOWNLOAD.getValue())) {
                // 下载
                builder.setExtensionType(ExtensionType.kExtAndroid);
                if (StringUtils.isNoneBlank(ad.getAppName())) {
                    builder.setAppName(ad.getAppName());
                }
                if (StringUtils.isNoneBlank(ad.getAppPackage())) {
                    builder.setPkgName(ad.getAppPackage());
                }

                if (ad.getReportStartDown() != null) {
                    List<String> downReports = new ArrayList<>(Arrays.asList(ad.getReportStartDown()));
                    if (!CollectionUtils.isEmpty(downReports)) {
                        builder.addAllDlMonitorUrl(downReports);
                    }
                }
            }
        }

        if (ad.getReportImpress() != null) {
            List<String> impReports = new ArrayList<>(Arrays.asList(ad.getReportImpress()));
            if (!CollectionUtils.isEmpty(impReports)) {
                builder.addAllImpMonitorUrl(impReports);
            }
        }

        if (ad.getReportClick() != null) {
            List<String> clkReports = new ArrayList<>(Arrays.asList(ad.getReportClick()));
            if (!CollectionUtils.isEmpty(clkReports)) {
                builder.addAllClkMonitorUrl(clkReports);
            }
        }

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

        builder.withRequestId(biddingReq.getId()).withApiVersion(API_VER).withMediaType(YunGaoUtil.MediaType.APP.getValue()).withRequestTime(String.valueOf(System.currentTimeMillis()));

        String appId = posInfo.getAppId().toStringUtf8();
        App.AppMapping appMapping = RtbUtils.getMappingDspApp(DSP_ID, appId);
        if (appMapping == null) {
            throw new AdNgxException("云告App应用映射获取失败：" + appId);
        }
        builder.withAppId(appMapping.getDspAppid()).withAppVersion(posInfo.getAppVersion()).withAppPackage(posInfo.getBundle());

        // 代码位参数处理
        SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());
        if (positionMapping == null) {
            throw new AdNgxException("云告广告位映射获取失败：" + posInfo.getPosId().toStringUtf8());
        }
        builder.withAdslotId(positionMapping.getDspAdPositionId());

        // 设备参数处理
        builder.withDeviceType(deviceTypeHolder.get(userInfo.getDeviceType())).withoOsVersion(userInfo.getOsv()).withVendor(userInfo.getDeviceBrand().toStringUtf8())
                .withModel(userInfo.getDeviceModel().toStringUtf8()).withScreenHeight(userInfo.getScreenHeight()).withScreenWidth(userInfo.getScreenWidth()).withUa(URLEncoder.encode(userInfo.getUa()));

        // 设备唯一识别码
        OSType osType = userInfo.getOs();
        String muId = userInfo.getMuid().toStringUtf8();
        if (osType == OSType.kAndroid) {
            builder.withImei(muId).withAndroidId(userInfo.getAdid()).withImsi("");
        } else if (osType == OSType.kIOS) {
            builder.withIdfa(muId).withIdfv("").withOpenudId("");
        }

        // 移动网络参数处理
        builder.withIpv4(userInfo.getIp()).withConnectionType(netTypeHolder.get(userInfo.getConnectType())).withOperatorType(carrierHolder.get(userInfo.getCarrier()));

        // GPS参数
        builder.withLongitude((float) userInfo.getLon()).withLatitude((float) userInfo.getLat());

        BidRequest request = builder.build();
        QueryStringBuilder queryStringBuilder = QueryStringBuilder.create(request);

        String queryString = queryStringBuilder.toQueryString();
        BiddingTracer.trace(biddingReq.getIsDebug(), "云告：{}，本次：{}，广告请求：{}", DSP_ID, biddingReq.getId(), queryString);
        return queryString;
    }
}

package com.iwanvi.adserv.ngx.router.broker.oneway;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-02 15:08
 */
public class OneWayDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(OneWayDspBroker.class);

    private static final String DSP_ID =MinervaCfg.get().getConfigProperty("oneway.dspid");
    private static final String API_VER =MinervaCfg.get().getConfigProperty("oneway.apiver");
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

    private static final Map<ConnectType, String> connectHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier, String> carrierHolder = new ConcurrentHashMap<>();

    static {
        connectHolder.put(ConnectType.kConnectTypeUnKnown, OneWayUtils.ConnnectType.NONE.getValue());
        connectHolder.put(ConnectType.kWifi, OneWayUtils.ConnnectType.WIFI.getValue());
        connectHolder.put(ConnectType.k2g, OneWayUtils.ConnnectType.CALLULAR.getValue());
        connectHolder.put(ConnectType.k3g, OneWayUtils.ConnnectType.CALLULAR.getValue());
        connectHolder.put(ConnectType.k4g, OneWayUtils.ConnnectType.CALLULAR.getValue());
        connectHolder.put(ConnectType.k5g, OneWayUtils.ConnnectType.CALLULAR.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, OneWayUtils.Carrier.OTHER.getValue());
        carrierHolder.put(Carrier.kMobile, OneWayUtils.Carrier.CHAIN_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, OneWayUtils.Carrier.CHAIN_UNICOM.getValue());
        carrierHolder.put(Carrier.kTelecom, OneWayUtils.Carrier.CHAIN_TELECOM.getValue());
    }

    @Override
    public String getDspId() {
        return DSP_ID;
    }

    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {
        BidRequest.Builder builder = BidRequest.builder();

        PosInfo posInfo = biddingReq.getPosInfo(0);

        // 广告位处理
        SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());
        if (positionMapping == null) {
            throw new AdNgxException("OneWay获取广告位映射失败：" + posInfo.getPosId().toStringUtf8());
        }
        builder.withPlacementId(positionMapping.getDspAdPositionId());

        CreativeType creativeType = posInfo.getCreativeType(0);
        // 创意类型处理
        builder.withCreativeType(creativeType == CreativeType.kVideo ? OneWayUtils.CreativeType.VIDEO.getValue() : OneWayUtils.CreativeType.PIC.getValue());

        UserInfo userInfo = biddingReq.getUserInfo();
        OSType osType = userInfo.getOs();
        String muid = userInfo.getMuid().toStringUtf8();
        // 系统处理
        if (osType == OSType.kAndroid) {
            builder.withImei(muid).withAndroidId(userInfo.getAdid());
        } else if (osType == OSType.kIOS) {
            builder.withDeviceId(muid);
        }

        ConnectType connectType = userInfo.getConnectType();
        Carrier carrier = userInfo.getCarrier();
        builder.withApiVersion(API_VER).withBundleId(posInfo.getBundle()).withBundleVersion(posInfo.getAppVersion()).withOsVersion(userInfo.getOsv())
                .withConnectionType(connectHolder.get(connectType)).withNetworkType(getNetType(carrier,connectType)).withNetworkOperator(carrierHolder.get(userInfo.getCarrier())).withSimOperator(carrierHolder.get(carrier))
                .withDeviceMake(userInfo.getDeviceBrand().toStringUtf8()).withDeviceModel(userInfo.getDeviceModel().toStringUtf8()).withMac(userInfo.getMac())
                .withScreenWidth(userInfo.getScreenWidth()).withScreenHeight(userInfo.getScreenHeight()).withScreenDensity((int) userInfo.getDensity())
                .withUserAgentn(userInfo.getUa()).withIp(userInfo.getIp()).withLongitude((float) userInfo.getLon()).withLatitude((float) userInfo.getLat());

        BidRequest bidRequest = builder.build();

        BiddingTracer.trace(biddingReq.getIsDebug(), "OneWay:{}，广告请求内容：{}", getDspId(),JSON.toJSONString(bidRequest));
        return JSON.toJSONBytes(bidRequest);
    }

    /**
     * 网络类型处理
     * @param carrier 运营商
     * @param connectType 网络连接类型
     * @return
     *
     *  2g: NETWORK_TYPE_GPRS(联通) NETWORK_TYPE_EDGE(移动) NETWORK_TYPE_CDMA(电信)
     *  3g: NETWORK_TYPE_TD_SCDMA(移动), NETWORK_TYPE_HSPA(联通), NETWORK_TYPE_EVDO_A(电信)
     *  4g/wifi: NETWORK_TYPE_LTE
     */
    private Integer getNetType(Carrier carrier,ConnectType connectType){
        if (connectType == ConnectType.kConnectTypeUnKnown || carrier ==Carrier.kCarrierUnKnown){
            return OneWayUtils.NetType.NETWORK_TYPE_UNKNOWN.getValue();
        }
        switch (connectType.getNumber()){
            case ConnectType.k2g_VALUE:
                switch (carrier.getNumber()){
                    case Carrier.kMobile_VALUE:
                        return OneWayUtils.NetType.NETWORK_TYPE_EDGE.getValue();
                    case Carrier.kUnicom_VALUE:
                        return OneWayUtils.NetType.NETWORK_TYPE_GPRS.getValue();
                    case Carrier.kTelecom_VALUE:
                        return OneWayUtils.NetType.NETWORK_TYPE_CDMA.getValue();
                }
            case ConnectType.k3g_VALUE:
                switch (carrier.getNumber()){
                    case Carrier.kMobile_VALUE:
                        return OneWayUtils.NetType.NETWORK_TYPE_TD_SCDMA.getValue();
                    case Carrier.kUnicom_VALUE:
                        return OneWayUtils.NetType.NETWORK_TYPE_HSPA.getValue();
                    case Carrier.kTelecom_VALUE:
                        return OneWayUtils.NetType.NETWORK_TYPE_EVDO_A.getValue();
                }
            case ConnectType.k4g_VALUE:
                return OneWayUtils.NetType.NETWORK_TYPE_LTE.getValue();
            case ConnectType.kWifi_VALUE:
                return OneWayUtils.NetType.NETWORK_TYPE_LTE.getValue();
            default:
                return OneWayUtils.NetType.NETWORK_TYPE_UNKNOWN.getValue();
        }
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();

        String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "OneWay：{},广告响应内容：{}", getDspId(), respBody);
        BidResponse response = JSON.parseObject(respBody, BidResponse.class);
        if (response == null || response.isSuccess() == false || (response.getErrorCode() != null && !response.getErrorCode().equals(200))|| response.getData() == null) {
            BiddingTracer.trace(isDebug, "OneWay:{}无广告响应", getDspId());
            return null;
        }

        List<AdMsg> adMsgs = new ArrayList<>();
        try {
            AdMsg adMsg = buildAdMsg(response.getData());
            if (adMsg != null) {
                adMsgs.add(adMsg);
            }
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        return adMsgs;
    }

    private AdMsg buildAdMsg(BidResponse.Data data) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        AdMsg.Builder builder = AdMsg.newBuilder();

        builder.setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        String landurl = "";
        if (StringUtils.isNoneBlank(data.getLandingPageUrl())) {
            landurl = data.getLandingPageUrl();
        } else if (StringUtils.isNoneBlank(data.getClickUrl())) {
            landurl = data.getClickUrl();
        }
        builder.setLandUrl(landurl);

        // 交互类型处理
        Integer type = data.getDownloadType();
        if (type.equals(OneWayUtils.DownloadType.DEEP_LINK.getValue())) {
            // dp处理
            if (StringUtils.isNoneBlank(data.getDeeplink())) {
                builder.setLandUrl(data.getDeeplink()).setFallback(landurl);
            }
        } else if (type.equals(OneWayUtils.DownloadType.DOWNLOAD.getValue())) {
            // 直接下载处理
            builder.setExtensionType(ExtensionType.kExtAndroid);
            if (StringUtils.isNoneBlank(data.getAppName())) {
                builder.setAppName(data.getAppName());
            }
            if (StringUtils.isNoneBlank(data.getAppStoreId())) {
                builder.setPkgName(data.getAppStoreId());
            }

            // 下载开始检测
            if (data.getTrackingEvents().getApkDownloadStart()!=null && data.getTrackingEvents().getApkDownloadStart().length>0){
                builder.addAllDlMonitorUrl(dspMicro(data.getTrackingEvents().getApkDownloadStart()));
            }
        }

        // 广告类型处理
        if (data.getVideo() != null) {
            // 视频素材处理
            BidResponse.Video video = data.getVideo();
            builder.setPicUrl(video.getUrl()).setAdDuration(video.getDuration().intValue()).setCreativeType(CreativeType.kVideo);
        } else {
            // 图片素材处理
            NativeAd.Builder nab = NativeAd.newBuilder();
            if (StringUtils.isNoneBlank(data.getAppIcon())) {
                nab.setUserPortrait(data.getAppIcon());
            }
            if (StringUtils.isNoneBlank(data.getTitle())) {
                nab.setThreadTitle(data.getTitle());
            }
            String methodNameFormat = "setThreadPic%d";
            int imageIndex = 1;
            for (BidResponse.Image img : data.getImages()) {
                MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), img.getUrl());
                imageIndex++;
            }
            builder.setPicUrl(nab.getThreadPic1()).setNativeAd(nab.build());
        }

        //检测数据处理
        List<String> pv = new ArrayList<>();
        List<String> clk = new ArrayList<>();
        if (data.getTrackingEvents().getShow()!=null && data.getTrackingEvents().getShow().length>0){
            pv.addAll(dspMicro(data.getTrackingEvents().getShow()));
        }
        if (data.getTrackingEvents().getClick()!=null && data.getTrackingEvents().getClick().length>0){
            clk.addAll(dspMicro(data.getTrackingEvents().getClick()));
        }
        builder.addAllImpMonitorUrl(pv).addAllClkMonitorUrl(clk);

        return builder.build();
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }

    @Override
    public String getDspUrl(String url,BiddingReq biddingReq) {
        PosInfo posInfo = biddingReq.getPosInfo(0);
        String posId = posInfo.getAppId().toStringUtf8();
        App.AppMapping appMapping = RtbUtils.getMappingDspApp(getDspId(),posId);
        if (appMapping==null){
            throw new AdNgxException("OneWay获取应用映射失败：" + posId);
        }
        String[] appInfos = appMapping.getDspAppid().split("_");
        if (appInfos == null || appInfos.length != 2){
            throw new AdNgxException("OneWay 应用映射配置不正确!");
        }
        String  publishId = appInfos[0];  // 应用标识
        String token = appInfos[1]; // 应用密钥
        url = url+"?token="+token+"&publishId="+publishId+"&ts="+System.currentTimeMillis();
        BiddingTracer.trace(biddingReq.getIsDebug(),"OneWay Request URL:{}",url);
        return url;
    }

    private List<String> dspMicro(String[] list) {
        if (list==null){return null;}
        return Arrays.stream(list).map(x -> x.replace("{TIMESTAMP}", "{AUCTION_TIME}")).collect(Collectors.toList());
    }
}

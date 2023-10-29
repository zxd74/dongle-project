package com.iwanvi.adserv.ngx.router.broker.xiaomi;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.broker.xiaomi.video.*;
import com.iwanvi.adserv.ngx.router.broker.xiaomi.video.Creative;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.MD5Utils;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;
import com.iwanvi.adserv.ngx.util.Xml2JavaUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : 郑晓东
 * @version : v1.0
 * @date : 2019-07-17 09:55
 * @description : 米盟广告请求响应转换处理类
 */
public class XiaoMiDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(XiaoMiDspBroker.class);
    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("xiaomi.dspid");
    private static final String API_VER = MinervaCfg.get().getConfigProperty("xiaomi.apiver");
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";
    private static final Map<ConnectType, String> netHolder = new ConcurrentHashMap<>();

    static {
        netHolder.put(ConnectType.kConnectTypeUnKnown, XiaoMiEnumUtils.NetType.UNKNOWN.getValue());
        netHolder.put(ConnectType.kWifi, XiaoMiEnumUtils.NetType.WIFI.getValue());
        netHolder.put(ConnectType.k2g, XiaoMiEnumUtils.NetType.K2G.getValue());
        netHolder.put(ConnectType.k3g, XiaoMiEnumUtils.NetType.K3G.getValue());
        netHolder.put(ConnectType.k4g, XiaoMiEnumUtils.NetType.K4G.getValue());
        netHolder.put(ConnectType.k5g, XiaoMiEnumUtils.NetType.UNKNOWN.getValue());

    }

    @Override
    public String getDspId() {
        return DSP_ID;
    }

    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {
        BidRequest.Builder builder = BidRequest.builder();
        PosInfo posInfo = biddingReq.getPosInfo(0);
        UserInfo userInfo = biddingReq.getUserInfo();
        builder.withVersion(API_VER);

        // 广告位处理
        SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, DSP_ID);
        if (positionMapping == null || !positionMapping.hasDspAdPositionId()) {
            throw new AdNgxException("小米广告位映射ID不存在：" + posInfo.getPosId().toStringUtf8());
        }
        builder.withUpId(positionMapping.getDspAdPositionId());

        // 设备信息
        BidRequest.DeviceInfo.Builder device = BidRequest.DeviceInfo.builder();
        device.withMake(userInfo.getDeviceBrand().toStringUtf8()).withModel(userInfo.getDeviceModel().toStringUtf8());

        // 用户信息
        BidRequest.UserInfo.Builder user = BidRequest.UserInfo.builder();
        user.withMac(userInfo.getMac()).withIp(userInfo.getIp()).withConnectionType(netHolder.get(userInfo.getConnectType()));

        // 系统类型处理
        OSType osType = userInfo.getOs();
        if (osType == OSType.kAndroid) {
            // android 处理
            device.withOs(XiaoMiEnumUtils.OsType.ANDROID.getValue()).withAndroidVersion(userInfo.getOsv());
            user.withAndroidId(userInfo.getAdid()).withImei(MD5Utils.md5Hex(userInfo.getMuid().toStringUtf8()));
        } else if (osType == OSType.kIOS) {
            // ios 处理
            device.withOs(XiaoMiEnumUtils.OsType.IOS.getValue()).withIosVersion(userInfo.getOsv());
            user.withIdfa(MD5Utils.md5Hex(userInfo.getMuid().toStringUtf8()));
        }

        // App信息
        BidRequest.AppInfo.Builder app = BidRequest.AppInfo.builder();
        app.withPackageName(posInfo.getBundle()).withVersion(posInfo.getAppVersion());

        // 广告请求信息
        BidRequest.ImpRequests.Builder imp = BidRequest.ImpRequests.builder();
        imp.withAdsCount(1).withWidth(positionMapping.getWidth()).withHeight(positionMapping.getHeight());

        //参数信息处理
        BidRequest.ClientInfo.Builder client = BidRequest.ClientInfo.builder();
        client.withDeviceInfo(device.build()).withUserInfo(user.build()).withAppInfo(app.build()).withImpRequests(imp.build());
        String clientJson = JSON.toJSONString(client.build());
        builder.withClientInfo(clientJson);
        // 构建请求body
        BidRequest bidRequest = builder.build();
        QueryStringBuilder<BidRequest> queryStringBuilder = QueryStringBuilder.create(bidRequest);
        String queryString = queryStringBuilder.toQueryString();

        BiddingTracer.trace(biddingReq.getIsDebug(), "小米米盟广告请求：{}", queryString);
        return queryString.getBytes();
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();
        List<AdMsg> adMsgs = new ArrayList<>();

        if (responseBody == null) {
            return null;
        }
        String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "小米米盟:{}，广告响应内容：{}", DSP_ID, respBody);
        BidResponse bidResponse = JSON.parseObject(respBody, BidResponse.class);

        if (bidResponse == null) {
            // 如果普通广告响应为空，按视频广告处理
            // 视频广告处理
            adMsgs = buildVideo(isDebug, respBody);
            return adMsgs;
        } else if (bidResponse.getCode() != 0 || bidResponse.getAdInfos() == null) {
            BiddingTracer.trace(isDebug, "小米米盟无广告响应 {}", bidResponse.getCode());
            return null;
        }

        // 广告处理

        for (BidResponse.AdInfo adInfo : bidResponse.getAdInfos()) {
            try {
                AdMsg adMsg = buildNative(biddingReq.getId(), adInfo);
                if (adMsg != null) {
                    adMsgs.add(adMsg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return adMsgs;
    }

    /**
     * 视频广告处理
     *
     * @param isDebug
     * @param respBody
     * @return
     */
    private List<AdMsg> buildVideo(boolean isDebug, String respBody) {
        List<AdMsg> adMsgs = new ArrayList<>();

        try {
            VAST video = Xml2JavaUtils.converyToBean(respBody, VAST.class);

            if (video.getAd() == null || video.getError() != null) {
                BiddingTracer.trace(isDebug, "小米米盟无广告响应 {}", video.getError() != null ? video.getError() : "");
                return null;
            }

            Ad ad = video.getAd();
            if (ad.getInLine() == null || ad.getInLine().getCreatives() == null) {
                return null;
            }

            List<Creative> creatives = video.getAd().getInLine().getCreatives().getCreative();
            for (Creative creative : creatives) {
                AdMsg.Builder builder = AdMsg.newBuilder();

                // 只取其一素材内容
                MediaFile mediaFile = creative.getLinear().getMediaFiles().getMediaFile().get(0);
                builder.setDspId(DSP_ID).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm).setCrid(mediaFile.getAssetId())
                        .setTitle(ByteString.copyFromUtf8(ad.getInLine().getAdTitle()))
                        .setDescription(ad.getInLine().getDescription());
                builder.setLandUrl(creative.getLinear().getVideoClicks().getClickThrough()).setPicUrl(mediaFile.getValue());

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date start = sdf.parse("00:00:00");
                Date date = sdf.parse(creative.getLinear().getDuration());
                builder.setAdDuration((int) (date.getTime() - start.getTime()) / 1000);

                // 只取其一下载内容
                if (ad.getInLine().getExtensions().getExtension() != null) {
                    Extension extension = ad.getInLine().getExtensions().getExtension().get(0);
                    if (extension.getTargetType().equals(XiaoMiEnumUtils.TargetType.DOWNLOAD.getValue())) {
                        builder.setExtensionType(ExtensionType.kExtAndroid).setPkgName(extension.getPackageName()).setLandUrl(extension.getDownloadUrl());
                    }
                }

                builder.addAllImpMonitorUrl(ad.getInLine().getImpression()).addAllClkMonitorUrl(creative.getLinear().getVideoClicks().getClickTracking());

                AdMsg adMsg = builder.build();
                if (adMsg != null) {
                    adMsgs.add(adMsg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            BiddingTracer.trace(isDebug, "小米米盟视频广告处理异常：{}", respBody);
        }
        return adMsgs;
    }

    /**
     * 原生广告处理
     *
     * @param reqId
     * @param adInfo
     * @return
     * @throws Exception
     */
    private AdMsg buildNative(String reqId, BidResponse.AdInfo adInfo) throws Exception {
        AdMsg.Builder builder = AdMsg.newBuilder();
        builder.setDspId(DSP_ID).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm).setCrid(adInfo.getId().toString())
                .setTitle(adInfo.getTitle() == null ? ByteString.copyFromUtf8("") : ByteString.copyFromUtf8(adInfo.getTitle()))
                .setDescription(adInfo.getSummary() == null ? "" : adInfo.getSummary()).setLandUrl(adInfo.getLandingPageUrl());

        if (StringUtils.isNoneBlank(adInfo.getDeeplink())) {
            // deeplink 处理
            builder.setLandUrl(adInfo.getDeeplink());
            // dp失败处理
            if (adInfo.getTargetType().equals(XiaoMiEnumUtils.TargetType.LINK.getValue())) {
                // 外链方式
                builder.setFallback(adInfo.getLandingPageUrl());
            } else if (adInfo.getTargetType().equals(XiaoMiEnumUtils.TargetType.DOWNLOAD.getValue())) {
                // 下载方式
                builder.setFallback(adInfo.getActionUrl());
            }
        } else if (adInfo.getTargetType().equals(XiaoMiEnumUtils.TargetType.DOWNLOAD.getValue())) {
            // download 处理
            builder.setExtensionType(ExtensionType.kExtAndroid).setPkgName(adInfo.getPackageName());
        }

        // 广告素材处理
        if (adInfo.getAssets() == null || adInfo.getAssets().length == 0) {
            LOG.error("小米米盟广告素材列表为空，请求ID：{}", reqId);
            return null;
        }
        // 目前只接收处理一个视频广告素材
        // 原生图片处理
        NativeAd.Builder nab = NativeAd.newBuilder();
        nab.setThreadTitle(builder.getTitle().toStringUtf8()).setThreadContent(builder.getDescription());
        if (StringUtils.isNoneBlank(adInfo.getIconUrl())) {
            nab.setUserPortrait(adInfo.getIconUrl());
        }
        String methodNameFormat = "setThreadPic%d";
        int imageIndex = 1;
        for (BidResponse.Asset asset : adInfo.getAssets()) {
            MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), asset.getUrl());
            imageIndex++;
        }
        builder.setNativeAd(nab.build()).setPicUrl(nab.getThreadPic1());
        // 监测地址处理
        builder.addAllImpMonitorUrl(Arrays.asList(adInfo.getViewMonitorUrls())).addAllClkMonitorUrl(Arrays.asList(adInfo.getClickMonitorUrls()));
        if (adInfo.getStartDownloadMonitorUrls() != null && adInfo.getStartDownloadMonitorUrls().length > 0) {
            // 开始下载检测
            builder.addAllDlMonitorUrl(Arrays.asList(adInfo.getStartDownloadMonitorUrls()));
        }
        return builder.build();
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }
}

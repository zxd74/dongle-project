package com.iwanvi.adserv.ngx.router.broker.zhihemobi;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.MD5Utils;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: 郑晓东
 * @date: 2019-06-11 15:12
 * @version: v1.0
 * @Description:
 */
public class ZhiHeMoBiDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(ZhiHeMoBiDspBroker.class);
    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("zhihemobi.dspid");
    private static final String API_VER = MinervaCfg.get().getConfigProperty("zhihemobi.apiver");
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    private static final Map<ConnectType, Integer> connectHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier, Integer> carrierHolder = new ConcurrentHashMap<>();
    private static final Map<CreativeType, Integer> adTypeHolder = new ConcurrentHashMap<>();

    static {
        connectHolder.put(ConnectType.kConnectTypeUnKnown, BidRequest.ConnectionType.UNKNOWN.getValue());
        connectHolder.put(ConnectType.k2g, BidRequest.ConnectionType.K2G.getValue());
        connectHolder.put(ConnectType.k3g, BidRequest.ConnectionType.K3G.getValue());
        connectHolder.put(ConnectType.k4g, BidRequest.ConnectionType.K4G.getValue());
        connectHolder.put(ConnectType.k5g, BidRequest.ConnectionType.K5G.getValue());
        connectHolder.put(ConnectType.kWifi, BidRequest.ConnectionType.WIFI.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, BidRequest.OperatorType.UNKNOWN.getValue());
        carrierHolder.put(Carrier.kMobile, BidRequest.OperatorType.CHINA_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, BidRequest.OperatorType.CHINA_UNICOM.getValue());
        carrierHolder.put(Carrier.kTelecom, BidRequest.OperatorType.CHINA_TELECOM.getValue());

        adTypeHolder.put(CreativeType.kVideo, BidRequest.AdType.NATIVE_VIDEO.getValue());
        adTypeHolder.put(CreativeType.kPic, BidRequest.AdType.BANNER.getValue());
        adTypeHolder.put(CreativeType.kNative, BidRequest.AdType.NATIVE.getValue());
        adTypeHolder.put(CreativeType.kText, BidRequest.AdType.BANNER.getValue());
        adTypeHolder.put(CreativeType.kFirstScreen, BidRequest.AdType.FULL_SCREEN.getValue());
        adTypeHolder.put(CreativeType.kScreen, BidRequest.AdType.SCREEN.getValue());
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

        Long time = System.currentTimeMillis();
        builder.withRequestId(biddingReq.getId()).withTime(time).withApiVersion(API_VER).withToken(MD5Utils.md5Hex(biddingReq.getId() + time));

        // app映射
        App.AppMapping appMapping = RtbUtils.getMappingDspApp(getDspId(), posInfo.getAppId().toStringUtf8());
        if (appMapping == null) {
            throw new AdNgxException("缺少智合移动媒体App映射配置");
        }
        builder.withAppId(appMapping.getDspAppid()).withAppName(posInfo.getAppName()).withAppVersion(posInfo.getAppVersion()).withAppPackage(posInfo.getBundle());

        // 广告位映射
        SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());
        if (positionMapping == null) {
            throw new AdNgxException("缺少智合移动媒体广告位映射配置");
        }
        builder.withAdslotId(Integer.valueOf(positionMapping.getDspAdPositionId())).withAdslotWidth(positionMapping.getWidth()).withAdslotHeight(positionMapping.getHeight());

        // 如果存在广告位映射，则以映射为主
        if (positionMapping.hasDspAdType()) {
            builder.withAdType(adTypeHolder.get(positionMapping.getDspAdType()));
        } else {
            // 没有广告位映射，则以广告位类型处理

            // 如果存在广告位是开屏广告，则需要以开屏处理
            SspAdPosition adslot = RepositoryFactory.getRepository().loadSspAdPosition(posInfo.getPosId().toStringUtf8());
            CreativeType adType = adslot.getAdType();
            if (adType == CreativeType.kFirstScreen) {
                builder.withAdType(adTypeHolder.get(adType));
            } else {
                // 非开屏广告则按广告类型处理
                CreativeType creativeType = posInfo.getCreativeType(0);
                builder.withAdType(adTypeHolder.get(creativeType));
            }
        }

        builder.withAdmimes(BidRequest.AdMimeType.IMAGE.getValue() + "," + BidRequest.AdMimeType.IMAGE_TEXT.getValue());

        // 设备信息
        builder.withModel(userInfo.getDeviceModel().toStringUtf8()).withVendor(userInfo.getDeviceBrand().toStringUtf8());

        OSType osType = userInfo.getOs();
        if (osType == OSType.kAndroid) {
            builder.withOsType(BidRequest.OsType.ANDROID.getValue()).withImei(userInfo.getMuid().toStringUtf8()).withImsi("").withAndroidid(userInfo.getAdid());
        } else if (osType == OSType.kIOS) {
            builder.withOsType(BidRequest.OsType.IOS.getValue()).withIdfa(userInfo.getMuid().toStringUtf8()).withIdfv("").withOpenUdid("");
        }

        builder.withOsVersion(userInfo.getOsv()).withDeviceType(BidRequest.DeviceType.PHONE.getValue()).withCori(BidRequest.CoriType.LANDSCAPE.getValue())
                .withScreenWidth(userInfo.getScreenWidth()).withScreenHeight(userInfo.getScreenHeight())
                .withUa(userInfo.getUa()).withMac(userInfo.getMac());

        // 移动网络处理
        builder.withIp(userInfo.getIp()).withOperatorType(carrierHolder.get(userInfo.getCarrier())).withConnectionType(connectHolder.get(userInfo.getConnectType()));

        // 其它选填参数
        builder.withDensity(String.valueOf(userInfo.getDensity()));

        BiddingTracer.trace(biddingReq.getIsDebug(), JSON.toJSONString(builder.build()));
        return JSON.toJSONBytes(builder.build());
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();

        String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "智合移动广告响应：{}", respBody);

        BidResponse bidResponse = JSON.parseObject(respBody, BidResponse.class);

        if (bidResponse == null || !bidResponse.getErrorCode().equals(0)) {
            BiddingTracer.trace(isDebug, "智合移动无广告响应");
            return null;
        }

        List<BidResponse.Ad> ads = JSON.parseArray(bidResponse.getAds(), BidResponse.Ad.class);
        if (ads == null || ads.isEmpty()) {
            BiddingTracer.trace(isDebug, "智合移动返回广告列表为空");
            return null;
        }

        List<AdMsg> adList = new ArrayList<>();
        try {
            for (BidResponse.Ad ad : ads) {
                AdMsg adMsg = buildAdMsg(ad, biddingReq);
                if (adMsg != null) {
                    adList.add(adMsg);
                }
            }
        } catch (Exception ex) {
            LOG.error("", ex);
        }
        return adList;
    }

    private AdMsg buildAdMsg(BidResponse.Ad ad, BiddingReq biddingReq) throws Exception {
        AdMsg.Builder builder = AdMsg.newBuilder();
        CreativeType creativeType = biddingReq.getPosInfo(0).getCreativeType(0);
        builder.setDspType(DspType.kDspTypePublic).setDspId(getDspId()).setCrid(ad.getAdKey()).setCreativeType(creativeType).setCostType(CostType.kCpm);

        BidResponse.MetaGroup[] metaGroups = ad.getMetaGroups();
        List<String> imps = new ArrayList<>();
        List<String> clks = new ArrayList<>();
        for (BidResponse.MetaGroup metaGroup : metaGroups) {
            builder.setLandUrl(builderMecros(metaGroup.getClickUrl()));

            if (metaGroup.getCreativeType().equals(BidResponse.CreativeType.VIDEO.getValue())) {
                builder.setCreativeType(CreativeType.kVideo).setPicUrl(metaGroup.getVideoUrl()).setAdDuration(metaGroup.getVideoDuration());
            } else if (metaGroup.getCreativeType().equals(BidResponse.CreativeType.HTML.getValue())) {
                // HTML动态广告(暂不支持)
                return null;
            } else {
                NativeAd.Builder nab = NativeAd.newBuilder();
                if (StringUtils.isNoneBlank(metaGroup.getAdTitle())) {
                    nab.setThreadTitle(metaGroup.getAdTitle());
                }
                if (metaGroup.getDescriptions() != null && metaGroup.getDescriptions().length > 0) {
                    nab.setThreadContent(metaGroup.getDescriptions()[0]);
                }
                if (StringUtils.isNoneBlank(metaGroup.getAdTitle())) {
                    nab.setThreadTitle(metaGroup.getAdTitle());
                }

                String methodNameFormat = "setThreadPic%d";
                int imageIndex = 1;
                for (String img : metaGroup.getImageSrcs()) {
                    MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), img);
                    imageIndex++;
                }
                builder.setNativeAd(nab.build()).setPicUrl(nab.getThreadPic1());
            }

            if (metaGroup.getInteractionType().equals(BidResponse.InteractionType.DEEP_LINK.getValue())) {
                // DeepLink处理
                if (StringUtils.isNoneBlank(metaGroup.getDeeplink())) {
                    builder.setLandUrl(metaGroup.getDeeplink()).setFallback(builderMecros(metaGroup.getClickUrl()));
                }
            } else if (metaGroup.getInteractionType().equals(BidResponse.InteractionType.DOWNLOAD.getValue())) {
                // 下载处理
                builder.setExtensionType(ExtensionType.kExtAndroid);
                if (StringUtils.isNoneBlank(metaGroup.getAppPackage())) {
                    builder.setPkgName(metaGroup.getAppPackage());
                }
                if (StringUtils.isNoneBlank(metaGroup.getBrandName())) {
                    builder.setAppName(metaGroup.getBrandName());
                }
            }

            imps.addAll(new ArrayList<>(Arrays.asList(metaGroup.getWinNoticeUrls())));
            clks.addAll(builderMecros(metaGroup.getClickTrack()));
        }

        // DeepLink 点击事件监测
        Arrays.stream(ad.getAdTrackings()).filter(x -> x.getTrackingEventType().equals(BidResponse.TrackingEventType.DEEP_LINK_CLICK.getValue())).forEach(x -> clks.addAll(builderMecros(x.getTrackingUrls())));

        builder.addAllImpMonitorUrl(imps).addAllClkMonitorUrl(clks);

        // 开始下载检测
        List<String> dnStarts =new ArrayList<>();
        Arrays.stream(ad.getAdTrackings()).filter(x -> x.getTrackingEventType().equals(BidResponse.TrackingEventType.DOWNLOAD_START.getValue())).forEach(x -> dnStarts.addAll(builderMecros(x.getTrackingUrls())));
        if (!CollectionUtils.isEmpty(dnStarts)){ builder.addAllDlMonitorUrl(dnStarts);}

        return builder.build();
    }

    private List<String> builderMecros(String[] urls) {
        return Arrays.stream(urls).map(x -> x = builderMecros(x)).collect(Collectors.toList());
    }

    private String builderMecros(String url) {
        String str = "-999";
        return url.replace("[down_x]", str).replace("[down_y]", str)
                .replace("[up_x]", str).replace("[up_y]", str)
                .replace("[re_down_x]", str).replace("[re_down_y]", str)
                .replace("[re_up_x]", str).replace("[re_up_y]", str)
                .replace("[latitude]", str).replace("[longitude]", str);
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }
}

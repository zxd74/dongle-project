package com.iwanvi.adserv.ngx.router.broker.threesix;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.DspLoggers;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CodeUtils;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.BiddingProto;
import com.iwanvi.nvwa.proto.BiddingProto.*;

import com.iwanvi.adserv.ngx.router.broker.threesix.BidRequest.App;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidRequest.Device;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidRequest.CarrierId;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidRequest.NetType;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidRequest.DeviceId;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidRequest.Adspaces;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidRequest.AdspaceType;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidRequest.NativeStyle;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.Ad;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.AdmType;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.Creative;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.InteractionType;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.Interaction;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.Native;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.Img;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.Video;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.EventTrack;
import com.iwanvi.adserv.ngx.router.broker.threesix.BidResponse.EventType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.DspType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.OSType;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

/**
 * @BelongsProject: nvwa-adserv-ngx
 * @BelongsPackage: com.iwanvi.adserv.ngx.router.broker.threesix
 * @author: 郑晓东
 * @date: 2019-04-22 09:04
 * @version: v1.0
 * @Description:
 */
public class ThreeSixDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(ThreeSixDspBroker.class);

    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

    /**
     * 获取DSP的ID
     *
     * @return
     */
    @Override
    public String getDspId() {
        return MinervaCfg.get().getConfigProperty("360.dspid");
    }

    /**
     * 运营商类型 映射
     */
    private static final Map<Carrier, Integer> carriersHolder = new ConcurrentHashMap<>();
    /**
     * 网络类型 映射
     */
    private static final Map<ConnectType, Integer> connectTypesHolder = new ConcurrentHashMap<>();
    /**
     * DeviceId类型  映射
      */
    private static final Map<CreativeType, Integer> adspaceTypesHolder = new ConcurrentHashMap<>();
    /**
     * Create-Native类型映射
     */
    private static final Map<CreativeType, List<Integer>> createNativeHolder = new ConcurrentHashMap<>();

    static {
        // 运营商类型
        carriersHolder.put(Carrier.kCarrierUnKnown, CarrierId.UNKNOWN.getValue());
        carriersHolder.put(Carrier.kMobile, CarrierId.CHINA_MOBILE.getValue());
        carriersHolder.put(Carrier.kTelecom, CarrierId.CHINA_TELECOM.getValue());
        carriersHolder.put(Carrier.kUnicom, CarrierId.UNICOM.getValue());

        // 网络类型
        connectTypesHolder.put(ConnectType.k2g, NetType.NET_2G.getValue());
        connectTypesHolder.put(ConnectType.k3g, NetType.NET_3G.getValue());
        connectTypesHolder.put(ConnectType.k4g, NetType.NET_4G.getValue());
        connectTypesHolder.put(ConnectType.kWifi, NetType.NET_WIFI.getValue());
        connectTypesHolder.put(ConnectType.k5g, NetType.NET_UNKNOWN.getValue());
        // 注意：360提示，未知只能用在无法识别5G情况下
        connectTypesHolder.put(ConnectType.kConnectTypeUnKnown, NetType.NET_UNKNOWN.getValue());

        // 广告位类型
        adspaceTypesHolder.put(CreativeType.kPic, AdspaceType.BANNER.getValue());
        adspaceTypesHolder.put(CreativeType.kFocusPic, AdspaceType.FOCUS.getValue());
        adspaceTypesHolder.put(CreativeType.kVideo, AdspaceType.VIDEO.getValue());
        adspaceTypesHolder.put(CreativeType.kNative, AdspaceType.NATIVE.getValue());
        adspaceTypesHolder.put(CreativeType.kFirstScreen, AdspaceType.OPENING.getValue());
        adspaceTypesHolder.put(CreativeType.kScreen, AdspaceType.INTERSTITIAL.getValue());
        adspaceTypesHolder.put(CreativeType.kText, AdspaceType.BANNER.getValue());
        adspaceTypesHolder.put(CreativeType.kDrawVideo, AdspaceType.VIDEO.getValue());
        adspaceTypesHolder.put(CreativeType.kRewardVideo, AdspaceType.VIDEO.getValue());

        // 原生素材样式：创意类型
        createNativeHolder.put(CreativeType.kPic, Arrays.asList(NativeStyle.SINGLE_IMAGE.getValue(),NativeStyle.MULTI_IMAGES.getValue()));
        createNativeHolder.put(CreativeType.kVideo, Arrays.asList(NativeStyle.VIDEO.getValue()));
        createNativeHolder.put(CreativeType.kNative, Arrays.asList(NativeStyle.SINGLE_IMAGE.getValue(),NativeStyle.MULTI_IMAGES.getValue()));
        // ,NativeStyle.VIDEO.getValue()
    }

    /**
     * 转换DSP请求
     * adx.BidRequest -> dsp.BidRequest
     *
     * @param biddingReq
     * @return
     */
    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {
        BidRequest.Builder brb = BidRequest.builder();

        BiddingProto.UserInfo userInfo = biddingReq.getUserInfo();
        ConnectType connectType = userInfo.getConnectType();
        App app = buildApp(biddingReq);

        brb.withBId(MD5(userInfo.getMuid() + app.getPackageName() + (new Date()).getTime())).withIp(userInfo.getIp()).withUserAgent(userInfo.getUa()).withUid(MD5(userInfo.getMuid() + app.getPackageName() + ""))
                .withApp(app).withDevice(buildDevice(biddingReq)).withNetType(connectTypesHolder.get(connectType)).withAdspaces(buildAdspace(biddingReq));

        BiddingTracer.trace(biddingReq.getIsDebug(),"打印360的请求数据内容:{}", JSON.toJSONString(brb.build()));

        return JSON.toJSONBytes(brb.build());
    }

    /**
     * App 构建
     *
     * @param biddingReq
     * @return
     */
    private App buildApp(BiddingReq biddingReq) {
        List<PosInfo> posInfos = biddingReq.getPosInfoList();
        App.Builder builder = App.builder();
        for (PosInfo posInfo : posInfos) {
            builder.withAppName(posInfo.getAppName());
            builder.withAppVersion(posInfo.getAppVersion());
            builder.withPackageName(posInfo.getBundle());
            break;
        }
        return builder.build();
    }

    /**
     * 构建Device设备信息
     *
     * @param biddingReq
     * @return
     */
    private Device buildDevice(BiddingReq biddingReq) {
        BiddingProto.UserInfo userInfo = biddingReq.getUserInfo();
        Carrier carrier = userInfo.getCarrier();
        OSType os = userInfo.getOs();

        Device.Builder deviceBuilder = Device.builder();

        // 设备ID处理
        List<DeviceId> list = new ArrayList<>();
        DeviceId.Builder builder = DeviceId.builder();
        if (os == OSType.kAndroid) {
            deviceBuilder.withOsType(BidRequest.OSTpye.OS_ANDROID.getValue()).withModel(userInfo.getDeviceModel().toStringUtf8());
            // 设备id值：IMEI ，小写的原始值或小写的MD5值
            builder.withDeviceId(userInfo.getMuid().toStringUtf8().toLowerCase()).withDeviceIdType(DeviceId.DeviceIdType.IMEI.getValue()).withHashType(DeviceId.HashType.NONE.getValue());
            list.add(builder.build());
        } else if (os == OSType.kIOS) {
            deviceBuilder.withOsType(BidRequest.OSTpye.OS_IOS.getValue()).withModel("Apple");
            builder.withDeviceId(userInfo.getMuid().toStringUtf8().toLowerCase()).withDeviceIdType(DeviceId.DeviceIdType.IDFA.getValue()).withHashType(DeviceId.HashType.NONE.getValue());
            list.add(builder.build());
        }
        deviceBuilder.withDeviceId(list);

        // 设备其它信息处理
        deviceBuilder.withOsVersion(String.valueOf(userInfo.getOsv())).withBrand(userInfo.getDeviceBrand().toStringUtf8()).withCarrierId(carriersHolder.get(carrier));

        return deviceBuilder.build();
    }

    /**
     * Adspace 构建
     *
     * @return
     */
    private List<Adspaces> buildAdspace(BiddingReq biddingReq) {
        List<Adspaces> list = new ArrayList<>();
        List<PosInfo> posInfos = biddingReq.getPosInfoList();

        for (PosInfo posInfo : posInfos) {
            Adspaces.Builder builder = Adspaces.builder();

            // 广告位映射处理
            AdModelsProto.SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq,getDspId());
            if (positionMapping == null) {
                DspLoggers.code(biddingReq,getDspId(), CodeUtils.APP_MAP_ERROR);
                throw new AdNgxException("没有匹配的dsp平台广告位映射, adslotId: " + posInfo.getPosId().toStringUtf8());
            }
            builder.withAdspaceId(positionMapping.getDspAdPositionId()).withAllowedHtml(false);

            if (positionMapping.hasDspAdType() && adspaceTypesHolder.containsKey(positionMapping.getDspAdType())){
                // 有广告类型映射时已映射类型为准
                builder.withAdspaceType(adspaceTypesHolder.get(positionMapping.getDspAdType()));
            }else{
                // 无广告类型映射时采用广告位类型映射为准
                builder.withAdspaceType(adspaceTypesHolder.get(posInfo.getAdType()));
            }

            // 广告位位置
            AdModelsProto.SspAdPosition adPosition = RepositoryFactory.getRepository().loadSspAdPosition(posInfo.getPosId().toStringUtf8());
            if (adPosition.getAdType() == CreativeType.kFirstScreen) {
                builder.withAdspacePosition(BidRequest.AdspacePosition.FIRST_POS.getValue());
            } else {
                builder.withAdspacePosition(BidRequest.AdspacePosition.OTHERS.getValue());
            }

            // 广告宽和高
            builder.withWidth(posInfo.getWidth()).withHeight(posInfo.getHeight());

            // 创意类型转换(如果不做处理则360随机分配)
            if (posInfo.getCreativeTypeList() != null) {
                List<Integer> nativeStyles = new ArrayList<>(posInfo.getCreativeTypeList().size());
                posInfo.getCreativeTypeList().forEach((type) -> nativeStyles.addAll(createNativeHolder.get(type)));
                builder.withNativeStyle(nativeStyles);
            }
            list.add(builder.build());
        }
        return list;
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    /**
     * 响应转换 处理
     * dsp.BidResponse -> adx.BidResponse
     *
     * @param biddingReq
     * @param responseBody
     * @return
     */
    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();

        List<AdMsg> adList = new ArrayList<>();
        try {
            String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
            BiddingTracer.trace(isDebug, "360 dsp平台广告响应：{}", respBody);
            BidResponse bidResponse = JSON.parseObject(respBody, BidResponse.class);

            List<Ad> ads = bidResponse.getAds();
            if (ads == null || ads.size() == 0){
                DspLoggers.code(biddingReq,getDspId(), CodeUtils.RESPONSE_NOT_AD);
                BiddingTracer.trace(isDebug, "360dsp返回广告为空");
            }
            for (Ad ad : ads) {
                AdMsg adMsg = toAdMsg(biddingReq, ad);
                if (adMsg!=null){
                    adList.add(adMsg);
                }else{
                    DspLoggers.code(biddingReq,getDspId(), CodeUtils.RESPONSE_HANDLER_NOT_AD);
                }

            }
        } catch (Throwable ex) {
            LOG.error("", ex);
            DspLoggers.code(biddingReq,getDspId(), CodeUtils.AD_HANDLER_ERROR);
        }
        return adList;
    }

    /**
     * 数据转换处理
     *
     * @param biddingReq
     * @param ad
     * @return
     * @throws Exception
     */
    private AdMsg toAdMsg(BiddingReq biddingReq, Ad ad) throws Exception {

        if (CollectionUtils.isEmpty(ad.getCreative())) {
            return null;
        }

        Creative creative = ad.getCreative().get(0);
        PosInfo posInfo = biddingReq.getPosInfo(0);
        CreativeType creativeType = posInfo.getCreativeType(0);
        // 固定价格，无需做价格处理
        AdMsg.Builder adb = AdMsg.newBuilder().setCrid(String.valueOf(creative.getBannerId()))
                .setDspId(getDspId()).setCostType(CommonProto.CostType.kCpm)
                .setCreativeType(creativeType)
                .setDspType(DspType.kDspTypePublic);

        // 广告类型、广告素材处理
        String admType = creative.getAdmType();
        if (admType.equals(AdmType.NATIVE.getValue())) {
            buildNativeAd(adb, creative, creativeType);
            // 交互动作
            buildInteraction(adb, creative);
        }

        // EventTrack对象列表，用于各种事件的追踪
        buildEventTrack(adb, creative);

        return adb.build();
    }

    /**
     * 处理EventTrack对象列表，用于各种事件的追踪
     *
     * @param adb
     * @param creative
     */
    private void buildEventTrack(AdMsg.Builder adb, Creative creative) {
        List<EventTrack> eventTracks = creative.getEventTrack();
        for (EventTrack eventTrack : eventTracks) {
            String eventType = eventTrack.getEventType();
            if (eventType.equals(EventType.SHOW.getValue())) {
                adb.addAllImpMonitorUrl(eventTrack.getNotifyUrl());
            } else if (eventType.equals(EventType.CLICK.getValue())) {
                adb.addAllClkMonitorUrl(eventTrack.getNotifyUrl());
            }else if (eventType.equals(EventType.BEGIN_DOWNLOAD.getValue())){
                adb.addAllClkMonitorUrl(eventTrack.getNotifyUrl());
            }
        }
    }

    /**
     * 原生类素材处理
     *
     * @return
     */
    private void buildNativeAd(AdMsg.Builder adb, Creative creative, CreativeType creativeType) throws Exception {
        if (createNativeHolder.get(creativeType).contains(NativeStyle.VIDEO.getValue()) && creative.getAdm().getNat().getVideo()!=null) {
            // 原生(视频)广告处理
            Video video = creative.getAdm().getNat().getVideo();
            adb.setCreativeType(CreativeType.kVideo).setAdDuration(video.getDuration()).setPicUrl(video.getUrl());
        } else {
            // 原生(图片)广告处理
            NativeAd.Builder nab = NativeAd.newBuilder();
            Native nat = creative.getAdm().getNat();
            nab.setThreadTitle(nat.getTitle().getText()).setThreadContent(nat.getDesc()).setUserPortrait(nat.getLogo());

            String methodNameFormat = "setThreadPic%d";
            int imageIndex = 1;
            List<Img> imageList = nat.getMultiImgs();
            if (imageList == null || imageList.isEmpty()) {
                // 单图
                if (nat.getImg() != null) {
                    nab.setThreadPic1(nat.getImg().getUrl());
                }
                // 联动图
                if (nat.getLinkedImg() != null) {
                    nab.setThreadPic2(nat.getLinkedImg().getUrl());
                }
            } else {
                for (Img image : imageList) {
                    MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), image.getUrl());
                    imageIndex++;
                }
            }
            adb.setPicUrl(nab.getThreadPic1());

            adb.setNativeAd(nab);
        }
    }

    /**
     * 交互动作处理
     *
     * @param adb
     * @param creative
     */
    private void buildInteraction(AdMsg.Builder adb, Creative creative) {
        Interaction interaction = creative.getInteraction();
        String interactionType = creative.getInteractionType();

        if (interaction == null){
            BidResponse.ComponentInfo componentInfo = creative.getComponentInfo();
            interaction = componentInfo.getInteraction();
        }

        if (interaction == null) { return;}

        if (StringUtils.isNotBlank(interaction.getUrl())) {
            adb.setLandUrl(interaction.getUrl());
        }
        if (StringUtils.isNotBlank(interaction.getDeepLink())) {
            adb.setLandUrl(interaction.getDeepLink());
            adb.setFallback(interaction.getUrl());
        }else if (interactionType.equals(InteractionType.DOWNLOAD.getValue())) {
            // 下载类处理
            adb.setExtensionType(CommonProto.ExtensionType.kExtAndroid);
            if (StringUtils.isNotBlank(creative.getPackageName())) {
                adb.setPkgName(creative.getPackageName());
            }
        }
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }

    private static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
}

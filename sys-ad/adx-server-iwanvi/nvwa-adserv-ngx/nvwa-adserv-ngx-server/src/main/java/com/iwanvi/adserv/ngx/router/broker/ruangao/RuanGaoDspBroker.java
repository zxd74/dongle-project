package com.iwanvi.adserv.ngx.router.broker.ruangao;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition.AdPositionMapping;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.BiddingProto;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.TerminalType;
import com.iwanvi.nvwa.proto.CommonProto.OSType;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.router.broker.ruangao.BidRequest.Site;
import com.iwanvi.adserv.ngx.router.broker.ruangao.BidRequest.Imp;
import com.iwanvi.adserv.ngx.router.broker.ruangao.BidRequest.App;
import com.iwanvi.adserv.ngx.router.broker.ruangao.BidRequest.Device;
import com.iwanvi.adserv.ngx.router.broker.ruangao.BidRequest.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 郑晓东
 * @date: 2019-05-05 11:40
 * @version: v1.0
 * @Description: 软告云 DSP 数据转换处理 Broker
 */
public class RuanGaoDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(RuanGaoDspBroker.class);

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private static final Map<Carrier, Integer> carriersHolder = new ConcurrentHashMap<>();
    private static final Map<OSType, String> osTypeHolder = new ConcurrentHashMap<>();
    private static final Map<ConnectType,Integer> connectTypeHolder = new ConcurrentHashMap<>();

    static{
        // 运营商类型
        carriersHolder.put(Carrier.kMobile, BidRequest.Carrier.CHINA_MOBILE.getValue());
        carriersHolder.put(Carrier.kTelecom, BidRequest.Carrier.CHINA_TELECOM.getValue());
        carriersHolder.put(Carrier.kUnicom, BidRequest.Carrier.CHINA_UNICOM.getValue());
        carriersHolder.put(Carrier.kCarrierUnKnown, BidRequest.Carrier.UNKNOWN.getValue());

        // 操作系统类型
        osTypeHolder.put(OSType.kAndroid,"android");
        osTypeHolder.put(OSType.kIOS,"apple");
        osTypeHolder.put(OSType.kPc,"pc");

        // 网络连接类型
        connectTypeHolder.put(ConnectType.kConnectTypeUnKnown, BidRequest.ConnectionType.NONE.getValue());
        connectTypeHolder.put(ConnectType.kWifi,BidRequest.ConnectionType.WIFI.getValue());
        connectTypeHolder.put(ConnectType.k2g,BidRequest.ConnectionType.NET.getValue());
        connectTypeHolder.put(ConnectType.k3g,BidRequest.ConnectionType.NET.getValue());
        connectTypeHolder.put(ConnectType.k4g,BidRequest.ConnectionType.NET.getValue());
        connectTypeHolder.put(ConnectType.k5g,BidRequest.ConnectionType.NET.getValue());
    }


    @Override
    public String getDspId() {
        return MinervaCfg.get().getConfigProperty("ruangao.dspid");
    }

    /**
     * 获取软告云接口版本
     * @return
     */
    private String getInterfaceVersion(){
        return MinervaCfg.get().getConfigProperty("ruangao.interfaceversion");
    }

    /**
     * 软广告云上的媒体ID（万维注册ID）
     * @return
     */
    private String getMediaId(){
        return MinervaCfg.get().getConfigProperty("ruangao.mediaid");
    }

    /**
     * 软广告云上的媒体名称（万维注册名称）
     * @return
     */
    private String getMediaName(){
        return "中文万维";
    }

    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {

        BidRequest.Builder brb = BidRequest.builder();

        brb.withId(biddingReq.getId()).withVersion(getInterfaceVersion()).withSite(buildSite(biddingReq))
                .withImp(buildImp(biddingReq)).withApp(buildApp(biddingReq)).withDevice(buildDevice(biddingReq));

        BiddingTracer.trace( biddingReq.getIsDebug(),JSON.toJSONString(brb.build()));
        return JSON.toJSONBytes(brb.build());
    }

    /**
     * 构建Site
     * @param biddingReq
     * @return
     */
    private Site buildSite(BiddingReq biddingReq) {
        Site.Builder builder = Site.builder();
        builder.withId(getMediaId()).withName(getMediaName());
        return builder.build();
    }

    /**
     * 构建Impression
     * @param biddingReq
     * @return
     */
    private Imp buildImp(BiddingReq biddingReq) {
        PosInfo posInfo = biddingReq.getPosInfo(0);
        Imp.Builder builder = Imp.builder();

        builder.withId(posInfo.getId());

        // 广告位映射处理
        String ruanGaoPosId = RtbUtils.getMappingDspAdSlotId(biddingReq, getDspId());
        if (ruanGaoPosId == null) {
            throw new AdNgxException("没有匹配的dsp平台广告位映射, adslotId: " + posInfo.getPosId().toStringUtf8());
        }
        builder.withTagId(ruanGaoPosId);

        // Banner或Video处理
        CreativeType creativeType = posInfo.getCreativeType(0);

        if (creativeType == CreativeType.kVideo){
            // Video广告请求
            BidRequest.Video.Builder video = BidRequest.Video.builder();
            video.withWidth(posInfo.getWidth()).withHeight(posInfo.getHeight()).withMaxduration(posInfo.getMaxDuration()).withMinduration(posInfo.getMinDuration());
            builder.withVideo(video.build());
        }else{
            // Banner 广告请求: Banner 和信息流都填充到Banner对象中
            BidRequest.Banner.Builder banner = BidRequest.Banner.builder();
            banner.withWidth(posInfo.getWidth()).withHeight(posInfo.getHeight());
            builder.withBanner(banner.build());
        }
        return builder.build();
    }

    /**
     * 构建APP对象
     * @param biddingReq
     * @return
     */
    private App buildApp(BiddingReq biddingReq) {
        PosInfo posInfo = biddingReq.getPosInfo(0);
        App.Builder builder = App.builder();
        // AppId为包名
        builder.withId(posInfo.getBundle())
                .withName(posInfo.getAppName())
                .withVer(posInfo.getAppVersion());
        return builder.build();
    }

    /**
     * 构建设备Device
     * @param biddingReq
     * @return
     */
    private Device buildDevice(BiddingReq biddingReq) {
        UserInfo userInfo = biddingReq.getUserInfo();
        Carrier carrier = userInfo.getCarrier();
        OSType os = userInfo.getOs();

        Device.Builder builder = Device.builder();

        builder.withUa(userInfo.getUa()).withIp(userInfo.getIp())
                .withCarrier(carriersHolder.get(carrier).toString())
                .withModel(userInfo.getDeviceModel().toStringUtf8())
                .withOs(osTypeHolder.get(os)).withOsv(userInfo.getOsv())
                .withConnectionType(connectTypeHolder.get(userInfo.getConnectType()))
                .withMac(userInfo.getMac()).withWidth(userInfo.getScreenWidth()).withHeight(userInfo.getScreenHeight());

        // 移动端处理
        if (TerminalType.forNumber(userInfo.getTerminalType().getNumber()) == TerminalType.kMobileTerminal){
            builder.withId(userInfo.getMuid().toStringUtf8()).withdpId(userInfo.getMuid().toStringUtf8());
        }
        // 设备类型处理
        if (os == OSType.kIOS){
            builder.withDeviceType(BidRequest.DeviceType.IPHONE.getValue());
        }else if(os == OSType.kAndroid){
            builder.withDeviceType(BidRequest.DeviceType.ANDROID.getValue());
        }else if(os == OSType.kPc){
            builder.withDeviceType(BidRequest.DeviceType.PC.getValue());
        }
        return builder.build();
    }

    /**
     * 构建User（PC端必须）
     * @param biddingReq
     * @return
     */
    private User buildUser(BiddingReq biddingReq) {
        User.Builder builder = User.builder();
        builder.withId("");
        return builder.build();
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();
        List<BiddingProto.AdMsg> adList = new ArrayList<>();
        try {
            BidResponse bidResponse = JSON.parseObject(responseBody, BidResponse.class);
            BiddingTracer.trace(biddingReq.getIsDebug(), "软告云 bidResponse: {}",JSON.toJSONString(bidResponse));

            if (bidResponse == null || bidResponse.getId() == null){
                BiddingTracer.trace(isDebug, "软告云dsp返回广告为空");
            }else{
                AdMsg adMsg = toAdMsg(biddingReq,bidResponse);
                if (adMsg!=null){
                    adList.add(adMsg);
                }
            }
        } catch (Throwable ex) {
            LOG.error("", ex);
        }
        return adList;
    }

    private AdMsg toAdMsg(BiddingReq biddingReq,BidResponse bidResponse) {
        PosInfo posInfo = biddingReq.getPosInfo(0);
        CreativeType creativeType = posInfo.getCreativeType(0);

        BiddingProto.AdMsg.Builder builder = BiddingProto.AdMsg.newBuilder().setDspId(getDspId()).setDspType(CommonProto.DspType.kDspTypePublic).setCostType(CommonProto.CostType.kCpm);

        if (StringUtils.isNoneBlank(bidResponse.getCrid())){builder.setCrid(bidResponse.getCrid());}
        if (StringUtils.isNoneBlank(bidResponse.getTitle())){ builder.setTitle(ByteString.copyFromUtf8(bidResponse.getTitle()));}
        if (StringUtils.isNoneBlank(bidResponse.getDesc())){ builder.setDescription(bidResponse.getDesc());}

        builder.setCreativeType(creativeType).setPicUrl(bidResponse.getAdUrl()).setId(bidResponse.getId());

        // 价格处理
        if (StringUtils.isNotBlank(bidResponse.getPrice())){
            int price = Double.valueOf(bidResponse.getPrice()).intValue();
            builder.setBidPrice(price).setPaidCpc(price);
        }

        builder.setLandUrl(bidResponse.getTarget());
        // deeplink 广告落地页处理
        if (StringUtils.isNoneBlank(bidResponse.getDplurl())){
            builder.setLandUrl(bidResponse.getDplurl()).setFallback(bidResponse.getTarget());
        }else if (bidResponse.getAdType().equals(BidResponse.AdType.DOWNLOAD.getValue())){
            // 下载广告处理
            builder.setExtensionType(CommonProto.ExtensionType.kExtAndroid);
            if (StringUtils.isNoneBlank(bidResponse.getApkname())){
                builder.setAppName(bidResponse.getApkname());
            }
            if (StringUtils.isNoneBlank(bidResponse.getPackageName())){
                builder.setPkgName(bidResponse.getPackageName());
            }
        }

        // 视频广告处理
        if (creativeType == CreativeType.kVideo){
            builder.setCreativeType(CreativeType.kVideo).setAdDuration(bidResponse.getDuration());
        }else{
            // Banner 和 原生（信息流）广告处理
            builder.setNativeAd(buildNativeAd(bidResponse));
        }

        // 曝光和点击监测url处理
        List<String> impMonitorUrls = new ArrayList<>(bidResponse.getPv().size());
        bidResponse.getPv().stream().forEach((pv)->impMonitorUrls.add(pv.getUrl()));
        builder.addAllImpMonitorUrl(impMonitorUrls);
        List<String> clkMonitorUrls = new ArrayList<>(bidResponse.getClick().size());
        bidResponse.getClick().stream().forEach((clk)->clkMonitorUrls.add(clk.getUrl()));
        builder.addAllClkMonitorUrl(clkMonitorUrls);

        return builder.build();
    }

    private NativeAd.Builder buildNativeAd(BidResponse bidResponse) {
        NativeAd.Builder nab = NativeAd.newBuilder();

        // 可选属性处理
        if (bidResponse.getButtonText()!=null){nab.setButtonText(bidResponse.getButtonText()).setThreadBtnText(bidResponse.getButtonText());}
        if (bidResponse.getTitle()!=null){ nab.setThreadTitle(bidResponse.getTitle());}
        if (bidResponse.getIconUrl()!=null){ nab.setUserPortrait(bidResponse.getIconUrl());}
        if (bidResponse.getDesc()!=null){nab.setThreadContent(bidResponse.getDesc());}

        nab.setThreadPic1(bidResponse.getAdUrl());
        return nab;
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }
}

package com.iwanvi.adserv.ngx.router.broker.shenqihuyu;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 郑晓东
 * @date: 2019-06-13 12:30
 * @version: v1.0
 * @Description: 神奇互娱请求响应数据转换
 */
public class ShenQiHuYuDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(ShenQiHuYuDspBroker.class);
    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("shenqihuyu.dspid");
    private static final Map<ConnectType,Integer> connectHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier,String> carrierHolder = new ConcurrentHashMap<>();
    private static final Map<CreativeType,Integer> adTypeHolder = new ConcurrentHashMap<>();

    static{
        connectHolder.put(ConnectType.kConnectTypeUnKnown, ShenQiHuYuUtil.NetType.UNKNOWN.getValue());
        connectHolder.put(ConnectType.kWifi, ShenQiHuYuUtil.NetType.WIFI.getValue());
        connectHolder.put(ConnectType.k2g, ShenQiHuYuUtil.NetType.CELLULAR.getValue());
        connectHolder.put(ConnectType.k3g, ShenQiHuYuUtil.NetType.CELLULAR.getValue());
        connectHolder.put(ConnectType.k4g, ShenQiHuYuUtil.NetType.CELLULAR.getValue());
        connectHolder.put(ConnectType.k5g, ShenQiHuYuUtil.NetType.CELLULAR.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, ShenQiHuYuUtil.CarrierType.UNKNOWN.getValue());
        carrierHolder.put(Carrier.kMobile, ShenQiHuYuUtil.CarrierType.CHINA_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, ShenQiHuYuUtil.CarrierType.CHINA_UNICOM.getValue());
        carrierHolder.put(Carrier.kTelecom, ShenQiHuYuUtil.CarrierType.CHINA_TELECOM.getValue());

        adTypeHolder.put(CreativeType.kVideo,ShenQiHuYuUtil.AdType.VIDEO.getValue());
        adTypeHolder.put(CreativeType.kPic,ShenQiHuYuUtil.AdType.BANNER.getValue());
        adTypeHolder.put(CreativeType.kNative,ShenQiHuYuUtil.AdType.INTERSTITIAL.getValue());
        adTypeHolder.put(CreativeType.kText,ShenQiHuYuUtil.AdType.BANNER.getValue());
        adTypeHolder.put(CreativeType.kFirstScreen,ShenQiHuYuUtil.AdType.FULLSCREEN.getValue());
        adTypeHolder.put(CreativeType.kScreen,ShenQiHuYuUtil.AdType.INTERSTITIAL.getValue());

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
        boolean isDebug = biddingReq.getIsDebug();
        String resBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug,"神奇互娱响应内容{}",resBody);

        BidResponse bidResponse = JSON.parseObject(resBody,BidResponse.class);

        if (bidResponse==null || bidResponse.getCode()!=0){
            BiddingTracer.trace(isDebug,"神奇互娱无广告响应");
            return null;
        }

        AdMsg.Builder builder = AdMsg.newBuilder();
        PosInfo posInfo = biddingReq.getPosInfo(0);
        CreativeType creativeType = posInfo.getCreativeType(0);

        builder.setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        BidResponse.Ad ad = bidResponse.getAd();
        if (ad == null){
            BiddingTracer.trace(isDebug,"神奇互娱广告响应内容缺失");
            return null;
        }
        builder.setLandUrl(ad.getLpg());

        if (creativeType == CreativeType.kVideo){
            // 视频广告响应处理
            BidResponse.Video video = bidResponse.getVideo();
            if (video ==null){
                BiddingTracer.trace(isDebug,"神奇互娱视频广告响应内容缺失");
                return null;
            }
            builder.setPicUrl(video.getVideoHUrl()).setCreativeType(CreativeType.kVideo).setAdDuration(video.getDuration());
        }else if (creativeType == CreativeType.kNative){
            // 原生广告处理
            NativeAd.Builder nab = NativeAd.newBuilder();
            nab.setThreadPic1(ad.getImgUrl());

            builder.setNativeAd(nab.build()).setPicUrl(ad.getImgUrl());
        }

        // DeepLink处理
        if (StringUtils.isNoneBlank(ad.getDeepLink())){
            builder.setLandUrl(ad.getDeepLink()).setFallback(ad.getLpg());
        }else if (ad.getAdtype().equals(ShenQiHuYuUtil.AdType.DOWNLOAD.getValue())){
            // 下载类处理
            BidResponse.AdExt adExt = bidResponse.getAdext();
            builder.setExtensionType(ExtensionType.kExtAndroid);
            if (StringUtils.isNoneBlank(adExt.getPackageName())){
                builder.setPkgName(adExt.getPackageName());
            }
            if (StringUtils.isNoneBlank(adExt.getAppName())){
                builder.setPkgName(adExt.getAppName());
            }
        }

        // 监测地址处理
        builder.addAllImpMonitorUrl(new ArrayList<>(Arrays.asList(ad.getImp()))).addAllClkMonitorUrl(new ArrayList<>(Arrays.asList(ad.getClick())));

        AdMsg adMsg = builder.build();
        List<AdMsg> adMsgs = new ArrayList<>();
        if (adMsg != null){
            adMsgs.add(adMsg);
        }
        return adMsgs;
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
        CreativeType creativeType = posInfo.getCreativeType(0);
        CreativeType  adType = posInfo.getAdType();

        AdModelsProto.SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq,getDspId());
        if (positionMapping==null){
            throw new AdNgxException("神奇互娱无广告位映射：" + posInfo.getPosId());
        }
        builder.withMagicKey(positionMapping.getDspAdPositionId()).withPackageName(posInfo.getBundle()).withAppName(posInfo.getAppName())
                .withAppVersion(posInfo.getAppVersion()).withWidth(userInfo.getScreenWidth()).withHeight(userInfo.getScreenHeight())
                .withIP(userInfo.getIp()).withUa(URLEncoder.encode(userInfo.getUa())).withMac(userInfo.getMac()).withOsVersion(userInfo.getOsv()).withModel(userInfo.getDeviceModel().toStringUtf8())
                .withBrand(userInfo.getDeviceBrand().toStringUtf8()).withOrientation(ShenQiHuYuUtil.OrientationType.LANDSCAPE.getValue());

        // 广告处理
        if (positionMapping.hasDspAdType() && adTypeHolder.containsKey(positionMapping.getDspAdType())){
            builder.withType(adTypeHolder.get(positionMapping.getDspAdType()));
        }else{
            adTypeHolder.get(creativeType);
        }

        // 设备处理
        if (osType == OSType.kIOS){
            // IOS设备
            builder.withIdfa(userInfo.getMuid().toStringUtf8()).withIdfv("").withPlatform(ShenQiHuYuUtil.PlatformType.IOS.getValue());
        }else if(osType == OSType.kAndroid){
            // ANDROID设备
            builder.withImei(userInfo.getMuid().toStringUtf8()).withImsi("").withAid(userInfo.getAdid()).withPlatform(ShenQiHuYuUtil.PlatformType.ANDROID.getValue());
        }

        // 网络处理
        builder.withNetType(connectHolder.get(userInfo.getConnectType()));

        // 运营商处理
        builder.withCarrier(carrierHolder.get(userInfo.getCarrier()));

        // 地理位置处理
        builder.withLat((float)userInfo.getLat()).withLon((float)userInfo.getLon());

        // 请求转换
        BidRequest bidRequest = builder.build();
        QueryStringBuilder<BidRequest> qsBuilder = QueryStringBuilder.create(bidRequest);
        String request = qsBuilder.toQueryString();
        // 注意缺陷：如果其他内容包含package，则会造成误差
        request.replaceFirst("packageName=","package=");

        BiddingTracer.trace(biddingReq.getIsDebug(),"神奇互娱广告请求为：{}",request);
        return request;
    }
}

package com.iwanvi.adserv.ngx.router.broker.mifu;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.MD5Utils;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;
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

/**
 * @version : v1.0
 * @author : 郑晓东
 * @since : 2019-08-06 09:56
 */
public class MiFuDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(MiFuDspBroker.class);

    private static final String DSP_ID= MinervaCfg.get().getConfigProperty("mifu.dspid");

    private static final Map<ConnectType,Integer> netHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier,Integer> carrierHolder = new ConcurrentHashMap<>();
    private static final Map<OSType,Integer> osHolder = new ConcurrentHashMap<>();

    static {
        netHolder.put(ConnectType.kConnectTypeUnKnown, MiFuUtil.NetType.UNKNOWN.getValue());
        netHolder.put(ConnectType.kWifi, MiFuUtil.NetType.WIFI.getValue());
        netHolder.put(ConnectType.k2g, MiFuUtil.NetType.K2G.getValue());
        netHolder.put(ConnectType.k3g, MiFuUtil.NetType.K3G.getValue());
        netHolder.put(ConnectType.k4g, MiFuUtil.NetType.K4G.getValue());
        netHolder.put(ConnectType.k5g, MiFuUtil.NetType.UNKNOWN.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, MiFuUtil.Carrier.UNKNOWN.getValue());
        carrierHolder.put(Carrier.kMobile, MiFuUtil.Carrier.CHINA_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, MiFuUtil.Carrier.CHINA_UNICOM.getValue());
        carrierHolder.put(Carrier.kTelecom, MiFuUtil.Carrier.CHINA_TELECOM.getValue());

        osHolder.put(OSType.kOSUnKnown, MiFuUtil.OsType.OTHER.getValue());
        osHolder.put(OSType.kHtml5, MiFuUtil.OsType.OTHER.getValue());
        osHolder.put(OSType.kPc, MiFuUtil.OsType.OTHER.getValue());
        osHolder.put(OSType.kAndroid, MiFuUtil.OsType.ANDROID.getValue());
        osHolder.put(OSType.kIOS, MiFuUtil.OsType.IOS.getValue());
    }

    @Override
    public String toQueryString(BiddingReq biddingReq) {
        BidRequest.Builder builder = BidRequest.builder();

        PosInfo posInfo = biddingReq.getPosInfo(0);
        UserInfo userInfo = biddingReq.getUserInfo();
        SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq,getDspId());
        if (positionMapping == null){
            throw new AdNgxException("米赋缺少广告位映射："+posInfo.getPosId().toStringUtf8());
        }
        builder.withPosId(positionMapping.getDspAdPositionId());

        OSType osType = userInfo.getOs();
        String muid = userInfo.getMuid().toStringUtf8();
        if (osType == OSType.kAndroid){
            builder.withImei(muid).withImeiMD5(MD5Utils.md5Hex(muid)).withAndroidId(userInfo.getAdid());
        }else if (osType == OSType.kIOS){
            builder.withIdfa(muid);
        }

        builder.withPackage(posInfo.getBundle()).withAppName(posInfo.getAppName()).withAppVersion(posInfo.getAppVersion()).withUa(userInfo.getUa())
                .withNetType(netHolder.get(userInfo.getConnectType())).withCarrier(carrierHolder.get(userInfo.getCarrier()))
                .withOs(osHolder.get(osType)).withOsVersion(userInfo.getOsv()).withBrand(userInfo.getDeviceBrand().toStringUtf8()).withModel(userInfo.getDeviceModel().toStringUtf8()).withMac(userInfo.getMac())
                .withWidth(userInfo.getScreenWidth()).withHeight(userInfo.getScreenHeight()).withLat(userInfo.getLat()).withLon(userInfo.getLon()).withIp(userInfo.getIp()).withDensity(userInfo.getDensity());

        BidRequest request = builder.build();

        QueryStringBuilder queryStringBuilder = QueryStringBuilder.create(request);
        String queryString = queryStringBuilder.toQueryString();

        BiddingTracer.trace(biddingReq.getIsDebug(),"请求：{}，米赋：{}，广告请求内容：{}",biddingReq.getId(),getDspId(),queryString);
        return queryString;
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

        String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug,"请求：{},米赋:{},广告响应：{}",biddingReq.getId(),getDspId(),respBody);
        BidResponse bidResponse = JSON.parseObject(respBody,BidResponse.class);
        if (bidResponse==null){
            BiddingTracer.trace(isDebug,"请求：{},米赋广告响应内容为空",biddingReq.getId());
        }
        List<AdMsg> adMsgs = new ArrayList<>();

        try {
            AdMsg adMsg = buildAdMsg(bidResponse);
            if (adMsg!=null){
                adMsgs.add(adMsg);
            }
        }catch (Exception ex){
            LOG.error("",ex);
        }

        return adMsgs;
    }

    private AdMsg buildAdMsg(BidResponse bidResponse) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        AdMsg.Builder builder = AdMsg.newBuilder();

        builder.setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        String landUrl="",title="",desc="",icon="";
        if (StringUtils.isNoneBlank(bidResponse.getIp())){landUrl=bidResponse.getIp();}
        if (StringUtils.isNoneBlank(bidResponse.getTitle())){title = bidResponse.getTitle();}
        if (StringUtils.isNoneBlank(bidResponse.getDesc())){desc = bidResponse.getDesc();}
        if (StringUtils.isNoneBlank(bidResponse.getIcon())){icon=bidResponse.getIcon();}

        builder.setLandUrl(landUrl);

        Integer adformat = bidResponse.getAdformat();
        if(adformat == null){return null;}
        if (adformat.equals(MiFuUtil.AdFormat.MP4.getValue())){
            // 视频广告处理
            builder.setCreativeType(CreativeType.kVideo);
            if (StringUtils.isNoneBlank(bidResponse.getMediafile())){builder.setPicUrl(bidResponse.getMediafile());}
            if (bidResponse.getDuration()!=null){builder.setAdDuration(bidResponse.getDuration());}
        }else{
            // 非视频广告处理
            NativeAd.Builder nab = NativeAd.newBuilder();
            nab.setUserPortrait(icon).setThreadTitle(title).setThreadContent(desc);

            // 素材处理
            String methodNameFormat = "setThreadPic%d";
            int imageIndex = 1;
            for (String image : bidResponse.getImg()) {
                MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), image);
                imageIndex++;
            }
            if (nab.hasThreadPic1()){
                builder.setPicUrl(nab.getThreadPic1());
            }
            builder.setNativeAd(nab.build());
        }

        // 检测处理
        builder.addAllImpMonitorUrl(Arrays.asList(bidResponse.getPm())).addAllClkMonitorUrl(Arrays.asList(bidResponse.getCm()));

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
}

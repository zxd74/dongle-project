package com.iwanvi.adserv.ngx.router.broker.taido;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
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
 * @since : 2019-08-01 17:13
 */
public class TaiDoDspBroker  implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(TaiDoDspBroker.class);

    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("taido.dspid");
    private static final String API_VER = MinervaCfg.get().getConfigProperty("taido.apiver");
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

    private static final Map<CreativeType,Integer> adTypeHolder =new ConcurrentHashMap<>();
    private static final Map<CreativeType,Integer> adTypeMapHoder =new ConcurrentHashMap<>();
    private static final Map<DeviceType,Integer> deviceTypeHolder =new ConcurrentHashMap<>();
    private static final Map<Carrier,Integer> carrierHolder =new ConcurrentHashMap<>();
    private static final Map<ConnectType,Integer> netTypeHolder =new ConcurrentHashMap<>();
    static{
        adTypeHolder.put(CreativeType.kPic, TaiDoUtils.AdType.FIRST_SCREEN.getValue());
        adTypeHolder.put(CreativeType.kNative, TaiDoUtils.AdType.NATIVE.getValue());
        adTypeHolder.put(CreativeType.kVideo, TaiDoUtils.AdType.NATIVE.getValue());

        adTypeMapHoder.put(CreativeType.kPic, TaiDoUtils.AdType.BANNER.getValue());
        adTypeMapHoder.put(CreativeType.kText, TaiDoUtils.AdType.BANNER.getValue());
        adTypeMapHoder.put(CreativeType.kFocusPic, TaiDoUtils.AdType.SCREEN.getValue());
        adTypeMapHoder.put(CreativeType.kScreen, TaiDoUtils.AdType.SCREEN.getValue());
        adTypeMapHoder.put(CreativeType.kFirstScreen, TaiDoUtils.AdType.FIRST_SCREEN.getValue());
        adTypeMapHoder.put(CreativeType.kNative, TaiDoUtils.AdType.NATIVE.getValue());
        adTypeMapHoder.put(CreativeType.kVideo, TaiDoUtils.AdType.NATIVE.getValue());

        deviceTypeHolder.put(DeviceType.kPhone, TaiDoUtils.DeviceType.PHONE.getValue());
        deviceTypeHolder.put(DeviceType.kPad, TaiDoUtils.DeviceType.TABLET.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, TaiDoUtils.Carrier.UNKNOWN.getValue());
        carrierHolder.put(Carrier.kMobile, TaiDoUtils.Carrier.CHINA_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, TaiDoUtils.Carrier.CHINA_UNICOM.getValue());
        carrierHolder.put(Carrier.kTelecom, TaiDoUtils.Carrier.CHINA_TELETCOM.getValue());

        netTypeHolder.put(ConnectType.kConnectTypeUnKnown, TaiDoUtils.NetType.UNKNOWN.getValue());
        netTypeHolder.put(ConnectType.k2g, TaiDoUtils.NetType.K2G.getValue());
        netTypeHolder.put(ConnectType.k3g, TaiDoUtils.NetType.K3G.getValue());
        netTypeHolder.put(ConnectType.k4g, TaiDoUtils.NetType.K4G.getValue());
        netTypeHolder.put(ConnectType.k5g, TaiDoUtils.NetType.K5G.getValue());
        netTypeHolder.put(ConnectType.kWifi, TaiDoUtils.NetType.WIFI.getValue());
    }

    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {
        BidRequest.Builder builder = BidRequest.builder();

        PosInfo posInfo = biddingReq.getPosInfo(0);
        UserInfo userInfo = biddingReq.getUserInfo();

        // 广告位处理
        BidRequest.AdInfo.Builder adInfo = BidRequest.AdInfo.builder();
        SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq,getDspId());
        if (positionMapping==null){
            throw new AdNgxException("TaiDo广告位映射获取失败："+posInfo.getPosId().toStringUtf8());
        }
        adInfo.withAdId(positionMapping.getDspAdPositionId()).withAdNum(1).withAdWidth(positionMapping.getWidth()).withAdHeight(positionMapping.getHeight());
        // 映射广告位类型优先处理
        if (positionMapping.hasDspAdType()){
            adInfo.withAdType(adTypeMapHoder.get(positionMapping.getDspAdType()));
        }else{
            // 没有映射广告位类型
            SspAdPosition adslot = RepositoryFactory.getRepository().loadSspAdPosition(posInfo.getPosId().toStringUtf8());
            CreativeType adType = adslot.getAdType();
            if (adType == CreativeType.kFirstScreen) {
                adInfo.withAdType(adTypeHolder.get(adType));
            } else {
                // 非开屏广告则按广告类型处理
                CreativeType creativeType = posInfo.getCreativeType(0);
                adInfo.withAdType(adTypeHolder.get(creativeType));
            }
        }

        // 客户端处理
        BidRequest.ClientInfo.Builder clientInfo = BidRequest.ClientInfo.builder();
        // 根据系统处理请求信息
        OSType osType = userInfo.getOs();
        if (osType==OSType.kAndroid){
            clientInfo.withOs(TaiDoUtils.OsType.ANDROID.getValue()).withAndroidId(userInfo.getAdid()).withImei(userInfo.getMuid().toStringUtf8());
        }else if(osType== OSType.kIOS){
            clientInfo.withOs(TaiDoUtils.OsType.IOS.getValue()).withIdfa(userInfo.getMuid().toStringUtf8());
        }
        clientInfo.withBrand(userInfo.getDeviceBrand().toStringUtf8()).withModel(userInfo.getDeviceModel().toStringUtf8()).withUa(userInfo.getUa())
                .withOsVersion(userInfo.getOsv()).withMac(userInfo.getMac()).withNet(netTypeHolder.get(userInfo.getConnectType())).withCarrier(carrierHolder.get(userInfo.getCarrier()))
                .withType(deviceTypeHolder.get(userInfo.getDeviceType())).withDirect(0).withScreenWidth(userInfo.getScreenWidth()).withScreenHeightd(userInfo.getScreenHeight()).withDensity(userInfo.getDensity());

        // 用户信息处理
        BidRequest.UserInfo.Builder user = BidRequest.UserInfo.builder();
        user.withIp(userInfo.getIp()).withLat(userInfo.getLat()).withLng(userInfo.getLon());

        // 构建请求信息
        builder.withAdInfo(adInfo.build()).withClientInfo(clientInfo.build()).withUserInfo(user.build()).withApiVersion(API_VER);

        BiddingTracer.trace(biddingReq.getIsDebug(),"TAI DO：{}，广告请求：{}",getDspId(),JSON.toJSONString(builder.build()));
        return JSON.toJSONBytes(builder.build());
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
        BiddingTracer.trace(isDebug,"TaiDo:{},广告响应内容：{}",getDspId(),respBody);

        BidResponse response = JSON.parseObject(respBody,BidResponse.class);
        if (response==null||response.getRes().equals(-1)){
            BiddingTracer.trace(isDebug,"TaiDo:{},无广告响应",getDspId());
        }

        List<AdMsg> adMsgs = new ArrayList<>();
        try {
        for (BidResponse.AdDetail adDetail:response.getAddetail()){
            AdMsg adMsg = buildAdMsg(adDetail);
            if (adMsg!=null){
                adMsgs.add(adMsg);
            }
        }
        } catch (Exception e) {
            LOG.error("",e);
        }
        return adMsgs;
    }

    private AdMsg buildAdMsg(BidResponse.AdDetail adDetail) throws Exception {
        if (adDetail==null){return null;}

        AdMsg.Builder builder = AdMsg.newBuilder();

        builder.setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        String title="",desc="",landUrl=""; // 标题，描述，落地页地址
        if (StringUtils.isNoneBlank(adDetail.getClickurl())){landUrl=adDetail.getClickurl();}
        if (StringUtils.isNoneBlank(adDetail.getTitle())){title=adDetail.getTitle();}
        if (StringUtils.isNoneBlank(adDetail.getRemark())){desc=adDetail.getRemark();}

        builder.setLandUrl(landUrl).setTitle(ByteString.copyFromUtf8(title)).setDescription(desc);

        // 广告类型处理
        Integer adtype = adDetail.getAdtype();
        if (adtype.equals(TaiDoUtils.AdType.VIDEO.getValue())){
            // 视频广告处理
            builder.setCreativeType(CreativeType.kVideo).setPicUrl(adDetail.getVideo());
        }else if(adtype.equals(TaiDoUtils.AdType.HTML.getValue())){
            // HTML5 广告处理，暂时不能处理
            return null;
        }else{
            // 图片、文字、图文广告处理
            NativeAd.Builder nab = NativeAd.newBuilder();
            nab.setThreadTitle(title).setThreadContent(desc);
            if (StringUtils.isNoneBlank(adDetail.getIcon())){nab.setUserPortrait(adDetail.getIcon());}

            String methodNameFormat = "setThreadPic%d";
            int imageIndex = 1;

            for (String image : adDetail.getImages()) {
                MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), image);
                imageIndex++;
            }
            if (nab.hasThreadPic1()){builder.setPicUrl(nab.getThreadPic1());}
            builder.setNativeAd(nab.build());
        }

        // 广告交互类型处理
        Integer type = adDetail.getType();
        if (type.equals(TaiDoUtils.InteractType.DEEP_LINK.getValue())){
            // DP广告处理
            if (StringUtils.isNoneBlank(adDetail.getDlink().trim())){
                builder.setLandUrl(adDetail.getDlink()).setFallback(landUrl);
            }
        } else if (type.equals(TaiDoUtils.InteractType.DOWNLOAD.getValue())){
            // 下载广告处理
            if (StringUtils.isNoneBlank(adDetail.getPack_name())){builder.setPkgName(adDetail.getPack_name());}
            builder.setExtensionType(ExtensionType.kExtAndroid);
            // 补充下载检测
            if (adDetail.getStartdown()!=null){
                List<String> dlUrls = Arrays.asList(adDetail.getStartdown());
                if (CollectionUtils.isEmpty(dlUrls)){
                    builder.addAllDlMonitorUrl(dlUrls);
                }
            }
        }else if (type.equals(TaiDoUtils.InteractType.MINI_APP.getValue())){
            // 微信小程序  不做处理
            return null;
        }

        if(adDetail.getPvurls()!=null) {
            List<String> impUrls = dspMicros(Arrays.asList(adDetail.getPvurls()));
            if (!CollectionUtils.isEmpty(impUrls)) {
                builder.addAllImpMonitorUrl(impUrls);
            }
        }

        if (adDetail.getCkurls()!=null){
            List<String> clkUrls = dspMicros(Arrays.asList(adDetail.getCkurls()));
            if (!CollectionUtils.isEmpty(clkUrls)){ builder.addAllClkMonitorUrl(clkUrls);}
        }

        return builder.build();
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }

    private List<String> dspMicros(List<String> urls){

        if (CollectionUtils.isEmpty(urls)){
            return null;
        }

        // 广点通模式下点击ID不能处理
        List<String> result = urls.stream().map(x -> x=micros(x)).collect(Collectors.toList());
        return result;
    }
    private String micros(String value){
        return value.replace("__TIMESTAMP__","{AUCTION_TIME}")
                .replace("__CLICK_DOWN_X__","-999").replace("__CLICK_DOWN_Y__","-999")
                .replace("__CLICK_UP_X__","-999").replace("__CLICK_UP_Y__","-999");
    }

}

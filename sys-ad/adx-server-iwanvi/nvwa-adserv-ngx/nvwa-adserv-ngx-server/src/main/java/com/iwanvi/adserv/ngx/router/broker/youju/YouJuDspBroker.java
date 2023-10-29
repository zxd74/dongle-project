package com.iwanvi.adserv.ngx.router.broker.youju;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.MD5Utils;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;

import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 郑晓东
 * @date: 2019-07-31 10:20
 * @version: v1.0
 * @Description: 优聚请求响应数据转换处理类
 */
public class YouJuDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(YouJuDspBroker.class);
    private static final String DSP_ID =MinervaCfg.get().getConfigProperty("youju.dspid");
    private static final String MID = MinervaCfg.get().getConfigProperty("youju.mid");
    private static final String TOKEN =MinervaCfg.get().getConfigProperty("youju.token");
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded; charset=UTF-8";

    private static final Map<ConnectType, Integer> netHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier, Integer> carrierHolder = new ConcurrentHashMap<>();
    private static final Map<DeviceType, Integer> deviceHolder = new ConcurrentHashMap<>();
    private static final Map<OSType, Integer> osHolder = new ConcurrentHashMap<>();

    static {
        netHolder.put(ConnectType.kConnectTypeUnKnown, YouJuEnumUtils.NetType.UNKNOWN.getValue());
        netHolder.put(ConnectType.k2g, YouJuEnumUtils.NetType.K2G.getValue());
        netHolder.put(ConnectType.k3g, YouJuEnumUtils.NetType.K3G.getValue());
        netHolder.put(ConnectType.k4g, YouJuEnumUtils.NetType.K4G.getValue());
        netHolder.put(ConnectType.k5g, YouJuEnumUtils.NetType.K5G.getValue());
        netHolder.put(ConnectType.kWifi, YouJuEnumUtils.NetType.WIFI.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, YouJuEnumUtils.Carrier.UNKNOWN.getValue());
        carrierHolder.put(Carrier.kMobile, YouJuEnumUtils.Carrier.CHINA_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, YouJuEnumUtils.Carrier.CHINA_UNICOM.getValue());
        carrierHolder.put(Carrier.kTelecom, YouJuEnumUtils.Carrier.CHINA_TELECOM.getValue());

        deviceHolder.put(DeviceType.kDeviceTypeUnKnown, YouJuEnumUtils.DeviceType.OTHER.getValue());
        deviceHolder.put(DeviceType.kPad, YouJuEnumUtils.DeviceType.TABLET.getValue());
        deviceHolder.put(DeviceType.kPC, YouJuEnumUtils.DeviceType.OTHER.getValue());
        deviceHolder.put(DeviceType.kPhone, YouJuEnumUtils.DeviceType.PHONE.getValue());
        deviceHolder.put(DeviceType.kTV, YouJuEnumUtils.DeviceType.OTHER.getValue());

        osHolder.put(OSType.kOSUnKnown, YouJuEnumUtils.OsType.OTHER.getValue());
        osHolder.put(OSType.kAndroid, YouJuEnumUtils.OsType.ANDROID.getValue());
        osHolder.put(OSType.kHtml5, YouJuEnumUtils.OsType.OTHER.getValue());
        osHolder.put(OSType.kPc, YouJuEnumUtils.OsType.OTHER.getValue());
        osHolder.put(OSType.kIOS, YouJuEnumUtils.OsType.IOS.getValue());
    }

    @Override
    public String getDspId() {
        return DSP_ID;
    }

    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {
        PosInfo posInfo = biddingReq.getPosInfo(0);
        UserInfo userInfo = biddingReq.getUserInfo();
        DeviceType deviceType = userInfo.getDeviceType();
        OSType osType = userInfo.getOs();

        // 构建请求构建类
        BidRequest.Builder builder = BidRequest.builder();

        // 构建应用信息
        AdModelsProto.App.AppMapping appMapping = RtbUtils.getMappingDspApp(getDspId(),posInfo.getAppId().toStringUtf8());
        if (appMapping==null){
            throw new AdNgxException("优聚App应用映射获取失败："+ posInfo.getAppId().toStringUtf8());
        }
        String[] appInfos = appMapping.getDspAppid().split("_");
        if (appInfos == null || appInfos.length != 2){
            throw new AdNgxException("优聚App应用映射配置不正确");
        }

        String appId = appInfos[0];  // 应用标识
        String secret = appInfos[1]; // 应用密钥
        builder.withAid(Integer.valueOf(appId));

        // 构建广告位信息
        AdModelsProto.SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq,getDspId());
        if (positionMapping==null){
            throw new AdNgxException("优聚广告位映射获取失败："+ posInfo.getPosId().toStringUtf8());
        }
        // 获取所配置默认尺寸素材广告
        builder.withTagid("{\""+positionMapping.getDspAdPositionId().trim()+"\":[]}");

        // 系统区分信息
        if (osType == OSType.kAndroid){
            // android区分
            builder.withMac(userInfo.getMac()).withAndroidId(userInfo.getAdid()).withImei(userInfo.getMuid().toStringUtf8());
        }else if (osType == OSType.kIOS){
            //ios区分
            builder.withIdfa(userInfo.getMuid().toStringUtf8());
        }
        builder.withOsType(osHolder.get(osType));

        // 设备区分信息
        builder.withDeviceType(deviceHolder.get(deviceType));

        // 网络连接区分信息
        builder.withNetType(netHolder.get(userInfo.getConnectType()));

        // 运营商连接区分信息
        builder.withCarrier(carrierHolder.get(userInfo.getCarrier()));

        // 编码处理: ua需要Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        // 其他信息整理: 用户ID不提供，用户信息不提供
        builder.withMid(Integer.valueOf(MID)).withIp(userInfo.getIp()).withOsVersion(URLEncoder.encode(userInfo.getOsv())).withUa(encoder.encodeToString(userInfo.getUa().getBytes()))
                .withBrand(userInfo.getDeviceBrand().toStringUtf8()).withModel(userInfo.getDeviceModel().toStringUtf8())
                .withAppName(posInfo.getAppName()).withAppVersion(posInfo.getAppVersion()).withAppPackage(posInfo.getBundle())
                .withLgt(userInfo.getLon()).withLat(userInfo.getLat()).withToken(TOKEN).withUid(String.valueOf(System.currentTimeMillis()));

        // 密钥签名处理
        // 获得未签名前的json字符串
        BidRequest bidRequest = builder.build();
        String json = JSON.toJSONString(bidRequest);
        // 将json数据转换成map
        Map<String,Object> map = (Map<String, Object>)JSON.parse(json);
        // 获得排序后的字符串
        String request = YouJuEnumUtils.kSort(map);
        // 拼接密钥：参数需要先Urldecoder处理
        request = URLDecoder.decode(request) + secret;
        // 字符串MD加密:需要URLEncoder
        builder.withSign(MD5Utils.md5Hex(request));

        // UrlEncode处理:
        builder.withOsVersion(URLEncoder.encode(bidRequest.getOv())).withBrand(URLEncoder.encode(bidRequest.getDb())).withModel(URLEncoder.encode(bidRequest.getDm()))
                .withAppName(URLEncoder.encode(bidRequest.getAn())).withAppVersion(URLEncoder.encode(bidRequest.getAv())).withAppPackage(bidRequest.getAp());

        // 构建请求body
        QueryStringBuilder queryStringBuilder = QueryStringBuilder.create(builder.build());
        String queryString = queryStringBuilder.toQueryString();

        BiddingTracer.trace(biddingReq.getIsDebug(),"优聚广告请求信息：{}",queryString);
        return queryString.getBytes();
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();

        String resBody = StringUtils.toEncodedString(responseBody,Charsets.UTF_8);
        BiddingTracer.trace(isDebug,"优聚：{}，广告响应内容：{}",getDspId(),resBody);
        BidResponse bidResponse = JSON.parseObject(resBody,BidResponse.class);
        if (bidResponse==null|| !bidResponse.getCode().equals(200)){
            BiddingTracer.trace(isDebug,"优聚：{}，无广告响应",getDspId());
            return null;
        }

        List<AdMsg> adMsgs = new ArrayList<>();
        try {
            for (BidResponse.Data data:bidResponse.getData()){
                AdMsg adMsg =  buildAdMsg(biddingReq,data);
                if (adMsg!=null){
                    adMsgs.add(adMsg);
                }
            }
        }catch (Exception e) {
                e.printStackTrace();
            }
        return adMsgs;
    }

    private AdMsg buildAdMsg(BiddingReq biddingReq,BidResponse.Data data) throws Exception {
        AdMsg.Builder builder = AdMsg.newBuilder();
        builder.setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        if (StringUtils.isNoneBlank(data.getLandingurl())){
            builder.setLandUrl(data.getLandingurl());
        }

       if (data.getImg() == null){
           LOG.error("请求ID：{}，优聚：{}无广告素材返回",biddingReq.getId(),getDspId());
       }else{
           AdModelsProto.NativeAd.Builder nab = AdModelsProto.NativeAd.newBuilder();
           String title="",desc="";
           String methodNameFormat = "setThreadPic%d";
           int imageIndex = 1;
           for (BidResponse.Img img:data.getImg()){
               if (img.getTitle()!=null){ title=img.getTitle();}
               if (img.getDesc()!=null){ desc=img.getDesc();}
               MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), img.getUrl());
               imageIndex++;
           }
           nab.setThreadTitle(title).setThreadContent(title);
           builder.setTitle(ByteString.copyFromUtf8(title)).setDescription(desc).setPicUrl(nab.getThreadPic1()).setNativeAd(nab.build());
       }

        if (data.getExposureUrl()!=null){builder.addAllImpMonitorUrl(Arrays.asList(data.getExposureUrl()));}
        if (data.getClickUrl()!=null){builder.addAllClkMonitorUrl(Arrays.asList(data.getClickUrl()));}

        return builder.build();
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }
}

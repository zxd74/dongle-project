package com.iwanvi.adserv.ngx.router.broker.meishu;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 郑晓东
 * @date: 2019-07-09 16:42
 * @version: v1.0
 * @Description: 美数广告请求响应数据转换处理类
 */
public class MeiShuDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(MeiShuDspBroker.class);
    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("meishu.dspid");
    private static final String API_VER = MinervaCfg.get().getConfigProperty("meishu.apiver");
    private static final Map<DeviceType, Integer> deviceTypeHolder = new ConcurrentHashMap<>();
    private static final Map<OSType, String> osTypeHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier, String> carrierHolder = new ConcurrentHashMap<>();
    private static final Map<ConnectType, Integer> netHolder = new ConcurrentHashMap<>();

    static {
        deviceTypeHolder.put(DeviceType.kPC, MeiShuEnumUtils.DeviceType.PC.getValue());
        deviceTypeHolder.put(DeviceType.kTV, MeiShuEnumUtils.DeviceType.TV.getValue());
        deviceTypeHolder.put(DeviceType.kPad, MeiShuEnumUtils.DeviceType.PAD.getValue());
        deviceTypeHolder.put(DeviceType.kPhone, MeiShuEnumUtils.DeviceType.PHONE.getValue());
        deviceTypeHolder.put(DeviceType.kDeviceTypeUnKnown, MeiShuEnumUtils.DeviceType.UNKNOWN.getValue());

        osTypeHolder.put(OSType.kPc, MeiShuEnumUtils.OsType.WP.getValue());
        osTypeHolder.put(OSType.kAndroid, MeiShuEnumUtils.OsType.ANDROID.getValue());
        osTypeHolder.put(OSType.kIOS, MeiShuEnumUtils.OsType.IOS.getValue());
        osTypeHolder.put(OSType.kOSUnKnown, MeiShuEnumUtils.OsType.OTHERS.getValue());
        osTypeHolder.put(OSType.kHtml5, MeiShuEnumUtils.OsType.OTHERS.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown, MeiShuEnumUtils.Carrier.OTHER.getValue());
        carrierHolder.put(Carrier.kMobile, MeiShuEnumUtils.Carrier.CHAIN_MOBILE.getValue());
        carrierHolder.put(Carrier.kUnicom, MeiShuEnumUtils.Carrier.CHAIN_UNION.getValue());
        carrierHolder.put(Carrier.kTelecom, MeiShuEnumUtils.Carrier.CHAIN_TELECOM.getValue());

        netHolder.put(ConnectType.kConnectTypeUnKnown, MeiShuEnumUtils.NetType.OTHER.getValue());
        netHolder.put(ConnectType.kWifi, MeiShuEnumUtils.NetType.WIFI.getValue());
        netHolder.put(ConnectType.k2g, MeiShuEnumUtils.NetType.K2G.getValue());
        netHolder.put(ConnectType.k3g, MeiShuEnumUtils.NetType.K3G.getValue());
        netHolder.put(ConnectType.k4g, MeiShuEnumUtils.NetType.K4G.getValue());
        netHolder.put(ConnectType.k5g, MeiShuEnumUtils.NetType.K5G.getValue());
    }

    @Override
    public String getDspId() {
        return DSP_ID;
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
        DeviceType deviceType = userInfo.getDeviceType();
        OSType osType = userInfo.getOs();
        builder.withVersion(API_VER).withReqId(biddingReq.getId());

        // 广告位处理
        builder.withPid(RtbUtils.getMappingDspAdSlotId(biddingReq, getDspId()));
        // 服务器IP
        try {
            builder.withIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        // App 映射处理
        App.AppMapping appMapping = RtbUtils.getMappingDspApp(getDspId(), posInfo.getAppId().toStringUtf8());
        if (appMapping == null || !appMapping.hasDspAppid()) {
            throw new AdNgxException(String.format("美数对接缺少必要App映射：%s", posInfo.getAppId().toStringUtf8()));
        }
        builder.withAppPackage(posInfo.getBundle()).withAppId(appMapping.getDspAppid()).withAppName(posInfo.getAppName()).withAppVer(posInfo.getAppVersion());

        // GEO处理
        builder.withDeviceGeoLat(userInfo.getLat()).withDeviceGeoLon(userInfo.getLon());

        // 操作系统类型处理
        if (osType == OSType.kAndroid) {
            // Android
            builder.withDeviceImei(userInfo.getMuid().toStringUtf8()).withDeviceAdId(userInfo.getAdid());
        } else if (osType == OSType.kIOS) {
            // IOS
            builder.withDeviceIdfv(userInfo.getMuid().toStringUtf8());
        }
        // 其它设备信息处理
        builder.withDeviceType(deviceTypeHolder.get(deviceType)).withDeviceTypeOs(userInfo.getOsv())
                .withDevicePpi(userInfo.getDensity()).withdeviceMac(userInfo.getMac()).withDeviceBrand(userInfo.getDeviceBrand().toStringUtf8())
                .withDeviceModel(userInfo.getDeviceModel().toStringUtf8()).withDeviceWidth(userInfo.getScreenWidth()).withDeviceHeight(userInfo.getScreenHeight())
                .withDeviceImsi(carrierHolder.get(userInfo.getCarrier())).withDeviceNetwork(netHolder.get(userInfo.getConnectType())).withDeviceOs(osTypeHolder.get(osType))
                .withDeviceIp(userInfo.getIp()).withDeviceUa(URLEncoder.encode(userInfo.getUa()))
                .withDeviceOrientation(MeiShuEnumUtils.Orientation.ORIENTATION_PORTRAIT.getValue());

        if (deviceType==DeviceType.kPhone){
            builder.withIsMobile(1);
        }else if(deviceType==DeviceType.kPC){
            builder.withIsMobile(0);
        }

        BidRequest bidRequest = builder.build();
        QueryStringBuilder<BidRequest> queryStringBuilder = QueryStringBuilder.create(bidRequest);
        String queryString = queryStringBuilder.toQueryString();

        BiddingTracer.trace(biddingReq.getIsDebug(), "美数广告请求内容为：{}", queryString);
        return queryString;
    }

    @Override
    public String getRequestContentType() {
        return null;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();
        if (responseBody == null) {
            return null;
        }

        String resBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "美数广告请求响应内容：{}",resBody);
        BidResponse bidResponse = JSON.parseObject(resBody, BidResponse.class);
        if (bidResponse == null) {
            BiddingTracer.trace(isDebug, "请求美数广告无响应内容");
            return null;
        }

        List<AdMsg> adMsgs = new ArrayList<>();
        AdMsg adMsg = null;
        try {
            adMsg = buildAdMsg(bidResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (adMsg != null) {
            adMsgs.add(adMsg);
        }
        return adMsgs;
    }

    private AdMsg buildAdMsg(BidResponse bidResponse) throws Exception {
        AdMsg.Builder builder = AdMsg.newBuilder();

        builder.setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        if(StringUtils.isNoneBlank(bidResponse.getCid())){builder.setCrid(bidResponse.getCid());}

        String landUrl = "";
        if (bidResponse.getLoadUrl() != null && bidResponse.getLoadUrl().length > 0) {
            landUrl = bidResponse.getLoadUrl()[0];
        }
        builder.setLandUrl(landUrl);
        String title = bidResponse.getTitle()==null?"":bidResponse.getTitle();
        String desc = bidResponse.getContent()==null?"":bidResponse.getContent();
        builder.setTitle(ByteString.copyFromUtf8(title)).setDescription(desc);

        // 创意类型处理
        Integer creativeType = bidResponse.getCreativeType();
        if (creativeType == null || creativeType.equals(MeiShuEnumUtils.CreativeType.PIC.getValue())) {
            // 图片素材
            NativeAd.Builder nab = NativeAd.newBuilder();
            String icon = bidResponse.getIcon()==null?"":bidResponse.getIcon();
            nab.setThreadTitle(title).setThreadContent(desc).setUserPortrait(icon);

            String methodNameFormat = "setThreadPic%d";
            int imageIndex = 1;
            for (String image : bidResponse.getSrcUrls()) {
                MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), image);
                imageIndex++;
            }
            builder.setPicUrl(nab.getThreadPic1()).setNativeAd(nab.build());
        } else if (creativeType.equals(MeiShuEnumUtils.CreativeType.VIDEO.getValue())) {
            // Video素材
            String videoUrl = "";
            if (bidResponse.getSrcUrls() != null && bidResponse.getSrcUrls().length > 0) {
                videoUrl = bidResponse.getSrcUrls()[0];
            }
            builder.setCreativeType(CreativeType.kVideo).setAdDuration(bidResponse.getVideoDuration()).setPicUrl(videoUrl);
        }

        // 交互类型处理
        if (StringUtils.isNoneBlank(bidResponse.getDeepLink())) {
            // DeepLink处理
            builder.setLandUrl(bidResponse.getDeepLink()).setFallback(landUrl);
        }else if (bidResponse.getTargetType().equals(MeiShuEnumUtils.TargetType.DOWNLOAD.getValue())) {
            // 下载处理
            builder.setExtensionType(ExtensionType.kExtAndroid).setAppName(bidResponse.getAppName()).setPkgName(bidResponse.getPackageName());
        }

        // 检测处理
        builder.addAllImpMonitorUrl(Arrays.asList(bidResponse.getMonitorUrl())).addAllClkMonitorUrl(Arrays.asList(bidResponse.getClickUrl()));
        if(bidResponse.getDnStart()!=null && bidResponse.getDnStart().length>0){
            // 下载开始检测
            builder.addAllDlMonitorUrl(Arrays.asList(bidResponse.getDnStart()));
        }
        if (bidResponse.getDpFail()!=null && bidResponse.getDpFail().length>0){
            // dp 失败检测
            builder.addAllDpfMonitorUrl(Arrays.asList(bidResponse.getDpFail()));
        }
        if (bidResponse.getDpSucc()!=null && bidResponse.getDpSucc().length>0){
            // dp 成功检测
            builder.addAllDpsMonitorUrl(Arrays.asList(bidResponse.getDpSucc()));
        }
        return builder.build();
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }
}

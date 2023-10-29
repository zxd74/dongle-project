package com.iwanvi.adserv.ngx.router.broker.adwo;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
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
 * @date: 2019-06-04 18:32
 * @version: v1.0
 * @Description:
 */
public class AdwoDspBroker implements DspBroker {

    private static final String ADWO_DSP_ID = MinervaCfg.get().getConfigProperty("adwo.dspid");
    private static final Logger LOG = LoggerFactory.getLogger(AdwoDspBroker.class);
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

    private static final Map<ConnectType,Integer> connectTypeHolder = new ConcurrentHashMap<>();

    static{

    }

    @Override
    public String getDspId() {
        return ADWO_DSP_ID;
    }

    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {

        PosInfo posInfo = biddingReq.getPosInfoList().get(0);
        UserInfo userInfo = biddingReq.getUserInfo();
        CreativeType creativeType = posInfo.getCreativeType(0);


        BidRequest.Builder builder = BidRequest.builder();

        // 广告位ID映射处理
        SspAdPosition.AdPositionMapping adPositionMapping = RtbUtils.getMappingDspAdPosition(biddingReq,getDspId());
        builder.withPid(adPositionMapping.getDspAdPositionId());

        // 请求广告类型处理
        if (creativeType == CreativeType.kVideo){
            // 视频广告请求处理
            builder.withFormat(BidRequest.FormatType.VIDEO.getValue());
        }else{
            // Feed信息流广告请求处理
            builder.withFormat(BidRequest.FormatType.FEEDS.getValue());
            // Feed信息流对象 (大图采用映射尺寸，ICON默认80*80)
            BidRequest.Feed.Builder feed = BidRequest.Feed.builder().withImgWidth(adPositionMapping.getWidth()).withImgHeight(adPositionMapping.getHeight()).withIconWidth(80).withIconHeight(80);
            builder.withFeed(feed.build());
        }
        // 构建 BidRequest
        builder.withApp(buildApp(posInfo)).withDevice(buildDevice(posInfo,userInfo));

        BiddingTracer.trace(biddingReq.getIsDebug(), JSON.toJSONString(builder.build()));
        return JSON.toJSONBytes(builder.build());
    }

    /**
     * 设备构建
     * @param posInfo
     * @param userInfo
     * @return
     */
    private BidRequest.Device buildDevice(PosInfo posInfo, UserInfo userInfo) {
        ConnectType connectType = userInfo.getConnectType();
        OSType osType = userInfo.getOs();

        // 设别信息处理
        BidRequest.Device.Builder device = BidRequest.Device.builder();
        device.withIp(userInfo.getIp()).withDensity((float)userInfo.getDensity())
                .withUa(URLEncoder.encode(userInfo.getUa())).withOsv(userInfo.getOsv())
                .withManu(userInfo.getDeviceModel().toStringUtf8()).withBn(userInfo.getDeviceBrand().toStringUtf8())
                .withScreenWidth(userInfo.getScreenWidth()).withScreenHeight(userInfo.getScreenHeight());
        // 网络类型处理
        if (connectType == ConnectType.kWifi){
            device.withNet(BidRequest.NetType.WIFI.getValue());
        }else{
            device.withNet(BidRequest.NetType.NO_WIFI.getValue());
        }
        // 设别类型处理
        if (osType == OSType.kAndroid){
            //Android处理
            device.withImei(userInfo.getMuid().toStringUtf8()).withAndroidId(userInfo.getAdid());
        }else if(osType == OSType.kIOS){
            device.withIdfa(userInfo.getMuid().toStringUtf8()).withMac(userInfo.getMac());
        }
        return device.build();
    }

    /**
     * App 构建
     * @param posInfo
     * @return
     */
    private BidRequest.App buildApp(PosInfo posInfo) {
        // app信息处理
        BidRequest.App.Builder app = BidRequest.App.builder().withName(posInfo.getAppName()).withPackage(posInfo.getBundle()).withVersion(posInfo.getAppVersion());
        return app.build();
    }

    @Override
    public String getRequestContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();

        List<AdMsg> adList = new ArrayList<>();

        String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "安沃dsp平台广告响应：{}", respBody);
        BidResponse bidResponse = JSON.parseObject(respBody,BidResponse.class);
        if (bidResponse == null || bidResponse.equals("{}")||bidResponse.getResultcode() != 1){
            BiddingTracer.trace(isDebug, "安沃dsp返回广告为空");
            return null;
        }

        BidResponse.Ad ad = bidResponse.getAd();
        if (ad == null){
            BiddingTracer.trace(isDebug, "安沃 无广告响应");
            return null;
        }

        AdMsg adMsg = buildAdMsg(ad,biddingReq);
        if (adMsg!=null){
            adList.add(buildAdMsg(ad,biddingReq));
        }
        return adList;
    }

    /**
     * 广告处理
     * @param ad
     * @param biddingReq
     * @return
     */
    private AdMsg buildAdMsg(BidResponse.Ad ad, BiddingReq biddingReq) {

        CreativeType creativeType = biddingReq.getPosInfo(0).getCreativeType(0);

        AdMsg.Builder builder = AdMsg.newBuilder();
        builder.setCrid(String.valueOf(ad.getAdid())).setDspType(DspType.kDspTypePublic).setDspId(getDspId())
                .setLandUrl(ad.getTarget()).setBidPrice((int)(ad.getPushmoney().doubleValue()*100d))
                .setAdxBidPrice((int)(ad.getPushmoney().doubleValue()*100d))
                .setCreativeType( biddingReq.getPosInfo(0).getCreativeType(0)).setCostType(CostType.kCpm);

        if (ad.getActionType().equals(BidResponse.ActionType.DEEPLINK.getValue())){
            builder.setLandUrl(ad.getDeeplink()).setFallback(ad.getTarget());
        }else if (ad.getActionType().equals(BidResponse.ActionType.DOWNLOAD.getValue())){
            builder.setExtensionType(ExtensionType.kExtAndroid).setPkgName(ad.getPkName());
        }

        if (creativeType == CreativeType.kVideo){
            // 视频处理
            builder.setCreativeType(CreativeType.kVideo).setPicUrl(ad.getVideo().getUrl()).setAdDuration(ad.getVideo().getT());
        }else{
            NativeAd.Builder nab = NativeAd.newBuilder();
            BidResponse.Feed feed = ad.getFeeds();
            if (StringUtils.isNoneBlank(feed.getIco())){nab.setUserPortrait(feed.getIco());}
            if (StringUtils.isNoneBlank(feed.getTxt())){nab.setThreadTitle(feed.getTxt());}
            if (StringUtils.isNoneBlank(feed.getDesc())){nab.setThreadContent(feed.getDesc());}
            nab.setThreadPic1(feed.getImg());

            builder.setPicUrl(nab.getThreadPic1()).setNativeAd(nab.build());
        }

        // 监测地址处理
        builder.addAllImpMonitorUrl(Arrays.asList(ad.getShowBeacons())).addAllClkMonitorUrl(Arrays.asList(ad.getBeacons()));

        return builder.build();
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return null;
    }
}

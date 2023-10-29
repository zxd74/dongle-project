package com.iwanvi.adserv.ngx.router.broker.youdao;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.BiddingProto;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.CommonProto;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 郑晓东
 * @date: 2019-05-10 09:56
 * @version: v1.0
 * @Description: 有道请求和响应数据转换处理
 */
public class YouDaoDspBroker implements DspBroker {

    private static final Logger LOG = LoggerFactory.getLogger(YouDaoDspBroker.class);

    private static final Map<CommonProto.ConnectType, Integer> carriersHolder = new ConcurrentHashMap<>();

    static {
        carriersHolder.put(CommonProto.ConnectType.k2g, BidRequest.SubNetType.K_2G.getValue());
        carriersHolder.put(CommonProto.ConnectType.k3g, BidRequest.SubNetType.K_3G.getValue());
        carriersHolder.put(CommonProto.ConnectType.k4g, BidRequest.SubNetType.K_4G.getValue());
        carriersHolder.put(CommonProto.ConnectType.k5g, BidRequest.SubNetType.K_UNKNOWN.getValue());
        carriersHolder.put(CommonProto.ConnectType.kConnectTypeUnKnown, BidRequest.SubNetType.K_UNKNOWN.getValue());
    }

    @Override
    public String getDspId() {
        return MinervaCfg.get().getConfigProperty("youdao.dspid");
    }

    @Override
    public String getRequestContentType() {
        return null;
    }

    @Override
    public List<BiddingProto.AdMsg> toAdMsgs(BiddingProto.BiddingReq biddingReq, byte[] responseBody) {
        boolean isDebug = biddingReq.getIsDebug();
        List<BiddingProto.AdMsg> adMsgs = new ArrayList<>();

        if (responseBody == null) {
            return adMsgs;
        }

        String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
        BiddingTracer.trace(isDebug, "网易有道dsp平台广告响应：{}", respBody);

        List<BidResponse> responses = new ArrayList<>();
        if (StringUtils.isNoneBlank(respBody)) {
            if (respBody.startsWith("[")) {
                responses = JSON.parseArray(respBody, BidResponse.class);
            } else {
                BidResponse bidResponse = JSON.parseObject(respBody, BidResponse.class);
                if (bidResponse != null) {
                    responses.add(bidResponse);
                }
            }
        }

        if (CollectionUtils.isEmpty(responses)) {
            return adMsgs;
        }

        for (BidResponse bidResponse : responses) {
            adMsgs.add(toAdMsg(biddingReq, bidResponse));
        }
        return adMsgs;
    }

    private BiddingProto.AdMsg toAdMsg(BiddingProto.BiddingReq biddingReq, BidResponse bidResponse) {
        BiddingProto.AdMsg.Builder builder = BiddingProto.AdMsg.newBuilder().setDspId(getDspId()).setDspType(CommonProto.DspType.kDspTypePublic).setCostType(CommonProto.CostType.kCpm);

        builder.setId(bidResponse.getCreativeId().toString()).setLandUrl(bidResponse.getClk()).setDspId(getDspId()).setDspType(CommonProto.DspType.kDspTypePublic);

        // 其它信息处理
        if (StringUtils.isNoneBlank(bidResponse.getTitle())) {
            builder.setTitle(ByteString.copyFromUtf8(bidResponse.getTitle()));
        }
        if (StringUtils.isNoneBlank(bidResponse.getMainimage())) {
            builder.setPicUrl(bidResponse.getMainimage());
        }
        if (StringUtils.isNoneBlank(bidResponse.getCoverImage())) {
            builder.setPicUrl(bidResponse.getCoverImage());
        }

        if (bidResponse.getIconimage() != null || bidResponse.getMainimage() != null || bidResponse.getText() != null) {
            builder.setNativeAd(builderNativeAd(biddingReq, builder, bidResponse));
        }

        Integer adType = bidResponse.getYdAdType();
        // DeepLink 处理
        if (StringUtils.isNoneBlank(bidResponse.getDeeplink())) {
            builder.setLandUrl(bidResponse.getDeeplink()).setFallback(bidResponse.getClk());
        }else if (adType.equals(BidResponse.AdType.DOWNLOAD.getValue())) {
            // 下载类广告处理
            builder.setExtensionType(CommonProto.ExtensionType.kExtAndroid);
            // 如果包应用名存在则填充应用名
            if (StringUtils.isNoneBlank(bidResponse.getAppName())) {
                builder.setAppName(bidResponse.getAppName());
            }
            // 如果包名存在则填充包名
            if (StringUtils.isNoneBlank(bidResponse.getPackageName())) {
                builder.setPkgName(bidResponse.getPackageName());
            }
        }

        // 数据上报url处理
        builder.addAllClkMonitorUrl(Arrays.asList(bidResponse.getClktrackers())).addAllImpMonitorUrl(Arrays.asList(bidResponse.getImptracker()));

        return builder.build();
    }

    private AdModelsProto.NativeAd builderNativeAd(BiddingProto.BiddingReq biddingReq, BiddingProto.AdMsg.Builder adb, BidResponse bidResponse) {
        AdModelsProto.NativeAd.Builder builder = AdModelsProto.NativeAd.newBuilder();

        if (bidResponse.getAdCat() == null) {
            // 基本广告处理
            builderBaseInfo(builder, bidResponse);
        } else if (bidResponse.getAdCat().equals(BidResponse.AdCat.REWARD_VIDEO.getValue())) {
            builderVideo(biddingReq, adb, bidResponse);
        } else if (bidResponse.getAdCat().equals(BidResponse.AdCat.NATIVE_VIDEO.getValue())) {
            builderVideo(biddingReq, adb, bidResponse);
        } else if (bidResponse.getAdCat().equals(BidResponse.AdCat.NATIVE.getValue())) {
            builderBaseInfo(builder, bidResponse);
        }

        return builder.build();
    }

    private void builderVideo(BiddingReq biddingReq, BiddingProto.AdMsg.Builder adb, BidResponse bidResponse) {
        adb.setCreativeType(CommonProto.CreativeType.kVideo).setPicUrl(bidResponse.getVideoUrl());
        try {
            if (StringUtils.isNoneBlank(bidResponse.getVideoDuration())) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
                Date start = sdf.parse("00:00:00.000");

                Date date = sdf.parse(bidResponse.getVideoDuration());
                adb.setAdDuration((int) (date.getTime() - start.getTime()) / 1000);
            }
        } catch (ParseException e) {
            BiddingTracer.trace(biddingReq.getIsDebug(), "视频时长转换出错：" + bidResponse.getVideoDuration());
        }
    }

    private void builderBaseInfo(AdModelsProto.NativeAd.Builder builder, BidResponse bidResponse) {
        if (StringUtils.isNoneBlank(bidResponse.getTitle())) {
            builder.setThreadTitle(bidResponse.getTitle());
        }
        if (StringUtils.isNoneBlank(bidResponse.getIconimage())) {
            builder.setUserPortrait(bidResponse.getIconimage());
        }
        if (StringUtils.isNoneBlank(bidResponse.getMainimage())) {
            builder.setThreadPic1(bidResponse.getMainimage());
        }
        if (StringUtils.isNoneBlank(bidResponse.getCoverImage())) {
            builder.setThreadPic1(bidResponse.getCoverImage());
        }
        if (StringUtils.isNoneBlank(bidResponse.getText())) {
            builder.setButtonText(bidResponse.getText()).setThreadContent(bidResponse.getText());
        }
        if (StringUtils.isNoneBlank(bidResponse.getCtaText())) {
            builder.setButtonText(bidResponse.getCtaText());
        }

        // 组图处理
        if (StringUtils.isNoneBlank(bidResponse.getMainimage1())) {
            builder.setThreadPic1(bidResponse.getMainimage1());
        }
        if (StringUtils.isNoneBlank(bidResponse.getMainimage2())) {
            builder.setThreadPic2(bidResponse.getMainimage2());
        }
        if (StringUtils.isNoneBlank(bidResponse.getMainimage3())) {
            builder.setThreadPic3(bidResponse.getMainimage3());
        }
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

        BidRequest bidRequest = bidRequest(biddingReq);

        QueryStringBuilder queryStringBuilder = QueryStringBuilder.create(bidRequest);
        String queryString = queryStringBuilder.toQueryString();

        BiddingTracer.trace(biddingReq.getIsDebug(),"有道：{}，广告请求：{}",getDspId(),queryString);
        return queryString;
    }

    private BidRequest bidRequest(BiddingReq biddingReq) {
        BiddingProto.PosInfo posInfo = biddingReq.getPosInfoList().get(0);
        BiddingProto.UserInfo userInfo = biddingReq.getUserInfo();

        BidRequest.Builder builder = BidRequest.builder();

        // 映射广告位处理
        String youDaoPosId = RtbUtils.getMappingDspAdSlotId(biddingReq, getDspId());
        if (youDaoPosId == null) {
            throw new AdNgxException("没有匹配的有道dsp平台广告位映射, adslotId: " + posInfo.getPosId().toStringUtf8());
        }
        builder.withId(youDaoPosId).withAv(posInfo.getAppVersion());

        // 网络连接类型处理
        CommonProto.ConnectType connectType = userInfo.getConnectType();
        CommonProto.Carrier carrier = userInfo.getCarrier();
        if (connectType == CommonProto.ConnectType.kConnectTypeUnKnown || carrier == CommonProto.Carrier.kCarrierUnKnown) {
            // 未知类型
            builder.withCt(BidRequest.NetType.UNKNOWN.getValue());
        } else if (connectType == CommonProto.ConnectType.kWifi) {
            builder.withCt(BidRequest.NetType.WIFI.getValue());
        } else if (carrier == CommonProto.Carrier.kMobile || carrier == CommonProto.Carrier.kTelecom || carrier == CommonProto.Carrier.kUnicom) {
            builder.withCt(BidRequest.NetType.MOBILE.getValue());
            // 子网连接类型处理
            builder.withDct(carriersHolder.get(connectType));
        }

        // 设备标识处理,MD5采用 DigestU-tils.md5Hex（uid、auidmd5、imei、imeimd5、aaid至少一个）
        builder.withIMEI(userInfo.getMuid().toStringUtf8().toUpperCase());

        builder.withRip(userInfo.getIp());

        return builder.build();
    }
}

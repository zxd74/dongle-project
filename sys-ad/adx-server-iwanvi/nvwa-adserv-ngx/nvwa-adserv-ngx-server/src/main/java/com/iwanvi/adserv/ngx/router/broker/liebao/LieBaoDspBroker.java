package com.iwanvi.adserv.ngx.router.broker.liebao;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.MD5Utils;
import com.iwanvi.adserv.ngx.util.Xml2JavaUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.*;
import com.iwanvi.nvwa.proto.BiddingProto.*;
import com.iwanvi.nvwa.proto.CommonProto.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: 郑晓东
 * @date: 2019-06-06 15:15
 * @version: v1.0
 * @Description: 猎豹DspBroker处理类
 */
public class LieBaoDspBroker implements DspBroker {

    private static final String DSP_ID = MinervaCfg.get().getConfigProperty("liebao.dspid");
    private static final Logger LOG = LoggerFactory.getLogger(LieBaoDspBroker.class);
    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    private static final String[] IMG_MINES = {"JPG", "PNG"};
    private static final String[] VIDEO_MINES = {"video/mp4", "video/x-ms-wmv"};
    private static final Integer[] VAST_TYPE = {BidRequest.VastType.VAST1.getValue(),
            BidRequest.VastType.VAST2.getValue(),
            BidRequest.VastType.VAST3.getValue(),
            BidRequest.VastType.VAST1_WRAPPER.getValue(),
            BidRequest.VastType.VAST2_WRAPPER.getValue(),
            BidRequest.VastType.VAST3_WRAPPER.getValue()};
    private static final Map<DeviceType, Integer> deviceTypeHolder = new ConcurrentHashMap<>();
    private static final Map<ConnectType, Integer> connectTypeHolder = new ConcurrentHashMap<>();
    private static final Map<Carrier, String> carrierHolder = new ConcurrentHashMap<>();
    private Dsp dsp;

    static {
        deviceTypeHolder.put(DeviceType.kDeviceTypeUnKnown, BidRequest.DeviceType.UNKNOWN.getValue());
        deviceTypeHolder.put(DeviceType.kPhone, BidRequest.DeviceType.PHONE.getValue());
        deviceTypeHolder.put(DeviceType.kPad, BidRequest.DeviceType.TABLET.getValue());
        deviceTypeHolder.put(DeviceType.kPC, BidRequest.DeviceType.UNKNOWN.getValue());
        deviceTypeHolder.put(DeviceType.kTV, BidRequest.DeviceType.UNKNOWN.getValue());

        connectTypeHolder.put(ConnectType.k5g, BidRequest.ConnectionType.UNKNOWN_G.getValue());
        connectTypeHolder.put(ConnectType.k4g, BidRequest.ConnectionType.K4G.getValue());
        connectTypeHolder.put(ConnectType.k3g, BidRequest.ConnectionType.K3G.getValue());
        connectTypeHolder.put(ConnectType.k2g, BidRequest.ConnectionType.K2G.getValue());
        connectTypeHolder.put(ConnectType.kConnectTypeUnKnown, BidRequest.ConnectionType.UNKNOWN.getValue());

        carrierHolder.put(Carrier.kCarrierUnKnown,"46004");
        carrierHolder.put(Carrier.kTelecom,"46003");
        carrierHolder.put(Carrier.kUnicom,"46001");
        carrierHolder.put(Carrier.kMobile,"46000");
    }

    @Override
    public void setDsp(Dsp dsp) {
        this.dsp = dsp;
    }

    @Override
    public String getDspId() {
        return DSP_ID;
    }

    @Override
    public byte[] toDspRequest(BiddingReq biddingReq) {
        BidRequest.Builder builder = BidRequest.builder();
        builder.withId(biddingReq.getId()).withImps(buildImps(biddingReq)).withApp(buildApp(biddingReq)).withDevice(buildDevice(biddingReq)).withTMax(250).withUser(buildUser(biddingReq));

        // NOTIFY: 暂不处理User用户信息

        BiddingTracer.trace(biddingReq.getIsDebug(), JSON.toJSONString(builder.build()));
        return JSON.toJSONBytes(builder.build());
    }

    private BidRequest.Imp[] buildImps(BiddingReq biddingReq) {
        // 定义imps容量
        BidRequest.Imp[] imps = new BidRequest.Imp[biddingReq.getPosInfoCount()];

        // 构建imps
        for (int i = 0; i < imps.length; i++) {
            PosInfo posInfo = biddingReq.getPosInfo(i);
            CreativeType creativeType = posInfo.getCreativeType(0);
            CreativeType adType = posInfo.getAdType();

            BidRequest.Imp.Builder impBuilder = BidRequest.Imp.builder();
            SspAdPosition.AdPositionMapping positionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());
            if (positionMapping == null) {
                throw new AdNgxException("找不到猎豹广告位映射信息, posId: " + posInfo.getId());
            }
            double bidfloor = RtbUtils.getBidfloor(dsp, biddingReq);
            impBuilder.withId(UUID.randomUUID().toString()).withTagId(posInfo.getPosId().toStringUtf8()).withPosId(positionMapping.getDspAdPositionId())
                    .withBidfloor((float) (bidfloor/100)).withBidfloorCur(BidRequest.CurType.CNY.getValue());

            // 广告创意类型处理
            // NOTIFY:  开屏暂对应猎豹的插屏广告，以下开屏处理代码不要清理，后续开屏对应正常可打开注释
            if (adType == CreativeType.kFirstScreen) {
                // Banner广告请求（开屏）
                BidRequest.Banner.Builder builder = BidRequest.Banner.builder();
                builder.withWidth(positionMapping.getWidth()).withHeight(positionMapping.getHeight());
                impBuilder.withBanner(builder.build());
            }
            if (creativeType == CreativeType.kVideo) {
                // 视频广告（普通视频）处理
                BidRequest.Video.Builder video = BidRequest.Video.builder();
                video.withVideoType(BidRequest.VideoType.COMMON.getValue()).withMimes(VIDEO_MINES)
                        .withProtocols(VAST_TYPE).withWidth(posInfo.getWidth()).withHeight(posInfo.getHeight())
                        .withSkip(BidRequest.Skip.YES.getValue()).withLinearity(BidRequest.LinearityType.LINEAR_IN_STREAM.getValue());
                impBuilder.withVideo(video.build());
            } else if (creativeType == CreativeType.kNative || creativeType == CreativeType.kPic) {
                // 原生广告处理 （NOTIFY：以下内容禁止更改，影响广告响应处理！！！）
                BidRequest.Asset.Builder imgAsset = BidRequest.Asset.builder().withRequired(1).withId(0).withImg(BidRequest.Img.builder()
                        .withMimes(IMG_MINES).withWidth(positionMapping.getWidth()).withHeight(positionMapping.getHeight()).withType(BidRequest.ImgType.MAIN_IMG.getValue()).build());
                BidRequest.Asset.Builder titleAsset = BidRequest.Asset.builder().withRequired(1).withId(1).withTitle(new BidRequest.Title(12));
                BidRequest.Asset.Builder dataAsset = BidRequest.Asset.builder().withRequired(1).withId(2).withData(BidRequest.Data.builder().withLen(20).build());
                BidRequest.Asset[] assets = {imgAsset.build(), titleAsset.build(), dataAsset.build()};
                BidRequest.Native.Builder nat = BidRequest.Native.builder().withRequest(JSON.toJSONString(new BidRequest.NativeRequst(new BidRequest.NativeTopLevel(assets))));
                impBuilder.withNative(nat.build());
            }
            // NOTIFY: 暂时无私有交易

            imps[i] = impBuilder.build();
        }
        return imps;
    }

    private BidRequest.App buildApp(BiddingReq biddingReq) {
        BidRequest.App.Builder app = BidRequest.App.builder();
        PosInfo posInfo = biddingReq.getPosInfo(0);
        app.withId(posInfo.getAppId().toStringUtf8()).withName(posInfo.getAppName()).withBundle(posInfo.getBundle()).withVer(posInfo.getAppVersion());
        return app.build();
    }

    private BidRequest.Device buildDevice(BiddingReq biddingReq) {
        BidRequest.Device.Builder device = BidRequest.Device.builder();
        UserInfo userInfo = biddingReq.getUserInfo();
        DeviceType deviceType = userInfo.getDeviceType();
        ConnectType connectType = userInfo.getConnectType();
        OSType osType = userInfo.getOs();
        Carrier carrier = userInfo.getCarrier();

        device.withUa(userInfo.getUa()).withIp(userInfo.getIp()).withDeviceType(deviceTypeHolder.get(deviceType)).withModel(userInfo.getDeviceModel().toStringUtf8())
                .withWidth(userInfo.getScreenWidth()).withHeight(userInfo.getScreenHeight()).withConnectionType(connectTypeHolder.get(connectType)).withCarrier(carrierHolder.get(carrier));

        // 操作系统处理
        if (osType == OSType.kAndroid) {
            device.withOs("android").withOsv(userInfo.getOsv()).withDpIdMD5(MD5Utils.md5Hex(userInfo.getAdid())).withImei(userInfo.getMuid().toStringUtf8());
        } else if (osType == OSType.kIOS) {
            device.withOs("ios").withOsv(userInfo.getOsv()).withDpIdMD5(MD5Utils.md5Hex(userInfo.getMuid().toStringUtf8())).withIdfa(userInfo.getMuid().toStringUtf8());
        }

        // 暂不处理GEO地理未知信息
        return device.build();
    }

    private BidRequest.User buildUser(BiddingReq biddingReq) {
        return null;
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
        BiddingTracer.trace(isDebug, "猎豹 dsp平台广告响应：{}", respBody);
        BidResponse bidResponse = JSON.parseObject(respBody, BidResponse.class);

        if (bidResponse == null || bidResponse.getSeatBid() == null || bidResponse.getSeatBid().length == 0) {
            BiddingTracer.trace(isDebug, "猎豹 dsp平台无广告响应");
            return null;
        }
        // NOTIFY: 目前SeatBid只支持一个
        for (BidResponse.Bid bid : bidResponse.getSeatBid()[0].getBid()) {
            AdMsg adMsg = buildAdMsg(bid, biddingReq);
            if (adMsg != null) {
                adList.add(buildAdMsg(bid, biddingReq));
            }
        }
        return adList;
    }

    /**
     * @param bid
     * @param biddingReq
     * @return
     */
    private AdMsg buildAdMsg(BidResponse.Bid bid, BiddingReq biddingReq) {
        AdMsg.Builder builder = AdMsg.newBuilder();
        builder.setBidPrice((int) (bid.getPrice() * 100)).setAdxBidPrice((int) (bid.getPrice() * 100)).setCrid(bid.getAdid()).setDspId(getDspId()).setDspType(DspType.kDspTypePublic).setCostType(CostType.kCpm);

        CreativeType creativeType = biddingReq.getPosInfo(0).getCreativeType(0);

        // 广告类型处理
        if (creativeType == CreativeType.kVideo) {
            // 视频广告处理
            // 字符串中存在\n换行符，替换去除
            try {
            VAST video = Xml2JavaUtils.converyToBean(bid.getAdm().replace("\n", ""), VAST.class);

                VAST.Ad.InLine.Creatives.Creative.Linear linear = video.getAd().get(0).getInLine().get(0).getCreatives().get(0).getCreative().get(0).getLinear().get(0);
                if (StringUtils.isNoneBlank(linear.getDuration())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    Date start = sdf.parse("00:00:00");
                    Date date = sdf.parse(linear.getDuration());
                    builder.setAdDuration((int) (date.getTime() - start.getTime()) / 1000);
                }

                // 字符串中存在空格，trim处理
                List<String> list = new ArrayList<>();
                for (VAST.Ad.InLine.Creatives.Creative.Linear.VideoClicks clicks : linear.getVideoClicks()) {
                    list.add(clicks.getClickTracking().trim());
                }
                builder.setPicUrl(linear.getMediaFiles().get(0).getMediaFile().get(0).getValue().trim()).setCreativeType(CreativeType.kVideo).addImpMonitorUrl(video.getAd().get(0).getInLine().get(0).getImpression().trim()).addAllClkMonitorUrl(list);
            } catch (Exception ex) {
                LOG.error("猎豹视频广告处理异常：", ex);
            }
        } else {
            BidResponse.Adm adm = JSON.parseObject(bid.getAdm(), BidResponse.Adm.class);
            if (adm != null && adm.getBanner() != null) {
                // Banner 广告处理
                builder.setPicUrl(adm.getBanner().getImg().getUrl()).addAllImpMonitorUrl(builderMacros(adm.getBanner().getImpTrackers(), bid.getAdid())).setLandUrl(adm.getBanner().getLink().getUrl()).addAllClkMonitorUrl(builderMacros(adm.getBanner().getImpTrackers(), bid.getAdid()));
            } else if (adm != null && adm.getNat() != null) {
                // 原生广告响应处理
                BidResponse.Native nat = adm.getNat();
                builder.addAllImpMonitorUrl(builderMacros(nat.getImpTrackers(), bid.getAdid())).addAllClkMonitorUrl(builderMacros(nat.getLink().getClickTrackers(), bid.getAdid()));

                buildLink(nat.getLink(), builder);

                NativeAd.Builder nab = NativeAd.newBuilder();
                // 广告素材处理
                List<BidResponse.Asset> assets = JSON.parseArray(nat.getAssets(), BidResponse.Asset.class);
                String methodNameFormat = "setThreadPic%d";
                int imageIndex = 1;

                for (BidResponse.Asset asset : assets) {
                    if (asset.getImg() != null) {
                        try {
                            MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), asset.getImg().getUrl());
                            imageIndex++;
                            continue;
                        } catch (Exception ex) {
                            LOG.error("猎豹原生广告图片处理异常：", ex);
                        }
                    }
                    if (asset.getTitle() != null) {
                        nab.setThreadTitle(asset.getTitle().getText());
                        continue;
                    }
                    if (asset.getData() != null) {
                        nab.setThreadContent(asset.getData().getValue());
                        continue;
                    }
                    if (asset.getLink() != null) {
                        nab.setThreadContent(asset.getData().getValue());
                        continue;
                    }
                    buildLink(asset.getLink(), builder);
                }
                builder.setPicUrl(nab.getThreadPic1()).setNativeAd(nab.build());
            }
            if (!builder.hasFallback() && StringUtils.isNoneBlank(bid.getBundle())) {
                // 当包名存在时，按下载处理
                builder.setExtensionType(ExtensionType.kExtAndroid).setPkgName(bid.getBundle());
            }
        }

        return builder.build();
    }

    /**
     * 宏替换处理
     *
     * @param urls
     * @return
     */
    private List<String> builderMacros(String[] urls, String adid) {
        return Arrays.stream(urls).map(e -> e.replace("${AUCTION_PRICE}", "{AUCTION_PRICE}")
                .replace("${AUCTION_AD_ID}", adid)
                .replace("${AUCTION_BID_ID}", "{AUCTION_BID_ID}")
                .replace("${AUCTION_ID}", "{AUCTION_ID}")
                .replace("${AUCTION_CURRENCY}", BidRequest.CurType.CNY.getValue()))
                .collect(Collectors.toList());
    }

    private void buildLink(BidResponse.Link link, AdMsg.Builder builder) {
        builder.setLandUrl(link.getUrl());
        if (StringUtils.isNoneBlank(link.getFallback())) {
            // DeepLink处理
            builder.setLandUrl(link.getFallback()).setFallback(link.getLandUrl());
        }
    }

    @Override
    public WinPriceCodec getWinPriceCodec() {
        return new LieBaoWinPriceCodec();
    }

}

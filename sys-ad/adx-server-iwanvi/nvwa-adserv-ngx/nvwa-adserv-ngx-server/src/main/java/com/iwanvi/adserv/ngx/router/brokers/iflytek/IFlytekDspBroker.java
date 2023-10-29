package com.iwanvi.adserv.ngx.router.brokers.iflytek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.brokers.iflytek.BidRequest.App;
import com.iwanvi.adserv.ngx.router.brokers.iflytek.BidRequest.Device;
import com.iwanvi.adserv.ngx.router.brokers.iflytek.BidRequest.Imp;
import com.iwanvi.adserv.ngx.router.brokers.iflytek.BidResponse.Ad;
import com.iwanvi.adserv.ngx.router.brokers.iflytek.BidResponse.Image;
import com.iwanvi.adserv.ngx.router.brokers.iflytek.BidResponse.Monitor;
import com.iwanvi.adserv.ngx.router.brokers.iflytek.BidResponse.Video;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition.AdPositionMapping;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.DspType;
import com.iwanvi.nvwa.proto.CommonProto.ExtensionType;
import com.iwanvi.nvwa.proto.CommonProto.OSType;

/**
 * @author wangweiping
 *
 */
public class IFlytekDspBroker implements DspBroker {
	static final Logger LOG = LoggerFactory.getLogger(IFlytekDspBroker.class);
	private Dsp dsp;

	private static final String CONTENT_TYPE = "application/json; charset=UTF-8";
	private static final String DEFAULT_AD_ICON = "http://iflyad.bj.openstorage.cn/voicestatic/common/dsp_logo250_250.png";
	private static final Map<Carrier, Integer> carriersHolder = new ConcurrentHashMap<>();
	private static final Map<ConnectType, Integer> connectTypesHolder = new ConcurrentHashMap<>();

	static {
		carriersHolder.put(Carrier.kMobile, 1);
		carriersHolder.put(Carrier.kUnicom, 2);
		carriersHolder.put(Carrier.kTelecom, 3);
		carriersHolder.put(Carrier.kCarrierUnKnown, 0);

		connectTypesHolder.put(ConnectType.k2g, 4);
		connectTypesHolder.put(ConnectType.k3g, 5);
		connectTypesHolder.put(ConnectType.k4g, 6);
		connectTypesHolder.put(ConnectType.k5g, 7);
		connectTypesHolder.put(ConnectType.kWifi, 2);
		connectTypesHolder.put(ConnectType.kConnectTypeUnKnown, 0);
	}

	@Override
	public void setDsp(Dsp dsp) {
		this.dsp = dsp;
	}

	@Override
	public String getDspId() {
		return MinervaCfg.get().getConfigProperty("iflytek.dspid");
	}

	@Override
	public byte[] toDspRequest(BiddingReq biddingReq) {
		BidRequest.Builder brb = BidRequest.builder();
		PosInfo posInfo = biddingReq.getPosInfo(0);

		List<Imp> imps = buildImps(biddingReq);

		brb.withId(biddingReq.getId()).withImps(imps).withDevice(buildDevice(biddingReq))
				.withApp(App.builder().withAppName(posInfo.getAppName()).withAppVer(posInfo.getAppVersion()).build());

		String reqBody = JSON.toJSONString(brb.build());
		BiddingTracer.trace(biddingReq.getIsDebug(), "讯飞广告平台请求： {}", reqBody);

		return reqBody.getBytes(Charsets.UTF_8);
	}

	private Device buildDevice(BiddingReq biddingReq) {
		UserInfo userInfo = biddingReq.getUserInfo();

		Carrier carrier = userInfo.getCarrier();
		OSType os = userInfo.getOs();
		ConnectType connectType = userInfo.getConnectType();

		Device.Builder deviceBuilder = Device.builder().withDeviceType(0);

		if (os == OSType.kAndroid) {
			deviceBuilder.withImei(userInfo.getMuid().toStringUtf8()).withOs(0);
		}
		if (os == OSType.kIOS) {
			deviceBuilder.withIdfa(userInfo.getMuid().toStringUtf8()).withOs(1);
		}

		deviceBuilder.withCarrier(carriersHolder.get(carrier));
		deviceBuilder.withNet(connectTypesHolder.get(connectType));
		deviceBuilder.withIp(userInfo.getIp()).withMac("02:00:00:00:00:00")
				.withAdidMd5(DigestUtils.md5Hex(userInfo.getAdid())).withAdid(userInfo.getAdid())
				.withUa(org.apache.commons.lang3.StringUtils.defaultString(userInfo.getUa(), Constants.DEFAULT_UA))
				.withDvw(userInfo.getScreenWidth()).withDvh(userInfo.getScreenHeight())
				.withMake(userInfo.getDeviceBrand().toStringUtf8()).withModel(userInfo.getDeviceModel().toStringUtf8())
				.withOsv(userInfo.getOsv());

		return deviceBuilder.build();
	}

	private List<Imp> buildImps(BiddingReq biddingReq) {
		PosInfo posInfo = biddingReq.getPosInfo(0);
		List<Imp> imps = new ArrayList<BidRequest.Imp>();

		AdPositionMapping adPositionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());
		if (adPositionMapping == null) {
			throw new AdNgxException("没有匹配的dsp平台广告位映射, adslotId: " + posInfo.getPosId().toStringUtf8());
		}

		String iflytekAdUnitId = adPositionMapping.getDspAdPositionId();
		if (iflytekAdUnitId == null) {
			throw new AdNgxException("没有匹配的dsp平台广告位映射, adslotId: " + posInfo.getPosId().toStringUtf8());
		}
		BiddingTracer.trace(biddingReq.getIsDebug(), "匹配讯飞广告位: {}, 平台广告位id: {}", iflytekAdUnitId,
				posInfo.getPosId().toStringUtf8());
		double bidfloor = RtbUtils.getBidfloor(dsp, biddingReq);

		double iflytekBidfloor = (double) Math.round((bidfloor / 100) * 100) / 100;

		Imp.Builder ib = Imp.builder().withAdunitId(iflytekAdUnitId).withBidfloor(iflytekBidfloor)
				.withAdh(adPositionMapping.getHeight()).withAdw(adPositionMapping.getWidth())
				.withDpSupportStatus(new Integer[] { 1 }).withSecure(3);
		imps.add(ib.build());

		return imps;
	}

	@Override
	public String getRequestContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] responseBody) {
		List<AdMsg> adMsgs = new ArrayList<>();

		if (responseBody == null) {
			return adMsgs;
		}

		String respBody = StringUtils.toEncodedString(responseBody, Charsets.UTF_8);
		BiddingTracer.trace(biddingReq.getIsDebug(), "讯飞dsp平台广告响应：{}", respBody);
		BidResponse bidResponse = JSON.parseObject(respBody, BidResponse.class);
		List<Ad> ads = bidResponse.getAds();
		if (CollectionUtils.isEmpty(ads))
			return adMsgs;

		for (Ad ad : ads) {
			AdMsg adMsg = toAdMsg(biddingReq, ad);
			if (adMsg != null)
				adMsgs.add(adMsg);

		}
		return adMsgs;
	}

	private AdMsg toAdMsg(BiddingReq biddingReq, Ad ad) {
		int price = (int) (ad.getPrice().doubleValue() * 100);
		AdMsg.Builder builder = AdMsg.newBuilder().setAdxBidPrice(price).setBidPrice(price).setPaidCpc(price)
				.setCostType(CostType.kCpm).setDspId(getDspId()).setDspType(DspType.kDspTypePublic)
				.setLandUrl(ad.getLanding());
		if (StringUtils.isNotBlank(ad.getCreativeId())) {
			builder.setCrid(ad.getCreativeId());
		}

		Video video = ad.getVideo();
		if (video != null && biddingReq.getPosInfo(0).getCreativeType(0) == CreativeType.kVideo) {
			builder.setPicUrl(video.getUrl());
		}

		NativeAd.Builder nativeAdBuilder = NativeAd.newBuilder();
		Image image = ad.getImg();
		if (image != null && ad.getImg().getUrl() != null) {
			builder.setPicUrl(ad.getImg().getUrl());
			nativeAdBuilder.setThreadPic1(ad.getImg().getUrl());
		}

		Image image1 = ad.getImg1();
		Image image2 = ad.getImg2();
		Image image3 = ad.getImg3();

		if (image1 != null && image1.getUrl() != null)
			nativeAdBuilder.setThreadPic1(image1.getUrl());
		if (image2 != null && image2.getUrl() != null)
			nativeAdBuilder.setThreadPic2(image2.getUrl());
		if (image3 != null && image3.getUrl() != null)
			nativeAdBuilder.setThreadPic3(image3.getUrl());

		if (StringUtils.isNotBlank(ad.getTitle()))
			nativeAdBuilder.setThreadTitle(ad.getTitle());
		if (ad.getIcon() != null && ad.getIcon().getUrl() != null)
			nativeAdBuilder.setUserPortrait(ad.getIcon().getUrl());
		if (StringUtils.isNotBlank(ad.getDesc()))
			nativeAdBuilder.setThreadContent(ad.getDesc());

		if (StringUtils.isNotBlank(ad.getBrand()))
			nativeAdBuilder.setUserName(ad.getBrand());
		if (StringUtils.isNotBlank(ad.getCtatext()))
			nativeAdBuilder.setThreadBtnText(ad.getCtatext());

		if (image == null || image.getUrl() == null) {
			if (image1 != null)
				builder.setPicUrl(image1.getUrl());
		}
		String appDownloadUrl = ad.getAppDownloadUrl();

		Integer actionType = ad.getActionType();
		if (actionType != null && actionType == 3) {
			builder.setExtensionType(ExtensionType.kExtAndroid);
		}

		if (StringUtils.isNotBlank(appDownloadUrl)) {
			builder.setLandUrl(appDownloadUrl);
			builder.setFallback(ad.getLanding());
			builder.setAppName(ad.getApp_name());
		}
		if (StringUtils.isNotBlank(ad.getDeeplink())) {
			builder.clearExtensionType();
			builder.setLandUrl(ad.getDeeplink());
			builder.setFallback(ad.getLanding());
		}
		Monitor monitor = ad.getMonitor();
		if (monitor != null) {
			List<String> imps = monitor.getImpressUrls().stream()
					.map(e -> e.replace("${AUCTION_PRICE}", "{AUCTION_PRICE}")).collect(Collectors.toList());
			builder.addAllImpMonitorUrl(imps);
			builder.addAllClkMonitorUrl(monitor.getClickUrls());
		}

		if (StringUtils.isBlank(nativeAdBuilder.getUserPortrait())) {
			nativeAdBuilder.setUserPortrait(DEFAULT_AD_ICON);
		}
		builder.setNativeAd(nativeAdBuilder);
		return builder.build();
	}

	@Override
	public WinPriceCodec getWinPriceCodec() {
		return new IflytekWinPriceCodec();
	}

}

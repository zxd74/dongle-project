/**
 * 
 */
package com.iwanvi.adserv.ngx.router.broker.inmobi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.broker.inmobi.BidRequest.App;
import com.iwanvi.adserv.ngx.router.broker.inmobi.BidRequest.Device;
import com.iwanvi.adserv.ngx.router.broker.inmobi.BidRequest.Geo;
import com.iwanvi.adserv.ngx.router.broker.inmobi.BidRequest.Imp;
import com.iwanvi.adserv.ngx.router.broker.inmobi.BidRequest.Imp.Banner;
import com.iwanvi.adserv.ngx.router.broker.inmobi.BidRequest.Imp.Native;
import com.iwanvi.adserv.ngx.router.broker.inmobi.BidResponse.Ad;
import com.iwanvi.adserv.ngx.router.broker.inmobi.BidResponse.Ad.Content.Image;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition.AdPositionMapping;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
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
public class InMobiDspBroker implements DspBroker {
	private static final String INMOBI_DSPID = MinervaCfg.get().getConfigProperty("inmobi.dspid");
	private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

	@Override
	public String getDspId() {
		return INMOBI_DSPID;
	}

	@Override
	public String getRequestContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] respBody) {
		List<AdMsg> adMsgs = new ArrayList<>();
		try {
			if (respBody == null)
				return null;
			boolean isDebug = biddingReq.getIsDebug();

			String strJSON = new String(respBody);
			BiddingTracer.trace(isDebug, "InMobi平台广告响应：{}", strJSON);
			BidResponse bidResponse = JSON.parseObject(strJSON, BidResponse.class);
			if (bidResponse == null)
				return null;
			List<Ad> ads = bidResponse.getAds();
			if (CollectionUtils.isEmpty(ads))
				return null;

			ads.forEach(ad -> {
				AdMsg adMsg = toAdMsg(biddingReq, ad);
				if (adMsg != null)
					adMsgs.add(adMsg);
			});
			return adMsgs;
		} catch (Throwable ex) {
			throw new AdNgxException(ex);
		}
	}

	private AdMsg toAdMsg(BiddingReq biddingReq, Ad ad) {
		PosInfo posInfo = biddingReq.getPosInfo(0);

		AdMsg.Builder adMsg = AdMsg.newBuilder().setDspId(getDspId()).setDspType(DspType.kDspTypePublic)
				.addAllClkMonitorUrl(ad.getEventTracking().getClkTracking().getUrls())
				.addAllImpMonitorUrl(ad.getEventTracking().getImpTracking().getUrls()).setCostType(CostType.kCpm)
				.setCreativeType(posInfo.getCreativeType(0));

		if (ad.isApp()) {
			adMsg.setExtensionType(ExtensionType.kExtAndroid);
		}

		Ad.Content adContent = ad.getContent();
		if (ad.isOpenExternal()) {
			adMsg.setFallback(ad.getLandingPage())
					.setLandUrl(adContent == null ? StringUtils.EMPTY : adContent.getLandingURL());
		} else {
			adMsg.setLandUrl(ad.getLandingPage());
		}

		if (adContent != null) {
			NativeAd.Builder nativeAd = NativeAd.newBuilder();
			Image icon = adContent.getIcon();
			Image screenshots = adContent.getScreenshots();

			nativeAd.setThreadTitle(adContent.getTitle()).setThreadBtnText(adContent.getCta());
			if (icon != null)
				nativeAd.setUserPortrait(StringUtils.defaultString(icon.getUrl()));
			if (screenshots != null) {
				nativeAd.setThreadPic1(StringUtils.defaultString(screenshots.getUrl()));
				adMsg.setPicUrl(StringUtils.defaultString(screenshots.getUrl()));
			}
			nativeAd.setThreadContent(StringUtils.defaultString(adContent.getDescription()));
			adMsg.setNativeAd(nativeAd);
		}
		return adMsg.build();
	}

	@Override
	public byte[] toDspRequest(BiddingReq biddingReq) {
		try {
			PosInfo posInfo = biddingReq.getPosInfo(0);

			String inmobiAdslotId = RtbUtils.getMappingDspAdSlotId(biddingReq, getDspId());
			App.Builder app = App.builder().withBundle(posInfo.getBundle()).withId(inmobiAdslotId);

			BidRequest.Builder builder = BidRequest.builder().withApp(app.build()).withDevice(buildDevice(biddingReq))
					.withImp(buildImp(biddingReq));

			BidRequest bidRequest = builder.build();
			BiddingTracer.trace(biddingReq.getIsDebug(), "InMobi bidRequest: {}", JSON.toJSONString(bidRequest));

			return JSON.toJSONBytes(bidRequest);
		} catch (Throwable ex) {
			// DO NOTHING
		}
		return null;
	}

	private Imp buildImp(BiddingReq biddingReq) {
		Imp.Builder imp = Imp.builder();
		imp.withNativeAd(new Imp.Native());

		PosInfo posInfo = biddingReq.getPosInfo(0);
		AdPositionMapping adslotMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());
		SspAdPosition sspAdPosition = RepositoryFactory.getRepository()
				.loadSspAdPosition(posInfo.getPosId().toStringUtf8());

		if (adslotMapping == null)
			throw new AdNgxException("没有配置inmobi平台广告位映射");

		CreativeType creativeType = biddingReq.getPosInfo(0).getCreativeType(0);
		if (creativeType == CreativeType.kPic) {
			if (sspAdPosition.getAdType() == CreativeType.kFirstScreen) {
				imp.withNativeAd(new Native());
			} else {
				imp.withBanner(
						Banner.builder().withh(adslotMapping.getHeight()).withw(adslotMapping.getWidth()).build());
			}
		} else if (creativeType == CreativeType.kNative) {
			imp.withNativeAd(new Native());
		}
		return imp.build();
	}

	private Device buildDevice(BiddingReq biddingReq) throws Exception {
		UserInfo userInfo = biddingReq.getUserInfo();
		OSType osType = userInfo.getOs();
		Device.Builder builder = Device.builder().withGeo(Geo.builder().withLat((float) userInfo.getLat()).withAccu(0)
				.withLon((float) userInfo.getLon()).build());

		if (osType == OSType.kIOS) {
			builder.withIfa(biddingReq.getUserInfo().getMuid().toStringUtf8()).withOs("ios");
		} else if (osType == OSType.kAndroid) {
			String imei = userInfo.getMuid().toStringUtf8();
			String adid = userInfo.getAdid();
			if (StringUtils.isNotBlank(adid) && adid.length() > 10) {
				builder.withO1(DigestUtils.sha1Hex(adid.toLowerCase().substring(0, 10)));
			}
			builder.withMd5Imei(DigestUtils.md5Hex(imei)).withSha1Imei(DigestUtils.sha1Hex(imei))
					.withUm5(DigestUtils.md5Hex(adid.toLowerCase())).withOs("android");
		}
		builder.withIp(userInfo.getIp()).withModel(userInfo.getDeviceModel().toStringUtf8()).withUa(userInfo.getUa())
				.withOsv(userInfo.getOsv());

		ConnectType connectType = userInfo.getConnectType();
		if (connectType == ConnectType.kWifi)
			builder.withConnectiontype(2);
		if (connectType == ConnectType.k2g)
			builder.withConnectiontype(4);
		if (connectType == ConnectType.k3g)
			builder.withConnectiontype(5);
		if (connectType == ConnectType.k4g)
			builder.withConnectiontype(6);
		if (connectType == ConnectType.kConnectTypeUnKnown)
			builder.withConnectiontype(0);
		return builder.build();
	}

	@Override
	public WinPriceCodec getWinPriceCodec() {
		// TODO Auto-generated method stub
		return null;
	}
}

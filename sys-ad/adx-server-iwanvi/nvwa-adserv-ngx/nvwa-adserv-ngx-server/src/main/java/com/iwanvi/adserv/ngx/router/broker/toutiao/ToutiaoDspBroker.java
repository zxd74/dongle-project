/*
 * Copyright 2017 The OpenDSP Project
 *
 * The OpenDSP Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.iwanvi.adserv.ngx.router.broker.toutiao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.AdSlot;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.AdSlot.AdType;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.AdSlot.Position;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.AdSlot.Size;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.App;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.Device;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.Device.ConnectionType;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.Device.DeviceType;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidRequest.Device.OsType;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidResponse;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidResponse.Ad;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidResponse.Ad.MaterialMeta;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidResponse.Ad.MaterialMeta.Image;
import com.iwanvi.adserv.ngx.router.broker.toutiao.ToutiaoRtb.BidResponse.Ad.MaterialMeta.InteractionType;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
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

/**
 * 
 * @author wangwp
 */
public class ToutiaoDspBroker implements DspBroker {
	private static final Logger LOG = LoggerFactory.getLogger(ToutiaoDspBroker.class);

	private static final String APP_ID = MinervaCfg.get().getConfigProperty("toutiao.appid");

	private static final Map<CreativeType, AdType> adTypeMapper = new HashMap<>();
	private static final Map<AdType, Position> positionMapper = new HashMap<>();
	private static final Map<AdType, List<AdSlot.CreativeType>> creativeTypeMapper = new HashMap<>();
	private static final WinPriceCodec winPriceCodec = new ToutiaoWinPriceCodec();

	private Dsp dsp;

	@Override
	public void setDsp(Dsp dsp) {
		this.dsp = dsp;
	}

	static {
		adTypeMapper.put(CreativeType.kFirstScreen, AdType.SPLASH);
		adTypeMapper.put(CreativeType.kPic, AdType.BANNER);
		adTypeMapper.put(CreativeType.kVideo, AdType.PATCH);
		adTypeMapper.put(CreativeType.kDrawVideo, AdType.DRAW_VIDEO);
		adTypeMapper.put(CreativeType.kRewardVideo, AdType.REWARD_VIDEO);
		adTypeMapper.put(CreativeType.kText, AdType.BANNER);
		adTypeMapper.put(CreativeType.kScreen, AdType.INTERSTITIAL);
		adTypeMapper.put(CreativeType.kNative, AdType.STREAM);

		positionMapper.put(AdType.BANNER, Position.BOTTOM);
		positionMapper.put(AdType.INTERSTITIAL, Position.MIDDLE);
		positionMapper.put(AdType.SPLASH, Position.FULLSCREEN);
		positionMapper.put(AdType.STREAM, Position.FLOW);

		creativeTypeMapper.put(AdType.BANNER,
				Arrays.asList(AdSlot.CreativeType.HTML, AdSlot.CreativeType.IMAGE, AdSlot.CreativeType.TEXT));
		creativeTypeMapper.put(AdType.INTERSTITIAL, Arrays.asList(AdSlot.CreativeType.HTML, AdSlot.CreativeType.IMAGE));
		creativeTypeMapper.put(AdType.STREAM, Arrays.asList(AdSlot.CreativeType.TEXT_ICON));
		creativeTypeMapper.put(AdType.SPLASH, Arrays.asList(AdSlot.CreativeType.IMAGE));
		creativeTypeMapper.put(AdType.PATCH, Arrays.asList(AdSlot.CreativeType.VIDEO));
		creativeTypeMapper.put(AdType.DRAW_VIDEO, Arrays.asList(AdSlot.CreativeType.VIDEO));

	}

	@Override
	public String getDspId() {
		return MinervaCfg.get().getConfigProperty("toutiao.dspid");
	}

	@Override
	public byte[] toDspRequest(BiddingReq biddingReq) {
		UserInfo u = biddingReq.getUserInfo();
		PosInfo p = biddingReq.getPosInfoList().get(0);

		BidRequest.Geo.Builder geo = BidRequest.Geo.newBuilder().setLatitude((float) u.getLat()).setLongitude((float) u.getLon());
		App.Builder ab = App.newBuilder().setAppid(APP_ID).setPackageName(p.getBundle()).setName(p.getAppName()).setVersion(p.getAppVersion()).setGeo(geo.build());

		String did = u.getMuid().toStringUtf8();
		Device.Builder db = Device.newBuilder().setDid(did).setImei(did).setMac(u.getMac()).setOs(OsType.ANDROID)
				.setConnType(connType(u.getConnectType())).setType(deviceType(u)).setScreenWidth(u.getScreenWidth())
				.setScreenHeight(u.getScreenHeight());

		BidRequest.Builder brb = BidRequest.newBuilder().setRequestId(biddingReq.getId()).setApiVersion("1.0")
				.setUid(biddingReq.getUserInfo().getMuid().toStringUtf8()).setSourceType("app").setApp(ab.build())
				.setDevice(db.build()).setIp(u.getIp()).setUa(userAgent(u)).setTimeout(MinervaCfg.get().getDspTimeout())
				.setAdxName("WANWEI").setAdxPassword("WANWEI_PASSWORD");

		SspAdPosition adslot = RepositoryFactory.getRepository().loadSspAdPosition(p.getPosId().toStringUtf8());
		if (adslot == null) {
			throw new AdNgxException("找不到媒体广告位信息, posId: " + p.getPosId().toStringUtf8());
		}
		AdSlot.Builder adsb = buildAdSlot(biddingReq, RtbUtils.getBidfloor(dsp, biddingReq));
		brb.addAdslots(adsb.build());

		BidRequest bidRequest = brb.build();
		BiddingTracer.trace(biddingReq.getIsDebug(), TextFormat.printToUnicodeString(bidRequest));
		return bidRequest.toByteArray();
	}

	private AdSlot.Builder buildAdSlot(BiddingReq req, double bidfloor) {
		// TODO 头条广告获取在广告位映射修改之后需要重新更新逻辑
		PosInfo p = req.getPosInfo(0);

		CreativeType adPositionType = p.getAdType();
		CreativeType adpCreativeType = p.getCreativeType(0);
		AdPositionMapping adPositionMapping = RtbUtils.getMappingDspAdPosition(req, getDspId());

		int width = p.getWidth();
		int height = p.getHeight();

		int sizeCreativeType = 0;

		if (adPositionMapping != null) {
			width = adPositionMapping.getWidth();
			height = adPositionMapping.getHeight();
			if (adPositionMapping.hasCreativeStyle())
				sizeCreativeType = adPositionMapping.getCreativeStyle();
		}

		AdType toutiaoAdType = adTypeMapper.get(adpCreativeType);
		if (width == 1080 && height == 1920)
			toutiaoAdType = AdType.SPLASH;

		Position adPos = positionMapper.get(toutiaoAdType);
		if (adPos == null)
			adPos = Position.FULLSCREEN;

		if (CollectionUtils.containsAny(p.getCreativeTypeList(), Constants.CREATIVE_TYPES_IMAGE)) {
			sizeCreativeType = 1;
		}

		if (adPositionType == CreativeType.kVideo || adPositionType == CreativeType.kDrawVideo) {
			sizeCreativeType = 3;
		}

		if (sizeCreativeType == 0) {
			sizeCreativeType = p.getToutiaoCreativeType();
		}

		AdSlot.Builder adsb = AdSlot.newBuilder().setAdtype(toutiaoAdType).setPos(adPos)
				.addAcceptedSize(Size.newBuilder().setWidth(width).setHeight(height).setCreativeType(sizeCreativeType))
				.setId(p.getPosId().toStringUtf8()).setMinimumCpm((int) bidfloor);
		return adsb;
	}

	private DeviceType deviceType(UserInfo u) {
		return DeviceType.PHONE;
	}

	private String userAgent(UserInfo u) {
		return StringUtils.isBlank(u.getUa()) ? Constants.DEFAULT_UA : u.getUa();
	}

	private ConnectionType connType(ConnectType connectType) {
		return ConnectionType.forNumber(connectType.getNumber());
	}

	@Override
	public String getRequestContentType() {
		return "application/octet-stream";
	}

	@Override
	public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] respBody) {
		boolean isDebug = biddingReq.getIsDebug();

		List<AdMsg> adList = new ArrayList<>();
		try {
			BidResponse bidResponse = BidResponse.parseFrom(respBody);
			BiddingTracer.trace(biddingReq.getIsDebug(), "toutiao bidResponse: {}",
					TextFormat.printToUnicodeString(bidResponse));
			if (bidResponse.getAdsCount() == 0) {
				BiddingTracer.trace(isDebug, "头条dsp返回广告为空");
			}
			for (Ad ad : bidResponse.getAdsList()) {
				adList.add(toAdMsg(biddingReq, ad));
			}
		} catch (Throwable ex) {
			LOG.error("", ex);
		}
		return adList;
	}

	private AdMsg toAdMsg(BiddingReq biddingReq, Ad ad) throws Exception {
		MaterialMeta creative = ad.getCreative();
		PosInfo posInfo = biddingReq.getPosInfo(0);
		CreativeType creativeType = posInfo.getCreativeType(0);

		int price = (int) ad.getPrice();
		if (dsp.getIsTest()) {
			price = (int)Math.round(ad.getPrice()*0.9);
		}
		LOG.debug("toutiao bidPrice: {}",ad.getPrice());
		AdMsg.Builder adb = AdMsg.newBuilder().setCrid(ad.getAdId()).setBidPrice(price).setAdxBidPrice(price)
				.setPaidCpc(price).setCostType(CostType.kCpm).setDspId(getDspId()).setLandUrl(creative.getTargetUrl())
				.setDspType(DspType.kDspTypePublic).setPicUrl(creative.getImage().getUrl())
				.setCreativeType(creativeType);

		InteractionType interactionType = creative.getInteractionType();
		if (interactionType == InteractionType.DOWLOAD) {
			adb.setPkgName(creative.getPackageName()).setLandUrl(creative.getDownloadUrl())
					.setAppName(creative.getAppName()).setExtensionType(ExtensionType.kExtAndroid);
		}

		if (creativeType == CreativeType.kNative) {
			adb.setNativeAd(buildNativeAd(creative));
		}

		if (creativeType == CreativeType.kVideo) {
			MaterialMeta.Video video = creative.getVideo();
			adb.setCreativeType(CreativeType.kVideo).setAdDuration((int) video.getVideoDuration())
					.setPicUrl(video.getVideoUrl());
		}
		adb.addAllImpMonitorUrl(creative.getShowUrlList());
		adb.addAllClkMonitorUrl(creative.getClickUrlList());

		return adb.build();
	}

	private NativeAd.Builder buildNativeAd(MaterialMeta creative) throws Exception {
		NativeAd.Builder nab = NativeAd.newBuilder();
		nab.setButtonText(creative.getButtonText()).setThreadTitle(creative.getTitle())
				.setUserPortrait(creative.getIcon()).setThreadBtnText(creative.getButtonText())
				.setThreadContent(creative.getDescription()).setUserName(creative.getSubtitle());

		String methodNameFormat = "setThreadPic%d";

		int imageIndex = 1;
		List<Image> imageList = creative.getImageListList();
		if (imageList.isEmpty()) {
			nab.setThreadPic1(creative.getImage().getUrl());
		} else {
			for (Image image : creative.getImageListList()) {
				MethodUtils.invokeMethod(nab, true, String.format(methodNameFormat, imageIndex), image.getUrl());
				imageIndex++;
			}
		}
		return nab;
	}

	@Override
	public WinPriceCodec getWinPriceCodec() {
		return winPriceCodec;
	}
}

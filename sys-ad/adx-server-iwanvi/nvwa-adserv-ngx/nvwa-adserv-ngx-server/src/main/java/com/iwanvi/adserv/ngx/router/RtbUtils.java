/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.router;

import static com.iwanvi.adserv.ngx.util.Constants.COST_TYPES_RTB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.search.AdSearchers;
import com.iwanvi.adserv.ngx.search.ContextHolder;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.NgxServices;
import com.iwanvi.nvwa.proto.AdModelsProto.App.AppMapping;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp.AuditType;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp.DspAdPositionPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition.AdPositionMapping;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg.Builder;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingRsp;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.DspType;
import com.iwanvi.nvwa.proto.CommonProto.ExtensionType;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;
import com.iwanvi.nvwa.proto.CommonProto.OSType;
import com.iwanvi.nvwa.proto.CommonProto.Pmp;
import com.iwanvi.nvwa.proto.CommonProto.PutType;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.App;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Device;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Geo;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Imp;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Imp.Banner;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Imp.Video;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.User;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidResponse.SeatBid.Bid;

/**
 * @author weiping wang
 *
 */
public final class RtbUtils {
	private static final Logger LOG = LoggerFactory.getLogger(RtbUtils.class);
	private static final Map<OSType, String> osMapper = new HashMap<>();

	static {
		osMapper.put(OSType.kAndroid, "android");
		osMapper.put(OSType.kIOS, "ios");
	}

	public static AdMsg.Builder toAdMsg(BiddingReq biddingReq, Bid bid, String dspId) {
		AdMsg.Builder adMsg = AdMsg.newBuilder().setImpid(bid.getImpid());
		DspCreative creative = RepositoryFactory.getRepository().getDspCreative(bid.getCrid(), dspId);
		if (creative == null || creative.getStatus() == 0) {
			return null;
		}

		adMsg.setBidPrice((int) bid.getPrice()).setAdxBidPrice((int) bid.getPrice()).setPaidCpc((int) bid.getPrice())
				.addAllClkMonitorUrl(bid.getClktrackersList()).addAllImpMonitorUrl(bid.getImptrackersList())
				.setLandUrl(creative.getLandingPage()).setCostType(CostType.kCpm).setPutType(PutType.kPutTypeRtb)
				.setDspType(DspType.kDspTypePublic).setPicUrl(creative.getCreativeUrl()).setDspId(dspId)
				.setCreativeId(creative.getId()).setCrid(creative.getCreativeId())
				.setExtensionType(ExtensionType.kExtWebsite).addAllDlMonitorUrl(bid.getDltrackersList())
				.addAllDpsMonitorUrl(bid.getDpstrackersList()).addAllDpfMonitorUrl(bid.getDpftrackersList())
				.setPmp(Pmp.newBuilder().setDealId(bid.getDealid()));

		adMsg.setNativeAd(buildNativeAdByCreative(bid, creative));
		switch (bid.getClickType()) {
		case 2:
			if (StringUtils.isNotBlank(bid.getAppDownloadUrl())) {
				adMsg.setLandUrl(bid.getAppDownloadUrl()).setFallback(bid.getLdp());
			}
			adMsg.setAppName(bid.getAppName()).setPkgName(bid.getBundle());
			adMsg.setExtensionType(ExtensionType.kExtAndroid);
			break;
		case 3:
			if (StringUtils.isNotBlank(bid.getDeeplink())) {
				adMsg.setLandUrl(bid.getDeeplink()).setFallback(bid.getLdp());
			}
			adMsg.setExtensionType(ExtensionType.kExtDeeplink);
			break;
		case 4:
			adMsg.setExtensionType(ExtensionType.kExtIos).setPkgName(bid.getBundle());
		default:
			break;
		}
		return adMsg;
	}

	private static NativeAd buildNativeAdByCreative(Bid bid, DspCreative creative) {
		NativeAd.Builder nativeAd = NativeAd.newBuilder();
		nativeAd.setThreadTitle(creative.getTitle()).setThreadBtnText(creative.getCtatext())
				.setThreadContent(creative.getDesc()).setUserPortrait(creative.getIcon());

		int imgCount = creative.getImagesCount();
		if (imgCount == 0) {
			nativeAd.setThreadPic1(creative.getCreativeUrl());
		}
		if (imgCount > 0)
			nativeAd.setThreadPic1(creative.getImages(0));
		if (imgCount > 1)
			nativeAd.setThreadPic2(creative.getImages(1));
		if (imgCount > 2)
			nativeAd.setThreadPic3(creative.getImages(2));
		nativeAd.setTemplateId(bid.getNativeAd().getTemplateId());
		return nativeAd.build();
	}

	private static NativeAd buildNativeAd(Bid bid) {
		NativeAd.Builder nativeAd = NativeAd.newBuilder();
		nativeAd.setThreadTitle(bid.getNativeAd().getTitle()).setUserPortrait(bid.getNativeAd().getIcon())
				.setButtonText(bid.getNativeAd().getCtatext()).setThreadContent(bid.getNativeAd().getDesc());

		String methodNameFormat = "setThreadPic%d";
		int imageIndex = 1;
		List<String> imageList = bid.getNativeAd().getImgsList();
		if (imageList.isEmpty()) {
			nativeAd.setThreadPic1(bid.getCreativeUrl());
		} else {
			for (String image : imageList) {
				try {
					MethodUtils.invokeMethod(nativeAd, true, String.format(methodNameFormat, imageIndex), image);
					imageIndex++;
				} catch (Throwable ex) {
					LOG.error("设置原生广告位图片异常", ex.getMessage());
				}
			}
		}
		nativeAd.setTemplateId(bid.getNativeAd().getTemplateId());
		return nativeAd.build();
	}

	public static BidRequest toBidRequest(BiddingReq biddingReq) {
		BidRequest.Builder bidRequest = BidRequest.newBuilder().setId(biddingReq.getId());
		UserInfo userInfo = biddingReq.getUserInfo();

		for (PosInfo posInfo : biddingReq.getPosInfoList()) {
			List<String> supportTmids = posInfo.getTmidList();
			String posId = posInfo.getPosId().toStringUtf8();
			SspAdPosition sspAdPosition = NgxServices.getRepository().loadSspAdPosition(posId);
			Imp.Builder imp = Imp.newBuilder().setId(posInfo.getId()).setTagid(posId)
					.setBidfloor(sspAdPosition.getBidfloor());

			List<CreativeType> creativeTypes = posInfo.getCreativeTypeList();
			if (CollectionUtils.containsAny(creativeTypes, Constants.CREATIVE_TYPES_IMAGE)) {
				imp.setBanner(Banner.newBuilder().setH(posInfo.getHeight()).setW(posInfo.getWidth()));
			}

			if (creativeTypes.contains(CreativeType.kVideo)) {
				imp.setVideo(Video.newBuilder().setMinduration(posInfo.getMinDuration())
						.setMaxduration(posInfo.getMaxDuration()));
			}

			if (creativeTypes.contains(CreativeType.kNative)) {
				imp.setIsNativeAd(true);
				com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Imp.NativeAd.Builder natiAd = com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Imp.NativeAd
						.newBuilder();
				if (CollectionUtils.isNotEmpty(supportTmids)) {
					natiAd.addAllTemplateId(supportTmids);
				} else {
					// natiAd.addAllTemplateId(sspAdPosition.getTemplateIdList());
				}
				imp.setNativeAd(natiAd.build());
			}
			bidRequest.addImp(imp);
		}

		Device.Builder deviceBuilder = Device.newBuilder();
		// String didmd5 = MD5Utils.md5Hex(userInfo.getMuid().toByteArray());
		String didmd5 = DigestUtils.md5Hex(userInfo.getMuid().toStringUtf8());

		String os = osMapper.get(userInfo.getOs());
		if (os == null)
			os = StringUtils.EMPTY;

		deviceBuilder.setConnectiontype(biddingReq.getUserInfo().getConnectType().getNumber()).setDidmd5(didmd5)
				.setIfa(userInfo.getMuid().toStringUtf8()).setIp(userInfo.getIp())
				.setModel(userInfo.getDeviceModel().toStringUtf8()).setMake(userInfo.getDeviceBrand().toStringUtf8())
				.setGeo(Geo.newBuilder().setAreaCode(userInfo.getAreaCode()).setLat((float) userInfo.getLat())
						.setLon((float) userInfo.getLon()))
				.setCarrier(String.valueOf(userInfo.getCarrier().getNumber()))
				.setDevicetype(userInfo.getDeviceType().getNumber()).setOs(os).setUa(userInfo.getUa())
				.setOsv(userInfo.getOsv()).setAdid(userInfo.getAdid()).setH(userInfo.getScreenHeight())
				.setW(userInfo.getScreenWidth()).setMac(userInfo.getMac());

		bidRequest.setDevice(deviceBuilder.build());
		bidRequest.setUser(User.newBuilder().setId(didmd5));

		PosInfo posInfo = biddingReq.getPosInfo(0);
		bidRequest.setApp(App.newBuilder().setBundle(posInfo.getBundle()).setVer(posInfo.getAppVersion())
				.setId(posInfo.getAppId().toStringUtf8()).setName(posInfo.getAppName()));
		return bidRequest.build();
	}

	public static List<BiddingReq> splitBiddingReq(BiddingReq biddingReq) {
		List<BiddingReq> biddingReqList = new ArrayList<>(biddingReq.getPosInfoCount());
		biddingReq.getPosInfoList().forEach(imp -> {
			biddingReqList.add(BiddingReq.newBuilder(biddingReq).clearPosInfo().addPosInfo(PosInfo.newBuilder(imp)
					.clearCostType().addAllCostType(COST_TYPES_RTB).addExt(MapEntry.newBuilder()
							.setKey(Constants.PUT_TYPE_EXT_KEY).setValue(String.valueOf(PutType.kPutTypeRtb_VALUE))))
					.build());
		});
		return biddingReqList;
	}

	public static List<AdMsg> getAdMsgs(BiddingReq request) {
		BiddingContext context = BiddingContext.create(request);
		context.trace("bidding request: {}", TextFormat.printToUnicodeString(request));
		ContextHolder.getInstance().setContext(context);

		try {
			AdSearchers.search(context);
			if (context.getBiddingRsp() == null) {
				return null;
			}
			BiddingRsp bidding_resp = context.getBiddingRsp();
			if (bidding_resp.getAdImpsList() == null || bidding_resp.getAdImpsList().isEmpty()) {
				return null;
			}
			context.trace("bidding response: {}", TextFormat.printToUnicodeString(bidding_resp));
			return bidding_resp.getAdImpsList();
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	public static Map<String, String> toMap(List<MapEntry> mapEntryList) {
		Map<String, String> map = new HashMap<String, String>();
		mapEntryList.forEach(entry -> {
			map.put(entry.getKey(), entry.getValue());
		});
		return map;
	}

	public static List<MapEntry> remove(List<MapEntry> mapEntryList, String... keys) {
		Map<String, String> map = toMap(mapEntryList);
		if (keys == null)
			return mapEntryList;

		Arrays.asList(keys).forEach(key -> map.remove(key));
		return toMapEntryList(map);
	}

	private static List<MapEntry> toMapEntryList(Map<String, String> map) {
		if (map == null)
			return Collections.emptyList();
		List<MapEntry> mapEntryList = new ArrayList<>();
		map.forEach((k, v) -> mapEntryList.add(MapEntry.newBuilder().setKey(k).setValue(v).build()));
		return mapEntryList;
	}

	public static String getDspStatsRedisKey(String dspId, int type) {
		String datestr = DateFormatUtils.format(new Date(), "yyyyMMdd:HH");
		if (type == 1) // req
			return String.format("dsp:hour:req:%s:%s", dspId, datestr);
		if (type == 2) // bid
			return String.format("dsp:hour:bid:%s:%s", dspId, datestr);
		if (type == 3) // win
			return String.format("dsp:hour:win:%s:%s", dspId, datestr);
		if (type == 4) // timeout
			return String.format("dsp:hour:timeout:%s:%s", dspId, datestr);
		if (type == 5) // error
			return String.format("dsp:hour:error:%s:%s", dspId, datestr);
		if (type == 6)// 放弃竞价
			return String.format("dsp:hour:nobid:%s:%s", dspId, datestr);
		if (type == 7) // 低于底价
			return String.format("dsp:hour:lower:%s:%s", dspId, datestr);
		if (type == 8) // 超过qps限制
			return String.format("dsp:hour:overqps:%s:%s", dspId, datestr);
		return null;
	}

	public static String getDspReqRedisKey(String dspId) {
		return getDspStatsRedisKey(dspId, 1);
	}

	public static String getDspBidRedisKey(String dspId) {
		return getDspStatsRedisKey(dspId, 2);
	}

	public static String getDspWinRedisKey(String dspId) {
		return getDspStatsRedisKey(dspId, 3);
	}

	public static String getDspTimeoutRedisKey(String dspId) {
		return getDspStatsRedisKey(dspId, 4);
	}

	public static String getDspErrorRedisKey(String dspId) {
		return getDspStatsRedisKey(dspId, 5);
	}

	public static String getDspNoBidRedisKey(String dspId) {
		return getDspStatsRedisKey(dspId, 6);
	}

	public static String getDspUnderFloorRedisKey(String dspId) {
		return getDspStatsRedisKey(dspId, 7);
	}

	public static String getDspQpsOverLimitRedisKey(String dspId) {
		return getDspStatsRedisKey(dspId, 8);
	}

	public static int getDspFixedPrice(BiddingReq biddingReq, Dsp dsp) {
		String posId = biddingReq.getPosInfo(0).getPosId().toStringUtf8();
		for (DspAdPositionPrice price : dsp.getDspAdPositionPriceList()) {
			if (posId.contentEquals(price.getTagid()))
				return price.getPrice();
		}
		LOG.warn("==没有对dsp:{}配置广告位{}的固定价格==", dsp.getDspId(), posId);
		throw new AdNgxException(String.format("dsp: %s, 广告位: %s, 没有设置固定价格", dsp.getDspId(), posId));
	}

	/**
	 * 获得第三方平台广告位id
	 * 
	 * @param biddingReq 竞价请求
	 * @param dspId      第三方广告平台id
	 * @return 第三方广告平台广告位id
	 */
	public static String getMappingDspAdSlotId(BiddingReq biddingReq, String dspId) {
		String adxAdSlotId = biddingReq.getPosInfo(0).getPosId().toStringUtf8();
		SspAdPosition adxAdSlot = RepositoryFactory.getRepository().loadSspAdPosition(adxAdSlotId);
		if (adxAdSlot == null)
			return null;

		for (AdPositionMapping adSlotMapping : adxAdSlot.getAdPositionMappingsList()) {
			if (dspId.equals(adSlotMapping.getDspId())) {
				return adSlotMapping.getDspAdPositionId();
			}
		}

		return null;
	}

	public static AdPositionMapping getMappingDspAdPosition(BiddingReq biddingReq, String dspId) {
		PosInfo posInfo = biddingReq.getPosInfo(0);

		AdPositionMapping.Builder mappingAdslot = null;
		String adxAdSlotId = biddingReq.getPosInfo(0).getPosId().toStringUtf8();
		SspAdPosition adxAdSlot = RepositoryFactory.getRepository().loadSspAdPosition(adxAdSlotId);
		if (adxAdSlot == null)
			return null;

		int width = posInfo.getWidth();
		int height = posInfo.getHeight();

		for (AdPositionMapping adSlotMapping : adxAdSlot.getAdPositionMappingsList()) {
			if (dspId.equals(adSlotMapping.getDspId())) {
				if (adSlotMapping.hasWidth())
					width = adSlotMapping.getWidth();

				if (adSlotMapping.hasHeight())
					height = adSlotMapping.getHeight();

				mappingAdslot = AdPositionMapping.newBuilder(adSlotMapping).setWidth(width).setHeight(height)
						.setDspAdPositionId(adSlotMapping.getDspAdPositionId());
				return mappingAdslot.build();
			}
		}
		return null;
	}

	/**
	 * 获取DSP广告位映射数据
	 * 
	 * @param adxAdSlotId ADX系统广告位ID
	 * @param dspId       DspId
	 * @return
	 */
	public static AdPositionMapping getMappingDspAdPosition(String adxAdSlotId, String dspId) {
		AdPositionMapping.Builder mappingAdslot = null;
		SspAdPosition adxAdSlot = RepositoryFactory.getRepository().loadSspAdPosition(adxAdSlotId);
		if (adxAdSlot == null)
			return null;
		int width = 0, height = 0;
		for (AdPositionMapping adSlotMapping : adxAdSlot.getAdPositionMappingsList()) {
			if (dspId.equals(adSlotMapping.getDspId())) {
				if (adSlotMapping.hasWidth()) {
					width = adSlotMapping.getWidth();
				}
				if (adSlotMapping.hasHeight()) {
					height = adSlotMapping.getHeight();
				}
				mappingAdslot = AdPositionMapping.newBuilder(adSlotMapping).setWidth(width).setHeight(height)
						.setDspAdPositionId(adSlotMapping.getDspAdPositionId());
				return mappingAdslot.build();
			}
		}
		return null;
	}

	public static void handleAdMsg(Builder adMsg, BiddingReq biddingReq) {
		String posId = biddingReq.getPosInfo(0).getPosId().toStringUtf8();
		if (posId.equals("qM7Jza") || posId.equals("VRBf6n")) {
			NativeAd natiAd = adMsg.getNativeAd();
			adMsg.getNativeAdBuilder().setUserPortrait(natiAd.getThreadPic1());
		}
	}

	/**
	 * 获取平台底价信息
	 * 
	 * @param dsp
	 * @param biddingReq
	 * @return
	 */
	public static double getBidfloor(Dsp dsp, BiddingReq biddingReq) {
		// 如果dsp处于测试状态则设置底价为1分，保证dsp平台所有广告不会被系统底价过滤
		if (dsp.getIsTest()) {
			return 0D;
		}
		PosInfo p = biddingReq.getPosInfo(0);

		// 如果存在底价映射，需要按映射的底价处理
		AdPositionMapping adPositionMapping = RtbUtils.getMappingDspAdPosition(biddingReq, dsp.getDspId());
		if (adPositionMapping != null && adPositionMapping.hasDspFloor()) {
			// 存在广告位映射
			return adPositionMapping.getDspFloor();
		}

		SspAdPosition adslot = NgxServices.getRepository().loadSspAdPosition(p.getPosId().toStringUtf8());
		if (adslot == null) {
			throw new AdNgxException("广告位信息不存在, posId: " + p.getPosId().toStringUtf8());
		}

		return adslot.getBidfloor();
	}

	public static Builder toAdMsg(BiddingReq biddingReq, Bid bid, Dsp dsp) {
		// 如果dsp平台免审，则直接从响应中获取广告数据
		if (dsp.getAuditType() == AuditType.NO_AUDIT) {
			AdMsg.Builder adMsg = AdMsg.newBuilder().setImpid(bid.getImpid());
			adMsg.setBidPrice((int) bid.getPrice()).setAdxBidPrice((int) bid.getPrice())
					.addAllClkMonitorUrl(bid.getClktrackersList()).addAllImpMonitorUrl(bid.getImptrackersList())
					.setLandUrl(bid.getLdp()).setCostType(CostType.kCpm).setPutType(PutType.kPutTypeRtb)
					.setDspType(DspType.kDspTypePublic).setPicUrl(bid.getCreativeUrl()).setDspId(dsp.getDspId())
					.setCrid(bid.getCrid()).setExtensionType(ExtensionType.kExtWebsite)
					.addAllDlMonitorUrl(bid.getDltrackersList()).addAllDpsMonitorUrl(bid.getDpstrackersList())
					.addAllDpfMonitorUrl(bid.getDpftrackersList()).setPmp(Pmp.newBuilder().setDealId(bid.getDealid()));
			switch (bid.getClickType()) {
			case 2:
				if (StringUtils.isNotBlank(bid.getAppDownloadUrl())) {
					adMsg.setLandUrl(bid.getAppDownloadUrl()).setFallback(bid.getLdp());
				}
				adMsg.setAppName(bid.getAppName()).setPkgName(bid.getBundle());
				adMsg.setExtensionType(ExtensionType.kExtAndroid);
				break;
			case 3:
				if (StringUtils.isNotBlank(bid.getDeeplink())) {
					adMsg.setLandUrl(bid.getDeeplink()).setFallback(bid.getLdp());
				}
				adMsg.setExtensionType(ExtensionType.kExtDeeplink);
				break;
			case 4:
				adMsg.setExtensionType(ExtensionType.kExtIos).setPkgName(bid.getBundle());
			default:
				break;
			}
			adMsg.setNativeAd(buildNativeAd(bid));
			return adMsg;
		} else {
			return toAdMsg(biddingReq, bid, dsp.getDspId());
		}
	}

	public static void main(String[] args) throws Exception {
		double bidfloor = 6.75;

		double bidflooryuan = bidfloor / 100;
		System.out.println((double) Math.round(bidflooryuan * 100) / 100);
	}

	public static AppMapping getMappingDspApp(String dspId, String appId) {
		com.iwanvi.nvwa.proto.AdModelsProto.App app = RepositoryFactory.getRepository().loadApp(appId);
		if (app == null)
			return null;

		List<AppMapping> appMappingList = app.getAppMappingList();
		for (AppMapping appMapping : appMappingList) {
			if (appMapping.getDspid().equals(dspId)) {
				return appMapping;
			}
		}
		return null;
	}

	public static Imp getImp(BidRequest bidRequest, Bid bid) {
		String impid = bid.getImpid();
		for (Imp imp : bidRequest.getImpList()) {
			if (impid.contentEquals(imp.getId()))
				return imp;
		}
		return null;
	}

	public static String getReqRedisKey() {
		String datestr = DateFormatUtils.format(new Date(), "yyyyMMdd:HH");
		return String.format("rtb:req:%s", datestr);
	}

	public static Dsp getDsp(String dspId){
		return RepositoryFactory.getRepository().loadDsp(dspId);
	}
}

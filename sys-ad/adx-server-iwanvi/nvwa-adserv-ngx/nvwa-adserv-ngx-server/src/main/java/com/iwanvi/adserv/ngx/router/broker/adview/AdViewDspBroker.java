/**
 * 
 */
package com.iwanvi.adserv.ngx.router.broker.adview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.RtbUtils;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.broker.adview.BidResponse.Ad;
import com.iwanvi.adserv.ngx.router.broker.adview.BidResponse.Image;
import com.iwanvi.adserv.ngx.router.broker.adview.BidResponse.Native;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.MD5Utils;
import com.iwanvi.adserv.ngx.util.QueryStringBuilder;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
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

/**
 * @author wangweiping
 *
 */
public class AdViewDspBroker implements DspBroker {
	private static final String ADVIEW_DSP_ID = MinervaCfg.get().getConfigProperty("adview.dspid");
	private static final String SECRETKEY = MinervaCfg.get().getConfigProperty("adview.secretkey");

	private static final Map<ConnectType, String> ntMapper = new HashMap<>();
	private static final Map<Carrier, String> nopMapper = new HashMap<>();
	private static final Map<CreativeType, Integer> ptMapper = new HashMap<>();

	static {
		ntMapper.put(ConnectType.k2g, "2g");
		ntMapper.put(ConnectType.k3g, "3g");
		ntMapper.put(ConnectType.k4g, "4g");
		ntMapper.put(ConnectType.kWifi, "wifi");
		ntMapper.put(ConnectType.kConnectTypeUnKnown, StringUtils.EMPTY);

		nopMapper.put(Carrier.kMobile, "46000");
		nopMapper.put(Carrier.kCarrierUnKnown, StringUtils.EMPTY);
		nopMapper.put(Carrier.kUnicom, "46001");
		nopMapper.put(Carrier.kTelecom, "46003");

		ptMapper.put(CreativeType.kPic, 0);
		ptMapper.put(CreativeType.kNative, 6);
		ptMapper.put(CreativeType.kFirstScreen, 4);
		ptMapper.put(CreativeType.kScreen, 1);
		ptMapper.put(CreativeType.kVideo, 5);
	}

	@Override
	public String getDspId() {
		return ADVIEW_DSP_ID;
	}

	@Override
	public String getRequestContentType() {
		return null;
	}

	@Override
	public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] respBody) {
		boolean isDebug = biddingReq.getIsDebug();
		String respJSON = new String(respBody);
		BiddingTracer.trace(isDebug, "adview广告平台响应: {}", respJSON);

		BidResponse bidResponse = JSON.parseObject(respJSON, BidResponse.class);
		if (bidResponse == null)
			return null;

		int res = bidResponse.getRes();
		if (res == 0) {
			BiddingTracer.trace(isDebug, "adview获取广告失败");
			return null;
		}

		List<Ad> ads = bidResponse.getAd();
		if (ads == null || ads.isEmpty()) {
			BiddingTracer.trace(isDebug, "adview返回广告列表为空");
			return null;
		}

		List<AdMsg> adMsgList = new ArrayList<>();
		for (Ad ad : ads) {
			AdMsg adMsg = toAdMsg(biddingReq, ad);
			if (adMsg != null)
				adMsgList.add(adMsg);
		}
		return adMsgList;
	}

	private AdMsg toAdMsg(BiddingReq req, Ad ad) {
		PosInfo p = req.getPosInfo(0);

		List<String> impList = ad.getEs() != null ? ad.getEs().get("0") : Collections.emptyList();
		List<String> clkList = ad.getEc();

		if (impList == null)
			impList = Collections.emptyList();
		if (clkList == null)
			clkList = Collections.emptyList();

		AdMsg.Builder adMsg = AdMsg.newBuilder().setDspId(getDspId()).setDspType(DspType.kDspTypePublic)
				.addAllImpMonitorUrl(impList).addAllClkMonitorUrl(clkList).setCostType(CostType.kCpm)
				.setCreativeType(p.getCreativeType(0)).setLandUrl(StringUtils.defaultString(ad.getAl()));

		Integer act = ad.getAct();
		String cta = "查看详情";
		if (act != null && act == 2) {
			adMsg.setAppName(StringUtils.defaultString(ad.getDan())).setPkgName(StringUtils.defaultString(ad.getDpn()))
					.setExtensionType(ExtensionType.kExtAndroid);
			List<String> surl = ad.getSurl();
			if (surl != null && !surl.isEmpty()) {
				adMsg.addAllClkMonitorUrl(surl);
			}
			cta = "立即下载";
		}

		if (StringUtils.isNotBlank(ad.getDl())) {
			adMsg.setLandUrl(ad.getDl()).setFallback(ad.getAl());
		}

		Native _native = ad.getNativeAd();
		if (_native != null) {
			NativeAd.Builder nativeAd = NativeAd.newBuilder().setThreadBtnText(cta)
					.setThreadTitle(StringUtils.defaultString(_native.getTitle()))
					.setUserPortrait(StringUtils
							.defaultString(_native.getIcon() == null ? StringUtils.EMPTY : _native.getIcon().getUrl()))
					.setThreadContent(_native.getDesc());

			List<Image> images = _native.getImages();
			if (CollectionUtils.isNotEmpty(images)) {
				if (images.size() > 0)
					nativeAd.setThreadPic1(StringUtils.defaultString(images.get(0).getUrl()));
				if (images.size() > 1)
					nativeAd.setThreadPic2(StringUtils.defaultString(images.get(1).getUrl()));
				if (images.size() > 2)
					nativeAd.setThreadPic3(StringUtils.defaultString(images.get(2).getUrl()));
			}
			adMsg.setNativeAd(nativeAd.build());
		} else {
			List<String> images = ad.getApi();
			if (CollectionUtils.isNotEmpty(images)) {
				NativeAd.Builder nativeAd = NativeAd.newBuilder()
						.setThreadPic1(StringUtils.defaultString(images.get(0)));
				adMsg.setNativeAd(nativeAd.build());
			}
		}

		List<String> images = ad.getApi();
		if (CollectionUtils.isNotEmpty(images)) {
			adMsg.setPicUrl(StringUtils.defaultString(images.get(0)));
		}
		return adMsg.build();
	}

	@Override
	public String toQueryString(BiddingReq biddingReq) {
		boolean isDebug = biddingReq.getIsDebug();

		PosInfo p = biddingReq.getPosInfo(0);
		UserInfo u = biddingReq.getUserInfo();

		SspAdPosition adslot = RepositoryFactory.getRepository().loadSspAdPosition(p.getPosId().toStringUtf8());
		AdPositionMapping adslotMapping = RtbUtils.getMappingDspAdPosition(biddingReq, getDspId());

		if (adslotMapping == null)
			throw new AdNgxException("缺少adview广告位映射配置");

		String[] adslotInfos = adslotMapping.getDspAdPositionId().split("_");
		if (adslotInfos == null || adslotInfos.length != 2)
			throw new AdNgxException("adview广告位映射配置不正确");
		String appid = adslotInfos[0];
		String posid = adslotInfos[1];

		long time = System.currentTimeMillis();
		BidRequest.Builder builder = BidRequest.builder().withAndid(u.getAdid()).withAppid(appid)
				.withBdr(u.getOsv())
				.withBrd(u.getDeviceBrand().toStringUtf8()).withTp(u.getDeviceModel().toStringUtf8())
				.withDeny(u.getDensity()/160).withDidmd5(MD5Utils.md5Hex(u.getMuid().toByteArray()))
				.withDidsha1(DigestUtils.sha1Hex(u.getMuid().toStringUtf8())).withh(adslotMapping.getHeight())
				.withw(adslotMapping.getWidth()).withUa(u.getUa()).withSw(u.getScreenWidth())
				.withSh(u.getScreenHeight()).withSn(u.getMuid().toStringUtf8()).withNt(ntMapper.get(u.getConnectType()))
				.withNop(nopMapper.get(u.getCarrier())).withOs(0).withIp(u.getIp()).withTab(0).withPack(p.getBundle())
				.withTime(time).withPosId(posid).withPt(ptMapper.get(adslot.getAdType())).withLat(u.getLat())
				.withLon(u.getLon());

		// 广告位映射处理
		if (adslotMapping.hasDspAdType() && ptMapper.containsKey(adslotMapping.getDspAdType())){
			builder.withPt(ptMapper.get(adslotMapping.getDspAdType()));
		}

		// MD5(appid+sn 或 gd+os+nop+pack+time+secretKey)
		String token = MD5Utils.md5Hex(appid + u.getMuid().toStringUtf8() + "0" + nopMapper.get(u.getCarrier())
				+ p.getBundle() + time + SECRETKEY).toLowerCase();

		builder.withToken(token);

		BidRequest bidRequest = builder.build();
		QueryStringBuilder<BidRequest> qsBuilder = QueryStringBuilder.create(bidRequest);
		String queryString = qsBuilder.toQueryString();

		BiddingTracer.trace(isDebug, "adview bidding req: {}", queryString);
		return queryString;
	}

	@Override
	public WinPriceCodec getWinPriceCodec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpMethod requestMethod() {
		return HttpMethod.GET;
	}
}

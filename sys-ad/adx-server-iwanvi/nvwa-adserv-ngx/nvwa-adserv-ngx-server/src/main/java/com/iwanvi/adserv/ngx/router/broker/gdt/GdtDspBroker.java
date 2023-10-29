package com.iwanvi.adserv.ngx.router.broker.gdt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.broker.gdt.BidResponse.Ad;
import com.iwanvi.adserv.ngx.router.broker.gdt.BidResponse.Ads;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.DspType;
import com.iwanvi.nvwa.proto.CommonProto.ExtensionType;

//广点通广告接口实现
public class GdtDspBroker implements DspBroker {
	private static final String GDT_DSPID = MinervaCfg.get().getConfigProperty("gdt.dspid");

	@Override
	public String getDspId() {
		return GDT_DSPID;
	}

	@Override
	public String getRequestContentType() {
		return null;
	}

	@Override
	public HttpMethod requestMethod() {
		return HttpMethod.GET;
	}

	@Override
	public byte[] toDspRequest(BiddingReq biddingReq) {
		// TODO Auto-generated method stub
		return DspBroker.super.toDspRequest(biddingReq);
	}

	@Override
	public Map<String, String> getHeaders(BiddingReq biddingReq) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("X-Forwarded-For", biddingReq.getUserInfo().getIp());
		return headers;
	}

	@Override
	public List<AdMsg> toAdMsgs(BiddingReq biddingReq, byte[] body) {
		boolean isDebug = biddingReq.getIsDebug();
		String posId = biddingReq.getPosInfo(0).getPosId().toStringUtf8();

		if (body == null)
			return null;
		String strBody = new String(body, Charsets.UTF_8);
		if (isDebug) {
			BiddingTracer.trace(isDebug, "gdt bid response: {}", strBody);
		}
		BidResponse bidResponse = JSON.parseObject(strBody, BidResponse.class);
		if (bidResponse == null)
			return null;
		int ret = bidResponse.getRet();

		List<AdMsg> adMsgs = new ArrayList<>();
		if (ret == 0 && (bidResponse.getData() != null && !bidResponse.getData().isEmpty())) {
			AdMsg adMsg = toAdMsg(biddingReq, bidResponse.getData().get(posId));
			if (adMsg != null)
				adMsgs.add(adMsg);
		}
		return adMsgs;
	}

	private AdMsg toAdMsg(BiddingReq biddingReq, Ads ads) {
		PosInfo adslot = biddingReq.getPosInfo(0);
		CreativeType creativeType = adslot.getCreativeType(0);

		if (ads == null)
			return null;
		if (CollectionUtils.isEmpty(ads.getList()))
			return null;

		Ad ad = ads.getList().get(0);
		AdMsg.Builder adBuilder = AdMsg.newBuilder().setDspId(getDspId()).setCostType(CostType.kCpm)
				.setDspType(DspType.kDspTypePublic).setCreativeType(biddingReq.getPosInfo(0).getCreativeType(0))
				.addImpMonitorUrl(ad.getImpression_link()).setLandUrl(StringUtils.defaultString(ad.getClick_link()));

		int actionType = ad.getInteract_type();
		if (actionType == 1) {
			adBuilder.setExtensionType(ExtensionType.kExtAndroid)
					.setPkgName(StringUtils.defaultString(ad.getPackage_name()));
		}

		String deeplink = StringUtils.defaultString(ad.getCustomized_invoke_url());
		if (StringUtils.isNotBlank(deeplink)) {
			adBuilder.setLandUrl(deeplink).setFallback(StringUtils.defaultString(ad.getClick_link()));
		}

		// 如果crt_type为空，默认按照图片处理
		String title = StringUtils.defaultString(ad.getTitle());
		String desc = StringUtils.defaultString(ad.getDescription());

		NativeAd.Builder nati = NativeAd.newBuilder();
		Integer crtType = ad.getCrt_type() == null ? 2 : ad.getCrt_type();
		if (creativeType == CreativeType.kPic) {
			adBuilder.setPicUrl(ad.getImg_url());
		} else if (creativeType == CreativeType.kVideo) {
			adBuilder.setPicUrl(ad.getVideo_url());
		} else if (creativeType == CreativeType.kNative) {
			nati.setThreadTitle(title).setThreadContent(desc);
			if (crtType == 7) {
				List<String> imgList = ad.getImg_list();
				if (imgList.size() > 0)
					nati.setThreadPic1(imgList.get(0));
				if (imgList.size() > 1)
					nati.setThreadPic2(imgList.get(1));
				if (imgList.size() > 2)
					nati.setThreadPic3(imgList.get(2));
			} else if (crtType == 11) {
				nati.setUserPortrait(StringUtils.defaultString(ad.getImg2_url())).setThreadPic1(ad.getImg_url());
			}
			adBuilder.setNativeAd(nati);
		}

		String videoUrl = StringUtils.defaultString(ad.getVideo_url());
		return adBuilder.build();
	}

	@Override
	public WinPriceCodec getWinPriceCodec() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toQueryString(BiddingReq biddingReq) {
		return null;
	}
}

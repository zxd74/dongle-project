/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.AdxTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.Creative;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingRsp;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.CommonProto.AdxInfo;
import com.iwanvi.nvwa.proto.CommonProto.AdxType;
import com.iwanvi.nvwa.proto.CommonProto.AgentType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.DspType;
import com.iwanvi.nvwa.proto.CommonProto.FrequencyCapping;
import com.iwanvi.nvwa.proto.CommonProto.FrequencyType;
import com.iwanvi.nvwa.proto.CommonProto.PutType;

/**
 * 创意优选，目前没有有效利用点击率数据，推广单元的下面的创意随机出现
 * 
 * @author wangwp
 */
public class OptimizeSearcher extends AbstractSearcher {
	private static final Logger LOGGER = LoggerFactory.getLogger(OptimizeSearcher.class.getSimpleName());

	@Override
	public void doSearch(BiddingContext context) {

		BiddingRsp.Builder builder = BiddingRsp.newBuilder().setId(context.getBiddingReq().getId());
		List<SearchItem.Builder> searchResults = context.getSearchResults();

		for (int i = 0, size = searchResults.size(); i < size; i++) {
			AdMsg adMsg = optimizeCreatives(searchResults.get(i));
			if (adMsg != null) {
				builder.addAdImps(adMsg);
			}
		}
		context.setBiddingRsp(builder.build());
	}

	// 创意优选逻辑，目前逻辑很简单，直接从推广单元下审核通过的所有创意中随机选择一个
	private AdMsg optimizeCreatives(SearchItem.Builder searchItem) {
		// 过滤审核通过的所有创意
		List<Creative> creatives = getEffectiveCreatives(searchItem.getAdUnit().getCreativesList());

		if (creatives == null || creatives.isEmpty()) {
			return null;
		}

		AdUnit adUnit = searchItem.getAdUnit();
		Advertiser advertiser = searchItem.getAdvertiser();
		AdPlan adPlan = searchItem.getAdPlan();

		Creative creative = creatives.get(ThreadLocalRandom.current().nextInt(creatives.size()));

		AdMsg.Builder builder = AdMsg.newBuilder().setAdvertiserId(advertiser.getAdvertiserId())
				.setAdvertiserUuid(advertiser.getAdvertiserUuid()).setPlanId(adPlan.getPlanId())
				.setUnitId(adUnit.getUnitId()).setCreativeId(creative.getCreativeId())
				.setExtensionType(adUnit.getExtensionType()).setCreativeType(adUnit.getCreativeType())
				.setPaidCpc(searchItem.getPaidPrice()).setPicUrl(creative.getPicUrl()).setLandUrl(adUnit.getLandUrl())
				.setPkgName(adUnit.getPkgName()).setCostType(adUnit.getCostType())
				.setAdxBidPrice(searchItem.getAdxBidPrice()).setSnkCreativeId(adUnit.getExtCreativeId())
				.setBidPrice(searchItem.getBidPrice()).setAgentUuid(advertiser.getAgentUuid())
				.setIndustryId(advertiser.getIndustryId()).setAdDuration(creative.getDuration())
				.setPutType(adUnit.getPutType()).setImpid(context().getPosInfo().getId());

		if (adUnit.getPutType() == PutType.kPutTypeOrder) {
			builder.setOrderId(String.valueOf(adUnit.getPlanId()));
		}

		if (adUnit.getPutType() == PutType.kPutTypeRtb) {
			builder.setDspType(DspType.kDspTypePrivate);
			builder.setDspId(MinervaCfg.get().getDspId());
		}
		builder.addAllClkMonitorUrl(creative.getClickMonitorUrlList())
				.addAllImpMonitorUrl(creative.getImpMonitorUrlList());

		if (adUnit.hasFrequencyCapping()) {
			builder.setFrequencyCapping(adUnit.getFrequencyCapping());
			FrequencyCapping fc = adUnit.getFrequencyCapping();
			builder.setFrequencyType(FrequencyType.kFrequencyTypeDevice);
			if (fc.getIpCapping()) {
				builder.setFrequencyType(FrequencyType.kFrequencyTypeIp);
			}
		}

		if (adUnit.hasCpaCapping()) {
			builder.setCpaCapping(adUnit.getCpaCapping());
		}

		if (adUnit.hasPmp()) {
			builder.setPmp(adUnit.getPmp());
		}
		if (context().getUserInfo().getAdxType() != AdxType.kAdxTypeUnKnown) {
			AdxInfo adxInfo = getAdxInfo(creative, context().getUserInfo().getAdxType());
			if (adxInfo != null && adxInfo.hasAdxMUrl()) {
				builder.setPicUrl(adxInfo.getAdxMUrl());
				builder.setAdxInfo(adxInfo);
			}
		}

		if (advertiser.hasAdxPayDiscount()) {
			builder.setAdxPayDiscount(advertiser.getAdxPayDiscount());
		} else {
			builder.setAdxPayDiscount(MinervaCfg.get().getAdxPayDiscount());
		}

		if (creative.hasNativeAd()) {
			builder.setNativeAd(creative.getNativeAd());
		}

		Agent agent = searchItem.getAgent();
		builder.setAgentType(agent.getAgentType());

		if (agent.getAgentType() == AgentType.kAgentTypeNM) {
			builder.setAgentCost(searchItem.getAgentFloorPriceConfig().getFloorPrice());
		}

		if (agent.getAgentType() == AgentType.kAgentTypeKA) {
			builder.setAgentPayDiscount(1 / (1 - agent.getProfitMargin()));
		}

		if (adUnit.getCreativeType() == CreativeType.kVideo) {
			builder.setAdDuration(adUnit.getDuration());
		}

		if (searchItem.getApp() != null) {
			builder.setSharing(searchItem.getApp().getSharing());
		}
		return builder.build();
	}

	// 过滤掉指定单元下面未通过adx审核的创意
	private List<Creative> getEffectiveCreatives(List<Creative> creatives) {
		BiddingContext _context = context();
		boolean isOttAdRequest = _context.isOttAdReq();
		if (isOttAdRequest) {
			return filterOttCreatives(creatives, _context.getPosInfo());
		}
		AdxType adxType = _context.getBiddingReq().getUserInfo().getAdxType();

		if (adxType == AdxType.kAdxTypeUnKnown) {
			return creatives;
		}

		List<Creative> results = new ArrayList<>(creatives.size());

		for (Creative creative : creatives) {
			if (isAdExchangeAuditStatusOk(creative, adxType)) {
				results.add(creative);
			} else {
				LOGGER.debug("Invalid adx audit status, adxType: {}, creative_id: {}", adxType,
						creative.getCreativeId());
			}
		}
		return results;
	}

	private List<Creative> filterOttCreatives(List<Creative> creatives, PosInfo posInfo) {
		List<Creative> ottCreatives = new ArrayList<>();

		for (Creative creative : creatives) {
			if (isMatchAdSpec(posInfo, creative)) {
				ottCreatives.add(creative);
			}
		}
		return ottCreatives;
	}

	private boolean isMatchAdSpec(PosInfo posInfo, Creative creative) {
		if (!posInfo.getCreativeTypeList().contains(creative.getCreativeType())) {
			return false;
		}
		switch (creative.getCreativeType()) {
		case kPic:
			return posInfo.getWidth() == creative.getWidth() && posInfo.getHeight() == creative.getHeight();
		case kVideo:
			return creative.getDuration() >= posInfo.getMinDuration()
					&& creative.getDuration() <= posInfo.getMaxDuration();
		default:
			break;
		}
		return false;
	}

	private boolean isAdExchangeAuditStatusOk(Creative creative, AdxType adxType) {
		AdxTarget adxTarget = creative.getAdxTarget();
		for (int i = 0; i < adxTarget.getAdxInfoCount(); i++) {
			if (adxType == adxTarget.getAdxInfo(i).getAdxType()) {
				return true;
			}
		}
		return false;
	}

	AdxInfo getAdxInfo(Creative creative, AdxType adxType) {
		AdxTarget adxTarget = creative.getAdxTarget();

		AdxInfo result = null;
		for (int i = 0; i < adxTarget.getAdxInfoCount(); i++) {
			result = adxTarget.getAdxInfo(i);
			if (adxType == result.getAdxType()) {
				return result;
			}
		}
		return null;
	}
}

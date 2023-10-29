/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.filter.AdFilters;
import com.iwanvi.adserv.ngx.index.ReverseIndex;
import com.iwanvi.adserv.ngx.index.ReverseIndexFactory;
import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.AgentFloorPriceConfigKeyBuilder;
import com.iwanvi.adserv.ngx.util.AreaLevelIndex;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.CpaCappingDataHolder;
import com.iwanvi.adserv.ngx.util.ReverseIndexKeysUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.AdxType;
import com.iwanvi.nvwa.proto.CommonProto.AgentType;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.DeliveryMode;

/**
 * 索引查找器
 * 
 * @author wangwp
 */
public class IndexSearcher extends AbstractSearcher {
	static final Logger LOG = LoggerFactory.getLogger(IndexSearcher.class.getSimpleName());

	private static final Repository _repository = RepositoryFactory.getRepository();

	public IndexSearcher() {
	}

	@Override
	public void doSearch(BiddingContext context) {
		long start = System.currentTimeMillis();
		PosInfo posInfo = context.getBiddingReq().getPosInfo(0);
		UserInfo userInfo = context.getBiddingReq().getUserInfo();

		List<Long> indexKeys = ReverseIndexKeysUtils.buildReverseIndexKeys(posInfo, userInfo);

		context.trace("Search index keys: {}", indexKeys);
		context.trace("build reverse index keys cost: {}ms", (System.currentTimeMillis() - start));

		long idxSearchStart = System.currentTimeMillis();
		ReverseIndex<AdUnit> reverseIndex = ReverseIndexFactory.getReverseIndex();
		List<AdUnit> adUnits = reverseIndex.search(indexKeys);

		context.trace("matched ad size: {}", adUnits.size());
		context.trace("search reverse index cost: {}ms, match adUnits's count: {}",
				(System.currentTimeMillis() - idxSearchStart), adUnits == null ? 0 : adUnits.size());

		if (adUnits == null || adUnits.isEmpty()) {
			return;
		}

		List<SearchItem.Builder> searchItems = new ArrayList<>();

		for (AdUnit adUnit : adUnits) {
			context.trace("match adUnit id: {}", adUnit.getUnitId());
			if (adUnit.getStatus() == 0) {
				context.trace("Invalid adUnit status, uid: {}", adUnit.getUnitId());
				continue;
			}

			if (adUnit.getCreativesCount() == 0) {
				context.trace("No creatives in adUnit, uid: {}", adUnit.getUnitId());
				continue;
			}

			AdPlan adPlan = _repository.loadAdPlan(adUnit.getPlanId());
			if (adPlan == null || adPlan.getStatus() == 0) {
				context.trace("Invalid adPlan status, pid: {}, uid: {}", adUnit.getPlanId(), adUnit.getUnitId());
				continue;
			}

			Advertiser advertiser = _repository.loadAdvertiser(adUnit.getAdvertiserId());
			if (advertiser == null || advertiser.getStatus() == 0) {
				context.trace("Invalid advertiser status, aid: {}", adUnit.getAdvertiserId());
				continue;
			}

			// 设置代理商以及代理商价格配置相关属性
			Agent agent = _repository.loadAgent(advertiser.getAgentId());
			if (agent == null || agent.getStatus() == 0) {
				context.trace("Agent not exists or invalid status, agent_id: {}", advertiser.getAgentId());
				continue;
			}

			AgentFloorPriceConfig agentFloorPriceConfig = null;

			if (agent.getAgentType() == AgentType.kAgentTypeNM) {
				Integer areaLevel = getAreaLevel(userInfo.getAreaCode());
				Long key = AgentFloorPriceConfigKeyBuilder.buildLongKey(agent.getAgentId(), userInfo.getMediaUuid(),
						advertiser.getIndustryId(), adUnit.getAdTypeId(), userInfo.getOs(), areaLevel);
				agentFloorPriceConfig = _repository.loadAgentFloorPriceConfig(key);

				// 普通代理商必须配置底价信息，如果底价信息不存在的话，记录错误信息
				if (agentFloorPriceConfig == null) {
					context.trace(
							"The floor price config not exists for agent, agent_id: {},media_uuid: {},industry_id: {}, ad_pos_id: {}, uid: {}, areaLevel: {}",
							advertiser.getAgentId(), userInfo.getMediaUuid(), advertiser.getIndustryId(),
							adUnit.getAdTypeId(), adUnit.getUnitId(), areaLevel);
					continue;
				}

				// 如果广告主出价低于代理商底价的话，不参与竞价
				if (adUnit.getCpc() < agentFloorPriceConfig.getFloorPrice()) {
					context.trace("广告主出价: {}低于代理商底价: {}, 广告id: {}", adUnit.getCpc(),
							agentFloorPriceConfig.getFloorPrice(), adUnit.getUnitId());
					continue;
				}
			}

			if (adUnit.hasCpaCapping() && adUnit.getCpaCapping().getCpaCapping()) {
				CpaCappingDataHolder.registerCpaAdunit(adUnit);
			}

			if (AdFilters.filterAd(context, adUnit)) {
				continue;
			}

			SearchItem.Builder builder = SearchItem.newBuilder();
			CostType costType = adUnit.getCostType();

			int score = adUnit.getCpc();
			if (costType == CostType.kCpt)
				score = 1;
			if (costType == CostType.kCpfm)
				score = 0;
			if (costType == CostType.kCpfc)
				score = -1;

			builder.setAdPlan(adPlan).setAdTypeConfig(_repository.loadAdTypeConfig(adUnit.getAdTypeId()))
					.setAdUnit(adUnit).setAdvertiser(advertiser)
					.setPaidPrice(adUnit.getCpc() + Constants.RANK_PRICE_DIFF_UNIT).setBidPrice(adUnit.getCpc())
					.setScore(score).setAgent(agent).setAgentFloorPriceConfig(agentFloorPriceConfig);

			// 如果是普通代理商的话需要设置底价相关信息
			if (agent.getAgentType() == AgentType.kAgentTypeNM) {
				builder.setAgentFloorPriceConfig(agentFloorPriceConfig);
			}

			// 对于视频贴片来说，rank的score是单位时间的cpc,即cpc/duration
			if (adUnit.getCreativeType() == CreativeType.kVideo) {
				builder.setScore(adUnit.getCpc() / (adUnit.getDuration() + 0.0f));
			}

			String targetAppId = context.getUserExtProperty(Constants.EXT_APP_ID);
			// 如果是ssp联盟流量的话
			if (StringUtils.isNotBlank(targetAppId) && userInfo.getAdxType() == AdxType.kAdxTypeUnKnown) {
				App app = _repository.loadApp(targetAppId);
				builder.setApp(app);
			}

			// 如果单元投放方式为匀速投放的话，设置引擎上下文的skipSmoothBudgetDelivery为false
			if (adUnit.getDeliveryMode() == DeliveryMode.kSmoothBudgetDelivery && adUnit.getCpc() > 0) {
				context.setSkipSmoothBudgetDelivery(false);
			}
			searchItems.add(builder);
		}

		adUnits = null; // help GC!!!!
		if (searchItems.isEmpty()) {
			return;
		}
		context.setSearchResults(searchItems);
	}

	private Integer getAreaLevel(int areaCode) {
		String mediaUuid = context().getUserInfo().getMediaUuid();
		return AreaLevelIndex.getInstance().getAreaLevel(mediaUuid, areaCode);
	}
}

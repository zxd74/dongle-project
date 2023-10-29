/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.search.SearchItem.Builder;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.AdModelsProto.AdTypeConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdTypeConfig.AdxConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.CommonProto.AdPositionType;
import com.iwanvi.nvwa.proto.CommonProto.AdxType;
import com.iwanvi.nvwa.proto.CommonProto.AgentType;
import com.iwanvi.nvwa.proto.CommonProto.CostType;

/**
 * 根据eCPM对符合条件的广告进行排序
 * 
 * @author wangwp
 */
public class RankSearcher extends AbstractSearcher {
	static final Logger LOGGER = LoggerFactory.getLogger(RankSearcher.class.getSimpleName());

	public RankSearcher() {
	}

	public static class AdxSearchItemComparator implements Comparator<SearchItem.Builder> {
		private static final AdxSearchItemComparator _instance = new AdxSearchItemComparator();

		private AdxSearchItemComparator() {
		}

		public static AdxSearchItemComparator getInstance() {
			return _instance;
		}

		@Override
		public int compare(SearchItem.Builder a, SearchItem.Builder b) {
			if (a.getAdUnit().getCostType() == b.getAdUnit().getCostType()) {
				if (a.getScore() == b.getScore()) {
					return 0;
				}
				return a.getScore() > b.getScore() ? -1 : 1;
			} else {
				return a.getAdUnit().getCostType() == CostType.kCpm ? -1 : 1;
			}
		}
	}

	public static class NonAdxSearchItemComparator implements Comparator<SearchItem.Builder> {
		private static final NonAdxSearchItemComparator _instance = new NonAdxSearchItemComparator();

		private NonAdxSearchItemComparator() {
		}

		public static NonAdxSearchItemComparator getInstance() {
			return _instance;
		}

		@Override
		public int compare(SearchItem.Builder a, SearchItem.Builder b) {
			if (a.getScore() == b.getScore()) {
				return 0;
			}
			return a.getScore() > b.getScore() ? -1 : 1;
		}
	}

	@Override
	public void doSearch(BiddingContext context) {
		calAdxBidPrices();

		if (context.getUserInfo().getAdxType() == AdxType.kAdxTypeUnKnown) {
			Collections.sort(context.getSearchResults(), NonAdxSearchItemComparator.getInstance());
		} else {
			Collections.sort(context.getSearchResults(), AdxSearchItemComparator.getInstance());
		}
	}

	private double getAdxBidWeight(SearchItem.Builder searchItem) {
		double adxBidWeight = MinervaCfg.get().getAdxBidWeight();
		if (searchItem.getAdvertiser().hasAdxBidDiscount()) {
			adxBidWeight = searchItem.getAdvertiser().getAdxBidDiscount();
		} else if (context().getAdCommonConfig().hasAdxBidDiscount()) {
			adxBidWeight = context().getAdCommonConfig().getAdxBidDiscount();
		}
		return adxBidWeight;
	}

	private double getCpc2cpmCtr(AdTypeConfig config) {
		AdxType adxType = context().getUserInfo().getAdxType();
		for (int i = 0; i < config.getAdxConfCount(); i++) {
			AdxConfig adxConfig = config.getAdxConf(i);
			if (adxType == adxConfig.getAdxType()) {
				return adxConfig.getCtr();
			}
		}
		return config.getDefaultCtr();
	}

	private void calAdxBidPrices() {
		// 默认全局cpc->cpm转化率
		List<SearchItem.Builder> searchItems = context().getSearchResults();

		for (SearchItem.Builder searchItem : searchItems) {
			Agent agent = searchItem.getAgent();
			// 如果代理商不存在或者代理商类型为直客类型的话按照直客方式计算出价
			if (agent.getAgentType() == AgentType.kAgentTypeKA) {
				calAdxBidPrice4KAAgent(searchItem, agent);
			} else {
				calAdxBidPrices4NMAgent(searchItem, agent);
			}
		}
	}

	// 直客代理商下面的广告出价计费逻辑
	private void calAdxBidPrice4KAAgent(Builder searchItem, Agent agent) {
		double cpc2cpmCtr = context().getMinervaCfg().getCpc2cpmCtr();
		AdUnit adUnit = searchItem.getAdUnit();

		// 广告主出价系数
		double adxBidWeight = getAdxBidWeight(searchItem);
		// 代理商出价系数为1-非凡的利润率
		double agentBidWeight = 1 - agent.getProfitMargin();

		CostType costType = adUnit.getCostType();
		//如果是订单广告的话，无需出价
		if (Constants.COST_TYPES_CPT.contains(costType))
			return;
		
		if (adUnit.getCostType() == CostType.kCpm) {
			searchItem.setAdxBidPrice((int) (searchItem.getPaidPrice() * adxBidWeight * agentBidWeight));
		} else {
			// 如果计费类型为cpc且广告位类型也是cpc的话，直接按照cpc出价即可，无需转化为CPM出价
			AdPositionType adPosType = searchItem.getAdTypeConfig().getAdPositionType();
			if (adPosType == AdPositionType.CPC) {
				searchItem.setAdxBidPrice((int) (searchItem.getPaidPrice() * adxBidWeight * agentBidWeight));
				return;
			}
			double convertCtr = cpc2cpmCtr;
			if (searchItem.getAdTypeConfig() != null) {
				convertCtr = getCpc2cpmCtr(searchItem.getAdTypeConfig());
			}
			int adxBidPrice = (int) (1000 * convertCtr * searchItem.getPaidPrice() * adxBidWeight * agentBidWeight);
			searchItem.setAdxBidPrice(adxBidPrice);
		}
	}

	// 计算代理商出价，常规代理
	private void calAdxBidPrices4NMAgent(Builder searchItem, Agent agent) {
		AgentFloorPriceConfig agentFloorPriceConfig = searchItem.getAgentFloorPriceConfig();

		int agentFloorPrice = agentFloorPriceConfig.getFloorPrice();

		double profitMargin = 0.0D;
		if (agentFloorPriceConfig.hasProfitMargin()) {
			profitMargin = agentFloorPriceConfig.getProfitMargin();
		} else {
			// 这里获取dsp平台的利润率系数,默认为0.2
			profitMargin = agent.getProfitMargin();
		}

		searchItem.setAdxBidPrice((int) (agentFloorPrice * (1 - profitMargin)));
	}
}

/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.RRWUtils;
import com.iwanvi.adserv.ngx.util.WeightResolver;

/**
 * 推广单元轮播
 * 
 * @author wangwp
 */
public class RollingSearcher extends AbstractSearcher {
	static final Logger LOGGER = LoggerFactory.getLogger(RollingSearcher.class.getSimpleName());

	public RollingSearcher() {
	}

	@Override
	public void doSearch(BiddingContext context) {

		List<SearchItem.Builder> searchItems = context.getSearchResults();
		int hitsSize = searchItems.size();

		int adNum = context().getPosInfo().getAdNum();
		if (!context().getPosInfo().hasAdNum()) {
			adNum = Constants.DEFAULT_FETCH_AD_NUM;
		}
		// 如果请求广告数大于符合条件的广告数的话，将adNum设置为符合条件的广告数
		if (adNum > hitsSize) {
			return;
		}
		rolling(adNum);
	}

	private void rolling(int adNum) {
		BiddingContext context = context();
		if (context.getSearchResults().size() == Constants.DEFAULT_FETCH_AD_NUM) {
			return;
		}

		List<SearchItem.Builder> rollingResults = new ArrayList<>(adNum);
		WeightResolver<SearchItem.Builder> weightResolver = SearchItemWeightResolver.getInstance();

		if (adNum == Constants.DEFAULT_FETCH_AD_NUM) {
			rollingResults.add(RRWUtils.rrw(context.getSearchResults(), weightResolver));
			context.setSearchResults(rollingResults);
			return;
		}

		for (int i = 0; i < adNum; i++) {
			int idx = RRWUtils.rrwIndex(context.getSearchResults(), weightResolver);
			if (idx == -1) {
				continue;
			}
			rollingResults.add(context.getSearchResults().remove(idx));
		}
		context.setSearchResults(rollingResults);
	}

	public static class SearchItemWeightResolver implements WeightResolver<SearchItem.Builder> {
		private static final SearchItemWeightResolver instance = new SearchItemWeightResolver();

		private SearchItemWeightResolver() {
		}

		public static SearchItemWeightResolver getInstance() {
			return instance;
		}

		@Override
		public int getWeight(SearchItem.Builder t) {
			if(t.getAdUnit().hasPutWeight()) {
				return t.getAdUnit().getPutWeight();
			}
			return t.getAdxBidPrice()==0?1:t.getAdxBidPrice();
		}
	}
}

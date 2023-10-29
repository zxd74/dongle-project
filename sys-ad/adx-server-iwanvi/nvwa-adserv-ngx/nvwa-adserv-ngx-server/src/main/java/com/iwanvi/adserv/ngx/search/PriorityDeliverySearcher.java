/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import java.util.ArrayList;
import java.util.List;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.nvwa.proto.CommonProto.PutType;

/**
 * 订单优先级投放支持
 * 
 * @author wangwp
 */
public class PriorityDeliverySearcher extends AbstractSearcher {

	@Override
	public void doSearch(BiddingContext context) {
		PutType putType = PutType.forNumber(context.getUserExtPropertyAsInt(Constants.PUT_TYPE_EXT_KEY));
		if (putType == null || putType != PutType.kPutTypeOrder) {
			return;
		}

		List<SearchItem.Builder> searchResults = context.getSearchResults();
		if (CollectionUtils.isEmpty(searchResults)) {
			return;
		}

		// 根据订单优先级排序，订单优先级相同的再通过rollingsearcher的权重逻辑进行处理
		searchResults.sort((a, b) -> b.getAdUnit().getPutLevel() - a.getAdUnit().getPutLevel());
		List<SearchItem.Builder> results = new ArrayList<>(searchResults.size());

		SearchItem.Builder head = searchResults.get(0);
		results.add(head);
		for (int i = 1; i < searchResults.size(); i++) {
			SearchItem.Builder item = searchResults.get(i);
			if (searchResults.get(i).getAdUnit().getPutLevel() == head.getAdUnit().getPutLevel()) {
				results.add(item);
			}
		}
		context.setSearchResults(results);
	}
}

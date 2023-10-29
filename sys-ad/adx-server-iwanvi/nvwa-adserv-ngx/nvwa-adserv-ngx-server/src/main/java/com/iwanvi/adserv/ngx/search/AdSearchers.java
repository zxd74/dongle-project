/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import java.util.List;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.ServiceLoaderUtils;

/**
 * 更新广告检索逻辑，将原来的的链式实现转换为通过{@link ServiceLoader}的方式
 * 
 * @author wangwp
 */
public class AdSearchers {
	private static final Logger LOG = LoggerFactory.getLogger(AdSearchers.class);

	private static final List<Searcher> adSearcherList = ServiceLoaderUtils.loadServices(Searcher.class);

	public static void search(BiddingContext context) {
		try {
			for (Searcher searcher : adSearcherList) {
				searcher.search(context);
				List<SearchItem.Builder> searchResults = context.getSearchResults();
				if (CollectionUtils.isEmpty(searchResults)) {
					context.trace("没有符合查询条件的广告， AdSearcher: {}", searcher.getClass().getName());
					return;
				}
				context.trace("匹配定向的广告：{}个, AdSearcher: {}", searchResults.size(), searcher.getClass().getName());
			}
		} catch (Throwable ex) {
			LOG.error("search ads error, cause: ", ex);
		}
	}
}

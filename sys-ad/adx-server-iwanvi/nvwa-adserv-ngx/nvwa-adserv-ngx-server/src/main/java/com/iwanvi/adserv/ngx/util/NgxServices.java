/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.List;

import com.iwanvi.adserv.ngx.filter.AdFilter;
import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.search.Searcher;
import com.iwanvi.adserv.ngx.service.BidFloorResolver;

/**
 * 引擎服务组件管理
 * 
 * @author wangwp
 */
public final class NgxServices {
	private static BidFloorResolver bidfloorResolver = ServiceLoaderUtils.loadService(BidFloorResolver.class);
	private static List<Searcher> searcherList = ServiceLoaderUtils.loadServices(Searcher.class);
	private static List<AdFilter> adFilters = ServiceLoaderUtils.loadServices(AdFilter.class);
	private static Repository repository = ServiceLoaderUtils.loadService(Repository.class);
	private static WinPriceCodec winPriceCodec = ServiceLoaderUtils.loadService(WinPriceCodec.class);

	public static BidFloorResolver getBidFloorResolver() {
		return bidfloorResolver;
	}

	public static Repository getRepository() {
		return repository;
	}

	public static List<Searcher> getSearcherList() {
		return searcherList;
	}

	public static List<AdFilter> getAdFilters() {
		return adFilters;
	}

	public static WinPriceCodec getWinPriceCodec() {
		return winPriceCodec;
	}
}

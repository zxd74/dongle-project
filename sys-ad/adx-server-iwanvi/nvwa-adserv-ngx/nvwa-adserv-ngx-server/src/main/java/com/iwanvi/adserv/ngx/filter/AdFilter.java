/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.filter;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

/**
 * 
 * @author wangwp
 */
public interface AdFilter {
	/**
	 * 广告过滤
	 * 
	 * @param ctx
	 *            竞价上下文
	 * @param ad
	 *            广告
	 * @return 返回true表示被过滤, 否则返回false
	 */
	boolean filterAd(BiddingContext ctx, AdUnit ad);
}

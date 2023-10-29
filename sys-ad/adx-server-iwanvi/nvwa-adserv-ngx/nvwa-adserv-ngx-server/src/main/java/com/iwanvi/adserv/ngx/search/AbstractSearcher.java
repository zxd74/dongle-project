/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.BiddingContext;

/**
 * 
 * @author wangwp
 */
public abstract class AbstractSearcher implements Searcher {
	private final Logger LOG = LoggerFactory.getLogger(getClass().getSimpleName());

	private static final ContextHolder contextHolder = ContextHolder.getInstance();

	public BiddingContext context() {
		return contextHolder.context();
	}

	@Override
	public void search(BiddingContext context) {
		long start = System.currentTimeMillis();
		doSearch(context);

		long elapsed = System.currentTimeMillis() - start;
		if (elapsed > 5) {
			LOG.info("doSearch method cost: {}ms", elapsed);
		}
	}

	public abstract void doSearch(BiddingContext context);
}

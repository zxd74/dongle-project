/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangwp
 *
 */
public class BiddingTracer {
	private static final Logger LOG = LoggerFactory.getLogger("biddingTracer");
	private static BiddingTracer biddingTracer = null;

	private BiddingTracer() {
	}

	public static BiddingTracer getTracer() {
		return biddingTracer;
	}

	public static BiddingTracer newTracer() {
		if (biddingTracer == null) {
			biddingTracer = new BiddingTracer();
		}
		return biddingTracer;
	}

	public void trace(String format, Object... args) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(format, args);
		} else {
			LOG.info(format, args);
		}
	}

	public static void trace(boolean isDebug, String format, Object... args) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(format, args);
		} else {
			if (isDebug) {
				LOG.info(format, args);
			}
		}
	}
}

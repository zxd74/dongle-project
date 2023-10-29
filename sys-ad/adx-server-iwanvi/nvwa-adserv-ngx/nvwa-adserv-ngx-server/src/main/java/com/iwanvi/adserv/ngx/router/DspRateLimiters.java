/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.router;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.math.NumberUtils;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.ratelimiter.LocalRateLimiter;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;

/**
 * @author weiping wang
 *
 */
public final class DspRateLimiters {
	private static final ConcurrentMap<String, DspRateLimiter> qpsLimiters = new ConcurrentHashMap<>();

	public static void removeRateLimiter(String dspId) {
		qpsLimiters.remove(dspId);
	}

	public static DspRateLimiter get(Dsp dsp) {
		DspRateLimiter rateLimiter = qpsLimiters.get(dsp.getDspId());
		if (rateLimiter != null)
			return rateLimiter;

		return null;
	}

	public static synchronized void createOrUpdateRateLimiter(Dsp dsp) {
		if (dsp == null)
			return;
		if (dsp.getQps() <= 0) {
			qpsLimiters.remove(dsp.getDspId());
			return;
		}

		int adNgxServerCount = NumberUtils.toInt(MinervaCfg.get().getConfigProperty("adngx.server.count"), 5);
		int perAdNgxQps = (int) Math.ceil((float) dsp.getQps() / adNgxServerCount);
		DspRateLimiter rateLimiter = new LocalRateLimiter(perAdNgxQps);
		
		qpsLimiters.put(dsp.getDspId(), rateLimiter);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Math.ceil(1f / 3));
	}
}

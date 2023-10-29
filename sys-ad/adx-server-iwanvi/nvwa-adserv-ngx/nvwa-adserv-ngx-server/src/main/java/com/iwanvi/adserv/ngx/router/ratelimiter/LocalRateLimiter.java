package com.iwanvi.adserv.ngx.router.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import com.iwanvi.adserv.ngx.router.DspRateLimiter;

public class LocalRateLimiter implements DspRateLimiter {
	private final RateLimiter internalRateLimiter;

	public LocalRateLimiter(int qps) {
		this.internalRateLimiter = RateLimiter.create(qps);
	}

	@Override
	public boolean tryAcquire() {
		return this.internalRateLimiter.tryAcquire();
	}
}

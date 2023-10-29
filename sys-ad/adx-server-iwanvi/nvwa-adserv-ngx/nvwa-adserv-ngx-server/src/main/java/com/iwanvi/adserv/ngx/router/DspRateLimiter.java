/**
 * 
 */
package com.iwanvi.adserv.ngx.router;

/**
 * @author wangweiping
 *
 */
public interface DspRateLimiter {
	boolean tryAcquire();
}

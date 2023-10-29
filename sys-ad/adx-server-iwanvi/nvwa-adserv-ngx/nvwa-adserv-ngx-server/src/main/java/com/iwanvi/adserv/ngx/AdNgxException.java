/**
 * 
 */
package com.iwanvi.adserv.ngx;

/**
 * @author wangweiping
 *
 */
public class AdNgxException extends RuntimeException {
	private static final long serialVersionUID = 8706639051504779817L;

	public AdNgxException() {
		super();
	}

	public AdNgxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AdNgxException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdNgxException(String message) {
		super(message);
	}

	public AdNgxException(Throwable cause) {
		super(cause);
	}
}

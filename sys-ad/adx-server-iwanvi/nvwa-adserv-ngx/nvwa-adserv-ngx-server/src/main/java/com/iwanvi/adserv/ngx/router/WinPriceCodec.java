/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.router;

/**
 * @author weiping wang
 *
 */
public interface WinPriceCodec {
	double decode(String price, String token);

	String encode(double price, String token);
}

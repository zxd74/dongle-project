/**
 * 
 */
package com.iwanvi.adserv.ngx.router.broker.toutiao;

import java.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import com.iwanvi.adserv.ngx.util.OpenRtbCrypto.Keys;
import com.iwanvi.adserv.ngx.util.OpenRtbCrypto.Price;

/**
 * @author wangweiping
 *
 */
public class ToutiaoWinPriceCodec implements WinPriceCodec {
	private static final Logger LOG = LoggerFactory.getLogger(ToutiaoWinPriceCodec.class);
	
	private static final String ekey = MinervaCfg.get().getConfigProperty("toutiao.ekey");
	private static final String ikey = MinervaCfg.get().getConfigProperty("toutiao.ikey");

	private static final String algorithm = "HmacSHA1";

	private static Price priceCodec = null;
	static {
		try {
			SecretKeySpec eKey = new SecretKeySpec(ekey.getBytes(), algorithm);
			SecretKeySpec iKey = new SecretKeySpec(ikey.getBytes(), algorithm);

			priceCodec = new Price(new Keys(eKey, iKey));
		} catch (Exception ex) {
			LOG.error("初始化价格编码器失败",ex);
		}
	}

	@Override
	public double decode(String price, String token) {
		try {
			return (double) priceCodec.decodePriceMicros(price);
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return 0D;
	}

	@Override
	public String encode(double price, String token) {
		return priceCodec.encodePriceMicros((long)price, null);
	}

}

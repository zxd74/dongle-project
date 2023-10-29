/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.router;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.adserv.ngx.router.DspRouter.DspBid;
import com.iwanvi.adserv.ngx.router.api.DspBroker;
import com.iwanvi.adserv.ngx.router.api.DspBrokerRegistry;
import com.iwanvi.adserv.ngx.util.NgxServices;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

/**
 * 
 * @author wangwp
 */
public class DspMacros {
	public static final String AUCTION_ID = "{AUCTION_ID}";

	public static final String AUCTION_BID_ID = "{AUCTION_BID_ID}";

	public static final String AUCTION_IMP_ID = "{AUCTION_IMP_ID}";

	public static final String AUCTION_PRICE = "{AUCTION_PRICE}";

	public static final String AUCTION_TIME = "{AUCTION_TIME}";

	public static String replaceMacros(String url, BiddingReq req, DspBid dspBid) {
		if (StringUtils.isBlank(url) || !StringUtils.contains(url, "{")) {
			return url;
		}
		AdMsg.Builder bid = dspBid.getBid();
		String price = String.valueOf(bid.getBidPrice());
		String auctionTime = String.valueOf(System.currentTimeMillis());

		// 如果来自第三方广告平台的响应, 需要用对价格宏进行加密处理,内部dsp直接明文返回
		if (dspBid.getDsp() != null) {
			DspBroker dspBroker = DspBrokerRegistry.getDspBroker(dspBid.getDsp().getDspId());
			if (dspBroker == null) {
				price = NgxServices.getWinPriceCodec().encode(bid.getBidPrice(), dspBid.getDsp().getToken());
			} else {
				WinPriceCodec codec = dspBroker.getWinPriceCodec();
				if (codec != null)
					price = codec.encode(bid.getBidPrice(), null);
				else
					price = String.valueOf(bid.getBidPrice());
			}
		}

		return url.replace(AUCTION_ID, req.getId()).replace(AUCTION_BID_ID, bid.getId())
				.replace(AUCTION_IMP_ID, bid.getImpid()).replace(AUCTION_PRICE, price)
				.replace(AUCTION_TIME, auctionTime);
	}
}

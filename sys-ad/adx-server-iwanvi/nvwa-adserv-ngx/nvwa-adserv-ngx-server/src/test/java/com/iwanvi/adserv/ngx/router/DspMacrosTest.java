package com.iwanvi.adserv.ngx.router;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.iwanvi.adserv.ngx.router.DspRouter.DspBid;
import com.iwanvi.adserv.ngx.util.NgxServices;
import com.iwanvi.adserv.ngx.util.UUIDUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

public class DspMacrosTest {

	//@Test
	public void testReplaceMacros() {
		String uuid = UUIDUtils.uuid(); // 请求id
		String token = UUIDUtils.uuid(); // 加密token
		String dspId = "JkUVrY"; // dspId

		BiddingReq biddingReq = BiddingReq.newBuilder().setId(uuid).build();
		DspBid dspBid = DspBid.create(Dsp.newBuilder().setDspId(dspId).setToken(token).build(),
				AdMsg.newBuilder().setBidPrice(500), 300d);

		String encodedPrice = NgxServices.getWinPriceCodec().encode(500, token);
		double price = NgxServices.getWinPriceCodec().decode(encodedPrice, token);

		assertEquals(500d, price, 0);

		String url = "{AUCTION_PRICE}";
		String replacedMacrosUrl = DspMacros.replaceMacros(url, biddingReq, dspBid);

		assertNotNull(url);
		assertEquals(encodedPrice, replacedMacrosUrl);
	}
}

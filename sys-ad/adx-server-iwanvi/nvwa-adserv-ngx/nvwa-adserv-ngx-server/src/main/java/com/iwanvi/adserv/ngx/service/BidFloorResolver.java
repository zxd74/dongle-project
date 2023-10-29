/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.service;

import com.iwanvi.adserv.ngx.service.impl.PrivateProtoDspFloorResolver;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

/**
 * dsp竞价底价获取器
 * 
 * @author weiping wang
 *
 */
public interface BidFloorResolver {
	BidFloorResolver PRIVATE_PROTO_DSP_BID_FLOOR_RESOLVER = new PrivateProtoDspFloorResolver();
	
	double getBidFloor(BiddingReq biddingReq, AdMsg bid, String dspId);
}

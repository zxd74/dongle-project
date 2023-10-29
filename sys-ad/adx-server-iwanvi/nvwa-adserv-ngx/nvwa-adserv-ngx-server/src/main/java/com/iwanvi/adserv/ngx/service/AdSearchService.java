/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.service;

import java.util.List;

import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

/**
 * @author weiping wang
 *
 */
public interface AdSearchService {
	List<AdMsg> getAds(BiddingReq adReq);
}

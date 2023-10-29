package com.iwanvi.nvwa.openapi.dsp.service;

import java.util.Map;

import com.iwanvi.nvwa.openapi.dsp.model.AdRequest;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;

public interface GeneralService {
	
	Map<String, String> getMonitorUrlMap(
			AdRequest adRequest, AdMsg adMsg, UserInfo userInfo, PosInfo posInfo);
	
}

package com.iwanvi.nvwa.openapi.dsp.service;

import java.util.List;

import com.iwanvi.nvwa.openapi.dsp.model.AdRequest;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse.Platform;

public interface TerminalService {

	void test();
	
	String getAds(AdRequest adRequest);
	
	AdResponse getAdResponse(AdRequest adRequest);

	List<Platform> getPlatform4period(String appId, String posId, String channel, String version, String did);
	
	List<Platform> getPlatform4Fixed(String appId, 
			String posId, String channel, String version, String did);
	
	String getConf(String appId);
}

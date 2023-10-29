package com.iwanvi.nvwa.pixel.connector.clk.service;

import java.util.Map;

public interface ClickService {

	String click(String appId, String cid, String did, Map<String, Object> map);
	
	String dspClick(String appId, String dspId, String cid, String p);
}

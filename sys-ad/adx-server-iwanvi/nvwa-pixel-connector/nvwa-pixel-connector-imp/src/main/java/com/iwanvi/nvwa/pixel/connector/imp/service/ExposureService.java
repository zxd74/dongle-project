package com.iwanvi.nvwa.pixel.connector.imp.service;

import java.util.Map;

public interface ExposureService {

	String exposure(String appId, String cid, String did, Map<String, Object> map);
	
	String dspExposure(String appId, String dspId, String cid, String p);
}

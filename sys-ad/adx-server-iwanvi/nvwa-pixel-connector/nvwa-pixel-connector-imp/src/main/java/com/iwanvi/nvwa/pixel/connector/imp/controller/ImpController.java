package com.iwanvi.nvwa.pixel.connector.imp.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.MD5Utils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.LogUtils;
import com.iwanvi.nvwa.pixel.connector.imp.service.ExposureService;

import ai.houyi.dorado.rest.annotation.Controller;
import ai.houyi.dorado.rest.annotation.Path;
import ai.houyi.dorado.rest.http.HttpRequest;
import ai.houyi.dorado.rest.util.IOUtils;

@Controller
@Path("/exp")
public class ImpController {

	private static final Logger LOG = LoggerFactory.getLogger("ImpController");

	@Resource
	private ExposureService exposureService;
	
	@Path("/m")
	public String monitor(String appId, String cid, String did, String p, String sign) {
		String result = null;
		try {
			LOG.info("monitor. appId: {}, cid: {}, did: {}, p: {}, sign: {}", appId, cid, did, p, sign);
			String signNow = MD5Utils.MD5(StringUtils.concat(appId, cid, did, p, Constants.KEY_ENCODE));
			if (!signNow.equals(sign)) {
				LOG.warn("============monitor IllegalArgumentException=========appId: {}, cid: {}", appId, cid);
				return JsonUtils.PARAMETER_ERROR();
			}
			
			String clearParam = new String(Base64.decodeBase64(p.getBytes()));
			Map<String, Object> map = JsonUtils.parse2Map(clearParam);
			if(map == null || map.isEmpty()){
				LOG.warn("monitor exception. map is null. appId: {}, cid: {}", appId, cid);
				return JsonUtils.PARAMETER_ERROR();
			}
			LogUtils.getInstance().printLog(map, 1);
			result = exposureService.exposure(appId, cid, did, map);
		} catch (Exception e) {
			LOG.error("monitor error. appId: {}, cid: {}, did: {}, p: {}", appId, cid, did, p, e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@Path("/dm")
	public String dspMonitor(String appId, String dspId, String cid, String p, String sign) {
		String result = null;
		try {
			LOG.info("dspMonitor. appId: {}, dspId: {}, cid: {}, p: {}, sign: {}", appId, dspId, cid, p, sign);
			String signNow = MD5Utils.MD5(StringUtils.concat(appId, dspId, cid, p, Constants.KEY_ENCODE));
			if (!signNow.equals(sign)) {
				LOG.warn("============dspMonitor IllegalArgumentException=========dspId: {}, cid: {}", dspId, cid);
				return JsonUtils.PARAMETER_ERROR();
			}
			String clearParam = new String(Base64.decodeBase64(p.getBytes()));
			Map<String, Object> map = JsonUtils.parse2Map(clearParam);
			if(map == null || map.isEmpty()){
				LOG.warn("dspMonitor exception. map is null. appId: {}, cid: {}", appId, cid);
				return JsonUtils.PARAMETER_ERROR();
			}
			LogUtils.getInstance().printLog(map, 1);
			result = exposureService.dspExposure(appId, dspId, cid, p);
		} catch (Exception e) {
			LOG.error("dspMonitor error. appId: {}, dspId: {}, cid: {}, p: {}", appId, dspId, cid, p, e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}

	@Path("/monitor")
	public String monitor1(HttpRequest request) {
		String json = null;
		try {
			json = IOUtils.toString(request.getInputStream(), "utf-8");

			LOG.info("monitor param. json={}", json);

		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("json: {}", json);
		return "json: " + json;
	}
}

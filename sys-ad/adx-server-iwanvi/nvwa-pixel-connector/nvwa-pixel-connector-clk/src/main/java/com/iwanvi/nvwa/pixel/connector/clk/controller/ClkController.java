package com.iwanvi.nvwa.pixel.connector.clk.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.MD5Utils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.pixel.connector.clk.service.ClickService;
import com.iwanvi.nvwa.pixel.connector.common.utils.LogUtils;

import ai.houyi.dorado.rest.annotation.Controller;
import ai.houyi.dorado.rest.annotation.Path;
import ai.houyi.dorado.rest.http.HttpResponse;

@Controller
@Path("/clk")
public class ClkController {

	private static final Logger LOG = LoggerFactory.getLogger("ClkController");

	@Resource
	private ClickService clickService;
	
	@Path("/m")
	public String monitor(String appId, String cid, String did, String p, String sign, HttpResponse response) {
		String result = null;
		try {
			LOG.info("monitor. appId: {}, cid: {}, did: {}, p: {}", appId, cid, did, p);
			String signNow = MD5Utils.MD5(StringUtils.concat(appId, cid, did, p, Constants.KEY_ENCODE));
			if (!signNow.equals(sign)) {
				LOG.warn("============monitor IllegalArgumentException=========");
				return JsonUtils.PARAMETER_ERROR();
			}
			String clearParam = new String(Base64.decodeBase64(p.getBytes()));
			Map<String, Object> map = JsonUtils.parse2Map(clearParam);
			if (map == null || map.isEmpty()) {
				LOG.warn("monitor exception. map is null. appId: {}, cid: {}", appId, cid);
				return JsonUtils.PARAMETER_ERROR();
			}

			LogUtils.getInstance().printLog(map, 2);
			result = clickService.click(appId, cid, did, map);
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
				LOG.warn("============dspMonitor IllegalArgumentException=========");
				return JsonUtils.PARAMETER_ERROR();
			}
			String clearParam = new String(Base64.decodeBase64(p.getBytes()));
			Map<String, Object> map = JsonUtils.parse2Map(clearParam);
			if (map == null || map.isEmpty()) {
				LOG.warn("dspMonitor exception. map is null. appId: {}, cid: {}", appId, cid);
				return JsonUtils.PARAMETER_ERROR();
			}

			LogUtils.getInstance().printLog(map, 2);
			result = clickService.dspClick(appId, dspId, cid, p);
		} catch (Exception e) {
			LOG.error("dspMonitor error. appId: {}, dspId: {}, cid: {}, p: {}", appId, dspId, cid, p, e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
}

package com.iwanvi.nvwa.pixel.connector.common.utils;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.utils.ApiLog;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.pixel.connector.common.service.PixelService;
import com.iwanvi.nvwa.pixel.connector.common.utils.IPUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.common.utils.ThreadDistribution;

import ai.houyi.dorado.spring.SpringContainer;

public class LogUtils {

	private static final Logger LOG = LoggerFactory.getLogger("LogUtils");

	private static final Logger LOG_EXPOSURE = LoggerFactory.getLogger("exp");
	private static final Logger LOG_CLICK = LoggerFactory.getLogger("clk");
	
	private PixelService pixelService = (PixelService)SpringContainer.get().getBean(PixelService.class);
	
	private static LogUtils instance = null;
	
	private LogUtils() {

	}

	synchronized public static LogUtils getInstance() {
		if (instance == null) {
			instance = new LogUtils();
		}
		return instance;
	}
	
	/**
	 * monitorType 监测类型，1：曝光，2：点击
	 */
	public void printLog(final Map<String, Object> map, final int monitorType) {
		try {
			ThreadDistribution.getInstance().doWork(new Runnable() {
				@Override
				public void run() {
					try {
						pixelService.count(map, monitorType);
						
						String ip = (String)map.get("ip");
						ApiLog.Builder builder = 
								ApiLog.builder().withTimestamp(DateUtils.formatPure(new Date()))
								.withReqId((String)map.get("reqId"))
								.withAppId((String)map.get("appId"))
								.withPosId((String)map.get("posId"))
								.withChannel1((String)map.get("channel"))
								.withChannel2((String)map.get("channel2"))
								.withDid((String)map.get("did"))
								.withIp(ip)
								.withOs(PixelConstants.OS_MAP.get((Integer)map.get("os")))
								.withVesion((String)map.get("version"))
								.withAreacode(String.valueOf(IPUtils.getInstance().getAreaCode(ip)));
						JSONObject extObject = JSONObject.parseObject(NvwaUtils.obj2Empty(map.get("appExt")));
						if (extObject != null) {
							builder.withLev1(String.valueOf(extObject.getString("lev1")))
								.withLev2(String.valueOf(extObject.getString("lev2")))
								.withLev3(String.valueOf(extObject.getString("lev3")));
						}
						String logStr = builder.build().toString();
						logStr = logStr.replaceAll("null", StringUtils.EMPTY);
						
						Logger log = monitorType == 1 ? LOG_EXPOSURE : LOG_CLICK;
						log.info(StringUtils.buildString(PixelConstants.LOG_FORMAT_MONITOR, 
								logStr, (String)map.get("dspId"), NvwaUtils.obj2Empty(map.get("cost"))));
					} catch (Exception e) {
						LOG.error("ThreadDistribution printLog error. ", e);
					}
				}
			});
		} catch (Exception e) {
			LOG.error("printLog error. ", e);
		}
	}
}

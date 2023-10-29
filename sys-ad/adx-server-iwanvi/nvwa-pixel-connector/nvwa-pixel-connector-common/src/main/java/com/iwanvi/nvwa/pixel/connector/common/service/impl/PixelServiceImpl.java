package com.iwanvi.nvwa.pixel.connector.common.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.HttpClientUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.pixel.connector.common.service.AliyunLogService;
import com.iwanvi.nvwa.pixel.connector.common.service.PixelService;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.ThreadDistribution;

import redis.clients.jedis.Pipeline;

@Service("pixelService")
public class PixelServiceImpl implements PixelService {

	private static final Logger LOG = LoggerFactory.getLogger("PixelService");
	
	private static final Pattern _PATTERN = Pattern.compile("(\\((.+?)\\))");
	
	private static final String KEY_REDIS_MONITOR_URL = "url:monitor:{0}"; // 创意的监测地址信息 url:monitor:{creativeId}
	
	@Resource
	private RedisDao redisDao;
	
	/**
	 * 上报广告主（回调第三方监测地址）
	 * monitorType 监测类型，1：曝光，2：点击
	 */
	@Override
	public void reportCustomer(Map<String, Object> paramMap, int monitorType) {
		try {
			String monitorInfoKey = StringUtils.buildString(KEY_REDIS_MONITOR_URL, StringUtils.trim(paramMap.get("cid")));
			String monitorInfoValue = redisDao.get(monitorInfoKey);
			
			if(StringUtils.isBlank(monitorInfoValue)) {
				LOG.warn("reportCustomer exception. monitorInfoValue is null. monitorInfoKey: {}", monitorInfoKey);
				return;
			}
			
			String monitorUrl = null;
			switch (monitorType) {
			case 1: // 曝光
				monitorUrl = (String) JSONObject.parseObject(monitorInfoValue).get("impMonitorUrl");
				break;
			case 2: // 点击
				monitorUrl = (String) JSONObject.parseObject(monitorInfoValue).get("clkMonitorUrl");
				break;
			default:
				LOG.warn("reportCustomer exception. monitorUrl is null. monitorInfoKey: {}", monitorInfoKey);
				return;
			}
			
			reportAder(paramMap, monitorUrl);
		} catch (Exception e) {
			LOG.error("reportCustomer error. ", e);
		}
	}
	
	private void reportAder(Map<String, Object> map, String cacheUrl) {
		try {
			if(StringUtils.isBlank(cacheUrl)) {
				LOG.warn("reportAder exception. cacheUrl is null. cacheUrl: {}", cacheUrl);
				return;
			}
			String idfa = StringUtils.EMPTY;
			String imei = StringUtils.EMPTY;
			int os = NvwaUtils.obj2int(map.get("os"));
			if (os == Constants.INTEGER_1) {
				idfa = StringUtils.trim(map.get("deviceId"));
			} else if (os == Constants.INTEGER_2) {
				imei = StringUtils.trim(map.get("deviceId"));
			} else {
				idfa = StringUtils.trim(map.get("deviceId"));
				imei = StringUtils.trim(map.get("deviceId"));
			}
			reportAder(map, cacheUrl, idfa, imei);
		} catch (Exception e) {
			LOG.error("reportAder error. ", e);
		}
	}
	private static void reportAder(Map<String, Object> map, String cacheUrl, String idfa, String imei) {
		try {
			StringBuffer monitorUrlBuffer, tempBuffer = null;
			String aderId = StringUtils.trim(map.get("aderId"));
			String rid = StringUtils.trim(map.get("id"));
			String cid = StringUtils.trim(map.get("cid"));
			String dt = StringUtils.trim(map.get("dt"));
			int os = NvwaUtils.obj2int(map.get("os"));
			String ip = StringUtils.trim(map.get("ip"));
			long ts = System.currentTimeMillis();
			String ua = URLEncoder.encode(NvwaUtils.obj2Empty(map.get("ua")), Constants.DEFAULT_ENCODING);
			
			String tempUrl = StringUtils.EMPTY;
			String[] monitorUrlArray = cacheUrl.split(Constants.SIGN_SPLIT);
			for (int i = 0; i < monitorUrlArray.length; i++) {
				tempUrl = monitorUrlArray[i];
				tempUrl = tempUrl.replace("%%OS%%", StringUtils.trim(os)).replace("%%IMEI%%", imei)
						.replace("%%IDFA%%", idfa).replace("%%IP%%", ip).replace("%%TS%%", StringUtils.trim(ts))
						.replace("%%CID%%", cid).replace("%%DT%%", dt)
						.replace("%%UA%%", ua).replace("%%ADERID%%", aderId).replace("%%RID%%", rid);
				tempBuffer = new StringBuffer(tempUrl);
				if(tempBuffer.indexOf("(") >= 0 && tempBuffer.indexOf(")") >= 0) {
					monitorUrlBuffer = new StringBuffer();
					Matcher matcher = _PATTERN.matcher(tempBuffer);
					while (matcher.find()) {
						matcher.appendReplacement(monitorUrlBuffer, URLEncoder.encode(matcher.group(2), Constants.DEFAULT_ENCODING));
					}	
					matcher.appendTail(monitorUrlBuffer);
				} else {
					monitorUrlBuffer = tempBuffer;
				}
				reportAder(monitorUrlBuffer.toString(), aderId);
			}
		} catch (Exception e) {
			LOG.error("buildMonitorUrl error. ", e);
		}
	}
	
	private static void reportAder(final String reportUrl, final String aderId){
		LOG.info("reportAder. aderId: {}. reportUrl: {}", aderId, reportUrl);
		if(StringUtils.isBlank(reportUrl)){
			return;
		}
		ThreadDistribution.getInstance().doWork(new Runnable() {
			@Override
			public void run() {
				HttpClientUtils.get(reportUrl);
			}
		});
	}
	
	@Override
	public void buildPipelineIncr(Pipeline pipeline, String templeteKey, 
			String quota, Object id, String day, String hour, Object incrValueTemp) {
		try {
			if (StringUtils.isBlank(NvwaUtils.obj2Empty(id))) {
				return;
			}
			long incrValue = NvwaUtils.obj2long(incrValueTemp);
			if (incrValue == 0) {
				return;
			}
			if (NvwaUtils.obj2Empty(id).equals(String.valueOf(Constants.INTEGER_0))) {
				return;
			}
			String key = StringUtils.buildString(templeteKey, quota, id, day, hour);
			pipeline.incrBy(key, incrValue);
			
			pipeline.expire(key, 604800);
		} catch (Exception e) {
			LOG.error("buildPipelineIncr error. ", e);
		}
	}
	
	/**
	 * type 1:exp,2:clk
	 */
	public void count(Map<String, Object> map, int type) {
		try {
			String channel = (String)map.get("channel");
			String version = (String)map.get("version");
			String did = NvwaUtils.obj2Empty(map.get("did"));
			String appId = NvwaUtils.obj2Empty(map.get("appId"));
			String posId = NvwaUtils.obj2Empty(map.get("posId"));
			
			String partKey = StringUtils.buildString(
					Constants.FORMMAT_COUNT_KEY, appId, posId, channel, version);
			String day = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
			String hour = DateUtils.format(new Date(), DateUtils.FORMAT_HOUR);
			
			String quotaUv = type == 1 ? Constants.QUOTA_DISPLAYUV : Constants.QUOTA_CLICKUV;
			String quota = type == 1 ? Constants.QUOTA_DISPLAY : Constants.QUOTA_CLICK;
			
			redisIncr(StringUtils.buildString(
					Constants.KEY_REDIS_MEDIA_QUOTA_HOUR, quota, Constants.IWANVI_UUID, day, hour));

			redisIncr(StringUtils.buildString(
					Constants.KEY_REDIS_ADPOSITION_QUOTA_HOUR, quota, posId, day, hour));
			
			if (!partKey.contains(Constants.SIGN_COLON_DOUBLE)) {
//				if (StringUtils.isNotBlank(did)) {
//					redisDao.pfAdd(StringUtils.buildString(
//							Constants.KEY_REDIS_MEDIA_QUOTA, quotaUv, partKey, day), did);
//				}
				
				redisIncr(StringUtils.buildString(
						Constants.KEY_REDIS_MEDIA_QUOTA, quota, partKey, day));
			}

			// 书目级别
			JSONObject extObject = JSONObject.parseObject(NvwaUtils.obj2Empty(map.get("appExt")));
			extObject = extObject == null ? new JSONObject() : extObject;
			partKey = StringUtils.buildString(Constants.FORMMAT_CATALOG_COUNT_KEY, 
					NvwaUtils.obj2Empty(extObject.getString("lev1")), 
					NvwaUtils.obj2Empty(extObject.getString("lev2")), 
					NvwaUtils.obj2Empty(extObject.getString("lev3")));
			
			if (!partKey.contains(Constants.SIGN_COLON_DOUBLE)) {
				redisIncr(StringUtils.buildString(
						Constants.KEY_REDIS_CATALOG_QUOTA, quota, partKey, day));
//				if (StringUtils.isNotBlank(did)) {
//					redisDao.pfAdd(StringUtils.buildString(
//							Constants.KEY_REDIS_CATALOG_QUOTA, quotaUv, partKey, day), did);
//				}
			}
		} catch (Exception e) {
			LOG.error("count error. ", e);
		}
	}
	
	public void redisIncr(String key) {
		try {
			redisDao.incr(key);
			redisDao.expire(key, 604800, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOG.error("saveToRedis error. ", e);
		}
	}

	public List<String> getQueryQuotaList(String logStore, String distinctItem, int from, int to) {
		List<String> list = null;
		try {
			for (int i = 0; i < 3; i++) {
				list = AliyunLogService.getDistinctItem(logStore, Constants.SIGN_ASTERISK, distinctItem, from, to);
				if (CollectionUtils.isNotEmpty(list)) {
					break;
				}
			}
		} catch (Exception e) {
			LOG.warn("dspPlatformTaskService getQueryQuotaList. distinctItem: {}", distinctItem);
		}
		return list;
	}
}

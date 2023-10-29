package com.iwanvi.nvwa.openapi.dsp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.openapi.dsp.model.SyncAdPosition;
import com.iwanvi.nvwa.openapi.dsp.utils.Constants;
import com.iwanvi.nvwa.openapi.dsp.utils.NvwaUtils;

@Component("dspTask")
public class DspTask {

	private static final Logger LOG = LoggerFactory.getLogger("DspTask");
	
	@Resource
	private RedisDao redisDao;
	
	/**
	 * 请求量归档
	 */
	private void fileReqCount(String day, String hour) {
		try {
			long temp = 0L;
			String redisKey = null;
			LOG.debug("fileReqCount start-------");
			for (String key : Constants.REQ_COUNT.keySet()) {
				LOG.debug("fileReqCount==========={}", key);
				temp = Constants.REQ_COUNT.get(key).getAndSet(0l);
				LOG.debug("fileReqCount temp-------" + temp);
				if (temp > 0) {
					if (key.equalsIgnoreCase(com.iwanvi.nvwa.common.utils.Constants.IWANVI_UUID)) {
						redisKey = StringUtils.buildString(
								com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_MEDIA_QUOTA_HOUR,
								Constants.QUOTA_REQ, key, day, hour);
					} else {
						redisKey = StringUtils.buildString(
								com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_ADPOSITION_QUOTA_HOUR, 
								Constants.QUOTA_REQ, key, day, hour);
					}
					redisDao.incr(redisKey, temp);
					redisDao.expire(redisKey, 604800, TimeUnit.SECONDS);
				}
			}
			
//			LOG.debug("fileReqUVCount start-------");
//			long l1 = System.currentTimeMillis();
//			Set<String> tempSet = null;
//			for (Map.Entry<String, Set<String>> entry : Constants.REQ_DID_MAP.entrySet()) {
//				LOG.debug("fileReqUVCount==========={}", entry.getKey());
//				tempSet = new HashSet<String>();
//				synchronized ("") {
//					tempSet.addAll(entry.getValue());
//					entry.getValue().clear();
//				}
//				for (String did : tempSet) {
//					redisDao.pfAdd(entry.getKey(), did);
//				}
//				tempSet.clear(); tempSet = null;
//			}
//			
//			LOG.debug("fileReqUVCount end. elpased: {}-------", (System.currentTimeMillis() - l1));
			
			LOG.debug("fileReqAdPostionCount start-------");
		} catch (Exception e) {
			LOG.error("fileReqCount error. ", e);
		}
	}
	
	/**
	 * 目录级别请求数归档
	 * @param day
	 */
	private void fileCataLogCount(String day) {
		try {
			LOG.debug("fileCataLogCount start-------");
			HashMap<String, AtomicLong> reqCatalogCountMap = new HashMap<>();
			synchronized (reqCatalogCountMap) {
				long temp = 0L;
				reqCatalogCountMap.putAll(Constants.REQ_CATALOG_COUNT);
				Constants.REQ_CATALOG_COUNT.clear();
				String redisKey = null;
				for (String key : reqCatalogCountMap.keySet()) {
					temp = reqCatalogCountMap.get(key).getAndSet(0l);
					LOG.debug("fileCataLogCount temp-------" + temp);
					if (temp > 0) {
						redisKey = StringUtils.buildString(
								com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_QUOTA,
								Constants.QUOTA_REQ, key, day);
						redisDao.incr(redisKey, temp);
					}
				}
			}
			reqCatalogCountMap.clear(); reqCatalogCountMap = null;
			
//			// 书目
//			LOG.debug("fileReqCataUVCount start-------");
//			long l1 = System.currentTimeMillis();
//			Set<String> tempSet = null;
//			for (Map.Entry<String, Set<String>> entry : Constants.REQ_CATALOG_DID_MAP.entrySet()) {
//				LOG.debug("fileReqCataUVCount==========={}", entry.getKey());
//				tempSet = new HashSet<String>();
//				synchronized ("") {
//					tempSet.addAll(entry.getValue());
//					entry.getValue().clear();
//				}
//				for (String did : tempSet) {
//					redisDao.pfAdd(entry.getKey(), did);
//				}
//				tempSet.clear(); tempSet = null;
//			}
//
//			tempSet = new HashSet<String>();
//			synchronized (tempSet) {
//				tempSet.addAll(Count.getInstance().getCataLogReqPartKeySet());
//				Count.getInstance().clearReqPartKeySet();
//				for (String partKey : tempSet) {
//					redisDao.setAdd(com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_KEYSET, partKey);
//				}
//			}
//			
//			LOG.debug("fileReqCataUVCount end. elpased: {}-------", (System.currentTimeMillis() - l1));
		} catch (Exception e) {
			LOG.error("fileCataLogCount error. ", e);
		}
	}
	
	/**
	 * 竞价数归档
	 * @param day
	 */
	private void fileBidCount(String day) {
		try {
			LOG.debug("fileBidCount start-------");
			long temp = 0L;
			for (String key : Constants.BID_COUNT.keySet()) {
				temp = Constants.BID_COUNT.get(key).getAndSet(0l);
				LOG.debug("fileBidCount temp-------" + temp);
				if (temp > 0) {
					redisDao.incr(StringUtils.buildString(
							com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_MEDIA_QUOTA,
							Constants.QUOTA_BID, key, day), temp);
				}
			}
		} catch (Exception e) {
			LOG.error("fileBidCount error. ", e);
		}
	}
	
	/**
	 * 更新内存中广告位信息
	 * 业务逻辑：每分钟更新广告位信息
	 */
	@PostConstruct
	public void updateAdPostion() {
		try {
			String key, adPositionValue = null;
			SyncAdPosition adPosition = null;
			for(String posId : Constants.AD_POSITION_UUID_SET) {
				key = StringUtils.buildString(Constants.KEY_REDIS_AD_POSITION, posId);
				adPositionValue = NvwaUtils.obj2Empty(redisDao.get(key));
				if (StringUtils.isBlank(adPositionValue)) {
					continue;
				}
				adPosition = JsonUtils.TO_OBJ(adPositionValue, SyncAdPosition.class);
				Constants.AD_POSITION_MAP.put(posId, adPosition);
				
				key = StringUtils.buildString(
						com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_HASH_SDKALLOTMENT, posId);
				Set<String> fields = redisDao.hashKeys(key);
				List<String> jsonList = redisDao.hashMultiGet(key, new ArrayList<>(fields));
				
				Constants.SDK_ALLOTMENT.put(posId, jsonList);
			}
			
			updateSdkAllotment();
			LOG.info("updateAdPostion============{}", JsonUtils.TO_JSON(Constants.SDK_ALLOTMENT));
		} catch (Exception e) {
			LOG.error("updateAdPostion error. ", e);
		}
	}
	
	/**
	 * 更新内存中 流量控制/禁投日期/预警设置信息
	 * 业务逻辑：每分钟更新
	 */
	public void updateSdkAllotment() {
		try {
			//禁投日期
			Constants.FORBIDDEN_DATE.clear();
			String value = redisDao.get(com.iwanvi.nvwa.common.utils.Constants.KEY_PROHIBITED_DATE);
			List<String> list = null;
			if (StringUtils.isNotBlank(value)) {
				list = JSONObject.parseArray(value, String.class);
				Constants.FORBIDDEN_DATE.addAll(list);
			}
			
			//SDK平台流量限制
			Constants.PLATFORM_LIMIT.clear();
			value = redisDao.get(com.iwanvi.nvwa.common.utils.Constants.KEY_FLOW_CONTROL);
			if (StringUtils.isNotBlank(value)) {
				list = JSONObject.parseArray(value, String.class);
				Constants.PLATFORM_LIMIT.addAll(list);
			}
		} catch (Exception e) {
			LOG.error("updateSdkAllotment error. ", e);
		}
	}
	
	/**
	 * 归档计数
	 * 每10秒执行一次
	 */
	public void count() {
		try {
			long l1 = System.currentTimeMillis();
			String day = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
			String hour = DateUtils.format(new Date(), DateUtils.FORMAT_HOUR);
			
			fileReqCount(day, hour);
			fileCataLogCount(day);
			fileBidCount(day);
			LOG.debug("count complete. elapsed: {}", (System.currentTimeMillis() - l1));
		} catch (Exception e) {
			LOG.error("count error. ");
		}
	}
	
	/**
	 * 清除前一天的内存数据
	 */
	public void clear() {
		try {
			long l1 = System.currentTimeMillis();
			Date preDay = DateUtils.getPreDayDate(new Date());
			String day = DateUtils.format(preDay, DateUtils.SHORT_FORMAT);
			
			HashMap<String, Set<String>> tempMap = new HashMap<String, Set<String>>();
			tempMap.putAll(Constants.REQ_DID_MAP);
			
			for (Map.Entry<String, Set<String>> entry : tempMap.entrySet()) {
				if (entry.getKey().contains(day)) {
					Constants.REQ_DID_MAP.remove(entry.getKey());
				}
			}
			tempMap.clear();
			
			tempMap.putAll(Constants.REQ_CATALOG_DID_MAP);
			for (Map.Entry<String, Set<String>> entry : tempMap.entrySet()) {
				if (entry.getKey().contains(day)) {
					Constants.REQ_CATALOG_DID_MAP.remove(entry.getKey());
				}
			}

			tempMap.clear(); tempMap = null;
			LOG.info("clear complete. elapsed: {}", (System.currentTimeMillis() - l1));
		} catch (Exception e) {
			LOG.error("clear error. ");
		}
	}

	@PostConstruct
	public void JVMHook() {
		try {
			LOG.info("JVMHook running-------");
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					count();

					LOG.debug("fileReqAdPostionCount start-------");
					Constants.AD_POSITION_UUID_SET.clear();
					LOG.info("-----------hook is execute-------");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

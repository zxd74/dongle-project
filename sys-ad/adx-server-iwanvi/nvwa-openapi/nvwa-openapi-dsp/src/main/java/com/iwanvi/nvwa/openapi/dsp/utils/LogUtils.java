package com.iwanvi.nvwa.openapi.dsp.utils;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.ApiLog;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.App;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.Device;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.Imp;
import com.iwanvi.nvwa.openapi.dsp.model.Count;

import ai.houyi.dorado.spring.SpringContainer;

public class LogUtils {

	private static final Logger LOG = LoggerFactory.getLogger("LogUtils");
	private static final Logger LOG_REQ = LoggerFactory.getLogger("req");
	private static final Logger LOG_BID = LoggerFactory.getLogger("bid");
	
	// bid_time|rid|elapsed|appId|posId|dspId|cid|os|bidprice
	private static final String LOG_FORMAT_BID = "{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}";

	private static LogUtils instance = null;
	
	private RedisDao redisDao = (RedisDao)SpringContainer.get().getBean(RedisDao.class);

	private LogUtils() {

	}

	synchronized public static LogUtils getInstance() {
		if (instance == null) {
			instance = new LogUtils();
		}
		return instance;
	}
	
	public void printReqLog(final AdRequest adRequest) {
		try {
			ThreadDistribution.getInstance().doWork(new Runnable() {
				@Override
				public void run() {
					try {
						App app = adRequest.getApp();
						Device device = adRequest.getDevice();
						String posId = StringUtils.EMPTY;
						for (Imp imp : adRequest.getImps()) {
							posId = imp.getPosId();
							break;
						}
						String ip = device.getIp();
						ApiLog.Builder builder = 
								ApiLog.builder().withTimestamp(DateUtils.formatPure(new Date()))
								.withReqId(adRequest.getId())
								.withAppId(app.getAppId())
								.withPosId(posId)
								.withChannel1(app.getChannel())
								.withChannel2(app.getChannel2())
								.withDid(device.getDid())
								.withIp(ip)
								.withOs(Constants.OS_MAP.get(device.getOs()))
								.withVesion(app.getVersion())
								.withAreacode(String.valueOf(IPUtils.getInstance().getAreaCode(ip)));
						JSONObject extObject = JSONObject.parseObject(NvwaUtils.obj2Empty(app.getExt()));
						if (extObject != null) {
							builder.withLev1(String.valueOf(extObject.getString("lev1")))
								.withLev2(String.valueOf(extObject.getString("lev2")))
								.withLev3(String.valueOf(extObject.getString("lev3")));
						}
						String logStr = builder.build().toString();
						LOG_REQ.info(logStr.replaceAll("null", StringUtils.EMPTY));
						count(adRequest.getApp(), posId, device.getDid());
					} catch (Exception e) {
						LOG.error("==========printReqLog error. ", e);
					}
					
				}
			});
		} catch (Exception e) {
			LOG.error("printReqLog error. ", e);
		}
	}
	
	private void count(App app, String posId, String did) {
		try {
			String partKey = com.iwanvi.nvwa.common.utils.Constants.IWANVI_UUID;
			// TODO req计数
			if (Constants.REQ_COUNT.containsKey(partKey)) {
				Constants.REQ_COUNT.get(partKey).incrementAndGet();
			} else {
				Constants.REQ_COUNT.put(partKey, new AtomicLong(1));
			}
			// TODO req计数
			if (Constants.REQ_COUNT.containsKey(posId)) {
				Constants.REQ_COUNT.get(posId).incrementAndGet();
			} else {
				Constants.REQ_COUNT.put(posId, new AtomicLong(1));
			}
			
//			partKey = StringUtils.buildString(
//					com.iwanvi.nvwa.common.utils.Constants.FORMMAT_COUNT_KEY, app.getAppId(), posId,
//					app.getChannel(), app.getVersion());
//			if (StringUtils.isBlank(app.getAppId()) || StringUtils.isBlank(posId) ||
//					StringUtils.isBlank(app.getChannel()) || StringUtils.isBlank(app.getVersion())) {
//				LOG.warn("Count break. partKey: {}", partKey);
//				//return;
//			} else {
//				// requv
//				if (StringUtils.isNotBlank(did)) {
//					String day = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
//					String key = StringUtils.buildString(
//							com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_MEDIA_QUOTA,
//							com.iwanvi.nvwa.common.utils.Constants.QUOTA_REQUV, partKey, day);
//					
//					if (!Constants.REQ_DID_MAP.containsKey(key)) {
//						Constants.REQ_DID_MAP.put(key, ConcurrentHashMap.newKeySet());
//					}
//					Constants.REQ_DID_MAP.get(key).add(did);
//				}
//				
//				// TODO req计数
//				if (Constants.REQ_COUNT.containsKey(partKey)) {
//					Constants.REQ_COUNT.get(partKey).incrementAndGet();
//				} else {
//					Constants.REQ_COUNT.put(partKey, new AtomicLong(1));
//				}
//			}
			
			// 预警
			partKey = "pv:warnning:{}:{}";

			//目录级别
			JSONObject extObject = JSONObject.parseObject(NvwaUtils.obj2Empty(app.getExt()));
			if (extObject == null) {
				return;
			}
			if (StringUtils.isBlank(extObject.getString("lev1"))) {
				LOG.info("count return. partKey: {}", partKey);
				return;
			}
			
			if (StringUtils.isBlank(extObject.getString("lev3"))) {
				if (StringUtils.isBlank(extObject.getString("lev2"))) {
					partKey = StringUtils.buildString(
							com.iwanvi.nvwa.common.utils.Constants.FORMMAT_CATALOG_COUNT_KEY, 
							extObject.getString("lev1"), 0, 0);
				} else {
					partKey = StringUtils.buildString(
							com.iwanvi.nvwa.common.utils.Constants.FORMMAT_CATALOG_COUNT_KEY, 
							extObject.getString("lev1"), extObject.getString("lev2"), 0);
				}
			} else {
				partKey = StringUtils.buildString(
						com.iwanvi.nvwa.common.utils.Constants.FORMMAT_CATALOG_COUNT_KEY, 
						extObject.getString("lev1"), extObject.getString("lev2"), extObject.getString("lev3"));
			}

			Count.getInstance().addCataLogReqPartKeySet(partKey);
			
			if (Constants.REQ_CATALOG_COUNT.containsKey(partKey)) {
				Constants.REQ_CATALOG_COUNT.get(partKey).incrementAndGet();
			} else {
				Constants.REQ_CATALOG_COUNT.put(partKey, new AtomicLong(1));
			}
			
			if (StringUtils.isNotBlank(did)) {
				String day = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
				String key = StringUtils.buildString(
						com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_QUOTA,
						com.iwanvi.nvwa.common.utils.Constants.QUOTA_REQUV, partKey, day);
				
				if (!Constants.REQ_CATALOG_DID_MAP.containsKey(key)) {
					Constants.REQ_CATALOG_DID_MAP.put(key, ConcurrentHashMap.newKeySet());
				}
				Constants.REQ_CATALOG_DID_MAP.get(key).add(did);
			}
		} catch (Exception e) {
			LOG.error("count error. ", e);
		}
	}
	
	public void printBidLog(AdRequest adRequest, String posId, String dspId, String os, int cid, int bidprice, long elapsed) {
		try {
			String reqId = adRequest.getId();
			App app = adRequest.getApp();
			String appId = app.getAppId();
			LOG_BID.info(StringUtils.buildString(LOG_FORMAT_BID, 
					DateUtils.formatPure(new Date()), reqId, elapsed, appId, posId, dspId, cid, os, bidprice));
			ThreadDistribution.getInstance().doWork(new Runnable() {
				@Override
				public void run() {
					Date now = new Date();
					String today = DateUtils.format(now, DateUtils.SHORT_FORMAT);
					String hour = DateUtils.format(now, DateUtils.FORMAT_HOUR);
					
					String key = StringUtils.buildString(Constants.KEY_REDIS_CREATIVE_QUOTA_HOUR,
							Constants.QUOTA_BID, String.valueOf(cid), today, hour);
					redisDao.incr(key);
					
					// TODO req计数
					String partKey = StringUtils.buildString(
							com.iwanvi.nvwa.common.utils.Constants.FORMMAT_COUNT_KEY, appId, posId,
							app.getChannel(), app.getVersion());
					if (Constants.BID_COUNT.containsKey(partKey)) {
						Constants.BID_COUNT.get(partKey).incrementAndGet();
					} else {
						Constants.BID_COUNT.put(partKey, new AtomicLong(1));
					}
					
					redisDao.incr(key);
				}
			});
		} catch (Exception e) {
			LOG.error("printBidLog error. ", e);
		}
	}
}

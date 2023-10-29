package com.iwanvi.nvwa.pixel.connector.imp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.pixel.connector.common.redis.RedisCallback;
import com.iwanvi.nvwa.pixel.connector.common.redis.RedisTemplate;
import com.iwanvi.nvwa.pixel.connector.common.redis.ShardedJedisUtils;
import com.iwanvi.nvwa.pixel.connector.common.service.PixelService;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.common.utils.ThreadDistribution;
import com.iwanvi.nvwa.pixel.connector.imp.service.ExposureService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Service("exposureService")
public class ExposureServiceImpl implements ExposureService {

	private static final Logger LOG = LoggerFactory.getLogger("ExposureService");
	
	private static final String KEY_REDIS_FREQUENCYCTRL_EXP = "f:e:{0}:{1}";  // 频次控制 f:e:{unitid}:{limitId}
	
	// 频次控制的消息订阅通道
	private static final String DISPLAY_CHANNEL = "channel:display";

	@Resource
	private PixelService pixelService;
	
	@Resource
	private RedisDao redisDao;
	
	@Override
	public String exposure(String appId, String cid, String did, Map<String, Object> map) {
		String result = null;
		try {
			record(map);
			frequencyCtrl(map);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			result = JsonUtils.EXCEPTION_ERROR();
			LOG.error("exposure error. ", e);
		}
		return result;
	}
	
	@Override
	public String dspExposure(String appId, String dspId, String cid, String p) {
		String result = null;
		try {
			String clearParam = new String(Base64.decodeBase64(p.getBytes()));
			Map<String, Object> map = JsonUtils.parse2Map(clearParam);
			if(map == null || map.isEmpty()){
				LOG.warn("dspExposure exception. map is null. appId: {}, cid: {}", appId, cid);
				return JsonUtils.PARAMETER_ERROR();
			}
			dspRecord(map, appId, dspId, Integer.valueOf(cid));
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			result = JsonUtils.EXCEPTION_ERROR();
			LOG.error("dspExposure error. ", e);
		}
		return result;
	}
	
	/**
	 * 频次控制
	 */
	private void frequencyCtrl(Map<String, Object> map) {
		try {
			Integer frequencyType = NvwaUtils.obj2int(map.get("frequencyType")); // 频次控制 类型，1:ip、2:did
			Integer unitId = NvwaUtils.obj2int(map.get("unitId"));
			boolean isLimit = Boolean.valueOf(NvwaUtils.obj2Empty(map.get("isLimit")));
			String deviceId = NvwaUtils.obj2Empty(map.get("did"));
			String ip = NvwaUtils.obj2Empty(map.get("ip"));
			String limitId = null;
			switch (frequencyType) {
			case 1:
				limitId = ip;
				break;
			case 2:
				limitId = deviceId;
				break;
			default:
				return;
			}

			if(isLimit && StringUtils.isNotBlank(limitId)){
				Integer lperiod = NvwaUtils.obj2int(map.get("lperiod"));
				Integer periodNum = lperiod;
				switch (lperiod) {
				case 1:
					lperiod = 3600 * 24;
					break;
				case 2:
					lperiod = 3600 * 24 * 7;
					break;
				case 3:
					lperiod = 3600 * 24 * 30;
					break;
				}
				if (lperiod > Constants.INTEGER_0) {
					final String key = StringUtils.buildString(KEY_REDIS_FREQUENCYCTRL_EXP, unitId, limitId);
					Long count = ShardedJedisUtils.getInstance().incrR(key, lperiod);
					LOG.info("frequencyCtrl. key: {}, lperiod: {}", key, lperiod);

					if (count != null) {
						redisDao.pub(DISPLAY_CHANNEL, StringUtils.concat(unitId, Constants.SIGN_COLON,
								limitId, Constants.SIGN_UNDERLINE, count, Constants.SIGN_UNDERLINE, periodNum));
						LOG.info("frequencyCtrl. key: {}, count: {}, lperiod: {}", key, count, periodNum);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("frequencyCtrl error. ", e);
		}
	}
	
	public void record(final Map<String, Object> map) {
		try {
			Date now = new Date();
			ThreadDistribution.getInstance().doWork(new Runnable() {
				@Override
				public void run() {
					RedisTemplate.execute(new RedisCallback() {
						@Override
						public Object doRedis(Jedis jedis) {
							long l1 = System.currentTimeMillis();
							Integer planId = NvwaUtils.obj2int(map.get("planId"));
							Integer unitId = NvwaUtils.obj2int(map.get("unitId"));
							int cid = NvwaUtils.obj2int(map.get("cid"));
							try {
								LOG.info("start recordDisplay to redis");
								String appId = NvwaUtils.obj2Empty(map.get("appId"));
								int agentType = NvwaUtils.obj2int(map.get("agentType"));
								String aderId = NvwaUtils.obj2Empty(map.get("aderId"));
								long cost = NvwaUtils.obj2long(map.get("cost"));
								long agentCost = NvwaUtils.obj2long(map.get("agentCost"));
								String agentId = NvwaUtils.obj2Empty(map.get("agentId"));
								Integer puType = NvwaUtils.obj2int(map.get("puType")); // 投放类型(1 订单投放 2 精确投放 3 抄底投放)

								String posId = NvwaUtils.obj2Empty(map.get("posId"));
								String channel = NvwaUtils.obj2Empty(map.get("channel"));
								String version = NvwaUtils.obj2Empty(map.get("version"));
								
								String countKey = StringUtils.buildString(
										com.iwanvi.nvwa.common.utils.Constants.FORMMAT_COUNT_KEY, appId, posId, channel, version);
								
								String today = DateUtils.format(now, DateUtils.SHORT_FORMAT);
								String hour = DateUtils.format(now, DateUtils.FORMAT_HOUR);
								String valueQueue = null;
								
								LOG.info("redis first pipeline op start");

								Pipeline pipeline = jedis.pipelined();
								// 创意的曝光数
								pixelService.buildPipelineIncr(pipeline, PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR,
										Constants.QUOTA_DISPLAY, cid, today, hour, 1);
								// 广告主曝光数
								pixelService.buildPipelineIncr(pipeline, 
										PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR, Constants.QUOTA_DISPLAY, aderId, today, hour, 1);
								// 代理商曝光数
								pixelService.buildPipelineIncr(pipeline, 
										PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR, Constants.QUOTA_DISPLAY, agentId, today, hour, 1);
								
								if(puType == 1) {
									// 订单
									String kpiNowKey = StringUtils.buildString(PixelConstants.KEY_REDIS_ORDER_QUOTA_DAY, 
											Constants.QUOTA_DISPLAY, unitId, today);
									String kpiKey = StringUtils.buildString(PixelConstants.KEY_REDIS_KPI_EXP, unitId);

									List<String> keys = new ArrayList<>();
									keys.add(kpiNowKey);
									keys.add(kpiKey);
									
									List<String> results = redisDao.multiGet(keys);
									Long kpiNow = NvwaUtils.obj2long(results.get(0));
									Long kpi = NvwaUtils.obj2long(results.get(1));
									
									if (kpi > 0 && kpiNow >= kpi) {
										// 通知KPI已完成
										valueQueue = StringUtils.buildString(
												PixelConstants.VALUE_REDIS_OVER_LIMIT_ORDER, today, aderId, unitId, kpiNow+1);
										pipeline.sadd(PixelConstants.KEY_REDIS_OVER_NOTIFY, valueQueue);
										LOG.info("recordDisplay. Order OverKpi. kpi: {}, over_queue_value: {}, current: {}",
												kpi, valueQueue, kpiNow+1);
									} else {
										pixelService.buildPipelineIncr(pipeline, 
												PixelConstants.KEY_REDIS_ORDER_QUOTA_HOUR, Constants.QUOTA_DISPLAY, unitId, today, hour, 1);
										pipeline.incr(kpiNowKey);
									}
									
									pipeline.sync();
									pipeline = null;
									LOG.info("redis first pipeline op end. orderPut. unitId: {}, kpi: {}, kpiNow: {}", 
											unitId, kpiKey + "=" + kpi, kpiNowKey + "=" + kpiNow);
									
									return null;
								}
								
								pipeline.sync();
								pipeline = null;
								
								if(puType == 3) {
									LOG.info("redis first pipeline op end. cid: {}, appId: {}, aderId: {}, agentId: {}", 
											cid, appId, aderId, agentId);
									return null;
								}
								
								LOG.info("redis first pipeline op end");
								
								// 今日计划消费
								String planDayKey = StringUtils.buildString(PixelConstants.KEY_REDIS_PLAN_QUOTA_DAY,
										Constants.QUOTA_COST, planId, today);
								// 今日单元消费
								String unitDayKey = StringUtils.buildString(PixelConstants.KEY_REDIS_UNIT_QUOTA_DAY,
										Constants.QUOTA_COST, unitId, today);
								// 当日计划限额
								String planLimitKey = StringUtils.buildString(PixelConstants.KEY_REDIS_MONEY_LIMIT_PLAN,
										aderId, planId);
								// 当日单元限额
								String unitLimitKey = StringUtils.buildString(PixelConstants.KEY_REDIS_MONEY_LIMIT_UNIT,
										aderId, unitId);
								
								String agentBalanceKey = StringUtils.buildString(PixelConstants.KEY_REDIS_MONEY_TOTAL, agentId);
								String aderBalanceKey = StringUtils.buildString(PixelConstants.KEY_REDIS_MONEY_TOTAL, aderId);
								String aderCostKey = StringUtils.buildString(PixelConstants.KEY_REDIS_MONEY_COST, aderId, today);
								String agentCostKey = StringUtils.buildString(PixelConstants.KEY_REDIS_MONEY_COST, agentId, today);
								
								List<String> results = jedis.mget(planDayKey, unitDayKey, planLimitKey, unitLimitKey,
										aderBalanceKey, agentBalanceKey);
								LOG.info("redis mget result: {}", results);
								Long plan_consume = NvwaUtils.obj2long(results.get(0));
								Long unit_consume = NvwaUtils.obj2long(results.get(1));
								Long planLimit = NvwaUtils.obj2long(results.get(2));
								Long unitLimit = NvwaUtils.obj2long(results.get(3));
								Long aderBalance = NvwaUtils.obj2long(results.get(4));
								Long agentBalance = NvwaUtils.obj2long(results.get(5));
								
								Long agentBalance_now = agentBalance - agentCost;
								Long aderBalance_now = aderBalance - cost;
								Long plan_consume_now = plan_consume + cost;
								Long unit_consume_now = unit_consume + cost;
								
								LOG.info("recordDisplay incr start. appId: {}, agentId: {}, aderId: {}, planId: {}, "
										+ "unitId: {}, cid: {}, cost: {}, aderBalance: {}, aderBalance_now: {}, "
										+ "plan_consume: {}, plan_consume_now: {}, unit_consume: {}, unit_consume_now: {}, "
										+ "planLimit: {}, unitLimit: {}, agentBalance: {}, agentBalance_now: {}, "
										+ "agentType: {}, agentCost: {}, elapsed: {}",
										appId, agentId, aderId, planId, unitId, cid, cost, aderBalance,
										aderBalance_now, plan_consume, plan_consume_now, unit_consume,
										unit_consume_now, planLimit, unitLimit, agentBalance, agentBalance_now,
										agentType, agentCost, (System.currentTimeMillis() - l1));
								
								LOG.info("start redis second pipeline op");
								Pipeline pipeline1 = jedis.pipelined();
								
								boolean isCost = true; // 是否扣费
								if (agentBalance_now < Constants.INTEGER_0) {
									// 通知代理商余额不足
									valueQueue = StringUtils.buildString(
											PixelConstants.VALUE_REDIS_OVER_BALANCE_USER, today, agentId, 0);
									LOG.info("recordDisplay. Agent OverBalance. agentBalance: {}, over_balance_value: {}",
											agentBalance, valueQueue);
								} else if (aderBalance_now < Constants.INTEGER_0) {
									// 通知广告主余额不足
									valueQueue = StringUtils.buildString(
											PixelConstants.VALUE_REDIS_OVER_BALANCE_USER, today, aderId, 0);
									LOG.info("recordDisplay. Ader OverBalance. aderBalance: {}, over_balance_value: {}",
											aderBalance, valueQueue);
								} else if (plan_consume_now >= planLimit) {
									// 通知今日消费达到计划上限
									valueQueue = StringUtils.buildString(
											PixelConstants.VALUE_REDIS_OVER_LIMIT_PLAN, today, aderId, planId,
											plan_consume_now + cost);
									isCost = false;
									LOG.info("recordDisplay. OverPlanLimit. over_limit_value: {}", valueQueue);
								} else if (unit_consume_now >= unitLimit) {
									// 通知今日消费达到单元上限
									valueQueue = StringUtils.buildString(
											PixelConstants.VALUE_REDIS_OVER_LIMIT_UNIT, today, aderId, unitId,
											unit_consume_now + cost);
									isCost = false;
									LOG.info("recordDisplay. OverUnitLimit. over_limit_value: {}", valueQueue);
								}
								// 扣费（广告主）
								if (isCost && cost != Constants.INTEGER_0) {
									if (aderBalance < cost) {
										LOG.info("Change cost. cost: {}, aderBalance: {}", cost, aderBalance);
										cost = aderBalance;
									}
									// 创意消费
									pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR,
											Constants.QUOTA_COST, cid, today, hour, cost);
									// 计划消费
									pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_PLAN_QUOTA_HOUR,
											Constants.QUOTA_COST, planId, today, hour, cost);
									// 单元消费
									pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_UNIT_QUOTA_HOUR,
											Constants.QUOTA_COST, unitId, today, hour, cost);
									// 广告主消费
									pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR,
											Constants.QUOTA_COST, aderId, today, hour, cost);
									// 代理商消费
									pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR,
											Constants.QUOTA_COST, agentId, today, hour, cost);
									// 实时监控
									pixelService.buildPipelineIncr(pipeline1, Constants.KEY_REDIS_MEDIA_QUOTA_HOUR,
											Constants.QUOTA_COST, Constants.IWANVI_UUID, today, hour, cost);
									pixelService.buildPipelineIncr(pipeline1, Constants.KEY_REDIS_ADPOSITION_QUOTA_HOUR,
											Constants.QUOTA_COST, posId, today, hour, cost);
									

									pipeline1.incrBy(aderBalanceKey, -cost);
									pipeline1.incrBy(aderCostKey, cost);
									
									pipeline1.incrBy(planDayKey, cost);
									pipeline1.incrBy(unitDayKey, cost);
									
									if (!countKey.contains(Constants.SIGN_COLON_DOUBLE)) {
										pipeline1.incrBy(StringUtils.buildString(com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_MEDIA_QUOTA, 
												Constants.QUOTA_COST, countKey, today), cost);
									}
								}
									
								if (isCost && agentCost != Constants.INTEGER_0) {
									if (agentBalance < agentCost) {
										LOG.info("Change agentCost. agentCost: {}, aderBalance: {}", agentCost, agentBalance);
										agentCost = agentBalance;
									}
									pipeline1.incrBy(agentBalanceKey, -agentCost);
									pipeline1.incrBy(agentCostKey, agentCost);
								}
								
								if (StringUtils.isNotBlank(valueQueue)) {
									pipeline1.sadd(PixelConstants.KEY_REDIS_OVER_NOTIFY, valueQueue);
								}
								
								pipeline1.sync();
								pipeline1 = null;
								LOG.info("redis second pipeline op end");
								LOG.info("recordDisplay elapsed: {}", (System.currentTimeMillis() - l1));
							} catch (Exception ex) {
								LOG.error("doRedis error. cid: {}", cid, ex);
								String content = StringUtils.concat("Exposure exception. cid: ", cid, ", unitId: ", unitId, ", planId: ", planId);
								redisDao.leftPush("monitor", content);
							}
							return null;
						}
					});
				}
			});
		} catch (Exception e) {
			LOG.error("exposureService record error. ", e);
		}
	}
	public void dspRecord(final Map<String, Object> map, final String appId, final String dspId, final Integer cid) {
		try {
			final Date now = new Date();
			ThreadDistribution.getInstance().doWork(new Runnable() {
				@Override
				public void run() {
					final long l1 = System.currentTimeMillis();
					RedisTemplate.execute(new RedisCallback() {
						@Override
						public Object doRedis(Jedis jedis) {
							try {
								LOG.info("start recordDisplay to redis");
								long cost = NvwaUtils.obj2long(map.get("cost"));
								
								String today = DateUtils.format(now, DateUtils.SHORT_FORMAT);
								String hour = DateUtils.format(now, DateUtils.FORMAT_HOUR);

								String posId = NvwaUtils.obj2Empty(map.get("posId"));
								String channel = NvwaUtils.obj2Empty(map.get("channel"));
								String version = NvwaUtils.obj2Empty(map.get("version"));
								
								String countKey = StringUtils.buildString(
										com.iwanvi.nvwa.common.utils.Constants.FORMMAT_COUNT_KEY, appId, posId, channel, version);
								
								LOG.debug("redis first pipeline op start");
								Pipeline pipeline = jedis.pipelined();
								
								// 创意的曝光数
								pixelService.buildPipelineIncr(pipeline, PixelConstants.KEY_REDIS_DSP_CREATIVE_QUOTA_HOUR,
										Constants.QUOTA_DISPLAY, cid, today, hour, 1);
//								// DSP曝光数
								pixelService.buildPipelineIncr(pipeline, PixelConstants.KEY_REDIS_DSP_QUOTA_HOUR, 
										Constants.QUOTA_DISPLAY, dspId, today, hour, 1);
								
								pipeline.sync();
								pipeline = null;

								LOG.debug("redis first pipeline op end");
								
								LOG.info("recordDisplay incr start. appId: {}, dspId: {}, cid: {}, cost: {}, countKey: {}, elapsed: {}",
										appId, dspId, cid, cost, countKey, (System.currentTimeMillis() - l1));

								LOG.debug("start redis second pipeline op");
								Pipeline pipeline1 = jedis.pipelined();
								
								// 扣费（广告主）
								if (cost != Constants.INTEGER_0) {
									// 创意消费
									pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_DSP_CREATIVE_QUOTA_HOUR,
											Constants.QUOTA_COST, cid, today, hour, cost);
									pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_DSP_QUOTA_HOUR,
											Constants.QUOTA_COST, dspId, today, hour, cost);
									// 流量源实时监控
									pixelService.buildPipelineIncr(pipeline1, Constants.KEY_REDIS_MEDIA_QUOTA_HOUR,
											Constants.QUOTA_COST, Constants.IWANVI_UUID, today, hour, cost);
									pixelService.buildPipelineIncr(pipeline1, Constants.KEY_REDIS_ADPOSITION_QUOTA_HOUR,
											Constants.QUOTA_COST, posId, today, hour, cost);
									
									if (!countKey.contains(Constants.SIGN_COLON_DOUBLE)) {
										pipeline1.incrBy(StringUtils.buildString(
												com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_MEDIA_QUOTA, 
												Constants.QUOTA_COST, countKey, today), cost);
									}
								}
								pipeline1.sync();
								pipeline1 = null;
								LOG.debug("redis second pipeline op end");
								LOG.info("recordDisplay elapsed: {}", (System.currentTimeMillis() - l1));
							} catch (Exception ex) {
								LOG.error("doRedis error. cid: {}", cid, ex);
								String content = StringUtils.concat("Exposure exception. appId: ", appId, ", dspId: ", dspId, ", cid: ", cid);
								redisDao.leftPush("monitor", content);
							}
							return null;
						}
					});
				}
			});
		} catch (Exception e) {
			LOG.error("exposureService dspRecord error. ", e);
		}
	}
	
	@PostConstruct
	public void pushToQueue() {
		try {
			ThreadDistribution.getInstance().getScheduler().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					try {
						Set<String> overNotifications = redisDao.setMembers(PixelConstants.KEY_REDIS_OVER_NOTIFY_TEMP);
						if (overNotifications == null || overNotifications.isEmpty()) {
							return;
						}
						redisDao.setRemove(PixelConstants.KEY_REDIS_OVER_NOTIFY_TEMP, overNotifications.toArray());
						redisDao.leftPushAll(PixelConstants.KEY_REDIS_OVER_NOTIFY, overNotifications.toArray(new String[] {}));
					} catch (Exception e) {
						LOG.error("pushToQueue error. ", e);
					}
				}
			}, 10, 2, TimeUnit.SECONDS);
		} catch (Exception e) {
			LOG.info("pushToQueue error. ", e);
		}
	}
}

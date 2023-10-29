package com.iwanvi.nvwa.pixel.connector.clk.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.iwanvi.nvwa.pixel.connector.clk.service.ClickService;
import com.iwanvi.nvwa.pixel.connector.common.ip.IpSearch;
import com.iwanvi.nvwa.pixel.connector.common.redis.RedisCallback;
import com.iwanvi.nvwa.pixel.connector.common.redis.RedisTemplate;
import com.iwanvi.nvwa.pixel.connector.common.service.PixelService;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.common.utils.ThreadDistribution;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Service("clickService")
public class ClickServiceImpl implements ClickService {

	private static final Logger LOG = LoggerFactory.getLogger("ClickService");

	@Resource
	private PixelService pixelService;

	@Resource
	private RedisDao redisDao;

	@Override
	public String click(String appId, String cid, String did, Map<String, Object> map) {
		String result = null;
		try {
			record(map, appId, did, Integer.valueOf(cid));
			// 调用第三方监测地址
			// pixelService.reportCustomer(map, 2);
			
			// 保存点击用户设备id
			if (StringUtils.isNotBlank(did)) {
				saveQuotaDid(map, did);
			}
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			result = JsonUtils.EXCEPTION_ERROR();
			LOG.error("click error. ", e);
		}
		return result;
	}

	@Override
	public String dspClick(String appId, String dspId, String cid, String p) {
		String result = null;
		try {
			String clearParam = new String(Base64.decodeBase64(p.getBytes()));
			Map<String, Object> map = JsonUtils.parse2Map(clearParam);
			if (map == null || map.isEmpty()) {
				LOG.warn("clickService exception. map is null. appId: {}, dspId: {}, cid: {}", appId, dspId, cid);
				return JsonUtils.PARAMETER_ERROR();
			}
			dspRecord(map, appId, dspId, Integer.valueOf(cid));
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			result = JsonUtils.EXCEPTION_ERROR();
			LOG.error("click error. ", e);
		}
		return result;
	}

	public void record(final Map<String, Object> map, final String appId, final String did, final Integer cid) {
		try {
			final Date now = new Date();
			ThreadDistribution.getInstance().doWork(new Runnable() {
				@Override
				public void run() {
					try {
						RedisTemplate.execute(new RedisCallback() {
							@Override
							public Object doRedis(Jedis jedis) {
								try {
									long l1 = System.currentTimeMillis();
									String aderId = NvwaUtils.obj2Empty(map.get("aderId"));
									Integer planId = NvwaUtils.obj2int(map.get("planId"));
									Integer unitId = NvwaUtils.obj2int(map.get("unitId"));
									String agentId = NvwaUtils.obj2Empty(map.get("agentId"));
									Integer agentType = NvwaUtils.obj2int(map.get("agentType"));
									long cost = NvwaUtils.obj2int(map.get("cost"));
									long agentCost = NvwaUtils.obj2int(map.get("agentCost"));
									Integer dspId = NvwaUtils.obj2int(map.get("dspId"));
									Integer puType = NvwaUtils.obj2int(map.get("puType")); // 投放类型(1 订单投放 2 精确投放 3 抄底投放)
									
									String today = DateUtils.format(now, DateUtils.SHORT_FORMAT);
									String hour = DateUtils.format(now, DateUtils.FORMAT_HOUR);
									String valueQueue = null;
									
									String posId = NvwaUtils.obj2Empty(map.get("posId"));
									String channel = NvwaUtils.obj2Empty(map.get("channel"));
									String version = NvwaUtils.obj2Empty(map.get("version"));
									
									String countKey = StringUtils.buildString(
											com.iwanvi.nvwa.common.utils.Constants.FORMMAT_COUNT_KEY, appId, posId, channel, version);
									
									LOG.info("redis second pipeline op end");
									Pipeline pipeline = jedis.pipelined();
									
									// 创意的点击量
									pixelService.buildPipelineIncr(pipeline, PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR,
											Constants.QUOTA_CLICK, cid, today, hour, 1);
									// DSP
									pixelService.buildPipelineIncr(pipeline, PixelConstants.KEY_REDIS_DSP_QUOTA_HOUR,
											Constants.QUOTA_CLICK, dspId, today, hour, 1);
									// 广告主
									pixelService.buildPipelineIncr(pipeline, 
											PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR, Constants.QUOTA_CLICK, aderId, today, hour, 1);
									// 代理商
									pixelService.buildPipelineIncr(pipeline, 
											PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR, Constants.QUOTA_CLICK, agentId, today, hour, 1);
									
									if(puType == 1) {
										// 订单
										String kpiNowKey = StringUtils.buildString(PixelConstants.KEY_REDIS_ORDER_QUOTA_DAY, 
												Constants.QUOTA_CLICK, unitId, today);
										String kpiKey = StringUtils.buildString(PixelConstants.KEY_REDIS_KPI_CLK, unitId);

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
											LOG.info("recordClick. Order OverKpi. kpi: {}, over_queue_value: {}, current: {}",
													kpi, valueQueue, kpiNow+1);
										} else {
											pixelService.buildPipelineIncr(pipeline, 
													PixelConstants.KEY_REDIS_ORDER_QUOTA_HOUR, Constants.QUOTA_CLICK, unitId, today, hour, 1);
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
									String planLimitKey = StringUtils
											.buildString(PixelConstants.KEY_REDIS_MONEY_LIMIT_PLAN, aderId, planId);
									
									// 当日单元限额
									String unitLimitKey = StringUtils
											.buildString(PixelConstants.KEY_REDIS_MONEY_LIMIT_UNIT, aderId, unitId);
									
									String aderCostKey = StringUtils.buildString(
											PixelConstants.KEY_REDIS_MONEY_COST, aderId, today);
									String agentCostKey = StringUtils.buildString(
											PixelConstants.KEY_REDIS_MONEY_COST, agentId, today);
									String aderBalanceKey = StringUtils.buildString(
											PixelConstants.KEY_REDIS_MONEY_TOTAL, aderId);
									String agentBalanceKey = StringUtils.buildString(
											PixelConstants.KEY_REDIS_MONEY_TOTAL, agentId);
									
									List<String> results = jedis.mget(planDayKey, unitDayKey, planLimitKey,
											unitLimitKey, aderBalanceKey, agentBalanceKey);
									LOG.info("redis mget result: {}", results);
									
									Long plan_consume = NvwaUtils.obj2long(results.get(0));
									Long unit_consume = NvwaUtils.obj2long(results.get(1));
									Long planLimit = NvwaUtils.obj2long(results.get(2));
									Long unitLimit = NvwaUtils.obj2long(results.get(3));
									Long aderBalance = NvwaUtils.obj2long(results.get(4));
									Long agentBalance = NvwaUtils.obj2long(results.get(5));
									
									Long aderBalance_now = aderBalance - cost;
									Long agentBalance_now = agentBalance - agentCost;
									Long unit_consume_now = unit_consume + cost;
									Long plan_consume_now = plan_consume + cost;
									
									LOG.info("recordClick incr start. appId: {}, agentId: {}, aderId: {}, planId: {}, "
											+ "unitId: {}, cid: {}, cost: {}, aderBalance: {}, aderBalance_now: {}, "
											+ "plan_consume: {}, plan_consume_now: {}, unit_consume: {}, unit_consume_now: {}, "
											+ "planLimit: {}, unitLimit: {}, agentBalance: {}, agentBalance_now: {}, "
											+ "agentType: {}, agentCost: {}: dspId: {}, elapsed: {}",
											appId, agentId, aderId, planId, unitId, cid, cost, aderBalance,
											aderBalance_now, plan_consume, plan_consume_now, unit_consume,
											unit_consume_now, planLimit, unitLimit, agentBalance, agentBalance_now,
											agentType, agentCost, dspId, (System.currentTimeMillis() - l1));
									
									LOG.info("start redis second pipeline op");
									Pipeline pipeline1 = jedis.pipelined();
									boolean isCost = true; // 是否扣费
									if (agentBalance_now < Constants.INTEGER_0) {
										// 通知代理商余额不足
										valueQueue = StringUtils.buildString(
												PixelConstants.VALUE_REDIS_OVER_BALANCE_USER, today, agentId, 0);
										LOG.info("recordClick. Agent OverBalance. over_balance_value: {}", valueQueue);
									} else if (aderBalance_now < Constants.INTEGER_0) {
										// 通知广告主余额不足
										valueQueue = StringUtils.buildString(
												PixelConstants.VALUE_REDIS_OVER_BALANCE_USER, today, aderId, 0);
										LOG.info("recordClick. Ader OverBalance. over_balance_value: {}", valueQueue);
									} else if (plan_consume_now >= planLimit) {
										// 通知今日计划消费达到上限
										valueQueue = StringUtils.buildString(
												PixelConstants.VALUE_REDIS_OVER_LIMIT_PLAN, today, aderId, planId,
												plan_consume_now + cost);
										isCost = false;
										LOG.info("recordClick. OverPlanLimit. over_limit_value: {}", valueQueue);
									} else if (unit_consume_now >= unitLimit) {
										// 通知今日单元消费达到上限
										valueQueue = StringUtils.buildString(
												PixelConstants.VALUE_REDIS_OVER_LIMIT_UNIT, today, aderId, unitId,
												unit_consume_now + cost);
										isCost = false;
										LOG.info("recordClick. OverUnitLimit. over_limit_value: {}", valueQueue);
									}
									// 扣费（广告主）
									if (isCost && cost != Constants.INTEGER_0) {
										if (aderBalance < cost) {
											LOG.info("Change cost. cost: {}, aderBalance: {}", cost, aderBalance);
											cost = aderBalance;
										}
										// 创意
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
											pipeline1.incrBy(StringUtils.buildString(
													com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_MEDIA_QUOTA, 
													Constants.QUOTA_COST, countKey, today), cost);
										}
										
										LOG.info("record incr end. ");
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
									LOG.info("record elapsed: {}", (System.currentTimeMillis() - l1));
								} catch (Exception e) {
									LOG.error("record redisCallback error. ", e);
								}
								return null;
							}
						});
					} catch (Exception e) {
						LOG.error("clickService thread record error. ", e);
					}
				}
			});
			
		} catch (Exception e) {
			LOG.error("clickService record error. ", e);
		}
	}
	public void dspRecord(final Map<String, Object> map, final String appId, final String dspId, final Integer cid) {
		try {
			final Date now = new Date();
			ThreadDistribution.getInstance().doWork(new Runnable() {
				@Override
				public void run() {
					try {
						RedisTemplate.execute(new RedisCallback() {
							@Override
							public Object doRedis(Jedis jedis) {
								try {
									long l1 = System.currentTimeMillis();
									long cost = NvwaUtils.obj2int(map.get("cost"));
									
									String today = DateUtils.format(now, DateUtils.SHORT_FORMAT);
									String hour = DateUtils.format(now, DateUtils.FORMAT_HOUR);

									String posId = NvwaUtils.obj2Empty(map.get("posId"));
									String channel = NvwaUtils.obj2Empty(map.get("channel"));
									String version = NvwaUtils.obj2Empty(map.get("version"));
									
									String countKey = StringUtils.buildString(
											com.iwanvi.nvwa.common.utils.Constants.FORMMAT_COUNT_KEY, appId, posId, channel, version);
									
									LOG.info("redis second pipeline op end");
									Pipeline pipeline = jedis.pipelined();
									// 创意的点击量
									pixelService.buildPipelineIncr(pipeline, PixelConstants.KEY_REDIS_DSP_CREATIVE_QUOTA_HOUR,
											Constants.QUOTA_CLICK, cid, today, hour, 1);
									// DSP
									pixelService.buildPipelineIncr(pipeline, PixelConstants.KEY_REDIS_DSP_QUOTA_HOUR,
											Constants.QUOTA_CLICK, dspId, today, hour, 1);
									
									pipeline.sync();
									pipeline = null;
									
									LOG.info("recordClick incr start. appId: {}, dspId: {}, cid: {}, cost: {}, countKey: {}, elapsed: {}",
											appId, dspId, cid, cost, countKey, (System.currentTimeMillis() - l1));

									Pipeline pipeline1 = jedis.pipelined();
									// 扣费（广告主）
									if (cost != Constants.INTEGER_0) {
										// 创意
										pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_DSP_CREATIVE_QUOTA_HOUR,
												Constants.QUOTA_COST, cid, today, hour, cost);
										pixelService.buildPipelineIncr(pipeline1, PixelConstants.KEY_REDIS_DSP_QUOTA_HOUR,
												Constants.QUOTA_COST, dspId, today, hour, cost);
										// 实时监控
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
									LOG.info("record elapsed: {}", (System.currentTimeMillis() - l1));
								} catch (Exception e) {
									LOG.error("record redisCallback error. ", e);
								}
								return null;
							}
						});
					} catch (Exception e) {
						LOG.error("clickService thread record error. ", e);
					}
				}
			});

		} catch (Exception e) {
			LOG.error("clickService dspRecord error. ", e);
		}
	}
	
	private void saveQuotaDid(Map<String, Object> paraMap, String did){
		try {
			String mediaId = NvwaUtils.obj2Empty(paraMap.get("appId"));
			Integer cid = NvwaUtils.obj2int(paraMap.get("cid"));
			String ip = NvwaUtils.obj2Empty(paraMap.get("ip"));
			String areaCode = StringUtils.EMPTY;
			if(StringUtils.isNotBlank(ip)){
				areaCode = String.valueOf(IpSearch.getInstance().getAreaCode(ip));
			}
			
			Long clkTime = NvwaUtils.obj2long(
					DateUtils.format(new Date(), DateUtils.FORMAT_YMDHMS));
			Map<String, Object> map = new HashMap<>();
			map.put("mediaId", mediaId);
			map.put("cid", cid);
			map.put("did", did);
			map.put("clkTime", clkTime);
			map.put("ip", ip);
			map.put("areaCode", areaCode);
			
			redisDao.leftPush(PixelConstants.KEY_REDIS_CLICK, JsonUtils.TO_JSON(map));
		} catch (Exception e) {
			LOG.error("clickService saveQuotaDid error. ", e);
		}
	}
}

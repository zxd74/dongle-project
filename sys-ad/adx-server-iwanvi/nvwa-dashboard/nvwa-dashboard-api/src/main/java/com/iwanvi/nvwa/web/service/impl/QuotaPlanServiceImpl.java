package com.iwanvi.nvwa.web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.PlanMapper;
import com.iwanvi.nvwa.dao.QuotaPlanMapper;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.QuotaDid;
import com.iwanvi.nvwa.dao.model.QuotaOrder;
import com.iwanvi.nvwa.dao.model.QuotaPlan;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.QuotaDidService;
import com.iwanvi.nvwa.web.service.QuotaPlanService;
import com.iwanvi.nvwa.web.util.VoUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class QuotaPlanServiceImpl implements QuotaPlanService {
	@Autowired
	private QuotaPlanMapper quotaPlanMapper;
	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private QuotaDidService quotaDidService;
	@Autowired
	private PlanMapper planMapper;
	@Autowired
	private RedisDao redisDao;

	public static final String KEY_REDIS_CREATIVE_QUOTA_HOUR = "creative:hour:{0}:{1}:{2}:{3}";// creative:hour:{quota}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_CREATIVE_CALLBACK_HOUR = "callback:{0}:clk:{1}:{2}";// callback:{cid}:clk:{ymd}:{HH}

	@Override
	public List<QuotaPlan> listByDay(Map<String, Object> paramMap) {
		Date sDate = DateUtils.parse(paramMap.get("sTime").toString(), DateUtils.SHORT_FORMAT);
		Date eDate = DateUtils.parse(paramMap.get("eTime").toString(), DateUtils.SHORT_FORMAT);
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		Integer index = 0;
		//判断日期 检查结束日期是否大于今天
		if (sDate.compareTo(eDate) > 0) {
			return null;
		} else if (eDate.compareTo(today) > 0) {
			//若大于今天 结束日期以今天代替
			eDate = today;
			paramMap.put("eTime", DateUtils.format(eDate, DateUtils.SHORT_FORMAT));
		}
		List<QuotaPlan> resultList = Lists.newArrayList();
		Map<String, QuotaPlan> MapByDay = Maps.newHashMap();
		Integer planId = Integer.valueOf(paramMap.get("id").toString());
		//初始化数据 每一天生成一条数据
		for (Date startDay = sDate; startDay.compareTo(eDate) <= 0; startDay = DateUtils.getNextDaysDate(startDay, 1)) {
			QuotaPlan quotaPlan = new QuotaPlan();
			quotaPlan.setPlanId(planId);
			quotaPlan.setCreDay(Integer.valueOf(DateUtils.format(startDay, DateUtils.SHORT_FORMAT)));
			resultList.add(quotaPlan);
			MapByDay.put(DateUtils.format(startDay, DateUtils.SHORT_FORMAT), quotaPlan);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		paramMap.put("hour", hour - 1);
		paramMap.put("day", DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
		List<QuotaPlan> QuotaPlans = quotaPlanMapper.listByDay(paramMap);
		//遍历查询数据 代替初始化数据
		for (QuotaPlan QuotaPlan : QuotaPlans) {
			index = new Long(DateUtils.getDValue2Day(sDate,
					DateUtils.parse(QuotaPlan.getCreDay().toString(), DateUtils.SHORT_FORMAT))).intValue();
			resultList.set(index, QuotaPlan);
			MapByDay.put(QuotaPlan.getCreDay().toString(), QuotaPlan);
		}
		QuotaPlan allQuotaPlan = new QuotaPlan();
		Plan plan = planMapper.selectByPrimaryKey(planId);
		allQuotaPlan.setPlanName(plan.getPlanName());
		allQuotaPlan.setPlanId(planId);
		for (String dayStr : MapByDay.keySet()) {
			QuotaPlan quotaPlan = MapByDay.get(dayStr);
			paramMap.put("eTime", dayStr);
			Long quotaDidStartDate = Long.valueOf(
					DateUtils.format(DateUtils.parse(dayStr, DateUtils.SHORT_FORMAT), DateUtils.FORMAT_YMDHMS));
			Long quotaDidEndDate = Long
					.valueOf(DateUtils.format(
							DateUtils.getNextDaysDate(
									DateUtils.parse(dayStr, DateUtils.SHORT_FORMAT), 1),
							DateUtils.FORMAT_YMDHMS));
			computeQuotaPlan(quotaPlan, paramMap, quotaDidStartDate, quotaDidEndDate);
			allQuotaPlan.setReq(allQuotaPlan.getReq() + quotaPlan.getReq());
			allQuotaPlan.setExp(allQuotaPlan.getExp() + quotaPlan.getExp());
			allQuotaPlan.setClk(allQuotaPlan.getClk() + quotaPlan.getClk()) ;
			allQuotaPlan.setRealCost(allQuotaPlan.getRealCost() + quotaPlan.getRealCost());
			allQuotaPlan.setCpm(allQuotaPlan.getCpm() + quotaPlan.getCpm());
			allQuotaPlan.setCpc(allQuotaPlan.getCpc() + quotaPlan.getCpc());
			allQuotaPlan.setCpa(allQuotaPlan.getCpa() + quotaPlan.getCpa());
			allQuotaPlan.setCvr(allQuotaPlan.getCvr() + quotaPlan.getCvr());
			allQuotaPlan.setPlanActive(allQuotaPlan.getPlanActive() + quotaPlan.getPlanActive());
		}
		//去掉汇总数据 前端计算
//		resultList.add(allQuotaPlan);
		return resultList;
	}

	@Override
	public SwaggerPage<List<QuotaPlan>> listByInit(Map<String, Object> paramMap) {
		long start = new Date().getTime();
		Date sDate = DateUtils.parse(paramMap.get("sTime").toString(), DateUtils.SHORT_FORMAT);
		Date eDate = DateUtils.parse(paramMap.get("eTime").toString(), DateUtils.SHORT_FORMAT);
		Long quotaDidStartDate = Long.valueOf(DateUtils.format(sDate, DateUtils.FORMAT_YMDHMS));
		Long quotaDidEndDate = Long
				.valueOf(DateUtils.format(DateUtils.getNextDaysDate(eDate, 1), DateUtils.FORMAT_YMDHMS));
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		SwaggerPage<List<QuotaPlan>> page = new SwaggerPage<>();
		if (sDate.compareTo(eDate) > 0) {
			return null;
		} else if (eDate.compareTo(today) > 0) {
			eDate = today;
			paramMap.put("eTime", DateUtils.format(eDate, DateUtils.SHORT_FORMAT));
		}
		int count = planMapper.countByMap(paramMap);
		if (count > 0) {
			page = new SwaggerPage<>(count, (Integer) paramMap.get("cp"), (Integer) paramMap.get("ps"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
			paramMap.put("hour", hour - 1);
			paramMap.put("day", DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
			paramMap.put("start", page.getStartPosition());
			paramMap.put("limit", page.getPageSize());
			List<QuotaPlan> QuotaPlans = quotaPlanMapper.listByInit(paramMap);
			if (QuotaPlans != null) {
				for (QuotaPlan QuotaPlan : QuotaPlans) {
					paramMap.put("id", QuotaPlan.getPlanId());
					computeQuotaPlan(QuotaPlan, paramMap, quotaDidStartDate, quotaDidEndDate);
				}
			}
			page.setData(QuotaPlans);
		} else {
			page = new SwaggerPage<>(count);
		}
		return page;
	}

	private void computeQuotaPlan(QuotaPlan QuotaPlan, Map<String, Object> paramMap, Long quotaDidStartDate,
			Long quotaDidEndDate) {
		Map<String, Object> entityParamMap = Maps.newHashMap();
		Long active = 0l;
		entityParamMap.put("plid", paramMap.get("id"));
		if (paramMap.get("createUser") != null) {
			entityParamMap.put("createUser", paramMap.get("createUser"));
		} else if (paramMap.get("createUsers") != null) {
			entityParamMap.put("createUsers", paramMap.get("createUsers"));
		}
		List<Entity> entities = entityMapper.listByMap(entityParamMap);
		if (entities != null) {
			List<Integer> entIds = Lists.transform(entities, new Function<Entity, Integer>() {

				@Override
				public Integer apply(Entity input) {
					return input.getId();
				}
			});
			//若 结束时间是今天则获取redis的数据
			if (paramMap.get("eTime").toString().equals(paramMap.get("day").toString())) {
				Integer hour = Integer.parseInt(paramMap.get("hour").toString()) + 1;
				String todayString = paramMap.get("day").toString();
				String hourString = hour < 10 ? "0" + hour : hour.toString();
				String hourString1 = hour < 11 ? "0" + (hour - 1) : "" + (hour - 1);
				for (Integer entId : entIds) {
					//获取当前小时的数据
					VoUtils.handleRealTimeData(entId, QuotaPlan, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
							KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString);
					//获取前一小时的数据
					VoUtils.handleRealTimeData(entId, QuotaPlan, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
							KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString1);
				}
			}
			//获取激活数
			List<QuotaDid> quotaDids = quotaDidService.listByEntId(null, entIds, quotaDidStartDate, quotaDidEndDate);
			if (quotaDids != null && quotaDids.size() > 0) {
				active += quotaDids.size();
				QuotaPlan.setPlanActive(active);
			}
			QuotaPlan.computeQuotaBase(QuotaPlan, active);
		}
	}

}

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
import com.iwanvi.nvwa.dao.OrdersMapper;
import com.iwanvi.nvwa.dao.QuotaOrderMapper;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.QuotaDid;
import com.iwanvi.nvwa.dao.model.QuotaOrder;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.QuotaDidService;
import com.iwanvi.nvwa.web.service.QuotaOrderService;
import com.iwanvi.nvwa.web.util.VoUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class QuotaOrderServiceImpl implements QuotaOrderService {
	@Autowired
	private QuotaOrderMapper quotaOrderMapper;
	@Autowired
	private QuotaDidService quotaDidService;
	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private RedisDao redisDao;

	public static final String KEY_REDIS_CREATIVE_QUOTA_HOUR = "creative:hour:{0}:{1}:{2}:{3}";// creative:hour:{quota}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_CREATIVE_CALLBACK_HOUR = "callback:{0}:clk:{1}:{2}";// callback:{cid}:clk:{ymd}:{HH}

	@Override
	public List<QuotaOrder> listByDay(Map<String, Object> paramMap) {
		Date sDate = DateUtils.parse(paramMap.get("sTime").toString(), DateUtils.SHORT_FORMAT);
		Date eDate = DateUtils.parse(paramMap.get("eTime").toString(), DateUtils.SHORT_FORMAT);
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		Integer index = 0;
		if (sDate.compareTo(eDate) > 0) {
			return null;
		} else if (eDate.compareTo(today) > 0) {
			eDate = today;
			paramMap.put("eTime", DateUtils.format(eDate, DateUtils.SHORT_FORMAT));
		}
		List<QuotaOrder> resultList = Lists.newArrayList();
		Map<String, QuotaOrder> MapByDay = Maps.newHashMap();
		Integer orderId = Integer.valueOf(paramMap.get("id").toString());
		for (Date startDay = sDate; startDay.compareTo(eDate) <= 0; startDay = DateUtils.getNextDaysDate(startDay, 1)) {
			QuotaOrder quotaOrder = new QuotaOrder();
			quotaOrder.setCreDay(Integer.valueOf(DateUtils.format(startDay, DateUtils.SHORT_FORMAT)));
			quotaOrder.setOrderId(orderId);
			resultList.add(quotaOrder);
			MapByDay.put(DateUtils.format(startDay, DateUtils.SHORT_FORMAT), quotaOrder);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		paramMap.put("hour", hour - 1);
		paramMap.put("day", DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
		List<QuotaOrder> quotaOrders = quotaOrderMapper.listByDay(paramMap);
		for (QuotaOrder quotaOrder : quotaOrders) {
			index = new Long(DateUtils.getDValue2Day(sDate,
					DateUtils.parse(quotaOrder.getCreDay().toString(), DateUtils.SHORT_FORMAT))).intValue();
			resultList.set(index, quotaOrder);
			MapByDay.put(quotaOrder.getCreDay().toString(), quotaOrder);
		}
		QuotaOrder allQuotaOrder = new QuotaOrder();
		Orders orders = ordersMapper.selectByPrimaryKey(orderId);
		allQuotaOrder.setOrderName(orders.getName());
		allQuotaOrder.setOrderId(orderId);
		for (String dayStr : MapByDay.keySet()) {
			QuotaOrder quotaOrder = MapByDay.get(dayStr);
			paramMap.put("eTime", dayStr);
			Long quotaDidStartDate = Long.valueOf(
					DateUtils.format(DateUtils.parse(dayStr, DateUtils.SHORT_FORMAT), DateUtils.FORMAT_YMDHMS));
			Long quotaDidEndDate = Long
					.valueOf(DateUtils.format(
							DateUtils.getNextDaysDate(
									DateUtils.parse(dayStr, DateUtils.SHORT_FORMAT), 1),
							DateUtils.FORMAT_YMDHMS));
			computeQuotaOrder(quotaOrder, paramMap, quotaDidStartDate, quotaDidEndDate);
			allQuotaOrder.setReq(allQuotaOrder.getReq() + quotaOrder.getReq());
			allQuotaOrder.setExp(allQuotaOrder.getExp() + quotaOrder.getExp());
			allQuotaOrder.setClk(allQuotaOrder.getClk() + quotaOrder.getClk()) ;
			allQuotaOrder.setRealCost(allQuotaOrder.getRealCost() + quotaOrder.getRealCost());
			allQuotaOrder.setCpm(allQuotaOrder.getCpm() + quotaOrder.getCpm());
			allQuotaOrder.setCpc(allQuotaOrder.getCpc() + quotaOrder.getCpc());
			allQuotaOrder.setCpa(allQuotaOrder.getCpa() + quotaOrder.getCpa());
			allQuotaOrder.setCvr(allQuotaOrder.getCvr() + quotaOrder.getCvr());
			allQuotaOrder.setOrderActive(allQuotaOrder.getOrderActive() + quotaOrder.getOrderActive());
		}
		//去掉汇总数据 前端计算
//		resultList.add(allQuotaOrder);
		return resultList;
	}

	@Override
	public SwaggerPage<List<QuotaOrder>> listByInit(Map<String, Object> paramMap) {
		Date sDate = DateUtils.parse(paramMap.get("sTime").toString(), DateUtils.SHORT_FORMAT);
		Date eDate = DateUtils.parse(paramMap.get("eTime").toString(), DateUtils.SHORT_FORMAT);
		Long quotaDidStartDate = Long.valueOf(DateUtils.format(sDate, DateUtils.FORMAT_YMDHMS));
		Long quotaDidEndDate = Long
				.valueOf(DateUtils.format(DateUtils.getNextDaysDate(eDate, 1), DateUtils.FORMAT_YMDHMS));
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		SwaggerPage<List<QuotaOrder>> page = new SwaggerPage<>();
		if (sDate.compareTo(eDate) > 0) {
			return null;
		} else if (eDate.compareTo(today) > 0) {
			eDate = today;
			paramMap.put("eTime", DateUtils.format(eDate, DateUtils.SHORT_FORMAT));
		}
		int count = ordersMapper.countByMap(paramMap);
		if (count > 0) {
			page = new SwaggerPage<>(count, (Integer) paramMap.get("cp"), (Integer) paramMap.get("ps"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
			paramMap.put("hour", hour - 1);
			paramMap.put("day", DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
			paramMap.put("start", page.getStartPosition());
			paramMap.put("limit", page.getPageSize());
			List<QuotaOrder> quotaOrders = quotaOrderMapper.listByInit(paramMap);
			if (quotaOrders != null) {
				for (QuotaOrder quotaOrder : quotaOrders) {
					paramMap.put("id", quotaOrder.getOrderId());
					computeQuotaOrder(quotaOrder, paramMap, quotaDidStartDate, quotaDidEndDate);
				}
			}
			page.setData(quotaOrders);
		} else {
			page = new SwaggerPage<>(count);
		}
		return page;
	}

	private void computeQuotaOrder(QuotaOrder quotaOrder, Map<String, Object> paramMap, Long quotaDidStartDate,
			Long quotaDidEndDate) {
		Map<String, Object> entityParamMap = Maps.newHashMap();
		Long active = 0l;
		entityParamMap.put("oid", paramMap.get("id"));
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
			// 若是获取今天的数据，则从redis中获取最新数据
			if (paramMap.get("eTime").toString().equals(paramMap.get("day").toString())) {
				Integer hour = Integer.parseInt(paramMap.get("hour").toString()) + 1;
				String todayString = paramMap.get("day").toString();
				String hourString = hour < 10 ? "0" + hour : hour.toString();
				String hourString1 = hour < 11 ? "0" + (hour - 1) : "" + (hour - 1);
				for (Integer entId : entIds) {
					VoUtils.handleRealTimeData(entId, quotaOrder, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
							KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString);
					VoUtils.handleRealTimeData(entId, quotaOrder, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
							KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString1);
				}
			}
			List<QuotaDid> quotaDids = quotaDidService.listByEntId(null, entIds, quotaDidStartDate, quotaDidEndDate);
			if (quotaDids != null && quotaDids.size() > 0) {
				active += quotaDids.size();
				quotaOrder.setOrderActive(active);
			}
			quotaOrder.computeQuotaBase(quotaOrder, active);
		}

	}

}

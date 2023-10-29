package com.iwanvi.nvwa.web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.PutMapper;
import com.iwanvi.nvwa.dao.QuotaPutMapper;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.EntityExample;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.QuotaDid;
import com.iwanvi.nvwa.dao.model.QuotaPut;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.QuotaDidService;
import com.iwanvi.nvwa.web.service.QuotaPutService;
import com.iwanvi.nvwa.web.util.VoUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
public class QuotaPutServiceImpl implements QuotaPutService {

	@Autowired
	private QuotaPutMapper quotaPutMapper;
	@Autowired
	private PutMapper putMapper;
	@Autowired
	private QuotaDidService quotaDidService;
	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private OrderPutMapper orderPutMapper;

	public static final String KEY_REDIS_CREATIVE_QUOTA_HOUR = "creative:hour:{0}:{1}:{2}:{3}";// creative:hour:{quota}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_CREATIVE_CALLBACK_HOUR = "callback:{0}:clk:{1}:{2}";// callback:{cid}:clk:{ymd}:{HH}

	@Override
	public List<QuotaPut> listByDay(Map<String, Object> paramMap) {
		Date sDate = DateUtils.parse(paramMap.get("sTime").toString(), DateUtils.SHORT_FORMAT);
		Date eDate = DateUtils.parse(paramMap.get("eTime").toString(), DateUtils.SHORT_FORMAT);
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (sDate.compareTo(eDate) > 0) {
			return null;
		} else if (eDate.compareTo(today) > 0) {
			eDate = today;
			paramMap.put("eTime", DateUtils.format(eDate, DateUtils.SHORT_FORMAT));
		}
		List<QuotaPut> resultList = Lists.newArrayList();
		Integer putId = Integer.valueOf(paramMap.get("putId").toString());
		Integer putType = Integer.valueOf(paramMap.get("putType").toString());
		for (Date startDate = sDate; startDate.compareTo(eDate) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
				1)) {
			QuotaPut quotaPut = new QuotaPut();
			quotaPut.setCreDay(Integer.valueOf(DateUtils.format(startDate, DateUtils.SHORT_FORMAT)));
			quotaPut.setPutId(putId);
			resultList.add(quotaPut);
		}
		Calendar calendar = Calendar.getInstance();
		Integer hour = calendar.get(calendar.HOUR_OF_DAY);
		String todayString = DateUtils.format(today, DateUtils.SHORT_FORMAT);
		paramMap.put("hour", hour - 1);
		paramMap.put("day", todayString);
		List<QuotaPut> quotaPuts = quotaPutMapper.listByDay(paramMap);
		if (quotaPuts != null) {
			for (QuotaPut quotaPut : quotaPuts) {
				Long index = DateUtils.getDValue2Day(sDate,
						DateUtils.parse(quotaPut.getCreDay().toString(), DateUtils.SHORT_FORMAT));
				resultList.set(index.intValue(), quotaPut);
			}
		}
		for (QuotaPut quotaPut : resultList) {
			Long quotaStartDate = Long.valueOf(DateUtils.format(
					DateUtils.parse(quotaPut.getCreDay().toString(), DateUtils.SHORT_FORMAT), DateUtils.FORMAT_YMDHMS));
			Long quotaEndDate = Long
					.valueOf(DateUtils.format(
							DateUtils.getNextDaysDate(
									DateUtils.parse(quotaPut.getCreDay().toString(), DateUtils.SHORT_FORMAT), 1),
							DateUtils.FORMAT_YMDHMS));
			List<Integer> entIds = getEntIdsByPid(quotaPut);
			//获取激活数
			computeQuotaPut(entIds, quotaPut, quotaStartDate, quotaEndDate);
		}
		// 若是需获取今天的数据需访问redis获取最新的数据
		if (today.equals(eDate)) {
			QuotaPut quotaPut = resultList.get(resultList.size() - 1);
			List<Integer> entIds = getEntIdsByPid(quotaPut);
			String hourString = hour < 10 ? "0" + hour : hour.toString();
			String hourString1 = hour < 11 ? "0" + (hour - 1) : "" + (hour - 1);
			for (Integer entId : entIds) {
				//获取当前小时的数据
				VoUtils.handleRealTimeData(entId, quotaPut, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
						KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString);
				//获取前一小时的数据
				VoUtils.handleRealTimeData(entId, quotaPut, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
						KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString1);
			}
		}
		//计算分日数据的总数
		QuotaPut allQuotaPut = new QuotaPut();
		if (Constants.PUT_TYPE_ACCURATE.equals(putType)) {
			Put put = putMapper.selectByPrimaryKey(putId);
		}else if (Constants.PUT_TYPE_ORDER.equals(putType)) {
			OrderPut orderPut = orderPutMapper.selectByPrimaryKey(putId);
			allQuotaPut.setPutName(orderPut.getPutName());
		}
		allQuotaPut.setPutId(putId);
		for (QuotaPut quotaPut : resultList) {
			allQuotaPut.setReq(allQuotaPut.getReq() + quotaPut.getReq());
			allQuotaPut.setExp(allQuotaPut.getExp() + quotaPut.getExp());
			allQuotaPut.setClk(allQuotaPut.getClk() + quotaPut.getClk()) ;
			allQuotaPut.setRealCost(allQuotaPut.getRealCost() + quotaPut.getRealCost());
			allQuotaPut.setCpm(allQuotaPut.getCpm() + quotaPut.getCpm());
			allQuotaPut.setCpc(allQuotaPut.getCpc() + quotaPut.getCpc());
			allQuotaPut.setCpa(allQuotaPut.getCpa() + quotaPut.getCpa());
			allQuotaPut.setCvr(allQuotaPut.getCvr() + quotaPut.getCvr());
			allQuotaPut.setPutActive(allQuotaPut.getPutActive() + quotaPut.getPutActive());
		}
		//去掉汇总数据 前端计算
//		resultList.add(allQuotaPut);
		return resultList;
	}

	@Override
	public SwaggerPage<List<QuotaPut>> listByPidOrOid(Map<String, Object> paramMap) {
		Date sDate = DateUtils.parse(paramMap.get("sTime").toString(), DateUtils.SHORT_FORMAT);
		Date eDate = DateUtils.parse(paramMap.get("eTime").toString(), DateUtils.SHORT_FORMAT);
		Long quotaStartDate = Long.valueOf(DateUtils.format(sDate, DateUtils.FORMAT_YMDHMS));
		Long quotaEndDate = Long
				.valueOf(DateUtils.format(DateUtils.getNextDaysDate(eDate, 1), DateUtils.FORMAT_YMDHMS));
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (sDate.compareTo(eDate) > 0) {
			return null;
		} else if (eDate.compareTo(new Date()) > 0) {
			eDate = today;
			paramMap.put("eTime", DateUtils.format(eDate, DateUtils.SHORT_FORMAT));
		}
		SwaggerPage<List<QuotaPut>> page;
		int count = 0;
		if (paramMap.get("putType") != null && !Constants.PUT_TYPE_ORDER.equals(Integer.valueOf(paramMap.get("putType").toString()))) {
			count = putMapper.countByMap(paramMap);
		} else {
			count = orderPutMapper.countByMap(paramMap);
		}
		if (count > 0) {
			page = new SwaggerPage<>(count, Integer.valueOf(paramMap.get("cp").toString()),
					Integer.valueOf(paramMap.get("ps").toString()));
			paramMap.put("start", page.getStartPosition());
			paramMap.put("limit", page.getPageSize());
			Calendar calendar = Calendar.getInstance();
			Integer hour = calendar.get(calendar.HOUR_OF_DAY);
			String todayString = DateUtils.format(today, DateUtils.SHORT_FORMAT);
			paramMap.put("hour", hour - 1);
			paramMap.put("day", todayString);
			List<QuotaPut> quotaPuts = quotaPutMapper.listByOidOrPid(paramMap);
			if (quotaPuts != null) {
				boolean isToday = false;
				// 若需获取今天的数据需要访问接口来获取最新的数据
				if (today.equals(eDate)) {
					isToday = true;
				}
				for (QuotaPut quotaPut : quotaPuts) {
					List<Integer> entIds = getEntIdsByPid(quotaPut);
					// 若结束日期是今天 则获取本日当前及前一小时的数据
					if (isToday) {
						String hourString = hour < 10 ? "0" + hour : hour.toString();
						String hourString1 = hour < 11 ? "0" + (hour - 1) : "" + (hour - 1);
						for (Integer entId : entIds) {
							VoUtils.handleRealTimeData(entId, quotaPut, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
									KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString);
							VoUtils.handleRealTimeData(entId, quotaPut, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
									KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString1);
						}
					}
					//获取激活数
					computeQuotaPut(entIds, quotaPut, quotaStartDate, quotaEndDate);
				}

			}
			page.setData(quotaPuts);
		} else {
			page = new SwaggerPage<>(count);
		}
		return page;
	}

	private void computeQuotaPut(List<Integer> entIds, QuotaPut quotaPut, Long quotaStartDate, Long quotaEndDate) {
		Long active = 0l;
		if (entIds != null && entIds.size() > 0) {
			List<QuotaDid> quotaDids = quotaDidService.listByEntId(null, entIds, quotaStartDate, quotaEndDate);
			if (quotaDids != null && quotaDids.size() > 0) {
				active += Long.valueOf(quotaDids.size());
				quotaPut.setPutActive(active);
			}
		}
		quotaPut.computeQuotaBase(quotaPut, active);
	}

	private List<Integer> getEntIdsByPid(QuotaPut quotaPut) {
		EntityExample entityExample = new EntityExample();
		entityExample.createCriteria().andPidEqualTo(quotaPut.getPutId());
		List<Entity> entities = entityMapper.selectByExample(entityExample);
		List<Integer> entIds = Lists.transform(entities, new Function<Entity, Integer>() {

			@Override
			public Integer apply(Entity input) {
				return input.getId();
			}
		});
		return entIds;
	}

}

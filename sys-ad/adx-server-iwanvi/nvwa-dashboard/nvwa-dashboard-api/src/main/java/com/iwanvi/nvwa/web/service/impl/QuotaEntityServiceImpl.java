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
import com.iwanvi.nvwa.dao.QuotaEntityMapper;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.QuotaDid;
import com.iwanvi.nvwa.dao.model.QuotaEntity;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.QuotaDidService;
import com.iwanvi.nvwa.web.service.QuotaEntityService;
import com.iwanvi.nvwa.web.util.VoUtils;
import com.google.common.collect.Lists;

@Service
public class QuotaEntityServiceImpl implements QuotaEntityService {

	@Autowired
	private QuotaEntityMapper quotaEntityMapper;
	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private QuotaDidService quotaDidService;
	@Autowired
	private RedisDao redisDao;

	public static final String KEY_REDIS_CREATIVE_QUOTA_HOUR = "creative:hour:{0}:{1}:{2}:{3}";// creative:hour:{quota}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_CREATIVE_CALLBACK_HOUR = "callback:{0}:clk:{1}:{2}";// callback:{cid}:clk:{ymd}:{HH}

	@Override
	public List<QuotaEntity> reportByDay(Map<String, Object> paramMap) {
		Date sDate = DateUtils.parse(paramMap.get("sTime").toString(), DateUtils.SHORT_FORMAT);
		Date eDate = DateUtils.parse(paramMap.get("eTime").toString(), DateUtils.SHORT_FORMAT);
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (sDate.compareTo(eDate) > 0 || today.compareTo(sDate) < 0) {
			return null;
		} else if (eDate.compareTo(today) > 0) {
			eDate = today;
			paramMap.put("eTime", DateUtils.format(eDate, DateUtils.SHORT_FORMAT));
		}
		List<QuotaEntity> resultList = Lists.newArrayList();
		Integer entId = Integer.valueOf(paramMap.get("entId").toString());
		for (Date startDate = sDate; startDate.compareTo(eDate) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
				1)) {
			QuotaEntity quotaEntity = new QuotaEntity();
			quotaEntity.setCreDay(Integer.valueOf(DateUtils.format(startDate, DateUtils.SHORT_FORMAT)));
			quotaEntity.setEntId(entId);
			resultList.add(quotaEntity);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
		String todayString = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
		paramMap.put("hour", hour - 1);
		paramMap.put("day", todayString);
		List<QuotaEntity> quotaEntities = quotaEntityMapper.listByDay(paramMap);
		if (quotaEntities != null) {
			for (QuotaEntity quotaEntity : quotaEntities) {
				Date creDate = DateUtils.parse(quotaEntity.getCreDay().toString(), DateUtils.SHORT_FORMAT);
				Long index = DateUtils.getDValue2Day(sDate, creDate);
				resultList.set(index.intValue(), quotaEntity);
			}
		}
		// 若查看现在天数的数据需要调用接口获取
		if (today.equals(eDate)) {
			String hourString = hour < 10 ? "0" + hour : hour.toString();
			String hourString1 = (hour - 1) < 10 ? "0" + (hour - 1) : String.valueOf((hour - 1));
			QuotaEntity quotaEntity = resultList.get(resultList.size() - 1);
			// 从redis获取本小时的数据
			VoUtils.handleRealTimeData(quotaEntity.getEntId(), quotaEntity, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
					KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString);
			// 从redis获取一个小时前的数据
			VoUtils.handleRealTimeData(quotaEntity.getEntId(), quotaEntity, redisDao, KEY_REDIS_CREATIVE_QUOTA_HOUR,
					KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString1);
			quotaEntity.computeQuotaBase(quotaEntity, null);
		}
		//计算分日数据的总数
		QuotaEntity allQuotaEntity = new QuotaEntity();
		Entity entity = entityMapper.selectByPrimaryKey(entId);
		allQuotaEntity.setEntityName(entity.getEntityName());
		allQuotaEntity.setEntId(entId);
		for (QuotaEntity quotaEntity : quotaEntities) {
			allQuotaEntity.setReq(allQuotaEntity.getReq() + quotaEntity.getReq());
			allQuotaEntity.setExp(allQuotaEntity.getExp() + quotaEntity.getExp());
			allQuotaEntity.setClk(allQuotaEntity.getClk() + quotaEntity.getClk()) ;
			allQuotaEntity.setRealCost(allQuotaEntity.getRealCost() + quotaEntity.getRealCost());
			allQuotaEntity.setCpm(allQuotaEntity.getCpm() + quotaEntity.getCpm());
			allQuotaEntity.setCpc(allQuotaEntity.getCpc() + quotaEntity.getCpc());
			allQuotaEntity.setCpa(allQuotaEntity.getCpa() + quotaEntity.getCpa());
			allQuotaEntity.setCvr(allQuotaEntity.getCvr() + quotaEntity.getCvr());
			allQuotaEntity.setEntityActive(allQuotaEntity.getEntityActive() + quotaEntity.getEntityActive());
		}
		//去掉汇总数据 前端计算
//		resultList.add(allQuotaEntity);
		return resultList;
	}

	@Override
	public SwaggerPage<List<QuotaEntity>> listByPid(Map<String, Object> paramMap) {
		Date sDate = DateUtils.parse(paramMap.get("sTime").toString(), DateUtils.SHORT_FORMAT);
		Date eDate = DateUtils.parse(paramMap.get("eTime").toString(), DateUtils.SHORT_FORMAT);
		long didStartTime = sDate.getTime();
		long didEndTime = DateUtils.getNextDaysDate(eDate, 1).getTime();
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (sDate.compareTo(eDate) > 0 || today.compareTo(sDate) < 0) {
			return null;
		} else if (eDate.compareTo(today) > 0) {
			eDate = today;
			paramMap.put("eTime", DateUtils.format(eDate, DateUtils.SHORT_FORMAT));
		}
		SwaggerPage<List<QuotaEntity>> page = null;
		int count = entityMapper.countListByMap(paramMap);
		if (count > 0) {
			page = new SwaggerPage<>(count, Integer.valueOf(paramMap.get("cp").toString()),
					Integer.valueOf(paramMap.get("ps").toString()));
			paramMap.put("start", page.getStartPosition());
			paramMap.put("limit", page.getPageSize());
			// 不获取当前时间和当前一个小时之前的数据
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
			String todayString = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
			paramMap.put("hour", hour - 1);
			paramMap.put("day", todayString);
			List<QuotaEntity> quotaEntities = quotaEntityMapper.listByUidAndTime(paramMap);
			for (QuotaEntity quotaEntity : quotaEntities) {
				//根据创意获取激活数
				long active = 0L;
				List<QuotaDid> quotaDids = quotaDidService.listByEntId(quotaEntity.getEntId(), null, didStartTime,
						didEndTime);
				if (quotaDids != null && quotaDids.size() > 0) {
					active += quotaDids.size();
					quotaEntity.setEntityActive(active);
				}
			}
			// 若是要获取今天的数据则需要从redis获取
			if (quotaEntities != null && today.equals(eDate)) {
				String hourString = hour < 10 ? "0" + hour : hour.toString();
				String hourString1 = (hour - 1) < 10 ? "0" + (hour - 1) : String.valueOf((hour - 1));
				for (QuotaEntity quotaEntity : quotaEntities) {
					// 从redis获取本小时的数据
					VoUtils.handleRealTimeData(quotaEntity.getEntId(), quotaEntity, redisDao,
							KEY_REDIS_CREATIVE_QUOTA_HOUR, KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString);
					// 从redis获取一个小时前的数据
					VoUtils.handleRealTimeData(quotaEntity.getEntId(), quotaEntity, redisDao,
							KEY_REDIS_CREATIVE_QUOTA_HOUR, KEY_REDIS_CREATIVE_CALLBACK_HOUR, todayString, hourString1);
					quotaEntity.computeQuotaBase(quotaEntity, null);
				}
			}
			page.setData(quotaEntities);
		} else {
			page = new SwaggerPage<>(count);
		}
		return page;
	}

}

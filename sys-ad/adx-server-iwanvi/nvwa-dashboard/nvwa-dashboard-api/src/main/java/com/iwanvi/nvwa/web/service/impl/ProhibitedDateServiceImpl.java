package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.ProhibitedDateMapper;
import com.iwanvi.nvwa.dao.model.ProhibitedDate;
import com.iwanvi.nvwa.dao.model.ProhibitedDateExample;
import com.iwanvi.nvwa.web.service.ProhibitedDateService;
import com.iwanvi.nvwa.web.util.WebConstants;

@Service
public class ProhibitedDateServiceImpl implements ProhibitedDateService {

	@Autowired
	private ProhibitedDateMapper prohibitedDateMapper;
	
	@Autowired
	private RedisDao redisDao;

	@SuppressWarnings("static-access")
	@Override
	public Map<String, List<Integer>> getProhibitedDateByMonth(String month) {
		Map<String, List<Integer>> finalResult = new HashMap<String, List<Integer>>();
		List<Integer> result = new ArrayList<Integer>();

		Date newDate = DateUtils.parse(month, DateUtils.SHORT_FORMAT_MONTH);
		Date lastDay = DateUtils.getLastDayOfMonth(newDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastDay);
		Integer day = calendar.get(calendar.DAY_OF_MONTH);
		for (int i = 0; i < day; i++) {
			result.add(Constants.INTEGER_0);
		}
		ProhibitedDateExample dateExample = new ProhibitedDateExample();
		dateExample.createCriteria().andProhibitedDataLike(month + "%");
		List<ProhibitedDate> list = prohibitedDateMapper.selectByExample(dateExample);

		if (!CollectionUtils.isEmpty(list)) {
			List<Integer> dates = FluentIterable.from(list).transform((ProhibitedDate prohibitedDate) -> {
				String prohibitedData = prohibitedDate.getProhibitedData();
				String dayTime = prohibitedData.substring(prohibitedData.length() - Constants.INTEGER_2,
						prohibitedData.length());
				return Integer.valueOf(dayTime);
			}).toList();
			dates.forEach(date -> {
				result.set(date - Constants.INTEGER_1, Constants.INTEGER_1);
			});
		}
		finalResult.put("time", result);
		return finalResult;
	}

	@Override
	@Transactional
	public void setProhibitedDateByMonth(List<Map<String, List<Integer>>> list) {
		if (CollectionUtils.isEmpty(list))
			throw new ServiceException("param is empty .");
		ProhibitedDateExample example = new ProhibitedDateExample();
		prohibitedDateMapper.deleteByExample(example);

		list.forEach(map -> {
			map.entrySet().forEach(entry -> {
				String month = entry.getKey();
				List<Integer> days = entry.getValue();
				if (CollectionUtils.isEmpty(days))
					throw new ServiceException(month + "list is empty .");
				String dayTime = StringUtils.EMPTY;
				for (int i = 0; i < days.size(); i++) {
					if (days.get(i) == Constants.INTEGER_1) {
						dayTime = String.valueOf((i + 1));
						if (dayTime.length() == Constants.INTEGER_1) {
							dayTime = StringUtils.concat(Constants.INTEGER_0, dayTime);
						}
						dayTime = StringUtils.concat(month, dayTime);
						ProhibitedDate prohibitedDate = new ProhibitedDate();
						prohibitedDate.setProhibitedData(dayTime);
						prohibitedDateMapper.insert(prohibitedDate);
					}
				}
			});
		});
		ProhibitedDateExample prohibitedDateExample = new ProhibitedDateExample();
		List<ProhibitedDate> byExample = prohibitedDateMapper.selectByExample(prohibitedDateExample);
		if (!CollectionUtils.isEmpty(byExample)) {
			List<Integer> cre_days = FluentIterable.from(byExample).transform((ProhibitedDate prohibitedDate) -> {
				return Integer.parseInt(prohibitedDate.getProhibitedData());
			}).toList();
			redisDao.set(WebConstants.KEY_PROHIBITED_DATE, JsonUtils.TO_JSON(cre_days));
		} else {
			redisDao.set(WebConstants.KEY_PROHIBITED_DATE, JsonUtils.TO_JSON(new ArrayList<Integer>()));
		}
	}
}

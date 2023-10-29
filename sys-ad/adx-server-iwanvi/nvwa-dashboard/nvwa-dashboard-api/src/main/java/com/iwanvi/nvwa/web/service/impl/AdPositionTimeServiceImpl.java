package com.iwanvi.nvwa.web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AdPositionTimeMapper;
import com.iwanvi.nvwa.dao.OrderPutAllMapper;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionTime;
import com.iwanvi.nvwa.dao.model.AdPositionTimeExample;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.web.service.AdPositionTimeService;

@Service
public class AdPositionTimeServiceImpl implements AdPositionTimeService {
	@Autowired
	private AdPositionTimeMapper adPositionTimeMapper;
	@Autowired
	private AdPositionMapper adPositionMapper;
	@Autowired
	private OrderPutMapper orderPutMapper;

	@Override
	public List<Map<String, Object>> scheduling(String adIds, String monthStr, Integer putId) {
		List<Map<String, Object>> resultList = Lists.newArrayList();
		List<Integer> adIdList = StringUtils.str2List4Int(adIds, Constants.SIGN_COMMA);
		for (Integer adId : adIdList) {
			Map<String, Object> resultMap = Maps.newHashMap();
			List<Integer> result = Lists.newArrayList();
			AdPosition adPosition = adPositionMapper.selectByPrimaryKey(adId);
			resultMap.put("adId", adId);
			resultMap.put("name", adPosition.getName());
			// 设置初始值
			resultMap.put("expDuration", 0);
			resultMap.put("changePage", 0);
			resultMap.put("changeChapter", 0);
			// 反显曝光设置
			toExpConfig(adId, putId, resultMap);
			// 通过广告位id和月份查询AdPositionTime表中是否存在响应的数据
			AdPositionTimeExample adPositionTimeExample = new AdPositionTimeExample();
			adPositionTimeExample.createCriteria().andAdPositionIdEqualTo(adId).andYearsEqualTo(monthStr)
					.andStateEqualTo(Constants.STATE_VALID);
			List<AdPositionTime> adPositionTimes = adPositionTimeMapper.selectByExample(adPositionTimeExample);
			if (adPositionTimes.isEmpty()) {
				// 若不存在则填充所要月份的无占位情况
				Date newDate = DateUtils.parse(monthStr, DateUtils.SHORT_FORMAT_MONTH);
				String sdString = DateUtils.format(newDate);
				Date lastDay = DateUtils.getLastDayOfMonth(newDate);
				String daString = DateUtils.format(lastDay);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(lastDay);
				// 获取所要月份的天数 填充为0
				Integer day = calendar.get(calendar.DAY_OF_MONTH);
				for (int i = 0; i < day; i++) {
					result.add(Constants.INTEGER_0);
				}
			} else {
				boolean isFirst = false;
				String oneTime;
				for (AdPositionTime adPositionTime : adPositionTimes) {
					String putTime = adPositionTime.getPutTime();
					if (!isFirst) {
						for (int i = 0; i < putTime.length(); i++) {
							oneTime = putTime.charAt(i) + "";
							if (Constants.INTEGER_1.toString().equals(oneTime)) {
								if (putId == null) {
									result.add(Constants.INTEGER_2);// 不可操作的排期
								} else {
									if (putId.equals(adPositionTime.getPutAllId())) {
										result.add(Constants.INTEGER_1);// 可操作的排期
									} else {
										result.add(Constants.INTEGER_2);// 不可操作的排期
									}
								}
							} else {
								result.add(Integer.valueOf(oneTime));// 空闲排期
							}
						}
						isFirst = true;
					} else {
						for (int i = 0; i < putTime.length(); i++) {
							oneTime = putTime.charAt(i) + "";
							if (Constants.INTEGER_1.toString().equals(oneTime)) {
								if (putId == null) {
									result.set(i, Constants.INTEGER_2);// 不可操作的排期
								} else {
									if (putId.equals(adPositionTime.getPutAllId())) {
										result.set(i, Constants.INTEGER_1);// 可操作的排期
									} else {
										result.set(i, Constants.INTEGER_2);// 不可操作的排期
									}
								}
							}
						}
					}
				}
			}
			resultMap.put("times", result);
			resultList.add(resultMap);
		}
		return resultList;
	}

	private void toExpConfig(Integer adId, Integer putId, Map<String, Object> resultMap) {
		if (putId != null) {
			OrderPutExample orderPutExample = new OrderPutExample();
			orderPutExample.createCriteria().andAdPositionEqualTo(adId)
					.andPutAllIdEqualTo(putId);
			OrderPut orderPut = orderPutMapper.selectOneByExample(orderPutExample);
			if (orderPut != null) {
				// 反显曝光设置
				resultMap.put("expDuration", orderPut.getExpDuration());
				resultMap.put("changePage", orderPut.getChangePage());
				resultMap.put("changeChapter", orderPut.getChangeChapter());
			}
		}
	}

}

package com.iwanvi.nvwa.web.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.PutMapper;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.PutExample;
import com.iwanvi.nvwa.web.service.EntityService;
import com.iwanvi.nvwa.web.service.OrderPutService;
import com.iwanvi.nvwa.web.service.PutService;
import com.iwanvi.nvwa.web.service.SyslogService;

@Component
public class TaskCrontab {
	public static final Logger logger = LoggerFactory.getLogger(TaskCrontab.class);

	@Autowired
	private PutService putService;
	@Autowired
	private PutMapper putMapper;
	@Autowired
	private SyslogService syslogService;
	@Autowired
	private EntityService entityService;
	@Autowired
	private OrderPutMapper orderPutMapper;
	@Autowired
	private OrderPutService orderPutService;
//	@Autowired
//	private SysCrontabService sysCrontabService;

	@Scheduled(cron = "0 0 0 * * ? ")
	public void modifyEveryday() {
		//修改精准投放的投放时间、时间段、限额
//		modifyPutLimitsEveryday();
		//修改订单投放的投放时间、时间段
		modifyOrderPutLimitsEveryday();
		//修改创意的第三方id
		entityService.checkExtIdsOfDate();
	}

//	@Scheduled(cron = "0 5 * * * ? ")
//	public void restOrderPutKPI() {
//		logger.info("in task restOrderPutKPI");
//		OrderPutExample orderPutExample = new OrderPutExample();
//		orderPutExample.createCriteria().andCostTypeNotEqualTo(Constants.COST_TYPE_CPT)
//				.andPutStateNotEqualTo(Constants.STATE_INVALID).andResetFaildEqualTo(Constants.STATE_VALID);
//		List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
//		List<Integer> restIdList = Lists.newArrayList();
//		if (orderPuts != null && orderPuts.size() > 0) {
//			for (OrderPut orderPut : orderPuts) {
//				restIdList.add(orderPut.getId());
//				if (orderPutService.resetPutLimit(orderPut)) {
//					OrderPut newOrderPut = new OrderPut();
//					newOrderPut.setId(orderPut.getId());
//					newOrderPut.setResetFaild(Constants.STATE_INVALID);
//					orderPutMapper.updateByPrimaryKeySelective(newOrderPut);
//					sysCrontabService.addSysCrontabCheckCount(orderPut.getId(), Constants.OBJ_ORDER_PUT,
//							Constants.OP_UPDATE);
//				}
//			}
//		}
//		logger.info("out task restOrderPutKPI success restIdList: {}", restIdList);
//	}

	private void modifyOrderPutLimitsEveryday() {
		OrderPutExample orderPutExample = new OrderPutExample();
		orderPutExample.createCriteria().andLimitsIsNotNull().andPutTypeEqualTo(Constants.PUT_TYPE_ORDER);
		List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
		OrderPut oldOrderPut = new OrderPut();
		if (orderPuts != null && orderPuts.size() > 0) {
			for (OrderPut orderPut : orderPuts) {
				BeanUtils.copyProperties(orderPut, oldOrderPut);
				List<Map<String, Object>> mapList = JsonUtils.TO_OBJ(orderPut.getLimits(), List.class);
				for (Map<String, Object> map : mapList) {
					String date = map.get("date").toString();
					String times = map.containsKey("times") ? map.get("times").toString() : "";
					Integer limit = map.containsKey("limit") ? (Integer) map.get("limit") : Integer.MAX_VALUE;
					List<String> dates = StringUtils.str2List(date, Constants.SIGN_MINUS);
					if (dates != null && dates.size() == 2) {
						Date startDate = DateUtils.parse(dates.get(0), DateUtils.SHORT_FORMAT);
						Date endDate = DateUtils.parse(dates.get(1), DateUtils.SHORT_FORMAT);
						Date nowDate = DateUtils.getFirstSecondOfDay(new Date());
						if (nowDate.compareTo(startDate) >= 0 && nowDate.compareTo(endDate) <= 0) {
							startDate = DateUtils.getFirstSecondOfDay(startDate);
							endDate = DateUtils.getLastSecondOfDay(endDate);
							if (!(startDate.equals(oldOrderPut.getBeginTime())
									|| endDate.equals(oldOrderPut.getEndTime()))) {
								orderPut.setBeginTime(DateUtils.getFirstSecondOfDay(startDate));
								orderPut.setEndTime(DateUtils.getLastSecondOfDay(endDate));
								orderPut.setTimeInterval(times);
								orderPut.setPutLimit(limit);
								orderPutService.update(orderPut, oldOrderPut);
								syslogService.addSyslog(0, 0, orderPut, this.getClass().getName(),
										"modifyOrderPutLimitsEveryday");
								logger.info("TaskCrontab orderPutTimeIntervalCrontab success date: {}", date);
							}
						}
					}
				}
			}
		}
	}

	public void modifyPutLimitsEveryday() {
		PutExample putExample = new PutExample();
		putExample.createCriteria().andLimitsIsNotNull();
		List<Put> puts = putMapper.selectByExample(putExample);
		Put oldPut = new Put();
		if (puts != null && puts.size() > 0) {
			for (Put put : puts) {
				BeanUtils.copyProperties(put, oldPut);
				String limits = put.getLimits();
				if (StringUtils.isNotBlank(limits)) {
					List<Map<String, Object>> mapList = JsonUtils.TO_OBJ(limits, List.class);
					for (Map<String, Object> map : mapList) {
						String date = map.get("date").toString();
						String times = map.get("times").toString();
						Integer limit = (Integer) map.get("limit");
						List<String> dates = StringUtils.str2List(date, Constants.SIGN_MINUS);
						if (dates != null && dates.size() == 2) {
							Date startDate = DateUtils.parse(dates.get(0), DateUtils.SHORT_FORMAT);
							Date endDate = DateUtils.parse(dates.get(1), DateUtils.SHORT_FORMAT);
							Date nowDate = DateUtils.getFirstSecondOfDay(new Date());
							boolean changePutDate = false;
							boolean changePutState = false;
							if (nowDate.compareTo(startDate) >= 0 && nowDate.compareTo(endDate) <= 0) {
								startDate = DateUtils.getFirstSecondOfDay(startDate);
								endDate = DateUtils.getLastSecondOfDay(endDate);
								if (!(startDate.equals(put.getBeginTime()) || endDate.equals(put.getEndTime()))) {
									put.setBeginTime(startDate);
									put.setEndTime(endDate);
									put.setTimeInterval(times);
									if (limit != null) {
										if (!limit.equals(put.getPutLimit())) {
											put.setPutLimit(limit * 100);
											boolean isReset = false;
											while (!isReset) {
												isReset = putService.resetPutLimit(put);
												logger.info(
														"reset adx interface that the put limit is changed putId: {}, limit: {}",
														put.getId(), limit);
											}
											if (isReset) {
												putService.update(put, oldPut);
												syslogService.addSyslog(0, 0, put, this.getClass().getName(),
														"modifyPutLimitsEveryday");
												logger.info(
														"TaskCrontab putTimeIntervalCrontab success date: {}, times: {}, limit: {}",
														date, times, limit);
											}
										} else {
											putService.update(put, oldPut);
											syslogService.addSyslog(0, 0, put, this.getClass().getName(),
													"modifyPutLimitsEveryday");
											logger.info(
													"TaskCrontab putTimeIntervalCrontab success date: {}, times: {}, limit: {}",
													date, times, limit);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

}

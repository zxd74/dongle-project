package com.iwanvi.nvwa.web.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AdPositionTimeMapper;
import com.iwanvi.nvwa.dao.DictionaryMapper;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.OrderPutAllMapper;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.OrdersMapper;
import com.iwanvi.nvwa.dao.PutPositionTimeMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionTime;
import com.iwanvi.nvwa.dao.model.Dictionary;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.EntityExample;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutAll;
import com.iwanvi.nvwa.dao.model.OrderPutAllExample;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.PutPositionTime;
import com.iwanvi.nvwa.dao.model.AdPositionTimeExample;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.OrdersExample;
import com.iwanvi.nvwa.dao.model.PutPositionTimeExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.OrderPutAllService;
import com.iwanvi.nvwa.web.service.OrderPutService;
import com.iwanvi.nvwa.web.vo.OrderPutAllQuery;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class OrderPutAllServiceImpl implements OrderPutAllService {

	private static Logger logger = LoggerFactory.getLogger(OrderPutAllServiceImpl.class);

	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderPutMapper orderPutMapper;
	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private DictionaryMapper dictionaryMapper;
	@Autowired
	private AdPositionMapper adPositionMapper;
	@Autowired
	private AdPositionTimeMapper adPositionTimeMapper;
	@Autowired
	private PutPositionTimeMapper putPositionTimeMapper;
	@Autowired
	private OrderPutAllMapper orderPutAllMapper;
	@Autowired
	private OrderPutService orderPutService;

	@Override
	public Page<OrderPutAll> selectPage(OrderPutAllQuery orderPutAllQuery, Integer adverId, List<Integer> advers) {
		Page<OrderPutAll> page = null;
		List<Integer> orderIds = null;
		int count = 0;
		OrderPutAllExample orderPutAllExample = null;
		if (adverId != null || (advers != null && advers.size() > 0)) {
			OrdersExample ordersExample = new OrdersExample();
			com.iwanvi.nvwa.dao.model.OrdersExample.Criteria criteria = ordersExample.createCriteria();
			if (adverId != null) {
				criteria.andCustIdEqualTo(adverId);
			} else if (advers != null && advers.size() > 0) {
				criteria.andCustIdIn(advers);
			}
			List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);
			if (ordersList.size() == 0) {
				return new Page<>(Constants.INTEGER_0);
			}
			orderIds = Lists.transform(ordersList, (Orders oneOrder) -> oneOrder.getId());
		}
		orderPutAllExample = orderPutAllQuery.toExample(orderIds);
		count = new Long(orderPutAllMapper.countByExample(orderPutAllExample)).intValue();
		if (count > 0) {
			page = new Page<>(count, orderPutAllQuery.getCp(), orderPutAllQuery.getPs());
			orderPutAllExample.setOrderByClause("run_state desc,id desc");
			orderPutAllExample.setOffset(page.getStartPosition());
			orderPutAllExample.setRows(page.getPageSize());
			List<OrderPutAll> orderPutAlls = orderPutAllMapper.selectByExample(orderPutAllExample);
			fillOrderPut(orderPutAlls);
			page.setData(orderPutAlls);
		} else {
			page = new Page<>(count);
		}
		return page;
	}

	private void fillOrderPut(List<OrderPutAll> orderPutAlls) {
		for (OrderPutAll oneOrderPutAll : orderPutAlls) {
			String adPositions = oneOrderPutAll.getAdPosition();
			if (StringUtils.isNotBlank(adPositions)) {
				oneOrderPutAll.setAdPosition(adPositions.substring(1, adPositions.length() - 1));
			}
			Orders orders = ordersMapper.selectByPrimaryKey(oneOrderPutAll.getOid());
			oneOrderPutAll.setOrderName(orders.getName());
			Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(oneOrderPutAll.getCostType());
			oneOrderPutAll.setCostTypeName(dictionary.getDicValue());
			List<Integer> positions = StringUtils.str2List4Int(oneOrderPutAll.getAdPosition(), Constants.SIGN_COMMA);
			oneOrderPutAll.setPositionNum(positions.size());
		}
	}

	@Override
	public List<OrderPutAll> selectList(OrderPutAllQuery orderPutAllQuery, Integer adverId, List<Integer> advers) {
		List<Integer> orderIds = Lists.newArrayList();
		if (adverId != null || (advers != null && advers.size() > 0)) {
			OrdersExample ordersExample = new OrdersExample();
			com.iwanvi.nvwa.dao.model.OrdersExample.Criteria criteria = ordersExample.createCriteria();
			if (adverId != null) {
				criteria.andCustIdEqualTo(adverId);
			} else if (advers != null && advers.size() > 0) {
				criteria.andCustIdIn(advers);
			}
			List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);
			if (ordersList.size() == 0) {
				return Lists.newArrayList();
			}
			orderIds = Lists.transform(ordersList, (Orders oneOrder) -> oneOrder.getId());
		}
		OrderPutAllExample orderPutAllExample = orderPutAllQuery.toExample(orderIds);
		orderPutAllExample.setOrderByClause("run_state desc,id desc");
		return orderPutAllMapper.selectByExample(orderPutAllExample);
	}

	@Override
	public void update(OrderPutAll orderPutAll) {
		if (!Constants.STATE_INVALID.equals(orderPutAll.getPutState())) {
			// 状态有效时
			List<AdPositionTime> adPositionTimes = orderPutAll.getAdPositionTimes();
			if (Constants.COST_TYPE_CPT.equals(orderPutAll.getCostType()) && adPositionTimes != null
					&& adPositionTimes.size() > 0) {
				for (AdPositionTime adPositionTime : adPositionTimes) {
					if (StringUtils.isNotBlank(adPositionTime.getPutTime())) {
						checkTimeIsExist(orderPutAll.getId(), adPositionTime);
						AdPositionTimeExample adPositionTimeExample = new AdPositionTimeExample();
						adPositionTimeExample.createCriteria().andAdPositionIdEqualTo(adPositionTime.getAdPositionId())
								.andYearsEqualTo(adPositionTime.getYears()).andStateEqualTo(Constants.STATE_VALID)
								.andPutAllIdEqualTo(orderPutAll.getId());
						List<AdPositionTime> adPositionTimeList = adPositionTimeMapper
								.selectByExample(adPositionTimeExample);
						if (adPositionTime.getExpDuration() == null) {
							adPositionTime.setExpDuration(0);
						}
						if (adPositionTime.getChangePage() == null) {
							adPositionTime.setChangePage(0);
						}
						if (adPositionTime.getChangeChapter() == null) {
							adPositionTime.setChangeChapter(0);
						}
						if (adPositionTimeList.isEmpty()) {
							adPositionTime.setState(Constants.STATE_VALID);
							adPositionTime.setPutAllId(orderPutAll.getId());
							adPositionTimeMapper.insertSelective(adPositionTime);
						} else {
							adPositionTime.setId(adPositionTimeList.get(0).getId());
							adPositionTimeMapper.updateByPrimaryKeySelective(adPositionTime);
						}
					}
				}
				handlePositionTime(orderPutAll);
			}
			if (StringUtils.isNotBlank(orderPutAll.getLimits())) {
				OrderPutAll oldOrderPutAll = orderPutAllMapper.selectByPrimaryKey(orderPutAll.getId());
				// 若是cpt计费类型则需判断是否需填充排期
				if (Constants.COST_TYPE_CPT.equals(orderPutAll.getCostType())) {
					fullLimits(orderPutAll, oldOrderPutAll.getLimits());
				}
				// 处理limits
				openLimits(orderPutAll, oldOrderPutAll, Constants.OP_UPDATE);
			} else {
				OrderPutExample orderPutExample = new OrderPutExample();
				orderPutExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId())
						.andPutStateEqualTo(Constants.STATE_VALID);
				List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
				for (OrderPut oldOrderPut : orderPuts) {
					OrderPut orderPut = new OrderPut();
					BeanUtils.copyProperties(orderPutAll, orderPut);
					orderPut.setPutName(null);
					orderPut.setPrice(0);
					orderPut.setId(null);
					orderPut.setId(oldOrderPut.getId());
					orderPutService.update(orderPut, oldOrderPut);
				}
			}
		} else {
			// 状态无效时
			if (Constants.COST_TYPE_CPT.equals(orderPutAll.getCostType())) {
				AdPositionTimeExample adPositionTimeExample = new AdPositionTimeExample();
				adPositionTimeExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId())
						.andStateEqualTo(Constants.STATE_VALID);
				AdPositionTime adPositionTime = new AdPositionTime();
				adPositionTime.setState(Constants.STATE_INVALID);
				adPositionTimeMapper.updateByExampleSelective(adPositionTime, adPositionTimeExample);
			}
			// 清空putPostionTime跟此投放相关联的数据
			PutPositionTimeExample putPositionTimeExample = new PutPositionTimeExample();
			putPositionTimeExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId());
			putPositionTimeMapper.deleteByExample(putPositionTimeExample);
			// 将拆分的orderPut子数据一一改成无效
			OrderPutExample orderPutExample = new OrderPutExample();
			orderPutExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId())
					.andPutStateNotEqualTo(Constants.STATE_INVALID);
			List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
			for (OrderPut orderPut : orderPuts) {
				OrderPut updatePut = new OrderPut();
				updatePut.setPutState(Constants.STATE_INVALID);
				updatePut.setId(orderPut.getId());
				orderPutService.update(updatePut, orderPut);
			}
		}
		if (!Constants.COST_TYPE_CPT.equals(orderPutAll.getCostType())) {
			orderPutAll.setPrice(0);
		}
		if (StringUtils.isNotBlank(orderPutAll.getLimits())) {
			// 前端会传入limitArray，无用删除防止过长
			List<Map<String, Object>> positionRelations = JsonUtils.TO_OBJ(orderPutAll.getLimits(), List.class);
			if (positionRelations != null) {
				for (Map<String, Object> oneRelationMap : positionRelations) {
					if (oneRelationMap.containsKey("limitArray")) {
						oneRelationMap.remove("limitArray");
					}
				}
			}
			orderPutAll.setLimits(JsonUtils.TO_JSON(positionRelations));
		}
		orderPutAll.setUpdateTime(new Date());
		orderPutAllMapper.updateByPrimaryKeySelective(orderPutAll);
	}

	private void fullLimits(OrderPutAll orderPutAll, String limits) {
		List<Map<String, Object>> newLimits = JsonUtils.TO_OBJ(orderPutAll.getLimits(), List.class);
		List<Map<String, Object>> oldLimits = JsonUtils.TO_OBJ(limits, List.class);
		for (int i = 0; i < newLimits.size(); i++) {
			List<String> yearMonthList = Lists.newArrayList();
			List<Map<String, Object>> newLimitsList = (List<Map<String, Object>>) newLimits.get(i).get("limits");
			List<Map<String, Object>> oldLimitsList = (List<Map<String, Object>>) oldLimits.get(i).get("limits");
			List<AdPositionTime> adPositionTimes = orderPutAll.getAdPositionTimes();
			for (AdPositionTime adPositionTime : adPositionTimes) {
				yearMonthList.add(adPositionTime.getYears());
			}
//				for (Map<String, Object> map : newLimitsList) {
//					String date = (String) map.get("date");
//					String yearMonth = StringUtils.str2List(date, Constants.SIGN_MINUS).get(0).substring(0, 6);
////					compareMap.put(yearMonth, map);
//					yearMonthList.add(yearMonth);
//				}
			for (Map<String, Object> map : oldLimitsList) {
				String date = (String) map.get("date");
				String yearMonth = StringUtils.str2List(date, Constants.SIGN_MINUS).get(0).substring(0, 6);
				if (!yearMonthList.contains(yearMonth)) {
					newLimitsList.add(map);
				}
			}
		}
		orderPutAll.setLimits(JsonUtils.TO_JSON(newLimits));
	}

	@Override
	public OrderPutAll info(Integer orderPutAllId) {
		OrderPutAll orderPutAll = orderPutAllMapper.selectByPrimaryKey(orderPutAllId);
		return orderPutAll;
	}

	@Override
	public void checkPutState(PutPositionTime putPositionTime, PutPositionTime oldPositionTime) {
		if (putPositionTime.getBeginTime() != null && putPositionTime.getEndTime() != null) {
			if (putPositionTime.getBeginTime().compareTo(new Date()) > 0) {
				putPositionTime.setTimeState(Constants.STATE_UNSTART);
			} else if (putPositionTime.getEndTime().compareTo(new Date()) < 0) {
				putPositionTime.setTimeState(Constants.STATE_EXPIRED);
			} else if (oldPositionTime != null
					&& Constants.STATE_BALANCE_INVALID.equals(oldPositionTime.getTimeState())) {
				putPositionTime.setTimeState(oldPositionTime.getTimeState());
			} else if (oldPositionTime != null && Constants.STATE_UNIT_LIMIT.equals(oldPositionTime.getTimeState())) {
				if (putPositionTime.getPutLimit() != null && oldPositionTime.getPutLimit() != null
						&& putPositionTime.getPutLimit() > oldPositionTime.getPutLimit()) {
					putPositionTime.setTimeState(Constants.STATE_VALID);
				} else {
					putPositionTime.setTimeState(oldPositionTime.getTimeState());
				}
			} else {
				putPositionTime.setTimeState(Constants.STATE_VALID);
			}
		}
	}

	@Override
	@Transactional
	public void add(OrderPutAll orderPutAll) {
		orderPutAll.setCreateTime(new Date());
		orderPutAll.setPutState(Constants.STATE_VALID);
		orderPutAll.setRunState(Constants.STATE_VALID);
		// 若是订单投放 且计费类型是cpt则需要处理排期信息
		List<AdPositionTime> adPositionTimes = orderPutAll.getAdPositionTimes();
		if (Constants.COST_TYPE_CPT.equals(orderPutAll.getCostType()) && adPositionTimes != null
				&& adPositionTimes.size() > 0) {
			for (AdPositionTime adPositionTime : adPositionTimes) {
				if (StringUtils.isNotBlank(adPositionTime.getPutTime())) {
					// 判断多选日期是否已占位
					checkTimeIsExist(null, adPositionTime);
				}
			}
			handlePositionTime(orderPutAll);
		}
		if (StringUtils.isNotBlank(orderPutAll.getLimits())) {
			// 前端会传入limitArray，无用删除防止过长
			List<Map<String, Object>> positionRelations = JsonUtils.TO_OBJ(orderPutAll.getLimits(), List.class);
			if (positionRelations != null) {
				for (Map<String, Object> oneRelationMap : positionRelations) {
					if (oneRelationMap.containsKey("limitArray")) {
						oneRelationMap.remove("limitArray");
					}
				}
			}
			orderPutAll.setLimits(JsonUtils.TO_JSON(positionRelations));
		}
		orderPutAllMapper.insertSelective(orderPutAll);
		orderPutAllMapper.countByExample(null);
		if (StringUtils.isNotBlank(orderPutAll.getLimits())) {
			openLimits(orderPutAll, null, Constants.OP_ADD);
		}
		// 若是订单投放 且计费类型是cpt则需要处理排期信息
		if (Constants.COST_TYPE_CPT.equals(orderPutAll.getCostType()) && adPositionTimes != null
				&& adPositionTimes.size() > 0) {
			for (AdPositionTime adPositionTime : adPositionTimes) {
				if (StringUtils.isNotBlank(adPositionTime.getPutTime())) {
					adPositionTime.setPutAllId(orderPutAll.getId());
					adPositionTime.setState(Constants.STATE_VALID);
					adPositionTimeMapper.insertSelective(adPositionTime);
				}
			}
		}
	}

	private void handlePositionTime(OrderPutAll orderPutAll) {
		List<AdPositionTime> adPositionTimes = orderPutAll.getAdPositionTimes();
		List<Map<String, Object>> limits = Lists.newArrayList();
		Map<Integer, Object> limitMap = Maps.newHashMap();
		for (AdPositionTime adPositionTime : adPositionTimes) {
			Map<String, Object> positionMap;
			List<Map<String, Object>> limitList;
			if (limitMap.containsKey(adPositionTime.getAdPositionId())) {
				positionMap = (Map<String, Object>) limitMap.get(adPositionTime.getAdPositionId());
				limitList = (List<Map<String, Object>>) positionMap.get("limits");
			} else {
				positionMap = Maps.newHashMap();
				limitList = Lists.newArrayList();
				positionMap.put("limits", limitList);
				positionMap.put("expDuration", adPositionTime.getExpDuration());
				positionMap.put("changePage", adPositionTime.getChangePage());
				positionMap.put("changeChapter", adPositionTime.getChangeChapter());
				limitMap.put(adPositionTime.getAdPositionId(), positionMap);
			}
			String putTimes = adPositionTime.getPutTime();
			String ym = adPositionTime.getYears();
			int k = 0;
			int z = 0;
			int g = 0;
			for (int i = 0; i < putTimes.length(); i++) {
				Map<String, Object> oneLimitMap = Maps.newHashMap();
				String dateStr = new String();
				Character oneDay = putTimes.charAt(i);
				Integer num = Integer.valueOf(oneDay.toString());
				if (Constants.INTEGER_1.equals(num)) {
					if (i == putTimes.length() - 1) {
						g = putTimes.length();
						if (z == 0) {
							z = putTimes.length();
						}
						dateStr = packDateStr(ym, z, g);
						oneLimitMap.put("date", dateStr);
						limitList.add(oneLimitMap);
					} else {
						if (k == 0) {
							z = i + 1;
						}
						k++;
						continue;
					}
				} else {
					if (k != 0) {
						k = 0;
						g = i;
						dateStr = packDateStr(ym, z, g);
						oneLimitMap.put("date", dateStr);
						limitList.add(oneLimitMap);
					}
				}
			}
		}
		Set<Integer> forSet = new TreeSet<>(limitMap.keySet());
		for (Integer key : forSet) {
			AdPosition adPosition = adPositionMapper.selectByPrimaryKey(key);
			if (adPosition == null) {
				throw new ServiceException("关联广告位不存在");
			}
			Map<String, Object> positionMap = (Map<String, Object>) limitMap.get(key);
			positionMap.put("id", key);
			positionMap.put("name", adPosition.getName());
			limits.add(positionMap);
		}
		orderPutAll.setLimits(JsonUtils.TO_JSON(limits));
	}

	private String packDateStr(String ym, int z, int g) {
		String dateStr;
		String zStr = z + "";
		String gStr = g + "";
		if (z < 10) {
//			dateStr = StringUtils.concat(ym, Constants.INTEGER_0, z, Constants.SIGN_MINUS, ym,
//					Constants.INTEGER_0, g);
			zStr = StringUtils.concat(Constants.INTEGER_0, z);
		}
		if (g < 10) {
			gStr = StringUtils.concat(Constants.INTEGER_0, g);
		}
		dateStr = StringUtils.concat(ym, zStr, Constants.SIGN_MINUS, ym, gStr);
		return dateStr;
	}

	private void checkTimeIsExist(Integer id, AdPositionTime adPositionTime) {
		char[] putTimeChars = adPositionTime.getPutTime().toCharArray();
		for (int i = 0; i < putTimeChars.length; i++) {
			String fa = putTimeChars[i] + "";
			if ((putTimeChars[i] + "").equals("1")) {
				AdPositionTimeExample adPositionTimeExample = new AdPositionTimeExample();
				com.iwanvi.nvwa.dao.model.AdPositionTimeExample.Criteria criteria = adPositionTimeExample
						.createCriteria();
				if (id != null) {
					criteria.andPutAllIdNotEqualTo(id);
				}
				criteria.andAdPositionIdEqualTo(adPositionTime.getAdPositionId())
						.andYearsEqualTo(adPositionTime.getYears()).andStateEqualTo(Constants.STATE_VALID);
				List<AdPositionTime> adPositionTimes = adPositionTimeMapper.selectByExample(adPositionTimeExample);
				for (AdPositionTime adTime : adPositionTimes) {
					String putTime = adTime.getPutTime();
					if (putTime.charAt(i) == putTimeChars[i]) {
						throw new ServiceException("排期存在已占位情况,请重新选择");
					}
				}
			}
		}
	}

	@Override
	public boolean checkNameIsExistByOrder(OrderPutAll orderPutAll) {
		OrderPutAllExample orderPutAllExample = new OrderPutAllExample();
		orderPutAllExample.createCriteria().andOidEqualTo(orderPutAll.getOid())
				.andPutNameEqualTo(orderPutAll.getPutName()).andPutStateNotEqualTo(Constants.STATE_INVALID);
		List<OrderPutAll> orderPutAlls = orderPutAllMapper.selectByExample(orderPutAllExample);
		if (orderPutAlls.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(OrderPutAll orderPutAll) {
		if (orderPutAll.getId() != null) {
			AdPositionTimeExample adPositionTimeExample = new AdPositionTimeExample();
			adPositionTimeExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId())
					.andStateEqualTo(Constants.STATE_VALID);
			AdPositionTime adPositionTime = new AdPositionTime();
			adPositionTime.setState(Constants.STATE_INVALID);
			adPositionTimeMapper.updateByExampleSelective(adPositionTime, adPositionTimeExample);
			orderPutMapper.deleteByPrimaryKey(orderPutAll.getId());
		}
	}

	private void openLimits(OrderPutAll orderPutAll, OrderPutAll oldOrderPutAll, Integer opType) {
		if (StringUtils.isNotBlank(orderPutAll.getLimits())) {
			if (oldOrderPutAll != null && !orderPutAll.getLimits().equals(oldOrderPutAll.getLimits())) {
				PutPositionTimeExample putPositionTimeExample = new PutPositionTimeExample();
				putPositionTimeExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId());
				putPositionTimeMapper.deleteByExample(putPositionTimeExample);
			}
			List<Integer> orderPutIds = Lists.newArrayList();
			Date startDate;
			Date endDate;
			List<Map<String, Object>> positionRelations = JsonUtils.TO_OBJ(orderPutAll.getLimits(), List.class);
			if (positionRelations != null) {
				List<Integer> positions = Lists.newArrayList();
				for (Map<String, Object> oneRelationMap : positionRelations) {
					if (oneRelationMap.containsKey("limitArray")) {
						oneRelationMap.remove("limitArray");
					}
					Integer adPosition = (Integer) oneRelationMap.get("id");
					positions.add(adPosition);
					OrderPut orderPut = new OrderPut();
					BeanUtils.copyProperties(orderPutAll, orderPut);
					PutPositionTime putPositionTime = new PutPositionTime();
					putPositionTime.setPutAllId(orderPutAll.getId());
					putPositionTime.setPositionId(adPosition);
					putPositionTime.setCostType(orderPutAll.getCostType());
//					Map<String, Object> positionMap = (Map<String, Object>) positionRelations.get(adPositionStr);
					orderPut.setDeliveryMode((Integer) oneRelationMap.get("deliveryMode"));
					//给cpc和cpm的曝光设置赋默认值
					if (!oneRelationMap.containsKey("changePage")) {
						oneRelationMap.put("changePage", 0);
					}
					if (!oneRelationMap.containsKey("expDuration")) {
						oneRelationMap.put("expDuration", 0);
					}
					if (!oneRelationMap.containsKey("changeChapter")) {
						oneRelationMap.put("changeChapter", 0);
					}
					List<Map<String, Object>> limits = (List<Map<String, Object>>) oneRelationMap.get("limits");
					for (Map<String, Object> oneMap : limits) {
						if (oldOrderPutAll == null || !orderPutAll.getLimits().equals(oldOrderPutAll.getLimits())) {
							String dates = oneMap.get("date").toString();
							String times = oneMap.containsKey("times") ? oneMap.get("times").toString() : "";
							Integer limit = oneMap.containsKey("limit") ? (Integer) oneMap.get("limit") : null;
							oneMap.remove("delivery");
							List<String> dateList = StringUtils.str2List(dates, Constants.SIGN_MINUS);
							if (dateList != null && dateList.size() == 2) {
								startDate = DateUtils.parse(dateList.get(0), DateUtils.SHORT_FORMAT);
								endDate = DateUtils.parse(dateList.get(1), DateUtils.SHORT_FORMAT);
								logger.info("openLimits putId: {}, dates: {}, times: {}, limit: {}",
										orderPutAll.getId(), dates, times, limit);
								putPositionTime.setBeginTime(DateUtils.getFirstSecondOfDay(startDate));
								putPositionTime.setEndTime(DateUtils.getLastSecondOfDay(endDate));
								putPositionTime.setPutLimit(limit);
								putPositionTime.setTimeInterval(times);
								putPositionTimeMapper.insertSelective(putPositionTime);
							}
						}
					}
					orderPutAll.setLimits(JsonUtils.TO_JSON(positionRelations));
					// 默认设置价格为0
					orderPut.setPrice(0);
					orderPut.setId(null);
					orderPut.setPutType(Constants.PUT_TYPE_ORDER);
					orderPut.setPutAllId(orderPutAll.getId());
					orderPut.setPutName(StringUtils.concat(orderPut.getPutName(), Constants.SIGN_MINUS,
							putPositionTime.getPositionId()));
					orderPut.setLimits(JsonUtils.TO_JSON(limits));
					orderPut.setAdPosition(putPositionTime.getPositionId());
					orderPut.setExpDuration((Integer) oneRelationMap.get("expDuration"));
					orderPut.setChangePage((Integer) oneRelationMap.get("changePage"));
					orderPut.setChangeChapter((Integer) oneRelationMap.get("changeChapter"));
					if (opType.equals(Constants.OP_ADD)) {
						// 新建投放总数据则插入拆分的订单投放数据
						orderPutService.add(orderPut);
						orderPutIds.add(orderPut.getId());
					} else {
						// 修改投放总数据则更改拆分的订单投放数据
						OrderPutExample orderPutExample = new OrderPutExample();
						orderPutExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId())
								.andAdPositionEqualTo(putPositionTime.getPositionId());
						OrderPut updateOrderPut = orderPutMapper.selectOneByExample(orderPutExample);
						orderPut.setPutName(null);
						orderPut.setId(updateOrderPut.getId());
						orderPutService.update(orderPut, updateOrderPut);
					}
				}
				// 删除投放订单没关联的广告位信息
//				PutPositionTimeExample deletePositionTimeExample = new PutPositionTimeExample();
//				deletePositionTimeExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId())
//						.andPositionIdNotIn(positions).andTimeStateNotEqualTo(Constants.STATE_INVALID);
//				PutPositionTime deletePutPositionTime = new PutPositionTime();
//				deletePutPositionTime.setTimeState(Constants.STATE_INVALID);
//				putPositionTimeMapper.updateByExampleSelective(deletePutPositionTime, deletePositionTimeExample);
//				orderPutAll.setAdPosition(StringUtils.list2str4Int(positions, Constants.SIGN_COMMA));
			}
			if (opType.equals(Constants.OP_ADD)) {
				orderPutAll.setPids(StringUtils.list2str4Int(orderPutIds, Constants.SIGN_COMMA));
			}
			orderPutAllMapper.updateByPrimaryKeySelective(orderPutAll);
		}
	}

	@Override
	public List<Map<String, Object>> getAdpositionsByPut(Integer id) {
		List<Map<String, Object>> resultList = Lists.newArrayList();
		OrderPutAll orderPutAll = orderPutAllMapper.selectByPrimaryKey(id);
		List<Integer> adPositions = StringUtils.str2List4Int(orderPutAll.getAdPosition(), Constants.SIGN_COMMA);
		for (Integer adPositionId : adPositions) {
			Map<String, Object> positionMap = Maps.newHashMap();
			AdPosition adPosition = adPositionMapper.selectByPrimaryKey(adPositionId);
			EntityExample entityExample = new EntityExample();
			entityExample.createCriteria().andAdPositionEqualTo(adPositionId).andPutAllIdEqualTo(id);
			List<Entity> entities = entityMapper.selectByExample(entityExample);
			OrderPutExample orderPutExample = new OrderPutExample();
			orderPutExample.createCriteria().andPutAllIdEqualTo(id).andAdPositionEqualTo(adPositionId)
					.andPutStateNotEqualTo(Constants.STATE_INVALID);
			OrderPut orderPut = orderPutMapper.selectOneByExample(orderPutExample);
			positionMap.put("id", adPositionId);
			positionMap.put("name", adPosition.getName());
			positionMap.put("type", adPosition.getType());
			positionMap.put("num", entities.size());
			positionMap.put("state", orderPut.getPutState());
			resultList.add(positionMap);
		}
		return resultList;
	}

	@Override
	public void updateByRunState(OrderPutAll orderPutAll) {
		OrderPutExample orderPutExample = new OrderPutExample();
		orderPutExample.createCriteria().andPutAllIdEqualTo(orderPutAll.getId())
				.andPutStateEqualTo(Constants.STATE_VALID);
		List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
		for (OrderPut orderPut : orderPuts) {
			OrderPut newOrderPut = new OrderPut();
			newOrderPut.setId(orderPut.getId());
			newOrderPut.setCostType(orderPut.getCostType());
			newOrderPut.setRunState(orderPutAll.getRunState());
			orderPutService.update(newOrderPut, orderPut);
		}
		orderPutAllMapper.updateByPrimaryKeySelective(orderPutAll);
	}
}

package com.iwanvi.nvwa.web.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AdPositionTimeMapper;
import com.iwanvi.nvwa.dao.AdxRelationMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.DictionaryMapper;
import com.iwanvi.nvwa.dao.OrderPutAllMapper;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.OrdersMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionTime;
import com.iwanvi.nvwa.dao.model.AdPositionTimeExample;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.CompanyExample;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutAll;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.OrderPutExample.Criteria;
import com.iwanvi.nvwa.dao.model.OrdersExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.EntityService;
import com.iwanvi.nvwa.web.service.OrderPutService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.service.SyslogService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.iwanvi.nvwa.web.vo.OrderPutQuery;

@Service
public class OrderPutServiceImpl implements OrderPutService {

	private static Logger logger = LoggerFactory.getLogger(OrderPutServiceImpl.class);

	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderPutMapper orderPutMapper;
	@Autowired
	private EntityService entityService;
	@Autowired
	private SysCrontabService sysCrontabService;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SyslogService syslogService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private AdxRelationMapper adxRelationMapper;
	@Autowired
	private OrderPutAllMapper orderPutAllMapper;
	@Autowired
	private AdPositionMapper adPositionMapper;
	@Autowired
	private AppsMapper appsMapper;
	@Autowired
	private AdPositionTimeMapper adPositionTimeMapper;

	@Override
	public Page<OrderPut> selectPage(OrderPutQuery orderPutQuery, List<Integer> createUsers) {
		Page<OrderPut> page = null;
		int count = 0;
		OrderPutExample orderPutExample = null;
		orderPutExample = orderPutQuery.toExample(createUsers);
		count = new Long(orderPutMapper.countByExample(orderPutExample)).intValue();
		if (count > 0) {
			page = new Page<>(count, orderPutQuery.getCp(), orderPutQuery.getPs());
			orderPutExample.setOrderByClause("run_state desc,id desc");
			orderPutExample.setOffset(page.getStartPosition());
			orderPutExample.setRows(page.getPageSize());
			List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
			fillOrderPut(orderPuts);
			page.setData(orderPuts);
		} else {
			page = new Page<>(count);
		}
		return page;
	}

	private void fillOrderPut(List<OrderPut> orderPuts) {
		for (OrderPut oneOrderPut : orderPuts) {
			AdPosition adPosition = adPositionMapper.selectByPrimaryKey(oneOrderPut.getAdPosition());
			oneOrderPut.setAdPositionName(adPosition.getName());
			Apps apps = appsMapper.selectByPrimaryKey(Integer.valueOf(oneOrderPut.getDxApp()));
			oneOrderPut.setAppName(apps.getAppName());
		}
	}

	@Override
	public List<OrderPut> selectList(OrderPutQuery orderPutInfo, List<Integer> createUsers) {
		OrderPutExample orderPutExample = orderPutInfo.toExample(createUsers);
		return orderPutMapper.selectByExample(orderPutExample);
	}

	@Override
	public void update(OrderPut orderPut, OrderPut oldOrderPut) {
		if (!Constants.STATE_INVALID.equals(orderPut.getPutState())) {
			if (StringUtils.isNotBlank(orderPut.getLimits())) {
				openLimits(orderPut);
			}
			checkPutState(orderPut, null);
			boolean needAdxAudit = false;
			Integer entityState = null;
			if (StringUtils.isNotBlank(orderPut.getLandUrl())
					&& !orderPut.getLandUrl().equals(oldOrderPut.getLandUrl())) {
				needAdxAudit = true;
				entityState = Constants.STATE_WAIT_AUDIT;
			}
			if (StringUtils.isNotBlank(orderPut.getDxMedia())
					&& !orderPut.getDxMedia().equals(oldOrderPut.getDxMedia())) {
				needAdxAudit = true;
				entityState = Constants.STATE_WAIT_UPDATE;
			}
			if (orderPut.getAdPosition() != null && !orderPut.getAdPosition().equals(oldOrderPut.getAdPosition())) {
				needAdxAudit = true;
				entityState = Constants.STATE_WAIT_UPDATE;
			}
			// 若 落地页、媒体、广告位发生变化则级联重置相关创意的审核(改为未审核)
			if (needAdxAudit) {
				Entity entity = new Entity();
				entity.setEntityState(entityState);
				entityService.updateEntityByPid(entity, oldOrderPut.getId());
			}
		}
		if (Constants.STATE_INVALID.equals(orderPut.getPutState())) {
			Entity entity = new Entity();
			entity.setEntityState(Constants.STATE_INVALID);
			entityService.updateEntityByPid(entity, orderPut.getId());
		}
		if (!Constants.COST_TYPE_CPT.equals(orderPut.getCostType())) {
			orderPut.setPrice(0);
		}
		orderPut.setUpdateTime(new Date());
		orderPutMapper.updateByPrimaryKeySelective(orderPut);
		if ((orderPut.getPutLimit() != null && !orderPut.getPutLimit().equals(oldOrderPut.getPutLimit()))
				|| (orderPut.getCostType() != null && !orderPut.getCostType().equals(oldOrderPut.getCostType()))) {
			// 重置限额
			try {
				if (!resetPutLimit(orderPut)) {
					logger.error("add put error, reset limit error! id: {}", orderPut.getId());
					throw new ServiceException("add put error, reset limit faild");
				}
			} catch (Exception e) {
				logger.error("add put error, reset limit error! id: {}", orderPut.getId());
				throw new ServiceException("add put error, reset limit error");
			}
		}
		// 同步
		sysCrontabService.addSysCrontabCheckCount(orderPut.getId(), Constants.OBJ_ORDER_PUT, Constants.OP_UPDATE);
		syslogService.addSyslog(0, 0, orderPut, oldOrderPut, this.getClass().getName(), "update");
	}

	@Override
	public OrderPut info(Integer orderPutId) {
		return orderPutMapper.selectByPrimaryKey(orderPutId);
	}

	private boolean resetPutLimit(OrderPut orderPut) {
		try {
			if (Constants.COST_TYPE_ORDER_CPC.equals(orderPut.getCostType())
					|| Constants.COST_TYPE_ORDER_CPM.equals(orderPut.getCostType())) {
				String key = StringUtils.buildString(WebConstants.KEY_REDIS_KPI_LIMIT_ORDER_PUT_PV, orderPut.getId());
				if (Constants.COST_TYPE_ORDER_CPC.equals(orderPut.getCostType())) {
//					key = StringUtils.buildString(WebConstants.KEY_REDIS_KPI_LIMIT_ORDER_PUT_CLK, orderPut.getId());
					AdPosition adPosition = adPositionMapper.selectByPrimaryKey(orderPut.getAdPosition());
					BigDecimal ctr = new BigDecimal(Double.toString(adPosition.getForecastCtr()));
					redisDao.set(key,
							new BigDecimal(orderPut.getPutLimit()).divide(ctr, 3, BigDecimal.ROUND_CEILING).longValue()
									+ "");
				} else if (Constants.COST_TYPE_ORDER_CPM.equals(orderPut.getCostType())) {
					redisDao.set(key, orderPut.getPutLimit() * 1000 + "");
				}
				logger.info("orderPut resetPutLimit success id {}, limit {}", orderPut.getId(), orderPut.getPutLimit());
			}
			return true;
		} catch (Exception e) {
			logger.error("orderPut resetPutLimit error id {}, limit {}, e {}", orderPut.getId(), orderPut.getPutLimit(),
					e);
			return false;
		}
	}

	@Override
	public void checkPutState(OrderPut orderPut, OrderPut oldOrdePut) {
		if (orderPut.getBeginTime() != null && orderPut.getEndTime() != null) {
			if (orderPut.getBeginTime().compareTo(new Date()) > 0) {
				orderPut.setPutState(Constants.STATE_UNSTART);
			} else if (orderPut.getEndTime().compareTo(new Date()) < 0) {
				orderPut.setPutState(Constants.STATE_EXPIRED);
			} else if (oldOrdePut != null && Constants.STATE_BALANCE_INVALID.equals(oldOrdePut.getPutState())) {
				orderPut.setPutState(oldOrdePut.getPutState());
			} else if (oldOrdePut != null && Constants.STATE_UNIT_LIMIT.equals(oldOrdePut.getPutState())) {
				if (orderPut.getPutLimit() != null && oldOrdePut.getPutLimit() != null
						&& orderPut.getPutLimit() > oldOrdePut.getPutLimit()) {
					orderPut.setPutState(Constants.STATE_VALID);
				} else {
					orderPut.setPutState(oldOrdePut.getPutState());
				}
			} else {
				orderPut.setPutState(Constants.STATE_VALID);
			}
		}
	}

	@Transactional
	@Override
	public void updateOrderPutByOid(OrderPut orderPut, Integer oid) {
		if (orderPut != null && oid != null) {
			OrderPutExample orderPutExample = new OrderPutExample();
			orderPutExample.createCriteria().andOidEqualTo(oid).andPutStateNotEqualTo(Constants.STATE_INVALID);
			if (Constants.STATE_INVALID.equals(orderPut.getPutState())) {
				updateEntityByOrderPut(orderPutExample);
				OrderPut oneOrderPut = orderPutMapper.selectOneByExample(orderPutExample);
				OrderPutAll orderPutAll = new OrderPutAll();
				orderPutAll.setId(oneOrderPut.getPutAllId());
				orderPutAll.setPutState(Constants.STATE_INVALID);
				orderPutAllMapper.updateByPrimaryKeySelective(orderPutAll);
				AdPositionTimeExample adPositionTimeExample = new AdPositionTimeExample();
				adPositionTimeExample.createCriteria().andPutAllIdEqualTo(oneOrderPut.getPutAllId())
						.andStateEqualTo(Constants.STATE_VALID);
				AdPositionTime adPositionTime = new AdPositionTime();
				adPositionTime.setState(Constants.STATE_INVALID);
				adPositionTimeMapper.updateByExampleSelective(adPositionTime, adPositionTimeExample);
			}
			orderPutMapper.updateByExampleSelective(orderPut, orderPutExample);
		}
	}

	private void updateEntityByOrderPut(OrderPutExample orderPutExample) {
		List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
		Entity entity = new Entity();
		entity.setEntityState(Constants.STATE_INVALID);
		entity.setUpdateTime(new Date());
		for (OrderPut orderPut : orderPuts) {
			entityService.updateEntityByPid(entity, orderPut.getId());
		}
	}

	@Override
	@Transactional
	public void update(OrderPut orderPut) {
		if (orderPut.getId() != null) {
			orderPut.setUpdateTime(new Date());
			orderPutMapper.updateByPrimaryKeySelective(orderPut);
			sysCrontabService.addSysCrontabCheckCount(orderPut.getId(), Constants.OBJ_ORDER_PUT, Constants.OP_UPDATE);
		}
	}

	@Override
	public void updateByOrderPutExample(OrderPut orderPut, OrderPutExample orderPutExample) {
		orderPutMapper.updateByExampleSelective(orderPut, orderPutExample);
		updateEntityByOrderPut(orderPutExample);
	}

	@Override
	@Transactional
	public void add(OrderPut orderPut) {
		orderPut.setCreateTime(new Date());
		orderPut.setPutState(Constants.STATE_VALID);
		orderPut.setRunState(Constants.STATE_VALID);
		if (StringUtils.isNotBlank(orderPut.getLimits())) {
			openLimits(orderPut);
		}
		checkPutState(orderPut, null);
		// 若是抄底投放则初始化必要关联数据
		if (Constants.PUT_TYPE_BOTTOM.equals(orderPut.getPutType())) {
			orderPut.setPutLimit(Integer.MAX_VALUE);
			orderPut.setBeginTime(new Date());
			String dateStr = "28881212";
			orderPut.setEndTime(DateUtils.parse(dateStr, DateUtils.SHORT_FORMAT));
			OrdersExample ordersExample = new OrdersExample();
			ordersExample.createCriteria().andCreateTypeEqualTo(WebConstants.PLAN_CREATE_AUTO)
					.andOrdersStateNotEqualTo(Constants.STATE_INVALID);
			List<Orders> ordersList = ordersMapper.selectByExample(ordersExample);
			if (ordersList != null && ordersList.size() > 0) {
				orderPut.setOid(ordersList.get(0).getId());
			} else {
				// 初始化计划
				Orders orders = new Orders();
				orders.setName("IWanVi抄底初始化订单(勿操作)");
				orders.setCreateTime(new Date());
				orders.setCreateType(WebConstants.PLAN_CREATE_AUTO);
				orders.setRunState(Constants.STATE_VALID);
				orders.setOrdersState(Constants.STATE_VALID);
				CompanyExample companyExample = new CompanyExample();
				companyExample.createCriteria().andUuidEqualTo(WebConstants.KA_ADVER_UUID)
						.andStatusEqualTo(Constants.STATE_VALID).andTypeEqualTo(WebConstants.COMPANY_TYPE_ADVER)
						.andAgTypeEqualTo(Constants.INTEGER_1);
				List<Company> companies = companyMapper.selectByExample(companyExample);
				if (companies != null && companies.size() > 0) {
					orders.setCustId(companies.get(0).getId());
				} else {
					// 初始化抄底广告主
					Company company = new Company();
					company.setFullName("IWanVi抄底初始化广告主(勿操作)");
					company.setUuid(WebConstants.KA_ADVER_UUID);
					company.setType(WebConstants.COMPANY_TYPE_ADVER);
					company.setStatus(Constants.STATE_VALID);
					company.setCreateUser(Constants.INTEGER_1);
					company.setAid(WebConstants.KA_AGENT_ID);
					company.setBalanceStatus(Constants.STATE_VALID);
					company.setAuditStatus(Constants.STATE_VALID);
					companyMapper.insertSelective(company);
					companyMapper.countByExample(null);
					orders.setCustId(company.getId());
					// 插入一条免审adxRelation数据
					AdxRelation adxRelation = new AdxRelation();
					adxRelation.setAdxType(Constants.FLOW_MEDIA_AIKA);
					adxRelation.setObjId(company.getId());
					adxRelation.setAuditState(Constants.STATE_VALID);
					adxRelation.setCreateTime(new Date());
					adxRelation.setObjType(Constants.OBJ_ADVERTISER);
					adxRelation.setStatus(Constants.STATE_VALID);
					adxRelationMapper.insertSelective(adxRelation);
				}
				ordersMapper.insertSelective(orders);
				ordersMapper.countByExample(null);
				orderPut.setOid(orders.getId());
			}
		}
		orderPutMapper.insertSelective(orderPut);
		orderPutMapper.countByExample(null);
		if (orderPut.getPutLimit() != null) {
			// 重置限额
			try {
				if (!resetPutLimit(orderPut)) {
					// 限额失败标记限额失败定时重置
					logger.error("add put error, reset limit error! id: {}", orderPut.getId());
					throw new ServiceException("add put error, reset limit faild");
				}
			} catch (Exception e) {
				logger.error("add put error, reset limit error! id: {}", orderPut.getId());
				throw new ServiceException("add put error, reset limit error");
			}
		}
	}

	@Override
	public boolean checkNameIsExistByOrder(OrderPut orderPut) {
		OrderPutExample orderPutExample = new OrderPutExample();
		Criteria criteria = orderPutExample.createCriteria();
		criteria.andPutNameEqualTo(orderPut.getPutName()).andPutStateNotEqualTo(Constants.STATE_INVALID);
		if (orderPut.getOid() != null) {
			criteria.andOidEqualTo(orderPut.getOid());
		}
		if (orderPut.getPutType() != null) {
			criteria.andPutTypeEqualTo(orderPut.getPutType());
		}
		List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
		if (orderPuts.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(OrderPut orderPut) {
		if (orderPut.getId() != null) {
			orderPutMapper.deleteByPrimaryKey(orderPut.getId());
		}
	}

	private void openLimits(OrderPut orderPut) {
		if (StringUtils.isNotBlank(orderPut.getLimits())) {
			Date nowDate = DateUtils.getFirstSecondOfDay(new Date());
			Date startDate;
			Date endDate;
			List<Map<String, Object>> limits = JsonUtils.TO_OBJ(orderPut.getLimits(), List.class);
			if (limits != null) {
				boolean isNow = false;
				for (Map<String, Object> map : limits) {
					String dates = map.get("date").toString();
					String times = map.containsKey("times") ? map.get("times").toString() : "";
					Integer limit = map.containsKey("limit") ? (Integer) map.get("limit") : Integer.MAX_VALUE;
					List<String> dateList = StringUtils.str2List(dates, Constants.SIGN_MINUS);
					if (dateList != null && dateList.size() == 2) {
						startDate = DateUtils.parse(dateList.get(0), DateUtils.SHORT_FORMAT);
						endDate = DateUtils.parse(dateList.get(1), DateUtils.SHORT_FORMAT);
						// 选取符合当前日期的时间段作为投放的投放时间
						if (nowDate.compareTo(startDate) >= 0 && nowDate.compareTo(endDate) <= 0) {
							logger.info("openLimits putId: {}, dates: {}, times: {}, limit: {}", orderPut.getId(),
									dates, times, limit);
							orderPut.setBeginTime(DateUtils.getFirstSecondOfDay(startDate));
							orderPut.setEndTime(DateUtils.getLastSecondOfDay(endDate));
							orderPut.setPutLimit(limit);
							orderPut.setTimeInterval(times);
							isNow = true;
						}
					}
				}
				// 若无符合的时间段则默认选取第一个时间段作为投放的投放时间
				if (!isNow) {
					Map<String, Object> map = limits.get(0);
					String dates = map.get("date").toString();
					String times = map.containsKey("times") ? map.get("times").toString() : "";
					Integer limit = map.containsKey("limit") ? (Integer) map.get("limit") : Integer.MAX_VALUE;
					List<String> dateList = StringUtils.str2List(dates, Constants.SIGN_MINUS);
					startDate = DateUtils.parse(dateList.get(0), DateUtils.SHORT_FORMAT);
					endDate = DateUtils.parse(dateList.get(1), DateUtils.SHORT_FORMAT);
					logger.info("openLimits default putId: {}, dates: {}, times: {}, limit: {}", orderPut.getId(),
							dates, times);
					orderPut.setBeginTime(DateUtils.getFirstSecondOfDay(startDate));
					orderPut.setEndTime(DateUtils.getLastSecondOfDay(endDate));
					orderPut.setPutLimit(limit);
					orderPut.setTimeInterval(times);
				}
			}
		}
	}
}

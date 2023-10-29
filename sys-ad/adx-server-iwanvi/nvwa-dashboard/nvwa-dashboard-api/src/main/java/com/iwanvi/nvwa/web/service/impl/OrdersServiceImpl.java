package com.iwanvi.nvwa.web.service.impl;

import java.rmi.ServerException;
import java.util.Date;
import java.util.List;

import com.iwanvi.nvwa.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.OrdersMapper;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.OrdersExample;
import com.iwanvi.nvwa.dao.model.OrdersExample.Criteria;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.OrderPutService;
import com.iwanvi.nvwa.web.service.OrdersService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.vo.OrdersQuery;

@Service
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private SysCrontabService sysCrontabService;
	@Autowired
	private OrderPutService orderPutService;
	@Autowired
	private OrderPutMapper orderPutMapper;
	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public Page<Orders> listForPage(OrdersQuery ordersQuery, List<Integer> advers) {
		Page<Orders> page;
		OrdersExample ordersExample = ordersQuery.toExample(advers);
		Long count = ordersMapper.countByExample(ordersExample);
		if (count > 0) {
			page = new Page<>(count.intValue(), ordersQuery.getCp(), ordersQuery.getPs());
			ordersExample.setOrderByClause("run_state desc,id desc");
			ordersExample.setOffset(page.getStartPosition());
			ordersExample.setRows(page.getPageSize());
			List<Orders> orders = ordersMapper.selectByExample(ordersExample);
			fillOrders(orders);
			// for (Orders oneOrder : orderss) {
			// int putCount = putService.getPutCountByOidOrPid(null,
			// oneOrder.getId());
			// oneOrder.setPutNumbers(putCount);
			// }
			page.setData(orders);
		} else {
			page = new Page<>(0);
		}
		return page;
	}

	private void fillOrders(List<Orders> orders) {
		for (Orders oneOrder : orders) {
			Company company = companyMapper.selectByPrimaryKey(oneOrder.getCustId());
			oneOrder.setCustName(company.getFullName());
		}
		
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		Orders orders = new Orders();
		orders.setId(id);
		orders.setOrdersState(Constants.STATE_INVALID);
		update(orders);
	}

	@Override
	@Transactional
	public void update(Orders orders) {
		Orders oldOrders = ordersMapper.selectByPrimaryKey(orders.getId());
		orders.setUpdateTime(new Date());
		boolean isSync = false;
		// 若订单状态为无效则级联修改订单投放的状态
		if (Constants.STATE_INVALID.equals(orders.getOrdersState())) {
			OrderPut orderput = new OrderPut();
			orderput.setPutState(Constants.STATE_INVALID);
			orderPutService.updateOrderPutByOid(orderput, orders.getId());
		}
		if (orders.getRunState() != null && !orders.getRunState().equals(oldOrders.getRunState())) {
			isSync = true;
		}
		if (orders.getOrdersState() != null && !orders.getOrdersState().equals(oldOrders.getOrdersState())) {
			isSync = true;
		}
		if (isSync) {
			sysCrontabService.addSysCrontabCheckCount(orders.getId(), Constants.OBJ_ORDER, Constants.OP_UPDATE);
		}
		ordersMapper.updateByPrimaryKeySelective(orders);
	}

	@Override
	@Transactional
	public void add(Orders orders) {
		// checkOrdersState(orders, null);
		if (orders.getCustId() == null) {
			throw new ServiceException("缺少客户或客户名不正确");
		}
		orders.setCreateTime(new Date());
		orders.setRunState(Constants.STATE_VALID);
		orders.setOrdersState(Constants.STATE_VALID);
		ordersMapper.insertSelective(orders);
	}

	@Override
	public Orders info(Integer id) {
		return ordersMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Orders> list(OrdersQuery ordersQuery, List<Integer> adverIds) {
		// if (Constants.INTEGER_1.equals(uids.size())) {
		// orders.setCreateUser(uids.get(0));
		// }
		OrdersExample ordersExample = ordersQuery.toExample(adverIds);
		if (StringUtils.isNotBlank(ordersQuery.getName())) {
			ordersExample.setOffset(0);
			ordersExample.setRows(20);
		}
		ordersExample.setOrderByClause("run_state desc,id desc");
		return ordersMapper.selectByExample(ordersExample);
	}

	private OrdersExample packageExample(Orders orders, List<Integer> advers) {
		OrdersExample ordersExample = new OrdersExample();
		if (orders != null) {
			Criteria criteria = ordersExample.createCriteria();
			if (StringUtils.isNotBlank(orders.getName())) {
				criteria.andNameLike(
						StringUtils.concat(Constants.SIGN_PERCENT, orders.getName(), Constants.SIGN_PERCENT));
			}
			if (orders.getCustId() != null) {
				criteria.andCustIdEqualTo(orders.getCustId());
			} else if (advers != null && advers.size() > 0) {
				criteria.andCustIdIn(advers);
			}
			if (orders.getRunState() != null) {
				criteria.andRunStateEqualTo(orders.getRunState());
			}
			if (orders.getOrdersState() != null) {
				criteria.andOrdersStateEqualTo(orders.getOrdersState());
			} else {
				criteria.andOrdersStateNotEqualTo(Constants.STATE_INVALID);
			}
			if (orders.getCreateUser() != null) {
				criteria.andCreateUserEqualTo(orders.getCreateUser());
			}
			// else if (uids != null && uids.size() > 0) {
			// criteria.andCreateUserIn(uids);
			// }
		}
		return ordersExample;
	}

	@Override
	public boolean ordersNameIsExist(String ordersName) {
		OrdersExample ordersExample = new OrdersExample();
		ordersExample.createCriteria().andNameEqualTo(ordersName).andOrdersStateNotEqualTo(Constants.STATE_INVALID);
		long count = ordersMapper.countByExample(ordersExample);
		return count > 0;
	}

	@Override
	public boolean checkOrdersIsTrue(Orders orders) {
		boolean isSyncOrder = false;
		if (orders.getRunState() == null) {
			isSyncOrder = true;
		}
		if (!isSyncOrder && orders.getOrdersState() == null) {
			isSyncOrder = true;
		}
		if (!isSyncOrder && orders.getCustId() == null) {
			isSyncOrder = true;
		}
		return isSyncOrder;
	}

	public Integer getCustIdByPutId(Integer putId) {
		OrderPut orderPut = orderPutMapper.selectByPrimaryKey(putId);
		Orders orders = ordersMapper.selectByPrimaryKey(orderPut.getOid());
		return orders.getCustId();
	}
	// private void checkOrdersState(Orders orders, Orders oldOrders) {
	// if (orders.getPutStartTime() != null && orders.getPutEndTime() != null) {
	// if (oldOrders != null &&
	// Constants.STATE_BALANCE_INVALID.equals(oldOrders.getOrdersState())) {
	// orders.setOrdersState(Constants.STATE_UNSTART);
	// } else if (new Date().compareTo(orders.getPutStartTime()) <= 0) {
	// orders.setOrdersState(Constants.STATE_UNSTART);
	// } else if (new Date().compareTo(orders.getPutEndTime()) >= 0) {
	// orders.setOrdersState(Constants.STATE_EXPIRED);
	// } else {
	// orders.setOrdersState(Constants.STATE_VALID);
	// }
	// }
	// }

}

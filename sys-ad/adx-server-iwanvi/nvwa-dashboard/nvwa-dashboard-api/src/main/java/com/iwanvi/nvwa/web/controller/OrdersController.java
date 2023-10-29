package com.iwanvi.nvwa.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.CompanyService;
import com.iwanvi.nvwa.web.service.OrdersService;
import com.iwanvi.nvwa.web.service.UserService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.OrdersQuery;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "订单接口")
@RequestMapping(value = "orders")
public class OrdersController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(OrdersController.class);

	@Autowired
	private OrdersService ordersService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private UserService userService;

	@ApiOperation("订单分页列表")
	@NvwaRespBody
	@PostMapping("page")
	public Page<Orders> page(HttpServletRequest request ,@RequestBody OrdersQuery ordersQuery) {
		Integer type = getUserType(request);
		Integer uid = getUserId(request);
		List<Integer> advers = Lists.newArrayList();
		if (Constants.USER_TYPE_CUST.equals(type)) {
			Integer adverId = userService.get(uid).getCompany();
			advers.add(adverId);
		} else if (!Constants.USER_TYPE_ADMIN.equals(type)) {
			advers = companyService.getCompanyIdsByWithUid(uid);
			if (advers != null && advers.size() == 0) {
				throw new ServiceException("此用户无关联广告主");
			}
		}
		Page<Orders> page = ordersService.listForPage(ordersQuery, advers);
		return page;
	}

	@ApiOperation("订单列表")
	@NvwaRespBody
	@PostMapping("list")
	public List<Orders> list(HttpServletRequest request,@RequestBody OrdersQuery ordersQuery) {
		Integer uid = getUserId(request);
		Integer type = getUserType(request);
		if (uid == null || type == null) {
			throw new ServiceException("没有相关登录人或许登录超时");
		} else {
			List<Integer> advers = Lists.newArrayList();
			if (Constants.USER_TYPE_CUST.equals(type)) {
				Integer adverId = userService.get(uid).getCompany();
				advers.add(adverId);
			} else if (!Constants.USER_TYPE_ADMIN.equals(type)) {
				advers = companyService.getCompanyIdsByWithUid(uid);
				if (advers != null && advers.size() == 0) {
					throw new ServiceException("此用户无关联广告主");
				}
			}
			return ordersService.list(ordersQuery, advers);
		}
	}

	@ApiOperation("修改订单")
	@NvwaRespBody
	@PostMapping("update")
	public void update(HttpServletRequest request, @RequestBody Orders orders) {
		if (orders.getId() == null) {
			throw new ServiceException("订单id不得为空");
		}
		Orders oldOrders = ordersService.info(orders.getId());
		String ordersName = orders.getName();
		if (StringUtils.isNotBlank(ordersName) && !ordersName.equals(oldOrders.getName())) {
			// 判断是否重名
			boolean isExist = ordersService.ordersNameIsExist(ordersName);
			if (isExist) {
				// 重名则返回
				throw new ServiceException("订单名称已存在");
			}
		}
		Integer uid = getUserId(request);
		orders.setCustId(null);
		orders.setUpdateUser(uid);
		ordersService.update(orders);
	}

	@ApiOperation("新增订单")
	@NvwaRespBody
	@PostMapping("add")
	public void add(HttpServletRequest request, @RequestBody Orders orders) {
		if (StringUtils.isBlank(orders.getName())) {
			throw new ServiceException("订单名称不能为空");
		}
		boolean isExist = ordersService.ordersNameIsExist(orders.getName());
		if (isExist) {
			// 重名则返回
			logger.error("orders update error beacuse name is exist name: {}", orders.getName());
			throw new ServiceException("订单名称已存在");
		}
		Integer uid = getUserId(request);
		orders.setCreateUser(uid);
		ordersService.add(orders);
	}

	@ApiOperation("删除订单")
	@NvwaRespBody
	@PostMapping("delete/{id}")
	public void delete(@PathVariable Integer id) {
		if (id == null) {
			throw new ServiceException("订单id不得为空");
		}
		ordersService.delete(id);
	}

	@ApiOperation("订单详情")
	@NvwaRespBody
	@GetMapping("info/{id}")
	public Orders info(@PathVariable Integer id) {
		if (id == null) {
			throw new ServiceException("订单id不得为空");
		}
		Orders orders = ordersService.info(id);
		return orders;
	}

}

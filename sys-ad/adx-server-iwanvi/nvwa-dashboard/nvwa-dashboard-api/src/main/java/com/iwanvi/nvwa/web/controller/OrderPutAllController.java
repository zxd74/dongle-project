package com.iwanvi.nvwa.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.OrderPutAll;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AreaService;
import com.iwanvi.nvwa.web.service.CompanyService;
import com.iwanvi.nvwa.web.service.OrderPutAllService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.OrderPutAllQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "订单投放接口")
@RequestMapping(value = "orderPut")
public class OrderPutAllController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(OrderPutAllController.class);

	@Autowired
	private OrderPutAllService orderPutAllService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AreaService areaService;

	@ApiOperation("获取订单投放分页接口")
	@NvwaRespBody
	@PostMapping("pages")
	public Page<OrderPutAll> page(HttpServletRequest request, @RequestBody OrderPutAllQuery orderPutAllQuery) {
		Integer type = getUserType(request);
		Integer uid = getUserId(request);
		Integer adverId = null;
		List<Integer> advers = null;
		if (orderPutAllQuery.getCp() == null || orderPutAllQuery.getPs() == null) {
			throw new ControllerException("缺少分页参数");
		}
		if (Constants.USER_TYPE_CUST.equals(type)) {
			adverId = companyService.getAiKaCompanyIdByWithUid(uid);
		} else if (!Constants.USER_TYPE_ADMIN.equals(type)) {
			advers = companyService.getCompanyIdsByWithUid(uid);
			if (advers != null && advers.size() == 0) {
				throw new ServiceException("此用户无关联广告主");
			}
		}
		Page<OrderPutAll> page = orderPutAllService.selectPage(orderPutAllQuery, adverId, advers);
		logger.info("orderPut pages success");
		return page;
	}

	@ApiOperation("获取订单投放列表接口")
	@NvwaRespBody
	@PostMapping("list")
	public List<OrderPutAll> list(HttpServletRequest request, @RequestBody OrderPutAllQuery orderPutAllQuery) {
		Integer type = getUserType(request);
		Integer uid = getUserId(request);
		Integer adverId = null;
		List<Integer> advers = null;
		if (Constants.USER_TYPE_CUST.equals(type)) {
			adverId = companyService.getAiKaCompanyIdByWithUid(uid);
		} else if (!Constants.USER_TYPE_ADMIN.equals(type)) {
			advers = companyService.getCompanyIdsByWithUid(uid);
			if (advers != null && advers.size() == 0) {
				throw new ServiceException("此用户无关联广告主");
			}
		}
		List<OrderPutAll> orderPuts = orderPutAllService.selectList(orderPutAllQuery, adverId, advers);
		logger.info("orderPut list success");
		return orderPuts;
	}

	@NvwaRespBody
	@ApiOperation("修改订单投放总数据")
	@PostMapping("update")
	public void update(HttpServletRequest request) throws IOException {
		Map<String, Object> paramMap = getParamMap(request);
		OrderPutAll orderPutAll = JsonUtils.TO_OBJ(paramMap, OrderPutAll.class);
		if (orderPutAll.getId() == null) {
			throw new ServiceException("订单投放的id不得为空");
		}
		// 以下属性不可修改
		orderPutAll.setDxApp(null);
		orderPutAll.setDxZdlx(null);
		orderPutAll.setAdPosition(null);
		orderPutAll.setOid(null);
		OrderPutAll oldOrderPutAll = orderPutAllService.info(orderPutAll.getId());
		String putName = oldOrderPutAll.getPutName();
		if (orderPutAll.getOid() == null) {
			orderPutAll.setOid(oldOrderPutAll.getOid());
		}
		if (StringUtils.isNotBlank(putName) && !putName.equals(oldOrderPutAll.getPutName())) {
			boolean isExist = orderPutAllService.checkNameIsExistByOrder(orderPutAll);
			if (isExist) {
				throw new ServiceException("此订单下的订单投放名称已存在");
			}
		}
		if (orderPutAll.getDqRule() != null && orderPutAll.getPreDq() != null) {
			changeDxDq(orderPutAll);
		}
		orderPutAllService.update(orderPutAll);
		logger.info("orderPut update success");
	}
	
	@NvwaRespBody
	@ApiOperation("修改订单投放运行状态")
	@PostMapping("updateRunState")
	public void update(@RequestBody OrderPutAll orderPutAll) throws IOException {
		if (orderPutAll.getId() == null || orderPutAll.getRunState() == null) {
			throw new ServiceException("订单投放的id或运行状态不得为空");
		}
		orderPutAllService.updateByRunState(orderPutAll);
		logger.info("orderPut update success");
	}

	@NvwaRespBody
	@ApiOperation("删除订单投放总数据")
	@PostMapping("delete/{id}")
	public void delete(@PathVariable Integer id) throws IOException {
		if (id == null) {
			throw new ServiceException("订单投放的id不得为空");
		}
		OrderPutAll orderPutAll = new OrderPutAll();
		OrderPutAll oldOrderPutAll = orderPutAllService.info(id);
		orderPutAll.setId(id);
		orderPutAll.setPutState(Constants.STATE_INVALID);
		orderPutAll.setCostType(oldOrderPutAll.getCostType());
		orderPutAllService.update(orderPutAll);
		logger.info("orderPut update success");
	}

	/**
	 * 获取订单投放详情信息
	 * 
	 * @param id
	 * @return
	 */
	@NvwaRespBody
	@ApiOperation("获取订单投放详情")
	@GetMapping("info/{id}")
	public OrderPutAll info(@PathVariable Integer id) {
		if (id == null) {
			throw new ServiceException("id不得为空");
		}
		OrderPutAll orderPutAll = orderPutAllService.info(id);
		logger.info("orderPut info success");
		return orderPutAll;
	}

	@NvwaRespBody
	@ApiOperation("新增订单投放")
	@PostMapping("add")
	public void add(HttpServletRequest request) throws IOException {
		Map<String, Object> paramMap = getParamMap(request);
		OrderPutAll orderPutAll = JsonUtils.TO_OBJ(paramMap, OrderPutAll.class);
		if (StringUtils.isBlank(orderPutAll.getPutName()) || orderPutAll.getCostType() == null
				|| orderPutAll.getDxMedia() == null && orderPutAll.getOid() == null) {
			throw new ServiceException("缺少必要条件");
		}
		boolean isExist = orderPutAllService.checkNameIsExistByOrder(orderPutAll);
		if (isExist) {
			throw new ServiceException("此订单下的订单投放名称已存在");
		}
		Integer loginId = getUserId(request);
		// 先将创建人信息放入flag字段中 后期会再修改
		orderPutAll.setCreateUser(loginId);
		if (orderPutAll.getDqRule() != null && orderPutAll.getPreDq() != null) {
			changeDxDq(orderPutAll);
		}
		if (StringUtils.isNotBlank(orderPutAll.getAdPosition())) {
			orderPutAll.setAdPosition(
					StringUtils.concat(Constants.SIGN_COMMA, orderPutAll.getAdPosition(), Constants.SIGN_COMMA));
		}
		orderPutAll.setDxZdlx(Constants.TERMINAL_TYPE_APP.toString());
		orderPutAllService.add(orderPutAll);
		logger.info("orderPut a success");
	}

	@ApiOperation("获取订单广告位信息")
	@NvwaRespBody
	@GetMapping("positions/{id}")
	public List<Map<String, Object>> getAdpositionsByPut(@PathVariable Integer id) {
		if (id == null) {
			throw new ControllerException("缺少订单投放id");
		}
		return orderPutAllService.getAdpositionsByPut(id);
	}

	private void changeDxDq(OrderPutAll orderPutAll) throws UnsupportedEncodingException {
		if (Constants.AREA_RULE_INCLUDE.equals(orderPutAll.getDqRule())) {
			orderPutAll.setDxDq(orderPutAll.getPreDq());
		} else {
			List<Integer> allAreaId = areaService.getAllAreaCityId();
			String excludeIdStr = orderPutAll.getPreDq();
			List<Integer> excludeId = StringUtils.str2List4Int(excludeIdStr, Constants.SIGN_COMMA);
			allAreaId.removeAll(excludeId);
			orderPutAll.setDxDq(StringUtils.list2str4Int(allAreaId, Constants.SIGN_COMMA));
		}

	}

}

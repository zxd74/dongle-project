package com.iwanvi.nvwa.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AreaService;
import com.iwanvi.nvwa.web.service.OrderPutService;
import com.iwanvi.nvwa.web.service.SyslogService;
import com.iwanvi.nvwa.web.service.UserService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.OrderPutQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "抄底投放接口")
@RequestMapping(value = "put")
public class PutController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(PutController.class);

	@Autowired
	private OrderPutService orderPutService;
	@Autowired
	private SyslogService syslogService;
	@Autowired
	private UserService userService;
	@Autowired
	private AreaService areaService;

	@ApiOperation(value = "获取投放的分页列表")
	@NvwaRespBody
	@PostMapping("page")
	public Page<OrderPut> page(HttpServletRequest request, @RequestBody OrderPutQuery orderPutInfo) {
		Page<OrderPut> page;
		Integer uid = getUserId(request);
		Integer type = getUserType(request);
		List<Integer> uids = null;
		if (orderPutInfo.getCp() == null || orderPutInfo.getPs() == null) {
			throw new ControllerException("缺少分页参数");
		}
		if (uid == null || type == null) {
			throw new ControllerException("session 没有相关数据或许登录超时");
		} else {
			if (!Constants.USER_TYPE_ADMIN.equals(type)) {
				// 获取此用户的所有下级用户id
				uids = userService.selectUidsByAgentId(uid);
				if (uids == null || uids.isEmpty()) {
					orderPutInfo.setCreateUser(uid);
				} else {
					uids.add(uid);
				}
			}
			orderPutInfo.setPutType(Constants.PUT_TYPE_BOTTOM);
			page = orderPutService.selectPage(orderPutInfo, uids);
			logger.info("put page success, loginId: {}", uid);
		}
		return page;
	}

	@ApiOperation(value = "获取投放列表")
	@NvwaRespBody
	@PostMapping("list")
	public List<OrderPut> list(HttpServletRequest request, @RequestBody OrderPutQuery orderPutInfo) {
		List<OrderPut> result;
		Integer uid = getUserId(request);
		Integer type = getUserType(request);
		if (uid == null || type == null) {
			throw new ControllerException("session 没有相关数据或许登录超时");
		} else {
			List<Integer> uids = null;
			if (!Constants.USER_TYPE_ADMIN.equals(type)) {
				uids = userService.selectUidsByAgentId(uid);
				if (uids != null && uids.size() > 0) {
					orderPutInfo.setCreateUser(uid);
				} else {
					uids.add(uid);
				}
			}
			orderPutInfo.setPutType(Constants.PUT_TYPE_BOTTOM);
			result = orderPutService.selectList(orderPutInfo, uids);
		}
		return result;
	}

	@ApiOperation(value = "修改投放")
	@NvwaRespBody
	@PostMapping("update")
	public void update(HttpServletRequest request, @RequestBody OrderPut put) throws IOException {
		if (put.getId() == null) {
			throw new ControllerException("参数错误");
		}
		int uid = getUserId(request);
		int type = getUserType(request);
		put.setDxApp(null);
		put.setDxZdlx(null);
		put.setAdPosition(null);
		boolean isExist = false;
		OrderPut oldPut = orderPutService.info(put.getId());
		if (StringUtils.isNotBlank(put.getPutName()) && !put.getPutName().equals(oldPut.getPutName())) {
			// 若是抄底投放，则检查有无相同名称的投放
			isExist = orderPutService.checkNameIsExistByOrder(put);
			if (isExist) {
				throw new ControllerException("该抄底投放名称已存在");
			}
		}
//		if (put.getDqRule() != null && put.getPreDq() != null) {
//			// 设置定向地域
//			changeDxDq(put);
//		}
		put.setOpUser(uid);
		put.setOpUserType(type);
		// 不更改投放所属的计划id
		put.setId(oldPut.getId());
		put.setLimits(null);
		orderPutService.update(put, oldPut);
		syslogService.addSyslog(uid, type, put, oldPut, this.getClass().getName(), "update");
	}

	@ApiOperation(value = "删除投放")
	@NvwaRespBody
	@PostMapping("delete/{id}")
	public void delete(@PathVariable Integer id, @RequestAttribute("uid") Integer uid,
			@RequestAttribute("type") Integer type) {
		if (id == null) {
			throw new ControllerException("id 不得为空");
		}
		OrderPut put = new OrderPut();
		OrderPut oldPut = orderPutService.info(id);
		put.setId(id);
		put.setPutState(Constants.STATE_INVALID);
		orderPutService.update(put, oldPut);
		OrderPut newPut = orderPutService.info(id);
		syslogService.addSyslog(uid, type, newPut, oldPut, this.getClass().getName(), "deletePut");
	}

	@ApiOperation("增加投放")
	@NvwaRespBody
	@PostMapping("add")
	public Integer add(HttpServletRequest request, @RequestBody OrderPut put) throws IOException {
		int uid = getUserId(request);
		int type = getUserType(request);
		if (put.getPutType() != null && StringUtils.isNotBlank(put.getPutName())) {
			throw new ControllerException("缺少必要参数");
		}
		put.setPutType(Constants.PUT_TYPE_BOTTOM);
		put.setCreateUser(uid);
		put.setOpUser(uid);
		put.setOpUserType(type);
		put.setDxZdlx(Constants.TERMINAL_TYPE_APP.toString());
		boolean isExist = false;
		// 抄底投放不可重名
		isExist = orderPutService.checkNameIsExistByOrder(put);
		if (isExist) {
			throw new ControllerException("该抄底投放名称已存在");
		}
//		if (put.getDqRule() != null && put.getPreDq() != null) {
//			changeDxDq(put);
//		}
		orderPutService.add(put);
		syslogService.addSyslog(uid, type, put, this.getClass().getName(), "add");
		return put.getId();
	}

	@ApiOperation("投放详情")
	@NvwaRespBody
	@GetMapping("info/{id}")
	public OrderPut info(@PathVariable Integer id) {
		if (id == null) {
			throw new ControllerException("id 不可为空");
		}
		OrderPut put = orderPutService.info(id);
		return put;
	}

	private void changeDxDq(OrderPut put) {
		if (Constants.AREA_RULE_INCLUDE.equals(put.getDqRule())) {
			put.setDxDq(put.getPreDq());
		} else {
			List<Integer> allAreaId = areaService.getAllAreaCityId();
			String excludeIdStr = put.getPreDq();
			List<Integer> excludeId = StringUtils.str2List4Int(excludeIdStr, Constants.SIGN_COMMA);
			allAreaId.removeAll(excludeId);
			put.setDxDq(StringUtils.list2str4Int(allAreaId, Constants.SIGN_COMMA));
		}
	}
}

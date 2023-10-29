package com.iwanvi.nvwa.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.PlanService;
import com.iwanvi.nvwa.web.service.SyslogService;
import com.iwanvi.nvwa.web.service.UserGrandService;
import com.iwanvi.nvwa.web.service.UserService;
import com.iwanvi.nvwa.web.util.ObjectResultUtil;
import com.iwanvi.nvwa.web.util.ResponseResult;
import com.iwanvi.nvwa.web.util.WebConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "计划管理")
@RequestMapping(value = "plan", method = { RequestMethod.GET, RequestMethod.POST })
public class PlanController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);

	@Autowired
	private PlanService planService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private SyslogService syslogService;
	@Autowired
	private UserGrandService userGrandService;

	@ApiOperation("计划分页列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "planName", value = "计划名称", dataType = "string", required = false),
			@ApiImplicitParam(name = "adverId", value = "关联客户id", dataType = "int", required = false),
			@ApiImplicitParam(name = "cp", value = "当前页数", dataType = "int", required = true),
			@ApiImplicitParam(name = "ps", value = "一页的条数", dataType = "int", required = true) })
	@RequestMapping("page")
	public ObjectResultUtil<SwaggerPage<List<Plan>>> page(HttpServletRequest request, Plan plan,
			@RequestParam(defaultValue = "1") Integer cp, @RequestParam(defaultValue = "10") Integer ps) {
		ObjectResultUtil<SwaggerPage<List<Plan>>> resultUtil;
		try {
			Integer userId = getUserId(request);
			Integer userType = getUserType(request);
			if (userId == null || userType == null) {
				logger.error("loginUser not find data,maybe not login");
				resultUtil = ResponseResult.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, "没有相关登录人或许登录超时");
				return resultUtil;
			}
			List<Integer> advers = null;
			if (Constants.USER_TYPE_CUST.equals(userType)) {
				User user = userService.get(userId);
				plan.setAdverId(user.getCompany());
			} else if (!Constants.USER_TYPE_ADMIN.equals(userType)) {
				advers = userGrandService.getIdListByUidAndType(userId, WebConstants.AGENT_OBJECT_TYPE_ADVER);
				if (advers != null && advers.size() == 0) {
					resultUtil = ResponseResult.STATUS_FAILED(Constants.NO_ADVERTISER, "无关联广告主");
					return resultUtil;
				}
			}
			SwaggerPage<List<Plan>> page = planService.selectByPage(plan, advers, cp, ps);
			logger.info("plan page success");
			resultUtil = ResponseResult.STATUS_OK(page);
		} catch (Exception e) {
			logger.error("plan page error", e);
			resultUtil = ResponseResult.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return resultUtil;
	}
	
	@ApiOperation("订单列表接口")
	@ApiImplicitParam(name = "planName", value = "计划名称", dataType = "string", required = true)
	@RequestMapping("list")
	public ObjectResultUtil<List<Plan>> list(HttpServletRequest request, Plan plan) {
		ObjectResultUtil<List<Plan>> resultUtil;
		try {
			Integer userId = getUserId(request);
			Integer userType = getUserType(request);
			List<Integer> advers = null;
			if (userId == null || userType == null) {
				logger.error("loginUser not find data,maybe not login");
				resultUtil = ResponseResult.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, "没有相关登录人或许登录超时");
				return resultUtil;
			}
			if (Constants.USER_TYPE_CUST.equals(userType)) {
				User user = userService.get(userId);
				plan.setAdverId(user.getCompany());
			} else if (!Constants.USER_TYPE_ADMIN.equals(userType)) {
				advers = userGrandService.getIdListByUidAndType(userId, WebConstants.AGENT_OBJECT_TYPE_ADVER);
				if (advers != null && advers.size() == 0) {
					resultUtil = ResponseResult.STATUS_FAILED(Constants.NO_ADVERTISER, "无关联广告主");
					return resultUtil;
				}
			}
			List<Plan> plans = planService.selectByList(plan, advers);
			logger.info("plan list success");
			resultUtil = ResponseResult.STATUS_OK(plans);
		} catch (Exception e) {
			logger.error("plan list error", e);
			resultUtil = ResponseResult.EXCEPTION_ERROR();
		}
		return resultUtil;
	};

	@ApiOperation("订单详情接口")
	@RequestMapping("info")
	public ObjectResultUtil<Plan> info(@ApiParam(value = "订单id",required=true) @RequestParam Integer id) {
		ObjectResultUtil<Plan> resultUtil;
		if (id != null) {
			Plan plan = planService.info(id);
			logger.info("plan info success id {}", id);
			resultUtil = ResponseResult.STATUS_OK(plan);
		} else {
			logger.error("plan update error beacuse id is null");
			resultUtil = ResponseResult.PARAMETER_ERROR();
		}
		return resultUtil;
	}

	@ApiOperation("新增计划")
	@ApiImplicitParams({ @ApiImplicitParam(name = "planName", value = "计划名称", dataType = "string", required = true),
			@ApiImplicitParam(name = "adverId", value = "关联客户id", dataType = "int", required = true),
			@ApiImplicitParam(name = "planLimit", value = "计划限额", dataType = "int", required = true),
			@ApiImplicitParam(name = "impMonitorUrl", value = "曝光上报地址", dataType = "string", required = false),
			@ApiImplicitParam(name = "clkMonitorUrl", value = "点击上报地址", dataType = "string", required = false) })
	@RequestMapping("add")
	public <T> ObjectResultUtil<T> add(HttpServletRequest request,@RequestBody Plan plan) {
		ObjectResultUtil<T> resultUtil;
		if (plan.getPlanLimit() != null && StringUtils.isNotBlank(plan.getPlanName())) {
			try {
				Integer userId = getUserId(request);
				Integer userType = getUserType(request);
				if (userId == null || userType == null) {
					logger.error("loginUser not find data,maybe not login");
					resultUtil = ResponseResult.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, "没有相关登录人或许登录超时");
					return resultUtil;
				}
				if (Constants.USER_TYPE_CUST.equals(userType)) {
					User user = userMapper.selectByPrimaryKey(userId);
					plan.setAdverId(user.getCompany());
				}else {
					if (plan.getAdverId() == null) {
						resultUtil = ResponseResult.PARAMETER_ERROR();
						logger.error("plan add error beacuse adverId is null");
						return resultUtil;
					}
				}
				boolean isExist = planService.checkNameIsExist(plan.getPlanName());
				if (isExist) {
					logger.error("plan add error beacuse name is exist, name {}",plan.getPlanName());
					resultUtil = ResponseResult.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
					return resultUtil;
				}
				plan.setCreateUser(userId);
				//修改限额变成分
				if (plan.getPlanLimit() != null) {
					plan.setPlanLimit(plan.getPlanLimit() * 100);
				}
				planService.add(plan);
				try {
					if (!planService.noticePlanLimit(plan)) {
						planService.deletePlan(plan.getId());
						logger.error("plan add error,notice planLimit error");
						resultUtil = ResponseResult.STATUS_FAILED();
						return resultUtil;
					}
				} catch (Exception e) {
					planService.delete(plan.getId());
					resultUtil = ResponseResult.STATUS_FAILED();
					logger.error("plan add error,notice planLimit error",e);
					return resultUtil;
				}
				syslogService.addSyslog(userId, userType, plan, this.getClass().getName(), "addPlan");
				logger.info("plan page success planName {}, planId {}, userId {}",plan.getPlanName(),plan.getId(),userId);
				resultUtil = ResponseResult.STATUS_OK();
			} catch (Exception e) {
				logger.error("plan page error", e);
				resultUtil = ResponseResult.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
			}
		} else {
			logger.error("plan page error beacuse param is error, adverId {}, planLimit {}, planName {}",
					plan.getAdverId(), plan.getPlanLimit(), plan.getPlanName());
			resultUtil = ResponseResult.PARAMETER_ERROR();
		}
		return resultUtil;
	}

	@ApiOperation("修改订单")
	@ApiImplicitParam(name = "id",value="订单id",dataType = "int",required=true)
	@RequestMapping("update")
	public <T> ObjectResultUtil<T> update(HttpServletRequest request,@RequestBody Plan plan) {
		ObjectResultUtil<T> resultUtil;
		if (plan.getId() != null) {
			try {
				Integer userId = getUserId(request);
				Integer userType = getUserType(request);
				Plan oldPlan = planService.getById(plan.getId());
				if (oldPlan == null) {
					resultUtil = ResponseResult.DATA_NOT_FIND();
					logger.error("plan update error beacuse data not find");
					return resultUtil;
				}
				//检查名称是否已经存在
				if (StringUtils.isNotBlank(plan.getPlanName())) {
					boolean isExist = false;
					if (!plan.getPlanName().equals(oldPlan.getPlanName())) {
						isExist = planService.checkNameIsExist(plan.getPlanName());
					}
					if (isExist) {
						logger.error("plan update error beacuse name is exist , name {}",plan.getPlanName());
						resultUtil = ResponseResult.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
						return resultUtil;
					}
				}
				if (plan.getPlanLimit() != null) {
					plan.setPlanLimit(plan.getPlanLimit() * 100);
				}
				plan.setLimitState(null);
				planService.update(plan);
				if (plan.getPlanLimit() != null && !plan.getPlanLimit().equals(oldPlan.getPlanLimit())) {
					try {
						//往redis里同步计划限额
						if (!planService.noticePlanLimit(plan)) {
							planService.update(oldPlan);
							logger.error("plan update error,notice planLimit error");
							resultUtil = ResponseResult.STATUS_FAILED();
							return resultUtil;
						}
					} catch (Exception e) {
						planService.update(oldPlan);
						logger.error("plan update error,notice planLimit error",e);
						resultUtil = ResponseResult.STATUS_FAILED();
						return resultUtil;
					}
				}
				Plan newPlan = planService.getById(plan.getId());
				syslogService.addSyslog(userId, userType, newPlan, oldPlan, this.getClass().getName(), "updatePlan");
				resultUtil = ResponseResult.STATUS_OK();
				logger.info("plan update success id {},userId {}", plan.getId(), userId);
			} catch (Exception e) {
				logger.error("plan list error", e);
				resultUtil = ResponseResult.EXCEPTION_ERROR();
			}
		} else {
			logger.error("plan update error beacuse id is null");
			resultUtil = ResponseResult.PARAMETER_ERROR();
		}
		return resultUtil;
	}

	@ApiOperation("删除订单接口")
	@RequestMapping("delete")
	public <T> ObjectResultUtil<T> delete(HttpServletRequest request,@ApiParam(name="id",value="订单id",required=true) @RequestParam Integer id) {
		ObjectResultUtil<T> resultUtil;
		if (id != null) {
			try {
				Integer userId = getUserId(request);
				planService.delete(id);
				resultUtil = ResponseResult.STATUS_OK();
				logger.info("plan delete success, id {}, userId {}", id, userId);
			} catch (Exception e) {
				logger.error("plan list error", e);
				resultUtil = ResponseResult.EXCEPTION_ERROR();
			}
		} else {
			logger.error("plan update error beacuse id is null");
			resultUtil = ResponseResult.PARAMETER_ERROR();
		}
		return resultUtil;
	}

}

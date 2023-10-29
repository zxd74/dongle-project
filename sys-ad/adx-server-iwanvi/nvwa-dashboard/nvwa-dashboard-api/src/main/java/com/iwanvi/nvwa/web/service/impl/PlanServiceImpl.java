package com.iwanvi.nvwa.web.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.property.PropertyCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.PlanMapper;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.PlanExample;
import com.iwanvi.nvwa.dao.model.PlanExample.Criteria;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.PlanService;
import com.iwanvi.nvwa.web.service.PutService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.util.WebConstants;

@Service
public class PlanServiceImpl implements PlanService {
	private static final Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);

	@Autowired
	private PlanMapper planMapper;
	@Autowired
	private PutService putService;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private SysCrontabService sysCrontabService;

	@Override
	public boolean checkNameIsExist(String planName) {
		PlanExample planExample = new PlanExample();
		planExample.createCriteria().andPlanNameEqualTo(planName).andPlanStateNotEqualTo(Constants.STATE_INVALID);
		List<Plan> plans = planMapper.selectByExample(planExample);
		return plans.size() > 0;
	}

	@Override
	public SwaggerPage<List<Plan>> selectByPage(Plan plan, List<Integer> advers, Integer cp, Integer ps) {
		PlanExample planExample = packExample(plan, advers);
		SwaggerPage<List<Plan>> page = new SwaggerPage<>();
		int count = planMapper.countByExample(planExample);
		if (count > 0) {
			page = new SwaggerPage<>(count, cp, ps);
			planExample.setStart(page.getStartPosition());
			planExample.setLimit(ps);
			planExample.setOrderByClause("run_state desc, id desc");
			List<Plan> plans = planMapper.selectByExample(planExample);
			for (Plan onePlan : plans) {
//				int putCount = putService.getPutCountByOidOrPid(onePlan.getId(), null);
//				onePlan.setPutNumbers(putCount);
				if (onePlan.getPlanLimit() != null) {
					onePlan.setPlanLimit(onePlan.getPlanLimit()/100);
				}
				fullPlan(onePlan);
			}
			page.setData(plans);
		} else {
			page = new SwaggerPage<>(count);
		}
		return page;
	}

	private void fullPlan(Plan onePlan) {
		Map<String, String> map = planMapper.selectOtherById(onePlan.getId());
		if (map != null && !map.isEmpty()) {
			onePlan.setCustName(map.get("custName"));
			onePlan.setUserName(map.get("userName"));
		}
	}

	private PlanExample packExample(Plan plan, List<Integer> advers) {
		PlanExample planExample = new PlanExample();
		Criteria criteria = planExample.createCriteria();
		if (StringUtils.isNotBlank(plan.getPlanName())) {
			criteria.andPlanNameLike(
					StringUtils.concat(Constants.SIGN_PERCENT, plan.getPlanName(), Constants.SIGN_PERCENT));
		}
		if (plan.getAdverId() != null) {
			criteria.andAdverIdEqualTo(plan.getAdverId());
		} else if (advers != null && advers.size() > 0) {
			criteria.andAdverIdIn(advers);
		}
		if (plan.getCreateUser() != null) {
			criteria.andCreateUserEqualTo(plan.getCreateUser());
		}
		// if (uids != null && uids.size() > 0) {
		// criteria.andCreateUserIn(uids);
		// }
		if (plan.getPlanState() != null) {
			criteria.andPlanStateEqualTo(plan.getPlanState());
		} else {
			criteria.andPlanStateNotEqualTo(Constants.STATE_INVALID);
		}
		if (plan.getRunState() != null) {
			criteria.andRunStateEqualTo(plan.getRunState());
		}
		if (plan.getCreateType() != null) {
			criteria.andCreateTypeNotEqualTo(plan.getCreateType());
		}else {
			criteria.andCreateTypeNotEqualTo(WebConstants.PLAN_CREATE_AUTO);
		}
		return planExample;
	}

	@Override
	@Transactional
	public void add(Plan plan) {
		plan.setCreateTime(new Date());
		plan.setPlanState(Constants.STATE_VALID);
		plan.setRunState(Constants.STATE_VALID);
		plan.setLimitState(Constants.STATE_VALID);
		planMapper.insertSelective(plan);
	}

	@Override
	public List<Plan> selectByList(Plan plan, List<Integer> advers) {
		PlanExample planExample = packExample(plan, advers);
		if (StringUtils.isNotBlank(plan.getPlanName())) {
			planExample.setStart(0);
			planExample.setLimit(20);
		}
		return planMapper.selectByExample(planExample);
	}

	@Override
	public Plan info(Integer id) {
		Plan plan = planMapper.selectByPrimaryKey(id);
		if (plan.getPlanLimit() != null) {
			plan.setPlanLimit(plan.getPlanLimit()/100);
		}
		return plan;
	}
	
	@Override
	public Plan getById(Integer id) {
		Plan plan = planMapper.selectByPrimaryKey(id);
		return plan;
	}

	@Override
	@Transactional
	public void update(Plan plan) {
		plan.setUpdateTime(new Date());
		// 同步计划
		updateSysCrontab(plan);
		planMapper.updateByPrimaryKeySelective(plan);
		if (Constants.STATE_INVALID.equals(plan.getPlanState())) {
			Put put = new Put();
			put.setPutState(Constants.STATE_INVALID);
			put.setUpdateTime(new Date());
			putService.updatePutByPid(put, plan.getId());
		}
	}

	@Override
	public void updatePlanByUid(Plan plan, Integer uid, Integer status) {
		PlanExample planExample = new PlanExample();
		planExample.createCriteria().andAdverIdEqualTo(uid).andPlanStateEqualTo(status);
		if (Constants.STATE_BALANCE_INVALID.equals(plan.getPlanState())
				|| Constants.STATE_VALID.equals(plan.getPlanState())) {
			List<Plan> plans = planMapper.selectByExample(planExample);
			Plan plan2 = new Plan();
			PropertyCopier.copyBeanProperties(Plan.class, plan, plan2);
			for (Plan onePlan : plans) {
				if (Constants.STATE_BALANCE_INVALID.equals(plan.getPlanState())) {
					Put put = new Put();
					put.setPutState(Constants.STATE_BALANCE_INVALID);
					putService.updatePutByPid(put, plan.getId(), Constants.STATE_VALID);
				} else {
					Put put = new Put();
					put.setPutState(Constants.STATE_VALID);
					putService.updatePutByPid(put, plan.getId(), Constants.STATE_BALANCE_INVALID);
				}
				updateSysCrontab(plan2, onePlan);
			}
		}
		plan.setUpdateTime(new Date());
		planMapper.updateByExampleSelective(plan, planExample);
		logger.info("update plan by user success plan {}", JsonUtils.TO_JSON(plan));
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		Plan plan = new Plan();
		plan.setId(id);
		plan.setPlanState(Constants.STATE_INVALID);
		update(plan);
	}

	@Override
	public boolean noticePlanLimit(Plan plan) {
		try {
			// User adverUser = userService.getAdverIdByAid(plan.getAdverId());
			Company company = companyMapper.selectByPrimaryKey(plan.getAdverId());
			String key = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_LIMIT_PLAN, company.getUuid(),
					plan.getId());
			redisDao.set(key, "" + plan.getPlanLimit() * 1000);
			logger.info("planService modify planLimit success planId {}, planLimit {}", plan.getId(),
					plan.getPlanLimit());
			return true;
		} catch (Exception e) {
			logger.error("planService modify planLimit error", e);
			return false;
		}
	}

	@Override
	@Transactional
	public void deletePlan(Integer planId) {
		if (planId != null) {
			planMapper.deleteByPrimaryKey(planId);
		}
	}

	private void updateSysCrontab(Plan plan) {
		Plan oldPlan = planMapper.selectByPrimaryKey(plan.getId());
		updateSysCrontab(plan, oldPlan);
	}

	private void updateSysCrontab(Plan plan, Plan oldPlan) {
		Integer opType = null;
		Integer planLimit = plan.getPlanLimit();
		Integer oldPlanLimit = oldPlan.getPlanLimit();
		if (planLimit != null && !planLimit.equals(oldPlanLimit)) {
			if (oldPlanLimit != null && planLimit > oldPlanLimit) {
				if (Constants.STATE_INVALID.equals(oldPlan.getLimitState())) {
					plan.setLimitState(Constants.STATE_VALID);
				}
			}
		}
		if (plan.getPlanState() != null || plan.getRunState() != null || plan.getLimitState() != null) {
			if (plan.getPlanState() == null) {
				plan.setPlanState(oldPlan.getPlanState());
			}
			if (plan.getRunState() == null) {
				plan.setRunState(oldPlan.getRunState());
			}
			if (plan.getLimitState() == null) {
				plan.setLimitState(oldPlan.getLimitState());
			}
			if (!oldPlan.getLimitState().equals(plan.getLimitState())
					|| !oldPlan.getRunState().equals(plan.getRunState())
					|| !oldPlan.getPlanState().equals(plan.getPlanState())) {
				opType = Constants.OP_UPDATE;
			}
		}
		if (opType != null) {
			// 同步
			sysCrontabService.addSysCrontabCheckCount(oldPlan.getId(), Constants.OBJ_PLAN, opType);
		}
	}
}

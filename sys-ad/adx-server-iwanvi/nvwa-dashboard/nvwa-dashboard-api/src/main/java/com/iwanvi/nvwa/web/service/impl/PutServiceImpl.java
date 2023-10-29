package com.iwanvi.nvwa.web.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdxRelationMapper;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.PlanMapper;
import com.iwanvi.nvwa.dao.PutMapper;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.CompanyExample;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.PlanExample;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.PutExample;
import com.iwanvi.nvwa.dao.model.PutExample.Criteria;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.AdxRelationService;
import com.iwanvi.nvwa.web.service.EntityService;
import com.iwanvi.nvwa.web.service.PutService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

@Service
public class PutServiceImpl implements PutService {
	private static final Logger logger = LoggerFactory.getLogger(PutServiceImpl.class);
	@Autowired
	private PutMapper putMapper;
	@Autowired
	private PlanMapper planMapper;
	@Autowired
	private EntityService entityService;
	@Autowired
	private AdxRelationService adxRelationService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SysCrontabService sysCrontabService;
	@Autowired
	private AdxRelationMapper adxRelationMapper;

	@Override
	public SwaggerPage<List<Put>> listForPage(Put put, List<Integer> uids, Integer adverId, List<Integer> advers,
			Integer cp, Integer ps) {
		SwaggerPage<List<Put>> page;
		List<Integer> putStates = StringUtils.isNotBlank(put.getPutStates())
				? StringUtils.str2List4Int(put.getPutStates(), Constants.SIGN_COMMA)
				: null;
		int count = putMapper.selectCountForPage(put, uids, adverId, advers, putStates);
		if (count > 0) {
			page = new SwaggerPage<>(count, cp, ps);
			List<Put> list = putMapper.selectForPage(put, uids, adverId, advers, putStates, page.getStartPosition(),
					ps);
			for (Put onePut : list) {
				if (onePut.getPutLimit() != null) {
					onePut.setPutLimit(onePut.getPutLimit() / 100);
				}
				if (onePut.getPrice() != null) {
					onePut.setPrice(onePut.getPrice() / 100);
				}
			}
			page.setData(list);
		} else {
			page = new SwaggerPage<>(Constants.INTEGER_0, cp, ps);
		}
		return page;
	}

	@Override
	public List<Put> list(Put put, List<Integer> uids, Integer adverId, List<Integer> advers) {
		// if (Constants.INTEGER_1.equals(uids.size())) {
		// put.setCreateUser(uids.get(0));
		// }
		PutExample putExample = packageExample(put, uids, adverId, advers);
		if (putExample != null) {
			if (StringUtils.isNotBlank(put.getPutName())) {
				putExample.setStart(0);
				putExample.setLimit(20);
			}
		} else {
			return Lists.newArrayList();
		}
		return putMapper.selectByExample(putExample);
	}

	@Override
	@Transactional
	public void update(Put put) {
		if (put.getId() != null) {
			put.setUpdateTime(new Date());
			putMapper.updateByPrimaryKeySelective(put);
			// 同步
			updateSysCrontab(put);
		}
	}

	@Override
	@Transactional
	public void update(Put put, Put oldPut) {
		if (!Constants.STATE_INVALID.equals(put.getPutState())) {
			checkPutState(put, oldPut);
			boolean needAdxAudit = false;
			if (StringUtils.isNotBlank(put.getLandUrl()) && !put.getLandUrl().equals(oldPut.getLandUrl())) {
				needAdxAudit = true;
			}
			if (StringUtils.isNotBlank(put.getDxMedia()) && !put.getDxMedia().equals(oldPut.getDxMedia())) {
				needAdxAudit = true;
			}
			if (put.getAdPosition() != null && !put.getAdPosition().equals(oldPut.getAdPosition())) {
				needAdxAudit = true;
			}
			//若 落地页、媒体、广告位发生变化则级联重置相关创意的审核(改为未审核)
			if (needAdxAudit) {
				Entity entity = new Entity();
				entity.setEntityState(Constants.STATE_WAIT_AUDIT);
				entityService.updateEntityByPid(entity, put.getId());
				adxRelationService.restAdxRelationByPut(put.getId(), Constants.PUT_TYPE_ACCURATE);
			}
		}
		if (Constants.STATE_INVALID.equals(put.getIsPdb())) {
			put.setDealId(StringUtils.EMPTY);
		}
		if (StringUtils.isNotBlank(put.getDxMedia())) {
			if (StringUtils.isBlank(put.getDxApp())) {
				put.setDxApp(StringUtils.EMPTY);
			}
			if (StringUtils.isBlank(put.getDxAppType())) {
				put.setDxAppType(StringUtils.EMPTY);
			}
		}
		if (Constants.STATE_INVALID.equals(put.getPutState())) {
			Entity entity = new Entity();
			entity.setEntityState(Constants.STATE_INVALID);
			//使相关投放的创意变为无效
			entityService.updateEntityByPid(entity, put.getId());
		}
		// 同步
		updateSysCrontab(put);
		put.setUpdateTime(new Date());
		putMapper.updateByPrimaryKeySelective(put);
	}

	@Override
	@Transactional
	public void add(Put put) {
		put.setCreateTime(new Date());
		put.setRunState(Constants.STATE_VALID);
		checkPutState(put, null);
		if (Constants.STATE_INVALID.equals(put.getIsPdb())) {
			put.setDealId(StringUtils.EMPTY);
		}
		if (Constants.PUT_TYPE_BOTTOM.equals(put.getPutType())) {
			put.setPutLimit(Integer.MAX_VALUE);
			put.setBeginTime(new Date());
			String dateStr = "28881212";
			put.setEndTime(DateUtils.parse(dateStr, DateUtils.SHORT_FORMAT));
			PlanExample planExample = new PlanExample();
			planExample.createCriteria().andCreateTypeEqualTo(WebConstants.PLAN_CREATE_AUTO)
					.andPlanStateNotEqualTo(Constants.STATE_INVALID);
			List<Plan> plans = planMapper.selectByExample(planExample);
			if (plans != null && plans.size() > 0) {
				put.setPid(plans.get(0).getId());
			} else {
				// 初始化计划
				Plan plan = new Plan();
				plan.setPlanName("IWanVi抄底初始化计划(勿操作)");
				plan.setCreateTime(new Date());
				plan.setCreateType(WebConstants.PLAN_CREATE_AUTO);
				plan.setLimitState(Constants.STATE_VALID);
				plan.setRunState(Constants.STATE_VALID);
				plan.setPlanState(Constants.STATE_VALID);
				plan.setPlanLimit(Integer.MAX_VALUE);
				CompanyExample companyExample = new CompanyExample();
				companyExample.createCriteria().andUuidEqualTo(WebConstants.KA_ADVER_UUID)
						.andStatusEqualTo(Constants.STATE_VALID).andTypeEqualTo(WebConstants.COMPANY_TYPE_ADVER)
						.andAgTypeEqualTo(Constants.INTEGER_1);
				List<Company> companies = companyMapper.selectByExample(companyExample);
				if (companies != null && companies.size() > 0) {
					plan.setAdverId(companies.get(0).getId());
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
					plan.setAdverId(company.getId());
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
				planMapper.insertSelective(plan);
				planMapper.countByExample(null);
				put.setPid(plan.getId());
			}
		}
		putMapper.insertSelective(put);
		putMapper.countByExample(null);
	}

	@Override
	public void checkPutState(Put put, Put oldPut) {
		if (Constants.PUT_TYPE_BOTTOM.equals(put.getPutType())) {
			put.setPutState(Constants.STATE_VALID);
		} else {
			if (put.getBeginTime() != null && put.getEndTime() != null) {
				if (put.getBeginTime().compareTo(new Date()) > 0) {
					put.setPutState(Constants.STATE_UNSTART);
				} else if (put.getEndTime().compareTo(new Date()) < 0) {
					put.setPutState(Constants.STATE_EXPIRED);
				} else if (oldPut != null && Constants.STATE_BALANCE_INVALID.equals(oldPut.getPutState())) {
					put.setPutState(oldPut.getPutState());
				} else if (oldPut != null && Constants.STATE_UNIT_LIMIT.equals(oldPut.getPutState())) {
					if (put.getPutLimit() != null && oldPut.getPutLimit() != null
							&& put.getPutLimit() > oldPut.getPutLimit()) {
						put.setPutState(Constants.STATE_VALID);
					}else {
						put.setPutState(oldPut.getPutState());
					}
				} else {
					put.setPutState(Constants.STATE_VALID);
				}
			}
		}

	}

	@Override
	public Put info(Integer id) {
		Put put = putMapper.selectByPrimaryKey(id);
		if (put.getPutLimit() != null) {
			put.setPutLimit(put.getPutLimit() / 100);
		}
		if (put.getPrice() != null) {
			put.setPrice(put.getPrice() / 100);
		}
		return put;
	}
	
	@Override
	public Put getById(Integer id) {
		Put put = putMapper.selectByPrimaryKey(id);
		return put;
	}

	private PutExample packageExample(Put put, List<Integer> uids, Integer adverId, List<Integer> advers) {
		PutExample putExample = new PutExample();
		Criteria criteria = putExample.createCriteria();
		List<Integer> pids = null;
		// 客戶查看投放列表
		if (adverId != null || advers != null) {
			if (Constants.PUT_TYPE_ACCURATE.equals(put.getPutType()) && put.getPid() == null) {
				PlanExample planExample = new PlanExample();
				com.iwanvi.nvwa.dao.model.PlanExample.Criteria planCriteria = planExample.createCriteria();
				if (adverId != null) {
					planCriteria.andAdverIdEqualTo(adverId);
				} else if (advers != null && advers.size() > 0) {
					planCriteria.andAdverIdIn(advers);
				}
				List<Plan> plans = planMapper.selectByExample(planExample);
				if (plans.isEmpty()) {
					return null;
				}
				pids = Lists.transform(plans, new Function<Plan, Integer>() {
					@Override
					public Integer apply(Plan input) {
						return input.getId();
					}
				});
			}
		}
		if (StringUtils.isNotBlank(put.getPutName())) {
			criteria.andPutNameLike(
					StringUtils.concat(Constants.SIGN_PERCENT, put.getPutName(), Constants.SIGN_PERCENT));
		}
		if (put.getPutType() != null) {
			criteria.andPutTypeEqualTo(put.getPutType());
		}
		if (put.getPid() != null) {
			criteria.andPidEqualTo(put.getPid());
		} else if (pids != null) {
			criteria.andPidIn(pids);
		}
		if (put.getAdPosition() != null) {
			criteria.andAdPositionEqualTo(put.getAdPosition());
		}
		if (put.getRunState() != null) {
			criteria.andRunStateEqualTo(put.getRunState());
		}
		if (put.getPutState() != null) {
			criteria.andPutStateEqualTo(put.getPutState());
		} else if (StringUtils.isNotBlank(put.getPutStates())) {
			List<Integer> states = StringUtils.str2List4Int(put.getPutStates(), Constants.SIGN_COMMA);
			criteria.andPutStateIn(states);
		} else {
			criteria.andPutStateNotEqualTo(Constants.STATE_INVALID);
		}
		if (put.getCreateUser() != null) {
			criteria.andCreateUserEqualTo(put.getCreateUser());
		} else if (uids != null && uids.size() > 0) {
			criteria.andCreateUserIn(uids);
		}
		return putExample;
	}

	@Override
	public boolean checkNameExistInOther(String putName, Integer pid) {
		PutExample putExample = new PutExample();
		Criteria criteria = putExample.createCriteria();
		criteria.andPutNameEqualTo(putName).andPutStateNotEqualTo(Constants.STATE_INVALID);
		if (pid != null) {
			criteria.andPidEqualTo(pid).andPutTypeEqualTo(Constants.PUT_TYPE_ACCURATE);
		} else {
			criteria.andPutTypeEqualTo(Constants.PUT_TYPE_BOTTOM);
		}
		int count = putMapper.countByExample(putExample);
		return count > 0;
	}

//	@Override
//	public Map<String, Object> selectSchedules(Map<String, Object> paramMap) {
//		Map<String, Object> resultMap;
//		resultMap = putMapper.selectSchedules(paramMap);
//		return resultMap;
//	}

	@Override
	public boolean resetPutLimit(Put put) {
		try {
			Integer companyId = null;
			if (put.getPid() != null) {
				companyId = planMapper.selectByPrimaryKey(put.getPid()).getAdverId();
			}
			Company company = companyMapper.selectByPrimaryKey(companyId);
			String key = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_LIMIT_UNIT, company.getUuid(),
					put.getId());
			redisDao.set(key, Long.toString(Long.valueOf(put.getPutLimit()) * 1000));
			logger.info("reset put limit success id:{}, limit: {}", put.getId(), put.getPutLimit());
			return true;
		} catch (Exception e) {
			logger.error("reset put limit error id:{}, limit: {}, e:{}", put.getId(), put.getPutLimit(), e);
			return false;
		}
	}

	@Override
	@Transactional
	public void delete(Put put) {
		if (put.getId() != null) {
			putMapper.deleteByPrimaryKey(put.getId());
		}
	}

	@Override
	@Transactional
	public void updatePutByPid(Put put, Integer pid) {
		if (put != null && pid != null) {
			PutExample putExample = new PutExample();
			putExample.createCriteria().andPidEqualTo(pid).andPutTypeEqualTo(Constants.PUT_TYPE_ACCURATE)
					.andPutStateNotEqualTo(Constants.STATE_INVALID);
			if (Constants.STATE_INVALID.equals(put.getPutState())) {
				updateEntityWithPut(putExample);
			}
			putMapper.updateByExampleSelective(put, putExample);
		}
	}

	private void updateEntityWithPut(PutExample putExample) {
		List<Put> puts = putMapper.selectByExample(putExample);
		for (Put onePut : puts) {
			Entity entity = new Entity();
			entity.setUpdateTime(new Date());
			entity.setEntityState(Constants.STATE_INVALID);
			entityService.updateEntityByPid(entity, onePut.getId());
		}
	}

//	@Override
//	public Integer getPutCountByOidOrPid(Integer pid, Integer oid) {
//		if (pid != null && oid != null) {
//			PutExample putExample = new PutExample();
//			Criteria criteria = putExample.createCriteria();
//			if (pid != null) {
//				criteria.andPidEqualTo(pid);
//			}
//			if (oid != null) {
//				criteria.andOidEqualTo(oid);
//			}
//			criteria.andPutStateNotEqualTo(Constants.STATE_INVALID).andRunStateEqualTo(Constants.STATE_VALID);
//			return putMapper.countByExample(putExample);
//		}
//		return 0;
//	}

	@Override
	public void updatePutByPid(Put put, Integer pid, Integer status) {
		if (put != null) {
			if (Constants.STATE_BALANCE_INVALID.equals(put.getPutState())
					|| Constants.STATE_VALID.equals(put.getPutState())) {
				PutExample putExample = new PutExample();
				putExample.createCriteria().andPidEqualTo(pid).andPutStateEqualTo(status);
				putMapper.updateByExampleSelective(put, putExample);
				logger.info("put update by pid success pid {}, status {}, put {}", pid, status, JsonUtils.TO_JSON(put));
			}
		}
	}

	/**
	 * 清除投放于dmp标签定向的关联
	 */
	@Override
	public void deletePutCustomDx(List<Integer> tags) {
		for (Integer tag : tags) {
			String tagStr = StringUtils.concat(Constants.SIGN_COMMA, tag, Constants.SIGN_COMMA);
			PutExample putExample = new PutExample();
			putExample.createCriteria()
					.andDxDmpLike(StringUtils.concat(Constants.SIGN_PERCENT, tagStr, Constants.SIGN_PERCENT))
					.andPutStateNotEqualTo(Constants.STATE_INVALID);
			List<Put> puts = putMapper.selectByExampleWithBLOBs(putExample);
			for (Put put : puts) {
				Put newPut = new Put();
				newPut.setId(put.getId());
				newPut.setDxDmp(put.getDxDmp().replaceAll(tagStr, ""));
				putMapper.updateByPrimaryKeySelective(newPut);
				// 同步
				sysCrontabService.addSysCrontabCheckCount(put.getId(), Constants.OBJ_PUT, Constants.OP_UPDATE);
			}
		}
	}

	private void updateSysCrontab(Put put) {
		sysCrontabService.addSysCrontabCheckCount(put.getId(), Constants.OBJ_PUT, Constants.OP_UPDATE);
	}
}

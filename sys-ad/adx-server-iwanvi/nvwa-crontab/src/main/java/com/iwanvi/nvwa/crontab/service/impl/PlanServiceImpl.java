package com.iwanvi.nvwa.crontab.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.PlanService;
import com.iwanvi.nvwa.crontab.service.PutService;
import com.iwanvi.nvwa.crontab.service.SysCrontabService;
import com.iwanvi.nvwa.dao.PlanMapper;
import com.iwanvi.nvwa.dao.PutMapper;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.PlanExample;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.PutExample;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class PlanServiceImpl implements PlanService {

	private static final Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);

	@Autowired
	private PlanMapper planMapper;

	@Autowired
	private SysCrontabService sysCrontabService;

	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;
	
	@Autowired
	private AuditMessageProducer auditMessageProducer;
	
	@Autowired
	private PutMapper putMapper;
	
	@Autowired
	private PutService putService;
	
	@Override
	public Plan getPlan(Integer objectId) {
		return planMapper.selectByPrimaryKey(objectId);
	}

	@Override
	@Transactional
	public void resetPlanLimitState() {
		PlanExample example = new PlanExample();
		example.createCriteria().andPlanStateEqualTo(Constants.STATE_VALID)
				.andLimitStateEqualTo(Constants.STATE_INVALID);
		Plan plan = new Plan();
		plan.setLimitState(Constants.STATE_VALID);
		plan.setUpdateTime(new Date());
		List<Plan> planList = planMapper.selectByExample(example);
		logger.info("over limit plans:{}", JsonUtils.TO_JSON(planList));

		if (!CollectionUtils.isEmpty(planList)) {
			// 查找计划中状态正常但是超限额的计划 然后修改状态并同步到同步表
			planMapper.updateByExampleSelective(plan, example);
			for (Plan p : planList) {
				// 判断同步表中是否已存在相应计划的记录
				if (Constants.STATE_VALID == p.getRunState()) {
					sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_PLAN, Constants.OP_UPDATE,
							plan.getCreateUser());
				}
			}
		}

	}

	@Override
	public List<Plan> getValidPlanByUid(Integer id) {
		PlanExample example = new PlanExample();
		// 包含限额无效状态
		example.createCriteria().andAdverIdEqualTo(id).andPlanStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID);
		return planMapper.selectByExample(example);
	}

	@Override
	public PubRecord buildPubRecord(Plan plan, OpType kadd) {
		AdModelsProto.AdPlan p = buildAmpPlan(plan, kadd);
		if (p != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(p.toByteString());
			pubRecord.setEntityType(EntityType.kAdPlan);
			return pubRecord.build();
		}
		return null;
	}

	private AdPlan buildAmpPlan(Plan plan, OpType opType) {
		try {
			AdPlan.Builder planBuilder = AdPlan.newBuilder();
			planBuilder.setPlanId(plan.getId());
			if (!OpType.kDelete.equals(opType)) {
				if (Constants.STATE_VALID.equals(plan.getPlanState())
						&& Constants.STATE_VALID.equals(plan.getRunState())
						&& Constants.STATE_VALID.equals(plan.getLimitState())) {
					planBuilder.setStatus(Constants.STATE_VALID);
					planBuilder.setAdvertiserId(plan.getAdverId());
				} else {
					planBuilder.setStatus(Constants.STATE_INVALID);
				}
			}
			return planBuilder.build();
		} catch (Exception e) {
			logger.error("build plan error! id: {}", plan.getId(), e);
			return null;
		}
	}

	@Override
	public void buildPlanSend(Integer id, OpType opType) {
		Plan adPlan = planMapper.selectByPrimaryKey(id);
		//Company company = companyMapper.selectByPrimaryKey(adPlan.getCreateUser());
		AdModelsProto.AdPlan plan = buildAmpPlan(adPlan, opType);
		if (plan != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_PLAN + id, plan.toByteArray());
			logger.info("plan cache to redis, id : {}", id);
			auditMessageProducer.send(EntityType.kAdPlan, id, opType);
			logger.info("crontab ampPlan message is send, id : {}, opType : {}", id, opType);
		}
		PutExample putExample = new PutExample();
		putExample.createCriteria().andPidEqualTo(adPlan.getId());
		List<Put> puts = putMapper.selectByExample(putExample);
		if(puts.size() > 0){
			for(Put put : puts){
				putService.buildPutSend(put.getId(), opType);
			}
		}
	}

}

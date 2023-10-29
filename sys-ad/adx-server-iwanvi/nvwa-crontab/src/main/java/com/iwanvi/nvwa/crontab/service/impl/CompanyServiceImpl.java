package com.iwanvi.nvwa.crontab.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.CompanyService;
import com.iwanvi.nvwa.dao.AdxRelationMapper;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.AdxRelationExample;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.CompanyExample;
import com.iwanvi.nvwa.dao.model.CompanyExample.Criteria;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class CompanyServiceImpl implements CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;
	
	@Autowired
	private AuditMessageProducer auditMessageProducer;
	
	@Autowired
	private AdxRelationMapper adxRelationMapper;
	
	
	@Override
	public List<Company> getAllCompanyByType(Integer type) {
		CompanyExample companyExample = new CompanyExample();
		Criteria createCriteria = companyExample.createCriteria();
		createCriteria.andTypeEqualTo(type);
/*		if (type == CrontabConstants.COMPANY_AGENT_TYPE) {
			createCriteria.andBalanceStatusEqualTo(Constants.STATE_VALID);
		}*/
/*		if (type == CrontabConstants.COMPANY_ADVER_TYPE) {
			if
			createCriteria.andAuditStatusEqualTo(Constants.STATE_VALID);//广告主一张表
		}*/
		return companyMapper.selectByExample(companyExample);
	}

	@Override
	public PubRecord buildPubRecord(Company company, OpType kadd, EntityType type) {
		AdModelsProto.Agent agent = buildAgent(company, kadd);
		if (agent != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(agent.toByteString());
			pubRecord.setEntityType(type);
			return pubRecord.build();
		}
		return null;
	}

	private Agent buildAgent(Company company, OpType type) {
		try {
			Agent.Builder agentBuilder = Agent.newBuilder();
			agentBuilder.setAgentId(company.getId()).setAgentUuid(company.getUuid());
			if (!OpType.kDelete.equals(type)) {
				if (Constants.STATE_VALID.equals(company.getStatus())) {
					if (Constants.STATE_VALID.equals(company.getStatus())
							&& Constants.STATE_VALID.equals(company.getBalanceStatus())) {
						agentBuilder.setStatus(Constants.STATE_VALID);
					} else {
						agentBuilder.setStatus(Constants.STATE_INVALID);
					}
				if (company.getAgType() != null) {//代理商类型
					agentBuilder.setAgentType( //1直客 2常规
							CommonProto.AgentType.valueOf(company.getAgType()));
				}
				if (company.getProfitMargin() != null) {
					agentBuilder.setProfitMargin(company.getProfitMargin());
				}
				return agentBuilder.build();
			}
		}
			return null;
		} catch (Exception e) {
			logger.error("build agent error! id: {}", company.getId(), e);
			return null;
		}
	}

	@Override
	public void buildAdvertiserSend(Integer objectId, OpType opType) {
		Company company = companyMapper.selectByPrimaryKey(objectId);
		AdModelsProto.Advertiser advertiser = buildAdvertiser(company, opType);
		if (advertiser != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_ADVERTISER + objectId, advertiser.toByteArray());
			logger.info("advertisers cache to redis, id : {}", objectId);
			auditMessageProducer.send(EntityType.kAdvertiser, objectId, opType);
			logger.info("crontab advertisers message is send, id : {}, opType : {}", objectId, opType);
		}
	}

	private Advertiser buildAdvertiser(Company advertisers, OpType opType) {
		try {
			if (advertisers.getAid() != null) {
				Company ampAgent = companyMapper.selectByPrimaryKey(advertisers.getAid());
				if (ampAgent != null && ampAgent.getStatus() != null
						&& Constants.STATE_VALID.equals(ampAgent.getStatus())) {
					Advertiser.Builder userBuilder = Advertiser.newBuilder();
					userBuilder.setAdvertiserId(advertisers.getId()).setAdvertiserUuid(advertisers.getUuid());
					if (!OpType.kDelete.equals(opType)) {
						AdxRelationExample adxRelationExample = new AdxRelationExample();
						com.iwanvi.nvwa.dao.model.AdxRelationExample.Criteria criteria = adxRelationExample.createCriteria();
						criteria.andObjIdEqualTo(advertisers.getId())
						.andStatusEqualTo(Constants.STATE_VALID).andObjTypeEqualTo(1);
						if (ampAgent.getAgType() != 1) {//非直客
							criteria.andIndustryEqualTo(advertisers.getIndustryType());
						}
						List<AdxRelation> example = adxRelationMapper.selectByExample(adxRelationExample);
						AdxRelation adxRelation = null;
						if (!CollectionUtils.isEmpty(example)) {
							adxRelation = example.get(0);
							if (Constants.STATE_VALID.equals(advertisers.getStatus())
									&& Constants.STATE_VALID.equals(adxRelation.getStatus())) {
								if (advertisers.getOutCid() != null) {	
									userBuilder.setStatus(Constants.STATE_VALID);
//								} else if (Constants.STATE_VALID.equals(advertisers.getBalanceStatus())) {
								} else if (Constants.STATE_VALID.equals(adxRelation.getAuditState())) {
									userBuilder.setStatus(Constants.STATE_VALID);
								} else {
									userBuilder.setStatus(Constants.STATE_INVALID);
								} 
							} else {
								userBuilder.setStatus(Constants.STATE_INVALID);
							}
						} else {
							userBuilder.setStatus(Constants.STATE_INVALID);//未审核
						}
						logger.info(" adver status:{}",userBuilder.getStatus());

						if (advertisers.getBidDiscount() != null) {
							userBuilder.setAdxBidDiscount(advertisers.getBidDiscount());
						}
						if (advertisers.getPayDiscount() != null) {
							userBuilder.setAdxPayDiscount(advertisers.getPayDiscount());
						}
						if (advertisers.getAid() != null) {
							userBuilder.setAgentId(advertisers.getAid());
						}
						if (ampAgent.getUuid() != null) {
							userBuilder.setAgentUuid(ampAgent.getUuid());
						}
						if (advertisers.getIndustryType() != null) {
							userBuilder.setIndustryId(advertisers.getIndustryType());
						}
						return userBuilder.build();
					}
				}
			}
			return null;
		} catch (Exception e) {
			logger.error("build advertisers error! id: {}", advertisers.getId(), e);
			return null;
		}
	}

	@Override
	public void buildAgentSend(Integer id, OpType opType) {
		Company ampAgent = companyMapper.selectByPrimaryKey(id);
		AdModelsProto.Agent agent = buildAgent(ampAgent, opType);
		if (agent != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_AGENT + id, agent.toByteArray());
			logger.info("agent cache to redis, id : {}", id);
			auditMessageProducer.send(EntityType.kAgent, id, opType);
			logger.info("crontab agent message is send, id : {}, opType : {}", id, opType);
		}
	}

	@Override
	public PubRecord buildPubAderRecord(Company company, OpType kadd, EntityType kadvertiser) {
		AdModelsProto.Advertiser advertiser = buildAdvertiser(company, kadd);
		if (advertiser != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(advertiser.toByteString());
			pubRecord.setEntityType(kadvertiser);
			return pubRecord.build();
		}
		return null;
	}

}

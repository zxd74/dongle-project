package com.iwanvi.nvwa.web.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdxRelationMapper;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.PutMapper;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.AdxRelationExample;
import com.iwanvi.nvwa.dao.model.AdxRelationExample.Criteria;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.EntityExample;
import com.iwanvi.nvwa.web.service.AdxRelationService;
import com.iwanvi.nvwa.web.service.EntityService;
import com.iwanvi.nvwa.web.service.SysCrontabService;

@Service
public class AdxRelationServiceImpl implements AdxRelationService {

	private static final Logger logger = LoggerFactory.getLogger(AdxRelationServiceImpl.class);

	@Autowired
	private AdxRelationMapper adxRelationMapper;
	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private SysCrontabService sysCrontabService;
	@Autowired
	private EntityService entityService;
	@Autowired
	private PutMapper putMapper;

	@Override
	public void restAdxRelation(Integer objId, Integer objType) {
		logger.info("rest adxRelation objId:{}, objType:{}", objId, objType);
		AdxRelationExample relationExample = new AdxRelationExample();
		relationExample.createCriteria().andObjIdEqualTo(objId).andObjTypeEqualTo(objType);
		AdxRelation adxRelation = new AdxRelation();
//		adxRelation.setAuditState(Constants.STATE_INVALID);
		adxRelation.setStatus(Constants.STATE_INVALID);
		adxRelation.setAuditComments(StringUtils.EMPTY);
		adxRelation.setUpdateTime(new Date());
		adxRelationMapper.updateByExampleSelective(adxRelation, relationExample);
	}

	@Override
	public void restAdxRelationByPut(Integer pid, Integer putType) {
		EntityExample entityExample = new EntityExample();
		entityExample.createCriteria().andPidEqualTo(pid).andEntityStateNotEqualTo(Constants.STATE_INVALID);
		List<Entity> entities = entityMapper.selectByExample(entityExample);
		List<Integer> objIds = Lists.transform(entities, new Function<Entity, Integer>() {

			@Override
			public Integer apply(Entity input) {
				return input.getId();
			}

		});
		logger.info("rest adxRelation pid:{}, putType:{}, objIds:{}", pid, putType, objIds);
		if (entities != null && entities.size() > 0) {
			AdxRelationExample relationExample = new AdxRelationExample();
			relationExample.createCriteria().andObjIdIn(objIds).andObjTypeEqualTo(Constants.OBJ_ENTITY);
			AdxRelation adxRelation = new AdxRelation();
			adxRelation.setAuditState(Constants.STATE_INVALID);
			adxRelation.setAuditComments(StringUtils.EMPTY);
			adxRelation.setUpdateTime(new Date());
			adxRelationMapper.updateByExampleSelective(adxRelation, relationExample);
		}

	}

	@Override
	public void deleteAdxRelation(Integer objId, Integer objType) throws Exception {
		logger.info("delete adxRelation objId:{}, objType:{}", objId, objType);
		AdxRelationExample relationExample = new AdxRelationExample();
		relationExample.createCriteria().andObjIdEqualTo(objId).andObjTypeEqualTo(objType);
		AdxRelation relation = new AdxRelation();
		if (Constants.OBJ_ENTITY.equals(objType)) {
			Entity entity = entityMapper.selectByPrimaryKey(objId);
			relation.setAuditState(Constants.STATE_VALID);
		}
//		relation.setStatus(Constants.STATE_INVALID);
		relation.setUpdateTime(new Date());
		adxRelationMapper.updateByExampleSelective(relation, relationExample);
	}

	@Override
	public List<AdxRelation> selectList(AdxRelation adxRelation) {
		AdxRelationExample relationExample = packageExample(adxRelation);
		return adxRelationMapper.selectByExample(relationExample);
	}

	private AdxRelationExample packageExample(AdxRelation adxRelation) {
		AdxRelationExample example = new AdxRelationExample();
		Criteria criterion = example.createCriteria();
		if (adxRelation.getObjId() != null) {
			criterion.andObjIdEqualTo(adxRelation.getObjId());
		}
		if (adxRelation.getObjType() != null) {
			criterion.andObjTypeEqualTo(adxRelation.getObjType());
		}
		if (adxRelation.getAdxType() != null) {
			criterion.andAdxTypeEqualTo(adxRelation.getAdxType());
		}
		if (adxRelation.getStatus() != null) {
			criterion.andStatusEqualTo(adxRelation.getStatus());
		}
		if (adxRelation.getAuditState() != null) {
			criterion.andAuditStateEqualTo(adxRelation.getAuditState());
		}
		return example;
	}

	@Override
	public void audit(AdxRelation adxRelation, Integer uid) {
		Integer isAgree = adxRelation.getAuditState();
		String auditComments = adxRelation.getAuditComments();
		adxRelation.setAuditState(null);
		adxRelation.setAuditComments(null);
		AdxRelationExample example = packageExample(adxRelation);
		List<AdxRelation> relations = adxRelationMapper.selectByExample(example);
		if (relations != null && relations.size() > 0) {
			Integer auditAdxState = isAgree == Constants.INTEGER_1 ? Constants.STATE_VALID
					: Constants.STATE_FAILURE_AUDIT;
			Integer objState = isAgree == Constants.INTEGER_1 ? Constants.STATE_VALID
					: Constants.STATE_FAILURE_AUDIT;
			AdxRelation auditAdxRelation = new AdxRelation();
			auditAdxRelation.setAuditState(auditAdxState);
			if (Constants.STATE_VALID.equals(auditAdxState)) {
				auditAdxRelation.setAuditComments(StringUtils.EMPTY);
			}else {
				auditAdxRelation.setAuditComments(auditComments);
			}
			adxRelationMapper.updateByExampleSelective(auditAdxRelation, example);
			if (Constants.OBJ_ENTITY.equals(adxRelation.getObjType())) {
				Entity entity = new Entity();
				entity.setId(adxRelation.getObjId());
				entity.setEntityState(objState);
				if (Constants.STATE_VALID.equals(objState)) {
					entity.setAuditComments(StringUtils.EMPTY);
				}else {
					entity.setAuditComments(auditComments);
				}
				entity.setAuditUser(uid);
				entityMapper.updateByPrimaryKeySelective(entity);
				if (!Constants.STATE_INVALID.equals(objState)) {
					Entity crontabEntity = entityMapper.selectByPrimaryKey(adxRelation.getObjId());
					sysCrontabService.updateSysCrontabByEntity(crontabEntity.getPid(), Constants.OBJ_ORDER_PUT, Constants.OP_UPDATE);
				}
			}else{
				Company company = new Company();
				company.setId(adxRelation.getObjId());
				company.setAuditStatus(objState);
				if (Constants.STATE_VALID.equals(objState)) {
					company.setAuditComment(StringUtils.EMPTY);
				}else {
					company.setAuditComment(auditComments);
				}
				company.setAuditUser(uid);
				companyMapper.updateByPrimaryKeySelective(company);
				if (!Constants.STATE_INVALID.equals(objState)) {
					sysCrontabService.addSysCrontabCheckCount(company.getId(), Constants.OBJ_ADVERTISER, Constants.OP_UPDATE);
				}
			}
		}
	}

	@Override
	public void add(AdxRelation adxRelation) {
		if (adxRelation != null) {
            AdxRelationExample example = new AdxRelationExample();
            example.createCriteria().andObjTypeEqualTo(Constants.OBJ_ADVERTISER).andObjIdEqualTo(adxRelation.getObjId())
                    .andAdxTypeEqualTo(adxRelation.getAdxType());
            Company company = companyMapper.selectByPrimaryKey(adxRelation.getObjId());
            if (company != null) {
                int count = adxRelationMapper.countByExample(example);
                adxRelation.setIndustry(company.getIndustryType());
                if (Constants.FLOW_MEDIA_AIKA == adxRelation.getAdxType()) {
                    adxRelation.setAuditState(Constants.STATE_WAIT_AUDIT);
                } else {
                    adxRelation.setAuditState(Constants.STATE_INVALID);
                }
                if (count == Constants.INTEGER_0) {
                    adxRelation.setStatus(Constants.STATE_VALID);
                    adxRelation.setCreateTime(new Date());
                    adxRelationMapper.insertSelective(adxRelation);
                } else {
                    adxRelation.setAuditComments("");
                    adxRelationMapper.updateByExampleSelective(adxRelation, example);
                }
            } else {
                logger.error("company is not existed {}",adxRelation.getObjId());
            }
        }
	}

}

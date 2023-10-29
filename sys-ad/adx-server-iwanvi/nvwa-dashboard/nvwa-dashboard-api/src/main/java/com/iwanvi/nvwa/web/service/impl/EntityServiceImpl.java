package com.iwanvi.nvwa.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdxRelationMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.IndustryMapper;
import com.iwanvi.nvwa.dao.OrderPutAllMapper;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.OrdersMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.AdxRelationExample;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutAll;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelation;
import com.iwanvi.nvwa.dao.model.EntityExample;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.OrdersExample;
import com.iwanvi.nvwa.dao.model.EntityExample.Criteria;
import com.iwanvi.nvwa.dao.model.Industry;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.AdxRelationService;
import com.iwanvi.nvwa.web.service.AuditService;
import com.iwanvi.nvwa.web.service.EntityService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.service.SyslogService;
import com.iwanvi.nvwa.web.vo.EntityQuery;

import javax.annotation.Resource;

@Service
public class EntityServiceImpl implements EntityService {

	private static Logger logger = LoggerFactory.getLogger(EntityService.class);

	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private OrderPutMapper orderPutMapper;
	@Autowired
	private AdxRelationMapper adxRelationMapper;
	@Autowired
	private AdxRelationService adxRelationService;
	@Autowired
	private SyslogService syslogService;
	@Autowired
	private AdPositionService adPositionService;
	@Autowired
	private SysCrontabService sysCrontabService;
	@Autowired
	private AppsMapper appsMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private AuditService auditService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private OrderPutAllMapper orderPutAllMapper;
	@Autowired
	private IndustryMapper industryMapper;
	@Resource(name = "checkThreadPool")
	private ExecutorService checkThreadPool;

	@Override
	public Page<Entity> selectPage(EntityQuery entityQuery, Integer custId, List<Integer> advers) {
		Page<Entity> page;
		List<Integer> putIds = getPutIdsByCust(custId, advers);
		if (putIds == null || putIds.size() == 0) {
			return new Page<>(Constants.INTEGER_0);
		}
		EntityExample entityExample = entityQuery.toExample(putIds);
		Long count = entityMapper.countByExample(entityExample);
		if (count > 0) {
			page = new Page<>(count.intValue(), entityQuery.getCp(), entityQuery.getPs());
			entityExample.setOrderByClause("run_state desc,id desc");
			entityExample.setOffset(page.getStartPosition());
			entityExample.setRows(page.getPageSize());
			List<Entity> entities = entityMapper.selectByExample(entityExample);
			for (Entity entity : entities) {
				fullEntity(entity);
			}
			page.setData(entities);
		} else {
			page = new Page<>(count.intValue());
		}
		return page;
	}

	@Override
	public List<Entity> list(EntityQuery entityQuery, Integer adverId, List<Integer> advers) {
		List<Integer> putIds = getPutIdsByCust(adverId, advers);
		EntityExample entityExample = entityQuery.toExample(putIds);
		if (putIds == null || putIds.size() == 0) {
			return Lists.newArrayList();
		}
		return entityMapper.selectByExample(entityExample);
	}

	private List<Integer> getPutIdsByCust(Integer custId, List<Integer> advers) {
		List<Integer> putIds = Lists.newArrayList();
		List<Integer> oids = Lists.newArrayList();
		if (custId != null || (advers != null && advers.size() > 0)) {
			OrdersExample ordersExample = new OrdersExample();
			com.iwanvi.nvwa.dao.model.OrdersExample.Criteria criteria = ordersExample.createCriteria();
			if (custId != null) {
				criteria.andCustIdEqualTo(custId);
			} else {
				criteria.andCustIdIn(advers);
			}
			List<Orders> orders = ordersMapper.selectByExample(ordersExample);
			if (orders.size() > 0) {
				oids = Lists.transform(orders, Orders::getId);
			} else {
				return Lists.newArrayList();
			}
		}
		OrderPutExample orderPutExample = new OrderPutExample();
		com.iwanvi.nvwa.dao.model.OrderPutExample.Criteria criteria = orderPutExample.createCriteria();
		criteria.andPutTypeEqualTo(Constants.PUT_TYPE_ORDER).andPutStateNotEqualTo(Constants.STATE_INVALID);
		if (oids.size() > 0) {
			criteria.andOidIn(oids);
		}
		List<OrderPut> orderPuts = orderPutMapper.selectByExample(orderPutExample);
		if (orderPuts.size() > 0) {
			putIds = Lists.transform(orderPuts, OrderPut::getId);
		}
		return putIds;
	}

	@Override
	@Transactional
	public void add(Entity entity) throws Exception {
		entity.setRunState(Constants.STATE_VALID);
		entity.setCreateTime(new Date());
		// 若是订单投放则需关联pid
		Integer adPositionId;
		if (Constants.PUT_TYPE_ORDER.equals(entity.getPutType())) {
			OrderPutExample orderPutExample = new OrderPutExample();
			orderPutExample.createCriteria().andPutAllIdEqualTo(entity.getPutAllId())
					.andAdPositionEqualTo(entity.getAdPosition()).andPutStateNotEqualTo(Constants.STATE_INVALID);
			OrderPut orderPut = orderPutMapper.selectOneByExample(orderPutExample);
			if (orderPut == null || orderPut.getId() == null) {
				logger.error("entity add error beacuse put is error putAllId: {},adPosition: {}", entity.getPutAllId(),
						entity.getAdPosition());
				throw new ServiceException("关联投放异常，无对应的投放");
			}
			entity.setPid(orderPut.getId());
			adPositionId = orderPut.getAdPosition();
		} else {
			adPositionId = orderPutMapper.selectByPrimaryKey(entity.getPid()).getAdPosition();
		}
		AdPosition adPosition = adPositionService.get(adPositionId);
		// 将广告位类型放入entityType中，方便审核需要
		entity.setEntityType(adPosition.getType());
		// 验证创意的素材是否合格
		if (Constants.AD_TYPE_TEXT.equals(adPosition.getType())) {
			if (Constants.STATE_VALID.equals(adPosition.getIsSupportDynamic())) {
				if (StringUtils.isBlank(entity.getThreadTitle()) && StringUtils.isBlank(entity.getDynamicCode())) {
					throw new ServiceException("缺少素材内容：代码或标题");
				}
			} else {
				if (StringUtils.isBlank(entity.getThreadTitle())) {
					throw new ServiceException("缺少素材内容：标题");
				}
			}
		} else if (Constants.AD_TYPE_INCENTIVE.equals(adPosition.getType())) {
			if (StringUtils.isBlank(entity.getDynamicCode())) {
				throw new ServiceException("缺少素材内容:代码");
			}
		} else {
			if (!Constants.STATE_VALID.equals(adPosition.getIsSupportDynamic())) {
				// 检查创意是否缺少主图片或者视频物料
				if (StringUtils.isBlank(entity.getEntityUrl()) && StringUtils.isBlank(entity.getThreadPic1())) {
					throw new ServiceException("缺少素材内容:图片或视频，请添加");
				}
			} else {
				if (StringUtils.isBlank(entity.getEntityUrl()) && StringUtils.isBlank(entity.getThreadPic1())
						&& StringUtils.isBlank(entity.getDynamicCode())) {
					throw new ServiceException("缺少素材内容:图片或视频或代码，请添加");
				}
			}
		}
		// 抄底投放的创意免审
		if (Constants.PUT_TYPE_BOTTOM.equals(entity.getPutType())) {
			entity.setEntityState(Constants.STATE_VALID);
		} else {
			entity.setEntityState(Constants.STATE_WAIT_AUDIT);
		}
		if (StringUtils.isNotBlank(entity.getExtIds())) {
			updateExtIdFormExtIds(entity);
		}
		// 填充代码到 原系统的代码字段
		if (StringUtils.isNotBlank(entity.getDynamicCode())) {
			entity.setEntityUrl(entity.getDynamicCode());
		}
		entityMapper.insertSelective(entity);
		// 非精确投放创建adxRelation免审关联关系
		entityMapper.countByExample(null);
//			AdxRelation adxRelation = new AdxRelation();
//			Integer mediaId = getMediaByEntity(entity);
//			adxRelation.setObjId(entity.getId());
//			adxRelation.setObjType(Constants.OBJ_ENTITY);
//			adxRelation.setAdxType(mediaId);
//			adxRelation.setAuditState(Constants.STATE_VALID);
//			adxRelation.setStatus(Constants.STATE_VALID);
//			adxRelation.setCreateTime(new Date());
//			adxRelationMapper.insertSelective(adxRelation);
		if (Constants.PUT_TYPE_ORDER.equals(entity.getPutType())) {
			checkThreadPool.execute(() -> {
				try {
					auditService.auditEntity(entity);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			});
			updateRelation(entity);
		} else {
			updateRelation(entity);
			sysCrontabService.updateSysCrontabByEntity(entity.getPid(), Constants.OBJ_ORDER_PUT, Constants.OP_UPDATE);
		}
	}

	@Override
	@Transactional
	public void update(Entity entity, Entity oldEntity) throws Exception {
		if (Constants.STATE_INVALID.equals(entity.getEntityState())) {
			// 创意删除后，重置relation
			adxRelationService.restAdxRelation(entity.getId(), Constants.OBJ_ENTITY);
			updateSysCrontab(oldEntity.getPid());

		} else {
			Integer adPositionId;
			if (Constants.PUT_TYPE_ORDER.equals(entity.getPutType())) {
				adPositionId = oldEntity.getAdPosition();
			} else {
				adPositionId = orderPutMapper.selectByPrimaryKey(entity.getPid()).getAdPosition();
			}
			if (entity.getPutType() != null) {
				AdPosition adPosition = adPositionService.get(adPositionId);
				// 将广告位类型放入entityType中，方便审核需要
				entity.setEntityType(adPosition.getType());
				// 验证创意的素材是否合格
				if (Constants.AD_TYPE_TEXT.equals(adPosition.getType())) {
					if (Constants.STATE_VALID.equals(adPosition.getIsSupportDynamic())) {
						if (StringUtils.isBlank(entity.getThreadTitle())
								&& StringUtils.isBlank(entity.getDynamicCode())) {
							throw new ServiceException("缺少素材内容：代码或标题");
						}
					} else {
						if (StringUtils.isBlank(entity.getThreadTitle())) {
							throw new ServiceException("缺少素材内容：标题");
						}
					}
				} else if (Constants.AD_TYPE_INCENTIVE.equals(adPosition.getType())) {
					if (StringUtils.isBlank(entity.getDynamicCode())) {
						throw new ServiceException("缺少素材内容:代码");
					}
				} else {
					if (!Constants.STATE_VALID.equals(adPosition.getIsSupportDynamic())) {
						// 检查创意是否缺少主图片或者视频物料
						if (StringUtils.isBlank(entity.getEntityUrl()) && StringUtils.isBlank(entity.getThreadPic1())) {
							throw new ServiceException("缺少素材内容:图片或视频，请添加");
						}
					} else {
						if (StringUtils.isBlank(entity.getEntityUrl()) && StringUtils.isBlank(entity.getThreadPic1())
								&& StringUtils.isBlank(entity.getDynamicCode())) {
							throw new ServiceException("缺少素材内容:图片或视频或代码，请添加");
						}
					}
				}
			}
//			if (!Constants.PUT_TYPE_ACCURATE.equals(entity.getPutType())) {
//				entity.setEntityState(Constants.STATE_VALID);
//				AdxRelation adxRelation = new AdxRelation();
//				adxRelation.setAuditState(Constants.STATE_VALID);
//				adxRelation.setAuditComments(StringUtils.EMPTY);
//				adxRelation.setStatus(Constants.STATE_VALID);
//				adxRelation.setUpdateTime(new Date());
//				AdxRelationExample adxRelationExample = new AdxRelationExample();
//				adxRelationExample.createCriteria().andObjIdEqualTo(entity.getId())
//						.andObjTypeEqualTo(Constants.OBJ_ENTITY).andAdxTypeEqualTo(Constants.FLOW_MEDIA_AIKA);
//				adxRelationMapper.updateByExampleSelective(adxRelation, adxRelationExample);
//				updateSysCrontab(entity.getPid());
//			} else {
			compareEntity2Update(entity, oldEntity);
//			}
		}
		if (StringUtils.isNotBlank(entity.getExtIds())) {
			updateExtIdFormExtIds(entity);
		}
		entity.setUpdateTime(new Date());
		// 填充代码到 原系统的代码字段
		if (StringUtils.isNotBlank(entity.getDynamicCode())) {
			entity.setEntityUrl(entity.getDynamicCode());
		}
		entityMapper.updateByPrimaryKeySelective(entity);
	}

	private void compareEntity2Update(Entity entity, Entity oldEntity) throws Exception {
		boolean isUpdateState = false;
		boolean isUpdateCrontab = false;
		boolean isCheck = false;
		if (entity.getRunState() != null && !entity.getRunState().equals(oldEntity.getRunState())) {
			isUpdateCrontab = true;
		}
		if (StringUtils.isNotBlank(entity.getIndustry()) && !entity.getIndustry().equals(oldEntity.getIndustry())) {
			isUpdateState = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getEntityUrl())
				&& !entity.getEntityUrl().equals(oldEntity.getEntityUrl())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadTitle())
				&& !entity.getThreadTitle().equals(oldEntity.getThreadTitle())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadContent())
				&& !entity.getThreadContent().equals(oldEntity.getThreadContent())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getUserPortrait())
				&& !entity.getUserPortrait().equals(oldEntity.getUserPortrait())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic1())
				&& !entity.getThreadPic1().equals(oldEntity.getThreadPic1())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic2())
				&& !entity.getThreadPic2().equals(oldEntity.getThreadPic2())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic3())
				&& !entity.getThreadPic3().equals(oldEntity.getThreadPic3())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic4())
				&& !entity.getThreadPic4().equals(oldEntity.getThreadPic4())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic5())
				&& !entity.getThreadPic5().equals(oldEntity.getThreadPic5())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic6())
				&& !entity.getThreadPic6().equals(oldEntity.getThreadPic6())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic7())
				&& !entity.getThreadPic7().equals(oldEntity.getThreadPic7())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic8())
				&& !entity.getThreadPic8().equals(oldEntity.getThreadPic8())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (!isCheck && StringUtils.isNotBlank(entity.getThreadPic9())
				&& !entity.getThreadPic9().equals(oldEntity.getThreadPic9())) {
			isUpdateState = true;
			isUpdateCrontab = true;
			isCheck = true;
		}
		if (isUpdateState) {
			logger.info("entity:{}", JsonUtils.TO_JSON(entity));
			logger.info("oldEntity:{}", JsonUtils.TO_JSON(oldEntity));
//			entity.setEntityState(Constants.STATE_VALID);
//			entity.setAuditComments(StringUtils.EMPTY);
			OrderPut orderPut = orderPutMapper.selectByPrimaryKey(entity.getPid());
			updateRelation(entity);
			if (Constants.PUT_TYPE_ORDER.equals(orderPut.getPutType())) {
//				adxRelationService.deleteAdxRelation(entity.getId(), Constants.OBJ_ENTITY);\
				checkThreadPool.execute(() -> {
					try {
						auditService.auditEntity(entity);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				});
//				entity.setEntityState(Constants.STATE_WAIT_AUDIT);
			}
//			updateSysCrontab(oldEntity.getPid());
		}
		if (isUpdateCrontab) {
			updateSysCrontab(oldEntity.getPid());
			logger.info("sysCrontab success!" + oldEntity.getPid());
		} else {
			logger.info("sysCrontab not need");
		}
	}

	@Override
	public Entity info(Integer id) {
		Entity entity = entityMapper.selectByPrimaryKey(id);
		if (entity.getPid() != null) {
			Integer position;
			OrderPut orderPut = orderPutMapper.selectByPrimaryKey(entity.getPid());
			if (Constants.PUT_TYPE_ORDER.equals(orderPut.getPutType())) {
				position = entity.getAdPosition();
				entity.setOrderId(orderPut.getOid());
				Orders orders = ordersMapper.selectByPrimaryKey(orderPut.getOid());
				entity.setOrderName(orders.getName());
			} else {
				position = orderPut.getAdPosition();
			}
			fullEntityToModule(entity, position);
		}
		return entity;
	}

	@Override
	public boolean entityNameIsExist(Entity entity) {
		EntityExample entityExample = new EntityExample();
		Criteria criteria = entityExample.createCriteria();
		criteria.andEntityNameEqualTo(entity.getEntityName()).andEntityStateNotEqualTo(Constants.STATE_INVALID);
		long count = entityMapper.countByExample(entityExample);
		return count > 0;
	}

	@Override
	public Entity auditInfo(Integer id) {
		Entity entity = entityMapper.selectByPrimaryKey(id);
		if (entity != null) {
			OrderPut orderPut = orderPutMapper.selectByPrimaryKey(entity.getPid());
			fullEntityToModule(entity, orderPut.getAdPosition());
			fullEntity(entity);
		}
		return entity;
	}

	private void fullEntityToModule(Entity entity, Integer positionId) {
		// 获取关联投放的广告位所拥有的模板
		List<TemplateModuleRelation> moduleRelations = adPositionService.getRelationModulesByAdpositionId(positionId, entity.getTemplateId());
		// 判断审核详情页是否展示动态代码组件
		boolean isExistDynamic = false;
		for (TemplateModuleRelation templateModuleRelation : moduleRelations) {
			if (StringUtils.isNotBlank(entity.getDynamicCode())
					&& Constants.INTEGER_4.equals(templateModuleRelation.getModuleType())) {
				isExistDynamic = true;
			}
		}
		// 通过模板填充数据值
		for (int i = 0; i < moduleRelations.size(); i++) {
			TemplateModuleRelation moduleRelation = moduleRelations.get(i);
			if (isExistDynamic && !Constants.INTEGER_4.equals(moduleRelation.getModuleType())) {
				moduleRelations.remove(i);
				continue;
			} else if (!isExistDynamic && Constants.INTEGER_4.equals(moduleRelation.getModuleType())) {
				moduleRelations.remove(i);
				continue;
			}
			fillEntityByModule(moduleRelation, entity);
		}
		entity.setModuleRelations(moduleRelations);
	}

	private void fullEntity(Entity entity) {
		OrderPutAll orderPutAll = orderPutAllMapper.selectByPrimaryKey(entity.getPutAllId());
		OrderPut orderPut = orderPutMapper.selectByPrimaryKey(entity.getPid());
//		if (entity.getIndustry() != null) {
//			Dictionary industry = dictionaryMapper.selectByPrimaryKey(entity.getIndustry());
//			entity.setIndustryName(industry.getDicValue());
//		}
		if (orderPut.getAdPosition() != null) {
			entity.setPosName(adPositionService.get(orderPut.getAdPosition()).getName());
		}
		if (orderPut.getOid() != null) {
			Orders orders = ordersMapper.selectByPrimaryKey(orderPut.getOid());
			entity.setAdverName(companyMapper.selectByPrimaryKey(orders.getCustId()).getFullName());
			entity.setOrderName(orders.getName());
		}
		if (StringUtils.isNotBlank(orderPut.getAppId())) {
			entity.setAppName(appsMapper.selectByPrimaryKey(Integer.valueOf(orderPut.getDxApp())).getAppName());
		}
		if (StringUtils.isNotBlank(entity.getIndustry())) {
			String industryId = entity.getIndustry().contains(Constants.SIGN_COMMA)
					? entity.getIndustry().substring(entity.getIndustry().lastIndexOf(Constants.SIGN_COMMA) + 1)
					: entity.getIndustry();
			Industry industry = industryMapper.selectByPrimaryKey(Integer.valueOf(industryId));
			entity.setIndustryName(industry.getName());
		}
		entity.setPutName(orderPutAll.getPutName());
		entity.setLandUrl(orderPut.getLandUrl());
		entity.setTimeInterval(orderPut.getTimeInterval());
		entity.setBeginTime(orderPut.getBeginTime());
		entity.setEndTime(orderPut.getEndTime());
	}

	/**
	 * 转换组件的key填充对象
	 * 
	 * @param module
	 * @param entity
	 */
	private void fillEntityByModule(TemplateModuleRelation moduleRelations, Entity entity) {
		String getKey = moduleRelations.getModuleKey();
//		if (Constants.MODULE_TYPE_CODE.equals(moduleRelations.getmId())) {
//			if (moduleRelations.gettId() != null) {
//				Template template = templateMapper.selectByPrimaryKey(moduleRelations.gettId());
//				if (Constants.AD_TYPE_INCENTIVE.equals(template.getType())) {
//					entity.setDynamicCode(moduleRelations.getCode());
//				}
//			}
//		} else {
		if (getKey.contains(Constants.SIGN_UNDERLINE)) {
			Integer charIndex = getKey.indexOf(Constants.SIGN_UNDERLINE);
			Character lowerChar = getKey.charAt(charIndex + 1);
			Character upChar = Character.toUpperCase(lowerChar);
			getKey = getKey.replaceFirst(StringUtils.concat(Constants.SIGN_UNDERLINE, lowerChar), upChar.toString());
		}
		Character firstChar = getKey.charAt(Constants.INTEGER_0);
		getKey = getKey.replaceFirst(firstChar.toString(), ((Character) Character.toUpperCase(firstChar)).toString());
		getKey = StringUtils.concat("get", getKey);
		Class entityClass = entity.getClass();
		try {
			Method method = entityClass.getMethod(getKey);
			String value = (String) method.invoke(entity);
			moduleRelations.setModuleValue(value);
		} catch (Exception e) {
			logger.error("entityService fillEntityByModule error", e);
			e.printStackTrace();
		}
//		}
	}

	@Override
	@Transactional
	public void auditAdx(AdxRelation adxRelation) {
//		Integer entityId = adxRelation.getObjId();
//		Integer adxType = adxRelation.getAdxType();
//		Integer industry = adxRelation.getIndustry();
//		updateRelation(adxRelation);
		// 下面发送一个审核创意的消息
	}

	private void updateRelation(Entity entity) {
		if (!Constants.STATE_FAILURE_AUDIT_BLACKLIST.equals(entity.getEntityState())) {
			AdxRelationExample adxRelationExample = new AdxRelationExample();
			adxRelationExample.createCriteria().andObjIdEqualTo(entity.getId())
					.andAdxTypeEqualTo(Constants.FLOW_MEDIA_AIKA).andObjTypeEqualTo(Constants.OBJ_ENTITY)
					.andStatusEqualTo(Constants.STATE_VALID);
			List<AdxRelation> relationList = adxRelationMapper.selectByExample(adxRelationExample);
			Integer industry = null;
			if (StringUtils.isNotBlank(entity.getIndustry())) {
				String industryStr = entity.getIndustry();
				if (industryStr.contains(Constants.SIGN_COMMA)) {
					industry = Integer
							.valueOf(industryStr.substring(industryStr.lastIndexOf(Constants.SIGN_COMMA) + 1));
				} else {
					industry = Integer.valueOf(industryStr);
				}
			}
			if (relationList.size() > 0) {
				AdxRelation adxRelation = relationList.get(0);
				adxRelation.setAuditState(Constants.STATE_VALID);
				adxRelation.setStatus(Constants.STATE_VALID);
				adxRelation.setUpdateTime(new Date());
				adxRelation.setIndustry(industry);
				adxRelation.setAuditComments("");
				adxRelationMapper.updateByPrimaryKeySelective(adxRelation);
			} else {
				AdxRelation adxRelation = new AdxRelation();
				adxRelation.setAdxType(Constants.FLOW_MEDIA_AIKA);
				adxRelation.setObjId(entity.getId());
				adxRelation.setObjType(Constants.OBJ_ENTITY);
				adxRelation.setAuditState(Constants.STATE_VALID);
				adxRelation.setStatus(Constants.STATE_VALID);
				adxRelation.setCreateTime(new Date());
				adxRelation.setIndustry(industry);
				adxRelationMapper.insertSelective(adxRelation);
			}
		}
	}

	/**
	 * 订单投放(cpt)创意默认免审，故只获取计划投放(rtb)创意
	 */
	@Override
	public Page<Entity> auditPage(EntityQuery entityQuery) {
		Page<Entity> page;
		List<Integer> putIds = Lists.newArrayList();
		putIds = getPutIdsByCust(entityQuery.getCustId(), null);
		if (putIds == null || putIds.size() == 0) {
			return new Page<>(Constants.INTEGER_0);
		}
		EntityExample entityExample = entityQuery.toExample(putIds);
		Long count = entityMapper.countByExample(entityExample);
		if (count > 0) {
			page = new Page<>(count.intValue(), entityQuery.getCp(), entityQuery.getPs());
			entityExample.setOrderByClause("id desc");
			entityExample.setOffset(page.getStartPosition());
			entityExample.setRows(page.getPageSize());
			List<Entity> entities = entityMapper.selectByExample(entityExample);
			for (Entity entity : entities) {
				fullEntity(entity);
			}
			page.setData(entities);
		} else {
			page = new Page<>(count.intValue());
		}
		return page;
	}

	@Override
	public void updateEntityByPid(Entity entity, Integer pid) {
		if (entity != null) {
			EntityExample entityExample = new EntityExample();
			entityExample.createCriteria().andPidEqualTo(pid).andEntityStateNotEqualTo(Constants.STATE_INVALID);
			entity.setUpdateTime(new Date());
			entityMapper.updateByExampleSelective(entity, entityExample);
		}
	}

	@Override
	public void checkExtIdsOfDate() {
		logger.info("entity checkExtIdsOfDate start");
		EntityExample entityExample = new EntityExample();
		entityExample.createCriteria().andExtIdsIsNotNull();
		List<Entity> entities = entityMapper.selectByExample(entityExample);
		for (Entity entity : entities) {
			try {
				updateExtIdFormExtIds(entity);
				entity.setUpdateTime(new Date());
				entityMapper.updateByPrimaryKeySelective(entity);
				syslogService.addSyslog(Constants.INTEGER_0, Constants.INTEGER_0, entity, this.getClass().getName(),
						"checkExtIdsOfDate");
			} catch (Exception e) {
				logger.error("entity checkExtIdsOfDate error, entityId {}, server exception {}", entity.getId(), e);
			}
		}
		logger.info("entity checkExtIdsOfDate end");
	}

	private void updateExtIdFormExtIds(Entity entity) {
		Date nowDate = new Date();
		JSONArray jsonArray = JSONArray.parseArray(entity.getExtIds());
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String sDateStr = jsonObject.getString("startDate");
			String eDateStr = jsonObject.getString("endDate");
			Date startDate = DateUtils.getFirstSecondOfDay(DateUtils.parse(sDateStr, DateUtils.SHORT_FORMAT));
			Date endDate = DateUtils.getLastSecondOfDay(DateUtils.parse(eDateStr, DateUtils.SHORT_FORMAT));
			if (startDate.compareTo(nowDate) <= 0 && endDate.compareTo(nowDate) >= 0) {
				jsonObject.remove("startDate");
				jsonObject.remove("endDate");
				entity.setExtId(jsonObject.toJSONString());
			}
		}
	}

	private void updateSysCrontab(Integer pid) {
		int count = sysCrontabService.countCrontab(pid, Constants.OBJ_ORDER_PUT);
		if (count > 0) {
			sysCrontabService.addSysCrontab(pid, Constants.OBJ_ORDER_PUT, Constants.OP_UPDATE);
		}
	}

	@Override
	public Entity getOneByPid(Integer pid) {
		EntityExample entityExample = new EntityExample();
		entityExample.createCriteria().andPidEqualTo(pid).andEntityStateNotEqualTo(Constants.STATE_INVALID);
		List<Entity> entities = entityMapper.selectByExample(entityExample);
		if (entities != null && entities.size() > 0) {
			Entity entity = entities.get(0);
			OrderPut orderPut = orderPutMapper.selectByPrimaryKey(pid);
			List<TemplateModuleRelation> moduleRelations = adPositionService
					.getRelationModulesByAdpositionId(orderPut.getAdPosition(), entity.getTemplateId());
			for (TemplateModuleRelation moduleRelation : moduleRelations) {
				fillEntityByModule(moduleRelation, entity);
			}
			entity.setModuleRelations(moduleRelations);
			return entity;
		}
		return null;
	}

	@Override
	@Transactional
	public void updateByOrderOrBottom(Entity entity) {
		if (Constants.STATE_INVALID.equals(entity.getEntityState())) {
			// 创意删除后，重置relation
			adxRelationService.restAdxRelation(entity.getId(), Constants.OBJ_ENTITY);
			updateSysCrontab(entity.getPid());
		} else {
			entity.setEntityState(Constants.STATE_VALID);
			entity.setUpdateTime(new Date());
//			AdxRelation adxRelation = new AdxRelation();
//			adxRelation.setAuditState(Constants.STATE_VALID);
//			adxRelation.setAuditComments(StringUtils.EMPTY);
//			adxRelation.setStatus(Constants.STATE_VALID);
//			adxRelation.setUpdateTime(new Date());
//			AdxRelationExample adxRelationExample = new AdxRelationExample();
//			adxRelationExample.createCriteria().andObjIdEqualTo(entity.getId())
//					.andAdxTypeEqualTo(Constants.FLOW_MEDIA_AIKA).andAdxTypeEqualTo(Constants.FLOW_MEDIA_AIKA);
//			adxRelationMapper.updateByExampleSelective(adxRelation, adxRelationExample);
			updateSysCrontab(entity.getPid());
		}

	}

	@Override
	public Map<String, Object> checkEntity(Integer id) {
		Map<String, Object> resultMap = Maps.newHashMap();
		Entity entity = entityMapper.selectByPrimaryKey(id);
		if (entity == null) {
			throw new ServiceException("无此创意数据");
		}
		resultMap.put("entityName", entity.getEntityName());
		resultMap.put("entityState", entity.getEntityState());
		resultMap.put("entityRun", entity.getRunState());
		if (entity.getPid() == null) {
			throw new ServiceException("创意无相关关联投放");
		}
		OrderPut orderPut = orderPutMapper.selectByPrimaryKey(entity.getPid());
		if (orderPut == null) {
			throw new ServiceException("无此创意关联投放数据");
		} else {
			if (Constants.PUT_TYPE_BOTTOM.equals(orderPut.getPutType())) {
				throw new ServiceException("抄底创意不可看");
			}
		}
		OrderPutAll orderPutAll = orderPutAllMapper.selectByPrimaryKey(orderPut.getPutAllId());
		if (orderPut.getAdPosition() != null) {
			AdPosition adPosition = adPositionService.get(orderPut.getAdPosition());
			resultMap.put("positionName",
					StringUtils.concat(adPosition.getId(), Constants.SIGN_COLON, adPosition.getName()));
		}
		resultMap.put("putId", entity.getPutAllId());
		resultMap.put("putName", orderPutAll.getPutName());
		resultMap.put("putState", orderPut.getPutState());
		resultMap.put("startDate", DateUtils.format(orderPut.getBeginTime(), DateUtils.DEFAULT_FORMAT));
		resultMap.put("endDate", DateUtils.format(orderPut.getEndTime(), DateUtils.DEFAULT_FORMAT));
		resultMap.put("putRun", orderPut.getRunState());
		if (StringUtils.isBlank(orderPut.getTimeInterval())) {
			resultMap.put("timeInterval", "全时段");
			resultMap.put("timeIntervalState", Constants.STATE_VALID);
		} else {
			List<Integer> times = StringUtils.str2List4Int(orderPut.getTimeInterval(), Constants.SIGN_MINUS);
			resultMap.put("timeInterval", orderPut.getTimeInterval());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			if (times.get(0) <= hour && hour < times.get(1)) {
				resultMap.put("timeIntervalState", Constants.STATE_VALID);
			} else {
				resultMap.put("timeIntervalState", Constants.STATE_INVALID);
			}
		}
		if (!Constants.STATE_VALID.equals(orderPut.getPutState())) {
			resultMap.put("dateState", Constants.STATE_INVALID);
		} else {
			resultMap.put("dateState", Constants.STATE_VALID);
		}
		if (orderPut.getOid() == null) {
			throw new ServiceException("创意关联投放无关联订单");
		}
		Orders orders = ordersMapper.selectByPrimaryKey(orderPut.getOid());
		resultMap.put("orderId", orders.getId());
		resultMap.put("orderName", orders.getName());
		resultMap.put("orderRun", orders.getRunState());
		resultMap.put("orderState", orders.getOrdersState());
		if (orders.getCustId() == null) {
			throw new ServiceException("订单无关联广告主");
		}
		Company company = companyMapper.selectByPrimaryKey(orders.getCustId());
		resultMap.put("adverState", company.getStatus());
		resultMap.put("adverAudit", company.getAuditStatus());
		return resultMap;
	}
}

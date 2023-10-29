package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.*;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.Dictionary;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.*;
import com.iwanvi.nvwa.web.util.BitUtils;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.ExceptionUtils;
import com.iwanvi.nvwa.web.util.MatcherUtils;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.*;

@Service
public class AdPositionServiceImpl implements AdPositionService {

	private static final Logger logger = LoggerFactory.getLogger(AdPositionServiceImpl.class);

	@Autowired
	private AdPositionMapper adPositionMapper;
	@Autowired
	private AdPositionPriceService adPositionPriceService;
	@Autowired
	private FlowSourceService flowSourceService;
	@Autowired
	private DictionaryMapper dictionaryMapper;
	@Autowired
	private PositionExcludeMapper positionExcludeMapper;
	@Autowired
	private TongfaMapper tongfaMapper;
	@Autowired
	private TemplateMapper templateMapper;
	@Autowired
	private TemplateModuleRelationService templateModuleRelationService;
	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SysCrontabService sysCrontabService;
	@Autowired
	private FlowSourceMapper flowSourceMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private PutMapper putMapper;
	@Autowired
	private QuotaPutMapper quotaPutMapper;
	@Autowired
	private SdkAdCarouselMapper sdkAdCarouselMapper;
	@Autowired
	private AdPositionMappingMapper adPositionMappingMapper;
	@Autowired
	private FlowConsumerMapper flowConsumerMapper;
	@Autowired
	private PutPositionTimeMapper putPositionTimeMapper;
	@Autowired
	private AppsMapper appsMapper;
	@Autowired
	private ProhibitedDateMapper prohibitedDateMapper;
	@Autowired
	private SdkAllotmentMapper sdkAllotmentMapper;
	@Autowired
	private SdkAllotScheduleMapper sdkAllotScheduleMapper;
	@Autowired
	private AdPositionTimeMapper adPositionTimeMapper;
	@Autowired
	private TemplateModuleRelationMapper templateModuleRelationMapper;
	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private QuotaFlowMapper quotaFlowMapper;

	@Override
	@Transactional
	public void add(AdPosition adPosition) {
		if (adPosition != null) {
			if (!checkAdPositionNum(adPosition)) {
				throw new ServiceException("请输入正确的数字");
			}
			boolean success = false;
			while (!success) {
				String uuid = ShortUrlUtils.getByUUID();
				boolean existed = checkUuidIsExisted(uuid);
				if (!existed) {
					FlowSourceExample example = new FlowSourceExample();
					example.createCriteria()
							.andMediaUuidEqualTo(appsMapper.selectByPrimaryKey(adPosition.getAppId()).getMedias());
					List<FlowSource> list = flowSourceMapper.selectListByExample(example);
					if (list.size() > 0)
						adPosition.setFlowUuid(list.get(0).getMediaUuid());
					Apps apps = appsMapper.selectByPrimaryKey(adPosition.getAppId());
					adPosition.setOs(apps.getPlatform());
					adPosition.setUuid(uuid);
					adPosition.setForecastExposure(0L);
					adPosition.setForecastCtr(adPosition.getForecastCtr() / 100);
					adPosition.setTerminal(Constants.TERMINAL_TYPE_APP);
					adPosition.setStatus(Constants.STATE_VALID);
					adPosition.setUpdateTime(new Date());
					// 拼接templateId
					if (adPosition.getTemplateList() != null) {
						List<Integer> templateIds = Lists.transform(adPosition.getTemplateList(), Template::getId);
						StringBuilder sb = new StringBuilder(Constants.SIGN_COMMA);
						templateIds.forEach(id -> {
							if (id != null) {
								sb.append(id).append(Constants.SIGN_COMMA);
							}
						});
						adPosition.setTemplateId(sb.toString());
					}
					adPositionMapper.insert(adPosition);
					adPositionMapper.countByExample(null);

					/*
					 * if (adPosition.getType() == 7) {//信息流 // 往sdkAdCarousel数据
					 * SdkAdCarouselExample exampleTemp = new SdkAdCarouselExample();
					 * exampleTemp.createCriteria().andIsDefaultEqualTo(Constants.INTEGER_1)
					 * .andAdTypeEqualTo(adPosition.getType()); List<SdkAdCarousel>
					 * sdkAdCarouselList = sdkAdCarouselMapper.selectByExample(exampleTemp);
					 * SdkAdCarousel sdkAdCarousel = new SdkAdCarousel(); sdkAdCarousel.setIsDel(0);
					 * sdkAdCarousel.setAdPosition(adPosition.getId());
					 * sdkAdCarousel.setAdType(adPosition.getType()); sdkAdCarousel.setIsDefault(1);
					 * if (sdkAdCarouselList.size() > 0) {
					 * sdkAdCarousel.setChangeType(sdkAdCarouselList.get(0).getChangeType());
					 * sdkAdCarousel.setPageValue(sdkAdCarouselList.get(0).getPageValue());
					 * sdkAdCarousel.setDurationValue(sdkAdCarouselList.get(0).getDurationValue());
					 * } else { sdkAdCarousel.setChangeType(0); sdkAdCarousel.setPageValue(0);
					 * sdkAdCarousel.setDurationValue(0); }
					 * sdkAdCarouselMapper.insert(sdkAdCarousel); // ad_carousel信息写入redis String
					 * appid = appsMapper.selectByPrimaryKey(adPosition.getAppId()).getAppId();
					 * List<Map<String, Object>> respMap = sdkAdCarouselMapper.getByAppid(appid);
					 * List<Map<String, Object>> res = new ArrayList<Map<String, Object>>(); for
					 * (Map<String, Object> map : respMap) { if
					 * (!Constants.DEFULT_IDS.contains(map.get("id"))) { res.add(map); } } String
					 * adcarouselKey = StringUtils.buildString(WebConstants.KEY_REDIS_AD_CAROUSEL,
					 * appid); redisDao.set(adcarouselKey, JsonUtils.TO_JSON(res)); }
					 */
					sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_APP_POSITION, Constants.OP_ADD);
					// 广告位信息写入redis
					writeToRedis(adPosition);
					success = true;
				}
			}
		}
	}

	private boolean checkAdPositionNum(AdPosition adPosition) {
		boolean result = true;
		if (adPosition.getPicWidth() != null) {
			result = MatcherUtils.isPositiveInteger(adPosition.getPicWidth().toString()) == true ? true : false;
		}
		if (adPosition.getPicHeight() != null) {
			result = MatcherUtils.isPositiveInteger(adPosition.getPicWidth().toString()) == true ? true : false;
		}
		if (adPosition.getVideoWidth() != null) {
			result = MatcherUtils.isPositiveInteger(adPosition.getVideoWidth().toString()) == true ? true : false;
		}
		if (adPosition.getVideoHeight() != null) {
			result = MatcherUtils.isPositiveInteger(adPosition.getVideoHeight().toString()) == true ? true : false;
		}
		if (adPosition.getLocationX() != null) {
			result = MatcherUtils.isPositiveInteger(adPosition.getLocationX().toString()) == true ? true : false;
		}
		if (adPosition.getLocationY() != null) {
			result = MatcherUtils.isPositiveInteger(adPosition.getLocationY().toString()) == true ? true : false;
		}
		if (adPosition.getDuration() != null) {
			result = MatcherUtils.isPositiveInteger(adPosition.getDuration().toString()) == true ? true : false;
		}
		if (!MatcherUtils.isPositionNumber(adPosition.getForecastCtr().toString())) {
			result = false;
		}
		return result;
	}

	@Override
	public void writeToRedis(AdPosition adPosition) {
		Map<String, Object> paramMap = Maps.newHashMap();
		if (adPosition.getType().equals(Constants.AD_TYPE_PIC) || adPosition.getType().equals(Constants.AD_TYPE_SCREEN)
				|| adPosition.getType().equals(Constants.AD_TYPE_SCREEN_FIRST)) {
			paramMap.put("width", adPosition.getPicWidth());
			paramMap.put("height", adPosition.getPicHeight());
		}
		if (adPosition.getType().equals(Constants.AD_TYPE_VIDEO)) {
			paramMap.put("duration", adPosition.getDuration());
		}
		if (adPosition.getId() != null) {
			AdPositionMappingExample example = new AdPositionMappingExample();
			example.createCriteria().andAdPositionIdEqualTo(adPosition.getId());
			List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(example);
			List<Map<String, Object>> redisList = Lists.newArrayList();
			for (AdPositionMapping adPositionMapping : mappingList) {

				Map<String, Object> map = Maps.newHashMap();
				AdPosition position = adPositionMapper.selectByPrimaryKey(adPositionMapping.getAdPositionId());
				Apps apps = appsMapper.selectByPrimaryKey(position.getAppId());
				map.put("forecastCtr", adPositionMapping.getForecastCtr());
				map.put("flowConsumerId", adPositionMapping.getFlowConsumerId());
				map.put("flowConsumerUuid",
						flowConsumerMapper.selectByPrimaryKey(adPositionMapping.getFlowConsumerId()).getDspId());
				map.put("adPositionUuid", position.getUuid());
				map.put("consumerPositionId", adPositionMapping.getConsumerPositionId());
				map.put("appUuid", apps.getAppId());
				// 平台app
				Map<String, Object> appMap = JsonUtils.parse2Map(apps.getConsumerMapped());
				map.put("consumerAppId", appMap == null ? "" : appMap.get(adPositionMapping.getFlowConsumerId()));
				redisList.add(map);
			}
//			StringBuilder sb = new StringBuilder();
//			mappingList.forEach(adPositionMapping -> sb.append(adPositionMapping.getConsumerPositionId()).append(Constants.SIGN_COMMA));
//			String consumerPositionIds = sb.toString().indexOf(Constants.SIGN_COMMA) < 0 ? sb.toString() : sb.substring(0,sb.lastIndexOf(Constants.SIGN_COMMA));
			paramMap.put("mapping", redisList);
		}
		// createStyle 1:单图 2:组图 3:视频
		if (adPosition.getType().equals(Constants.AD_TYPE_VIDEO)
				|| adPosition.getType().equals(Constants.AD_TYPE_INCENTIVE)) {
			paramMap.put("creativeStyle", Constants.INTEGER_3);
		}
		if (adPosition.getType().equals(Constants.AD_TYPE_PIC)
				|| adPosition.getType().equals(Constants.AD_TYPE_SCREEN_FIRST)
				|| adPosition.getType().equals(Constants.AD_TYPE_SCREEN)) {
			paramMap.put("creativeStyle", Constants.INTEGER_1);
		}
		if (adPosition.getType().equals(Constants.AD_TYPE_NATIVE)) {
			List<String> templateIds = StringUtils.str2List(adPosition.getTemplateId(), Constants.SIGN_COMMA);
			TemplateModuleRelationExample example = new TemplateModuleRelationExample();
			for (String templateId : templateIds) {
				if (StringUtils.isNotBlank(templateId)) {
					example.createCriteria().andTIdEqualTo(Integer.parseInt(templateId))
							.andStatusEqualTo(Constants.INTEGER_1);
					break;
				}
			}
			List<TemplateModuleRelation> list = templateModuleRelationMapper.selectByExample(example);
			int creativeStyle = 1;
			for (TemplateModuleRelation templateModuleRelation : list) {
				if (templateModuleRelation.getmId() == 5) {
					paramMap.put("width", templateModuleRelation.getWidth());
					paramMap.put("height", templateModuleRelation.getHeight());
				}
				if (templateModuleRelation.getmId() > 5 && templateModuleRelation.getmId() != 8) {
					creativeStyle = 2;
					break;
				}
			}
			paramMap.put("creativeStyle", creativeStyle);
		}
		paramMap.put("creativeType", dictionaryMapper.selectByPrimaryKey(adPosition.getType()).getEnumValue());
		String positionKey = StringUtils.buildString(WebConstants.KEY_REDIS_AD_POSITION, adPosition.getUuid());
		redisDao.set(positionKey, JsonUtils.TO_JSON(paramMap));
		logger.info("write adPosition to redis success ! positionUuid:{}", adPosition.getUuid());

	}

	/**
	 * 计算广告位预估曝光值
	 */
	@Override
	public void forecastExposureTask() {
		List<AdPosition> positionList;
		try {
			AdPositionExample example = new AdPositionExample();
			example.createCriteria().andStatusEqualTo(Constants.STATE_VALID);
			positionList = adPositionMapper.selectByExample(example);
			for (AdPosition adPosition : positionList) {
				// 查询前七天的平均曝光
				Date fromDate = DateUtils.getPreDaysDate(DateUtils.getFirstSecondOfDay(new Date()), 8);
				Date toDate = DateUtils.getPreDaysDate(DateUtils.getFirstSecondOfDay(new Date()), 1);
				String fromDay = DateUtils.format(fromDate, DateUtils.SHORT_FORMAT);
				String toDay = DateUtils.format(toDate, DateUtils.SHORT_FORMAT);
				QuotaFlowExample flowExample = new QuotaFlowExample();
				flowExample.createCriteria().andCreDayBetween(Integer.parseInt(fromDay), Integer.parseInt(toDay))
						.andAdpidEqualTo(adPosition.getUuid());
				List<QuotaFlow> quotaFlowList = quotaFlowMapper.selectByExample(flowExample);
				Long sum = quotaFlowList.stream().mapToLong(QuotaFlow::getReq).sum();
				adPosition.setForecastExposure(sum / 7);
				adPositionMapper.updateByPrimaryKeySelective(adPosition);
			}
		} catch (NumberFormatException e) {
			logger.info("compute adPosition forecast exposure error! ", e);
		}
	}

	/**
	 * 在流量源广告位id前拼上流量园id 如："62_5543"
	 *
	 * @param adPosition
	 */
	/*
	 * private void convertPosId(AdPosition adPosition) { Integer flowId =
	 * flowSourceService.getFlowSourceByUUID(adPosition.getFlowUuid()).getId(); if
	 * (adPosition.getFlowPositionId().indexOf(Constants.SIGN_COMMA) < 0) { String
	 * newPosId = flowId + Constants.SIGN_UNDERLINE +
	 * adPosition.getFlowPositionId(); adPosition.setFlowPositionId(newPosId); }
	 * else { StringBuilder sb = new StringBuilder(); String[] posIds =
	 * adPosition.getFlowPositionId().split(Constants.SIGN_COMMA); for (String posId
	 * : posIds) {
	 * sb.append(flowId).append(Constants.SIGN_UNDERLINE).append(posId).append(
	 * Constants.SIGN_COMMA); }
	 * adPosition.setFlowPositionId(sb.substring(Constants.INTEGER_0,
	 * sb.lastIndexOf(Constants.SIGN_COMMA))); } }
	 */

	@Override
	@Transactional
	public void update(AdPosition adPosition) {

		if (!checkAdPositionNum(adPosition)) {
			throw new ServiceException("请输入正确的数字");
		}
		// 拼接templateId
		if (adPosition.getTemplateList() != null) {
			List<Integer> templateIds = Lists.transform(adPosition.getTemplateList(), Template::getId);
			StringBuilder sb = new StringBuilder(Constants.SIGN_COMMA);
			templateIds.forEach(id -> sb.append(id).append(Constants.SIGN_COMMA));
			adPosition.setTemplateId(sb.toString());
		}
		adPosition.setUpdateTime(new Date());
		adPosition.setUuid(adPositionMapper.selectByPrimaryKey(adPosition.getId()).getUuid());
		adPosition.setForecastCtr(adPosition.getForecastCtr() / 100);
		adPositionMapper.updateByPrimaryKeySelective(adPosition);
		sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_APP_POSITION, Constants.OP_UPDATE);
		writeToRedis(adPosition);
	}

	@Override
	@Transactional
	public void delete(AdPosition adPosition) {
		if (adPosition.getId() != null) {
			// 先将广告位为此id的底价信息置为无效
			adPositionPriceService.deleteByPosition(adPosition);
			adPositionMapper.updateByPrimaryKey(adPosition);

//			if (isSspAdPosition(adPosition)) {
//				sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_APP_POSITION, Constants.OP_UPDATE);
//			}
		}
	}

	@Override
	public void mappingSwitch(AdPosition adPosition) {
		adPosition.setUpdateTime(new Date());
		adPositionMapper.updateByPrimaryKeySelective(adPosition);
		sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_APP_POSITION, Constants.OP_UPDATE);
	}

	/**
	 * 根据流量源或者模版删除广告位， 根据流量源删除，设置adPosition的flow_uuid
	 * 根据模版删除，设置adPosition的template_id
	 *
	 * @param adPosition
	 */
	@Override
	@Transactional
	public void deleteByExample(AdPosition adPosition) {
		adPosition.setStatus(Constants.STATE_INVALID);
		adPosition.setUpdateTime(new Date());
		AdPositionExample example = new AdPositionExample();
		AdPositionExample.Criteria criteria = example.createCriteria();
		if (adPosition.getId() != null) {
			criteria.andIdEqualTo(adPosition.getId());
		}
		if (adPosition.getFlowUuid() != null) {
			criteria.andFlowUuidEqualTo(adPosition.getFlowUuid());
		}
		if (adPosition.getTemplateId() != null) {
			criteria.andTemplateIdEqualTo(adPosition.getTemplateId());
		}
		// 先删除广告位下的底价
		adPositionPriceService.deleteByPosition(adPosition);
		adPositionMapper.updateByExampleSelective(adPosition, example);
	}

	@Override
	public AdPosition get(Integer id) {
		if (id != null) {
			AdPosition adPosition = adPositionMapper.selectByPrimaryKey(id);
			AdPositionMappingExample example = new AdPositionMappingExample();
			example.createCriteria().andAdPositionIdEqualTo(id);
			List<AdPositionMapping> list = adPositionMappingMapper.selectByExample(example);
			list.forEach(adPositionMapping -> {
				adPositionMapping.setForecastCtr(
						adPositionMapping.getForecastCtr() == null ? null : adPositionMapping.getForecastCtr() * 100);
			});
			if (list.size() == 0)
				list.add(new AdPositionMapping());
			adPosition.setMappingList(list);
			if (adPosition.getForecastCtr() != null) {
				adPosition.setForecastCtr(adPosition.getForecastCtr() * 100);
			}
			adPosition.setOsName(adPosition.getOs() == 182 ? "IOS" : "Android");
			adPosition.setTerminalName(
					adPosition.getTerminal() == 22 ? "PC" : adPosition.getTerminal() == 23 ? "WAP" : "APP");
			adPosition.setAppName(appsMapper.selectByPrimaryKey(adPosition.getAppId()).getAppName());
			return adPosition;
		}
		return null;
	}

	/**
	 * 转换flowPositionId
	 */
	private AdPosition convertPositionId(AdPosition adPosition) {
		if (StringUtils.isNotBlank(adPosition.getFlowPositionId())) {
			if (adPosition.getFlowPositionId().indexOf(Constants.SIGN_COMMA) < 0) {
				adPosition.setFlowPositionId(adPosition.getFlowPositionId().split(Constants.SIGN_UNDERLINE)[1]);
			} else {
				StringBuilder sb = new StringBuilder();
				String[] posIds = adPosition.getFlowPositionId().split(Constants.SIGN_COMMA);
				for (String posId : posIds) {
					sb.append(posId.split(Constants.SIGN_UNDERLINE)[1]).append(Constants.SIGN_COMMA);
				}
				adPosition.setFlowPositionId(sb.substring(Constants.INTEGER_0, sb.lastIndexOf(Constants.SIGN_COMMA)));
			}
		}
		return adPosition;
	}

	@Override
	public List<AdPosition> selectForList(AdPosition adPosition) {
		AdPositionExample example = bindingExample(adPosition);
		return adPositionMapper.selectByExample(example);
	}

	@Override
	public Page<AdPosition> selectForPage(AdPosition adPosition) {

		Page<AdPosition> page;
		AdPositionExample example = bindingExample(adPosition);
		int count = adPositionMapper.countByExample(example);
		List<AdPosition> list = Lists.newArrayList();
		if (adPosition.getCp() != null && adPosition.getPs() != null) {
			page = new Page<AdPosition>(count, adPosition.getCp(), adPosition.getPs());
		} else {
			page = new Page<AdPosition>(count);
		}
		example.setStart(page.getStartPosition());
		example.setLimit(page.getPageSize());
		list = adPositionMapper.selectByExample(example);
		for (AdPosition position : list) {
			if (StringUtils.isNotBlank(position.getTemplateId())) {
				List<String> templateIds = StringUtils.str2List(position.getTemplateId(), Constants.SIGN_COMMA);
				List<Template> templateList = Lists.newArrayList();
				templateIds.forEach(id -> {
					if (StringUtils.isNotBlank(id)) {
						templateList.add(templateMapper.selectByPrimaryKey(Integer.valueOf(id)));
					}
				});
				position.setTemplateList(templateList);
			}
			if (position.getAppId() != null) {
				position.setAppName(appsMapper.selectByPrimaryKey(position.getAppId()).getAppName());
				position.setPositionAppName(
						StringUtils.concat(position.getName(), Constants.SIGN_MINUS, position.getAppName()));
			} else {
				position.setPositionAppName(position.getName());
			}
			if (position.getType() != null) {
				position.setTypeName(dictionaryMapper.selectByPrimaryKey(position.getType()).getDicValue());
			}

			BigDecimal b1 = new BigDecimal(Double.toString(position.getForecastCtr()));
			BigDecimal b2 = new BigDecimal(String.valueOf(100));
			position.setForecastCtr(b1.multiply(b2).doubleValue());
			AdPositionMappingExample mappingExample = new AdPositionMappingExample();
			mappingExample.createCriteria().andAdPositionIdEqualTo(position.getId());
			List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(mappingExample);
			if (mappingList.size() > 0) {
				StringBuilder mappingConsumer = new StringBuilder();
				mappingList.forEach(adPositionMapping -> mappingConsumer.append(
						flowConsumerMapper.selectByPrimaryKey(adPositionMapping.getFlowConsumerId()).getConsumerName())
						.append(Constants.SIGN_COMMA));
				position.setMappingConsumer(
						mappingConsumer.toString().substring(0, mappingConsumer.lastIndexOf(Constants.SIGN_COMMA)));
			}
		}
		page.bindData(list);
		return page;
	}

	@Override
	public Page<AdPosition> selectForPageByUser(AdPosition adPosition, Integer cp, Integer ps, Integer uid) {
		Page<AdPosition> page;
		AdPositionExample example = bindingExampleByUser(adPosition, uid);
		int count = adPositionMapper.countByExample(example);
		List<AdPosition> list = Lists.newArrayList();
		if (cp != null && ps != null) {
			page = new Page<AdPosition>(count, cp, ps);
		} else {
			page = new Page<AdPosition>(count);
		}
		example.setStart(page.getStartPosition());
		example.setLimit(page.getPageSize());
		list = adPositionMapper.selectByExample(example);
		if (list.size() > 0) {
			for (AdPosition position : list) {
				if (position.getTemplateId() != null) {
//					position.setTemplateName(templateMapper.selectByPrimaryKey(position.getTemplateId()).getName());
				}
			}
		}
		/*
		 * for (AdPosition position : list) { position = convertPositionId(position); }
		 */
		page.bindData(list);
		return page;
	}

	/**
	 * 绑定example
	 */
	private AdPositionExample bindingExample(AdPosition adPosition) {
		AdPositionExample example = new AdPositionExample();
		if (adPosition != null) {
			AdPositionExample.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(adPosition.getName())) {
				criteria.andNameLike("%" + adPosition.getName() + "%");
			}
			if (adPosition.getStatus() != null) {
				criteria.andStatusEqualTo(adPosition.getStatus());
			}
			if (adPosition.getType() != null) {
				criteria.andTypeEqualTo(adPosition.getType());
			}
			if (adPosition.getTerminal() != null) {
				criteria.andTerminalEqualTo(adPosition.getTerminal());
			}
			if (adPosition.getTemplateId() != null) {
				criteria.andTemplateIdEqualTo(adPosition.getTemplateId());
			}
			if (adPosition.getMappingSwitch() != null) {
				criteria.andMappingSwitchEqualTo(adPosition.getMappingSwitch());
			}
			if (adPosition.getAppId() != null) {
				criteria.andAppIdEqualTo(adPosition.getAppId());
			}
			if (adPosition.getUuid() != null) {
				criteria.andUuidLike("%" + adPosition.getUuid() + "%");
			}
			if (StringUtils.isNotBlank(adPosition.getAppIds())) {
				List<String> appIds = StringUtils.str2List(adPosition.getAppIds(), Constants.SIGN_COMMA);
				List<Integer> appidList = Lists.transform(appIds, s -> Integer.parseInt(s));
				criteria.andAppIdIn(appidList);
			}
			if (adPosition.getFlowConsumerId() != null) {
				AdPositionMappingExample mappingExample = new AdPositionMappingExample();
				mappingExample.createCriteria().andFlowConsumerIdEqualTo(adPosition.getFlowConsumerId());
				List<AdPositionMapping> list = adPositionMappingMapper.selectByExample(mappingExample);
				if (list.size() > 0) {
					criteria.andIdIn(Lists.transform(list, AdPositionMapping::getAdPositionId));
				} else {
					criteria.andIdEqualTo(Integer.MAX_VALUE);
				}
			}
			if (StringUtils.isNotBlank(adPosition.getConsumerPositionId())) {
				AdPositionMappingExample mappingExample = new AdPositionMappingExample();
				mappingExample.createCriteria().andConsumerPositionIdEqualTo(adPosition.getConsumerPositionId());
				List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(mappingExample);
				if (mappingList.size() > 0) {
					criteria.andIdIn(Lists.transform(mappingList, AdPositionMapping::getAdPositionId));
				}
			}
			if (StringUtils.isNotBlank(adPosition.getConsumerPositionName())) {
				AdPositionMappingExample mappingExample = new AdPositionMappingExample();
				mappingExample.createCriteria().andConsumerPositionNameEqualTo(adPosition.getConsumerPositionName());
				List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(mappingExample);
				if (mappingList.size() > 0) {
					criteria.andIdIn(Lists.transform(mappingList, AdPositionMapping::getAdPositionId));
				}
			}
			example.setOrderByClause("status desc,id desc");
		}
		return example;
	}

	/**
	 * 绑定example
	 */
	private AdPositionExample bindingExampleByUser(AdPosition adPosition, Integer uid) {
		FlowSourceExample flowSourceExample = new FlowSourceExample();
		if (uid != null) {
			flowSourceExample.createCriteria().andCreateUserEqualTo(uid);
		}
		List<FlowSource> flowSourceList = flowSourceMapper.selectByExample(flowSourceExample);
		List<String> flowUuids = Lists.transform(flowSourceList, FlowSource::getMediaUuid);
		AdPositionExample example = new AdPositionExample();
		if (adPosition != null) {
			AdPositionExample.Criteria criteria = example.createCriteria();
			if (!userService.isAdmin(uid) && !CollectionUtils.isEmpty(flowUuids)) {
				criteria.andFlowUuidIn(flowUuids);
			}
			if (!userService.isAdmin(uid) && CollectionUtils.isEmpty(flowUuids)) {
				throw new ControllerException("未搜索到数据");
			}
			if (StringUtils.isNotBlank(adPosition.getName())) {
				criteria.andNameLike("%" + adPosition.getName() + "%");
			}
			if (adPosition.getStatus() != null) {
				criteria.andStatusEqualTo(adPosition.getStatus());
			}
			if (StringUtils.isNotBlank(adPosition.getFlowUuid())) {
				criteria.andFlowUuidEqualTo(adPosition.getFlowUuid());
			}
			if (adPosition.getTerminal() != null) {
				criteria.andTerminalEqualTo(adPosition.getTerminal());
			}
			if (adPosition.getTemplateId() != null) {
				criteria.andTemplateIdEqualTo(adPosition.getTemplateId());
			}
			example.setOrderByClause("status desc,id desc");
		}
		return example;
	}

	/**
	 * 检查uuid是否存在
	 */
	@Override
	public boolean checkUuidIsExisted(String uuid) {
		boolean isExisted = false;
		if (uuid != null) {
			AdPositionExample example = new AdPositionExample();
			example.createCriteria().andUuidEqualTo(uuid);
			int count = adPositionMapper.countByExample(example);
			if (count > 0) {
				isExisted = true;
			}
		}
		return isExisted;
	}

	@Override
	public List<AdPosition> getCollectionList(Integer platForm, String mediaUuids) {
		String[] mUuids = mediaUuids.split(Constants.SIGN_COMMA);
		Set<AdPosition> collectionSet = Sets.newHashSet();
		for (String uuid : mUuids) {
			if (StringUtils.isNotBlank(uuid)) {
				AdPositionExample example = new AdPositionExample();
				AdPositionExample.Criteria criteria = example.createCriteria();
				criteria.andStatusEqualTo(Constants.STATE_VALID);
				criteria.andTerminalEqualTo(158);// 操作系统类型写死
				criteria.andFlowUuidLike("%" + uuid + "%");
				List<AdPosition> collections = adPositionMapper.selectByExample(example);
				collectionSet.addAll(collections);
			}
		}
		List<AdPosition> resultList = Lists.newArrayList();
		resultList.addAll(collectionSet);
		Collections.sort(resultList, new Comparator<AdPosition>() {
			@Override
			public int compare(AdPosition o1, AdPosition o2) {
				if (o1.getId() > o2.getId()) {
					return -1;
				}
				return 1;
			}
		});
		return resultList;
	}

	@Override
	public void addPageAttribute(String name, Integer type, Integer superiorId) {
		if (StringUtils.isNotBlank(name) && type != null) {

			Dictionary dictionary = new Dictionary();
			dictionary.setDicValue(name);
			dictionary.setDicType(Constants.INTEGER_1); // 一级菜单
			dictionary.setStatus(Constants.STATE_VALID);
			if (Constants.INTEGER_1.equals(type)) {
				dictionary.setDicGroup(WebConstants.POSITIO_NCHANNEL);
			} else if (Constants.INTEGER_3.equals(type)) {
				dictionary.setDicGroup(WebConstants.CAR_LEVEL);
			} else if (Constants.INTEGER_5.equals(type)) {
				dictionary.setDicGroup(WebConstants.PRICE_RANGE);
			} else {
				dictionary.setDicGroup(superiorId);
				dictionary.setDicType(Constants.INTEGER_2); // 设置为二级菜单
			}
			dictionaryMapper.insert(dictionary);
		}
	}

	@Override
	public boolean checkAttributeExist(String name, Integer type) {
		boolean isExists = false;
		if (StringUtils.isNotBlank(name)) {
			DictionaryExample example = new DictionaryExample();
			DictionaryExample.Criteria criteria = example.createCriteria();

			criteria.andDicValueEqualTo(name);
			if (Constants.INTEGER_2.equals(type)) {
				criteria.andDicGroupEqualTo(WebConstants.POSITIO_NCHANNEL);
			}
			if (Constants.INTEGER_4.equals(type)) {
				criteria.andDicGroupEqualTo(WebConstants.CAR_LEVEL);
			}
			List<Dictionary> list = dictionaryMapper.selectByExample(example);
			if (list != null) {
				if (!(list.size() == 1 && list.get(0).getDicValue().equals(name)) && list.size() != 0) {
					isExists = true;
				}
			}
		}
		return isExists;
	}

	/**
	 * 查看广告位名称是否存在
	 */
	@Override
	public boolean checkPositionExist(AdPosition adPosition) {
		boolean isExists = false;
		AdPositionExample example = new AdPositionExample();
		example.createCriteria().andNameEqualTo(adPosition.getName()).andAppIdEqualTo(adPosition.getAppId());
		List<AdPosition> list = adPositionMapper.selectByExample(example);
		if (list != null) {
			if (!(list.size() == 1 && list.get(0).getId().equals(adPosition.getId())) && list.size() != 0) {
				isExists = true;
			}
		}
		return isExists;
	}

	/**
	 * 获得互斥位置
	 */
	@Override
	public Map<String, List> getExcludeList(Integer id, Integer type) {
		Map<String, List> respMap = Maps.newHashMap();
		// 广告位的互斥位
		if (Constants.INTEGER_1.equals(type)) {
			// 互斥的广告位
			List<AdPosition> excludePosition = Lists.newArrayList();
			// 互斥的通发组合
			List<Tongfa> exculdeTongfa = Lists.newArrayList();
			PositionExcludeExample example = new PositionExcludeExample();
			example.createCriteria().andPositionIdEqualTo(id).andStatusEqualTo(Constants.STATE_VALID);
			List<PositionExclude> excludeList = positionExcludeMapper.selectByExample(example);
			for (PositionExclude positionExclude : excludeList) {
				Integer pid = positionExclude.getExcludePosition();
				if (pid != null) {
					excludePosition.add(adPositionMapper.selectByPrimaryKey(pid));
				} else {
					exculdeTongfa.add(tongfaMapper.selectByPrimaryKey(positionExclude.getExcludeTongfa()));
				}
			}
			// 反向查询
			PositionExcludeExample example2 = new PositionExcludeExample();
			example.createCriteria().andExcludePositionEqualTo(id);
			List<PositionExclude> excludeList2 = positionExcludeMapper.selectByExample(example2);
			for (PositionExclude positionExclude : excludeList2) {
				Integer pid = positionExclude.getPositionId();
				if (pid != null) {
					excludePosition.add(adPositionMapper.selectByPrimaryKey(pid));
				} else {
					exculdeTongfa.add(tongfaMapper.selectByPrimaryKey(positionExclude.getTongfaId()));
				}
			}
			List<Integer> pidList = FluentIterable.from(excludePosition).transform(AdPosition::getId).toList();

			List<Integer> tidList = FluentIterable.from(exculdeTongfa).transform(Tongfa::getId).toList();

			AdPositionExample positionExample = new AdPositionExample();
			positionExample.createCriteria().andIdIn(pidList);
			// 可选的广告位
			List<AdPosition> optionalPosition = adPositionMapper.selectByExample(positionExample);

			TongfaExample tongfaExample = new TongfaExample();
			tongfaExample.createCriteria().andIdIn(tidList);
			// 可选的通发组合
			List<Tongfa> optionalTongfa = tongfaMapper.selectByExample(tongfaExample);

			respMap.put("excludePosition", excludePosition);
			respMap.put("excludeTongfa", exculdeTongfa);
			respMap.put("optionalPosition", optionalPosition);
			respMap.put("optionalTongfa", optionalTongfa);
		} else {
			// 互斥的广告位
			List<AdPosition> excludePosition = Lists.newArrayList();
			// 互斥的通发组合
			List<Tongfa> exculdeTongfa = Lists.newArrayList();
			PositionExcludeExample example = new PositionExcludeExample();
			example.createCriteria().andTongfaIdEqualTo(id).andStatusEqualTo(Constants.STATE_VALID);
			List<PositionExclude> excludeList = positionExcludeMapper.selectByExample(example);
			for (PositionExclude positionExclude : excludeList) {
				Integer pid = positionExclude.getExcludePosition();
				if (pid != null) {
					excludePosition.add(adPositionMapper.selectByPrimaryKey(pid));
				} else {
					exculdeTongfa.add(tongfaMapper.selectByPrimaryKey(positionExclude.getExcludeTongfa()));
				}
			}
			// 反向查询
			PositionExcludeExample example2 = new PositionExcludeExample();
			example.createCriteria().andExcludeTongfaEqualTo(id);
			List<PositionExclude> excludeList2 = positionExcludeMapper.selectByExample(example2);
			for (PositionExclude positionExclude : excludeList2) {
				Integer pid = positionExclude.getPositionId();
				if (pid != null) {
					excludePosition.add(adPositionMapper.selectByPrimaryKey(pid));
				} else {
					exculdeTongfa.add(tongfaMapper.selectByPrimaryKey(positionExclude.getTongfaId()));
				}
			}
			List<Integer> pidList = FluentIterable.from(excludePosition).transform(AdPosition::getId).toList();

			List<Integer> tidList = FluentIterable.from(exculdeTongfa).transform(Tongfa::getId).toList();

			AdPositionExample positionExample = new AdPositionExample();
			positionExample.createCriteria().andIdIn(pidList);
			// 可选的广告位
			List<AdPosition> optionalPosition = adPositionMapper.selectByExample(positionExample);

			TongfaExample tongfaExample = new TongfaExample();
			tongfaExample.createCriteria().andIdIn(tidList);
			// 可选的通发组合
			List<Tongfa> optionalTongfa = tongfaMapper.selectByExample(tongfaExample);

			respMap.put("excludePosition", excludePosition);
			respMap.put("excludeTongfa", exculdeTongfa);
			respMap.put("optionalPosition", optionalPosition);
			respMap.put("optionalTongfa", optionalTongfa);
		}
		return respMap;
	}

	@Override
	public List<AdPosition> getAiKaPosition(Integer appid, Integer status) {
		AdPositionExample example = new AdPositionExample();
		AdPositionExample.Criteria criteria = example.createCriteria();
		if (status != null) {
			criteria.andStatusEqualTo(status);
		}
		if (appid != null) {
			criteria.andAppIdEqualTo(appid);
		}
		return adPositionMapper.selectByExample(example);
	}

	@Override
	public List<Module> getModulesByAdpositionId(Integer adPositionId) {
		Map<String, Object> resultMap = getModulesMapByAdpositionId(adPositionId, null);
		return resultMap.containsKey("modules") ? (List<Module>) resultMap.get("modules") : null;
	}

	@Override
	public List<TemplateModuleRelation> getRelationModulesByAdpositionId(Integer adPositionId, Integer templateId) {
		Map<String, Object> resultMap = getModulesMapByAdpositionId(adPositionId, templateId);
		return resultMap.containsKey("templateModules")
				? (List<TemplateModuleRelation>) resultMap.get("templateModules")
				: null;
	}

	@Override
	public List<TemplateModuleRelation> getRelationModulesByTemplateId(Integer templateId) {
		Map<String, Object> resultMap = Maps.newHashMap();
		List<TemplateModuleRelation> copyModuleRelations = Lists.newArrayList();
		List<Module> moduleList = Lists.newArrayList();
		getModulesMapByTemplateId(copyModuleRelations, moduleList, templateId);
		resultMap.put("templateModules", copyModuleRelations);
		resultMap.put("modules", moduleList);
		return resultMap.containsKey("templateModules")
				? (List<TemplateModuleRelation>) resultMap.get("templateModules")
				: null;
	}

	@Override
	public Map<String, Object> getModulesMapByAdpositionId(Integer adPositionId, Integer templateId) {
		Map<String, Object> resultMap = Maps.newHashMap();
		List<TemplateModuleRelation> copyModuleRelations = Lists.newArrayList();
		List<Module> moduleList = Lists.newArrayList();
		AdPosition position = adPositionMapper.selectByPrimaryKey(adPositionId);
		Module dynamicModule = null;
		if (Constants.STATE_VALID.equals(position.getIsSupportDynamic())) {
			dynamicModule = moduleMapper.selectByPrimaryKey(Constants.MODULE_TYPE_CODE);
		}
		if (Constants.AD_TYPE_NATIVE.equals(position.getType())
				|| Constants.AD_TYPE_INCENTIVE.equals(position.getType())) {
//			String templateStr = position.getTemplateId();
//			if (position.getTemplateId().startsWith(Constants.SIGN_COMMA)
//					&& position.getTemplateId().endsWith(Constants.SIGN_COMMA)) {
//				templateStr = position.getTemplateId().substring(Constants.INTEGER_1, templateStr.length() - 1);
//			}
			getModulesMapByTemplateId(copyModuleRelations, moduleList, templateId);
		} else {
			ModuleExample moduleExample = new ModuleExample();
			moduleExample.createCriteria()
					.andDicIdLike(StringUtils.concat(Constants.SIGN_PERCENT,
							StringUtils.concat(Constants.SIGN_COMMA, position.getType(), Constants.SIGN_COMMA),
							Constants.SIGN_PERCENT));
			moduleList = moduleMapper.selectByExample(moduleExample);
			for (Module module : moduleList) {
				TemplateModuleRelation moduleRelation = new TemplateModuleRelation();
				String placeHolderStr = "";
				if (Constants.INTEGER_1.equals(module.getType())) {
					placeHolderStr = "字数不得超过" + position.getTitle() + "字";
					moduleRelation.setWordLimit(position.getTitle());
				} else if (Constants.INTEGER_2.equals(module.getType())) {
					placeHolderStr = position.getPicWidth() + "x" + position.getPicHeight();
					moduleRelation.setWidth(position.getPicWidth());
					moduleRelation.setHeight(position.getPicHeight());
				} else if (Constants.INTEGER_3.equals(module.getType())) {
					placeHolderStr = "时长不得超过" + position.getDuration() + "秒";
					moduleRelation.setWidth(position.getVideoWidth());
					moduleRelation.setHeight(position.getVideoHeight());
					moduleRelation.setDuration(position.getDuration());
				}
				moduleRelation.setmId(module.getId());
				moduleRelation.setPlaceHolderStr(placeHolderStr);
				moduleRelation.setModuleType(module.getType());
				moduleRelation.setModuleName(module.getName());
				moduleRelation.setModuleKey(module.getModuleKey());
				copyModuleRelations.add(moduleRelation);
			}
		}
//		if (!Constants.AD_TYPE_INCENTIVE.equals(position.getType())) {
		if (dynamicModule != null) {
			TemplateModuleRelation moduleRelation = new TemplateModuleRelation();
			moduleRelation.setmId(dynamicModule.getId());
			moduleRelation.setModuleType(dynamicModule.getType());
			moduleRelation.setModuleName(dynamicModule.getName());
			moduleRelation.setModuleKey(dynamicModule.getModuleKey());
			copyModuleRelations.add(moduleRelation);
			moduleList.add(dynamicModule);
		}
//		}
		resultMap.put("templateModules", copyModuleRelations);
		resultMap.put("modules", moduleList);
		return resultMap;
	}

	private void getModulesMapByTemplateId(List<TemplateModuleRelation> copyModuleRelations, List<Module> moduleList,
			Integer templateId) {
		List<TemplateModuleRelation> moduleRelations = templateModuleRelationService.get(templateId);
		if (moduleRelations != null && moduleRelations.size() > 0) {
			Map<Integer, TemplateModuleRelation> relationMap = Maps.newHashMap();
			List<Integer> moduleIds = Lists.newArrayList();
			for (TemplateModuleRelation templateModuleRelation : moduleRelations) {
				relationMap.put(templateModuleRelation.getmId(), templateModuleRelation);
				moduleIds.add(templateModuleRelation.getmId());
			}
			// List<Integer> moduleIds = Lists.transform(moduleRelations,
			// (TemplateModuleRelation template) -> (template.getmId()));
			ModuleExample moduleExample = new ModuleExample();
			moduleExample.createCriteria().andIdIn(moduleIds);
			moduleList = moduleMapper.selectByExample(moduleExample);
			for (Module module : moduleList) {
				String placeHolderStr = "";
				TemplateModuleRelation moduleRelation = relationMap.get(module.getId());
				if (Constants.INTEGER_1.equals(module.getType())) {
					placeHolderStr = "字数不得超过" + moduleRelation.getWordLimit() + "字";
				} else if (Constants.INTEGER_2.equals(module.getType())) {
					placeHolderStr = moduleRelation.getWidth() + "x" + moduleRelation.getHeight();
				} else if (Constants.INTEGER_3.equals(module.getType())) {
//					moduleRelation.setModuleValue(moduleRelation.getCode());
					placeHolderStr = "时长不得超过" + moduleRelation.getDuration() + "秒";
					moduleRelation.setWidth(moduleRelation.getWidth());
					moduleRelation.setHeight(moduleRelation.getHeight());
					moduleRelation.setDuration(moduleRelation.getDuration());
				}
				moduleRelation.setPlaceHolderStr(placeHolderStr);
				moduleRelation.setModuleType(module.getType());
				moduleRelation.setModuleName(module.getName());
				moduleRelation.setModuleKey(module.getModuleKey());
				copyModuleRelations.add(moduleRelation);
			}
		}

	}

	@Override
	public boolean isSspAdPosition(AdPosition adPosition) {
		if (adPosition == null || StringUtils.isBlank(adPosition.getFlowUuid()))
			return false;

		FlowSource media = flowSourceService.getFlowSourceByUUID(adPosition.getFlowUuid());
		if (media == null) {
			ExceptionUtils.throwServiceException(
					String.format("媒体{}不存在，媒体uuid: {}", adPosition.getFlowName(), adPosition.getFlowUuid()));
		}

		// 如果属于爱卡流量源或者媒体类型为ssp的话，此广告位为ssp广告位
		if (Constants.FLOW_MEDIA_AIKA == media.getId() || media.getJoinType() == Constants.SSP_JOINTYPE) {
			return true;
		}
		return false;
	}

	@Override
	public Map<Integer, Map<String, Object>> stock(List<Map<String, Object>> positionList, Integer type) {
		Map<Integer, Map<String, Object>> resultMap = Maps.newHashMap();
		for (Map<String, Object> map : positionList) {
			if (!map.containsKey("id")) {
				throw new ServiceException("缺少关键参数：广告位id");
			}
			if (!map.containsKey("time")) {
				throw new ServiceException("缺少关键参数：排期时间");
			}
			Integer adId = (Integer) map.get("id");
			String timeStr = (String) map.get("time");
			Map<String, Object> adPositionMockMap = Maps.newHashMap();
			resultMap.put(adId, adPositionMockMap);
			List<String> timeList = StringUtils.str2List(timeStr, Constants.SIGN_COMMA);
			for (String time : timeList) {
				List<Map<String, Object>> adPositionMockList = Lists.newArrayList();
				adPositionMockMap.put(time, adPositionMockList);
				// 判断月份是否属于当月
				String nowYearMonth = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT_MONTH);
				Date checkYM = nowYearMonth.equals(time) ? new Date()
						: DateUtils.parse(time, DateUtils.SHORT_FORMAT_MONTH);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(checkYM);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				// 获取当月的最大天数
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.roll(Calendar.DAY_OF_MONTH, -1);
				int maxDay = calendar.get(Calendar.DATE);
				// 遍历从今日到最大天数
				for (int i = day; i <= maxDay; i++) {
					Map<String, Object> stockMap = Maps.newHashMap();
					calendar.set(Calendar.DAY_OF_MONTH, i);
					Date date = DateUtils.getFirstSecondOfDay(calendar.getTime());
					String resultDate = DateUtils.format(date, "yyyy-M-d");
					String normalDate = DateUtils.format(date, DateUtils.SHORT_FORMAT);
					Long stock = 0l;
					Long allCpm = 0l;
					// 1. 判定是否是特殊日期 若是则库存为0
					ProhibitedDateExample prohibitedDateExample = new ProhibitedDateExample();
					prohibitedDateExample.createCriteria().andProhibitedDataEqualTo(normalDate);
					prohibitedDateMapper.selectByExample(prohibitedDateExample);
					List<ProhibitedDate> prohibitedDates = prohibitedDateMapper.selectByExample(prohibitedDateExample);
					if (prohibitedDates.size() > 0) {
						stock = 0l;
					} else {
						// 2. 若是sdk包段投放 则库存为0
						SdkAllotmentExample sdkAllotmentExample = new SdkAllotmentExample();
						sdkAllotmentExample.createCriteria().andTypeEqualTo(Constants.INTEGER_1)
								.andStatusEqualTo(Constants.STATE_VALID);
						List<SdkAllotment> sdkAllotments = sdkAllotmentMapper.selectByExample(sdkAllotmentExample);
						List<Integer> allotmentIds = Lists.transform(sdkAllotments, SdkAllotment::getId);
						String monthStr = normalDate.substring(0, 6);
						if (allotmentIds.size() > 0) {
							SdkAllotScheduleExample sdkAllotScheduleExample = new SdkAllotScheduleExample();
							sdkAllotScheduleExample.createCriteria().andMonthPeriodEqualTo(Integer.valueOf(monthStr))
									.andAdPosIdEqualTo(adId).andAllotmentIdIn(allotmentIds)
									.andStatusEqualTo(Constants.STATE_VALID);
							List<SdkAllotSchedule> sdkAllotSchedules = sdkAllotScheduleMapper
									.selectByExample(sdkAllotScheduleExample);
							boolean flag = false;
							for (SdkAllotSchedule sdkAllotSchedule : sdkAllotSchedules) {
								String periodStr;
								if (normalDate.charAt(Constants.INTEGER_6) == '0') {
									periodStr = normalDate.substring(Constants.INTEGER_7);
								} else {
									periodStr = normalDate.substring(Constants.INTEGER_6);
								}
								BitSet bitSet = BitUtils.convert(sdkAllotSchedule.getSchedulePeriod().longValue());
								if (bitSet.get(Integer.valueOf(periodStr))) {
									stock = 0l;
									stockMap.put(resultDate, stock);
									adPositionMockList.add(stockMap);
									flag = true;
									break;
								}
							}
							if (flag) {
								continue;
							}
						}
						AdPosition adPosition = adPositionMapper.selectByPrimaryKey(adId);
						// 获取广告位的前七天的平均曝光
						Long exp = new BigDecimal(adPosition.getForecastExposure())
								.divide(new BigDecimal(1000), BigDecimal.ROUND_CEILING).longValue();
						// 3. 若没有sdk包段投放 则处理sdk固定量投放
//						Long cpmAllLimits = getChargeAllLimit(sdkAllotmentExample, normalDate);
//						allCpm += cpmAllLimits;
//						if (WebConstants.SDK_PUT_TYPE.equals(type)) {
//							stock = exp - allCpm;
//							if (stock < 0) {
//								stock = 0l;
//							}
//							stockMap.put(resultDate, stock);
//							adPositionMockList.add(stockMap);
//							continue;
//						}
						// 4. 若是订单投放存在cpt类型的投放 则库存为0
						AdPositionTimeExample adPositionTimeExample = new AdPositionTimeExample();
						adPositionTimeExample.createCriteria().andAdPositionIdEqualTo(adId).andYearsEqualTo(monthStr)
								.andStateEqualTo(Constants.STATE_VALID);
						List<AdPositionTime> adPositionTimes = adPositionTimeMapper
								.selectByExample(adPositionTimeExample);
						String periodStr = normalDate.substring(6);
						boolean flag = false;
						for (AdPositionTime adPositionTime : adPositionTimes) {
							Character scheduleChar = adPositionTime.getPutTime().charAt(Integer.valueOf(periodStr) - 1);
							if (Constants.INTEGER_1.toString().equals(scheduleChar.toString())) {
								stock = 0l;
								stockMap.put(resultDate, stock);
								adPositionMockList.add(stockMap);
								flag = true;
								break;
							}
						}
						if (flag) {
							continue;
						}
						// 5. 需减去cpm、cpc的限额
						// 获取广告位的预估ctr
						BigDecimal ctr = new BigDecimal(Double.toString(adPosition.getForecastCtr() * 1000));
						// 获取符合日期的所有putPositionTime数据
						PutPositionTimeExample putPositionTimeExample = new PutPositionTimeExample();
						putPositionTimeExample.createCriteria().andPositionIdEqualTo(adId)
								.andBeginTimeLessThanOrEqualTo(date).andEndTimeGreaterThan(date)
								.andCostTypeNotEqualTo(Constants.COST_TYPE_CPT);
						List<PutPositionTime> putPositionTimes = putPositionTimeMapper
								.selectByExample(putPositionTimeExample);
						// 遍历数据 计算投放的总cpm
						for (PutPositionTime putPositionTime : putPositionTimes) {
							if (Constants.COST_TYPE_ORDER_CPM.equals(putPositionTime.getCostType())) {
								allCpm += putPositionTime.getPutLimit().longValue();
							} else if (Constants.COST_TYPE_ORDER_CPC.equals(putPositionTime.getCostType())) {
								allCpm += new BigDecimal(putPositionTime.getPutLimit())
										.divide(ctr, BigDecimal.ROUND_CEILING).longValue();
							}
						}
						stock = exp - allCpm;
						if (stock < 0) {
							stock = 0l;
						}
					}
					stockMap.put(resultDate, stock);
					adPositionMockList.add(stockMap);
				}
			}
		}
		return resultMap;
	}

	@Override
	public boolean modifiable(Integer id) {
		EntityExample entityExample = new EntityExample();
		entityExample.createCriteria().andAdPositionEqualTo(id);
		return entityMapper.selectByExample(entityExample).size() > 0;
	}

	@Override
	public List<AdPosition> getPositionByConsumer(String uuids) {
		if (StringUtils.isBlank(uuids)) {
			return null;
		}
		List<String> uuidList = StringUtils.str2List(uuids, Constants.SIGN_COMMA);
		FlowConsumerExample consumerExample = new FlowConsumerExample();
		consumerExample.createCriteria().andDspIdIn(uuidList).andConsumerStateEqualTo(Constants.STATE_VALID);
		List<FlowConsumer> consumerList = flowConsumerMapper.selectByExample(consumerExample);
		List<Integer> ids = Lists.transform(consumerList, FlowConsumer::getId);

		AdPositionMappingExample example = new AdPositionMappingExample();
		example.createCriteria().andFlowConsumerIdIn(ids).andStatusEqualTo(Constants.STATE_VALID);
		List<AdPositionMapping> mapperList = adPositionMappingMapper.selectByExample(example);
		List<Integer> positionIds = Lists.transform(mapperList, AdPositionMapping::getAdPositionId);
		AdPositionExample positionExample = new AdPositionExample();
		AdPositionExample.Criteria criteria = positionExample.createCriteria();
		criteria.andStatusEqualTo(Constants.STATE_VALID);
		if (positionIds.size() > 0) {
			criteria.andIdIn(positionIds);
		} else {
			criteria.andIdIn(Lists.newArrayList(Integer.MAX_VALUE));
		}
		return adPositionMapper.selectByExample(positionExample);
	}

	@Override
	public List<AdPosition> getPositionByApp(String uuids) {
		if (StringUtils.isBlank(uuids)) {
			return null;
		}
		List<String> uuidList = StringUtils.str2List(uuids, Constants.SIGN_COMMA);
		AppsExample appsExample = new AppsExample();
		appsExample.createCriteria().andAppIdIn(uuidList).andStatusEqualTo(Constants.STATE_VALID);
		List<Integer> appIds = Lists.transform(appsMapper.selectByExample(appsExample), Apps::getId);
		AdPositionExample example = new AdPositionExample();
		AdPositionExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(Constants.STATE_VALID);
		if (appIds.size() > 0) {
			criteria.andAppIdIn(appIds);
		} else {
			criteria.andAppIdIn(Lists.newArrayList(Integer.MAX_VALUE));
		}
		return adPositionMapper.selectByExample(example);
	}

	private Long getChargeAllLimit(SdkAllotmentExample sdkAllotmentExample, String normalDate) {
		Long cpmLimits = getChargeLimit(sdkAllotmentExample, normalDate, Constants.COST_TYPE_ORDER_CPM);
		Long cpcLimits = getChargeLimit(sdkAllotmentExample, normalDate, Constants.COST_TYPE_ORDER_CPC);
		return cpmLimits + cpcLimits;
	}

	private Long getChargeLimit(SdkAllotmentExample sdkAllotmentExample, String normalDate, Integer chargeType) {
		Map<Integer, SdkAllotment> allotmentMap = Maps.newHashMap();
		Long chargeLimits = 0l;
		sdkAllotmentExample.clear();
		sdkAllotmentExample.createCriteria().andTypeEqualTo(Constants.INTEGER_2).andStatusEqualTo(Constants.STATE_VALID)
				.andChargeTypeEqualTo(chargeType);
		List<SdkAllotment> fixedAllotments = sdkAllotmentMapper.selectByExample(sdkAllotmentExample);
		List<Integer> fixedAllotmentIds = Lists.transform(fixedAllotments, SdkAllotment::getId);
		for (SdkAllotment sdkAllotment : fixedAllotments) {
			allotmentMap.put(sdkAllotment.getId(), sdkAllotment);
		}
		if (fixedAllotmentIds.size() > 0) {
			SdkAllotScheduleExample sdkAllotScheduleExample = new SdkAllotScheduleExample();
			sdkAllotScheduleExample.createCriteria().andAllotmentIdIn(fixedAllotmentIds)
					.andStartDayLessThanOrEqualTo(Integer.valueOf(normalDate))
					.andEndDayGreaterThanOrEqualTo(Integer.valueOf(normalDate));
			List<SdkAllotSchedule> sdkAllotSchedules = sdkAllotScheduleMapper.selectByExample(sdkAllotScheduleExample);
			// cpc的库存计算方法
			if (Constants.COST_TYPE_ORDER_CPC.equals(chargeType)) {
				for (SdkAllotSchedule sdkAllotSchedule : sdkAllotSchedules) {
					SdkAllotment sdkAllotment = allotmentMap.get(sdkAllotSchedule.getAllotmentId());
					Integer platformId = sdkAllotment.getPlatformId();
					AdPositionMappingExample adPositionMappingExample = new AdPositionMappingExample();
					adPositionMappingExample.createCriteria().andAdPositionIdEqualTo(sdkAllotSchedule.getAdPosId())
							.andFlowConsumerIdEqualTo(platformId).andStatusEqualTo(Constants.STATE_VALID);
					List<AdPositionMapping> adPositionMappings = adPositionMappingMapper
							.selectByExample(adPositionMappingExample);
					Double forecastCtr = adPositionMappings.get(0).getForecastCtr();
					chargeLimits += new BigDecimal(sdkAllotSchedule.getExposureLimit())
							.divide(new BigDecimal(Double.toString(forecastCtr)), BigDecimal.ROUND_CEILING).longValue();
				}
			} else if (Constants.COST_TYPE_ORDER_CPM.equals(chargeType)) {
				for (SdkAllotSchedule sdkAllotSchedule : sdkAllotSchedules) {
					chargeLimits += sdkAllotSchedule.getExposureLimit().longValue();
				}
			}
		}
		return chargeLimits;
	}

	@Override
	public List<Template> getTemplatesById(Integer positionId) {
		AdPosition adPosition = adPositionMapper.selectByPrimaryKey(positionId);
		if (!Constants.AD_TYPE_NATIVE.equals(adPosition.getType())
				&& !Constants.AD_TYPE_INCENTIVE.equals(adPosition.getType())) {
			throw new ServiceException("此广告位不是模板广告位类型");
		}
		if (StringUtils.isNotBlank(adPosition.getTemplateId())) {
			List<Integer> templateIds = StringUtils.str2List4Int(adPosition.getTemplateId(), Constants.SIGN_COMMA);
			TemplateExample templateExample = new TemplateExample();
			templateExample.createCriteria().andIdIn(templateIds);
			List<Template> templates = templateMapper.selectByExample(templateExample);
			return templates;
		}
		return null;
	}

	@Override
	public List<AdPosition> listByConsumerAndApp(String consumerIds, String appIds) {
		AdPositionMappingExample mappeExample = new AdPositionMappingExample();
		if (StringUtils.isNotBlank(consumerIds)) {
			mappeExample.createCriteria().andFlowConsumerIdIn(Lists
					.transform(StringUtils.str2List(consumerIds, Constants.SIGN_COMMA), id -> Integer.parseInt(id)));
		}
		List<AdPositionMapping> mappingList = adPositionMappingMapper.selectByExample(mappeExample);
		List<Integer> ids = Lists.transform(mappingList, AdPositionMapping::getAdPositionId);

		AdPositionExample example = new AdPositionExample();
		AdPositionExample.Criteria criteria = example.createCriteria();
		if (ids.size() > 0) {
			criteria.andIdIn(ids);
		}
		if (StringUtils.isNotBlank(appIds)) {
			criteria.andAppIdIn(Lists.transform(StringUtils.str2List(appIds, Constants.SIGN_COMMA),
					appid -> Integer.parseInt(appid)));
		}
		List<AdPosition> respList = adPositionMapper.selectByExample(example);
		respList.forEach(adPosition -> {
			Apps apps = appsMapper.selectByPrimaryKey(adPosition.getAppId());
			adPosition.setName(StringUtils.concat(adPosition.getName(), Constants.SIGN_MINUS, apps.getAppName()));
		});
		return respList;
	}

}

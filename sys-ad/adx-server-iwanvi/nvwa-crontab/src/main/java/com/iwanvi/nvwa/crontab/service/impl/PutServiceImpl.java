package com.iwanvi.nvwa.crontab.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.adserv.ngx.util.ReverseIndexKeysUtils;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.MD5Utils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AdDicService;
import com.iwanvi.nvwa.crontab.service.AdpositionService;
import com.iwanvi.nvwa.crontab.service.AgentPriceService;
import com.iwanvi.nvwa.crontab.service.EntityService;
import com.iwanvi.nvwa.crontab.service.PutService;
import com.iwanvi.nvwa.crontab.service.SysCrontabService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.PlanMapper;
import com.iwanvi.nvwa.dao.PutMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.AppsExample;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.CompanyExample;
import com.iwanvi.nvwa.dao.model.Dictionary;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.PutExample;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit.Builder;
import com.iwanvi.nvwa.proto.AdModelsProto.AgeTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.BehaviorTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.CarrierTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.ConnectTypeTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.DeviceTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.MediaTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.OsTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.AdModelsProto.TerminalTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.TimeTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.TrafficTarget;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.CPACapping;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.CommonProto.FrequencyCapping;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;
import com.iwanvi.nvwa.proto.CommonProto.Pmp;
import com.iwanvi.nvwa.proto.CommonProto.PutType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class PutServiceImpl implements PutService {

	private static final Logger logger = LoggerFactory.getLogger(PutServiceImpl.class);

	@Autowired
	private PutMapper putMapper;

	@Autowired
	private SysCrontabService sysCrontabService;

	@Autowired
	private EntityService entityService;

	@Autowired
	private AdDicService adDicService;

	@Autowired
	private AppsMapper appsMapper;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private RedisDao redisDao;

	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Autowired
	private AdpositionService adpositionService;

	@Autowired
	private PlanMapper planMapper;

	@Autowired
	private AgentPriceService agentPriceService;

	// 投放方式白名單
	public static final String FREQUENCY_WHITELIST_KEY = "frequency_whitelist_key";
	// 代理商投放限额低价
	public static final String LOW_PRICE = "low_price";

	public static Map<Integer, String> AUDIENCE_MAP = Maps.newHashMap();

	static {
		AUDIENCE_MAP.put(1, "talkingdata");
	}

	@Override
	public Put getPut(Integer objectId) {
		return putMapper.selectByPrimaryKey(objectId);
	}

	@Override
	public void checkUnstartState() {
		logger.info("change putStart put state start .");
		PutExample example = new PutExample();
		Date curDate = new Date();
		List<Put> puts = Lists.newArrayList();
		// 启动在时间内待启动的单元
		example.createCriteria().andPutStateEqualTo(Constants.STATE_UNSTART).andBeginTimeLessThanOrEqualTo(curDate)
				.andEndTimeGreaterThan(curDate);
		puts = putMapper.selectByExample(example);
		// update
		Put put = new Put();
		put.setPutState(Constants.STATE_VALID);
		putMapper.updateByExampleSelective(put, example);
		logger.info("change putStart put state end .");
		// 将更改状态的单元添加到同步表
		if (!CollectionUtils.isEmpty(puts)) {
			for (Put p : puts) {
				sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_PUT, Constants.OP_UPDATE,
						p.getCreateUser());
			}
		}
		logger.info(" putStart putlist : {} .", JsonUtils.TO_JSON(puts));

	}

	@Override
	@Transactional
	public void resetPutLimitState() {
		PutExample example = new PutExample();
		example.createCriteria().andPutStateEqualTo(Constants.STATE_UNIT_LIMIT);
		List<Put> putList = putMapper.selectByExample(example);
		Put put = new Put();
		put.setPutState(Constants.STATE_VALID);
		logger.info("over limit puts:{}", JsonUtils.TO_JSON(putList));
		if (!CollectionUtils.isEmpty(putList)) {
			putMapper.updateByExampleSelective(put, example);
			for (Put p : putList) {
				// 判断同步表中是否已存在相应计划的记录
				if (Constants.STATE_VALID == p.getRunState()) {
					sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_PUT, Constants.OP_UPDATE,
							p.getCreateUser());
				}
			}
		}
	}

	@Override
	@Transactional
	public void checkExpiredState() {
		PutExample example = new PutExample();
		Date currentDate = new Date();
		// 未启动单元置为有效
		example.createCriteria().andPutStateEqualTo(Constants.STATE_UNSTART).andBeginTimeLessThan(currentDate)
				.andEndTimeGreaterThan(currentDate);
		List<Put> putList = putMapper.selectByExample(example);

		Put put = new Put();
		put.setPutState(Constants.STATE_VALID);
		putMapper.updateByExampleSelective(put, example);

		if (!CollectionUtils.isEmpty(putList)) {
			for (Put p : putList) {
				sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_PUT, Constants.OP_UPDATE,
						p.getCreateUser());
			}
		}
		logger.info("checkExpiredState putList to start : {} ", JsonUtils.TO_JSON(putList));

		// 过期单元置为无效
		example = new PutExample();
		example.createCriteria().andPutStateNotEqualTo(Constants.STATE_EXPIRED)
				.andPutStateNotEqualTo(Constants.STATE_INVALID).andEndTimeLessThan(currentDate);
		putList = putMapper.selectByExample(example);

		put.setPutState(Constants.STATE_EXPIRED);
		putMapper.updateByExampleSelective(put, example);

		if (!CollectionUtils.isEmpty(putList)) {
			for (Put p : putList) {
				sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_PUT, Constants.OP_UPDATE,
						p.getCreateUser());
			}
		}
		logger.info("checkExpiredState putList to end : {} ", JsonUtils.TO_JSON(putList));

	}

	@Override
	public List<Put> getValidPutByPid(Integer id) {
		PutExample example = new PutExample();
		example.createCriteria().andPidEqualTo(id).andPutStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID);
		return putMapper.selectByExample(example);
	}

	@Override
	public PubRecord buildPubRecord(Put put, List<Entity> entities, OpType kadd) {
		AdModelsProto.AdUnit p = buildPut(put, entities, kadd);
		AdModelsProto.AdUnit.Builder a = AdModelsProto.AdUnit.newBuilder();
		if (p != null) {
			logger.info("crontab adUnit data, put:{}", p.toString());
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(p.toByteString());
			pubRecord.setEntityType(EntityType.kAdUnit);
			return pubRecord.build();
		}
		return null;
	}

	private AdUnit buildPut(Put put, List<Entity> entities, OpType opType) {
		try {
			if (opType.equals(OpType.kAdd) && CollectionUtils.isEmpty(entities)) {
				logger.warn("not build put: entities is null! id: {}", put.getId());
				return null;
			}
			AdModelsProto.AdUnit.Builder putBuilder = AdModelsProto.AdUnit.newBuilder();
			putBuilder.setUnitId(put.getId());
			if (!OpType.kDelete.equals(opType)) {
				if (Constants.STATE_VALID.equals(put.getPutState())
						&& Constants.STATE_VALID.equals(put.getRunState())) {
					putBuilder.setStatus(Constants.STATE_VALID);
					Plan plan = planMapper.selectByPrimaryKey(put.getPid());
					putBuilder.setAdvertiserId(plan.getAdverId());
					putBuilder.setPlanId(put.getPid());
					if (!CollectionUtils.isEmpty(entities)) {// 构建单元同步信息
						for (Entity entity : entities) {
							putBuilder.addCreatives(entityService.buildCreative(entity, put));
						}
					}
					buildImpression(putBuilder, put, entities);
					buildTarget4Unit(putBuilder, put);
				} else {
					putBuilder.setStatus(Constants.STATE_INVALID);
				}
			}
			return putBuilder.build();
		} catch (Exception e) {
			logger.error("build ampUnit error! id: {}", put.getId(), e);
			return null;
		}
	}

	private void buildTarget4Unit(Builder builder, Put put) {
		builder.setTimeTarget(buildTimeTarget(put));
		builder.setTimeSlots(countHours(put));
		builder.setPutType(PutType.kPutTypeRtb);

		if (StringUtils.isNotBlank(put.getDxSb())) {
			builder.setDeviceTarget(buildDeviceTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxDq())) {
			builder.setAreaTarget(buildAreaTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxNl())) {
			builder.setAgeTarget(buildAgeTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxWl())) {// 网络
			builder.setConnectTypeTarget(buildConnectTypeTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxYys())) {// 运营商
			builder.setCarrierTarget(buildCarrierTarget(put));
		}

		if (StringUtils.isNotBlank(put.getDxCzxt())) {// 操作系统
			builder.setOsTarget(buildOsTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxLlly())) {
			builder.setTrafficTarget(buildTrafficTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxZdlx())) {// 终端
			builder.setTerminalTarget(buildTerminalTarget(put));
		}
		// 增加dealid 2016年12月13号
		if (StringUtils.isNotBlank(put.getDealId())) {
			builder.setPmp(buildPmpTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxMedia())) {
			builder.setMediaTarget(buildMediaTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxModel())) {
			builder.addExtTargets(buildModelTarget(put));
		}
		if (StringUtils.isNotBlank(put.getDxApp())) {
			builder.addExtTargets(buildAppTarget(put));
		} else if (StringUtils.isNotBlank(put.getDxAppType())) {
			builder.addExtTargets(buildAppTypeTarget(put));
		}

		if (StringUtils.isNotBlank(put.getDxChannel())) {
			CommonProto.MapEntry.Builder dxbuilder = CommonProto.MapEntry.newBuilder();
			dxbuilder.setKey("dx_channel");
			dxbuilder.setValue(put.getDxChannel());
			builder.addExtTargets(dxbuilder.build());
		}
		
		if (StringUtils.isNotBlank(put.getDxXw())) {
			if (inWhiteList(put)) {// 如果广告主没在白名单内,则兴趣定向无效.
				builder.setBehaviorTarget(buildBehaviorTarget(put));
			}
		}
		if (put.getPrice() != null) {
			builder.setCpc(put.getPrice());
		}
		if (put.getPutType() == CrontabConstants.PUT_TYPE_BOTTOM) {
			builder.setLimit(Integer.MAX_VALUE);
		} else if (put.getPutLimit() != null) {
			builder.setLimit(put.getPutLimit());
		}

		/*
		 * f2time帐号下的所有广告主单元上的投放方式按广告主选择同步,其他代理商帐号下的广告主单元投放方式同步时按下面规则同步:
		 * 单元限额低于500时按匀速投放同步,高于或等于500时按广告主选择同步. 2016年12月29号
		 */
		// AmpAdvertisers adver =
		// ampAdvertisersMapper.selectByPrimaryKey(ampUnit.getCreateUser());
		// String lowPrice = redisDao.get(LOW_PRICE);
		// if (!Constants.INTEGER_1.equals(adver.getAid()) &&
		// StringUtils.isNotBlank(lowPrice)
		// && Integer.parseInt(lowPrice) > ampUnit.getUnitLimit()) {
		// // 单元限额小于设定值的时候 默认匀速投放
		// builder.setDeliveryMode(CommonProto.DeliveryMode.valueOf(Constants.INTEGER_2));
		// } else {
		if (put.getDeliveryMode() != null) {
			builder.setDeliveryMode(
					CommonProto.DeliveryMode.valueOf(adDicService.getDic(put.getDeliveryMode()).getEnumValue()));
		} else {
			// 不填 默认标准投放
			builder.setDeliveryMode(CommonProto.DeliveryMode.valueOf(Constants.INTEGER_1));
		}
		// }
		if (put.getLandUrl() != null) {
			builder.setLandUrl(put.getLandUrl());
		}
		if (put.getPkgName() != null) {
			builder.setPkgName(put.getPkgName());
		}
		if (put.getExtCreativeId() != null) {
			builder.setExtCreativeId(put.getExtCreativeId());
		}
		if (put.getIsfilterDeviceCode() != null) {
			builder.setInvalidDeviceFiltering(put.getIsfilterDeviceCode() == 1 ? true : false);
		}
		if (put.getExtensionType() != null) {
			builder.setExtensionType(
					CommonProto.ExtensionType.valueOf(adDicService.getDic(put.getExtensionType()).getEnumValue()));
		}
		if (put.getCostType() == null && put.getPutType() != null
				&& put.getPutType() == CrontabConstants.PUT_TYPE_BOTTOM) {
			put.setCostType(111);// cpm
		}
		builder.setCostType(CommonProto.CostType.valueOf(adDicService.getDic(put.getCostType()).getEnumValue()));
		builder.setFrequencyCapping(buildFrequencyTarget(put));
		builder.setCpaCapping(buildCpaTarget(put));
	}

	private AreaTarget buildAreaTarget(Put put) {
		AdModelsProto.AreaTarget.Builder builder = AdModelsProto.AreaTarget.newBuilder();
		String[] ids = new String(put.getDxDq()).split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addAreaCode(adDicService.getArea(Integer.valueOf(id)).getAreaCode());
			}
		}
		return builder.build();
	}

	private CPACapping buildCpaTarget(Put ampUnit) {
		CommonProto.CPACapping.Builder builder = CommonProto.CPACapping.newBuilder();
		if (Constants.STATE_VALID.equals(ampUnit.getIsOptimize())) {
			builder.setCpa(ampUnit.getOptimizeCpa());
			builder.setCpaCapping(true);
		} else {
			builder.setCpaCapping(false);
		}
		return builder.build();
	}

	private FrequencyCapping buildFrequencyTarget(Put ampUnit) {
		CommonProto.FrequencyCapping.Builder builder = CommonProto.FrequencyCapping.newBuilder();
		Integer isFre = ampUnit.getIsFrequency();
		Integer times = ampUnit.getFrequencNum();
		Integer period = ampUnit.getFrequencPeriod();
		if (Constants.STATE_VALID.equals(isFre) && times != null && period != null) {
			builder.setFrequencyCapping(true);
			builder.setFrequency(times);
			builder.setFrequencyPeriod(CommonProto.FrequencyPeriod.valueOf(adDicService.getDic(period).getEnumValue()));
			buildIpFrequencyTarget(ampUnit, builder);
		} else {
			builder.setFrequencyCapping(false);
		}
		return builder.build();
	}

	private void buildIpFrequencyTarget(Put ampUnit, FrequencyCapping.Builder builder) {
		Integer isFre = ampUnit.getIsFrequencyIp();
		Integer times = ampUnit.getFrequencyNumIp();
		Integer period = ampUnit.getFrequencyPeriodIp();
		if (Constants.STATE_VALID.equals(isFre) && times != null && period != null) {
			builder.setIpCapping(true);
			builder.setFrequency(times);
			builder.setFrequencyPeriod(CommonProto.FrequencyPeriod.valueOf(adDicService.getDic(period).getEnumValue()));
		}
	}

	private BehaviorTarget buildBehaviorTarget(Put put) {
		AdModelsProto.BehaviorTarget.Builder builder = AdModelsProto.BehaviorTarget.newBuilder();
		String[] ids = put.getDxXw().split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addBehavior(Integer.parseInt(id));
			}
		}
		return builder.build();
	}

	private boolean inWhiteList(Put put) {
		boolean flag = false;
		if (put != null) {
			CompanyExample companyExample = new CompanyExample();
			companyExample.createCriteria().andTypeEqualTo(CrontabConstants.COMPANY_ADVER_TYPE)
					.andIdEqualTo(put.getCreateUser());
			List<Company> selectByExample = companyMapper.selectByExample(companyExample);
			Company ampAdvertisers = selectByExample.get(0);
			Integer adverId = ampAdvertisers.getId();
			String value = redisDao.get(FREQUENCY_WHITELIST_KEY);
			if (StringUtils.isNotBlank(value)) {
				List<Integer> adverIdList = StringUtils.str2List4Int(value, Constants.SIGN_COMMA);
				if (adverIdList.contains(adverId)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	private MapEntry buildAppTypeTarget(Put put) {
		CommonProto.MapEntry.Builder builder = CommonProto.MapEntry.newBuilder();
		String appTypes = put.getDxAppType();
		String[] typeIds = appTypes.split(Constants.SIGN_COMMA);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < typeIds.length; i++) {
			if (StringUtils.isNotBlank(typeIds[i])) {
				AppsExample example = new AppsExample();
				example.createCriteria().andTypeEqualTo(Integer.parseInt(typeIds[i]))
						.andStatusEqualTo(Constants.STATE_VALID);
				List<Apps> list = appsMapper.selectByExample(example);
				if (list != null) {
					for (Apps app : list) {
						if (app != null) {
							stringBuilder.append(app.getAppId());
							stringBuilder.append(Constants.SIGN_COMMA);
						}
					}
				}
			}
		}
		builder.setKey("dx_app");
		builder.setValue(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
		return builder.build();
	}

	private MapEntry buildAppTarget(Put put) {
		CommonProto.MapEntry.Builder builder = CommonProto.MapEntry.newBuilder();
		String apps = put.getDxApp();
		Integer filterApp = put.getFilterApp();
		String[] ids = apps.split(Constants.SIGN_COMMA);
		StringBuilder stringBuilder = new StringBuilder();
		if (Constants.INTEGER_0.intValue() == filterApp) {
			for (int i = 0; i < ids.length; i++) {
				stringBuilder.append(appsMapper.selectByPrimaryKey(Integer.parseInt(ids[i])).getAppId());
				stringBuilder.append(Constants.SIGN_COMMA);
			}
			if (StringUtils.isNotBlank(stringBuilder)) {
				builder.setKey("dx_app");
				builder.setValue(stringBuilder.toString().substring(0, stringBuilder.length() - 1));
			}
		} else {
			AppsExample example = new AppsExample();
			example.createCriteria().andMediasEqualTo(put.getDxMedia()).andStatusEqualTo(Constants.STATE_VALID)
					.andPlatformEqualTo(Integer.parseInt(put.getDxCzxt())).andAppIdNotIn(Lists.newArrayList(ids));
			List<Apps> appsList = appsMapper.selectByExample(example);
			List<String> appIds = FluentIterable.from(appsList).transform(new Function<Apps, String>() {
				@Override
				public String apply(Apps input) {
					return input.getAppId();
				}
			}).toList();
			if (!CollectionUtils.isEmpty(appIds)) {
				builder.setKey("blk_app");
				builder.setValue(StringUtils.list2str(appIds));
			}
		}
		return builder.build();
	}

	private MapEntry buildModelTarget(Put put) {
		CommonProto.MapEntry.Builder builder = CommonProto.MapEntry.newBuilder();
		String models = put.getDxModel();
		if (StringUtils.isNotBlank(models)) {
			String[] modelArr = models.split(Constants.SIGN_COMMA);
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < modelArr.length; i++) {
				Dictionary model = adDicService.getDic(Integer.parseInt(modelArr[i]));
				if (model != null) {
					stringBuilder.append(Constants.SIGN_COMMA);
					stringBuilder.append(model.getDicKey());
				}
			}
			if (StringUtils.isNotBlank(stringBuilder)) {
				builder.setKey("dx_model");
				builder.setValue(stringBuilder.substring(1));
			}
		}
		return builder.build();
	}

	private MediaTarget buildMediaTarget(Put put) {
		AdModelsProto.MediaTarget.Builder builder = AdModelsProto.MediaTarget.newBuilder();
		String[] uuids = put.getDxMedia().split(Constants.SIGN_COMMA);
		for (String uuid : uuids) {
			if (StringUtils.isNotBlank(uuid)) {
				builder.addMediaUuid(uuid);
			}
		}
		return builder.build();
	}

	private Pmp buildPmpTarget(Put put) {
		CommonProto.Pmp.Builder builder = CommonProto.Pmp.newBuilder();
		if (StringUtils.isNotBlank(put.getDealId())) {
			builder.setDealId(put.getDealId());
		}
		return builder.build();
	}

	private TerminalTarget buildTerminalTarget(Put put) {
		AdModelsProto.TerminalTarget.Builder builder = AdModelsProto.TerminalTarget.newBuilder();
		String[] ids = put.getDxZdlx().split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addTerminal(
						CommonProto.TerminalType.valueOf(adDicService.getDic(Integer.valueOf(id)).getEnumValue()));
			}
		}
		return builder.build();
	}

	private TrafficTarget buildTrafficTarget(Put put) {
		AdModelsProto.TrafficTarget.Builder builder = AdModelsProto.TrafficTarget.newBuilder();
		String[] ids = put.getDxLlly().split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addTraffic(
						CommonProto.TrafficType.valueOf(adDicService.getDic(Integer.valueOf(id)).getEnumValue()));
			}
		}
		return builder.build();
	}

	private OsTarget buildOsTarget(Put put) {
		AdModelsProto.OsTarget.Builder builder = AdModelsProto.OsTarget.newBuilder();
		String[] ids = put.getDxCzxt().split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addOs(CommonProto.OSType.valueOf(adDicService.getDic(Integer.valueOf(id)).getEnumValue()));
			}
		}
		return builder.build();
	}

	private CarrierTarget buildCarrierTarget(Put put) {
		AdModelsProto.CarrierTarget.Builder builder = AdModelsProto.CarrierTarget.newBuilder();
		String[] ids = put.getDxYys().split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addCarrier(
						CommonProto.Carrier.valueOf(adDicService.getDic(Integer.valueOf(id)).getEnumValue()));
			}
		}
		return builder.build();
	}

	private ConnectTypeTarget buildConnectTypeTarget(Put put) {
		AdModelsProto.ConnectTypeTarget.Builder builder = AdModelsProto.ConnectTypeTarget.newBuilder();
		String[] ids = put.getDxWl().split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addConnectType(
						CommonProto.ConnectType.valueOf(adDicService.getDic(Integer.valueOf(id)).getEnumValue()));
			}
		}
		return builder.build();
	}

	private AgeTarget buildAgeTarget(Put put) {
		AdModelsProto.AgeTarget.Builder builder = AdModelsProto.AgeTarget.newBuilder();
		String[] ages = put.getDxNl().split(Constants.SIGN_COMMA);
		for (String age : ages) {
			if (StringUtils.isNotBlank(age)) {
				String[] ageValues = age.split(Constants.SIGN_MINUS);
				if (ageValues.length == 2) {
					builder.addAgeInterval(
							AdModelsProto.AgeTarget.AgeInterval.newBuilder().setBeginAge(Integer.valueOf(ageValues[0]))
									.setEndAge(Integer.valueOf(ageValues[1])).build());
				}
			}
		}
		return builder.build();
	}

	private DeviceTarget buildDeviceTarget(Put put) {
		AdModelsProto.DeviceTarget.Builder builder = AdModelsProto.DeviceTarget.newBuilder();
		String[] devices = put.getDxSb().split(Constants.SIGN_COMMA);
		for (String device : devices) {
			if (StringUtils.isNotBlank(device)) {
				builder.addDid(MD5Utils.MD5(device));
			}
		}
		return builder.build();
	}

	private int countHours(Put unit) {
		int hours = 24;
		if (unit != null && StringUtils.isNotBlank(unit.getTimeInterval())) {
			String[] times = unit.getTimeInterval().split(Constants.SIGN_COMMA);
			hours = 0;
			for (String time : times) {
				if (StringUtils.isNotBlank(time)) {
					String[] hourStrs = time.split(Constants.SIGN_MINUS);
					if (hourStrs.length == 2) {
						int start = Integer.parseInt(hourStrs[0]);
						int end = Integer.parseInt(hourStrs[1]);
						for (int i = start; i < end; i++) {
							hours++;
						}
					}
				}
			}
		}
		return hours;
	}

	private TimeTarget buildTimeTarget(Put ampUnit) {
		AdModelsProto.TimeTarget.Builder builder = AdModelsProto.TimeTarget.newBuilder();
		// 开始结束时间单位为秒
		if (ampUnit.getBeginTime() != null) {
			builder.setBeginTime(ampUnit.getBeginTime().getTime() / 1000);
		}
		if (ampUnit.getEndTime() != null) {
			builder.setEndTime(ampUnit.getEndTime().getTime() / 1000);
		} else {
			builder.setEndTime(DateUtils.addYears(new Date(), 100).getTime() / 1000);
		}
		long timeInterval = 0l;
		if (StringUtils.isNotBlank(ampUnit.getTimeInterval())) {
			String[] times = ampUnit.getTimeInterval().split(Constants.SIGN_COMMA);
			for (String time : times) {
				if (StringUtils.isNotBlank(time)) {
					String[] hours = time.split(Constants.SIGN_MINUS);
					if (hours.length == 2) {
						int start = Integer.parseInt(hours[0]);
						int end = Integer.parseInt(hours[1]);
						if (start < end && end <= 48) {
							for (int i = start * 2; i < end * 2; i++) {
								timeInterval += (1l << i);
							}
						}
					}
				}
			}
		}
		if (timeInterval > 0) {
			builder.setHalfHours(timeInterval);
		} else {
			builder.setHalfHours(Constants.ALL_TIME);
		}
		return builder.build();
	}

	private void buildImpression(Builder builder, Put put, List<Entity> entities) {
		if (put.getAdPosition() != null) {
			builder.setAdTypeId(put.getAdPosition());
			if (put.getAdPosition() != null) {
				builder.setAdTypeId(put.getAdPosition());
			}
			AdPosition adPosition = adpositionService.getAdPosition(put.getAdPosition());
			int creativeType = adPosition.getType();
			if ((creativeType == CrontabConstants.AD_TYPE_DYNAMIC && adPosition.getDuration() == null)
					|| creativeType == CrontabConstants.AD_TYPE_PIC
					|| creativeType == CrontabConstants.AD_TYPE_SCREEN) {
				builder.setWidth(adPosition.getPicWidth()).setHeight(adPosition.getPicHeight());
			} else if (creativeType == CrontabConstants.AD_TYPE_VIDEO) {
				builder.setDuration(adPosition.getDuration());
			}
			if (StringUtils.isNotBlank(adPosition.getFlowPositionId())) {
				builder.setAdxAdTypeId(adPosition.getFlowPositionId());
			} else {
				builder.setAdxAdTypeId(adPosition.getUuid());
			}
			builder.setCreativeType(CommonProto.CreativeType.valueOf(adDicService.getDic(creativeType).getEnumValue()));
		}
	}

	@Override
	public void buildPutSend(Integer id, OpType opType) {
		Put put = putMapper.selectByPrimaryKey(id);
		List<Entity> entities = entityService.getValidEntityByPut(id);
		// 单元下没有可出的创意时,同步删除单元
		if (entities == null || entities.size() == 0) {
			logger.info("no have valid entity, uid:{}", put.getId());
			opType = OpType.kDelete;
		}
		AdModelsProto.AdUnit unit = buildPut(put, entities, opType);
		if (unit != null) {
			List<Long> reverseIndexKeys = ReverseIndexKeysUtils.buildReverseIndexKeys(unit);
			AdModelsProto.AdUnit.Builder adUnitBuilder = AdModelsProto.AdUnit.newBuilder(unit);
			AdModelsProto.AdUnit adUnit = adUnitBuilder.addAllReverseIndex(reverseIndexKeys).build();
			logger.info("crontab adUnit data, adUnit:{}", adUnit.toString());
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_PUT + put.getId(), adUnit.toByteArray());
			logger.info("ampUnit cache to redis, id : {}", put.getId());
			auditMessageProducer.send(CommonProto.EntityType.kAdUnit, put.getId(), opType);
			logger.info("crontab ampUnit message is send, id : {}, opType : {}", put.getId(), opType);

			if (!OpType.kDelete.equals(opType) && put.getPutState() == Constants.STATE_VALID
					&& put.getRunState() == Constants.STATE_VALID) {
				agentPriceService.buildAgentPriceSend(put, opType);
			}
		}

	}

}

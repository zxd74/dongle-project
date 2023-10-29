package com.iwanvi.nvwa.crontab.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.iwanvi.adserv.ngx.util.ReverseIndexKeysUtils;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AdDicService;
import com.iwanvi.nvwa.crontab.service.AdpositionService;
import com.iwanvi.nvwa.crontab.service.EntityService;
import com.iwanvi.nvwa.crontab.service.OrderPutService;
import com.iwanvi.nvwa.crontab.service.SysCrontabService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.dao.AppChannelMapper;
import com.iwanvi.nvwa.dao.AppVersionMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.BookCategoryMapper;
import com.iwanvi.nvwa.dao.DeviceModelMapper;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.OrdersMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AppChannel;
import com.iwanvi.nvwa.dao.model.AppChannelExample;
import com.iwanvi.nvwa.dao.model.AppVersion;
import com.iwanvi.nvwa.dao.model.AppVersionExample;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.AppsExample;
import com.iwanvi.nvwa.dao.model.BookCategory;
import com.iwanvi.nvwa.dao.model.BookCategoryExample;
import com.iwanvi.nvwa.dao.model.DeviceModel;
import com.iwanvi.nvwa.dao.model.DeviceModelExample;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.ConnectTypeTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit.Builder;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.MediaTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.AdModelsProto.TimeTarget;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.CommonProto.FrequencyCapping;
import com.iwanvi.nvwa.proto.CommonProto.PutType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class OrderPutServiceImpl implements OrderPutService {

	private static final Logger logger = LoggerFactory.getLogger(OrderPutServiceImpl.class);

	@Autowired
	private OrdersMapper ordersMapper;

	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Autowired
	private OrderPutMapper orderPutMapper;

	@Autowired
	private EntityService entityService;

	@Autowired
	private AdpositionService adpositionService;

	@Autowired
	private AdDicService adDicService;

	@Autowired
	private SysCrontabService sysCrontabService;

	@Autowired
	private AppChannelMapper appChannelMapper;
	
	@Autowired
	private AppVersionMapper appVersionMapper;
	
	@Autowired
	private AppsMapper appsMapper;
	
	@Autowired
	private BookCategoryMapper bookCategoryMapper; 

	@Autowired
	private DeviceModelMapper deviceModelMapper;
	
	@Override
	public void buildOrderPutSend(Integer id, OpType opType) {
		OrderPut put = orderPutMapper.selectByPrimaryKey(id);
		List<Entity> entities = entityService.getValidEntityByPut(id);
		// 单元下没有可出的创意时,同步删除单元
		if (entities == null || entities.size() == 0) {
			logger.info("no have valid entity, uid:{}", put.getId());
			opType = OpType.kDelete;
		}
		AdModelsProto.AdUnit unit = buildOrderPut(put, entities, opType);

		if (unit != null) {
			List<Long> reverseIndexKeys = ReverseIndexKeysUtils.buildReverseIndexKeys(unit);
			AdModelsProto.AdUnit.Builder adUnitBuilder = AdModelsProto.AdUnit.newBuilder(unit);
			AdModelsProto.AdUnit adUnit = adUnitBuilder.addAllReverseIndex(reverseIndexKeys).build();
			logger.info("crontab adUnit data, adUnit:{}", adUnit.toString());
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_PUT + put.getId(), adUnit.toByteArray());
			logger.info("ampUnit cache to redis, id : {}", put.getId());
			auditMessageProducer.send(CommonProto.EntityType.kAdUnit, put.getId(), opType);
			logger.info("crontab ampUnit message is send, id : {}, opType : {}", put.getId(), opType);
		}
	}

	private AdUnit buildOrderPut(OrderPut put, List<Entity> entities, OpType opType) {
		try {
			if (opType.equals(OpType.kAdd) && CollectionUtils.isEmpty(entities)) {
				logger.warn("not build put: entities is null! id: {}", put.getId());
				return null;
			}
			AdModelsProto.AdUnit.Builder ampUnitBuilder = AdModelsProto.AdUnit.newBuilder();
			ampUnitBuilder.setUnitId(put.getId());
			if (!OpType.kDelete.equals(opType)) {
				if (Constants.STATE_VALID.equals(put.getPutState())
						&& Constants.STATE_VALID.equals(put.getRunState())) {
					ampUnitBuilder.setStatus(Constants.STATE_VALID);
					Orders order = ordersMapper.selectByPrimaryKey(put.getOid());
					if (order == null) {
						logger.warn("orders is null! id: {}", put.getOid());
						return null;
					}
					ampUnitBuilder.setAdvertiserId(order.getCustId());
					ampUnitBuilder.setPlanId(put.getOid());
					if (!CollectionUtils.isEmpty(entities)) {
						for (Entity ampEntity : entities) {
							ampUnitBuilder.addCreatives(entityService.buildOrderCreative(ampEntity, put));
						}
					}
					buildImpression(ampUnitBuilder, put, entities);
					buildTarget4Unit(ampUnitBuilder, put);
				} else {
					ampUnitBuilder.setStatus(Constants.STATE_INVALID);
				}
			}
			return ampUnitBuilder.build();
		} catch (Exception e) {
			logger.error("build orderPut error! id: {}", put.getId(), e);
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	private void buildTarget4Unit(Builder builder, OrderPut ampUnit) {
		builder.setTimeTarget(buildTimeTarget(ampUnit));
		builder.setTimeSlots(countHours(ampUnit));
		
		if (CrontabConstants.PUT_TYPE_ORDER.equals(ampUnit.getPutType())) {
			builder.setPutType(PutType.kPutTypeOrder);
		} else if (CrontabConstants.PUT_TYPE_BOTTOM.equals(ampUnit.getPutType())) {
			builder.setPutType(PutType.kPutTypeDefault);
		}
		
		
		if (StringUtils.isNotBlank(ampUnit.getLandUrl())) {
			builder.setLandUrl(ampUnit.getLandUrl());
		} 
		
		if (ampUnit.getDxMedia() != null) {
			builder.setMediaTarget(buildMediaTarget(ampUnit));
		}

		if (ampUnit.getPutLimit() != null) {
			if (!CrontabConstants.COST_TYPE_CPT.equals(ampUnit.getCostType())) {//cpt不传限额
				builder.setLimit(ampUnit.getPutLimit());
			}
		}
		if (ampUnit.getDeliveryMode() != null) {
			builder.setDeliveryMode(
					CommonProto.DeliveryMode.valueOf(adDicService.getDic(ampUnit.getDeliveryMode()).getEnumValue()));
		} else {
			// 不填 默认标准投放
			builder.setDeliveryMode(CommonProto.DeliveryMode.valueOf(Constants.INTEGER_1));
		}
		if(StringUtils.isNotBlank(ampUnit.getDxQd())) { //渠道
			CommonProto.MapEntry.Builder dxbuilder = CommonProto.MapEntry.newBuilder();
			String dxQd = ampUnit.getDxQd();
			List<Integer> dxQds = Arrays.asList(dxQd.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
					.collect(Collectors.toList());
			AppChannelExample appChannelExample = new AppChannelExample();
			appChannelExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(dxQds);
			List<AppChannel> list = appChannelMapper.selectByExample(appChannelExample);
			if (!CollectionUtils.isEmpty(list)) {
				List<String> names = FluentIterable.from(list).transform((AppChannel appChannel) -> {
					return appChannel.getName();
				}).toList();
				dxbuilder.setKey("dx_channel");
				dxbuilder.setValue(String.join(Constants.SIGN_COMMA, names));
				builder.addExtTargets(dxbuilder.build());	
			}
		}
		if(StringUtils.isNotBlank(ampUnit.getDxJx())) { //机型
			CommonProto.MapEntry.Builder dxbuilder = CommonProto.MapEntry.newBuilder();
			String dxJx = ampUnit.getDxJx();
			List<Integer> dxJxs = Arrays.asList(dxJx.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
					.collect(Collectors.toList());
			DeviceModelExample deviceModelExample = new DeviceModelExample();
			deviceModelExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(dxJxs);
			List<DeviceModel> list = deviceModelMapper.selectByExample(deviceModelExample);
			if (!CollectionUtils.isEmpty(list)) {
				List<String> names = FluentIterable.from(list).transform((DeviceModel deviceModel) -> {
					return deviceModel.getModelName();
				}).toList();
				dxbuilder.setKey("dx_model");
				dxbuilder.setValue(String.join(Constants.SIGN_COMMA, names));
				builder.addExtTargets(dxbuilder.build());	
			}
		}
		if(StringUtils.isNotBlank(ampUnit.getDxApp())) {
			CommonProto.MapEntry.Builder dxbuilder = CommonProto.MapEntry.newBuilder();
			String dxApp = ampUnit.getDxApp();
			List<Integer> dxApps = Arrays.asList(dxApp.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
					.collect(Collectors.toList());
			AppsExample appsExample = new AppsExample();
			appsExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppStatusEqualTo(Constants.STATE_VALID).andIdIn(dxApps);
			List<Apps> list = appsMapper.selectByExample(appsExample);
			if (!CollectionUtils.isEmpty(list)) {
				List<String> uuids = FluentIterable.from(list).transform((Apps apps) -> {
					return apps.getAppId();
				}).toList();
				dxbuilder.setKey("dx_app");
				dxbuilder.setValue(String.join(Constants.SIGN_COMMA, uuids));
				builder.addExtTargets(dxbuilder.build());
			}
		}
		if(StringUtils.isNotBlank(ampUnit.getDxTs())) { //图书
			CommonProto.MapEntry.Builder dxbuilder = CommonProto.MapEntry.newBuilder();
			String dxTs = ampUnit.getDxTs();
			List<Integer> dxTss = Arrays.asList(dxTs.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
					.collect(Collectors.toList());
			BookCategoryExample bookCategoryExample = new BookCategoryExample();
			bookCategoryExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(dxTss);
			List<BookCategory> list = bookCategoryMapper.selectByExample(bookCategoryExample);
			if (!CollectionUtils.isEmpty(list)) {
				List<String> names = FluentIterable.from(list).transform((BookCategory bookCategory) -> {
					return bookCategory.getName();
				}).toList();
				dxbuilder.setKey("dx_bookcat");
				dxbuilder.setValue(String.join(Constants.SIGN_COMMA, names));
				builder.addExtTargets(dxbuilder.build());
			}
		}
		if(StringUtils.isNotBlank(ampUnit.getDxBb())) { //版本
			CommonProto.MapEntry.Builder dxbuilder = CommonProto.MapEntry.newBuilder();
			String dxBb = ampUnit.getDxBb();
			List<Integer> dxBbs = Arrays.asList(dxBb.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
					.collect(Collectors.toList());
			AppVersionExample appVersionExample = new AppVersionExample();
			appVersionExample.createCriteria().andIsDeletedEqualTo(false).andIdIn(dxBbs);
			List<AppVersion> list = appVersionMapper.selectByExample(appVersionExample);
			if (!CollectionUtils.isEmpty(list)) {
				List<String> names = FluentIterable.from(list).transform((AppVersion appVersion) -> {
					return appVersion.getName();
				}).toList();
				dxbuilder.setKey("dx_app_version");
				dxbuilder.setValue(String.join(Constants.SIGN_COMMA, names));
				builder.addExtTargets(dxbuilder.build());
			}
		}
		if (StringUtils.isNotBlank(ampUnit.getDxWl())) {// 网络
			builder.setConnectTypeTarget(buildConnectTypeTarget(ampUnit));
		}
		if (StringUtils.isNotBlank(ampUnit.getDxDq())) { //地域定向
			builder.setAreaTarget(buildAreaTarget(ampUnit));
		}
		if (ampUnit.getCostType() == null && ampUnit.getPutType() != null
				&& CrontabConstants.PUT_TYPE_BOTTOM.equals(ampUnit.getPutType())) {
			ampUnit.setCostType(111);// cpm
		}
		builder.setCostType(CommonProto.CostType.valueOf(adDicService.getDic(ampUnit.getCostType()).getEnumValue()));
		builder.setFrequencyCapping(buildFrequencyTarget(ampUnit));
	}
	
	private AreaTarget buildAreaTarget(OrderPut ampUnit) {
		AdModelsProto.AreaTarget.Builder builder = AdModelsProto.AreaTarget.newBuilder();
		String[] ids = new String(ampUnit.getDxDq()).split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addAreaCode(adDicService.getArea(Integer.valueOf(id)).getAreaCode());
			}
		}
		return builder.build();
	}

	@SuppressWarnings("deprecation")
	private ConnectTypeTarget buildConnectTypeTarget(OrderPut ampUnit) {
		AdModelsProto.ConnectTypeTarget.Builder builder = AdModelsProto.ConnectTypeTarget.newBuilder();
		String[] ids = ampUnit.getDxWl().split(Constants.SIGN_COMMA);
		for (String id : ids) {
			if (StringUtils.isNotBlank(id)) {
				builder.addConnectType(
						CommonProto.ConnectType.valueOf(adDicService.getDic(Integer.valueOf(id)).getEnumValue()));
			}
		}
		return builder.build();
	}
	@SuppressWarnings("deprecation")
	private FrequencyCapping buildFrequencyTarget(OrderPut ampUnit) {
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

	@SuppressWarnings("deprecation")
	private void buildIpFrequencyTarget(OrderPut ampUnit,
			com.iwanvi.nvwa.proto.CommonProto.FrequencyCapping.Builder builder) {
		Integer isFre = ampUnit.getIsFrequency();
		Integer times = ampUnit.getFrequencNum();
		Integer period = ampUnit.getFrequencPeriod();
		if (Constants.STATE_VALID.equals(isFre) && times != null && period != null) {
			builder.setIpCapping(true);
			builder.setFrequency(times);
			builder.setFrequencyPeriod(CommonProto.FrequencyPeriod.valueOf(adDicService.getDic(period).getEnumValue()));
		}
	}

	private MediaTarget buildMediaTarget(OrderPut ampUnit) {// 只有中文万维一个媒体
		AdModelsProto.MediaTarget.Builder builder = AdModelsProto.MediaTarget.newBuilder();
		builder.addMediaUuid(CrontabConstants.AK_UUID);
		return builder.build();
	}

	private int countHours(OrderPut unit) {
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

	private TimeTarget buildTimeTarget(OrderPut ampUnit) {
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

	private void buildImpression(Builder builder, OrderPut put, List<Entity> entities) {
		if (put.getAdPosition() != null) {
			builder.setAdTypeId(put.getAdPosition());
			if (put.getAdPosition() != null) {
				builder.setAdTypeId(put.getAdPosition());
			}
			AdPosition adPosition = adpositionService.getAdPosition(put.getAdPosition());
			int creativeType = adPosition.getType();
			if ((creativeType == CrontabConstants.AD_TYPE_DYNAMIC && adPosition.getDuration() == null)
					|| creativeType == CrontabConstants.AD_TYPE_PIC || creativeType == CrontabConstants.AD_TYPE_SCREEN) {
				builder.setWidth(adPosition.getPicWidth()).setHeight(adPosition.getPicHeight());
			} else if (creativeType == CrontabConstants.AD_TYPE_VIDEO
					|| (creativeType == CrontabConstants.AD_TYPE_DYNAMIC && adPosition.getDuration() == null)) {
				builder.setDuration(adPosition.getDuration());
			}
			if (StringUtils.isNotBlank(adPosition.getFlowPositionId())) {
				builder.setAdxAdTypeId(adPosition.getFlowPositionId());
			}else {
				logger.info("==设置订单adxAdTypeId为：{}==",adPosition.getUuid());
				builder.setAdxAdTypeId(adPosition.getUuid());
			}
			builder.setCreativeType(CommonProto.CreativeType.valueOf(adDicService.getDic(creativeType).getEnumValue()));
			logger.info("test04");
		}
	}

	@Override
	public OrderPut getOrderPut(Integer objectId) {
		return orderPutMapper.selectByPrimaryKey(objectId);
	}

	@Override
	public List<OrderPut> getValidOrderPutByOid(Integer id) {
		OrderPutExample example = new OrderPutExample();
		example.createCriteria().andOidEqualTo(id).andPutStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID);
		return orderPutMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public PubRecord buildPubRecord(OrderPut orderPut, List<Entity> entities, OpType kadd) {
		AdModelsProto.AdUnit p = buildOrderPut(orderPut, entities, kadd);
		logger.info("test01  :"  + p);
		if (p != null) {
			logger.info("crontab orderPut data, put:{}", p.toString());
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(p.toByteString());
			pubRecord.setEntityType(EntityType.kAdUnit);
			return pubRecord.build();
		}
		return null;
	}

	@Override
	@Transactional
	public void checkExpiredState() {
		OrderPutExample example = new OrderPutExample();
		Date currentDate = new Date();
		example.createCriteria().andPutStateEqualTo(Constants.STATE_UNSTART).andBeginTimeLessThan(currentDate)
				.andEndTimeGreaterThan(currentDate);
		List<OrderPut> opList = orderPutMapper.selectByExample(example);

		OrderPut put = new OrderPut();
		put.setPutState(Constants.STATE_VALID);
		orderPutMapper.updateByExampleSelective(put, example);

		if (!CollectionUtils.isEmpty(opList)) {
			for (OrderPut p : opList) {
				Orders orders = ordersMapper.selectByPrimaryKey(p.getOid());
				sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_ORDER_PUT, Constants.OP_UPDATE,
						orders.getCustId());
			}
		}
		logger.info("checkExpiredState orderputList to start : {} ", JsonUtils.TO_JSON(opList));

		// 过期单元置为无效
		example = new OrderPutExample();
		example.createCriteria().andPutStateNotEqualTo(Constants.STATE_EXPIRED)
				.andPutStateNotEqualTo(Constants.STATE_INVALID).andEndTimeLessThan(currentDate);
		opList = orderPutMapper.selectByExample(example);

		put.setPutState(Constants.STATE_EXPIRED);
		orderPutMapper.updateByExampleSelective(put, example);

		if (!CollectionUtils.isEmpty(opList)) {
			for (OrderPut p : opList) {
				Orders orders = ordersMapper.selectByPrimaryKey(p.getOid());
				sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_PUT, Constants.OP_UPDATE,
						orders.getCustId());
			}
		}
		logger.info("checkExpiredState orderputList to end : {} ", JsonUtils.TO_JSON(opList));

	}

	@Override
	@Transactional
	public void resetPutLimitState() {
		OrderPutExample example = new OrderPutExample();
		example.createCriteria().andPutStateEqualTo(Constants.STATE_UNIT_LIMIT);
		List<OrderPut> putList = orderPutMapper.selectByExample(example);
		OrderPut put = new OrderPut();
		put.setPutState(Constants.STATE_VALID);
		logger.info("over limit orderputs:{}", JsonUtils.TO_JSON(putList));
		if (!CollectionUtils.isEmpty(putList)) {
			orderPutMapper.updateByExampleSelective(put, example);
			for (OrderPut p : putList) {
				Orders orders = ordersMapper.selectByPrimaryKey(p.getOid());
				// 判断同步表中是否已存在相应计划的记录
				if (Constants.STATE_VALID == p.getRunState()) {
					sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_PUT, Constants.OP_UPDATE,
							orders.getCustId());
				}
			}
		}
	}

	@Override
	public void checkOrderPutstartState() {
		logger.info("change OrderPutStart put state start .");
		OrderPutExample example = new OrderPutExample();
		Date curDate = new Date();
		List<OrderPut> puts = Lists.newArrayList();
		//启动在时间内待启动的投放
		example.createCriteria().andPutStateEqualTo(Constants.STATE_UNSTART).andBeginTimeLessThanOrEqualTo(curDate)
				.andEndTimeGreaterThan(curDate);
		puts = orderPutMapper.selectByExample(example);
		// update
		OrderPut put = new OrderPut();
		put.setPutState(Constants.STATE_VALID);
		orderPutMapper.updateByExampleSelective(put, example);
		logger.info("change OrderPutStart put state end .");
		//将更改状态的单元添加到同步表
		if (!CollectionUtils.isEmpty(puts)) {
			for (OrderPut p : puts) {
				Orders orders = ordersMapper.selectByPrimaryKey(p.getOid());
				sysCrontabService.insertSysCrontab(p.getId(), Constants.OBJ_ORDER_PUT, Constants.OP_UPDATE,
						orders.getCustId());
			}
		}
		logger.info(" OrderPutStart putlist : {} .", JsonUtils.TO_JSON(puts));
		
	}

}

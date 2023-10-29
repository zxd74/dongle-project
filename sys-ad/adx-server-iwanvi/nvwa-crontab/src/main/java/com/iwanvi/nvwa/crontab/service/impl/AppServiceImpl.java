package com.iwanvi.nvwa.crontab.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AppService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.FlowSourceMapper;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.AppsExample;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.FlowSourceExample;
import com.google.common.collect.FluentIterable;
import com.googlecode.protobuf.format.JsonFormat;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.AdModelsProto.App.AppMapping;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.NotifyMsgProto;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class AppServiceImpl implements AppService {

	private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	@Autowired
	private AppsMapper appsMapper;

	@Autowired
	private FlowSourceMapper flowSourceMapper;

	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Autowired
	private FlowConsumerMapper flowConsumerMapper;

	@Override
	public PubRecord buildApp(Apps app, OpType kadd) {
		AdModelsProto.App sspApp = build(app, kadd);
		if (sspApp != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(sspApp.toByteString());
			pubRecord.setEntityType(CommonProto.EntityType.kApp);
			return pubRecord.build();
		}
		return null;
	}

	@Override
	public List<Apps> getAllSspApps() {
		FlowSourceExample flowSourceExample = new FlowSourceExample();
		flowSourceExample.createCriteria().andJoinTypeEqualTo(CrontabConstants.SSP_JOIN_TYPE);
		List<FlowSource> fs = flowSourceMapper.selectByExample(flowSourceExample);
		List<String> mediaUuids = null;
		if (!CollectionUtils.isEmpty(fs)) {
			mediaUuids = FluentIterable.from(fs).transform((FlowSource flowSource) -> {
				return flowSource.getMediaUuid();
			}).toList();
		}
		if (CollectionUtils.isEmpty(mediaUuids)) {
			return null;
		}
		AppsExample example = new AppsExample();
		example.createCriteria().andAppStatusEqualTo(Constants.STATE_VALID).andStatusEqualTo(Constants.STATE_VALID)
				.andMediasIn(mediaUuids);
		// .andAuditStatusEqualTo(Constants.STATE_VALID);
		return appsMapper.selectByExample(example);
	}

	@Override
	public void buildAppSend(Integer appId, OpType opType) {
		Apps app = appsMapper.selectByPrimaryKey(appId);
		AdModelsProto.App appSend = build(app, opType);
		if (app != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_SSP_APP + appId, appSend.toByteArray());
			logger.info("ampapp cache to redis, id : {}", appId);
			auditMessageProducer.send(CommonProto.EntityType.kApp, appId, opType);
			logger.info("crontab ampapp message is send, id : {}, opType : {}", appId, opType);
		}
	}

	@SuppressWarnings("unchecked")
	private App build(Apps app, OpType opType) {
		AdModelsProto.App.Builder builder = AdModelsProto.App.newBuilder();
		builder.setAppId(app.getAppId());
		if (NotifyMsgProto.OpType.kDelete.equals(opType) || Constants.STATE_INVALID.equals(app.getStatus())
				|| Constants.STATE_INVALID.equals(app.getAppStatus())) {
			builder.setStatus(Constants.STATE_INVALID);
		} else {
			builder.setStatus(Constants.STATE_VALID);
		}
		builder.setSharing(CrontabConstants.APP_PAYDISCOUNT);
		String consumerMapped = app.getConsumerMapped();
		if (StringUtils.isNotBlank(consumerMapped)) {
			Map<Integer, String> appMapped = JsonUtils.TO_OBJ(consumerMapped, Map.class);
			if (!CollectionUtils.isEmpty(appMapped)) {
				AppMapping.Builder amb = AppMapping.newBuilder();
				appMapped.entrySet().forEach(entry -> {
					FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(entry.getKey());
					amb.setDspid(flowConsumer.getDspId());
					amb.setDspAppid(entry.getValue());
					builder.addAppMapping(amb);
				});
			}
		}
		logger.info("App : {}", JsonFormat.printToString(builder.build()));
		return builder.build();
	}

}

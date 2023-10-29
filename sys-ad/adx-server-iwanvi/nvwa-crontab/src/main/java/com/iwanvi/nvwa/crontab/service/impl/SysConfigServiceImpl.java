package com.iwanvi.nvwa.crontab.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.SysConfigService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.dao.SysConfigMapper;
import com.iwanvi.nvwa.dao.model.SysConfig;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AdCommonConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class SysConfigServiceImpl implements SysConfigService {

	private static final Logger logger = LoggerFactory.getLogger(SysConfigServiceImpl.class);

	
	@Autowired
	private SysConfigMapper sysConfigMapper;
	
	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;
	
	@Autowired
	private AuditMessageProducer auditMessageProducer;
	
	@Override
	public PubRecord buildPubRecord(OpType kadd) {
		AdModelsProto.AdCommonConfig config = buildConfig(kadd);
		if (config != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(config.toByteString());
			pubRecord.setEntityType(EntityType.kCommonConfig);
			return pubRecord.build();
		}
		return null;
	}

	private AdCommonConfig buildConfig(OpType kadd) {
		try {
			AdModelsProto.AdCommonConfig.Builder configBuilder = AdModelsProto.AdCommonConfig.newBuilder();
			String adxBidDiscount = getValueById(CrontabConstants.CONF_ADX_BID_DISCOUNT);
			configBuilder.setAdxBidDiscount(Double.valueOf(adxBidDiscount));
			return configBuilder.build();
		} catch (Exception e) {
			logger.error("build ampUnit error!", e);
			return null;
		}
	}

	private String getValueById(Integer id) {
		SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(id);
		return sysConfig.getConfigValue();
	}

	@Override
	public void buildConfigSend(OpType opType) {
		try {
			AdModelsProto.AdCommonConfig config = buildConfig(opType);
			if (config != null) {
				byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_COMMON_CONFIG, config.toByteArray());
				logger.info("commonConfig cache to redis");
				auditMessageProducer.send(CommonProto.EntityType.kCommonConfig, 0, opType);
				logger.info("crontab commonConfig message is send, opType : {}", opType);
			}
		} catch (Exception e) {
			logger.error("build ampUnit error!", e);
		}
	}

	
}

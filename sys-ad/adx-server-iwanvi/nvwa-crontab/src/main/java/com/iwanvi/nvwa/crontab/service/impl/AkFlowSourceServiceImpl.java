package com.iwanvi.nvwa.crontab.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AkFlowSourceService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.dao.FlowSourceMapper;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class AkFlowSourceServiceImpl implements AkFlowSourceService {

	private static final Logger logger = LoggerFactory.getLogger(AkFlowSourceServiceImpl.class);
	
	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;
	
	@Autowired
	private AuditMessageProducer auditMessageProducer;
	
	@Autowired
	private FlowSourceMapper flowSourceMapper;
	
	@Override
	public void buildAKAppSend(Integer objectId, OpType opType) {
        FlowSource flowSource = flowSourceMapper.selectByPrimaryKey(objectId);
        AdModelsProto.App.Builder builder = AdModelsProto.App.newBuilder();
        builder.setAppId(flowSource.getMediaUuid());
        if (Constants.STATE_VALID.equals(flowSource.getMediaState())
        		&& Constants.STATE_VALID.equals(flowSource.getRunState())) {
        	builder.setStatus(Constants.STATE_VALID);
		} else {
			builder.setStatus(Constants.STATE_INVALID);
		}        
        builder.setSharing(CrontabConstants.APP_PAYDISCOUNT);

        AdModelsProto.App appSend = builder.build();
        if (appSend != null) {
            byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_SSP_APP + objectId, appSend.toByteArray());
            logger.info("akapp cache to redis, id : {}", objectId);
            auditMessageProducer.send(CommonProto.EntityType.kApp, objectId, opType);
            logger.info("crontab akapp message is send, id : {}, opType : {}", objectId, opType);
        }
    }

	@Override
	public PubRecord buildPubRecord(FlowSource flowSource, OpType kadd) {
		AdModelsProto.App.Builder builder = AdModelsProto.App.newBuilder();
		builder.setAppId(flowSource.getMediaUuid());
        if (Constants.STATE_VALID.equals(flowSource.getMediaState()) 
        		&& Constants.STATE_VALID.equals(flowSource.getRunState())) {
        	builder.setStatus(Constants.STATE_VALID);
		} else {
			builder.setStatus(Constants.STATE_INVALID);
		}  
		builder.setSharing(CrontabConstants.APP_PAYDISCOUNT);
		AdModelsProto.App sspApp = builder.build();
		if (sspApp != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(sspApp.toByteString());
			pubRecord.setEntityType(CommonProto.EntityType.kApp);
			return pubRecord.build();
		}
		return null;
	}

}

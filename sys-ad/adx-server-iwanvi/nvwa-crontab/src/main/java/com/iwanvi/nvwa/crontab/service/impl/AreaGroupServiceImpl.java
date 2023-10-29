package com.iwanvi.nvwa.crontab.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AreaGroupService;
import com.iwanvi.nvwa.dao.AreaGroupMapper;
import com.iwanvi.nvwa.dao.AreaMapper;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.AreaExample;
import com.iwanvi.nvwa.dao.model.AreaGroup;
import com.iwanvi.nvwa.dao.model.AreaGroupExample;
import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaLevel;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class AreaGroupServiceImpl implements AreaGroupService {

	private static final Logger logger = LoggerFactory.getLogger(AreaGroupServiceImpl.class);
	
	@Autowired
	private AreaGroupMapper areaGroupMapper;
	
	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Autowired
	private AreaMapper areaMapper;
	
	@Override
	public List<AreaGroup> getList() {
		AreaGroupExample areaGroupExample = new AreaGroupExample();
		areaGroupExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID);
		return areaGroupMapper.selectByExampleWithBLOBs(areaGroupExample);
	}

	@Override
	public PubRecord buildPubRecord(AreaGroup areaGroup, OpType kadd, EntityType karealevel) {
		AdModelsProto.AreaLevel areLevel =  buildAreaGroup(areaGroup, kadd, karealevel);
		if (areLevel != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(areLevel.toByteString());
			pubRecord.setEntityType(karealevel);
			return pubRecord.build();
		}
		return null;
	}

	private AreaLevel buildAreaGroup(AreaGroup areaGroup, OpType kadd, EntityType karealevel) {
        AdModelsProto.AreaLevel.Builder builder = AdModelsProto.AreaLevel.newBuilder();
        String areaIds = areaGroup.getAreaIds();
        if (StringUtils.isBlank(areaIds)) {
			return null;
		}
        List<Integer> ids = Arrays.stream(areaIds.split(Constants.SIGN_COMMA)).map(s->Integer.parseInt(s.trim())).
                collect(Collectors.toList());
        AreaExample areaExample = new AreaExample();
        areaExample.createCriteria().andIdIn(ids).andStatusEqualTo(Constants.STATE_VALID);
        List<Area> areaList = areaMapper.selectByExample(areaExample);
        if (!CollectionUtils.isEmpty(areaList)) {
    		List<Integer> areas = null;
    		if (!CollectionUtils.isEmpty(areaList)) {
    			areas = FluentIterable.from(areaList).transform((Area area) -> {
    				return area.getAreaCode();
    			}).toList();
    		}
            builder.addAllAreaCodes(areas);
            builder.setName(areaGroup.getGroupName());
            builder.setId(areaGroup.getId());
    		return builder.build();
		}
        return null;
	}

	@Override
	public void buildAreaGroupSend(Integer objectId, OpType opType) {
		AreaGroup areaGroup = areaGroupMapper.selectByPrimaryKey(objectId);
		AdModelsProto.AreaLevel areLevel =  buildAreaGroup(areaGroup, opType, EntityType.kAreaLevel);
		if (areLevel != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_AREA_LEVEL + areaGroup.getId(), areLevel.toByteArray());
			logger.info("areaGroup cache to redis, id : {}", areaGroup.getId());
			auditMessageProducer.send(CommonProto.EntityType.kAreaLevel, areaGroup.getId(), opType);
			logger.info("crontab areaLevel message is send, id : {}, opType : {}", areaGroup.getId(), opType);
		}
		
	}

}

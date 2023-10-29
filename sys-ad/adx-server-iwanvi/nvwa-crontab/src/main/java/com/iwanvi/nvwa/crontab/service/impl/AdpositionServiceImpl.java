package com.iwanvi.nvwa.crontab.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.googlecode.protobuf.format.JsonFormat;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AdDicService;
import com.iwanvi.nvwa.crontab.service.AdpositionService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AdPositionMappingMapper;
import com.iwanvi.nvwa.dao.AdPositionPriceMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.FlowSourceMapper;
import com.iwanvi.nvwa.dao.TemplateModuleRelationMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionExample;
import com.iwanvi.nvwa.dao.model.AdPositionMapping;
import com.iwanvi.nvwa.dao.model.AdPositionMappingExample;
import com.iwanvi.nvwa.dao.model.AdPositionPrice;
import com.iwanvi.nvwa.dao.model.AdPositionPriceExample;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.FlowSourceExample;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelation;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelationExample;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AdTypeConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class AdpositionServiceImpl implements AdpositionService {

    private static final Logger logger = LoggerFactory.getLogger(AdpositionServiceImpl.class);

    @Autowired
    private AdPositionMapper adPositionMapper;

    private LoadingCache<Integer, AdPosition> adColIdCache;

    @Autowired
    private ByteArrayRedisDao byteArrayRedisDao;

    @Autowired
    private AuditMessageProducer auditMessageProducer;

    @Autowired
    private AdPositionPriceMapper adPositionPriceMapper;

    @Autowired
    private FlowSourceMapper flowSourceMapper;

    @Autowired
    private AdDicService adDicService;

    @Autowired
    private AdPositionMappingMapper adPositionMappingMapper;

    @Autowired
    private FlowConsumerMapper flowConsumerMapper;

    @Autowired
    private TemplateModuleRelationMapper templateModuleRelationMapper;

    @PostConstruct
    private void init() {
        adColIdCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<Integer, AdPosition>() {
                    @Override
                    public AdPosition load(Integer id) throws Exception {
                        AdPosition adPosition = adPositionMapper.selectByPrimaryKey(id);
                        if (Constants.STATE_VALID.equals(adPosition.getStatus())) {
                            return adPosition;
                        }
                        return null;
                    }
                });
    }

    @Override
    public List<AdPosition> getAdpositions() {
        FlowSourceExample flowSourceExample = new FlowSourceExample();
        flowSourceExample.createCriteria().andMediaStateEqualTo(Constants.STATE_VALID)
                .andRunStateEqualTo(Constants.STATE_VALID).andJoinTypeNotEqualTo(CrontabConstants.SSP_JOIN_TYPE);
        List<FlowSource> fsList = flowSourceMapper.selectByExample(flowSourceExample);
        List<String> mediaUuids = null;
        if (!CollectionUtils.isEmpty(fsList)) {
            mediaUuids = FluentIterable.from(fsList).transform((FlowSource flowSource) -> {
                return flowSource.getMediaUuid();
            }).toList();
        }
        if (CollectionUtils.isEmpty(mediaUuids)) {
            return null;
        }
        AdPositionExample adPositionExample = new AdPositionExample();
        adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andFlowUuidIn(mediaUuids);
        return adPositionMapper.selectByExample(adPositionExample);
    }

    @Override
    public AdPosition getAdPosition(Integer id) {
        try {
            if (id != null) {
                return adColIdCache.get(id);
            } else {
                return null;
            }
        } catch (ExecutionException e) {
            throw new ServiceException("getAdPosition by id error!", e);
        }
    }

    @Override
    public void buildPosSend(Integer objectId, OpType opType) {
        AdPosition positions = adPositionMapper.selectByPrimaryKey(objectId);
        AdModelsProto.SspAdPosition position = buildPosition(positions, opType);
        if (position != null) {
            byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_SSP_POS + positions.getId(), position.toByteArray());
            logger.info("pos cache to redis, id : {}", positions.getId());
            auditMessageProducer.send(CommonProto.EntityType.kSspAdPosition, positions.getId(), opType);
            logger.info("crontab pos message is send, id : {}, opType : {}", positions.getId(), opType);
        }
    }

    @Override
    public List<AdPosition> getValidPosList() {
        FlowSourceExample flowSourceExample = new FlowSourceExample();
        flowSourceExample.createCriteria().andMediaStateEqualTo(Constants.STATE_VALID)
                .andRunStateEqualTo(Constants.STATE_VALID).andJoinTypeEqualTo(CrontabConstants.SSP_JOIN_TYPE);
        List<String> mediaUuids = null;
        List<FlowSource> fsList = flowSourceMapper.selectByExample(flowSourceExample);
        if (!CollectionUtils.isEmpty(fsList)) {
            mediaUuids = FluentIterable.from(fsList).transform((FlowSource flowSource) -> {
                return flowSource.getMediaUuid();
            }).toList();
        }
        AdPositionExample example = new AdPositionExample();
        List<AdPosition> selectByExample = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mediaUuids)) {
            example.createCriteria().andFlowUuidIn(mediaUuids).andStatusEqualTo(Constants.STATE_VALID);
            selectByExample = adPositionMapper.selectByExample(example);
        }
        example = new AdPositionExample();
        example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andFlowUuidEqualTo(CrontabConstants.AK_UUID);
        List<AdPosition> selectByExample2 = adPositionMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(selectByExample)) {
            return selectByExample2;
        } else {
            selectByExample.addAll(selectByExample2);
            return selectByExample;
        }
    }

    @Override
    public PubRecord buildPubRecord(AdPosition position, OpType kadd) {
        AdModelsProto.SspAdPosition sspAdPosition = buildPosition(position, kadd);
        if (sspAdPosition != null) {
            AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
            pubRecord.setData(sspAdPosition.toByteString());
            pubRecord.setEntityType(CommonProto.EntityType.kSspAdPosition);
            return pubRecord.build();
        }
        return null;
    }

    @SuppressWarnings({"deprecation"})
    private SspAdPosition buildPosition(AdPosition position, OpType kadd) {
        AdModelsProto.SspAdPosition.Builder builder = AdModelsProto.SspAdPosition.newBuilder();
        AdPositionPriceExample adPositionPriceExample = new AdPositionPriceExample();
        adPositionPriceExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID)
                .andAreaLevelEqualTo(Constants.INTEGER_1).andIndustryEqualTo(Constants.INTEGER_1)
                .andPositionIdEqualTo(position.getId());
        List<AdPositionPrice> selectByExample = adPositionPriceMapper.selectByExample(adPositionPriceExample);
        if (!CollectionUtils.isEmpty(selectByExample)) {
            builder.setBidfloor((new Double(selectByExample.get(Constants.INTEGER_0).getPrice())).intValue());
        }
        builder.setAdType(CommonProto.CreativeType.valueOf((adDicService.getDic(position.getType()).getEnumValue())));
        builder.setAdPosId(position.getUuid());
        Integer status = Constants.STATE_INVALID;
        if (Constants.STATE_VALID.equals(position.getStatus())) {
            status = Constants.STATE_VALID;
        }

        if (StringUtils.isNotBlank(position.getTemplateId())) {// 模板ID
            List<String> tids = Arrays.asList(position.getTemplateId().split(Constants.SIGN_COMMA)).stream()
                    .filter(action -> StringUtils.isNotBlank(action)).collect(Collectors.toList());
            builder.addAllTemplateId(tids);
        }

        // 广告位映射
        AdPositionMappingExample adPositionMappingExample = new AdPositionMappingExample();
        adPositionMappingExample.createCriteria().andAdPositionIdEqualTo(position.getId())
                .andStatusEqualTo(Constants.STATE_VALID);
        List<AdPositionMapping> list = adPositionMappingMapper.selectByExample(adPositionMappingExample);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(action -> {
                Integer width = action.getWidth();
                Integer height = action.getHeight();

                SspAdPosition.AdPositionMapping.Builder admb = SspAdPosition.AdPositionMapping.newBuilder();
                Integer mapperType = action.getMapperType();
                if (mapperType != null) {
                    admb.setDspAdType(
                            CommonProto.CreativeType.forNumber((adDicService.getDic(mapperType).getEnumValue())));
                }
                admb.setDspAdPositionId(action.getConsumerPositionId());
                if (width != null && width > 0)
                    admb.setWidth(width);
                if (height != null && height > 0)
                    admb.setHeight(height);
                Integer id = action.getFlowConsumerId();
                FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(id);
                admb.setDspId(flowConsumer.getDspId());
                // 模板
                if (action.getTemplateId() != null) {
                    admb.setTemplateId(String.valueOf(action.getTemplateId()));
                    TemplateModuleRelationExample example = new TemplateModuleRelationExample();
                    example.createCriteria().andTIdEqualTo(action.getTemplateId());
                    List<TemplateModuleRelation> tmrs = templateModuleRelationMapper.selectByExample(example);
                    for (TemplateModuleRelation tmr : tmrs) {
                        if (tmr.getmId() == 4) {
                            admb.setCreativeStyle(Constants.INTEGER_3); // 视频
                            break;
                        }
                        if (tmr.getmId() > 5 && tmr.getmId() != 8) {
                            admb.setCreativeStyle(Constants.INTEGER_2); // 多图
                            break;
                        }
                        if (tmr.getmId() == 5) {
                            admb.setCreativeStyle(Constants.INTEGER_1); // 单图
                        }
                    }
                } else {
                    int type = position.getType();
                    if (type == CrontabConstants.AD_TYPE_VIDEO || type == CrontabConstants.AD_TYPE_VIDEO_INCENTIVE) {
                        admb.setCreativeStyle(Constants.INTEGER_3);
                    } else if (type == CrontabConstants.AD_TYPE_PIC || type == CrontabConstants.AD_TYPE_SCREEN
                            || type == CrontabConstants.AD_TYPE_DYNAMIC) {
                        admb.setCreativeStyle(Constants.INTEGER_1);
                    }
                }

                // 底价
                Float dspFloor = action.getDspFloor() == null ? null : Float.valueOf(action.getDspFloor().toString());
                if (dspFloor != null) {
                    admb.setDspFloor(dspFloor);
                }

                builder.addAdPositionMappings(admb);
            });
        }

        builder.setStatus(status);
        logger.info("SspAdPosition : {}", JsonFormat.printToString(builder.build()));
        return builder.build();
    }

    @Override
    public PubRecord buildCommonPubRecord(AdPosition adCollection, OpType kadd) {
        AdModelsProto.AdTypeConfig adTypeConfig = buildCollection(adCollection, kadd);
        if (adTypeConfig != null) {
            AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
            pubRecord.setData(adTypeConfig.toByteString());
            pubRecord.setEntityType(EntityType.kAdTypeConfig);
            return pubRecord.build();
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private AdTypeConfig buildCollection(AdPosition adCollection, OpType kadd) {
        try {
            // if (Constants.COST_TYPE_CPC.equals(adCollection.getCostType())) {
            AdModelsProto.AdTypeConfig.Builder configBuilder = AdModelsProto.AdTypeConfig.newBuilder();
            configBuilder.setAdTypeId(adCollection.getId());
            if (Constants.COST_TYPE_CPC.equals(adCollection.getSellType())) {
                configBuilder.setAdPositionType(CommonProto.AdPositionType
                        .valueOf(adDicService.getDic(adCollection.getSellType()).getEnumValue()));
            }
            configBuilder.setDefaultCtr(CrontabConstants.ADP_DEFAULTCTR);

            AdModelsProto.AdTypeConfig.AdxConfig.Builder adxConfigBuilder = AdModelsProto.AdTypeConfig.AdxConfig
                    .newBuilder();
            String flowUuid = adCollection.getFlowUuid();
            FlowSourceExample example = new FlowSourceExample();
            example.createCriteria().andMediaStateEqualTo(Constants.STATE_VALID)
                    .andRunStateEqualTo(Constants.STATE_VALID).andMediaUuidEqualTo(flowUuid);
            List<FlowSource> list = flowSourceMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(list)) {
                logger.info("get adxid error! id: {}", adCollection.getId());
                return null;
            }
            if (CrontabConstants.AK_UUID.equals(list.get(0).getMediaUuid())) {
                adxConfigBuilder.setAdxType(CommonProto.AdxType.kAdxTypeUnKnown);
            } else {
                adxConfigBuilder.setAdxType(
                        CommonProto.AdxType.valueOf(adDicService.getDic(list.get(0).getId()).getEnumValue()));// dic没有枚举值
            }
            adxConfigBuilder.setCtr(CrontabConstants.ADP_ADXCTR);
            configBuilder.addAdxConf(adxConfigBuilder.build());

            return configBuilder.build();
        } catch (Exception e) {
            logger.error("build adCollection error! id: {}", adCollection.getId());
            return null;
        }
    }

    @Override
    public void refreshCollection(Integer objectId) throws ServiceException {
        adColIdCache.refresh(objectId);
    }

    @Override
    public void buildCollectionSend(Integer objectId, OpType opType) {
        try {
            AdPosition adCollection = adColIdCache.get(objectId);
            AdModelsProto.AdTypeConfig adTypeConfig = buildCollection(adCollection, opType);
            if (adTypeConfig != null) {
                byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_AD_TYPE_CONFIG + adCollection.getId(),
                        adTypeConfig.toByteArray());
                logger.info("adCollection cache to redis, id : {}", adCollection.getId());
                auditMessageProducer.send(CommonProto.EntityType.kAdTypeConfig, adCollection.getId(), opType);
                logger.info("crontab adCollection message is send, id : {}, opType : {}", adCollection.getId(), opType);
            }
        } catch (ExecutionException e) {
            logger.error("build adCollection error! id: {}", objectId);
        }
    }

}

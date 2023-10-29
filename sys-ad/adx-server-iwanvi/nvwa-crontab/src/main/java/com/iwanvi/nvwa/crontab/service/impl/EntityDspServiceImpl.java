package com.iwanvi.nvwa.crontab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.EntityDspService;
import com.iwanvi.nvwa.crontab.util.DspDataUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AdPositionPriceMapper;
import com.iwanvi.nvwa.dao.AdvertiserDspMapper;
import com.iwanvi.nvwa.dao.DspCreativeMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.IndustryMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionPrice;
import com.iwanvi.nvwa.dao.model.AdPositionPriceExample;
import com.iwanvi.nvwa.dao.model.AdvertiserDsp;
import com.iwanvi.nvwa.dao.model.AdvertiserDspExample;
import com.iwanvi.nvwa.dao.model.DspCreative;
import com.iwanvi.nvwa.dao.model.DspCreativeExample;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.Industry;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative.Builder;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.NotifyMsgProto;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class EntityDspServiceImpl implements EntityDspService {

	private static final Logger logger = LoggerFactory.getLogger(EntityDspServiceImpl.class);

	@Autowired
	private AdvertiserDspMapper advertiserDspMapper;

	@Autowired
	private DspCreativeMapper dspCreativeMapper;

	@Autowired
	private FlowConsumerMapper flowConsumerMapper;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;
	
	@Autowired
	private AdPositionPriceMapper adPositionPriceMapper;
	
	@Autowired
	private AdPositionMapper adPositionMapper;
	
	@Autowired
	private IndustryMapper industryMapper;

	@Override
	public void buildPosSend(Integer objectId, OpType opType) {
		DspCreative dspCreative = dspCreativeMapper.selectByPrimaryKey(objectId);
		AdModelsProto.DspCreative ngxDspCreative = buildDspCreatives(dspCreative, opType);

		if (ngxDspCreative != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_CONSIMER_ENTITY + dspCreative.getId(),
					ngxDspCreative.toByteArray());
			byteArrayRedisDao.setWithExpiredByDays(
					String.format("dspc:%s:%s", ngxDspCreative.getDspId(), ngxDspCreative.getCreativeId()),
					ngxDspCreative.toByteArray(), Constants.EXPIRED_DAYS_DSP_ENTITY);
			logger.info("dspCreative cache to redis, id : {}", objectId);
			auditMessageProducer.send(CommonProto.EntityType.kDspCreative, objectId, opType);
			logger.info("crontab pos message is send, id : {}, opType : {}", objectId, opType);
		}
	}

	private com.iwanvi.nvwa.proto.AdModelsProto.DspCreative buildDspCreatives(DspCreative dspCreative, OpType opType) {
		AdModelsProto.DspCreative toNgxDspCreative = DspDataUtils.convertToNgxDspCreative(dspCreative);

		Builder newBuilder = AdModelsProto.DspCreative.newBuilder(toNgxDspCreative);
		AdvertiserDsp advertiserDsp = advertiserDspMapper.selectByPrimaryKey(dspCreative.getAdvertiserId());
		newBuilder.setIndustry(advertiserDsp.getIndustryType());

		if (dspCreative.getIndustry() != null) {
			int parentId = getParentIndustry(dspCreative.getIndustry());
			newBuilder.setIndustry(parentId);
		}
		FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(advertiserDsp.getFlowConsumerId());

		if (flowConsumer != null)
			newBuilder.setDspId(flowConsumer.getDspId());
		if (!NotifyMsgProto.OpType.kDelete.equals(opType) &&  Constants.STATE_VALID.equals(advertiserDsp.getStatus())
				&& Constants.STATE_VALID.equals(dspCreative.getStatus())
				&& Constants.STATE_VALID.equals(advertiserDsp.getExaminesStatus())
				&& Constants.STATE_VALID.equals(dspCreative.getExaminesStatus())) {
			newBuilder.setStatus(Constants.STATE_VALID);
		} else {
			newBuilder.setStatus(Constants.STATE_INVALID);
		}
		return newBuilder.build();
	}

	private int getParentIndustry(Integer id) {
		Industry industry = industryMapper.selectByPrimaryKey(id);
		if (industry.getParentId() != Constants.INTEGER_0) {
			Industry parentIndustry = industryMapper.selectByPrimaryKey(industry.getParentId());
			getParentIndustry(parentIndustry.getId());
		}
		return id;
	}

	@Override
	public List<DspCreative> buildEntiyDspList(Integer id) {
		List<DspCreative> list = null;
		AdvertiserDspExample example = new AdvertiserDspExample();
		example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andExaminesStatusEqualTo(Constants.STATE_VALID)
				.andFlowConsumerIdEqualTo(id);
		List<AdvertiserDsp> advertiserDsps = advertiserDspMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(advertiserDsps)) {
			List<Integer> advers = FluentIterable.from(advertiserDsps).transform((AdvertiserDsp advertiserDsp) -> {
				return advertiserDsp.getId();
			}).toList();
			DspCreativeExample creativeExample = new DspCreativeExample();
			creativeExample.createCriteria().andExaminesStatusEqualTo(Constants.STATE_VALID)
					.andStatusEqualTo(Constants.STATE_VALID).andAdvertiserIdIn(advers);
			list = dspCreativeMapper.selectByExample(creativeExample);
		}
		return list;
	}

	@Override
	public PubRecord buildDspCreative(DspCreative dC, OpType kadd) {
		AdModelsProto.DspCreative ngxDspCreative = buildDspCreatives(dC, kadd);
		if (ngxDspCreative != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(ngxDspCreative.toByteString());
			pubRecord.setEntityType(CommonProto.EntityType.kDspCreative);
			return pubRecord.build();
		}
		return null;
	}

	@Override
	public List<AdPositionFloorPrice> buildAdPositionFloorPriceByDspCreative(DspCreative dspCreative) {
		int adPositionId = dspCreative.getPositionId();
		AdvertiserDsp advertiserDsp = advertiserDspMapper.selectByPrimaryKey(dspCreative.getAdvertiserId());
		if (advertiserDsp == null)
			return null;

		int industry = advertiserDsp.getIndustryType();
		AdPositionPriceExample example = new AdPositionPriceExample();
		example.createCriteria().andIndustryEqualTo(industry).andPositionIdEqualTo(adPositionId)
				.andStatusEqualTo(Constants.STATE_VALID);

		List<AdPositionPrice> adPositionPriceList = adPositionPriceMapper.selectByExample(example);
		if (adPositionPriceList == null || adPositionPriceList.isEmpty()) {
			return null;
		}

		List<AdPositionFloorPrice> syncAdPositionFloorPriceList = new ArrayList<AdPositionFloorPrice>();
		adPositionPriceList.forEach(adpp -> {
			AdPosition adp = adPositionMapper.selectByPrimaryKey(adpp.getPositionId());
			AdPositionFloorPrice.Builder adpfp = AdPositionFloorPrice.newBuilder().setAdPositionId(adp.getUuid())
					.setAreaLevelId(adpp.getAreaLevel()).setIndustry(adpp.getIndustry()).setFloorPrice(adpp.getPrice());
			syncAdPositionFloorPriceList.add(adpfp.build());
		});

		return syncAdPositionFloorPriceList;
	}

}

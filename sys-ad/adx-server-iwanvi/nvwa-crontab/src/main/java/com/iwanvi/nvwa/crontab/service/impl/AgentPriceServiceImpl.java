package com.iwanvi.nvwa.crontab.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AdDicService;
import com.iwanvi.nvwa.crontab.service.AgentPriceService;
import com.iwanvi.nvwa.dao.AgentPriceMapper;
import com.iwanvi.nvwa.dao.AreaGroupMapper;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.FlowSourceMapper;
import com.iwanvi.nvwa.dao.PlanMapper;
import com.iwanvi.nvwa.dao.model.AgentPrice;
import com.iwanvi.nvwa.dao.model.AgentPriceExample;
import com.iwanvi.nvwa.dao.model.AgentPriceExample.Criteria;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.AreaGroup;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.FlowSourceExample;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaLevel;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class AgentPriceServiceImpl implements AgentPriceService {

	private static final Logger logger = LoggerFactory.getLogger(AgentPriceServiceImpl.class);

	@Autowired
	private AgentPriceMapper agentPriceMapper;

	@Autowired
	private FlowSourceMapper flowSourceMapper;

	@Autowired
	private AdDicService adDicService;

	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Autowired
	private AreaGroupMapper areaGroupMapper;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private PlanMapper planMapper;

	@Override
	public List<AgentPrice> getAgentPrice(Put put, Integer aid, Integer industryType) {
		logger.info("!!!!!!!!!!crontab put is :{}", put.getId());
		if (put.getAdPosition() != null) {
			Company company = companyMapper.selectByPrimaryKey(aid);
			if (company.getAgType() == 1) { // 直客
				return null;
			}
			AgentPriceExample example = new AgentPriceExample();
			Criteria criteria = example.createCriteria().andAidEqualTo(aid).andIndustryidEqualTo(industryType)
					.andPidEqualTo(put.getAdPosition()).andStatusEqualTo(Constants.STATE_VALID);

			List<Integer> midList = new ArrayList<>();
			if (StringUtils.isNotBlank(put.getDxMedia())) {
				String[] uuids = put.getDxMedia().split(Constants.SIGN_COMMA);
				for (String uuid : uuids) {
					if (StringUtils.isNotBlank(uuid)) {
						FlowSourceExample flowSourceExample = new FlowSourceExample();
						flowSourceExample.createCriteria().andMediaStateEqualTo(Constants.STATE_VALID)
								.andMediaUuidEqualTo(uuid);// TODO 是媒体的id还是媒体的UUID
						List<FlowSource> devMedias = flowSourceMapper.selectByExample(flowSourceExample);
						if (!devMedias.isEmpty() && devMedias.size() > 0) {
							midList.add(devMedias.get(0).getId());
						}
					}
				}
				criteria.andMidIn(midList);
			}

			List<AgentPrice> agentPrices = agentPriceMapper.selectByExample(example);
			return agentPrices;
		}
		return null;
	}

	@Override
	public PubRecord buildPubRecord(AgentPrice agentPrice, OpType opType) {
		AgentFloorPriceConfig agentFloorPrice = buildAgentPrice(agentPrice, opType);
		if (agentFloorPrice != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(agentFloorPrice.toByteString());
			pubRecord.setEntityType(EntityType.kAgentFloorPriceConfig);
			return pubRecord.build();
		}
		return null;
	}

	private AgentFloorPriceConfig buildAgentPrice(AgentPrice agentPrice, OpType opType) {
		try {
			logger.info("build agentPrice : {}, opType : {}", agentPrice, opType);
			AgentFloorPriceConfig.Builder agentPriceBuilder = AgentFloorPriceConfig.newBuilder();
			if (!OpType.kDelete.equals(opType)) {
				if (agentPrice.getAid() != null) {
					agentPriceBuilder.setAgentId(agentPrice.getAid());
				}
				if (agentPrice.getPid() != null) {
					agentPriceBuilder.setAdPosId(agentPrice.getPid());
				}
				if (agentPrice.getIndustryid() != null) {
					agentPriceBuilder.setIndustryId(agentPrice.getIndustryid());
				}
				if (agentPrice.getMid() != null) {
					FlowSource devMedia = flowSourceMapper.selectByPrimaryKey(agentPrice.getMid());
					if (devMedia != null && devMedia.getMediaUuid() != null) {
						agentPriceBuilder.setMediaUuid(devMedia.getMediaUuid());
					}
				}
				if (agentPrice.getPrice() != null) {
					agentPriceBuilder.setFloorPrice(agentPrice.getPrice());
				}
				if (agentPrice.getProfitMargin() != null) {
					agentPriceBuilder.setProfitMargin(agentPrice.getProfitMargin());
				}
				if (agentPrice.getAreaLevel() != null) {
					AreaGroup areaGroup = areaGroupMapper.selectByPrimaryKey(agentPrice.getAreaLevel());
					agentPriceBuilder.setAreaLevelId(agentPrice.getAreaLevel());
				}

				return agentPriceBuilder.build();
			}
			return null;
		} catch (Exception e) {
			logger.error("build agentPrice error! id: {}", agentPrice.getId(), e);
			return null;
		}
	}

	@Override
	public void buildAgentPriceSend(Integer id, OpType opType) {
		AgentPrice agentPrice = agentPriceMapper.selectByPrimaryKey(id);
		AgentFloorPriceConfig agentFloorPrice = buildAgentPrice(agentPrice, opType);
		if (agentFloorPrice != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_AGENT_FLOOR_PRICE_CONFIG + agentPrice.getId(),
					agentFloorPrice.toByteArray());
			logger.info("agentPrice cache to redis, id : {}", agentPrice.getId());
			auditMessageProducer.send(EntityType.kAgentFloorPriceConfig, agentPrice.getId(), opType);
			logger.info("crontab agentPrice message is send, id : {}, opType : {}", agentPrice.getId(), opType);
		}
	}

	@Override
	public List<AgentPrice> getOrderAgentPrice(OrderPut orderPut, Integer aid, Integer industryType) {
		logger.info("!!!!!!!!!!crontab orderPut is :{}", orderPut.getId());
		if (orderPut.getAdPosition() != null) {
			AgentPriceExample example = new AgentPriceExample();
			example.createCriteria().andAidEqualTo(aid).andIndustryidEqualTo(industryType)
					.andPidEqualTo(orderPut.getAdPosition()).andStatusEqualTo(Constants.STATE_VALID)
					.andMidEqualTo(Constants.FLOW_MEDIA_AIKA);
			List<AgentPrice> agentPrices = agentPriceMapper.selectByExample(example);
			return agentPrices;
		}
		return null;
	}

	@Override
	public void buildAgentPriceSend(Put ampUnit, OpType opType) {
		Plan plan = planMapper.selectByPrimaryKey(ampUnit.getPid());
		if (ampUnit == null || plan == null || plan.getAdverId() == null) {
			return;
		}
		Company company = companyMapper.selectByPrimaryKey(plan.getAdverId());

		if (company == null || company.getAid() == null) {
			return;
		}
		Company agent = companyMapper.selectByPrimaryKey(company.getAid());
		if (agent.getAgType() == 1) { // 如果是直客
			return;
		}
		AgentPriceExample agentPriceExample = new AgentPriceExample();
		agentPriceExample.createCriteria().andAidEqualTo(company.getAid())
				.andIndustryidEqualTo(company.getIndustryType());
		List<AgentPrice> ampAgentPrices = agentPriceMapper.selectAgentPrice(agentPriceExample);
		if (!ampAgentPrices.isEmpty() && ampAgentPrices.size() > 0) {
			for (AgentPrice agentPrice : ampAgentPrices) {
				AgentFloorPriceConfig agentFloorPrice = buildAgentPrice(agentPrice, opType);
				if (agentFloorPrice != null) {
					byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_AGENT_FLOOR_PRICE_CONFIG + agentPrice.getId(),
							agentFloorPrice.toByteArray());
					logger.info("agentPrice cache to redis, id : {}", agentPrice.getId());
					auditMessageProducer.send(EntityType.kAgentFloorPriceConfig, agentPrice.getId(), opType);
					logger.info("crontab agentPrice message is send, id : {}, opType : {}", agentPrice.getId(), opType);
				}
			}
		}

	}
}

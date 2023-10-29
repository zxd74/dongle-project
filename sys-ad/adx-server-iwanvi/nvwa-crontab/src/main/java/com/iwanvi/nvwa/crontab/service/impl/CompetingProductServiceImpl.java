package com.iwanvi.nvwa.crontab.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.CompetingProductService;
import com.iwanvi.nvwa.dao.CompetingProductsMapper;
import com.iwanvi.nvwa.dao.model.CompetingProducts;
import com.iwanvi.nvwa.dao.model.CompetingProductsExample;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class CompetingProductServiceImpl implements CompetingProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(CompetingProductServiceImpl.class);
	
	@Autowired
	private CompetingProductsMapper competingProductsMapper;
	
	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Override
	public List<CompetingProducts> getList() {
		CompetingProductsExample competingProductsExample = new CompetingProductsExample();
		competingProductsExample.createCriteria().andIsDeletedNotEqualTo(true);
		List<CompetingProducts> res = competingProductsMapper.selectByExample(competingProductsExample);
		return res;
	}

	@Override
	public PubRecord buildPubRecord(CompetingProducts competingProducts, OpType kadd, EntityType entityType) {
		if (StringUtils.isBlank(competingProducts.getProductsName())) {
			return null;
		}
		AdModelsProto.CompetingProduct.Builder builder = AdModelsProto.CompetingProduct.newBuilder();
		builder.setKeyword(competingProducts.getProductsName());
		AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
		pubRecord.setData(builder.build().toByteString());
		pubRecord.setEntityType(entityType);
		return pubRecord.build();
	}

	@Override
	public void buildCompetingProductsSend(Integer objectId, OpType opType) {
		// TODO Auto-generated method stub
		
	}

}

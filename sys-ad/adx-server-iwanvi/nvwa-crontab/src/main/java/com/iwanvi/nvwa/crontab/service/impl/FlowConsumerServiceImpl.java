package com.iwanvi.nvwa.crontab.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.iwanvi.nvwa.crontab.service.AdDicService;
import com.iwanvi.nvwa.crontab.service.FlowConsumerService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.crontab.util.DspDataUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AppChannelMapper;
import com.iwanvi.nvwa.dao.AreaMapper;
import com.iwanvi.nvwa.dao.BookCategoryMapper;
import com.iwanvi.nvwa.dao.ConsumerPositionPriceMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionExample;
import com.iwanvi.nvwa.dao.model.AppChannel;
import com.iwanvi.nvwa.dao.model.AppChannelExample;
import com.iwanvi.nvwa.dao.model.AreaExample;
import com.iwanvi.nvwa.dao.model.BookCategory;
import com.iwanvi.nvwa.dao.model.BookCategoryExample;
import com.iwanvi.nvwa.dao.model.ConsumerPositionPrice;
import com.iwanvi.nvwa.dao.model.ConsumerPositionPriceExample;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.FlowConsumerExample;
import com.google.common.collect.FluentIterable;
import com.googlecode.protobuf.format.JsonFormat;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.CommonProto;
import com.iwanvi.nvwa.proto.NotifyMsgProto;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp.AuditType;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp.Builder;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp.DspAdPositionPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class FlowConsumerServiceImpl implements FlowConsumerService {

	private static final Logger logger = LoggerFactory.getLogger(FlowConsumerServiceImpl.class);

	@Autowired
	private FlowConsumerMapper flowConsumerMapper;

	@Autowired
	private AdPositionMapper adPositionMapper;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;

	@Autowired
	private AreaMapper areaMapper;

	@Autowired
	private BookCategoryMapper bookCategoryMapper;

	@Autowired
	private ConsumerPositionPriceMapper consumerPositionPriceMapper;

	@Autowired
	private AppChannelMapper appChannelMapper;

	@Autowired
	private AdDicService adDicService;

	@Override
	public void buildPosSend(Integer objectId, OpType opType) {
		FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(objectId);
		AdModelsProto.Dsp dsp = buildDsp(flowConsumer, opType, objectId);
		if (dsp != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_FLOW_CONSIMER + flowConsumer.getId(), dsp.toByteArray());
			logger.info("flowConsumer cache to redis, id : {}", flowConsumer.getId());
			// 不能同步广告平台类型为sdk的广告平台到引擎
			if (flowConsumer.getConsumerType() != null && flowConsumer.getConsumerType() != 97) {
				auditMessageProducer.send(CommonProto.EntityType.kDsp, flowConsumer.getId(), opType);
				logger.info("crontab pos message is send, id : {}, opType : {}", flowConsumer.getId(), opType);
			}
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private Dsp buildDsp(FlowConsumer flowConsumer, OpType opType, Integer objectId) {
		Dsp toNgxDsp = DspDataUtils.convertToNgxDsp(flowConsumer);
		Builder builder = Dsp.newBuilder(toNgxDsp);
		if (!NotifyMsgProto.OpType.kDelete.equals(opType)) {
			if (flowConsumer.getRunState() == Constants.STATE_VALID.intValue()
					&& flowConsumer.getConsumerState() == Constants.STATE_VALID.intValue()) {
				builder.setStatus(true);
			} else {
				builder.setStatus(false);
			}
		} else {
			builder.setStatus(false);
		}
		if (flowConsumer.getIsCheck() != null) {
			builder.setAuditType(AuditType.valueOf(adDicService.getDic(flowConsumer.getIsCheck()).getEnumValue()));// 审核
		}
		if (CrontabConstants.IS_DEAL.equals(flowConsumer.getIsDeal())) {
			builder.setIsFixedPrice(true);
			List<DspAdPositionPrice> list = buildDspAdPositionPrices(flowConsumer.getId());
			builder.addAllDspAdPositionPrice(list);
		} else {
			builder.setIsFixedPrice(false);
		}
		AdModelsProto.DspTarget.Builder dspTarget = AdModelsProto.DspTarget.newBuilder();
		if (StringUtils.isNotBlank(flowConsumer.getAdposId())) {
			Map<String, List<Integer>> adp = JsonUtils.TO_OBJ(flowConsumer.getAdposId(), Map.class);
			List<Integer> dx_area = adp.get("dx_area");
			List<Integer> dx_adposion = adp.get("dx_adposion");
			List<Integer> dx_book = adp.get("dx_book");
			List<Integer> dx_channel = adp.get("dx_channel");
			if (!CollectionUtils.isEmpty(dx_adposion)) {
				AdPositionExample adPositionExample = new AdPositionExample();
				adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andIdIn(dx_adposion);
				List<AdPosition> example = adPositionMapper.selectByExample(adPositionExample);
				if (!CollectionUtils.isEmpty(example)) {
					List<String> adpUuids = FluentIterable.from(example).transform((AdPosition adPosition) -> {
						return adPosition.getUuid();
					}).toList();
					AdModelsProto.AdPositionTarget.Builder adpt = AdModelsProto.AdPositionTarget.newBuilder();
					adpt.addAllTagids(adpUuids);
					dspTarget.addAdPosTargets(adpt);
				}
			}
			if (!CollectionUtils.isEmpty(dx_area)) {
				AreaExample areaExample = new AreaExample();
				areaExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andIdIn(dx_book);
				List<Area> example = areaMapper.selectByExample(areaExample);
				if (!CollectionUtils.isEmpty(example)) {
					List<Integer> areaCode = FluentIterable.from(example).transform((Area a) -> {
						return a.getAreaCode();
					}).toList();
					AdModelsProto.AreaTarget.Builder area = AdModelsProto.AreaTarget.newBuilder();
					area.addAllAreaCode(areaCode);
					dspTarget.addAreaTargets(area);
				}
			}
			if (!CollectionUtils.isEmpty(dx_book)) {
				dx_book = buildbooks(dx_book);
				BookCategoryExample bookCategoryExample = new BookCategoryExample();
				bookCategoryExample.createCriteria().andIdIn(dx_book);
				List<BookCategory> example = bookCategoryMapper.selectByExample(bookCategoryExample);
				if (!CollectionUtils.isEmpty(example)) {
					List<String> books = FluentIterable.from(example).transform((BookCategory b) -> {
						return b.getName();
					}).toList();
					AdModelsProto.BookCategoryTarget.Builder bct = AdModelsProto.BookCategoryTarget.newBuilder();
					bct.addAllCategoryIds(books);
					dspTarget.addBookCategoryTargets(bct);
				}
			}
			if (!CollectionUtils.isEmpty(dx_channel)) {
				AppChannelExample appChannelExample = new AppChannelExample();
				appChannelExample.createCriteria().andIdIn(dx_channel).andIsDeletedEqualTo(false);
				List<AppChannel> list = appChannelMapper.selectByExample(appChannelExample);
				if (!CollectionUtils.isEmpty(list)) {
					List<String> cs = FluentIterable.from(list).transform((AppChannel c) -> {
						return c.getName();
					}).toList();
					AdModelsProto.ChannelTarget.Builder ac = AdModelsProto.ChannelTarget.newBuilder();
					ac.addAllChannelIds(cs);
					dspTarget.setChannelTarget(ac);
				}
			}

		}
		String dids = flowConsumer.getDxDid();
		if (StringUtils.isNotBlank(dids)) {
			List<String> didList = Arrays.asList(dids.split(Constants.SIGN_COMMA)).stream()
					.collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(didList)) {
				AdModelsProto.DeviceTarget.Builder ad = AdModelsProto.DeviceTarget.newBuilder();
				ad.addAllDid(didList);
				dspTarget.setDeviceTarget(ad);
			}
		}
		builder.setDspTarget(dspTarget);
		logger.info("Dsp : {}", JsonFormat.printToString(builder.build()));
		return builder.build();
	}

	private List<DspAdPositionPrice> buildDspAdPositionPrices(Integer id) {
		List<DspAdPositionPrice> result = new ArrayList<AdModelsProto.Dsp.DspAdPositionPrice>();
		ConsumerPositionPriceExample consumerPositionPriceExample = new ConsumerPositionPriceExample();
		consumerPositionPriceExample.createCriteria().andCidEqualTo(id);
		List<ConsumerPositionPrice> list = consumerPositionPriceMapper.selectByExample(consumerPositionPriceExample);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		list.forEach(action -> {
			Dsp.DspAdPositionPrice.Builder builder = Dsp.DspAdPositionPrice.newBuilder();
			AdPositionExample adPositionExample = new AdPositionExample();
			adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andIdEqualTo(action.getPid());
			List<AdPosition> adps = adPositionMapper.selectByExample(adPositionExample);
			if (CollectionUtils.isEmpty(adps)) {
				return;
			}
			builder.setPrice(action.getPrice());
			builder.setTagid(adps.get(0).getUuid());
			result.add(builder.build());
		});
		return result;
	}

	private List<Integer> buildbooks(List<Integer> dx_book) {
		List<Integer> sonBooks = new ArrayList<Integer>();
		dx_book.forEach(action -> {
			BookCategoryExample bookCategoryExample = new BookCategoryExample();
			bookCategoryExample.createCriteria().andParentIdEqualTo(action);
			List<BookCategory> list = bookCategoryMapper.selectByExample(bookCategoryExample);
			if (CollectionUtils.isEmpty(list)) {
				sonBooks.add(action);
			}
		});
		return sonBooks;
	}

	@Override
	public List<FlowConsumer> getAllFc() {
		FlowConsumerExample consumerExample = new FlowConsumerExample();
		// 这里不能同步sdk类型的广告平台到广告引擎
		consumerExample.createCriteria().andConsumerStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID).andConsumerTypeNotEqualTo(97);

		List<FlowConsumer> example = flowConsumerMapper.selectByExample(consumerExample);
		return example;
	}

	@Override
	public PubRecord buildFlowConsumer(FlowConsumer flowConsumer, OpType kadd) {
		AdModelsProto.Dsp dsp = buildDsp(flowConsumer, kadd, flowConsumer.getId());
		if (dsp != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(dsp.toByteString());
			pubRecord.setEntityType(CommonProto.EntityType.kDsp);
			return pubRecord.build();
		}
		return null;
	}
}

/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.dao.DspAdvertiserMapper;
import com.iwanvi.nvwa.dao.DspCreativeMapper;
import com.iwanvi.nvwa.dao.SysCrontabMapper;
import com.iwanvi.nvwa.dao.model.DspAdvertiser;
import com.iwanvi.nvwa.dao.model.DspAdvertiserExample;
import com.iwanvi.nvwa.dao.model.DspCreative;
import com.iwanvi.nvwa.dao.model.DspCreativeExample;
import com.iwanvi.nvwa.dao.model.SysCrontab;
import com.iwanvi.nvwa.openapi.adx.helper.ExceptionUtils;
import com.iwanvi.nvwa.openapi.adx.helper.RedisTemplate;
import com.iwanvi.nvwa.openapi.adx.model.Advertiser;
import com.iwanvi.nvwa.openapi.adx.model.AdvertiserAuditResult;
import com.iwanvi.nvwa.openapi.adx.model.AuditResult;
import com.iwanvi.nvwa.openapi.adx.model.Creative;
import com.iwanvi.nvwa.openapi.adx.model.CreativeAuditResult;
import com.iwanvi.nvwa.openapi.adx.service.AdExchangeService;
import com.iwanvi.nvwa.openapi.adx.service.AuditService;

/**
 * 
 * @author wangwp
 */
@Service
public class AdExchangeServiceImpl implements AdExchangeService {

	@Autowired
	private DspAdvertiserMapper advertiserMapper;
	@Autowired
	private DspCreativeMapper creativeMapper;
	@Autowired
	private SysCrontabMapper crontabMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private AuditService auditService;

	@Override
	@Transactional
	public void updateCreative(Creative creative) {
		DspCreative dspCreative = creative.toDspCreative();
		DspCreativeExample example = new DspCreativeExample().createCriteria()
				.andConsumerIdEqualTo(creative.getDsp().getId()).andDspCreativeIdEqualTo(creative.getCreativeId())
				.andDspAdvertiserIdEqualTo(creative.getAdvertiserId()).example();

		long creativeCount = creativeMapper.countByExample(example);
		if (creativeCount == 0) {
			ExceptionUtils.throwOpenApiException("素材不存在, 请先上传素材");
		}

		DspCreative oldDspCreative = creativeMapper.selectOneByExample(example);
		dspCreative.setId(oldDspCreative.getId());

		creativeMapper.updateByPrimaryKeySelective(dspCreative);
		auditService.auditEntity(dspCreative);
		// TODO 发消息通知引擎创意下线
		SysCrontab crontabMsg = new SysCrontab();
		crontabMsg.setObjectId(oldDspCreative.getId());
		crontabMsg.setObjectType(14); // dsp平台创意
		crontabMsg.setOpStatus(0); // 未处理
		crontabMsg.setOpTime(new Date());
		crontabMsg.setOpType(3); // 更新操作

		crontabMapper.insert(crontabMsg);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String notifyMsg = sdf.format(new Date()) + "_" + crontabMsg.getId();
		redisTemplate.lpush("mq:crontab", notifyMsg);
	}

	@Override
	public void addCreative(Creative creative) {
		DspCreative dspCreative = creative.toDspCreative();
		DspCreativeExample example = new DspCreativeExample().createCriteria()
				.andConsumerIdEqualTo(creative.getDsp().getId()).andDspCreativeIdEqualTo(creative.getCreativeId())
				.andDspAdvertiserIdEqualTo(creative.getAdvertiserId()).example();

		long creativeCount = creativeMapper.countByExample(example);
		if (creativeCount > 0) {
			ExceptionUtils.throwOpenApiException("创意已经存在");
		}
		String advertiserId = dspCreative.getDspAdvertiserId();
		DspAdvertiser advertiser = advertiserMapper.selectOneByExample(
				new DspAdvertiserExample().createCriteria().andDspAdvertiserIdEqualTo(dspCreative.getDspAdvertiserId())
						.andFlowConsumerIdEqualTo(creative.getDsp().getId()).example());
		if (advertiser == null) {
			ExceptionUtils.throwOpenApiException(String.format("广告主%s不存在,请先上传广告主", advertiserId));
		}

		if (advertiser.getExaminesStatus() == null || advertiser.getExaminesStatus() != 1) {
			ExceptionUtils.throwOpenApiException(String.format("广告主%s未审核通过", advertiserId));
		}
		dspCreative.setAdvertiserId(advertiser.getId());
		creativeMapper.insertSelective(dspCreative);
		auditService.auditEntity(dspCreative);
	}

	@Override
	public void addAdvertiser(Advertiser advertiser) {
		DspAdvertiser dspAdvertiser = advertiser.toDspAdvertiser();
		long advertiserCount = advertiserMapper.countByExample(
				new DspAdvertiserExample().createCriteria().andDspAdvertiserIdEqualTo(advertiser.getAdvertiserId())
						.andFlowConsumerIdEqualTo(advertiser.getDsp().getId()).example());
		if (advertiserCount > 0) {
			ExceptionUtils.throwOpenApiException("广告主已经存在");
		}

		dspAdvertiser.setCreateTime(new Date());
		dspAdvertiser.setExaminesStatus(3); // TODO magic number, 待审核

		advertiserMapper.insertSelective(dspAdvertiser);
	}

	@Override
	public void updateAdvertiser(Advertiser advertiser) {
		DspAdvertiser dspAdvertiser = advertiser.toDspAdvertiser();

		DspAdvertiserExample example = new DspAdvertiserExample().createCriteria()
				.andDspAdvertiserIdEqualTo(dspAdvertiser.getDspAdvertiserId())
				.andFlowConsumerIdEqualTo(advertiser.getDsp().getId()).example();

		long advertiserCount = advertiserMapper.countByExample(example);
		if (advertiserCount == 0) {
			ExceptionUtils.throwOpenApiException("广告主不存在，请先上传广告主信息");
		}

		DspAdvertiser oldDspAdvertiser = advertiserMapper.selectOneByExample(example);

		dspAdvertiser.setId(oldDspAdvertiser.getId());
		advertiserMapper.updateByPrimaryKeySelective(dspAdvertiser);

		SysCrontab crontabMsg = new SysCrontab();
		crontabMsg.setObjectId(oldDspAdvertiser.getId());
		crontabMsg.setObjectType(20); // dsp平台广告主
		crontabMsg.setOpStatus(0); // 未处理
		crontabMsg.setOpTime(new Date());
		crontabMsg.setOpType(3); // 更新操作

		crontabMapper.insert(crontabMsg);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String notifyMsg = sdf.format(new Date()) + "_" + crontabMsg.getId();
		redisTemplate.lpush("mq:crontab", notifyMsg);
	}

	@Override
	public List<AdvertiserAuditResult> getAdvertiserAuditResult(int flowConsumerId, List<String> advertiserIds) {
		DspAdvertiserExample example = new DspAdvertiserExample().createCriteria()
				.andFlowConsumerIdEqualTo(flowConsumerId).andDspAdvertiserIdIn(advertiserIds).example();

		List<DspAdvertiser> advertisers = advertiserMapper.selectByExample(example);

		List<AdvertiserAuditResult> advertiserAuditResults = new ArrayList<>();
		advertisers.forEach(dspAdvertiser -> advertiserAuditResults
				.add(new AdvertiserAuditResult(dspAdvertiser.getDspAdvertiserId(),
						new AuditResult(dspAdvertiser.getExaminesStatus(), dspAdvertiser.getExaminesRemarks()))));

		return advertiserAuditResults;
	}

	@Override
	public List<CreativeAuditResult> getCreativeAuditResults(int flowConsumerId, List<String> creativeIds) {
		DspCreativeExample example = new DspCreativeExample().createCriteria().andConsumerIdEqualTo(flowConsumerId)
				.andDspCreativeIdIn(creativeIds).example();
		List<DspCreative> creatives = creativeMapper.selectByExample(example);

		List<CreativeAuditResult> creativeAuditResults = new ArrayList<>();
		creatives
				.forEach(dspCreative -> creativeAuditResults.add(new CreativeAuditResult(dspCreative.getDspCreativeId(),
						new AuditResult(dspCreative.getExaminesStatus(), dspCreative.getExaminesRemarks()))));

		return creativeAuditResults;
	}

}

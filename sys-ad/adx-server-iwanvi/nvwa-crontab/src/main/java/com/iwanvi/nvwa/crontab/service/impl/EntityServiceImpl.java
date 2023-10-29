package com.iwanvi.nvwa.crontab.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.service.AdDicService;
import com.iwanvi.nvwa.crontab.service.AdpositionService;
import com.iwanvi.nvwa.crontab.service.AdxRelationService;
import com.iwanvi.nvwa.crontab.service.EntityService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.TemplateMapper;
import com.iwanvi.nvwa.dao.TemplateModuleRelationMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.EntityExample;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.Template;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelation;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelationExample;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelationExample.Criteria;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.Creative;
import com.iwanvi.nvwa.proto.CommonProto;

@Service
public class EntityServiceImpl implements EntityService {

	private static final Logger logger = LoggerFactory.getLogger(EntityServiceImpl.class);

	@Autowired
	private EntityMapper entityMapper;

	@Autowired
	private AdpositionService adpositionService;

	@Autowired
	private AdxRelationService adxRelationService;

	@Autowired
	private AdDicService adDicService;

	@Override
	public List<Entity> getValidEntityByPut(Integer id) {
		EntityExample entityExample = new EntityExample();
		entityExample.createCriteria().andPidEqualTo(id).andEntityStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID);

		return entityMapper.selectByExample(entityExample);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Creative buildCreative(Entity entity, Put put) {
		AdModelsProto.Creative.Builder builder = AdModelsProto.Creative.newBuilder().setCreativeId(entity.getId())
				.setUnitId(entity.getPid());
		if (Constants.STATE_VALID.equals(entity.getEntityState()) && Constants.STATE_VALID.equals(entity.getRunState())
				&& Constants.STATE_VALID.equals(entity.getAuditState())) {
			builder.setStatus(Constants.STATE_VALID);
		} else {
			builder.setStatus(Constants.STATE_INVALID);
		}

		if (put.getPutType() != CrontabConstants.PUT_TYPE_BOTTOM) {
			builder.setCpc(put.getPrice().intValue());// 出价
		}
		if (StringUtils.isNotBlank(put.getLandUrl())) {
			builder.setLandUrl(put.getLandUrl());
		}
		if (StringUtils.isNotBlank(put.getPkgName())) {
			builder.setPkgName(put.getPkgName());
		}
		if (StringUtils.isNotBlank(entity.getEntityTitle())) {
			builder.setTitle(ByteString.copyFromUtf8(entity.getEntityTitle()));
		}
		if (StringUtils.isNotBlank(entity.getEntityDesc())) {
			builder.setDescription(ByteString.copyFromUtf8(entity.getEntityDesc()));
		}
		if (put.getExtCreativeId() != null) {
			builder.setSnkCreativeId(put.getExtCreativeId());
		}

		if (put.getAdPosition() != null) {
			builder.setAdTypeId(put.getAdPosition());
		}
		// ssp广告位与普通逻辑一致
		AdPosition adPosition = adpositionService.getAdPosition(put.getAdPosition());
		if (adPosition == null) {
			logger.info(" adPosition is null . id is : " + put.getAdPosition());
			return null;
		}
		int creativeType = adPosition.getType();
		if ((creativeType == CrontabConstants.AD_TYPE_DYNAMIC && adPosition.getDuration() == null)
				|| creativeType == CrontabConstants.AD_TYPE_PIC || creativeType == CrontabConstants.AD_TYPE_SCREEN) {
			if (StringUtils.isNotBlank(entity.getThreadPic1())) {
				builder.setPicUrl(entity.getThreadPic1());
			}
			builder.setWidth(adPosition.getPicWidth()).setHeight(adPosition.getPicHeight());
		} else if (creativeType == CrontabConstants.AD_TYPE_VIDEO
				|| (creativeType == CrontabConstants.AD_TYPE_DYNAMIC && adPosition.getDuration() == null)) {
			builder.setDuration(adPosition.getDuration());
			if (StringUtils.isNotBlank(entity.getEntityUrl())) {
				builder.setPicUrl(entity.getEntityUrl());
			}
			AdModelsProto.NativeAd.Builder nativeAd = AdModelsProto.NativeAd.newBuilder();
			if (StringUtils.isNotBlank(entity.getThreadPic1())) {
				nativeAd.setThreadPic1(entity.getThreadPic1());
			} // 激励视频
		} else if (creativeType == CrontabConstants.AD_TYPE_NATIVE) {
			AdModelsProto.NativeAd.Builder nativeAd = AdModelsProto.NativeAd.newBuilder();
			if (StringUtils.isNotBlank(entity.getUserName())) {
				nativeAd.setUserName(entity.getUserName());
			}
			if (StringUtils.isNotBlank(entity.getButtonText())) {
				nativeAd.setButtonText(entity.getButtonText());
			}
			if (StringUtils.isNotBlank(entity.getCustomCss())) {
				nativeAd.setCustomCss(entity.getCustomCss());
			}
			if (StringUtils.isNotBlank(entity.getGoodsStyle())) {
				nativeAd.setGoodsStyle(entity.getGoodsStyle());
			}
			if (StringUtils.isNotBlank(entity.getLabelTitle())) {
				nativeAd.setLabelTitle(entity.getLabelTitle());
			}
			if (StringUtils.isNotBlank(entity.getPopWindowText())) {
				nativeAd.setPopWindowText(entity.getPopWindowText());
			}
			if (StringUtils.isNotBlank(entity.getRecommendLink())) {
				nativeAd.setRecommendLink(entity.getRecommendLink());
			}
			if (StringUtils.isNotBlank(entity.getRecommendName())) {
				nativeAd.setRecommendName(entity.getRecommendName());
			}
			if (StringUtils.isNotBlank(entity.getThreadContent())) {
				nativeAd.setThreadContent(entity.getThreadContent());
			}
			if (StringUtils.isNotBlank(entity.getThreadPic1())) {
				nativeAd.setThreadPic1(entity.getThreadPic1());
			}
			if (StringUtils.isNotBlank(entity.getThreadPic2())) {
				nativeAd.setThreadPic2(entity.getThreadPic2());
			}
			if (StringUtils.isNotBlank(entity.getThreadPic3())) {
				nativeAd.setThreadPic3(entity.getThreadPic3());
			}
			if (StringUtils.isNotBlank(entity.getThreadTitle())) {
				nativeAd.setThreadTitle(entity.getThreadTitle());
			}
			if (StringUtils.isNotBlank(entity.getThreadUrlText())) {
				nativeAd.setThreadUrlText(entity.getThreadUrlText());
			}
			if (StringUtils.isNotBlank(entity.getUserPortrait())) {
				nativeAd.setUserPortrait(entity.getUserPortrait());
			}
			if (entity.getLabelVisible() != null && StringUtils.isNotBlank(entity.getLabelVisible().toString())) {
				nativeAd.setLabelVisible(Integer.valueOf(entity.getLabelVisible()));
			}
			builder.setNativeAd(nativeAd);
		} else if (creativeType == CrontabConstants.AD_TYPE_VIDEO_INCENTIVE) {
			builder.setPicUrl(entity.getEntityUrl());
		}
		if (adPosition.getFlowPositionId() != null) {
			builder.setAdxAdTypeId(String.valueOf(adPosition.getFlowPositionId()));
		}
		builder.setCreativeType(CommonProto.CreativeType.valueOf(adDicService.getDic(creativeType).getEnumValue()));
		// 增加曝光点击检测地址
		if (StringUtils.isNotBlank(put.getImpMonitorUrl())) {
			String[] imps = put.getImpMonitorUrl().split(Constants.SIGN_SPLIT);
			List<String> list = Lists.newArrayList(imps);
			builder.addAllImpMonitorUrl(list);
		} else if (StringUtils.isNotBlank(put.getImpMonitorUrl())) {
			String[] imps = put.getImpMonitorUrl().split(Constants.SIGN_SPLIT);
			List<String> list = Lists.newArrayList(imps);
			builder.addAllImpMonitorUrl(list);

		}
		if (StringUtils.isNotBlank(put.getClkMonitorUrl())) {
			String[] clks = put.getClkMonitorUrl().split(Constants.SIGN_SPLIT);
			List<String> list = Lists.newArrayList(clks);
			builder.addAllClickMonitorUrl(list);
		} else if (StringUtils.isNotBlank(put.getClkMonitorUrl())) {
			String[] clks = put.getClkMonitorUrl().split(Constants.SIGN_SPLIT);
			List<String> list = Lists.newArrayList(clks);
			builder.addAllClickMonitorUrl(list);
		}
		List<AdxRelation> adxRelations = adxRelationService.getValidEntityAdxRelations(entity.getId());
		logger.info(" Entity id is : " + entity.getId());
		AdModelsProto.AdxTarget.Builder adxTargetBuilder = AdModelsProto.AdxTarget.newBuilder();
		CommonProto.AdxInfo.Builder adxInfoBuilder = CommonProto.AdxInfo.newBuilder();
		if (!CollectionUtils.isEmpty(adxRelations)) {
			for (AdxRelation adxRelation : adxRelations) {
				logger.info(" getAdxType id is : " + adxRelation.getAdxType());
				if (adxRelation.getAdxCrid() != null) {
					adxInfoBuilder.setAdxCrid(String.valueOf(adxRelation.getAdxCrid()));
				}
				if (adxRelation.getAdxUrl() != null) {
					adxInfoBuilder.setAdxMUrl(adxRelation.getAdxUrl());
				}
				adxTargetBuilder.addAdxInfo(adxInfoBuilder);
			}
		}
		builder.setAdxTarget(adxTargetBuilder.build());
		return builder.build();
	}

	@Override
	public Creative buildOrderCreative(Entity ampEntity, OrderPut put) {
		AdModelsProto.Creative.Builder builder = AdModelsProto.Creative.newBuilder().setCreativeId(ampEntity.getId())
				.setUnitId(ampEntity.getPid()).setStatus(ampEntity.getEntityState());

		Integer price = put.getPrice() == null ? Constants.INTEGER_0 : put.getPrice();
		builder.setCpc(price);
		if (StringUtils.isNotBlank(ampEntity.getEntityTitle())) {
			builder.setTitle(ByteString.copyFromUtf8(ampEntity.getEntityTitle()));
		}
		if (StringUtils.isNotBlank(ampEntity.getEntityDesc())) {
			builder.setDescription(ByteString.copyFromUtf8(ampEntity.getEntityDesc()));
		}
		/*
		 * if (ampUnit.getExtCreativeId() != null) {
		 * builder.setSnkCreativeId(ampUnit.getExtCreativeId()); }
		 */// 创意下的snkid

		if (put.getAdPosition() != null) {
			builder.setAdTypeId(put.getAdPosition());
		}
		AdPosition adPosition = adpositionService.getAdPosition(put.getAdPosition());
		if (adPosition == null) {
			logger.info(" adPosition is null . id is : " + put.getAdPosition());
			return null;
		}
		int creativeType = adPosition.getType();
		if ((creativeType == CrontabConstants.AD_TYPE_DYNAMIC && adPosition.getDuration() == null)
				|| creativeType == CrontabConstants.AD_TYPE_PIC || creativeType == CrontabConstants.AD_TYPE_SCREEN) {
			builder.setWidth(adPosition.getPicWidth()).setHeight(adPosition.getPicHeight());
			if (StringUtils.isNotBlank(ampEntity.getThreadPic1())) {
				builder.setPicUrl(ampEntity.getThreadPic1());
			} else {
				logger.info(" ThreadPic1 is null . ent_id is : " + ampEntity.getId());
				return null;
			}
		} else if (creativeType == CrontabConstants.AD_TYPE_VIDEO) {
			builder.setDuration(adPosition.getDuration());
			if (StringUtils.isNotBlank(ampEntity.getEntityUrl())) {
				builder.setPicUrl(ampEntity.getEntityUrl());
			}
			AdModelsProto.NativeAd.Builder nativeAd = AdModelsProto.NativeAd.newBuilder();
			if (StringUtils.isNotBlank(ampEntity.getThreadPic1())) {
				nativeAd.setThreadPic1(ampEntity.getThreadPic1());
			}
		} else if (creativeType == CrontabConstants.AD_TYPE_NATIVE) {
			AdModelsProto.NativeAd.Builder nativeAd = AdModelsProto.NativeAd.newBuilder();
			if (StringUtils.isNotBlank(ampEntity.getUserName())) {
				nativeAd.setUserName(ampEntity.getUserName());
			}
			if (StringUtils.isNotBlank(ampEntity.getButtonText())) {
				nativeAd.setButtonText(ampEntity.getButtonText());
			}
			if (StringUtils.isNotBlank(ampEntity.getCustomCss())) {
				nativeAd.setCustomCss(ampEntity.getCustomCss());
			}
			if (StringUtils.isNotBlank(ampEntity.getGoodsStyle())) {
				nativeAd.setGoodsStyle(ampEntity.getGoodsStyle());
			}
			if (StringUtils.isNotBlank(ampEntity.getLabelTitle())) {
				nativeAd.setLabelTitle(ampEntity.getLabelTitle());
			}
			if (StringUtils.isNotBlank(ampEntity.getPopWindowText())) {
				nativeAd.setPopWindowText(ampEntity.getPopWindowText());
			}
			if (StringUtils.isNotBlank(ampEntity.getRecommendLink())) {
				nativeAd.setRecommendLink(ampEntity.getRecommendLink());
			}
			if (StringUtils.isNotBlank(ampEntity.getRecommendName())) {
				nativeAd.setRecommendName(ampEntity.getRecommendName());
			}
			if (StringUtils.isNotBlank(ampEntity.getThreadContent())) {
				nativeAd.setThreadContent(ampEntity.getThreadContent());
			}
			if (StringUtils.isNotBlank(ampEntity.getThreadPic1())) {
				nativeAd.setThreadPic1(ampEntity.getThreadPic1());
			}
			if (StringUtils.isNotBlank(ampEntity.getThreadPic2())) {
				nativeAd.setThreadPic2(ampEntity.getThreadPic2());
			}
			if (StringUtils.isNotBlank(ampEntity.getThreadPic3())) {
				nativeAd.setThreadPic3(ampEntity.getThreadPic3());
			}
			if (StringUtils.isNotBlank(ampEntity.getThreadTitle())) {
				nativeAd.setThreadTitle(ampEntity.getThreadTitle());
			}
			if (StringUtils.isNotBlank(ampEntity.getThreadUrlText())) {
				nativeAd.setThreadUrlText(ampEntity.getThreadUrlText());
			}
			if (StringUtils.isNotBlank(ampEntity.getUserPortrait())) {
				nativeAd.setUserPortrait(ampEntity.getUserPortrait());
			}
			if (ampEntity.getLabelVisible() != null && StringUtils.isNotBlank(ampEntity.getLabelVisible().toString())) {
				nativeAd.setLabelVisible(Integer.valueOf(ampEntity.getLabelVisible()));
			}
			builder.setNativeAd(nativeAd);
		} else if (creativeType == CrontabConstants.AD_TYPE_VIDEO_INCENTIVE) {
			builder.setPicUrl(ampEntity.getEntityUrl());
		}
		if (adPosition.getFlowPositionId() != null) {
			builder.setAdxAdTypeId(String.valueOf(adPosition.getFlowPositionId()));
		}
		builder.setCreativeType(CommonProto.CreativeType.valueOf(adDicService.getDic(creativeType).getEnumValue()));
		logger.info("test00009");// TODO
		return builder.build();
	}

}

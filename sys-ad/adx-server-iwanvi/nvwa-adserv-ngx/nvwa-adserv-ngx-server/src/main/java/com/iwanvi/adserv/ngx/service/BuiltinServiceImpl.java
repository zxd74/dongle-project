/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.iwanvi.adserv.ngx.index.ReverseIndex;
import com.iwanvi.adserv.ngx.index.ReverseIndexFactory;
import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.AdPositionFloorPriceUtils;
import com.iwanvi.adserv.ngx.util.AgentFloorPriceConfigKeyBuilder;
import com.iwanvi.adserv.ngx.util.AreaLevelIndex;
import com.iwanvi.adserv.ngx.util.LogLevelUtils;
import com.iwanvi.adserv.ngx.util.MinervaStatHolder;
import com.iwanvi.adserv.ngx.util.RedisTemplate;
import com.iwanvi.adserv.ngx.util.ReverseIndexKeysUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.BuiltinService.BlockingInterface;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.BuiltinService.Interface;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAdPosFloorPriceReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAdPosFloorPriceRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAdReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAdResp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAllDspCreativeReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAllDspCreativeRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAllDspReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAllDspRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAreaLevelReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetAreaLevelRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetBiddingCreativesReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetBiddingCreativesResp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetBiddingRequestCountReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetBiddingRequestCountResp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspCreativeReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspCreativeRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetDspRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetLoggersReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetLoggersRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetSspAdPositionReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetSspAdPositionRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.InspectAdReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.InspectAdResp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.InspectBiddingReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.InspectBiddingResp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.InspectStatus;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.ListAppsReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.ListAppsRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.OfflineAdReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.OfflineAdRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.SearchAdRevIndexReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.SearchAdRevIndexResp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.SearchAdRevIndexResp.RevIndexAd;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.SetLogLevelReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.SetLogLevelRsp;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.Stat;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.StatReq;
import com.iwanvi.nvwa.proto.BuiltinServiceProto.StatResp;
import com.iwanvi.nvwa.proto.CommonProto.AgentType;
import com.iwanvi.nvwa.proto.CommonProto.OSType;

/**
 * @author wangwp
 *
 */
public class BuiltinServiceImpl implements BlockingInterface, Interface {
	private static final Logger LOG = LoggerFactory.getLogger("Minerva-Builtin");

	private static final Repository repo = RepositoryFactory.getRepository();

	private static final GetAdResp EMPTY_GET_AD_RESP = GetAdResp.newBuilder().build();

	@Override
	public GetAdResp getAd(RpcController controller, GetAdReq request) throws ServiceException {
		int adUnitId = request.getUnitId();

		Repository repo = RepositoryFactory.getRepository();
		AdUnit adUnit = repo.loadAdUnit(adUnitId);
		if (adUnit == null)
			return EMPTY_GET_AD_RESP;

		GetAdResp.Builder builder = GetAdResp.newBuilder().setAdUnit(adUnit);
		AdPlan adPlan = repo.loadAdPlan(adUnit.getPlanId());
		if (adPlan == null)
			return builder.build();

		builder.setAdPlan(adPlan);

		Advertiser advertiser = repo.loadAdvertiser(adUnit.getAdvertiserId());
		if (advertiser == null)
			return builder.build();

		builder.setAdvertiser(advertiser);

		Agent agent = repo.loadAgent(advertiser.getAgentId());
		if (agent == null)
			return builder.build();

		builder.setAgent(agent);

		if (agent.getAgentType() == AgentType.kAgentTypeKA) {
			return builder.build();
		}

		if (adUnit.hasMediaTarget() && adUnit.getMediaTarget().getMediaUuidCount() == 0) {
			return builder.build();
		}

		LOG.info(adUnit.toString());
		List<OSType> targetOsList = adUnit.getOsTarget().getOsList();
		if (targetOsList.isEmpty()) {
			targetOsList = new ArrayList<>();
			targetOsList.addAll(Arrays.asList(OSType.kAndroid, OSType.kIOS, OSType.kPc));
		}
		return builder.build();
	}

	@Override
	public InspectAdResp inspectAd(RpcController controller, InspectAdReq request) throws ServiceException {
		InspectAdResp.Builder builder = InspectAdResp.newBuilder();

		int adUnitId = request.getAdUnitId();
		AdUnit adUnit = repo.loadAdUnit(adUnitId);
		if (adUnit == null) {
			return builder.setStatus(InspectStatus.AD_NOT_FOUND).build();
		}

		if (adUnit.getStatus() == 0) {
			return builder.setStatus(InspectStatus.INVALID_AD_STATUS).build();
		}

		AdPlan adplan = repo.loadAdPlan(adUnit.getPlanId());
		if (adplan == null) {
			return builder.setStatus(InspectStatus.AD_PLAN_NOT_FOUND).build();
		}

		if (adplan.getStatus() == 0) {
			return builder.setStatus(InspectStatus.INVALID_PLAN_STATUS).build();
		}

		Advertiser advertiser = repo.loadAdvertiser(adUnit.getAdvertiserId());
		if (advertiser == null) {
			return builder.setStatus(InspectStatus.ADVERTISER_NOT_FOUND).build();
		}

		if (advertiser.getStatus() == 0) {
			return builder.setStatus(InspectStatus.INVALID_ADVERTISER_STATUS).build();
		}

		Agent agent = repo.loadAgent(advertiser.getAgentId());
		if (agent == null) {
			return builder.setStatus(InspectStatus.AGENT_NOT_FOUND).build();
		}

		if (agent.getStatus() == 0) {
			return builder.setStatus(InspectStatus.INVALID_AGENT_STATUS).build();
		}

		if (agent.getAgentType() == AgentType.kAgentTypeKA) {
			return builder.setStatus(InspectStatus.OK).build();
		}

		if (!request.hasOs() || !request.hasAreaLevelId()) {
			return builder.setStatus(InspectStatus.MISSING_PARAMETERS).build();
		}

		OSType os = request.getOs();
		int area_level_id = request.getAreaLevelId();

		String mediaUuid = adUnit.getMediaTarget().getMediaUuid(0);
		AgentFloorPriceConfig agentFloorPriceConfig = repo
				.loadAgentFloorPriceConfig(AgentFloorPriceConfigKeyBuilder.buildLongKey(agent.getAgentId(), mediaUuid,
						advertiser.getIndustryId(), adUnit.getAdTypeId(), os, area_level_id));

		if (agentFloorPriceConfig == null) {
			return builder.setStatus(InspectStatus.AGENT_FLOOR_NOT_FOUND).build();
		}

		return builder.setStatus(InspectStatus.OK).build();
	}

	@Override
	public StatResp stat(RpcController controller, StatReq request) throws ServiceException {
		Stat stat = MinervaStatHolder.stat();
		return StatResp.newBuilder().setStat(stat).build();
	}

	@Override
	public void getAd(RpcController controller, GetAdReq request, RpcCallback<GetAdResp> done) {
		try {
			done.run(getAd(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public void inspectAd(RpcController controller, InspectAdReq request, RpcCallback<InspectAdResp> done) {
		try {
			done.run(inspectAd(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public void stat(RpcController controller, StatReq request, RpcCallback<StatResp> done) {
		try {
			done.run(stat(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public void getBiddingCreatives(RpcController controller, GetBiddingCreativesReq request,
			RpcCallback<GetBiddingCreativesResp> done) {
		try {
			done.run(getBiddingCreatives(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public GetBiddingCreativesResp getBiddingCreatives(RpcController controller, GetBiddingCreativesReq request)
			throws ServiceException {
		return GetBiddingCreativesResp.newBuilder().addAllBiddingCreative(MinervaStatHolder.biddingCreatives()).build();
	}

	@Override
	public void getBiddingRequestCount(RpcController controller, GetBiddingRequestCountReq request,
			RpcCallback<GetBiddingRequestCountResp> done) {
		try {
			done.run(getBiddingRequestCount(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public GetBiddingRequestCountResp getBiddingRequestCount(RpcController controller,
			GetBiddingRequestCountReq request) throws ServiceException {

		return GetBiddingRequestCountResp.newBuilder()
				.setBiddingRequestCount(MinervaStatHolder.statInfo().biddingRequestCount.get()).build();
	}

	@Override
	public void searchAdRevIndex(RpcController controller, SearchAdRevIndexReq request,
			RpcCallback<SearchAdRevIndexResp> done) {
		try {
			done.run(searchAdRevIndex(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public SearchAdRevIndexResp searchAdRevIndex(RpcController controller, SearchAdRevIndexReq request)
			throws ServiceException {
		BiddingReq bidding_req = request.getBiddingReq();

		SearchAdRevIndexResp.Builder resp_builder = SearchAdRevIndexResp.newBuilder();
		List<Long> reverseIndexKeys = ReverseIndexKeysUtils.buildReverseIndexKeys(bidding_req.getPosInfo(0),
				bidding_req.getUserInfo());
		resp_builder.addAllReverseIndex(reverseIndexKeys);

		ReverseIndex<AdUnit> reverse_index = ReverseIndexFactory.getReverseIndex();
		for (Long reverseIndexKey : reverseIndexKeys) {
			resp_builder.addRevIndexAd(RevIndexAd.newBuilder().setReverseIndex(reverseIndexKey)
					.addAllAdUnitId(reverse_index.search(reverseIndexKey)));
		}

		return resp_builder.build();
	}

	@Override
	public void inspectBidding(RpcController controller, InspectBiddingReq request,
			RpcCallback<InspectBiddingResp> done) {
		try {
			done.run(inspectBidding(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public InspectBiddingResp inspectBidding(RpcController controller, InspectBiddingReq request)
			throws ServiceException {
		InspectBiddingResp.Builder builder = InspectBiddingResp.newBuilder();

		return builder.build();
	}

	@Override
	public void setLogLevel(RpcController controller, SetLogLevelReq request, RpcCallback<SetLogLevelRsp> done) {
		try {
			done.run(setLogLevel(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public SetLogLevelRsp setLogLevel(RpcController controller, SetLogLevelReq request) throws ServiceException {
		String loggerName = request.getLoggerName();
		SetLogLevelReq.Level level = request.getLogLevel();

		Level log_level = null;
		switch (level) {
		case DEBUG:
			log_level = Level.DEBUG;
			break;
		case INFO:
			log_level = Level.INFO;
			break;
		case WARN:
			log_level = Level.WARN;
			break;
		case ERROR:
			log_level = Level.ERROR;
			break;
		default:
			break;
		}

		if (StringUtils.isBlank(loggerName)) {
			LogLevelUtils.setRootLogLevel(log_level);
		} else {
			LogLevelUtils.setLogLevel(loggerName, log_level);
		}

		return SetLogLevelRsp.newBuilder().build();
	}

	@Override
	public void getLoggers(RpcController controller, GetLoggersReq request, RpcCallback<GetLoggersRsp> done) {
		try {
			done.run(getLoggers(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public GetLoggersRsp getLoggers(RpcController controller, GetLoggersReq request) throws ServiceException {
		GetLoggersRsp.Builder getLoggersRsp = GetLoggersRsp.newBuilder();

		List<GetLoggersRsp.Logger> loggers = LogLevelUtils.getLoggers();
		if (loggers == null || loggers.isEmpty()) {
			return getLoggersRsp.build();
		}

		getLoggersRsp.addAllLogger(loggers);
		return getLoggersRsp.build();
	}

	@Override
	public void offlineAd(RpcController controller, OfflineAdReq request, RpcCallback<OfflineAdRsp> done) {
		try {
			done.run(offlineAd(controller, request));
		} catch (ServiceException ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public OfflineAdRsp offlineAd(RpcController controller, OfflineAdReq request) throws ServiceException {
		for (Integer adId : request.getAdIdList()) {
			repo.removeAdUnit(adId);
		}
		return OfflineAdRsp.newBuilder().setRespCode(0).build();
	}

	@Override
	public void listApps(RpcController controller, ListAppsReq request, RpcCallback<ListAppsRsp> done) {
		try {
			done.run(listApps(controller, request));
		} catch (Throwable ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public ListAppsRsp listApps(RpcController controller, ListAppsReq request) throws ServiceException {
		String appId = request.getAppId();
		if (StringUtils.isBlank(appId)) {
			return ListAppsRsp.newBuilder().build();
		}

		App app = repo.loadApp(appId);
		if (app == null) {
			return ListAppsRsp.newBuilder().build();
		}
		return ListAppsRsp.newBuilder().addApp(app.getAppId()).build();
	}

	@Override
	public void getDspCreative(RpcController controller, GetDspCreativeReq request,
			RpcCallback<GetDspCreativeRsp> done) {
		try {
			done.run(getDspCreative(controller, request));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getDsp(RpcController controller, GetDspReq request, RpcCallback<GetDspRsp> done) {
		try {
			done.run(getDsp(controller, request));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public GetDspCreativeRsp getDspCreative(RpcController controller, GetDspCreativeReq request)
			throws ServiceException {
		return GetDspCreativeRsp.newBuilder()
				.setDspCreative(
						RepositoryFactory.getRepository().getDspCreative(request.getCreativeId(), request.getDspId()))
				.build();
	}

	@Override
	public GetDspRsp getDsp(RpcController controller, GetDspReq request) throws ServiceException {
		return GetDspRsp.newBuilder().setDsp(RepositoryFactory.getRepository().loadDsp(request.getDspId())).build();
	}

	@Override
	public void getAreaLevel(RpcController controller, GetAreaLevelReq request, RpcCallback<GetAreaLevelRsp> done) {
		try {
			done.run(getAreaLevel(controller, request));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getAdPosFloorPrice(RpcController controller, GetAdPosFloorPriceReq request,
			RpcCallback<GetAdPosFloorPriceRsp> done) {
		try {
			done.run(getAdPosFloorPrice(controller, request));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public GetAreaLevelRsp getAreaLevel(RpcController controller, GetAreaLevelReq request) throws ServiceException {
		GetAreaLevelRsp.Builder rsp = GetAreaLevelRsp.newBuilder();
		Integer areaLevel = AreaLevelIndex.getInstance().getAreaLevel(request.getAreaCode());

		return rsp.setAreaLevel(areaLevel).build();
	}

	@Override
	public GetAdPosFloorPriceRsp getAdPosFloorPrice(RpcController controller, GetAdPosFloorPriceReq request)
			throws ServiceException {
		String tagId = request.getTagId();
		int industry = request.getIndustry();
		int areaCode = request.getAreaCode();

		String cacheKey = StringUtils.join("adpfp:", AdPositionFloorPriceUtils.genHashKey(tagId, industry, areaCode));

		AdPositionFloorPrice adPositionFloorPrice = (AdPositionFloorPrice) RedisTemplate.execute((r) -> {
			byte[] data = r.get(cacheKey.getBytes());
			if (data == null) {
				LOG.warn("==广告位对应底价不存在,industry: {},adPosId: {}, areaCode: {}==", tagId, industry, areaCode);
				return null;
			}
			try {
				AdPositionFloorPrice adfp = AdPositionFloorPrice.newBuilder().mergeFrom(data).build();
				return adfp;
			} catch (InvalidProtocolBufferException ex) {
				LOG.error("==从缓存中获取广告位底价异常==", ex);
			}
			return null;
		});

		return adPositionFloorPrice == null ? GetAdPosFloorPriceRsp.newBuilder().build()
				: GetAdPosFloorPriceRsp.newBuilder().setAdpfp(adPositionFloorPrice).build();
	}

	@Override
	public void getAllDspList(RpcController controller, GetAllDspReq request, RpcCallback<GetAllDspRsp> done) {
		try {
			done.run(getAllDspList(controller, request));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getAllDspCreativeList(RpcController controller, GetAllDspCreativeReq request,
			RpcCallback<GetAllDspCreativeRsp> done) {
		try {
			done.run(getAllDspCreativeList(controller, request));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public GetAllDspRsp getAllDspList(RpcController controller, GetAllDspReq request) throws ServiceException {
		List<Dsp> dspList = RepositoryFactory.getRepository().getAllDspList();
		return GetAllDspRsp.newBuilder().addAllDsp(dspList).build();
	}

	@Override
	public GetAllDspCreativeRsp getAllDspCreativeList(RpcController controller, GetAllDspCreativeReq request)
			throws ServiceException {
		List<DspCreative> dspCreativeList = RepositoryFactory.getRepository().getAllDspCreativeList();
		return GetAllDspCreativeRsp.newBuilder().addAllDspCreative(dspCreativeList).build();
	}

	@Override
	public void getSspAdPosition(RpcController controller, GetSspAdPositionReq request,
			RpcCallback<GetSspAdPositionRsp> done) {
		// TODO Auto-generated method stub
		try {
			done.run(getSspAdPosition(controller, request));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public GetSspAdPositionRsp getSspAdPosition(RpcController controller, GetSspAdPositionReq request)
			throws ServiceException {
		// TODO Auto-generated method stub
		SspAdPosition adslot = RepositoryFactory.getRepository().loadSspAdPosition(request.getAdslotId());
		return GetSspAdPositionRsp.newBuilder().setAdslot(adslot).build();
	}
}

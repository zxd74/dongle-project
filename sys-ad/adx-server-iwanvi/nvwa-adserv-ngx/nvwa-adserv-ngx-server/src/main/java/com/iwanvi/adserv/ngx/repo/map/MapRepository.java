/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.repo.map;

import static com.iwanvi.adserv.ngx.util.PubIndexUtils.record;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.protobuf.ByteString;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.index.ReverseIndexFactory;
import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.router.DspIndexUtils;
import com.iwanvi.adserv.ngx.router.DspRateLimiters;
import com.iwanvi.adserv.ngx.util.AdPositionFloorPriceUtils;
import com.iwanvi.adserv.ngx.util.AgentFloorPriceConfigKeyBuilder;
import com.iwanvi.adserv.ngx.util.AreaLevelIndex;
import com.iwanvi.adserv.ngx.util.BiddingRateLimiterFactory;
import com.iwanvi.adserv.ngx.util.BitSetUtils;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.CpaCappingDataHolder;
import com.iwanvi.adserv.ngx.util.IOUtils;
import com.iwanvi.adserv.ngx.util.MinervaDB;
import com.iwanvi.adserv.ngx.util.MinervaStatHolder.StatInfo;
import com.iwanvi.adserv.ngx.util.PubIndexUtils;
import com.iwanvi.adserv.ngx.util.RedisTemplate;
import com.iwanvi.adserv.ngx.util.SBDStatHolder;
import com.iwanvi.adserv.ngx.util.ThreadPools;
import com.iwanvi.nvwa.proto.AdModelsProto.AdCommonConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.AdTypeConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaLevel;
import com.iwanvi.nvwa.proto.AdModelsProto.CompetingProduct;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.AdModelsProto.SBDStat;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.CommonProto.DeliveryMode;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;

/**
 * @author wangwp
 *
 */
public class MapRepository implements Repository {
	private static final Logger LOG = LoggerFactory.getLogger("MinervaNotifyConsumer");

	private static final Map<Integer, Agent> _agent_holder = new ConcurrentHashMap<>();
	private static final Map<Integer, Advertiser> _advertiser_holder = new ConcurrentHashMap<>();
	private static final Map<Integer, AdPlan> _adplan_holder = new ConcurrentHashMap<>();
	private static final Map<Integer, AdUnit> _adunit_holder = new ConcurrentHashMap<>();
	private static final Map<Long, AgentFloorPriceConfig> _agentFloorPriceConfig_holder = new ConcurrentHashMap<>();
	private static final Map<Integer, AdTypeConfig> _adtypecfg_holder = new ConcurrentHashMap<>();
	private static final Map<Integer, AdCommonConfig> _sys_config_holder = new ConcurrentHashMap<>();
	private static final Map<Integer, SBDStat> _sbd_stat_holder = new ConcurrentHashMap<>();
	private static final Map<String, App> _app_holder = new ConcurrentHashMap<>();
	private static final Map<String, SspAdPosition> _ssp_ad_pos_holder = new ConcurrentHashMap<>();

	private static final List<Integer> _frequency_capping_holder = new ArrayList<>();
	private static final Map<String, Dsp> _dsp_holder = new HashMap<>();
	private static final List<String> _competing_keyword_holder = new ArrayList<>();


	private static final LoadingCache<String, DspCreative> _dsp_creative_holder = CacheBuilder.newBuilder()
			.maximumSize(1000).build(new CacheLoader<String, DspCreative>() {
				@Override
				public DspCreative load(String key) throws Exception {
					return (DspCreative) RedisTemplate.execute((r) -> {
						try {
							byte[] data = r.get(key.getBytes());
							if (data == null)
								throw new AdNgxException("Not found redis data for key: " + key);
							return DspCreative.parseFrom(data);
						} catch (Exception ex) {
							throw new AdNgxException(ex.getMessage());
						}
					});
				}
			});

	private static final Map<Integer, AreaLevel> _area_level_holder = new HashMap<>();
	private static final Map<String, AdPositionFloorPrice> _ad_position_floor_price_holder = new HashMap<>();

	@Override
	public void saveOrUpdateAgent(Agent agent) {
		_agent_holder.put(agent.getAgentId(), agent);
	}

	@Override
	public void removeAgent(int id) {
		_agent_holder.remove(id);
	}

	@Override
	public Agent loadAgent(int id) {
		return _agent_holder.get(id);
	}

	@Override
	public void saveOrUpdateAdvertiser(Advertiser advertiser) {
		_advertiser_holder.put(advertiser.getAdvertiserId(), advertiser);
	}

	@Override
	public void removeAdvertiser(int id) {
		_advertiser_holder.remove(id);
	}

	@Override
	public Advertiser loadAdvertiser(int id) {
		return _advertiser_holder.get(id);
	}

	@Override
	public void saveOrUpdateAdPlan(AdPlan adPlan) {
		_adplan_holder.put(adPlan.getPlanId(), adPlan);
	}

	@Override
	public void removeAdPlan(int id) {
		_adplan_holder.remove(id);
	}

	@Override
	public AdPlan loadAdPlan(int id) {
		return _adplan_holder.get(id);
	}

	@Override
	public void saveOrUpdateAdUnit(AdUnit adUnit) {
		_adunit_holder.put(adUnit.getUnitId(), adUnit);

		if (adUnit.hasFrequencyCapping() && adUnit.getFrequencyCapping().getFrequencyCapping()) {
			if (!_frequency_capping_holder.contains(adUnit.getUnitId()))
				_frequency_capping_holder.add(adUnit.getUnitId());
		}
	}

	@Override
	public void removeAdUnit(int id) {
		_adunit_holder.remove(id);
	}

	@Override
	public AdUnit loadAdUnit(int id) {
		return _adunit_holder.get(id);
	}

	@Override
	public void saveOrUpdateAdTypeConfig(AdTypeConfig adTypeConfig) {
		_adtypecfg_holder.put(adTypeConfig.getAdTypeId(), adTypeConfig);
	}

	@Override
	public AdTypeConfig loadAdTypeConfig(int id) {
		return _adtypecfg_holder.get(id);
	}

	@Override
	public void removeAdTypeConfig(int id) {
		_adtypecfg_holder.remove(id);
	}

	@Override
	public void saveOrUpdateAgentFloorPriceConfig(AgentFloorPriceConfig afpc) {
		long agentFloorPriceConfigKey = AgentFloorPriceConfigKeyBuilder.buildLongKey(afpc);
		_agentFloorPriceConfig_holder.put(agentFloorPriceConfigKey, afpc);
	}

	@Override
	public AgentFloorPriceConfig loadAgentFloorPriceConfig(long id) {
		return _agentFloorPriceConfig_holder.get(id);
	}

	@Override
	public synchronized boolean reload() {
		MinervaDB db = null;
		try {
			db = MinervaDB.open();
			// 保存系统内置广告流量源
			String appId = MinervaCfg.get().getConfigProperty("media.internal.uuid");
			App internalApp = App.newBuilder().setAppId(appId).setStatus(1).build();
			_app_holder.put(appId, internalApp);
			for (Iterator<PubRecord> iterator = db.iterator(); iterator.hasNext();) {
				savePubRecord(iterator.next());
			}
			return true;
		} catch (Exception ex) {
			LOG.error("", ex);
		} finally {
			if (db != null)
				db.close();
		}
		return false;
	}

	@Override
	public void saveAdCommonConfig(AdCommonConfig adCommonConfig) {
		_sys_config_holder.put(Constants.INT_ZERO, adCommonConfig);
	}

	@Override
	public AdCommonConfig loadAdCommonConfig() {
		return _sys_config_holder.get(Constants.INT_ZERO);
	}

	@Override
	public void saveOrUpdateSBDStat(SBDStat stat) {
		_sbd_stat_holder.put(stat.getUnitId(), stat);
	}

	@Override
	public SBDStat loadSBDStat(int id) {
		return _sbd_stat_holder.get(id);
	}

	@Override
	public void removeSBDStat(int id) {
		_sbd_stat_holder.remove(id);
	}

	@Override
	public void impFromPubIndexFile(String file) {
		DataInputStream in = null;
		try {
			cleanup();
			in = new DataInputStream(new FileInputStream(file));
			while (in.available() > 0) {
				PubRecord record = PubIndexUtils.readRecord(in);
				savePubRecord(record);
			}
		} catch (Exception ex) {
			LOG.error("import full ad index file error, cause: " + ex.getMessage(), ex);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	private void savePubRecord(PubRecord record) throws Exception {
		try {
			EntityType type = record.getEntityType();
			ByteString data = record.getData();

			switch (type) {
			case kAreaLevel:
				AreaLevel areaLevel = AreaLevel.newBuilder().mergeFrom(data).build();
				LOG.info("update AreaLevel: {}", areaLevel.toString());

				_area_level_holder.put(areaLevel.getId(), areaLevel);
				LOG.info("==对地域等级创建索引==");
				AreaLevelIndex.getInstance().index(areaLevel);
				break;
			case kDsp:
				Dsp dsp = Dsp.newBuilder().mergeFrom(data).build();
				_dsp_holder.put(dsp.getDspId(), dsp);
				DspIndexUtils.indexDsp(dsp);
				// DspRateLimiters.removeRateLimiter(dsp.getDspId());
				DspRateLimiters.createOrUpdateRateLimiter(dsp);
				break;
			case kDspCreative:
				DspCreative dspCreative = DspCreative.newBuilder().mergeFrom(data).build();
				saveOrUpdateDspCreative(dspCreative);
				break;
			case kAgent:
				Agent agent = Agent.newBuilder().mergeFrom(data).build();
				saveOrUpdateAgent(agent);
				break;
			case kAgentFloorPriceConfig:
				AgentFloorPriceConfig agentFloorPriceConfig = AgentFloorPriceConfig.newBuilder().mergeFrom(data)
						.build();
				saveOrUpdateAgentFloorPriceConfig(agentFloorPriceConfig);
				break;
			case kAdvertiser:
				Advertiser advertiser = Advertiser.newBuilder().mergeFrom(data).build();
				saveOrUpdateAdvertiser(advertiser);
				break;
			case kAdPlan:
				AdPlan adPlan = AdPlan.newBuilder().mergeFrom(data).build();
				saveOrUpdateAdPlan(adPlan);
				break;
			case kAdUnit:
				AdUnit.Builder builder = AdUnit.newBuilder().mergeFrom(data);
				if (builder.getDeliveryMode() == DeliveryMode.kSmoothBudgetDelivery) {
					BitSet bits = BitSetUtils.long2BitSet(builder.getTimeTarget().getHalfHours());
					for (int i = 0; i < bits.length() / 2; i++) {
						if (bits.get(i * 2)) {
							builder.addDeliveryHours(i);
						}
					}
				}
				AdUnit adUnit = builder.build();
				LOG.info("full index ad: {}", adUnit.getUnitId());
				saveOrUpdateAdUnit(adUnit);
				ReverseIndexFactory.getReverseIndex().buildIndex(adUnit);
				SBDStatHolder.registerSBDAdUnit(adUnit);
				BiddingRateLimiterFactory.createOrUpdateRateLimiter(adUnit);
				CpaCappingDataHolder.registerCpaAdunit(adUnit);
				break;
			case kCommonConfig:
				AdCommonConfig commonConfig = AdCommonConfig.newBuilder().mergeFrom(data).build();
				saveAdCommonConfig(commonConfig);
				break;
			case kAdTypeConfig:
				AdTypeConfig adPosition = AdTypeConfig.newBuilder().mergeFrom(data).build();
				saveOrUpdateAdTypeConfig(adPosition);
				break;
			case kApp:
				App sspapp = App.newBuilder().mergeFrom(data).build();
				LOG.info("add sspapp to adserv-ngx, appId: {},status: {}", sspapp.getAppId(), sspapp.getStatus());
				saveOrUpdateApp(sspapp);
				break;
			case kSspAdPosition:
				SspAdPosition sspAdPosition = SspAdPosition.newBuilder().mergeFrom(data).build();
				LOG.info("add ssp adposition to adserv-ngx, adposition id: {},status: {}", sspAdPosition.getAdPosId(),
						sspAdPosition.getStatus());
				saveOrUpdateSspAdPosition(sspAdPosition);
				break;
			case kAdPositionFloorPrice:
				AdPositionFloorPrice adfp = AdPositionFloorPrice.newBuilder().mergeFrom(data).build();
				saveOrUpdateAdPositionFloorPrice(adfp);
				break;
			case kCompetingProduct:
				CompetingProduct cp = CompetingProduct.newBuilder().mergeFrom(data).build();
				saveOrUpdateCompetingProduct(cp);
				break;
			default:
				break;
			}
		} catch (Throwable ex) {
			LOG.error("", ex);
		}
	}

	@Override
	public synchronized void cleanup() {
		_agent_holder.clear();
		_advertiser_holder.clear();
		_adplan_holder.clear();
		_adunit_holder.clear();
		_adtypecfg_holder.clear();
		_agentFloorPriceConfig_holder.clear();
		_sys_config_holder.clear();
		_sbd_stat_holder.clear();
		_frequency_capping_holder.clear();
		_competing_keyword_holder.clear();
		_app_holder.clear();
		_ssp_ad_pos_holder.clear();
		_dsp_holder.clear();
		try {
			_dsp_creative_holder.invalidateAll();
		} catch (Exception ex) {
		}
		_area_level_holder.clear();
		_ad_position_floor_price_holder.clear();
		// 这里需要清除dsp索引相关数据
		DspIndexUtils.clear();
		CpaCappingDataHolder.clear();
	}

	@Override
	public synchronized boolean store() {
		MinervaDB db = null;
		long start = System.currentTimeMillis();
		LOG.info("==store adngx data to minervadb==");
		try {
			MinervaDB.destroy();
			db = MinervaDB.open();

			for (Iterator<Agent> iterator = _agent_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<Advertiser> iterator = _advertiser_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<AdPlan> iterator = _adplan_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<AdUnit> iterator = _adunit_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<AdTypeConfig> iterator = _adtypecfg_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<AgentFloorPriceConfig> iterator = _agentFloorPriceConfig_holder.values().iterator(); iterator
					.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<AdCommonConfig> iterator = _sys_config_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<App> iterator = _app_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<SspAdPosition> iterator = _ssp_ad_pos_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

			for (Iterator<Dsp> iterator = _dsp_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}

//			for (Iterator<DspCreative> iterator = _dsp_creative_holder.values().iterator(); iterator.hasNext();) {
//				db.write(record(iterator.next()));
//			}

			for (Iterator<AreaLevel> iterator = _area_level_holder.values().iterator(); iterator.hasNext();) {
				db.write(record(iterator.next()));
			}
			for (Iterator<AdPositionFloorPrice> iterator = _ad_position_floor_price_holder.values().iterator(); iterator
					.hasNext();) {
				db.write(record(iterator.next()));
			}
			LOG.info("==store adngx data to minervadb...OK, cost: {}ms==", System.currentTimeMillis() - start);
			return true;
		} catch (Exception ex) {
			LOG.error("write ad data to minerva db error", ex);
		} finally {
			db.close();
		}
		return false;
	}

	@Override
	public void stat(StatInfo stat) {
		stat.validAdunitCount = _adunit_holder.size();
		stat.validAdplanCount = _adplan_holder.size();
		stat.validAdvertiserCount = _advertiser_holder.size();
		stat.validAgentCount = _agent_holder.size();
		stat.validAgentFloorCount = _agentFloorPriceConfig_holder.size();
		stat.smoothBudgetDeliveryAdCount = _sbd_stat_holder.size();
		stat.cpaCappingAdCount = CpaCappingDataHolder.cpaCappingAdCount();
		stat.frequencyCappingAdCount = _frequency_capping_holder.size();
		stat.competingKeywordAdCount = _competing_keyword_holder.size();
		
	}

	@Override
	public void saveOrUpdateApp(App sspapp) {
		_app_holder.put(sspapp.getAppId(), sspapp);
	}

	@Override
	public App loadApp(String appId) {
		return _app_holder.get(appId);
	}

	@Override
	public void saveOrUpdateSspAdPosition(SspAdPosition sspAdPosition) {
		_ssp_ad_pos_holder.put(sspAdPosition.getAdPosId(), sspAdPosition);
	}

	@Override
	public SspAdPosition loadSspAdPosition(String adPosId) {
		return _ssp_ad_pos_holder.get(adPosId);
	}

	@Override
	public void saveOrUpdateDsp(Dsp dsp) {
		_dsp_holder.put(dsp.getDspId(), dsp);
	}

	@Override
	public void removeDsp(String dspId) {
		_dsp_holder.remove(dspId);
	}

	@Override
	public void saveOrUpdateDspCreative(DspCreative dspCreative) {
		LOG.info("==save dsp creative to adserv-ngx, dspCreative: {}",
				dspCreative == null ? "" : dspCreative.toString());
		String key = String.format("dspc:%s:%s", dspCreative.getDspId(), dspCreative.getCreativeId());
		_dsp_creative_holder.put(key, dspCreative);
		ThreadPools.concurrentDoTask(
				() -> RedisTemplate.executeQuietly((r) -> r.set(key.getBytes(), dspCreative.toByteArray())));
	}

	@Override
	public List<Dsp> getAllValidDspList() {
		List<Dsp> validDspList = new ArrayList<>();
		_dsp_holder.forEach((id, dsp) -> {
			if (dsp.getStatus()) {
				validDspList.add(dsp);
			}
		});
		return validDspList;
	}

	@Override
	public Dsp loadDsp(String dspId) {
		return _dsp_holder.get(dspId);
	}

	@Override
	public void saveOrUpdateAreaLevel(AreaLevel areaLevel) {
		_area_level_holder.put(areaLevel.getId(), areaLevel);
	}

	@Override
	public void saveOrUpdateAdPositionFloorPrice(AdPositionFloorPrice adfp) {
		LOG.info("==保存广告位底价信息：{}==", adfp.toString());
		_ad_position_floor_price_holder.put(AdPositionFloorPriceUtils.genKey(adfp), adfp);
	}

	@Override
	public void saveOrUpdateCompetingProduct(CompetingProduct cp) {
		LOG.info("==保存竞品信息：{}={}=", cp.toString(), cp.getKeyword());
		if (cp.hasKeyword() && !cp.getKeyword().isEmpty()) {
			for (String keyword : _competing_keyword_holder) {
				if (keyword.equals(cp.getKeyword())) {
					return;
				}
			}
			_competing_keyword_holder.add(cp.getKeyword());
		}
	}

	@Override
	public List<String> getAllCompetingKeyword() {
		return _competing_keyword_holder;
	}

	@Override
	public AdPositionFloorPrice loadAdPositionFloorPrice(String key) {
		return _ad_position_floor_price_holder.get(key);
	}

	@Override
	public DspCreative getDspCreative(String crid, String dspId) {
		String key = String.format("dspc:%s:%s", dspId, crid);

		try {
			DspCreative dspCreative = _dsp_creative_holder.get(key);
			return dspCreative;
		} catch (Exception ex) {
			LOG.error("获取第三方创意异常, dspId: " + dspId + ", crid: " + crid);
		}
		return null;
	}

	@Override
	public void removeDspCreative(DspCreative dspCreative) {
		try {
		_dsp_creative_holder
				.invalidate(String.format("dspc:%s:%s", dspCreative.getDspId(), dspCreative.getCreativeId()));
		}catch(Exception ex) {
			//do nothing
		}
	}

	@Override
	public List<Dsp> getAllDspList() {
		List<Dsp> dspList = new ArrayList<>();
		dspList.addAll(_dsp_holder.values());
		return dspList;
	}

	@Override
	public List<DspCreative> getAllDspCreativeList() {
		List<DspCreative> dspCreativeList = new ArrayList<>();
		dspCreativeList.addAll(_dsp_creative_holder.asMap().values());
		return dspCreativeList;
	}
}

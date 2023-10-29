/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.notification;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.index.ReverseIndexFactory;
import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.router.DspIndexUtils;
import com.iwanvi.adserv.ngx.router.DspRateLimiters;
import com.iwanvi.adserv.ngx.util.AreaLevelIndex;
import com.iwanvi.adserv.ngx.util.BiddingRateLimiterFactory;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.CpaCappingDataHolder;
import com.iwanvi.adserv.ngx.util.PubIndexUtils;
import com.iwanvi.adserv.ngx.util.RedisCallback;
import com.iwanvi.adserv.ngx.util.RedisTemplate;
import com.iwanvi.adserv.ngx.util.SBDStatHolder;
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
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.NotifyMsg;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;

/**
 * dsp平台广告模型变更消息处理器
 * 
 * @author wangwp
 */
public class DataSyncMsgListener extends BinaryJedisPubSub {
	private static final Logger LOGGER = LoggerFactory.getLogger("MinervaNotifyConsumer");
	private static final Repository repository = RepositoryFactory.getRepository();

	@Override
	public void onMessage(byte[] channel, byte[] message) {
		NotifyMsg notifyMsg = null;
		try {
			notifyMsg = NotifyMsg.newBuilder().mergeFrom(message).build();
			if (notifyMsg.getOpType() != OpType.kPing) {
				LOGGER.info("Received notify msg: {}", TextFormat.printToString(notifyMsg));
			}
		} catch (Exception ex) {
			LOGGER.error("Invaid notify message", ex);
		}

		if (notifyMsg == null) {
			return;
		}

		try {
			// 全量同步
			if (notifyMsg.hasFileName()) {
				long start = System.currentTimeMillis();
				LOGGER.info("==开始进行全量同步, file url: {}==", notifyMsg.getFileName());
				PubIndexUtils.downloadAndLoadAdNgxData(notifyMsg.getFileName());
				LOGGER.info("==全量数据同步完成，耗时：" + (System.currentTimeMillis() - start) + "ms");
				return;
			}

			EntityType entityType = notifyMsg.getEntityType();
			switch (entityType) {
			case kAdPlan:
				updateAdPlan(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kAdUnit:
				updateAdUnit(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kAdvertiser:
				updateAdvertiser(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kAdTypeConfig:
				updateAdTypeConfig(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kCommonConfig:
				updateCommonConfig();
				break;
			case kAgent:
				updateAgent(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kAgentFloorPriceConfig:
				updateAgentFloorPriceConfig(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kApp:
				updateApp(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kSspAdPosition:
				updateSspAdPosition(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kDsp:
				updateDsp(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kDspCreative:
				updateDspCreative(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kAreaLevel:
				updateAreaLevel(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			case kAdPositionFloorPrice:
				updateAdPositionFloorPrice(notifyMsg.getEntityId(), notifyMsg.getOpType());
				break;
			default:
				break;
			}
		} catch (Throwable ex) {
			LOGGER.error("", ex);
		}
	}

	private void updateAdPositionFloorPrice(int entityId, OpType opType) {
		final String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_ADPF, entityId);
		RedisTemplate.execute((r) -> {
			byte[] data = r.get(key.getBytes());
			if (data == null)
				return null;

			try {
				AdPositionFloorPrice adfp = AdPositionFloorPrice.newBuilder().mergeFrom(data).build();
				repository.saveOrUpdateAdPositionFloorPrice(adfp);
			} catch (Exception ex) {
				LOGGER.error("==保存媒体广告位底价异常==", ex);
			}
			return null;
		});
	}

	private void updateAreaLevel(int entityId, OpType opType) {
		RedisTemplate.execute((redis) -> {
			String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_AREALEVEL, entityId);
			byte[] data = redis.get(key.getBytes());
			if (data == null)
				return null;

			try {
				AreaLevel areaLevel = AreaLevel.newBuilder().mergeFrom(data).build();
				repository.saveOrUpdateAreaLevel(areaLevel);
				AreaLevelIndex.getInstance().index(areaLevel);
			} catch (Exception ex) {
				LOGGER.error("", ex);
			}
			return null;
		});
	}

	private void updateDspCreative(int entityId, OpType opType) {
		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_DSP_CREATIVE, entityId);
				byte[] data = jedis.get(key.getBytes());
				if (data == null) {
					LOGGER.info("");
					return null;
				}

				try {
					DspCreative dspCreative = DspCreative.newBuilder().mergeFrom(data).setId(entityId).build();
					LOGGER.info("==更新第三方广告平台创意：" + dspCreative.toString());
					if (dspCreative.getStatus() == 0) {
						repository.removeDspCreative(dspCreative);
					} else {
						repository.saveOrUpdateDspCreative(dspCreative);
					}
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}

	private void updateDsp(int entityId, OpType opType) {
		final String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_DSP, entityId);
		RedisTemplate.execute((jedis) -> {
			byte[] data = jedis.get(key.getBytes());
			if (data == null)
				return null;
			try {
				Dsp dsp = Dsp.newBuilder().mergeFrom(data).build();
				if (dsp == null)
					return null;

				Dsp origDsp = repository.loadDsp(dsp.getDspId());
				DspIndexUtils.deleteDsp(origDsp);

				if (!dsp.getStatus()) {
					repository.removeDsp(dsp.getDspId());
				} else {
					repository.saveOrUpdateDsp(dsp);
					DspIndexUtils.indexDsp(dsp);
					DspRateLimiters.removeRateLimiter(dsp.getDspId());
					DspRateLimiters.createOrUpdateRateLimiter(dsp);
				}
			} catch (Exception ex) {
				// DO NOTHING
			}
			return null;
		});
	}

	private void updateSspAdPosition(final int entityId, OpType opType) {
		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_SSP_AD_POSITION, entityId);
				byte[] data = jedis.get(key.getBytes());
				if (data == null)
					return null;

				try {
					SspAdPosition sspAdPosition = SspAdPosition.newBuilder().mergeFrom(data).build();
					repository.saveOrUpdateSspAdPosition(sspAdPosition);
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}

	private void updateApp(final int entityId, final OpType opType) {
		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_SSP_APP, entityId);
				byte[] data = jedis.get(key.getBytes());
				if (data == null)
					return null;

				try {
					App sspapp = App.newBuilder().mergeFrom(data).build();
					repository.saveOrUpdateApp(sspapp);
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}

	private void updateAgentFloorPriceConfig(final int entityId, OpType opType) {
		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_AGENT_FLOOR_PRICE_CONFIG, entityId);
				byte[] data = jedis.get(key.getBytes());
				if (data == null)
					return null;

				try {
					AgentFloorPriceConfig agentFloorPriceConfig = AgentFloorPriceConfig.newBuilder().mergeFrom(data)
							.build();
					repository.saveOrUpdateAgentFloorPriceConfig(agentFloorPriceConfig);
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}

	private void updateAgent(final int entityId, OpType opType) {
		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_AGENT, entityId);
				byte[] data = jedis.get(key.getBytes());
				if (data == null)
					return null;
				try {
					Agent agent = Agent.newBuilder().mergeFrom(data).build();
					repository.saveOrUpdateAgent(agent);
				} catch (Exception ex) {
					LOGGER.error("update Agent error, cause: {}", ex.getMessage());
				}
				return null;
			}
		});
	}

	private void updateCommonConfig() {
		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				byte[] data = jedis.get(Constants.REDIS_KEY_PREFIX_COMMON.getBytes());
				if (data == null) {
					return null;
				}
				try {
					AdCommonConfig cfg = AdCommonConfig.newBuilder().mergeFrom(data).build();
					repository.saveAdCommonConfig(cfg);
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}

	private void updateAdTypeConfig(final int entityId, OpType opType) {
		if (opType == OpType.kDelete) {
			RepositoryFactory.getRepository().removeAdTypeConfig(entityId);
			return;
		}

		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_AD_TYPE_CONFIG, entityId);
				byte[] data = jedis.get(key.getBytes());
				if (data == null) {
					return null;
				}

				try {
					AdTypeConfig adPosition = AdTypeConfig.newBuilder().mergeFrom(data).build();
					repository.saveOrUpdateAdTypeConfig(adPosition);
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}

	private void updateAdvertiser(final int entityId, OpType opType) {
		if (opType == OpType.kDelete) {
			RepositoryFactory.getRepository().removeAdvertiser(entityId);
			return;
		}

		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = String.format("%s%d", Constants.REDIS_KEY_PREFIX_ADVERTISER, entityId);
				byte[] data = jedis.get(key.getBytes());
				if (data == null) {
					return null;
				}
				try {
					Advertiser advertiser = Advertiser.newBuilder().mergeFrom(data).build();
					repository.saveOrUpdateAdvertiser(advertiser);
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}

	private void updateAdUnit(final int entityId, OpType opType) {
		if (opType == OpType.kDelete) {
			AdUnit adUnit = repository.loadAdUnit(entityId);
			if (adUnit != null) {
				repository.removeAdUnit(entityId);
				SBDStatHolder.unregisterSBDAdUnit(adUnit.getUnitId());
				// 更新倒排索引
				ReverseIndexFactory.getReverseIndex().delete(adUnit);
			}
			return;
		}

		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = StringUtils.join(new Object[] { Constants.REDIS_KEY_PREFIX_AD_UNIT, entityId });
				byte[] data = jedis.get(key.getBytes());
				if (data == null)
					return null;

				try {
					AdUnit origAdUnit = repository.loadAdUnit(entityId);
					if (origAdUnit != null) {
						LOGGER.info("Delete old reverse index data, adUnit's id: {}", origAdUnit.getUnitId());
						ReverseIndexFactory.getReverseIndex().delete(origAdUnit);
					}

					AdUnit.Builder builder = AdUnit.newBuilder().mergeFrom(data);
					AdUnit adUnit = builder.build();

					// 对于推广单元来说需要同步更新倒排索引文件，如果推广单元状态为无效，则不更新倒排索引
					if (adUnit.getStatus() != 0) {
						repository.saveOrUpdateAdUnit(adUnit);
						ReverseIndexFactory.getReverseIndex().buildIndex(adUnit);
						SBDStatHolder.registerSBDAdUnit(adUnit);
						BiddingRateLimiterFactory.createOrUpdateRateLimiter(adUnit);
						CpaCappingDataHolder.registerCpaAdunit(adUnit);
					} else {
						LOGGER.info("Delete invalid adunit, uid: {}", entityId);
						repository.removeAdUnit(entityId);
						SBDStatHolder.unregisterSBDAdUnit(entityId);
						CpaCappingDataHolder.unregisterCpaAdunit(entityId);
					}
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}

	private void updateAdPlan(final int entityId, final OpType opType) {
		if (opType == OpType.kDelete) {
			repository.removeAdPlan(entityId);
			return;
		}

		RedisTemplate.execute(new RedisCallback() {
			@Override
			public Object doRedis(Jedis jedis) {
				String key = StringUtils.join(new Object[] { Constants.REDIS_KEY_PREFIX_AD_PLAN, entityId });
				byte[] data = jedis.get(key.getBytes());

				if (data == null) {
					LOGGER.error("Not found adPlan from redis, key: {}", key);
					return null;
				}
				try {
					AdPlan adPlan = AdPlan.newBuilder().mergeFrom(data).build();
					repository.saveOrUpdateAdPlan(adPlan);
				} catch (InvalidProtocolBufferException ex) {
					LOGGER.error("", ex);
				}
				return null;
			}
		});
	}
}

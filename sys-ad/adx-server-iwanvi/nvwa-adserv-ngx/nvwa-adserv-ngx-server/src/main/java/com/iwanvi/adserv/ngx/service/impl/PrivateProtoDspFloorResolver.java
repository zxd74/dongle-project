/**
 * 
 */
package com.iwanvi.adserv.ngx.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.protobuf.InvalidProtocolBufferException;
import com.iwanvi.adserv.ngx.AdNgxException;
import com.iwanvi.adserv.ngx.service.BidFloorResolver;
import com.iwanvi.adserv.ngx.util.AdPositionFloorPriceUtils;
import com.iwanvi.adserv.ngx.util.RedisUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

import redis.clients.jedis.Jedis;

/**
 * 私有协议dsp广告位底价获取
 * 
 * @author wangweiping
 *
 */
public class PrivateProtoDspFloorResolver implements BidFloorResolver {
	private static final Logger LOG = LoggerFactory.getLogger(PrivateProtoDspFloorResolver.class);

	private static final String AD_POSITION_PRICE_REDIS_KEY = "adpfp:";
	private static LoadingCache<String, AdPositionFloorPrice> cache;

	static {
		// CacheBuilder.newBuilder().ex
		cache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES)
				.build(new CacheLoader<String, AdPositionFloorPrice>() {
					@Override
					public AdPositionFloorPrice load(String key) throws Exception {
						try {
							Jedis r = RedisUtils.getRedisClient();
							byte[] data = r.get(key.getBytes());
							if (data == null) {
								throw new RuntimeException("no data");
							}
							try {
								AdPositionFloorPrice adfp = AdPositionFloorPrice.newBuilder().mergeFrom(data).build();
								return adfp;
							} catch (InvalidProtocolBufferException ex) {
								LOG.error("==从缓存中获取广告位底价异常==", ex);
								throw ex;
							} finally {
								if (r != null)
									r.close();
							}
						} catch (Throwable ex) {
							// DO NOTHING
							throw ex;
						}
					}
				});
	}

	@Override
	public double getBidFloor(BiddingReq biddingReq, AdMsg bid, String dspId) {
		final String adPosId = getAdPosId(biddingReq, bid);
		String cacheKey = StringUtils.join(AD_POSITION_PRICE_REDIS_KEY,
				AdPositionFloorPriceUtils.genHashKeyWithAreaLevel(adPosId, 1, 1));

		try {
			AdPositionFloorPrice adPositionFloorPrice = cache.get(cacheKey);
			if (adPositionFloorPrice == null)
				throw new AdNgxException("获取底价异常");
			LOG.info("==get bidfloor from redis, key:{}, industry:{},adPosId:{},areaLevel:{}, bidfloor: {}==", cacheKey,
					1, adPosId, 1, adPositionFloorPrice.getFloorPrice());
			return adPositionFloorPrice.getFloorPrice();
		} catch (Exception ex) {
			LOG.error("获取广告位底价异常, cause: " + ex.getMessage());
			throw new AdNgxException(ex);
		}
	}

	private String getAdPosId(BiddingReq biddingReq, AdMsg bid) {
		return biddingReq.getPosInfo(0).getPosId().toStringUtf8();
	}
}

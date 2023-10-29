/*
 * Copyright 2019 iwanvi.com All right reserved.
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
import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.service.BidFloorResolver;
import com.iwanvi.adserv.ngx.util.AdPositionFloorPriceUtils;
import com.iwanvi.adserv.ngx.util.RedisUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

import redis.clients.jedis.Jedis;

/**
 * @author weiping wang
 *
 */
public class DefaultBidFloorResolverImpl implements BidFloorResolver {
	private static final Logger LOG = LoggerFactory.getLogger(BidFloorResolver.class);

	private static final String AD_POSITION_PRICE_REDIS_KEY = "adpfp:";

	private static Repository repo = RepositoryFactory.getRepository();
	private static LoadingCache<String, AdPositionFloorPrice> cache;

	static {
		cache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES)
				.build(new CacheLoader<String, AdPositionFloorPrice>() {
					@Override
					public AdPositionFloorPrice load(String key) throws Exception {
						Jedis r = RedisUtils.getRedisClient();
						byte[] data = r.get(key.getBytes());
						if (data == null) {
							throw new RuntimeException("no cache data");
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
					}
				});
	}

	@Override
	public double getBidFloor(BiddingReq biddingReq, AdMsg bid, String dspId) {
		int industry = getIndustry(biddingReq, bid);

		final String adPosId = getAdPosId(biddingReq, bid);
		int areaCode = biddingReq.getUserInfo().getAreaCode();
		String cacheKey = StringUtils.join(AD_POSITION_PRICE_REDIS_KEY,
				AdPositionFloorPriceUtils.genHashKeyWithAreaLevel(adPosId, industry, 1));

		AdPositionFloorPrice adPositionFloorPrice;
		try {
			adPositionFloorPrice = cache.get(cacheKey);
			if (adPositionFloorPrice == null)
				throw new AdNgxException("Not found adposition floor price");

			double floor = adPositionFloorPrice.getFloorPrice();
			LOG.debug("==get bidfloor from redis, key:{}, industry:{},adPosId:{},areaCode:{}, bidfloor: {}==", cacheKey,
					industry, adPosId, areaCode, floor);
			return floor;
		} catch (Exception ex) {
			LOG.error("==获取广告位底价异常==", ex);
			throw new AdNgxException(ex);
		}
	}

	private int getIndustry(BiddingReq biddingReq, AdMsg bid) {
		String crid = bid.getCrid();
		if (StringUtils.isBlank(crid)) {
			Advertiser advertiser = repo.loadAdvertiser(bid.getAdvertiserId());
			return advertiser == null ? 1 : advertiser.getIndustryId();
		} else {
			DspCreative dspCreative = repo.getDspCreative(crid, bid.getDspId());
			return dspCreative == null ? 1 : dspCreative.getIndustry();
		}
	}

	private String getAdPosId(BiddingReq biddingReq, AdMsg bid) {
		return biddingReq.getPosInfo(0).getPosId().toStringUtf8();
	}
}

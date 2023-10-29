/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;

/**
 * 
 * @author wangwp
 */
public class AdPositionFloorPriceUtils {
	private static final long hash_seed = 19860319;

	public static long genHashKey(AdPositionFloorPrice adfp) {
		String hashString = StringUtils.join(adfp.getAdPositionId(), adfp.getAreaLevelId(), adfp.getIndustry());
		long key = MurmurHash.hash2_64(hashString, hash_seed);
		return key;
	}

	public static String genKey(AdPositionFloorPrice adfp) {
		return StringUtils.join(adfp.getAdPositionId(), adfp.getAreaLevelId(), adfp.getIndustry());
	}

	public static double getBidFloor(int industry, int areaCode, String adPosId) {
		Integer areaLevel = AreaLevelIndex.getInstance().getAreaLevel(areaCode);
		String key = StringUtils.join(adPosId, areaLevel, industry);

		AdPositionFloorPrice floorPriceCfg = RepositoryFactory.getRepository().loadAdPositionFloorPrice(key);
		return floorPriceCfg == null ? -1d : floorPriceCfg.getFloorPrice();
	}

	public static long genHashKey(String adPosId, int industry, int areaCode) {
		Integer areaLevel = AreaLevelIndex.getInstance().getAreaLevel(areaCode);
		String key = StringUtils.join(adPosId, areaLevel, industry);

		return MurmurHash.hash2_64(key, hash_seed);
	}
	
	public static long genHashKeyWithAreaLevel(String adPosId, int industry, int areaLevel) {
		//Integer areaLevel = AreaLevelIndex.getInstance().getAreaLevel(areaCode);
		String key = StringUtils.join(adPosId, areaLevel, industry);

		return MurmurHash.hash2_64(key, hash_seed);
	}
	
	public static void main(String[] args) throws Exception {
		long l = MurmurHash.hash2_64(StringUtils.join("VRBf6n",1,1), hash_seed);
		System.out.println(l);
	}
}

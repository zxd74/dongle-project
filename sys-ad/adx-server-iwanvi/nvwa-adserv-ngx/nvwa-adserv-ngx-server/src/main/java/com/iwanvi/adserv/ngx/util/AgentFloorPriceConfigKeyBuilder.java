/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaLevel;
import com.iwanvi.nvwa.proto.CommonProto.OSType;

/**
 * 代理商低价存储key构建器
 * 
 * @author wangwp
 */
public class AgentFloorPriceConfigKeyBuilder {

	public static String buildKey(AgentFloorPriceConfig config) {
		String key = StringUtils.join(new Object[] { config.getAgentId(), config.getMediaUuid(), config.getIndustryId(),
				config.getAdPosId(), config.getAreaLevelId() }, Constants.SIGN_UNDERLINE);
		return key;
	}

	public static String buildKey(int agentId, String mediaUuid, int industryId, int adPosId, OSType os,
			AreaLevel areaLevel) {
		return StringUtils.join(new Object[] { agentId, mediaUuid, industryId, adPosId, areaLevel.getId() },
				Constants.SIGN_UNDERLINE);
	}

	public static long buildLongKey(AgentFloorPriceConfig config) {
		String agentFloorPriceConfigKeyAsString = StringUtils.join(new Object[] { config.getAgentId(),
				config.getMediaUuid(), config.getIndustryId(), config.getAdPosId(), config.getAreaLevelId() },
				Constants.SIGN_UNDERLINE);

		long key = MurmurHash.hash2_64(agentFloorPriceConfigKeyAsString, Constants.HASHING_SEED);
		return key;
	}

	public static long buildLongKey(int agentId, String mediaUuid, int industryId, int adPosId, OSType os,
			Integer areaLevel) {
		return MurmurHash.hash2_64(StringUtils.join(new Object[] { agentId, mediaUuid, industryId, adPosId, areaLevel },
				Constants.SIGN_UNDERLINE), Constants.HASHING_SEED);
	}
}

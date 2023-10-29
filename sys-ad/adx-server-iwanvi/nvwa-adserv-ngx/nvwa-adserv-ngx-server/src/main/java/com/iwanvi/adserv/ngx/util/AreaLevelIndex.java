/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaLevel;

/**
 * 地域级别索引
 * 
 * @author wangwp
 */
public class AreaLevelIndex {
	private static final AreaLevelIndex INSTANCE = new AreaLevelIndex();

	private final Map<String, Map<Integer, Integer>> areaLevelRepository;

	private AreaLevelIndex() {
		// 分媒体地域等级，格式：<media_uuid,<area_code,area_level_id>>
		this.areaLevelRepository = new ConcurrentHashMap<String, Map<Integer, Integer>>();

		init();
	}

	public static AreaLevelIndex getInstance() {
		return INSTANCE;
	}

	public void init() {
		this.areaLevelRepository.put(MinervaCfg.get().getInternalMediaUuid(), new HashMap<>());
	}

	public Integer getAreaLevel(int areaCode) {
		String mediaUuid = MinervaCfg.get().getInternalMediaUuid();
		return getAreaLevel(mediaUuid, areaCode);
	}

	public Integer getAreaLevel(String mediaUuid, int areaCode) {
		Map<Integer, Integer> mediaAreaLevels = areaLevelRepository.get(mediaUuid);
		if (mediaAreaLevels == null || mediaAreaLevels.isEmpty()) {
			return null;
		}

		// 获取地域码的前4位城市代码
		Integer areaLevel = mediaAreaLevels.get(areaCode / 100);
		if (areaLevel == null) {
			if (isMunicipality(areaCode)) {
				areaLevel = mediaAreaLevels.get(areaCode / 10000);
			}
		}
		return areaLevel;
	}

	public void index(String mediaUuid, AreaLevel areaLevel) {
		Map<Integer, Integer> mediaAreaLevels = areaLevelRepository.get(mediaUuid);
		if (mediaAreaLevels == null) {
			mediaAreaLevels = new HashMap<>();
			areaLevelRepository.put(mediaUuid, mediaAreaLevels);
		}

		if (areaLevel == null)
			return;

		for (Integer areacode : areaLevel.getAreaCodesList()) {
			int city = areacode / 100;
			mediaAreaLevels.put(city, areaLevel.getId());
			if (isMunicipality(areacode)) {
				mediaAreaLevels.put(areacode / 10000, areaLevel.getId());
			}
		}
	}

	private boolean isMunicipality(int areacode) {
		String strAreaCode = String.valueOf(areacode);
		if (strAreaCode.startsWith("11") || strAreaCode.startsWith("12") || strAreaCode.startsWith("31")
				|| strAreaCode.startsWith("50")) {
			return true;
		}
		return false;
	}

	public void index(AreaLevel areaLevel) {
		String internalMediaUuid = MinervaCfg.get().getInternalMediaUuid();
		index(internalMediaUuid, areaLevel);
	}
}

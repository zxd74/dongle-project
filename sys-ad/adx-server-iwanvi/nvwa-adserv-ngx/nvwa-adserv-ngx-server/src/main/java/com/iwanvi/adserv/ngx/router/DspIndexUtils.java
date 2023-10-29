/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.MurmurHash;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspTarget;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;

/**
 * dsp索引工具类,在流量分发的时候用来快速检索符合流量的dsp平台
 * 
 * @author wangwp
 */
public final class DspIndexUtils {
	private static final ConcurrentMap<Long, List<String>> dsp_index = new ConcurrentHashMap<>();
	private static long HASH_SEED = 19881224L;
	private static final ConcurrentMap<String, String> target_channels_dsp_holder = new ConcurrentHashMap<String, String>();

	public static void indexDsp(Dsp dsp) {
		DspTarget dspTarget = dsp.getDspTarget();
		List<String> tagids = dspTarget.getAdPosTargetsList().stream().flatMap(t -> t.getTagidsList().stream())
				.collect(Collectors.toList());
		List<Integer> areaCodes = dspTarget.getAreaTargetsList().stream().flatMap(t -> t.getAreaCodeList().stream())
				.collect(Collectors.toList());
		List<String> catList = dspTarget.getBookCategoryTargetsList().stream()
				.flatMap(t -> t.getCategoryIdsList().stream()).collect(Collectors.toList());
		List<String> channels = dspTarget.getChannelTarget().getChannelIdsList();
		if (!channels.isEmpty()) {
			channels.forEach(c -> target_channels_dsp_holder.put(c, dsp.getDspId()));
		}
		
		List<String> didList = dspTarget.getDeviceTarget().getDidList();
		if (CollectionUtils.isEmpty(areaCodes)) {
			areaCodes = Arrays.asList(0);
		}

		if (CollectionUtils.isEmpty(catList)) {
			catList = Arrays.asList("0");
		}

		if (didList.isEmpty())
			didList = Arrays.asList("0");

		for (String tagid : tagids) {
			for (Integer areaCode : areaCodes) {
				for (String cat : catList) {
					for (String did : didList) {
						Long indexKey = MurmurHash.hash2_64(String.format("%s%s%s%s", tagid, areaCode, cat, did),
								HASH_SEED);
						if (dsp_index.containsKey(indexKey)) {
							List<String> dspIds = dsp_index.get(indexKey);
							if (!dspIds.contains(dsp.getDspId())) {
								dsp_index.get(indexKey).add(dsp.getDspId());
							}
						} else {
							List<String> dspList = new ArrayList<>();
							dspList.add(dsp.getDspId());
							dsp_index.put(indexKey, dspList);
						}
					}
				}
			}
		}
	}

	public static List<Long> indexKeys(Dsp dsp) {
		DspTarget dspTarget = dsp.getDspTarget();
		List<String> tagids = dspTarget.getAdPosTargetsList().stream().flatMap(t -> t.getTagidsList().stream())
				.collect(Collectors.toList());
		List<Integer> areaCodes = dspTarget.getAreaTargetsList().stream().flatMap(t -> t.getAreaCodeList().stream())
				.collect(Collectors.toList());
		List<String> catList = dspTarget.getBookCategoryTargetsList().stream()
				.flatMap(t -> t.getCategoryIdsList().stream()).collect(Collectors.toList());
		List<String> didList = dspTarget.getDeviceTarget().getDidList();

		if (CollectionUtils.isEmpty(areaCodes)) {
			areaCodes = Arrays.asList(0);
		}

		if (CollectionUtils.isEmpty(catList)) {
			catList = Arrays.asList("0");
		}

		if (didList.isEmpty())
			didList = Arrays.asList("0");

		List<Long> indexKeys = new ArrayList<>();
		for (String tagid : tagids) {
			for (Integer areaCode : areaCodes) {
				for (String cat : catList) {
					for (String did : didList) {
						Long indexKey = MurmurHash.hash2_64(String.format("%s%s%s%s", tagid, areaCode, cat, did),
								HASH_SEED);
						if (!indexKeys.contains(indexKey))
							indexKeys.add(indexKey);
					}
				}
			}
		}
		return indexKeys;
	}

	public static void deleteDsp(Dsp dsp) {
		if (dsp == null || StringUtils.isBlank(dsp.getDspId()))
			return;
		List<Long> keys = indexKeys(dsp);
		if (CollectionUtils.isEmpty(keys))
			return;
		List<String> targetChannels = dsp.getDspTarget().getChannelTarget().getChannelIdsList();

		for (String channel : targetChannels) {
			target_channels_dsp_holder.remove(channel);
		}

		keys.stream().filter(k -> dsp_index.containsKey(k)).forEach(k -> {
			dsp_index.get(k).remove(dsp.getDspId());
		});
	}

	public static List<Long> genIndexKeysByBiddingReq(BiddingReq biddingReq) {
		int areaCode = biddingReq.getUserInfo().getAreaCode();

		List<Integer> areas = new ArrayList<>();
		areas.add(0);
		if (areaCode != 0) {
			areas.add((areaCode / 100) * 100);
		}

		List<String> catList = new ArrayList<>();
		List<String> didList = new ArrayList<String>();

		BiddingContext context = new BiddingContext(biddingReq);
		String bookCat = context.getUserExtProperty("dx_cat");
		catList.add("0");
		didList.add("0");

		if (StringUtils.isNotBlank(bookCat))
			catList.add(bookCat);

		didList.add(biddingReq.getUserInfo().getMuid().toStringUtf8());

		List<String> tagids = biddingReq.getPosInfoList().stream().map(r -> r.getPosId().toStringUtf8())
				.collect(Collectors.toList());

		Set<Long> keys = new HashSet<Long>();
		for (String tagid : tagids) {
			for (Integer area : areas) {
				for (String cat : catList) {
					for (String did : didList) {
						Long key = MurmurHash.hash2_64(String.format("%s%s%s%s", tagid, area, cat, did), HASH_SEED);
						keys.add(key);
					}
				}
			}
		}

		List<Long> indexKeys = new ArrayList<>();
		indexKeys.addAll(keys);
		return indexKeys;
	}

	public static List<Dsp> getDspListByBiddingReq(BiddingReq biddingReq) {
		List<Long> indexKeys = genIndexKeysByBiddingReq(biddingReq);
		BiddingTracer.trace(biddingReq.getIsDebug(), "biddingReq: {}, biddingReq indexKeys: {}",
				TextFormat.printToUnicodeString(biddingReq), indexKeys);

		if (indexKeys == null || indexKeys.isEmpty())
			return Collections.emptyList();

		Set<String> dspIdList = new HashSet<>();
		for (Long key : indexKeys) {
			List<String> idList = dsp_index.get(key);
			if (CollectionUtils.isNotEmpty(idList)) {
				dspIdList.addAll(idList);
			}
		}

		List<Dsp> dspList = new ArrayList<>();

		BiddingContext ctx = new BiddingContext(biddingReq);
		String channel = ctx.getUserExtProperty("dx_channel");
		String target_channel_dsp_id = target_channels_dsp_holder.get(channel);
		if (StringUtils.isNotBlank(target_channel_dsp_id)) {
			if (!dspIdList.contains(target_channel_dsp_id)) {
				return null;
			} else {
				Dsp dsp = RepositoryFactory.getRepository().loadDsp(target_channel_dsp_id);
				if (dsp != null)
					dspList.add(dsp);
				return dspList;
			}
		}

		dspIdList.forEach(dspid -> {
			Dsp dsp = RepositoryFactory.getRepository().loadDsp(dspid);
			// 流量渠道没有指定dsp平台，则所有指定渠道定向的dsp都需要过滤掉
			if (dsp != null) {
				List<String> targetChannels = dsp.getDspTarget().getChannelTarget().getChannelIdsList();
				if (!targetChannels.isEmpty())
					return;
				dspList.add(dsp);
			}
		});
		return dspList;
	}

	public static void clear() {
		dsp_index.clear();
		target_channels_dsp_holder.clear();
	}
}

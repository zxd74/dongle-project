/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.index.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.iwanvi.adserv.ngx.index.AbstractReverseIndex;
import com.iwanvi.adserv.ngx.util.MinervaStatHolder.StatInfo;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

/**
 * 基于哈希表实现的广告倒排索引实现
 * 
 * @author wangwp
 *
 */
public class MapReverseIndex extends AbstractReverseIndex {
	private static final Map<Long, List<Integer>> _reverse_index_holder = new ConcurrentHashMap<>();

	@Override
	public void buildIndex(AdUnit adUnit) {
		List<Long> indexKeys = buildIndexKeys(adUnit);

		for (Long indexKey : indexKeys) {
			if (_reverse_index_holder.containsKey(indexKey)) {
				List<Integer> postingList = _reverse_index_holder.get(indexKey);
				if (!postingList.contains(adUnit.getUnitId())) {
					postingList.add(adUnit.getUnitId());
				}
			} else {
				List<Integer> postingList = new ArrayList<>();
				postingList.add(adUnit.getUnitId());
				_reverse_index_holder.put(indexKey, postingList);
			}
		}
	}

	@Override
	public List<AdUnit> search(List<Long> keys) {
		List<AdUnit> adUnits = new ArrayList<>();
		Set<Integer> adUnitIds = new HashSet<>();

		for (Long key : keys) {
			List<Integer> postingList = _reverse_index_holder.get(key);
			if (postingList != null && !postingList.isEmpty()) {
				adUnitIds.addAll(postingList);
			}
		}

		for (Integer adUnitId : adUnitIds) {
			AdUnit adUnit = getAdUnitById(adUnitId);
			if (adUnit != null) {
				adUnits.add(adUnit);
			}
		}
		adUnitIds = null; // gc helper
		return adUnits;
	}

	@Override
	public void delete(AdUnit adUnit) {
		List<Long> reverseIndexKeys = buildIndexKeys(adUnit);

		for (Long reverseIndexKey : reverseIndexKeys) {
			if (_reverse_index_holder.containsKey(reverseIndexKey)) {
				List<Integer> postingList = _reverse_index_holder.get(reverseIndexKey);
				postingList.remove(Integer.valueOf(adUnit.getUnitId()));
			}
		}
	}

	@Override
	public void clear() {
		_reverse_index_holder.clear();
	}

	@Override
	public void stat(StatInfo stat) {
		stat.reverseIndexSize.set(_reverse_index_holder.size());

		int reverseIndexValueSize = 0;
		for (Iterator<List<Integer>> iterator = _reverse_index_holder.values().iterator(); iterator.hasNext();) {
			reverseIndexValueSize += iterator.next().size();
		}
		stat.reverseIndexValuesSize = reverseIndexValueSize;
	}

	@Override
	public List<Integer> search(long key) {
		List<Integer> results = _reverse_index_holder.get(key);
		return results == null ? new ArrayList<Integer>() : results;
	}
}

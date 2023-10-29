/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.index.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.carrotsearch.hppc.IntArrayList;
import com.carrotsearch.hppc.IntHashSet;
import com.carrotsearch.hppc.LongObjectHashMap;
import com.carrotsearch.hppc.LongObjectMap;
import com.carrotsearch.hppc.cursors.IntCursor;
import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.iwanvi.adserv.ngx.index.AbstractReverseIndex;
import com.iwanvi.adserv.ngx.util.MinervaStatHolder.StatInfo;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

/**
 * 基于高性能的基本数据类型集合框架HPPC实现的倒排索引
 * 
 * @author wangwp
 *
 */
public class HppcReverseIndex extends AbstractReverseIndex {
	private static final LongObjectMap<IntArrayList> _reverse_index_holder = new LongObjectHashMap<>();

	@Override
	public void buildIndex(AdUnit adUnit) {
		List<Long> indexKeys = buildIndexKeys(adUnit);
		int adunit_id = adUnit.getUnitId();

		for (long indexKey : indexKeys) {
			if (_reverse_index_holder.containsKey(indexKey)) {
				IntArrayList postingList = _reverse_index_holder.get(indexKey);
				if (!postingList.contains(adunit_id)) {
					_reverse_index_holder.get(indexKey).add(adunit_id);
				}
			} else {
				IntArrayList postingList = new IntArrayList();
				postingList.add(adunit_id);
				_reverse_index_holder.put(indexKey, postingList);
			}
		}
	}

	@Override
	public List<AdUnit> search(List<Long> keys) {
		IntHashSet postingList = new IntHashSet();
		for (long indexKey : keys) {
			IntArrayList results = _reverse_index_holder.get(indexKey);
			if (results != null && !results.isEmpty()) {
				postingList.addAll(results);
			}
		}

		List<AdUnit> adUnits = new ArrayList<>();
		Iterator<IntCursor> iterator = postingList.iterator();
		while (iterator.hasNext()) {
			int adUnitId = iterator.next().value;
			AdUnit adUnit = getAdUnitById(adUnitId);
			if (adUnit != null)
				adUnits.add(adUnit);
		}
		return adUnits;
	}

	@Override
	public void delete(AdUnit adUnit) {
		List<Long> indexKeys = buildIndexKeys(adUnit);
		int adunit_id = adUnit.getUnitId();

		for (long indexKey : indexKeys) {
			if (_reverse_index_holder.containsKey(indexKey)) {
				_reverse_index_holder.get(indexKey).remove(adunit_id);
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

		for (Iterator<ObjectCursor<IntArrayList>> iterator = _reverse_index_holder.values().iterator(); iterator
				.hasNext();) {
			reverseIndexValueSize += iterator.next().value.size();
		}
		stat.reverseIndexValuesSize = reverseIndexValueSize;
	}

	@Override
	public List<Integer> search(long key) {
		List<Integer> results = new ArrayList<>();
		IntArrayList posttingList = _reverse_index_holder.get(key);
		Iterator<IntCursor> iterator = posttingList.iterator();

		while (iterator.hasNext()) {
			results.add(iterator.next().value);
		}
		return results;
	}
}

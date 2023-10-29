/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 基于权重的随机算法通用实现版本
 * 
 * @author wangwp
 */
public class RRWUtils {

	private static ThreadLocalRandom rand() {
		return ThreadLocalRandom.current();
	}

	/**
	 * 根据指定权重从对象列表中获取
	 * 
	 * @param lists
	 *            对象列表
	 * @param weightResolver
	 *            权重获取器
	 * @return 符合条件的对象
	 */
	public static <T> T rrw(List<T> lists, WeightResolver<T> weightResolver) {
		int sumWeight = 0;
		for (int i = 0; i < lists.size(); i++) {
			sumWeight += weightResolver.getWeight(lists.get(i));
		}

		int random = rand().nextInt(sumWeight);
		int sum = 0;
		for (int i = 0; i < lists.size(); i++) {
			int weight = weightResolver.getWeight(lists.get(i));
			if (sum <= random && random < (sum + weight)) {
				return lists.get(i);
			}
			sum += weight;
		}
		return null;
	}

	/**
	 * 返回符合指定权重的对象在列表中的索引
	 * 
	 * @param lists
	 * @param weightResolver
	 * @return
	 */
	public static <T> int rrwIndex(List<T> lists, WeightResolver<T> weightResolver) {
		int sumWeight = 0;
		for (int i = 0; i < lists.size(); i++) {
			sumWeight += weightResolver.getWeight(lists.get(i));
		}

		int random = rand().nextInt(sumWeight);
		int sum = 0;
		for (int i = 0; i < lists.size(); i++) {
			int weight = weightResolver.getWeight(lists.get(i));
			if (sum <= random && random < (sum + weight)) {
				return i;
			}
			sum += weight;
		}
		return -1;
	}
}

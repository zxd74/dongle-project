/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.Collection;

/**
 * 
 * @author wangwp
 */
public final class CollectionUtils {

	public static boolean containsAll(Collection<?> collection, Collection<?> items) {
		for (Object item : items) {
			if (!collection.contains(item)) {
				return false;
			}
		}
		return true;
	}

	public static boolean containsAny(Collection<?> collection, Collection<?> items) {
		for (Object item : items) {
			if (collection.contains(item)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	}
}

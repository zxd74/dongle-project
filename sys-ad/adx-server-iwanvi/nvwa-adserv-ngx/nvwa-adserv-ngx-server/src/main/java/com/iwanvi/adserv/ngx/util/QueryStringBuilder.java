/**
 * 
 */
package com.iwanvi.adserv.ngx.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.adserv.ngx.AdNgxException;

/**
 * @author wangweiping
 *
 */
public final class QueryStringBuilder<T> {
	private final T t;

	private QueryStringBuilder(T t) {
		this.t = t;
	}

	public static <T> QueryStringBuilder<T> create(T t) {
		return new QueryStringBuilder<T>(t);
	}

	public String toQueryString() {
		StringBuilder qs = new StringBuilder();
		try {
			Field[] fields = t.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				Object value = f.get(t);
				if (value != null) {
					qs.append("&").append(f.getName()).append("=").append(value.toString());
				}
			}
			if (qs.length() > 0)
				return qs.substring(1);
		} catch (Throwable ex) {
			throw new AdNgxException(ex);
		}
		return StringUtils.EMPTY;
	}
}

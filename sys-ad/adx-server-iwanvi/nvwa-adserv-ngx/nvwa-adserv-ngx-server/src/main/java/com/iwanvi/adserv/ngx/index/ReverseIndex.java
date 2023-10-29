/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.index;

import java.util.List;

import com.iwanvi.adserv.ngx.Statable;

/**
 * 
 * @author wangwp
 */
public interface ReverseIndex<T> extends Statable{
	/**
	 * 单元倒排索引构建
	 * 
	 * @param adUnit
	 *            推广单元
	 */
	void buildIndex(T t);

	List<T> search(List<Long> keys);
	
	List<Integer> search(long key);

	void delete(T t);

	void clear();
}

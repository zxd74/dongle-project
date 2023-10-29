/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.repo;

import com.iwanvi.adserv.ngx.Statable;

/**
 * 用户曝光信息数据存储
 * 
 * @author wangwp
 *
 */
public interface ImpRepository extends Statable{
	int getDaily(int user);

	void putDaily(int user, int impressions);

	int getWeekly(int user);

	void putWeekly(int user, int impressions);

	int getMonthly(int user);

	void putMonthly(int user, int impressions);
}

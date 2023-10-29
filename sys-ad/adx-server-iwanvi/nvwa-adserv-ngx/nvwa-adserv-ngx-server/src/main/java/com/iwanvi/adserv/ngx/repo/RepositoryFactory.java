/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.repo;

import com.iwanvi.adserv.ngx.util.ServiceLoaderUtils;

/**
 * @author wangwp
 *
 */
public class RepositoryFactory {
	private static final Repository repo = ServiceLoaderUtils.loadService(Repository.class);

	public static Repository getRepository() {
		return repo;
	}
}

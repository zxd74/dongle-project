/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.repo;

import com.iwanvi.adserv.ngx.util.ServiceLoaderUtils;

/**
 * @author wangwp
 *
 */
public class ImpRepositoryFactory {
	static final ImpRepository repository = ServiceLoaderUtils.loadService(ImpRepository.class);

	public static ImpRepository getRepository() {
		return repository;
	}
}

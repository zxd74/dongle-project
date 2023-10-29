/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.index;

import java.util.List;

import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.util.ReverseIndexKeysUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;

/**
 * 
 * @author wangwp
 */
public abstract class AbstractReverseIndex implements ReverseIndex<AdUnit> {

	public List<Long> buildIndexKeys(AdUnit adUnit) {
		return ReverseIndexKeysUtils.buildReverseIndexKeys(adUnit);
	}

	public AdUnit getAdUnitById(int unitId) {
		return RepositoryFactory.getRepository().loadAdUnit(unitId);
	}
}

/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.repo;

import java.util.List;

import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;

/**
 * @author weiping wang
 *
 */
public interface DspDataRepository {
	List<Dsp> getAllDsps();

	DspCreative getDspCreative(String creativeId);
	
}

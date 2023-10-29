/**
 * 
 */
package com.iwanvi.adserv.ngx.controller;

import java.util.concurrent.ThreadPoolExecutor;

import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.stats.AdNgxStats;
import com.iwanvi.adserv.ngx.util.MinervaDB;
import com.iwanvi.adserv.ngx.util.ThreadPools;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;

import ai.houyi.dorado.rest.annotation.Controller;
import ai.houyi.dorado.rest.annotation.Path;

/**
 * @author wangweiping
 *
 */
@Controller
@Path("/builtin")
public class BuiltinController {

	@Path("/stats")
	public AdNgxStats ngxStats() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) ThreadPools.getDspRouterExecutor();
		int activeCount = executor.getActiveCount();
		AdNgxStats stats = AdNgxStats.builder().withDspRouterActiveThreadCount(activeCount).build();

		return stats;
	}

	@Path("/dsp/{dspId}/creative/{crid}")
	public String dspCreative(String dspId, String crid) {
		DspCreative dspCreative = RepositoryFactory.getRepository().getDspCreative(crid, dspId);
		return TextFormat.printToUnicodeString(dspCreative);
	}

	@Path("/dsp/{dspId}")
	public String dsp(String dspId) {
		Dsp dsp = RepositoryFactory.getRepository().loadDsp(dspId);
		if (dsp == null)
			return "Not found dsp: " + dspId;
		return TextFormat.printToUnicodeString(dsp);
	}

	@Path("/app/{appId}")
	public String app(String appId) {
		App app = RepositoryFactory.getRepository().loadApp(appId);
		return TextFormat.printToUnicodeString(app);
	}

	@Path("/adslot/{posId}")
	public String adslot(String posId) {
		SspAdPosition sspAdPosition = RepositoryFactory.getRepository().loadSspAdPosition(posId);
		return TextFormat.printToUnicodeString(sspAdPosition);
	}
	
	@Path("/db/stats")
	public String statsDb() {
		return MinervaDB.stats()+"";
	}
	
	public String setLogLevel(String name,String level) {
		return "OK";
	}
	
}

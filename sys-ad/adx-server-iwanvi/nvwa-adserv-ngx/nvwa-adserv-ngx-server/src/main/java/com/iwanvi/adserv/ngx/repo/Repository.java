/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.repo;

import java.util.List;

import com.iwanvi.adserv.ngx.Statable;
import com.iwanvi.nvwa.proto.AdModelsProto.AdCommonConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.AdTypeConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.App;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaLevel;
import com.iwanvi.nvwa.proto.AdModelsProto.CompetingProduct;
import com.iwanvi.nvwa.proto.AdModelsProto.Dsp;
import com.iwanvi.nvwa.proto.AdModelsProto.DspCreative;
import com.iwanvi.nvwa.proto.AdModelsProto.SBDStat;
import com.iwanvi.nvwa.proto.AdModelsProto.SspAdPosition;

/**
 * @author wangwp
 *
 */
public interface Repository extends Statable {
	void saveOrUpdateAgent(Agent agent);

	void removeAgent(int id);

	Agent loadAgent(int id);

	void saveOrUpdateAdvertiser(Advertiser advertiser);

	void removeAdvertiser(int id);

	Advertiser loadAdvertiser(int id);

	void saveOrUpdateAdPlan(AdPlan adPlan);

	void removeAdPlan(int id);

	AdPlan loadAdPlan(int id);

	void saveOrUpdateAdUnit(AdUnit adUnit);

	void removeAdUnit(int id);

	AdUnit loadAdUnit(int id);

	void saveOrUpdateAdTypeConfig(AdTypeConfig adTypeConfig);

	AdTypeConfig loadAdTypeConfig(int id);

	void removeAdTypeConfig(int id);

	void saveOrUpdateAgentFloorPriceConfig(AgentFloorPriceConfig afpc);

	AgentFloorPriceConfig loadAgentFloorPriceConfig(long id);

	void saveAdCommonConfig(AdCommonConfig adCommonConfig);

	AdCommonConfig loadAdCommonConfig();

	void saveOrUpdateSBDStat(SBDStat stat);

	SBDStat loadSBDStat(int id);

	void removeSBDStat(int id);

	boolean store();

	boolean reload();

	void impFromPubIndexFile(String file);

	void cleanup();

	void saveOrUpdateApp(App sspapp);

	App loadApp(String appId);

	void saveOrUpdateSspAdPosition(SspAdPosition sspAdPosition);

	SspAdPosition loadSspAdPosition(String adPosId);

	void saveOrUpdateDsp(Dsp dsp);

	void removeDsp(String dspId);

	void saveOrUpdateDspCreative(DspCreative dspCreative);

	void removeDspCreative(DspCreative dspCreative);

	List<Dsp> getAllValidDspList();

	Dsp loadDsp(String dspid);

	void saveOrUpdateAreaLevel(AreaLevel areaLevel);

	void saveOrUpdateAdPositionFloorPrice(AdPositionFloorPrice adfp);
	
	void saveOrUpdateCompetingProduct(CompetingProduct cp);
	
	List<String> getAllCompetingKeyword();

	AdPositionFloorPrice loadAdPositionFloorPrice(String key);

	DspCreative getDspCreative(String crid, String dspId);

	List<Dsp> getAllDspList();

	List<DspCreative> getAllDspCreativeList();
}

package com.iwanvi.nvwa.openapi.dsp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.SdkAdCarouselMapper;
import com.iwanvi.nvwa.openapi.dsp.filter.FrequencyCtrler;
import com.iwanvi.nvwa.openapi.dsp.filter.PlatformCtrler;
import com.iwanvi.nvwa.openapi.dsp.filter.SmoothCtrler;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse.Platform;
import com.iwanvi.nvwa.openapi.dsp.model.SdkConf;
import com.iwanvi.nvwa.openapi.dsp.model.SyncAdPosition;
import com.iwanvi.nvwa.openapi.dsp.service.GeneralService;
import com.iwanvi.nvwa.openapi.dsp.service.TerminalService;
import com.iwanvi.nvwa.openapi.dsp.utils.Constants;
import com.iwanvi.nvwa.openapi.dsp.utils.NvwaUtils;

@Service("confService")
public class ConfServiceImpl implements TerminalService {

	private static final Logger LOG = LoggerFactory.getLogger("ConfService");

	@Resource
	private GeneralService generalService;
	
	@Resource
	private RedisDao redisDao;
	
	@Resource
	private SdkAdCarouselMapper sdkAdCarouselMapper;

	@Override
	public String getConf(String appId) {
		String result = null;
		try {
			String value = redisDao.get(com.iwanvi.nvwa.common.utils.StringUtils.buildString(
					com.iwanvi.nvwa.common.utils.Constants.KEY_CAROUSEL, appId)); // 轮播策略
			if (StringUtils.isBlank(value)) {
				return null;
			}
			JSONArray jsonArray = JSONObject.parseArray(value);
			List<SdkConf> resultList = new ArrayList<SdkConf>();
			JSONObject jsonObject = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				SdkConf sdkConf = SdkConf.builder()
						.posId(jsonObject.getString("uuid"))
						.isDef(jsonObject.getIntValue("is_default"))
						.posType(jsonObject.getIntValue("ad_type"))
						.straChapter(jsonObject.getIntValue("chapter_value"))
						.straDuration(jsonObject.getIntValue("duration_value"))
						.straPage(jsonObject.getIntValue("page_value"))
						.build();
				resultList.add(sdkConf);
			}
			if (resultList.isEmpty()) {
				return null;
			}
			result = JsonUtils.STATUS_OK(resultList);
		} catch (Exception e) {
			LOG.error("getConf error. ", e);
		}
		return result;
	}
	
	@Override
	public List<Platform> getPlatform4period(String appId, 
			String posId, String channel, String version, String did) {
		List<Platform> list = null;
		try {
			SyncAdPosition syncAdPosition = Constants.AD_POSITION_MAP.get(posId);
			JSONObject periodObj = new JSONObject();
			JSONObject jsonObject = null;
			List<String> jsonList = Constants.SDK_ALLOTMENT.get(posId);
			
			if (CollectionUtils.isEmpty(jsonList)) {
				return null;
			}
			for (String jsonStr : jsonList) {
				jsonObject = JSONObject.parseObject(jsonStr);
				if (jsonObject.getIntValue("alloType") == 1) { // 包段
					periodObj.putAll(jsonObject);
				}
			}
			if (periodObj.isEmpty()) {
				return null;
			}
			List<Map<String, Object>> mappingList = syncAdPosition.getMapping();
			if (syncAdPosition == null || mappingList == null || mappingList.isEmpty()) {
				return null;
			} else {
				int platformId = periodObj.getIntValue("platformId");
				int frequency = periodObj.getIntValue("frequency");
				if (PlatformCtrler.isFilter(appId, channel, version)){
				} else if (FrequencyCtrler.isFilter(platformId, frequency, did)) {
				} else {
					list = new ArrayList<AdResponse.Platform>();
					Platform platform = null;
					for (Map<String, Object> mapping : mappingList) {
						if (platformId == NvwaUtils.obj2int(mapping.get("flowConsumerId"))) {
							if (frequency > 0 && StringUtils.isNotBlank(did)) {
								FrequencyCtrler.updateFrequency(platformId, did);
							}
							platform = Platform.builder().appId(NvwaUtils.obj2Empty(mapping.get("consumerAppId")))
								.platformId(periodObj.getString("dspId"))
								.posType(periodObj.getIntValue("posType"))
								.straChapter(periodObj.getIntValue("straChapter"))
								.straPage(periodObj.getIntValue("straPage"))
								.straTime(periodObj.getIntValue("straTime"))
								.posId(NvwaUtils.obj2Empty(mapping.get("consumerPositionId"))).build();
							list.add(platform);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("getPlatform4period error. ", e);
		}
		return list;
	}
	
	@Override
	public List<Platform> getPlatform4Fixed(String appId, 
			String posId, String channel, String version, String did) {
		List<Platform> list = null;
		try {
			JSONObject jsonObject = null;
			List<JSONObject> fixedList = new ArrayList<JSONObject>();
			List<String> jsonList = Constants.SDK_ALLOTMENT.get(posId);
			if (CollectionUtils.isEmpty(jsonList)) {
				return null;
			}
			for (String jsonStr : jsonList) {
				jsonObject = JSONObject.parseObject(jsonStr);
				if (jsonObject.getIntValue("alloType") == 2) { // 固定量
					fixedList.add(jsonObject);
				}
			}
			if (fixedList.isEmpty()) {
				return null;
			}
			
			Collections.sort(fixedList, new Comparator<JSONObject>() {
	            public int compare(JSONObject arg0, JSONObject arg1) {
	                int hits0 = arg0.getIntValue("priority");
	                int hits1 = arg1.getIntValue("priority");
	                if (hits1 > hits0) {
	                    return 1;
	                } else if (hits1 == hits0) {
	                    return 0;
	                } else {
	                    return -1;
	                }
	            }
	        });
			SyncAdPosition syncAdPosition = Constants.AD_POSITION_MAP.get(posId);
			List<Map<String, Object>> mappingList = syncAdPosition.getMapping();
			if (syncAdPosition == null || mappingList == null || mappingList.isEmpty()) {
				return null;
			}
			
			Platform platform = null;
			for (JSONObject obj : fixedList) {
				Integer platformId = obj.getIntValue("platformId");
				int frequency = obj.getIntValue("frequency");
				if (PlatformCtrler.isFilter(appId, channel, version)){
				} else if (FrequencyCtrler.isFilter(platformId, frequency, did)) {
				} else {
					list = new ArrayList<AdResponse.Platform>();
					for (Map<String, Object> mapping : mappingList) {
						if (platformId == NvwaUtils.obj2int(mapping.get("flowConsumerId"))) {
							if (obj.getIntValue("isSmooth") == 105 && SmoothCtrler.isFilter(mapping)) { // 104：标准，105：匀速
								break;
							}
							if (frequency > 0 && StringUtils.isNotBlank(did)) {
								FrequencyCtrler.updateFrequency(platformId, did);
							}
							platform = Platform.builder().appId(NvwaUtils.obj2Empty(mapping.get("consumerAppId")))
									.platformId(obj.getString("dspId"))
									.posType(obj.getIntValue("posType"))
									.straChapter(obj.getIntValue("straChapter"))
									.straPage(obj.getIntValue("straPage"))
									.straTime(obj.getIntValue("straTime"))
									.posId(NvwaUtils.obj2Empty(mapping.get("consumerPositionId"))).build();
							list.add(platform);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("getPlatform4Fixed error. ", e);
		}
		return list;
	}
	
	@Override
	public void test() {
	}

	@Override
	public AdResponse getAdResponse(AdRequest adRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAds(AdRequest adRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}

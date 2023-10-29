package com.iwanvi.nvwa.openapi.dsp.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.MD5Utils;
import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.App;
import com.iwanvi.nvwa.openapi.dsp.service.GeneralService;
import com.iwanvi.nvwa.openapi.dsp.utils.Constants;
import com.iwanvi.nvwa.openapi.dsp.utils.NvwaUtils;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.DspType;

@Service("generalService")
public class GeneralServiceImpl implements GeneralService {

	private static final Logger LOG = LoggerFactory.getLogger("generalService");
	
	private static final String URL_MONITOR_EXPOSURE = PropertyGetter.getString("url.monitor.exposure");
	private static final String URL_MONITOR_CLICK = PropertyGetter.getString("url.monitor.click");

	private static final String URL_MONITOR_DSP_EXPOSURE = PropertyGetter.getString("url.monitor.dsp.exposure");
	private static final String URL_MONITOR_DSP_CLICK = PropertyGetter.getString("url.monitor.dsp.click");

	@Resource
	private RedisDao redisDao;

	@Override
	public Map<String, String> getMonitorUrlMap(AdRequest adRequest, AdMsg adMsg, UserInfo userInfo, PosInfo posInfo) {
		Map<String, String> result = new HashMap<>();
		String url_clk = StringUtils.EMPTY;
		String url_exp = StringUtils.EMPTY;
		int price = 0;
		try {
			if (adMsg.getDspType() != DspType.kDspTypePrivate) { // 非自有平台
				return getDspMonitorUrlMap(adRequest.getId(), adMsg, userInfo, 
						posInfo.getPosId().toStringUtf8(), adRequest.getApp());
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			int bidPrice = adMsg.getBidPrice();
			String appId = userInfo.getMediaUuid();
			CostType costType = adMsg.getCostType();
			int cid = adMsg.getCreativeId();
			String did = userInfo.getMuid().toStringUtf8();
			
			App app = adRequest.getApp();
			map.put("reqId", adRequest.getId());
			map.put("appId", appId);
			map.put("posId", posInfo.getPosId().toStringUtf8());
			map.put("channel", app.getChannel());
			map.put("version", app.getVersion());
			map.put("appExt", app.getExt());
			map.put("cid", cid);
			map.put("aderId", adMsg.getAdvertiserUuid());
			map.put("planId", adMsg.getPlanId());
			map.put("unitId", adMsg.getUnitId());
			map.put("orderId", adMsg.getOrderId());
			map.put("agentId", adMsg.getAgentUuid());
			map.put("ldp", adMsg.getLandUrl().trim());
			map.put("ts", DateUtils.format(new Date(), DateUtils.DEFAULT_FORMAT));
			map.put("os", userInfo.getOs().getNumber());
			map.put("did", did);
			map.put("ip", userInfo.getIp());

			map.put("channel2", app.getChannel2());
			
			int frequencyType = 0;
			if (adMsg.hasFrequencyCapping()) {
				if (adMsg.getFrequencyCapping().getIpCapping()) {
					frequencyType = 1;
				} else {
					frequencyType = 2;
				}
			}
			map.put("frequencyType", frequencyType); // 频次控制类型 设备id:2或者ip:1
			
			int puType = adMsg.getPutType().getNumber();
			map.put("puType", puType); // 1:订单投放, 2:rtb投放, 3:打底广告投放
			if (puType != 1) {
				map.put("agentType", adMsg.getAgentType().getNumber()); // 1:直客,2:普通
				map.put("agentCost", adMsg.getAgentCost());
				if (adMsg.hasFrequencyCapping()) {
					map.put("isLimit", adMsg.getFrequencyCapping().getFrequencyCapping());
					map.put("lperiod", adMsg.getFrequencyCapping().getFrequencyPeriod().getNumber());
				}
				map.put("adxPayDiscount", adMsg.getAdxPayDiscount());
				map.put("agentPayDiscount", adMsg.getAgentPayDiscount());
				map.put("dspId", adMsg.getDspId());
				map.put("dspType", adMsg.getDspType().getNumber()); // DSP类型 1:自有平台,2:第三方dsp平台
			}
			
			int cpc = 0;
			int cpm = 0;
			if(costType == CostType.kCpc){
				cpc = bidPrice * 1000;
			} else if (costType == CostType.kCpm || costType == CostType.kCpfm) {
				cpm = bidPrice;
			}
			String base64pCpc = getBase64Str(map, cpc);
			String base64pCpm = getBase64Str(map, cpm);
			price = NvwaUtils.obj2int(map.get("cost"));
			
			url_exp = StringUtils.buildString(URL_MONITOR_EXPOSURE, appId, cid, did, base64pCpm, 
					MD5Utils.MD5(StringUtils.concat(appId, cid, did, base64pCpm, com.iwanvi.nvwa.common.utils.Constants.KEY_ENCODE)));
			url_clk = StringUtils.buildString(URL_MONITOR_CLICK, appId, cid, did, base64pCpc, 
					MD5Utils.MD5(StringUtils.concat(appId, cid, did, base64pCpc, com.iwanvi.nvwa.common.utils.Constants.KEY_ENCODE)));
		} catch (Exception e) {
			LOG.error("getMonitorUrl error. ", e);
		} finally {
			result.put(Constants.MONITOR_TYPE_CLICK, url_clk);
			result.put(Constants.MONITOR_TYPE_EXPOSURE, url_exp);
			result.put(Constants.QUOTA_BID_PRICE, String.valueOf(price));
		}
		return result;
	}
	
	public synchronized Map<String, String> getDspMonitorUrlMap(String reqId, 
			AdMsg adMsg, UserInfo userInfo, String posId, App app) {
		Map<String, String> result = new HashMap<>();
		String url_clk = StringUtils.EMPTY;
		String url_exp = StringUtils.EMPTY;
		int price = 0;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int bidPrice = adMsg.getBidPrice();
			CostType costType = adMsg.getCostType();
			
			int cid = adMsg.getCreativeId();
			String dspId = adMsg.getDspId();
			String did = userInfo.getMuid().toStringUtf8();
			String appId = userInfo.getMediaUuid();
			map.put("reqId", reqId);
			map.put("appId", appId);
			map.put("posId", posId);
			map.put("channel", app.getChannel());
			map.put("channel2", app.getChannel2());
			map.put("version", app.getVersion());
			map.put("appExt", app.getExt());
			
			map.put("os", userInfo.getOs().getNumber());
			map.put("did", did);
			map.put("ip", userInfo.getIp());
			map.put("cid", cid);
			
			map.put("dspId", adMsg.getDspId());
			
			int cpc = 0;
			int cpm = 0;
			if(costType == CostType.kCpc){
				cpc = bidPrice * 1000;
				map.put("cost", cpc);
			} else if (costType == CostType.kCpm || costType == CostType.kCpfm) {
				cpm = bidPrice;
				map.put("cost", cpm);
			}
			String base64p = Base64.getEncoder().encodeToString(JsonUtils.TO_JSON(map).getBytes(StandardCharsets.UTF_8));
			price = NvwaUtils.obj2int(map.get("cost"));
			url_exp = StringUtils.buildString(URL_MONITOR_DSP_EXPOSURE, appId, dspId, cid, base64p, 
					MD5Utils.MD5(StringUtils.concat(appId, dspId, cid, base64p, adMsg.getPmp().getDealId(), com.iwanvi.nvwa.common.utils.Constants.KEY_ENCODE)));
			
			url_clk = StringUtils.buildString(URL_MONITOR_DSP_CLICK, appId, dspId, cid, base64p, 
					MD5Utils.MD5(StringUtils.concat(appId, dspId, cid, base64p, adMsg.getPmp().getDealId(), com.iwanvi.nvwa.common.utils.Constants.KEY_ENCODE)));
		} catch (Exception e) {
			LOG.error("getDspMonitorUrlMap error. ", e);
		} finally {
			result.put(Constants.MONITOR_TYPE_CLICK, url_clk);
			result.put(Constants.MONITOR_TYPE_EXPOSURE, url_exp);
			result.put(Constants.QUOTA_BID_PRICE, String.valueOf(price));
		}
		return result;
	}
	
	private String getBase64Str(Map<String, Object> map, int cost) {
		String base64p = StringUtils.EMPTY;
		try {
			map.put("cost", cost);
			base64p = Base64.getEncoder().encodeToString(JsonUtils.TO_JSON(map).getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			LOG.error("getBase64Str error. ", e);
		}
		return base64p;
	}
}

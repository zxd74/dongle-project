package com.iwanvi.nvwa.openapi.dsp.controller;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.HttpClientUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.common.utils.UUIDUtils;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.QuotaEntityMapper;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.openapi.dsp.filter.ForbiddenDate;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.App;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.Device;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.Imp;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse.Platform;
import com.iwanvi.nvwa.openapi.dsp.service.DspTask;
import com.iwanvi.nvwa.openapi.dsp.service.RpcOperator;
import com.iwanvi.nvwa.openapi.dsp.service.TerminalService;
import com.iwanvi.nvwa.openapi.dsp.utils.BrokerUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.LogUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.NvwaUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.ReqJsonTest;

import ai.houyi.dorado.rest.annotation.Controller;
import ai.houyi.dorado.rest.annotation.POST;
import ai.houyi.dorado.rest.annotation.Path;
import ai.houyi.dorado.rest.annotation.Produce;
import ai.houyi.dorado.rest.http.HttpRequest;
import ai.houyi.dorado.rest.http.HttpResponse;
import ai.houyi.dorado.rest.http.impl.ChannelHolder;
import ai.houyi.dorado.rest.util.NetUtils;
import io.netty.handler.codec.http.HttpResponseStatus;

@Controller
@Path("/api")
public class ApiController {

	private static final Logger LOG = LoggerFactory.getLogger("ApiController");

	private static final String JSON_TEST = PropertyGetter.getString("json.test"); // 模拟拉取广告的请求示例

	@Resource
	private TerminalService terminalService;

	@Resource
	private TerminalService confService;

	@Resource
	private QuotaEntityMapper quotaEntityMapper;

	@Resource
	private EntityMapper entityMapper;

	@Resource
	private DspTask dspTask;

	@Resource
	private RedisDao redisDao;

	@POST
	@Path("/ad")
	public String getAd(HttpRequest request, HttpResponse response) {
		String ad = null;
		try {
			String param = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8.name());
			AdRequest adRequest = JsonUtils.TO_OBJ(param, AdRequest.class);

			adRequest.getApp().setChannel(com.iwanvi.nvwa.common.utils.Constants.CHANNEL);

			LogUtils.getInstance().printReqLog(adRequest);

			AdResponse adResponse = terminalService.getAdResponse(adRequest);
			if (adResponse == null) {
				LOG.info("AdResponse is null.");
				response.setStatus(HttpResponseStatus.NO_CONTENT.code());
				return JsonUtils.DATA_NOT_FIND();
			} else {
				ad = JsonUtils.STATUS_OK(adResponse);
			}
		} catch (Exception e) {
			response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
			ad = JsonUtils.EXCEPTION_ERROR();
			LOG.error("getAd error. ", e);
		}
		return ad;
	}

	@Path("/test")
	public String getTestAd(String reqUrl, String appId, String posId, String ip, String did, int os,
			int connectionType, int deviceType, String callback) {
		String result = null;
		try {
			JSONObject jsonObj = JSONObject.parseObject(JSON_TEST);

			jsonObj.getJSONObject("device").put("ip", NvwaUtils.obj2Empty(ip));
			jsonObj.getJSONObject("device").put("did", NvwaUtils.obj2Empty(did));
			jsonObj.getJSONObject("device").put("os", NvwaUtils.obj2int(os));
			jsonObj.getJSONObject("device").put("connectionType", NvwaUtils.obj2int(connectionType));
			jsonObj.getJSONObject("device").put("deviceType", NvwaUtils.obj2int(deviceType));
			jsonObj.getJSONObject("app").put("appId", NvwaUtils.obj2Empty(appId));
			jsonObj.getJSONArray("imps").getJSONObject(0).put("posId", NvwaUtils.obj2Empty(posId));
			String param = jsonObj.toJSONString();

			LOG.info("getTestAd param-----" + param);

			result = com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils
					.STATUS_OK(ReqJsonTest.sendPostRequestBody(reqUrl, param), callback);
		} catch (Exception e) {
			LOG.error("getTestAd error. ", e);
			result = com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.EXCEPTION_ERROR(callback);
		}
		return result;
	}

	// 引擎数据
	@Path("/engine")
	public String getEngine(Integer type, Integer id, String callback) {
		String result = null;
		try {
			if (id == null || type == null) {
				return com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.PARAMETER_ERROR(callback);
			}
			int unitId = id;
			if (type == 1) { // 创意ID
				Entity entity = entityMapper.selectByPrimaryKey(id);
				if (entity != null) {
					unitId = entity.getPid();
				}
			}
			String rpcResult = RpcOperator.getInstance().getEngineData(unitId);
			if (StringUtils.isBlank(rpcResult)) {
				result = com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.DATA_NOT_FIND(callback);
			} else {
				result = com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.STATUS_OK(rpcResult, callback);
			}
		} catch (Exception e) {
			LOG.error("getEngine error. ", e);
			result = com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.EXCEPTION_ERROR(callback);
		}
		return result;
	}

	// util
	@Path("/util")
	public String util(String url, String callback) {
		String result = null;
		try {
			if (StringUtils.isBlank(url)) {
				return com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.PARAMETER_ERROR(callback);
			}
			String httpResult = HttpClientUtils.get(URLDecoder.decode(url, StandardCharsets.UTF_8.name()));
			if (StringUtils.isBlank(httpResult)) {
				result = com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.DATA_NOT_FIND(callback);
			} else {
				result = com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.STATUS_OK(httpResult, callback);
			}
		} catch (Exception e) {
			LOG.error("util error. ", e);
			result = com.iwanvi.nvwa.openapi.dsp.utils.JsonUtils.EXCEPTION_ERROR(callback);
		}
		return result;
	}

	@Path("/sdk/ad")
	// 临时兼容原始接口
	@Produce("application/json;charset=UTF-8")
	public String getAds(HttpRequest request, String appId, String pid, String ext, String ip, int os) {
		String result = null;
		try {
			boolean debugOn = false;
			boolean isDefault = NumberUtils.toInt(request.getParameter("isDefault")) == 1; // 是否打底广告
			LOG.info("getAds params: appId: {}, pid: {}, ext: {}, ip: {}, os: {}", appId, pid, ext, ip, os);

			if (StringUtils.isBlank(pid) || StringUtils.isBlank(appId)) {
				LOG.warn("getAds params illegal.");
				return BrokerUtils.PARAMETER_ERROR();
			}

			Map<String, Object> extMap = null;
			if (StringUtils.isNotBlank(ext)) {
				extMap = JsonUtils.parse2Map(ext);
			}

			AdRequest adRequest = new AdRequest();
			App app = new App();
			app.setAppId(appId);

			Device device = new Device();
			device.setOs(2);
			String version = StringUtils.EMPTY;
			if (extMap != null) {
				ip = extMap.get("ip") == null ? ip : NvwaUtils.obj2Empty(extMap.get("ip"));
				if (org.apache.commons.lang3.StringUtils.isBlank(ip)) {
					ip = getRemoteAddr(request);
				}
				device.setIp(ip);
				device.setUa(NvwaUtils.obj2Empty(extMap.get("ua")));

				device.setDid(NvwaUtils.obj2Empty(extMap.get("did")));
				device.setCh(NvwaUtils.obj2int(extMap.get("ch")));
				device.setCw(NvwaUtils.obj2int(extMap.get("cw")));
				device.setOsv(NvwaUtils.obj2Empty(extMap.get("osv")));
				device.setDeviceType(NvwaUtils.obj2int(extMap.get("dt"), 1));
				device.setConnectionType(NvwaUtils.obj2int(extMap.get("conn"), 0));
				device.setCarrier(NvwaUtils.obj2int(extMap.get("carrier"), 0));
				device.setMake(NvwaUtils.obj2Empty(extMap.get("mf")));
				device.setModel(NvwaUtils.obj2Empty(extMap.get("device")));
				device.setAdid(NvwaUtils.obj2Empty(extMap.get("adid")));
				device.setDensity(NvwaUtils.obj2int(extMap.get("density")));
				device.setLat(NumberUtils.toDouble(NvwaUtils.obj2Empty(extMap.get("lat"))));
				device.setLon(NumberUtils.toDouble(NvwaUtils.obj2Empty(extMap.get("lng"))));
				device.setMac(NvwaUtils.obj2Empty(extMap.get("mac")));

				app.setChannel2(NvwaUtils.obj2Empty(extMap.get("channel2")));

				version = NvwaUtils.obj2Empty(extMap.get("appv"));
				app.setVersion(version);
				app.setName(NvwaUtils.obj2Empty(extMap.get("appn")));
				app.setBundle(NvwaUtils.obj2Empty(extMap.get("appid")));
				app.setExt("");
				app.setOrigChannel(NvwaUtils.obj2Empty(extMap.get("channel")));
			}
			adRequest.setDevice(device);

			app.setChannel(com.iwanvi.nvwa.common.utils.Constants.CHANNEL);
			adRequest.setApp(app);
			adRequest.setDebugOn(debugOn);
			adRequest.setDefault(isDefault);

			adRequest.setId(UUIDUtils.getUUID());
			Imp imp = new Imp();
			imp.setPosId(pid);
			imp.setTmids(NvwaUtils.obj2Empty(extMap.get("tmids")));
			adRequest.setImps(Arrays.asList(imp));

			LogUtils.getInstance().printReqLog(adRequest);

			if (ForbiddenDate.isFilter()) { // 禁投日期过滤
				LOG.warn("========ForbiddenDate filter=======");
				return BrokerUtils.DATA_NOT_FIND();
			}
			result = result == null ? terminalService.getAds(adRequest) : result;
		} catch (Exception e) {
			LOG.error("getAds error. ext: {}, getRemoteAddr: {}", ext, request.getRemoteAddr(), e);
			result = BrokerUtils.EXCEPTION_ERROR();
		}
		return result;
	}

	/**
	 * @param type 1:包段，2:包量
	 * @return
	 */
	@Path("/platform")
	@Produce("application/json;charset=UTF-8")
	public String getPlatform(String appId, String posId, String channel, String version, String did, int type) {
		String result = null;
		try {
			if (StringUtils.isBlank(appId) || StringUtils.isBlank(posId) || type > 2 || type < 1) {
				return BrokerUtils.PARAMETER_ERROR();
			}
			channel = com.iwanvi.nvwa.common.utils.Constants.CHANNEL;
			List<Platform> platforms = null;
			switch (type) {
			case 1:
				platforms = confService.getPlatform4period(appId, posId, channel, version, did);
				break;
			case 2:
				platforms = confService.getPlatform4Fixed(appId, posId, channel, version, did);
				break;
			default:
				break;
			}
			if (CollectionUtils.isEmpty(platforms)) {
				return BrokerUtils.DATA_NOT_FIND();
			}
			result = BrokerUtils.STATUS_OK(platforms);
		} catch (Exception e) {
			result = BrokerUtils.EXCEPTION_ERROR();
			LOG.error("getPlatform error. ", e);
		}
		return result;
	}

	/**
	 * sdk配置信息
	 * 
	 * @param appId uuid
	 * @return
	 */
	@Path("/conf")
	@Produce("application/json;charset=UTF-8")
	public String conf(String appId) {
		String result = null;
		try {
			result = confService.getConf(appId);
			result = result == null ? JsonUtils.DATA_NOT_FIND() : result;
		} catch (Exception e) {
			LOG.error("conf error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}

	private String getRemoteAddr(HttpRequest request) {
		try {
			InetSocketAddress addr = (InetSocketAddress) ChannelHolder.get().remoteAddress();
			String fallbackAddr = addr.getAddress().getHostAddress();

			String xForwardFor = request.getHeader("X-Forwarded-For");
			if (xForwardFor == null || StringUtils.isBlank(xForwardFor)) {
				return fallbackAddr;
			}

			String[] proxyIpList = xForwardFor.split(",");
			for (int i = proxyIpList.length - 1; i >= 0; i--) {
				String proxyIp = proxyIpList[i];
				if (!StringUtils.isBlank(proxyIp))
					proxyIp = proxyIp.trim();
				if (!NetUtils.isInternalIp(proxyIp)) {
					return proxyIp;
				}
			}
			return fallbackAddr;
		} catch (Exception ex) {
			LOG.error("获取ip地址异常", ex);
		}
		return StringUtils.EMPTY;
	}
}

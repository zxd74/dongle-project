package com.iwanvi.nvwa.openapi.dsp.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.protobuf.ByteString;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.common.utils.UUIDUtils;
import com.iwanvi.nvwa.openapi.dsp.filter.ForbiddenDate;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.App;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.Device;
import com.iwanvi.nvwa.openapi.dsp.model.AdRequest.Imp;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse.Ad;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse.Ad.Feed;
import com.iwanvi.nvwa.openapi.dsp.model.AdResponse.Platform;
import com.iwanvi.nvwa.openapi.dsp.model.SyncAdPosition;
import com.iwanvi.nvwa.openapi.dsp.service.GeneralService;
import com.iwanvi.nvwa.openapi.dsp.service.RpcOperator;
import com.iwanvi.nvwa.openapi.dsp.service.TerminalService;
import com.iwanvi.nvwa.openapi.dsp.utils.BrokerUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.Constants;
import com.iwanvi.nvwa.openapi.dsp.utils.IPUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.IPv4Util;
import com.iwanvi.nvwa.openapi.dsp.utils.LogUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.NvwaUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.NativeAd;
import com.iwanvi.nvwa.proto.BiddingProto.AdMsg;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo.Builder;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.Carrier;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.DeviceType;
import com.iwanvi.nvwa.proto.CommonProto.ExtensionType;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;
import com.iwanvi.nvwa.proto.CommonProto.OSType;
import com.iwanvi.nvwa.proto.CommonProto.PutType;
import com.iwanvi.nvwa.proto.CommonProto.TerminalType;

@Service("terminalService")
public class TerminalServiceImpl implements TerminalService {

	private static final Logger LOG = LoggerFactory.getLogger("TerminalService");
	
	@Resource
	private GeneralService generalService;
	
	@Resource
	private TerminalService confService;
	
	@Resource
	private RedisDao redisDao;
	
	@Override
	public AdResponse getAdResponse(AdRequest adRequest) {
		AdResponse adResponse = null;
		try {
			if (ForbiddenDate.isFilter()) { // 禁投日期过滤
				return null;
			}
			String reqId = adRequest.getId();
			String appId = adRequest.getApp().getAppId();
			UserInfo userInfo = getUserInfo(adRequest.getDevice(), appId);
			List<PosInfo> posInfos = new ArrayList<>();
			List<AdMsg> adMsgs = null;
			PosInfo posInfo = null;
			for (Imp imp : adRequest.getImps()) {
				posInfo = getPosInfo(imp, adRequest);
				if (posInfo != null) {
					posInfos.add(posInfo);
					// sdk目前处理逻辑为单个
					break;
				}
			}
			
			if (posInfos.isEmpty()) {
				return null;
			}
			
			adMsgs = RpcOperator.getInstance().getAdMsgs(reqId, posInfos, userInfo);
			if(adMsgs == null || adMsgs.isEmpty()) {
				return null;
			}
			
			List<Ad> ads = new ArrayList<>();
			AdMsg adMsg = null;
			Ad ad = null;
			for (int j = 0; j < adMsgs.size(); j++) {
				adMsg = adMsgs.get(j);
				ad = buildAd(adRequest, userInfo, posInfo, adMsg);
				if(ad == null) {
					continue;
				}
				ads.add(ad);
			}
			
			adResponse = AdResponse.builder()
					.id(reqId)
					.ads(ads)
					.build();
		} catch (Exception e) {
			LOG.error("getAdResponse error. ", e);
		}
		return adResponse;
	}
	
	@SuppressWarnings("static-access")
	private UserInfo getUserInfo(Device device, String appId){
		UserInfo userInfo = null;
		try {
			// 0:未知,1:PHONE,2:PAD,3:PC,4:TV
			int terminalType = 0;
			switch (device.getDeviceType()) {
			case 1:
				terminalType = 1;
				break;
			case 2:
				terminalType = 1;
				break;
			case 3:
				terminalType = 2;
				break;
			case 4:
				terminalType = 1;
				break;
			default:
				break;
			}
			String ip = NvwaUtils.obj2Empty(device.getIp());
			userInfo = userInfo.newBuilder()
					.setMuid(ByteString.copyFromUtf8(device.getDid()))
					.setDeviceBrand(ByteString.copyFromUtf8(NvwaUtils.obj2Empty(device.getMake())))
					.setDeviceModel(ByteString.copyFromUtf8(NvwaUtils.obj2Empty(device.getModel())))
					.setConnectType(ConnectType.forNumber(device.getConnectionType()))
					.setCarrier(Carrier.forNumber(device.getCarrier()))
					.setOs(OSType.forNumber(device.getOs()))
					.setOsv(device.getOsv())
					.setDeviceType(DeviceType.forNumber(device.getDeviceType()))
					.setClientIp(IPv4Util.ipToInt(ip))
					.setIp(ip)
					.setUa(NvwaUtils.obj2Empty(device.getUa()))
					.setAreaCode(IPUtils.getInstance().getAreaCode(ip))
					.setTerminalType(TerminalType.forNumber(terminalType))
					//.setTrafficType(TrafficType.kTrafficTypeUnKnown)
					.setMediaUuid(appId).setScreenWidth(device.getCw()).setScreenHeight(device.getCh())
					.setAdid(NvwaUtils.obj2Empty(device.getAdid()))
					.setDensity(device.getDensity())
					.setLat(device.getLat()).setLon(device.getLon())
					.setMac(device.getMac())
					.build();
		} catch (Exception e) {
			LOG.error("getUserInfo error. ", e);
		}
		return userInfo;
	}
	
	@SuppressWarnings({ "static-access" })
	private PosInfo getPosInfo(Imp imp, AdRequest adRequest){
		PosInfo posInfo = null;
		try {
			String posId = imp.getPosId();
			Constants.AD_POSITION_UUID_SET.add(posId);
			SyncAdPosition adPosition = Constants.AD_POSITION_MAP.get(posId);
			if(adPosition == null) {
				String adPositionKey = StringUtils.buildString(Constants.KEY_REDIS_AD_POSITION, posId);
				String position = redisDao.get(adPositionKey);
				adPosition = JsonUtils.TO_OBJ(position, SyncAdPosition.class);
				Constants.AD_POSITION_MAP.put(posId, adPosition);
			}
			if(adPosition == null) {
				LOG.warn("getPosInfo adPosition is null. posId: {}", posId);
				return posInfo;
			}
			LOG.info("getPosInfo. posId: {}, adPosition: {}", posId, JsonUtils.TO_JSON(adPosition));
			int creativeType = adPosition.getCreativeType();
			if(creativeType == -1){
				LOG.warn("getPosInfo exception. creativeType: {}", creativeType);
				return posInfo;
			}
			if(creativeType == 3 || creativeType == 4 || creativeType == 6){
				creativeType = 2;
			}
			App app = adRequest.getApp();
			Builder builder = posInfo.newBuilder().setAppId(ByteString.copyFromUtf8(app.getAppId()))
					.setId(UUIDUtils.getUUID())
					.setPosId(ByteString.copyFromUtf8(posId))
					.setWidth(adPosition.getWidth())
					.setHeight(adPosition.getHeight())
					.addCreativeType(CreativeType.forNumber(creativeType))
					.setAdType(CreativeType.forNumber(creativeType))
					.setDuration(NvwaUtils.obj2int(adPosition.getDuration()))
					.setBundle(NvwaUtils.obj2Empty(app == null ? StringUtils.EMPTY : app.getBundle()))
					.setAppVersion(NvwaUtils.obj2Empty(app == null ? StringUtils.EMPTY : app.getVersion()))
					.setAppName(NvwaUtils.obj2Empty(app == null ? StringUtils.EMPTY : app.getName()))
					.setAdNum(1)
					//.addAllTmid(Arrays.asList(adRequest.getImps().get(0).getTmids().split(com.iwanvi.nvwa.common.utils.Constants.SIGN_COMMA)))
					.addExt(MapEntry.newBuilder().setKey("dx_app").setValue(app.getAppId()))
					.addExt(MapEntry.newBuilder().setKey("dx_channel").setValue(app.getOrigChannel()));
			
			if(adPosition.getCreativeStyle()!=null) {
				builder.setToutiaoCreativeType(adPosition.getCreativeStyle());
			}
			String tmids = imp.getTmids();
			if(org.apache.commons.lang3.StringUtils.isNotBlank(tmids)) {
				builder.addAllTmid(Arrays.asList(org.apache.commons.lang3.StringUtils.split(tmids, com.iwanvi.nvwa.common.utils.Constants.SIGN_COMMA)));
			}
			if(creativeType == 7){ // native
				builder.setAdTypeId(posId);
			}
			
			if (adRequest.isDefault()) { // 打底广告
				builder.addExt(MapEntry.newBuilder().setKey("putType")
						.setValue(String.valueOf(PutType.kPutTypeDefault_VALUE)));
			}
			
//			buildPosInf(builder, adRequest.getApp().getExt());
			buildPosInf(builder, imp.getExt());
			posInfo = builder.build();
		} catch (Exception e) {
			LOG.error("getPosInfo error. ", e);
		}
		return posInfo;
	}
	
	private void buildPosInf(Builder builder, Object ext) {
		try {
			if (ext == null) {
				return;
			}
			Map<?, ?> extMap = new BeanMap(ext);
			String key = null;
			for (Map.Entry<?, ?> entry : extMap.entrySet()) {
				if (entry.getValue() == null) {
					continue;
				}
				key = String.valueOf(entry.getKey());
				if(key.equals("class")) {
					continue;
				}
				builder.addExt(MapEntry.newBuilder()
						.setKey(key)
						.setValue(String.valueOf(entry.getValue())));
			}
		} catch (Exception e) {
			LOG.error("buildPosInf error. ", e);
		}
	}
	
	private Ad buildAd(AdRequest adRequest, UserInfo userInfo, PosInfo posInfo, AdMsg adMsg){
		int cid = 0, price = 0;
		long l1 = System.currentTimeMillis();
		Ad ad = null;
		try {
			com.iwanvi.nvwa.openapi.dsp.model.AdResponse.Ad.Builder builder = Ad.builder();
			
			List<String> expList = new ArrayList<>();
			List<String> clkList = new ArrayList<>();
			List<String> dlmList = new ArrayList<>(); //下载监测
			List<String> dpsList = new ArrayList<>(); //deeplink成功监测
			List<String> dpfList = new ArrayList<>(); //deeplink失败监测
			
			List<String> adMsgImpMontorUrlList = adMsg.getImpMonitorUrlList();
			if(adMsgImpMontorUrlList != null && !adMsgImpMontorUrlList.isEmpty()){
				expList.addAll(adMsgImpMontorUrlList);
			}
			List<String> adMsgClkMontorUrlList = adMsg.getClkMonitorUrlList();
			if(adMsgClkMontorUrlList != null && !adMsgClkMontorUrlList.isEmpty()){
				clkList.addAll(adMsgClkMontorUrlList);
			}
			Map<String, String> monitorUrlMap = generalService.getMonitorUrlMap(adRequest, adMsg, userInfo, posInfo);
			
			price = NvwaUtils.obj2int(monitorUrlMap.get(Constants.QUOTA_BID_PRICE));
			
			expList.add(monitorUrlMap.get(com.iwanvi.nvwa.openapi.dsp.utils.Constants.MONITOR_TYPE_EXPOSURE));
			clkList.add(monitorUrlMap.get(com.iwanvi.nvwa.openapi.dsp.utils.Constants.MONITOR_TYPE_CLICK));
			
			builder.pm(expList);
			builder.cm(clkList);
			builder.dlm(adMsg.getDlMonitorUrlList());
			builder.dpsm(adMsg.getDpsMonitorUrlList());
			builder.dpfm(adMsg.getDpfMonitorUrlList());
			
			if(posInfo.getCreativeTypeList().contains(CreativeType.kNative)){
				NativeAd nativeAd = adMsg.getNativeAd();
				
				List<String> imgs = new ArrayList<>();
				if(StringUtils.isNotBlank(nativeAd.getThreadPic1())) {
					imgs.add(nativeAd.getThreadPic1());
				}
				if(StringUtils.isNotBlank(nativeAd.getThreadPic2())) {
					imgs.add(nativeAd.getThreadPic2());
				}
				if(StringUtils.isNotBlank(nativeAd.getThreadPic3())) {
					imgs.add(nativeAd.getThreadPic3());
				}
				
				Feed feed = Feed.builder()
						.name(nativeAd.getUserName())
						.title(nativeAd.getThreadTitle())
						.portrait(nativeAd.getUserPortrait())
						.button(nativeAd.getButtonText())
						.desc(nativeAd.getThreadContent())
						.imgs(imgs)
						.tmid(nativeAd.getTemplateId())
						.build();
				
				builder.Feed(feed);
			} else if(posInfo.getCreativeTypeList().contains(CreativeType.kVideo)) {
				builder.video(adMsg.getPicUrl());
				builder.img(adMsg.getNativeAd().getThreadPic1());
			} else {
				builder.img(adMsg.getPicUrl());
			}
			
			cid = adMsg.getCreativeId();
			builder.cid(cid).ldp(adMsg.getLandUrl()).fallback(adMsg.getFallback())
				.posId(posInfo.getPosId().toStringUtf8()).source(adMsg.getSource());
			ad = builder.build();
		} catch (Exception e) {
			LOG.error("buildAd error. ", e);
		}
		
		LogUtils.getInstance().printBidLog(adRequest, posInfo.getPosId().toStringUtf8(), adMsg.getDspId(),
				Constants.OS_MAP.get(userInfo.getOs().getNumber()), cid, price, (System.currentTimeMillis() - l1));
		
		return ad;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAds(AdRequest adRequest) {
		String result = StringUtils.EMPTY;
		try {
			Imp imp = adRequest.getImps().get(0);
			String pid = imp.getPosId();
			
			UserInfo userInfo = getUserInfo(adRequest.getDevice(), adRequest.getApp().getAppId());
			PosInfo posInfo = getPosInfo(imp, adRequest);
			
			if (userInfo == null || posInfo == null) {
				return BrokerUtils.DATA_NOT_FIND();
			}
			List<PosInfo> posInfos = new ArrayList<>();
			posInfos.add(posInfo);
			List<AdMsg> adMsgs = RpcOperator.getInstance().getAdMsgs(adRequest, posInfos, userInfo);
			if(CollectionUtils.isEmpty(adMsgs)){
				LOG.warn("getAds adMsgs is null. ");
				return BrokerUtils.DATA_NOT_FIND();
			}
			AdMsg adMsg = adMsgs.get(0);
			Map<String, Object> posMap = new HashMap<String, Object>();
			Map<String, Object> pidMap = new HashMap<String, Object>();
			Ad ad = buildAd(adRequest, userInfo, posInfo, adMsg);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if(ad != null){
				Map<?, ?> map = new org.apache.commons.beanutils.BeanMap(ad);
				Map<String, Object> temp = new HashMap<String, Object>();
				temp.putAll((Map<String, Object>)map);
				temp.put("exp", map.get("pm"));
				temp.put("clk", map.get("cm"));
				temp.remove("pm");
				temp.remove("cm");
				temp.remove("class");
				
				if(adMsg.getExtensionType() == ExtensionType.kExtAndroid){
					Map<String, Object> appInfo = new HashMap<String, Object>();
					appInfo.put("size", 0);
					appInfo.put("pkgName", adMsg.getPkgName());
					appInfo.put("appName", adMsg.getAppName());
					temp.put("appInfo", appInfo);
				}
				
				list.add(temp);
			}

			if(list.isEmpty()){
				return BrokerUtils.DATA_NOT_FIND();
			}
			
			pidMap.put("ret", String.valueOf(0));
			pidMap.put("msg", StringUtils.EMPTY);
			pidMap.put("list", list);
			
			posMap.put(pid, pidMap);
			result = BrokerUtils.STATUS_OK(posMap);
		} catch (Exception e) {
			LOG.error("getAds error. ", e);
			return BrokerUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@Override
	public List<Platform> getPlatform4period(String appId, 
			String posId, String channel, String version, String did) {
		return null;
	}
	
	public List<Platform> getPlatform4Fixed(String appId, 
			String posId, String channel, String version, String did) {
		return null;
	}
	
	@Override
	public void test() {
	}
	
	@Override
	public String getConf(String appId) {
		return null;
	}
}

package com.iwanvi.nvwa.web.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.AdPositionExample.Criteria;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.ShortUrlUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
//import com.iwanvi.nvwa.dao.FlowConMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.FlowControlMapper;
import com.iwanvi.nvwa.dao.SdkAdCarouselMapper;
import com.iwanvi.nvwa.dao.WarningSettingMapper;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdDicService;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.AppService;
import com.iwanvi.nvwa.web.service.FlowControlServicce;
import com.iwanvi.nvwa.web.service.FlowSourceService;
import com.iwanvi.nvwa.web.service.WarningSettingService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.FluentIterable;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private AppsMapper appsMapper;

	@Autowired
	private AdDicService adDicService;

	@Autowired
	private FlowSourceService flowSourceService;

	@Autowired
	private AdPositionMapper adPositionMapper;

	@Autowired
	private SdkAdCarouselMapper sdkAdCarouselMapper;
	
	@Autowired
	private FlowControlMapper flowControlMapper;
	
	@Autowired
	private FlowConsumerMapper flowConsumerMapper;
	
	@Autowired
	private FlowControlServicce flowControlService;
	
	@Autowired
	private WarningSettingService warningSettingService;
	
	@Autowired
	private WarningSettingMapper warningSettingMapper;

	@Autowired
	private AdPositionService adPositionService;
	
	/*
	 * @Autowired private FlowConMapper flowConMapper;
	 */
	
	@Override
	public Page<Apps> getAppList(Apps apps, Integer currentPage, Integer pageSize) {
		// stau 运行状态 appst 删除状态
		Page<Apps> page = null;
		AppsExample example = buildExample(apps);
		int count = (int) appsMapper.countByExample(example);
		if (currentPage != null && pageSize != null) {
			page = new Page<Apps>(count, currentPage, pageSize);
		} else {
			page = new Page<Apps>(count);
		}
		example.setStart(page.getStartPosition());
		example.setLimit(page.getPageSize());
		List<Apps> list = appsMapper.selectByExample(example);
		fillApp(list);
		page.bindData(list);
		return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Apps getAppById(Integer id) {
		if (id != null) {
			Apps app = appsMapper.selectByPrimaryKey(id);
			String medias = app.getMedias();
			FlowSource flowSource = flowSourceService.getFlowSourceByUUID(medias);
			app.setMediaName(flowSource.getMediaName());
			app.setJoinType(flowSource.getJoinType());
			String consumerMapped = app.getConsumerMapped();
			if (StringUtils.isNotBlank(consumerMapped)) {
				Map<Integer, String> map = JsonUtils.TO_OBJ(consumerMapped, Map.class);
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				map.entrySet().forEach(entry -> {
					Map<String, String> newMap = new HashMap<String, String>();
					newMap.put(String.valueOf(entry.getKey()), entry.getValue());
					list.add(newMap);
				});
				app.setEchoMapped(list);
			}
			return app;
		}
		return null;
	}

	/**
	 * build条件
	 * 
	 * @param apps
	 * @return
	 */
	private AppsExample buildExample(Apps apps) {
		AppsExample example = new AppsExample();
		if (apps != null) {
			AppsExample.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(apps.getAppName())) {
				criteria.andAppNameLike("%" + apps.getAppName() + "%");
			}
			if (StringUtils.isNotBlank(apps.getAppId())) {
				criteria.andAppIdLike("%" + apps.getAppId() + "%");
			}
			if (apps.getType() != null) {
				criteria.andTypeEqualTo(apps.getType());
			}
			if (apps.getPlatform() != null) {
				criteria.andPlatformEqualTo(apps.getPlatform());
			}
			if (StringUtils.isNotBlank(apps.getMedias())) {
				criteria.andMediasEqualTo(apps.getMedias());
			}
			if (apps.getStatus() != null) {
				criteria.andStatusEqualTo(apps.getStatus());
			}
			criteria.andAppStatusEqualTo(Constants.STATE_VALID);
			FlowSource fs = new FlowSource();
			fs.setMediaState(Constants.STATE_VALID);
			fs.setCreateUser(apps.getCreateUser());
			List<FlowSource> fsList = flowSourceService.getFSList(fs);
			if (!CollectionUtils.isEmpty(fsList)) {
				List<String> mediaUuids = FluentIterable.from(fsList).transform((FlowSource flowSource) -> {
					return flowSource.getMediaUuid();
				}).toList();
				criteria.andMediasIn(mediaUuids);
			}
			example.setOrderByClause("status desc,id desc");
		}
		return example;
	}

	/**
	 * 替换app/平台/媒体name
	 * 
	 * @param list
	 */
	private void fillApp(List<Apps> list) {
		if (list != null) {
			for (Apps app : list) {
				app.setTypeName(adDicService.getDic(app.getType()).getDicValue());
				app.setPlatformName(adDicService.getDic(app.getPlatform()).getDicValue());
				app.setMediaName(flowSourceService.getFlowSourceByUUID(app.getMedias()).getMediaName());
				AdPositionExample adPositionExample = new AdPositionExample();
				adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppIdEqualTo(app.getId());
				List<AdPosition> adps = adPositionMapper.selectByExample(adPositionExample);
				if (CollectionUtils.isEmpty(adps)) {
					app.setAdPosNum(Constants.INTEGER_0);
				} else {
					app.setAdPosNum(adps.size());
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public void insert(Apps app) {
		if (checkAppName(app)) {
			throw new ServiceException("系统中已存在同名APP.");
		}
		String adPosIds = app.getAdPosIds();
		Set<String> adpos = new HashSet<>();
		if (StringUtils.isNotBlank(adPosIds)) {
			String[] split = adPosIds.split(",");
			for (int i = 0; i < split.length; i++) {
				String adp = split[i];
				if (NumberUtils.isNumber(adp)) {
					adpos.add(adp);
				}
			}
			String adpo = org.apache.commons.lang3.StringUtils.join(adpos, ",");
			app.setAdPosIds(adpo);
		}
		if (!CollectionUtils.isEmpty(app.getAppMapped())) {
			Map<Integer, String> map = new HashMap<Integer, String>();
			List<Map<Integer, String>> appMappeds = app.getAppMapped();
			appMappeds.forEach(m -> {
				m.entrySet().forEach(entry -> {
					map.put(entry.getKey(), entry.getValue());
				});
			});
			app.setConsumerMapped(JsonUtils.TO_JSON(map));
		}
		app.setCreateTime(new Date());
		app.setAppStatus(Constants.STATE_VALID);
		app.setAppId(ShortUrlUtils.getByUUID());

		appsMapper.insert(app);
	}

	/**
	 * 不同媒体/操作系统下appName可以相同
	 * 
	 * @param app
	 * @return
	 */
	private boolean checkAppName(Apps app) {
		boolean isExists = false;
		AppsExample example = new AppsExample();
		example.createCriteria().andAppNameEqualTo(app.getAppName()).andPlatformEqualTo(app.getPlatform())
				.andMediasEqualTo(app.getMedias()).andAppStatusEqualTo(Constants.STATE_VALID);
		List<Apps> list = appsMapper.selectByExample(example);
		if (list != null) {
			if (!(list.size() == 1 && list.get(0).getId().equals(app.getId())) && list.size() != 0) {
				isExists = true;
			}
		}
		return isExists;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Transactional
	@Override
	public void update(Apps app) {
		Apps apps = appsMapper.selectByPrimaryKey(app.getId());
		if (checkAppName(app)) {
			if (!apps.getAppName().equals(app.getAppName())) {
				throw new ServiceException("系统中已存在同名APP.");
			}
		}
		String adPosIds = app.getAdPosIds();
		Set<String> adpos = new HashSet<>();
		if (StringUtils.isNotBlank(adPosIds)) {
			String[] split = adPosIds.split(",");
			for (int i = 0; i < split.length; i++) {
				String adp = split[i];
				if (NumberUtils.isNumber(adp)) {
					adpos.add(adp);
				}
			}
			String adpo = org.apache.commons.lang3.StringUtils.join(adpos, ",");
			app.setAdPosIds(adpo);
		}
		if (!CollectionUtils.isEmpty(app.getAppMapped())) {
			Map<Integer, String> map = new HashMap<Integer, String>();
			List<Map<Integer, String>> appMappeds = app.getAppMapped();
			appMappeds.forEach(m -> {
				m.entrySet().forEach(entry -> {
					if (StringUtils.isNotBlank(app.getConsumerMapped())) {
						Map<Integer, String> map_test = JsonUtils.TO_OBJ(apps.getConsumerMapped(), Map.class);
						map_test.put(entry.getKey(), entry.getValue());
					} else {
						map.put(entry.getKey(), entry.getValue());
					}
				});
			});
			app.setConsumerMapped(JsonUtils.TO_JSON(map));
		}
		appsMapper.updateByPrimaryKeySelective(app);
		
		doAdpositionService(app.getId());
	}



	@Transactional
	@Override
	public void deleteApp(Apps app) {
		if (app != null && app.getId() != null) {
			app.setAppStatus(Constants.STATE_INVALID);
			app.setUpdateUser(app.getCreateUser());
			app.setUpdateTime(new Date());
			appsMapper.updateByPrimaryKeySelective(app);
		}
		//updateFlowControlStatus(app.getId()); //删除后将流量控制状态关闭
		updateWarnSettingStatus(app.getId()); //删除后预警设置状态关闭
		doAdpositionService(app.getId());
	}


	private void updateFlowControlStatus(Integer id) {
		FlowControlExample controlExample = new FlowControlExample();
		controlExample.createCriteria().andAppIdEqualTo(id);
		List<FlowControl> list = flowControlMapper.selectByExample(controlExample);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		list.forEach(action -> {
			flowControlService.getDel(action.getId());
		});
		
	}

	@Transactional
	@Override
	public void deleteByExample(Apps apps) {
		apps.setAppStatus(Constants.STATE_INVALID);
		apps.setUpdateTime(new Date());
		AppsExample appsExample = new AppsExample();
		AppsExample.Criteria criteria = appsExample.createCriteria();
		if (apps.getId() != null) {
			criteria.andIdEqualTo(apps.getId());
		}
		if (apps.getMedias() != null) {
			criteria.andMediasEqualTo(apps.getMedias());
		}
		appsMapper.updateByExample(apps, appsExample);
		doAdpositionService(apps.getId());
	}

	@Override
	public List<Apps> getApps(Apps apps) {
		AppsExample example = buildExample(apps);
		List<Apps> list = appsMapper.selectByExample(example);
		return list;
	}

	@Transactional
	@Override
	public void updateStaus(Integer id, Integer status) {
		Apps apps = appsMapper.selectByPrimaryKey(id);
		apps.setStatus(status);
		appsMapper.updateByPrimaryKeySelective(apps);
		if (status == Constants.STATE_INVALID) {
			updateFlowControlStatus(id);
			updateWarnSettingStatus(id); //删除后预警设置状态关闭
		}
		doAdpositionService(id);
	}

	private void updateWarnSettingStatus(Integer id) {
		WarningSettingExample warningSettingExample = new WarningSettingExample();
		warningSettingExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppIdEqualTo(id);
		List<WarningSetting> list = warningSettingMapper.selectByExample(warningSettingExample);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		list.forEach(action -> {
			warningSettingService.updateStatus(Constants.STATE_INVALID, action.getId());
		});
	}

	@Override
	public List<Apps> getAll() {
		AppsExample appsExample = new AppsExample();
		appsExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID)
		.andAppStatusEqualTo(Constants.STATE_VALID).andMediasEqualTo(WebConstants.AIKA_UUID);
		List<Apps> list = appsMapper.selectByExample(appsExample);
		return list;
	}

	@SuppressWarnings("serial")
	@Override
	public Apps getOne(Integer id) {
		Apps apps = appsMapper.selectByPrimaryKey(id);		
		List<Integer> adtypes = new ArrayList<Integer>() {
			{
				add(7); //信息流
				/*
				 * add(218);//文字链 add(216);//插屏
				 */				
			}
		};
		AdPositionExample s = new AdPositionExample();
		s.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppIdEqualTo(id).andTypeIn(adtypes);
		List<AdPosition> list = adPositionMapper.selectByExample(s);
		apps.setPlatformName(adDicService.getDic(apps.getPlatform()).getDicValue());
		if (CollectionUtils.isEmpty(list))
			return apps;
		List<Integer> ids = FluentIterable.from(list).transform((AdPosition adPosition) -> {
			return adPosition.getId();
		}).toList();
		SdkAdCarouselExample adCarouselExample = new SdkAdCarouselExample();
		adCarouselExample.createCriteria().andIsDefaultEqualTo(Constants.STATE_VALID)
				.andIsDelEqualTo(Constants.STATE_INVALID).andIdNotIn(Constants.DEFULT_IDS).andAdPositionIn(ids);
		List<SdkAdCarousel> selectByExample = sdkAdCarouselMapper.selectByExample(adCarouselExample);
		if (CollectionUtils.isEmpty(selectByExample))
			return apps;
		List<Integer> pidList = FluentIterable.from(selectByExample).transform((SdkAdCarousel sdkAdCarousel) -> {
			return sdkAdCarousel.getAdPosition();
		}).toList();
		AdPositionExample adPositionExample = new AdPositionExample();
		adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andIdIn(pidList);
		apps.setAdpList(adPositionMapper.selectByExample(adPositionExample));
		return apps;
	}

	@Override
	public List<Apps> getAllByFc() {
		FlowControlExample controlExample = new FlowControlExample();
		controlExample.createCriteria()/* .andStatusEqualTo(Constants.STATE_VALID) */;
		List<FlowControl> list = flowControlMapper.selectByExample(controlExample);
		if (CollectionUtils.isEmpty(list)) 
			return null;
		List<Integer> aids = FluentIterable.from(list).transform((FlowControl flowControl) -> {
			return flowControl.getAppId();
		}).toList();
		aids = aids.stream().distinct().collect(Collectors.toList());
		AppsExample appsExample = new AppsExample();
		appsExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andIdIn(aids);
		List<Apps> result = appsMapper.selectByExample(appsExample);		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAllByFc(Integer cid) {
		Map<String, Object> result = new HashMap<String, Object>();
		FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(cid);
		String adposId = flowConsumer.getAdposId();
		if (StringUtils.isBlank(adposId)) {
			result.put("apps", getAll());
			result.put("select", new ArrayList<Integer>());
			return result;
		}
		Map<String, List<Integer>> map = JsonUtils.TO_OBJ(adposId, Map.class);
		if (CollectionUtils.isEmpty(map) || CollectionUtils.isEmpty(map.get("select_app"))) {
			result.put("apps", getAll());
			result.put("select", new ArrayList<Integer>());
			return result;
		}
		List<Integer> aids = map.get("select_app");
		AppsExample appsExample = new AppsExample();
		appsExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppStatusEqualTo(Constants.STATE_VALID);
		List<Apps> list = appsMapper.selectByExample(appsExample);
		result.put("apps", list);
		result.put("select", aids);
		return result;
	}

	@Override
	public List<Apps> getAppByConsumer(String uuids) {
		if (StringUtils.isBlank(uuids)) {
			return null;
		}
		List<String> uuidList = StringUtils.str2List(uuids, Constants.SIGN_COMMA);
		List<Integer> appIds = Lists.newArrayList();
		FlowConsumerExample consumerExample = new FlowConsumerExample();
		consumerExample.createCriteria().andDspIdIn(uuidList).andConsumerStateEqualTo(Constants.STATE_VALID);
		List<FlowConsumer> consumerList = flowConsumerMapper.selectByExample(consumerExample);
		List<Apps> appsList = appsMapper.selectByExample(new AppsExample());

		for (Apps apps : appsList) {
			String mapper = apps.getConsumerMapped();
			Map<String, Object> mapperMap = JsonUtils.parse2Map(mapper);
			if (mapperMap == null) {
				continue;
			}
			for (FlowConsumer flowConsumer : consumerList) {
				if (mapperMap.containsKey(flowConsumer.getId())) {
					appIds.add(apps.getId());
					break;
				}
			}
		}
		AppsExample example = new AppsExample();
		AppsExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(Constants.STATE_VALID);
		if (appIds.size() > 0) {
			criteria.andIdIn(appIds);
		}
		return appsMapper.selectByExample(example);
	}

	@Override
	public Apps getSdkOne(Integer id) {
		Apps apps = appsMapper.selectByPrimaryKey(id);	
		apps.setPlatformName(adDicService.getDic(apps.getPlatform()).getDicValue());
		AdPositionExample s = new AdPositionExample();
		Criteria createCriteria = s.createCriteria();
		createCriteria.andStatusEqualTo(Constants.STATE_VALID).andAppIdEqualTo(id);
		List<AdPosition> list = adPositionMapper.selectByExample(s);
		if (CollectionUtils.isEmpty(list))
			return apps;
		SdkAdCarouselExample adCarouselExample = new SdkAdCarouselExample();
		List<SdkAdCarousel> example = sdkAdCarouselMapper.selectByExample(adCarouselExample);
		if (CollectionUtils.isEmpty(example)) {
			apps.setAdpList(list);
			return apps;
		}
		List<Integer> pidList = FluentIterable.from(example).transform((SdkAdCarousel sdkAdCarousel) -> {
			return sdkAdCarousel.getAdPosition();
		}).toList();
		createCriteria.andIdNotIn(pidList);
		apps.setAdpList(adPositionMapper.selectByExample(s));
		return apps;
	}

	@Override
	public void doAdpositionService(Integer id) {
		AdPositionExample adPositionExample = new AdPositionExample();
		adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppIdEqualTo(id);
		List<AdPosition> selectByExample = adPositionMapper.selectByExample(adPositionExample);
		if (!CollectionUtils.isEmpty(selectByExample)) {
			selectByExample.forEach(action->{
				adPositionService.writeToRedis(action);
			});
		}		
	}

	/*
	 * @Override public List<Apps> getAppsByFc() { List<FlowCon> list =
	 * flowConMapper.selectByExample(new FlowConExample()); if
	 * (CollectionUtils.isEmpty(list)) { return null; } List<Integer> aids =
	 * FluentIterable.from(list).transform((FlowCon flowControl) -> { return
	 * flowControl.getAppId(); }).toList(); AppsExample appsExample = new
	 * AppsExample();
	 * appsExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).
	 * andAppStatusEqualTo(Constants.STATE_VALID).andIdIn(aids); return
	 * appsMapper.selectByExample(appsExample); }
	 */

}

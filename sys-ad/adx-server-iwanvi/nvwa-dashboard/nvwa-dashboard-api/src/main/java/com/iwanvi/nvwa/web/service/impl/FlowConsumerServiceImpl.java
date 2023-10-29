package com.iwanvi.nvwa.web.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.ShortUrlUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AppChannelMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.AreaMapper;
import com.iwanvi.nvwa.dao.ConsumerPositionPriceMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionExample;
import com.iwanvi.nvwa.dao.model.AppChannel;
import com.iwanvi.nvwa.dao.model.AppChannelExample;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.AppsExample;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.AreaExample;
import com.iwanvi.nvwa.dao.model.ConsumerPositionPrice;
import com.iwanvi.nvwa.dao.model.ConsumerPositionPriceExample;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.FlowConsumerExample;
import com.iwanvi.nvwa.dao.model.FlowConsumerExample.Criteria;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdDicService;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.FlowConsumerService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.util.MatcherUtils;
import com.iwanvi.nvwa.web.util.WebConstants;

@Service
public class FlowConsumerServiceImpl implements FlowConsumerService {

	@Autowired
	private AdPositionService adPositionService;

	@Autowired
	private FlowConsumerMapper flowConsumerMapper;

	@Autowired
	private AdDicService adDicService;

	@Autowired
	private AdPositionMapper adPositionMapper;

	@Autowired
	private AreaMapper areaMapper;

	@Autowired
	private SysCrontabService sysCrontabService;

	@Autowired
	private AppsMapper appsMapper;

	@Autowired
	private ConsumerPositionPriceMapper consumerPositionPriceMapper;

	@Autowired
	private AppChannelMapper appChannelMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, List<AdPosition>> getAkAdposdbyTerminal(Integer id, Integer terminal) {// terminal 170 172 173
																								// 171
		Integer termial_test = 0;
		switch (terminal) {
		case 170:
			termial_test = 22;// pc
			break;
		case 171:
			termial_test = 158;// ios
			break;
		case 172:
			termial_test = 158;// and
			break;
		case 173:
			termial_test = 23;// wap
			break;
		default:
			throw new ServiceException("terminal id erro");
		}
		FlowConsumer fc = flowConsumerMapper.selectByPrimaryKey(id);
		String adposIds = fc.getAdposId();
		Map<String, List<Integer>> adp = null;
		if (StringUtils.isNotBlank(adposIds)) {
			adp = JsonUtils.TO_OBJ(adposIds, Map.class);
		}
		AdPosition adPosition = new AdPosition();
		adPosition.setFlowUuid(WebConstants.KA_FS_UUID);
		adPosition.setStatus(Constants.STATE_VALID);
		Map<String, List<AdPosition>> resultMap = new HashMap<>();
		List<AdPosition> akAdPositions = adPositionService.selectForList(adPosition);
		for (AdPosition adPos : akAdPositions) {
			if (termial_test.equals(adPos.getTerminal())) {
				if (adp != null && adp.containsKey(adDicService.getDic(terminal).getDicValue())
						&& !CollectionUtils.isEmpty(adp.get(adDicService.getDic(terminal).getDicValue()))
						&& adp.get(adDicService.getDic(terminal).getDicValue()).contains(adPos.getId())) {
					adPos.setStatus(3);
				}
				if (resultMap.containsKey(adDicService.getDic(adPos.getType()).getDicValue().split("-")[1])) {
					resultMap.get(adDicService.getDic(adPos.getType()).getDicValue().split("-")[1]).add(adPos);
				} else {
					List<AdPosition> adps = new ArrayList<>();
					adps.add(adPos);
					resultMap.put(adDicService.getDic(adPos.getType()).getDicValue().split("-")[1], adps);
				}
			}
		}
		return resultMap;
	}

	@Override
	public Page<FlowConsumer> geFcPage(FlowConsumer fc, Integer currentPage, Integer pageSize) {
		Page<FlowConsumer> page = null;
		FlowConsumerExample flowConsumerExample = bulidExample(fc);
		int count = (int) flowConsumerMapper.countByExample(flowConsumerExample);
		if (currentPage != null && pageSize != null) {
			page = new Page<FlowConsumer>(count, currentPage, pageSize);
		} else {
			page = new Page<FlowConsumer>(count);
		}
		flowConsumerExample.setStart(page.getStartPosition());
		flowConsumerExample.setLimit(page.getPageSize());
		List<FlowConsumer> selectByExample = flowConsumerMapper.selectByExample(flowConsumerExample);
		fillApp(selectByExample);
		page.bindData(selectByExample);
		return page;
	}

	private void fillApp(List<FlowConsumer> list) {
		if (list != null) {
			for (FlowConsumer flowConsumer : list) {
				String type = adDicService.getDic(flowConsumer.getConsumerType()).getDicValue();
				if (flowConsumer.getConsumerType() == Constants.FC_TYPE_DSP) {
					type = StringUtils.concat(type, Constants.SIGN_MINUS,
							adDicService.getDic(flowConsumer.getIsPrivate()).getDicValue());
				}
				flowConsumer.setTypeName(type);
			}
		}

	}

	private FlowConsumerExample bulidExample(FlowConsumer fc) {
		FlowConsumerExample example = new FlowConsumerExample();
		FlowConsumerExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(fc.getConsumerName())) {
			criteria.andConsumerNameLike("%" + fc.getConsumerName() + "%");
		}
		if (StringUtils.isNotBlank(fc.getConsumerUuid())) {
			criteria.andConsumerUuidEqualTo(fc.getConsumerUuid());
		}
		if (fc.getConsumerType() != null) {// 绫诲瀷
			criteria.andConsumerTypeEqualTo(fc.getConsumerType());
		}
		if (fc.getRunState() != null) {// 杩愯鐘舵��
			criteria.andRunStateEqualTo(fc.getRunState());
		}
		if (StringUtils.isNotBlank(fc.getDspId())) {
			criteria.andDspIdLike("%" + fc.getDspId() + "%");
		}
		criteria.andConsumerStateEqualTo(Constants.STATE_VALID);
		example.setOrderByClause("run_state desc,id desc");
		return example;
	}

	@Transactional
	@Override
	public FlowConsumer getFlowConsumerID(Integer id) {
		return flowConsumerMapper.selectByPrimaryKey(id);
	}

	@Override
	public void insertFc(FlowConsumer fc) {
		if (StringUtils.isBlank(fc.getRtbUrl())) {
			throw new ServiceException("请输入RTB URL .");
		}
		if (!fc.getRtbUrl().trim().startsWith("http://") && !fc.getRtbUrl().trim().startsWith("https://")) {
			throw new ServiceException("请输入正确的RTB URL .");
		}
		String consumerName = fc.getConsumerName();
		if (StringUtils.isNotBlank(consumerName)) {
			if (checkMediaNameExist(consumerName)) {
				throw new ServiceException("该名称" + consumerName + "已存在");
			}
			boolean uuidSuccess = false;
			boolean dspIduccess = false;
			while (!uuidSuccess && !dspIduccess) {
				String uuid = ShortUrlUtils.getByUUID();
				String dspId = ShortUrlUtils.getByUUID();
				boolean uuidExisted = checkUuidIsExisted(uuid);
				boolean dspidExisted = checkDspidIsExisted(dspId);
				if (!uuidExisted && !dspidExisted) {
					if (StringUtils.isNotBlank(fc.getAdposId())) {
						Integer terminal = fc.getTerminal();
						String adposId = fc.getAdposId();
						Map<Integer, List<Integer>> adps = new HashMap<>();
						String[] split = adposId.split(",");
						List<Integer> adpid = Arrays.asList(split).stream().map(Integer::parseInt)
								.collect(Collectors.toList());
						adps.put(terminal, adpid);
						fc.setAdposId(JsonUtils.TO_JSON(adps));
					}
					fc.setConsumerUuid(ShortUrlUtils.getByUUID());

					fc.setDspId(dspId);
					fc.setRunState(Constants.STATE_VALID);
					fc.setConsumerState(Constants.STATE_VALID);
					fc.setCreateTime(new Date());
					flowConsumerMapper.insert(fc);
					/*
					 * 选择定向条件后同步 int count = sysCrontabService.countCrontab(fc.getId(),
					 * Constants.OBJ_APP); if (count > 0) {
					 * sysCrontabService.addSysCrontab(fc.getId(), Constants.OBJ_FLOW_CONSUMER,
					 * Constants.OP_UPDATE); } else { sysCrontabService.addSysCrontab(fc.getId(),
					 * Constants.OBJ_FLOW_CONSUMER, Constants.OP_ADD); }
					 */
					uuidSuccess = true;
					dspIduccess = true;
				}
			}
		}

		/*
		 * @Override public List<AdPosition> getAllianceAdpos(FlowSource flowSource,
		 * Integer platform) { Apps apps = new Apps();
		 * apps.setMedias(flowSource.getMediaUuid()); apps.setPlatform(platform);
		 * List<Apps> appsList = appService.getApps(apps); StringBuilder builder = new
		 * StringBuilder(); for (Apps application : appsList) { String adPosIds =
		 * application.getAdPosIds(); if (StringUtils.isNotBlank(adPosIds)) { if
		 * (!builder.toString().contains(adPosIds)) {
		 * builder.append(StringUtils.buildString(Constants.SIGN_COMMA, adPosIds)); } }
		 * } String[] split = builder.toString().split(Constants.SIGN_COMMA);
		 * List<Integer> adposids = CollectionUtils.arrayToList(split); //List<Integer>
		 * adposids =
		 * asList.stream().map(Integer::parseInt).collect(Collectors.toList());
		 * AdPositionExample example = new AdPositionExample();
		 * example.createCriteria().andIdIn(adposids); List<AdPosition> adPositions =
		 * adPositionMapper.selectByExample(example); return adPositions; }
		 */
	}

	private boolean checkUuidIsExisted(String uuid) {
		boolean isExisted = false;
		if (uuid != null) {
			FlowConsumerExample example = new FlowConsumerExample();
			example.createCriteria().andConsumerUuidEqualTo(uuid);
			long count = flowConsumerMapper.countByExample(example);
			if (count > 0) {
				isExisted = true;
			}
		}
		return isExisted;
	}

	private boolean checkDspidIsExisted(String dspid) {
		boolean isExisted = false;
		if (dspid != null) {
			FlowConsumerExample example = new FlowConsumerExample();
			example.createCriteria().andDspIdEqualTo(dspid);
			long count = flowConsumerMapper.countByExample(example);
			if (count > 0) {
				isExisted = true;
			}
		}
		return isExisted;
	}

	private boolean checkMediaNameExist(String consumerName) {
		FlowConsumerExample example = new FlowConsumerExample();
		example.createCriteria().andConsumerNameEqualTo(consumerName).andConsumerStateEqualTo(Constants.STATE_VALID);
		long count = flowConsumerMapper.countByExample(example);
		return count > Constants.INTEGER_0;
	}

	@Override
	public void updateFC(FlowConsumer fc) {
		FlowConsumer oldFlowConsumer = flowConsumerMapper.selectByPrimaryKey(fc.getId());
		if (oldFlowConsumer != null) {
			fc.setUpdateTime(new Date());
			fc.setConsumerState(Constants.STATE_VALID);
			flowConsumerMapper.updateByPrimaryKeySelective(fc);
			int count = sysCrontabService.countCrontab(fc.getId(), Constants.OBJ_FLOW_CONSUMER);
			if (count > 0) {
				sysCrontabService.addSysCrontab(fc.getId(), Constants.OBJ_FLOW_CONSUMER, Constants.OP_UPDATE);
			} else {
				sysCrontabService.addSysCrontab(fc.getId(), Constants.OBJ_FLOW_CONSUMER, Constants.OP_ADD);
			}
		}
	}

	@Override
	@Transactional
	public void deFcById(Integer id) {
		FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(id);
		flowConsumer.setConsumerState(Constants.STATE_INVALID);
		flowConsumer.setUpdateTime(new Date());
		flowConsumerMapper.updateByPrimaryKeySelective(flowConsumer);
		int count = sysCrontabService.countCrontab(id, Constants.OBJ_FLOW_CONSUMER);
		if (count > 0) {
			sysCrontabService.addSysCrontab(id, Constants.OBJ_FLOW_CONSUMER, Constants.OP_REMOVE);
		}
	}

	@Override
	public List<FlowConsumer> getSdkList() {
		FlowConsumerExample consumerExample = new FlowConsumerExample();
		consumerExample.createCriteria().andConsumerStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID).andConsumerTypeEqualTo(Constants.FC_TYPE_SDK);
		return flowConsumerMapper.selectByExample(consumerExample);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getFlowDx(Map<String, Object> param) {
		Integer id = (Integer) param.get("id");
		if (!param.containsKey("aids") || param.get("aids") == null
				|| CollectionUtils.isEmpty((List<Integer>) param.get("aids"))) {
			throw new ServiceException("请选择App .");
		}
		List<Integer> aids = (List<Integer>) param.get("aids");

		Map<String, Object> map = new HashMap<String, Object>();

		FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(id);
		String json = flowConsumer.getAdposId();
		Map<String, List<Integer>> oldMap = null;
		if (StringUtils.isNotBlank(json)) {
			oldMap = JsonUtils.TO_OBJ(json, Map.class);
		}
		AppsExample appsExample = new AppsExample();
		appsExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppStatusEqualTo(Constants.STATE_VALID)
				.andIdIn(aids);

		List<Apps> all = appsMapper.selectByExample(appsExample);
		List<Apps> apps = new ArrayList<Apps>();
		for (Apps app : all) {
			AdPositionExample adPositionExample = new AdPositionExample();
			adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppIdEqualTo(app.getId());
			List<AdPosition> list = adPositionMapper.selectByExample(adPositionExample);
			if (!CollectionUtils.isEmpty(list)) {
				apps.add(app);
			}
		}
		map.put("consumer_name", flowConsumer.getConsumerName());
		// List<BookCategory> books = bookCategoryService.select(null);
		apps = buildApps(oldMap, apps);
		List<AppChannel> channels = buildchannels(oldMap);
		map.put("dx_channel", channels);
		if (CollectionUtils.isEmpty(oldMap)) {
			map.put("dx_app", apps);
			map.put("dx_book", null);
			map.put("dx_area", null);
			map.put("dx_did", flowConsumer.getDxDid());
			return map;
		}
		List<Integer> books = oldMap.get("dx_book");
		List<Area> areas = buildArea(oldMap);
		map.put("dx_app", apps);
		map.put("dx_book", books);
		map.put("dx_area", areas);
		map.put("dx_did", flowConsumer.getDxDid());
		return map;
	}

	private List<AppChannel> buildchannels(Map<String, List<Integer>> oldMap) {
		AppChannelExample appChannelExample = new AppChannelExample();
		appChannelExample.createCriteria().andIsDeletedEqualTo(false);
		List<AppChannel> list = appChannelMapper.selectByExample(appChannelExample);
		if (CollectionUtils.isEmpty(oldMap)) {
			return list;
		}
		List<Integer> channels = oldMap.get("dx_channel");
		if (!CollectionUtils.isEmpty(list)) {
			list.forEach(action->{
				action.setBaseId(channels);
			});
		}
		return list;
	}

	private List<Area> buildArea(Map<String, List<Integer>> oldMap) {
		List<Integer> areaId = oldMap.get("dx_area");
		if (CollectionUtils.isEmpty(areaId))
			return null;
		AreaExample areaExample = new AreaExample();
		areaExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andIdIn(areaId);
		List<Area> list = areaMapper.selectByExample(areaExample);
		return list;
	}

	private List<Apps> buildApps(Map<String, List<Integer>> oldMap, List<Apps> apps) {
		if (CollectionUtils.isEmpty(oldMap)) {
			oldMap = new HashMap<String, List<Integer>>();
		}
		List<Integer> list = oldMap.get("dx_adposion");
		List<Integer> appList = oldMap.get("dx_app");
		apps.forEach(app -> {
			List<Integer> adpIds = new ArrayList<Integer>();
			AdPositionExample adPositionExample = new AdPositionExample();
			adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andAppIdEqualTo(app.getId());
			List<AdPosition> adps = adPositionMapper.selectByExample(adPositionExample);
			if (!CollectionUtils.isEmpty(list)) {
				list.forEach(adpId -> {
					Optional<AdPosition> Optional = adps.stream().filter(adp -> adp.getId().equals(adpId)).findFirst();
					if (Optional.isPresent()) {
						AdPosition adPosition = Optional.get();
						adpIds.add(adPosition.getId());
					}
				});
			}
			app.setBaseid(adpIds);
			if (!CollectionUtils.isEmpty(appList) && appList.contains(app.getId())) {
				app.setBaseid(null);
			}
			app.setAdpList(adps);
			app.setPlatformName(adDicService.getDic(app.getPlatform()).getDicValue());
		});
		return apps;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void setFlowDx(Map<String, Object> map) {
		String dids = (String) map.get("dids");
		Integer id = (Integer) map.get("id");
		String aids = String.valueOf(map.get("aids"));
		List<Integer> aidList = StringUtils.isBlank(aids) ? new ArrayList<Integer>()
				: Arrays.asList(aids.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
						.collect(Collectors.toList());
		List<Integer> bids = CollectionUtils.isEmpty((List<Integer>) map.get("bids")) ? new ArrayList<Integer>()
				: (List<Integer>) map.get("bids");
		
		List<Integer> cids = CollectionUtils.isEmpty((List<Integer>) map.get("cids")) ? new ArrayList<Integer>()
				: (List<Integer>) map.get("cids");
		
		List<Map<String, List<Integer>>> apps_adp = (List<Map<String, List<Integer>>>) map.get("dx_adposion");

		List<Integer> adpList = new ArrayList<Integer>();
		List<Integer> apps = new ArrayList<Integer>();
		apps_adp.forEach(app -> {
			app.entrySet().forEach(entry -> {
				List<Integer> adps = entry.getValue();
				if (adps.size() == Constants.INTEGER_1 && adps.get(Constants.INTEGER_0) == -1) {// -1为不限
					apps.add(Integer.parseInt(entry.getKey()));
					AdPositionExample adPositionExample = new AdPositionExample();
					adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID)
							.andAppIdEqualTo(Integer.parseInt(entry.getKey()));
					List<AdPosition> list = adPositionMapper.selectByExample(adPositionExample);
					if (CollectionUtils.isEmpty(list)) {
						adps = new ArrayList<Integer>();
					} else {
						adps = FluentIterable.from(list).transform((AdPosition adPosition) -> {
							return adPosition.getId();
						}).toList();
					}
				}
				adpList.addAll(adps);
			});
		});

		/*
		 * if (CollectionUtils.isEmpty(adpList)) { throw new
		 * ServiceException("请选择广告位 ."); }
		 */

		Map<String, List<Integer>> dx_map = new HashMap<String, List<Integer>>();
		dx_map.put("dx_app", apps); // 该属性只存放选择不限的app
		dx_map.put("dx_adposion", adpList);
		dx_map.put("dx_book", bids);
		dx_map.put("dx_channel", cids);
		dx_map.put("dx_area", aidList);

		List<Integer> select_app = CollectionUtils.isEmpty((List<Integer>) map.get("select_app"))
				? new ArrayList<Integer>()
				: (List<Integer>) map.get("select_app");
		dx_map.put("select_app", select_app);
		FlowConsumer consumer = new FlowConsumer();
		consumer.setDxDid(dids);
		consumer.setId(id);
		consumer.setAdposId(JsonUtils.TO_JSON(dx_map));
		flowConsumerMapper.updateByPrimaryKeySelective(consumer);

		FlowConsumer fc = flowConsumerMapper.selectByPrimaryKey(id);

		if (Constants.IS_DEAL.equals(fc.getIsDeal()) && !CollectionUtils.isEmpty(adpList)) { 
			ConsumerPositionPriceExample c = new ConsumerPositionPriceExample();
			c.createCriteria().andCidEqualTo(id).andPidNotIn(adpList);
			consumerPositionPriceMapper.deleteByExample(c);// 没有的广告位删除
			adpList.forEach(action -> { // 向价格表中插入数据
				ConsumerPositionPrice cpp = new ConsumerPositionPrice();
				cpp.setCid(id);
				cpp.setPid(action);
				cpp.setPrice(Constants.INTEGER_0);
				ConsumerPositionPriceExample consumerPositionPriceExample = new ConsumerPositionPriceExample();
				consumerPositionPriceExample.createCriteria().andCidEqualTo(id).andPidEqualTo(action);
				List<ConsumerPositionPrice> list = consumerPositionPriceMapper
						.selectByExample(consumerPositionPriceExample);
				if (CollectionUtils.isEmpty(list)) {
					consumerPositionPriceMapper.insert(cpp);
				}
			});
		}

		int count = sysCrontabService.countCrontab(id, Constants.OBJ_APP);
		if (count > 0) {
			sysCrontabService.addSysCrontab(id, Constants.OBJ_FLOW_CONSUMER, Constants.OP_UPDATE);
		} else {
			sysCrontabService.addSysCrontab(id, Constants.OBJ_FLOW_CONSUMER, Constants.OP_ADD);
		}

	}

	@Override
	public List<FlowConsumer> getListByType(FlowConsumer fc) {
		FlowConsumerExample example = new FlowConsumerExample();
		FlowConsumerExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(fc.getConsumerName())) {
			criteria.andConsumerNameLike("%" + fc.getConsumerName() + "%");
		}
		if (StringUtils.isNotBlank(fc.getConsumerUuid())) {
			criteria.andConsumerUuidEqualTo(fc.getConsumerUuid());
		}
		if (fc.getConsumerType() != null) {
			criteria.andConsumerTypeEqualTo(fc.getConsumerType());
		}
		if (fc.getRunState() != null) {
			criteria.andRunStateEqualTo(fc.getRunState());
		}
		if (StringUtils.isNotBlank(fc.getDspId())) {
			criteria.andDspIdLike("%" + fc.getDspId() + "%");
		}
		if (fc.getId() != null) {
			criteria.andIdEqualTo(fc.getId());
		}
		return flowConsumerMapper.selectByExample(example);
	}

	@Override
	public List<FlowConsumer> getSdkListToApp(String cids) {
		FlowConsumerExample consumerExample = new FlowConsumerExample();
		Criteria createCriteria = consumerExample.createCriteria();
		createCriteria.andConsumerStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID);
		if (StringUtils.isNotBlank(cids)) {
			List<Integer> cidList = StringUtils.isBlank(cids) ? new ArrayList<Integer>()
					: Arrays.asList(cids.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
							.collect(Collectors.toList());
			createCriteria.andIdNotIn(cidList);
		}
		return flowConsumerMapper.selectByExample(consumerExample);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FlowConsumer> getListByMapped(Integer pid) {
		AdPosition adPosition = adPositionMapper.selectByPrimaryKey(pid);
		Integer appId = adPosition.getAppId();
		Apps apps = appsMapper.selectByPrimaryKey(appId);
		String consumerMapped = apps.getConsumerMapped();
		if (StringUtils.isBlank(consumerMapped)) {
			return null;
		}
		Map<Integer, String> map_test = JsonUtils.TO_OBJ(apps.getConsumerMapped(), Map.class);
		if (CollectionUtils.isEmpty(map_test)) {
			return null;
		}
		List<Integer> keys = new ArrayList<>();
		map_test.entrySet().stream().forEach(e -> keys.add(e.getKey()));
		FlowConsumerExample consumerExample = new FlowConsumerExample();
		consumerExample.createCriteria().andConsumerStateEqualTo(Constants.STATE_VALID).andRunStateEqualTo(Constants.STATE_VALID).andIdIn(keys);
		return flowConsumerMapper.selectByExample(consumerExample);
	}

	@Override
	public List<FlowConsumer> listByApp(String appIds) {
		FlowConsumerExample example = new FlowConsumerExample();
		if (StringUtils.isNotBlank(appIds)) {
			List<String> appids = StringUtils.str2List(appIds, Constants.SIGN_COMMA);
			Set<Integer> consumerIds = Sets.newHashSet();
			appids.forEach(id -> {
				Apps apps = appsMapper.selectByPrimaryKey(Integer.parseInt(id));
				Map<Integer, Object> map = JsonUtils.parse2MapForIntKey(apps.getConsumerMapped());
				if (map != null) {
					for (Integer key : map.keySet()) {
						consumerIds.add(key);
					}
				}
			});
			if (consumerIds.size() > 0) {
				example.createCriteria().andIdIn(new ArrayList<>(consumerIds));
			}else if (appids.size() > 0 && consumerIds.size() == 0) {
				List<Integer> temp = Lists.newArrayList(Integer.MAX_VALUE);
				example.createCriteria().andIdIn(temp);
			}
		}
		return flowConsumerMapper.selectByExample(example);
	}
}
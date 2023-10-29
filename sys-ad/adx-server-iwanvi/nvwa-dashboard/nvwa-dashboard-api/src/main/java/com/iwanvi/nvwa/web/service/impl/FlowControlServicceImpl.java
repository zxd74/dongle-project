package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AppChannelMapper;
import com.iwanvi.nvwa.dao.AppVersionMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.FlowControlMapper;
import com.iwanvi.nvwa.dao.model.AppChannel;
import com.iwanvi.nvwa.dao.model.AppChannelExample;
import com.iwanvi.nvwa.dao.model.AppVersion;
import com.iwanvi.nvwa.dao.model.AppVersionExample;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.FlowConsumerExample;
import com.iwanvi.nvwa.dao.model.FlowControl;
import com.iwanvi.nvwa.dao.model.FlowControlExample;
import com.iwanvi.nvwa.dao.model.FlowControlExample.Criteria;
import com.iwanvi.nvwa.dao.model.support.FlowControlVo;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.FlowConsumerService;
import com.iwanvi.nvwa.web.service.FlowControlServicce;
import com.iwanvi.nvwa.web.util.WebConstants;

@Service
public class FlowControlServicceImpl implements FlowControlServicce {

	@Autowired
	private FlowControlMapper flowControlMapper;

	@Autowired
	private AppsMapper appsMapper;

	@Autowired
	private FlowConsumerMapper flowConsumerMapper;

	@Autowired
	private AppChannelMapper appChannelMapper;

	@Autowired
	private AppVersionMapper appVersionMapper;

	@Autowired
	private FlowConsumerService flowConsumerService;

	@Autowired
	private RedisDao redisDao;

	@Override
	@Transactional
	public void batchInsert(FlowControl flowControl) {
		if (StringUtils.isBlank(flowControl.getAids())) {
			throw new ServiceException("请至少选择一个App . ");
		}
		if (StringUtils.isBlank(flowControl.getVids())) {
			throw new ServiceException("请至少输入一个版本 . ");
		}
		if (StringUtils.isBlank(flowControl.getCids())) {
			throw new ServiceException("请至少输入一个渠道 . ");
		}
		
		String versions = flowControl.getVids();
		List<String> version_list = Arrays.asList(versions.split(Constants.SIGN_COMMA)).stream()
				.filter(action -> StringUtils.isNotBlank(action)).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(version_list)) {
			throw new ServiceException("版本不能为空。");
		}
		AppVersionExample appVersionExample = new AppVersionExample();
		appVersionExample.createCriteria().andIsDeletedEqualTo(false).andNameIn(version_list);
		List<AppVersion> exit_versions = appVersionMapper.selectByExample(appVersionExample);

		String channels = flowControl.getCids();
		List<String> channel_list = Arrays.asList(channels.split(Constants.SIGN_COMMA)).stream()
				.filter(action -> StringUtils.isNotBlank(action)).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(channel_list)) {
			throw new ServiceException("渠道不能为空。");
		}

		AppChannelExample appChannelExample = new AppChannelExample();
		appChannelExample.createCriteria().andIsDeletedEqualTo(false).andNameIn(channel_list);
		List<AppChannel> exit_channels = appChannelMapper.selectByExample(appChannelExample);

		if (CollectionUtils.isEmpty(exit_channels) || CollectionUtils.isEmpty(exit_versions)) {
			throw new ServiceException("没有匹配的值 ");
		}

		List<Integer> vidList  = FluentIterable.from(exit_versions).transform((AppVersion appVersion) -> {
			return appVersion.getId();
		}).toList();
		List<Integer> cidList = FluentIterable.from(exit_channels).transform((AppChannel appChannel) -> {
			return appChannel.getId();
		}).toList();
		
		String aids = flowControl.getAids();
		List<Integer> aidList = Arrays.asList(aids.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());

		List<FlowControl> reList = new ArrayList<FlowControl>();
		aidList.forEach(aid -> {
			vidList.forEach(vid -> {
				cidList.forEach(cid -> {
					FlowControlExample controlExample = new FlowControlExample();
					controlExample.createCriteria().andAppIdEqualTo(aid).andChannelIdEqualTo(cid)
							.andVersionIdEqualTo(vid);
					List<FlowControl> list = flowControlMapper.selectByExample(controlExample);
					FlowControl control = new FlowControl();
					control.setStatus(flowControl.getStatus());
					control.setAdPlatformIds(flowControl.getAdPlatformIds());
					if (CollectionUtils.isEmpty(list)) {
						control.setAppId(aid);
						control.setChannelId(cid);
						control.setVersionId(vid);
						reList.add(control);
					} /*
						 * else { flowControlMapper.updateByExampleSelective(control, controlExample); }
						 */
				});
			});
		});
		if (!CollectionUtils.isEmpty(reList))
			flowControlMapper.batchInsert(reList);

		List<FlowControlVo> list = buildList();
		redisDao.set(WebConstants.KEY_FLOW_CONTROL, JsonUtils.TO_JSON(list));
	}
	

	private List<FlowControlVo> buildList() { 
		List<FlowControl> reList = flowControlMapper.selectByExample(new FlowControlExample());
		
		List<FlowControlVo> list = new ArrayList<FlowControlVo>();
		reList.forEach(action -> {
			FlowControlVo controlVo = new FlowControlVo();
			Apps apps = appsMapper.selectByPrimaryKey(action.getAppId());
			AppChannel appchannel = appChannelMapper.selectByPrimaryKey(action.getChannelId());
			AppVersion appv = appVersionMapper.selectByPrimaryKey(action.getVersionId());
			controlVo.setId(action.getId());
			controlVo.setApp_uuid(apps.getAppId());
			controlVo.setC_id(appchannel.getName());
			controlVo.setV_id(appv.getName());
			//controlVo.setPlatform_id(action.getAdPlatformIds());
			list.add(controlVo);
		});
		return list;
	}

	@Override
	public Page<FlowControl> getPage(FlowControl flowControl) {
		Page<FlowControl> page = null;
		FlowControlExample example = buildExample(flowControl);
		int count = (int) flowControlMapper.countByExample(example);
		if (flowControl.getCurrentPage() != null && flowControl.getPageSize() != null) {
			page = new Page<FlowControl>(count, flowControl.getCurrentPage(), flowControl.getPageSize());
		} else {
			page = new Page<FlowControl>(count);
		}
		example.setStart(page.getStartPosition());
		example.setLimit(page.getPageSize());
		List<FlowControl> list = flowControlMapper.selectByExample(example);
		fillFlowControl(list);
		page.bindData(list);
		return page;
	}

	private void fillFlowControl(List<FlowControl> list) {
		if (CollectionUtils.isEmpty(list))
			return;
		list.forEach(flowControl -> {
			flowControl = bulidFlowControl(flowControl);
			String adPlatformIds = flowControl.getAdPlatformIds();
			if (StringUtils.isNotBlank(adPlatformIds)) {
				List<Integer> pidList = Arrays.asList(adPlatformIds.split(Constants.SIGN_COMMA)).stream()
						.map(s -> Integer.parseInt(s)).collect(Collectors.toList());
				FlowConsumerExample consumerExample = new FlowConsumerExample();
				consumerExample.createCriteria().andIdIn(pidList);
				List<FlowConsumer> fList = flowConsumerMapper.selectByExample(consumerExample);
				if (!CollectionUtils.isEmpty(fList)) {
					List<String> fNames = FluentIterable.from(fList).transform((FlowConsumer flowConsumer) -> {
						return flowConsumer.getConsumerName();
					}).toList();
					flowControl.setAdPlatformNames(String.join(Constants.SIGN_COMMA, fNames));
				}
			}
		});

	}

	private FlowControlExample buildExample(FlowControl flowControl) {
		FlowControlExample example = new FlowControlExample();
		FlowControlExample.Criteria criteria = example.createCriteria();
		if (flowControl.getAppId() != null) {
			criteria.andAppIdEqualTo(flowControl.getAppId());
		}
		if (flowControl.getChannelId() != null) {
			criteria.andChannelIdEqualTo(flowControl.getChannelId());
		}
		if (flowControl.getVersionId() != null) {
			criteria.andVersionIdEqualTo(flowControl.getVersionId());
		}
		if (flowControl.getStatus() != null) {
			criteria.andStatusEqualTo(flowControl.getStatus());
		}
		if (StringUtils.isNotBlank(flowControl.getAdPlatformIds())) {
			criteria.andAdPlatformIdsLike("%" + flowControl.getAdPlatformIds() + "%");
		}
		example.setOrderByClause("status desc,id desc");
		return example;
	}

	@Override
	@Transactional
	public void flowSwitch(Integer status, Integer id) {
		if (status == Constants.STATE_VALID) {
			FlowControl flowControl = flowControlMapper.selectByPrimaryKey(id);
			Apps apps = appsMapper.selectByPrimaryKey(flowControl.getAppId());
			if (apps.getStatus() == Constants.STATE_INVALID || apps.getAppStatus() == Constants.STATE_INVALID) {
				throw new ServiceException("该App已删除或状态关闭，无法开启流量  .  ");
			}
			AppVersion appVersion = appVersionMapper.selectByPrimaryKey(flowControl.getVersionId());
			if (appVersion.getIsDeleted()) {
				throw new ServiceException("该版本已删除，无法开启流量  .  ");
			}
			AppChannel appChannel = appChannelMapper.selectByPrimaryKey(flowControl.getChannelId());
			if (appChannel.getIsDeleted()) {
				throw new ServiceException("该渠道已删除，无法开启流量  .  ");
			}
		}
		FlowControl record = new FlowControl();
		record.setStatus(status);
		record.setId(id);
		flowControlMapper.updateByPrimaryKeySelective(record);

		List<FlowControlVo> list = buildList();
		redisDao.set(WebConstants.KEY_FLOW_CONTROL, JsonUtils.TO_JSON(list));
	}

	@Override
	public FlowControl getOne(Integer id) {
		FlowControl flowControl = flowControlMapper.selectByPrimaryKey(id);
		flowControl = bulidFlowControl(flowControl);
		String adPlatformIds = flowControl.getAdPlatformIds();
		List<FlowConsumer> sdkList = flowConsumerService.getSdkList();
		if (StringUtils.isBlank(adPlatformIds)) {
			flowControl.setPlatformList(sdkList);
			return flowControl;
		}
		List<Integer> pidList = Arrays.asList(adPlatformIds.split(Constants.SIGN_COMMA)).stream()
				.map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		pidList.forEach(pid -> {
			Optional<FlowConsumer> Optional = sdkList.stream().filter(sdk -> sdk.getId().equals(pid)).findFirst();
			if (Optional.isPresent()) {
				FlowConsumer flowConsumer = Optional.get();
				flowConsumer.setConsumerState(Constants.INTEGER_2);
			}
		});
		flowControl.setPlatformList(sdkList);
		return flowControl;
	}

	private FlowControl bulidFlowControl(FlowControl flowControl) {
		flowControl.setAppName(appsMapper.selectByPrimaryKey(flowControl.getAppId()).getAppName());
		flowControl.setChannelName(appChannelMapper.selectByPrimaryKey(flowControl.getChannelId()).getName());
		flowControl.setVersion(appVersionMapper.selectByPrimaryKey(flowControl.getVersionId()).getName());
		return flowControl;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public void batchUpdate(FlowControl flowControl) {
		String appIds = flowControl.getAids();
		String cnames = flowControl.getCids();
		String vnames = flowControl.getVids();

		if (StringUtils.isBlank(appIds) && StringUtils.isBlank(cnames) && StringUtils.isBlank(vnames)) {
			throw new ServiceException("必须选择一个维度进行操作 .  ");
		}		
		List<Integer> vidList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(vnames)) {
			List<String> version_list = Arrays.asList(vnames.split(Constants.SIGN_COMMA)).stream()
					.filter(action -> StringUtils.isNotBlank(action)).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(version_list)) {
				AppVersionExample appVersionExample = new AppVersionExample();
				appVersionExample.createCriteria().andIsDeletedEqualTo(false).andNameIn(version_list);
				List<AppVersion> exit_versions = appVersionMapper.selectByExample(appVersionExample);
				List<Integer> list = FluentIterable.from(exit_versions).transform((AppVersion appVersion) -> {
					return appVersion.getId();
				}).toList();
				if (CollectionUtils.isEmpty(list)) {
					throw new ServiceException("您输入的版本号有误，请检查后重新输入 .  ");
				}
				vidList = CollectionUtils.isEmpty(list) ? new ArrayList<Integer>() :  new ArrayList(list);
			}
		}
		if (vidList.size() == 0) {
			vidList.add(Constants.INTEGER_0);	
		}
		List<Integer> cidList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(cnames)) {
			List<String> channel_list = Arrays.asList(cnames.split(Constants.SIGN_COMMA)).stream()
					.filter(action -> StringUtils.isNotBlank(action)).collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(channel_list)) {
				AppChannelExample appChannelExample = new AppChannelExample();
				appChannelExample.createCriteria().andIsDeletedEqualTo(false).andNameIn(channel_list);
				List<AppChannel> exit_versions = appChannelMapper.selectByExample(appChannelExample);
				List<Integer> list = FluentIterable.from(exit_versions).transform((AppChannel appChannel) -> {
					return appChannel.getId();
				}).toList();
				if (CollectionUtils.isEmpty(list)) {
					throw new ServiceException("您输入的渠道号有误，请检查后重新输入 .  ");
				}
				cidList = CollectionUtils.isEmpty(list) ? new ArrayList<Integer>() :  new ArrayList(list);
			}
		}
		if (cidList.size() == 0) {
			cidList.add(Constants.INTEGER_0);
		}
		List<Integer> aidList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(appIds)) {
			List<Integer> list = Arrays.asList(appIds.split(Constants.SIGN_COMMA)).stream().map(s -> Integer.parseInt(s))
					.collect(Collectors.toList());
			aidList = CollectionUtils.isEmpty(list) ? new ArrayList<Integer>() :  new ArrayList(list);
		}
		if (aidList.size() == 0) {
			aidList.add(Constants.INTEGER_0);
		}
		
		for (Integer aid : aidList) {
			for (Integer vid : vidList) {
				for (Integer cid : cidList) {
					FlowControlExample flowControlExample = new FlowControlExample();
					Criteria criteria = flowControlExample.createCriteria();
					if (aid != Constants.INTEGER_0) {
						criteria.andAppIdEqualTo(aid);
					}
					if (cid != Constants.INTEGER_0) {
						criteria.andChannelIdEqualTo(cid);
					}
					if (vid != Constants.INTEGER_0) {
						criteria.andVersionIdEqualTo(vid);
					}
					if (aid == Constants.INTEGER_0 && cid == Constants.INTEGER_0 && vid == Constants.INTEGER_0) {
						continue;
					}
					flowControlMapper.deleteByExample(flowControlExample);
				}
			}
		}
		List<FlowControlVo> list = buildList();
		redisDao.set(WebConstants.KEY_FLOW_CONTROL, JsonUtils.TO_JSON(list));
	}

	@Override
	@Transactional
	public void update(FlowControl flowControl) {
		if (flowControl.getId() == null)
			throw new ServiceException("id不能为空 .  ");
		flowControlMapper.updateByPrimaryKeySelective(flowControl);

		List<FlowControlVo> list = buildList();
		redisDao.set(WebConstants.KEY_FLOW_CONTROL, JsonUtils.TO_JSON(list));
	}


	@Override
	@Transactional
	public void getDel(Integer id) {
		flowControlMapper.deleteByPrimaryKey(id);
		List<FlowControlVo> list = buildList();
		redisDao.set(WebConstants.KEY_FLOW_CONTROL, JsonUtils.TO_JSON(list));
	}

}

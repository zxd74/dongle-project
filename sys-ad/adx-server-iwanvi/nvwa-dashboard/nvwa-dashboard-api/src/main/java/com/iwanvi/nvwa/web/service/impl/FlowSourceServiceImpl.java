package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.ShortUrlUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.FlowSourceMapper;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionExample;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.FlowSourceExample;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.UserExample;
import com.iwanvi.nvwa.dao.model.UserGrand;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.AppService;
import com.iwanvi.nvwa.web.service.FlowSourceService;
import com.iwanvi.nvwa.web.util.WebConstants;

@Service
public class FlowSourceServiceImpl implements FlowSourceService {

	@Autowired
	private FlowSourceMapper flowSourceMapper;

	@Autowired
	private AdPositionService adPositionService;

	@Autowired
	private AppService appService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AdPositionMapper adPositionMapper;

	@Override
	public Page<FlowSource> getfsPage(FlowSource fs, Integer currentPage, Integer pageSize) {
		Page<FlowSource> page = null;
		FlowSourceExample flowSourceExample = bulidExample(fs);
		if (flowSourceExample == null) {
			return new Page<FlowSource>(0);
		}
		int count = flowSourceMapper.countByExample(flowSourceExample);
		if (currentPage != null && pageSize != null) {
			page = new Page<FlowSource>(count, currentPage, pageSize);
		} else {
			page = new Page<FlowSource>(count);
		}
		flowSourceExample.setStart(page.getStartPosition());
		flowSourceExample.setLimit(page.getPageSize());
		List<FlowSource> flowSourceList = flowSourceMapper.selectByExample(flowSourceExample);
		List<AdPosition> list = null;
		for (FlowSource flowSource : flowSourceList) {
			AdPositionExample adPositionExample = new AdPositionExample();
			adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID)
					.andFlowUuidEqualTo(flowSource.getMediaUuid());
			list = adPositionMapper.selectByExample(adPositionExample);
			if (CollectionUtils.isEmpty(list)) {
				flowSource.setAdpCount(0);
			} else {
				flowSource.setAdpCount(list.size());
			}
		}
		page.bindData(flowSourceList);

		return page;
	}

	@Override
	public List<FlowSource> getFSList(FlowSource fs) {
		FlowSourceExample flowSourceExample = bulidExample(fs);
		if (flowSourceExample == null) {
			return new ArrayList();
		}
		List<FlowSource> flowSourceList = flowSourceMapper.selectByExample(flowSourceExample);
		return flowSourceList;
	}

	/**
	 * 添加查询条件
	 * 
	 * @param fs
	 * @param aids
	 * @return
	 */
	private FlowSourceExample bulidExample(FlowSource fs) {
		FlowSourceExample example = new FlowSourceExample();
		FlowSourceExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(fs.getMediaName())) {
			criteria.andMediaNameLike("%" + fs.getMediaName() + "%");
		}
		if (StringUtils.isNotBlank(fs.getMediaUuid())) {// 媒体UUID
			criteria.andMediaUuidEqualTo(fs.getMediaUuid());
		}
		if (fs.getMediaType() != null) {// 媒体类型
			criteria.andMediaTypeEqualTo(fs.getMediaType());
		}
		if (fs.getRunState() != null) {// 运行状态
			criteria.andRunStateEqualTo(fs.getRunState());
		}
		if (fs.getCompanyName() != null) {
			criteria.andCompanyNameLike("%" + fs.getCompanyName() + "%");
		}
		if (fs.getType() != null) {
			criteria.andTypeEqualTo(fs.getType());
		}
		criteria.andMediaStateEqualTo(Constants.STATE_VALID);

		example.setOrderByClause("run_state desc,id desc");
		return example;
	}

	@Transactional
	@Override
	public void insertFS(FlowSource fs) {
		String mediaName = fs.getMediaName();
		Integer type = fs.getMediaType();
		if (StringUtils.isNotBlank(mediaName)) {
			if (checkMediaNameExist(mediaName, type)) {
				throw new ServiceException("流量源：" + mediaName + "已存在");
			}
			fs.setMediaUuid(ShortUrlUtils.getByUUID());
			fs.setMediaState(Constants.STATE_VALID);// 删除用该值
			fs.setRunState(Constants.STATE_VALID);
			fs.setCreateUser(fs.getCreateUser());
			fs.setCreateTime(new Date());
			flowSourceMapper.insertSelective(fs);
		}
	}

	/**
	 * media是否存在 不同媒体类型可重名
	 * 
	 * @param mediaName
	 * @param type
	 * @return
	 */
	private boolean checkMediaNameExist(String mediaName, Integer type) {
		FlowSourceExample example = new FlowSourceExample();
		example.createCriteria().andMediaNameEqualTo(mediaName).andMediaTypeEqualTo(type)
				.andMediaStateNotEqualTo(Constants.STATE_INVALID);
		int count = flowSourceMapper.countByExample(example);
		return count > Constants.INTEGER_0;
	}

	@Transactional
	@Override
	public void deFsById(int fsid) {
		FlowSource flowSource = flowSourceMapper.selectByPrimaryKey(fsid);
		flowSource.setMediaState(Constants.STATE_INVALID);
		flowSource.setUpdateTime(new Date());
		String mediaUuid = flowSource.getMediaUuid();
		// 删除流量源须将广告位和app无效
		AdPosition adPosition = new AdPosition();
		adPosition.setFlowUuid(mediaUuid);
		adPositionService.deleteByExample(adPosition);
		if (flowSource.getType() == WebConstants.FLOWSOURCE_ALLIANCE) {
			Apps apps = new Apps();
			apps.setMedias(mediaUuid);
			appService.deleteByExample(apps);
		}
		flowSourceMapper.updateByPrimaryKeySelective(flowSource);
	}

	@Transactional
	@Override
	public void updateFS(FlowSource fs) {
		FlowSource oldFlowSource = flowSourceMapper.selectByPrimaryKey(fs.getId());
		if (oldFlowSource != null) {
			String newMediaName = fs.getMediaName();
			Integer newType = fs.getType();
			if (StringUtils.isNotBlank(newMediaName) && oldFlowSource.getMediaName().equals(newMediaName)) {
				boolean isExist = checkMediaNameExist(newMediaName, newType);
				if (isExist) {
					throw new ServiceException("流量源：" + newMediaName + "已存在");
				}
			}
			fs.setUpdateTime(new Date());
			fs.setMediaState(Constants.STATE_VALID);
			fs.setCreateUser(oldFlowSource.getCreateUser());
			fs.setCreateTime(oldFlowSource.getCreateTime());
			flowSourceMapper.updateByPrimaryKeySelective(fs);
			if (Constants.STATE_INVALID.equals(fs.getMediaState())) {
				// 将广告位置无效
				AdPosition adPosition = new AdPosition();
				adPosition.setStatus(Constants.STATE_INVALID);
				adPosition.setUpdateTime(new Date());
				adPositionService.update(adPosition);
				if (newType == WebConstants.FLOWSOURCE_ALLIANCE) {// 联盟媒体下属app置无效
					Apps apps = new Apps();
					apps.setStatus(Constants.STATE_INVALID);
					apps.setUpdateTime(new Date());
					appService.update(apps);
				}
			}
		}

	}

	@Override
	public FlowSource getFlowSourceByUUID(String mediaUuid) {
		FlowSource flowSource = null;
		FlowSource fs = new FlowSource();
		fs.setMediaUuid(mediaUuid);
		List<FlowSource> flowSources = flowSourceMapper.selectByExample(bulidExample(fs));
		flowSource = flowSources.size() == Constants.INTEGER_1 ? flowSources.get(Constants.INTEGER_0) : null;
		return flowSource;
	}

	/**
	 * 判断该流量管理员是否有管理该流量权限
	 * 
	 * @param fMId
	 * @param fSId
	 * @return
	 */
	@Override
	public boolean checkFlowManngerExist(UserGrand userGrand) {
		/*
		 * UserGrandExample grandExample = new UserGrandExample();
		 * grandExample.createCriteria().andAidEqualTo(userGrand.getAid()).andUidEqualTo
		 * (userGrand.getUid())
		 * .andStatusEqualTo(Constants.STATE_VALID).andTypeEqualTo(WebConstants.
		 * AGENT_OBJECT_TYPE_FLOW); int count =
		 * userGrandMapper.countByExample(grandExample);
		 */
		return true;
	}

	@Override
	public FlowSource getFlowSourceByID(Integer id) {
		FlowSource flowSource = flowSourceMapper.selectByPrimaryKey(id);
		return flowSource;
	}

	@Override
	public List<User> get(String name) {
		if (StringUtils.isBlank(name)) {
			name = StringUtils.EMPTY;
		}
		UserExample example = new UserExample();
		example.createCriteria().andUserNameLike("%" + name + "%")
				.andTypeEqualTo(Constants.USER_TYPE_ADMIN_FLOW).andStatusEqualTo(Constants.STATE_VALID);
		List<User> users = userMapper.selectByExample(example);
		return users;
	}

	@Override
	public List<FlowSource> getAllListByType(Integer type) {
		FlowSourceExample flowSourceExample = new FlowSourceExample();
        FlowSourceExample.Criteria criteria = flowSourceExample.createCriteria();
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        criteria.andMediaStateEqualTo(Constants.STATE_VALID).andRunStateEqualTo(Constants.STATE_VALID);
		return flowSourceMapper.selectByExample(flowSourceExample);
	}

}

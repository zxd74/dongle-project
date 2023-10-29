package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.DeviceModelMapper;
import com.iwanvi.nvwa.dao.model.DeviceModel;
import com.iwanvi.nvwa.dao.model.DeviceModel.Column;
import com.iwanvi.nvwa.dao.model.DeviceModelExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DeviceModelService;
import com.iwanvi.nvwa.web.vo.DeviceModelQueryReq;

@Service
public class DeviceModelServiceImpl implements DeviceModelService {
	@Autowired
	private DeviceModelMapper deviceModelMapper;

	@Override
	public void update(DeviceModel record) {
		deviceModelMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void delete(Integer id) {
		DeviceModel model = DeviceModel.builder().id(id).isDeleted(true).build();
		deviceModelMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public List<DeviceModel> select(String name) {
		DeviceModelExample example = new DeviceModelExample();
		DeviceModelExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andModelNameLike("%" + name + "%");
		}

		return deviceModelMapper.selectByExample(example);
	}

	@Override
	public DeviceModel loadById(Integer id) {
		return deviceModelMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<DeviceModel> selectPage(DeviceModelQueryReq queryReq) {

		DeviceModelExample modelExample = queryReq.toExample();
		int total = (int) deviceModelMapper.countByExample(modelExample);
		List<DeviceModel> dataList = deviceModelMapper.selectByExample(modelExample);

		return Page.create(total, queryReq.getPageSize(), dataList);
	}

	@Override
	public void insert(DeviceModel record) {
		String deviceModelName = record.getModelName();
		if (StringUtils.isBlank(deviceModelName)) {
			return;
		}
		String[] deviceModels = StringUtils.split(deviceModelName, Constants.SIGN_COMMA);
		List<String> deviceModelNames = new ArrayList<>(Arrays.asList(deviceModels));

		List<DeviceModel> deviceModelList = deviceModelMapper
				.selectByExample(DeviceModelExample.newAndCreateCriteria().andModelNameIn(deviceModelNames).example());
		for (DeviceModel deviceModel : deviceModelList) {
			if (deviceModel.getIsDeleted()) {
				deviceModel.setIsDeleted(false);
				deviceModelMapper.updateByPrimaryKeySelective(deviceModel);
			}
			deviceModelNames.remove(deviceModel.getModelName());
		}

		List<DeviceModel> deviceModel = new ArrayList<DeviceModel>();
		for (String model : deviceModelNames) {
			deviceModel.add(DeviceModel.builder().modelName(model).build());
		}
		if (!deviceModel.isEmpty()) {
			deviceModelMapper.batchInsertSelective(deviceModel, Column.modelName);
		}
	}

	@Override
	public boolean deviceModelNameIsExist(String name) {
		DeviceModelExample device = new DeviceModelExample();
		device.createCriteria().andModelNameEqualTo(name);
		long count = deviceModelMapper.countByExample(device);
		return count > 0;
	}
}

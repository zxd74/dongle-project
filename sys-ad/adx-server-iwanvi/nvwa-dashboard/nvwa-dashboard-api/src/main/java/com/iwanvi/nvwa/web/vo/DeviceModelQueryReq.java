package com.iwanvi.nvwa.web.vo;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.DeviceModelExample;
import com.iwanvi.nvwa.dao.model.support.QueryReq;

public class DeviceModelQueryReq extends QueryReq<DeviceModelExample> {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public DeviceModelExample buildExample() {
		DeviceModelExample example = new DeviceModelExample();
		example.page(pageNo, pageSize);
		example.setOrderByClause("id desc");

		DeviceModelExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andModelNameLike("%" + name + "%");
		}
		return example;
	}
}

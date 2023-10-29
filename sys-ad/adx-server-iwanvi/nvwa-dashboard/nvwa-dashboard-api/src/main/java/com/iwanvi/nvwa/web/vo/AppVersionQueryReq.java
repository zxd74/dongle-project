package com.iwanvi.nvwa.web.vo;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.nvwa.dao.model.AppVersionExample;
import com.iwanvi.nvwa.dao.model.support.QueryReq;

/**
 * 
 * @author wangweiping
 *
 */
public class AppVersionQueryReq extends QueryReq<AppVersionExample> {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public AppVersionExample buildExample() {
		AppVersionExample example = new AppVersionExample();
		example.page(pageNo, pageSize);
		example.setOrderByClause("id desc");

		AppVersionExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%" + name + "%");
		}

		return example;
	}

}

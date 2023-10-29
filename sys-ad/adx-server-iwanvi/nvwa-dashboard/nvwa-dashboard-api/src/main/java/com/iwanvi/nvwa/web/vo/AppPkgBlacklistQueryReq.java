package com.iwanvi.nvwa.web.vo;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AppPkgBlacklistExample;
import com.iwanvi.nvwa.dao.model.support.QueryReq;

public class AppPkgBlacklistQueryReq extends QueryReq<AppPkgBlacklistExample> {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public AppPkgBlacklistExample buildExample() {
		AppPkgBlacklistExample example = new AppPkgBlacklistExample();
		example.page(pageNo, pageSize);
		example.setOrderByClause("id desc");

		AppPkgBlacklistExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);

		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%" + name + "%");
			example.or(example.createCriteria().andAppNameLike("%" + name + "%"));
		}
		return example;
	}

}

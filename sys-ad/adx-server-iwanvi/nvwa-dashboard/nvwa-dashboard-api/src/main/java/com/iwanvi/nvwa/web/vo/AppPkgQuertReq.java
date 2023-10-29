package com.iwanvi.nvwa.web.vo;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AppPkgExample;
import com.iwanvi.nvwa.dao.model.support.QueryReq;

public class AppPkgQuertReq extends QueryReq<AppPkgExample> {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public AppPkgExample buildExample() {
		AppPkgExample example = new AppPkgExample();
		example.page(pageNo, pageSize);
		example.setOrderByClause("id desc");

		AppPkgExample.Criteria creiteria = example.createCriteria();
		creiteria.andIsDeletedNotEqualTo(true);

		if (StringUtils.isNotBlank(name)) {
			creiteria.andPkgNameLike("%" + name + "%");
			example.or(example.createCriteria().andAppNameLike("%" + name + "%"));

		}

		return example;
	}

}

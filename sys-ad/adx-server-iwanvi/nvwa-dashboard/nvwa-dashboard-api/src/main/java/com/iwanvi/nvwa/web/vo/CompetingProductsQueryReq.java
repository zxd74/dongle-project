package com.iwanvi.nvwa.web.vo;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.CompetingProductsExample;
import com.iwanvi.nvwa.dao.model.support.QueryReq;

public class CompetingProductsQueryReq extends QueryReq<CompetingProductsExample> {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public CompetingProductsExample buildExample() {
		CompetingProductsExample example = new CompetingProductsExample();
		example.page(pageNo, pageSize);
		example.setOrderByClause("id desc");

		CompetingProductsExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andProductsNameLike("%" + name + "%");
		}
		return example;
	}

}

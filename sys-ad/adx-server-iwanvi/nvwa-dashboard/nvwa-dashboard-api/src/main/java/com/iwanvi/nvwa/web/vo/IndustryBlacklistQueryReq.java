package com.iwanvi.nvwa.web.vo;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.IndustryBlacklistExample;
import com.iwanvi.nvwa.dao.model.support.QueryReq;

public class IndustryBlacklistQueryReq extends QueryReq<IndustryBlacklistExample> {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public IndustryBlacklistExample buildExample() {
		IndustryBlacklistExample example = new IndustryBlacklistExample();
		example.page(pageNo, pageSize);

		if (StringUtils.isNotBlank(name)) {
			example.createCriteria().andIndustryNameLike("%" + name + "%");
		}
		return example;
	}

}

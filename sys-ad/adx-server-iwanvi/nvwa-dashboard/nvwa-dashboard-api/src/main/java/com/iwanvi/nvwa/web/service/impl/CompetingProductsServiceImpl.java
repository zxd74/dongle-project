package com.iwanvi.nvwa.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.CompetingProductsMapper;
import com.iwanvi.nvwa.dao.model.CompetingProducts;
import com.iwanvi.nvwa.dao.model.CompetingProductsExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.CompetingProductsService;
import com.iwanvi.nvwa.web.vo.CompetingProductsQueryReq;

@Service
public class CompetingProductsServiceImpl implements CompetingProductsService {
	@Autowired
	private CompetingProductsMapper competingProductsMapper;

	@Override
	public void insert(CompetingProducts record) {
		CompetingProducts competingProducts = competingProductsMapper.selectOneByExample(CompetingProductsExample
				.newAndCreateCriteria().andProductsNameEqualTo(record.getProductsName()).example());
		if (competingProducts == null) {
			competingProductsMapper.insertSelective(record);
			return;
		}

		if (competingProducts.getIsDeleted()) {
			competingProductsMapper.updateByPrimaryKeySelective(
					CompetingProducts.builder().id(competingProducts.getId()).isDeleted(false).build());
		} else {
			throw new ServiceException("竞品标签名称不能重复");
		}
	}

	@Override
	public void delete(Integer id) {
		CompetingProducts competing = CompetingProducts.builder().id(id).isDeleted(true).build();
		competingProductsMapper.updateByPrimaryKeySelective(competing);
	}

	@Override
	public List<CompetingProducts> selectByExample(String name) {
		CompetingProductsExample cpe = new CompetingProductsExample();
		CompetingProductsExample.Criteria criteria = cpe.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andProductsNameLike("%" + name + "%");
		}

		return competingProductsMapper.selectByExample(cpe);

	}

	@Override
	public Page<CompetingProducts> selectPage(CompetingProductsQueryReq queryReq) {
		CompetingProductsExample example = queryReq.toExample();

		int total = (int) competingProductsMapper.countByExample(example);
		List<CompetingProducts> dataList = competingProductsMapper.selectByExample(example);

		return Page.create(total, queryReq.getPageSize(), dataList);
	}

	@Override
	public boolean CompetingProductsProductsName(String name) {
		CompetingProductsExample competing = new CompetingProductsExample();
		competing.createCriteria().andProductsNameEqualTo(name);
		long count = competingProductsMapper.countByExample(competing);
		return count > 0;
	}

}

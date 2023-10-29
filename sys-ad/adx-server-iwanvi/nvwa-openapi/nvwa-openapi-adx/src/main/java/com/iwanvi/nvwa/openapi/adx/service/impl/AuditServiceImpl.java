package com.iwanvi.nvwa.openapi.adx.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.iwanvi.nvwa.dao.CompetingProductsMapper;
import com.iwanvi.nvwa.dao.DspCreativeMapper;
import com.iwanvi.nvwa.dao.model.CompetingProducts;
import com.iwanvi.nvwa.dao.model.CompetingProductsExample;
import com.iwanvi.nvwa.dao.model.DspCreative;
import com.iwanvi.nvwa.openapi.adx.service.AuditService;

/**
 * class
 *
 * @author hao
 * @date 2019/3/26.
 */
@Service
public class AuditServiceImpl implements AuditService {
	private static final Logger LOG = LoggerFactory.getLogger(AuditService.class);

	private CompetingProductsMapper competingProductsMapper;
	@Autowired
	private DspCreativeMapper dspCreativeMapper;

	@Override
	public void auditEntity(DspCreative creative) {
		// 过滤黑名单
		try {
			Map<String, Object> blackMap = filterFromBlacklists(creative);
			if ((boolean) blackMap.get("isBlack")) {
				creative.setExaminesStatus(2);
				creative.setExaminesRemarks(blackMap.get("remark").toString());
				dspCreativeMapper.updateByPrimaryKeySelective(creative);
				return;
			}
		} catch (Exception ex) {
			LOG.error("黑名单过滤异常", ex);
		}
	}

	/**
	 * 过滤黑名单
	 * 
	 * @return
	 */
	private Map<String, Object> filterFromBlacklists(DspCreative dspCreative) {
		// 竞品黑名单
		List<CompetingProducts> competingList = competingProductsMapper.selectByExample(new CompetingProductsExample());
		Map<String, Object> resultMap = Maps.newHashMap();
		// 过滤竞品
		for (CompetingProducts competingProducts : competingList) {
			if (dspCreative.getThreadTitle() != null
					&& dspCreative.getThreadTitle().indexOf(competingProducts.getProductsName()) > 0) {
				resultMap.put("isBlack", true);
				resultMap.put("remark", "竞品:" + competingProducts.getProductsName());
				return resultMap;
			}
			if (dspCreative.getThreadContent() != null
					&& dspCreative.getThreadContent().indexOf(competingProducts.getProductsName()) > 0) {
				resultMap.put("isBlack", true);
				resultMap.put("remark", "竞品:" + competingProducts.getProductsName());
				return resultMap;
			}
		}
		resultMap.put("isBlack", false);
		return resultMap;
	}
}

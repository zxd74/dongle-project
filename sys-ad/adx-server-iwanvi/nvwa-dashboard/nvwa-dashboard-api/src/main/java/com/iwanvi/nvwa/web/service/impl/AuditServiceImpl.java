package com.iwanvi.nvwa.web.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.web.service.AuditService;
import com.iwanvi.nvwa.web.service.OrdersService;
import com.iwanvi.nvwa.web.util.WangYiDunUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * class
 *
 * @author hao
 * @date 2019/3/26.
 */
@Service
public class AuditServiceImpl implements AuditService {
	@Autowired
	private AppPkgBlacklistMapper appPkgBlacklistMapper;
	@Autowired
	private IndustryBlacklistMapper industryBlacklistMapper;
	@Autowired
	private CompetingProductsMapper competingProductsMapper;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private OrderPutMapper orderPutMapper;
	@Autowired
	private AdxRelationMapper adxRelationMapper;
	@Autowired
	private EntityMapper entityMapper;
	@Autowired
	private EntityDspMapper entityDspMapper;
	@Autowired
	private OrdersService ordersService;

	@Override
	public void auditEntity(Entity entity) throws UnsupportedEncodingException {
		// 过滤黑名单
		Map<String, Object> blackMap = filterFromBlacklists(entity);
		if ((boolean) blackMap.get("isBlack")) {
			entity.setEntityState(Constants.STATE_FAILURE_AUDIT_BLACKLIST);
			entity.setAuditState(Constants.STATE_FAILURE_AUDIT_BLACKLIST);
			entity.setAuditComments(blackMap.get("remark").toString());
			entityMapper.updateByPrimaryKeySelective(entity);
			return;
		}
		// 机审
//        switch (entity.getEntityType()) {
//            case 216:
//                WangYiDunUtils.textCheck(entity);
//                break;
//            case 4:
//                WangYiDunUtils.videoUpload(entity);
//                break;
//            case 5:
//                if (StringUtils.isNotBlank(entity.getEntityUrl())) {
//                    WangYiDunUtils.videoUpload(entity);
//                } else {
//                    WangYiDunUtils.imageCheck(entity);
//                }
//                break;
//            case 7:
//                WangYiDunUtils.textCheck(entity);
//                WangYiDunUtils.imageCheck(entity);
//                break;
//            default:
//                WangYiDunUtils.imageCheck(entity);
//        }
		entity.setEntityState(Constants.STATE_WAIT_AUDIT);
		entity.setAuditComments(StringUtils.EMPTY);
		entityMapper.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 过滤黑名单
	 * 
	 * @return
	 */
	private Map<String, Object> filterFromBlacklists(Entity entity) {
		// 应用黑名单
		List<AppPkgBlacklist> appPkgList = appPkgBlacklistMapper.selectByExample(new AppPkgBlacklistExample());
		// 竞品黑名单
		List<CompetingProducts> competingList = competingProductsMapper.selectByExample(new CompetingProductsExample());
		// 行业黑名单
		List<IndustryBlacklist> industryList = industryBlacklistMapper.selectByExample(new IndustryBlacklistExample());
		Map<String, Object> resultMap = Maps.newHashMap();
		// 过滤竞品
		for (CompetingProducts competingProducts : competingList) {
			if (entity.getThreadTitle() != null
					&& entity.getThreadTitle().indexOf(competingProducts.getProductsName()) > 0) {
				resultMap.put("isBlack", true);
				resultMap.put("remark", "竞品:" + competingProducts.getProductsName());
				return resultMap;
			}
			if (entity.getThreadContent() != null
					&& entity.getThreadContent().indexOf(competingProducts.getProductsName()) > 0) {
				resultMap.put("isBlack", true);
				resultMap.put("remark", "竞品:" + competingProducts.getProductsName());
				return resultMap;
			}
		}
		// 过滤行业
		// 创业所属广告主行业
		int companyIndustry = companyMapper.selectByPrimaryKey(ordersService.getCustIdByPutId(entity.getPid()))
				.getIndustryType();
		String entityIndustry = entity.getIndustry();
		entityIndustry = entityIndustry.indexOf(Constants.SIGN_COMMA) > 0
				? entityIndustry.substring(entityIndustry.lastIndexOf(Constants.SIGN_COMMA) + 1)
				: entityIndustry;
		List<Integer> industrys = Lists.transform(industryList, IndustryBlacklist::getIndustryId);
		if (industrys.contains(Integer.parseInt(entityIndustry))) {
			resultMap.put("isBlack", true);
			resultMap.put("remark", "创意行业黑名单:"
					+ industryList.get(industrys.indexOf(Integer.valueOf(entityIndustry))).getIndustryName());
			return resultMap;
		}
		if (industrys.contains(companyIndustry)) {
			resultMap.put("isBlack", true);
			resultMap.put("remark",
					"广告主行业黑名单:" + industryList.get(industrys.indexOf(companyIndustry)).getIndustryName());
			return resultMap;
		}

		// 过滤应用包名
		String appPkg = orderPutMapper.selectByPrimaryKey(entity.getPid()).getPkgName();
		if (StringUtils.isNotBlank(appPkg)) {
			List<String> appPkgs = Lists.transform(appPkgList, AppPkgBlacklist::getName);
			if (StringUtils.isNotBlank(appPkg) && appPkgs.contains(appPkg)) {
				resultMap.put("isBlack", true);
				resultMap.put("remark", "包名黑名单:" + appPkg);
				return resultMap;
			}
		}
		resultMap.put("isBlack", false);
		return resultMap;
	}

	@Override
	public void checkVideoStatus() throws UnsupportedEncodingException {
		EntityExample entityExample = new EntityExample();
		entityExample.createCriteria().andEntityTypeEqualTo(Constants.AD_TYPE_VIDEO)
				.andEntityStateEqualTo(Constants.STATE_WAIT_AUDIT_MACHINE);
		List<Entity> entityList = entityMapper.selectByExample(entityExample);
		for (Entity entity : entityList) {
			WangYiDunUtils.videoCheck(entity);
		}

	}
}

package com.iwanvi.nvwa.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdxRelationService;
import com.iwanvi.nvwa.web.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "审核广告主接口")
@RequestMapping("auditAdver")
public class AuditAdverController {

	public static final Logger logger = LoggerFactory.getLogger(AuditAdverController.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private AdxRelationService adxRelationService;
	
	@ApiOperation("审核分页列表")
	@RequestMapping("pages")
	public String pages(Company company, @RequestParam(defaultValue = "1") Integer cp,
			@RequestParam(defaultValue = "10") Integer ps) {
		String result;
		try {
			Page<Company> page = companyService.auditPages(company, cp, ps);
			result = JsonUtils.STATUS_OK(page);
			logger.info("auditAdver pages success");
		} catch (Exception e) {
			result = JsonUtils.EXCEPTION_ERROR();
			logger.error("auditAdver pages error",e);
		}
		return result;
	}
	
	@ApiOperation("审核详情页")
	@RequestMapping("auditInfo")
	public String auditInfo(Integer id){
		String result;
		if (id != null) {
			try {
				Company company = companyService.auditInfo(id);
				result = JsonUtils.STATUS_OK(company);
				logger.info("auditAdver auditInfo success");
			} catch (Exception e) {
				result = JsonUtils.EXCEPTION_ERROR();
				logger.error("auditAdver auditInfo error",e);;
			}
		}else {
			result = JsonUtils.PARAMETER_ERROR();
			logger.error("auditAdver auditInfo error beacuse id is null");
		}
		return result;
	}
	
	@ApiOperation("审核操作")
	@RequestMapping("audit")
	public String audit(AdxRelation adxRelation,@RequestAttribute Integer uid){
		String result;
		if (adxRelation.getObjId() != null && adxRelation.getAuditState() != null) {
			try {
				adxRelation.setObjType(Constants.OBJ_ADVERTISER);
				adxRelation.setAdxType(Constants.FLOW_MEDIA_AIKA);
				adxRelation.setStatus(Constants.STATE_VALID);
				adxRelationService.audit(adxRelation,uid);
				result = JsonUtils.STATUS_OK();
				logger.info("auditAdver audit success");
			} catch (Exception e) {
				result = JsonUtils.EXCEPTION_ERROR();
				logger.error("auditAdver audit error",e);
			}
		}else{
			result = JsonUtils.PARAMETER_ERROR();
			logger.error("auditAdver audit error beacuse paramter is errpr, paramStr : {}", JsonUtils.TO_JSON(adxRelation));
		}
		return result;
	}
}

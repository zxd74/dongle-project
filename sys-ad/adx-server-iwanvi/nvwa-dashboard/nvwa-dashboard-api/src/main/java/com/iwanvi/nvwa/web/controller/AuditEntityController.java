package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdxRelationService;
import com.iwanvi.nvwa.web.service.EntityService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.EntityQuery;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "审核创意")
@RequestMapping(value = "auditEntity")
public class AuditEntityController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AuditEntityController.class);

	@Autowired
	private EntityService entityService;
	@Autowired
	private AdxRelationService adxRelationService;

	@ApiOperation("审核创意列表")
	@NvwaRespBody
	@PostMapping("pages")
	public Page<Entity> pages(@RequestBody EntityQuery entityQuery) {
		Page<Entity> page = entityService.auditPage(entityQuery);
		logger.info("auditEntity pages success");
		return page;
	}

	@ApiOperation("审核创意")
	@NvwaRespBody
	@GetMapping("audit")
	public void audit(HttpServletRequest request, AdxRelation adxRelation) {
		if (adxRelation.getObjId() == null || adxRelation.getAuditState() == null) {
			throw new ControllerException("缺少必要参数:objId或者auditState");
		}
		Integer uid = getUserId(request);
		adxRelation.setObjType(Constants.OBJ_ENTITY);
		adxRelation.setAdxType(Constants.FLOW_MEDIA_AIKA);
		adxRelation.setStatus(Constants.STATE_VALID);
		adxRelationService.audit(adxRelation, uid);
		logger.info("auditEntity audit success");
	}

	@ApiOperation("获取创意审核详情页")
	@NvwaRespBody
	@GetMapping("auditInfo")
	public Entity auditInfo(HttpServletRequest request, Integer id) {
		if (id == null) {
			throw new ControllerException("缺少必要参数： id");
		}
		Entity entity = entityService.auditInfo(id);
		return entity;
	}
}

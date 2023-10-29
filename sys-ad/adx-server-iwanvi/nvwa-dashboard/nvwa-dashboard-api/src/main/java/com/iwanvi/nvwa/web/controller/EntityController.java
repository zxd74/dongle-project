package com.iwanvi.nvwa.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.EntityService;
import com.iwanvi.nvwa.web.service.SyslogService;
import com.iwanvi.nvwa.web.service.UserGrandService;
import com.iwanvi.nvwa.web.service.UserService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.util.ObjectResultUtil;
import com.iwanvi.nvwa.web.util.ResponseResult;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.iwanvi.nvwa.web.vo.EntityQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "/entity", tags = "创意接口")
@RequestMapping(value = "entity")
public class EntityController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(EntityController.class);

	@Autowired
	private EntityService entityService;
	@Autowired
	private UserService userService;
	@Autowired
	private SyslogService syslogService;
	@Autowired
	private UserGrandService userGrandService;

	@NvwaRespBody
	@ApiOperation("创意分页")
	@PostMapping("page")
	public Page<Entity> page(HttpServletRequest request, @RequestBody EntityQuery entityQuery) {
		Integer uid = getUserId(request);
		Integer type = getUserType(request);
		Integer custId = null;
		if (uid == null || type == null) {
			throw new ControllerException("session 没有相关数据或许登录超时");
		} else {
			if (entityQuery.getCp() == null) {
				entityQuery.setCp(1);
			} else if (entityQuery.getPs() == null) {
				entityQuery.setPs(10);
			}
			List<Integer> advers = null;
			if (Constants.USER_TYPE_CUST.equals(type)) {
				custId = userService.get(uid).getCompany();
			} else if (!Constants.USER_TYPE_ADMIN.equals(type)) {
				advers = userGrandService.getIdListByUidAndType(uid, WebConstants.AGENT_OBJECT_TYPE_ADVER);
				if (advers != null && advers.size() == 0) {
					throw new ControllerException("无关联广告主");
				}
			}
			Page<Entity> page = entityService.selectPage(entityQuery, custId, advers);
			logger.info("entity list success actualId: {}", uid);
			return page;
		}
	}

	@NvwaRespBody
	@ApiOperation("创意列表")
	@PostMapping("list")
	public List<Entity> list(HttpServletRequest request, @RequestBody EntityQuery entityQuery) {
		Integer loginId = getUserId(request);
		Integer type = getUserType(request);
		if (loginId == null || type == null) {
			throw new ControllerException("session 没有相关数据或许登录超时");
		} else {
			// List<Integer> uids = userService.selectUidsByCreateUser(uid,
			// type);
			Integer adverId = null;
			List<Integer> advers = null;
			if (Constants.USER_TYPE_CUST.equals(type)) {
				adverId = userService.get(loginId).getCompany();
			} else if (!Constants.USER_TYPE_ADMIN.equals(type)) {
				advers = userGrandService.getIdListByUidAndType(loginId, WebConstants.AGENT_OBJECT_TYPE_ADVER);
				if (advers != null && advers.size() == 0) {
					throw new ControllerException("无关联广告主");
				}
			}
			List<Entity> list = entityService.list(entityQuery, adverId, advers);
			logger.info("entity list success, loginId: {} actualId: {}", loginId, loginId);
			return list;
		}
	}

	@NvwaRespBody
	@ApiOperation("创意修改")
	@PostMapping("update")
	public void update(HttpServletRequest request, @RequestBody Entity entity) throws Exception {
		if (entity.getId() == null) {
			throw new ControllerException("缺少必要参数：创意id");
		}
		Integer loginId = getUserId(request);
		Entity oldEntity = entityService.info(entity.getId());
		// 非抄底投放的创意的名字需要判断是否重名
		if (StringUtils.isNotBlank(entity.getEntityName())
				&& !entity.getEntityName().equals(oldEntity.getEntityName())) {
			if (entity.getPutType() == null) {
				throw new ControllerException("缺少必要参数：投放类型");
			}
			boolean isExist = entityService.entityNameIsExist(entity);
			if (isExist) {
				throw new ControllerException("该创意名称已存在");
			}
		}
		// 无法更改创意所属的pid
		entity.setPutAllId(null);
		entity.setPid(null);
		entity.setPid(oldEntity.getPid());
		entity.setCreateUser(oldEntity.getCreateUser());
		entityService.update(entity, oldEntity);
		Entity newEntity = entityService.info(entity.getId());
		syslogService.addSyslog(loginId, getUserType(request), newEntity, oldEntity, this.getClass().getName(),
				"updateEntity");
		logger.info("entity update success, loginId: {}", loginId);
	}

	@NvwaRespBody
	@ApiOperation("删除创意")
	@PostMapping("delete/{id}")
	public void delete(HttpServletRequest request, @PathVariable Integer id) throws Exception {
		if (id == null) {
			throw new ControllerException("缺少id");
		}
		Entity oldEntity = entityService.info(id);
		Entity entity = new Entity();
		entity.setId(id);
		entity.setEntityState(Constants.STATE_INVALID);
		entityService.update(entity, oldEntity);
		Entity newEntity = entityService.info(id);
		syslogService.addSyslog(getUserId(request), getUserType(request), newEntity, oldEntity,
				this.getClass().getName(), "deleteEntity");

	}

	@NvwaRespBody
	@ApiOperation("新增创意")
	@PostMapping("add")
	public void add(@RequestBody Entity entity, HttpServletRequest request) throws Exception {
		if (entity.getPutType() == null) {
			throw new ControllerException("缺少必要参数：投放类型");
		}
		if (Constants.PUT_TYPE_ORDER.equals(entity.getPutType())) {
			if (entity.getPutAllId() == null || entity.getAdPosition() == null) {
				throw new ControllerException("缺少必要参数：订单投放id或广告位id");
			}
			if (StringUtils.isBlank(entity.getIndustry())) {
				throw new ControllerException("缺少必要参数：行业");
			}
		} else if (Constants.PUT_TYPE_BOTTOM.equals(entity.getPutType())) {
			if (entity.getPid() == null) {
				throw new ControllerException("缺少必要参数：抄底投放id");
			}
		}
		// 非抄底投放的创意的名字需要判断是否重名
		if (Constants.PUT_TYPE_ORDER.equals(entity.getPutType())) {
			if (StringUtils.isNotBlank(entity.getEntityName())) {
				boolean isExist = entityService.entityNameIsExist(entity);
				if (isExist) {
					throw new ControllerException("该创意名称已存在");
				}
			} else {
				throw new ControllerException("缺少必要参数：创意名称");
			}
		}
		Integer loginId = getUserId(request);
		entity.setCreateUser(loginId);
		entityService.add(entity);
		syslogService.addSyslog(loginId, getUserType(request), entity, this.getClass().getName(), "addEntity");
	}

	@NvwaRespBody
	@ApiOperation("创意详情")
	@GetMapping("info/{id}")
	public Entity info(HttpServletRequest request, @PathVariable Integer id) {
		if (id == null) {
			throw new ControllerException("缺少id");
		}
		Entity entity = entityService.info(id);
		return entity;
	}

	@ApiOperation("审核创意详情")
	@NvwaRespBody
	@GetMapping("auditInfo")
	public ObjectResultUtil<Entity> auditInfo(HttpServletRequest request, Integer id) {
		ObjectResultUtil<Entity> result;
		if (id != null) {
			try {
				Entity entity = entityService.auditInfo(id);
				logger.info("entity auditInfo success");
				result = ResponseResult.STATUS_OK(entity);
			} catch (Exception e) {
				logger.error("entity auditInfo error", e);
				result = ResponseResult.EXCEPTION_ERROR();
			}
		} else {
			logger.error("entity auditInfo error beacuse id not null");
			result = ResponseResult.PARAMETER_ERROR();
		}
		return result;
	}

	@ApiOperation("创意提交媒体审核")
	@NvwaRespBody
	@PostMapping("auditAdx")
	public <T> ObjectResultUtil<T> auditAdx(HttpServletRequest request, @RequestBody AdxRelation adxRelation) {
		ObjectResultUtil<T> result;
		try {
			entityService.auditAdx(adxRelation);
			logger.info("entity auditAdx success");
			result = ResponseResult.STATUS_OK();
		} catch (Exception e) {
			logger.error("entity auditAdx error", e);
			result = ResponseResult.EXCEPTION_ERROR();
		}
		return result;
	}

	/**
	 * 抄底投放根据投放id获取
	 */
	@NvwaRespBody
	@ApiOperation("根据投放id获取抄底投放创意")
	@GetMapping("getOneByPid/{pid}")
	public Entity getOneByPid(@PathVariable Integer pid) {
		if (pid == null) {
			throw new ControllerException("缺少投放id");
		}
		Entity entity = entityService.getOneByPid(pid);
		return entity;
	}
	
	@NvwaRespBody
	@ApiOperation("检查创意状态")
	@GetMapping("checkEntity/{id}")
	public Map<String, Object> checkEntity(@PathVariable Integer id) {
		if (id == null) {
			throw new ControllerException("缺少必要条件：id");
		}
		Map<String, Object> entityMap = entityService.checkEntity(id);
		return entityMap;
	}
}

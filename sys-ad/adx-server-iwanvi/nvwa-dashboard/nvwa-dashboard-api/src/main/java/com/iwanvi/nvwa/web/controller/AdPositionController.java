package com.iwanvi.nvwa.web.controller;

import com.google.common.collect.Lists;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.*;import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionMapping;
import com.iwanvi.nvwa.dao.model.SysCrontab;
import com.iwanvi.nvwa.dao.model.Template;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelation;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdPositionService;import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.DateLimitService;
import com.iwanvi.nvwa.web.service.PositionMapperingService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.NvwaRespBody;

import io.swagger.annotations.ApiOperation;

import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("adPosition")
public class AdPositionController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(AdPositionController.class);

	@Autowired
	private AdPositionService adPositionService;
	@Autowired
	private PositionMapperingService positionMapperingService;
	@Autowired
	private SysCrontabService sysCrontabService;

	@ResponseBody
	@PostMapping("add")
	public String add(@RequestBody AdPosition adPosition, @RequestAttribute Integer uid) {
		String result = StringUtils.EMPTY;
		try {
			if (adPosition != null) {
				if (StringUtils.isBlank(adPosition.getName().trim())) {
					throw new ControllerException("广告位名称不能为空");
				}
				boolean positionExist = adPositionService.checkPositionExist(adPosition);
				if (positionExist) {
					result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
					logger.info("adPosition name already exist ！, name:{}", adPosition.getName());
				} else {
					adPosition.setUpdateUser(uid);
					adPositionService.add(adPosition);
					result = JsonUtils.STATUS_OK();
				}
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.info("insert adposition error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}


	@ResponseBody
	@PostMapping("update")
	public String update(@RequestBody AdPosition adPosition, @RequestAttribute Integer uid) {
		String result = StringUtils.EMPTY;
		try {
			if (adPosition != null) {
				boolean positionExist = adPositionService.checkPositionExist(adPosition);
				if (positionExist) {
					result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
					logger.info("adPosition name already exist ！, name:{}", adPosition.getName());
				} else {
					adPosition.setUpdateUser(uid);
					adPositionService.update(adPosition);
					result = JsonUtils.STATUS_OK();
				}
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.info("update adposition error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("get")
	public String get(Integer id) {
		String result = StringUtils.EMPTY;
		try {
			if (id != null) {
				AdPosition adPosition = adPositionService.get(id);
				result = JsonUtils.STATUS_OK(adPosition);
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.info("get adposition error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@PostMapping("getList")
	public String get(@RequestBody AdPosition adPosition) {
		String result = StringUtils.EMPTY;
		try {
			if (adPosition != null) {
				Page<AdPosition> page = adPositionService.selectForPage(adPosition);
				result = JsonUtils.STATUS_OK(page);
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.info("getList adPosition error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("getListByUser")
	public String get(AdPosition adPosition, Integer cp, Integer ps, @RequestAttribute Integer uid) {
		String result = StringUtils.EMPTY;
		try {
			if (adPosition != null) {
				Page<AdPosition> page = adPositionService.selectForPageByUser(adPosition, cp, ps, uid);
				result = JsonUtils.STATUS_OK(page);
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.info("getList adPosition error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/getlistbyfsorplatForm")
	public String getList(Integer platForm, String mediaUuids) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isNotBlank(mediaUuids)) {
				List<AdPosition> adps = adPositionService.getCollectionList(platForm, mediaUuids);
				result = JsonUtils.STATUS_OK(adps);
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("get adposition List error!", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}

	@GetMapping("getAiKaPosition")
	public String getAiKaPosition(@RequestParam Integer appId, @RequestParam Integer status) {
		String result = StringUtils.EMPTY;
		try {
			List<AdPosition> list = adPositionService.getAiKaPosition(appId, status);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("get aika adposition List error!", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}

	@RequestMapping("getModules")
	public String getModules(Integer adPositionId) {
		String result = StringUtils.EMPTY;
		try {
			if (adPositionId != null) {
				List<TemplateModuleRelation> moduleRelations = adPositionService
						.getRelationModulesByAdpositionId(adPositionId, null);
				logger.info("adposition getModules success");
				result = JsonUtils.STATUS_OK(moduleRelations);
			} else {
				logger.error("adposition getModules error beacuse adPositionId is null");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("adposition getModules error!", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@ApiOperation("通过模板id获取组件")
	@GetMapping("getModulesByTemplateId")
	public String getModulesByTemplateId(Integer templateId) {
		String result = StringUtils.EMPTY;
		try {
			if (templateId != null) {
				List<TemplateModuleRelation> moduleRelations = adPositionService
						.getRelationModulesByTemplateId(templateId);
				logger.info("adposition getModules success");
				result = JsonUtils.STATUS_OK(moduleRelations);
			} else {
				logger.error("adposition getModules error beacuse templateId is null");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("adposition getModules error!", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}

	@RequestMapping("updateStatus")
	public String updateStatus(AdPosition adPosition, @RequestAttribute Integer uid) {
		String result = StringUtils.EMPTY;
		try {
			if (adPosition != null) {
				adPosition.setUpdateUser(uid);
				adPositionService.mappingSwitch(adPosition);
				result = JsonUtils.STATUS_OK();
			} else {
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.info("update adposition error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@PostMapping("mapping-switch")
	public String mappingSwitch(@RequestBody AdPosition adPosition, @RequestAttribute Integer uid) {
		String result = StringUtils.EMPTY;
		try {
			if (adPosition.getId() == null || adPosition.getMappingSwitch() == null) {
				result = JsonUtils.PARAMETER_ERROR();
			} else {
				adPosition.setUpdateUser(uid);
				adPositionService.mappingSwitch(adPosition);
				result = JsonUtils.STATUS_OK();
			}
		} catch (Exception e) {
			logger.info("operate mapping-switch  error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@NvwaRespBody
	@PostMapping("position-mapping")
	public void positionMapping(@RequestBody AdPosition adPosition, @RequestAttribute Integer uid) {
		if (adPosition.getId() == null) {
			throw new ControllerException("参数错误：缺少id！");
		}
		positionMapperingService.delete(adPosition);
		List<AdPositionMapping> list = adPosition.getMappingList();
		Set<Integer> set = Sets.newHashSet();
		for (AdPositionMapping adPositionMapping : list) {
			if (set.contains(adPositionMapping.getFlowConsumerId())) {
				throw new ServiceException("同一平台只能存在一条数据");
			}
			set.add(adPositionMapping.getFlowConsumerId());
			adPositionMapping.setAdPositionId(adPosition.getId());
			positionMapperingService.insertOrUpdate(adPositionMapping);
		}
		adPositionService.writeToRedis(adPositionService.get(adPosition.getId()));
		sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_APP_POSITION, Constants.OP_UPDATE);
	}

	@NvwaRespBody
	@ApiOperation("广告位库存接口")
	@PostMapping("stock")
	public Map<Integer, Map<String, Object>> stock(@RequestBody Map<String, Object> paramMap) throws IOException {
//		Map<String, Object> paramMap = getParamMap(request);
		List<Map<String, Object>> positionList = Lists.newArrayList();
		if (!paramMap.containsKey("position")) {
			throw new ControllerException("参数错误：缺少广告信息");
		}else {
			positionList = (List<Map<String, Object>>) paramMap.get("position");
		}
		if (!paramMap.containsKey("type")) {
			throw new ControllerException("参数错误：缺少type");
		}
//		if (StringUtils.isBlank(time)) {
//			throw new ControllerException("参数错误：缺少时间");
//		}
		Integer type = (Integer) paramMap.get("type");
		return adPositionService.stock(positionList, type);
	}
	@NvwaRespBody
	@GetMapping("modifiable/{id}")
	public int positionModifiable(@PathVariable("id") Integer id) {
		int result ;
		if (adPositionService.modifiable(id)) {
			result = 0;
		} else {
			result = 1;
		}
		return result;
	}
	@NvwaRespBody
	@GetMapping("listByConsumerAndApp")
	public List<AdPosition> getPositionByConsumer( String consumerIds, String appIds) {
		return adPositionService.listByConsumerAndApp(consumerIds, appIds);
	}

	@NvwaRespBody
	@GetMapping("listByApp/{uuids}")
	public List<AdPosition> getPositionByApp(@PathVariable("uuids") String uuids) {
		return adPositionService.getPositionByApp(uuids);
	}
	@NvwaRespBody
	@ApiOperation("获取信息流广告位的模板list")
	@GetMapping("getTemplatesById/{id}")
	public List<Template> getTemplatesById(@PathVariable("id") Integer positionId){
		if (positionId == null) {
			throw new ControllerException("缺少必要参数：广告位id");
		}
		return adPositionService.getTemplatesById(positionId);
	}
}

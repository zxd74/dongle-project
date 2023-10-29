package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.AreaGroup;
import com.iwanvi.nvwa.web.service.AreaGroupService;
import com.iwanvi.nvwa.web.service.AreaService;
import com.iwanvi.nvwa.web.util.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("areaGroup")
public class AreaGroupController {

    private static final Logger logger = LoggerFactory.getLogger(AreaGroupController.class);

    @Autowired
    private AreaGroupService areaGroupService;
    @Autowired
    private AreaService areaService;

    @ResponseBody
    @RequestMapping("add")
    public String add(AreaGroup areaGroup, @RequestAttribute Integer uid) {
        String result = StringUtils.EMPTY;
        try {
            if (areaGroup != null) {
                boolean groupExist = areaGroupService.checkAreaGroupExist(areaGroup);
                if (groupExist) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
                    logger.info("areaGroup name  already exist ！, name:{}", areaGroup.getGroupName());
                } else {
                    if (StringUtils.isBlank(areaGroup.getAreaIds())) {
                        throw new ControllerException("地域等级必须添加城市!");
                    }
                    areaGroup.setCreateUser(uid);
                    areaGroupService.add(areaGroup);
                    result = JsonUtils.STATUS_OK();
                }
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("add areaGroup error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("update")
    public String update(AreaGroup areaGroup, Integer uid) {
        String result = StringUtils.EMPTY;
        try {
            if (areaGroup != null) {
                areaGroup.setCreateUser(uid);
                areaGroupService.update(areaGroup);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("update areaGroup error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("get")
    public String get(Integer id) {
        String result = StringUtils.EMPTY;
        try {
            if (id != null) {
                AreaGroup areaGroup = areaGroupService.get(id);
                result = JsonUtils.STATUS_OK(areaGroup);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("get areaGroup error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getList")
    public String get(AreaGroup areaGroup) {
        String result = StringUtils.EMPTY;
        try {
            if (areaGroup != null) {
                List<AreaGroup> list = areaGroupService.selectForList(areaGroup);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("get areaGroup list error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getAreaList")
    public String getAreaList(AreaGroup areaGroup) {
        String result = StringUtils.EMPTY;
        try {
            if (areaGroup != null) {
                List<AreaGroup> list = areaGroupService.selectForList(areaGroup);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("get area list error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getOptionalArea")
    public String getOptionalList(Area area) {
        String result = StringUtils.EMPTY;
        try {
            if (area != null) {
                List<Area> list = areaService.selectForList(area);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("get area getOptionalArea error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    
    @RequestMapping("getAreasByNameAndType")
    public String getAreasByNameAndType(Integer findType, String areaName){
    	String result;
    	if (findType != null && StringUtils.isNotBlank(areaName)) {
			try {
				List<Area> areas = areaService.selectListByNameAndType(findType,areaName);
				logger.info("areaGroup getAreasByNameAndType success");
				result = JsonUtils.STATUS_OK(areas);
			} catch (Exception e) {
				logger.error("areaGroup getAreasByNameAndType error",e);
				result = JsonUtils.EXCEPTION_ERROR();
			}
		}else{
			logger.error("areaGroup getAreasByNameAndType error beacuse param is error, type {}, name {}",findType,areaName);
			result = JsonUtils.PARAMETER_ERROR();
		}
    	return result;
    }
    
    @RequestMapping("listByIdStr")
    public String listByIdStr(String idStr) {
        String result = null;
        try {
            if (StringUtils.isNotBlank(idStr)) {
            	List<Integer> ids = StringUtils.str2List4Int(idStr, Constants.SIGN_COMMA);
                List<Area> list = areaService.getDicByIds(ids);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("listByIdStr dictionary error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("delete")
    public String delete(Integer areaGroupId) {
        String result = null;
        try {
            if (areaGroupId != null) {
                areaGroupService.delete(areaGroupId);
                logger.info("delete areaGroup  success ！, areaGroupId:{}", areaGroupId);
                result = JsonUtils.STATUS_OK();
            }
        } catch (Exception e) {
            logger.info("delete areaGroup   error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

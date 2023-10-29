package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.TemplateMapper;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.service.TemplateModuleRelationService;
import com.iwanvi.nvwa.web.service.TemplateService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("template")
public class TemplateController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateService templateService;
    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private TemplateModuleRelationService templateModuleRelationService;
    @Autowired
    private AdPositionMapper adPositionMapper;
    @Autowired
    private SysCrontabService sysCrontabService;
    @Autowired
    private AdPositionService adPositionService;

    @PostMapping("add")
    @ResponseBody
    public String add( @RequestBody Template template, @RequestAttribute Integer uid){
        String result = StringUtils.EMPTY;
        try {
            if (template != null) {
                boolean nameExisted = templateService.checkTemplateExist(template);
                if (nameExisted) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
                    logger.info("template name already exist ！, name:{}", template.getName());
                } else {
                    template.setCreateUser(uid);
                    templateService.add(template);
                    result = JsonUtils.STATUS_OK();
                }
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("insert template error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("updateStatus")
    public String updateStatus(Template template, @RequestAttribute Integer uid) {
        String result = StringUtils.EMPTY;
        try {
            if (template != null) {
                template.setUpdateUser(uid);
                templateService.updateStatus(template);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("update template status error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("update")
    public String update(@RequestBody Template template, @RequestAttribute Integer uid) {
        String result = StringUtils.EMPTY;
        try {
            if (template != null && template.getId() != null) {
                boolean nameExisted = templateService.checkTemplateExist(template);
                if (nameExisted) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
                    logger.info("template name already exist ！, name:{}", template.getName());
                } else {
                    template.setCreateUser(uid);
                    templateService.update(template);
                    //添加TemplateModuleRelation
                    result = JsonUtils.STATUS_OK();
                }
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("update template error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @NvwaRespBody
    @RequestMapping("get")
    public Template get(Integer id) {
            Template template = templateService.get(id);
            return template;
    }

    @ResponseBody
    @RequestMapping("getList")
    public String get(Template template, Integer cp, Integer ps) {
        String result = StringUtils.EMPTY;
        try {
            if (template != null) {
                Page<Template> page = templateService.selectForPage(template,cp,ps);
                result = JsonUtils.STATUS_OK(page);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("getList template error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getTemplate")
    public String getTemplate(String name) {
        String result = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(name)) {
                Template template = new Template();
                template.setMaterialName(name);
                List<Template> list = templateService.selectForList(template);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("getList template error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @NvwaRespBody
    @GetMapping("modifiable/{id}")
    public int positionModifiable(@PathVariable("id") Integer id) {
        int result ;
        if (templateService.modifiable(id)) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    @NvwaRespBody
    @GetMapping("modifiableByPosition")
    public int modifiableForPosition(Integer id, Integer positionId) {
        int result ;
        if (templateService.modifiable(id,positionId)) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    @NvwaRespBody
    @GetMapping("listByPosition/{positionId}")
    public List<Template> getListByPosition(@PathVariable("positionId") Integer positionId) {
        return templateService.listByPosition(positionId);
    }

	 public void test(){}}

package com.iwanvi.nvwa.web.controller;


import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.DmpPersons;
import com.iwanvi.nvwa.dao.model.DmpTags;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpPersonsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("persons")
public class DmpPersonsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DmpPersonsController.class);

    @Autowired
    private DmpPersonsService dmpPersonsService;

    @RequestMapping("list")
    @ResponseBody
    public String list(DmpPersons persons, Integer cp, Integer ps) {
        String result = null;
        try {
            Page<DmpPersons> page = dmpPersonsService.selectForPage(persons, cp, ps);
            result = JsonUtils.STATUS_OK(page);
        } catch (Exception e) {
            logger.error("list persons error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody DmpPersons persons, HttpServletRequest request) {
        String result = null;
        try {
            if (persons != null) {
                Integer uid = getUserId(request);
                persons.setCreateUser(uid);
                persons.setCreateTime(new Date());
                dmpPersonsService.add(persons);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("add persons error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody DmpPersons persons) {
        String result = null;
        try {
            if (persons != null) {
                dmpPersonsService.update(persons);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("update persons error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("getTags")
    @ResponseBody
    public String getTags(Integer id) {
        String result = null;
        try {
            if (id != null) {
                List<DmpTags> tagsList = dmpPersonsService.getTagsByPersons(id);
                result = JsonUtils.STATUS_OK(tagsList);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("get persons tags error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("getTagPercent")
    @ResponseBody
    public String getTagPercent(Integer pid, Integer tid) {
        String result = null;
        try {
            if (pid != null && tid != null) {
                List<Map<String, Object>> tagsList = dmpPersonsService.getPercentByPersonIdAndTagsId(pid, tid);
                result = JsonUtils.STATUS_OK(tagsList);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("get persons tags percent error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

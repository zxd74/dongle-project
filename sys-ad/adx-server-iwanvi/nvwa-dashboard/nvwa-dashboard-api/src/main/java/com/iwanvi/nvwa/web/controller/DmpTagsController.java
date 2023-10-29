package com.iwanvi.nvwa.web.controller;


import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.DmpTag;
import com.iwanvi.nvwa.dao.model.DmpTags;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpTagService;
import com.iwanvi.nvwa.web.service.DmpTagsService;
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

@RestController
@RequestMapping("tags")
public class DmpTagsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DmpTagsController.class);

    @Autowired
    private DmpTagsService dmpTagsService;
    @Autowired
    private DmpTagService dmpTagService;

    @RequestMapping("list")
    @ResponseBody
    public String list(DmpTags tags, Integer cp, Integer ps) {
        String result = null;
        try {
            Page<DmpTags> page = dmpTagsService.selectForPage(tags, cp, ps);
            result = JsonUtils.STATUS_OK(page);
        } catch (Exception e) {
            logger.error("list tags error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("getDxTags")
    @ResponseBody
    public String getDxTags() {
        String result = null;
        try {
            List<DmpTags> list = dmpTagsService.selectListForDx();
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("get dx tags error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody DmpTags tags, HttpServletRequest request) {
        String result = null;
        try {
            if (tags != null) {
                Integer uid = getUserId(request);
                tags.setCreateUser(uid);
                tags.setCreateTime(new Date());
                dmpTagsService.add(tags);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("add tags error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("addTag")
    @ResponseBody
    public String addTag(@RequestBody DmpTag tag, HttpServletRequest request) {
        String result = null;
        try {
            if (tag != null) {
                dmpTagService.add(tag);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("add tag error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody DmpTags tags) {
        String result = null;
        try {
            if (tags != null) {
                dmpTagsService.update(tags);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("update tags error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("updateTag")
    @ResponseBody
    public String updateTag(@RequestBody DmpTag tag) {
        String result = null;
        try {
            if (tag != null) {
                dmpTagService.update(tag);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("update tag error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("syncTags")
    @ResponseBody
    public String syncTags(DmpTags tags) {
        String result = null;
        try {
            if (tags.getId() != null && tags.getIsDx() != null) {
                dmpTagsService.syncTags(tags);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("sync tags error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

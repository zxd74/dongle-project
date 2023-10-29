package com.iwanvi.nvwa.web.controller;


import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.AuthGroup;
import com.iwanvi.nvwa.dao.model.Auths;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AuthGroupService;
import com.iwanvi.nvwa.web.service.AuthsService;
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
@RequestMapping("group")
public class AuthGroupController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AuthGroupController.class);

    @Autowired
    private AuthGroupService authGroupService;
    @Autowired
    private AuthsService authsService;


    @RequestMapping("listForAdd")
    @ResponseBody
    public String listForUserAdd() {
        String result = null;
        try {
            List<AuthGroup> list = authGroupService.getGroupListForAdd();
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("list group for add error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("listAuths")
    @ResponseBody
    public String listAuths() {
        String result = null;
        try {
            List<Auths> list = authsService.selectForList(null);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("list auths error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("list")
    @ResponseBody
    public String list(AuthGroup group, Integer cp, Integer ps) {
        String result = null;
        try {
            Page<AuthGroup> page = authGroupService.selectForPage(group, cp, ps);
            result = JsonUtils.STATUS_OK(page);
        } catch (Exception e) {
            logger.error("list groups error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public String get(Integer id) {
        String result = null;
        try {
            if (id != null) {
                AuthGroup group = authGroupService.get(id);
                result = JsonUtils.STATUS_OK(group);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("get groups error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody AuthGroup group, HttpServletRequest request) {
        String result = null;
        try {
            Integer uid = getUserId(request);
            if (group != null) {
                group.setCreateUser(uid);
                group.setCreateTime(new Date());
                authGroupService.add(group);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("add group error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody AuthGroup group) {
        String result = null;
        try {
            if (group != null) {
                authGroupService.update(group);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("update group error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

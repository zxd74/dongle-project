package com.iwanvi.nvwa.web.controller;


import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody User user, HttpServletRequest request) {
        String result = null;
        try {
            Integer uid = getUserId(request);
            if (user != null) {
                user.setCreateUser(uid);
                user.setCreateTime(new Date());
                userService.add(user);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("add user error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody User user) {
        String result = null;
        try {
            if (user != null) {
                userService.update(user);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("update user error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(Integer id) {
        String result = null;
        try {
            if (id != null) {
                userService.delete(id);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("delete user error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("list")
    @ResponseBody
    public String list(User user, Integer cp, Integer ps, HttpServletRequest request) {
        String result = null;
        try {
            Integer uid = getUserId(request);
            if (uid != null) {
                if (!userService.isAdmin(uid)) {
                    user.setCreateUser(uid);
                }
                Page<User> page = userService.selectForPage(user, cp, ps);
                result = JsonUtils.STATUS_OK(page);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION_NOT_LOGIN);
            }
        } catch (Exception e) {
            logger.error("list user error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public String get(Integer id, HttpServletRequest request) {
        String result = null;
        try {
            if (id != null) {
                User user = userService.get(id);
                result = JsonUtils.STATUS_OK(user);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("get user error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("getAgInfo")
    @ResponseBody
    public String getAgInfo(HttpServletRequest request) {
        String result = null;
        try {
            Integer uid = getUserId(request);
            if (uid != null) {
                Integer agType = userService.getAgType(uid);
                result = JsonUtils.STATUS_OK(agType);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("get user error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("resetPwd")
    @ResponseBody
    public String resetPwd(String oldPwd, String newPwd, HttpServletRequest request) {
        String result = null;
        try {
            if (StringUtils.isNotBlank(oldPwd) && StringUtils.isNotBlank(newPwd)) {
                Integer uid = getUserId(request);
                if (uid != null) {
                    userService.resetPassword(oldPwd, newPwd, uid);
                    result = JsonUtils.STATUS_OK();
                } else {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, Constants.RESPONSE_EXCEPTION_NOT_LOGIN);
                }
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("get user error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

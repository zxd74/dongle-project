package com.iwanvi.nvwa.auth.controller;

import com.iwanvi.nvwa.auth.model.AuthsVo;
import com.iwanvi.nvwa.auth.service.AuthsService;
import com.iwanvi.nvwa.auth.service.UserService;
import com.iwanvi.nvwa.auth.util.WebConstants;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.Auths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("auth")
public class AuthsController {
    private static final Logger logger = LoggerFactory.getLogger(AuthsController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AuthsService authsService;
    @Autowired
    private RedisDao redisDao;

    @RequestMapping("login")
    @ResponseBody
    public String login(String account, String pwd, Integer platform) {
        String result = null;
        try {
            if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd) && platform != null) {
                Map<String, Object> userMap = userService.login(account, pwd, platform);
                result = JsonUtils.STATUS_OK(userMap);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("login error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("logout")
    @ResponseBody
    public String logout(String token) {
        String result = null;
        try {
            if (StringUtils.isNotBlank(token)) {
                redisDao.del(StringUtils.buildString(Constants.TOKEN_KEY, token));
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("logout error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("agent")
    @ResponseBody
    public String agent(String token, Integer uid, Integer platform) {
        String result = null;
        try {
            if (StringUtils.isNotBlank(token) && uid != null && platform != null) {
                String userMap = redisDao.get(token);
                if (StringUtils.isNotBlank(userMap)) {
                    List<AuthsVo> list = authsService.getUserAuths(uid, platform);
                    if (CollectionUtils.isEmpty(list)) {
                        result = JsonUtils.DATA_NOT_FIND();
                    } else {
                        result = JsonUtils.STATUS_OK(list);
                    }
                } else {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION_NOT_LOGIN,"用户未登录");
                }
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("agent error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("getAuth")
    @ResponseBody
    public String getAuth(Integer gid, Integer platform) {
        String result = null;
        try {
            if (gid != null && platform != null) {
                List<AuthsVo> list = authsService.getGroupAuths(gid, platform);
                if (CollectionUtils.isEmpty(list)) {
                    result = JsonUtils.DATA_NOT_FIND();
                } else {
                    result = JsonUtils.STATUS_OK(list);
                }
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("get auth error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("getAllAuth")
    @ResponseBody
    public String getAllAuth(Integer platform) {
        String result = null;
        try {
            if (platform != null) {
                Auths auths = new Auths();
                auths.setPlatform(platform);
                List<AuthsVo> list = authsService.selectForList(auths);
                if (CollectionUtils.isEmpty(list)) {
                    result = JsonUtils.DATA_NOT_FIND();
                } else {
                    result = JsonUtils.STATUS_OK(list);
                }
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("get all auths error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

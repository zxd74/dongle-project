package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.Dictionary;
import com.iwanvi.nvwa.web.service.AdDicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("dic")
public class DictionaryController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private AdDicService adDicService;

    @RequestMapping("list")
    public String list(Integer gid) {
        String result = null;
        try {
            if (gid != null) {
                List<Dictionary> list = adDicService.getDicByGroupId(gid);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION_NOT_LOGIN);
            }
        } catch (Exception e) {
            logger.error("list dictionary error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    
    @RequestMapping("listByIdStr")
    public String listByIdStr(String idStr) {
        String result = null;
        try {
            if (StringUtils.isNotBlank(idStr)) {
            	List<Integer> ids = StringUtils.str2List4Int(idStr, Constants.SIGN_COMMA);
                List<Dictionary> list = adDicService.getDicByIds(ids);
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
    
    @RequestMapping("mapList")
    public String mapList(Integer gid) {
        String result = null;
        try {
            if (gid != null) {
                Map<String, Integer> mapList = adDicService.getDicByGroupIdWithMap(gid);
                result = JsonUtils.STATUS_OK(mapList);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION_NOT_LOGIN);
            }
        } catch (Exception e) {
            logger.error("mapList dictionary error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

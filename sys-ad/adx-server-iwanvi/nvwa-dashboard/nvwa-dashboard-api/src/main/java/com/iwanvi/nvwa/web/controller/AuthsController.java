package com.iwanvi.nvwa.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.HttpClientUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthsController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AuthsController.class);

    @RequestMapping("getAllAuth")
    public String getAllAuths() {
        String result = null;
        try {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("platform",Constants.INTEGER_1.toString());
            String resp = HttpClientUtils.get(WebConstants.AUTH_HOST + WebConstants.AUTH_GETALLAUTH,paramMap);
            Map<String, Object> respMap = JsonUtils.TO_OBJ(resp, Map.class);
            if (Constants.RESPONSE_SUCCESS.equals(respMap.get("code"))) {
                List<Map> list = JSONArray.parseArray(respMap.get("data").toString(),Map.class);
                for (Map map : list) {
                    map.remove("id");
                    map.remove("status");
                    map.remove("pid");
                    map.remove("orders");
                    map.remove("path");
                    map.remove("isMenu");
                    map.remove("platform");
                    List<Map> childList = JSONArray.parseArray(map.get("childs").toString(),Map.class);
                    for (Map childMap : childList) {
                        childMap.remove("id");
                        childMap.remove("status");
                        childMap.remove("pid");
                        childMap.remove("orders");
                        childMap.remove("path");
                        childMap.remove("isMenu");
                        childMap.remove("platform");
                    }
                    map.put("childs",childList);
                }
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.STATUS_FAILED(respMap.get("code").toString(),respMap.get("message").toString());
            }
        } catch (Exception e) {
            logger.error("get all auth error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("getAuth")
    public String getAuth(Integer gid) {
        String result = null;
        try {
            Map<String, String> paramMap = Maps.newHashMap();
            paramMap.put("gid",gid.toString());
            paramMap.put("platform",Constants.INTEGER_1.toString());
            String resp = HttpClientUtils.get(WebConstants.AUTH_HOST + WebConstants.AUTH_GETAUTH,paramMap);
            Map<String, Object> respMap = JsonUtils.TO_OBJ(resp, Map.class);
            if (Constants.RESPONSE_SUCCESS.equals(respMap.get("code"))) {
                List<Map> list = JSONArray.parseArray(respMap.get("data").toString(),Map.class);
                for (Map map : list) {
                    List<Map> childList = JSONArray.parseArray(map.get("childs").toString(),Map.class);
                    for (Map childMap : childList) {
                        childMap.remove("id");
                        childMap.remove("status");
                        childMap.remove("pid");
                        childMap.remove("orders");
                        childMap.remove("path");
                        childMap.remove("isMenu");
                        childMap.remove("platform");
                    }
                    map.put("childs",childList);
                    map.remove("id");
                    map.remove("status");
                    map.remove("pid");
                    map.remove("orders");
                    map.remove("path");
                    map.remove("isMenu");
                    map.remove("platform");
                }
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.STATUS_FAILED(respMap.get("code").toString(),respMap.get("message").toString());
            }
        } catch (Exception e) {
            logger.error("get auth error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

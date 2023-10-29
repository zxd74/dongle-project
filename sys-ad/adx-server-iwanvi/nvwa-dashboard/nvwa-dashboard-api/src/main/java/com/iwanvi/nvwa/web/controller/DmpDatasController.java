package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.DmpDatas;
import com.iwanvi.nvwa.dao.model.DmpDatasource;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpDatasService;
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
@RequestMapping("datas")
public class DmpDatasController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DmpDatasController.class);

    @Autowired
    private DmpDatasService dmpDatasService;

    @RequestMapping("list")
    @ResponseBody
    public String list(DmpDatas datas, Integer cp, Integer ps) {
        String result = null;
        try {
            Page<DmpDatas> page = dmpDatasService.selectForPage(datas, cp, ps);
            result = JsonUtils.STATUS_OK(page);
        } catch (Exception e) {
            logger.error("list datas error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("preview")
    @ResponseBody
    public String preview(Integer sid, Integer signCode, String sign) {
        String result = null;
        try {
            List<List<String>> list = dmpDatasService.preview(sid, signCode, sign);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("preview datas error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody DmpDatas datas, HttpServletRequest request) {
        String result = null;
        try {
            if (datas != null) {
                Integer uid = getUserId(request);
                datas.setCreateUser(uid);
                datas.setCreateTime(new Date());
                dmpDatasService.add(datas);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("add datas error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody DmpDatas datas) {
        String result = null;
        try {
            if (datas != null) {
                dmpDatasService.update(datas);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("update datas error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

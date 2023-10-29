package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.DmpDatasource;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpDatasourceService;
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
@RequestMapping("ds")
public class DmpDatasourceController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DmpDatasourceController.class);

    @Autowired
    private DmpDatasourceService dmpDatasourceService;

    @RequestMapping("list")
    @ResponseBody
    public String list(DmpDatasource datasource, Integer cp, Integer ps) {
        String result = null;
        try {
            Page<DmpDatasource> page = dmpDatasourceService.selectForPage(datasource, cp, ps);
            result = JsonUtils.STATUS_OK(page);
        } catch (Exception e) {
            logger.error("list datasource error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody DmpDatasource datasource, HttpServletRequest request) {
        String result = null;
        try {
            if (datasource != null) {
                Integer uid = getUserId(request);
                datasource.setCreateUser(uid);
                datasource.setCreateTime(new Date());
                dmpDatasourceService.add(datasource);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("add datasource error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody DmpDatasource datasource) {
        String result = null;
        try {
            if (datasource != null) {
                dmpDatasourceService.update(datasource);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("update datasource error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("rejudge")
    @ResponseBody
    public String rejudge(Integer id) {
        String result = null;
        try {
            if (id != null) {
                dmpDatasourceService.rejudge(id);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("rejudge datasource error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

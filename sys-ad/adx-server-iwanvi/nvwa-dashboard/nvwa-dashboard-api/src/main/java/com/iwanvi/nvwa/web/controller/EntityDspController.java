package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.EntityDsp;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.EntityDspService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("entityDsp")
public class EntityDspController {

    private static final Logger logger = LoggerFactory.getLogger(EntityDspController.class);

    @Autowired
    private EntityDspService entityDspService;

    @ResponseBody
    @RequestMapping("update")
    public String update(EntityDsp entityDsp) {
        String result = StringUtils.EMPTY;
        try {
            if (entityDsp == null) {
                result = JsonUtils.PARAMETER_ERROR();
            } else {
                entityDspService.update(entityDsp);
                result = JsonUtils.STATUS_OK();
            }
        } catch (Exception e) {
            logger.info("update entiryDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("examines")
    public String examines(EntityDsp entityDsp, @RequestAttribute Integer uid, Integer cp, Integer ps) {
        String result = StringUtils.EMPTY;
        try {
            if (entityDsp != null) {
                entityDsp.setExaminesTime(new Date());
                entityDsp.setExaminesUser(uid);
                entityDspService.update(entityDsp);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("examines entityDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getList")
    public String getList(@RequestBody EntityDsp entityDsp, Integer cp, Integer ps) {
        String result = StringUtils.EMPTY;
        try {
            if (entityDsp != null) {
                Page<EntityDsp> page = entityDspService.selectForPage(entityDsp,cp,ps);
                result = JsonUtils.STATUS_OK(page);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("getList entityDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("get")
    public String get(Integer id ) {
        String result = StringUtils.EMPTY;
        try {
            if (id != null) {
                EntityDsp entityDsp = entityDspService.get(id);
                result = JsonUtils.STATUS_OK(entityDsp);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("get entityDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}

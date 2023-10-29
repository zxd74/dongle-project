package com.iwanvi.nvwa.web.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.HttpClientUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.SignatureUtils;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.model.Entity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class WangYiDunUtils {

    private static final Logger LOG = LoggerFactory.getLogger(WangYiDunUtils.class);


    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private static EntityMapper staticEntityMapper;

    @PostConstruct
    public void init() {
        staticEntityMapper = entityMapper;
    }

    /**
     * 图片检测
     */
    public static void imageCheck(Entity entity) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        // 1.设置公共参数
        params.put("secretId", Constants.SECRETID);
        params.put("businessId", Constants.BUSINESSID_IMAGE);
        params.put("version", "v3");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt()));
        // 2.设置私有参数
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isNotBlank(entity.getEntityUrl())) {
            JSONObject image1 = new JSONObject();
            image1.put("name", entity.getEntityUrl());
            image1.put("type", 1);
            image1.put("data", entity.getEntityUrl());
            jsonArray.add(image1);
        }
        if (StringUtils.isNotBlank(entity.getThreadPic1())) {
            JSONObject image2 = new JSONObject();
            image2.put("name", entity.getThreadPic1());
            image2.put("type", 1);
            image2.put("data", entity.getThreadPic1());
            jsonArray.add(image2);
        }
        if (StringUtils.isNotBlank(entity.getThreadPic2())) {
            JSONObject image3 = new JSONObject();
            image3.put("name", entity.getThreadPic2());
            image3.put("type", 1);
            image3.put("data", entity.getThreadPic2());
            jsonArray.add(image3);
        }
        if (StringUtils.isNotBlank(entity.getThreadPic3())) {
            JSONObject image4 = new JSONObject();
            image4.put("name", entity.getThreadPic3());
            image4.put("type", 1);
            image4.put("data", entity.getThreadPic3());
            jsonArray.add(image4);
        }
        params.put("images", jsonArray.toString());
        // 3.生成签名信息
        String signature = SignatureUtils.genSignature(Constants.SECRETKEY, params);
        params.put("signature", signature);
        String respData = HttpClientUtils.post(Constants.IMAGE_CHECK_API_URL, params);
        //监测规则
        Map<String, Object> respMap = JsonUtils.parse2Map(respData);
        int code = (int) respMap.get("code");
        //todo 需要中文万维的检测规则
        if (code != 200) {
            entity.setEntityState(Constants.STATE_FAILURE_AUDIT_MACHINE);
            entity.setAuditComments("机审异常！");
            staticEntityMapper.updateByPrimaryKeySelective(entity);
            return;
        }
        List<Map<String, Object>> resultMap = JsonUtils.parse2Generic(respMap.get("result").toString());
        for (Map<String, Object> map : resultMap) {
            List<Map<String, Integer>> imageStatus = JsonUtils.parse2Generic(map.get("labels").toString());
            for (Map<String, Integer> status : imageStatus) {
                if ((int) status.get("level") == 2){
                    entity.setEntityState(Constants.STATE_FAILURE_AUDIT_MACHINE);
                    staticEntityMapper.updateByPrimaryKeySelective(entity);
                    LOG.info("check creative rejected ！ createId:{}",entity.getId());
                    return;
                }
            }
        }
        entity.setAuditState(Constants.STATE_WAIT_AUDIT);
        entity.setAuditComments(StringUtils.EMPTY);
        staticEntityMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 视频上传
     */
    public static void videoUpload(Entity entity) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<>();
        // 1.设置公共参数
        params.put("secretId", Constants.SECRETID);
        params.put("businessId", Constants.BUSINESSID_IMAGE);
        params.put("version", "v3");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt()));
        // 2.设置私有参数
        params.put("url", entity.getEntityUrl());
        params.put("dataId", entity.getId().toString());
        params.put("callback", "");
        params.put("scFrequency", "3");
        // 3.生成签名信息
        String signature = SignatureUtils.genSignature(Constants.SECRETKEY, params);
        params.put("signature", signature);
        String respData = HttpClientUtils.post(Constants.IMAGE_CHECK_API_URL, params);
        Map<String, Object> respMap = JsonUtils.parse2Map(respData);
        int code = (int) respMap.get("code");
        //todo 需要中文万维的检测规则
        if (code != 200) {
            entity.setEntityState(Constants.STATE_FAILURE_AUDIT_MACHINE);
            entity.setAuditComments("机审异常！");
            staticEntityMapper.updateByPrimaryKeySelective(entity);
            return ;
        }
        Map<String, Object> resultMap = JsonUtils.parse2Map(respMap.get("result").toString());
        entity.setAuditState(Constants.STATE_WAIT_AUDIT_MACHINE);
        entity.setAuditComments("机审待审核");
        entity.setTaskId(resultMap.get("taskId").toString());
        staticEntityMapper.updateByPrimaryKeySelective(entity);
        return;
    }

    /**
     * 视频检测
     */
    public static void videoCheck(Entity entity) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        // 1.设置公共参数
        params.put("secretId", Constants.SECRETID);
        params.put("businessId", Constants.BUSINESSID_IMAGE);
        params.put("version", "v1");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt()));
        // 2.设置私有参数
        params.put("taskIds", entity.getTaskId());
        // 3.生成签名信息
        String signature = SignatureUtils.genSignature(Constants.SECRETKEY, params);
        params.put("signature", signature);
        String respData = HttpClientUtils.post(Constants.IMAGE_CHECK_API_URL, params);
        Map<String, Object> respMap = JsonUtils.parse2Map(respData);
        int code = (int) respMap.get("code");
        if (code != 200) {
            entity.setEntityState(Constants.STATE_FAILURE_AUDIT_MACHINE);
            entity.setAuditComments("机审异常！");
            staticEntityMapper.updateByPrimaryKeySelective(entity);
            return;
        }
        List<Map<String, Object>> resultMap = JsonUtils.parse2Generic(respMap.get("result").toString());
        //todo 需要中文万维的检测规则
        for (Map<String, Object> map : resultMap) {
            if ((int) map.get("status") != 0){
                entity.setEntityState(Constants.STATE_FAILURE_AUDIT_MACHINE);
                entity.setAuditComments("机审检测失败！");
                staticEntityMapper.updateByPrimaryKeySelective(entity);
                return;
            }
            if ((int) map.get("level") == 2){
                entity.setEntityState(Constants.STATE_FAILURE_AUDIT_MACHINE);
                staticEntityMapper.updateByPrimaryKeySelective(entity);
                return;
            }
        }
        entity.setEntityState(Constants.STATE_WAIT_AUDIT);
        entity.setAuditComments(StringUtils.EMPTY);
        staticEntityMapper.updateByPrimaryKeySelective(entity);
        return;
    }

    /**
     * 文本检测
     */
    public static void textCheck(Entity entity) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        // 1.设置公共参数
        params.put("secretId", Constants.SECRETID);
        params.put("businessId", Constants.BUSINESSID_IMAGE);
        params.put("version", "v1");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        params.put("nonce", String.valueOf(new Random().nextInt()));
        // 2.设置私有参数
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(entity.getThreadTitle()))
            sb.append(entity.getThreadTitle());
        if (StringUtils.isNotBlank(entity.getThreadContent()))
            sb.append(entity.getThreadContent());
        params.put("dataId", entity.getId().toString());
        params.put("content", sb.toString());
//        params.put("dataType", "1");
//        params.put("ip", "123.115.77.137");
//        params.put("account", "java@163.com");
//        params.put("deviceType", "4");
        params.put("publishTime", String.valueOf(System.currentTimeMillis()));
        // 3.生成签名信息
        String signature = SignatureUtils.genSignature(Constants.SECRETKEY, params);
        params.put("signature", signature);
        String respData = HttpClientUtils.post(Constants.TEXT_CHECK_API_URL, params);
        Map<String, Object> respMap = JsonUtils.parse2Map(respData);
        int code = (int) respMap.get("code");
        //todo 需要中文万维的检测规则
        if (code != 200) {
            entity.setEntityState(Constants.STATE_FAILURE_AUDIT_MACHINE);
            entity.setAuditComments("机审异常！");
            staticEntityMapper.updateByPrimaryKeySelective(entity);
            return;
        }
        Map<String, Object> resultMap = JsonUtils.parse2Map(respMap.get("result").toString());
        if ((int) resultMap.get("action") == 2) {
            entity.setEntityState(Constants.STATE_FAILURE_AUDIT_MACHINE);
            staticEntityMapper.updateByPrimaryKeySelective(entity);
            return;
        }
        entity.setEntityState(Constants.STATE_WAIT_AUDIT);
        entity.setAuditComments(StringUtils.EMPTY);
        staticEntityMapper.updateByPrimaryKeySelective(entity);
        return;
    }
}

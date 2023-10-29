package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.web.service.DmpTaskService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DmpTaskServiceImpl implements DmpTaskService {
    private static final Logger logger = LoggerFactory.getLogger(DmpTaskServiceImpl.class);

    @Autowired
    private DmpDatasourceMapper dmpDatasourceMapper;
    @Autowired
    private DmpJudgeMapper dmpJudgeMapper;
    @Autowired
    private DmpRulesMapper dmpRulesMapper;
    @Autowired
    private DmpPersonRuleMapper dmpPersonRuleMapper;
    @Autowired
    private DmpDatasMapper dmpDatasMapper;
    @Autowired
    private DmpDataDefinitionMapper dmpDataDefinitionMapper;
    @Autowired
    private RedisDao redisDao;
    private Executor taskExecutor;

    @Override
    public void downloadTask() {
        getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        final String message = redisDao.rightPop(WebConstants.DATASOURCE_DOWNLOAD_MQ);
                        if(StringUtils.isNotBlank(message)){
                            logger.info("download task, message:{}",message);
                            getExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    downloadFile(message);
                                }
                            });
                        }else {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        logger.info("-------------------can not connect to redis--------------------");
                    }
                }
            }
        });
    }

    private void downloadFile(String message) {
        String[] args = message.split(Constants.SIGN_UNDERLINE);
        DmpDatasource datasource = dmpDatasourceMapper.selectByPrimaryKey(
                Integer.parseInt(args[1]));
        if (datasource != null) {
            String paths = datasource.getPaths();
            List<String> pathList = StringUtils.str2List(paths,Constants.SIGN_COMMA);
            String fileName;
            String localPath;
            File localFile;
            URL url;
            String fileKey = StringUtils.buildString(WebConstants.DATASOURCE_DOWNLOAD_FILE_KEY,
                    datasource.getId());
            for (String path : pathList) {
                logger.info("path:{}",path);
                if (StringUtils.isNotBlank(path)) {
                    fileName = path.substring(path.lastIndexOf(Constants.SIGN_OBLIQUELINE));
                    localPath = StringUtils.concat(WebConstants.DATASOURCE_DOWNLOAD_PATH,
                            datasource.getId(),Constants.SIGN_OBLIQUELINE);
                    localFile = new File(localPath + fileName);
                    if (!localFile.exists()) {
                        logger.info(" file {} no exist.",localFile.getPath());
                        File perentDic = new File(localPath);
                        if (!perentDic.exists()) {
                            logger.info(" dic {} no exist.",localPath);
                            perentDic.mkdirs();
                        }
                        try {
                            url = new URL(path);
                            URLConnection con = url.openConnection();
                            InputStream inStream = con.getInputStream();
                            FileOutputStream fos = new FileOutputStream(localFile);
                            byte[] buffer = new byte[1204];
                            int byteread = 0;
                            while ((byteread = inStream.read(buffer)) != -1) {
                                fos.write(buffer, 0, byteread);
                            }

                            fos.close();
                            String files = redisDao.get(fileKey);
                            if (StringUtils.isNotBlank(files)) {
                                redisDao.set(fileKey, files + Constants.SIGN_COMMA + path);
                            } else {
                                redisDao.set(fileKey, path);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            String allFiles = redisDao.get(fileKey);
            if (StringUtils.isNotBlank(allFiles)) {
                List<String> allPathList = StringUtils.str2List(allFiles, Constants.SIGN_COMMA);
                if (allPathList.size() == pathList.size()) {
                    String fileDoneKey = StringUtils.buildString(WebConstants.DATASOURCE_DOWNLOAD_FILE_OK_KEY,
                            datasource.getId());
                    redisDao.set(fileDoneKey, DateUtils.format(new Date()));
                }
            }
        }
    }

    @Override
    public void judgeTask() {
        getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        final String message = redisDao.rightPop(WebConstants.RULE_JUDGE_MQ);
                        if(StringUtils.isNotBlank(message)){
                            logger.info("judge task, message:{}",message);
                            getExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    String[] args = message.split(Constants.SIGN_UNDERLINE);
                                    DmpJudge judge = dmpJudgeMapper.selectByPrimaryKey(Integer.parseInt(args[1]));
                                    if (judge != null) {
                                        DmpDatas datas = dmpDatasMapper.selectByPrimaryKey(judge.getDid());
                                        DmpRulesExample rulesExample = new DmpRulesExample();
                                        rulesExample.createCriteria().andJidEqualTo(judge.getId());
                                        List<DmpRules> rulesList = dmpRulesMapper.selectByExampleWithBLOBs(rulesExample);
                                        if (datas != null && !CollectionUtils.isEmpty(rulesList)) {
                                            DmpDatasource datasource = dmpDatasourceMapper.selectByPrimaryKey(
                                                    datas.getSid());
                                            if (datasource != null && StringUtils.isNotBlank(datasource.getPaths())) {
                                                Map<Integer, List<DmpRules>> tagMap = Maps.newHashMap();
                                                for (DmpRules rule : rulesList) {
                                                    List<DmpRules> tagRuleList = tagMap.get(rule.getTid());
                                                    if (CollectionUtils.isEmpty(tagRuleList)) {
                                                        tagRuleList = Lists.newArrayList();
                                                        tagRuleList.add(rule);
                                                        tagMap.put(rule.getTid(),tagRuleList);
                                                    } else {
                                                        tagRuleList.add(rule);
                                                    }
                                                }
                                                judgeRules(datasource.getPaths(), datas, tagMap, judge.getId());
                                            } else {
                                                logger.info("path of datasource {} {} is empty", datasource.getId(),
                                                        datasource.getName());
                                            }
                                        }
                                        logger.info("judge complete...");
                                    }
                                }
                            });
                        }else {
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        logger.info("-------------------can not connect to redis--------------------");
                    }
                }
            }
        });
    }

    private void judgeRules(String paths, DmpDatas datas, Map<Integer, List<DmpRules>> tagMap, Integer jid) {
        List<String> pathList = StringUtils.str2List(paths, Constants.SIGN_COMMA);
        if (!CollectionUtils.isEmpty(pathList)) {
            List<List<DmpRules>> tagRulesList = Lists.newArrayList(tagMap.values());
            DmpDataDefinitionExample example = new DmpDataDefinitionExample();
            example.createCriteria().andDidEqualTo(datas.getId());
            List<DmpDataDefinition> definitions = dmpDataDefinitionMapper.selectByExample(example);
            Integer keyCol = 0;
            Map<Integer, Integer> colTypeMap = Maps.newHashMap();
            Map<Integer, Integer> colNumMap = Maps.newHashMap();
            for (DmpDataDefinition def : definitions) {
                if (Constants.STATE_VALID == def.getIsKey()) {
                    keyCol = def.getCol();
                }
                colTypeMap.put(def.getId(), def.getColType());
                colNumMap.put(def.getId(), def.getCol());
            }
            for (String path : pathList) {
                String judgeFileKey = StringUtils.buildString(WebConstants.DATASOUCE_FILE_JUDGED, datas.getSid(), jid);
                String oldPath = redisDao.get(judgeFileKey);
                if (StringUtils.isNotBlank(oldPath) && oldPath.contains(path)) {
                    logger.info("judge:{}, datas:{}, path:{} has already judged",jid, datas.getId(), path);
                    continue;
                }
                String fileName = path.substring(path.lastIndexOf(Constants.SIGN_OBLIQUELINE));
                String localpath = StringUtils.concat(WebConstants.DATASOURCE_DOWNLOAD_PATH, datas.getSid(),
                        Constants.SIGN_OBLIQUELINE,fileName);
                File localFile = new File(localpath);
                if (localFile.exists()) {
                    try {
                        FileReader reader = new FileReader(localFile);
                        BufferedReader br = new BufferedReader(reader);
                        String line;
                        String signStr = WebConstants.getSign(datas.getDelId());
                        if (StringUtils.isBlank(signStr)) {
                            signStr = datas.getDelimiter();
                        }
                        if (WebConstants.isContainsSpecialChar(signStr)) {
                            signStr = "\\" + signStr;
                        }
                        Map<String, Map<Integer, Double>> personRuleMap = Maps.newHashMap();
                        Map<Integer, Double> ruleMap;
                        while ((line = br.readLine()) != null) {
                            List<String> dataArray = StringUtils.str2List(line, signStr);
                            String key = dataArray.get(keyCol);
                            ruleMap = personRuleMap.get(key);
                            if (ruleMap == null) {
                                ruleMap = Maps.newHashMap();
                                personRuleMap.put(key,ruleMap);
                            }
                            for (List<DmpRules> ruleList : tagRulesList) {
                                boolean canAdd = false;
                                DmpRules dmpRules = ruleList.get(Constants.INTEGER_0);
                                Integer tid = ruleList.get(Constants.INTEGER_0).getTid();
                                Double score = ruleMap.get(tid);
                                if (score == null) {
                                    score = 0d;
                                }
                                if (dmpRules.getRelation() != null
                                        && WebConstants.DMP_RULE_RELATION_AND == dmpRules.getRelation()) {
                                    int canAddCount = 0;
                                    for (DmpRules rule : ruleList) {
                                        String colStr = dataArray.get(colNumMap.get(rule.getRid()));
                                        Integer type = colTypeMap.get(rule.getRid());
                                        String args = rule.getArgs();
                                        List<String> argList = StringUtils.str2List(args,"\n");
                                        for (String arg : argList) {
                                            if (StringUtils.isNotBlank(arg)) {
                                                if (WebConstants.DMP_RULE_OPERATE_EQ == rule.getOperation()) {
                                                    if (arg.equals(colStr)) {
                                                        canAddCount++;
                                                        break;
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_NOTEQ == rule.getOperation()) {
                                                    canAddCount++;
                                                    if (arg.equals(colStr)) {
                                                        canAddCount--;
                                                        break;
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_GT == rule.getOperation()) {
                                                    if (WebConstants.COLUMN_DATATYPE_NUM == type) {
                                                        if (Double.parseDouble(colStr) > Double.parseDouble(arg)) {
                                                            canAddCount++;
                                                            break;
                                                        }
                                                    } else if (WebConstants.COLUMN_DATATYPE_DATE == type) {
                                                        if (new Date(colStr).compareTo(new Date(arg)) > 0) {
                                                            canAddCount++;
                                                            break;
                                                        }
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_GTEQ == rule.getOperation()) {
                                                    if (WebConstants.COLUMN_DATATYPE_NUM == type) {
                                                        if (Double.parseDouble(colStr) >= Double.parseDouble(arg)) {
                                                            canAddCount++;
                                                            break;
                                                        }
                                                    } else if (WebConstants.COLUMN_DATATYPE_DATE == type) {
                                                        if (new Date(colStr).compareTo(new Date(arg)) >= 0) {
                                                            canAddCount++;
                                                            break;
                                                        }
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_LT == rule.getOperation()) {
                                                    if (WebConstants.COLUMN_DATATYPE_NUM == type) {
                                                        if (Double.parseDouble(colStr) < Double.parseDouble(arg)) {
                                                            canAddCount++;
                                                            break;
                                                        }
                                                    } else if (WebConstants.COLUMN_DATATYPE_DATE == type) {
                                                        if (new Date(colStr).compareTo(new Date(arg)) < 0) {
                                                            canAddCount++;
                                                            break;
                                                        }
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_LTEQ == rule.getOperation()) {
                                                    if (WebConstants.COLUMN_DATATYPE_NUM == type) {
                                                        if (Double.parseDouble(colStr) <= Double.parseDouble(arg)) {
                                                            canAddCount++;
                                                            break;
                                                        }
                                                    } else if (WebConstants.COLUMN_DATATYPE_DATE == type) {
                                                        if (new Date(colStr).compareTo(new Date(arg)) <= 0) {
                                                            canAddCount++;
                                                            break;
                                                        }
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_CONT == rule.getOperation()) {
                                                    Pattern pattern = Pattern.compile(arg);
                                                    Matcher matcher = pattern.matcher(colStr);
                                                    if (matcher.find()) {
                                                        canAddCount++;
                                                        break;
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_NOTCONT == rule.getOperation()) {
                                                    canAddCount++;
                                                    Pattern pattern = Pattern.compile(arg);
                                                    Matcher matcher = pattern.matcher(colStr);
                                                    if (matcher.find()) {
                                                        canAddCount--;
                                                        break;
                                                    }
                                                }
                                            } else {
                                                logger.info("args is empty {}", jid);
                                            }
                                        }
                                    }
                                    if (canAddCount == ruleList.size()) {
                                        score += dmpRules.getScore();
                                        ruleMap.put(tid, score);
                                    }
                                } else {
                                    for (DmpRules rule : ruleList) {
                                        String colStr = dataArray.get(colNumMap.get(rule.getRid()));
                                        Integer type = colTypeMap.get(rule.getRid());
                                        String args = rule.getArgs();
                                        List<String> argList = StringUtils.str2List(args,"\n");
                                        for (String arg : argList) {
                                            if (StringUtils.isNotBlank(arg)) {
                                                if (WebConstants.DMP_RULE_OPERATE_EQ == rule.getOperation()) {
                                                    if (arg.equals(colStr)) {
                                                        canAdd = true;
                                                        break;
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_NOTEQ == rule.getOperation()) {
                                                    canAdd = true;
                                                    if (arg.equals(colStr)) {
                                                        canAdd = false;
                                                        break;
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_GT == rule.getOperation()) {
                                                    if (WebConstants.COLUMN_DATATYPE_NUM == type) {
                                                        if (Double.parseDouble(colStr) > Double.parseDouble(arg)) {
                                                            canAdd = true;
                                                            break;
                                                        }
                                                    } else if (WebConstants.COLUMN_DATATYPE_DATE == type) {
                                                        if (new Date(colStr).compareTo(new Date(arg)) > 0) {
                                                            canAdd = true;
                                                            break;
                                                        }
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_GTEQ == rule.getOperation()) {
                                                    if (WebConstants.COLUMN_DATATYPE_NUM == type) {
                                                        if (Double.parseDouble(colStr) >= Double.parseDouble(arg)) {
                                                            canAdd = true;
                                                            break;
                                                        }
                                                    } else if (WebConstants.COLUMN_DATATYPE_DATE == type) {
                                                        if (new Date(colStr).compareTo(new Date(arg)) >= 0) {
                                                            canAdd = true;
                                                            break;
                                                        }
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_LT == rule.getOperation()) {
                                                    if (WebConstants.COLUMN_DATATYPE_NUM == type) {
                                                        if (Double.parseDouble(colStr) < Double.parseDouble(arg)) {
                                                            canAdd = true;
                                                            break;
                                                        }
                                                    } else if (WebConstants.COLUMN_DATATYPE_DATE == type) {
                                                        if (new Date(colStr).compareTo(new Date(arg)) < 0) {
                                                            canAdd = true;
                                                            break;
                                                        }
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_LTEQ == rule.getOperation()) {
                                                    if (WebConstants.COLUMN_DATATYPE_NUM == type) {
                                                        if (Double.parseDouble(colStr) <= Double.parseDouble(arg)) {
                                                            canAdd = true;
                                                            break;
                                                        }
                                                    } else if (WebConstants.COLUMN_DATATYPE_DATE == type) {
                                                        if (new Date(colStr).compareTo(new Date(arg)) <= 0) {
                                                            canAdd = true;
                                                            break;
                                                        }
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_CONT == rule.getOperation()) {
                                                    Pattern pattern = Pattern.compile(arg);
                                                    Matcher matcher = pattern.matcher(colStr);
                                                    if (matcher.find()) {
                                                        canAdd = true;
                                                        break;
                                                    }
                                                } else if (WebConstants.DMP_RULE_OPERATE_NOTCONT == rule.getOperation()) {
                                                    canAdd = true;
                                                    Pattern pattern = Pattern.compile(arg);
                                                    Matcher matcher = pattern.matcher(colStr);
                                                    if (matcher.find()) {
                                                        canAdd = false;
                                                        break;
                                                    }
                                                }
                                            } else {
                                                logger.info("args is empty {}", jid);
                                            }
                                        }
                                        if (canAdd) {
                                            score += rule.getScore();
                                            ruleMap.put(tid, score);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        savePersonRuleMap(personRuleMap,tagMap,datas.getId());
                        br.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    redisDao.set(judgeFileKey, StringUtils.concat(oldPath, Constants.SIGN_COMMA, path));
                } else {
                    logger.info("file is not exist:{}", localpath);
                }
            }
        }
    }

    private void savePersonRuleMap(Map<String, Map<Integer, Double>> personRuleMap, Map<Integer, List<DmpRules>> tagMap,
                                   Integer did) {
        if (personRuleMap != null) {
            DmpPersonRule personRule;
            Map<Integer, Double> ruleMap;
            for (String key : personRuleMap.keySet()) {
                ruleMap = personRuleMap.get(key);
                if (ruleMap != null) {
                    for (Integer tid : ruleMap.keySet()) {
                        DmpPersonRuleExample example = new DmpPersonRuleExample();
                        example.createCriteria().andPkeyEqualTo(key).andTidEqualTo(tid).andDidEqualTo(did);
                        List<DmpPersonRule> personRuleList = dmpPersonRuleMapper.selectByExample(example);
                        if (CollectionUtils.isEmpty(personRuleList)) {
                            personRule = new DmpPersonRule();
                            personRule.setPkey(key);
                            personRule.setTid(tid);
                            personRule.setScore(ruleMap.get(tid));
                        } else {
                            personRule = personRuleList.get(Constants.INTEGER_0);
                            personRule.setScore(personRule.getScore() + ruleMap.get(tid));
                        }
                        List<DmpRules> rulesList = tagMap.get(tid);
                        Double scoreL = rulesList.get(Constants.INTEGER_0).getSumScL();
                        Double scoreB = rulesList.get(Constants.INTEGER_0).getSumScB();
                        if (scoreB != null && scoreL != null) {
                            if (personRule.getScore() > scoreL && scoreB > personRule.getScore()) {
                                personRule.setUseTag(Constants.STATE_VALID);
                            }
                        } else if (scoreB != null) {
                            if (scoreB > personRule.getScore()) {
                                personRule.setUseTag(Constants.STATE_VALID);
                            }
                        } else if (scoreL != null) {
                            if (personRule.getScore() > scoreL) {
                                personRule.setUseTag(Constants.STATE_VALID);
                            }
                        }
                        personRule.setDid(did);
                        if (personRule.getId() == null) {
                            dmpPersonRuleMapper.insertSelective(personRule);
                        } else {
                            dmpPersonRuleMapper.updateByPrimaryKeySelective(personRule);
                        }
                        if (Constants.STATE_VALID == personRule.getUseTag()) {
                            String pkey = StringUtils.buildString(WebConstants.DMP_KEY, personRule.getPkey());
                            String oldTag = redisDao.get(pkey);
                            if (StringUtils.isNotBlank(oldTag)) {
                                String newTag = oldTag + Constants.SIGN_COMMA + personRule.getTid();
                                redisDao.set(pkey, newTag);
                                logger.info("cache dmp person,{}:{}", pkey, newTag);
                            } else {
                                redisDao.set(pkey, Integer.toString(personRule.getTid()));
                                logger.info("cache dmp person,{}:{}", pkey, personRule.getTid());
                            }
                        }
                    }
                }
            }
        }
    }

    public Executor getExecutor(){
        if(taskExecutor == null){
            taskExecutor = Executors.newCachedThreadPool();
        }
        return taskExecutor;
    }
    public Executor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}

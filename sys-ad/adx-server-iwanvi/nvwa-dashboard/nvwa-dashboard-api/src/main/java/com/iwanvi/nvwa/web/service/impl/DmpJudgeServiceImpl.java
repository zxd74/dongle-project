package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpJudgeService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class DmpJudgeServiceImpl implements DmpJudgeService {

    @Autowired
    private DmpJudgeMapper dmpJudgeMapper;
    @Autowired
    private DmpRulesMapper dmpRulesMapper;
    @Autowired
    private DmpTagsMapper dmpTagsMapper;
    @Autowired
    private DmpTagMapper dmpTagMapper;
    @Autowired
    private DmpDatasMapper dmpDatasMapper;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private DmpPersonRuleMapper dmpPersonRuleMapper;
    @Autowired
    private DmpDataDefinitionMapper dmpDataDefinitionMapper;

    @Transactional
    @Override
    public void add(DmpJudge judge) {
        if (judge != null) {
            DmpDatas datas = dmpDatasMapper.selectByPrimaryKey(judge.getDid());
            String fileKey = StringUtils.buildString(WebConstants.DATASOURCE_DOWNLOAD_FILE_OK_KEY, datas.getSid());
            String fileDownDate = redisDao.get(fileKey);
            if (StringUtils.isBlank(fileDownDate)) {
                throw new ServiceException("数据集依赖的文件还未下载完成，不能使用该数据集");
            }
            judge.setStatus(Constants.STATE_VALID);
            judge.setCreateTime(new Date());
            dmpJudgeMapper.insertSelective(judge);
            dmpJudgeMapper.countByExample(null);
            for (DmpRules rule : judge.getRulesList()) {
                rule.setJid(judge.getId());
                dmpRulesMapper.insertSelective(rule);
            }
            redisDao.leftPush(WebConstants.RULE_JUDGE_MQ,WebConstants.RULE_JUDGE_MQ_KEY + judge.getId());
        }
    }

    @Override
    public void update(DmpJudge judge) {
        if (judge != null && judge.getId() != null) {
            dmpJudgeMapper.updateByPrimaryKeySelective(judge);
        }
    }

    @Override
    public void delete(Integer id) {
        if (id != null) {
            dmpJudgeMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public Page<DmpJudge> selectForPage(DmpJudge dmpJudge, Integer cp, Integer ps) {
        Page<DmpJudge> page;
        DmpJudgeExample example = bindingExample(dmpJudge);
        int count = dmpJudgeMapper.countByExample(example);
        List<DmpJudge> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<DmpJudge>(count, cp, ps);
        } else {
            page = new Page<DmpJudge>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = dmpJudgeMapper.selectByExample(example);
        fillList(list);
        page.bindData(list);
        return page;
    }

    @Override
    public DmpJudge get(Integer id) {
        if (id != null) {
            DmpJudge judge = dmpJudgeMapper.selectByPrimaryKey(id);
            DmpRulesExample example = new DmpRulesExample();
            example.createCriteria().andJidEqualTo(id);
            List<DmpRules> rules = dmpRulesMapper.selectByExample(example);
            judge.setRulesList(rules);
            return judge;
        }
        return null;
    }

    @Override
    public void dropJudge(Integer id) {
        if (id != null) {
            DmpJudge judge = get(id);
            if (judge != null) {
                Integer did = judge.getDid();
                List<DmpRules> rules = judge.getRulesList();
                List<Integer> ruleIdList = Lists.transform(rules, DmpRules::getTid);
                if (did != null && !CollectionUtils.isEmpty(ruleIdList)) {
                    DmpPersonRuleExample example = new DmpPersonRuleExample();
                    example.createCriteria().andDidEqualTo(did).andTidIn(ruleIdList);
                    dmpPersonRuleMapper.deleteByExample(example);
                }
            }
            judge.setStatus(Constants.STATE_INVALID);
            dmpJudgeMapper.updateByPrimaryKeySelective(judge);
        }
    }

    private void fillList(List<DmpJudge> list) {
        if (!CollectionUtils.isEmpty(list)) {
            DmpRulesExample example;
            DmpTagExample dmpTagExample;
            DmpTags tags;
            DmpDatas datas;
            for (DmpJudge judge : list) {
                tags = dmpTagsMapper.selectByPrimaryKey(judge.getTid());
                judge.setTagsName(tags.getName());

                example = new DmpRulesExample();
                example.createCriteria().andJidEqualTo(judge.getId());
                List<DmpRules> rules = dmpRulesMapper.selectByExample(example);
                List<Integer> ruleTags = Lists.transform(rules, DmpRules::getTid);
                fillRuleList(rules);

                dmpTagExample = new DmpTagExample();
                dmpTagExample.createCriteria().andIdIn(ruleTags);
                List<DmpTag> tagList = dmpTagMapper.selectByExample(dmpTagExample);
                List<String> tagNames = Lists.transform(tagList, DmpTag::getName);
                judge.setJudgeRules(StringUtils.list2str(tagNames));

                datas = dmpDatasMapper.selectByPrimaryKey(judge.getDid());
                judge.setdName(datas.getName());
            }
        }
    }

    private void fillRuleList(List<DmpRules> rules) {
        if (!CollectionUtils.isEmpty(rules)) {
            DmpTag tag;
            DmpDataDefinition definition;
            for (DmpRules rule : rules) {
                tag = dmpTagMapper.selectByPrimaryKey(rule.getTid());
                if (tag != null) {
                    rule.settName(tag.getName());
                }
                definition = dmpDataDefinitionMapper.selectByPrimaryKey(rule.getRid());
                if (definition != null) {
                    rule.setrName(definition.getColName());
                }
            }
        }
    }

    private DmpJudgeExample bindingExample(DmpJudge judge) {
        DmpJudgeExample example = new DmpJudgeExample();
        DmpJudgeExample.Criteria criteria = example.createCriteria();
        if (judge != null) {
            if (judge.getTid() != null) {
                criteria.andTidEqualTo(judge.getTid());
            }
            if (judge.getDid() != null) {
                criteria.andDidEqualTo(judge.getDid());
            }
            if (judge.getStatus() != null) {
                criteria.andStatusEqualTo(judge.getStatus());
            } else {
                criteria.andStatusEqualTo(Constants.STATE_VALID);
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }
}

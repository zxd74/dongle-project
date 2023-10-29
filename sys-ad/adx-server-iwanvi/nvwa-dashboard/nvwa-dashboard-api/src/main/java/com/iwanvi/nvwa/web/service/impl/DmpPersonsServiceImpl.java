package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpPersonsService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DmpPersonsServiceImpl implements DmpPersonsService {

    @Autowired
    private DmpPersonsMapper dmpPersonsMapper;
    @Autowired
    private DmpTagMapper dmpTagMapper;
    @Autowired
    private DmpDatasMapper dmpDatasMapper;
    @Autowired
    private DmpPersonRuleMapper dmpPersonRuleMapper;
    @Autowired
    private DmpTagsMapper dmpTagsMapper;
    @Override
    public void add(DmpPersons dmpPersons) {
        if (dmpPersons != null) {
            dmpPersonsMapper.insertSelective(dmpPersons);
        }
    }

    @Override
    public void update(DmpPersons dmpPersons) {
        if (dmpPersons != null && dmpPersons.getId() != null) {
            dmpPersonsMapper.updateByPrimaryKeySelective(dmpPersons);
        }
    }

    @Override
    public DmpPersons get(Integer id) {
        DmpPersons dmpPersons = dmpPersonsMapper.selectByPrimaryKey(id);
        if (StringUtils.isNotBlank(dmpPersons.getTags())) {
            List<Integer> tagList = StringUtils.str2List4Int(dmpPersons.getTags(),Constants.SIGN_COMMA);
            DmpTagExample example = new DmpTagExample();
            example.createCriteria().andIdIn(tagList);
            List<DmpTag> tags = dmpTagMapper.selectByExample(example);
            dmpPersons.setTagList(tags);
        }
        if (StringUtils.isNotBlank(dmpPersons.getDid())) {
            List<Integer> dataList = StringUtils.str2List4Int(dmpPersons.getDid(),Constants.SIGN_COMMA);
            DmpDatasExample example = new DmpDatasExample();
            example.createCriteria().andIdIn(dataList);
            List<DmpDatas> datas = dmpDatasMapper.selectByExample(example);
            dmpPersons.setDatasList(datas);
        }
        return dmpPersons;
    }

    @Override
    public Page<DmpPersons> selectForPage(DmpPersons dmpPersons, Integer cp, Integer ps) {
        Page<DmpPersons> page;
        DmpPersonsExample example = bindingExample(dmpPersons);
        int count = dmpPersonsMapper.countByExample(example);
        List<DmpPersons> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<DmpPersons>(count, cp, ps);
        } else {
            page = new Page<DmpPersons>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = dmpPersonsMapper.selectByExampleWithBLOBs(example);
        fillList(list);
        page.bindData(list);
        return page;
    }

    @Override
    public List<DmpTags> getTagsByPersons(Integer id) {
        if (id != null) {
            DmpPersons dmpPersons = get(id);
            List<DmpTag> tagList = dmpPersons.getTagList();
            Set<Integer> tagsIdSet = FluentIterable.from(tagList).transform(DmpTag::getTid).toSet();
            if (!CollectionUtils.isEmpty(tagsIdSet)) {
                DmpTagsExample example = new DmpTagsExample();
                example.createCriteria().andIdIn(((ImmutableSet<Integer>) tagsIdSet).asList());
                List<DmpTags> tagsList = dmpTagsMapper.selectByExample(example);
                return tagsList;
            }
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getPercentByPersonIdAndTagsId(Integer personId, Integer id) {
        if (id != null && personId != null) {
            DmpPersons dmpPersons = get(personId);
            List<Integer> didList = StringUtils.str2List4Int(dmpPersons.getDid(),Constants.SIGN_COMMA);
            List<DmpTag> tagList = dmpPersons.getTagList();
            List<Integer> tagIds = Lists.newArrayList();
            for (DmpTag tag : tagList) {
                if (id == tag.getTid()) {
                    tagIds.add(tag.getId());
                }
            }
            DmpPersonRuleExample example = new DmpPersonRuleExample();
            example.createCriteria().andDidIn(didList).andTidIn(tagIds);
            int sum = dmpPersonRuleMapper.countByExample(example);
            List<Map<String,Object>> tidMapList = dmpPersonRuleMapper.countByDidsAndTids(didList,tagIds);
            for (Map<String, Object> tidMap : tidMapList) {
                tidMap.put("percent", Double.parseDouble(tidMap.get("num").toString())/sum);
            }
            return tidMapList;
        }
        return null;
    }

    private void fillList(List<DmpPersons> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> dataIds;
            List<Integer> tagIds;
            DmpTagExample tagExample;
            DmpDatasExample datasExample;
            List<DmpTag> tagList;
            List<DmpDatas> datasList;
            for (DmpPersons person : list) {
                dataIds = StringUtils.str2List4Int(person.getDid(),Constants.SIGN_COMMA);
                tagIds = StringUtils.str2List4Int(person.getTags(),Constants.SIGN_COMMA);
                Integer count = 0;
                List<Map<String,Object>> keyMapList;
                if (WebConstants.DMP_RULE_RELATION_AND == person.getRelation()) {
                    keyMapList = dmpPersonRuleMapper.countPersonByDidsAndTidsJoin(dataIds,tagIds);
                    Iterator<Map<String,Object>> it = keyMapList.iterator();
                    while (it.hasNext()) {
                        Map<String,Object> item = it.next();
                        Long num = Long.parseLong(item.get("num").toString());
                        if (num < tagIds.size()) {
                            it.remove();
                        }
                    }
                    count = keyMapList.size();
                } else if (WebConstants.DMP_RULE_RELATION_OR == person.getRelation()) {
                    count = dmpPersonRuleMapper.countPersonByDidsAndTidsUnion(dataIds,tagIds);
                }
                person.setNum(count);
                tagExample = new DmpTagExample();
                tagExample.createCriteria().andIdIn(tagIds);
                tagList = dmpTagMapper.selectByExample(tagExample);
                person.setTagList(tagList);
                datasExample = new DmpDatasExample();
                datasExample.createCriteria().andIdIn(dataIds);
                datasList = dmpDatasMapper.selectByExample(datasExample);
                person.setDatasList(datasList);
            }
        }
    }

    private DmpPersonsExample bindingExample(DmpPersons dmpPersons) {
        DmpPersonsExample example = new DmpPersonsExample();
        DmpPersonsExample.Criteria criteria = example.createCriteria();
        if (dmpPersons != null) {
            if (StringUtils.isNotBlank(dmpPersons.getName())) {
                criteria.andNameLike("%" + dmpPersons.getName() + "%");
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }
}

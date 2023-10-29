package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.DmpTagMapper;
import com.iwanvi.nvwa.dao.DmpTagsMapper;
import com.iwanvi.nvwa.dao.model.DmpTag;
import com.iwanvi.nvwa.dao.model.DmpTagExample;
import com.iwanvi.nvwa.dao.model.DmpTags;
import com.iwanvi.nvwa.dao.model.DmpTagsExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpTagService;
import com.iwanvi.nvwa.web.service.DmpTagsService;
import com.iwanvi.nvwa.web.service.PutService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class DmpTagsServiceImpl implements DmpTagsService {

    @Autowired
    private DmpTagsMapper dmpTagsMapper;
    @Autowired
    private DmpTagService dmpTagService;
    @Autowired
    private DmpTagMapper dmpTagMapper;
    @Autowired
    private PutService putService;
    @Transactional
    @Override
    public void add(DmpTags tags) {
        if (tags != null && StringUtils.isNotBlank(tags.getName())) {
            if (!checkNameInDb(tags.getName())) {
                tags.setStatus(Constants.STATE_VALID);
                dmpTagsMapper.insertSelective(tags);
                dmpTagsMapper.countByExample(null);
                if (!CollectionUtils.isEmpty(tags.getTagList())) {
                    for (DmpTag tag : tags.getTagList()) {
                        tag.setTid(tags.getId());
                        tag.setStatus(Constants.STATE_VALID);
                        dmpTagService.add(tag);
                    }
                }
            } else {
                throw new ServiceException("标签组" + tags.getName() + "已存在");
            }
        }
    }

    @Transactional
    @Override
    public void update(DmpTags tags) {
        if (tags != null && tags.getId() != null) {
            if (StringUtils.isNotBlank(tags.getName())) {
                DmpTagsExample example = new DmpTagsExample();
                example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andNameEqualTo(tags.getName());
                List<DmpTags> tagsList = dmpTagsMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(tagsList)) {
                    DmpTags tagsDb =  tagsList.get(Constants.INTEGER_0);
                    if (tagsDb.getId() != tags.getId()) {
                        throw new ServiceException("已存在重名标签组");
                    }
                }
            }
            tags.setUpdateTime(new Date());
            dmpTagsMapper.updateByPrimaryKeySelective(tags);
            if (!CollectionUtils.isEmpty(tags.getTagList())) {
                for (DmpTag tag : tags.getTagList()) {
                    tag.setTid(tags.getId());
                    if (tag.getId() == null) {
                        tag.setStatus(Constants.STATE_VALID);
                        dmpTagService.add(tag);
                    }
                }
            }
        }
    }

    @Override
    public void syncTags(DmpTags tags) {
        if (tags.getId() != null && tags.getIsDx() != null) {
            if (Constants.STATE_VALID == tags.getIsDx()) {
                List<Integer> usedTagIdList = dmpTagsMapper.selcetTagIdUsedByTagsId(tags.getId());
                if (CollectionUtils.isEmpty(usedTagIdList)) {
                    throw new ServiceException("标签组内的标签还未使用过，不能用于定向");
                }
            } else {
                List<DmpTag> tagList = dmpTagService.selectForList(tags.getId());
                List<Integer> tagIdList = Lists.transform(tagList, DmpTag::getId);
                //取消标签组定向时，通知投放修改定向设置
                putService.deletePutCustomDx(tagIdList);
            }
            dmpTagsMapper.updateByPrimaryKeySelective(tags);
        }
    }

    @Override
    public Page<DmpTags> selectForPage(DmpTags dmpTags, Integer cp, Integer ps) {
        Page<DmpTags> page;
        DmpTagsExample example = bindingExample(dmpTags);
        int count = dmpTagsMapper.countByExample(example);
        List<DmpTags> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<DmpTags>(count, cp, ps);
        } else {
            page = new Page<DmpTags>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = dmpTagsMapper.selectByExample(example);
        fillList(list);
        page.bindData(list);
        return page;
    }

    @Override
    public List<DmpTags> selectListForDx() {
        DmpTagsExample example = new DmpTagsExample();
        example.createCriteria().andIsDxEqualTo(Constants.STATE_VALID).andStatusEqualTo(Constants.STATE_VALID);
        List<DmpTags> tagsList = dmpTagsMapper.selectByExample(example);
        Iterator<DmpTags> tags = tagsList.iterator();
        List<DmpTag> tagList;
        while (tags.hasNext()) {
            DmpTags dmpTags = tags.next();
            List<Integer> usedTagIdList = dmpTagsMapper.selcetTagIdUsedByTagsId(dmpTags.getId());
            if (CollectionUtils.isEmpty(usedTagIdList)) {
                tags.remove();
            } else {
                DmpTagExample tagExample = new DmpTagExample();
                tagExample.createCriteria().andIdIn(usedTagIdList);
                tagList = dmpTagMapper.selectByExample(tagExample);
                dmpTags.setTagList(tagList);
            }
        }
        return tagsList;
    }

    private boolean checkNameInDb(String name) {
        boolean isExisted = false;
        if (StringUtils.isNotBlank(name)) {
            DmpTagsExample example = new DmpTagsExample();
            example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andNameEqualTo(name);
            int count = dmpTagsMapper.countByExample(example);
            if (count > 0) {
                isExisted = true;
            }
        }
        return isExisted;
    }

    private void fillList(List<DmpTags> list) {
        if (!CollectionUtils.isEmpty(list)) {
            for (DmpTags tags : list) {
                List<DmpTag> tagList = dmpTagService.selectForList(tags.getId());
                tags.setTagList(tagList);
                List<String> tagNameList = Lists.transform(tagList, DmpTag::getName);
                tags.setTags(StringUtils.list2str(tagNameList));
            }
        }
    }

    private DmpTagsExample bindingExample(DmpTags tags) {
        DmpTagsExample example = new DmpTagsExample();
        if (tags != null) {
            DmpTagsExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(tags.getName())) {
                criteria.andNameLike("%" + tags.getName() + "%");
            }
            if (tags.getStatus() != null) {
                criteria.andStatusEqualTo(tags.getStatus());
            }
            if (tags.getIsDx() != null) {
                criteria.andIsDxEqualTo(tags.getIsDx());
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }
}

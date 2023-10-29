package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.TemplateModuleRelationMapper;
import com.iwanvi.nvwa.dao.model.Template;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelation;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelationExample;
import com.iwanvi.nvwa.web.service.TemplateModuleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
public class TemplateModuleRelationServiceImpl implements TemplateModuleRelationService {

    @Autowired
    private TemplateModuleRelationMapper templateModuleRelationMapper;

    @Override
    @Transactional()
    public void add(TemplateModuleRelation templateModuleRelation) {
        if (templateModuleRelation != null) {
            templateModuleRelationMapper.insertSelective(templateModuleRelation);
        }
    }

    @Override
    @Transactional
    public void update(TemplateModuleRelation templateModuleRelation) {
        if (templateModuleRelation != null) {
            templateModuleRelationMapper.updateByPrimaryKeySelective(templateModuleRelation);
        }
    }

    @Override
    @Transactional
    public void delete(Template template) {
        if (template.getId() != null) {
            TemplateModuleRelation templateModuleRelation = new TemplateModuleRelation();
            templateModuleRelation.setUpdateTime(new Date());
            templateModuleRelation.setUpdateUser(template.getUpdateUser());
            templateModuleRelation.setStatus(Constants.STATE_INVALID);

            TemplateModuleRelationExample example = new TemplateModuleRelationExample();
            example.createCriteria().andTIdEqualTo(template.getId());
            List<TemplateModuleRelation> list = templateModuleRelationMapper.selectByExample(example);
            if (list.size() > 0) {
                templateModuleRelationMapper.updateByExampleSelective(templateModuleRelation, example);
            }
        }
    }

    @Override
    public List<TemplateModuleRelation> get(Integer id) {
        if (id != null) {
            TemplateModuleRelationExample example = new TemplateModuleRelationExample();
            example.createCriteria().andTIdEqualTo(id).andStatusEqualTo(Constants.STATE_VALID);
            return templateModuleRelationMapper.selectByExample(example);
        }
        return null;
    }

    @Override
    public List<TemplateModuleRelation> getListByTemplateId(Integer id) {
        TemplateModuleRelationExample example = new TemplateModuleRelationExample();
        if (id != null) {
            example.createCriteria().andTIdEqualTo(id);
        }
        return templateModuleRelationMapper.selectByExample(example);
    }

}

package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Template;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelation;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;

public interface TemplateModuleRelationService {

    void add(TemplateModuleRelation templateModuleRelation);

    void update(TemplateModuleRelation templateModuleRelation);

    void delete(Template template);

    List<TemplateModuleRelation> get(Integer id);

    List<TemplateModuleRelation> getListByTemplateId(Integer id);

}

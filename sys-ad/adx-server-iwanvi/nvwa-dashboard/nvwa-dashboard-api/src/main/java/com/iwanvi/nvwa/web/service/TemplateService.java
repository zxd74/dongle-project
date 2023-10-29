package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Template;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.rmi.ServerException;
import java.util.List;

public interface TemplateService {

    void add(Template template);

    void update(Template template);

    void delete(Integer id, Integer userId);

    Template get(Integer id);

    List<Template> selectForList(Template template);

    Page<Template> selectForPage(Template template, Integer cp, Integer ps);

    boolean checkTemplateExist(Template template);

    void updateStatus(Template template) throws ServerException;

    boolean modifiable(Integer id);

    List<Template> listByPosition(Integer positionId);

    boolean modifiable(Integer id, Integer positionId);
}

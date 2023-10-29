package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.DmpPersons;
import com.iwanvi.nvwa.dao.model.DmpTags;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;
import java.util.Map;

public interface DmpPersonsService {

    void add(DmpPersons dmpPersons);

    void update(DmpPersons dmpPersons);

    DmpPersons get(Integer id);

    Page<DmpPersons> selectForPage(DmpPersons dmpPersons, Integer cp, Integer ps);

    List<DmpTags> getTagsByPersons(Integer id);

    List<Map<String, Object>> getPercentByPersonIdAndTagsId(Integer personId, Integer id);
}

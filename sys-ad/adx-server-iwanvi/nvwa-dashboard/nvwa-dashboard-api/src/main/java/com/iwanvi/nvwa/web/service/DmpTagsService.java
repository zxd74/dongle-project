package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.DmpTags;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;

public interface DmpTagsService {
    void add(DmpTags tags);

    void update(DmpTags tags);

    void syncTags(DmpTags tags);

    Page<DmpTags> selectForPage(DmpTags dmpTags, Integer cp, Integer ps);

    List<DmpTags> selectListForDx();
}

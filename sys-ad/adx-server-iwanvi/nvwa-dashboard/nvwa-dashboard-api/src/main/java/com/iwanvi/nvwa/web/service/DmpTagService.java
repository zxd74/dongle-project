package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.DmpTag;

import java.util.List;

public interface DmpTagService {

    void add(DmpTag dmpTag);

    void update(DmpTag dmpTag);

    List<DmpTag> selectForList(Integer tid);
}

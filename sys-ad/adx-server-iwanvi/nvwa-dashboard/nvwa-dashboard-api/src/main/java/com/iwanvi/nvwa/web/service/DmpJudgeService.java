package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.DmpJudge;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface DmpJudgeService {

    void add(DmpJudge judge);

    void update(DmpJudge judge);

    void delete(Integer id);

    Page<DmpJudge> selectForPage(DmpJudge dmpJudge, Integer cp, Integer ps);

    DmpJudge get(Integer id);

    void dropJudge(Integer id);
}

package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.EntityDsp;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface EntityDspService {

    void update(EntityDsp entityDsp);

    Page<EntityDsp> selectForPage(EntityDsp entityDsp, Integer cp, Integer ps);

    EntityDsp get(Integer id);

    void checkEntityOutOfDate();
}

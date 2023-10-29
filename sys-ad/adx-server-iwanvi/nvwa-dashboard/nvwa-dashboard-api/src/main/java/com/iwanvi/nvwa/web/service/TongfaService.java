package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Tongfa;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface TongfaService {

    void add(Tongfa tongfa);

    void update(Tongfa tongfa);

    Tongfa get(Integer id);

    Page<Tongfa> selectForPage(Tongfa tongfa, Integer cp, Integer ps);

}

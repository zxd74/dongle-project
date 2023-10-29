package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.DmpDatasource;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface DmpDatasourceService {

    void add(DmpDatasource datasource);

    void update(DmpDatasource datasource);

    Page<DmpDatasource> selectForPage(DmpDatasource datasource, Integer cp, Integer ps);

    void rejudge(Integer id);
}

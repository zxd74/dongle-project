package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.DmpDatas;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;

public interface DmpDatasService {

    void add(DmpDatas dmpDatas);

    void update(DmpDatas dmpDatas);

    Page<DmpDatas> selectForPage(DmpDatas dmpDatas, Integer cp, Integer ps);

    List<List<String>> preview(Integer sid, Integer signCode, String sign);
}

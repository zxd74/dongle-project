package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.AdvertiserDsp;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.support.Page;


public interface AdvertiserDspService {

    void add(AdvertiserDsp advertiserDsp);

    void update(AdvertiserDsp advertiserDsp);

    AdvertiserDsp get(Integer id);

    Page<AdvertiserDsp> selectForPage(AdvertiserDsp advertiserDsp, Integer cp, Integer ps);

    boolean checkNameExist(AdvertiserDsp advertiserDsp);
}

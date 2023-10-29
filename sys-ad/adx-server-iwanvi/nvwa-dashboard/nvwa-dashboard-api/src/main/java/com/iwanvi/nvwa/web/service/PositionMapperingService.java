package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionMapping;

import java.util.List;

public interface PositionMapperingService {

    void insertOrUpdate(AdPositionMapping adPositionMapping);

    void delete(Integer id, Integer uid);

    AdPositionMapping get(Integer id);

    List<AdPositionMapping> selectForList(AdPositionMapping adPositionMapping);

    List<AdPositionMapping> selectForPage(AdPositionMapping adPositionMapping);

    void delete(AdPosition adPosition);
}

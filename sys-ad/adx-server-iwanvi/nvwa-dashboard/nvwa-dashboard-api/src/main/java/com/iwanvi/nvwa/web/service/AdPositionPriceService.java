package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionPrice;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;

public interface AdPositionPriceService {
    void add(AdPositionPrice adPositionPrice);

    void update(AdPositionPrice adPositionPrice);

    void delete(Integer id, Integer userId);

    void deleteByPosition(AdPosition adPosition);

    AdPositionPrice get(Integer id);

    List<AdPositionPrice> selectForList(AdPositionPrice adPositionPrice);

    Page<AdPositionPrice> selectForPage(AdPositionPrice adPositionPrice, Integer cp, Integer ps);

    boolean checkPriceExist(AdPositionPrice adPositionPrice);

    void addIndustry(String industryName);

    public boolean checkIndustryExist(String industryName);

    List<AdPositionPrice> getPositionByPrice();

    void deleteIndustry(Integer id);
}

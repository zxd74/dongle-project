package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.AreaGroup;

import java.util.List;

/**
 * class
 *
 * @author hao
 * @date 2018/10/16.
 */
public interface AreaGroupService {
    void add(AreaGroup areaGroup);

    void update(AreaGroup areaGroup);

    void delete(Integer id);

    AreaGroup get(Integer id);

    List<AreaGroup> selectForList(AreaGroup areaGroup);

    List<Area> selectAreaList(String areaName);

    public boolean checkAreaGroupExist(AreaGroup areaGroup);


}

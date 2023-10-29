package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.PlanExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PlanMapper {
    int countByExample(PlanExample example);

    int deleteByExample(PlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Plan record);

    int insertSelective(Plan record);

    List<Plan> selectByExample(PlanExample example);

    Plan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Plan record, @Param("example") PlanExample example);

    int updateByExample(@Param("record") Plan record, @Param("example") PlanExample example);

    int updateByPrimaryKeySelective(Plan record);

    int updateByPrimaryKey(Plan record);

	int countByMap(Map<String, Object> paramMap);

	Map<String, String> selectOtherById(@Param("id") Integer id);
}
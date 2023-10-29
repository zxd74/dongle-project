package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpPersonRule;
import com.iwanvi.nvwa.dao.model.DmpPersonRuleExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DmpPersonRuleMapper {
    int countByExample(DmpPersonRuleExample example);

    int deleteByExample(DmpPersonRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpPersonRule record);

    int insertSelective(DmpPersonRule record);

    List<DmpPersonRule> selectByExample(DmpPersonRuleExample example);

    DmpPersonRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpPersonRule record, @Param("example") DmpPersonRuleExample example);

    int updateByExample(@Param("record") DmpPersonRule record, @Param("example") DmpPersonRuleExample example);

    int updateByPrimaryKeySelective(DmpPersonRule record);

    int updateByPrimaryKey(DmpPersonRule record);

    List<Map<String,Object>> countPersonByDidsAndTidsJoin(@Param("dataList") List<Integer> didList,
                                                    @Param("tagList") List<Integer> tidList);
    int countPersonByDidsAndTidsUnion(@Param("dataList") List<Integer> didList, @Param("tagList") List<Integer> tidList);

    List<Map<String,Object>> countByDidsAndTids(@Param("dataList") List<Integer> didList,
                                              @Param("tagList") List<Integer> tidList);

    List<Map<String, String>> selectAllPersons();
}
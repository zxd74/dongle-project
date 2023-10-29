package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpRules;
import com.iwanvi.nvwa.dao.model.DmpRulesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmpRulesMapper {
    int countByExample(DmpRulesExample example);

    int deleteByExample(DmpRulesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpRules record);

    int insertSelective(DmpRules record);

    List<DmpRules> selectByExampleWithBLOBs(DmpRulesExample example);

    List<DmpRules> selectByExample(DmpRulesExample example);

    DmpRules selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpRules record, @Param("example") DmpRulesExample example);

    int updateByExampleWithBLOBs(@Param("record") DmpRules record, @Param("example") DmpRulesExample example);

    int updateByExample(@Param("record") DmpRules record, @Param("example") DmpRulesExample example);

    int updateByPrimaryKeySelective(DmpRules record);

    int updateByPrimaryKeyWithBLOBs(DmpRules record);

    int updateByPrimaryKey(DmpRules record);
}
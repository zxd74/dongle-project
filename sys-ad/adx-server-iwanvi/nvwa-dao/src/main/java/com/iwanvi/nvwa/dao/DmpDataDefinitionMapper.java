package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpDataDefinition;
import com.iwanvi.nvwa.dao.model.DmpDataDefinitionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmpDataDefinitionMapper {
    int countByExample(DmpDataDefinitionExample example);

    int deleteByExample(DmpDataDefinitionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpDataDefinition record);

    int insertSelective(DmpDataDefinition record);

    List<DmpDataDefinition> selectByExample(DmpDataDefinitionExample example);

    DmpDataDefinition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpDataDefinition record, @Param("example") DmpDataDefinitionExample example);

    int updateByExample(@Param("record") DmpDataDefinition record, @Param("example") DmpDataDefinitionExample example);

    int updateByPrimaryKeySelective(DmpDataDefinition record);

    int updateByPrimaryKey(DmpDataDefinition record);
}
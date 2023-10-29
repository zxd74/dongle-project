package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpPersons;
import com.iwanvi.nvwa.dao.model.DmpPersonsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmpPersonsMapper {
    int countByExample(DmpPersonsExample example);

    int deleteByExample(DmpPersonsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpPersons record);

    int insertSelective(DmpPersons record);

    List<DmpPersons> selectByExampleWithBLOBs(DmpPersonsExample example);

    List<DmpPersons> selectByExample(DmpPersonsExample example);

    DmpPersons selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpPersons record, @Param("example") DmpPersonsExample example);

    int updateByExampleWithBLOBs(@Param("record") DmpPersons record, @Param("example") DmpPersonsExample example);

    int updateByExample(@Param("record") DmpPersons record, @Param("example") DmpPersonsExample example);

    int updateByPrimaryKeySelective(DmpPersons record);

    int updateByPrimaryKeyWithBLOBs(DmpPersons record);

    int updateByPrimaryKey(DmpPersons record);
}
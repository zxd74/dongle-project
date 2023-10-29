package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdPositionMapper {
    int countByExample(AdPositionExample example);

    int deleteByExample(AdPositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdPosition record);

    int insertSelective(AdPosition record);

    List<AdPosition> selectByExample(AdPositionExample example);

    AdPosition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdPosition record, @Param("example") AdPositionExample example);

    int updateByExample(@Param("record") AdPosition record, @Param("example") AdPositionExample example);

    int updateByPrimaryKeySelective(AdPosition record);

    int updateByPrimaryKey(AdPosition record);
}
package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.PositionExclude;
import com.iwanvi.nvwa.dao.model.PositionExcludeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PositionExcludeMapper {
    int countByExample(PositionExcludeExample example);

    int deleteByExample(PositionExcludeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PositionExclude record);

    int insertSelective(PositionExclude record);

    List<PositionExclude> selectByExample(PositionExcludeExample example);

    PositionExclude selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PositionExclude record, @Param("example") PositionExcludeExample example);

    int updateByExample(@Param("record") PositionExclude record, @Param("example") PositionExcludeExample example);

    int updateByPrimaryKeySelective(PositionExclude record);

    int updateByPrimaryKey(PositionExclude record);
}
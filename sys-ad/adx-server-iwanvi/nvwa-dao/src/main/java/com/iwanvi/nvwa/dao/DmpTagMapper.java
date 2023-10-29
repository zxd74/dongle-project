package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpTag;
import com.iwanvi.nvwa.dao.model.DmpTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmpTagMapper {
    int countByExample(DmpTagExample example);

    int deleteByExample(DmpTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpTag record);

    int insertSelective(DmpTag record);

    List<DmpTag> selectByExample(DmpTagExample example);

    DmpTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpTag record, @Param("example") DmpTagExample example);

    int updateByExample(@Param("record") DmpTag record, @Param("example") DmpTagExample example);

    int updateByPrimaryKeySelective(DmpTag record);

    int updateByPrimaryKey(DmpTag record);
}
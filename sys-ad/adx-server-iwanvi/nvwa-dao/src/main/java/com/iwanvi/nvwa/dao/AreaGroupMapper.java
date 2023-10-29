package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AreaGroup;
import com.iwanvi.nvwa.dao.model.AreaGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AreaGroupMapper {
    int countByExample(AreaGroupExample example);

    int deleteByExample(AreaGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AreaGroup record);

    int insertSelective(AreaGroup record);

    List<AreaGroup> selectByExampleWithBLOBs(AreaGroupExample example);

    List<AreaGroup> selectByExample(AreaGroupExample example);

    AreaGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AreaGroup record, @Param("example") AreaGroupExample example);

    int updateByExampleWithBLOBs(@Param("record") AreaGroup record, @Param("example") AreaGroupExample example);

    int updateByExample(@Param("record") AreaGroup record, @Param("example") AreaGroupExample example);

    int updateByPrimaryKeySelective(AreaGroup record);

    int updateByPrimaryKeyWithBLOBs(AreaGroup record);

    int updateByPrimaryKey(AreaGroup record);
}
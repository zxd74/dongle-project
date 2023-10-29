package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.Tongfa;
import com.iwanvi.nvwa.dao.model.TongfaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TongfaMapper {
    int countByExample(TongfaExample example);

    int deleteByExample(TongfaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tongfa record);

    int insertSelective(Tongfa record);

    List<Tongfa> selectByExample(TongfaExample example);

    Tongfa selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tongfa record, @Param("example") TongfaExample example);

    int updateByExample(@Param("record") Tongfa record, @Param("example") TongfaExample example);

    int updateByPrimaryKeySelective(Tongfa record);

    int updateByPrimaryKey(Tongfa record);
}
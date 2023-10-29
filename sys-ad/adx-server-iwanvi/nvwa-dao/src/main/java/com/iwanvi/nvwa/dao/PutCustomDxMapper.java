package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.PutCustomDx;
import com.iwanvi.nvwa.dao.model.PutCustomDxExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PutCustomDxMapper {
    int countByExample(PutCustomDxExample example);

    int deleteByExample(PutCustomDxExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PutCustomDx record);

    int insertSelective(PutCustomDx record);

    List<PutCustomDx> selectByExample(PutCustomDxExample example);

    PutCustomDx selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PutCustomDx record, @Param("example") PutCustomDxExample example);

    int updateByExample(@Param("record") PutCustomDx record, @Param("example") PutCustomDxExample example);

    int updateByPrimaryKeySelective(PutCustomDx record);

    int updateByPrimaryKey(PutCustomDx record);
}
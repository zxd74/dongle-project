package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaDsp;
import com.iwanvi.nvwa.dao.model.QuotaDspExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuotaDspMapper {
    int countByExample(QuotaDspExample example);

    int deleteByExample(QuotaDspExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaDsp record);

    int insertSelective(QuotaDsp record);

    List<QuotaDsp> selectByExample(QuotaDspExample example);

    QuotaDsp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaDsp record, @Param("example") QuotaDspExample example);

    int updateByExample(@Param("record") QuotaDsp record, @Param("example") QuotaDspExample example);

    int updateByPrimaryKeySelective(QuotaDsp record);

    int updateByPrimaryKey(QuotaDsp record);
}
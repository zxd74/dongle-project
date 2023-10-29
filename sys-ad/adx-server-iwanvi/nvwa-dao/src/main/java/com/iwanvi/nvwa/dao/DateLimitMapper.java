package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DateLimit;
import com.iwanvi.nvwa.dao.model.DateLimitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DateLimitMapper {
    int countByExample(DateLimitExample example);

    int deleteByExample(DateLimitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DateLimit record);

    int insertSelective(DateLimit record);

    List<DateLimit> selectByExample(DateLimitExample example);

    DateLimit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DateLimit record, @Param("example") DateLimitExample example);

    int updateByExample(@Param("record") DateLimit record, @Param("example") DateLimitExample example);

    int updateByPrimaryKeySelective(DateLimit record);

    int updateByPrimaryKey(DateLimit record);
}
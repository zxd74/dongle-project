package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.FlowDic;
import com.iwanvi.nvwa.dao.model.FlowDicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlowDicMapper {
    int countByExample(FlowDicExample example);

    int deleteByExample(FlowDicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FlowDic record);

    int insertSelective(FlowDic record);

    List<FlowDic> selectByExample(FlowDicExample example);

    FlowDic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FlowDic record, @Param("example") FlowDicExample example);

    int updateByExample(@Param("record") FlowDic record, @Param("example") FlowDicExample example);

    int updateByPrimaryKeySelective(FlowDic record);

    int updateByPrimaryKey(FlowDic record);
}
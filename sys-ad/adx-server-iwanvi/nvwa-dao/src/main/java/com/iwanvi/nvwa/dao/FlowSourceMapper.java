package com.iwanvi.nvwa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.FlowSourceExample;

public interface FlowSourceMapper {
    int countByExample(FlowSourceExample example);

    int deleteByExample(FlowSourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FlowSource record);

    int insertSelective(FlowSource record);

    List<FlowSource> selectByExample(FlowSourceExample example);
    
    List<FlowSource> selectListByExample(FlowSourceExample example);

    FlowSource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FlowSource record, @Param("example") FlowSourceExample example);

    int updateByExample(@Param("record") FlowSource record, @Param("example") FlowSourceExample example);

    int updateByPrimaryKeySelective(FlowSource record);

    int updateByPrimaryKey(FlowSource record);

	Integer getCnt();
	
	List<FlowSource> getIds(Map<String, Object> paramMap);
}
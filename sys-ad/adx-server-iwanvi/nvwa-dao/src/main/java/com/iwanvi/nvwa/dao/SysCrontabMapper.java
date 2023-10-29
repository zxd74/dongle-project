package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.SysCrontab;
import com.iwanvi.nvwa.dao.model.SysCrontabExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysCrontabMapper {
    int countByExample(SysCrontabExample example);

    int deleteByExample(SysCrontabExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysCrontab record);

    int insertSelective(SysCrontab record);

    List<SysCrontab> selectByExample(SysCrontabExample example);

    SysCrontab selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysCrontab record, @Param("example") SysCrontabExample example);

    int updateByExample(@Param("record") SysCrontab record, @Param("example") SysCrontabExample example);

    int updateByPrimaryKeySelective(SysCrontab record);

    int updateByPrimaryKey(SysCrontab record);

	void updateStatusByIds(List<Integer> ids);
}
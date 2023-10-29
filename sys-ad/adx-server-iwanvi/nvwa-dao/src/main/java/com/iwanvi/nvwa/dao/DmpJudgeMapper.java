package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpJudge;
import com.iwanvi.nvwa.dao.model.DmpJudgeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmpJudgeMapper {
    int countByExample(DmpJudgeExample example);

    int deleteByExample(DmpJudgeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpJudge record);

    int insertSelective(DmpJudge record);

    List<DmpJudge> selectByExample(DmpJudgeExample example);

    DmpJudge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpJudge record, @Param("example") DmpJudgeExample example);

    int updateByExample(@Param("record") DmpJudge record, @Param("example") DmpJudgeExample example);

    int updateByPrimaryKeySelective(DmpJudge record);

    int updateByPrimaryKey(DmpJudge record);
}
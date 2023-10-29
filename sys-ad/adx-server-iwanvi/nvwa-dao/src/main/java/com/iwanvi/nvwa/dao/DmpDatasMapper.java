package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpDatas;
import com.iwanvi.nvwa.dao.model.DmpDatasExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmpDatasMapper {
    int countByExample(DmpDatasExample example);

    int deleteByExample(DmpDatasExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpDatas record);

    int insertSelective(DmpDatas record);

    List<DmpDatas> selectByExample(DmpDatasExample example);

    DmpDatas selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpDatas record, @Param("example") DmpDatasExample example);

    int updateByExample(@Param("record") DmpDatas record, @Param("example") DmpDatasExample example);

    int updateByPrimaryKeySelective(DmpDatas record);

    int updateByPrimaryKey(DmpDatas record);
}
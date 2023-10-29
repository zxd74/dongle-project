package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DmpDatasource;
import com.iwanvi.nvwa.dao.model.DmpDatasourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DmpDatasourceMapper {
    int countByExample(DmpDatasourceExample example);

    int deleteByExample(DmpDatasourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DmpDatasource record);

    int insertSelective(DmpDatasource record);

    List<DmpDatasource> selectByExampleWithBLOBs(DmpDatasourceExample example);

    List<DmpDatasource> selectByExample(DmpDatasourceExample example);

    DmpDatasource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DmpDatasource record, @Param("example") DmpDatasourceExample example);

    int updateByExampleWithBLOBs(@Param("record") DmpDatasource record, @Param("example") DmpDatasourceExample example);

    int updateByExample(@Param("record") DmpDatasource record, @Param("example") DmpDatasourceExample example);

    int updateByPrimaryKeySelective(DmpDatasource record);

    int updateByPrimaryKeyWithBLOBs(DmpDatasource record);

    int updateByPrimaryKey(DmpDatasource record);
}
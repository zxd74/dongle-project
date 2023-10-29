package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.PackagePosition;
import com.iwanvi.nvwa.dao.model.PackagePositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PackagePositionMapper {
    int countByExample(PackagePositionExample example);

    int deleteByExample(PackagePositionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PackagePosition record);

    int insertSelective(PackagePosition record);

    List<PackagePosition> selectByExample(PackagePositionExample example);

    PackagePosition selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PackagePosition record, @Param("example") PackagePositionExample example);

    int updateByExample(@Param("record") PackagePosition record, @Param("example") PackagePositionExample example);

    int updateByPrimaryKeySelective(PackagePosition record);

    int updateByPrimaryKey(PackagePosition record);
}
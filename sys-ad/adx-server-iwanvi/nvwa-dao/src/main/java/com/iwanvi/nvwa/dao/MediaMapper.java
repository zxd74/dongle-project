package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.Media;
import com.iwanvi.nvwa.dao.model.MediaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MediaMapper {
    int countByExample(MediaExample example);

    int deleteByExample(MediaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Media record);

    int insertSelective(Media record);

    List<Media> selectByExample(MediaExample example);

    Media selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Media record, @Param("example") MediaExample example);

    int updateByExample(@Param("record") Media record, @Param("example") MediaExample example);

    int updateByPrimaryKeySelective(Media record);

    int updateByPrimaryKey(Media record);
}
package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AdvertiserDsp;
import com.iwanvi.nvwa.dao.model.AdvertiserDspExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertiserDspMapper {
    int countByExample(AdvertiserDspExample example);

    int deleteByExample(AdvertiserDspExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdvertiserDsp record);

    int insertSelective(AdvertiserDsp record);

    List<AdvertiserDsp> selectByExample(AdvertiserDspExample example);

    AdvertiserDsp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdvertiserDsp record, @Param("example") AdvertiserDspExample example);

    int updateByExample(@Param("record") AdvertiserDsp record, @Param("example") AdvertiserDspExample example);

    int updateByPrimaryKeySelective(AdvertiserDsp record);

    int updateByPrimaryKey(AdvertiserDsp record);
}
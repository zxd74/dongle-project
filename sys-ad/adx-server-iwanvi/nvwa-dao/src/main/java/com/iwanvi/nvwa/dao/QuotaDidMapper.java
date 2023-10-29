package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaDid;
import com.iwanvi.nvwa.dao.model.QuotaDidExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuotaDidMapper {
    int countByExample(QuotaDidExample example);

    int deleteByExample(QuotaDidExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaDid record);

    int insertSelective(QuotaDid record);

    List<QuotaDid> selectByExample(QuotaDidExample example);

    QuotaDid selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaDid record, @Param("example") QuotaDidExample example);

    int updateByExample(@Param("record") QuotaDid record, @Param("example") QuotaDidExample example);

    int updateByPrimaryKeySelective(QuotaDid record);

    int updateByPrimaryKey(QuotaDid record);

    int countActByUidAndDay(@Param("uid") Integer uid, @Param("sTime") String sTime, @Param("eTime") String eTime);
}
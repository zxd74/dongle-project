package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaCust;
import com.iwanvi.nvwa.dao.model.QuotaCustExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QuotaCustMapper {
    int countByExample(QuotaCustExample example);

    int deleteByExample(QuotaCustExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaCust record);

    int insertSelective(QuotaCust record);

    List<QuotaCust> selectByExample(QuotaCustExample example);

    QuotaCust selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaCust record, @Param("example") QuotaCustExample example);

    int updateByExample(@Param("record") QuotaCust record, @Param("example") QuotaCustExample example);

    int updateByPrimaryKeySelective(QuotaCust record);

    int updateByPrimaryKey(QuotaCust record);

    List<QuotaCust> selectByTypeAndDay(@Param("type") Integer type, @Param("sDay") String sDay,
                                       @Param("eDay") String eDay, @Param("uuids") List<String> uuids);

    List<QuotaCust> selectByTypeAndDayAndUuid(@Param("sDay") String sDay, @Param("eDay") String eDay,
                                              @Param("uuid") String uuid);

    List<Map<String, Object>> getCustList(Map<String, Object> paramMap);
}
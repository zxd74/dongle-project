package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.PutExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PutMapper {
    int countByExample(PutExample example);

    int deleteByExample(PutExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Put record);

    int insertSelective(Put record);

    List<Put> selectByExampleWithBLOBs(PutExample example);

    List<Put> selectByExample(PutExample example);

    Put selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Put record, @Param("example") PutExample example);

    int updateByExampleWithBLOBs(@Param("record") Put record, @Param("example") PutExample example);

    int updateByExample(@Param("record") Put record, @Param("example") PutExample example);

    int updateByPrimaryKeySelective(Put record);

    int updateByPrimaryKeyWithBLOBs(Put record);

    int updateByPrimaryKey(Put record);
    
    int selectCountForPage(@Param("put") Put put, @Param("uids") List<Integer> uids, @Param("custId") Integer custId, @Param("custs") List<Integer> custs,@Param("putStates") List<Integer> putStates);
    
    List<Put> selectForPage(@Param("put") Put put, @Param("uids") List<Integer> uids, @Param("custId") Integer custId, @Param("custs") List<Integer> custs,@Param("putStates") List<Integer> putStates, @Param("start") Integer start,@Param("limit") Integer limit);

	Map<String, Object> selectSchedules(@Param("paramMap") Map<String, Object> paramMap);

	int countByMap(Map<String, Object> paramMap);
}
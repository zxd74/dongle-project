package com.dongle.store.system.dao.mapper;

import com.dongle.store.system.dao.entity.StoreUser;
import com.dongle.store.system.dao.entity.StoreUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StoreUserMapper {
    long countByExample(StoreUserExample example);

    int deleteByExample(StoreUserExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(StoreUser row);

    int insertSelective(StoreUser row);

    List<StoreUser> selectByExample(StoreUserExample example);

    StoreUser selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("row") StoreUser row, @Param("example") StoreUserExample example);

    int updateByExample(@Param("row") StoreUser row, @Param("example") StoreUserExample example);

    int updateByPrimaryKeySelective(StoreUser row);

    int updateByPrimaryKey(StoreUser row);
}
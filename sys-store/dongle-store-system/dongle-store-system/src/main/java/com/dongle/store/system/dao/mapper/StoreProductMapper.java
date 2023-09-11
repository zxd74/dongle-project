package com.dongle.store.system.dao.mapper;

import com.dongle.store.system.dao.entity.StoreProduct;
import com.dongle.store.system.dao.entity.StoreProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StoreProductMapper {
    long countByExample(StoreProductExample example);

    int deleteByExample(StoreProductExample example);

    int deleteByPrimaryKey(String id);

    int insert(StoreProduct row);

    int insertSelective(StoreProduct row);

    List<StoreProduct> selectByExample(StoreProductExample example);

    StoreProduct selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") StoreProduct row, @Param("example") StoreProductExample example);

    int updateByExample(@Param("row") StoreProduct row, @Param("example") StoreProductExample example);

    int updateByPrimaryKeySelective(StoreProduct row);

    int updateByPrimaryKey(StoreProduct row);
}
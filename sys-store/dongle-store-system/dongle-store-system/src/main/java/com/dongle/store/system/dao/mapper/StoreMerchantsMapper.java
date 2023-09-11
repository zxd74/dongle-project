package com.dongle.store.system.dao.mapper;

import com.dongle.store.system.dao.entity.StoreMerchants;
import com.dongle.store.system.dao.entity.StoreMerchantsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StoreMerchantsMapper {
    long countByExample(StoreMerchantsExample example);

    int deleteByExample(StoreMerchantsExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(StoreMerchants row);

    int insertSelective(StoreMerchants row);

    List<StoreMerchants> selectByExample(StoreMerchantsExample example);

    StoreMerchants selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("row") StoreMerchants row, @Param("example") StoreMerchantsExample example);

    int updateByExample(@Param("row") StoreMerchants row, @Param("example") StoreMerchantsExample example);

    int updateByPrimaryKeySelective(StoreMerchants row);

    int updateByPrimaryKey(StoreMerchants row);
}
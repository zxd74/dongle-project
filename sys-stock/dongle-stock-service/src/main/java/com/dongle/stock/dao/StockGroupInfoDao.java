package com.dongle.stock.dao;

import com.dongle.stock.dao.entity.StockGroupInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:45
 */
public interface StockGroupInfoDao extends CrudRepository<StockGroupInfo,StockGroupKey> {

    /**
     * 查询分组所有股票代码
     * @param groupId
     * @return
     */
    @Query(value = "select code from `stock_group_info` where `group_id` = ?1",nativeQuery = true)
    List<String> findAllCodeByGroupId(int groupId);

    @Query(value = "delete from `stock_group_info` where `group_id`=?1",nativeQuery = true)
    @Modifying
    void deleteByGroupId(int groupId);
}

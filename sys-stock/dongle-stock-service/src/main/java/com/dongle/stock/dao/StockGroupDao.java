package com.dongle.stock.dao;

import com.dongle.stock.dao.entity.StockGroup;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:18
 */

public interface StockGroupDao extends CrudRepository<StockGroup, Integer> {

    @Query(value = "delete from StockGroup where id=:groupId")
    @Modifying
    void deleteByGroupId(int groupId);
}

package com.dongle.stack.dao;

import com.dongle.stack.dao.entity.StockHistoryData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockHistoryDataDao extends CrudRepository<StockHistoryData,StockHistoryKey> {

    @Query(value="select sh from StockHistoryData sh where sh.code=?1")
    List<StockHistoryData> queryByCode(String code);
}

package com.dongle.stock.dao;

import com.dongle.stock.dao.entity.StockHistoryData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StockHistoryDataDao extends CrudRepository<StockHistoryData,StockHistoryKey> {

    @Query(value="select * from stock_history_data where code=?1 order by date limit ?2",nativeQuery = true)
    List<StockHistoryData> queryByCode(String code,int day);

    @Query(value = "select sh from StockHistoryData sh where sh.date = (SELECT MAX(shd.date) from StockHistoryData shd) order by sh.code")
    List<StockHistoryData> queryByNew();
}

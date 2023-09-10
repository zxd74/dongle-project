package com.dongle.stock.dao;

import com.dongle.stock.dao.entity.StockInfo;
import org.springframework.data.repository.CrudRepository;

public interface StockInfoDao extends CrudRepository<StockInfo,String> {

}

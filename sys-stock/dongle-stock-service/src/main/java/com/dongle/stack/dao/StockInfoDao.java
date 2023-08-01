package com.dongle.stack.dao;

import com.dongle.stack.dao.entity.StockInfo;
import org.springframework.data.repository.CrudRepository;

public interface StockInfoDao extends CrudRepository<StockInfo,String> {

}

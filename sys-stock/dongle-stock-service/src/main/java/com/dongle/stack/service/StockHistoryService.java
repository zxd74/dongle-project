package com.dongle.stack.service;

import com.dongle.stack.model.Stock;

import java.util.List;

public interface StockHistoryService {

    Stock queryStock(String code);

    List<Stock> queryStockHistory(String code,int day);

    List<Stock> queryAllStock();

    List<Stock> queryAllStockData();

    List<Stock> collectAllStockData();

    /**
     * 获取最近一天所有数据
     * @return
     */
    List<Stock> queryNewAllStockData();

    List<Stock> queryGroupStockData(String groupId);
}

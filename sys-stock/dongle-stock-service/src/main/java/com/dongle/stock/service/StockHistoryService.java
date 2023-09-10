package com.dongle.stock.service;

import com.dongle.stock.model.StockModel;

import java.util.List;

public interface StockHistoryService {

    StockModel queryStock(String code);

    List<StockModel> queryStockHistory(String code, int day);

    List<StockModel> queryAllStock();

    List<StockModel> queryAllStockData();

    List<StockModel> collectAllStockData();

    /**
     * 获取最近一天所有数据
     * @return
     */
    List<StockModel> queryNewAllStockData();

    List<StockModel> queryGroupStockData(String groupId);

    /**
     * 查询股票集数据
     * @param codes
     * @return
     */
    void queryStockData(List<StockModel> codes);
}

package com.dongle.stack.service;

import com.dongle.stack.model.Stock;

import java.util.List;

public interface StockHistoryService {

    Stock queryStock(String code);

    List<Stock> queryStockHistory(String code);

    List<Stock> queryAllStock();

    List<Stock> queryAllStockData();

    List<Stock> collectAllStockData();
}

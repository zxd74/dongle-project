package com.dongle.stack.service;

import com.dongle.stack.model.Stock;

import java.util.List;

public interface StockHistoryService {

    List<Stock> queryStockHistory(Stock stock);
}

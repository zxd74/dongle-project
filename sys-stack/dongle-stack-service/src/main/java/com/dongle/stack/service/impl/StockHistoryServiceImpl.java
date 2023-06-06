package com.dongle.stack.service.impl;

import com.dongle.stack.model.Stock;
import com.dongle.stack.service.StockHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistoryServiceImpl implements StockHistoryService {

    @Override
    public List<Stock> queryStockHistory(Stock stock) {
        return null;
    }
}

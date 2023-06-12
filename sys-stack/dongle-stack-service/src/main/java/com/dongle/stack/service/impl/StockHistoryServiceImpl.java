package com.dongle.stack.service.impl;

import com.dongle.stack.dao.StockHistoryDataDao;
import com.dongle.stack.dao.StockInfoDao;
import com.dongle.stack.dao.entity.StockHistoryData;
import com.dongle.stack.dao.entity.StockInfo;
import com.dongle.stack.model.Stock;
import com.dongle.stack.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockHistoryServiceImpl implements StockHistoryService {

    @Autowired
    private StockInfoDao stockInfoDao;
    @Autowired
    private StockHistoryDataDao stockHistoryDataDao;

    @Override
    public Stock queryStock(String code) {
        return convert(stockInfoDao.findById(code).orElse(null));
    }

    @Override
    public List<Stock> queryStockHistory(String code) {
        List<Stock> stocks = new ArrayList<>();
        Iterable<StockHistoryData> iterable = stockHistoryDataDao.queryByCode(code);
        iterable.forEach(stockInfo -> stocks.add(convert(stockInfo)));
        return stocks;
    }

    @Override
    public List<Stock> queryAllStock() {
        List<Stock> stocks = new ArrayList<>();
        Iterable<StockInfo> iterable = stockInfoDao.findAll();
        iterable.forEach(stockInfo -> stocks.add(convert(stockInfo)));
        return stocks;
    }

    @Override
    public List<Stock> queryAllStockData() {
        List<Stock> stocks = new ArrayList<>();
        stockHistoryDataDao.findAll().forEach(stockHistoryData -> stocks.add(convert(stockHistoryData)));
        return stocks;
    }

    @Override
    public List<Stock> collectAllStockData() {
        List<Stock> stocks = new ArrayList<>();
        // TODO 汇总各股票数据

        return stocks;
    }

    private Stock convert(StockInfo stockInfo) {
        if (stockInfo == null) return null;
        Stock stock = new Stock();
        stock.setCode(stockInfo.getCode());
        stock.setName(stockInfo.getName());
        return stock;
    }

    private Stock convert(StockHistoryData stockInfo) {
        Stock stock = new Stock();
        stock.setCode(stockInfo.getCode());
        stock.setDate(stockInfo.getDate());
        stock.setOpen(stockInfo.getOpen());
        stock.setHigh(stockInfo.getHigh());
        stock.setLow(stockInfo.getLow());
        stock.setPrice(stockInfo.getClose());
        return stock;
    }

}

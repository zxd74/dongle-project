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
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<Stock> queryStockHistory(String code,int day) {
        List<Stock> stocks = new ArrayList<>();
        Iterable<StockHistoryData> iterable = stockHistoryDataDao.queryByCode(code,day);
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
    public List<Stock> queryNewAllStockData() {
        Map<String,List<Stock>> map = stockHistoryDataDao.queryByNew().stream().map(this::convert).collect(Collectors.groupingBy(Stock::getCode));
        Iterable<StockInfo> iterable = stockInfoDao.findAll();
        iterable.forEach(stockInfo -> convert(map.get(stockInfo.getCode()).get(0),stockInfo));
        List<Stock> stocks = new ArrayList<>(map.size());
        map.values().forEach(stocks::addAll);
        return stocks;
    }

    @Override
    public List<Stock> collectAllStockData() {
        List<Stock> stocks = new ArrayList<>();
        // TODO 汇总各股票数据
        return stocks;
    }

    private Stock convert(StockInfo stockInfo) {
        Stock stock = new Stock();
        convert(stock,stockInfo);
        return stock;
    }

    private void convert(Stock stock,StockInfo stockInfo) {
        if (stockInfo == null) return;
        stock.setCode(stockInfo.getCode());
        stock.setName(stockInfo.getName());
    }

    private Stock convert(StockHistoryData historyData) {
        Stock stock = new Stock();
        convert(stock,historyData);
        return stock;
    }

    private void convert(Stock stock,StockHistoryData historyData) {
        if (historyData == null) return;
        stock.setCode(historyData.getCode());
        stock.setDate(historyData.getDate());
        stock.setOpen(historyData.getOpen());
        stock.setHigh(historyData.getHigh());
        stock.setLow(historyData.getLow());
        stock.setPrice(historyData.getClose());
    }

}

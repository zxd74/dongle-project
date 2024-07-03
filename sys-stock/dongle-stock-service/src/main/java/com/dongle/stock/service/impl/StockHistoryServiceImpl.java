package com.dongle.stock.service.impl;

import com.dongle.stock.dao.StockHistoryDataDao;
import com.dongle.stock.dao.StockInfoDao;
import com.dongle.stock.dao.entity.StockHistoryData;
import com.dongle.stock.dao.entity.StockInfo;
import com.dongle.stock.model.StockModel;
import com.dongle.stock.service.StockHistoryService;
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
    public StockModel queryStock(String code) {
        return convert(stockInfoDao.findById(code).orElse(null));
    }

    @Override
    public List<StockModel> queryStockHistory(String code, int day) {
        List<StockModel> stockModels = new ArrayList<>();
        Iterable<StockHistoryData> iterable = stockHistoryDataDao.queryByCode(code,day);
        iterable.forEach(stockInfo -> stockModels.add(convert(stockInfo)));
        stockModels.sort((s1,s2)->-1);
        return stockModels;
    }

    @Override
    public List<StockModel> queryAllStock() {
        List<StockModel> stockModels = new ArrayList<>();
        Iterable<StockInfo> iterable = stockInfoDao.findAll();
        iterable.forEach(stockInfo -> stockModels.add(convert(stockInfo)));
        return stockModels;
    }

    @Override
    public List<StockModel> queryAllStockData() {
        List<StockModel> stockModels = new ArrayList<>();
        stockHistoryDataDao.findAll().forEach(stockHistoryData -> stockModels.add(convert(stockHistoryData)));
        return stockModels;
    }

    @Override
    public List<StockModel> queryNewAllStockData() {
        Map<String,List<StockModel>> map = stockHistoryDataDao.queryByNew().stream().map(this::convert).collect(Collectors.groupingBy(StockModel::getCode));
        Iterable<StockInfo> iterable = stockInfoDao.findAll();
        iterable.forEach(stockInfo -> convert(map.get(stockInfo.getCode()).get(0),stockInfo));
        List<StockModel> stockModels = new ArrayList<>(map.size());
        map.values().forEach(stockModels::addAll);
        return stockModels;
    }

    @Override
    public List<StockModel> collectAllStockData() {
        List<StockModel> stockModels = new ArrayList<>();
        // TODO 汇总各股票数据
        return stockModels;
    }

    @Override
    public List<StockModel> queryGroupStockData(String groupId) {
        List<StockModel> stockModels = queryAllStock();
        stockModels.forEach(stock -> stock.setData(stockHistoryDataDao.queryByCode(stock.getCode(),30).stream().map(this::convert).collect(Collectors.toList())));
        return stockModels;
    }

    @Override
    public void queryStockData(List<StockModel> stockModels) {
        stockModels.forEach(stock -> stock.setData(stockHistoryDataDao.queryByCode(stock.getCode(),30).stream().map(this::convert).collect(Collectors.toList())));
    }

    private StockModel convert(StockInfo stockInfo) {
        StockModel stockModel = new StockModel();
        convert(stockModel,stockInfo);
        return stockModel;
    }

    private void convert(StockModel stockModel, StockInfo stockInfo) {
        if (stockInfo == null) return;
        stockModel.setCode(stockInfo.getCode());
        stockModel.setName(stockInfo.getName());
    }

    private StockModel convert(StockHistoryData historyData) {
        StockModel stockModel = new StockModel();
        convert(stockModel,historyData);
        return stockModel;
    }

    private void convert(StockModel stockModel, StockHistoryData historyData) {
        if (historyData == null) return;
        stockModel.setCode(historyData.getCode());
        stockModel.setDate(historyData.getDate());
        stockModel.setOpen(historyData.getOpen());
        stockModel.setHigh(historyData.getHigh());
        stockModel.setLow(historyData.getLow());
        stockModel.setPrice(historyData.getClose());
    }

}

package com.dongle.stack.controller;

import com.dongle.stack.model.Stock;
import com.dongle.stack.service.StockGrabService;
import com.dongle.stack.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    private StockHistoryService stockHistoryService;

    @Autowired
    private StockGrabService stockGrabService;


    @RequestMapping("add-new")
    public String addNewStock(@RequestParam("code") String code, @RequestParam(value = "from",required = false) String from){
        return stockGrabService.grabNewStock(code,from) ? "OK":"Error";
    }

    @RequestMapping("grab-day")
    public String grabStockData(@RequestParam(value = "day",required = false) String day){
        return stockGrabService.grabByDay(day) ? "OK":"Error";
    }

    @RequestMapping("query-stock")
    public Stock queryStock(@RequestParam("code")String code){
        Stock stock = stockHistoryService.queryStock(code);
        if (stock == null) return null;
        stock.setData(stockHistoryService.queryStockHistory(code));
        return stock;
    }

    @RequestMapping("all-stock")
    public List<Stock> allStock(){
        return stockHistoryService.queryAllStock();
    }
    @RequestMapping("all-data")
    public List<Stock> allData(){

        return stockHistoryService.queryAllStockData();
    }
}

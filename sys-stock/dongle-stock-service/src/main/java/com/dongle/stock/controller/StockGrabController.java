package com.dongle.stock.controller;

import com.dongle.stock.service.StockGrabService;
import com.dongle.stock.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/stock-grab")
public class StockGrabController {

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
}

package com.dongle.stack.controller;

import com.dongle.stack.service.StockGrabService;
import com.dongle.stack.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("stock")
public class StockController {


    @Autowired
    private StockHistoryService stockHistoryService;
    @Autowired
    private StockGrabService stockGrabService;


    @RequestMapping("add-new")
    public String addNewStock(@RequestParam("code") String code, @RequestParam("from") String from){
        return stockGrabService.grabNewStock(code,from) ? "OK":"Error";
    }

    @RequestMapping("grab-day")
    public String grabStockData(Date date){
        return stockGrabService.grabByDay(date) ? "OK":"Error";
    }

    @RequestMapping("query")
    public String queryStock(){

        return "OK";
    }
}

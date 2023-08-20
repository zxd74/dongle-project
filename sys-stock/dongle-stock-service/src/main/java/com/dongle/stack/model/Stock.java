package com.dongle.stack.model;

import lombok.Data;

import java.util.List;

@Data
public class Stock {

    private String code;
    private String name;
    private String date;
    private double price;
    private double high;
    private double low;
    private double open;
    /**
     * Stock历史数据
     */
    private List<Stock> data;
}

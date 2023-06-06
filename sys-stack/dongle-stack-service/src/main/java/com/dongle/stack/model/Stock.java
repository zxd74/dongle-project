package com.dongle.stack.model;

import lombok.Data;

@Data
public class Stock {

    private String code;
    private String name;
    private String date;
    private double price;
    private double high;
    private double low;
}

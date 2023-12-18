package com.dongle.stock.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "stocks")
@Entity
public class StockInfo {
    @Id
    @Column(name = "code")
    private String code;
    @Column(name = "code_name")
    private String name;
    @Column(name = "ipoDate")
    private String date;
}

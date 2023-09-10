package com.dongle.stock.dao.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:14
 */
@Data
@Table(name = "stock_group")
@Entity

public class StockGroup {

    @Id
    private int id;
    private String name;
}

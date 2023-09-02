package com.dongle.stack.dao.entity;

import com.dongle.stack.dao.StockGroupKey;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:15
 */
@Table(name = "stock_group_info")
@IdClass(StockGroupKey.class)
@Data
@Entity
public class StockGroupInfo {

    @Id
    @Column(name = "group_id")
    private int groupId;
    @Id
    private String code;
}

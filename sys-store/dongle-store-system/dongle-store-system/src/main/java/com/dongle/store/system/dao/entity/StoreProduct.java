package com.dongle.store.system.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Dongle
 * @desc table is `store_products`
 */
public class StoreProduct {
    private String id;

    private String name;

    /**
     * 商品信息
     */
    private String info;

    /**
     * 短介绍
     */
    private String shortInfo;

    /**
     * 定价
     */
    private BigDecimal price;

    private String imgs;

    /**
     * 商家UUID
     */
    private String owner;

    private Date cdt;

    private Date udt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getShortInfo() {
        return shortInfo;
    }

    public void setShortInfo(String shortInfo) {
        this.shortInfo = shortInfo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getCdt() {
        return cdt;
    }

    public void setCdt(Date cdt) {
        this.cdt = cdt;
    }

    public Date getUdt() {
        return udt;
    }

    public void setUdt(Date udt) {
        this.udt = udt;
    }
}
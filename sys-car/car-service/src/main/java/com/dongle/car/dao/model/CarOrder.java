package com.dongle.car.dao.model;

import java.util.Date;

public class CarOrder {
    private String id;

    private String uId;

    private String shopId;

    private Integer status;

    private Integer payStatus;

    private String phone;

    private Integer evalId;

    private String evalLevel;

    private Date odt;

    private Date cdt;

    private Date udt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getEvalId() {
        return evalId;
    }

    public void setEvalId(Integer evalId) {
        this.evalId = evalId;
    }

    public String getEvalLevel() {
        return evalLevel;
    }

    public void setEvalLevel(String evalLevel) {
        this.evalLevel = evalLevel;
    }

    public Date getOdt() {
        return odt;
    }

    public void setOdt(Date odt) {
        this.odt = odt;
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
package com.iwanvi.nvwa.dao.model;

public class FlowDic {
    private Integer id;

    private String dicValue;

    private String dicKey;

    private Integer dicGroup;

    private Integer dicType;

    private Integer enumValue;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    public String getDicKey() {
        return dicKey;
    }

    public void setDicKey(String dicKey) {
        this.dicKey = dicKey;
    }

    public Integer getDicGroup() {
        return dicGroup;
    }

    public void setDicGroup(Integer dicGroup) {
        this.dicGroup = dicGroup;
    }

    public Integer getDicType() {
        return dicType;
    }

    public void setDicType(Integer dicType) {
        this.dicType = dicType;
    }

    public Integer getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(Integer enumValue) {
        this.enumValue = enumValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
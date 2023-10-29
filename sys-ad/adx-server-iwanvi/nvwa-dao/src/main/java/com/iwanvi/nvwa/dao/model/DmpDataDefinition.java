package com.iwanvi.nvwa.dao.model;

public class DmpDataDefinition {
    private Integer id;

    private Integer did;

    private Integer col;

    private String colName;

    private Integer colType;

    private Integer isKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Integer getColType() {
        return colType;
    }

    public void setColType(Integer colType) {
        this.colType = colType;
    }

    public Integer getIsKey() {
        return isKey;
    }

    public void setIsKey(Integer isKey) {
        this.isKey = isKey;
    }
}
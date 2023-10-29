package com.iwanvi.nvwa.dao.model;

import java.util.Date;
import java.util.List;

public class DmpDatas {
    private Integer id;

    private String name;

    private Integer sid;

    private String delimiter;

    private Integer delId;

    private Integer createUser;

    private Date createTime;

    private Integer status;

    private String sName;

    private List<DmpDataDefinition> definitions;

    private String definitionStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Integer getDelId() {
        return delId;
    }

    public void setDelId(Integer delId) {
        this.delId = delId;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public List<DmpDataDefinition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<DmpDataDefinition> definitions) {
        this.definitions = definitions;
    }

    public String getDefinitionStr() {
        return definitionStr;
    }

    public void setDefinitionStr(String definitionStr) {
        this.definitionStr = definitionStr;
    }
}
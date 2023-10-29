package com.iwanvi.nvwa.dao.model;

import java.util.List;

public class UserAuths {
    private Integer id;

    private Integer uid;

    private Integer aid;

    private String name;

    private Integer status;

    private Integer readonly;

    private Integer pid;

    private List<UserAuths> childs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReadonly() {
        return readonly;
    }

    public void setReadonly(Integer readonly) {
        this.readonly = readonly;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserAuths> getChilds() {
        return childs;
    }

    public void setChilds(List<UserAuths> childs) {
        this.childs = childs;
    }
}
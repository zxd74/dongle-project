package com.iwanvi.nvwa.dao.model;

import java.util.List;

public class GroupAuths {
    private Integer id;

    private Integer gid;

    private Integer aid;

    private Integer status;

    private Integer pid;

    private String name;

    List<GroupAuths> childs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
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

    public List<GroupAuths> getChilds() {
        return childs;
    }

    public void setChilds(List<GroupAuths> childs) {
        this.childs = childs;
    }
}
package com.iwanvi.nvwa.dao.model;

public class DmpPersonRule {
    private Integer id;

    private String pkey;

    private Integer tid;

    private Double score;

    private Integer useTag;

    private Integer did;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getUseTag() {
        return useTag;
    }

    public void setUseTag(Integer useTag) {
        this.useTag = useTag;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }
}
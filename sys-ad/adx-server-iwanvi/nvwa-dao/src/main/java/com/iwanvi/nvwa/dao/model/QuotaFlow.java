package com.iwanvi.nvwa.dao.model;


public class QuotaFlow {
    private Integer id;

    private String flowId;

    private Integer creDay;

    private Integer creHour;

    private Long req = 0l;

    private Integer bid = 0;

    private Integer exp = 0;

    private Integer clk = 0;

    private Integer requv = 0;

    private Integer expuv = 0;

    private Integer clkuv = 0;

    private Long invest = 0l;

    private Integer active;

    private float investment = 0f;

    private String fsName;

    private double bid_rate;

    private double exp_rate;

    private double clk_rate;

    private Integer putActive;

    private float cpm = 0f;

    private float cpc = 0f;

    private String fsId;

    private String appid;

    private String adpid;

    private String channel1;

    private String channel2;

    private String channel1Name;

    private String channel2Name;

    private String versions;

    private String appName;

    private String adpName;

    private String version;

    private Double expPer;

    private String itemId;

    private String itemName;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public QuotaFlow() {}

    public QuotaFlow(int creDay) {
        this.creDay=creDay;
    }

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }

    public float getCpm() {
        if (exp != null && exp != 0) {
            cpm = (float) getInvestment() / exp * 1000;
            cpm = (float) Math.round(cpm * 100) / 100;
        }
        return cpm;
    }

    public void setCpm(float cpm) {
        this.cpm = cpm;
    }

    public float getCpc() {
        if (clk != null && clk != 0) {
            cpc = (float) getInvestment() / clk;
            cpc = (float) Math.round(cpc * 100) / 100;
        }
        return cpc;
    }

    public void setCpc(float cpc) {
        this.cpc = cpc;
    }

    public double getBid_rate() {
        if (getReq() == null || getReq() == 0
                || getBid() == null || getBid() == 0) {
            bid_rate = 0;
        } else {
            bid_rate = Double.parseDouble(String.format("%.2f", (((float)getBid() / (float)getReq()) * 100.0)));
        }
        return bid_rate;
    }

    public double getExp_rate() {
        if (getReq() == null || getExp() == null) {
            exp_rate = 0 ;
        } else {
            if (getReq() > 0) {
                exp_rate = Double.parseDouble(String.format("%.2f", (((float)getExp() / (float)getReq()) * 100.0)));
            } else {
                exp_rate = 0;
            }
        }
        return exp_rate;
    }

    public double getClk_rate() {
        if (getClk() == null || getExp() == null) {
            clk_rate = 0;
        } else {
            if (getExp() > 0) {
                clk_rate = Double.parseDouble(String.format("%.2f", ((float)getClk() / (float)getExp()) * 100.0));
            } else {
                clk_rate = 0;
            }
        }
        return clk_rate;
    }

    public Double getExpPer() {
        if (getExpuv() == null || getExp() == null) {
            expPer = 0d;
        } else {
            if (getExpuv() > 0) {
                expPer = Double.parseDouble(String.format("%.2f", (((float)getExp() / (float)getExpuv()))));
            } else {
                expPer = 0d;
            }
        }
        return expPer;
    }

    public void setExpPer(Double expPer) {
        this.expPer = expPer;
    }

    public float getInvestment() {
        return (float) Math.round((float)invest / 1000) / 100;
    }

    public void setInvestment(float investment) {
        this.investment = investment;
    }

    public String getFsName() {
        return fsName;
    }

    public void setFsName(String fsName) {
        this.fsName = fsName;
    }


    public void setBid_rate(double bid_rate) {
        this.bid_rate = bid_rate;
    }


    public void setExp_rate(double exp_rate) {
        this.exp_rate = exp_rate;
    }


    public void setClk_rate(double clk_rate) {
        this.clk_rate = clk_rate;
    }

    public Integer getPutActive() {
        return putActive;
    }

    public void setPutActive(Integer putActive) {
        this.putActive = putActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Integer getCreDay() {
        return creDay;
    }

    public void setCreDay(Integer creDay) {
        this.creDay = creDay;
    }

    public Integer getCreHour() {
        return creHour;
    }

    public void setCreHour(Integer creHour) {
        this.creHour = creHour;
    }

    public Long getReq() {
        return req;
    }

    public void setReq(Long req) {
        this.req = req;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getClk() {
        return clk;
    }

    public void setClk(Integer clk) {
        this.clk = clk;
    }

    public Long getInvest() {
        return invest;
    }

    public void setInvest(Long invest) {
        this.invest = invest;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getRequv() {
        return requv;
    }

    public void setRequv(Integer requv) {
        this.requv = requv;
    }

    public Integer getExpuv() {
        return expuv;
    }

    public void setExpuv(Integer expuv) {
        this.expuv = expuv;
    }

    public Integer getClkuv() {
        return clkuv;
    }

    public void setClkuv(Integer clkuv) {
        this.clkuv = clkuv;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAdpid() {
        return adpid;
    }

    public void setAdpid(String adpid) {
        this.adpid = adpid;
    }

    public String getChannel1() {
        return channel1;
    }

    public void setChannel1(String channel1) {
        this.channel1 = channel1;
    }

    public String getChannel2() {
        return channel2;
    }

    public void setChannel2(String channel2) {
        this.channel2 = channel2;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAdpName() {
        return adpName;
    }

    public void setAdpName(String adpName) {
        this.adpName = adpName;
    }

    public String getChannel1Name() {
        return channel1Name;
    }

    public void setChannel1Name(String channel1Name) {
        this.channel1Name = channel1Name;
    }

    public String getChannel2Name() {
        return channel2Name;
    }

    public void setChannel2Name(String channel2Name) {
        this.channel2Name = channel2Name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
package com.iwanvi.nvwa.dao.model;


public class QuotaPlatform {
	private Integer id;

	private String platformId;

	private Integer creDay;

	private Integer creHour;

	private Long req = 0l;

	private Integer bid = 0;

	private Integer exp = 0;

	private Integer clk = 0;

	private Long invest = 0l;

	private Integer cid = 0;

	private String consumerName;

	private double bid_rate;

	private double exp_rate;

	private double clk_rate;

	private float investment = 0f;

	private float cpm = 0f;

	private float cpc = 0f;
	
	private String dspId;
	
	private Integer timeout = 0;

	private Integer error = 0;

	private Long nobid = 0l;

	private Integer lower = 0;

	private Long overqps = 0l;

	private Long win = 0l;

	private String appid;

	private String adpid;

	private String itemId;

	private String itemName;

	private String appName;

	private String adpName;

	private String dspName;
	
	public QuotaPlatform() {
	}
	
	public QuotaPlatform(int creDay){
		this.creDay=creDay;
	}
	
	public QuotaPlatform(int creDay, long req, int exp ,int clk, float investment){
		this.creDay=creDay;
		this.req = req;
		this.exp = exp;
		this.clk = clk;
		this.investment = investment;
	}
	
	public String getDspId() {
		return dspId;
	}

	public void setDspId(String dspId) {
		this.dspId = dspId;
	}
	public float getCpm() {
		if (exp != null && exp != 0 && invest != null) {
			cpm = (float) invest / 100000 / exp * 1000;
			cpm = Float.parseFloat(String.format("%.2f", cpm));
		}
		return cpm;
	}

	public void setCpm(float cpm) {
		this.cpm = cpm;
	}

	public float getCpc() {
		if (clk != null && clk != 0 && invest != null) {
			cpc = (float) invest / 100000 / clk;
			cpc = Float.parseFloat(String.format("%.2f", cpc));
		}
		return cpc;
	}

	public void setCpc(float cpc) {
		this.cpc = cpc;
	}

	public double getBid_rate() {
		if (req != null && req != 0 && bid != null) {
			bid_rate = (double) bid / req;
			bid_rate = Double.parseDouble(String.format("%.2f", bid_rate * 100));
		}
		return bid_rate;
	}

	public double getExp_rate() {
		exp_rate = req != null && req != 0 && exp != null ? (double) exp / req : 0d;
		return Double.parseDouble(String.format("%.2f", exp_rate * 100));
	}

	public double getClk_rate() {
		clk_rate = exp != null && exp != 0 && clk != null ? (double) clk / exp : 0d;
		return Double.parseDouble(String.format("%.2f", clk_rate * 100));
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
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

	public float getInvestment() {
		investment = (float) invest / 100000;
		return Float.parseFloat(String.format("%.2f", investment));
	}

	public void setInvestment(float investment) {
		this.investment = investment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
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

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public Long getNobid() {
		return nobid;
	}

	public void setNobid(Long nobid) {
		this.nobid = nobid;
	}

	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}
	
	public Long getOverqps() {
		return overqps;
	}
	
	public void setOverqps(Long overqps) {
		this.overqps = overqps;
	}

	public Long getWin() {
		return win;
	}

	public void setWin(Long win) {
		this.win = win;
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

    public String getDspName() {
        return dspName;
    }

    public void setDspName(String dspName) {
        this.dspName = dspName;
    }
}
package com.iwanvi.nvwa.dao.model;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class Put {
    private Integer id;
    @ApiModelProperty("投放名称")
    private String putName;
    @ApiModelProperty("计划id")
    private Integer oid;
    @ApiModelProperty("关联计划id")
    private Integer pid;
    @ApiModelProperty("投放类型 (19精确投放 18 抄底投放) ")
    private Integer putType;
    @ApiModelProperty("投放开始时间")
    private Date beginTime;
    @ApiModelProperty("投放结束时间")
    private Date endTime;
    @ApiModelProperty("计费方式")
    private Integer costType;
    @ApiModelProperty("投放类型")
    private Integer extensionType;
    @ApiModelProperty("落地页")
    private String landUrl;

    private String appId;

    private String pkgName;

    private String timeInterval;
    @ApiModelProperty("定向ip地区规则(1 包含 2排除)")
    private Integer dqRule;

    private String dxXl;

    private String dxNl;

    private String dxXb;

    private String dxZd;

    private String dxXw;

    private String dxXq;
    @ApiModelProperty("操作系统")
    private String dxCzxt;
    @ApiModelProperty("定向运营商")
    private String dxYys;
    @ApiModelProperty("定向：网络")
    private String dxWl;
    
    private String dxLlly;
    @ApiModelProperty("定向终端类型")
    private String dxZdlx;

    private String dxSb;
    
    private Integer dxMediaType;
    @ApiModelProperty("定向媒体")
    private String dxMedia;

    private String dxApp;
    @ApiModelProperty("运行状态")
    private Integer runState;
    @ApiModelProperty("单元状态") 
    private Integer putState;

    private Integer createUser;

    private Date createTime;

    private Date updateTime;
    @ApiModelProperty("价格")
    private Integer price;
    @ApiModelProperty("广告位id")
    private Integer adPosition;
    @ApiModelProperty("曝光上报地址")
    private String impMonitorUrl;
    @ApiModelProperty("点击上报地址")
    private String clkMonitorUrl;
    
    private String extCreativeId;

    private Integer putLimit;

    private Integer opUser;

    private Integer opUserType;
    @ApiModelProperty("投放方式")
    private Integer deliveryMode;
    @ApiModelProperty("是否频次控制")
    private Integer isFrequency;
    @ApiModelProperty("频次控制周期")
    private Integer frequencPeriod;
    @ApiModelProperty("频次")
    private Integer frequencNum;
    @ApiModelProperty("日期 限额 时间段集合")
    private String limits;
    
    private Integer isOptimize;

    private Integer optimizeCpa;

    private String dxAppType;
    @ApiModelProperty("是否过滤非法设备")
    private Integer isfilterDeviceCode;
    @ApiModelProperty("是否PDB投放：0 否,1是")
    private Integer isPdb;	
    @ApiModelProperty("pdb 交易ID")
    private String dealId;

    private String dxFrame;

    private String dxAdtype;

    private String o2Url;

    private String dxRq;

    private Integer isAttribution;

    private Double attributionRatio;

    private Integer platform;
    @ApiModelProperty("是否IP频次控制")
    private Integer isFrequencyIp;
    @ApiModelProperty("IP频次控制周期")
    private Integer frequencyNumIp;
    @ApiModelProperty("IP频次")
    private Integer frequencyPeriodIp;

    private Integer filterApp;

    private Integer versioncode;

    private String versionname;

    private Integer size;

    private String sign;

    private String md5;

    private Integer minsdklevel;
    
    private String dxModel;

    private String isRedirect;
    @ApiModelProperty("定向页面属性-频道")
    private String dxChannel;
    @ApiModelProperty("定向页面属性-车级别集合")
    private String dxGgwLevelId;
    @ApiModelProperty("dx_ggw_series_id")
    private String dxGgwSeriesId;
    @ApiModelProperty("定向页面属性-价格标签集合")
    private String dxGgwPriceTagId;
    @ApiModelProperty("定向地区前置保存")
    private String preDq;
    @ApiModelProperty("定向-ip地域")
    private String dxDq;
    @ApiModelProperty("定向页面属性-地域ID集合")
    private String dxGgwAreaId;
    @ApiModelProperty("dmp定向")
    private String dxDmp;
    
    private String putStates;
    
    @ApiModelProperty("计划名称")
    private String planName;
    @ApiModelProperty("客户名称")
    private String adverName;
    @ApiModelProperty("广告位名称")
    private String adPositionName;
    @ApiModelProperty("终端类型名称")
    private String zdlxName;
    @ApiModelProperty("展现形式名称")
    private String showName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPutName() {
        return putName;
    }

    public void setPutName(String putName) {
        this.putName = putName;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPutType() {
        return putType;
    }

    public void setPutType(Integer putType) {
        this.putType = putType;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public Integer getExtensionType() {
        return extensionType;
    }

    public void setExtensionType(Integer extensionType) {
        this.extensionType = extensionType;
    }

    public String getLandUrl() {
        return landUrl;
    }

    public void setLandUrl(String landUrl) {
        this.landUrl = landUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Integer getDqRule() {
        return dqRule;
    }

    public void setDqRule(Integer dqRule) {
        this.dqRule = dqRule;
    }

    public String getDxXl() {
        return dxXl;
    }

    public void setDxXl(String dxXl) {
        this.dxXl = dxXl;
    }

    public String getDxNl() {
        return dxNl;
    }

    public void setDxNl(String dxNl) {
        this.dxNl = dxNl;
    }

    public String getDxXb() {
        return dxXb;
    }

    public void setDxXb(String dxXb) {
        this.dxXb = dxXb;
    }

    public String getDxZd() {
        return dxZd;
    }

    public void setDxZd(String dxZd) {
        this.dxZd = dxZd;
    }

    public String getDxXw() {
        return dxXw;
    }

    public void setDxXw(String dxXw) {
        this.dxXw = dxXw;
    }

    public String getDxXq() {
        return dxXq;
    }

    public void setDxXq(String dxXq) {
        this.dxXq = dxXq;
    }

    public String getDxCzxt() {
        return dxCzxt;
    }

    public void setDxCzxt(String dxCzxt) {
        this.dxCzxt = dxCzxt;
    }

    public String getDxYys() {
        return dxYys;
    }

    public void setDxYys(String dxYys) {
        this.dxYys = dxYys;
    }

    public String getDxWl() {
        return dxWl;
    }

    public void setDxWl(String dxWl) {
        this.dxWl = dxWl;
    }

    public String getDxLlly() {
        return dxLlly;
    }

    public void setDxLlly(String dxLlly) {
        this.dxLlly = dxLlly;
    }

    public String getDxZdlx() {
        return dxZdlx;
    }

    public void setDxZdlx(String dxZdlx) {
        this.dxZdlx = dxZdlx;
    }

    public String getDxSb() {
        return dxSb;
    }

    public void setDxSb(String dxSb) {
        this.dxSb = dxSb;
    }

    public Integer getDxMediaType() {
		return dxMediaType;
	}

	public void setDxMediaType(Integer dxMediaType) {
		this.dxMediaType = dxMediaType;
	}

	public String getDxMedia() {
        return dxMedia;
    }

    public void setDxMedia(String dxMedia) {
        this.dxMedia = dxMedia;
    }

    public String getDxApp() {
        return dxApp;
    }

    public void setDxApp(String dxApp) {
        this.dxApp = dxApp;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public Integer getPutState() {
        return putState;
    }

    public void setPutState(Integer putState) {
        this.putState = putState;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAdPosition() {
        return adPosition;
    }

    public void setAdPosition(Integer adPosition) {
        this.adPosition = adPosition;
    }

    public String getImpMonitorUrl() {
        return impMonitorUrl;
    }

    public void setImpMonitorUrl(String impMonitorUrl) {
        this.impMonitorUrl = impMonitorUrl;
    }

    public String getClkMonitorUrl() {
        return clkMonitorUrl;
    }

    public void setClkMonitorUrl(String clkMonitorUrl) {
        this.clkMonitorUrl = clkMonitorUrl;
    }

    public String getExtCreativeId() {
        return extCreativeId;
    }

    public void setExtCreativeId(String extCreativeId) {
        this.extCreativeId = extCreativeId;
    }

    public Integer getPutLimit() {
        return putLimit;
    }

    public void setPutLimit(Integer putLimit) {
        this.putLimit = putLimit;
    }

    public Integer getOpUser() {
        return opUser;
    }

    public void setOpUser(Integer opUser) {
        this.opUser = opUser;
    }

    public Integer getOpUserType() {
        return opUserType;
    }

    public void setOpUserType(Integer opUserType) {
        this.opUserType = opUserType;
    }

    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Integer getIsFrequency() {
        return isFrequency;
    }

    public void setIsFrequency(Integer isFrequency) {
        this.isFrequency = isFrequency;
    }

    public Integer getFrequencPeriod() {
        return frequencPeriod;
    }

    public void setFrequencPeriod(Integer frequencPeriod) {
        this.frequencPeriod = frequencPeriod;
    }

    public Integer getFrequencNum() {
        return frequencNum;
    }

    public void setFrequencNum(Integer frequencNum) {
        this.frequencNum = frequencNum;
    }

    public String getLimits() {
        return limits;
    }

    public void setLimits(String limits) {
        this.limits = limits;
    }

    public Integer getIsOptimize() {
        return isOptimize;
    }

    public void setIsOptimize(Integer isOptimize) {
        this.isOptimize = isOptimize;
    }

    public Integer getOptimizeCpa() {
        return optimizeCpa;
    }

    public void setOptimizeCpa(Integer optimizeCpa) {
        this.optimizeCpa = optimizeCpa;
    }

    public String getDxAppType() {
        return dxAppType;
    }

    public void setDxAppType(String dxAppType) {
        this.dxAppType = dxAppType;
    }

    public Integer getIsfilterDeviceCode() {
        return isfilterDeviceCode;
    }

    public void setIsfilterDeviceCode(Integer isfilterDeviceCode) {
        this.isfilterDeviceCode = isfilterDeviceCode;
    }

    public Integer getIsPdb() {
        return isPdb;
    }

    public void setIsPdb(Integer isPdb) {
        this.isPdb = isPdb;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDxFrame() {
        return dxFrame;
    }

    public void setDxFrame(String dxFrame) {
        this.dxFrame = dxFrame;
    }

    public String getDxAdtype() {
        return dxAdtype;
    }

    public void setDxAdtype(String dxAdtype) {
        this.dxAdtype = dxAdtype;
    }

    public String getO2Url() {
        return o2Url;
    }

    public void setO2Url(String o2Url) {
        this.o2Url = o2Url;
    }

    public String getDxRq() {
        return dxRq;
    }

    public void setDxRq(String dxRq) {
        this.dxRq = dxRq;
    }

    public Integer getIsAttribution() {
        return isAttribution;
    }

    public void setIsAttribution(Integer isAttribution) {
        this.isAttribution = isAttribution;
    }

    public Double getAttributionRatio() {
        return attributionRatio;
    }

    public void setAttributionRatio(Double attributionRatio) {
        this.attributionRatio = attributionRatio;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getIsFrequencyIp() {
        return isFrequencyIp;
    }

    public void setIsFrequencyIp(Integer isFrequencyIp) {
        this.isFrequencyIp = isFrequencyIp;
    }

    public Integer getFrequencyNumIp() {
        return frequencyNumIp;
    }

    public void setFrequencyNumIp(Integer frequencyNumIp) {
        this.frequencyNumIp = frequencyNumIp;
    }

    public Integer getFrequencyPeriodIp() {
        return frequencyPeriodIp;
    }

    public void setFrequencyPeriodIp(Integer frequencyPeriodIp) {
        this.frequencyPeriodIp = frequencyPeriodIp;
    }

    public Integer getFilterApp() {
        return filterApp;
    }

    public void setFilterApp(Integer filterApp) {
        this.filterApp = filterApp;
    }

    public Integer getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(Integer versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getMinsdklevel() {
        return minsdklevel;
    }

    public void setMinsdklevel(Integer minsdklevel) {
        this.minsdklevel = minsdklevel;
    }

    public String getDxModel() {
        return dxModel;
    }

    public void setDxModel(String dxModel) {
        this.dxModel = dxModel;
    }

    public String getIsRedirect() {
        return isRedirect;
    }

    public void setIsRedirect(String isRedirect) {
        this.isRedirect = isRedirect;
    }

    public String getDxChannel() {
        return dxChannel;
    }

    public void setDxChannel(String dxChannel) {
        this.dxChannel = dxChannel;
    }

    public String getDxGgwLevelId() {
        return dxGgwLevelId;
    }

    public void setDxGgwLevelId(String dxGgwLevelId) {
        this.dxGgwLevelId = dxGgwLevelId;
    }

    public String getDxGgwSeriesId() {
        return dxGgwSeriesId;
    }

    public void setDxGgwSeriesId(String dxGgwSeriesId) {
        this.dxGgwSeriesId = dxGgwSeriesId;
    }

    public String getDxGgwPriceTagId() {
        return dxGgwPriceTagId;
    }

    public void setDxGgwPriceTagId(String dxGgwPriceTagId) {
        this.dxGgwPriceTagId = dxGgwPriceTagId;
    }
    
    public String getPreDq() {
		return preDq;
	}

	public void setPreDq(String preDq) {
		this.preDq = preDq;
	}

	public String getDxDq() {
		return dxDq;
	}

	public void setDxDq(String dxDq) {
		this.dxDq = dxDq;
	}

	public String getDxGgwAreaId() {
        return dxGgwAreaId;
    }

    public void setDxGgwAreaId(String dxGgwAreaId) {
        this.dxGgwAreaId = dxGgwAreaId;
    }

	public String getDxDmp() {
		return dxDmp;
	}

	public void setDxDmp(String dxDmp) {
		this.dxDmp = dxDmp;
	}

	public String getPutStates() {
		return putStates;
	}

	public void setPutStates(String putStates) {
		this.putStates = putStates;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getAdverName() {
		return adverName;
	}

	public void setAdverName(String adverName) {
		this.adverName = adverName;
	}

	public String getAdPositionName() {
		return adPositionName;
	}

	public void setAdPositionName(String adPositionName) {
		this.adPositionName = adPositionName;
	}

	public String getZdlxName() {
		return zdlxName;
	}

	public void setZdlxName(String zdlxName) {
		this.zdlxName = zdlxName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
    
}
package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OrderPutAll {
    private Integer id;

    private String putName;

    private Integer oid;

    private String pids;

    private Date beginTime;

    private Date endTime;

    private Integer costType;

    private Integer extensionType;

    private String landUrl;

    private String appId;

    private String pkgName;

    private String timeInterval;

    private Integer dqRule;

    private String dxXl;

    private String dxNl;

    private String dxXb;

    private String dxZd;

    private String dxXw;

    private String dxXq;

    private String dxCzxt;

    private String dxYys;

    private String dxWl;

    private String dxLlly;

    private String dxZdlx;

    private String dxSb;

    private String dxBb;

    private String dxQd;

    private String dxJx;

    private String dxTs;

    private String dxMedia;

    private String dxApp;

    private Integer runState;

    private Integer putState;

    private Integer createUser;

    private Date createTime;

    private Date updateTime;

    private Integer price;

    private String adPosition;

    private String impMonitorUrl;

    private String clkMonitorUrl;

    private String extCreativeId;

    private Integer putLimit;

    private Integer opUser;

    private Integer opUserType;

    private Integer deliveryMode;

    private Integer isFrequency;

    private Integer frequencPeriod;

    private Integer frequencNum;

    private String limits;

    private Integer isOptimize;

    private Integer optimizeCpa;

    private String dxAppType;

    private Integer isfilterDeviceCode;

    private Integer isPdb;

    private String dealId;

    private String dxFrame;

    private String dxAdtype;

    private String o2Url;

    private String dxRq;

    private Integer isAttribution;

    private Double attributionRatio;

    private Integer platform;

    private Integer isFrequencyIp;

    private Integer frequencyNumIp;

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

    private String preDq;

    private String dxDq;

    private String dxDmp;
    
    private String orderName;

	private String costTypeName;

	private List<AdPositionTime> adPositionTimes;
	
	private Integer positionNum;

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

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
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

    public String getDxBb() {
        return dxBb;
    }

    public void setDxBb(String dxBb) {
        this.dxBb = dxBb;
    }

    public String getDxQd() {
        return dxQd;
    }

    public void setDxQd(String dxQd) {
        this.dxQd = dxQd;
    }

    public String getDxJx() {
        return dxJx;
    }

    public void setDxJx(String dxJx) {
        this.dxJx = dxJx;
    }

    public String getDxTs() {
        return dxTs;
    }

    public void setDxTs(String dxTs) {
        this.dxTs = dxTs;
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

    public String getAdPosition() {
        return adPosition;
    }

    public void setAdPosition(String adPosition) {
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

    public String getDxDmp() {
        return dxDmp;
    }

    public void setDxDmp(String dxDmp) {
        this.dxDmp = dxDmp;
    }

    public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getCostTypeName() {
		return costTypeName;
	}

	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	public List<AdPositionTime> getAdPositionTimes() {
		return adPositionTimes;
	}

	public void setAdPositionTimes(List<AdPositionTime> adPositionTimes) {
		this.adPositionTimes = adPositionTimes;
	}

	public Integer getPositionNum() {
		return positionNum;
	}

	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table order_put_all
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static OrderPutAll.Builder builder() {
        return new OrderPutAll.Builder();
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table order_put_all
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static class Builder {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private OrderPutAll obj;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder() {
            this.obj = new OrderPutAll();
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.id
         *
         * @param id the value for order_put_all.id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder id(Integer id) {
            obj.setId(id);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.put_name
         *
         * @param putName the value for order_put_all.put_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder putName(String putName) {
            obj.setPutName(putName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.oid
         *
         * @param oid the value for order_put_all.oid
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder oid(Integer oid) {
            obj.setOid(oid);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.pids
         *
         * @param pids the value for order_put_all.pids
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder pids(String pids) {
            obj.setPids(pids);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.begin_time
         *
         * @param beginTime the value for order_put_all.begin_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder beginTime(Date beginTime) {
            obj.setBeginTime(beginTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.end_time
         *
         * @param endTime the value for order_put_all.end_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder endTime(Date endTime) {
            obj.setEndTime(endTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.cost_type
         *
         * @param costType the value for order_put_all.cost_type
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder costType(Integer costType) {
            obj.setCostType(costType);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.extension_type
         *
         * @param extensionType the value for order_put_all.extension_type
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder extensionType(Integer extensionType) {
            obj.setExtensionType(extensionType);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.land_url
         *
         * @param landUrl the value for order_put_all.land_url
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder landUrl(String landUrl) {
            obj.setLandUrl(landUrl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.app_id
         *
         * @param appId the value for order_put_all.app_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder appId(String appId) {
            obj.setAppId(appId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.pkg_name
         *
         * @param pkgName the value for order_put_all.pkg_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder pkgName(String pkgName) {
            obj.setPkgName(pkgName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.time_interval
         *
         * @param timeInterval the value for order_put_all.time_interval
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder timeInterval(String timeInterval) {
            obj.setTimeInterval(timeInterval);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dq_rule
         *
         * @param dqRule the value for order_put_all.dq_rule
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dqRule(Integer dqRule) {
            obj.setDqRule(dqRule);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_xl
         *
         * @param dxXl the value for order_put_all.dx_xl
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxXl(String dxXl) {
            obj.setDxXl(dxXl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_nl
         *
         * @param dxNl the value for order_put_all.dx_nl
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxNl(String dxNl) {
            obj.setDxNl(dxNl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_xb
         *
         * @param dxXb the value for order_put_all.dx_xb
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxXb(String dxXb) {
            obj.setDxXb(dxXb);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_zd
         *
         * @param dxZd the value for order_put_all.dx_zd
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxZd(String dxZd) {
            obj.setDxZd(dxZd);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_zdlx
         *
         * @param dxZdlx the value for order_put_all.dx_zdlx
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxZdlx(String dxZdlx) {
            obj.setDxZdlx(dxZdlx);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_xw
         *
         * @param dxXw the value for order_put_all.dx_xw
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxXw(String dxXw) {
            obj.setDxXw(dxXw);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_xq
         *
         * @param dxXq the value for order_put_all.dx_xq
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxXq(String dxXq) {
            obj.setDxXq(dxXq);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_czxt
         *
         * @param dxCzxt the value for order_put_all.dx_czxt
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxCzxt(String dxCzxt) {
            obj.setDxCzxt(dxCzxt);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_yys
         *
         * @param dxYys the value for order_put_all.dx_yys
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxYys(String dxYys) {
            obj.setDxYys(dxYys);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_wl
         *
         * @param dxWl the value for order_put_all.dx_wl
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxWl(String dxWl) {
            obj.setDxWl(dxWl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_llly
         *
         * @param dxLlly the value for order_put_all.dx_llly
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxLlly(String dxLlly) {
            obj.setDxLlly(dxLlly);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_sb
         *
         * @param dxSb the value for order_put_all.dx_sb
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxSb(String dxSb) {
            obj.setDxSb(dxSb);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_bb
         *
         * @param dxBb the value for order_put_all.dx_bb
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxBb(String dxBb) {
            obj.setDxBb(dxBb);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_qd
         *
         * @param dxQd the value for order_put_all.dx_qd
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxQd(String dxQd) {
            obj.setDxQd(dxQd);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_jx
         *
         * @param dxJx the value for order_put_all.dx_jx
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxJx(String dxJx) {
            obj.setDxJx(dxJx);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_ts
         *
         * @param dxTs the value for order_put_all.dx_ts
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxTs(String dxTs) {
            obj.setDxTs(dxTs);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_media
         *
         * @param dxMedia the value for order_put_all.dx_media
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxMedia(String dxMedia) {
            obj.setDxMedia(dxMedia);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_app
         *
         * @param dxApp the value for order_put_all.dx_app
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxApp(String dxApp) {
            obj.setDxApp(dxApp);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_app_type
         *
         * @param dxAppType the value for order_put_all.dx_app_type
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxAppType(String dxAppType) {
            obj.setDxAppType(dxAppType);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.run_state
         *
         * @param runState the value for order_put_all.run_state
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder runState(Integer runState) {
            obj.setRunState(runState);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.put_state
         *
         * @param putState the value for order_put_all.put_state
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder putState(Integer putState) {
            obj.setPutState(putState);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.create_user
         *
         * @param createUser the value for order_put_all.create_user
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder createUser(Integer createUser) {
            obj.setCreateUser(createUser);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.create_time
         *
         * @param createTime the value for order_put_all.create_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder createTime(Date createTime) {
            obj.setCreateTime(createTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.update_time
         *
         * @param updateTime the value for order_put_all.update_time
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder updateTime(Date updateTime) {
            obj.setUpdateTime(updateTime);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.price
         *
         * @param price the value for order_put_all.price
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder price(Integer price) {
            obj.setPrice(price);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.ad_position
         *
         * @param adPosition the value for order_put_all.ad_position
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder adPosition(String adPosition) {
            obj.setAdPosition(adPosition);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.imp_monitor_url
         *
         * @param impMonitorUrl the value for order_put_all.imp_monitor_url
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder impMonitorUrl(String impMonitorUrl) {
            obj.setImpMonitorUrl(impMonitorUrl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.clk_monitor_url
         *
         * @param clkMonitorUrl the value for order_put_all.clk_monitor_url
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder clkMonitorUrl(String clkMonitorUrl) {
            obj.setClkMonitorUrl(clkMonitorUrl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.ext_creative_id
         *
         * @param extCreativeId the value for order_put_all.ext_creative_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder extCreativeId(String extCreativeId) {
            obj.setExtCreativeId(extCreativeId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.put_limit
         *
         * @param putLimit the value for order_put_all.put_limit
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder putLimit(Integer putLimit) {
            obj.setPutLimit(putLimit);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.op_user
         *
         * @param opUser the value for order_put_all.op_user
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder opUser(Integer opUser) {
            obj.setOpUser(opUser);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.op_user_type
         *
         * @param opUserType the value for order_put_all.op_user_type
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder opUserType(Integer opUserType) {
            obj.setOpUserType(opUserType);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.delivery_mode
         *
         * @param deliveryMode the value for order_put_all.delivery_mode
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder deliveryMode(Integer deliveryMode) {
            obj.setDeliveryMode(deliveryMode);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.is_frequency
         *
         * @param isFrequency the value for order_put_all.is_frequency
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isFrequency(Integer isFrequency) {
            obj.setIsFrequency(isFrequency);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.is_frequency_ip
         *
         * @param isFrequencyIp the value for order_put_all.is_frequency_ip
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isFrequencyIp(Integer isFrequencyIp) {
            obj.setIsFrequencyIp(isFrequencyIp);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.frequenc_period
         *
         * @param frequencPeriod the value for order_put_all.frequenc_period
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder frequencPeriod(Integer frequencPeriod) {
            obj.setFrequencPeriod(frequencPeriod);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.frequenc_num
         *
         * @param frequencNum the value for order_put_all.frequenc_num
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder frequencNum(Integer frequencNum) {
            obj.setFrequencNum(frequencNum);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.limits
         *
         * @param limits the value for order_put_all.limits
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder limits(String limits) {
            obj.setLimits(limits);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.is_optimize
         *
         * @param isOptimize the value for order_put_all.is_optimize
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isOptimize(Integer isOptimize) {
            obj.setIsOptimize(isOptimize);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.optimize_cpa
         *
         * @param optimizeCpa the value for order_put_all.optimize_cpa
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder optimizeCpa(Integer optimizeCpa) {
            obj.setOptimizeCpa(optimizeCpa);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.isfilter_device_code
         *
         * @param isfilterDeviceCode the value for order_put_all.isfilter_device_code
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isfilterDeviceCode(Integer isfilterDeviceCode) {
            obj.setIsfilterDeviceCode(isfilterDeviceCode);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.is_pdb
         *
         * @param isPdb the value for order_put_all.is_pdb
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isPdb(Integer isPdb) {
            obj.setIsPdb(isPdb);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.deal_id
         *
         * @param dealId the value for order_put_all.deal_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dealId(String dealId) {
            obj.setDealId(dealId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_frame
         *
         * @param dxFrame the value for order_put_all.dx_frame
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxFrame(String dxFrame) {
            obj.setDxFrame(dxFrame);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_adType
         *
         * @param dxAdtype the value for order_put_all.dx_adType
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxAdtype(String dxAdtype) {
            obj.setDxAdtype(dxAdtype);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.o2_url
         *
         * @param o2Url the value for order_put_all.o2_url
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder o2Url(String o2Url) {
            obj.setO2Url(o2Url);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_rq
         *
         * @param dxRq the value for order_put_all.dx_rq
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxRq(String dxRq) {
            obj.setDxRq(dxRq);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.is_attribution
         *
         * @param isAttribution the value for order_put_all.is_attribution
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isAttribution(Integer isAttribution) {
            obj.setIsAttribution(isAttribution);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.attribution_ratio
         *
         * @param attributionRatio the value for order_put_all.attribution_ratio
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder attributionRatio(Double attributionRatio) {
            obj.setAttributionRatio(attributionRatio);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.platform
         *
         * @param platform the value for order_put_all.platform
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder platform(Integer platform) {
            obj.setPlatform(platform);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.frequency_num_ip
         *
         * @param frequencyNumIp the value for order_put_all.frequency_num_ip
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder frequencyNumIp(Integer frequencyNumIp) {
            obj.setFrequencyNumIp(frequencyNumIp);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.frequency_period_ip
         *
         * @param frequencyPeriodIp the value for order_put_all.frequency_period_ip
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder frequencyPeriodIp(Integer frequencyPeriodIp) {
            obj.setFrequencyPeriodIp(frequencyPeriodIp);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.filter_app
         *
         * @param filterApp the value for order_put_all.filter_app
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder filterApp(Integer filterApp) {
            obj.setFilterApp(filterApp);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.versioncode
         *
         * @param versioncode the value for order_put_all.versioncode
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder versioncode(Integer versioncode) {
            obj.setVersioncode(versioncode);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.versionname
         *
         * @param versionname the value for order_put_all.versionname
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder versionname(String versionname) {
            obj.setVersionname(versionname);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.size
         *
         * @param size the value for order_put_all.size
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder size(Integer size) {
            obj.setSize(size);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.sign
         *
         * @param sign the value for order_put_all.sign
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder sign(String sign) {
            obj.setSign(sign);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.md5
         *
         * @param md5 the value for order_put_all.md5
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder md5(String md5) {
            obj.setMd5(md5);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.minsdklevel
         *
         * @param minsdklevel the value for order_put_all.minsdklevel
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder minsdklevel(Integer minsdklevel) {
            obj.setMinsdklevel(minsdklevel);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_model
         *
         * @param dxModel the value for order_put_all.dx_model
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxModel(String dxModel) {
            obj.setDxModel(dxModel);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.is_redirect
         *
         * @param isRedirect the value for order_put_all.is_redirect
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isRedirect(String isRedirect) {
            obj.setIsRedirect(isRedirect);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.pre_dq
         *
         * @param preDq the value for order_put_all.pre_dq
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder preDq(String preDq) {
            obj.setPreDq(preDq);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_dq
         *
         * @param dxDq the value for order_put_all.dx_dq
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxDq(String dxDq) {
            obj.setDxDq(dxDq);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column order_put_all.dx_dmp
         *
         * @param dxDmp the value for order_put_all.dx_dmp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxDmp(String dxDmp) {
            obj.setDxDmp(dxDmp);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public OrderPutAll build() {
            return this.obj;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table order_put_all
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        putName("put_name", "putName", "VARCHAR", false),
        oid("oid", "oid", "INTEGER", false),
        pids("pids", "pids", "VARCHAR", false),
        beginTime("begin_time", "beginTime", "TIMESTAMP", false),
        endTime("end_time", "endTime", "TIMESTAMP", false),
        costType("cost_type", "costType", "INTEGER", false),
        extensionType("extension_type", "extensionType", "INTEGER", false),
        landUrl("land_url", "landUrl", "VARCHAR", false),
        appId("app_id", "appId", "VARCHAR", false),
        pkgName("pkg_name", "pkgName", "VARCHAR", false),
        timeInterval("time_interval", "timeInterval", "VARCHAR", false),
        dqRule("dq_rule", "dqRule", "INTEGER", false),
        dxXl("dx_xl", "dxXl", "VARCHAR", false),
        dxNl("dx_nl", "dxNl", "VARCHAR", false),
        dxXb("dx_xb", "dxXb", "VARCHAR", false),
        dxZd("dx_zd", "dxZd", "VARCHAR", false),
        dxXw("dx_xw", "dxXw", "VARCHAR", false),
        dxXq("dx_xq", "dxXq", "VARCHAR", false),
        dxCzxt("dx_czxt", "dxCzxt", "VARCHAR", false),
        dxYys("dx_yys", "dxYys", "VARCHAR", false),
        dxWl("dx_wl", "dxWl", "VARCHAR", false),
        dxLlly("dx_llly", "dxLlly", "VARCHAR", false),
        dxZdlx("dx_zdlx", "dxZdlx", "VARCHAR", false),
        dxSb("dx_sb", "dxSb", "VARCHAR", false),
        dxBb("dx_bb", "dxBb", "VARCHAR", false),
        dxQd("dx_qd", "dxQd", "VARCHAR", false),
        dxJx("dx_jx", "dxJx", "VARCHAR", false),
        dxTs("dx_ts", "dxTs", "VARCHAR", false),
        dxMedia("dx_media", "dxMedia", "VARCHAR", false),
        dxApp("dx_app", "dxApp", "VARCHAR", false),
        runState("run_state", "runState", "INTEGER", false),
        putState("put_state", "putState", "INTEGER", false),
        createUser("create_user", "createUser", "INTEGER", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        price("price", "price", "INTEGER", false),
        adPosition("ad_position", "adPosition", "VARCHAR", false),
        impMonitorUrl("imp_monitor_url", "impMonitorUrl", "VARCHAR", false),
        clkMonitorUrl("clk_monitor_url", "clkMonitorUrl", "VARCHAR", false),
        extCreativeId("ext_creative_id", "extCreativeId", "VARCHAR", false),
        putLimit("put_limit", "putLimit", "INTEGER", false),
        opUser("op_user", "opUser", "INTEGER", false),
        opUserType("op_user_type", "opUserType", "INTEGER", false),
        deliveryMode("delivery_mode", "deliveryMode", "INTEGER", false),
        isFrequency("is_frequency", "isFrequency", "INTEGER", false),
        frequencPeriod("frequenc_period", "frequencPeriod", "INTEGER", false),
        frequencNum("frequenc_num", "frequencNum", "INTEGER", false),
        limits("limits", "limits", "VARCHAR", false),
        isOptimize("is_optimize", "isOptimize", "INTEGER", false),
        optimizeCpa("optimize_cpa", "optimizeCpa", "INTEGER", false),
        dxAppType("dx_app_type", "dxAppType", "VARCHAR", false),
        isfilterDeviceCode("isfilter_device_code", "isfilterDeviceCode", "INTEGER", false),
        isPdb("is_pdb", "isPdb", "INTEGER", false),
        dealId("deal_id", "dealId", "VARCHAR", false),
        dxFrame("dx_frame", "dxFrame", "VARCHAR", false),
        dxAdtype("dx_adType", "dxAdtype", "VARCHAR", false),
        o2Url("o2_url", "o2Url", "VARCHAR", false),
        dxRq("dx_rq", "dxRq", "VARCHAR", false),
        isAttribution("is_attribution", "isAttribution", "INTEGER", false),
        attributionRatio("attribution_ratio", "attributionRatio", "DOUBLE", false),
        platform("platform", "platform", "INTEGER", false),
        isFrequencyIp("is_frequency_ip", "isFrequencyIp", "INTEGER", false),
        frequencyNumIp("frequency_num_ip", "frequencyNumIp", "INTEGER", false),
        frequencyPeriodIp("frequency_period_ip", "frequencyPeriodIp", "INTEGER", false),
        filterApp("filter_app", "filterApp", "INTEGER", false),
        versioncode("versioncode", "versioncode", "INTEGER", false),
        versionname("versionname", "versionname", "VARCHAR", false),
        size("size", "size", "INTEGER", false),
        sign("sign", "sign", "VARCHAR", false),
        md5("md5", "md5", "VARCHAR", false),
        minsdklevel("minsdklevel", "minsdklevel", "INTEGER", false),
        dxModel("dx_model", "dxModel", "VARCHAR", false),
        isRedirect("is_redirect", "isRedirect", "VARCHAR", false),
        preDq("pre_dq", "preDq", "LONGVARCHAR", false),
        dxDq("dx_dq", "dxDq", "LONGVARCHAR", false),
        dxDmp("dx_dmp", "dxDmp", "LONGVARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table order_put_all
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}
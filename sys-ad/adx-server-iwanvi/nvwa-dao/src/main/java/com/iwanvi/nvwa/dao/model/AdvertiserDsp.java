package com.iwanvi.nvwa.dao.model;

import java.util.Date;

public class AdvertiserDsp {
    private Integer id;

    private String name;

    private Integer flowConsumerId;

    private String consumerName;

    private String dspAdvertiserId;

    private Integer status;

    private Integer examinesStatus;

    private Integer examinesUser;

    private Date examinesTime;

    private String examinesRemarks;

    private String businesslicense;

    private String qualifications;

    private Integer industryType;

    private String linkman;

    private String tel;

    private String address;

    private Date createTime;

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

    public Integer getFlowConsumerId() {
        return flowConsumerId;
    }

    public void setFlowConsumerId(Integer flowConsumerId) {
        this.flowConsumerId = flowConsumerId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getDspAdvertiserId() {
        return dspAdvertiserId;
    }

    public void setDspAdvertiserId(String dspAdvertiserId) {
        this.dspAdvertiserId = dspAdvertiserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExaminesStatus() {
        return examinesStatus;
    }

    public void setExaminesStatus(Integer examinesStatus) {
        this.examinesStatus = examinesStatus;
    }

    public Integer getExaminesUser() {
        return examinesUser;
    }

    public void setExaminesUser(Integer examinesUser) {
        this.examinesUser = examinesUser;
    }

    public Date getExaminesTime() {
        return examinesTime;
    }

    public void setExaminesTime(Date examinesTime) {
        this.examinesTime = examinesTime;
    }

    public String getExaminesRemarks() {
        return examinesRemarks;
    }

    public void setExaminesRemarks(String examinesRemarks) {
        this.examinesRemarks = examinesRemarks;
    }

    public String getBusinesslicense() {
        return businesslicense;
    }

    public void setBusinesslicense(String businesslicense) {
        this.businesslicense = businesslicense;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public Integer getIndustryType() {
        return industryType;
    }

    public void setIndustryType(Integer industryType) {
        this.industryType = industryType;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
package com.iwanvi.nvwa.dao.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("审核实体类")
public class AdxRelation {
	@ApiModelProperty("id")
    private Integer id;
	@ApiModelProperty("被审核对象id")
    private Integer objId;
	@ApiModelProperty("被审核对象类型")
    private Integer objType;
	@ApiModelProperty("审核媒体id")
    private Integer adxType;
	@ApiModelProperty("upid")
    private String adxUpid;
	@ApiModelProperty("crid")
    private String adxCrid;
	@ApiModelProperty("审核素材地址")
    private String adxUrl;
	@ApiModelProperty("审核状态")
    private Integer auditState;
	@ApiModelProperty("审核备注")
    private String auditComments;
	@ApiModelProperty("状态")
    private Integer status;
	@ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("行业")
    private Integer industry;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public Integer getObjType() {
        return objType;
    }

    public void setObjType(Integer objType) {
        this.objType = objType;
    }

    public Integer getAdxType() {
        return adxType;
    }

    public void setAdxType(Integer adxType) {
        this.adxType = adxType;
    }

    public String getAdxUpid() {
        return adxUpid;
    }

    public void setAdxUpid(String adxUpid) {
        this.adxUpid = adxUpid;
    }

    public String getAdxCrid() {
        return adxCrid;
    }

    public void setAdxCrid(String adxCrid) {
        this.adxCrid = adxCrid;
    }

    public String getAdxUrl() {
        return adxUrl;
    }

    public void setAdxUrl(String adxUrl) {
        this.adxUrl = adxUrl;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getAuditComments() {
        return auditComments;
    }

    public void setAuditComments(String auditComments) {
        this.auditComments = auditComments;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }
}
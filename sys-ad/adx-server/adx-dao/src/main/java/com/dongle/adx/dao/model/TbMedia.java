package com.dongle.adx.dao.model;

import java.io.Serializable;
import java.util.Date;

public class TbMedia implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.cp_id
     *
     * @mbg.generated
     */
    private String cpId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.type
     *
     * @mbg.generated
     */
    private Integer type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.trade_type
     *
     * @mbg.generated
     */
    private Integer tradeType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.status
     *
     * @mbg.generated
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.audit_status
     *
     * @mbg.generated
     */
    private Integer auditStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.audit_user
     *
     * @mbg.generated
     */
    private String auditUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.audit_msg
     *
     * @mbg.generated
     */
    private String auditMsg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.url
     *
     * @mbg.generated
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.tags
     *
     * @mbg.generated
     */
    private String tags;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.version
     *
     * @mbg.generated
     */
    private String version;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.bundle
     *
     * @mbg.generated
     */
    private String bundle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.op_user
     *
     * @mbg.generated
     */
    private String opUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.create_date
     *
     * @mbg.generated
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_media.update_date
     *
     * @mbg.generated
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.id
     *
     * @return the value of tb_media.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.id
     *
     * @param id the value for tb_media.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.name
     *
     * @return the value of tb_media.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.name
     *
     * @param name the value for tb_media.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.cp_id
     *
     * @return the value of tb_media.cp_id
     *
     * @mbg.generated
     */
    public String getCpId() {
        return cpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.cp_id
     *
     * @param cpId the value for tb_media.cp_id
     *
     * @mbg.generated
     */
    public void setCpId(String cpId) {
        this.cpId = cpId == null ? null : cpId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.type
     *
     * @return the value of tb_media.type
     *
     * @mbg.generated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.type
     *
     * @param type the value for tb_media.type
     *
     * @mbg.generated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.trade_type
     *
     * @return the value of tb_media.trade_type
     *
     * @mbg.generated
     */
    public Integer getTradeType() {
        return tradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.trade_type
     *
     * @param tradeType the value for tb_media.trade_type
     *
     * @mbg.generated
     */
    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.status
     *
     * @return the value of tb_media.status
     *
     * @mbg.generated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.status
     *
     * @param status the value for tb_media.status
     *
     * @mbg.generated
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.audit_status
     *
     * @return the value of tb_media.audit_status
     *
     * @mbg.generated
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.audit_status
     *
     * @param auditStatus the value for tb_media.audit_status
     *
     * @mbg.generated
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.audit_user
     *
     * @return the value of tb_media.audit_user
     *
     * @mbg.generated
     */
    public String getAuditUser() {
        return auditUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.audit_user
     *
     * @param auditUser the value for tb_media.audit_user
     *
     * @mbg.generated
     */
    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.audit_msg
     *
     * @return the value of tb_media.audit_msg
     *
     * @mbg.generated
     */
    public String getAuditMsg() {
        return auditMsg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.audit_msg
     *
     * @param auditMsg the value for tb_media.audit_msg
     *
     * @mbg.generated
     */
    public void setAuditMsg(String auditMsg) {
        this.auditMsg = auditMsg == null ? null : auditMsg.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.url
     *
     * @return the value of tb_media.url
     *
     * @mbg.generated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.url
     *
     * @param url the value for tb_media.url
     *
     * @mbg.generated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.tags
     *
     * @return the value of tb_media.tags
     *
     * @mbg.generated
     */
    public String getTags() {
        return tags;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.tags
     *
     * @param tags the value for tb_media.tags
     *
     * @mbg.generated
     */
    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.version
     *
     * @return the value of tb_media.version
     *
     * @mbg.generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.version
     *
     * @param version the value for tb_media.version
     *
     * @mbg.generated
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.bundle
     *
     * @return the value of tb_media.bundle
     *
     * @mbg.generated
     */
    public String getBundle() {
        return bundle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.bundle
     *
     * @param bundle the value for tb_media.bundle
     *
     * @mbg.generated
     */
    public void setBundle(String bundle) {
        this.bundle = bundle == null ? null : bundle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.op_user
     *
     * @return the value of tb_media.op_user
     *
     * @mbg.generated
     */
    public String getOpUser() {
        return opUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.op_user
     *
     * @param opUser the value for tb_media.op_user
     *
     * @mbg.generated
     */
    public void setOpUser(String opUser) {
        this.opUser = opUser == null ? null : opUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.create_date
     *
     * @return the value of tb_media.create_date
     *
     * @mbg.generated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.create_date
     *
     * @param createDate the value for tb_media.create_date
     *
     * @mbg.generated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_media.update_date
     *
     * @return the value of tb_media.update_date
     *
     * @mbg.generated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_media.update_date
     *
     * @param updateDate the value for tb_media.update_date
     *
     * @mbg.generated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
package com.dongle.adx.dao.model;

import java.io.Serializable;
import java.util.Date;

public class TbUser implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.ident
     *
     * @mbg.generated
     */
    private String ident;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.username
     *
     * @mbg.generated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.password
     *
     * @mbg.generated
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.cp_id
     *
     * @mbg.generated
     */
    private String cpId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.is_del
     *
     * @mbg.generated
     */
    private Integer isDel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.op_user
     *
     * @mbg.generated
     */
    private String opUser;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.create_date
     *
     * @mbg.generated
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.update_date
     *
     * @mbg.generated
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_user.remark
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_user
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.id
     *
     * @return the value of tb_user.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.id
     *
     * @param id the value for tb_user.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.ident
     *
     * @return the value of tb_user.ident
     *
     * @mbg.generated
     */
    public String getIdent() {
        return ident;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.ident
     *
     * @param ident the value for tb_user.ident
     *
     * @mbg.generated
     */
    public void setIdent(String ident) {
        this.ident = ident == null ? null : ident.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.username
     *
     * @return the value of tb_user.username
     *
     * @mbg.generated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.username
     *
     * @param username the value for tb_user.username
     *
     * @mbg.generated
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.password
     *
     * @return the value of tb_user.password
     *
     * @mbg.generated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.password
     *
     * @param password the value for tb_user.password
     *
     * @mbg.generated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.cp_id
     *
     * @return the value of tb_user.cp_id
     *
     * @mbg.generated
     */
    public String getCpId() {
        return cpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.cp_id
     *
     * @param cpId the value for tb_user.cp_id
     *
     * @mbg.generated
     */
    public void setCpId(String cpId) {
        this.cpId = cpId == null ? null : cpId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.is_del
     *
     * @return the value of tb_user.is_del
     *
     * @mbg.generated
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.is_del
     *
     * @param isDel the value for tb_user.is_del
     *
     * @mbg.generated
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.op_user
     *
     * @return the value of tb_user.op_user
     *
     * @mbg.generated
     */
    public String getOpUser() {
        return opUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.op_user
     *
     * @param opUser the value for tb_user.op_user
     *
     * @mbg.generated
     */
    public void setOpUser(String opUser) {
        this.opUser = opUser == null ? null : opUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.create_date
     *
     * @return the value of tb_user.create_date
     *
     * @mbg.generated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.create_date
     *
     * @param createDate the value for tb_user.create_date
     *
     * @mbg.generated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.update_date
     *
     * @return the value of tb_user.update_date
     *
     * @mbg.generated
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.update_date
     *
     * @param updateDate the value for tb_user.update_date
     *
     * @mbg.generated
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user.remark
     *
     * @return the value of tb_user.remark
     *
     * @mbg.generated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user.remark
     *
     * @param remark the value for tb_user.remark
     *
     * @mbg.generated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class FlowConsumer {
    private Integer id;

    private String consumerName;

    private String consumerUuid;

    private Integer consumerType;

    private String rtbUrl;

    private Integer runState;

    private Integer createUser;

    private Date createTime;

    private Date updateTime;

    private String companyName;

    private String companyAddr;

    private String companyLinkman;

    private String linkmanTel;

    private String adposId;

    private Integer consumerState;

    private Integer qps;

    private String remark;

    private String cookiemappingurl;

    private String dspId;

    private String token;

    private Integer isPrivate;

    private Integer isDeal;

    private String adpositionPrice;

    private Integer isCheck;

    private String dxDid;

    private Integer isTest;

    private String consumerMark;

    private String typeName;
    
    private Integer terminal;       
    
    public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTerminal() {
		return terminal;
	}

	public void setTerminal(Integer terminal) {
		this.terminal = terminal;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getConsumerUuid() {
        return consumerUuid;
    }

    public void setConsumerUuid(String consumerUuid) {
        this.consumerUuid = consumerUuid;
    }

    public Integer getConsumerType() {
        return consumerType;
    }

    public void setConsumerType(Integer consumerType) {
        this.consumerType = consumerType;
    }

    public String getRtbUrl() {
        return rtbUrl;
    }

    public void setRtbUrl(String rtbUrl) {
        this.rtbUrl = rtbUrl;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyLinkman() {
        return companyLinkman;
    }

    public void setCompanyLinkman(String companyLinkman) {
        this.companyLinkman = companyLinkman;
    }

    public String getLinkmanTel() {
        return linkmanTel;
    }

    public void setLinkmanTel(String linkmanTel) {
        this.linkmanTel = linkmanTel;
    }

    public String getAdposId() {
        return adposId;
    }

    public void setAdposId(String adposId) {
        this.adposId = adposId;
    }

    public Integer getConsumerState() {
        return consumerState;
    }

    public void setConsumerState(Integer consumerState) {
        this.consumerState = consumerState;
    }

    public Integer getQps() {
        return qps;
    }

    public void setQps(Integer qps) {
        this.qps = qps;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCookiemappingurl() {
        return cookiemappingurl;
    }

    public void setCookiemappingurl(String cookiemappingurl) {
        this.cookiemappingurl = cookiemappingurl;
    }

    public String getDspId() {
        return dspId;
    }

    public void setDspId(String dspId) {
        this.dspId = dspId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Integer isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }

    public String getAdpositionPrice() {
        return adpositionPrice;
    }

    public void setAdpositionPrice(String adpositionPrice) {
        this.adpositionPrice = adpositionPrice;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

    public String getDxDid() {
        return dxDid;
    }

    public void setDxDid(String dxDid) {
        this.dxDid = dxDid;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public String getConsumerMark() {
        return consumerMark;
    }

    public void setConsumerMark(String consumerMark) {
        this.consumerMark = consumerMark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_consumer
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static FlowConsumer.Builder builder() {
        return new FlowConsumer.Builder();
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table flow_consumer
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static class Builder {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private FlowConsumer obj;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder() {
            this.obj = new FlowConsumer();
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.id
         *
         * @param id the value for flow_consumer.id
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
         * This method sets the value of the database column flow_consumer.consumer_name
         *
         * @param consumerName the value for flow_consumer.consumer_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder consumerName(String consumerName) {
            obj.setConsumerName(consumerName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.consumer_uuid
         *
         * @param consumerUuid the value for flow_consumer.consumer_uuid
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder consumerUuid(String consumerUuid) {
            obj.setConsumerUuid(consumerUuid);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.consumer_type
         *
         * @param consumerType the value for flow_consumer.consumer_type
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder consumerType(Integer consumerType) {
            obj.setConsumerType(consumerType);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.rtb_url
         *
         * @param rtbUrl the value for flow_consumer.rtb_url
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder rtbUrl(String rtbUrl) {
            obj.setRtbUrl(rtbUrl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.run_state
         *
         * @param runState the value for flow_consumer.run_state
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
         * This method sets the value of the database column flow_consumer.create_user
         *
         * @param createUser the value for flow_consumer.create_user
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
         * This method sets the value of the database column flow_consumer.create_time
         *
         * @param createTime the value for flow_consumer.create_time
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
         * This method sets the value of the database column flow_consumer.update_time
         *
         * @param updateTime the value for flow_consumer.update_time
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
         * This method sets the value of the database column flow_consumer.company_name
         *
         * @param companyName the value for flow_consumer.company_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder companyName(String companyName) {
            obj.setCompanyName(companyName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.company_addr
         *
         * @param companyAddr the value for flow_consumer.company_addr
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder companyAddr(String companyAddr) {
            obj.setCompanyAddr(companyAddr);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.company_linkman
         *
         * @param companyLinkman the value for flow_consumer.company_linkman
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder companyLinkman(String companyLinkman) {
            obj.setCompanyLinkman(companyLinkman);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.linkman_tel
         *
         * @param linkmanTel the value for flow_consumer.linkman_tel
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder linkmanTel(String linkmanTel) {
            obj.setLinkmanTel(linkmanTel);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.adpos_id
         *
         * @param adposId the value for flow_consumer.adpos_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder adposId(String adposId) {
            obj.setAdposId(adposId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.consumer_state
         *
         * @param consumerState the value for flow_consumer.consumer_state
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder consumerState(Integer consumerState) {
            obj.setConsumerState(consumerState);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.qps
         *
         * @param qps the value for flow_consumer.qps
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder qps(Integer qps) {
            obj.setQps(qps);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.remark
         *
         * @param remark the value for flow_consumer.remark
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder remark(String remark) {
            obj.setRemark(remark);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.cookiemappingurl
         *
         * @param cookiemappingurl the value for flow_consumer.cookiemappingurl
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder cookiemappingurl(String cookiemappingurl) {
            obj.setCookiemappingurl(cookiemappingurl);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.dsp_id
         *
         * @param dspId the value for flow_consumer.dsp_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dspId(String dspId) {
            obj.setDspId(dspId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.token
         *
         * @param token the value for flow_consumer.token
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder token(String token) {
            obj.setToken(token);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.is_private
         *
         * @param isPrivate the value for flow_consumer.is_private
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isPrivate(Integer isPrivate) {
            obj.setIsPrivate(isPrivate);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.is_deal
         *
         * @param isDeal the value for flow_consumer.is_deal
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isDeal(Integer isDeal) {
            obj.setIsDeal(isDeal);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.adposition_price
         *
         * @param adpositionPrice the value for flow_consumer.adposition_price
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder adpositionPrice(String adpositionPrice) {
            obj.setAdpositionPrice(adpositionPrice);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.is_check
         *
         * @param isCheck the value for flow_consumer.is_check
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isCheck(Integer isCheck) {
            obj.setIsCheck(isCheck);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.dx_did
         *
         * @param dxDid the value for flow_consumer.dx_did
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder dxDid(String dxDid) {
            obj.setDxDid(dxDid);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.is_test
         *
         * @param isTest the value for flow_consumer.is_test
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder isTest(Integer isTest) {
            obj.setIsTest(isTest);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column flow_consumer.consumer_mark
         *
         * @param consumerMark the value for flow_consumer.consumer_mark
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder consumerMark(String consumerMark) {
            obj.setConsumerMark(consumerMark);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public FlowConsumer build() {
            return this.obj;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table flow_consumer
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        consumerName("consumer_name", "consumerName", "VARCHAR", false),
        consumerUuid("consumer_uuid", "consumerUuid", "VARCHAR", false),
        consumerType("consumer_type", "consumerType", "INTEGER", false),
        rtbUrl("rtb_url", "rtbUrl", "VARCHAR", false),
        runState("run_state", "runState", "INTEGER", false),
        createUser("create_user", "createUser", "INTEGER", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        companyName("company_name", "companyName", "VARCHAR", false),
        companyAddr("company_addr", "companyAddr", "VARCHAR", false),
        companyLinkman("company_linkman", "companyLinkman", "VARCHAR", false),
        linkmanTel("linkman_tel", "linkmanTel", "VARCHAR", false),
        adposId("adpos_id", "adposId", "VARCHAR", false),
        consumerState("consumer_state", "consumerState", "INTEGER", false),
        qps("qps", "qps", "INTEGER", false),
        remark("remark", "remark", "VARCHAR", false),
        cookiemappingurl("cookiemappingurl", "cookiemappingurl", "VARCHAR", false),
        dspId("dsp_id", "dspId", "VARCHAR", false),
        token("token", "token", "VARCHAR", false),
        isPrivate("is_private", "isPrivate", "INTEGER", false),
        isDeal("is_deal", "isDeal", "INTEGER", false),
        adpositionPrice("adposition_price", "adpositionPrice", "VARCHAR", false),
        isCheck("is_check", "isCheck", "INTEGER", false),
        dxDid("dx_did", "dxDid", "VARCHAR", false),
        isTest("is_test", "isTest", "INTEGER", false),
        consumerMark("consumer_mark", "consumerMark", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
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
         * This method corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table flow_consumer
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
         * This method corresponds to the database table flow_consumer
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
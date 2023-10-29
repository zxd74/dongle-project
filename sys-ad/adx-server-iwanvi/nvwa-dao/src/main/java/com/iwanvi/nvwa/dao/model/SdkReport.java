package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Arrays;

public class SdkReport {
    private Integer id;

    private Integer date;

    private String mediaName;

    private String positionId;

    private String positionName;

    private String positionType;

    private Long exp = 0l;

    private Long clk = 0l;

    private Double ctr = 0d;

    private Double fr = 0d;

    private Double income = 0d;

    private Double cpm = 0d;

    private Double cpc = 0d;

    private String appId;

    private String appName;

    private String linkman;

    private Double ecpm = 0d;

    private Double clkIncome = 0d;

    private Double expIncome = 0d;

    private Integer type;

    private Integer pid;

    private String adpName;

    private Long expUV = 0l;

    private Long clkUV = 0l;

    private Double expPer = 0d;

    private Long req = 0l;

    private Long reqUV = 0l;

    private Double clkDesire = 0d;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getClk() {
        return clk;
    }

    public void setClk(Long clk) {
        this.clk = clk;
    }

    public Double getCtr() {
        if (ctr != null) {
            ctr = Double.parseDouble(String.format("%.2f", ctr * 100));
        }
        return ctr;
    }

    public void setCtr(Double ctr) {
        this.ctr = ctr;
    }

    public Double getFr() {
        return fr;
    }

    public void setFr(Double fr) {
        this.fr = fr;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getCpm() {
        if (cpm != null) {
            cpm = Double.parseDouble(String.format("%.2f", cpm));
        }
        return cpm;
    }

    public void setCpm(Double cpm) {
        this.cpm = cpm;
    }

    public Double getCpc() {
        if (cpc != null) {
            cpc = Double.parseDouble(String.format("%.2f", cpc));
        }
        return cpc;
    }

    public void setCpc(Double cpc) {
        this.cpc = cpc;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Double getEcpm() {
        return ecpm;
    }

    public void setEcpm(Double ecpm) {
        this.ecpm = ecpm;
    }

    public Double getClkIncome() {
        return clkIncome;
    }

    public void setClkIncome(Double clkIncome) {
        this.clkIncome = clkIncome;
    }

    public Double getExpIncome() {
        return expIncome;
    }

    public void setExpIncome(Double expIncome) {
        this.expIncome = expIncome;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Long getExpUV() {
        return expUV;
    }

    public void setExpUV(Long expUV) {
        this.expUV = expUV;
    }

    public Long getClkUV() {
        return clkUV;
    }

    public void setClkUV(Long clkUV) {
        this.clkUV = clkUV;
    }

    public Double getExpPer() {
        return expPer;
    }

    public void setExpPer(Double expPer) {
        this.expPer = expPer;
    }

    public Long getReq() {
        return req;
    }

    public void setReq(Long req) {
        this.req = req;
    }

    public Long getReqUV() {
        return reqUV;
    }

    public void setReqUV(Long reqUV) {
        this.reqUV = reqUV;
    }

    public Double getClkDesire() {
        return clkDesire;
    }

    public void setClkDesire(Double clkDesire) {
        this.clkDesire = clkDesire;
    }

    public String getAdpName() {
        return adpName;
    }

    public void setAdpName(String adpName) {
        this.adpName = adpName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sdk_report
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static SdkReport.Builder builder() {
        return new SdkReport.Builder();
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table sdk_report
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static class Builder {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private SdkReport obj;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder() {
            this.obj = new SdkReport();
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.id
         *
         * @param id the value for sdk_report.id
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
         * This method sets the value of the database column sdk_report.date
         *
         * @param date the value for sdk_report.date
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder date(Integer date) {
            obj.setDate(date);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.media_name
         *
         * @param mediaName the value for sdk_report.media_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder mediaName(String mediaName) {
            obj.setMediaName(mediaName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.position_id
         *
         * @param positionId the value for sdk_report.position_id
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder positionId(String positionId) {
            obj.setPositionId(positionId);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.position_name
         *
         * @param positionName the value for sdk_report.position_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder positionName(String positionName) {
            obj.setPositionName(positionName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.position_type
         *
         * @param positionType the value for sdk_report.position_type
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder positionType(String positionType) {
            obj.setPositionType(positionType);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.exp
         *
         * @param exp the value for sdk_report.exp
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder exp(Long exp) {
            obj.setExp(exp);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.exp_income
         *
         * @param expIncome the value for sdk_report.exp_income
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder expIncome(Double expIncome) {
            obj.setExpIncome(expIncome);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.clk
         *
         * @param clk the value for sdk_report.clk
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder clk(Long clk) {
            obj.setClk(clk);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.clk_income
         *
         * @param clkIncome the value for sdk_report.clk_income
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder clkIncome(Double clkIncome) {
            obj.setClkIncome(clkIncome);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.ctr
         *
         * @param crt the value for sdk_report.ctr
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder crt(Double crt) {
            obj.setCtr(crt);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.fr
         *
         * @param fr the value for sdk_report.fr
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder fr(Double fr) {
            obj.setFr(fr);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.income
         *
         * @param income the value for sdk_report.income
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder income(Double income) {
            obj.setIncome(income);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.cpm
         *
         * @param cpm the value for sdk_report.cpm
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder cpm(Double cpm) {
            obj.setCpm(cpm);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.cpc
         *
         * @param cpc the value for sdk_report.cpc
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder cpc(Double cpc) {
            obj.setCpc(cpc);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.app_id
         *
         * @param appId the value for sdk_report.app_id
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
         * This method sets the value of the database column sdk_report.app_name
         *
         * @param appName the value for sdk_report.app_name
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder appName(String appName) {
            obj.setAppName(appName);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.linkman
         *
         * @param linkman the value for sdk_report.linkman
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder linkman(String linkman) {
            obj.setLinkman(linkman);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.ecpm
         *
         * @param ecpm the value for sdk_report.ecpm
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder ecpm(Double ecpm) {
            obj.setEcpm(ecpm);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.type
         *
         * @param type the value for sdk_report.type
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder type(Integer type) {
            obj.setType(type);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column sdk_report.pid
         *
         * @param pid the value for sdk_report.pid
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder pid(Integer pid) {
            obj.setPid(pid);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public SdkReport build() {
            return this.obj;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table sdk_report
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        date("date", "date", "INTEGER", false),
        mediaName("media_name", "mediaName", "VARCHAR", false),
        positionId("position_id", "positionId", "VARCHAR", false),
        positionName("position_name", "positionName", "VARCHAR", false),
        positionType("position_type", "positionType", "VARCHAR", false),
        exp("exp", "exp", "BIGINT", false),
        clk("clk", "clk", "BIGINT", false),
        crt("ctr", "ctr", "DOUBLE", false),
        fr("fr", "fr", "DOUBLE", false),
        income("income", "income", "DOUBLE", false),
        cpm("cpm", "cpm", "DOUBLE", false),
        cpc("cpc", "cpc", "DOUBLE", false),
        appId("app_id", "appId", "VARCHAR", false),
        appName("app_name", "appName", "VARCHAR", false),
        linkman("linkman", "linkman", "VARCHAR", false),
        ecpm("ecpm", "ecpm", "DOUBLE", false),
        clkIncome("clk_income", "clkIncome", "DOUBLE", false),
        expIncome("exp_income", "expIncome", "DOUBLE", false),
        type("type", "type", "INTEGER", false),
        pid("pid", "pid", "INTEGER", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
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
         * This method corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table sdk_report
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
         * This method corresponds to the database table sdk_report
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
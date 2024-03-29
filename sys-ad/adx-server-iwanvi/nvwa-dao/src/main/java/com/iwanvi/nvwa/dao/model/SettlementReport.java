package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Arrays;

public class SettlementReport {
    private Integer id;

    private Integer date;

    private String positionName;

    private String positionId;

    private Long exp = 0l;

    private Long clk = 0l;

    private Double fr = 0d;

    private Double ctr = 0d;

    private Double cpm = 0d;

    private Double cpc = 0d;

    private Double expIncome = 0d;

    private Double clkIncome = 0d;

    private Double income = 0d;

    private Integer pid;

    private Integer flowCon;

    private Integer app;

    private Integer channel;

    private String channelName;

    private String flowConName;

    private String appName;

    private String adpName;

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

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
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

    public Double getFr() {
        return fr;
    }

    public void setFr(Double fr) {
        this.fr = fr;
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

    public Double getExpIncome() {
        return expIncome;
    }

    public void setExpIncome(Double expIncome) {
        this.expIncome = expIncome;
    }

    public Double getClkIncome() {
        return clkIncome;
    }

    public void setClkIncome(Double clkIncome) {
        this.clkIncome = clkIncome;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getFlowCon() {
        return flowCon;
    }

    public void setFlowCon(Integer flowCon) {
        this.flowCon = flowCon;
    }

    public Integer getApp() {
        return app;
    }

    public void setApp(Integer app) {
        this.app = app;
    }

    public String getFlowConName() {
        return flowConName;
    }

    public void setFlowConName(String flowConName) {
        this.flowConName = flowConName;
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

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table settlement_report
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static SettlementReport.Builder builder() {
        return new SettlementReport.Builder();
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table settlement_report
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public static class Builder {
        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private SettlementReport obj;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder() {
            this.obj = new SettlementReport();
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column settlement_report.id
         *
         * @param id the value for settlement_report.id
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
         * This method sets the value of the database column settlement_report.date
         *
         * @param date the value for settlement_report.date
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
         * This method sets the value of the database column settlement_report.position_name
         *
         * @param positionName the value for settlement_report.position_name
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
         * This method sets the value of the database column settlement_report.position_id
         *
         * @param positionId the value for settlement_report.position_id
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
         * This method sets the value of the database column settlement_report.exp
         *
         * @param exp the value for settlement_report.exp
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
         * This method sets the value of the database column settlement_report.exp_income
         *
         * @param expIncome the value for settlement_report.exp_income
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
         * This method sets the value of the database column settlement_report.clk
         *
         * @param clk the value for settlement_report.clk
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
         * This method sets the value of the database column settlement_report.clk_income
         *
         * @param clkIncome the value for settlement_report.clk_income
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
         * This method sets the value of the database column settlement_report.fr
         *
         * @param fr the value for settlement_report.fr
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
         * This method sets the value of the database column settlement_report.ctr
         *
         * @param ctr the value for settlement_report.ctr
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder ctr(Double ctr) {
            obj.setCtr(ctr);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column settlement_report.cpm
         *
         * @param cpm the value for settlement_report.cpm
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
         * This method sets the value of the database column settlement_report.cpc
         *
         * @param cpc the value for settlement_report.cpc
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
         * This method sets the value of the database column settlement_report.income
         *
         * @param income the value for settlement_report.income
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
         * This method sets the value of the database column settlement_report.pid
         *
         * @param pid the value for settlement_report.pid
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
         * This method sets the value of the database column settlement_report.flow_con
         *
         * @param flowCon the value for settlement_report.flow_con
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder flowCon(Integer flowCon) {
            obj.setFlowCon(flowCon);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method sets the value of the database column settlement_report.app
         *
         * @param app the value for settlement_report.app
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public Builder app(Integer app) {
            obj.setApp(app);
            return this;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public SettlementReport build() {
            return this.obj;
        }
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table settlement_report
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        date("date", "date", "INTEGER", false),
        positionName("position_name", "positionName", "VARCHAR", false),
        positionId("position_id", "positionId", "VARCHAR", false),
        exp("exp", "exp", "BIGINT", false),
        clk("clk", "clk", "BIGINT", false),
        fr("fr", "fr", "DOUBLE", false),
        ctr("ctr", "ctr", "DOUBLE", false),
        cpm("cpm", "cpm", "DOUBLE", false),
        cpc("cpc", "cpc", "DOUBLE", false),
        expIncome("exp_income", "expIncome", "DOUBLE", false),
        clkIncome("clk_income", "clkIncome", "DOUBLE", false),
        income("income", "income", "DOUBLE", false),
        pid("pid", "pid", "INTEGER", false),
        flowCon("flow_con", "flowCon", "INTEGER", false),
        app("app", "app", "INTEGER", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "\"";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
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
         * This method corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table settlement_report
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
         * This method corresponds to the database table settlement_report
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
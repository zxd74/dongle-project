package com.dongle.car.dao.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CarOrder {
    private String id;

    private String uId;

    private String shopId;

    private Integer status;

    private Integer payStatus;

    private String phone;

    private Integer evalId;

    private String evalLevel;

    private Date odt;

    private Date cdt;

    private Date udt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getEvalId() {
        return evalId;
    }

    public void setEvalId(Integer evalId) {
        this.evalId = evalId;
    }

    public String getEvalLevel() {
        return evalLevel;
    }

    public void setEvalLevel(String evalLevel) {
        this.evalLevel = evalLevel;
    }

    public Date getOdt() {
        return odt;
    }

    public void setOdt(Date odt) {
        this.odt = odt;
    }

    public Date getCdt() {
        return cdt;
    }

    public void setCdt(Date cdt) {
        this.cdt = cdt;
    }

    public Date getUdt() {
        return udt;
    }

    public void setUdt(Date udt) {
        this.udt = udt;
    }

    public static enum Column {
        id("id", "id", "CHAR", false),
        uId("u_id", "uId", "CHAR", false),
        shopId("shop_id", "shopId", "CHAR", false),
        status("status", "status", "INTEGER", true),
        payStatus("pay_status", "payStatus", "INTEGER", false),
        phone("phone", "phone", "CHAR", false),
        evalId("eval_id", "evalId", "INTEGER", false),
        evalLevel("eval_level", "evalLevel", "VARCHAR", false),
        odt("odt", "odt", "TIMESTAMP", false),
        cdt("cdt", "cdt", "TIMESTAMP", false),
        udt("udt", "udt", "TIMESTAMP", false);

        private static final String BEGINNING_DELIMITER = "`";

        private static final String ENDING_DELIMITER = "`";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public static Column[] all() {
            return Column.values();
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}
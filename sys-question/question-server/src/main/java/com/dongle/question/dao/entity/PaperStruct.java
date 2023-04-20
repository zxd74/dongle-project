package com.dongle.question.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PaperStruct {
    private String pid;

    private Integer bqn;

    private String bname;

    private Integer info;

    private Integer desc;

    private Integer order;

    private Integer totalScore;

    private Date cdt;

    private Date udt;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getBqn() {
        return bqn;
    }

    public void setBqn(Integer bqn) {
        this.bqn = bqn;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public Integer getInfo() {
        return info;
    }

    public void setInfo(Integer info) {
        this.info = info;
    }

    public Integer getDesc() {
        return desc;
    }

    public void setDesc(Integer desc) {
        this.desc = desc;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
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

    public static PaperStruct.Builder builder() {
        return new PaperStruct.Builder();
    }

    public static class Builder {
        private PaperStruct obj;

        public Builder() {
            this.obj = new PaperStruct();
        }

        public Builder pid(String pid) {
            obj.setPid(pid);
            return this;
        }

        public Builder bqn(Integer bqn) {
            obj.setBqn(bqn);
            return this;
        }

        public Builder bname(String bname) {
            obj.setBname(bname);
            return this;
        }

        public Builder info(Integer info) {
            obj.setInfo(info);
            return this;
        }

        public Builder desc(Integer desc) {
            obj.setDesc(desc);
            return this;
        }

        public Builder order(Integer order) {
            obj.setOrder(order);
            return this;
        }

        public Builder totalScore(Integer totalScore) {
            obj.setTotalScore(totalScore);
            return this;
        }

        public Builder cdt(Date cdt) {
            obj.setCdt(cdt);
            return this;
        }

        public Builder udt(Date udt) {
            obj.setUdt(udt);
            return this;
        }

        public PaperStruct build() {
            return this.obj;
        }
    }

    public enum Column {
        pid("pid", "pid", "CHAR", false),
        bqn("bqn", "bqn", "INTEGER", false),
        bname("bname", "bname", "VARCHAR", false),
        info("info", "info", "INTEGER", false),
        desc("desc", "desc", "INTEGER", true),
        order("order", "order", "INTEGER", true),
        totalScore("total_score", "totalScore", "INTEGER", false),
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
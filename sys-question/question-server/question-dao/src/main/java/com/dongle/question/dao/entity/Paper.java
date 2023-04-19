package com.dongle.question.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Paper {
    private String id;

    private String name;

    private Integer type;

    private Integer totalScore;

    private Integer status;

    private Date cdt;

    private Date udt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public static Paper.Builder builder() {
        return new Paper.Builder();
    }

    public static class Builder {
        private Paper obj;

        public Builder() {
            this.obj = new Paper();
        }

        public Builder id(String id) {
            obj.setId(id);
            return this;
        }

        public Builder name(String name) {
            obj.setName(name);
            return this;
        }

        public Builder type(Integer type) {
            obj.setType(type);
            return this;
        }

        public Builder totalScore(Integer totalScore) {
            obj.setTotalScore(totalScore);
            return this;
        }

        public Builder status(Integer status) {
            obj.setStatus(status);
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

        public Paper build() {
            return this.obj;
        }
    }

    public enum Column {
        id("id", "id", "CHAR", false),
        name("name", "name", "VARCHAR", true),
        type("type", "type", "INTEGER", true),
        totalScore("total_score", "totalScore", "INTEGER", false),
        status("status", "status", "INTEGER", true),
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
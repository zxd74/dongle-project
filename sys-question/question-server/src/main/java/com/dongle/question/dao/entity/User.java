package com.dongle.question.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class User {
    private String id;

    private String username;

    private String password;

    private String sex;

    private Integer age;

    private Integer birth;

    private String phone;

    private String email;

    private String logo;

    private String source;

    private Date rdt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getBirth() {
        return birth;
    }

    public void setBirth(Integer birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getRdt() {
        return rdt;
    }

    public void setRdt(Date rdt) {
        this.rdt = rdt;
    }

    public static User.Builder builder() {
        return new User.Builder();
    }

    public static class Builder {
        private User obj;

        public Builder() {
            this.obj = new User();
        }

        public Builder id(String id) {
            obj.setId(id);
            return this;
        }

        public Builder username(String username) {
            obj.setUsername(username);
            return this;
        }

        public Builder password(String password) {
            obj.setPassword(password);
            return this;
        }

        public Builder sex(String sex) {
            obj.setSex(sex);
            return this;
        }

        public Builder age(Integer age) {
            obj.setAge(age);
            return this;
        }

        public Builder birth(Integer birth) {
            obj.setBirth(birth);
            return this;
        }

        public Builder phone(String phone) {
            obj.setPhone(phone);
            return this;
        }

        public Builder email(String email) {
            obj.setEmail(email);
            return this;
        }

        public Builder logo(String logo) {
            obj.setLogo(logo);
            return this;
        }

        public Builder source(String source) {
            obj.setSource(source);
            return this;
        }

        public Builder rdt(Date rdt) {
            obj.setRdt(rdt);
            return this;
        }

        public User build() {
            return this.obj;
        }
    }

    public enum Column {
        id("id", "id", "CHAR", false),
        username("username", "username", "VARCHAR", false),
        password("password", "password", "CHAR", true),
        sex("sex", "sex", "CHAR", false),
        age("age", "age", "INTEGER", false),
        birth("birth", "birth", "INTEGER", false),
        phone("phone", "phone", "VARCHAR", false),
        email("email", "email", "VARCHAR", false),
        logo("logo", "logo", "VARCHAR", false),
        source("source", "source", "VARCHAR", true),
        rdt("rdt", "rdt", "TIMESTAMP", false);

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
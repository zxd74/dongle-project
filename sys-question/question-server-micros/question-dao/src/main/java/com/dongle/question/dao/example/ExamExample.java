package com.dongle.question.dao.example;

import com.dongle.question.dao.entity.Exam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public ExamExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public ExamExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public ExamExample orderBy(String ... orderByClauses) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; i++) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria(this);
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        rows = null;
        offset = null;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getRows() {
        return this.rows;
    }

    public ExamExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public ExamExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public ExamExample page(Integer page, Integer pageSize) {
        this.offset = page * pageSize;
        this.rows = pageSize;
        return this;
    }

    public static Criteria newAndCreateCriteria() {
        ExamExample example = new ExamExample();
        return example.createCriteria();
    }

    public ExamExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public ExamExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
    }

    public ExamExample distinct(boolean distinct) {
        this.setDistinct(distinct);
        return this;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("id = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("id <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("id > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("id >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("id < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("id <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`name` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`name` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`name` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`name` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`name` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`name` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(String value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("pid = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(String value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("pid <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(String value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("pid > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(String value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("pid >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidLessThan(String value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("pid < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(String value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("pid <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidLike(String value) {
            addCriterion("pid like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotLike(String value) {
            addCriterion("pid not like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<String> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<String> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(String value1, String value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(String value1, String value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`type` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`type` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("`type` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`type` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("`type` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`type` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("`type` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`type` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("`type` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`type` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("`type` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`type` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("`type` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`type` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("`type` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("`type` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("`type` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("`type` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`status` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`status` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`status` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`status` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`status` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("`status` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCdtIsNull() {
            addCriterion("cdt is null");
            return (Criteria) this;
        }

        public Criteria andCdtIsNotNull() {
            addCriterion("cdt is not null");
            return (Criteria) this;
        }

        public Criteria andCdtEqualTo(Date value) {
            addCriterion("cdt =", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("cdt = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtNotEqualTo(Date value) {
            addCriterion("cdt <>", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("cdt <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtGreaterThan(Date value) {
            addCriterion("cdt >", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("cdt > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtGreaterThanOrEqualTo(Date value) {
            addCriterion("cdt >=", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("cdt >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtLessThan(Date value) {
            addCriterion("cdt <", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("cdt < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtLessThanOrEqualTo(Date value) {
            addCriterion("cdt <=", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("cdt <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtIn(List<Date> values) {
            addCriterion("cdt in", values, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtNotIn(List<Date> values) {
            addCriterion("cdt not in", values, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtBetween(Date value1, Date value2) {
            addCriterion("cdt between", value1, value2, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtNotBetween(Date value1, Date value2) {
            addCriterion("cdt not between", value1, value2, "cdt");
            return (Criteria) this;
        }

        public Criteria andUdtIsNull() {
            addCriterion("udt is null");
            return (Criteria) this;
        }

        public Criteria andUdtIsNotNull() {
            addCriterion("udt is not null");
            return (Criteria) this;
        }

        public Criteria andUdtEqualTo(Date value) {
            addCriterion("udt =", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("udt = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtNotEqualTo(Date value) {
            addCriterion("udt <>", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("udt <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtGreaterThan(Date value) {
            addCriterion("udt >", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("udt > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtGreaterThanOrEqualTo(Date value) {
            addCriterion("udt >=", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("udt >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtLessThan(Date value) {
            addCriterion("udt <", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("udt < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtLessThanOrEqualTo(Date value) {
            addCriterion("udt <=", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("udt <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtIn(List<Date> values) {
            addCriterion("udt in", values, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtNotIn(List<Date> values) {
            addCriterion("udt not in", values, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtBetween(Date value1, Date value2) {
            addCriterion("udt between", value1, value2, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtNotBetween(Date value1, Date value2) {
            addCriterion("udt not between", value1, value2, "udt");
            return (Criteria) this;
        }

        public Criteria andSdtIsNull() {
            addCriterion("sdt is null");
            return (Criteria) this;
        }

        public Criteria andSdtIsNotNull() {
            addCriterion("sdt is not null");
            return (Criteria) this;
        }

        public Criteria andSdtEqualTo(Date value) {
            addCriterion("sdt =", value, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("sdt = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSdtNotEqualTo(Date value) {
            addCriterion("sdt <>", value, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("sdt <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSdtGreaterThan(Date value) {
            addCriterion("sdt >", value, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("sdt > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSdtGreaterThanOrEqualTo(Date value) {
            addCriterion("sdt >=", value, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("sdt >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSdtLessThan(Date value) {
            addCriterion("sdt <", value, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("sdt < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSdtLessThanOrEqualTo(Date value) {
            addCriterion("sdt <=", value, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("sdt <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andSdtIn(List<Date> values) {
            addCriterion("sdt in", values, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtNotIn(List<Date> values) {
            addCriterion("sdt not in", values, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtBetween(Date value1, Date value2) {
            addCriterion("sdt between", value1, value2, "sdt");
            return (Criteria) this;
        }

        public Criteria andSdtNotBetween(Date value1, Date value2) {
            addCriterion("sdt not between", value1, value2, "sdt");
            return (Criteria) this;
        }

        public Criteria andEdtIsNull() {
            addCriterion("edt is null");
            return (Criteria) this;
        }

        public Criteria andEdtIsNotNull() {
            addCriterion("edt is not null");
            return (Criteria) this;
        }

        public Criteria andEdtEqualTo(Date value) {
            addCriterion("edt =", value, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("edt = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEdtNotEqualTo(Date value) {
            addCriterion("edt <>", value, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtNotEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("edt <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEdtGreaterThan(Date value) {
            addCriterion("edt >", value, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtGreaterThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("edt > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEdtGreaterThanOrEqualTo(Date value) {
            addCriterion("edt >=", value, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtGreaterThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("edt >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEdtLessThan(Date value) {
            addCriterion("edt <", value, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtLessThanColumn(Exam.Column column) {
            addCriterion(new StringBuilder("edt < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEdtLessThanOrEqualTo(Date value) {
            addCriterion("edt <=", value, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtLessThanOrEqualToColumn(Exam.Column column) {
            addCriterion(new StringBuilder("edt <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andEdtIn(List<Date> values) {
            addCriterion("edt in", values, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtNotIn(List<Date> values) {
            addCriterion("edt not in", values, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtBetween(Date value1, Date value2) {
            addCriterion("edt between", value1, value2, "edt");
            return (Criteria) this;
        }

        public Criteria andEdtNotBetween(Date value1, Date value2) {
            addCriterion("edt not between", value1, value2, "edt");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        private ExamExample example;

        protected Criteria(ExamExample example) {
            super();
            this.example = example;
        }

        public ExamExample example() {
            return this.example;
        }

        @Deprecated
        public Criteria andIf(boolean ifAdd, ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }

        public Criteria when(boolean condition, ICriteriaWhen then) {
            if (condition) {
                then.criteria(this);
            }
            return this;
        }

        public Criteria when(boolean condition, ICriteriaWhen then, ICriteriaWhen otherwise) {
            if (condition) {
                then.criteria(this);
            } else {
                otherwise.criteria(this);
            }
            return this;
        }

        @Deprecated
        public interface ICriteriaAdd {
            Criteria add(Criteria add);
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

    public interface ICriteriaWhen {
        void criteria(Criteria criteria);
    }

    public interface IExampleWhen {
        void example(com.dongle.question.dao.example.ExamExample example);
    }
}
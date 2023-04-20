package com.dongle.question.dao.example;

import com.dongle.question.dao.entity.PaperQuestion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaperQuestionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer offset;

    protected Integer rows;

    public PaperQuestionExample() {
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

    public PaperQuestionExample orderBy(String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }

    public PaperQuestionExample orderBy(String ... orderByClauses) {
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

    public PaperQuestionExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    public PaperQuestionExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    public PaperQuestionExample page(Integer page, Integer pageSize) {
        this.offset = page * pageSize;
        this.rows = pageSize;
        return this;
    }

    public static Criteria newAndCreateCriteria() {
        PaperQuestionExample example = new PaperQuestionExample();
        return example.createCriteria();
    }

    public PaperQuestionExample when(boolean condition, IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }

    public PaperQuestionExample when(boolean condition, IExampleWhen then, IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        } else {
            otherwise.example(this);
        }
        return this;
    }

    public PaperQuestionExample distinct(boolean distinct) {
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

        public Criteria andPidEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("pid = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(String value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("pid <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(String value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("pid > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(String value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("pid >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidLessThan(String value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("pid < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(String value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualToColumn(PaperQuestion.Column column) {
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

        public Criteria andQidIsNull() {
            addCriterion("qid is null");
            return (Criteria) this;
        }

        public Criteria andQidIsNotNull() {
            addCriterion("qid is not null");
            return (Criteria) this;
        }

        public Criteria andQidEqualTo(String value) {
            addCriterion("qid =", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("qid = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQidNotEqualTo(String value) {
            addCriterion("qid <>", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("qid <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQidGreaterThan(String value) {
            addCriterion("qid >", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("qid > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQidGreaterThanOrEqualTo(String value) {
            addCriterion("qid >=", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("qid >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQidLessThan(String value) {
            addCriterion("qid <", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("qid < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQidLessThanOrEqualTo(String value) {
            addCriterion("qid <=", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidLessThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("qid <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andQidLike(String value) {
            addCriterion("qid like", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidNotLike(String value) {
            addCriterion("qid not like", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidIn(List<String> values) {
            addCriterion("qid in", values, "qid");
            return (Criteria) this;
        }

        public Criteria andQidNotIn(List<String> values) {
            addCriterion("qid not in", values, "qid");
            return (Criteria) this;
        }

        public Criteria andQidBetween(String value1, String value2) {
            addCriterion("qid between", value1, value2, "qid");
            return (Criteria) this;
        }

        public Criteria andQidNotBetween(String value1, String value2) {
            addCriterion("qid not between", value1, value2, "qid");
            return (Criteria) this;
        }

        public Criteria andBqnIsNull() {
            addCriterion("bqn is null");
            return (Criteria) this;
        }

        public Criteria andBqnIsNotNull() {
            addCriterion("bqn is not null");
            return (Criteria) this;
        }

        public Criteria andBqnEqualTo(Integer value) {
            addCriterion("bqn =", value, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bqn = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBqnNotEqualTo(Integer value) {
            addCriterion("bqn <>", value, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bqn <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBqnGreaterThan(Integer value) {
            addCriterion("bqn >", value, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bqn > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBqnGreaterThanOrEqualTo(Integer value) {
            addCriterion("bqn >=", value, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bqn >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBqnLessThan(Integer value) {
            addCriterion("bqn <", value, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bqn < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBqnLessThanOrEqualTo(Integer value) {
            addCriterion("bqn <=", value, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnLessThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bqn <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBqnIn(List<Integer> values) {
            addCriterion("bqn in", values, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnNotIn(List<Integer> values) {
            addCriterion("bqn not in", values, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnBetween(Integer value1, Integer value2) {
            addCriterion("bqn between", value1, value2, "bqn");
            return (Criteria) this;
        }

        public Criteria andBqnNotBetween(Integer value1, Integer value2) {
            addCriterion("bqn not between", value1, value2, "bqn");
            return (Criteria) this;
        }

        public Criteria andBnameIsNull() {
            addCriterion("bname is null");
            return (Criteria) this;
        }

        public Criteria andBnameIsNotNull() {
            addCriterion("bname is not null");
            return (Criteria) this;
        }

        public Criteria andBnameEqualTo(String value) {
            addCriterion("bname =", value, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bname = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBnameNotEqualTo(String value) {
            addCriterion("bname <>", value, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bname <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBnameGreaterThan(String value) {
            addCriterion("bname >", value, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bname > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBnameGreaterThanOrEqualTo(String value) {
            addCriterion("bname >=", value, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bname >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBnameLessThan(String value) {
            addCriterion("bname <", value, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bname < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBnameLessThanOrEqualTo(String value) {
            addCriterion("bname <=", value, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameLessThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("bname <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andBnameLike(String value) {
            addCriterion("bname like", value, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameNotLike(String value) {
            addCriterion("bname not like", value, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameIn(List<String> values) {
            addCriterion("bname in", values, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameNotIn(List<String> values) {
            addCriterion("bname not in", values, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameBetween(String value1, String value2) {
            addCriterion("bname between", value1, value2, "bname");
            return (Criteria) this;
        }

        public Criteria andBnameNotBetween(String value1, String value2) {
            addCriterion("bname not between", value1, value2, "bname");
            return (Criteria) this;
        }

        public Criteria andInfoIsNull() {
            addCriterion("info is null");
            return (Criteria) this;
        }

        public Criteria andInfoIsNotNull() {
            addCriterion("info is not null");
            return (Criteria) this;
        }

        public Criteria andInfoEqualTo(Integer value) {
            addCriterion("info =", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("info = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andInfoNotEqualTo(Integer value) {
            addCriterion("info <>", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("info <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThan(Integer value) {
            addCriterion("info >", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("info > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThanOrEqualTo(Integer value) {
            addCriterion("info >=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("info >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andInfoLessThan(Integer value) {
            addCriterion("info <", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("info < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andInfoLessThanOrEqualTo(Integer value) {
            addCriterion("info <=", value, "info");
            return (Criteria) this;
        }

        public Criteria andInfoLessThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("info <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andInfoIn(List<Integer> values) {
            addCriterion("info in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotIn(List<Integer> values) {
            addCriterion("info not in", values, "info");
            return (Criteria) this;
        }

        public Criteria andInfoBetween(Integer value1, Integer value2) {
            addCriterion("info between", value1, value2, "info");
            return (Criteria) this;
        }

        public Criteria andInfoNotBetween(Integer value1, Integer value2) {
            addCriterion("info not between", value1, value2, "info");
            return (Criteria) this;
        }

        public Criteria andDescIsNull() {
            addCriterion("`desc` is null");
            return (Criteria) this;
        }

        public Criteria andDescIsNotNull() {
            addCriterion("`desc` is not null");
            return (Criteria) this;
        }

        public Criteria andDescEqualTo(Integer value) {
            addCriterion("`desc` =", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`desc` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDescNotEqualTo(Integer value) {
            addCriterion("`desc` <>", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`desc` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDescGreaterThan(Integer value) {
            addCriterion("`desc` >", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`desc` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualTo(Integer value) {
            addCriterion("`desc` >=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`desc` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDescLessThan(Integer value) {
            addCriterion("`desc` <", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`desc` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualTo(Integer value) {
            addCriterion("`desc` <=", value, "desc");
            return (Criteria) this;
        }

        public Criteria andDescLessThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`desc` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andDescIn(List<Integer> values) {
            addCriterion("`desc` in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotIn(List<Integer> values) {
            addCriterion("`desc` not in", values, "desc");
            return (Criteria) this;
        }

        public Criteria andDescBetween(Integer value1, Integer value2) {
            addCriterion("`desc` between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andDescNotBetween(Integer value1, Integer value2) {
            addCriterion("`desc` not between", value1, value2, "desc");
            return (Criteria) this;
        }

        public Criteria andOrderIsNull() {
            addCriterion("`order` is null");
            return (Criteria) this;
        }

        public Criteria andOrderIsNotNull() {
            addCriterion("`order` is not null");
            return (Criteria) this;
        }

        public Criteria andOrderEqualTo(Integer value) {
            addCriterion("`order` =", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`order` = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderNotEqualTo(Integer value) {
            addCriterion("`order` <>", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`order` <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThan(Integer value) {
            addCriterion("`order` >", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`order` > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("`order` >=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`order` >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderLessThan(Integer value) {
            addCriterion("`order` <", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`order` < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderLessThanOrEqualTo(Integer value) {
            addCriterion("`order` <=", value, "order");
            return (Criteria) this;
        }

        public Criteria andOrderLessThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("`order` <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andOrderIn(List<Integer> values) {
            addCriterion("`order` in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotIn(List<Integer> values) {
            addCriterion("`order` not in", values, "order");
            return (Criteria) this;
        }

        public Criteria andOrderBetween(Integer value1, Integer value2) {
            addCriterion("`order` between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("`order` not between", value1, value2, "order");
            return (Criteria) this;
        }

        public Criteria andScoreIsNull() {
            addCriterion("score is null");
            return (Criteria) this;
        }

        public Criteria andScoreIsNotNull() {
            addCriterion("score is not null");
            return (Criteria) this;
        }

        public Criteria andScoreEqualTo(Integer value) {
            addCriterion("score =", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("score = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualTo(Integer value) {
            addCriterion("score <>", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("score <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThan(Integer value) {
            addCriterion("score >", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("score > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("score >=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("score >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andScoreLessThan(Integer value) {
            addCriterion("score <", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("score < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualTo(Integer value) {
            addCriterion("score <=", value, "score");
            return (Criteria) this;
        }

        public Criteria andScoreLessThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("score <= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andScoreIn(List<Integer> values) {
            addCriterion("score in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotIn(List<Integer> values) {
            addCriterion("score not in", values, "score");
            return (Criteria) this;
        }

        public Criteria andScoreBetween(Integer value1, Integer value2) {
            addCriterion("score between", value1, value2, "score");
            return (Criteria) this;
        }

        public Criteria andScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("score not between", value1, value2, "score");
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

        public Criteria andCdtEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("cdt = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtNotEqualTo(Date value) {
            addCriterion("cdt <>", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("cdt <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtGreaterThan(Date value) {
            addCriterion("cdt >", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("cdt > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtGreaterThanOrEqualTo(Date value) {
            addCriterion("cdt >=", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("cdt >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtLessThan(Date value) {
            addCriterion("cdt <", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("cdt < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andCdtLessThanOrEqualTo(Date value) {
            addCriterion("cdt <=", value, "cdt");
            return (Criteria) this;
        }

        public Criteria andCdtLessThanOrEqualToColumn(PaperQuestion.Column column) {
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

        public Criteria andUdtEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("udt = ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtNotEqualTo(Date value) {
            addCriterion("udt <>", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtNotEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("udt <> ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtGreaterThan(Date value) {
            addCriterion("udt >", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtGreaterThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("udt > ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtGreaterThanOrEqualTo(Date value) {
            addCriterion("udt >=", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtGreaterThanOrEqualToColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("udt >= ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtLessThan(Date value) {
            addCriterion("udt <", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtLessThanColumn(PaperQuestion.Column column) {
            addCriterion(new StringBuilder("udt < ").append(column.getEscapedColumnName()).toString());
            return (Criteria) this;
        }

        public Criteria andUdtLessThanOrEqualTo(Date value) {
            addCriterion("udt <=", value, "udt");
            return (Criteria) this;
        }

        public Criteria andUdtLessThanOrEqualToColumn(PaperQuestion.Column column) {
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
    }

    public static class Criteria extends GeneratedCriteria {
        private PaperQuestionExample example;

        protected Criteria(PaperQuestionExample example) {
            super();
            this.example = example;
        }

        public PaperQuestionExample example() {
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
        void example(com.dongle.question.dao.example.PaperQuestionExample example);
    }
}
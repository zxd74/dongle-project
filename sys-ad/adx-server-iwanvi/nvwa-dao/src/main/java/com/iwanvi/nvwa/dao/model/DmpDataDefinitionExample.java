package com.iwanvi.nvwa.dao.model;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

import java.util.ArrayList;
import java.util.List;

public class DmpDataDefinitionExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DmpDataDefinitionExample() {
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

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDidIsNull() {
            addCriterion("did is null");
            return (Criteria) this;
        }

        public Criteria andDidIsNotNull() {
            addCriterion("did is not null");
            return (Criteria) this;
        }

        public Criteria andDidEqualTo(Integer value) {
            addCriterion("did =", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotEqualTo(Integer value) {
            addCriterion("did <>", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidGreaterThan(Integer value) {
            addCriterion("did >", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidGreaterThanOrEqualTo(Integer value) {
            addCriterion("did >=", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidLessThan(Integer value) {
            addCriterion("did <", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidLessThanOrEqualTo(Integer value) {
            addCriterion("did <=", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidIn(List<Integer> values) {
            addCriterion("did in", values, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotIn(List<Integer> values) {
            addCriterion("did not in", values, "did");
            return (Criteria) this;
        }

        public Criteria andDidBetween(Integer value1, Integer value2) {
            addCriterion("did between", value1, value2, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotBetween(Integer value1, Integer value2) {
            addCriterion("did not between", value1, value2, "did");
            return (Criteria) this;
        }

        public Criteria andColIsNull() {
            addCriterion("col is null");
            return (Criteria) this;
        }

        public Criteria andColIsNotNull() {
            addCriterion("col is not null");
            return (Criteria) this;
        }

        public Criteria andColEqualTo(Integer value) {
            addCriterion("col =", value, "col");
            return (Criteria) this;
        }

        public Criteria andColNotEqualTo(Integer value) {
            addCriterion("col <>", value, "col");
            return (Criteria) this;
        }

        public Criteria andColGreaterThan(Integer value) {
            addCriterion("col >", value, "col");
            return (Criteria) this;
        }

        public Criteria andColGreaterThanOrEqualTo(Integer value) {
            addCriterion("col >=", value, "col");
            return (Criteria) this;
        }

        public Criteria andColLessThan(Integer value) {
            addCriterion("col <", value, "col");
            return (Criteria) this;
        }

        public Criteria andColLessThanOrEqualTo(Integer value) {
            addCriterion("col <=", value, "col");
            return (Criteria) this;
        }

        public Criteria andColIn(List<Integer> values) {
            addCriterion("col in", values, "col");
            return (Criteria) this;
        }

        public Criteria andColNotIn(List<Integer> values) {
            addCriterion("col not in", values, "col");
            return (Criteria) this;
        }

        public Criteria andColBetween(Integer value1, Integer value2) {
            addCriterion("col between", value1, value2, "col");
            return (Criteria) this;
        }

        public Criteria andColNotBetween(Integer value1, Integer value2) {
            addCriterion("col not between", value1, value2, "col");
            return (Criteria) this;
        }

        public Criteria andColNameIsNull() {
            addCriterion("col_name is null");
            return (Criteria) this;
        }

        public Criteria andColNameIsNotNull() {
            addCriterion("col_name is not null");
            return (Criteria) this;
        }

        public Criteria andColNameEqualTo(String value) {
            addCriterion("col_name =", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameNotEqualTo(String value) {
            addCriterion("col_name <>", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameGreaterThan(String value) {
            addCriterion("col_name >", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameGreaterThanOrEqualTo(String value) {
            addCriterion("col_name >=", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameLessThan(String value) {
            addCriterion("col_name <", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameLessThanOrEqualTo(String value) {
            addCriterion("col_name <=", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameLike(String value) {
            addCriterion("col_name like", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameNotLike(String value) {
            addCriterion("col_name not like", value, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameIn(List<String> values) {
            addCriterion("col_name in", values, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameNotIn(List<String> values) {
            addCriterion("col_name not in", values, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameBetween(String value1, String value2) {
            addCriterion("col_name between", value1, value2, "colName");
            return (Criteria) this;
        }

        public Criteria andColNameNotBetween(String value1, String value2) {
            addCriterion("col_name not between", value1, value2, "colName");
            return (Criteria) this;
        }

        public Criteria andColTypeIsNull() {
            addCriterion("col_type is null");
            return (Criteria) this;
        }

        public Criteria andColTypeIsNotNull() {
            addCriterion("col_type is not null");
            return (Criteria) this;
        }

        public Criteria andColTypeEqualTo(Integer value) {
            addCriterion("col_type =", value, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeNotEqualTo(Integer value) {
            addCriterion("col_type <>", value, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeGreaterThan(Integer value) {
            addCriterion("col_type >", value, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("col_type >=", value, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeLessThan(Integer value) {
            addCriterion("col_type <", value, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeLessThanOrEqualTo(Integer value) {
            addCriterion("col_type <=", value, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeIn(List<Integer> values) {
            addCriterion("col_type in", values, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeNotIn(List<Integer> values) {
            addCriterion("col_type not in", values, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeBetween(Integer value1, Integer value2) {
            addCriterion("col_type between", value1, value2, "colType");
            return (Criteria) this;
        }

        public Criteria andColTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("col_type not between", value1, value2, "colType");
            return (Criteria) this;
        }

        public Criteria andIsKeyIsNull() {
            addCriterion("is_key is null");
            return (Criteria) this;
        }

        public Criteria andIsKeyIsNotNull() {
            addCriterion("is_key is not null");
            return (Criteria) this;
        }

        public Criteria andIsKeyEqualTo(Integer value) {
            addCriterion("is_key =", value, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyNotEqualTo(Integer value) {
            addCriterion("is_key <>", value, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyGreaterThan(Integer value) {
            addCriterion("is_key >", value, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_key >=", value, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyLessThan(Integer value) {
            addCriterion("is_key <", value, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyLessThanOrEqualTo(Integer value) {
            addCriterion("is_key <=", value, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyIn(List<Integer> values) {
            addCriterion("is_key in", values, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyNotIn(List<Integer> values) {
            addCriterion("is_key not in", values, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyBetween(Integer value1, Integer value2) {
            addCriterion("is_key between", value1, value2, "isKey");
            return (Criteria) this;
        }

        public Criteria andIsKeyNotBetween(Integer value1, Integer value2) {
            addCriterion("is_key not between", value1, value2, "isKey");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
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
}
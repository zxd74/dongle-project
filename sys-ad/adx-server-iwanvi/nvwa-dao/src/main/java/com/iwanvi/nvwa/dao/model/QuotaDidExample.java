package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.List;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

public class QuotaDidExample extends BaseExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuotaDidExample() {
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

        public Criteria andFsIdIsNull() {
            addCriterion("fs_id is null");
            return (Criteria) this;
        }

        public Criteria andFsIdIsNotNull() {
            addCriterion("fs_id is not null");
            return (Criteria) this;
        }

        public Criteria andFsIdEqualTo(String value) {
            addCriterion("fs_id =", value, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdNotEqualTo(String value) {
            addCriterion("fs_id <>", value, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdGreaterThan(String value) {
            addCriterion("fs_id >", value, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdGreaterThanOrEqualTo(String value) {
            addCriterion("fs_id >=", value, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdLessThan(String value) {
            addCriterion("fs_id <", value, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdLessThanOrEqualTo(String value) {
            addCriterion("fs_id <=", value, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdLike(String value) {
            addCriterion("fs_id like", value, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdNotLike(String value) {
            addCriterion("fs_id not like", value, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdIn(List<String> values) {
            addCriterion("fs_id in", values, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdNotIn(List<String> values) {
            addCriterion("fs_id not in", values, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdBetween(String value1, String value2) {
            addCriterion("fs_id between", value1, value2, "fsId");
            return (Criteria) this;
        }

        public Criteria andFsIdNotBetween(String value1, String value2) {
            addCriterion("fs_id not between", value1, value2, "fsId");
            return (Criteria) this;
        }

        public Criteria andCreIdIsNull() {
            addCriterion("cre_id is null");
            return (Criteria) this;
        }

        public Criteria andCreIdIsNotNull() {
            addCriterion("cre_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreIdEqualTo(Integer value) {
            addCriterion("cre_id =", value, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdNotEqualTo(Integer value) {
            addCriterion("cre_id <>", value, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdGreaterThan(Integer value) {
            addCriterion("cre_id >", value, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cre_id >=", value, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdLessThan(Integer value) {
            addCriterion("cre_id <", value, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdLessThanOrEqualTo(Integer value) {
            addCriterion("cre_id <=", value, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdIn(List<Integer> values) {
            addCriterion("cre_id in", values, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdNotIn(List<Integer> values) {
            addCriterion("cre_id not in", values, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdBetween(Integer value1, Integer value2) {
            addCriterion("cre_id between", value1, value2, "creId");
            return (Criteria) this;
        }

        public Criteria andCreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cre_id not between", value1, value2, "creId");
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

        public Criteria andDidEqualTo(String value) {
            addCriterion("did =", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotEqualTo(String value) {
            addCriterion("did <>", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidGreaterThan(String value) {
            addCriterion("did >", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidGreaterThanOrEqualTo(String value) {
            addCriterion("did >=", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidLessThan(String value) {
            addCriterion("did <", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidLessThanOrEqualTo(String value) {
            addCriterion("did <=", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidLike(String value) {
            addCriterion("did like", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotLike(String value) {
            addCriterion("did not like", value, "did");
            return (Criteria) this;
        }

        public Criteria andDidIn(List<String> values) {
            addCriterion("did in", values, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotIn(List<String> values) {
            addCriterion("did not in", values, "did");
            return (Criteria) this;
        }

        public Criteria andDidBetween(String value1, String value2) {
            addCriterion("did between", value1, value2, "did");
            return (Criteria) this;
        }

        public Criteria andDidNotBetween(String value1, String value2) {
            addCriterion("did not between", value1, value2, "did");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNull() {
            addCriterion("is_active is null");
            return (Criteria) this;
        }

        public Criteria andIsActiveIsNotNull() {
            addCriterion("is_active is not null");
            return (Criteria) this;
        }

        public Criteria andIsActiveEqualTo(Integer value) {
            addCriterion("is_active =", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotEqualTo(Integer value) {
            addCriterion("is_active <>", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThan(Integer value) {
            addCriterion("is_active >", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_active >=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThan(Integer value) {
            addCriterion("is_active <", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveLessThanOrEqualTo(Integer value) {
            addCriterion("is_active <=", value, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveIn(List<Integer> values) {
            addCriterion("is_active in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotIn(List<Integer> values) {
            addCriterion("is_active not in", values, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveBetween(Integer value1, Integer value2) {
            addCriterion("is_active between", value1, value2, "isActive");
            return (Criteria) this;
        }

        public Criteria andIsActiveNotBetween(Integer value1, Integer value2) {
            addCriterion("is_active not between", value1, value2, "isActive");
            return (Criteria) this;
        }

        public Criteria andClkTimeIsNull() {
            addCriterion("clk_time is null");
            return (Criteria) this;
        }

        public Criteria andClkTimeIsNotNull() {
            addCriterion("clk_time is not null");
            return (Criteria) this;
        }

        public Criteria andClkTimeEqualTo(Long value) {
            addCriterion("clk_time =", value, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeNotEqualTo(Long value) {
            addCriterion("clk_time <>", value, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeGreaterThan(Long value) {
            addCriterion("clk_time >", value, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("clk_time >=", value, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeLessThan(Long value) {
            addCriterion("clk_time <", value, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeLessThanOrEqualTo(Long value) {
            addCriterion("clk_time <=", value, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeIn(List<Long> values) {
            addCriterion("clk_time in", values, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeNotIn(List<Long> values) {
            addCriterion("clk_time not in", values, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeBetween(Long value1, Long value2) {
            addCriterion("clk_time between", value1, value2, "clkTime");
            return (Criteria) this;
        }

        public Criteria andClkTimeNotBetween(Long value1, Long value2) {
            addCriterion("clk_time not between", value1, value2, "clkTime");
            return (Criteria) this;
        }

        public Criteria andActTimeIsNull() {
            addCriterion("act_time is null");
            return (Criteria) this;
        }

        public Criteria andActTimeIsNotNull() {
            addCriterion("act_time is not null");
            return (Criteria) this;
        }

        public Criteria andActTimeEqualTo(Long value) {
            addCriterion("act_time =", value, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeNotEqualTo(Long value) {
            addCriterion("act_time <>", value, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeGreaterThan(Long value) {
            addCriterion("act_time >", value, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("act_time >=", value, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeLessThan(Long value) {
            addCriterion("act_time <", value, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeLessThanOrEqualTo(Long value) {
            addCriterion("act_time <=", value, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeIn(List<Long> values) {
            addCriterion("act_time in", values, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeNotIn(List<Long> values) {
            addCriterion("act_time not in", values, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeBetween(Long value1, Long value2) {
            addCriterion("act_time between", value1, value2, "actTime");
            return (Criteria) this;
        }

        public Criteria andActTimeNotBetween(Long value1, Long value2) {
            addCriterion("act_time not between", value1, value2, "actTime");
            return (Criteria) this;
        }

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(String value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(String value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(String value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(String value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(String value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(String value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLike(String value) {
            addCriterion("ip like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotLike(String value) {
            addCriterion("ip not like", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<String> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<String> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(String value1, String value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(String value1, String value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNull() {
            addCriterion("area_code is null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNotNull() {
            addCriterion("area_code is not null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeEqualTo(String value) {
            addCriterion("area_code =", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotEqualTo(String value) {
            addCriterion("area_code <>", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThan(String value) {
            addCriterion("area_code >", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("area_code >=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThan(String value) {
            addCriterion("area_code <", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("area_code <=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLike(String value) {
            addCriterion("area_code like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotLike(String value) {
            addCriterion("area_code not like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIn(List<String> values) {
            addCriterion("area_code in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotIn(List<String> values) {
            addCriterion("area_code not in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeBetween(String value1, String value2) {
            addCriterion("area_code between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotBetween(String value1, String value2) {
            addCriterion("area_code not between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAidIsNull() {
            addCriterion("aid is null");
            return (Criteria) this;
        }

        public Criteria andAidIsNotNull() {
            addCriterion("aid is not null");
            return (Criteria) this;
        }

        public Criteria andAidEqualTo(String value) {
            addCriterion("aid =", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotEqualTo(String value) {
            addCriterion("aid <>", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThan(String value) {
            addCriterion("aid >", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidGreaterThanOrEqualTo(String value) {
            addCriterion("aid >=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThan(String value) {
            addCriterion("aid <", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLessThanOrEqualTo(String value) {
            addCriterion("aid <=", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidLike(String value) {
            addCriterion("aid like", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotLike(String value) {
            addCriterion("aid not like", value, "aid");
            return (Criteria) this;
        }

        public Criteria andAidIn(List<String> values) {
            addCriterion("aid in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotIn(List<String> values) {
            addCriterion("aid not in", values, "aid");
            return (Criteria) this;
        }

        public Criteria andAidBetween(String value1, String value2) {
            addCriterion("aid between", value1, value2, "aid");
            return (Criteria) this;
        }

        public Criteria andAidNotBetween(String value1, String value2) {
            addCriterion("aid not between", value1, value2, "aid");
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
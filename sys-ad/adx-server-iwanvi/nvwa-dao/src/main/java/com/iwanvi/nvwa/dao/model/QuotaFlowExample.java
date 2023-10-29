package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.List;

public class QuotaFlowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuotaFlowExample() {
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

        public Criteria andFlowIdIsNull() {
            addCriterion("flow_id is null");
            return (Criteria) this;
        }

        public Criteria andFlowIdIsNotNull() {
            addCriterion("flow_id is not null");
            return (Criteria) this;
        }

        public Criteria andFlowIdEqualTo(String value) {
            addCriterion("flow_id =", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotEqualTo(String value) {
            addCriterion("flow_id <>", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThan(String value) {
            addCriterion("flow_id >", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThanOrEqualTo(String value) {
            addCriterion("flow_id >=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThan(String value) {
            addCriterion("flow_id <", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThanOrEqualTo(String value) {
            addCriterion("flow_id <=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLike(String value) {
            addCriterion("flow_id like", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotLike(String value) {
            addCriterion("flow_id not like", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdIn(List<String> values) {
            addCriterion("flow_id in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotIn(List<String> values) {
            addCriterion("flow_id not in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdBetween(String value1, String value2) {
            addCriterion("flow_id between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotBetween(String value1, String value2) {
            addCriterion("flow_id not between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andCreDayIsNull() {
            addCriterion("cre_day is null");
            return (Criteria) this;
        }

        public Criteria andCreDayIsNotNull() {
            addCriterion("cre_day is not null");
            return (Criteria) this;
        }

        public Criteria andCreDayEqualTo(Integer value) {
            addCriterion("cre_day =", value, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayNotEqualTo(Integer value) {
            addCriterion("cre_day <>", value, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayGreaterThan(Integer value) {
            addCriterion("cre_day >", value, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("cre_day >=", value, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayLessThan(Integer value) {
            addCriterion("cre_day <", value, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayLessThanOrEqualTo(Integer value) {
            addCriterion("cre_day <=", value, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayIn(List<Integer> values) {
            addCriterion("cre_day in", values, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayNotIn(List<Integer> values) {
            addCriterion("cre_day not in", values, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayBetween(Integer value1, Integer value2) {
            addCriterion("cre_day between", value1, value2, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreDayNotBetween(Integer value1, Integer value2) {
            addCriterion("cre_day not between", value1, value2, "creDay");
            return (Criteria) this;
        }

        public Criteria andCreHourIsNull() {
            addCriterion("cre_hour is null");
            return (Criteria) this;
        }

        public Criteria andCreHourIsNotNull() {
            addCriterion("cre_hour is not null");
            return (Criteria) this;
        }

        public Criteria andCreHourEqualTo(Integer value) {
            addCriterion("cre_hour =", value, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourNotEqualTo(Integer value) {
            addCriterion("cre_hour <>", value, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourGreaterThan(Integer value) {
            addCriterion("cre_hour >", value, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourGreaterThanOrEqualTo(Integer value) {
            addCriterion("cre_hour >=", value, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourLessThan(Integer value) {
            addCriterion("cre_hour <", value, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourLessThanOrEqualTo(Integer value) {
            addCriterion("cre_hour <=", value, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourIn(List<Integer> values) {
            addCriterion("cre_hour in", values, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourNotIn(List<Integer> values) {
            addCriterion("cre_hour not in", values, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourBetween(Integer value1, Integer value2) {
            addCriterion("cre_hour between", value1, value2, "creHour");
            return (Criteria) this;
        }

        public Criteria andCreHourNotBetween(Integer value1, Integer value2) {
            addCriterion("cre_hour not between", value1, value2, "creHour");
            return (Criteria) this;
        }

        public Criteria andReqIsNull() {
            addCriterion("req is null");
            return (Criteria) this;
        }

        public Criteria andReqIsNotNull() {
            addCriterion("req is not null");
            return (Criteria) this;
        }

        public Criteria andReqEqualTo(Integer value) {
            addCriterion("req =", value, "req");
            return (Criteria) this;
        }

        public Criteria andReqNotEqualTo(Integer value) {
            addCriterion("req <>", value, "req");
            return (Criteria) this;
        }

        public Criteria andReqGreaterThan(Integer value) {
            addCriterion("req >", value, "req");
            return (Criteria) this;
        }

        public Criteria andReqGreaterThanOrEqualTo(Integer value) {
            addCriterion("req >=", value, "req");
            return (Criteria) this;
        }

        public Criteria andReqLessThan(Integer value) {
            addCriterion("req <", value, "req");
            return (Criteria) this;
        }

        public Criteria andReqLessThanOrEqualTo(Integer value) {
            addCriterion("req <=", value, "req");
            return (Criteria) this;
        }

        public Criteria andReqIn(List<Integer> values) {
            addCriterion("req in", values, "req");
            return (Criteria) this;
        }

        public Criteria andReqNotIn(List<Integer> values) {
            addCriterion("req not in", values, "req");
            return (Criteria) this;
        }

        public Criteria andReqBetween(Integer value1, Integer value2) {
            addCriterion("req between", value1, value2, "req");
            return (Criteria) this;
        }

        public Criteria andReqNotBetween(Integer value1, Integer value2) {
            addCriterion("req not between", value1, value2, "req");
            return (Criteria) this;
        }

        public Criteria andRequvIsNull() {
            addCriterion("requv is null");
            return (Criteria) this;
        }

        public Criteria andRequvIsNotNull() {
            addCriterion("requv is not null");
            return (Criteria) this;
        }

        public Criteria andRequvEqualTo(Integer value) {
            addCriterion("requv =", value, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvNotEqualTo(Integer value) {
            addCriterion("requv <>", value, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvGreaterThan(Integer value) {
            addCriterion("requv >", value, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvGreaterThanOrEqualTo(Integer value) {
            addCriterion("requv >=", value, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvLessThan(Integer value) {
            addCriterion("requv <", value, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvLessThanOrEqualTo(Integer value) {
            addCriterion("requv <=", value, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvIn(List<Integer> values) {
            addCriterion("requv in", values, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvNotIn(List<Integer> values) {
            addCriterion("requv not in", values, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvBetween(Integer value1, Integer value2) {
            addCriterion("requv between", value1, value2, "requv");
            return (Criteria) this;
        }

        public Criteria andRequvNotBetween(Integer value1, Integer value2) {
            addCriterion("requv not between", value1, value2, "requv");
            return (Criteria) this;
        }

        public Criteria andBidIsNull() {
            addCriterion("bid is null");
            return (Criteria) this;
        }

        public Criteria andBidIsNotNull() {
            addCriterion("bid is not null");
            return (Criteria) this;
        }

        public Criteria andBidEqualTo(Integer value) {
            addCriterion("bid =", value, "bid");
            return (Criteria) this;
        }

        public Criteria andBidNotEqualTo(Integer value) {
            addCriterion("bid <>", value, "bid");
            return (Criteria) this;
        }

        public Criteria andBidGreaterThan(Integer value) {
            addCriterion("bid >", value, "bid");
            return (Criteria) this;
        }

        public Criteria andBidGreaterThanOrEqualTo(Integer value) {
            addCriterion("bid >=", value, "bid");
            return (Criteria) this;
        }

        public Criteria andBidLessThan(Integer value) {
            addCriterion("bid <", value, "bid");
            return (Criteria) this;
        }

        public Criteria andBidLessThanOrEqualTo(Integer value) {
            addCriterion("bid <=", value, "bid");
            return (Criteria) this;
        }

        public Criteria andBidIn(List<Integer> values) {
            addCriterion("bid in", values, "bid");
            return (Criteria) this;
        }

        public Criteria andBidNotIn(List<Integer> values) {
            addCriterion("bid not in", values, "bid");
            return (Criteria) this;
        }

        public Criteria andBidBetween(Integer value1, Integer value2) {
            addCriterion("bid between", value1, value2, "bid");
            return (Criteria) this;
        }

        public Criteria andBidNotBetween(Integer value1, Integer value2) {
            addCriterion("bid not between", value1, value2, "bid");
            return (Criteria) this;
        }

        public Criteria andExpIsNull() {
            addCriterion("exp is null");
            return (Criteria) this;
        }

        public Criteria andExpIsNotNull() {
            addCriterion("exp is not null");
            return (Criteria) this;
        }

        public Criteria andExpEqualTo(Integer value) {
            addCriterion("exp =", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotEqualTo(Integer value) {
            addCriterion("exp <>", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpGreaterThan(Integer value) {
            addCriterion("exp >", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpGreaterThanOrEqualTo(Integer value) {
            addCriterion("exp >=", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpLessThan(Integer value) {
            addCriterion("exp <", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpLessThanOrEqualTo(Integer value) {
            addCriterion("exp <=", value, "exp");
            return (Criteria) this;
        }

        public Criteria andExpIn(List<Integer> values) {
            addCriterion("exp in", values, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotIn(List<Integer> values) {
            addCriterion("exp not in", values, "exp");
            return (Criteria) this;
        }

        public Criteria andExpBetween(Integer value1, Integer value2) {
            addCriterion("exp between", value1, value2, "exp");
            return (Criteria) this;
        }

        public Criteria andExpNotBetween(Integer value1, Integer value2) {
            addCriterion("exp not between", value1, value2, "exp");
            return (Criteria) this;
        }

        public Criteria andExpuvIsNull() {
            addCriterion("expuv is null");
            return (Criteria) this;
        }

        public Criteria andExpuvIsNotNull() {
            addCriterion("expuv is not null");
            return (Criteria) this;
        }

        public Criteria andExpuvEqualTo(Integer value) {
            addCriterion("expuv =", value, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvNotEqualTo(Integer value) {
            addCriterion("expuv <>", value, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvGreaterThan(Integer value) {
            addCriterion("expuv >", value, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvGreaterThanOrEqualTo(Integer value) {
            addCriterion("expuv >=", value, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvLessThan(Integer value) {
            addCriterion("expuv <", value, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvLessThanOrEqualTo(Integer value) {
            addCriterion("expuv <=", value, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvIn(List<Integer> values) {
            addCriterion("expuv in", values, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvNotIn(List<Integer> values) {
            addCriterion("expuv not in", values, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvBetween(Integer value1, Integer value2) {
            addCriterion("expuv between", value1, value2, "expuv");
            return (Criteria) this;
        }

        public Criteria andExpuvNotBetween(Integer value1, Integer value2) {
            addCriterion("expuv not between", value1, value2, "expuv");
            return (Criteria) this;
        }

        public Criteria andClkIsNull() {
            addCriterion("clk is null");
            return (Criteria) this;
        }

        public Criteria andClkIsNotNull() {
            addCriterion("clk is not null");
            return (Criteria) this;
        }

        public Criteria andClkEqualTo(Integer value) {
            addCriterion("clk =", value, "clk");
            return (Criteria) this;
        }

        public Criteria andClkNotEqualTo(Integer value) {
            addCriterion("clk <>", value, "clk");
            return (Criteria) this;
        }

        public Criteria andClkGreaterThan(Integer value) {
            addCriterion("clk >", value, "clk");
            return (Criteria) this;
        }

        public Criteria andClkGreaterThanOrEqualTo(Integer value) {
            addCriterion("clk >=", value, "clk");
            return (Criteria) this;
        }

        public Criteria andClkLessThan(Integer value) {
            addCriterion("clk <", value, "clk");
            return (Criteria) this;
        }

        public Criteria andClkLessThanOrEqualTo(Integer value) {
            addCriterion("clk <=", value, "clk");
            return (Criteria) this;
        }

        public Criteria andClkIn(List<Integer> values) {
            addCriterion("clk in", values, "clk");
            return (Criteria) this;
        }

        public Criteria andClkNotIn(List<Integer> values) {
            addCriterion("clk not in", values, "clk");
            return (Criteria) this;
        }

        public Criteria andClkBetween(Integer value1, Integer value2) {
            addCriterion("clk between", value1, value2, "clk");
            return (Criteria) this;
        }

        public Criteria andClkNotBetween(Integer value1, Integer value2) {
            addCriterion("clk not between", value1, value2, "clk");
            return (Criteria) this;
        }

        public Criteria andClkuvIsNull() {
            addCriterion("clkuv is null");
            return (Criteria) this;
        }

        public Criteria andClkuvIsNotNull() {
            addCriterion("clkuv is not null");
            return (Criteria) this;
        }

        public Criteria andClkuvEqualTo(Integer value) {
            addCriterion("clkuv =", value, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvNotEqualTo(Integer value) {
            addCriterion("clkuv <>", value, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvGreaterThan(Integer value) {
            addCriterion("clkuv >", value, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvGreaterThanOrEqualTo(Integer value) {
            addCriterion("clkuv >=", value, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvLessThan(Integer value) {
            addCriterion("clkuv <", value, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvLessThanOrEqualTo(Integer value) {
            addCriterion("clkuv <=", value, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvIn(List<Integer> values) {
            addCriterion("clkuv in", values, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvNotIn(List<Integer> values) {
            addCriterion("clkuv not in", values, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvBetween(Integer value1, Integer value2) {
            addCriterion("clkuv between", value1, value2, "clkuv");
            return (Criteria) this;
        }

        public Criteria andClkuvNotBetween(Integer value1, Integer value2) {
            addCriterion("clkuv not between", value1, value2, "clkuv");
            return (Criteria) this;
        }

        public Criteria andInvestIsNull() {
            addCriterion("invest is null");
            return (Criteria) this;
        }

        public Criteria andInvestIsNotNull() {
            addCriterion("invest is not null");
            return (Criteria) this;
        }

        public Criteria andInvestEqualTo(Long value) {
            addCriterion("invest =", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestNotEqualTo(Long value) {
            addCriterion("invest <>", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestGreaterThan(Long value) {
            addCriterion("invest >", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestGreaterThanOrEqualTo(Long value) {
            addCriterion("invest >=", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestLessThan(Long value) {
            addCriterion("invest <", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestLessThanOrEqualTo(Long value) {
            addCriterion("invest <=", value, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestIn(List<Long> values) {
            addCriterion("invest in", values, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestNotIn(List<Long> values) {
            addCriterion("invest not in", values, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestBetween(Long value1, Long value2) {
            addCriterion("invest between", value1, value2, "invest");
            return (Criteria) this;
        }

        public Criteria andInvestNotBetween(Long value1, Long value2) {
            addCriterion("invest not between", value1, value2, "invest");
            return (Criteria) this;
        }

        public Criteria andActiveIsNull() {
            addCriterion("active is null");
            return (Criteria) this;
        }

        public Criteria andActiveIsNotNull() {
            addCriterion("active is not null");
            return (Criteria) this;
        }

        public Criteria andActiveEqualTo(Integer value) {
            addCriterion("active =", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveNotEqualTo(Integer value) {
            addCriterion("active <>", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveGreaterThan(Integer value) {
            addCriterion("active >", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveGreaterThanOrEqualTo(Integer value) {
            addCriterion("active >=", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveLessThan(Integer value) {
            addCriterion("active <", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveLessThanOrEqualTo(Integer value) {
            addCriterion("active <=", value, "active");
            return (Criteria) this;
        }

        public Criteria andActiveIn(List<Integer> values) {
            addCriterion("active in", values, "active");
            return (Criteria) this;
        }

        public Criteria andActiveNotIn(List<Integer> values) {
            addCriterion("active not in", values, "active");
            return (Criteria) this;
        }

        public Criteria andActiveBetween(Integer value1, Integer value2) {
            addCriterion("active between", value1, value2, "active");
            return (Criteria) this;
        }

        public Criteria andActiveNotBetween(Integer value1, Integer value2) {
            addCriterion("active not between", value1, value2, "active");
            return (Criteria) this;
        }

        public Criteria andAppidIsNull() {
            addCriterion("appid is null");
            return (Criteria) this;
        }

        public Criteria andAppidIsNotNull() {
            addCriterion("appid is not null");
            return (Criteria) this;
        }

        public Criteria andAppidEqualTo(String value) {
            addCriterion("appid =", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotEqualTo(String value) {
            addCriterion("appid <>", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThan(String value) {
            addCriterion("appid >", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThanOrEqualTo(String value) {
            addCriterion("appid >=", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLessThan(String value) {
            addCriterion("appid <", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLessThanOrEqualTo(String value) {
            addCriterion("appid <=", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLike(String value) {
            addCriterion("appid like", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotLike(String value) {
            addCriterion("appid not like", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidIn(List<String> values) {
            addCriterion("appid in", values, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotIn(List<String> values) {
            addCriterion("appid not in", values, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidBetween(String value1, String value2) {
            addCriterion("appid between", value1, value2, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotBetween(String value1, String value2) {
            addCriterion("appid not between", value1, value2, "appid");
            return (Criteria) this;
        }

        public Criteria andAdpidIsNull() {
            addCriterion("adpid is null");
            return (Criteria) this;
        }

        public Criteria andAdpidIsNotNull() {
            addCriterion("adpid is not null");
            return (Criteria) this;
        }

        public Criteria andAdpidEqualTo(String value) {
            addCriterion("adpid =", value, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidNotEqualTo(String value) {
            addCriterion("adpid <>", value, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidGreaterThan(String value) {
            addCriterion("adpid >", value, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidGreaterThanOrEqualTo(String value) {
            addCriterion("adpid >=", value, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidLessThan(String value) {
            addCriterion("adpid <", value, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidLessThanOrEqualTo(String value) {
            addCriterion("adpid <=", value, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidLike(String value) {
            addCriterion("adpid like", value, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidNotLike(String value) {
            addCriterion("adpid not like", value, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidIn(List<String> values) {
            addCriterion("adpid in", values, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidNotIn(List<String> values) {
            addCriterion("adpid not in", values, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidBetween(String value1, String value2) {
            addCriterion("adpid between", value1, value2, "adpid");
            return (Criteria) this;
        }

        public Criteria andAdpidNotBetween(String value1, String value2) {
            addCriterion("adpid not between", value1, value2, "adpid");
            return (Criteria) this;
        }

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("channel like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("channel not like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<String> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<String> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andVersionsIsNull() {
            addCriterion("versions is null");
            return (Criteria) this;
        }

        public Criteria andVersionsIsNotNull() {
            addCriterion("versions is not null");
            return (Criteria) this;
        }

        public Criteria andVersionsEqualTo(String value) {
            addCriterion("versions =", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotEqualTo(String value) {
            addCriterion("versions <>", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsGreaterThan(String value) {
            addCriterion("versions >", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsGreaterThanOrEqualTo(String value) {
            addCriterion("versions >=", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsLessThan(String value) {
            addCriterion("versions <", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsLessThanOrEqualTo(String value) {
            addCriterion("versions <=", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsLike(String value) {
            addCriterion("versions like", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotLike(String value) {
            addCriterion("versions not like", value, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsIn(List<String> values) {
            addCriterion("versions in", values, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotIn(List<String> values) {
            addCriterion("versions not in", values, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsBetween(String value1, String value2) {
            addCriterion("versions between", value1, value2, "versions");
            return (Criteria) this;
        }

        public Criteria andVersionsNotBetween(String value1, String value2) {
            addCriterion("versions not between", value1, value2, "versions");
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
package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

public class QuotaPlanExample extends BaseExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuotaPlanExample() {
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

        public Criteria andPlanIdIsNull() {
            addCriterion("plan_id is null");
            return (Criteria) this;
        }

        public Criteria andPlanIdIsNotNull() {
            addCriterion("plan_id is not null");
            return (Criteria) this;
        }

        public Criteria andPlanIdEqualTo(Integer value) {
            addCriterion("plan_id =", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotEqualTo(Integer value) {
            addCriterion("plan_id <>", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThan(Integer value) {
            addCriterion("plan_id >", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_id >=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThan(Integer value) {
            addCriterion("plan_id <", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdLessThanOrEqualTo(Integer value) {
            addCriterion("plan_id <=", value, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdIn(List<Integer> values) {
            addCriterion("plan_id in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotIn(List<Integer> values) {
            addCriterion("plan_id not in", values, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdBetween(Integer value1, Integer value2) {
            addCriterion("plan_id between", value1, value2, "planId");
            return (Criteria) this;
        }

        public Criteria andPlanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_id not between", value1, value2, "planId");
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

        public Criteria andCostIsNull() {
            addCriterion("cost is null");
            return (Criteria) this;
        }

        public Criteria andCostIsNotNull() {
            addCriterion("cost is not null");
            return (Criteria) this;
        }

        public Criteria andCostEqualTo(Long value) {
            addCriterion("cost =", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotEqualTo(Long value) {
            addCriterion("cost <>", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostGreaterThan(Long value) {
            addCriterion("cost >", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostGreaterThanOrEqualTo(Long value) {
            addCriterion("cost >=", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostLessThan(Long value) {
            addCriterion("cost <", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostLessThanOrEqualTo(Long value) {
            addCriterion("cost <=", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostIn(List<Long> values) {
            addCriterion("cost in", values, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotIn(List<Long> values) {
            addCriterion("cost not in", values, "cost");
            return (Criteria) this;
        }

        public Criteria andCostBetween(Long value1, Long value2) {
            addCriterion("cost between", value1, value2, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotBetween(Long value1, Long value2) {
            addCriterion("cost not between", value1, value2, "cost");
            return (Criteria) this;
        }

        public Criteria andInvestmentIsNull() {
            addCriterion("investment is null");
            return (Criteria) this;
        }

        public Criteria andInvestmentIsNotNull() {
            addCriterion("investment is not null");
            return (Criteria) this;
        }

        public Criteria andInvestmentEqualTo(Long value) {
            addCriterion("investment =", value, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentNotEqualTo(Long value) {
            addCriterion("investment <>", value, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentGreaterThan(Long value) {
            addCriterion("investment >", value, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentGreaterThanOrEqualTo(Long value) {
            addCriterion("investment >=", value, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentLessThan(Long value) {
            addCriterion("investment <", value, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentLessThanOrEqualTo(Long value) {
            addCriterion("investment <=", value, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentIn(List<Long> values) {
            addCriterion("investment in", values, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentNotIn(List<Long> values) {
            addCriterion("investment not in", values, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentBetween(Long value1, Long value2) {
            addCriterion("investment between", value1, value2, "investment");
            return (Criteria) this;
        }

        public Criteria andInvestmentNotBetween(Long value1, Long value2) {
            addCriterion("investment not between", value1, value2, "investment");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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

        public Criteria andWinIsNull() {
            addCriterion("win is null");
            return (Criteria) this;
        }

        public Criteria andWinIsNotNull() {
            addCriterion("win is not null");
            return (Criteria) this;
        }

        public Criteria andWinEqualTo(Integer value) {
            addCriterion("win =", value, "win");
            return (Criteria) this;
        }

        public Criteria andWinNotEqualTo(Integer value) {
            addCriterion("win <>", value, "win");
            return (Criteria) this;
        }

        public Criteria andWinGreaterThan(Integer value) {
            addCriterion("win >", value, "win");
            return (Criteria) this;
        }

        public Criteria andWinGreaterThanOrEqualTo(Integer value) {
            addCriterion("win >=", value, "win");
            return (Criteria) this;
        }

        public Criteria andWinLessThan(Integer value) {
            addCriterion("win <", value, "win");
            return (Criteria) this;
        }

        public Criteria andWinLessThanOrEqualTo(Integer value) {
            addCriterion("win <=", value, "win");
            return (Criteria) this;
        }

        public Criteria andWinIn(List<Integer> values) {
            addCriterion("win in", values, "win");
            return (Criteria) this;
        }

        public Criteria andWinNotIn(List<Integer> values) {
            addCriterion("win not in", values, "win");
            return (Criteria) this;
        }

        public Criteria andWinBetween(Integer value1, Integer value2) {
            addCriterion("win between", value1, value2, "win");
            return (Criteria) this;
        }

        public Criteria andWinNotBetween(Integer value1, Integer value2) {
            addCriterion("win not between", value1, value2, "win");
            return (Criteria) this;
        }

        public Criteria andAgentCostIsNull() {
            addCriterion("agent_cost is null");
            return (Criteria) this;
        }

        public Criteria andAgentCostIsNotNull() {
            addCriterion("agent_cost is not null");
            return (Criteria) this;
        }

        public Criteria andAgentCostEqualTo(Long value) {
            addCriterion("agent_cost =", value, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostNotEqualTo(Long value) {
            addCriterion("agent_cost <>", value, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostGreaterThan(Long value) {
            addCriterion("agent_cost >", value, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostGreaterThanOrEqualTo(Long value) {
            addCriterion("agent_cost >=", value, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostLessThan(Long value) {
            addCriterion("agent_cost <", value, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostLessThanOrEqualTo(Long value) {
            addCriterion("agent_cost <=", value, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostIn(List<Long> values) {
            addCriterion("agent_cost in", values, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostNotIn(List<Long> values) {
            addCriterion("agent_cost not in", values, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostBetween(Long value1, Long value2) {
            addCriterion("agent_cost between", value1, value2, "agentCost");
            return (Criteria) this;
        }

        public Criteria andAgentCostNotBetween(Long value1, Long value2) {
            addCriterion("agent_cost not between", value1, value2, "agentCost");
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
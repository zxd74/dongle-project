package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

public class PlanExample extends BaseExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PlanExample() {
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

        public Criteria andPlanNameIsNull() {
            addCriterion("plan_name is null");
            return (Criteria) this;
        }

        public Criteria andPlanNameIsNotNull() {
            addCriterion("plan_name is not null");
            return (Criteria) this;
        }

        public Criteria andPlanNameEqualTo(String value) {
            addCriterion("plan_name =", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotEqualTo(String value) {
            addCriterion("plan_name <>", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameGreaterThan(String value) {
            addCriterion("plan_name >", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameGreaterThanOrEqualTo(String value) {
            addCriterion("plan_name >=", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLessThan(String value) {
            addCriterion("plan_name <", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLessThanOrEqualTo(String value) {
            addCriterion("plan_name <=", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameLike(String value) {
            addCriterion("plan_name like", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotLike(String value) {
            addCriterion("plan_name not like", value, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameIn(List<String> values) {
            addCriterion("plan_name in", values, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotIn(List<String> values) {
            addCriterion("plan_name not in", values, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameBetween(String value1, String value2) {
            addCriterion("plan_name between", value1, value2, "planName");
            return (Criteria) this;
        }

        public Criteria andPlanNameNotBetween(String value1, String value2) {
            addCriterion("plan_name not between", value1, value2, "planName");
            return (Criteria) this;
        }

        public Criteria andAdverIdIsNull() {
            addCriterion("adver_id is null");
            return (Criteria) this;
        }

        public Criteria andAdverIdIsNotNull() {
            addCriterion("adver_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdverIdEqualTo(Integer value) {
            addCriterion("adver_id =", value, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdNotEqualTo(Integer value) {
            addCriterion("adver_id <>", value, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdGreaterThan(Integer value) {
            addCriterion("adver_id >", value, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("adver_id >=", value, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdLessThan(Integer value) {
            addCriterion("adver_id <", value, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdLessThanOrEqualTo(Integer value) {
            addCriterion("adver_id <=", value, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdIn(List<Integer> values) {
            addCriterion("adver_id in", values, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdNotIn(List<Integer> values) {
            addCriterion("adver_id not in", values, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdBetween(Integer value1, Integer value2) {
            addCriterion("adver_id between", value1, value2, "adverId");
            return (Criteria) this;
        }

        public Criteria andAdverIdNotBetween(Integer value1, Integer value2) {
            addCriterion("adver_id not between", value1, value2, "adverId");
            return (Criteria) this;
        }

        public Criteria andPlanLimitIsNull() {
            addCriterion("plan_limit is null");
            return (Criteria) this;
        }

        public Criteria andPlanLimitIsNotNull() {
            addCriterion("plan_limit is not null");
            return (Criteria) this;
        }

        public Criteria andPlanLimitEqualTo(Integer value) {
            addCriterion("plan_limit =", value, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitNotEqualTo(Integer value) {
            addCriterion("plan_limit <>", value, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitGreaterThan(Integer value) {
            addCriterion("plan_limit >", value, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_limit >=", value, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitLessThan(Integer value) {
            addCriterion("plan_limit <", value, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitLessThanOrEqualTo(Integer value) {
            addCriterion("plan_limit <=", value, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitIn(List<Integer> values) {
            addCriterion("plan_limit in", values, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitNotIn(List<Integer> values) {
            addCriterion("plan_limit not in", values, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitBetween(Integer value1, Integer value2) {
            addCriterion("plan_limit between", value1, value2, "planLimit");
            return (Criteria) this;
        }

        public Criteria andPlanLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_limit not between", value1, value2, "planLimit");
            return (Criteria) this;
        }

        public Criteria andRunStateIsNull() {
            addCriterion("run_state is null");
            return (Criteria) this;
        }

        public Criteria andRunStateIsNotNull() {
            addCriterion("run_state is not null");
            return (Criteria) this;
        }

        public Criteria andRunStateEqualTo(Integer value) {
            addCriterion("run_state =", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateNotEqualTo(Integer value) {
            addCriterion("run_state <>", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateGreaterThan(Integer value) {
            addCriterion("run_state >", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("run_state >=", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateLessThan(Integer value) {
            addCriterion("run_state <", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateLessThanOrEqualTo(Integer value) {
            addCriterion("run_state <=", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateIn(List<Integer> values) {
            addCriterion("run_state in", values, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateNotIn(List<Integer> values) {
            addCriterion("run_state not in", values, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateBetween(Integer value1, Integer value2) {
            addCriterion("run_state between", value1, value2, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateNotBetween(Integer value1, Integer value2) {
            addCriterion("run_state not between", value1, value2, "runState");
            return (Criteria) this;
        }

        public Criteria andLimitStateIsNull() {
            addCriterion("limit_state is null");
            return (Criteria) this;
        }

        public Criteria andLimitStateIsNotNull() {
            addCriterion("limit_state is not null");
            return (Criteria) this;
        }

        public Criteria andLimitStateEqualTo(Integer value) {
            addCriterion("limit_state =", value, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateNotEqualTo(Integer value) {
            addCriterion("limit_state <>", value, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateGreaterThan(Integer value) {
            addCriterion("limit_state >", value, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("limit_state >=", value, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateLessThan(Integer value) {
            addCriterion("limit_state <", value, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateLessThanOrEqualTo(Integer value) {
            addCriterion("limit_state <=", value, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateIn(List<Integer> values) {
            addCriterion("limit_state in", values, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateNotIn(List<Integer> values) {
            addCriterion("limit_state not in", values, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateBetween(Integer value1, Integer value2) {
            addCriterion("limit_state between", value1, value2, "limitState");
            return (Criteria) this;
        }

        public Criteria andLimitStateNotBetween(Integer value1, Integer value2) {
            addCriterion("limit_state not between", value1, value2, "limitState");
            return (Criteria) this;
        }

        public Criteria andPlanStateIsNull() {
            addCriterion("plan_state is null");
            return (Criteria) this;
        }

        public Criteria andPlanStateIsNotNull() {
            addCriterion("plan_state is not null");
            return (Criteria) this;
        }

        public Criteria andPlanStateEqualTo(Integer value) {
            addCriterion("plan_state =", value, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateNotEqualTo(Integer value) {
            addCriterion("plan_state <>", value, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateGreaterThan(Integer value) {
            addCriterion("plan_state >", value, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_state >=", value, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateLessThan(Integer value) {
            addCriterion("plan_state <", value, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateLessThanOrEqualTo(Integer value) {
            addCriterion("plan_state <=", value, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateIn(List<Integer> values) {
            addCriterion("plan_state in", values, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateNotIn(List<Integer> values) {
            addCriterion("plan_state not in", values, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateBetween(Integer value1, Integer value2) {
            addCriterion("plan_state between", value1, value2, "planState");
            return (Criteria) this;
        }

        public Criteria andPlanStateNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_state not between", value1, value2, "planState");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Integer value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Integer value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Integer value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Integer value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Integer value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Integer> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Integer> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Integer value1, Integer value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlIsNull() {
            addCriterion("imp_monitor_url is null");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlIsNotNull() {
            addCriterion("imp_monitor_url is not null");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlEqualTo(String value) {
            addCriterion("imp_monitor_url =", value, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlNotEqualTo(String value) {
            addCriterion("imp_monitor_url <>", value, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlGreaterThan(String value) {
            addCriterion("imp_monitor_url >", value, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlGreaterThanOrEqualTo(String value) {
            addCriterion("imp_monitor_url >=", value, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlLessThan(String value) {
            addCriterion("imp_monitor_url <", value, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlLessThanOrEqualTo(String value) {
            addCriterion("imp_monitor_url <=", value, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlLike(String value) {
            addCriterion("imp_monitor_url like", value, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlNotLike(String value) {
            addCriterion("imp_monitor_url not like", value, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlIn(List<String> values) {
            addCriterion("imp_monitor_url in", values, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlNotIn(List<String> values) {
            addCriterion("imp_monitor_url not in", values, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlBetween(String value1, String value2) {
            addCriterion("imp_monitor_url between", value1, value2, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andImpMonitorUrlNotBetween(String value1, String value2) {
            addCriterion("imp_monitor_url not between", value1, value2, "impMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlIsNull() {
            addCriterion("clk_monitor_url is null");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlIsNotNull() {
            addCriterion("clk_monitor_url is not null");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlEqualTo(String value) {
            addCriterion("clk_monitor_url =", value, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlNotEqualTo(String value) {
            addCriterion("clk_monitor_url <>", value, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlGreaterThan(String value) {
            addCriterion("clk_monitor_url >", value, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlGreaterThanOrEqualTo(String value) {
            addCriterion("clk_monitor_url >=", value, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlLessThan(String value) {
            addCriterion("clk_monitor_url <", value, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlLessThanOrEqualTo(String value) {
            addCriterion("clk_monitor_url <=", value, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlLike(String value) {
            addCriterion("clk_monitor_url like", value, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlNotLike(String value) {
            addCriterion("clk_monitor_url not like", value, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlIn(List<String> values) {
            addCriterion("clk_monitor_url in", values, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlNotIn(List<String> values) {
            addCriterion("clk_monitor_url not in", values, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlBetween(String value1, String value2) {
            addCriterion("clk_monitor_url between", value1, value2, "clkMonitorUrl");
            return (Criteria) this;
        }

        public Criteria andClkMonitorUrlNotBetween(String value1, String value2) {
            addCriterion("clk_monitor_url not between", value1, value2, "clkMonitorUrl");
            return (Criteria) this;
        }
        
        public Criteria andCreateTypeIsNull() {
            addCriterion("create_type is null");
            return (Criteria) this;
        }

        public Criteria andCreateTypeIsNotNull() {
            addCriterion("create_type is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTypeEqualTo(Integer value) {
            addCriterion("create_type =", value, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeNotEqualTo(Integer value) {
            addCriterion("create_type <>", value, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeGreaterThan(Integer value) {
            addCriterion("create_type >", value, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_type >=", value, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeLessThan(Integer value) {
            addCriterion("create_type <", value, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeLessThanOrEqualTo(Integer value) {
            addCriterion("create_type <=", value, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeIn(List<Integer> values) {
            addCriterion("create_type in", values, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeNotIn(List<Integer> values) {
            addCriterion("create_type not in", values, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeBetween(Integer value1, Integer value2) {
            addCriterion("create_type between", value1, value2, "createType");
            return (Criteria) this;
        }

        public Criteria andCreateTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("create_type not between", value1, value2, "createType");
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
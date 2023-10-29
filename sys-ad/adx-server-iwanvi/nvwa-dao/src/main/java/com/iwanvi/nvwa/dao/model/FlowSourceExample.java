package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

public class FlowSourceExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FlowSourceExample() {
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

        public Criteria andMediaNameIsNull() {
            addCriterion("media_name is null");
            return (Criteria) this;
        }

        public Criteria andMediaNameIsNotNull() {
            addCriterion("media_name is not null");
            return (Criteria) this;
        }

        public Criteria andMediaNameEqualTo(String value) {
            addCriterion("media_name =", value, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameNotEqualTo(String value) {
            addCriterion("media_name <>", value, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameGreaterThan(String value) {
            addCriterion("media_name >", value, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameGreaterThanOrEqualTo(String value) {
            addCriterion("media_name >=", value, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameLessThan(String value) {
            addCriterion("media_name <", value, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameLessThanOrEqualTo(String value) {
            addCriterion("media_name <=", value, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameLike(String value) {
            addCriterion("media_name like", value, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameNotLike(String value) {
            addCriterion("media_name not like", value, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameIn(List<String> values) {
            addCriterion("media_name in", values, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameNotIn(List<String> values) {
            addCriterion("media_name not in", values, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameBetween(String value1, String value2) {
            addCriterion("media_name between", value1, value2, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaNameNotBetween(String value1, String value2) {
            addCriterion("media_name not between", value1, value2, "mediaName");
            return (Criteria) this;
        }

        public Criteria andMediaTypeIsNull() {
            addCriterion("media_type is null");
            return (Criteria) this;
        }

        public Criteria andMediaTypeIsNotNull() {
            addCriterion("media_type is not null");
            return (Criteria) this;
        }

        public Criteria andMediaTypeEqualTo(Integer value) {
            addCriterion("media_type =", value, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeNotEqualTo(Integer value) {
            addCriterion("media_type <>", value, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeGreaterThan(Integer value) {
            addCriterion("media_type >", value, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("media_type >=", value, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeLessThan(Integer value) {
            addCriterion("media_type <", value, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeLessThanOrEqualTo(Integer value) {
            addCriterion("media_type <=", value, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeIn(List<Integer> values) {
            addCriterion("media_type in", values, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeNotIn(List<Integer> values) {
            addCriterion("media_type not in", values, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeBetween(Integer value1, Integer value2) {
            addCriterion("media_type between", value1, value2, "mediaType");
            return (Criteria) this;
        }

        public Criteria andMediaTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("media_type not between", value1, value2, "mediaType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIsNull() {
            addCriterion("target_type is null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIsNotNull() {
            addCriterion("target_type is not null");
            return (Criteria) this;
        }

        public Criteria andTargetTypeEqualTo(Integer value) {
            addCriterion("target_type =", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotEqualTo(Integer value) {
            addCriterion("target_type <>", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeGreaterThan(Integer value) {
            addCriterion("target_type >", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("target_type >=", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeLessThan(Integer value) {
            addCriterion("target_type <", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeLessThanOrEqualTo(Integer value) {
            addCriterion("target_type <=", value, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeIn(List<Integer> values) {
            addCriterion("target_type in", values, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotIn(List<Integer> values) {
            addCriterion("target_type not in", values, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeBetween(Integer value1, Integer value2) {
            addCriterion("target_type between", value1, value2, "targetType");
            return (Criteria) this;
        }

        public Criteria andTargetTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("target_type not between", value1, value2, "targetType");
            return (Criteria) this;
        }

        public Criteria andMediaUuidIsNull() {
            addCriterion("media_uuid is null");
            return (Criteria) this;
        }

        public Criteria andMediaUuidIsNotNull() {
            addCriterion("media_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andMediaUuidEqualTo(String value) {
            addCriterion("media_uuid =", value, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidNotEqualTo(String value) {
            addCriterion("media_uuid <>", value, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidGreaterThan(String value) {
            addCriterion("media_uuid >", value, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidGreaterThanOrEqualTo(String value) {
            addCriterion("media_uuid >=", value, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidLessThan(String value) {
            addCriterion("media_uuid <", value, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidLessThanOrEqualTo(String value) {
            addCriterion("media_uuid <=", value, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidLike(String value) {
            addCriterion("media_uuid like", value, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidNotLike(String value) {
            addCriterion("media_uuid not like", value, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidIn(List<String> values) {
            addCriterion("media_uuid in", values, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidNotIn(List<String> values) {
            addCriterion("media_uuid not in", values, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidBetween(String value1, String value2) {
            addCriterion("media_uuid between", value1, value2, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaUuidNotBetween(String value1, String value2) {
            addCriterion("media_uuid not between", value1, value2, "mediaUuid");
            return (Criteria) this;
        }

        public Criteria andMediaStateIsNull() {
            addCriterion("media_state is null");
            return (Criteria) this;
        }

        public Criteria andMediaStateIsNotNull() {
            addCriterion("media_state is not null");
            return (Criteria) this;
        }

        public Criteria andMediaStateEqualTo(Integer value) {
            addCriterion("media_state =", value, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateNotEqualTo(Integer value) {
            addCriterion("media_state <>", value, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateGreaterThan(Integer value) {
            addCriterion("media_state >", value, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("media_state >=", value, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateLessThan(Integer value) {
            addCriterion("media_state <", value, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateLessThanOrEqualTo(Integer value) {
            addCriterion("media_state <=", value, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateIn(List<Integer> values) {
            addCriterion("media_state in", values, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateNotIn(List<Integer> values) {
            addCriterion("media_state not in", values, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateBetween(Integer value1, Integer value2) {
            addCriterion("media_state between", value1, value2, "mediaState");
            return (Criteria) this;
        }

        public Criteria andMediaStateNotBetween(Integer value1, Integer value2) {
            addCriterion("media_state not between", value1, value2, "mediaState");
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

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andJoinTypeIsNull() {
            addCriterion("join_type is null");
            return (Criteria) this;
        }

        public Criteria andJoinTypeIsNotNull() {
            addCriterion("join_type is not null");
            return (Criteria) this;
        }

        public Criteria andJoinTypeEqualTo(Integer value) {
            addCriterion("join_type =", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeNotEqualTo(Integer value) {
            addCriterion("join_type <>", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeGreaterThan(Integer value) {
            addCriterion("join_type >", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("join_type >=", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeLessThan(Integer value) {
            addCriterion("join_type <", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeLessThanOrEqualTo(Integer value) {
            addCriterion("join_type <=", value, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeIn(List<Integer> values) {
            addCriterion("join_type in", values, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeNotIn(List<Integer> values) {
            addCriterion("join_type not in", values, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeBetween(Integer value1, Integer value2) {
            addCriterion("join_type between", value1, value2, "joinType");
            return (Criteria) this;
        }

        public Criteria andJoinTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("join_type not between", value1, value2, "joinType");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("company_name =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("company_name >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("company_name <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("company_name like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("company_name not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("company_name in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrIsNull() {
            addCriterion("company_addr is null");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrIsNotNull() {
            addCriterion("company_addr is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrEqualTo(String value) {
            addCriterion("company_addr =", value, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrNotEqualTo(String value) {
            addCriterion("company_addr <>", value, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrGreaterThan(String value) {
            addCriterion("company_addr >", value, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrGreaterThanOrEqualTo(String value) {
            addCriterion("company_addr >=", value, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrLessThan(String value) {
            addCriterion("company_addr <", value, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrLessThanOrEqualTo(String value) {
            addCriterion("company_addr <=", value, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrLike(String value) {
            addCriterion("company_addr like", value, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrNotLike(String value) {
            addCriterion("company_addr not like", value, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrIn(List<String> values) {
            addCriterion("company_addr in", values, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrNotIn(List<String> values) {
            addCriterion("company_addr not in", values, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrBetween(String value1, String value2) {
            addCriterion("company_addr between", value1, value2, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyAddrNotBetween(String value1, String value2) {
            addCriterion("company_addr not between", value1, value2, "companyAddr");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanIsNull() {
            addCriterion("company_linkman is null");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanIsNotNull() {
            addCriterion("company_linkman is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanEqualTo(String value) {
            addCriterion("company_linkman =", value, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanNotEqualTo(String value) {
            addCriterion("company_linkman <>", value, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanGreaterThan(String value) {
            addCriterion("company_linkman >", value, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("company_linkman >=", value, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanLessThan(String value) {
            addCriterion("company_linkman <", value, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanLessThanOrEqualTo(String value) {
            addCriterion("company_linkman <=", value, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanLike(String value) {
            addCriterion("company_linkman like", value, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanNotLike(String value) {
            addCriterion("company_linkman not like", value, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanIn(List<String> values) {
            addCriterion("company_linkman in", values, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanNotIn(List<String> values) {
            addCriterion("company_linkman not in", values, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanBetween(String value1, String value2) {
            addCriterion("company_linkman between", value1, value2, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkmanNotBetween(String value1, String value2) {
            addCriterion("company_linkman not between", value1, value2, "companyLinkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelIsNull() {
            addCriterion("linkman_tel is null");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelIsNotNull() {
            addCriterion("linkman_tel is not null");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelEqualTo(String value) {
            addCriterion("linkman_tel =", value, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelNotEqualTo(String value) {
            addCriterion("linkman_tel <>", value, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelGreaterThan(String value) {
            addCriterion("linkman_tel >", value, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelGreaterThanOrEqualTo(String value) {
            addCriterion("linkman_tel >=", value, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelLessThan(String value) {
            addCriterion("linkman_tel <", value, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelLessThanOrEqualTo(String value) {
            addCriterion("linkman_tel <=", value, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelLike(String value) {
            addCriterion("linkman_tel like", value, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelNotLike(String value) {
            addCriterion("linkman_tel not like", value, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelIn(List<String> values) {
            addCriterion("linkman_tel in", values, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelNotIn(List<String> values) {
            addCriterion("linkman_tel not in", values, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelBetween(String value1, String value2) {
            addCriterion("linkman_tel between", value1, value2, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andLinkmanTelNotBetween(String value1, String value2) {
            addCriterion("linkman_tel not between", value1, value2, "linkmanTel");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlIsNull() {
            addCriterion("website_url is null");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlIsNotNull() {
            addCriterion("website_url is not null");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlEqualTo(String value) {
            addCriterion("website_url =", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlNotEqualTo(String value) {
            addCriterion("website_url <>", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlGreaterThan(String value) {
            addCriterion("website_url >", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlGreaterThanOrEqualTo(String value) {
            addCriterion("website_url >=", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlLessThan(String value) {
            addCriterion("website_url <", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlLessThanOrEqualTo(String value) {
            addCriterion("website_url <=", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlLike(String value) {
            addCriterion("website_url like", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlNotLike(String value) {
            addCriterion("website_url not like", value, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlIn(List<String> values) {
            addCriterion("website_url in", values, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlNotIn(List<String> values) {
            addCriterion("website_url not in", values, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlBetween(String value1, String value2) {
            addCriterion("website_url between", value1, value2, "websiteUrl");
            return (Criteria) this;
        }

        public Criteria andWebsiteUrlNotBetween(String value1, String value2) {
            addCriterion("website_url not between", value1, value2, "websiteUrl");
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
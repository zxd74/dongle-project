package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

public class AdxRelationExample extends BaseExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdxRelationExample() {
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

        public Criteria andObjIdIsNull() {
            addCriterion("obj_id is null");
            return (Criteria) this;
        }

        public Criteria andObjIdIsNotNull() {
            addCriterion("obj_id is not null");
            return (Criteria) this;
        }

        public Criteria andObjIdEqualTo(Integer value) {
            addCriterion("obj_id =", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotEqualTo(Integer value) {
            addCriterion("obj_id <>", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThan(Integer value) {
            addCriterion("obj_id >", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("obj_id >=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThan(Integer value) {
            addCriterion("obj_id <", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThanOrEqualTo(Integer value) {
            addCriterion("obj_id <=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdIn(List<Integer> values) {
            addCriterion("obj_id in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotIn(List<Integer> values) {
            addCriterion("obj_id not in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdBetween(Integer value1, Integer value2) {
            addCriterion("obj_id between", value1, value2, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotBetween(Integer value1, Integer value2) {
            addCriterion("obj_id not between", value1, value2, "objId");
            return (Criteria) this;
        }

        public Criteria andObjTypeIsNull() {
            addCriterion("obj_type is null");
            return (Criteria) this;
        }

        public Criteria andObjTypeIsNotNull() {
            addCriterion("obj_type is not null");
            return (Criteria) this;
        }

        public Criteria andObjTypeEqualTo(Integer value) {
            addCriterion("obj_type =", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeNotEqualTo(Integer value) {
            addCriterion("obj_type <>", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeGreaterThan(Integer value) {
            addCriterion("obj_type >", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("obj_type >=", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeLessThan(Integer value) {
            addCriterion("obj_type <", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeLessThanOrEqualTo(Integer value) {
            addCriterion("obj_type <=", value, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeIn(List<Integer> values) {
            addCriterion("obj_type in", values, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeNotIn(List<Integer> values) {
            addCriterion("obj_type not in", values, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeBetween(Integer value1, Integer value2) {
            addCriterion("obj_type between", value1, value2, "objType");
            return (Criteria) this;
        }

        public Criteria andObjTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("obj_type not between", value1, value2, "objType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeIsNull() {
            addCriterion("adx_type is null");
            return (Criteria) this;
        }

        public Criteria andAdxTypeIsNotNull() {
            addCriterion("adx_type is not null");
            return (Criteria) this;
        }

        public Criteria andAdxTypeEqualTo(Integer value) {
            addCriterion("adx_type =", value, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeNotEqualTo(Integer value) {
            addCriterion("adx_type <>", value, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeGreaterThan(Integer value) {
            addCriterion("adx_type >", value, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("adx_type >=", value, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeLessThan(Integer value) {
            addCriterion("adx_type <", value, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeLessThanOrEqualTo(Integer value) {
            addCriterion("adx_type <=", value, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeIn(List<Integer> values) {
            addCriterion("adx_type in", values, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeNotIn(List<Integer> values) {
            addCriterion("adx_type not in", values, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeBetween(Integer value1, Integer value2) {
            addCriterion("adx_type between", value1, value2, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("adx_type not between", value1, value2, "adxType");
            return (Criteria) this;
        }

        public Criteria andAdxUpidIsNull() {
            addCriterion("adx_upid is null");
            return (Criteria) this;
        }

        public Criteria andAdxUpidIsNotNull() {
            addCriterion("adx_upid is not null");
            return (Criteria) this;
        }

        public Criteria andAdxUpidEqualTo(String value) {
            addCriterion("adx_upid =", value, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidNotEqualTo(String value) {
            addCriterion("adx_upid <>", value, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidGreaterThan(String value) {
            addCriterion("adx_upid >", value, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidGreaterThanOrEqualTo(String value) {
            addCriterion("adx_upid >=", value, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidLessThan(String value) {
            addCriterion("adx_upid <", value, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidLessThanOrEqualTo(String value) {
            addCriterion("adx_upid <=", value, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidLike(String value) {
            addCriterion("adx_upid like", value, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidNotLike(String value) {
            addCriterion("adx_upid not like", value, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidIn(List<String> values) {
            addCriterion("adx_upid in", values, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidNotIn(List<String> values) {
            addCriterion("adx_upid not in", values, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidBetween(String value1, String value2) {
            addCriterion("adx_upid between", value1, value2, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxUpidNotBetween(String value1, String value2) {
            addCriterion("adx_upid not between", value1, value2, "adxUpid");
            return (Criteria) this;
        }

        public Criteria andAdxCridIsNull() {
            addCriterion("adx_crid is null");
            return (Criteria) this;
        }

        public Criteria andAdxCridIsNotNull() {
            addCriterion("adx_crid is not null");
            return (Criteria) this;
        }

        public Criteria andAdxCridEqualTo(String value) {
            addCriterion("adx_crid =", value, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridNotEqualTo(String value) {
            addCriterion("adx_crid <>", value, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridGreaterThan(String value) {
            addCriterion("adx_crid >", value, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridGreaterThanOrEqualTo(String value) {
            addCriterion("adx_crid >=", value, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridLessThan(String value) {
            addCriterion("adx_crid <", value, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridLessThanOrEqualTo(String value) {
            addCriterion("adx_crid <=", value, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridLike(String value) {
            addCriterion("adx_crid like", value, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridNotLike(String value) {
            addCriterion("adx_crid not like", value, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridIn(List<String> values) {
            addCriterion("adx_crid in", values, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridNotIn(List<String> values) {
            addCriterion("adx_crid not in", values, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridBetween(String value1, String value2) {
            addCriterion("adx_crid between", value1, value2, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxCridNotBetween(String value1, String value2) {
            addCriterion("adx_crid not between", value1, value2, "adxCrid");
            return (Criteria) this;
        }

        public Criteria andAdxUrlIsNull() {
            addCriterion("adx_url is null");
            return (Criteria) this;
        }

        public Criteria andAdxUrlIsNotNull() {
            addCriterion("adx_url is not null");
            return (Criteria) this;
        }

        public Criteria andAdxUrlEqualTo(String value) {
            addCriterion("adx_url =", value, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlNotEqualTo(String value) {
            addCriterion("adx_url <>", value, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlGreaterThan(String value) {
            addCriterion("adx_url >", value, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlGreaterThanOrEqualTo(String value) {
            addCriterion("adx_url >=", value, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlLessThan(String value) {
            addCriterion("adx_url <", value, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlLessThanOrEqualTo(String value) {
            addCriterion("adx_url <=", value, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlLike(String value) {
            addCriterion("adx_url like", value, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlNotLike(String value) {
            addCriterion("adx_url not like", value, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlIn(List<String> values) {
            addCriterion("adx_url in", values, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlNotIn(List<String> values) {
            addCriterion("adx_url not in", values, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlBetween(String value1, String value2) {
            addCriterion("adx_url between", value1, value2, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAdxUrlNotBetween(String value1, String value2) {
            addCriterion("adx_url not between", value1, value2, "adxUrl");
            return (Criteria) this;
        }

        public Criteria andAuditStateIsNull() {
            addCriterion("audit_state is null");
            return (Criteria) this;
        }

        public Criteria andAuditStateIsNotNull() {
            addCriterion("audit_state is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStateEqualTo(Integer value) {
            addCriterion("audit_state =", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotEqualTo(Integer value) {
            addCriterion("audit_state <>", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateGreaterThan(Integer value) {
            addCriterion("audit_state >", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_state >=", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateLessThan(Integer value) {
            addCriterion("audit_state <", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateLessThanOrEqualTo(Integer value) {
            addCriterion("audit_state <=", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateIn(List<Integer> values) {
            addCriterion("audit_state in", values, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotIn(List<Integer> values) {
            addCriterion("audit_state not in", values, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateBetween(Integer value1, Integer value2) {
            addCriterion("audit_state between", value1, value2, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_state not between", value1, value2, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsIsNull() {
            addCriterion("audit_comments is null");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsIsNotNull() {
            addCriterion("audit_comments is not null");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsEqualTo(String value) {
            addCriterion("audit_comments =", value, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsNotEqualTo(String value) {
            addCriterion("audit_comments <>", value, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsGreaterThan(String value) {
            addCriterion("audit_comments >", value, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsGreaterThanOrEqualTo(String value) {
            addCriterion("audit_comments >=", value, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsLessThan(String value) {
            addCriterion("audit_comments <", value, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsLessThanOrEqualTo(String value) {
            addCriterion("audit_comments <=", value, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsLike(String value) {
            addCriterion("audit_comments like", value, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsNotLike(String value) {
            addCriterion("audit_comments not like", value, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsIn(List<String> values) {
            addCriterion("audit_comments in", values, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsNotIn(List<String> values) {
            addCriterion("audit_comments not in", values, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsBetween(String value1, String value2) {
            addCriterion("audit_comments between", value1, value2, "auditComments");
            return (Criteria) this;
        }

        public Criteria andAuditCommentsNotBetween(String value1, String value2) {
            addCriterion("audit_comments not between", value1, value2, "auditComments");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andIndustryIsNull() {
            addCriterion("industry is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNotNull() {
            addCriterion("industry is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryEqualTo(Integer value) {
            addCriterion("industry =", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotEqualTo(Integer value) {
            addCriterion("industry <>", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThan(Integer value) {
            addCriterion("industry >", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThanOrEqualTo(Integer value) {
            addCriterion("industry >=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThan(Integer value) {
            addCriterion("industry <", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThanOrEqualTo(Integer value) {
            addCriterion("industry <=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryIn(List<Integer> values) {
            addCriterion("industry in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotIn(List<Integer> values) {
            addCriterion("industry not in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryBetween(Integer value1, Integer value2) {
            addCriterion("industry between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotBetween(Integer value1, Integer value2) {
            addCriterion("industry not between", value1, value2, "industry");
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
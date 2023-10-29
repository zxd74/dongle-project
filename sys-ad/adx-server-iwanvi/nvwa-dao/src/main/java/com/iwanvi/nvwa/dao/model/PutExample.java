package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

public class PutExample extends BaseExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PutExample() {
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

        public Criteria andPutNameIsNull() {
            addCriterion("put_name is null");
            return (Criteria) this;
        }

        public Criteria andPutNameIsNotNull() {
            addCriterion("put_name is not null");
            return (Criteria) this;
        }

        public Criteria andPutNameEqualTo(String value) {
            addCriterion("put_name =", value, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameNotEqualTo(String value) {
            addCriterion("put_name <>", value, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameGreaterThan(String value) {
            addCriterion("put_name >", value, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameGreaterThanOrEqualTo(String value) {
            addCriterion("put_name >=", value, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameLessThan(String value) {
            addCriterion("put_name <", value, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameLessThanOrEqualTo(String value) {
            addCriterion("put_name <=", value, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameLike(String value) {
            addCriterion("put_name like", value, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameNotLike(String value) {
            addCriterion("put_name not like", value, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameIn(List<String> values) {
            addCriterion("put_name in", values, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameNotIn(List<String> values) {
            addCriterion("put_name not in", values, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameBetween(String value1, String value2) {
            addCriterion("put_name between", value1, value2, "putName");
            return (Criteria) this;
        }

        public Criteria andPutNameNotBetween(String value1, String value2) {
            addCriterion("put_name not between", value1, value2, "putName");
            return (Criteria) this;
        }

        public Criteria andOidIsNull() {
            addCriterion("oid is null");
            return (Criteria) this;
        }

        public Criteria andOidIsNotNull() {
            addCriterion("oid is not null");
            return (Criteria) this;
        }

        public Criteria andOidEqualTo(Integer value) {
            addCriterion("oid =", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotEqualTo(Integer value) {
            addCriterion("oid <>", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThan(Integer value) {
            addCriterion("oid >", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidGreaterThanOrEqualTo(Integer value) {
            addCriterion("oid >=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThan(Integer value) {
            addCriterion("oid <", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidLessThanOrEqualTo(Integer value) {
            addCriterion("oid <=", value, "oid");
            return (Criteria) this;
        }

        public Criteria andOidIn(List<Integer> values) {
            addCriterion("oid in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotIn(List<Integer> values) {
            addCriterion("oid not in", values, "oid");
            return (Criteria) this;
        }

        public Criteria andOidBetween(Integer value1, Integer value2) {
            addCriterion("oid between", value1, value2, "oid");
            return (Criteria) this;
        }

        public Criteria andOidNotBetween(Integer value1, Integer value2) {
            addCriterion("oid not between", value1, value2, "oid");
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

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPutTypeIsNull() {
            addCriterion("put_type is null");
            return (Criteria) this;
        }

        public Criteria andPutTypeIsNotNull() {
            addCriterion("put_type is not null");
            return (Criteria) this;
        }

        public Criteria andPutTypeEqualTo(Integer value) {
            addCriterion("put_type =", value, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeNotEqualTo(Integer value) {
            addCriterion("put_type <>", value, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeGreaterThan(Integer value) {
            addCriterion("put_type >", value, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("put_type >=", value, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeLessThan(Integer value) {
            addCriterion("put_type <", value, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeLessThanOrEqualTo(Integer value) {
            addCriterion("put_type <=", value, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeIn(List<Integer> values) {
            addCriterion("put_type in", values, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeNotIn(List<Integer> values) {
            addCriterion("put_type not in", values, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeBetween(Integer value1, Integer value2) {
            addCriterion("put_type between", value1, value2, "putType");
            return (Criteria) this;
        }

        public Criteria andPutTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("put_type not between", value1, value2, "putType");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNull() {
            addCriterion("begin_time is null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIsNotNull() {
            addCriterion("begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andBeginTimeEqualTo(Date value) {
            addCriterion("begin_time =", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotEqualTo(Date value) {
            addCriterion("begin_time <>", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThan(Date value) {
            addCriterion("begin_time >", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("begin_time >=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThan(Date value) {
            addCriterion("begin_time <", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeLessThanOrEqualTo(Date value) {
            addCriterion("begin_time <=", value, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeIn(List<Date> values) {
            addCriterion("begin_time in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotIn(List<Date> values) {
            addCriterion("begin_time not in", values, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeBetween(Date value1, Date value2) {
            addCriterion("begin_time between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andBeginTimeNotBetween(Date value1, Date value2) {
            addCriterion("begin_time not between", value1, value2, "beginTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andCostTypeIsNull() {
            addCriterion("cost_type is null");
            return (Criteria) this;
        }

        public Criteria andCostTypeIsNotNull() {
            addCriterion("cost_type is not null");
            return (Criteria) this;
        }

        public Criteria andCostTypeEqualTo(Integer value) {
            addCriterion("cost_type =", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotEqualTo(Integer value) {
            addCriterion("cost_type <>", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeGreaterThan(Integer value) {
            addCriterion("cost_type >", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("cost_type >=", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeLessThan(Integer value) {
            addCriterion("cost_type <", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeLessThanOrEqualTo(Integer value) {
            addCriterion("cost_type <=", value, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeIn(List<Integer> values) {
            addCriterion("cost_type in", values, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotIn(List<Integer> values) {
            addCriterion("cost_type not in", values, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeBetween(Integer value1, Integer value2) {
            addCriterion("cost_type between", value1, value2, "costType");
            return (Criteria) this;
        }

        public Criteria andCostTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("cost_type not between", value1, value2, "costType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeIsNull() {
            addCriterion("extension_type is null");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeIsNotNull() {
            addCriterion("extension_type is not null");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeEqualTo(Integer value) {
            addCriterion("extension_type =", value, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeNotEqualTo(Integer value) {
            addCriterion("extension_type <>", value, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeGreaterThan(Integer value) {
            addCriterion("extension_type >", value, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("extension_type >=", value, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeLessThan(Integer value) {
            addCriterion("extension_type <", value, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeLessThanOrEqualTo(Integer value) {
            addCriterion("extension_type <=", value, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeIn(List<Integer> values) {
            addCriterion("extension_type in", values, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeNotIn(List<Integer> values) {
            addCriterion("extension_type not in", values, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeBetween(Integer value1, Integer value2) {
            addCriterion("extension_type between", value1, value2, "extensionType");
            return (Criteria) this;
        }

        public Criteria andExtensionTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("extension_type not between", value1, value2, "extensionType");
            return (Criteria) this;
        }

        public Criteria andLandUrlIsNull() {
            addCriterion("land_url is null");
            return (Criteria) this;
        }

        public Criteria andLandUrlIsNotNull() {
            addCriterion("land_url is not null");
            return (Criteria) this;
        }

        public Criteria andLandUrlEqualTo(String value) {
            addCriterion("land_url =", value, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlNotEqualTo(String value) {
            addCriterion("land_url <>", value, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlGreaterThan(String value) {
            addCriterion("land_url >", value, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlGreaterThanOrEqualTo(String value) {
            addCriterion("land_url >=", value, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlLessThan(String value) {
            addCriterion("land_url <", value, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlLessThanOrEqualTo(String value) {
            addCriterion("land_url <=", value, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlLike(String value) {
            addCriterion("land_url like", value, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlNotLike(String value) {
            addCriterion("land_url not like", value, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlIn(List<String> values) {
            addCriterion("land_url in", values, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlNotIn(List<String> values) {
            addCriterion("land_url not in", values, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlBetween(String value1, String value2) {
            addCriterion("land_url between", value1, value2, "landUrl");
            return (Criteria) this;
        }

        public Criteria andLandUrlNotBetween(String value1, String value2) {
            addCriterion("land_url not between", value1, value2, "landUrl");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(String value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(String value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(String value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(String value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(String value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLike(String value) {
            addCriterion("app_id like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotLike(String value) {
            addCriterion("app_id not like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<String> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<String> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(String value1, String value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(String value1, String value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andPkgNameIsNull() {
            addCriterion("pkg_name is null");
            return (Criteria) this;
        }

        public Criteria andPkgNameIsNotNull() {
            addCriterion("pkg_name is not null");
            return (Criteria) this;
        }

        public Criteria andPkgNameEqualTo(String value) {
            addCriterion("pkg_name =", value, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameNotEqualTo(String value) {
            addCriterion("pkg_name <>", value, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameGreaterThan(String value) {
            addCriterion("pkg_name >", value, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameGreaterThanOrEqualTo(String value) {
            addCriterion("pkg_name >=", value, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameLessThan(String value) {
            addCriterion("pkg_name <", value, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameLessThanOrEqualTo(String value) {
            addCriterion("pkg_name <=", value, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameLike(String value) {
            addCriterion("pkg_name like", value, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameNotLike(String value) {
            addCriterion("pkg_name not like", value, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameIn(List<String> values) {
            addCriterion("pkg_name in", values, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameNotIn(List<String> values) {
            addCriterion("pkg_name not in", values, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameBetween(String value1, String value2) {
            addCriterion("pkg_name between", value1, value2, "pkgName");
            return (Criteria) this;
        }

        public Criteria andPkgNameNotBetween(String value1, String value2) {
            addCriterion("pkg_name not between", value1, value2, "pkgName");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalIsNull() {
            addCriterion("time_interval is null");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalIsNotNull() {
            addCriterion("time_interval is not null");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalEqualTo(String value) {
            addCriterion("time_interval =", value, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalNotEqualTo(String value) {
            addCriterion("time_interval <>", value, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalGreaterThan(String value) {
            addCriterion("time_interval >", value, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalGreaterThanOrEqualTo(String value) {
            addCriterion("time_interval >=", value, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalLessThan(String value) {
            addCriterion("time_interval <", value, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalLessThanOrEqualTo(String value) {
            addCriterion("time_interval <=", value, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalLike(String value) {
            addCriterion("time_interval like", value, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalNotLike(String value) {
            addCriterion("time_interval not like", value, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalIn(List<String> values) {
            addCriterion("time_interval in", values, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalNotIn(List<String> values) {
            addCriterion("time_interval not in", values, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalBetween(String value1, String value2) {
            addCriterion("time_interval between", value1, value2, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andTimeIntervalNotBetween(String value1, String value2) {
            addCriterion("time_interval not between", value1, value2, "timeInterval");
            return (Criteria) this;
        }

        public Criteria andDqRuleIsNull() {
            addCriterion("dq_rule is null");
            return (Criteria) this;
        }

        public Criteria andDqRuleIsNotNull() {
            addCriterion("dq_rule is not null");
            return (Criteria) this;
        }

        public Criteria andDqRuleEqualTo(Integer value) {
            addCriterion("dq_rule =", value, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleNotEqualTo(Integer value) {
            addCriterion("dq_rule <>", value, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleGreaterThan(Integer value) {
            addCriterion("dq_rule >", value, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleGreaterThanOrEqualTo(Integer value) {
            addCriterion("dq_rule >=", value, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleLessThan(Integer value) {
            addCriterion("dq_rule <", value, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleLessThanOrEqualTo(Integer value) {
            addCriterion("dq_rule <=", value, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleIn(List<Integer> values) {
            addCriterion("dq_rule in", values, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleNotIn(List<Integer> values) {
            addCriterion("dq_rule not in", values, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleBetween(Integer value1, Integer value2) {
            addCriterion("dq_rule between", value1, value2, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDqRuleNotBetween(Integer value1, Integer value2) {
            addCriterion("dq_rule not between", value1, value2, "dqRule");
            return (Criteria) this;
        }

        public Criteria andDxXlIsNull() {
            addCriterion("dx_xl is null");
            return (Criteria) this;
        }

        public Criteria andDxXlIsNotNull() {
            addCriterion("dx_xl is not null");
            return (Criteria) this;
        }

        public Criteria andDxXlEqualTo(String value) {
            addCriterion("dx_xl =", value, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlNotEqualTo(String value) {
            addCriterion("dx_xl <>", value, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlGreaterThan(String value) {
            addCriterion("dx_xl >", value, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlGreaterThanOrEqualTo(String value) {
            addCriterion("dx_xl >=", value, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlLessThan(String value) {
            addCriterion("dx_xl <", value, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlLessThanOrEqualTo(String value) {
            addCriterion("dx_xl <=", value, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlLike(String value) {
            addCriterion("dx_xl like", value, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlNotLike(String value) {
            addCriterion("dx_xl not like", value, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlIn(List<String> values) {
            addCriterion("dx_xl in", values, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlNotIn(List<String> values) {
            addCriterion("dx_xl not in", values, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlBetween(String value1, String value2) {
            addCriterion("dx_xl between", value1, value2, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxXlNotBetween(String value1, String value2) {
            addCriterion("dx_xl not between", value1, value2, "dxXl");
            return (Criteria) this;
        }

        public Criteria andDxNlIsNull() {
            addCriterion("dx_nl is null");
            return (Criteria) this;
        }

        public Criteria andDxNlIsNotNull() {
            addCriterion("dx_nl is not null");
            return (Criteria) this;
        }

        public Criteria andDxNlEqualTo(String value) {
            addCriterion("dx_nl =", value, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlNotEqualTo(String value) {
            addCriterion("dx_nl <>", value, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlGreaterThan(String value) {
            addCriterion("dx_nl >", value, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlGreaterThanOrEqualTo(String value) {
            addCriterion("dx_nl >=", value, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlLessThan(String value) {
            addCriterion("dx_nl <", value, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlLessThanOrEqualTo(String value) {
            addCriterion("dx_nl <=", value, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlLike(String value) {
            addCriterion("dx_nl like", value, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlNotLike(String value) {
            addCriterion("dx_nl not like", value, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlIn(List<String> values) {
            addCriterion("dx_nl in", values, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlNotIn(List<String> values) {
            addCriterion("dx_nl not in", values, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlBetween(String value1, String value2) {
            addCriterion("dx_nl between", value1, value2, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxNlNotBetween(String value1, String value2) {
            addCriterion("dx_nl not between", value1, value2, "dxNl");
            return (Criteria) this;
        }

        public Criteria andDxXbIsNull() {
            addCriterion("dx_xb is null");
            return (Criteria) this;
        }

        public Criteria andDxXbIsNotNull() {
            addCriterion("dx_xb is not null");
            return (Criteria) this;
        }

        public Criteria andDxXbEqualTo(String value) {
            addCriterion("dx_xb =", value, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbNotEqualTo(String value) {
            addCriterion("dx_xb <>", value, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbGreaterThan(String value) {
            addCriterion("dx_xb >", value, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbGreaterThanOrEqualTo(String value) {
            addCriterion("dx_xb >=", value, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbLessThan(String value) {
            addCriterion("dx_xb <", value, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbLessThanOrEqualTo(String value) {
            addCriterion("dx_xb <=", value, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbLike(String value) {
            addCriterion("dx_xb like", value, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbNotLike(String value) {
            addCriterion("dx_xb not like", value, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbIn(List<String> values) {
            addCriterion("dx_xb in", values, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbNotIn(List<String> values) {
            addCriterion("dx_xb not in", values, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbBetween(String value1, String value2) {
            addCriterion("dx_xb between", value1, value2, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxXbNotBetween(String value1, String value2) {
            addCriterion("dx_xb not between", value1, value2, "dxXb");
            return (Criteria) this;
        }

        public Criteria andDxZdIsNull() {
            addCriterion("dx_zd is null");
            return (Criteria) this;
        }

        public Criteria andDxZdIsNotNull() {
            addCriterion("dx_zd is not null");
            return (Criteria) this;
        }

        public Criteria andDxZdEqualTo(String value) {
            addCriterion("dx_zd =", value, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdNotEqualTo(String value) {
            addCriterion("dx_zd <>", value, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdGreaterThan(String value) {
            addCriterion("dx_zd >", value, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdGreaterThanOrEqualTo(String value) {
            addCriterion("dx_zd >=", value, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdLessThan(String value) {
            addCriterion("dx_zd <", value, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdLessThanOrEqualTo(String value) {
            addCriterion("dx_zd <=", value, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdLike(String value) {
            addCriterion("dx_zd like", value, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdNotLike(String value) {
            addCriterion("dx_zd not like", value, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdIn(List<String> values) {
            addCriterion("dx_zd in", values, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdNotIn(List<String> values) {
            addCriterion("dx_zd not in", values, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdBetween(String value1, String value2) {
            addCriterion("dx_zd between", value1, value2, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxZdNotBetween(String value1, String value2) {
            addCriterion("dx_zd not between", value1, value2, "dxZd");
            return (Criteria) this;
        }

        public Criteria andDxXwIsNull() {
            addCriterion("dx_xw is null");
            return (Criteria) this;
        }

        public Criteria andDxXwIsNotNull() {
            addCriterion("dx_xw is not null");
            return (Criteria) this;
        }

        public Criteria andDxXwEqualTo(String value) {
            addCriterion("dx_xw =", value, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwNotEqualTo(String value) {
            addCriterion("dx_xw <>", value, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwGreaterThan(String value) {
            addCriterion("dx_xw >", value, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwGreaterThanOrEqualTo(String value) {
            addCriterion("dx_xw >=", value, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwLessThan(String value) {
            addCriterion("dx_xw <", value, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwLessThanOrEqualTo(String value) {
            addCriterion("dx_xw <=", value, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwLike(String value) {
            addCriterion("dx_xw like", value, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwNotLike(String value) {
            addCriterion("dx_xw not like", value, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwIn(List<String> values) {
            addCriterion("dx_xw in", values, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwNotIn(List<String> values) {
            addCriterion("dx_xw not in", values, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwBetween(String value1, String value2) {
            addCriterion("dx_xw between", value1, value2, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXwNotBetween(String value1, String value2) {
            addCriterion("dx_xw not between", value1, value2, "dxXw");
            return (Criteria) this;
        }

        public Criteria andDxXqIsNull() {
            addCriterion("dx_xq is null");
            return (Criteria) this;
        }

        public Criteria andDxXqIsNotNull() {
            addCriterion("dx_xq is not null");
            return (Criteria) this;
        }

        public Criteria andDxXqEqualTo(String value) {
            addCriterion("dx_xq =", value, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqNotEqualTo(String value) {
            addCriterion("dx_xq <>", value, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqGreaterThan(String value) {
            addCriterion("dx_xq >", value, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqGreaterThanOrEqualTo(String value) {
            addCriterion("dx_xq >=", value, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqLessThan(String value) {
            addCriterion("dx_xq <", value, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqLessThanOrEqualTo(String value) {
            addCriterion("dx_xq <=", value, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqLike(String value) {
            addCriterion("dx_xq like", value, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqNotLike(String value) {
            addCriterion("dx_xq not like", value, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqIn(List<String> values) {
            addCriterion("dx_xq in", values, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqNotIn(List<String> values) {
            addCriterion("dx_xq not in", values, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqBetween(String value1, String value2) {
            addCriterion("dx_xq between", value1, value2, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxXqNotBetween(String value1, String value2) {
            addCriterion("dx_xq not between", value1, value2, "dxXq");
            return (Criteria) this;
        }

        public Criteria andDxCzxtIsNull() {
            addCriterion("dx_czxt is null");
            return (Criteria) this;
        }

        public Criteria andDxCzxtIsNotNull() {
            addCriterion("dx_czxt is not null");
            return (Criteria) this;
        }

        public Criteria andDxCzxtEqualTo(String value) {
            addCriterion("dx_czxt =", value, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtNotEqualTo(String value) {
            addCriterion("dx_czxt <>", value, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtGreaterThan(String value) {
            addCriterion("dx_czxt >", value, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtGreaterThanOrEqualTo(String value) {
            addCriterion("dx_czxt >=", value, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtLessThan(String value) {
            addCriterion("dx_czxt <", value, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtLessThanOrEqualTo(String value) {
            addCriterion("dx_czxt <=", value, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtLike(String value) {
            addCriterion("dx_czxt like", value, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtNotLike(String value) {
            addCriterion("dx_czxt not like", value, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtIn(List<String> values) {
            addCriterion("dx_czxt in", values, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtNotIn(List<String> values) {
            addCriterion("dx_czxt not in", values, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtBetween(String value1, String value2) {
            addCriterion("dx_czxt between", value1, value2, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxCzxtNotBetween(String value1, String value2) {
            addCriterion("dx_czxt not between", value1, value2, "dxCzxt");
            return (Criteria) this;
        }

        public Criteria andDxYysIsNull() {
            addCriterion("dx_yys is null");
            return (Criteria) this;
        }

        public Criteria andDxYysIsNotNull() {
            addCriterion("dx_yys is not null");
            return (Criteria) this;
        }

        public Criteria andDxYysEqualTo(String value) {
            addCriterion("dx_yys =", value, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysNotEqualTo(String value) {
            addCriterion("dx_yys <>", value, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysGreaterThan(String value) {
            addCriterion("dx_yys >", value, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysGreaterThanOrEqualTo(String value) {
            addCriterion("dx_yys >=", value, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysLessThan(String value) {
            addCriterion("dx_yys <", value, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysLessThanOrEqualTo(String value) {
            addCriterion("dx_yys <=", value, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysLike(String value) {
            addCriterion("dx_yys like", value, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysNotLike(String value) {
            addCriterion("dx_yys not like", value, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysIn(List<String> values) {
            addCriterion("dx_yys in", values, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysNotIn(List<String> values) {
            addCriterion("dx_yys not in", values, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysBetween(String value1, String value2) {
            addCriterion("dx_yys between", value1, value2, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxYysNotBetween(String value1, String value2) {
            addCriterion("dx_yys not between", value1, value2, "dxYys");
            return (Criteria) this;
        }

        public Criteria andDxWlIsNull() {
            addCriterion("dx_wl is null");
            return (Criteria) this;
        }

        public Criteria andDxWlIsNotNull() {
            addCriterion("dx_wl is not null");
            return (Criteria) this;
        }

        public Criteria andDxWlEqualTo(String value) {
            addCriterion("dx_wl =", value, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlNotEqualTo(String value) {
            addCriterion("dx_wl <>", value, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlGreaterThan(String value) {
            addCriterion("dx_wl >", value, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlGreaterThanOrEqualTo(String value) {
            addCriterion("dx_wl >=", value, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlLessThan(String value) {
            addCriterion("dx_wl <", value, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlLessThanOrEqualTo(String value) {
            addCriterion("dx_wl <=", value, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlLike(String value) {
            addCriterion("dx_wl like", value, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlNotLike(String value) {
            addCriterion("dx_wl not like", value, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlIn(List<String> values) {
            addCriterion("dx_wl in", values, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlNotIn(List<String> values) {
            addCriterion("dx_wl not in", values, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlBetween(String value1, String value2) {
            addCriterion("dx_wl between", value1, value2, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxWlNotBetween(String value1, String value2) {
            addCriterion("dx_wl not between", value1, value2, "dxWl");
            return (Criteria) this;
        }

        public Criteria andDxLllyIsNull() {
            addCriterion("dx_llly is null");
            return (Criteria) this;
        }

        public Criteria andDxLllyIsNotNull() {
            addCriterion("dx_llly is not null");
            return (Criteria) this;
        }

        public Criteria andDxLllyEqualTo(String value) {
            addCriterion("dx_llly =", value, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyNotEqualTo(String value) {
            addCriterion("dx_llly <>", value, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyGreaterThan(String value) {
            addCriterion("dx_llly >", value, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyGreaterThanOrEqualTo(String value) {
            addCriterion("dx_llly >=", value, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyLessThan(String value) {
            addCriterion("dx_llly <", value, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyLessThanOrEqualTo(String value) {
            addCriterion("dx_llly <=", value, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyLike(String value) {
            addCriterion("dx_llly like", value, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyNotLike(String value) {
            addCriterion("dx_llly not like", value, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyIn(List<String> values) {
            addCriterion("dx_llly in", values, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyNotIn(List<String> values) {
            addCriterion("dx_llly not in", values, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyBetween(String value1, String value2) {
            addCriterion("dx_llly between", value1, value2, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxLllyNotBetween(String value1, String value2) {
            addCriterion("dx_llly not between", value1, value2, "dxLlly");
            return (Criteria) this;
        }

        public Criteria andDxZdlxIsNull() {
            addCriterion("dx_zdlx is null");
            return (Criteria) this;
        }

        public Criteria andDxZdlxIsNotNull() {
            addCriterion("dx_zdlx is not null");
            return (Criteria) this;
        }

        public Criteria andDxZdlxEqualTo(String value) {
            addCriterion("dx_zdlx =", value, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxNotEqualTo(String value) {
            addCriterion("dx_zdlx <>", value, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxGreaterThan(String value) {
            addCriterion("dx_zdlx >", value, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxGreaterThanOrEqualTo(String value) {
            addCriterion("dx_zdlx >=", value, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxLessThan(String value) {
            addCriterion("dx_zdlx <", value, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxLessThanOrEqualTo(String value) {
            addCriterion("dx_zdlx <=", value, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxLike(String value) {
            addCriterion("dx_zdlx like", value, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxNotLike(String value) {
            addCriterion("dx_zdlx not like", value, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxIn(List<String> values) {
            addCriterion("dx_zdlx in", values, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxNotIn(List<String> values) {
            addCriterion("dx_zdlx not in", values, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxBetween(String value1, String value2) {
            addCriterion("dx_zdlx between", value1, value2, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxZdlxNotBetween(String value1, String value2) {
            addCriterion("dx_zdlx not between", value1, value2, "dxZdlx");
            return (Criteria) this;
        }

        public Criteria andDxSbIsNull() {
            addCriterion("dx_sb is null");
            return (Criteria) this;
        }

        public Criteria andDxSbIsNotNull() {
            addCriterion("dx_sb is not null");
            return (Criteria) this;
        }

        public Criteria andDxSbEqualTo(String value) {
            addCriterion("dx_sb =", value, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbNotEqualTo(String value) {
            addCriterion("dx_sb <>", value, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbGreaterThan(String value) {
            addCriterion("dx_sb >", value, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbGreaterThanOrEqualTo(String value) {
            addCriterion("dx_sb >=", value, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbLessThan(String value) {
            addCriterion("dx_sb <", value, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbLessThanOrEqualTo(String value) {
            addCriterion("dx_sb <=", value, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbLike(String value) {
            addCriterion("dx_sb like", value, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbNotLike(String value) {
            addCriterion("dx_sb not like", value, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbIn(List<String> values) {
            addCriterion("dx_sb in", values, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbNotIn(List<String> values) {
            addCriterion("dx_sb not in", values, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbBetween(String value1, String value2) {
            addCriterion("dx_sb between", value1, value2, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxSbNotBetween(String value1, String value2) {
            addCriterion("dx_sb not between", value1, value2, "dxSb");
            return (Criteria) this;
        }

        public Criteria andDxMediaIsNull() {
            addCriterion("dx_media is null");
            return (Criteria) this;
        }

        public Criteria andDxMediaIsNotNull() {
            addCriterion("dx_media is not null");
            return (Criteria) this;
        }

        public Criteria andDxMediaEqualTo(String value) {
            addCriterion("dx_media =", value, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaNotEqualTo(String value) {
            addCriterion("dx_media <>", value, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaGreaterThan(String value) {
            addCriterion("dx_media >", value, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaGreaterThanOrEqualTo(String value) {
            addCriterion("dx_media >=", value, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaLessThan(String value) {
            addCriterion("dx_media <", value, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaLessThanOrEqualTo(String value) {
            addCriterion("dx_media <=", value, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaLike(String value) {
            addCriterion("dx_media like", value, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaNotLike(String value) {
            addCriterion("dx_media not like", value, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaIn(List<String> values) {
            addCriterion("dx_media in", values, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaNotIn(List<String> values) {
            addCriterion("dx_media not in", values, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaBetween(String value1, String value2) {
            addCriterion("dx_media between", value1, value2, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxMediaNotBetween(String value1, String value2) {
            addCriterion("dx_media not between", value1, value2, "dxMedia");
            return (Criteria) this;
        }

        public Criteria andDxAppIsNull() {
            addCriterion("dx_app is null");
            return (Criteria) this;
        }

        public Criteria andDxAppIsNotNull() {
            addCriterion("dx_app is not null");
            return (Criteria) this;
        }

        public Criteria andDxAppEqualTo(String value) {
            addCriterion("dx_app =", value, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppNotEqualTo(String value) {
            addCriterion("dx_app <>", value, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppGreaterThan(String value) {
            addCriterion("dx_app >", value, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppGreaterThanOrEqualTo(String value) {
            addCriterion("dx_app >=", value, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppLessThan(String value) {
            addCriterion("dx_app <", value, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppLessThanOrEqualTo(String value) {
            addCriterion("dx_app <=", value, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppLike(String value) {
            addCriterion("dx_app like", value, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppNotLike(String value) {
            addCriterion("dx_app not like", value, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppIn(List<String> values) {
            addCriterion("dx_app in", values, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppNotIn(List<String> values) {
            addCriterion("dx_app not in", values, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppBetween(String value1, String value2) {
            addCriterion("dx_app between", value1, value2, "dxApp");
            return (Criteria) this;
        }

        public Criteria andDxAppNotBetween(String value1, String value2) {
            addCriterion("dx_app not between", value1, value2, "dxApp");
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

        public Criteria andPutStateIsNull() {
            addCriterion("put_state is null");
            return (Criteria) this;
        }

        public Criteria andPutStateIsNotNull() {
            addCriterion("put_state is not null");
            return (Criteria) this;
        }

        public Criteria andPutStateEqualTo(Integer value) {
            addCriterion("put_state =", value, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateNotEqualTo(Integer value) {
            addCriterion("put_state <>", value, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateGreaterThan(Integer value) {
            addCriterion("put_state >", value, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("put_state >=", value, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateLessThan(Integer value) {
            addCriterion("put_state <", value, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateLessThanOrEqualTo(Integer value) {
            addCriterion("put_state <=", value, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateIn(List<Integer> values) {
            addCriterion("put_state in", values, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateNotIn(List<Integer> values) {
            addCriterion("put_state not in", values, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateBetween(Integer value1, Integer value2) {
            addCriterion("put_state between", value1, value2, "putState");
            return (Criteria) this;
        }

        public Criteria andPutStateNotBetween(Integer value1, Integer value2) {
            addCriterion("put_state not between", value1, value2, "putState");
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

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Integer value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Integer value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Integer value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Integer value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Integer value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Integer> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Integer> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Integer value1, Integer value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andAdPositionIsNull() {
            addCriterion("ad_position is null");
            return (Criteria) this;
        }

        public Criteria andAdPositionIsNotNull() {
            addCriterion("ad_position is not null");
            return (Criteria) this;
        }

        public Criteria andAdPositionEqualTo(Integer value) {
            addCriterion("ad_position =", value, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionNotEqualTo(Integer value) {
            addCriterion("ad_position <>", value, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionGreaterThan(Integer value) {
            addCriterion("ad_position >", value, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionGreaterThanOrEqualTo(Integer value) {
            addCriterion("ad_position >=", value, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionLessThan(Integer value) {
            addCriterion("ad_position <", value, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionLessThanOrEqualTo(Integer value) {
            addCriterion("ad_position <=", value, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionIn(List<Integer> values) {
            addCriterion("ad_position in", values, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionNotIn(List<Integer> values) {
            addCriterion("ad_position not in", values, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionBetween(Integer value1, Integer value2) {
            addCriterion("ad_position between", value1, value2, "adPosition");
            return (Criteria) this;
        }

        public Criteria andAdPositionNotBetween(Integer value1, Integer value2) {
            addCriterion("ad_position not between", value1, value2, "adPosition");
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

        public Criteria andExtCreativeIdIsNull() {
            addCriterion("ext_creative_id is null");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdIsNotNull() {
            addCriterion("ext_creative_id is not null");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdEqualTo(String value) {
            addCriterion("ext_creative_id =", value, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdNotEqualTo(String value) {
            addCriterion("ext_creative_id <>", value, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdGreaterThan(String value) {
            addCriterion("ext_creative_id >", value, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdGreaterThanOrEqualTo(String value) {
            addCriterion("ext_creative_id >=", value, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdLessThan(String value) {
            addCriterion("ext_creative_id <", value, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdLessThanOrEqualTo(String value) {
            addCriterion("ext_creative_id <=", value, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdLike(String value) {
            addCriterion("ext_creative_id like", value, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdNotLike(String value) {
            addCriterion("ext_creative_id not like", value, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdIn(List<String> values) {
            addCriterion("ext_creative_id in", values, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdNotIn(List<String> values) {
            addCriterion("ext_creative_id not in", values, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdBetween(String value1, String value2) {
            addCriterion("ext_creative_id between", value1, value2, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andExtCreativeIdNotBetween(String value1, String value2) {
            addCriterion("ext_creative_id not between", value1, value2, "extCreativeId");
            return (Criteria) this;
        }

        public Criteria andPutLimitIsNull() {
            addCriterion("put_limit is null");
            return (Criteria) this;
        }

        public Criteria andPutLimitIsNotNull() {
            addCriterion("put_limit is not null");
            return (Criteria) this;
        }

        public Criteria andPutLimitEqualTo(Integer value) {
            addCriterion("put_limit =", value, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitNotEqualTo(Integer value) {
            addCriterion("put_limit <>", value, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitGreaterThan(Integer value) {
            addCriterion("put_limit >", value, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("put_limit >=", value, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitLessThan(Integer value) {
            addCriterion("put_limit <", value, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitLessThanOrEqualTo(Integer value) {
            addCriterion("put_limit <=", value, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitIn(List<Integer> values) {
            addCriterion("put_limit in", values, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitNotIn(List<Integer> values) {
            addCriterion("put_limit not in", values, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitBetween(Integer value1, Integer value2) {
            addCriterion("put_limit between", value1, value2, "putLimit");
            return (Criteria) this;
        }

        public Criteria andPutLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("put_limit not between", value1, value2, "putLimit");
            return (Criteria) this;
        }

        public Criteria andOpUserIsNull() {
            addCriterion("op_user is null");
            return (Criteria) this;
        }

        public Criteria andOpUserIsNotNull() {
            addCriterion("op_user is not null");
            return (Criteria) this;
        }

        public Criteria andOpUserEqualTo(Integer value) {
            addCriterion("op_user =", value, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserNotEqualTo(Integer value) {
            addCriterion("op_user <>", value, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserGreaterThan(Integer value) {
            addCriterion("op_user >", value, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("op_user >=", value, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserLessThan(Integer value) {
            addCriterion("op_user <", value, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserLessThanOrEqualTo(Integer value) {
            addCriterion("op_user <=", value, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserIn(List<Integer> values) {
            addCriterion("op_user in", values, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserNotIn(List<Integer> values) {
            addCriterion("op_user not in", values, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserBetween(Integer value1, Integer value2) {
            addCriterion("op_user between", value1, value2, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserNotBetween(Integer value1, Integer value2) {
            addCriterion("op_user not between", value1, value2, "opUser");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeIsNull() {
            addCriterion("op_user_type is null");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeIsNotNull() {
            addCriterion("op_user_type is not null");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeEqualTo(Integer value) {
            addCriterion("op_user_type =", value, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeNotEqualTo(Integer value) {
            addCriterion("op_user_type <>", value, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeGreaterThan(Integer value) {
            addCriterion("op_user_type >", value, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("op_user_type >=", value, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeLessThan(Integer value) {
            addCriterion("op_user_type <", value, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeLessThanOrEqualTo(Integer value) {
            addCriterion("op_user_type <=", value, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeIn(List<Integer> values) {
            addCriterion("op_user_type in", values, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeNotIn(List<Integer> values) {
            addCriterion("op_user_type not in", values, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeBetween(Integer value1, Integer value2) {
            addCriterion("op_user_type between", value1, value2, "opUserType");
            return (Criteria) this;
        }

        public Criteria andOpUserTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("op_user_type not between", value1, value2, "opUserType");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeIsNull() {
            addCriterion("delivery_mode is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeIsNotNull() {
            addCriterion("delivery_mode is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeEqualTo(Integer value) {
            addCriterion("delivery_mode =", value, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeNotEqualTo(Integer value) {
            addCriterion("delivery_mode <>", value, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeGreaterThan(Integer value) {
            addCriterion("delivery_mode >", value, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("delivery_mode >=", value, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeLessThan(Integer value) {
            addCriterion("delivery_mode <", value, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeLessThanOrEqualTo(Integer value) {
            addCriterion("delivery_mode <=", value, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeIn(List<Integer> values) {
            addCriterion("delivery_mode in", values, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeNotIn(List<Integer> values) {
            addCriterion("delivery_mode not in", values, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeBetween(Integer value1, Integer value2) {
            addCriterion("delivery_mode between", value1, value2, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andDeliveryModeNotBetween(Integer value1, Integer value2) {
            addCriterion("delivery_mode not between", value1, value2, "deliveryMode");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIsNull() {
            addCriterion("is_frequency is null");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIsNotNull() {
            addCriterion("is_frequency is not null");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyEqualTo(Integer value) {
            addCriterion("is_frequency =", value, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyNotEqualTo(Integer value) {
            addCriterion("is_frequency <>", value, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyGreaterThan(Integer value) {
            addCriterion("is_frequency >", value, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_frequency >=", value, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyLessThan(Integer value) {
            addCriterion("is_frequency <", value, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyLessThanOrEqualTo(Integer value) {
            addCriterion("is_frequency <=", value, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIn(List<Integer> values) {
            addCriterion("is_frequency in", values, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyNotIn(List<Integer> values) {
            addCriterion("is_frequency not in", values, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyBetween(Integer value1, Integer value2) {
            addCriterion("is_frequency between", value1, value2, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyNotBetween(Integer value1, Integer value2) {
            addCriterion("is_frequency not between", value1, value2, "isFrequency");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodIsNull() {
            addCriterion("frequenc_period is null");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodIsNotNull() {
            addCriterion("frequenc_period is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodEqualTo(Integer value) {
            addCriterion("frequenc_period =", value, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodNotEqualTo(Integer value) {
            addCriterion("frequenc_period <>", value, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodGreaterThan(Integer value) {
            addCriterion("frequenc_period >", value, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("frequenc_period >=", value, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodLessThan(Integer value) {
            addCriterion("frequenc_period <", value, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("frequenc_period <=", value, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodIn(List<Integer> values) {
            addCriterion("frequenc_period in", values, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodNotIn(List<Integer> values) {
            addCriterion("frequenc_period not in", values, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodBetween(Integer value1, Integer value2) {
            addCriterion("frequenc_period between", value1, value2, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("frequenc_period not between", value1, value2, "frequencPeriod");
            return (Criteria) this;
        }

        public Criteria andFrequencNumIsNull() {
            addCriterion("frequenc_num is null");
            return (Criteria) this;
        }

        public Criteria andFrequencNumIsNotNull() {
            addCriterion("frequenc_num is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencNumEqualTo(Integer value) {
            addCriterion("frequenc_num =", value, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumNotEqualTo(Integer value) {
            addCriterion("frequenc_num <>", value, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumGreaterThan(Integer value) {
            addCriterion("frequenc_num >", value, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("frequenc_num >=", value, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumLessThan(Integer value) {
            addCriterion("frequenc_num <", value, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumLessThanOrEqualTo(Integer value) {
            addCriterion("frequenc_num <=", value, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumIn(List<Integer> values) {
            addCriterion("frequenc_num in", values, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumNotIn(List<Integer> values) {
            addCriterion("frequenc_num not in", values, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumBetween(Integer value1, Integer value2) {
            addCriterion("frequenc_num between", value1, value2, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andFrequencNumNotBetween(Integer value1, Integer value2) {
            addCriterion("frequenc_num not between", value1, value2, "frequencNum");
            return (Criteria) this;
        }

        public Criteria andLimitsIsNull() {
            addCriterion("limits is null");
            return (Criteria) this;
        }

        public Criteria andLimitsIsNotNull() {
            addCriterion("limits is not null");
            return (Criteria) this;
        }

        public Criteria andLimitsEqualTo(String value) {
            addCriterion("limits =", value, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsNotEqualTo(String value) {
            addCriterion("limits <>", value, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsGreaterThan(String value) {
            addCriterion("limits >", value, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsGreaterThanOrEqualTo(String value) {
            addCriterion("limits >=", value, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsLessThan(String value) {
            addCriterion("limits <", value, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsLessThanOrEqualTo(String value) {
            addCriterion("limits <=", value, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsLike(String value) {
            addCriterion("limits like", value, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsNotLike(String value) {
            addCriterion("limits not like", value, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsIn(List<String> values) {
            addCriterion("limits in", values, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsNotIn(List<String> values) {
            addCriterion("limits not in", values, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsBetween(String value1, String value2) {
            addCriterion("limits between", value1, value2, "limits");
            return (Criteria) this;
        }

        public Criteria andLimitsNotBetween(String value1, String value2) {
            addCriterion("limits not between", value1, value2, "limits");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeIsNull() {
            addCriterion("is_optimize is null");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeIsNotNull() {
            addCriterion("is_optimize is not null");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeEqualTo(Integer value) {
            addCriterion("is_optimize =", value, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeNotEqualTo(Integer value) {
            addCriterion("is_optimize <>", value, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeGreaterThan(Integer value) {
            addCriterion("is_optimize >", value, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_optimize >=", value, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeLessThan(Integer value) {
            addCriterion("is_optimize <", value, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeLessThanOrEqualTo(Integer value) {
            addCriterion("is_optimize <=", value, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeIn(List<Integer> values) {
            addCriterion("is_optimize in", values, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeNotIn(List<Integer> values) {
            addCriterion("is_optimize not in", values, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeBetween(Integer value1, Integer value2) {
            addCriterion("is_optimize between", value1, value2, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andIsOptimizeNotBetween(Integer value1, Integer value2) {
            addCriterion("is_optimize not between", value1, value2, "isOptimize");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaIsNull() {
            addCriterion("optimize_cpa is null");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaIsNotNull() {
            addCriterion("optimize_cpa is not null");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaEqualTo(Integer value) {
            addCriterion("optimize_cpa =", value, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaNotEqualTo(Integer value) {
            addCriterion("optimize_cpa <>", value, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaGreaterThan(Integer value) {
            addCriterion("optimize_cpa >", value, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaGreaterThanOrEqualTo(Integer value) {
            addCriterion("optimize_cpa >=", value, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaLessThan(Integer value) {
            addCriterion("optimize_cpa <", value, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaLessThanOrEqualTo(Integer value) {
            addCriterion("optimize_cpa <=", value, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaIn(List<Integer> values) {
            addCriterion("optimize_cpa in", values, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaNotIn(List<Integer> values) {
            addCriterion("optimize_cpa not in", values, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaBetween(Integer value1, Integer value2) {
            addCriterion("optimize_cpa between", value1, value2, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andOptimizeCpaNotBetween(Integer value1, Integer value2) {
            addCriterion("optimize_cpa not between", value1, value2, "optimizeCpa");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeIsNull() {
            addCriterion("dx_app_type is null");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeIsNotNull() {
            addCriterion("dx_app_type is not null");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeEqualTo(String value) {
            addCriterion("dx_app_type =", value, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeNotEqualTo(String value) {
            addCriterion("dx_app_type <>", value, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeGreaterThan(String value) {
            addCriterion("dx_app_type >", value, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeGreaterThanOrEqualTo(String value) {
            addCriterion("dx_app_type >=", value, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeLessThan(String value) {
            addCriterion("dx_app_type <", value, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeLessThanOrEqualTo(String value) {
            addCriterion("dx_app_type <=", value, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeLike(String value) {
            addCriterion("dx_app_type like", value, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeNotLike(String value) {
            addCriterion("dx_app_type not like", value, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeIn(List<String> values) {
            addCriterion("dx_app_type in", values, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeNotIn(List<String> values) {
            addCriterion("dx_app_type not in", values, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeBetween(String value1, String value2) {
            addCriterion("dx_app_type between", value1, value2, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andDxAppTypeNotBetween(String value1, String value2) {
            addCriterion("dx_app_type not between", value1, value2, "dxAppType");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeIsNull() {
            addCriterion("isfilter_device_code is null");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeIsNotNull() {
            addCriterion("isfilter_device_code is not null");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeEqualTo(Integer value) {
            addCriterion("isfilter_device_code =", value, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeNotEqualTo(Integer value) {
            addCriterion("isfilter_device_code <>", value, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeGreaterThan(Integer value) {
            addCriterion("isfilter_device_code >", value, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("isfilter_device_code >=", value, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeLessThan(Integer value) {
            addCriterion("isfilter_device_code <", value, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeLessThanOrEqualTo(Integer value) {
            addCriterion("isfilter_device_code <=", value, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeIn(List<Integer> values) {
            addCriterion("isfilter_device_code in", values, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeNotIn(List<Integer> values) {
            addCriterion("isfilter_device_code not in", values, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeBetween(Integer value1, Integer value2) {
            addCriterion("isfilter_device_code between", value1, value2, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsfilterDeviceCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("isfilter_device_code not between", value1, value2, "isfilterDeviceCode");
            return (Criteria) this;
        }

        public Criteria andIsPdbIsNull() {
            addCriterion("is_pdb is null");
            return (Criteria) this;
        }

        public Criteria andIsPdbIsNotNull() {
            addCriterion("is_pdb is not null");
            return (Criteria) this;
        }

        public Criteria andIsPdbEqualTo(Integer value) {
            addCriterion("is_pdb =", value, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbNotEqualTo(Integer value) {
            addCriterion("is_pdb <>", value, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbGreaterThan(Integer value) {
            addCriterion("is_pdb >", value, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_pdb >=", value, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbLessThan(Integer value) {
            addCriterion("is_pdb <", value, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbLessThanOrEqualTo(Integer value) {
            addCriterion("is_pdb <=", value, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbIn(List<Integer> values) {
            addCriterion("is_pdb in", values, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbNotIn(List<Integer> values) {
            addCriterion("is_pdb not in", values, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbBetween(Integer value1, Integer value2) {
            addCriterion("is_pdb between", value1, value2, "isPdb");
            return (Criteria) this;
        }

        public Criteria andIsPdbNotBetween(Integer value1, Integer value2) {
            addCriterion("is_pdb not between", value1, value2, "isPdb");
            return (Criteria) this;
        }

        public Criteria andDealIdIsNull() {
            addCriterion("deal_id is null");
            return (Criteria) this;
        }

        public Criteria andDealIdIsNotNull() {
            addCriterion("deal_id is not null");
            return (Criteria) this;
        }

        public Criteria andDealIdEqualTo(String value) {
            addCriterion("deal_id =", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdNotEqualTo(String value) {
            addCriterion("deal_id <>", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdGreaterThan(String value) {
            addCriterion("deal_id >", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdGreaterThanOrEqualTo(String value) {
            addCriterion("deal_id >=", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdLessThan(String value) {
            addCriterion("deal_id <", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdLessThanOrEqualTo(String value) {
            addCriterion("deal_id <=", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdLike(String value) {
            addCriterion("deal_id like", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdNotLike(String value) {
            addCriterion("deal_id not like", value, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdIn(List<String> values) {
            addCriterion("deal_id in", values, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdNotIn(List<String> values) {
            addCriterion("deal_id not in", values, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdBetween(String value1, String value2) {
            addCriterion("deal_id between", value1, value2, "dealId");
            return (Criteria) this;
        }

        public Criteria andDealIdNotBetween(String value1, String value2) {
            addCriterion("deal_id not between", value1, value2, "dealId");
            return (Criteria) this;
        }

        public Criteria andDxFrameIsNull() {
            addCriterion("dx_frame is null");
            return (Criteria) this;
        }

        public Criteria andDxFrameIsNotNull() {
            addCriterion("dx_frame is not null");
            return (Criteria) this;
        }

        public Criteria andDxFrameEqualTo(String value) {
            addCriterion("dx_frame =", value, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameNotEqualTo(String value) {
            addCriterion("dx_frame <>", value, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameGreaterThan(String value) {
            addCriterion("dx_frame >", value, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameGreaterThanOrEqualTo(String value) {
            addCriterion("dx_frame >=", value, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameLessThan(String value) {
            addCriterion("dx_frame <", value, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameLessThanOrEqualTo(String value) {
            addCriterion("dx_frame <=", value, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameLike(String value) {
            addCriterion("dx_frame like", value, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameNotLike(String value) {
            addCriterion("dx_frame not like", value, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameIn(List<String> values) {
            addCriterion("dx_frame in", values, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameNotIn(List<String> values) {
            addCriterion("dx_frame not in", values, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameBetween(String value1, String value2) {
            addCriterion("dx_frame between", value1, value2, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxFrameNotBetween(String value1, String value2) {
            addCriterion("dx_frame not between", value1, value2, "dxFrame");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeIsNull() {
            addCriterion("dx_adType is null");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeIsNotNull() {
            addCriterion("dx_adType is not null");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeEqualTo(String value) {
            addCriterion("dx_adType =", value, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeNotEqualTo(String value) {
            addCriterion("dx_adType <>", value, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeGreaterThan(String value) {
            addCriterion("dx_adType >", value, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeGreaterThanOrEqualTo(String value) {
            addCriterion("dx_adType >=", value, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeLessThan(String value) {
            addCriterion("dx_adType <", value, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeLessThanOrEqualTo(String value) {
            addCriterion("dx_adType <=", value, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeLike(String value) {
            addCriterion("dx_adType like", value, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeNotLike(String value) {
            addCriterion("dx_adType not like", value, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeIn(List<String> values) {
            addCriterion("dx_adType in", values, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeNotIn(List<String> values) {
            addCriterion("dx_adType not in", values, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeBetween(String value1, String value2) {
            addCriterion("dx_adType between", value1, value2, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andDxAdtypeNotBetween(String value1, String value2) {
            addCriterion("dx_adType not between", value1, value2, "dxAdtype");
            return (Criteria) this;
        }

        public Criteria andO2UrlIsNull() {
            addCriterion("o2_url is null");
            return (Criteria) this;
        }

        public Criteria andO2UrlIsNotNull() {
            addCriterion("o2_url is not null");
            return (Criteria) this;
        }

        public Criteria andO2UrlEqualTo(String value) {
            addCriterion("o2_url =", value, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlNotEqualTo(String value) {
            addCriterion("o2_url <>", value, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlGreaterThan(String value) {
            addCriterion("o2_url >", value, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlGreaterThanOrEqualTo(String value) {
            addCriterion("o2_url >=", value, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlLessThan(String value) {
            addCriterion("o2_url <", value, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlLessThanOrEqualTo(String value) {
            addCriterion("o2_url <=", value, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlLike(String value) {
            addCriterion("o2_url like", value, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlNotLike(String value) {
            addCriterion("o2_url not like", value, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlIn(List<String> values) {
            addCriterion("o2_url in", values, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlNotIn(List<String> values) {
            addCriterion("o2_url not in", values, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlBetween(String value1, String value2) {
            addCriterion("o2_url between", value1, value2, "o2Url");
            return (Criteria) this;
        }

        public Criteria andO2UrlNotBetween(String value1, String value2) {
            addCriterion("o2_url not between", value1, value2, "o2Url");
            return (Criteria) this;
        }

        public Criteria andDxRqIsNull() {
            addCriterion("dx_rq is null");
            return (Criteria) this;
        }

        public Criteria andDxRqIsNotNull() {
            addCriterion("dx_rq is not null");
            return (Criteria) this;
        }

        public Criteria andDxRqEqualTo(String value) {
            addCriterion("dx_rq =", value, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqNotEqualTo(String value) {
            addCriterion("dx_rq <>", value, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqGreaterThan(String value) {
            addCriterion("dx_rq >", value, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqGreaterThanOrEqualTo(String value) {
            addCriterion("dx_rq >=", value, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqLessThan(String value) {
            addCriterion("dx_rq <", value, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqLessThanOrEqualTo(String value) {
            addCriterion("dx_rq <=", value, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqLike(String value) {
            addCriterion("dx_rq like", value, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqNotLike(String value) {
            addCriterion("dx_rq not like", value, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqIn(List<String> values) {
            addCriterion("dx_rq in", values, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqNotIn(List<String> values) {
            addCriterion("dx_rq not in", values, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqBetween(String value1, String value2) {
            addCriterion("dx_rq between", value1, value2, "dxRq");
            return (Criteria) this;
        }

        public Criteria andDxRqNotBetween(String value1, String value2) {
            addCriterion("dx_rq not between", value1, value2, "dxRq");
            return (Criteria) this;
        }

        public Criteria andIsAttributionIsNull() {
            addCriterion("is_attribution is null");
            return (Criteria) this;
        }

        public Criteria andIsAttributionIsNotNull() {
            addCriterion("is_attribution is not null");
            return (Criteria) this;
        }

        public Criteria andIsAttributionEqualTo(Integer value) {
            addCriterion("is_attribution =", value, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionNotEqualTo(Integer value) {
            addCriterion("is_attribution <>", value, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionGreaterThan(Integer value) {
            addCriterion("is_attribution >", value, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_attribution >=", value, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionLessThan(Integer value) {
            addCriterion("is_attribution <", value, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionLessThanOrEqualTo(Integer value) {
            addCriterion("is_attribution <=", value, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionIn(List<Integer> values) {
            addCriterion("is_attribution in", values, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionNotIn(List<Integer> values) {
            addCriterion("is_attribution not in", values, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionBetween(Integer value1, Integer value2) {
            addCriterion("is_attribution between", value1, value2, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andIsAttributionNotBetween(Integer value1, Integer value2) {
            addCriterion("is_attribution not between", value1, value2, "isAttribution");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioIsNull() {
            addCriterion("attribution_ratio is null");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioIsNotNull() {
            addCriterion("attribution_ratio is not null");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioEqualTo(Double value) {
            addCriterion("attribution_ratio =", value, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioNotEqualTo(Double value) {
            addCriterion("attribution_ratio <>", value, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioGreaterThan(Double value) {
            addCriterion("attribution_ratio >", value, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioGreaterThanOrEqualTo(Double value) {
            addCriterion("attribution_ratio >=", value, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioLessThan(Double value) {
            addCriterion("attribution_ratio <", value, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioLessThanOrEqualTo(Double value) {
            addCriterion("attribution_ratio <=", value, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioIn(List<Double> values) {
            addCriterion("attribution_ratio in", values, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioNotIn(List<Double> values) {
            addCriterion("attribution_ratio not in", values, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioBetween(Double value1, Double value2) {
            addCriterion("attribution_ratio between", value1, value2, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andAttributionRatioNotBetween(Double value1, Double value2) {
            addCriterion("attribution_ratio not between", value1, value2, "attributionRatio");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNull() {
            addCriterion("platform is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNotNull() {
            addCriterion("platform is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformEqualTo(Integer value) {
            addCriterion("platform =", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotEqualTo(Integer value) {
            addCriterion("platform <>", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThan(Integer value) {
            addCriterion("platform >", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThanOrEqualTo(Integer value) {
            addCriterion("platform >=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThan(Integer value) {
            addCriterion("platform <", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThanOrEqualTo(Integer value) {
            addCriterion("platform <=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformIn(List<Integer> values) {
            addCriterion("platform in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotIn(List<Integer> values) {
            addCriterion("platform not in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformBetween(Integer value1, Integer value2) {
            addCriterion("platform between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotBetween(Integer value1, Integer value2) {
            addCriterion("platform not between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpIsNull() {
            addCriterion("is_frequency_ip is null");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpIsNotNull() {
            addCriterion("is_frequency_ip is not null");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpEqualTo(Integer value) {
            addCriterion("is_frequency_ip =", value, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpNotEqualTo(Integer value) {
            addCriterion("is_frequency_ip <>", value, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpGreaterThan(Integer value) {
            addCriterion("is_frequency_ip >", value, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_frequency_ip >=", value, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpLessThan(Integer value) {
            addCriterion("is_frequency_ip <", value, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpLessThanOrEqualTo(Integer value) {
            addCriterion("is_frequency_ip <=", value, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpIn(List<Integer> values) {
            addCriterion("is_frequency_ip in", values, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpNotIn(List<Integer> values) {
            addCriterion("is_frequency_ip not in", values, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpBetween(Integer value1, Integer value2) {
            addCriterion("is_frequency_ip between", value1, value2, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andIsFrequencyIpNotBetween(Integer value1, Integer value2) {
            addCriterion("is_frequency_ip not between", value1, value2, "isFrequencyIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpIsNull() {
            addCriterion("frequency_num_ip is null");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpIsNotNull() {
            addCriterion("frequency_num_ip is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpEqualTo(Integer value) {
            addCriterion("frequency_num_ip =", value, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpNotEqualTo(Integer value) {
            addCriterion("frequency_num_ip <>", value, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpGreaterThan(Integer value) {
            addCriterion("frequency_num_ip >", value, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpGreaterThanOrEqualTo(Integer value) {
            addCriterion("frequency_num_ip >=", value, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpLessThan(Integer value) {
            addCriterion("frequency_num_ip <", value, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpLessThanOrEqualTo(Integer value) {
            addCriterion("frequency_num_ip <=", value, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpIn(List<Integer> values) {
            addCriterion("frequency_num_ip in", values, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpNotIn(List<Integer> values) {
            addCriterion("frequency_num_ip not in", values, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpBetween(Integer value1, Integer value2) {
            addCriterion("frequency_num_ip between", value1, value2, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyNumIpNotBetween(Integer value1, Integer value2) {
            addCriterion("frequency_num_ip not between", value1, value2, "frequencyNumIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpIsNull() {
            addCriterion("frequency_period_ip is null");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpIsNotNull() {
            addCriterion("frequency_period_ip is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpEqualTo(Integer value) {
            addCriterion("frequency_period_ip =", value, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpNotEqualTo(Integer value) {
            addCriterion("frequency_period_ip <>", value, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpGreaterThan(Integer value) {
            addCriterion("frequency_period_ip >", value, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpGreaterThanOrEqualTo(Integer value) {
            addCriterion("frequency_period_ip >=", value, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpLessThan(Integer value) {
            addCriterion("frequency_period_ip <", value, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpLessThanOrEqualTo(Integer value) {
            addCriterion("frequency_period_ip <=", value, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpIn(List<Integer> values) {
            addCriterion("frequency_period_ip in", values, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpNotIn(List<Integer> values) {
            addCriterion("frequency_period_ip not in", values, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpBetween(Integer value1, Integer value2) {
            addCriterion("frequency_period_ip between", value1, value2, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFrequencyPeriodIpNotBetween(Integer value1, Integer value2) {
            addCriterion("frequency_period_ip not between", value1, value2, "frequencyPeriodIp");
            return (Criteria) this;
        }

        public Criteria andFilterAppIsNull() {
            addCriterion("filter_app is null");
            return (Criteria) this;
        }

        public Criteria andFilterAppIsNotNull() {
            addCriterion("filter_app is not null");
            return (Criteria) this;
        }

        public Criteria andFilterAppEqualTo(Integer value) {
            addCriterion("filter_app =", value, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppNotEqualTo(Integer value) {
            addCriterion("filter_app <>", value, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppGreaterThan(Integer value) {
            addCriterion("filter_app >", value, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppGreaterThanOrEqualTo(Integer value) {
            addCriterion("filter_app >=", value, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppLessThan(Integer value) {
            addCriterion("filter_app <", value, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppLessThanOrEqualTo(Integer value) {
            addCriterion("filter_app <=", value, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppIn(List<Integer> values) {
            addCriterion("filter_app in", values, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppNotIn(List<Integer> values) {
            addCriterion("filter_app not in", values, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppBetween(Integer value1, Integer value2) {
            addCriterion("filter_app between", value1, value2, "filterApp");
            return (Criteria) this;
        }

        public Criteria andFilterAppNotBetween(Integer value1, Integer value2) {
            addCriterion("filter_app not between", value1, value2, "filterApp");
            return (Criteria) this;
        }

        public Criteria andVersioncodeIsNull() {
            addCriterion("versioncode is null");
            return (Criteria) this;
        }

        public Criteria andVersioncodeIsNotNull() {
            addCriterion("versioncode is not null");
            return (Criteria) this;
        }

        public Criteria andVersioncodeEqualTo(Integer value) {
            addCriterion("versioncode =", value, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeNotEqualTo(Integer value) {
            addCriterion("versioncode <>", value, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeGreaterThan(Integer value) {
            addCriterion("versioncode >", value, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("versioncode >=", value, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeLessThan(Integer value) {
            addCriterion("versioncode <", value, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeLessThanOrEqualTo(Integer value) {
            addCriterion("versioncode <=", value, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeIn(List<Integer> values) {
            addCriterion("versioncode in", values, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeNotIn(List<Integer> values) {
            addCriterion("versioncode not in", values, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeBetween(Integer value1, Integer value2) {
            addCriterion("versioncode between", value1, value2, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersioncodeNotBetween(Integer value1, Integer value2) {
            addCriterion("versioncode not between", value1, value2, "versioncode");
            return (Criteria) this;
        }

        public Criteria andVersionnameIsNull() {
            addCriterion("versionname is null");
            return (Criteria) this;
        }

        public Criteria andVersionnameIsNotNull() {
            addCriterion("versionname is not null");
            return (Criteria) this;
        }

        public Criteria andVersionnameEqualTo(String value) {
            addCriterion("versionname =", value, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameNotEqualTo(String value) {
            addCriterion("versionname <>", value, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameGreaterThan(String value) {
            addCriterion("versionname >", value, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameGreaterThanOrEqualTo(String value) {
            addCriterion("versionname >=", value, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameLessThan(String value) {
            addCriterion("versionname <", value, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameLessThanOrEqualTo(String value) {
            addCriterion("versionname <=", value, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameLike(String value) {
            addCriterion("versionname like", value, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameNotLike(String value) {
            addCriterion("versionname not like", value, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameIn(List<String> values) {
            addCriterion("versionname in", values, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameNotIn(List<String> values) {
            addCriterion("versionname not in", values, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameBetween(String value1, String value2) {
            addCriterion("versionname between", value1, value2, "versionname");
            return (Criteria) this;
        }

        public Criteria andVersionnameNotBetween(String value1, String value2) {
            addCriterion("versionname not between", value1, value2, "versionname");
            return (Criteria) this;
        }

        public Criteria andSizeIsNull() {
            addCriterion("size is null");
            return (Criteria) this;
        }

        public Criteria andSizeIsNotNull() {
            addCriterion("size is not null");
            return (Criteria) this;
        }

        public Criteria andSizeEqualTo(Integer value) {
            addCriterion("size =", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotEqualTo(Integer value) {
            addCriterion("size <>", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThan(Integer value) {
            addCriterion("size >", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("size >=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThan(Integer value) {
            addCriterion("size <", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeLessThanOrEqualTo(Integer value) {
            addCriterion("size <=", value, "size");
            return (Criteria) this;
        }

        public Criteria andSizeIn(List<Integer> values) {
            addCriterion("size in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotIn(List<Integer> values) {
            addCriterion("size not in", values, "size");
            return (Criteria) this;
        }

        public Criteria andSizeBetween(Integer value1, Integer value2) {
            addCriterion("size between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("size not between", value1, value2, "size");
            return (Criteria) this;
        }

        public Criteria andSignIsNull() {
            addCriterion("sign is null");
            return (Criteria) this;
        }

        public Criteria andSignIsNotNull() {
            addCriterion("sign is not null");
            return (Criteria) this;
        }

        public Criteria andSignEqualTo(String value) {
            addCriterion("sign =", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotEqualTo(String value) {
            addCriterion("sign <>", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThan(String value) {
            addCriterion("sign >", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignGreaterThanOrEqualTo(String value) {
            addCriterion("sign >=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThan(String value) {
            addCriterion("sign <", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLessThanOrEqualTo(String value) {
            addCriterion("sign <=", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignLike(String value) {
            addCriterion("sign like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotLike(String value) {
            addCriterion("sign not like", value, "sign");
            return (Criteria) this;
        }

        public Criteria andSignIn(List<String> values) {
            addCriterion("sign in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotIn(List<String> values) {
            addCriterion("sign not in", values, "sign");
            return (Criteria) this;
        }

        public Criteria andSignBetween(String value1, String value2) {
            addCriterion("sign between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andSignNotBetween(String value1, String value2) {
            addCriterion("sign not between", value1, value2, "sign");
            return (Criteria) this;
        }

        public Criteria andMd5IsNull() {
            addCriterion("md5 is null");
            return (Criteria) this;
        }

        public Criteria andMd5IsNotNull() {
            addCriterion("md5 is not null");
            return (Criteria) this;
        }

        public Criteria andMd5EqualTo(String value) {
            addCriterion("md5 =", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotEqualTo(String value) {
            addCriterion("md5 <>", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThan(String value) {
            addCriterion("md5 >", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThanOrEqualTo(String value) {
            addCriterion("md5 >=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThan(String value) {
            addCriterion("md5 <", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThanOrEqualTo(String value) {
            addCriterion("md5 <=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Like(String value) {
            addCriterion("md5 like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotLike(String value) {
            addCriterion("md5 not like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5In(List<String> values) {
            addCriterion("md5 in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotIn(List<String> values) {
            addCriterion("md5 not in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Between(String value1, String value2) {
            addCriterion("md5 between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotBetween(String value1, String value2) {
            addCriterion("md5 not between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelIsNull() {
            addCriterion("minsdklevel is null");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelIsNotNull() {
            addCriterion("minsdklevel is not null");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelEqualTo(Integer value) {
            addCriterion("minsdklevel =", value, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelNotEqualTo(Integer value) {
            addCriterion("minsdklevel <>", value, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelGreaterThan(Integer value) {
            addCriterion("minsdklevel >", value, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("minsdklevel >=", value, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelLessThan(Integer value) {
            addCriterion("minsdklevel <", value, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelLessThanOrEqualTo(Integer value) {
            addCriterion("minsdklevel <=", value, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelIn(List<Integer> values) {
            addCriterion("minsdklevel in", values, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelNotIn(List<Integer> values) {
            addCriterion("minsdklevel not in", values, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelBetween(Integer value1, Integer value2) {
            addCriterion("minsdklevel between", value1, value2, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andMinsdklevelNotBetween(Integer value1, Integer value2) {
            addCriterion("minsdklevel not between", value1, value2, "minsdklevel");
            return (Criteria) this;
        }

        public Criteria andDxModelIsNull() {
            addCriterion("dx_model is null");
            return (Criteria) this;
        }

        public Criteria andDxModelIsNotNull() {
            addCriterion("dx_model is not null");
            return (Criteria) this;
        }

        public Criteria andDxModelEqualTo(String value) {
            addCriterion("dx_model =", value, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelNotEqualTo(String value) {
            addCriterion("dx_model <>", value, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelGreaterThan(String value) {
            addCriterion("dx_model >", value, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelGreaterThanOrEqualTo(String value) {
            addCriterion("dx_model >=", value, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelLessThan(String value) {
            addCriterion("dx_model <", value, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelLessThanOrEqualTo(String value) {
            addCriterion("dx_model <=", value, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelLike(String value) {
            addCriterion("dx_model like", value, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelNotLike(String value) {
            addCriterion("dx_model not like", value, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelIn(List<String> values) {
            addCriterion("dx_model in", values, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelNotIn(List<String> values) {
            addCriterion("dx_model not in", values, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelBetween(String value1, String value2) {
            addCriterion("dx_model between", value1, value2, "dxModel");
            return (Criteria) this;
        }

        public Criteria andDxModelNotBetween(String value1, String value2) {
            addCriterion("dx_model not between", value1, value2, "dxModel");
            return (Criteria) this;
        }

        public Criteria andIsRedirectIsNull() {
            addCriterion("is_redirect is null");
            return (Criteria) this;
        }

        public Criteria andIsRedirectIsNotNull() {
            addCriterion("is_redirect is not null");
            return (Criteria) this;
        }

        public Criteria andIsRedirectEqualTo(String value) {
            addCriterion("is_redirect =", value, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectNotEqualTo(String value) {
            addCriterion("is_redirect <>", value, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectGreaterThan(String value) {
            addCriterion("is_redirect >", value, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectGreaterThanOrEqualTo(String value) {
            addCriterion("is_redirect >=", value, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectLessThan(String value) {
            addCriterion("is_redirect <", value, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectLessThanOrEqualTo(String value) {
            addCriterion("is_redirect <=", value, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectLike(String value) {
            addCriterion("is_redirect like", value, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectNotLike(String value) {
            addCriterion("is_redirect not like", value, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectIn(List<String> values) {
            addCriterion("is_redirect in", values, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectNotIn(List<String> values) {
            addCriterion("is_redirect not in", values, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectBetween(String value1, String value2) {
            addCriterion("is_redirect between", value1, value2, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andIsRedirectNotBetween(String value1, String value2) {
            addCriterion("is_redirect not between", value1, value2, "isRedirect");
            return (Criteria) this;
        }

        public Criteria andDxChannelIsNull() {
            addCriterion("dx_channel is null");
            return (Criteria) this;
        }

        public Criteria andDxChannelIsNotNull() {
            addCriterion("dx_channel is not null");
            return (Criteria) this;
        }

        public Criteria andDxChannelEqualTo(String value) {
            addCriterion("dx_channel =", value, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelNotEqualTo(String value) {
            addCriterion("dx_channel <>", value, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelGreaterThan(String value) {
            addCriterion("dx_channel >", value, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelGreaterThanOrEqualTo(String value) {
            addCriterion("dx_channel >=", value, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelLessThan(String value) {
            addCriterion("dx_channel <", value, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelLessThanOrEqualTo(String value) {
            addCriterion("dx_channel <=", value, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelLike(String value) {
            addCriterion("dx_channel like", value, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelNotLike(String value) {
            addCriterion("dx_channel not like", value, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelIn(List<String> values) {
            addCriterion("dx_channel in", values, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelNotIn(List<String> values) {
            addCriterion("dx_channel not in", values, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelBetween(String value1, String value2) {
            addCriterion("dx_channel between", value1, value2, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxChannelNotBetween(String value1, String value2) {
            addCriterion("dx_channel not between", value1, value2, "dxChannel");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdIsNull() {
            addCriterion("dx_ggw_level_id is null");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdIsNotNull() {
            addCriterion("dx_ggw_level_id is not null");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdEqualTo(String value) {
            addCriterion("dx_ggw_level_id =", value, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdNotEqualTo(String value) {
            addCriterion("dx_ggw_level_id <>", value, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdGreaterThan(String value) {
            addCriterion("dx_ggw_level_id >", value, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdGreaterThanOrEqualTo(String value) {
            addCriterion("dx_ggw_level_id >=", value, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdLessThan(String value) {
            addCriterion("dx_ggw_level_id <", value, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdLessThanOrEqualTo(String value) {
            addCriterion("dx_ggw_level_id <=", value, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdLike(String value) {
            addCriterion("dx_ggw_level_id like", value, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdNotLike(String value) {
            addCriterion("dx_ggw_level_id not like", value, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdIn(List<String> values) {
            addCriterion("dx_ggw_level_id in", values, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdNotIn(List<String> values) {
            addCriterion("dx_ggw_level_id not in", values, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdBetween(String value1, String value2) {
            addCriterion("dx_ggw_level_id between", value1, value2, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwLevelIdNotBetween(String value1, String value2) {
            addCriterion("dx_ggw_level_id not between", value1, value2, "dxGgwLevelId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdIsNull() {
            addCriterion("dx_ggw_series_id is null");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdIsNotNull() {
            addCriterion("dx_ggw_series_id is not null");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdEqualTo(String value) {
            addCriterion("dx_ggw_series_id =", value, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdNotEqualTo(String value) {
            addCriterion("dx_ggw_series_id <>", value, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdGreaterThan(String value) {
            addCriterion("dx_ggw_series_id >", value, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdGreaterThanOrEqualTo(String value) {
            addCriterion("dx_ggw_series_id >=", value, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdLessThan(String value) {
            addCriterion("dx_ggw_series_id <", value, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdLessThanOrEqualTo(String value) {
            addCriterion("dx_ggw_series_id <=", value, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdLike(String value) {
            addCriterion("dx_ggw_series_id like", value, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdNotLike(String value) {
            addCriterion("dx_ggw_series_id not like", value, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdIn(List<String> values) {
            addCriterion("dx_ggw_series_id in", values, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdNotIn(List<String> values) {
            addCriterion("dx_ggw_series_id not in", values, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdBetween(String value1, String value2) {
            addCriterion("dx_ggw_series_id between", value1, value2, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwSeriesIdNotBetween(String value1, String value2) {
            addCriterion("dx_ggw_series_id not between", value1, value2, "dxGgwSeriesId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdIsNull() {
            addCriterion("dx_ggw_price_tag_id is null");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdIsNotNull() {
            addCriterion("dx_ggw_price_tag_id is not null");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdEqualTo(String value) {
            addCriterion("dx_ggw_price_tag_id =", value, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdNotEqualTo(String value) {
            addCriterion("dx_ggw_price_tag_id <>", value, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdGreaterThan(String value) {
            addCriterion("dx_ggw_price_tag_id >", value, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdGreaterThanOrEqualTo(String value) {
            addCriterion("dx_ggw_price_tag_id >=", value, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdLessThan(String value) {
            addCriterion("dx_ggw_price_tag_id <", value, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdLessThanOrEqualTo(String value) {
            addCriterion("dx_ggw_price_tag_id <=", value, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdLike(String value) {
            addCriterion("dx_ggw_price_tag_id like", value, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdNotLike(String value) {
            addCriterion("dx_ggw_price_tag_id not like", value, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdIn(List<String> values) {
            addCriterion("dx_ggw_price_tag_id in", values, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdNotIn(List<String> values) {
            addCriterion("dx_ggw_price_tag_id not in", values, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdBetween(String value1, String value2) {
            addCriterion("dx_ggw_price_tag_id between", value1, value2, "dxGgwPriceTagId");
            return (Criteria) this;
        }

        public Criteria andDxGgwPriceTagIdNotBetween(String value1, String value2) {
            addCriterion("dx_ggw_price_tag_id not between", value1, value2, "dxGgwPriceTagId");
            return (Criteria) this;
        }
        
        public Criteria andDxDmpIsNull() {
            addCriterion("dx_dmp is null");
            return (Criteria) this;
        }

        public Criteria andDxDmpIsNotNull() {
            addCriterion("dx_dmp is not null");
            return (Criteria) this;
        }

        public Criteria andDxDmpEqualTo(String value) {
            addCriterion("dx_dmp =", value, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpNotEqualTo(String value) {
            addCriterion("dx_dmp <>", value, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpGreaterThan(String value) {
            addCriterion("dx_dmp >", value, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpGreaterThanOrEqualTo(String value) {
            addCriterion("dx_dmp >=", value, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpLessThan(String value) {
            addCriterion("dx_dmp <", value, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpLessThanOrEqualTo(String value) {
            addCriterion("dx_dmp <=", value, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpLike(String value) {
            addCriterion("dx_dmp like", value, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpNotLike(String value) {
            addCriterion("dx_dmp not like", value, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpIn(List<String> values) {
            addCriterion("dx_dmp in", values, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpNotIn(List<String> values) {
            addCriterion("dx_dmp not in", values, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpBetween(String value1, String value2) {
            addCriterion("dx_dmp between", value1, value2, "dxDmp");
            return (Criteria) this;
        }

        public Criteria andDxDmpNotBetween(String value1, String value2) {
            addCriterion("dx_dmp not between", value1, value2, "dxDmp");
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
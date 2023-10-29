package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.List;

public class SdkAllotScheduleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SdkAllotScheduleExample() {
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

        public Criteria andAllotmentIdIsNull() {
            addCriterion("allotment_id is null");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdIsNotNull() {
            addCriterion("allotment_id is not null");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdEqualTo(Integer value) {
            addCriterion("allotment_id =", value, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdNotEqualTo(Integer value) {
            addCriterion("allotment_id <>", value, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdGreaterThan(Integer value) {
            addCriterion("allotment_id >", value, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("allotment_id >=", value, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdLessThan(Integer value) {
            addCriterion("allotment_id <", value, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("allotment_id <=", value, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdIn(List<Integer> values) {
            addCriterion("allotment_id in", values, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdNotIn(List<Integer> values) {
            addCriterion("allotment_id not in", values, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdBetween(Integer value1, Integer value2) {
            addCriterion("allotment_id between", value1, value2, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAllotmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("allotment_id not between", value1, value2, "allotmentId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdIsNull() {
            addCriterion("ad_pos_id is null");
            return (Criteria) this;
        }

        public Criteria andAdPosIdIsNotNull() {
            addCriterion("ad_pos_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdPosIdEqualTo(Integer value) {
            addCriterion("ad_pos_id =", value, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdNotEqualTo(Integer value) {
            addCriterion("ad_pos_id <>", value, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdGreaterThan(Integer value) {
            addCriterion("ad_pos_id >", value, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ad_pos_id >=", value, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdLessThan(Integer value) {
            addCriterion("ad_pos_id <", value, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdLessThanOrEqualTo(Integer value) {
            addCriterion("ad_pos_id <=", value, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdIn(List<Integer> values) {
            addCriterion("ad_pos_id in", values, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdNotIn(List<Integer> values) {
            addCriterion("ad_pos_id not in", values, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdBetween(Integer value1, Integer value2) {
            addCriterion("ad_pos_id between", value1, value2, "adPosId");
            return (Criteria) this;
        }

        public Criteria andAdPosIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ad_pos_id not between", value1, value2, "adPosId");
            return (Criteria) this;
        }

        public Criteria andStartDayIsNull() {
            addCriterion("start_day is null");
            return (Criteria) this;
        }

        public Criteria andStartDayIsNotNull() {
            addCriterion("start_day is not null");
            return (Criteria) this;
        }

        public Criteria andStartDayEqualTo(Integer value) {
            addCriterion("start_day =", value, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayNotEqualTo(Integer value) {
            addCriterion("start_day <>", value, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayGreaterThan(Integer value) {
            addCriterion("start_day >", value, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_day >=", value, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayLessThan(Integer value) {
            addCriterion("start_day <", value, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayLessThanOrEqualTo(Integer value) {
            addCriterion("start_day <=", value, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayIn(List<Integer> values) {
            addCriterion("start_day in", values, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayNotIn(List<Integer> values) {
            addCriterion("start_day not in", values, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayBetween(Integer value1, Integer value2) {
            addCriterion("start_day between", value1, value2, "startDay");
            return (Criteria) this;
        }

        public Criteria andStartDayNotBetween(Integer value1, Integer value2) {
            addCriterion("start_day not between", value1, value2, "startDay");
            return (Criteria) this;
        }

        public Criteria andEndDayIsNull() {
            addCriterion("end_day is null");
            return (Criteria) this;
        }

        public Criteria andEndDayIsNotNull() {
            addCriterion("end_day is not null");
            return (Criteria) this;
        }

        public Criteria andEndDayEqualTo(Integer value) {
            addCriterion("end_day =", value, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayNotEqualTo(Integer value) {
            addCriterion("end_day <>", value, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayGreaterThan(Integer value) {
            addCriterion("end_day >", value, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_day >=", value, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayLessThan(Integer value) {
            addCriterion("end_day <", value, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayLessThanOrEqualTo(Integer value) {
            addCriterion("end_day <=", value, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayIn(List<Integer> values) {
            addCriterion("end_day in", values, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayNotIn(List<Integer> values) {
            addCriterion("end_day not in", values, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayBetween(Integer value1, Integer value2) {
            addCriterion("end_day between", value1, value2, "endDay");
            return (Criteria) this;
        }

        public Criteria andEndDayNotBetween(Integer value1, Integer value2) {
            addCriterion("end_day not between", value1, value2, "endDay");
            return (Criteria) this;
        }

        public Criteria andIsSectionIsNull() {
            addCriterion("is_section is null");
            return (Criteria) this;
        }

        public Criteria andIsSectionIsNotNull() {
            addCriterion("is_section is not null");
            return (Criteria) this;
        }

        public Criteria andIsSectionEqualTo(Integer value) {
            addCriterion("is_section =", value, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionNotEqualTo(Integer value) {
            addCriterion("is_section <>", value, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionGreaterThan(Integer value) {
            addCriterion("is_section >", value, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_section >=", value, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionLessThan(Integer value) {
            addCriterion("is_section <", value, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionLessThanOrEqualTo(Integer value) {
            addCriterion("is_section <=", value, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionIn(List<Integer> values) {
            addCriterion("is_section in", values, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionNotIn(List<Integer> values) {
            addCriterion("is_section not in", values, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionBetween(Integer value1, Integer value2) {
            addCriterion("is_section between", value1, value2, "isSection");
            return (Criteria) this;
        }

        public Criteria andIsSectionNotBetween(Integer value1, Integer value2) {
            addCriterion("is_section not between", value1, value2, "isSection");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedIsNull() {
            addCriterion("schedule_fixed is null");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedIsNotNull() {
            addCriterion("schedule_fixed is not null");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedEqualTo(String value) {
            addCriterion("schedule_fixed =", value, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedNotEqualTo(String value) {
            addCriterion("schedule_fixed <>", value, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedGreaterThan(String value) {
            addCriterion("schedule_fixed >", value, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedGreaterThanOrEqualTo(String value) {
            addCriterion("schedule_fixed >=", value, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedLessThan(String value) {
            addCriterion("schedule_fixed <", value, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedLessThanOrEqualTo(String value) {
            addCriterion("schedule_fixed <=", value, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedLike(String value) {
            addCriterion("schedule_fixed like", value, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedNotLike(String value) {
            addCriterion("schedule_fixed not like", value, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedIn(List<String> values) {
            addCriterion("schedule_fixed in", values, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedNotIn(List<String> values) {
            addCriterion("schedule_fixed not in", values, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedBetween(String value1, String value2) {
            addCriterion("schedule_fixed between", value1, value2, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andScheduleFixedNotBetween(String value1, String value2) {
            addCriterion("schedule_fixed not between", value1, value2, "scheduleFixed");
            return (Criteria) this;
        }

        public Criteria andExposureLimitIsNull() {
            addCriterion("exposure_limit is null");
            return (Criteria) this;
        }

        public Criteria andExposureLimitIsNotNull() {
            addCriterion("exposure_limit is not null");
            return (Criteria) this;
        }

        public Criteria andExposureLimitEqualTo(Integer value) {
            addCriterion("exposure_limit =", value, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitNotEqualTo(Integer value) {
            addCriterion("exposure_limit <>", value, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitGreaterThan(Integer value) {
            addCriterion("exposure_limit >", value, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("exposure_limit >=", value, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitLessThan(Integer value) {
            addCriterion("exposure_limit <", value, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitLessThanOrEqualTo(Integer value) {
            addCriterion("exposure_limit <=", value, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitIn(List<Integer> values) {
            addCriterion("exposure_limit in", values, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitNotIn(List<Integer> values) {
            addCriterion("exposure_limit not in", values, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitBetween(Integer value1, Integer value2) {
            addCriterion("exposure_limit between", value1, value2, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andExposureLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("exposure_limit not between", value1, value2, "exposureLimit");
            return (Criteria) this;
        }

        public Criteria andIsSmoothIsNull() {
            addCriterion("is_smooth is null");
            return (Criteria) this;
        }

        public Criteria andIsSmoothIsNotNull() {
            addCriterion("is_smooth is not null");
            return (Criteria) this;
        }

        public Criteria andIsSmoothEqualTo(Integer value) {
            addCriterion("is_smooth =", value, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothNotEqualTo(Integer value) {
            addCriterion("is_smooth <>", value, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothGreaterThan(Integer value) {
            addCriterion("is_smooth >", value, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_smooth >=", value, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothLessThan(Integer value) {
            addCriterion("is_smooth <", value, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothLessThanOrEqualTo(Integer value) {
            addCriterion("is_smooth <=", value, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothIn(List<Integer> values) {
            addCriterion("is_smooth in", values, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothNotIn(List<Integer> values) {
            addCriterion("is_smooth not in", values, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothBetween(Integer value1, Integer value2) {
            addCriterion("is_smooth between", value1, value2, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andIsSmoothNotBetween(Integer value1, Integer value2) {
            addCriterion("is_smooth not between", value1, value2, "isSmooth");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNull() {
            addCriterion("priority is null");
            return (Criteria) this;
        }

        public Criteria andPriorityIsNotNull() {
            addCriterion("priority is not null");
            return (Criteria) this;
        }

        public Criteria andPriorityEqualTo(Integer value) {
            addCriterion("priority =", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotEqualTo(Integer value) {
            addCriterion("priority <>", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThan(Integer value) {
            addCriterion("priority >", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("priority >=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThan(Integer value) {
            addCriterion("priority <", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("priority <=", value, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityIn(List<Integer> values) {
            addCriterion("priority in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotIn(List<Integer> values) {
            addCriterion("priority not in", values, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityBetween(Integer value1, Integer value2) {
            addCriterion("priority between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("priority not between", value1, value2, "priority");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodIsNull() {
            addCriterion("month_period is null");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodIsNotNull() {
            addCriterion("month_period is not null");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodEqualTo(Integer value) {
            addCriterion("month_period =", value, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodNotEqualTo(Integer value) {
            addCriterion("month_period <>", value, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodGreaterThan(Integer value) {
            addCriterion("month_period >", value, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("month_period >=", value, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodLessThan(Integer value) {
            addCriterion("month_period <", value, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodLessThanOrEqualTo(Integer value) {
            addCriterion("month_period <=", value, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodIn(List<Integer> values) {
            addCriterion("month_period in", values, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodNotIn(List<Integer> values) {
            addCriterion("month_period not in", values, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodBetween(Integer value1, Integer value2) {
            addCriterion("month_period between", value1, value2, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andMonthPeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("month_period not between", value1, value2, "monthPeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodIsNull() {
            addCriterion("schedule_period is null");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodIsNotNull() {
            addCriterion("schedule_period is not null");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodEqualTo(Integer value) {
            addCriterion("schedule_period =", value, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodNotEqualTo(Integer value) {
            addCriterion("schedule_period <>", value, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodGreaterThan(Integer value) {
            addCriterion("schedule_period >", value, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodGreaterThanOrEqualTo(Integer value) {
            addCriterion("schedule_period >=", value, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodLessThan(Integer value) {
            addCriterion("schedule_period <", value, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodLessThanOrEqualTo(Integer value) {
            addCriterion("schedule_period <=", value, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodIn(List<Integer> values) {
            addCriterion("schedule_period in", values, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodNotIn(List<Integer> values) {
            addCriterion("schedule_period not in", values, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodBetween(Integer value1, Integer value2) {
            addCriterion("schedule_period between", value1, value2, "schedulePeriod");
            return (Criteria) this;
        }

        public Criteria andSchedulePeriodNotBetween(Integer value1, Integer value2) {
            addCriterion("schedule_period not between", value1, value2, "schedulePeriod");
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

        public Criteria andStraTimeIsNull() {
            addCriterion("stra_time is null");
            return (Criteria) this;
        }

        public Criteria andStraTimeIsNotNull() {
            addCriterion("stra_time is not null");
            return (Criteria) this;
        }

        public Criteria andStraTimeEqualTo(Integer value) {
            addCriterion("stra_time =", value, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeNotEqualTo(Integer value) {
            addCriterion("stra_time <>", value, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeGreaterThan(Integer value) {
            addCriterion("stra_time >", value, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("stra_time >=", value, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeLessThan(Integer value) {
            addCriterion("stra_time <", value, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeLessThanOrEqualTo(Integer value) {
            addCriterion("stra_time <=", value, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeIn(List<Integer> values) {
            addCriterion("stra_time in", values, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeNotIn(List<Integer> values) {
            addCriterion("stra_time not in", values, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeBetween(Integer value1, Integer value2) {
            addCriterion("stra_time between", value1, value2, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("stra_time not between", value1, value2, "straTime");
            return (Criteria) this;
        }

        public Criteria andStraChapterIsNull() {
            addCriterion("stra_chapter is null");
            return (Criteria) this;
        }

        public Criteria andStraChapterIsNotNull() {
            addCriterion("stra_chapter is not null");
            return (Criteria) this;
        }

        public Criteria andStraChapterEqualTo(Integer value) {
            addCriterion("stra_chapter =", value, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterNotEqualTo(Integer value) {
            addCriterion("stra_chapter <>", value, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterGreaterThan(Integer value) {
            addCriterion("stra_chapter >", value, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterGreaterThanOrEqualTo(Integer value) {
            addCriterion("stra_chapter >=", value, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterLessThan(Integer value) {
            addCriterion("stra_chapter <", value, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterLessThanOrEqualTo(Integer value) {
            addCriterion("stra_chapter <=", value, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterIn(List<Integer> values) {
            addCriterion("stra_chapter in", values, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterNotIn(List<Integer> values) {
            addCriterion("stra_chapter not in", values, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterBetween(Integer value1, Integer value2) {
            addCriterion("stra_chapter between", value1, value2, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraChapterNotBetween(Integer value1, Integer value2) {
            addCriterion("stra_chapter not between", value1, value2, "straChapter");
            return (Criteria) this;
        }

        public Criteria andStraPageIsNull() {
            addCriterion("stra_page is null");
            return (Criteria) this;
        }

        public Criteria andStraPageIsNotNull() {
            addCriterion("stra_page is not null");
            return (Criteria) this;
        }

        public Criteria andStraPageEqualTo(Integer value) {
            addCriterion("stra_page =", value, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageNotEqualTo(Integer value) {
            addCriterion("stra_page <>", value, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageGreaterThan(Integer value) {
            addCriterion("stra_page >", value, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageGreaterThanOrEqualTo(Integer value) {
            addCriterion("stra_page >=", value, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageLessThan(Integer value) {
            addCriterion("stra_page <", value, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageLessThanOrEqualTo(Integer value) {
            addCriterion("stra_page <=", value, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageIn(List<Integer> values) {
            addCriterion("stra_page in", values, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageNotIn(List<Integer> values) {
            addCriterion("stra_page not in", values, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageBetween(Integer value1, Integer value2) {
            addCriterion("stra_page between", value1, value2, "straPage");
            return (Criteria) this;
        }

        public Criteria andStraPageNotBetween(Integer value1, Integer value2) {
            addCriterion("stra_page not between", value1, value2, "straPage");
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
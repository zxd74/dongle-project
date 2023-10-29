package com.iwanvi.nvwa.dao.model;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdvertiserDspExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdvertiserDspExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdIsNull() {
            addCriterion("flow_consumer_id is null");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdIsNotNull() {
            addCriterion("flow_consumer_id is not null");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdEqualTo(Integer value) {
            addCriterion("flow_consumer_id =", value, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdNotEqualTo(Integer value) {
            addCriterion("flow_consumer_id <>", value, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdGreaterThan(Integer value) {
            addCriterion("flow_consumer_id >", value, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("flow_consumer_id >=", value, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdLessThan(Integer value) {
            addCriterion("flow_consumer_id <", value, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdLessThanOrEqualTo(Integer value) {
            addCriterion("flow_consumer_id <=", value, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdIn(List<Integer> values) {
            addCriterion("flow_consumer_id in", values, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdNotIn(List<Integer> values) {
            addCriterion("flow_consumer_id not in", values, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdBetween(Integer value1, Integer value2) {
            addCriterion("flow_consumer_id between", value1, value2, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andFlowConsumerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("flow_consumer_id not between", value1, value2, "flowConsumerId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdIsNull() {
            addCriterion("dsp_advertiser_id is null");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdIsNotNull() {
            addCriterion("dsp_advertiser_id is not null");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdEqualTo(String value) {
            addCriterion("dsp_advertiser_id =", value, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdNotEqualTo(String value) {
            addCriterion("dsp_advertiser_id <>", value, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdGreaterThan(String value) {
            addCriterion("dsp_advertiser_id >", value, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdGreaterThanOrEqualTo(String value) {
            addCriterion("dsp_advertiser_id >=", value, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdLessThan(String value) {
            addCriterion("dsp_advertiser_id <", value, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdLessThanOrEqualTo(String value) {
            addCriterion("dsp_advertiser_id <=", value, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdLike(String value) {
            addCriterion("dsp_advertiser_id like", value, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdNotLike(String value) {
            addCriterion("dsp_advertiser_id not like", value, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdIn(List<String> values) {
            addCriterion("dsp_advertiser_id in", values, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdNotIn(List<String> values) {
            addCriterion("dsp_advertiser_id not in", values, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdBetween(String value1, String value2) {
            addCriterion("dsp_advertiser_id between", value1, value2, "dspAdvertiserId");
            return (Criteria) this;
        }

        public Criteria andDspAdvertiserIdNotBetween(String value1, String value2) {
            addCriterion("dsp_advertiser_id not between", value1, value2, "dspAdvertiserId");
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

        public Criteria andExaminesStatusIsNull() {
            addCriterion("examines_status is null");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusIsNotNull() {
            addCriterion("examines_status is not null");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusEqualTo(Integer value) {
            addCriterion("examines_status =", value, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusNotEqualTo(Integer value) {
            addCriterion("examines_status <>", value, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusGreaterThan(Integer value) {
            addCriterion("examines_status >", value, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("examines_status >=", value, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusLessThan(Integer value) {
            addCriterion("examines_status <", value, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusLessThanOrEqualTo(Integer value) {
            addCriterion("examines_status <=", value, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusIn(List<Integer> values) {
            addCriterion("examines_status in", values, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusNotIn(List<Integer> values) {
            addCriterion("examines_status not in", values, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusBetween(Integer value1, Integer value2) {
            addCriterion("examines_status between", value1, value2, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("examines_status not between", value1, value2, "examinesStatus");
            return (Criteria) this;
        }

        public Criteria andExaminesUserIsNull() {
            addCriterion("examines_user is null");
            return (Criteria) this;
        }

        public Criteria andExaminesUserIsNotNull() {
            addCriterion("examines_user is not null");
            return (Criteria) this;
        }

        public Criteria andExaminesUserEqualTo(Integer value) {
            addCriterion("examines_user =", value, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserNotEqualTo(Integer value) {
            addCriterion("examines_user <>", value, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserGreaterThan(Integer value) {
            addCriterion("examines_user >", value, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("examines_user >=", value, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserLessThan(Integer value) {
            addCriterion("examines_user <", value, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserLessThanOrEqualTo(Integer value) {
            addCriterion("examines_user <=", value, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserIn(List<Integer> values) {
            addCriterion("examines_user in", values, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserNotIn(List<Integer> values) {
            addCriterion("examines_user not in", values, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserBetween(Integer value1, Integer value2) {
            addCriterion("examines_user between", value1, value2, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesUserNotBetween(Integer value1, Integer value2) {
            addCriterion("examines_user not between", value1, value2, "examinesUser");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeIsNull() {
            addCriterion("examines_time is null");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeIsNotNull() {
            addCriterion("examines_time is not null");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeEqualTo(Date value) {
            addCriterion("examines_time =", value, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeNotEqualTo(Date value) {
            addCriterion("examines_time <>", value, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeGreaterThan(Date value) {
            addCriterion("examines_time >", value, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("examines_time >=", value, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeLessThan(Date value) {
            addCriterion("examines_time <", value, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeLessThanOrEqualTo(Date value) {
            addCriterion("examines_time <=", value, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeIn(List<Date> values) {
            addCriterion("examines_time in", values, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeNotIn(List<Date> values) {
            addCriterion("examines_time not in", values, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeBetween(Date value1, Date value2) {
            addCriterion("examines_time between", value1, value2, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesTimeNotBetween(Date value1, Date value2) {
            addCriterion("examines_time not between", value1, value2, "examinesTime");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksIsNull() {
            addCriterion("examines_remarks is null");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksIsNotNull() {
            addCriterion("examines_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksEqualTo(String value) {
            addCriterion("examines_remarks =", value, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksNotEqualTo(String value) {
            addCriterion("examines_remarks <>", value, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksGreaterThan(String value) {
            addCriterion("examines_remarks >", value, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("examines_remarks >=", value, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksLessThan(String value) {
            addCriterion("examines_remarks <", value, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksLessThanOrEqualTo(String value) {
            addCriterion("examines_remarks <=", value, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksLike(String value) {
            addCriterion("examines_remarks like", value, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksNotLike(String value) {
            addCriterion("examines_remarks not like", value, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksIn(List<String> values) {
            addCriterion("examines_remarks in", values, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksNotIn(List<String> values) {
            addCriterion("examines_remarks not in", values, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksBetween(String value1, String value2) {
            addCriterion("examines_remarks between", value1, value2, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andExaminesRemarksNotBetween(String value1, String value2) {
            addCriterion("examines_remarks not between", value1, value2, "examinesRemarks");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseIsNull() {
            addCriterion("businesslicense is null");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseIsNotNull() {
            addCriterion("businesslicense is not null");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseEqualTo(String value) {
            addCriterion("businesslicense =", value, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseNotEqualTo(String value) {
            addCriterion("businesslicense <>", value, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseGreaterThan(String value) {
            addCriterion("businesslicense >", value, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseGreaterThanOrEqualTo(String value) {
            addCriterion("businesslicense >=", value, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseLessThan(String value) {
            addCriterion("businesslicense <", value, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseLessThanOrEqualTo(String value) {
            addCriterion("businesslicense <=", value, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseLike(String value) {
            addCriterion("businesslicense like", value, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseNotLike(String value) {
            addCriterion("businesslicense not like", value, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseIn(List<String> values) {
            addCriterion("businesslicense in", values, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseNotIn(List<String> values) {
            addCriterion("businesslicense not in", values, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseBetween(String value1, String value2) {
            addCriterion("businesslicense between", value1, value2, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andBusinesslicenseNotBetween(String value1, String value2) {
            addCriterion("businesslicense not between", value1, value2, "businesslicense");
            return (Criteria) this;
        }

        public Criteria andQualificationsIsNull() {
            addCriterion("qualifications is null");
            return (Criteria) this;
        }

        public Criteria andQualificationsIsNotNull() {
            addCriterion("qualifications is not null");
            return (Criteria) this;
        }

        public Criteria andQualificationsEqualTo(String value) {
            addCriterion("qualifications =", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotEqualTo(String value) {
            addCriterion("qualifications <>", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsGreaterThan(String value) {
            addCriterion("qualifications >", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsGreaterThanOrEqualTo(String value) {
            addCriterion("qualifications >=", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLessThan(String value) {
            addCriterion("qualifications <", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLessThanOrEqualTo(String value) {
            addCriterion("qualifications <=", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsLike(String value) {
            addCriterion("qualifications like", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotLike(String value) {
            addCriterion("qualifications not like", value, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsIn(List<String> values) {
            addCriterion("qualifications in", values, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotIn(List<String> values) {
            addCriterion("qualifications not in", values, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsBetween(String value1, String value2) {
            addCriterion("qualifications between", value1, value2, "qualifications");
            return (Criteria) this;
        }

        public Criteria andQualificationsNotBetween(String value1, String value2) {
            addCriterion("qualifications not between", value1, value2, "qualifications");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeIsNull() {
            addCriterion("industry_type is null");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeIsNotNull() {
            addCriterion("industry_type is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeEqualTo(Integer value) {
            addCriterion("industry_type =", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeNotEqualTo(Integer value) {
            addCriterion("industry_type <>", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeGreaterThan(Integer value) {
            addCriterion("industry_type >", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("industry_type >=", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeLessThan(Integer value) {
            addCriterion("industry_type <", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeLessThanOrEqualTo(Integer value) {
            addCriterion("industry_type <=", value, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeIn(List<Integer> values) {
            addCriterion("industry_type in", values, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeNotIn(List<Integer> values) {
            addCriterion("industry_type not in", values, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeBetween(Integer value1, Integer value2) {
            addCriterion("industry_type between", value1, value2, "industryType");
            return (Criteria) this;
        }

        public Criteria andIndustryTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("industry_type not between", value1, value2, "industryType");
            return (Criteria) this;
        }

        public Criteria andLinkmanIsNull() {
            addCriterion("linkman is null");
            return (Criteria) this;
        }

        public Criteria andLinkmanIsNotNull() {
            addCriterion("linkman is not null");
            return (Criteria) this;
        }

        public Criteria andLinkmanEqualTo(String value) {
            addCriterion("linkman =", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotEqualTo(String value) {
            addCriterion("linkman <>", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanGreaterThan(String value) {
            addCriterion("linkman >", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanGreaterThanOrEqualTo(String value) {
            addCriterion("linkman >=", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLessThan(String value) {
            addCriterion("linkman <", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLessThanOrEqualTo(String value) {
            addCriterion("linkman <=", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanLike(String value) {
            addCriterion("linkman like", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotLike(String value) {
            addCriterion("linkman not like", value, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanIn(List<String> values) {
            addCriterion("linkman in", values, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotIn(List<String> values) {
            addCriterion("linkman not in", values, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanBetween(String value1, String value2) {
            addCriterion("linkman between", value1, value2, "linkman");
            return (Criteria) this;
        }

        public Criteria andLinkmanNotBetween(String value1, String value2) {
            addCriterion("linkman not between", value1, value2, "linkman");
            return (Criteria) this;
        }

        public Criteria andTelIsNull() {
            addCriterion("tel is null");
            return (Criteria) this;
        }

        public Criteria andTelIsNotNull() {
            addCriterion("tel is not null");
            return (Criteria) this;
        }

        public Criteria andTelEqualTo(String value) {
            addCriterion("tel =", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotEqualTo(String value) {
            addCriterion("tel <>", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThan(String value) {
            addCriterion("tel >", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelGreaterThanOrEqualTo(String value) {
            addCriterion("tel >=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThan(String value) {
            addCriterion("tel <", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLessThanOrEqualTo(String value) {
            addCriterion("tel <=", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelLike(String value) {
            addCriterion("tel like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotLike(String value) {
            addCriterion("tel not like", value, "tel");
            return (Criteria) this;
        }

        public Criteria andTelIn(List<String> values) {
            addCriterion("tel in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotIn(List<String> values) {
            addCriterion("tel not in", values, "tel");
            return (Criteria) this;
        }

        public Criteria andTelBetween(String value1, String value2) {
            addCriterion("tel between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andTelNotBetween(String value1, String value2) {
            addCriterion("tel not between", value1, value2, "tel");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
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
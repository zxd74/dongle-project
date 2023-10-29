package com.iwanvi.nvwa.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PackagePositionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PackagePositionExample() {
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

        public Criteria andFlowIdIsNull() {
            addCriterion("flow_id is null");
            return (Criteria) this;
        }

        public Criteria andFlowIdIsNotNull() {
            addCriterion("flow_id is not null");
            return (Criteria) this;
        }

        public Criteria andFlowIdEqualTo(Integer value) {
            addCriterion("flow_id =", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotEqualTo(Integer value) {
            addCriterion("flow_id <>", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThan(Integer value) {
            addCriterion("flow_id >", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("flow_id >=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThan(Integer value) {
            addCriterion("flow_id <", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdLessThanOrEqualTo(Integer value) {
            addCriterion("flow_id <=", value, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdIn(List<Integer> values) {
            addCriterion("flow_id in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotIn(List<Integer> values) {
            addCriterion("flow_id not in", values, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdBetween(Integer value1, Integer value2) {
            addCriterion("flow_id between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andFlowIdNotBetween(Integer value1, Integer value2) {
            addCriterion("flow_id not between", value1, value2, "flowId");
            return (Criteria) this;
        }

        public Criteria andForecastExposureIsNull() {
            addCriterion("\"forecast_ exposure\" is null");
            return (Criteria) this;
        }

        public Criteria andForecastExposureIsNotNull() {
            addCriterion("\"forecast_ exposure\" is not null");
            return (Criteria) this;
        }

        public Criteria andForecastExposureEqualTo(Integer value) {
            addCriterion("\"forecast_ exposure\" =", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureNotEqualTo(Integer value) {
            addCriterion("\"forecast_ exposure\" <>", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureGreaterThan(Integer value) {
            addCriterion("\"forecast_ exposure\" >", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureGreaterThanOrEqualTo(Integer value) {
            addCriterion("\"forecast_ exposure\" >=", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureLessThan(Integer value) {
            addCriterion("\"forecast_ exposure\" <", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureLessThanOrEqualTo(Integer value) {
            addCriterion("\"forecast_ exposure\" <=", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureIn(List<Integer> values) {
            addCriterion("\"forecast_ exposure\" in", values, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureNotIn(List<Integer> values) {
            addCriterion("\"forecast_ exposure\" not in", values, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureBetween(Integer value1, Integer value2) {
            addCriterion("\"forecast_ exposure\" between", value1, value2, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureNotBetween(Integer value1, Integer value2) {
            addCriterion("\"forecast_ exposure\" not between", value1, value2, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastClickIsNull() {
            addCriterion("forecast_click is null");
            return (Criteria) this;
        }

        public Criteria andForecastClickIsNotNull() {
            addCriterion("forecast_click is not null");
            return (Criteria) this;
        }

        public Criteria andForecastClickEqualTo(Integer value) {
            addCriterion("forecast_click =", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickNotEqualTo(Integer value) {
            addCriterion("forecast_click <>", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickGreaterThan(Integer value) {
            addCriterion("forecast_click >", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickGreaterThanOrEqualTo(Integer value) {
            addCriterion("forecast_click >=", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickLessThan(Integer value) {
            addCriterion("forecast_click <", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickLessThanOrEqualTo(Integer value) {
            addCriterion("forecast_click <=", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickIn(List<Integer> values) {
            addCriterion("forecast_click in", values, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickNotIn(List<Integer> values) {
            addCriterion("forecast_click not in", values, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickBetween(Integer value1, Integer value2) {
            addCriterion("forecast_click between", value1, value2, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickNotBetween(Integer value1, Integer value2) {
            addCriterion("forecast_click not between", value1, value2, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andPriceRtbIsNull() {
            addCriterion("price_rtb is null");
            return (Criteria) this;
        }

        public Criteria andPriceRtbIsNotNull() {
            addCriterion("price_rtb is not null");
            return (Criteria) this;
        }

        public Criteria andPriceRtbEqualTo(Integer value) {
            addCriterion("price_rtb =", value, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbNotEqualTo(Integer value) {
            addCriterion("price_rtb <>", value, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbGreaterThan(Integer value) {
            addCriterion("price_rtb >", value, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbGreaterThanOrEqualTo(Integer value) {
            addCriterion("price_rtb >=", value, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbLessThan(Integer value) {
            addCriterion("price_rtb <", value, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbLessThanOrEqualTo(Integer value) {
            addCriterion("price_rtb <=", value, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbIn(List<Integer> values) {
            addCriterion("price_rtb in", values, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbNotIn(List<Integer> values) {
            addCriterion("price_rtb not in", values, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbBetween(Integer value1, Integer value2) {
            addCriterion("price_rtb between", value1, value2, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceRtbNotBetween(Integer value1, Integer value2) {
            addCriterion("price_rtb not between", value1, value2, "priceRtb");
            return (Criteria) this;
        }

        public Criteria andPriceOtherIsNull() {
            addCriterion("price_other is null");
            return (Criteria) this;
        }

        public Criteria andPriceOtherIsNotNull() {
            addCriterion("price_other is not null");
            return (Criteria) this;
        }

        public Criteria andPriceOtherEqualTo(Integer value) {
            addCriterion("price_other =", value, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherNotEqualTo(Integer value) {
            addCriterion("price_other <>", value, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherGreaterThan(Integer value) {
            addCriterion("price_other >", value, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherGreaterThanOrEqualTo(Integer value) {
            addCriterion("price_other >=", value, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherLessThan(Integer value) {
            addCriterion("price_other <", value, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherLessThanOrEqualTo(Integer value) {
            addCriterion("price_other <=", value, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherIn(List<Integer> values) {
            addCriterion("price_other in", values, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherNotIn(List<Integer> values) {
            addCriterion("price_other not in", values, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherBetween(Integer value1, Integer value2) {
            addCriterion("price_other between", value1, value2, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPriceOtherNotBetween(Integer value1, Integer value2) {
            addCriterion("price_other not between", value1, value2, "priceOther");
            return (Criteria) this;
        }

        public Criteria andPositionIdsIsNull() {
            addCriterion("position_ids is null");
            return (Criteria) this;
        }

        public Criteria andPositionIdsIsNotNull() {
            addCriterion("position_ids is not null");
            return (Criteria) this;
        }

        public Criteria andPositionIdsEqualTo(Integer value) {
            addCriterion("position_ids =", value, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsNotEqualTo(Integer value) {
            addCriterion("position_ids <>", value, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsGreaterThan(Integer value) {
            addCriterion("position_ids >", value, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsGreaterThanOrEqualTo(Integer value) {
            addCriterion("position_ids >=", value, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsLessThan(Integer value) {
            addCriterion("position_ids <", value, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsLessThanOrEqualTo(Integer value) {
            addCriterion("position_ids <=", value, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsIn(List<Integer> values) {
            addCriterion("position_ids in", values, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsNotIn(List<Integer> values) {
            addCriterion("position_ids not in", values, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsBetween(Integer value1, Integer value2) {
            addCriterion("position_ids between", value1, value2, "positionIds");
            return (Criteria) this;
        }

        public Criteria andPositionIdsNotBetween(Integer value1, Integer value2) {
            addCriterion("position_ids not between", value1, value2, "positionIds");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Integer value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Integer value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Integer value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Integer value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Integer value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Integer> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Integer> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Integer value1, Integer value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
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
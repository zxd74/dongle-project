package com.iwanvi.nvwa.dao.model;

import com.iwanvi.nvwa.dao.model.support.BaseExample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdPositionExample extends BaseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdPositionExample() {
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

        public Criteria andFlowUuidIsNull() {
            addCriterion("flow_uuid is null");
            return (Criteria) this;
        }

        public Criteria andFlowUuidIsNotNull() {
            addCriterion("flow_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andFlowUuidEqualTo(String value) {
            addCriterion("flow_uuid =", value, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidNotEqualTo(String value) {
            addCriterion("flow_uuid <>", value, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidGreaterThan(String value) {
            addCriterion("flow_uuid >", value, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidGreaterThanOrEqualTo(String value) {
            addCriterion("flow_uuid >=", value, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidLessThan(String value) {
            addCriterion("flow_uuid <", value, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidLessThanOrEqualTo(String value) {
            addCriterion("flow_uuid <=", value, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidLike(String value) {
            addCriterion("flow_uuid like", value, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidNotLike(String value) {
            addCriterion("flow_uuid not like", value, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidIn(List<String> values) {
            addCriterion("flow_uuid in", values, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidNotIn(List<String> values) {
            addCriterion("flow_uuid not in", values, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidBetween(String value1, String value2) {
            addCriterion("flow_uuid between", value1, value2, "flowUuid");
            return (Criteria) this;
        }

        public Criteria andFlowUuidNotBetween(String value1, String value2) {
            addCriterion("flow_uuid not between", value1, value2, "flowUuid");
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

        public Criteria andAppIdEqualTo(Integer value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Integer value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Integer value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Integer value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Integer value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Integer> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Integer> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Integer value1, Integer value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Integer value1, Integer value2) {
            addCriterion("app_id not between", value1, value2, "appId");
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

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(String value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(String value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(String value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(String value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLike(String value) {
            addCriterion("template_id like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotLike(String value) {
            addCriterion("template_id not like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<String> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<String> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(String value1, String value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(String value1, String value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdIsNull() {
            addCriterion("out_template_id is null");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdIsNotNull() {
            addCriterion("out_template_id is not null");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdEqualTo(Integer value) {
            addCriterion("out_template_id =", value, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdNotEqualTo(Integer value) {
            addCriterion("out_template_id <>", value, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdGreaterThan(Integer value) {
            addCriterion("out_template_id >", value, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_template_id >=", value, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdLessThan(Integer value) {
            addCriterion("out_template_id <", value, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("out_template_id <=", value, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdIn(List<Integer> values) {
            addCriterion("out_template_id in", values, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdNotIn(List<Integer> values) {
            addCriterion("out_template_id not in", values, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("out_template_id between", value1, value2, "outTemplateId");
            return (Criteria) this;
        }

        public Criteria andOutTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("out_template_id not between", value1, value2, "outTemplateId");
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

        public Criteria andSellTypeIsNull() {
            addCriterion("sell_type is null");
            return (Criteria) this;
        }

        public Criteria andSellTypeIsNotNull() {
            addCriterion("sell_type is not null");
            return (Criteria) this;
        }

        public Criteria andSellTypeEqualTo(Integer value) {
            addCriterion("sell_type =", value, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeNotEqualTo(Integer value) {
            addCriterion("sell_type <>", value, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeGreaterThan(Integer value) {
            addCriterion("sell_type >", value, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("sell_type >=", value, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeLessThan(Integer value) {
            addCriterion("sell_type <", value, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeLessThanOrEqualTo(Integer value) {
            addCriterion("sell_type <=", value, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeIn(List<Integer> values) {
            addCriterion("sell_type in", values, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeNotIn(List<Integer> values) {
            addCriterion("sell_type not in", values, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeBetween(Integer value1, Integer value2) {
            addCriterion("sell_type between", value1, value2, "sellType");
            return (Criteria) this;
        }

        public Criteria andSellTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("sell_type not between", value1, value2, "sellType");
            return (Criteria) this;
        }

        public Criteria andTerminalIsNull() {
            addCriterion("terminal is null");
            return (Criteria) this;
        }

        public Criteria andTerminalIsNotNull() {
            addCriterion("terminal is not null");
            return (Criteria) this;
        }

        public Criteria andTerminalEqualTo(Integer value) {
            addCriterion("terminal =", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalNotEqualTo(Integer value) {
            addCriterion("terminal <>", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalGreaterThan(Integer value) {
            addCriterion("terminal >", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalGreaterThanOrEqualTo(Integer value) {
            addCriterion("terminal >=", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalLessThan(Integer value) {
            addCriterion("terminal <", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalLessThanOrEqualTo(Integer value) {
            addCriterion("terminal <=", value, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalIn(List<Integer> values) {
            addCriterion("terminal in", values, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalNotIn(List<Integer> values) {
            addCriterion("terminal not in", values, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalBetween(Integer value1, Integer value2) {
            addCriterion("terminal between", value1, value2, "terminal");
            return (Criteria) this;
        }

        public Criteria andTerminalNotBetween(Integer value1, Integer value2) {
            addCriterion("terminal not between", value1, value2, "terminal");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdIsNull() {
            addCriterion("flow_position_id is null");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdIsNotNull() {
            addCriterion("flow_position_id is not null");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdEqualTo(String value) {
            addCriterion("flow_position_id =", value, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdNotEqualTo(String value) {
            addCriterion("flow_position_id <>", value, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdGreaterThan(String value) {
            addCriterion("flow_position_id >", value, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdGreaterThanOrEqualTo(String value) {
            addCriterion("flow_position_id >=", value, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdLessThan(String value) {
            addCriterion("flow_position_id <", value, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdLessThanOrEqualTo(String value) {
            addCriterion("flow_position_id <=", value, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdLike(String value) {
            addCriterion("flow_position_id like", value, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdNotLike(String value) {
            addCriterion("flow_position_id not like", value, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdIn(List<String> values) {
            addCriterion("flow_position_id in", values, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdNotIn(List<String> values) {
            addCriterion("flow_position_id not in", values, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdBetween(String value1, String value2) {
            addCriterion("flow_position_id between", value1, value2, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andFlowPositionIdNotBetween(String value1, String value2) {
            addCriterion("flow_position_id not between", value1, value2, "flowPositionId");
            return (Criteria) this;
        }

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(String value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(String value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(String value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(String value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(String value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(String value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLike(String value) {
            addCriterion("uuid like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotLike(String value) {
            addCriterion("uuid not like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<String> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<String> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(String value1, String value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(String value1, String value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
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

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(Integer value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(Integer value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(Integer value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(Integer value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(Integer value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(Integer value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<Integer> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<Integer> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(Integer value1, Integer value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(Integer value1, Integer value2) {
            addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andSubChannelIsNull() {
            addCriterion("sub_Channel is null");
            return (Criteria) this;
        }

        public Criteria andSubChannelIsNotNull() {
            addCriterion("sub_Channel is not null");
            return (Criteria) this;
        }

        public Criteria andSubChannelEqualTo(Integer value) {
            addCriterion("sub_Channel =", value, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelNotEqualTo(Integer value) {
            addCriterion("sub_Channel <>", value, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelGreaterThan(Integer value) {
            addCriterion("sub_Channel >", value, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelGreaterThanOrEqualTo(Integer value) {
            addCriterion("sub_Channel >=", value, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelLessThan(Integer value) {
            addCriterion("sub_Channel <", value, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelLessThanOrEqualTo(Integer value) {
            addCriterion("sub_Channel <=", value, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelIn(List<Integer> values) {
            addCriterion("sub_Channel in", values, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelNotIn(List<Integer> values) {
            addCriterion("sub_Channel not in", values, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelBetween(Integer value1, Integer value2) {
            addCriterion("sub_Channel between", value1, value2, "subChannel");
            return (Criteria) this;
        }

        public Criteria andSubChannelNotBetween(Integer value1, Integer value2) {
            addCriterion("sub_Channel not between", value1, value2, "subChannel");
            return (Criteria) this;
        }

        public Criteria andCarLevelIsNull() {
            addCriterion("car_level is null");
            return (Criteria) this;
        }

        public Criteria andCarLevelIsNotNull() {
            addCriterion("car_level is not null");
            return (Criteria) this;
        }

        public Criteria andCarLevelEqualTo(Integer value) {
            addCriterion("car_level =", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotEqualTo(Integer value) {
            addCriterion("car_level <>", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelGreaterThan(Integer value) {
            addCriterion("car_level >", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("car_level >=", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelLessThan(Integer value) {
            addCriterion("car_level <", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelLessThanOrEqualTo(Integer value) {
            addCriterion("car_level <=", value, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelIn(List<Integer> values) {
            addCriterion("car_level in", values, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotIn(List<Integer> values) {
            addCriterion("car_level not in", values, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelBetween(Integer value1, Integer value2) {
            addCriterion("car_level between", value1, value2, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("car_level not between", value1, value2, "carLevel");
            return (Criteria) this;
        }

        public Criteria andCarSeriesIsNull() {
            addCriterion("car_series is null");
            return (Criteria) this;
        }

        public Criteria andCarSeriesIsNotNull() {
            addCriterion("car_series is not null");
            return (Criteria) this;
        }

        public Criteria andCarSeriesEqualTo(Integer value) {
            addCriterion("car_series =", value, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesNotEqualTo(Integer value) {
            addCriterion("car_series <>", value, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesGreaterThan(Integer value) {
            addCriterion("car_series >", value, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesGreaterThanOrEqualTo(Integer value) {
            addCriterion("car_series >=", value, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesLessThan(Integer value) {
            addCriterion("car_series <", value, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesLessThanOrEqualTo(Integer value) {
            addCriterion("car_series <=", value, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesIn(List<Integer> values) {
            addCriterion("car_series in", values, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesNotIn(List<Integer> values) {
            addCriterion("car_series not in", values, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesBetween(Integer value1, Integer value2) {
            addCriterion("car_series between", value1, value2, "carSeries");
            return (Criteria) this;
        }

        public Criteria andCarSeriesNotBetween(Integer value1, Integer value2) {
            addCriterion("car_series not between", value1, value2, "carSeries");
            return (Criteria) this;
        }

        public Criteria andAreaLevelIsNull() {
            addCriterion("area_level is null");
            return (Criteria) this;
        }

        public Criteria andAreaLevelIsNotNull() {
            addCriterion("area_level is not null");
            return (Criteria) this;
        }

        public Criteria andAreaLevelEqualTo(Integer value) {
            addCriterion("area_level =", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelNotEqualTo(Integer value) {
            addCriterion("area_level <>", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelGreaterThan(Integer value) {
            addCriterion("area_level >", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("area_level >=", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelLessThan(Integer value) {
            addCriterion("area_level <", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelLessThanOrEqualTo(Integer value) {
            addCriterion("area_level <=", value, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelIn(List<Integer> values) {
            addCriterion("area_level in", values, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelNotIn(List<Integer> values) {
            addCriterion("area_level not in", values, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelBetween(Integer value1, Integer value2) {
            addCriterion("area_level between", value1, value2, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("area_level not between", value1, value2, "areaLevel");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(Integer value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Integer value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Integer value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Integer value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Integer value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Integer value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Integer> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Integer> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Integer value1, Integer value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Integer value1, Integer value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andPriceRangeIsNull() {
            addCriterion("price_range is null");
            return (Criteria) this;
        }

        public Criteria andPriceRangeIsNotNull() {
            addCriterion("price_range is not null");
            return (Criteria) this;
        }

        public Criteria andPriceRangeEqualTo(Integer value) {
            addCriterion("price_range =", value, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeNotEqualTo(Integer value) {
            addCriterion("price_range <>", value, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeGreaterThan(Integer value) {
            addCriterion("price_range >", value, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeGreaterThanOrEqualTo(Integer value) {
            addCriterion("price_range >=", value, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeLessThan(Integer value) {
            addCriterion("price_range <", value, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeLessThanOrEqualTo(Integer value) {
            addCriterion("price_range <=", value, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeIn(List<Integer> values) {
            addCriterion("price_range in", values, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeNotIn(List<Integer> values) {
            addCriterion("price_range not in", values, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeBetween(Integer value1, Integer value2) {
            addCriterion("price_range between", value1, value2, "priceRange");
            return (Criteria) this;
        }

        public Criteria andPriceRangeNotBetween(Integer value1, Integer value2) {
            addCriterion("price_range not between", value1, value2, "priceRange");
            return (Criteria) this;
        }

        public Criteria andForecastExposureIsNull() {
            addCriterion("forecast_exposure is null");
            return (Criteria) this;
        }

        public Criteria andForecastExposureIsNotNull() {
            addCriterion("forecast_exposure is not null");
            return (Criteria) this;
        }

        public Criteria andForecastExposureEqualTo(Long value) {
            addCriterion("forecast_exposure =", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureNotEqualTo(Long value) {
            addCriterion("forecast_exposure <>", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureGreaterThan(Long value) {
            addCriterion("forecast_exposure >", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureGreaterThanOrEqualTo(Long value) {
            addCriterion("forecast_exposure >=", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureLessThan(Long value) {
            addCriterion("forecast_exposure <", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureLessThanOrEqualTo(Long value) {
            addCriterion("forecast_exposure <=", value, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureIn(List<Long> values) {
            addCriterion("forecast_exposure in", values, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureNotIn(List<Long> values) {
            addCriterion("forecast_exposure not in", values, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureBetween(Long value1, Long value2) {
            addCriterion("forecast_exposure between", value1, value2, "forecastExposure");
            return (Criteria) this;
        }

        public Criteria andForecastExposureNotBetween(Long value1, Long value2) {
            addCriterion("forecast_exposure not between", value1, value2, "forecastExposure");
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

        public Criteria andForecastClickEqualTo(Long value) {
            addCriterion("forecast_click =", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickNotEqualTo(Long value) {
            addCriterion("forecast_click <>", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickGreaterThan(Long value) {
            addCriterion("forecast_click >", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickGreaterThanOrEqualTo(Long value) {
            addCriterion("forecast_click >=", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickLessThan(Long value) {
            addCriterion("forecast_click <", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickLessThanOrEqualTo(Long value) {
            addCriterion("forecast_click <=", value, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickIn(List<Long> values) {
            addCriterion("forecast_click in", values, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickNotIn(List<Long> values) {
            addCriterion("forecast_click not in", values, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickBetween(Long value1, Long value2) {
            addCriterion("forecast_click between", value1, value2, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andForecastClickNotBetween(Long value1, Long value2) {
            addCriterion("forecast_click not between", value1, value2, "forecastClick");
            return (Criteria) this;
        }

        public Criteria andMinSellDayIsNull() {
            addCriterion("min_sell_day is null");
            return (Criteria) this;
        }

        public Criteria andMinSellDayIsNotNull() {
            addCriterion("min_sell_day is not null");
            return (Criteria) this;
        }

        public Criteria andMinSellDayEqualTo(Integer value) {
            addCriterion("min_sell_day =", value, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayNotEqualTo(Integer value) {
            addCriterion("min_sell_day <>", value, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayGreaterThan(Integer value) {
            addCriterion("min_sell_day >", value, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayGreaterThanOrEqualTo(Integer value) {
            addCriterion("min_sell_day >=", value, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayLessThan(Integer value) {
            addCriterion("min_sell_day <", value, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayLessThanOrEqualTo(Integer value) {
            addCriterion("min_sell_day <=", value, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayIn(List<Integer> values) {
            addCriterion("min_sell_day in", values, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayNotIn(List<Integer> values) {
            addCriterion("min_sell_day not in", values, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayBetween(Integer value1, Integer value2) {
            addCriterion("min_sell_day between", value1, value2, "minSellDay");
            return (Criteria) this;
        }

        public Criteria andMinSellDayNotBetween(Integer value1, Integer value2) {
            addCriterion("min_sell_day not between", value1, value2, "minSellDay");
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

        public Criteria andAikaTemplateIdIsNull() {
            addCriterion("aika_template_id is null");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdIsNotNull() {
            addCriterion("aika_template_id is not null");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdEqualTo(Integer value) {
            addCriterion("aika_template_id =", value, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdNotEqualTo(Integer value) {
            addCriterion("aika_template_id <>", value, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdGreaterThan(Integer value) {
            addCriterion("aika_template_id >", value, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("aika_template_id >=", value, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdLessThan(Integer value) {
            addCriterion("aika_template_id <", value, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("aika_template_id <=", value, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdIn(List<Integer> values) {
            addCriterion("aika_template_id in", values, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdNotIn(List<Integer> values) {
            addCriterion("aika_template_id not in", values, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("aika_template_id between", value1, value2, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andAikaTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("aika_template_id not between", value1, value2, "aikaTemplateId");
            return (Criteria) this;
        }

        public Criteria andOsIsNull() {
            addCriterion("os is null");
            return (Criteria) this;
        }

        public Criteria andOsIsNotNull() {
            addCriterion("os is not null");
            return (Criteria) this;
        }

        public Criteria andOsEqualTo(Integer value) {
            addCriterion("os =", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsNotEqualTo(Integer value) {
            addCriterion("os <>", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsGreaterThan(Integer value) {
            addCriterion("os >", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsGreaterThanOrEqualTo(Integer value) {
            addCriterion("os >=", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsLessThan(Integer value) {
            addCriterion("os <", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsLessThanOrEqualTo(Integer value) {
            addCriterion("os <=", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsIn(List<Integer> values) {
            addCriterion("os in", values, "os");
            return (Criteria) this;
        }

        public Criteria andOsNotIn(List<Integer> values) {
            addCriterion("os not in", values, "os");
            return (Criteria) this;
        }

        public Criteria andOsBetween(Integer value1, Integer value2) {
            addCriterion("os between", value1, value2, "os");
            return (Criteria) this;
        }

        public Criteria andOsNotBetween(Integer value1, Integer value2) {
            addCriterion("os not between", value1, value2, "os");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicIsNull() {
            addCriterion("is_support_dynamic is null");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicIsNotNull() {
            addCriterion("is_support_dynamic is not null");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicEqualTo(Integer value) {
            addCriterion("is_support_dynamic =", value, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicNotEqualTo(Integer value) {
            addCriterion("is_support_dynamic <>", value, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicGreaterThan(Integer value) {
            addCriterion("is_support_dynamic >", value, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_support_dynamic >=", value, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicLessThan(Integer value) {
            addCriterion("is_support_dynamic <", value, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicLessThanOrEqualTo(Integer value) {
            addCriterion("is_support_dynamic <=", value, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicIn(List<Integer> values) {
            addCriterion("is_support_dynamic in", values, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicNotIn(List<Integer> values) {
            addCriterion("is_support_dynamic not in", values, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicBetween(Integer value1, Integer value2) {
            addCriterion("is_support_dynamic between", value1, value2, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andIsSupportDynamicNotBetween(Integer value1, Integer value2) {
            addCriterion("is_support_dynamic not between", value1, value2, "isSupportDynamic");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchIsNull() {
            addCriterion("mapping_switch is null");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchIsNotNull() {
            addCriterion("mapping_switch is not null");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchEqualTo(Integer value) {
            addCriterion("mapping_switch =", value, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchNotEqualTo(Integer value) {
            addCriterion("mapping_switch <>", value, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchGreaterThan(Integer value) {
            addCriterion("mapping_switch >", value, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchGreaterThanOrEqualTo(Integer value) {
            addCriterion("mapping_switch >=", value, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchLessThan(Integer value) {
            addCriterion("mapping_switch <", value, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchLessThanOrEqualTo(Integer value) {
            addCriterion("mapping_switch <=", value, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchIn(List<Integer> values) {
            addCriterion("mapping_switch in", values, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchNotIn(List<Integer> values) {
            addCriterion("mapping_switch not in", values, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchBetween(Integer value1, Integer value2) {
            addCriterion("mapping_switch between", value1, value2, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andMappingSwitchNotBetween(Integer value1, Integer value2) {
            addCriterion("mapping_switch not between", value1, value2, "mappingSwitch");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("comment is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("comment is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(String value) {
            addCriterion("comment =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(String value) {
            addCriterion("comment <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(String value) {
            addCriterion("comment >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(String value) {
            addCriterion("comment >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(String value) {
            addCriterion("comment <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(String value) {
            addCriterion("comment <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(String value) {
            addCriterion("comment like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(String value) {
            addCriterion("comment not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(List<String> values) {
            addCriterion("comment in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(List<String> values) {
            addCriterion("comment not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(String value1, String value2) {
            addCriterion("comment between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(String value1, String value2) {
            addCriterion("comment not between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(Integer value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(Integer value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(Integer value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(Integer value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(Integer value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(Integer value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<Integer> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<Integer> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(Integer value1, Integer value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(Integer value1, Integer value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andPicWidthIsNull() {
            addCriterion("pic_width is null");
            return (Criteria) this;
        }

        public Criteria andPicWidthIsNotNull() {
            addCriterion("pic_width is not null");
            return (Criteria) this;
        }

        public Criteria andPicWidthEqualTo(Integer value) {
            addCriterion("pic_width =", value, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthNotEqualTo(Integer value) {
            addCriterion("pic_width <>", value, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthGreaterThan(Integer value) {
            addCriterion("pic_width >", value, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("pic_width >=", value, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthLessThan(Integer value) {
            addCriterion("pic_width <", value, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthLessThanOrEqualTo(Integer value) {
            addCriterion("pic_width <=", value, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthIn(List<Integer> values) {
            addCriterion("pic_width in", values, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthNotIn(List<Integer> values) {
            addCriterion("pic_width not in", values, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthBetween(Integer value1, Integer value2) {
            addCriterion("pic_width between", value1, value2, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("pic_width not between", value1, value2, "picWidth");
            return (Criteria) this;
        }

        public Criteria andPicHeightIsNull() {
            addCriterion("pic_height is null");
            return (Criteria) this;
        }

        public Criteria andPicHeightIsNotNull() {
            addCriterion("pic_height is not null");
            return (Criteria) this;
        }

        public Criteria andPicHeightEqualTo(Integer value) {
            addCriterion("pic_height =", value, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightNotEqualTo(Integer value) {
            addCriterion("pic_height <>", value, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightGreaterThan(Integer value) {
            addCriterion("pic_height >", value, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("pic_height >=", value, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightLessThan(Integer value) {
            addCriterion("pic_height <", value, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightLessThanOrEqualTo(Integer value) {
            addCriterion("pic_height <=", value, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightIn(List<Integer> values) {
            addCriterion("pic_height in", values, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightNotIn(List<Integer> values) {
            addCriterion("pic_height not in", values, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightBetween(Integer value1, Integer value2) {
            addCriterion("pic_height between", value1, value2, "picHeight");
            return (Criteria) this;
        }

        public Criteria andPicHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("pic_height not between", value1, value2, "picHeight");
            return (Criteria) this;
        }

        public Criteria andVideoWidthIsNull() {
            addCriterion("video_width is null");
            return (Criteria) this;
        }

        public Criteria andVideoWidthIsNotNull() {
            addCriterion("video_width is not null");
            return (Criteria) this;
        }

        public Criteria andVideoWidthEqualTo(Integer value) {
            addCriterion("video_width =", value, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthNotEqualTo(Integer value) {
            addCriterion("video_width <>", value, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthGreaterThan(Integer value) {
            addCriterion("video_width >", value, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("video_width >=", value, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthLessThan(Integer value) {
            addCriterion("video_width <", value, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthLessThanOrEqualTo(Integer value) {
            addCriterion("video_width <=", value, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthIn(List<Integer> values) {
            addCriterion("video_width in", values, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthNotIn(List<Integer> values) {
            addCriterion("video_width not in", values, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthBetween(Integer value1, Integer value2) {
            addCriterion("video_width between", value1, value2, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("video_width not between", value1, value2, "videoWidth");
            return (Criteria) this;
        }

        public Criteria andVideoHeightIsNull() {
            addCriterion("video_height is null");
            return (Criteria) this;
        }

        public Criteria andVideoHeightIsNotNull() {
            addCriterion("video_height is not null");
            return (Criteria) this;
        }

        public Criteria andVideoHeightEqualTo(Integer value) {
            addCriterion("video_height =", value, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightNotEqualTo(Integer value) {
            addCriterion("video_height <>", value, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightGreaterThan(Integer value) {
            addCriterion("video_height >", value, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("video_height >=", value, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightLessThan(Integer value) {
            addCriterion("video_height <", value, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightLessThanOrEqualTo(Integer value) {
            addCriterion("video_height <=", value, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightIn(List<Integer> values) {
            addCriterion("video_height in", values, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightNotIn(List<Integer> values) {
            addCriterion("video_height not in", values, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightBetween(Integer value1, Integer value2) {
            addCriterion("video_height between", value1, value2, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andVideoHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("video_height not between", value1, value2, "videoHeight");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(Integer value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(Integer value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(Integer value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(Integer value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(Integer value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(Integer value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<Integer> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<Integer> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(Integer value1, Integer value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(Integer value1, Integer value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andForecastCtrIsNull() {
            addCriterion("forecast_CTR is null");
            return (Criteria) this;
        }

        public Criteria andForecastCtrIsNotNull() {
            addCriterion("forecast_CTR is not null");
            return (Criteria) this;
        }

        public Criteria andForecastCtrEqualTo(Double value) {
            addCriterion("forecast_CTR =", value, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrNotEqualTo(Double value) {
            addCriterion("forecast_CTR <>", value, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrGreaterThan(Double value) {
            addCriterion("forecast_CTR >", value, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrGreaterThanOrEqualTo(Double value) {
            addCriterion("forecast_CTR >=", value, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrLessThan(Double value) {
            addCriterion("forecast_CTR <", value, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrLessThanOrEqualTo(Double value) {
            addCriterion("forecast_CTR <=", value, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrIn(List<Double> values) {
            addCriterion("forecast_CTR in", values, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrNotIn(List<Double> values) {
            addCriterion("forecast_CTR not in", values, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrBetween(Double value1, Double value2) {
            addCriterion("forecast_CTR between", value1, value2, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andForecastCtrNotBetween(Double value1, Double value2) {
            addCriterion("forecast_CTR not between", value1, value2, "forecastCtr");
            return (Criteria) this;
        }

        public Criteria andScreenshotIsNull() {
            addCriterion("screenshot is null");
            return (Criteria) this;
        }

        public Criteria andScreenshotIsNotNull() {
            addCriterion("screenshot is not null");
            return (Criteria) this;
        }

        public Criteria andScreenshotEqualTo(String value) {
            addCriterion("screenshot =", value, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotNotEqualTo(String value) {
            addCriterion("screenshot <>", value, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotGreaterThan(String value) {
            addCriterion("screenshot >", value, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotGreaterThanOrEqualTo(String value) {
            addCriterion("screenshot >=", value, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotLessThan(String value) {
            addCriterion("screenshot <", value, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotLessThanOrEqualTo(String value) {
            addCriterion("screenshot <=", value, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotLike(String value) {
            addCriterion("screenshot like", value, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotNotLike(String value) {
            addCriterion("screenshot not like", value, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotIn(List<String> values) {
            addCriterion("screenshot in", values, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotNotIn(List<String> values) {
            addCriterion("screenshot not in", values, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotBetween(String value1, String value2) {
            addCriterion("screenshot between", value1, value2, "screenshot");
            return (Criteria) this;
        }

        public Criteria andScreenshotNotBetween(String value1, String value2) {
            addCriterion("screenshot not between", value1, value2, "screenshot");
            return (Criteria) this;
        }

        public Criteria andLocationXIsNull() {
            addCriterion("location_x is null");
            return (Criteria) this;
        }

        public Criteria andLocationXIsNotNull() {
            addCriterion("location_x is not null");
            return (Criteria) this;
        }

        public Criteria andLocationXEqualTo(Integer value) {
            addCriterion("location_x =", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXNotEqualTo(Integer value) {
            addCriterion("location_x <>", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXGreaterThan(Integer value) {
            addCriterion("location_x >", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXGreaterThanOrEqualTo(Integer value) {
            addCriterion("location_x >=", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXLessThan(Integer value) {
            addCriterion("location_x <", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXLessThanOrEqualTo(Integer value) {
            addCriterion("location_x <=", value, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXIn(List<Integer> values) {
            addCriterion("location_x in", values, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXNotIn(List<Integer> values) {
            addCriterion("location_x not in", values, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXBetween(Integer value1, Integer value2) {
            addCriterion("location_x between", value1, value2, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationXNotBetween(Integer value1, Integer value2) {
            addCriterion("location_x not between", value1, value2, "locationX");
            return (Criteria) this;
        }

        public Criteria andLocationYIsNull() {
            addCriterion("location_y is null");
            return (Criteria) this;
        }

        public Criteria andLocationYIsNotNull() {
            addCriterion("location_y is not null");
            return (Criteria) this;
        }

        public Criteria andLocationYEqualTo(Integer value) {
            addCriterion("location_y =", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYNotEqualTo(Integer value) {
            addCriterion("location_y <>", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYGreaterThan(Integer value) {
            addCriterion("location_y >", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYGreaterThanOrEqualTo(Integer value) {
            addCriterion("location_y >=", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYLessThan(Integer value) {
            addCriterion("location_y <", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYLessThanOrEqualTo(Integer value) {
            addCriterion("location_y <=", value, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYIn(List<Integer> values) {
            addCriterion("location_y in", values, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYNotIn(List<Integer> values) {
            addCriterion("location_y not in", values, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYBetween(Integer value1, Integer value2) {
            addCriterion("location_y between", value1, value2, "locationY");
            return (Criteria) this;
        }

        public Criteria andLocationYNotBetween(Integer value1, Integer value2) {
            addCriterion("location_y not between", value1, value2, "locationY");
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
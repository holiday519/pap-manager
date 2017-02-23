package com.pxene.pap.domain.models.view;

import java.util.ArrayList;
import java.util.List;

public class CampaignKpiModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CampaignKpiModelExample() {
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

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(String value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(String value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(String value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(String value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(String value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLike(String value) {
            addCriterion("project_id like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotLike(String value) {
            addCriterion("project_id not like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<String> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<String> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(String value1, String value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(String value1, String value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdIsNull() {
            addCriterion("advertiser_id is null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdIsNotNull() {
            addCriterion("advertiser_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdEqualTo(String value) {
            addCriterion("advertiser_id =", value, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdNotEqualTo(String value) {
            addCriterion("advertiser_id <>", value, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdGreaterThan(String value) {
            addCriterion("advertiser_id >", value, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdGreaterThanOrEqualTo(String value) {
            addCriterion("advertiser_id >=", value, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdLessThan(String value) {
            addCriterion("advertiser_id <", value, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdLessThanOrEqualTo(String value) {
            addCriterion("advertiser_id <=", value, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdLike(String value) {
            addCriterion("advertiser_id like", value, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdNotLike(String value) {
            addCriterion("advertiser_id not like", value, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdIn(List<String> values) {
            addCriterion("advertiser_id in", values, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdNotIn(List<String> values) {
            addCriterion("advertiser_id not in", values, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdBetween(String value1, String value2) {
            addCriterion("advertiser_id between", value1, value2, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andAdvertiserIdNotBetween(String value1, String value2) {
            addCriterion("advertiser_id not between", value1, value2, "advertiserId");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNull() {
            addCriterion("project_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNotNull() {
            addCriterion("project_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNameEqualTo(String value) {
            addCriterion("project_name =", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotEqualTo(String value) {
            addCriterion("project_name <>", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThan(String value) {
            addCriterion("project_name >", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_name >=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThan(String value) {
            addCriterion("project_name <", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThanOrEqualTo(String value) {
            addCriterion("project_name <=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLike(String value) {
            addCriterion("project_name like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotLike(String value) {
            addCriterion("project_name not like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameIn(List<String> values) {
            addCriterion("project_name in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotIn(List<String> values) {
            addCriterion("project_name not in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameBetween(String value1, String value2) {
            addCriterion("project_name between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotBetween(String value1, String value2) {
            addCriterion("project_name not between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetIsNull() {
            addCriterion("total_budget is null");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetIsNotNull() {
            addCriterion("total_budget is not null");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetEqualTo(Integer value) {
            addCriterion("total_budget =", value, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetNotEqualTo(Integer value) {
            addCriterion("total_budget <>", value, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetGreaterThan(Integer value) {
            addCriterion("total_budget >", value, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_budget >=", value, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetLessThan(Integer value) {
            addCriterion("total_budget <", value, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetLessThanOrEqualTo(Integer value) {
            addCriterion("total_budget <=", value, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetIn(List<Integer> values) {
            addCriterion("total_budget in", values, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetNotIn(List<Integer> values) {
            addCriterion("total_budget not in", values, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetBetween(Integer value1, Integer value2) {
            addCriterion("total_budget between", value1, value2, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andTotalBudgetNotBetween(Integer value1, Integer value2) {
            addCriterion("total_budget not between", value1, value2, "totalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIsNull() {
            addCriterion("project_status is null");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIsNotNull() {
            addCriterion("project_status is not null");
            return (Criteria) this;
        }

        public Criteria andProjectStatusEqualTo(String value) {
            addCriterion("project_status =", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotEqualTo(String value) {
            addCriterion("project_status <>", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusGreaterThan(String value) {
            addCriterion("project_status >", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusGreaterThanOrEqualTo(String value) {
            addCriterion("project_status >=", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLessThan(String value) {
            addCriterion("project_status <", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLessThanOrEqualTo(String value) {
            addCriterion("project_status <=", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLike(String value) {
            addCriterion("project_status like", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotLike(String value) {
            addCriterion("project_status not like", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIn(List<String> values) {
            addCriterion("project_status in", values, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotIn(List<String> values) {
            addCriterion("project_status not in", values, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusBetween(String value1, String value2) {
            addCriterion("project_status between", value1, value2, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotBetween(String value1, String value2) {
            addCriterion("project_status not between", value1, value2, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andKpiValueIsNull() {
            addCriterion("kpi_value is null");
            return (Criteria) this;
        }

        public Criteria andKpiValueIsNotNull() {
            addCriterion("kpi_value is not null");
            return (Criteria) this;
        }

        public Criteria andKpiValueEqualTo(Integer value) {
            addCriterion("kpi_value =", value, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueNotEqualTo(Integer value) {
            addCriterion("kpi_value <>", value, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueGreaterThan(Integer value) {
            addCriterion("kpi_value >", value, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("kpi_value >=", value, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueLessThan(Integer value) {
            addCriterion("kpi_value <", value, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueLessThanOrEqualTo(Integer value) {
            addCriterion("kpi_value <=", value, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueIn(List<Integer> values) {
            addCriterion("kpi_value in", values, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueNotIn(List<Integer> values) {
            addCriterion("kpi_value not in", values, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueBetween(Integer value1, Integer value2) {
            addCriterion("kpi_value between", value1, value2, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiValueNotBetween(Integer value1, Integer value2) {
            addCriterion("kpi_value not between", value1, value2, "kpiValue");
            return (Criteria) this;
        }

        public Criteria andKpiIdIsNull() {
            addCriterion("kpi_id is null");
            return (Criteria) this;
        }

        public Criteria andKpiIdIsNotNull() {
            addCriterion("kpi_id is not null");
            return (Criteria) this;
        }

        public Criteria andKpiIdEqualTo(String value) {
            addCriterion("kpi_id =", value, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdNotEqualTo(String value) {
            addCriterion("kpi_id <>", value, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdGreaterThan(String value) {
            addCriterion("kpi_id >", value, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdGreaterThanOrEqualTo(String value) {
            addCriterion("kpi_id >=", value, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdLessThan(String value) {
            addCriterion("kpi_id <", value, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdLessThanOrEqualTo(String value) {
            addCriterion("kpi_id <=", value, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdLike(String value) {
            addCriterion("kpi_id like", value, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdNotLike(String value) {
            addCriterion("kpi_id not like", value, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdIn(List<String> values) {
            addCriterion("kpi_id in", values, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdNotIn(List<String> values) {
            addCriterion("kpi_id not in", values, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdBetween(String value1, String value2) {
            addCriterion("kpi_id between", value1, value2, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiIdNotBetween(String value1, String value2) {
            addCriterion("kpi_id not between", value1, value2, "kpiId");
            return (Criteria) this;
        }

        public Criteria andKpiNameIsNull() {
            addCriterion("kpi_name is null");
            return (Criteria) this;
        }

        public Criteria andKpiNameIsNotNull() {
            addCriterion("kpi_name is not null");
            return (Criteria) this;
        }

        public Criteria andKpiNameEqualTo(String value) {
            addCriterion("kpi_name =", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameNotEqualTo(String value) {
            addCriterion("kpi_name <>", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameGreaterThan(String value) {
            addCriterion("kpi_name >", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameGreaterThanOrEqualTo(String value) {
            addCriterion("kpi_name >=", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameLessThan(String value) {
            addCriterion("kpi_name <", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameLessThanOrEqualTo(String value) {
            addCriterion("kpi_name <=", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameLike(String value) {
            addCriterion("kpi_name like", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameNotLike(String value) {
            addCriterion("kpi_name not like", value, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameIn(List<String> values) {
            addCriterion("kpi_name in", values, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameNotIn(List<String> values) {
            addCriterion("kpi_name not in", values, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameBetween(String value1, String value2) {
            addCriterion("kpi_name between", value1, value2, "kpiName");
            return (Criteria) this;
        }

        public Criteria andKpiNameNotBetween(String value1, String value2) {
            addCriterion("kpi_name not between", value1, value2, "kpiName");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
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
package com.pxene.pap.domain.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeRuleModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TimeRuleModelExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
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

        public Criteria andHistoryDataIsNull() {
            addCriterion("history_data is null");
            return (Criteria) this;
        }

        public Criteria andHistoryDataIsNotNull() {
            addCriterion("history_data is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryDataEqualTo(String value) {
            addCriterion("history_data =", value, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataNotEqualTo(String value) {
            addCriterion("history_data <>", value, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataGreaterThan(String value) {
            addCriterion("history_data >", value, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataGreaterThanOrEqualTo(String value) {
            addCriterion("history_data >=", value, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataLessThan(String value) {
            addCriterion("history_data <", value, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataLessThanOrEqualTo(String value) {
            addCriterion("history_data <=", value, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataLike(String value) {
            addCriterion("history_data like", value, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataNotLike(String value) {
            addCriterion("history_data not like", value, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataIn(List<String> values) {
            addCriterion("history_data in", values, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataNotIn(List<String> values) {
            addCriterion("history_data not in", values, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataBetween(String value1, String value2) {
            addCriterion("history_data between", value1, value2, "historyData");
            return (Criteria) this;
        }

        public Criteria andHistoryDataNotBetween(String value1, String value2) {
            addCriterion("history_data not between", value1, value2, "historyData");
            return (Criteria) this;
        }

        public Criteria andExecCycleIsNull() {
            addCriterion("exec_cycle is null");
            return (Criteria) this;
        }

        public Criteria andExecCycleIsNotNull() {
            addCriterion("exec_cycle is not null");
            return (Criteria) this;
        }

        public Criteria andExecCycleEqualTo(String value) {
            addCriterion("exec_cycle =", value, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleNotEqualTo(String value) {
            addCriterion("exec_cycle <>", value, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleGreaterThan(String value) {
            addCriterion("exec_cycle >", value, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleGreaterThanOrEqualTo(String value) {
            addCriterion("exec_cycle >=", value, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleLessThan(String value) {
            addCriterion("exec_cycle <", value, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleLessThanOrEqualTo(String value) {
            addCriterion("exec_cycle <=", value, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleLike(String value) {
            addCriterion("exec_cycle like", value, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleNotLike(String value) {
            addCriterion("exec_cycle not like", value, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleIn(List<String> values) {
            addCriterion("exec_cycle in", values, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleNotIn(List<String> values) {
            addCriterion("exec_cycle not in", values, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleBetween(String value1, String value2) {
            addCriterion("exec_cycle between", value1, value2, "execCycle");
            return (Criteria) this;
        }

        public Criteria andExecCycleNotBetween(String value1, String value2) {
            addCriterion("exec_cycle not between", value1, value2, "execCycle");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNull() {
            addCriterion("data_type is null");
            return (Criteria) this;
        }

        public Criteria andDataTypeIsNotNull() {
            addCriterion("data_type is not null");
            return (Criteria) this;
        }

        public Criteria andDataTypeEqualTo(String value) {
            addCriterion("data_type =", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotEqualTo(String value) {
            addCriterion("data_type <>", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThan(String value) {
            addCriterion("data_type >", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeGreaterThanOrEqualTo(String value) {
            addCriterion("data_type >=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThan(String value) {
            addCriterion("data_type <", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLessThanOrEqualTo(String value) {
            addCriterion("data_type <=", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeLike(String value) {
            addCriterion("data_type like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotLike(String value) {
            addCriterion("data_type not like", value, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeIn(List<String> values) {
            addCriterion("data_type in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotIn(List<String> values) {
            addCriterion("data_type not in", values, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeBetween(String value1, String value2) {
            addCriterion("data_type between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataTypeNotBetween(String value1, String value2) {
            addCriterion("data_type not between", value1, value2, "dataType");
            return (Criteria) this;
        }

        public Criteria andDataIsNull() {
            addCriterion("data is null");
            return (Criteria) this;
        }

        public Criteria andDataIsNotNull() {
            addCriterion("data is not null");
            return (Criteria) this;
        }

        public Criteria andDataEqualTo(Double value) {
            addCriterion("data =", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotEqualTo(Double value) {
            addCriterion("data <>", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataGreaterThan(Double value) {
            addCriterion("data >", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataGreaterThanOrEqualTo(Double value) {
            addCriterion("data >=", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLessThan(Double value) {
            addCriterion("data <", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataLessThanOrEqualTo(Double value) {
            addCriterion("data <=", value, "data");
            return (Criteria) this;
        }

        public Criteria andDataIn(List<Double> values) {
            addCriterion("data in", values, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotIn(List<Double> values) {
            addCriterion("data not in", values, "data");
            return (Criteria) this;
        }

        public Criteria andDataBetween(Double value1, Double value2) {
            addCriterion("data between", value1, value2, "data");
            return (Criteria) this;
        }

        public Criteria andDataNotBetween(Double value1, Double value2) {
            addCriterion("data not between", value1, value2, "data");
            return (Criteria) this;
        }

        public Criteria andFareIsNull() {
            addCriterion("fare is null");
            return (Criteria) this;
        }

        public Criteria andFareIsNotNull() {
            addCriterion("fare is not null");
            return (Criteria) this;
        }

        public Criteria andFareEqualTo(Float value) {
            addCriterion("fare =", value, "fare");
            return (Criteria) this;
        }

        public Criteria andFareNotEqualTo(Float value) {
            addCriterion("fare <>", value, "fare");
            return (Criteria) this;
        }

        public Criteria andFareGreaterThan(Float value) {
            addCriterion("fare >", value, "fare");
            return (Criteria) this;
        }

        public Criteria andFareGreaterThanOrEqualTo(Float value) {
            addCriterion("fare >=", value, "fare");
            return (Criteria) this;
        }

        public Criteria andFareLessThan(Float value) {
            addCriterion("fare <", value, "fare");
            return (Criteria) this;
        }

        public Criteria andFareLessThanOrEqualTo(Float value) {
            addCriterion("fare <=", value, "fare");
            return (Criteria) this;
        }

        public Criteria andFareIn(List<Float> values) {
            addCriterion("fare in", values, "fare");
            return (Criteria) this;
        }

        public Criteria andFareNotIn(List<Float> values) {
            addCriterion("fare not in", values, "fare");
            return (Criteria) this;
        }

        public Criteria andFareBetween(Float value1, Float value2) {
            addCriterion("fare between", value1, value2, "fare");
            return (Criteria) this;
        }

        public Criteria andFareNotBetween(Float value1, Float value2) {
            addCriterion("fare not between", value1, value2, "fare");
            return (Criteria) this;
        }

        public Criteria andSaleIsNull() {
            addCriterion("sale is null");
            return (Criteria) this;
        }

        public Criteria andSaleIsNotNull() {
            addCriterion("sale is not null");
            return (Criteria) this;
        }

        public Criteria andSaleEqualTo(Float value) {
            addCriterion("sale =", value, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleNotEqualTo(Float value) {
            addCriterion("sale <>", value, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleGreaterThan(Float value) {
            addCriterion("sale >", value, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleGreaterThanOrEqualTo(Float value) {
            addCriterion("sale >=", value, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleLessThan(Float value) {
            addCriterion("sale <", value, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleLessThanOrEqualTo(Float value) {
            addCriterion("sale <=", value, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleIn(List<Float> values) {
            addCriterion("sale in", values, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleNotIn(List<Float> values) {
            addCriterion("sale not in", values, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleBetween(Float value1, Float value2) {
            addCriterion("sale between", value1, value2, "sale");
            return (Criteria) this;
        }

        public Criteria andSaleNotBetween(Float value1, Float value2) {
            addCriterion("sale not between", value1, value2, "sale");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(Date value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(Date value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(Date value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(Date value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(Date value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<Date> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<Date> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(Date value1, Date value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(Date value1, Date value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
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
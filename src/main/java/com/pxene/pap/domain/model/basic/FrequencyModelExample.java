package com.pxene.pap.domain.model.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrequencyModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FrequencyModelExample() {
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

        public Criteria andControlobjIsNull() {
            addCriterion("controlobj is null");
            return (Criteria) this;
        }

        public Criteria andControlobjIsNotNull() {
            addCriterion("controlobj is not null");
            return (Criteria) this;
        }

        public Criteria andControlobjEqualTo(String value) {
            addCriterion("controlobj =", value, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjNotEqualTo(String value) {
            addCriterion("controlobj <>", value, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjGreaterThan(String value) {
            addCriterion("controlobj >", value, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjGreaterThanOrEqualTo(String value) {
            addCriterion("controlobj >=", value, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjLessThan(String value) {
            addCriterion("controlobj <", value, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjLessThanOrEqualTo(String value) {
            addCriterion("controlobj <=", value, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjLike(String value) {
            addCriterion("controlobj like", value, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjNotLike(String value) {
            addCriterion("controlobj not like", value, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjIn(List<String> values) {
            addCriterion("controlobj in", values, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjNotIn(List<String> values) {
            addCriterion("controlobj not in", values, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjBetween(String value1, String value2) {
            addCriterion("controlobj between", value1, value2, "controlobj");
            return (Criteria) this;
        }

        public Criteria andControlobjNotBetween(String value1, String value2) {
            addCriterion("controlobj not between", value1, value2, "controlobj");
            return (Criteria) this;
        }

        public Criteria andTimetypeIsNull() {
            addCriterion("timetype is null");
            return (Criteria) this;
        }

        public Criteria andTimetypeIsNotNull() {
            addCriterion("timetype is not null");
            return (Criteria) this;
        }

        public Criteria andTimetypeEqualTo(String value) {
            addCriterion("timetype =", value, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeNotEqualTo(String value) {
            addCriterion("timetype <>", value, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeGreaterThan(String value) {
            addCriterion("timetype >", value, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeGreaterThanOrEqualTo(String value) {
            addCriterion("timetype >=", value, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeLessThan(String value) {
            addCriterion("timetype <", value, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeLessThanOrEqualTo(String value) {
            addCriterion("timetype <=", value, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeLike(String value) {
            addCriterion("timetype like", value, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeNotLike(String value) {
            addCriterion("timetype not like", value, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeIn(List<String> values) {
            addCriterion("timetype in", values, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeNotIn(List<String> values) {
            addCriterion("timetype not in", values, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeBetween(String value1, String value2) {
            addCriterion("timetype between", value1, value2, "timetype");
            return (Criteria) this;
        }

        public Criteria andTimetypeNotBetween(String value1, String value2) {
            addCriterion("timetype not between", value1, value2, "timetype");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNull() {
            addCriterion("frequency is null");
            return (Criteria) this;
        }

        public Criteria andFrequencyIsNotNull() {
            addCriterion("frequency is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencyEqualTo(Integer value) {
            addCriterion("frequency =", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotEqualTo(Integer value) {
            addCriterion("frequency <>", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThan(Integer value) {
            addCriterion("frequency >", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyGreaterThanOrEqualTo(Integer value) {
            addCriterion("frequency >=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThan(Integer value) {
            addCriterion("frequency <", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyLessThanOrEqualTo(Integer value) {
            addCriterion("frequency <=", value, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyIn(List<Integer> values) {
            addCriterion("frequency in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotIn(List<Integer> values) {
            addCriterion("frequency not in", values, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyBetween(Integer value1, Integer value2) {
            addCriterion("frequency between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andFrequencyNotBetween(Integer value1, Integer value2) {
            addCriterion("frequency not between", value1, value2, "frequency");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeIsNull() {
            addCriterion("creaetetime is null");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeIsNotNull() {
            addCriterion("creaetetime is not null");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeEqualTo(Date value) {
            addCriterion("creaetetime =", value, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeNotEqualTo(Date value) {
            addCriterion("creaetetime <>", value, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeGreaterThan(Date value) {
            addCriterion("creaetetime >", value, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("creaetetime >=", value, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeLessThan(Date value) {
            addCriterion("creaetetime <", value, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeLessThanOrEqualTo(Date value) {
            addCriterion("creaetetime <=", value, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeIn(List<Date> values) {
            addCriterion("creaetetime in", values, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeNotIn(List<Date> values) {
            addCriterion("creaetetime not in", values, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeBetween(Date value1, Date value2) {
            addCriterion("creaetetime between", value1, value2, "creaetetime");
            return (Criteria) this;
        }

        public Criteria andCreaetetimeNotBetween(Date value1, Date value2) {
            addCriterion("creaetetime not between", value1, value2, "creaetetime");
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
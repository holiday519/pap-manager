package com.pxene.pap.domain.model.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoTmplModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public VideoTmplModelExample() {
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

        public Criteria andSizeidIsNull() {
            addCriterion("sizeid is null");
            return (Criteria) this;
        }

        public Criteria andSizeidIsNotNull() {
            addCriterion("sizeid is not null");
            return (Criteria) this;
        }

        public Criteria andSizeidEqualTo(String value) {
            addCriterion("sizeid =", value, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidNotEqualTo(String value) {
            addCriterion("sizeid <>", value, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidGreaterThan(String value) {
            addCriterion("sizeid >", value, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidGreaterThanOrEqualTo(String value) {
            addCriterion("sizeid >=", value, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidLessThan(String value) {
            addCriterion("sizeid <", value, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidLessThanOrEqualTo(String value) {
            addCriterion("sizeid <=", value, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidLike(String value) {
            addCriterion("sizeid like", value, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidNotLike(String value) {
            addCriterion("sizeid not like", value, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidIn(List<String> values) {
            addCriterion("sizeid in", values, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidNotIn(List<String> values) {
            addCriterion("sizeid not in", values, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidBetween(String value1, String value2) {
            addCriterion("sizeid between", value1, value2, "sizeid");
            return (Criteria) this;
        }

        public Criteria andSizeidNotBetween(String value1, String value2) {
            addCriterion("sizeid not between", value1, value2, "sizeid");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeIsNull() {
            addCriterion("maxvolume is null");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeIsNotNull() {
            addCriterion("maxvolume is not null");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeEqualTo(Float value) {
            addCriterion("maxvolume =", value, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeNotEqualTo(Float value) {
            addCriterion("maxvolume <>", value, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeGreaterThan(Float value) {
            addCriterion("maxvolume >", value, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeGreaterThanOrEqualTo(Float value) {
            addCriterion("maxvolume >=", value, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeLessThan(Float value) {
            addCriterion("maxvolume <", value, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeLessThanOrEqualTo(Float value) {
            addCriterion("maxvolume <=", value, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeIn(List<Float> values) {
            addCriterion("maxvolume in", values, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeNotIn(List<Float> values) {
            addCriterion("maxvolume not in", values, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeBetween(Float value1, Float value2) {
            addCriterion("maxvolume between", value1, value2, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxvolumeNotBetween(Float value1, Float value2) {
            addCriterion("maxvolume not between", value1, value2, "maxvolume");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthIsNull() {
            addCriterion("maxtimelength is null");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthIsNotNull() {
            addCriterion("maxtimelength is not null");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthEqualTo(Integer value) {
            addCriterion("maxtimelength =", value, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthNotEqualTo(Integer value) {
            addCriterion("maxtimelength <>", value, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthGreaterThan(Integer value) {
            addCriterion("maxtimelength >", value, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxtimelength >=", value, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthLessThan(Integer value) {
            addCriterion("maxtimelength <", value, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthLessThanOrEqualTo(Integer value) {
            addCriterion("maxtimelength <=", value, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthIn(List<Integer> values) {
            addCriterion("maxtimelength in", values, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthNotIn(List<Integer> values) {
            addCriterion("maxtimelength not in", values, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthBetween(Integer value1, Integer value2) {
            addCriterion("maxtimelength between", value1, value2, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andMaxtimelengthNotBetween(Integer value1, Integer value2) {
            addCriterion("maxtimelength not between", value1, value2, "maxtimelength");
            return (Criteria) this;
        }

        public Criteria andImagetmplidIsNull() {
            addCriterion("imagetmplid is null");
            return (Criteria) this;
        }

        public Criteria andImagetmplidIsNotNull() {
            addCriterion("imagetmplid is not null");
            return (Criteria) this;
        }

        public Criteria andImagetmplidEqualTo(String value) {
            addCriterion("imagetmplid =", value, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidNotEqualTo(String value) {
            addCriterion("imagetmplid <>", value, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidGreaterThan(String value) {
            addCriterion("imagetmplid >", value, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidGreaterThanOrEqualTo(String value) {
            addCriterion("imagetmplid >=", value, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidLessThan(String value) {
            addCriterion("imagetmplid <", value, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidLessThanOrEqualTo(String value) {
            addCriterion("imagetmplid <=", value, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidLike(String value) {
            addCriterion("imagetmplid like", value, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidNotLike(String value) {
            addCriterion("imagetmplid not like", value, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidIn(List<String> values) {
            addCriterion("imagetmplid in", values, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidNotIn(List<String> values) {
            addCriterion("imagetmplid not in", values, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidBetween(String value1, String value2) {
            addCriterion("imagetmplid between", value1, value2, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andImagetmplidNotBetween(String value1, String value2) {
            addCriterion("imagetmplid not between", value1, value2, "imagetmplid");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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
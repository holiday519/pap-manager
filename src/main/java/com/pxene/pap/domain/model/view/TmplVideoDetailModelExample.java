package com.pxene.pap.domain.model.view;

import java.util.ArrayList;
import java.util.List;

public class TmplVideoDetailModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TmplVideoDetailModelExample() {
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

        public Criteria andMaxVolumeIsNull() {
            addCriterion("max_volume is null");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeIsNotNull() {
            addCriterion("max_volume is not null");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeEqualTo(Float value) {
            addCriterion("max_volume =", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotEqualTo(Float value) {
            addCriterion("max_volume <>", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeGreaterThan(Float value) {
            addCriterion("max_volume >", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeGreaterThanOrEqualTo(Float value) {
            addCriterion("max_volume >=", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeLessThan(Float value) {
            addCriterion("max_volume <", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeLessThanOrEqualTo(Float value) {
            addCriterion("max_volume <=", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeIn(List<Float> values) {
            addCriterion("max_volume in", values, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotIn(List<Float> values) {
            addCriterion("max_volume not in", values, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeBetween(Float value1, Float value2) {
            addCriterion("max_volume between", value1, value2, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotBetween(Float value1, Float value2) {
            addCriterion("max_volume not between", value1, value2, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthIsNull() {
            addCriterion("max_timelength is null");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthIsNotNull() {
            addCriterion("max_timelength is not null");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthEqualTo(Integer value) {
            addCriterion("max_timelength =", value, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthNotEqualTo(Integer value) {
            addCriterion("max_timelength <>", value, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthGreaterThan(Integer value) {
            addCriterion("max_timelength >", value, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_timelength >=", value, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthLessThan(Integer value) {
            addCriterion("max_timelength <", value, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthLessThanOrEqualTo(Integer value) {
            addCriterion("max_timelength <=", value, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthIn(List<Integer> values) {
            addCriterion("max_timelength in", values, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthNotIn(List<Integer> values) {
            addCriterion("max_timelength not in", values, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthBetween(Integer value1, Integer value2) {
            addCriterion("max_timelength between", value1, value2, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andMaxTimelengthNotBetween(Integer value1, Integer value2) {
            addCriterion("max_timelength not between", value1, value2, "maxTimelength");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdIsNull() {
            addCriterion("image_tmpl_id is null");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdIsNotNull() {
            addCriterion("image_tmpl_id is not null");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdEqualTo(String value) {
            addCriterion("image_tmpl_id =", value, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdNotEqualTo(String value) {
            addCriterion("image_tmpl_id <>", value, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdGreaterThan(String value) {
            addCriterion("image_tmpl_id >", value, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdGreaterThanOrEqualTo(String value) {
            addCriterion("image_tmpl_id >=", value, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdLessThan(String value) {
            addCriterion("image_tmpl_id <", value, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdLessThanOrEqualTo(String value) {
            addCriterion("image_tmpl_id <=", value, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdLike(String value) {
            addCriterion("image_tmpl_id like", value, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdNotLike(String value) {
            addCriterion("image_tmpl_id not like", value, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdIn(List<String> values) {
            addCriterion("image_tmpl_id in", values, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdNotIn(List<String> values) {
            addCriterion("image_tmpl_id not in", values, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdBetween(String value1, String value2) {
            addCriterion("image_tmpl_id between", value1, value2, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andImageTmplIdNotBetween(String value1, String value2) {
            addCriterion("image_tmpl_id not between", value1, value2, "imageTmplId");
            return (Criteria) this;
        }

        public Criteria andWidthIsNull() {
            addCriterion("width is null");
            return (Criteria) this;
        }

        public Criteria andWidthIsNotNull() {
            addCriterion("width is not null");
            return (Criteria) this;
        }

        public Criteria andWidthEqualTo(Integer value) {
            addCriterion("width =", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotEqualTo(Integer value) {
            addCriterion("width <>", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThan(Integer value) {
            addCriterion("width >", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("width >=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThan(Integer value) {
            addCriterion("width <", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThanOrEqualTo(Integer value) {
            addCriterion("width <=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthIn(List<Integer> values) {
            addCriterion("width in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotIn(List<Integer> values) {
            addCriterion("width not in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthBetween(Integer value1, Integer value2) {
            addCriterion("width between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("width not between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            addCriterion("height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(Integer value) {
            addCriterion("height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(Integer value) {
            addCriterion("height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(Integer value) {
            addCriterion("height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(Integer value) {
            addCriterion("height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(Integer value) {
            addCriterion("height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<Integer> values) {
            addCriterion("height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<Integer> values) {
            addCriterion("height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(Integer value1, Integer value2) {
            addCriterion("height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("height not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andSizeNameIsNull() {
            addCriterion("size_name is null");
            return (Criteria) this;
        }

        public Criteria andSizeNameIsNotNull() {
            addCriterion("size_name is not null");
            return (Criteria) this;
        }

        public Criteria andSizeNameEqualTo(String value) {
            addCriterion("size_name =", value, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameNotEqualTo(String value) {
            addCriterion("size_name <>", value, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameGreaterThan(String value) {
            addCriterion("size_name >", value, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameGreaterThanOrEqualTo(String value) {
            addCriterion("size_name >=", value, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameLessThan(String value) {
            addCriterion("size_name <", value, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameLessThanOrEqualTo(String value) {
            addCriterion("size_name <=", value, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameLike(String value) {
            addCriterion("size_name like", value, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameNotLike(String value) {
            addCriterion("size_name not like", value, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameIn(List<String> values) {
            addCriterion("size_name in", values, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameNotIn(List<String> values) {
            addCriterion("size_name not in", values, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameBetween(String value1, String value2) {
            addCriterion("size_name between", value1, value2, "sizeName");
            return (Criteria) this;
        }

        public Criteria andSizeNameNotBetween(String value1, String value2) {
            addCriterion("size_name not between", value1, value2, "sizeName");
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
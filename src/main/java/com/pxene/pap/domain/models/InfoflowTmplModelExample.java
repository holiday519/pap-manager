package com.pxene.pap.domain.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoflowTmplModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InfoflowTmplModelExample() {
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

        public Criteria andMaxTitleIsNull() {
            addCriterion("max_title is null");
            return (Criteria) this;
        }

        public Criteria andMaxTitleIsNotNull() {
            addCriterion("max_title is not null");
            return (Criteria) this;
        }

        public Criteria andMaxTitleEqualTo(Integer value) {
            addCriterion("max_title =", value, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleNotEqualTo(Integer value) {
            addCriterion("max_title <>", value, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleGreaterThan(Integer value) {
            addCriterion("max_title >", value, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_title >=", value, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleLessThan(Integer value) {
            addCriterion("max_title <", value, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleLessThanOrEqualTo(Integer value) {
            addCriterion("max_title <=", value, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleIn(List<Integer> values) {
            addCriterion("max_title in", values, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleNotIn(List<Integer> values) {
            addCriterion("max_title not in", values, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleBetween(Integer value1, Integer value2) {
            addCriterion("max_title between", value1, value2, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andMaxTitleNotBetween(Integer value1, Integer value2) {
            addCriterion("max_title not between", value1, value2, "maxTitle");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionIsNull() {
            addCriterion("have_description is null");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionIsNotNull() {
            addCriterion("have_description is not null");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionEqualTo(String value) {
            addCriterion("have_description =", value, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionNotEqualTo(String value) {
            addCriterion("have_description <>", value, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionGreaterThan(String value) {
            addCriterion("have_description >", value, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("have_description >=", value, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionLessThan(String value) {
            addCriterion("have_description <", value, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionLessThanOrEqualTo(String value) {
            addCriterion("have_description <=", value, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionLike(String value) {
            addCriterion("have_description like", value, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionNotLike(String value) {
            addCriterion("have_description not like", value, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionIn(List<String> values) {
            addCriterion("have_description in", values, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionNotIn(List<String> values) {
            addCriterion("have_description not in", values, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionBetween(String value1, String value2) {
            addCriterion("have_description between", value1, value2, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andHaveDescriptionNotBetween(String value1, String value2) {
            addCriterion("have_description not between", value1, value2, "haveDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionIsNull() {
            addCriterion("max_description is null");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionIsNotNull() {
            addCriterion("max_description is not null");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionEqualTo(Integer value) {
            addCriterion("max_description =", value, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionNotEqualTo(Integer value) {
            addCriterion("max_description <>", value, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionGreaterThan(Integer value) {
            addCriterion("max_description >", value, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_description >=", value, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionLessThan(Integer value) {
            addCriterion("max_description <", value, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionLessThanOrEqualTo(Integer value) {
            addCriterion("max_description <=", value, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionIn(List<Integer> values) {
            addCriterion("max_description in", values, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionNotIn(List<Integer> values) {
            addCriterion("max_description not in", values, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionBetween(Integer value1, Integer value2) {
            addCriterion("max_description between", value1, value2, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMaxDescriptionNotBetween(Integer value1, Integer value2) {
            addCriterion("max_description not between", value1, value2, "maxDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionIsNull() {
            addCriterion("must_description is null");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionIsNotNull() {
            addCriterion("must_description is not null");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionEqualTo(String value) {
            addCriterion("must_description =", value, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionNotEqualTo(String value) {
            addCriterion("must_description <>", value, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionGreaterThan(String value) {
            addCriterion("must_description >", value, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("must_description >=", value, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionLessThan(String value) {
            addCriterion("must_description <", value, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionLessThanOrEqualTo(String value) {
            addCriterion("must_description <=", value, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionLike(String value) {
            addCriterion("must_description like", value, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionNotLike(String value) {
            addCriterion("must_description not like", value, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionIn(List<String> values) {
            addCriterion("must_description in", values, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionNotIn(List<String> values) {
            addCriterion("must_description not in", values, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionBetween(String value1, String value2) {
            addCriterion("must_description between", value1, value2, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andMustDescriptionNotBetween(String value1, String value2) {
            addCriterion("must_description not between", value1, value2, "mustDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionIsNull() {
            addCriterion("have_cta_description is null");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionIsNotNull() {
            addCriterion("have_cta_description is not null");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionEqualTo(String value) {
            addCriterion("have_cta_description =", value, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionNotEqualTo(String value) {
            addCriterion("have_cta_description <>", value, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionGreaterThan(String value) {
            addCriterion("have_cta_description >", value, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("have_cta_description >=", value, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionLessThan(String value) {
            addCriterion("have_cta_description <", value, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionLessThanOrEqualTo(String value) {
            addCriterion("have_cta_description <=", value, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionLike(String value) {
            addCriterion("have_cta_description like", value, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionNotLike(String value) {
            addCriterion("have_cta_description not like", value, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionIn(List<String> values) {
            addCriterion("have_cta_description in", values, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionNotIn(List<String> values) {
            addCriterion("have_cta_description not in", values, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionBetween(String value1, String value2) {
            addCriterion("have_cta_description between", value1, value2, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andHaveCtaDescriptionNotBetween(String value1, String value2) {
            addCriterion("have_cta_description not between", value1, value2, "haveCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionIsNull() {
            addCriterion("max_cta_description is null");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionIsNotNull() {
            addCriterion("max_cta_description is not null");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionEqualTo(Integer value) {
            addCriterion("max_cta_description =", value, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionNotEqualTo(Integer value) {
            addCriterion("max_cta_description <>", value, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionGreaterThan(Integer value) {
            addCriterion("max_cta_description >", value, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_cta_description >=", value, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionLessThan(Integer value) {
            addCriterion("max_cta_description <", value, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionLessThanOrEqualTo(Integer value) {
            addCriterion("max_cta_description <=", value, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionIn(List<Integer> values) {
            addCriterion("max_cta_description in", values, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionNotIn(List<Integer> values) {
            addCriterion("max_cta_description not in", values, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionBetween(Integer value1, Integer value2) {
            addCriterion("max_cta_description between", value1, value2, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMaxCtaDescriptionNotBetween(Integer value1, Integer value2) {
            addCriterion("max_cta_description not between", value1, value2, "maxCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionIsNull() {
            addCriterion("must_cta_description is null");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionIsNotNull() {
            addCriterion("must_cta_description is not null");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionEqualTo(String value) {
            addCriterion("must_cta_description =", value, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionNotEqualTo(String value) {
            addCriterion("must_cta_description <>", value, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionGreaterThan(String value) {
            addCriterion("must_cta_description >", value, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("must_cta_description >=", value, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionLessThan(String value) {
            addCriterion("must_cta_description <", value, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionLessThanOrEqualTo(String value) {
            addCriterion("must_cta_description <=", value, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionLike(String value) {
            addCriterion("must_cta_description like", value, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionNotLike(String value) {
            addCriterion("must_cta_description not like", value, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionIn(List<String> values) {
            addCriterion("must_cta_description in", values, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionNotIn(List<String> values) {
            addCriterion("must_cta_description not in", values, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionBetween(String value1, String value2) {
            addCriterion("must_cta_description between", value1, value2, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andMustCtaDescriptionNotBetween(String value1, String value2) {
            addCriterion("must_cta_description not between", value1, value2, "mustCtaDescription");
            return (Criteria) this;
        }

        public Criteria andIconIdIsNull() {
            addCriterion("icon_id is null");
            return (Criteria) this;
        }

        public Criteria andIconIdIsNotNull() {
            addCriterion("icon_id is not null");
            return (Criteria) this;
        }

        public Criteria andIconIdEqualTo(String value) {
            addCriterion("icon_id =", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdNotEqualTo(String value) {
            addCriterion("icon_id <>", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdGreaterThan(String value) {
            addCriterion("icon_id >", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdGreaterThanOrEqualTo(String value) {
            addCriterion("icon_id >=", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdLessThan(String value) {
            addCriterion("icon_id <", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdLessThanOrEqualTo(String value) {
            addCriterion("icon_id <=", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdLike(String value) {
            addCriterion("icon_id like", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdNotLike(String value) {
            addCriterion("icon_id not like", value, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdIn(List<String> values) {
            addCriterion("icon_id in", values, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdNotIn(List<String> values) {
            addCriterion("icon_id not in", values, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdBetween(String value1, String value2) {
            addCriterion("icon_id between", value1, value2, "iconId");
            return (Criteria) this;
        }

        public Criteria andIconIdNotBetween(String value1, String value2) {
            addCriterion("icon_id not between", value1, value2, "iconId");
            return (Criteria) this;
        }

        public Criteria andImage1IdIsNull() {
            addCriterion("image1_id is null");
            return (Criteria) this;
        }

        public Criteria andImage1IdIsNotNull() {
            addCriterion("image1_id is not null");
            return (Criteria) this;
        }

        public Criteria andImage1IdEqualTo(String value) {
            addCriterion("image1_id =", value, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdNotEqualTo(String value) {
            addCriterion("image1_id <>", value, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdGreaterThan(String value) {
            addCriterion("image1_id >", value, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdGreaterThanOrEqualTo(String value) {
            addCriterion("image1_id >=", value, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdLessThan(String value) {
            addCriterion("image1_id <", value, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdLessThanOrEqualTo(String value) {
            addCriterion("image1_id <=", value, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdLike(String value) {
            addCriterion("image1_id like", value, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdNotLike(String value) {
            addCriterion("image1_id not like", value, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdIn(List<String> values) {
            addCriterion("image1_id in", values, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdNotIn(List<String> values) {
            addCriterion("image1_id not in", values, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdBetween(String value1, String value2) {
            addCriterion("image1_id between", value1, value2, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage1IdNotBetween(String value1, String value2) {
            addCriterion("image1_id not between", value1, value2, "image1Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdIsNull() {
            addCriterion("image2_id is null");
            return (Criteria) this;
        }

        public Criteria andImage2IdIsNotNull() {
            addCriterion("image2_id is not null");
            return (Criteria) this;
        }

        public Criteria andImage2IdEqualTo(String value) {
            addCriterion("image2_id =", value, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdNotEqualTo(String value) {
            addCriterion("image2_id <>", value, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdGreaterThan(String value) {
            addCriterion("image2_id >", value, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdGreaterThanOrEqualTo(String value) {
            addCriterion("image2_id >=", value, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdLessThan(String value) {
            addCriterion("image2_id <", value, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdLessThanOrEqualTo(String value) {
            addCriterion("image2_id <=", value, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdLike(String value) {
            addCriterion("image2_id like", value, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdNotLike(String value) {
            addCriterion("image2_id not like", value, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdIn(List<String> values) {
            addCriterion("image2_id in", values, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdNotIn(List<String> values) {
            addCriterion("image2_id not in", values, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdBetween(String value1, String value2) {
            addCriterion("image2_id between", value1, value2, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage2IdNotBetween(String value1, String value2) {
            addCriterion("image2_id not between", value1, value2, "image2Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdIsNull() {
            addCriterion("image3_id is null");
            return (Criteria) this;
        }

        public Criteria andImage3IdIsNotNull() {
            addCriterion("image3_id is not null");
            return (Criteria) this;
        }

        public Criteria andImage3IdEqualTo(String value) {
            addCriterion("image3_id =", value, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdNotEqualTo(String value) {
            addCriterion("image3_id <>", value, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdGreaterThan(String value) {
            addCriterion("image3_id >", value, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdGreaterThanOrEqualTo(String value) {
            addCriterion("image3_id >=", value, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdLessThan(String value) {
            addCriterion("image3_id <", value, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdLessThanOrEqualTo(String value) {
            addCriterion("image3_id <=", value, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdLike(String value) {
            addCriterion("image3_id like", value, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdNotLike(String value) {
            addCriterion("image3_id not like", value, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdIn(List<String> values) {
            addCriterion("image3_id in", values, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdNotIn(List<String> values) {
            addCriterion("image3_id not in", values, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdBetween(String value1, String value2) {
            addCriterion("image3_id between", value1, value2, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage3IdNotBetween(String value1, String value2) {
            addCriterion("image3_id not between", value1, value2, "image3Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdIsNull() {
            addCriterion("image4_id is null");
            return (Criteria) this;
        }

        public Criteria andImage4IdIsNotNull() {
            addCriterion("image4_id is not null");
            return (Criteria) this;
        }

        public Criteria andImage4IdEqualTo(String value) {
            addCriterion("image4_id =", value, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdNotEqualTo(String value) {
            addCriterion("image4_id <>", value, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdGreaterThan(String value) {
            addCriterion("image4_id >", value, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdGreaterThanOrEqualTo(String value) {
            addCriterion("image4_id >=", value, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdLessThan(String value) {
            addCriterion("image4_id <", value, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdLessThanOrEqualTo(String value) {
            addCriterion("image4_id <=", value, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdLike(String value) {
            addCriterion("image4_id like", value, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdNotLike(String value) {
            addCriterion("image4_id not like", value, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdIn(List<String> values) {
            addCriterion("image4_id in", values, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdNotIn(List<String> values) {
            addCriterion("image4_id not in", values, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdBetween(String value1, String value2) {
            addCriterion("image4_id between", value1, value2, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage4IdNotBetween(String value1, String value2) {
            addCriterion("image4_id not between", value1, value2, "image4Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdIsNull() {
            addCriterion("image5_id is null");
            return (Criteria) this;
        }

        public Criteria andImage5IdIsNotNull() {
            addCriterion("image5_id is not null");
            return (Criteria) this;
        }

        public Criteria andImage5IdEqualTo(String value) {
            addCriterion("image5_id =", value, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdNotEqualTo(String value) {
            addCriterion("image5_id <>", value, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdGreaterThan(String value) {
            addCriterion("image5_id >", value, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdGreaterThanOrEqualTo(String value) {
            addCriterion("image5_id >=", value, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdLessThan(String value) {
            addCriterion("image5_id <", value, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdLessThanOrEqualTo(String value) {
            addCriterion("image5_id <=", value, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdLike(String value) {
            addCriterion("image5_id like", value, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdNotLike(String value) {
            addCriterion("image5_id not like", value, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdIn(List<String> values) {
            addCriterion("image5_id in", values, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdNotIn(List<String> values) {
            addCriterion("image5_id not in", values, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdBetween(String value1, String value2) {
            addCriterion("image5_id between", value1, value2, "image5Id");
            return (Criteria) this;
        }

        public Criteria andImage5IdNotBetween(String value1, String value2) {
            addCriterion("image5_id not between", value1, value2, "image5Id");
            return (Criteria) this;
        }

        public Criteria andIsAppStarIsNull() {
            addCriterion("is_app_star is null");
            return (Criteria) this;
        }

        public Criteria andIsAppStarIsNotNull() {
            addCriterion("is_app_star is not null");
            return (Criteria) this;
        }

        public Criteria andIsAppStarEqualTo(String value) {
            addCriterion("is_app_star =", value, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarNotEqualTo(String value) {
            addCriterion("is_app_star <>", value, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarGreaterThan(String value) {
            addCriterion("is_app_star >", value, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarGreaterThanOrEqualTo(String value) {
            addCriterion("is_app_star >=", value, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarLessThan(String value) {
            addCriterion("is_app_star <", value, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarLessThanOrEqualTo(String value) {
            addCriterion("is_app_star <=", value, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarLike(String value) {
            addCriterion("is_app_star like", value, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarNotLike(String value) {
            addCriterion("is_app_star not like", value, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarIn(List<String> values) {
            addCriterion("is_app_star in", values, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarNotIn(List<String> values) {
            addCriterion("is_app_star not in", values, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarBetween(String value1, String value2) {
            addCriterion("is_app_star between", value1, value2, "isAppStar");
            return (Criteria) this;
        }

        public Criteria andIsAppStarNotBetween(String value1, String value2) {
            addCriterion("is_app_star not between", value1, value2, "isAppStar");
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
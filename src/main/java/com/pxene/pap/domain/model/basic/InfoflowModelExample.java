package com.pxene.pap.domain.model.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoflowModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InfoflowModelExample() {
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

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionIsNull() {
            addCriterion("cta_description is null");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionIsNotNull() {
            addCriterion("cta_description is not null");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionEqualTo(String value) {
            addCriterion("cta_description =", value, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionNotEqualTo(String value) {
            addCriterion("cta_description <>", value, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionGreaterThan(String value) {
            addCriterion("cta_description >", value, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("cta_description >=", value, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionLessThan(String value) {
            addCriterion("cta_description <", value, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionLessThanOrEqualTo(String value) {
            addCriterion("cta_description <=", value, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionLike(String value) {
            addCriterion("cta_description like", value, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionNotLike(String value) {
            addCriterion("cta_description not like", value, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionIn(List<String> values) {
            addCriterion("cta_description in", values, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionNotIn(List<String> values) {
            addCriterion("cta_description not in", values, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionBetween(String value1, String value2) {
            addCriterion("cta_description between", value1, value2, "ctaDescription");
            return (Criteria) this;
        }

        public Criteria andCtaDescriptionNotBetween(String value1, String value2) {
            addCriterion("cta_description not between", value1, value2, "ctaDescription");
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

        public Criteria andAppStarIsNull() {
            addCriterion("app_star is null");
            return (Criteria) this;
        }

        public Criteria andAppStarIsNotNull() {
            addCriterion("app_star is not null");
            return (Criteria) this;
        }

        public Criteria andAppStarEqualTo(Integer value) {
            addCriterion("app_star =", value, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarNotEqualTo(Integer value) {
            addCriterion("app_star <>", value, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarGreaterThan(Integer value) {
            addCriterion("app_star >", value, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarGreaterThanOrEqualTo(Integer value) {
            addCriterion("app_star >=", value, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarLessThan(Integer value) {
            addCriterion("app_star <", value, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarLessThanOrEqualTo(Integer value) {
            addCriterion("app_star <=", value, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarIn(List<Integer> values) {
            addCriterion("app_star in", values, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarNotIn(List<Integer> values) {
            addCriterion("app_star not in", values, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarBetween(Integer value1, Integer value2) {
            addCriterion("app_star between", value1, value2, "appStar");
            return (Criteria) this;
        }

        public Criteria andAppStarNotBetween(Integer value1, Integer value2) {
            addCriterion("app_star not between", value1, value2, "appStar");
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
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

        public Criteria andIconIsNull() {
            addCriterion("icon is null");
            return (Criteria) this;
        }

        public Criteria andIconIsNotNull() {
            addCriterion("icon is not null");
            return (Criteria) this;
        }

        public Criteria andIconEqualTo(String value) {
            addCriterion("icon =", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotEqualTo(String value) {
            addCriterion("icon <>", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThan(String value) {
            addCriterion("icon >", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconGreaterThanOrEqualTo(String value) {
            addCriterion("icon >=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThan(String value) {
            addCriterion("icon <", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLessThanOrEqualTo(String value) {
            addCriterion("icon <=", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconLike(String value) {
            addCriterion("icon like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotLike(String value) {
            addCriterion("icon not like", value, "icon");
            return (Criteria) this;
        }

        public Criteria andIconIn(List<String> values) {
            addCriterion("icon in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotIn(List<String> values) {
            addCriterion("icon not in", values, "icon");
            return (Criteria) this;
        }

        public Criteria andIconBetween(String value1, String value2) {
            addCriterion("icon between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andIconNotBetween(String value1, String value2) {
            addCriterion("icon not between", value1, value2, "icon");
            return (Criteria) this;
        }

        public Criteria andImage1IsNull() {
            addCriterion("image1 is null");
            return (Criteria) this;
        }

        public Criteria andImage1IsNotNull() {
            addCriterion("image1 is not null");
            return (Criteria) this;
        }

        public Criteria andImage1EqualTo(String value) {
            addCriterion("image1 =", value, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1NotEqualTo(String value) {
            addCriterion("image1 <>", value, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1GreaterThan(String value) {
            addCriterion("image1 >", value, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1GreaterThanOrEqualTo(String value) {
            addCriterion("image1 >=", value, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1LessThan(String value) {
            addCriterion("image1 <", value, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1LessThanOrEqualTo(String value) {
            addCriterion("image1 <=", value, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1Like(String value) {
            addCriterion("image1 like", value, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1NotLike(String value) {
            addCriterion("image1 not like", value, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1In(List<String> values) {
            addCriterion("image1 in", values, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1NotIn(List<String> values) {
            addCriterion("image1 not in", values, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1Between(String value1, String value2) {
            addCriterion("image1 between", value1, value2, "image1");
            return (Criteria) this;
        }

        public Criteria andImage1NotBetween(String value1, String value2) {
            addCriterion("image1 not between", value1, value2, "image1");
            return (Criteria) this;
        }

        public Criteria andImage2IsNull() {
            addCriterion("image2 is null");
            return (Criteria) this;
        }

        public Criteria andImage2IsNotNull() {
            addCriterion("image2 is not null");
            return (Criteria) this;
        }

        public Criteria andImage2EqualTo(String value) {
            addCriterion("image2 =", value, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2NotEqualTo(String value) {
            addCriterion("image2 <>", value, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2GreaterThan(String value) {
            addCriterion("image2 >", value, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2GreaterThanOrEqualTo(String value) {
            addCriterion("image2 >=", value, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2LessThan(String value) {
            addCriterion("image2 <", value, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2LessThanOrEqualTo(String value) {
            addCriterion("image2 <=", value, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2Like(String value) {
            addCriterion("image2 like", value, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2NotLike(String value) {
            addCriterion("image2 not like", value, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2In(List<String> values) {
            addCriterion("image2 in", values, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2NotIn(List<String> values) {
            addCriterion("image2 not in", values, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2Between(String value1, String value2) {
            addCriterion("image2 between", value1, value2, "image2");
            return (Criteria) this;
        }

        public Criteria andImage2NotBetween(String value1, String value2) {
            addCriterion("image2 not between", value1, value2, "image2");
            return (Criteria) this;
        }

        public Criteria andImage3IsNull() {
            addCriterion("image3 is null");
            return (Criteria) this;
        }

        public Criteria andImage3IsNotNull() {
            addCriterion("image3 is not null");
            return (Criteria) this;
        }

        public Criteria andImage3EqualTo(String value) {
            addCriterion("image3 =", value, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3NotEqualTo(String value) {
            addCriterion("image3 <>", value, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3GreaterThan(String value) {
            addCriterion("image3 >", value, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3GreaterThanOrEqualTo(String value) {
            addCriterion("image3 >=", value, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3LessThan(String value) {
            addCriterion("image3 <", value, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3LessThanOrEqualTo(String value) {
            addCriterion("image3 <=", value, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3Like(String value) {
            addCriterion("image3 like", value, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3NotLike(String value) {
            addCriterion("image3 not like", value, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3In(List<String> values) {
            addCriterion("image3 in", values, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3NotIn(List<String> values) {
            addCriterion("image3 not in", values, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3Between(String value1, String value2) {
            addCriterion("image3 between", value1, value2, "image3");
            return (Criteria) this;
        }

        public Criteria andImage3NotBetween(String value1, String value2) {
            addCriterion("image3 not between", value1, value2, "image3");
            return (Criteria) this;
        }

        public Criteria andImage4IsNull() {
            addCriterion("image4 is null");
            return (Criteria) this;
        }

        public Criteria andImage4IsNotNull() {
            addCriterion("image4 is not null");
            return (Criteria) this;
        }

        public Criteria andImage4EqualTo(String value) {
            addCriterion("image4 =", value, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4NotEqualTo(String value) {
            addCriterion("image4 <>", value, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4GreaterThan(String value) {
            addCriterion("image4 >", value, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4GreaterThanOrEqualTo(String value) {
            addCriterion("image4 >=", value, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4LessThan(String value) {
            addCriterion("image4 <", value, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4LessThanOrEqualTo(String value) {
            addCriterion("image4 <=", value, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4Like(String value) {
            addCriterion("image4 like", value, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4NotLike(String value) {
            addCriterion("image4 not like", value, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4In(List<String> values) {
            addCriterion("image4 in", values, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4NotIn(List<String> values) {
            addCriterion("image4 not in", values, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4Between(String value1, String value2) {
            addCriterion("image4 between", value1, value2, "image4");
            return (Criteria) this;
        }

        public Criteria andImage4NotBetween(String value1, String value2) {
            addCriterion("image4 not between", value1, value2, "image4");
            return (Criteria) this;
        }

        public Criteria andImage5IsNull() {
            addCriterion("image5 is null");
            return (Criteria) this;
        }

        public Criteria andImage5IsNotNull() {
            addCriterion("image5 is not null");
            return (Criteria) this;
        }

        public Criteria andImage5EqualTo(String value) {
            addCriterion("image5 =", value, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5NotEqualTo(String value) {
            addCriterion("image5 <>", value, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5GreaterThan(String value) {
            addCriterion("image5 >", value, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5GreaterThanOrEqualTo(String value) {
            addCriterion("image5 >=", value, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5LessThan(String value) {
            addCriterion("image5 <", value, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5LessThanOrEqualTo(String value) {
            addCriterion("image5 <=", value, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5Like(String value) {
            addCriterion("image5 like", value, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5NotLike(String value) {
            addCriterion("image5 not like", value, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5In(List<String> values) {
            addCriterion("image5 in", values, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5NotIn(List<String> values) {
            addCriterion("image5 not in", values, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5Between(String value1, String value2) {
            addCriterion("image5 between", value1, value2, "image5");
            return (Criteria) this;
        }

        public Criteria andImage5NotBetween(String value1, String value2) {
            addCriterion("image5 not between", value1, value2, "image5");
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
package com.pxene.pap.domain.model.basic;

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

        public Criteria andMaxtitleIsNull() {
            addCriterion("maxtitle is null");
            return (Criteria) this;
        }

        public Criteria andMaxtitleIsNotNull() {
            addCriterion("maxtitle is not null");
            return (Criteria) this;
        }

        public Criteria andMaxtitleEqualTo(Integer value) {
            addCriterion("maxtitle =", value, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleNotEqualTo(Integer value) {
            addCriterion("maxtitle <>", value, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleGreaterThan(Integer value) {
            addCriterion("maxtitle >", value, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxtitle >=", value, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleLessThan(Integer value) {
            addCriterion("maxtitle <", value, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleLessThanOrEqualTo(Integer value) {
            addCriterion("maxtitle <=", value, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleIn(List<Integer> values) {
            addCriterion("maxtitle in", values, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleNotIn(List<Integer> values) {
            addCriterion("maxtitle not in", values, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleBetween(Integer value1, Integer value2) {
            addCriterion("maxtitle between", value1, value2, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxtitleNotBetween(Integer value1, Integer value2) {
            addCriterion("maxtitle not between", value1, value2, "maxtitle");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionIsNull() {
            addCriterion("maxdescription is null");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionIsNotNull() {
            addCriterion("maxdescription is not null");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionEqualTo(Integer value) {
            addCriterion("maxdescription =", value, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionNotEqualTo(Integer value) {
            addCriterion("maxdescription <>", value, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionGreaterThan(Integer value) {
            addCriterion("maxdescription >", value, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionGreaterThanOrEqualTo(Integer value) {
            addCriterion("maxdescription >=", value, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionLessThan(Integer value) {
            addCriterion("maxdescription <", value, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionLessThanOrEqualTo(Integer value) {
            addCriterion("maxdescription <=", value, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionIn(List<Integer> values) {
            addCriterion("maxdescription in", values, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionNotIn(List<Integer> values) {
            addCriterion("maxdescription not in", values, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionBetween(Integer value1, Integer value2) {
            addCriterion("maxdescription between", value1, value2, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andMaxdescriptionNotBetween(Integer value1, Integer value2) {
            addCriterion("maxdescription not between", value1, value2, "maxdescription");
            return (Criteria) this;
        }

        public Criteria andIconidIsNull() {
            addCriterion("iconid is null");
            return (Criteria) this;
        }

        public Criteria andIconidIsNotNull() {
            addCriterion("iconid is not null");
            return (Criteria) this;
        }

        public Criteria andIconidEqualTo(String value) {
            addCriterion("iconid =", value, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidNotEqualTo(String value) {
            addCriterion("iconid <>", value, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidGreaterThan(String value) {
            addCriterion("iconid >", value, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidGreaterThanOrEqualTo(String value) {
            addCriterion("iconid >=", value, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidLessThan(String value) {
            addCriterion("iconid <", value, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidLessThanOrEqualTo(String value) {
            addCriterion("iconid <=", value, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidLike(String value) {
            addCriterion("iconid like", value, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidNotLike(String value) {
            addCriterion("iconid not like", value, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidIn(List<String> values) {
            addCriterion("iconid in", values, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidNotIn(List<String> values) {
            addCriterion("iconid not in", values, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidBetween(String value1, String value2) {
            addCriterion("iconid between", value1, value2, "iconid");
            return (Criteria) this;
        }

        public Criteria andIconidNotBetween(String value1, String value2) {
            addCriterion("iconid not between", value1, value2, "iconid");
            return (Criteria) this;
        }

        public Criteria andImage1idIsNull() {
            addCriterion("image1id is null");
            return (Criteria) this;
        }

        public Criteria andImage1idIsNotNull() {
            addCriterion("image1id is not null");
            return (Criteria) this;
        }

        public Criteria andImage1idEqualTo(String value) {
            addCriterion("image1id =", value, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idNotEqualTo(String value) {
            addCriterion("image1id <>", value, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idGreaterThan(String value) {
            addCriterion("image1id >", value, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idGreaterThanOrEqualTo(String value) {
            addCriterion("image1id >=", value, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idLessThan(String value) {
            addCriterion("image1id <", value, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idLessThanOrEqualTo(String value) {
            addCriterion("image1id <=", value, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idLike(String value) {
            addCriterion("image1id like", value, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idNotLike(String value) {
            addCriterion("image1id not like", value, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idIn(List<String> values) {
            addCriterion("image1id in", values, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idNotIn(List<String> values) {
            addCriterion("image1id not in", values, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idBetween(String value1, String value2) {
            addCriterion("image1id between", value1, value2, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage1idNotBetween(String value1, String value2) {
            addCriterion("image1id not between", value1, value2, "image1id");
            return (Criteria) this;
        }

        public Criteria andImage2idIsNull() {
            addCriterion("image2id is null");
            return (Criteria) this;
        }

        public Criteria andImage2idIsNotNull() {
            addCriterion("image2id is not null");
            return (Criteria) this;
        }

        public Criteria andImage2idEqualTo(String value) {
            addCriterion("image2id =", value, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idNotEqualTo(String value) {
            addCriterion("image2id <>", value, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idGreaterThan(String value) {
            addCriterion("image2id >", value, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idGreaterThanOrEqualTo(String value) {
            addCriterion("image2id >=", value, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idLessThan(String value) {
            addCriterion("image2id <", value, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idLessThanOrEqualTo(String value) {
            addCriterion("image2id <=", value, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idLike(String value) {
            addCriterion("image2id like", value, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idNotLike(String value) {
            addCriterion("image2id not like", value, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idIn(List<String> values) {
            addCriterion("image2id in", values, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idNotIn(List<String> values) {
            addCriterion("image2id not in", values, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idBetween(String value1, String value2) {
            addCriterion("image2id between", value1, value2, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage2idNotBetween(String value1, String value2) {
            addCriterion("image2id not between", value1, value2, "image2id");
            return (Criteria) this;
        }

        public Criteria andImage3idIsNull() {
            addCriterion("image3id is null");
            return (Criteria) this;
        }

        public Criteria andImage3idIsNotNull() {
            addCriterion("image3id is not null");
            return (Criteria) this;
        }

        public Criteria andImage3idEqualTo(String value) {
            addCriterion("image3id =", value, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idNotEqualTo(String value) {
            addCriterion("image3id <>", value, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idGreaterThan(String value) {
            addCriterion("image3id >", value, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idGreaterThanOrEqualTo(String value) {
            addCriterion("image3id >=", value, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idLessThan(String value) {
            addCriterion("image3id <", value, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idLessThanOrEqualTo(String value) {
            addCriterion("image3id <=", value, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idLike(String value) {
            addCriterion("image3id like", value, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idNotLike(String value) {
            addCriterion("image3id not like", value, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idIn(List<String> values) {
            addCriterion("image3id in", values, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idNotIn(List<String> values) {
            addCriterion("image3id not in", values, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idBetween(String value1, String value2) {
            addCriterion("image3id between", value1, value2, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage3idNotBetween(String value1, String value2) {
            addCriterion("image3id not between", value1, value2, "image3id");
            return (Criteria) this;
        }

        public Criteria andImage4idIsNull() {
            addCriterion("image4id is null");
            return (Criteria) this;
        }

        public Criteria andImage4idIsNotNull() {
            addCriterion("image4id is not null");
            return (Criteria) this;
        }

        public Criteria andImage4idEqualTo(String value) {
            addCriterion("image4id =", value, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idNotEqualTo(String value) {
            addCriterion("image4id <>", value, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idGreaterThan(String value) {
            addCriterion("image4id >", value, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idGreaterThanOrEqualTo(String value) {
            addCriterion("image4id >=", value, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idLessThan(String value) {
            addCriterion("image4id <", value, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idLessThanOrEqualTo(String value) {
            addCriterion("image4id <=", value, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idLike(String value) {
            addCriterion("image4id like", value, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idNotLike(String value) {
            addCriterion("image4id not like", value, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idIn(List<String> values) {
            addCriterion("image4id in", values, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idNotIn(List<String> values) {
            addCriterion("image4id not in", values, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idBetween(String value1, String value2) {
            addCriterion("image4id between", value1, value2, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage4idNotBetween(String value1, String value2) {
            addCriterion("image4id not between", value1, value2, "image4id");
            return (Criteria) this;
        }

        public Criteria andImage5idIsNull() {
            addCriterion("image5id is null");
            return (Criteria) this;
        }

        public Criteria andImage5idIsNotNull() {
            addCriterion("image5id is not null");
            return (Criteria) this;
        }

        public Criteria andImage5idEqualTo(String value) {
            addCriterion("image5id =", value, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idNotEqualTo(String value) {
            addCriterion("image5id <>", value, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idGreaterThan(String value) {
            addCriterion("image5id >", value, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idGreaterThanOrEqualTo(String value) {
            addCriterion("image5id >=", value, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idLessThan(String value) {
            addCriterion("image5id <", value, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idLessThanOrEqualTo(String value) {
            addCriterion("image5id <=", value, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idLike(String value) {
            addCriterion("image5id like", value, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idNotLike(String value) {
            addCriterion("image5id not like", value, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idIn(List<String> values) {
            addCriterion("image5id in", values, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idNotIn(List<String> values) {
            addCriterion("image5id not in", values, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idBetween(String value1, String value2) {
            addCriterion("image5id between", value1, value2, "image5id");
            return (Criteria) this;
        }

        public Criteria andImage5idNotBetween(String value1, String value2) {
            addCriterion("image5id not between", value1, value2, "image5id");
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
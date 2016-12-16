package com.pxene.pap.domain.model.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdxModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdxModelExample() {
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

        public Criteria andImpressionUrlIsNull() {
            addCriterion("impression_url is null");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlIsNotNull() {
            addCriterion("impression_url is not null");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlEqualTo(String value) {
            addCriterion("impression_url =", value, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlNotEqualTo(String value) {
            addCriterion("impression_url <>", value, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlGreaterThan(String value) {
            addCriterion("impression_url >", value, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlGreaterThanOrEqualTo(String value) {
            addCriterion("impression_url >=", value, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlLessThan(String value) {
            addCriterion("impression_url <", value, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlLessThanOrEqualTo(String value) {
            addCriterion("impression_url <=", value, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlLike(String value) {
            addCriterion("impression_url like", value, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlNotLike(String value) {
            addCriterion("impression_url not like", value, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlIn(List<String> values) {
            addCriterion("impression_url in", values, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlNotIn(List<String> values) {
            addCriterion("impression_url not in", values, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlBetween(String value1, String value2) {
            addCriterion("impression_url between", value1, value2, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andImpressionUrlNotBetween(String value1, String value2) {
            addCriterion("impression_url not between", value1, value2, "impressionUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlIsNull() {
            addCriterion("click_url is null");
            return (Criteria) this;
        }

        public Criteria andClickUrlIsNotNull() {
            addCriterion("click_url is not null");
            return (Criteria) this;
        }

        public Criteria andClickUrlEqualTo(String value) {
            addCriterion("click_url =", value, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlNotEqualTo(String value) {
            addCriterion("click_url <>", value, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlGreaterThan(String value) {
            addCriterion("click_url >", value, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlGreaterThanOrEqualTo(String value) {
            addCriterion("click_url >=", value, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlLessThan(String value) {
            addCriterion("click_url <", value, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlLessThanOrEqualTo(String value) {
            addCriterion("click_url <=", value, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlLike(String value) {
            addCriterion("click_url like", value, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlNotLike(String value) {
            addCriterion("click_url not like", value, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlIn(List<String> values) {
            addCriterion("click_url in", values, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlNotIn(List<String> values) {
            addCriterion("click_url not in", values, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlBetween(String value1, String value2) {
            addCriterion("click_url between", value1, value2, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andClickUrlNotBetween(String value1, String value2) {
            addCriterion("click_url not between", value1, value2, "clickUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlIsNull() {
            addCriterion("aexamine_url is null");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlIsNotNull() {
            addCriterion("aexamine_url is not null");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlEqualTo(String value) {
            addCriterion("aexamine_url =", value, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlNotEqualTo(String value) {
            addCriterion("aexamine_url <>", value, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlGreaterThan(String value) {
            addCriterion("aexamine_url >", value, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlGreaterThanOrEqualTo(String value) {
            addCriterion("aexamine_url >=", value, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlLessThan(String value) {
            addCriterion("aexamine_url <", value, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlLessThanOrEqualTo(String value) {
            addCriterion("aexamine_url <=", value, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlLike(String value) {
            addCriterion("aexamine_url like", value, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlNotLike(String value) {
            addCriterion("aexamine_url not like", value, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlIn(List<String> values) {
            addCriterion("aexamine_url in", values, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlNotIn(List<String> values) {
            addCriterion("aexamine_url not in", values, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlBetween(String value1, String value2) {
            addCriterion("aexamine_url between", value1, value2, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineUrlNotBetween(String value1, String value2) {
            addCriterion("aexamine_url not between", value1, value2, "aexamineUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlIsNull() {
            addCriterion("aexamineresult_url is null");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlIsNotNull() {
            addCriterion("aexamineresult_url is not null");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlEqualTo(String value) {
            addCriterion("aexamineresult_url =", value, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlNotEqualTo(String value) {
            addCriterion("aexamineresult_url <>", value, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlGreaterThan(String value) {
            addCriterion("aexamineresult_url >", value, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlGreaterThanOrEqualTo(String value) {
            addCriterion("aexamineresult_url >=", value, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlLessThan(String value) {
            addCriterion("aexamineresult_url <", value, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlLessThanOrEqualTo(String value) {
            addCriterion("aexamineresult_url <=", value, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlLike(String value) {
            addCriterion("aexamineresult_url like", value, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlNotLike(String value) {
            addCriterion("aexamineresult_url not like", value, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlIn(List<String> values) {
            addCriterion("aexamineresult_url in", values, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlNotIn(List<String> values) {
            addCriterion("aexamineresult_url not in", values, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlBetween(String value1, String value2) {
            addCriterion("aexamineresult_url between", value1, value2, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineresultUrlNotBetween(String value1, String value2) {
            addCriterion("aexamineresult_url not between", value1, value2, "aexamineresultUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlIsNull() {
            addCriterion("aupdate_url is null");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlIsNotNull() {
            addCriterion("aupdate_url is not null");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlEqualTo(String value) {
            addCriterion("aupdate_url =", value, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlNotEqualTo(String value) {
            addCriterion("aupdate_url <>", value, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlGreaterThan(String value) {
            addCriterion("aupdate_url >", value, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlGreaterThanOrEqualTo(String value) {
            addCriterion("aupdate_url >=", value, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlLessThan(String value) {
            addCriterion("aupdate_url <", value, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlLessThanOrEqualTo(String value) {
            addCriterion("aupdate_url <=", value, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlLike(String value) {
            addCriterion("aupdate_url like", value, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlNotLike(String value) {
            addCriterion("aupdate_url not like", value, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlIn(List<String> values) {
            addCriterion("aupdate_url in", values, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlNotIn(List<String> values) {
            addCriterion("aupdate_url not in", values, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlBetween(String value1, String value2) {
            addCriterion("aupdate_url between", value1, value2, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAupdateUrlNotBetween(String value1, String value2) {
            addCriterion("aupdate_url not between", value1, value2, "aupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlIsNull() {
            addCriterion("qexamine_url is null");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlIsNotNull() {
            addCriterion("qexamine_url is not null");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlEqualTo(String value) {
            addCriterion("qexamine_url =", value, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlNotEqualTo(String value) {
            addCriterion("qexamine_url <>", value, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlGreaterThan(String value) {
            addCriterion("qexamine_url >", value, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlGreaterThanOrEqualTo(String value) {
            addCriterion("qexamine_url >=", value, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlLessThan(String value) {
            addCriterion("qexamine_url <", value, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlLessThanOrEqualTo(String value) {
            addCriterion("qexamine_url <=", value, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlLike(String value) {
            addCriterion("qexamine_url like", value, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlNotLike(String value) {
            addCriterion("qexamine_url not like", value, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlIn(List<String> values) {
            addCriterion("qexamine_url in", values, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlNotIn(List<String> values) {
            addCriterion("qexamine_url not in", values, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlBetween(String value1, String value2) {
            addCriterion("qexamine_url between", value1, value2, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQexamineUrlNotBetween(String value1, String value2) {
            addCriterion("qexamine_url not between", value1, value2, "qexamineUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlIsNull() {
            addCriterion("qupdate_url is null");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlIsNotNull() {
            addCriterion("qupdate_url is not null");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlEqualTo(String value) {
            addCriterion("qupdate_url =", value, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlNotEqualTo(String value) {
            addCriterion("qupdate_url <>", value, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlGreaterThan(String value) {
            addCriterion("qupdate_url >", value, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlGreaterThanOrEqualTo(String value) {
            addCriterion("qupdate_url >=", value, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlLessThan(String value) {
            addCriterion("qupdate_url <", value, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlLessThanOrEqualTo(String value) {
            addCriterion("qupdate_url <=", value, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlLike(String value) {
            addCriterion("qupdate_url like", value, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlNotLike(String value) {
            addCriterion("qupdate_url not like", value, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlIn(List<String> values) {
            addCriterion("qupdate_url in", values, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlNotIn(List<String> values) {
            addCriterion("qupdate_url not in", values, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlBetween(String value1, String value2) {
            addCriterion("qupdate_url between", value1, value2, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andQupdateUrlNotBetween(String value1, String value2) {
            addCriterion("qupdate_url not between", value1, value2, "qupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlIsNull() {
            addCriterion("cexamine_url is null");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlIsNotNull() {
            addCriterion("cexamine_url is not null");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlEqualTo(String value) {
            addCriterion("cexamine_url =", value, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlNotEqualTo(String value) {
            addCriterion("cexamine_url <>", value, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlGreaterThan(String value) {
            addCriterion("cexamine_url >", value, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlGreaterThanOrEqualTo(String value) {
            addCriterion("cexamine_url >=", value, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlLessThan(String value) {
            addCriterion("cexamine_url <", value, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlLessThanOrEqualTo(String value) {
            addCriterion("cexamine_url <=", value, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlLike(String value) {
            addCriterion("cexamine_url like", value, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlNotLike(String value) {
            addCriterion("cexamine_url not like", value, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlIn(List<String> values) {
            addCriterion("cexamine_url in", values, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlNotIn(List<String> values) {
            addCriterion("cexamine_url not in", values, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlBetween(String value1, String value2) {
            addCriterion("cexamine_url between", value1, value2, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineUrlNotBetween(String value1, String value2) {
            addCriterion("cexamine_url not between", value1, value2, "cexamineUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlIsNull() {
            addCriterion("cexamine_result_url is null");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlIsNotNull() {
            addCriterion("cexamine_result_url is not null");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlEqualTo(String value) {
            addCriterion("cexamine_result_url =", value, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlNotEqualTo(String value) {
            addCriterion("cexamine_result_url <>", value, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlGreaterThan(String value) {
            addCriterion("cexamine_result_url >", value, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlGreaterThanOrEqualTo(String value) {
            addCriterion("cexamine_result_url >=", value, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlLessThan(String value) {
            addCriterion("cexamine_result_url <", value, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlLessThanOrEqualTo(String value) {
            addCriterion("cexamine_result_url <=", value, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlLike(String value) {
            addCriterion("cexamine_result_url like", value, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlNotLike(String value) {
            addCriterion("cexamine_result_url not like", value, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlIn(List<String> values) {
            addCriterion("cexamine_result_url in", values, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlNotIn(List<String> values) {
            addCriterion("cexamine_result_url not in", values, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlBetween(String value1, String value2) {
            addCriterion("cexamine_result_url between", value1, value2, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCexamineResultUrlNotBetween(String value1, String value2) {
            addCriterion("cexamine_result_url not between", value1, value2, "cexamineResultUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlIsNull() {
            addCriterion("cupdate_url is null");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlIsNotNull() {
            addCriterion("cupdate_url is not null");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlEqualTo(String value) {
            addCriterion("cupdate_url =", value, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlNotEqualTo(String value) {
            addCriterion("cupdate_url <>", value, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlGreaterThan(String value) {
            addCriterion("cupdate_url >", value, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlGreaterThanOrEqualTo(String value) {
            addCriterion("cupdate_url >=", value, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlLessThan(String value) {
            addCriterion("cupdate_url <", value, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlLessThanOrEqualTo(String value) {
            addCriterion("cupdate_url <=", value, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlLike(String value) {
            addCriterion("cupdate_url like", value, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlNotLike(String value) {
            addCriterion("cupdate_url not like", value, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlIn(List<String> values) {
            addCriterion("cupdate_url in", values, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlNotIn(List<String> values) {
            addCriterion("cupdate_url not in", values, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlBetween(String value1, String value2) {
            addCriterion("cupdate_url between", value1, value2, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andCupdateUrlNotBetween(String value1, String value2) {
            addCriterion("cupdate_url not between", value1, value2, "cupdateUrl");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagIsNull() {
            addCriterion("aexamine_flag is null");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagIsNotNull() {
            addCriterion("aexamine_flag is not null");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagEqualTo(String value) {
            addCriterion("aexamine_flag =", value, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagNotEqualTo(String value) {
            addCriterion("aexamine_flag <>", value, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagGreaterThan(String value) {
            addCriterion("aexamine_flag >", value, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagGreaterThanOrEqualTo(String value) {
            addCriterion("aexamine_flag >=", value, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagLessThan(String value) {
            addCriterion("aexamine_flag <", value, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagLessThanOrEqualTo(String value) {
            addCriterion("aexamine_flag <=", value, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagLike(String value) {
            addCriterion("aexamine_flag like", value, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagNotLike(String value) {
            addCriterion("aexamine_flag not like", value, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagIn(List<String> values) {
            addCriterion("aexamine_flag in", values, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagNotIn(List<String> values) {
            addCriterion("aexamine_flag not in", values, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagBetween(String value1, String value2) {
            addCriterion("aexamine_flag between", value1, value2, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andAexamineFlagNotBetween(String value1, String value2) {
            addCriterion("aexamine_flag not between", value1, value2, "aexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagIsNull() {
            addCriterion("cexamine_flag is null");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagIsNotNull() {
            addCriterion("cexamine_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagEqualTo(String value) {
            addCriterion("cexamine_flag =", value, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagNotEqualTo(String value) {
            addCriterion("cexamine_flag <>", value, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagGreaterThan(String value) {
            addCriterion("cexamine_flag >", value, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagGreaterThanOrEqualTo(String value) {
            addCriterion("cexamine_flag >=", value, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagLessThan(String value) {
            addCriterion("cexamine_flag <", value, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagLessThanOrEqualTo(String value) {
            addCriterion("cexamine_flag <=", value, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagLike(String value) {
            addCriterion("cexamine_flag like", value, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagNotLike(String value) {
            addCriterion("cexamine_flag not like", value, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagIn(List<String> values) {
            addCriterion("cexamine_flag in", values, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagNotIn(List<String> values) {
            addCriterion("cexamine_flag not in", values, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagBetween(String value1, String value2) {
            addCriterion("cexamine_flag between", value1, value2, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andCexamineFlagNotBetween(String value1, String value2) {
            addCriterion("cexamine_flag not between", value1, value2, "cexamineFlag");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyIsNull() {
            addCriterion("private_key is null");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyIsNotNull() {
            addCriterion("private_key is not null");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyEqualTo(String value) {
            addCriterion("private_key =", value, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyNotEqualTo(String value) {
            addCriterion("private_key <>", value, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyGreaterThan(String value) {
            addCriterion("private_key >", value, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyGreaterThanOrEqualTo(String value) {
            addCriterion("private_key >=", value, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyLessThan(String value) {
            addCriterion("private_key <", value, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyLessThanOrEqualTo(String value) {
            addCriterion("private_key <=", value, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyLike(String value) {
            addCriterion("private_key like", value, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyNotLike(String value) {
            addCriterion("private_key not like", value, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyIn(List<String> values) {
            addCriterion("private_key in", values, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyNotIn(List<String> values) {
            addCriterion("private_key not in", values, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyBetween(String value1, String value2) {
            addCriterion("private_key between", value1, value2, "privateKey");
            return (Criteria) this;
        }

        public Criteria andPrivateKeyNotBetween(String value1, String value2) {
            addCriterion("private_key not between", value1, value2, "privateKey");
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
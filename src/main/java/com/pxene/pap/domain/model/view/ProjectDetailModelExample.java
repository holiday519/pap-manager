package com.pxene.pap.domain.model.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjectDetailModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProjectDetailModelExample() {
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

        public Criteria andKpiValIsNull() {
            addCriterion("kpi_val is null");
            return (Criteria) this;
        }

        public Criteria andKpiValIsNotNull() {
            addCriterion("kpi_val is not null");
            return (Criteria) this;
        }

        public Criteria andKpiValEqualTo(Integer value) {
            addCriterion("kpi_val =", value, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValNotEqualTo(Integer value) {
            addCriterion("kpi_val <>", value, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValGreaterThan(Integer value) {
            addCriterion("kpi_val >", value, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValGreaterThanOrEqualTo(Integer value) {
            addCriterion("kpi_val >=", value, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValLessThan(Integer value) {
            addCriterion("kpi_val <", value, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValLessThanOrEqualTo(Integer value) {
            addCriterion("kpi_val <=", value, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValIn(List<Integer> values) {
            addCriterion("kpi_val in", values, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValNotIn(List<Integer> values) {
            addCriterion("kpi_val not in", values, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValBetween(Integer value1, Integer value2) {
            addCriterion("kpi_val between", value1, value2, "kpiVal");
            return (Criteria) this;
        }

        public Criteria andKpiValNotBetween(Integer value1, Integer value2) {
            addCriterion("kpi_val not between", value1, value2, "kpiVal");
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

        public Criteria andAdvertiserNameIsNull() {
            addCriterion("advertiser_name is null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameIsNotNull() {
            addCriterion("advertiser_name is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameEqualTo(String value) {
            addCriterion("advertiser_name =", value, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameNotEqualTo(String value) {
            addCriterion("advertiser_name <>", value, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameGreaterThan(String value) {
            addCriterion("advertiser_name >", value, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameGreaterThanOrEqualTo(String value) {
            addCriterion("advertiser_name >=", value, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameLessThan(String value) {
            addCriterion("advertiser_name <", value, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameLessThanOrEqualTo(String value) {
            addCriterion("advertiser_name <=", value, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameLike(String value) {
            addCriterion("advertiser_name like", value, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameNotLike(String value) {
            addCriterion("advertiser_name not like", value, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameIn(List<String> values) {
            addCriterion("advertiser_name in", values, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameNotIn(List<String> values) {
            addCriterion("advertiser_name not in", values, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameBetween(String value1, String value2) {
            addCriterion("advertiser_name between", value1, value2, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andAdvertiserNameNotBetween(String value1, String value2) {
            addCriterion("advertiser_name not between", value1, value2, "advertiserName");
            return (Criteria) this;
        }

        public Criteria andIndustryIdIsNull() {
            addCriterion("industry_id is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIdIsNotNull() {
            addCriterion("industry_id is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryIdEqualTo(String value) {
            addCriterion("industry_id =", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotEqualTo(String value) {
            addCriterion("industry_id <>", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdGreaterThan(String value) {
            addCriterion("industry_id >", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdGreaterThanOrEqualTo(String value) {
            addCriterion("industry_id >=", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdLessThan(String value) {
            addCriterion("industry_id <", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdLessThanOrEqualTo(String value) {
            addCriterion("industry_id <=", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdLike(String value) {
            addCriterion("industry_id like", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotLike(String value) {
            addCriterion("industry_id not like", value, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdIn(List<String> values) {
            addCriterion("industry_id in", values, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotIn(List<String> values) {
            addCriterion("industry_id not in", values, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdBetween(String value1, String value2) {
            addCriterion("industry_id between", value1, value2, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryIdNotBetween(String value1, String value2) {
            addCriterion("industry_id not between", value1, value2, "industryId");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIsNull() {
            addCriterion("industry_name is null");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIsNotNull() {
            addCriterion("industry_name is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryNameEqualTo(String value) {
            addCriterion("industry_name =", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotEqualTo(String value) {
            addCriterion("industry_name <>", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameGreaterThan(String value) {
            addCriterion("industry_name >", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameGreaterThanOrEqualTo(String value) {
            addCriterion("industry_name >=", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLessThan(String value) {
            addCriterion("industry_name <", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLessThanOrEqualTo(String value) {
            addCriterion("industry_name <=", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameLike(String value) {
            addCriterion("industry_name like", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotLike(String value) {
            addCriterion("industry_name not like", value, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameIn(List<String> values) {
            addCriterion("industry_name in", values, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotIn(List<String> values) {
            addCriterion("industry_name not in", values, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameBetween(String value1, String value2) {
            addCriterion("industry_name between", value1, value2, "industryName");
            return (Criteria) this;
        }

        public Criteria andIndustryNameNotBetween(String value1, String value2) {
            addCriterion("industry_name not between", value1, value2, "industryName");
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

        public Criteria andWinAmountIsNull() {
            addCriterion("win_amount is null");
            return (Criteria) this;
        }

        public Criteria andWinAmountIsNotNull() {
            addCriterion("win_amount is not null");
            return (Criteria) this;
        }

        public Criteria andWinAmountEqualTo(BigDecimal value) {
            addCriterion("win_amount =", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountNotEqualTo(BigDecimal value) {
            addCriterion("win_amount <>", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountGreaterThan(BigDecimal value) {
            addCriterion("win_amount >", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("win_amount >=", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountLessThan(BigDecimal value) {
            addCriterion("win_amount <", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("win_amount <=", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountIn(List<BigDecimal> values) {
            addCriterion("win_amount in", values, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountNotIn(List<BigDecimal> values) {
            addCriterion("win_amount not in", values, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("win_amount between", value1, value2, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("win_amount not between", value1, value2, "winAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountIsNull() {
            addCriterion("impression_amount is null");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountIsNotNull() {
            addCriterion("impression_amount is not null");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountEqualTo(BigDecimal value) {
            addCriterion("impression_amount =", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountNotEqualTo(BigDecimal value) {
            addCriterion("impression_amount <>", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountGreaterThan(BigDecimal value) {
            addCriterion("impression_amount >", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("impression_amount >=", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountLessThan(BigDecimal value) {
            addCriterion("impression_amount <", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("impression_amount <=", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountIn(List<BigDecimal> values) {
            addCriterion("impression_amount in", values, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountNotIn(List<BigDecimal> values) {
            addCriterion("impression_amount not in", values, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("impression_amount between", value1, value2, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("impression_amount not between", value1, value2, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountIsNull() {
            addCriterion("click_amount is null");
            return (Criteria) this;
        }

        public Criteria andClickAmountIsNotNull() {
            addCriterion("click_amount is not null");
            return (Criteria) this;
        }

        public Criteria andClickAmountEqualTo(BigDecimal value) {
            addCriterion("click_amount =", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountNotEqualTo(BigDecimal value) {
            addCriterion("click_amount <>", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountGreaterThan(BigDecimal value) {
            addCriterion("click_amount >", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("click_amount >=", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountLessThan(BigDecimal value) {
            addCriterion("click_amount <", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("click_amount <=", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountIn(List<BigDecimal> values) {
            addCriterion("click_amount in", values, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountNotIn(List<BigDecimal> values) {
            addCriterion("click_amount not in", values, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("click_amount between", value1, value2, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("click_amount not between", value1, value2, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickRateIsNull() {
            addCriterion("click_rate is null");
            return (Criteria) this;
        }

        public Criteria andClickRateIsNotNull() {
            addCriterion("click_rate is not null");
            return (Criteria) this;
        }

        public Criteria andClickRateEqualTo(BigDecimal value) {
            addCriterion("click_rate =", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateNotEqualTo(BigDecimal value) {
            addCriterion("click_rate <>", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateGreaterThan(BigDecimal value) {
            addCriterion("click_rate >", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("click_rate >=", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateLessThan(BigDecimal value) {
            addCriterion("click_rate <", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("click_rate <=", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateIn(List<BigDecimal> values) {
            addCriterion("click_rate in", values, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateNotIn(List<BigDecimal> values) {
            addCriterion("click_rate not in", values, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("click_rate between", value1, value2, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("click_rate not between", value1, value2, "clickRate");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountIsNull() {
            addCriterion("arrival_amount is null");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountIsNotNull() {
            addCriterion("arrival_amount is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountEqualTo(BigDecimal value) {
            addCriterion("arrival_amount =", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountNotEqualTo(BigDecimal value) {
            addCriterion("arrival_amount <>", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountGreaterThan(BigDecimal value) {
            addCriterion("arrival_amount >", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("arrival_amount >=", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountLessThan(BigDecimal value) {
            addCriterion("arrival_amount <", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("arrival_amount <=", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountIn(List<BigDecimal> values) {
            addCriterion("arrival_amount in", values, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountNotIn(List<BigDecimal> values) {
            addCriterion("arrival_amount not in", values, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("arrival_amount between", value1, value2, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("arrival_amount not between", value1, value2, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountIsNull() {
            addCriterion("unique_amount is null");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountIsNotNull() {
            addCriterion("unique_amount is not null");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountEqualTo(BigDecimal value) {
            addCriterion("unique_amount =", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountNotEqualTo(BigDecimal value) {
            addCriterion("unique_amount <>", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountGreaterThan(BigDecimal value) {
            addCriterion("unique_amount >", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unique_amount >=", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountLessThan(BigDecimal value) {
            addCriterion("unique_amount <", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unique_amount <=", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountIn(List<BigDecimal> values) {
            addCriterion("unique_amount in", values, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountNotIn(List<BigDecimal> values) {
            addCriterion("unique_amount not in", values, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unique_amount between", value1, value2, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unique_amount not between", value1, value2, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andCostIsNull() {
            addCriterion("cost is null");
            return (Criteria) this;
        }

        public Criteria andCostIsNotNull() {
            addCriterion("cost is not null");
            return (Criteria) this;
        }

        public Criteria andCostEqualTo(BigDecimal value) {
            addCriterion("cost =", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotEqualTo(BigDecimal value) {
            addCriterion("cost <>", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostGreaterThan(BigDecimal value) {
            addCriterion("cost >", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cost >=", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostLessThan(BigDecimal value) {
            addCriterion("cost <", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cost <=", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostIn(List<BigDecimal> values) {
            addCriterion("cost in", values, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotIn(List<BigDecimal> values) {
            addCriterion("cost not in", values, "cost");
            return (Criteria) this;
        }

        public Criteria andCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cost between", value1, value2, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cost not between", value1, value2, "cost");
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
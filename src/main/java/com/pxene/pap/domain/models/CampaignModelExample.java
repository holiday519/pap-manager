package com.pxene.pap.domain.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CampaignModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CampaignModelExample() {
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

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(String value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(String value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(String value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(String value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(String value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLike(String value) {
            addCriterion("project_id like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotLike(String value) {
            addCriterion("project_id not like", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<String> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<String> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(String value1, String value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(String value1, String value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdIsNull() {
            addCriterion("landpage_id is null");
            return (Criteria) this;
        }

        public Criteria andLandpageIdIsNotNull() {
            addCriterion("landpage_id is not null");
            return (Criteria) this;
        }

        public Criteria andLandpageIdEqualTo(String value) {
            addCriterion("landpage_id =", value, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdNotEqualTo(String value) {
            addCriterion("landpage_id <>", value, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdGreaterThan(String value) {
            addCriterion("landpage_id >", value, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdGreaterThanOrEqualTo(String value) {
            addCriterion("landpage_id >=", value, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdLessThan(String value) {
            addCriterion("landpage_id <", value, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdLessThanOrEqualTo(String value) {
            addCriterion("landpage_id <=", value, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdLike(String value) {
            addCriterion("landpage_id like", value, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdNotLike(String value) {
            addCriterion("landpage_id not like", value, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdIn(List<String> values) {
            addCriterion("landpage_id in", values, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdNotIn(List<String> values) {
            addCriterion("landpage_id not in", values, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdBetween(String value1, String value2) {
            addCriterion("landpage_id between", value1, value2, "landpageId");
            return (Criteria) this;
        }

        public Criteria andLandpageIdNotBetween(String value1, String value2) {
            addCriterion("landpage_id not between", value1, value2, "landpageId");
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

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(Date value) {
            addCriterion("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(Date value) {
            addCriterion("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(Date value) {
            addCriterion("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(Date value) {
            addCriterion("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(Date value) {
            addCriterion("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<Date> values) {
            addCriterion("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<Date> values) {
            addCriterion("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(Date value1, Date value2) {
            addCriterion("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(Date value1, Date value2) {
            addCriterion("end_date not between", value1, value2, "endDate");
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

        public Criteria andFrequencyIdIsNull() {
            addCriterion("frequency_id is null");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdIsNotNull() {
            addCriterion("frequency_id is not null");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdEqualTo(String value) {
            addCriterion("frequency_id =", value, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdNotEqualTo(String value) {
            addCriterion("frequency_id <>", value, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdGreaterThan(String value) {
            addCriterion("frequency_id >", value, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdGreaterThanOrEqualTo(String value) {
            addCriterion("frequency_id >=", value, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdLessThan(String value) {
            addCriterion("frequency_id <", value, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdLessThanOrEqualTo(String value) {
            addCriterion("frequency_id <=", value, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdLike(String value) {
            addCriterion("frequency_id like", value, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdNotLike(String value) {
            addCriterion("frequency_id not like", value, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdIn(List<String> values) {
            addCriterion("frequency_id in", values, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdNotIn(List<String> values) {
            addCriterion("frequency_id not in", values, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdBetween(String value1, String value2) {
            addCriterion("frequency_id between", value1, value2, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andFrequencyIdNotBetween(String value1, String value2) {
            addCriterion("frequency_id not between", value1, value2, "frequencyId");
            return (Criteria) this;
        }

        public Criteria andUniformIsNull() {
            addCriterion("uniform is null");
            return (Criteria) this;
        }

        public Criteria andUniformIsNotNull() {
            addCriterion("uniform is not null");
            return (Criteria) this;
        }

        public Criteria andUniformEqualTo(String value) {
            addCriterion("uniform =", value, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformNotEqualTo(String value) {
            addCriterion("uniform <>", value, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformGreaterThan(String value) {
            addCriterion("uniform >", value, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformGreaterThanOrEqualTo(String value) {
            addCriterion("uniform >=", value, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformLessThan(String value) {
            addCriterion("uniform <", value, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformLessThanOrEqualTo(String value) {
            addCriterion("uniform <=", value, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformLike(String value) {
            addCriterion("uniform like", value, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformNotLike(String value) {
            addCriterion("uniform not like", value, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformIn(List<String> values) {
            addCriterion("uniform in", values, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformNotIn(List<String> values) {
            addCriterion("uniform not in", values, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformBetween(String value1, String value2) {
            addCriterion("uniform between", value1, value2, "uniform");
            return (Criteria) this;
        }

        public Criteria andUniformNotBetween(String value1, String value2) {
            addCriterion("uniform not between", value1, value2, "uniform");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdIsNull() {
            addCriterion("rule_group_id is null");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdIsNotNull() {
            addCriterion("rule_group_id is not null");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdEqualTo(String value) {
            addCriterion("rule_group_id =", value, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdNotEqualTo(String value) {
            addCriterion("rule_group_id <>", value, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdGreaterThan(String value) {
            addCriterion("rule_group_id >", value, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("rule_group_id >=", value, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdLessThan(String value) {
            addCriterion("rule_group_id <", value, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdLessThanOrEqualTo(String value) {
            addCriterion("rule_group_id <=", value, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdLike(String value) {
            addCriterion("rule_group_id like", value, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdNotLike(String value) {
            addCriterion("rule_group_id not like", value, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdIn(List<String> values) {
            addCriterion("rule_group_id in", values, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdNotIn(List<String> values) {
            addCriterion("rule_group_id not in", values, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdBetween(String value1, String value2) {
            addCriterion("rule_group_id between", value1, value2, "ruleGroupId");
            return (Criteria) this;
        }

        public Criteria andRuleGroupIdNotBetween(String value1, String value2) {
            addCriterion("rule_group_id not between", value1, value2, "ruleGroupId");
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
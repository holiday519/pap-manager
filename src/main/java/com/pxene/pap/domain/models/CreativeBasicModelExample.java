package com.pxene.pap.domain.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreativeBasicModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CreativeBasicModelExample() {
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

        public Criteria andAdxIdIsNull() {
            addCriterion("adx_id is null");
            return (Criteria) this;
        }

        public Criteria andAdxIdIsNotNull() {
            addCriterion("adx_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdxIdEqualTo(String value) {
            addCriterion("adx_id =", value, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdNotEqualTo(String value) {
            addCriterion("adx_id <>", value, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdGreaterThan(String value) {
            addCriterion("adx_id >", value, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdGreaterThanOrEqualTo(String value) {
            addCriterion("adx_id >=", value, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdLessThan(String value) {
            addCriterion("adx_id <", value, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdLessThanOrEqualTo(String value) {
            addCriterion("adx_id <=", value, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdLike(String value) {
            addCriterion("adx_id like", value, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdNotLike(String value) {
            addCriterion("adx_id not like", value, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdIn(List<String> values) {
            addCriterion("adx_id in", values, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdNotIn(List<String> values) {
            addCriterion("adx_id not in", values, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdBetween(String value1, String value2) {
            addCriterion("adx_id between", value1, value2, "adxId");
            return (Criteria) this;
        }

        public Criteria andAdxIdNotBetween(String value1, String value2) {
            addCriterion("adx_id not between", value1, value2, "adxId");
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

        public Criteria andCampaignIdIsNull() {
            addCriterion("campaign_id is null");
            return (Criteria) this;
        }

        public Criteria andCampaignIdIsNotNull() {
            addCriterion("campaign_id is not null");
            return (Criteria) this;
        }

        public Criteria andCampaignIdEqualTo(String value) {
            addCriterion("campaign_id =", value, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdNotEqualTo(String value) {
            addCriterion("campaign_id <>", value, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdGreaterThan(String value) {
            addCriterion("campaign_id >", value, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdGreaterThanOrEqualTo(String value) {
            addCriterion("campaign_id >=", value, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdLessThan(String value) {
            addCriterion("campaign_id <", value, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdLessThanOrEqualTo(String value) {
            addCriterion("campaign_id <=", value, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdLike(String value) {
            addCriterion("campaign_id like", value, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdNotLike(String value) {
            addCriterion("campaign_id not like", value, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdIn(List<String> values) {
            addCriterion("campaign_id in", values, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdNotIn(List<String> values) {
            addCriterion("campaign_id not in", values, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdBetween(String value1, String value2) {
            addCriterion("campaign_id between", value1, value2, "campaignId");
            return (Criteria) this;
        }

        public Criteria andCampaignIdNotBetween(String value1, String value2) {
            addCriterion("campaign_id not between", value1, value2, "campaignId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(String value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(String value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(String value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(String value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLike(String value) {
            addCriterion("template_id like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotLike(String value) {
            addCriterion("template_id not like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<String> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<String> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(String value1, String value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(String value1, String value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(String value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(String value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(String value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(String value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(String value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(String value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLike(String value) {
            addCriterion("app_id like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotLike(String value) {
            addCriterion("app_id not like", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<String> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<String> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(String value1, String value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(String value1, String value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("app_name not between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAdxNameIsNull() {
            addCriterion("adx_name is null");
            return (Criteria) this;
        }

        public Criteria andAdxNameIsNotNull() {
            addCriterion("adx_name is not null");
            return (Criteria) this;
        }

        public Criteria andAdxNameEqualTo(String value) {
            addCriterion("adx_name =", value, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameNotEqualTo(String value) {
            addCriterion("adx_name <>", value, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameGreaterThan(String value) {
            addCriterion("adx_name >", value, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameGreaterThanOrEqualTo(String value) {
            addCriterion("adx_name >=", value, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameLessThan(String value) {
            addCriterion("adx_name <", value, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameLessThanOrEqualTo(String value) {
            addCriterion("adx_name <=", value, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameLike(String value) {
            addCriterion("adx_name like", value, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameNotLike(String value) {
            addCriterion("adx_name not like", value, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameIn(List<String> values) {
            addCriterion("adx_name in", values, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameNotIn(List<String> values) {
            addCriterion("adx_name not in", values, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameBetween(String value1, String value2) {
            addCriterion("adx_name between", value1, value2, "adxName");
            return (Criteria) this;
        }

        public Criteria andAdxNameNotBetween(String value1, String value2) {
            addCriterion("adx_name not between", value1, value2, "adxName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameIsNull() {
            addCriterion("campaign_name is null");
            return (Criteria) this;
        }

        public Criteria andCampaignNameIsNotNull() {
            addCriterion("campaign_name is not null");
            return (Criteria) this;
        }

        public Criteria andCampaignNameEqualTo(String value) {
            addCriterion("campaign_name =", value, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameNotEqualTo(String value) {
            addCriterion("campaign_name <>", value, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameGreaterThan(String value) {
            addCriterion("campaign_name >", value, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameGreaterThanOrEqualTo(String value) {
            addCriterion("campaign_name >=", value, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameLessThan(String value) {
            addCriterion("campaign_name <", value, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameLessThanOrEqualTo(String value) {
            addCriterion("campaign_name <=", value, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameLike(String value) {
            addCriterion("campaign_name like", value, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameNotLike(String value) {
            addCriterion("campaign_name not like", value, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameIn(List<String> values) {
            addCriterion("campaign_name in", values, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameNotIn(List<String> values) {
            addCriterion("campaign_name not in", values, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameBetween(String value1, String value2) {
            addCriterion("campaign_name between", value1, value2, "campaignName");
            return (Criteria) this;
        }

        public Criteria andCampaignNameNotBetween(String value1, String value2) {
            addCriterion("campaign_name not between", value1, value2, "campaignName");
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

        public Criteria andCampaignStartDateIsNull() {
            addCriterion("campaign_start_date is null");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateIsNotNull() {
            addCriterion("campaign_start_date is not null");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateEqualTo(Date value) {
            addCriterion("campaign_start_date =", value, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateNotEqualTo(Date value) {
            addCriterion("campaign_start_date <>", value, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateGreaterThan(Date value) {
            addCriterion("campaign_start_date >", value, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("campaign_start_date >=", value, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateLessThan(Date value) {
            addCriterion("campaign_start_date <", value, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateLessThanOrEqualTo(Date value) {
            addCriterion("campaign_start_date <=", value, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateIn(List<Date> values) {
            addCriterion("campaign_start_date in", values, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateNotIn(List<Date> values) {
            addCriterion("campaign_start_date not in", values, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateBetween(Date value1, Date value2) {
            addCriterion("campaign_start_date between", value1, value2, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStartDateNotBetween(Date value1, Date value2) {
            addCriterion("campaign_start_date not between", value1, value2, "campaignStartDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateIsNull() {
            addCriterion("campaign_end_date is null");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateIsNotNull() {
            addCriterion("campaign_end_date is not null");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateEqualTo(Date value) {
            addCriterion("campaign_end_date =", value, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateNotEqualTo(Date value) {
            addCriterion("campaign_end_date <>", value, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateGreaterThan(Date value) {
            addCriterion("campaign_end_date >", value, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateGreaterThanOrEqualTo(Date value) {
            addCriterion("campaign_end_date >=", value, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateLessThan(Date value) {
            addCriterion("campaign_end_date <", value, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateLessThanOrEqualTo(Date value) {
            addCriterion("campaign_end_date <=", value, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateIn(List<Date> values) {
            addCriterion("campaign_end_date in", values, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateNotIn(List<Date> values) {
            addCriterion("campaign_end_date not in", values, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateBetween(Date value1, Date value2) {
            addCriterion("campaign_end_date between", value1, value2, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignEndDateNotBetween(Date value1, Date value2) {
            addCriterion("campaign_end_date not between", value1, value2, "campaignEndDate");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusIsNull() {
            addCriterion("campaign_status is null");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusIsNotNull() {
            addCriterion("campaign_status is not null");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusEqualTo(String value) {
            addCriterion("campaign_status =", value, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusNotEqualTo(String value) {
            addCriterion("campaign_status <>", value, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusGreaterThan(String value) {
            addCriterion("campaign_status >", value, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusGreaterThanOrEqualTo(String value) {
            addCriterion("campaign_status >=", value, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusLessThan(String value) {
            addCriterion("campaign_status <", value, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusLessThanOrEqualTo(String value) {
            addCriterion("campaign_status <=", value, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusLike(String value) {
            addCriterion("campaign_status like", value, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusNotLike(String value) {
            addCriterion("campaign_status not like", value, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusIn(List<String> values) {
            addCriterion("campaign_status in", values, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusNotIn(List<String> values) {
            addCriterion("campaign_status not in", values, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusBetween(String value1, String value2) {
            addCriterion("campaign_status between", value1, value2, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andCampaignStatusNotBetween(String value1, String value2) {
            addCriterion("campaign_status not between", value1, value2, "campaignStatus");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNull() {
            addCriterion("project_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNotNull() {
            addCriterion("project_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNameEqualTo(String value) {
            addCriterion("project_name =", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotEqualTo(String value) {
            addCriterion("project_name <>", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThan(String value) {
            addCriterion("project_name >", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_name >=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThan(String value) {
            addCriterion("project_name <", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThanOrEqualTo(String value) {
            addCriterion("project_name <=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLike(String value) {
            addCriterion("project_name like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotLike(String value) {
            addCriterion("project_name not like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameIn(List<String> values) {
            addCriterion("project_name in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotIn(List<String> values) {
            addCriterion("project_name not in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameBetween(String value1, String value2) {
            addCriterion("project_name between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotBetween(String value1, String value2) {
            addCriterion("project_name not between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetIsNull() {
            addCriterion("project_total_budget is null");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetIsNotNull() {
            addCriterion("project_total_budget is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetEqualTo(Integer value) {
            addCriterion("project_total_budget =", value, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetNotEqualTo(Integer value) {
            addCriterion("project_total_budget <>", value, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetGreaterThan(Integer value) {
            addCriterion("project_total_budget >", value, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_total_budget >=", value, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetLessThan(Integer value) {
            addCriterion("project_total_budget <", value, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetLessThanOrEqualTo(Integer value) {
            addCriterion("project_total_budget <=", value, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetIn(List<Integer> values) {
            addCriterion("project_total_budget in", values, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetNotIn(List<Integer> values) {
            addCriterion("project_total_budget not in", values, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetBetween(Integer value1, Integer value2) {
            addCriterion("project_total_budget between", value1, value2, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectTotalBudgetNotBetween(Integer value1, Integer value2) {
            addCriterion("project_total_budget not between", value1, value2, "projectTotalBudget");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIsNull() {
            addCriterion("project_status is null");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIsNotNull() {
            addCriterion("project_status is not null");
            return (Criteria) this;
        }

        public Criteria andProjectStatusEqualTo(String value) {
            addCriterion("project_status =", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotEqualTo(String value) {
            addCriterion("project_status <>", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusGreaterThan(String value) {
            addCriterion("project_status >", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusGreaterThanOrEqualTo(String value) {
            addCriterion("project_status >=", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLessThan(String value) {
            addCriterion("project_status <", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLessThanOrEqualTo(String value) {
            addCriterion("project_status <=", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLike(String value) {
            addCriterion("project_status like", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotLike(String value) {
            addCriterion("project_status not like", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIn(List<String> values) {
            addCriterion("project_status in", values, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotIn(List<String> values) {
            addCriterion("project_status not in", values, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusBetween(String value1, String value2) {
            addCriterion("project_status between", value1, value2, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotBetween(String value1, String value2) {
            addCriterion("project_status not between", value1, value2, "projectStatus");
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
        
        /***********************************Johnny add begin*****************************************/
        public Criteria andCreativeIdsLike(String value) {
        	addCriterion("creative_ids like", value, "creativeIds");
        	return (Criteria) this;
        }
        /***********************************Johnny add end*****************************************/
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
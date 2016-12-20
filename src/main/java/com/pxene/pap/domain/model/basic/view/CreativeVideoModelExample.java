package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreativeVideoModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CreativeVideoModelExample() {
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

        public Criteria andMapIdIsNull() {
            addCriterion("map_id is null");
            return (Criteria) this;
        }

        public Criteria andMapIdIsNotNull() {
            addCriterion("map_id is not null");
            return (Criteria) this;
        }

        public Criteria andMapIdEqualTo(String value) {
            addCriterion("map_id =", value, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdNotEqualTo(String value) {
            addCriterion("map_id <>", value, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdGreaterThan(String value) {
            addCriterion("map_id >", value, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdGreaterThanOrEqualTo(String value) {
            addCriterion("map_id >=", value, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdLessThan(String value) {
            addCriterion("map_id <", value, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdLessThanOrEqualTo(String value) {
            addCriterion("map_id <=", value, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdLike(String value) {
            addCriterion("map_id like", value, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdNotLike(String value) {
            addCriterion("map_id not like", value, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdIn(List<String> values) {
            addCriterion("map_id in", values, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdNotIn(List<String> values) {
            addCriterion("map_id not in", values, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdBetween(String value1, String value2) {
            addCriterion("map_id between", value1, value2, "mapId");
            return (Criteria) this;
        }

        public Criteria andMapIdNotBetween(String value1, String value2) {
            addCriterion("map_id not between", value1, value2, "mapId");
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

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andFtypeIsNull() {
            addCriterion("ftype is null");
            return (Criteria) this;
        }

        public Criteria andFtypeIsNotNull() {
            addCriterion("ftype is not null");
            return (Criteria) this;
        }

        public Criteria andFtypeEqualTo(String value) {
            addCriterion("ftype =", value, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeNotEqualTo(String value) {
            addCriterion("ftype <>", value, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeGreaterThan(String value) {
            addCriterion("ftype >", value, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ftype >=", value, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeLessThan(String value) {
            addCriterion("ftype <", value, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeLessThanOrEqualTo(String value) {
            addCriterion("ftype <=", value, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeLike(String value) {
            addCriterion("ftype like", value, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeNotLike(String value) {
            addCriterion("ftype not like", value, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeIn(List<String> values) {
            addCriterion("ftype in", values, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeNotIn(List<String> values) {
            addCriterion("ftype not in", values, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeBetween(String value1, String value2) {
            addCriterion("ftype between", value1, value2, "ftype");
            return (Criteria) this;
        }

        public Criteria andFtypeNotBetween(String value1, String value2) {
            addCriterion("ftype not between", value1, value2, "ftype");
            return (Criteria) this;
        }

        public Criteria andCtypeIsNull() {
            addCriterion("ctype is null");
            return (Criteria) this;
        }

        public Criteria andCtypeIsNotNull() {
            addCriterion("ctype is not null");
            return (Criteria) this;
        }

        public Criteria andCtypeEqualTo(String value) {
            addCriterion("ctype =", value, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeNotEqualTo(String value) {
            addCriterion("ctype <>", value, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeGreaterThan(String value) {
            addCriterion("ctype >", value, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeGreaterThanOrEqualTo(String value) {
            addCriterion("ctype >=", value, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeLessThan(String value) {
            addCriterion("ctype <", value, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeLessThanOrEqualTo(String value) {
            addCriterion("ctype <=", value, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeLike(String value) {
            addCriterion("ctype like", value, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeNotLike(String value) {
            addCriterion("ctype not like", value, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeIn(List<String> values) {
            addCriterion("ctype in", values, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeNotIn(List<String> values) {
            addCriterion("ctype not in", values, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeBetween(String value1, String value2) {
            addCriterion("ctype between", value1, value2, "ctype");
            return (Criteria) this;
        }

        public Criteria andCtypeNotBetween(String value1, String value2) {
            addCriterion("ctype not between", value1, value2, "ctype");
            return (Criteria) this;
        }

        public Criteria andBundleIsNull() {
            addCriterion("bundle is null");
            return (Criteria) this;
        }

        public Criteria andBundleIsNotNull() {
            addCriterion("bundle is not null");
            return (Criteria) this;
        }

        public Criteria andBundleEqualTo(String value) {
            addCriterion("bundle =", value, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleNotEqualTo(String value) {
            addCriterion("bundle <>", value, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleGreaterThan(String value) {
            addCriterion("bundle >", value, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleGreaterThanOrEqualTo(String value) {
            addCriterion("bundle >=", value, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleLessThan(String value) {
            addCriterion("bundle <", value, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleLessThanOrEqualTo(String value) {
            addCriterion("bundle <=", value, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleLike(String value) {
            addCriterion("bundle like", value, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleNotLike(String value) {
            addCriterion("bundle not like", value, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleIn(List<String> values) {
            addCriterion("bundle in", values, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleNotIn(List<String> values) {
            addCriterion("bundle not in", values, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleBetween(String value1, String value2) {
            addCriterion("bundle between", value1, value2, "bundle");
            return (Criteria) this;
        }

        public Criteria andBundleNotBetween(String value1, String value2) {
            addCriterion("bundle not between", value1, value2, "bundle");
            return (Criteria) this;
        }

        public Criteria andApkNameIsNull() {
            addCriterion("apk_name is null");
            return (Criteria) this;
        }

        public Criteria andApkNameIsNotNull() {
            addCriterion("apk_name is not null");
            return (Criteria) this;
        }

        public Criteria andApkNameEqualTo(String value) {
            addCriterion("apk_name =", value, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameNotEqualTo(String value) {
            addCriterion("apk_name <>", value, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameGreaterThan(String value) {
            addCriterion("apk_name >", value, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameGreaterThanOrEqualTo(String value) {
            addCriterion("apk_name >=", value, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameLessThan(String value) {
            addCriterion("apk_name <", value, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameLessThanOrEqualTo(String value) {
            addCriterion("apk_name <=", value, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameLike(String value) {
            addCriterion("apk_name like", value, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameNotLike(String value) {
            addCriterion("apk_name not like", value, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameIn(List<String> values) {
            addCriterion("apk_name in", values, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameNotIn(List<String> values) {
            addCriterion("apk_name not in", values, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameBetween(String value1, String value2) {
            addCriterion("apk_name between", value1, value2, "apkName");
            return (Criteria) this;
        }

        public Criteria andApkNameNotBetween(String value1, String value2) {
            addCriterion("apk_name not between", value1, value2, "apkName");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionIsNull() {
            addCriterion("app_description is null");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionIsNotNull() {
            addCriterion("app_description is not null");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionEqualTo(String value) {
            addCriterion("app_description =", value, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionNotEqualTo(String value) {
            addCriterion("app_description <>", value, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionGreaterThan(String value) {
            addCriterion("app_description >", value, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("app_description >=", value, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionLessThan(String value) {
            addCriterion("app_description <", value, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionLessThanOrEqualTo(String value) {
            addCriterion("app_description <=", value, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionLike(String value) {
            addCriterion("app_description like", value, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionNotLike(String value) {
            addCriterion("app_description not like", value, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionIn(List<String> values) {
            addCriterion("app_description in", values, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionNotIn(List<String> values) {
            addCriterion("app_description not in", values, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionBetween(String value1, String value2) {
            addCriterion("app_description between", value1, value2, "appDescription");
            return (Criteria) this;
        }

        public Criteria andAppDescriptionNotBetween(String value1, String value2) {
            addCriterion("app_description not between", value1, value2, "appDescription");
            return (Criteria) this;
        }

        public Criteria andWIsNull() {
            addCriterion("w is null");
            return (Criteria) this;
        }

        public Criteria andWIsNotNull() {
            addCriterion("w is not null");
            return (Criteria) this;
        }

        public Criteria andWEqualTo(Integer value) {
            addCriterion("w =", value, "w");
            return (Criteria) this;
        }

        public Criteria andWNotEqualTo(Integer value) {
            addCriterion("w <>", value, "w");
            return (Criteria) this;
        }

        public Criteria andWGreaterThan(Integer value) {
            addCriterion("w >", value, "w");
            return (Criteria) this;
        }

        public Criteria andWGreaterThanOrEqualTo(Integer value) {
            addCriterion("w >=", value, "w");
            return (Criteria) this;
        }

        public Criteria andWLessThan(Integer value) {
            addCriterion("w <", value, "w");
            return (Criteria) this;
        }

        public Criteria andWLessThanOrEqualTo(Integer value) {
            addCriterion("w <=", value, "w");
            return (Criteria) this;
        }

        public Criteria andWIn(List<Integer> values) {
            addCriterion("w in", values, "w");
            return (Criteria) this;
        }

        public Criteria andWNotIn(List<Integer> values) {
            addCriterion("w not in", values, "w");
            return (Criteria) this;
        }

        public Criteria andWBetween(Integer value1, Integer value2) {
            addCriterion("w between", value1, value2, "w");
            return (Criteria) this;
        }

        public Criteria andWNotBetween(Integer value1, Integer value2) {
            addCriterion("w not between", value1, value2, "w");
            return (Criteria) this;
        }

        public Criteria andHIsNull() {
            addCriterion("h is null");
            return (Criteria) this;
        }

        public Criteria andHIsNotNull() {
            addCriterion("h is not null");
            return (Criteria) this;
        }

        public Criteria andHEqualTo(Integer value) {
            addCriterion("h =", value, "h");
            return (Criteria) this;
        }

        public Criteria andHNotEqualTo(Integer value) {
            addCriterion("h <>", value, "h");
            return (Criteria) this;
        }

        public Criteria andHGreaterThan(Integer value) {
            addCriterion("h >", value, "h");
            return (Criteria) this;
        }

        public Criteria andHGreaterThanOrEqualTo(Integer value) {
            addCriterion("h >=", value, "h");
            return (Criteria) this;
        }

        public Criteria andHLessThan(Integer value) {
            addCriterion("h <", value, "h");
            return (Criteria) this;
        }

        public Criteria andHLessThanOrEqualTo(Integer value) {
            addCriterion("h <=", value, "h");
            return (Criteria) this;
        }

        public Criteria andHIn(List<Integer> values) {
            addCriterion("h in", values, "h");
            return (Criteria) this;
        }

        public Criteria andHNotIn(List<Integer> values) {
            addCriterion("h not in", values, "h");
            return (Criteria) this;
        }

        public Criteria andHBetween(Integer value1, Integer value2) {
            addCriterion("h between", value1, value2, "h");
            return (Criteria) this;
        }

        public Criteria andHNotBetween(Integer value1, Integer value2) {
            addCriterion("h not between", value1, value2, "h");
            return (Criteria) this;
        }

        public Criteria andCurlIsNull() {
            addCriterion("curl is null");
            return (Criteria) this;
        }

        public Criteria andCurlIsNotNull() {
            addCriterion("curl is not null");
            return (Criteria) this;
        }

        public Criteria andCurlEqualTo(String value) {
            addCriterion("curl =", value, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlNotEqualTo(String value) {
            addCriterion("curl <>", value, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlGreaterThan(String value) {
            addCriterion("curl >", value, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlGreaterThanOrEqualTo(String value) {
            addCriterion("curl >=", value, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlLessThan(String value) {
            addCriterion("curl <", value, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlLessThanOrEqualTo(String value) {
            addCriterion("curl <=", value, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlLike(String value) {
            addCriterion("curl like", value, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlNotLike(String value) {
            addCriterion("curl not like", value, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlIn(List<String> values) {
            addCriterion("curl in", values, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlNotIn(List<String> values) {
            addCriterion("curl not in", values, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlBetween(String value1, String value2) {
            addCriterion("curl between", value1, value2, "curl");
            return (Criteria) this;
        }

        public Criteria andCurlNotBetween(String value1, String value2) {
            addCriterion("curl not between", value1, value2, "curl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlIsNull() {
            addCriterion("landing_url is null");
            return (Criteria) this;
        }

        public Criteria andLandingUrlIsNotNull() {
            addCriterion("landing_url is not null");
            return (Criteria) this;
        }

        public Criteria andLandingUrlEqualTo(String value) {
            addCriterion("landing_url =", value, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlNotEqualTo(String value) {
            addCriterion("landing_url <>", value, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlGreaterThan(String value) {
            addCriterion("landing_url >", value, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlGreaterThanOrEqualTo(String value) {
            addCriterion("landing_url >=", value, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlLessThan(String value) {
            addCriterion("landing_url <", value, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlLessThanOrEqualTo(String value) {
            addCriterion("landing_url <=", value, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlLike(String value) {
            addCriterion("landing_url like", value, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlNotLike(String value) {
            addCriterion("landing_url not like", value, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlIn(List<String> values) {
            addCriterion("landing_url in", values, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlNotIn(List<String> values) {
            addCriterion("landing_url not in", values, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlBetween(String value1, String value2) {
            addCriterion("landing_url between", value1, value2, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andLandingUrlNotBetween(String value1, String value2) {
            addCriterion("landing_url not between", value1, value2, "landingUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNull() {
            addCriterion("download_url is null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIsNotNull() {
            addCriterion("download_url is not null");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlEqualTo(String value) {
            addCriterion("download_url =", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotEqualTo(String value) {
            addCriterion("download_url <>", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThan(String value) {
            addCriterion("download_url >", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlGreaterThanOrEqualTo(String value) {
            addCriterion("download_url >=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThan(String value) {
            addCriterion("download_url <", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLessThanOrEqualTo(String value) {
            addCriterion("download_url <=", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlLike(String value) {
            addCriterion("download_url like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotLike(String value) {
            addCriterion("download_url not like", value, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlIn(List<String> values) {
            addCriterion("download_url in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotIn(List<String> values) {
            addCriterion("download_url not in", values, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlBetween(String value1, String value2) {
            addCriterion("download_url between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andDownloadUrlNotBetween(String value1, String value2) {
            addCriterion("download_url not between", value1, value2, "downloadUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlIsNull() {
            addCriterion("source_url is null");
            return (Criteria) this;
        }

        public Criteria andSourceUrlIsNotNull() {
            addCriterion("source_url is not null");
            return (Criteria) this;
        }

        public Criteria andSourceUrlEqualTo(String value) {
            addCriterion("source_url =", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlNotEqualTo(String value) {
            addCriterion("source_url <>", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlGreaterThan(String value) {
            addCriterion("source_url >", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlGreaterThanOrEqualTo(String value) {
            addCriterion("source_url >=", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlLessThan(String value) {
            addCriterion("source_url <", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlLessThanOrEqualTo(String value) {
            addCriterion("source_url <=", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlLike(String value) {
            addCriterion("source_url like", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlNotLike(String value) {
            addCriterion("source_url not like", value, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlIn(List<String> values) {
            addCriterion("source_url in", values, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlNotIn(List<String> values) {
            addCriterion("source_url not in", values, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlBetween(String value1, String value2) {
            addCriterion("source_url between", value1, value2, "sourceUrl");
            return (Criteria) this;
        }

        public Criteria andSourceUrlNotBetween(String value1, String value2) {
            addCriterion("source_url not between", value1, value2, "sourceUrl");
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
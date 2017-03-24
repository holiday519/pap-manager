package com.pxene.pap.domain.models.view;

import java.util.ArrayList;
import java.util.List;

public class CreativeInfoflowModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CreativeInfoflowModelExample() {
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

        public Criteria andCreativeIdIsNull() {
            addCriterion("creative_id is null");
            return (Criteria) this;
        }

        public Criteria andCreativeIdIsNotNull() {
            addCriterion("creative_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreativeIdEqualTo(String value) {
            addCriterion("creative_id =", value, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdNotEqualTo(String value) {
            addCriterion("creative_id <>", value, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdGreaterThan(String value) {
            addCriterion("creative_id >", value, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdGreaterThanOrEqualTo(String value) {
            addCriterion("creative_id >=", value, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdLessThan(String value) {
            addCriterion("creative_id <", value, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdLessThanOrEqualTo(String value) {
            addCriterion("creative_id <=", value, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdLike(String value) {
            addCriterion("creative_id like", value, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdNotLike(String value) {
            addCriterion("creative_id not like", value, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdIn(List<String> values) {
            addCriterion("creative_id in", values, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdNotIn(List<String> values) {
            addCriterion("creative_id not in", values, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdBetween(String value1, String value2) {
            addCriterion("creative_id between", value1, value2, "creativeId");
            return (Criteria) this;
        }

        public Criteria andCreativeIdNotBetween(String value1, String value2) {
            addCriterion("creative_id not between", value1, value2, "creativeId");
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

        public Criteria andLandpageUrlIsNull() {
            addCriterion("landpage_url is null");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlIsNotNull() {
            addCriterion("landpage_url is not null");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlEqualTo(String value) {
            addCriterion("landpage_url =", value, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlNotEqualTo(String value) {
            addCriterion("landpage_url <>", value, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlGreaterThan(String value) {
            addCriterion("landpage_url >", value, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("landpage_url >=", value, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlLessThan(String value) {
            addCriterion("landpage_url <", value, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlLessThanOrEqualTo(String value) {
            addCriterion("landpage_url <=", value, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlLike(String value) {
            addCriterion("landpage_url like", value, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlNotLike(String value) {
            addCriterion("landpage_url not like", value, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlIn(List<String> values) {
            addCriterion("landpage_url in", values, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlNotIn(List<String> values) {
            addCriterion("landpage_url not in", values, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlBetween(String value1, String value2) {
            addCriterion("landpage_url between", value1, value2, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andLandpageUrlNotBetween(String value1, String value2) {
            addCriterion("landpage_url not between", value1, value2, "landpageUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlIsNull() {
            addCriterion("monitor_url is null");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlIsNotNull() {
            addCriterion("monitor_url is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlEqualTo(String value) {
            addCriterion("monitor_url =", value, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlNotEqualTo(String value) {
            addCriterion("monitor_url <>", value, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlGreaterThan(String value) {
            addCriterion("monitor_url >", value, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlGreaterThanOrEqualTo(String value) {
            addCriterion("monitor_url >=", value, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlLessThan(String value) {
            addCriterion("monitor_url <", value, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlLessThanOrEqualTo(String value) {
            addCriterion("monitor_url <=", value, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlLike(String value) {
            addCriterion("monitor_url like", value, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlNotLike(String value) {
            addCriterion("monitor_url not like", value, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlIn(List<String> values) {
            addCriterion("monitor_url in", values, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlNotIn(List<String> values) {
            addCriterion("monitor_url not in", values, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlBetween(String value1, String value2) {
            addCriterion("monitor_url between", value1, value2, "monitorUrl");
            return (Criteria) this;
        }

        public Criteria andMonitorUrlNotBetween(String value1, String value2) {
            addCriterion("monitor_url not between", value1, value2, "monitorUrl");
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
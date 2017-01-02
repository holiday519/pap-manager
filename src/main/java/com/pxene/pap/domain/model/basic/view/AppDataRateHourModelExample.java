package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppDataRateHourModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppDataRateHourModelExample() {
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

        public Criteria andDatetimeIsNull() {
            addCriterion("datetime is null");
            return (Criteria) this;
        }

        public Criteria andDatetimeIsNotNull() {
            addCriterion("datetime is not null");
            return (Criteria) this;
        }

        public Criteria andDatetimeEqualTo(Date value) {
            addCriterion("datetime =", value, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeNotEqualTo(Date value) {
            addCriterion("datetime <>", value, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeGreaterThan(Date value) {
            addCriterion("datetime >", value, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("datetime >=", value, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeLessThan(Date value) {
            addCriterion("datetime <", value, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeLessThanOrEqualTo(Date value) {
            addCriterion("datetime <=", value, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeIn(List<Date> values) {
            addCriterion("datetime in", values, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeNotIn(List<Date> values) {
            addCriterion("datetime not in", values, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeBetween(Date value1, Date value2) {
            addCriterion("datetime between", value1, value2, "datetime");
            return (Criteria) this;
        }

        public Criteria andDatetimeNotBetween(Date value1, Date value2) {
            addCriterion("datetime not between", value1, value2, "datetime");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumIsNull() {
            addCriterion("bid_amount_sum is null");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumIsNotNull() {
            addCriterion("bid_amount_sum is not null");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumEqualTo(BigDecimal value) {
            addCriterion("bid_amount_sum =", value, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumNotEqualTo(BigDecimal value) {
            addCriterion("bid_amount_sum <>", value, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumGreaterThan(BigDecimal value) {
            addCriterion("bid_amount_sum >", value, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bid_amount_sum >=", value, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumLessThan(BigDecimal value) {
            addCriterion("bid_amount_sum <", value, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bid_amount_sum <=", value, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumIn(List<BigDecimal> values) {
            addCriterion("bid_amount_sum in", values, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumNotIn(List<BigDecimal> values) {
            addCriterion("bid_amount_sum not in", values, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bid_amount_sum between", value1, value2, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andBidAmountSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bid_amount_sum not between", value1, value2, "bidAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumIsNull() {
            addCriterion("win_amount_sum is null");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumIsNotNull() {
            addCriterion("win_amount_sum is not null");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumEqualTo(BigDecimal value) {
            addCriterion("win_amount_sum =", value, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumNotEqualTo(BigDecimal value) {
            addCriterion("win_amount_sum <>", value, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumGreaterThan(BigDecimal value) {
            addCriterion("win_amount_sum >", value, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("win_amount_sum >=", value, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumLessThan(BigDecimal value) {
            addCriterion("win_amount_sum <", value, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("win_amount_sum <=", value, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumIn(List<BigDecimal> values) {
            addCriterion("win_amount_sum in", values, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumNotIn(List<BigDecimal> values) {
            addCriterion("win_amount_sum not in", values, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("win_amount_sum between", value1, value2, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinAmountSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("win_amount_sum not between", value1, value2, "winAmountSum");
            return (Criteria) this;
        }

        public Criteria andWinRateIsNull() {
            addCriterion("win_rate is null");
            return (Criteria) this;
        }

        public Criteria andWinRateIsNotNull() {
            addCriterion("win_rate is not null");
            return (Criteria) this;
        }

        public Criteria andWinRateEqualTo(BigDecimal value) {
            addCriterion("win_rate =", value, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateNotEqualTo(BigDecimal value) {
            addCriterion("win_rate <>", value, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateGreaterThan(BigDecimal value) {
            addCriterion("win_rate >", value, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("win_rate >=", value, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateLessThan(BigDecimal value) {
            addCriterion("win_rate <", value, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("win_rate <=", value, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateIn(List<BigDecimal> values) {
            addCriterion("win_rate in", values, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateNotIn(List<BigDecimal> values) {
            addCriterion("win_rate not in", values, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("win_rate between", value1, value2, "winRate");
            return (Criteria) this;
        }

        public Criteria andWinRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("win_rate not between", value1, value2, "winRate");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumIsNull() {
            addCriterion("impression_amount_sum is null");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumIsNotNull() {
            addCriterion("impression_amount_sum is not null");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumEqualTo(BigDecimal value) {
            addCriterion("impression_amount_sum =", value, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumNotEqualTo(BigDecimal value) {
            addCriterion("impression_amount_sum <>", value, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumGreaterThan(BigDecimal value) {
            addCriterion("impression_amount_sum >", value, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("impression_amount_sum >=", value, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumLessThan(BigDecimal value) {
            addCriterion("impression_amount_sum <", value, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("impression_amount_sum <=", value, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumIn(List<BigDecimal> values) {
            addCriterion("impression_amount_sum in", values, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumNotIn(List<BigDecimal> values) {
            addCriterion("impression_amount_sum not in", values, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("impression_amount_sum between", value1, value2, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("impression_amount_sum not between", value1, value2, "impressionAmountSum");
            return (Criteria) this;
        }

        public Criteria andImpressionRateIsNull() {
            addCriterion("impression_rate is null");
            return (Criteria) this;
        }

        public Criteria andImpressionRateIsNotNull() {
            addCriterion("impression_rate is not null");
            return (Criteria) this;
        }

        public Criteria andImpressionRateEqualTo(BigDecimal value) {
            addCriterion("impression_rate =", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateNotEqualTo(BigDecimal value) {
            addCriterion("impression_rate <>", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateGreaterThan(BigDecimal value) {
            addCriterion("impression_rate >", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("impression_rate >=", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateLessThan(BigDecimal value) {
            addCriterion("impression_rate <", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("impression_rate <=", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateIn(List<BigDecimal> values) {
            addCriterion("impression_rate in", values, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateNotIn(List<BigDecimal> values) {
            addCriterion("impression_rate not in", values, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("impression_rate between", value1, value2, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("impression_rate not between", value1, value2, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumIsNull() {
            addCriterion("click_amount_sum is null");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumIsNotNull() {
            addCriterion("click_amount_sum is not null");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumEqualTo(BigDecimal value) {
            addCriterion("click_amount_sum =", value, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumNotEqualTo(BigDecimal value) {
            addCriterion("click_amount_sum <>", value, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumGreaterThan(BigDecimal value) {
            addCriterion("click_amount_sum >", value, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("click_amount_sum >=", value, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumLessThan(BigDecimal value) {
            addCriterion("click_amount_sum <", value, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("click_amount_sum <=", value, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumIn(List<BigDecimal> values) {
            addCriterion("click_amount_sum in", values, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumNotIn(List<BigDecimal> values) {
            addCriterion("click_amount_sum not in", values, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("click_amount_sum between", value1, value2, "clickAmountSum");
            return (Criteria) this;
        }

        public Criteria andClickAmountSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("click_amount_sum not between", value1, value2, "clickAmountSum");
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

        public Criteria andArrivalAmountSumIsNull() {
            addCriterion("arrival_amount_sum is null");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumIsNotNull() {
            addCriterion("arrival_amount_sum is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumEqualTo(BigDecimal value) {
            addCriterion("arrival_amount_sum =", value, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumNotEqualTo(BigDecimal value) {
            addCriterion("arrival_amount_sum <>", value, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumGreaterThan(BigDecimal value) {
            addCriterion("arrival_amount_sum >", value, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("arrival_amount_sum >=", value, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumLessThan(BigDecimal value) {
            addCriterion("arrival_amount_sum <", value, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("arrival_amount_sum <=", value, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumIn(List<BigDecimal> values) {
            addCriterion("arrival_amount_sum in", values, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumNotIn(List<BigDecimal> values) {
            addCriterion("arrival_amount_sum not in", values, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("arrival_amount_sum between", value1, value2, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("arrival_amount_sum not between", value1, value2, "arrivalAmountSum");
            return (Criteria) this;
        }

        public Criteria andArrivalRateIsNull() {
            addCriterion("arrival_rate is null");
            return (Criteria) this;
        }

        public Criteria andArrivalRateIsNotNull() {
            addCriterion("arrival_rate is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalRateEqualTo(BigDecimal value) {
            addCriterion("arrival_rate =", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateNotEqualTo(BigDecimal value) {
            addCriterion("arrival_rate <>", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateGreaterThan(BigDecimal value) {
            addCriterion("arrival_rate >", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("arrival_rate >=", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateLessThan(BigDecimal value) {
            addCriterion("arrival_rate <", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("arrival_rate <=", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateIn(List<BigDecimal> values) {
            addCriterion("arrival_rate in", values, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateNotIn(List<BigDecimal> values) {
            addCriterion("arrival_rate not in", values, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("arrival_rate between", value1, value2, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("arrival_rate not between", value1, value2, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumIsNull() {
            addCriterion("unique_amount_sum is null");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumIsNotNull() {
            addCriterion("unique_amount_sum is not null");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumEqualTo(BigDecimal value) {
            addCriterion("unique_amount_sum =", value, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumNotEqualTo(BigDecimal value) {
            addCriterion("unique_amount_sum <>", value, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumGreaterThan(BigDecimal value) {
            addCriterion("unique_amount_sum >", value, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unique_amount_sum >=", value, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumLessThan(BigDecimal value) {
            addCriterion("unique_amount_sum <", value, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unique_amount_sum <=", value, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumIn(List<BigDecimal> values) {
            addCriterion("unique_amount_sum in", values, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumNotIn(List<BigDecimal> values) {
            addCriterion("unique_amount_sum not in", values, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unique_amount_sum between", value1, value2, "uniqueAmountSum");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unique_amount_sum not between", value1, value2, "uniqueAmountSum");
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
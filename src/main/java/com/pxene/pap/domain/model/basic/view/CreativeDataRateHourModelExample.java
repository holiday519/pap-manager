package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreativeDataRateHourModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CreativeDataRateHourModelExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
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
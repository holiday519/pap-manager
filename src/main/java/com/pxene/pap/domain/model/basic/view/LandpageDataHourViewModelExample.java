package com.pxene.pap.domain.model.basic.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LandpageDataHourViewModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LandpageDataHourViewModelExample() {
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

        public Criteria andResidentTimeSumIsNull() {
            addCriterion("resident_time_sum is null");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumIsNotNull() {
            addCriterion("resident_time_sum is not null");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumEqualTo(BigDecimal value) {
            addCriterion("resident_time_sum =", value, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumNotEqualTo(BigDecimal value) {
            addCriterion("resident_time_sum <>", value, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumGreaterThan(BigDecimal value) {
            addCriterion("resident_time_sum >", value, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("resident_time_sum >=", value, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumLessThan(BigDecimal value) {
            addCriterion("resident_time_sum <", value, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("resident_time_sum <=", value, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumIn(List<BigDecimal> values) {
            addCriterion("resident_time_sum in", values, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumNotIn(List<BigDecimal> values) {
            addCriterion("resident_time_sum not in", values, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("resident_time_sum between", value1, value2, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andResidentTimeSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("resident_time_sum not between", value1, value2, "residentTimeSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumIsNull() {
            addCriterion("jump_amount_sum is null");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumIsNotNull() {
            addCriterion("jump_amount_sum is not null");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumEqualTo(BigDecimal value) {
            addCriterion("jump_amount_sum =", value, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumNotEqualTo(BigDecimal value) {
            addCriterion("jump_amount_sum <>", value, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumGreaterThan(BigDecimal value) {
            addCriterion("jump_amount_sum >", value, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("jump_amount_sum >=", value, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumLessThan(BigDecimal value) {
            addCriterion("jump_amount_sum <", value, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("jump_amount_sum <=", value, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumIn(List<BigDecimal> values) {
            addCriterion("jump_amount_sum in", values, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumNotIn(List<BigDecimal> values) {
            addCriterion("jump_amount_sum not in", values, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("jump_amount_sum between", value1, value2, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpAmountSumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("jump_amount_sum not between", value1, value2, "jumpAmountSum");
            return (Criteria) this;
        }

        public Criteria andJumpRateIsNull() {
            addCriterion("jump_rate is null");
            return (Criteria) this;
        }

        public Criteria andJumpRateIsNotNull() {
            addCriterion("jump_rate is not null");
            return (Criteria) this;
        }

        public Criteria andJumpRateEqualTo(BigDecimal value) {
            addCriterion("jump_rate =", value, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateNotEqualTo(BigDecimal value) {
            addCriterion("jump_rate <>", value, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateGreaterThan(BigDecimal value) {
            addCriterion("jump_rate >", value, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("jump_rate >=", value, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateLessThan(BigDecimal value) {
            addCriterion("jump_rate <", value, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("jump_rate <=", value, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateIn(List<BigDecimal> values) {
            addCriterion("jump_rate in", values, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateNotIn(List<BigDecimal> values) {
            addCriterion("jump_rate not in", values, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("jump_rate between", value1, value2, "jumpRate");
            return (Criteria) this;
        }

        public Criteria andJumpRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("jump_rate not between", value1, value2, "jumpRate");
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
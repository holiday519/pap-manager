package com.pxene.pap.domain.model.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CreativeDataDayModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CreativeDataDayModelExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterionForJDBCDate("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterionForJDBCDate("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterionForJDBCDate("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterionForJDBCDate("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("date not between", value1, value2, "date");
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

        public Criteria andWinAmountEqualTo(Long value) {
            addCriterion("win_amount =", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountNotEqualTo(Long value) {
            addCriterion("win_amount <>", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountGreaterThan(Long value) {
            addCriterion("win_amount >", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("win_amount >=", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountLessThan(Long value) {
            addCriterion("win_amount <", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountLessThanOrEqualTo(Long value) {
            addCriterion("win_amount <=", value, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountIn(List<Long> values) {
            addCriterion("win_amount in", values, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountNotIn(List<Long> values) {
            addCriterion("win_amount not in", values, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountBetween(Long value1, Long value2) {
            addCriterion("win_amount between", value1, value2, "winAmount");
            return (Criteria) this;
        }

        public Criteria andWinAmountNotBetween(Long value1, Long value2) {
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

        public Criteria andImpressionAmountEqualTo(Long value) {
            addCriterion("impression_amount =", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountNotEqualTo(Long value) {
            addCriterion("impression_amount <>", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountGreaterThan(Long value) {
            addCriterion("impression_amount >", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("impression_amount >=", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountLessThan(Long value) {
            addCriterion("impression_amount <", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountLessThanOrEqualTo(Long value) {
            addCriterion("impression_amount <=", value, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountIn(List<Long> values) {
            addCriterion("impression_amount in", values, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountNotIn(List<Long> values) {
            addCriterion("impression_amount not in", values, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountBetween(Long value1, Long value2) {
            addCriterion("impression_amount between", value1, value2, "impressionAmount");
            return (Criteria) this;
        }

        public Criteria andImpressionAmountNotBetween(Long value1, Long value2) {
            addCriterion("impression_amount not between", value1, value2, "impressionAmount");
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

        public Criteria andImpressionRateEqualTo(Float value) {
            addCriterion("impression_rate =", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateNotEqualTo(Float value) {
            addCriterion("impression_rate <>", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateGreaterThan(Float value) {
            addCriterion("impression_rate >", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateGreaterThanOrEqualTo(Float value) {
            addCriterion("impression_rate >=", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateLessThan(Float value) {
            addCriterion("impression_rate <", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateLessThanOrEqualTo(Float value) {
            addCriterion("impression_rate <=", value, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateIn(List<Float> values) {
            addCriterion("impression_rate in", values, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateNotIn(List<Float> values) {
            addCriterion("impression_rate not in", values, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateBetween(Float value1, Float value2) {
            addCriterion("impression_rate between", value1, value2, "impressionRate");
            return (Criteria) this;
        }

        public Criteria andImpressionRateNotBetween(Float value1, Float value2) {
            addCriterion("impression_rate not between", value1, value2, "impressionRate");
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

        public Criteria andClickAmountEqualTo(Long value) {
            addCriterion("click_amount =", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountNotEqualTo(Long value) {
            addCriterion("click_amount <>", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountGreaterThan(Long value) {
            addCriterion("click_amount >", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("click_amount >=", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountLessThan(Long value) {
            addCriterion("click_amount <", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountLessThanOrEqualTo(Long value) {
            addCriterion("click_amount <=", value, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountIn(List<Long> values) {
            addCriterion("click_amount in", values, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountNotIn(List<Long> values) {
            addCriterion("click_amount not in", values, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountBetween(Long value1, Long value2) {
            addCriterion("click_amount between", value1, value2, "clickAmount");
            return (Criteria) this;
        }

        public Criteria andClickAmountNotBetween(Long value1, Long value2) {
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

        public Criteria andClickRateEqualTo(Float value) {
            addCriterion("click_rate =", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateNotEqualTo(Float value) {
            addCriterion("click_rate <>", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateGreaterThan(Float value) {
            addCriterion("click_rate >", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateGreaterThanOrEqualTo(Float value) {
            addCriterion("click_rate >=", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateLessThan(Float value) {
            addCriterion("click_rate <", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateLessThanOrEqualTo(Float value) {
            addCriterion("click_rate <=", value, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateIn(List<Float> values) {
            addCriterion("click_rate in", values, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateNotIn(List<Float> values) {
            addCriterion("click_rate not in", values, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateBetween(Float value1, Float value2) {
            addCriterion("click_rate between", value1, value2, "clickRate");
            return (Criteria) this;
        }

        public Criteria andClickRateNotBetween(Float value1, Float value2) {
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

        public Criteria andArrivalAmountEqualTo(Long value) {
            addCriterion("arrival_amount =", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountNotEqualTo(Long value) {
            addCriterion("arrival_amount <>", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountGreaterThan(Long value) {
            addCriterion("arrival_amount >", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("arrival_amount >=", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountLessThan(Long value) {
            addCriterion("arrival_amount <", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountLessThanOrEqualTo(Long value) {
            addCriterion("arrival_amount <=", value, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountIn(List<Long> values) {
            addCriterion("arrival_amount in", values, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountNotIn(List<Long> values) {
            addCriterion("arrival_amount not in", values, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountBetween(Long value1, Long value2) {
            addCriterion("arrival_amount between", value1, value2, "arrivalAmount");
            return (Criteria) this;
        }

        public Criteria andArrivalAmountNotBetween(Long value1, Long value2) {
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

        public Criteria andArrivalRateEqualTo(Float value) {
            addCriterion("arrival_rate =", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateNotEqualTo(Float value) {
            addCriterion("arrival_rate <>", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateGreaterThan(Float value) {
            addCriterion("arrival_rate >", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateGreaterThanOrEqualTo(Float value) {
            addCriterion("arrival_rate >=", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateLessThan(Float value) {
            addCriterion("arrival_rate <", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateLessThanOrEqualTo(Float value) {
            addCriterion("arrival_rate <=", value, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateIn(List<Float> values) {
            addCriterion("arrival_rate in", values, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateNotIn(List<Float> values) {
            addCriterion("arrival_rate not in", values, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateBetween(Float value1, Float value2) {
            addCriterion("arrival_rate between", value1, value2, "arrivalRate");
            return (Criteria) this;
        }

        public Criteria andArrivalRateNotBetween(Float value1, Float value2) {
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

        public Criteria andUniqueAmountEqualTo(Long value) {
            addCriterion("unique_amount =", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountNotEqualTo(Long value) {
            addCriterion("unique_amount <>", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountGreaterThan(Long value) {
            addCriterion("unique_amount >", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("unique_amount >=", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountLessThan(Long value) {
            addCriterion("unique_amount <", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountLessThanOrEqualTo(Long value) {
            addCriterion("unique_amount <=", value, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountIn(List<Long> values) {
            addCriterion("unique_amount in", values, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountNotIn(List<Long> values) {
            addCriterion("unique_amount not in", values, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountBetween(Long value1, Long value2) {
            addCriterion("unique_amount between", value1, value2, "uniqueAmount");
            return (Criteria) this;
        }

        public Criteria andUniqueAmountNotBetween(Long value1, Long value2) {
            addCriterion("unique_amount not between", value1, value2, "uniqueAmount");
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
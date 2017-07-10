package com.pxene.pap.domain.models;

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

        public Criteria andDspIdIsNull() {
            addCriterion("dsp_id is null");
            return (Criteria) this;
        }

        public Criteria andDspIdIsNotNull() {
            addCriterion("dsp_id is not null");
            return (Criteria) this;
        }

        public Criteria andDspIdEqualTo(String value) {
            addCriterion("dsp_id =", value, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdNotEqualTo(String value) {
            addCriterion("dsp_id <>", value, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdGreaterThan(String value) {
            addCriterion("dsp_id >", value, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdGreaterThanOrEqualTo(String value) {
            addCriterion("dsp_id >=", value, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdLessThan(String value) {
            addCriterion("dsp_id <", value, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdLessThanOrEqualTo(String value) {
            addCriterion("dsp_id <=", value, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdLike(String value) {
            addCriterion("dsp_id like", value, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdNotLike(String value) {
            addCriterion("dsp_id not like", value, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdIn(List<String> values) {
            addCriterion("dsp_id in", values, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdNotIn(List<String> values) {
            addCriterion("dsp_id not in", values, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdBetween(String value1, String value2) {
            addCriterion("dsp_id between", value1, value2, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspIdNotBetween(String value1, String value2) {
            addCriterion("dsp_id not between", value1, value2, "dspId");
            return (Criteria) this;
        }

        public Criteria andDspNameIsNull() {
            addCriterion("dsp_name is null");
            return (Criteria) this;
        }

        public Criteria andDspNameIsNotNull() {
            addCriterion("dsp_name is not null");
            return (Criteria) this;
        }

        public Criteria andDspNameEqualTo(String value) {
            addCriterion("dsp_name =", value, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameNotEqualTo(String value) {
            addCriterion("dsp_name <>", value, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameGreaterThan(String value) {
            addCriterion("dsp_name >", value, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameGreaterThanOrEqualTo(String value) {
            addCriterion("dsp_name >=", value, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameLessThan(String value) {
            addCriterion("dsp_name <", value, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameLessThanOrEqualTo(String value) {
            addCriterion("dsp_name <=", value, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameLike(String value) {
            addCriterion("dsp_name like", value, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameNotLike(String value) {
            addCriterion("dsp_name not like", value, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameIn(List<String> values) {
            addCriterion("dsp_name in", values, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameNotIn(List<String> values) {
            addCriterion("dsp_name not in", values, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameBetween(String value1, String value2) {
            addCriterion("dsp_name between", value1, value2, "dspName");
            return (Criteria) this;
        }

        public Criteria andDspNameNotBetween(String value1, String value2) {
            addCriterion("dsp_name not between", value1, value2, "dspName");
            return (Criteria) this;
        }

        public Criteria andIurlIsNull() {
            addCriterion("iurl is null");
            return (Criteria) this;
        }

        public Criteria andIurlIsNotNull() {
            addCriterion("iurl is not null");
            return (Criteria) this;
        }

        public Criteria andIurlEqualTo(String value) {
            addCriterion("iurl =", value, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlNotEqualTo(String value) {
            addCriterion("iurl <>", value, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlGreaterThan(String value) {
            addCriterion("iurl >", value, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlGreaterThanOrEqualTo(String value) {
            addCriterion("iurl >=", value, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlLessThan(String value) {
            addCriterion("iurl <", value, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlLessThanOrEqualTo(String value) {
            addCriterion("iurl <=", value, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlLike(String value) {
            addCriterion("iurl like", value, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlNotLike(String value) {
            addCriterion("iurl not like", value, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlIn(List<String> values) {
            addCriterion("iurl in", values, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlNotIn(List<String> values) {
            addCriterion("iurl not in", values, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlBetween(String value1, String value2) {
            addCriterion("iurl between", value1, value2, "iurl");
            return (Criteria) this;
        }

        public Criteria andIurlNotBetween(String value1, String value2) {
            addCriterion("iurl not between", value1, value2, "iurl");
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

        public Criteria andAdvertiserAddUrlIsNull() {
            addCriterion("advertiser_add_url is null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlIsNotNull() {
            addCriterion("advertiser_add_url is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlEqualTo(String value) {
            addCriterion("advertiser_add_url =", value, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlNotEqualTo(String value) {
            addCriterion("advertiser_add_url <>", value, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlGreaterThan(String value) {
            addCriterion("advertiser_add_url >", value, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlGreaterThanOrEqualTo(String value) {
            addCriterion("advertiser_add_url >=", value, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlLessThan(String value) {
            addCriterion("advertiser_add_url <", value, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlLessThanOrEqualTo(String value) {
            addCriterion("advertiser_add_url <=", value, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlLike(String value) {
            addCriterion("advertiser_add_url like", value, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlNotLike(String value) {
            addCriterion("advertiser_add_url not like", value, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlIn(List<String> values) {
            addCriterion("advertiser_add_url in", values, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlNotIn(List<String> values) {
            addCriterion("advertiser_add_url not in", values, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlBetween(String value1, String value2) {
            addCriterion("advertiser_add_url between", value1, value2, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserAddUrlNotBetween(String value1, String value2) {
            addCriterion("advertiser_add_url not between", value1, value2, "advertiserAddUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlIsNull() {
            addCriterion("advertiser_update_url is null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlIsNotNull() {
            addCriterion("advertiser_update_url is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlEqualTo(String value) {
            addCriterion("advertiser_update_url =", value, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlNotEqualTo(String value) {
            addCriterion("advertiser_update_url <>", value, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlGreaterThan(String value) {
            addCriterion("advertiser_update_url >", value, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlGreaterThanOrEqualTo(String value) {
            addCriterion("advertiser_update_url >=", value, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlLessThan(String value) {
            addCriterion("advertiser_update_url <", value, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlLessThanOrEqualTo(String value) {
            addCriterion("advertiser_update_url <=", value, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlLike(String value) {
            addCriterion("advertiser_update_url like", value, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlNotLike(String value) {
            addCriterion("advertiser_update_url not like", value, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlIn(List<String> values) {
            addCriterion("advertiser_update_url in", values, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlNotIn(List<String> values) {
            addCriterion("advertiser_update_url not in", values, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlBetween(String value1, String value2) {
            addCriterion("advertiser_update_url between", value1, value2, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserUpdateUrlNotBetween(String value1, String value2) {
            addCriterion("advertiser_update_url not between", value1, value2, "advertiserUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlIsNull() {
            addCriterion("advertiser_query_url is null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlIsNotNull() {
            addCriterion("advertiser_query_url is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlEqualTo(String value) {
            addCriterion("advertiser_query_url =", value, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlNotEqualTo(String value) {
            addCriterion("advertiser_query_url <>", value, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlGreaterThan(String value) {
            addCriterion("advertiser_query_url >", value, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlGreaterThanOrEqualTo(String value) {
            addCriterion("advertiser_query_url >=", value, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlLessThan(String value) {
            addCriterion("advertiser_query_url <", value, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlLessThanOrEqualTo(String value) {
            addCriterion("advertiser_query_url <=", value, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlLike(String value) {
            addCriterion("advertiser_query_url like", value, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlNotLike(String value) {
            addCriterion("advertiser_query_url not like", value, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlIn(List<String> values) {
            addCriterion("advertiser_query_url in", values, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlNotIn(List<String> values) {
            addCriterion("advertiser_query_url not in", values, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlBetween(String value1, String value2) {
            addCriterion("advertiser_query_url between", value1, value2, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andAdvertiserQueryUrlNotBetween(String value1, String value2) {
            addCriterion("advertiser_query_url not between", value1, value2, "advertiserQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlIsNull() {
            addCriterion("qualification_add_url is null");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlIsNotNull() {
            addCriterion("qualification_add_url is not null");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlEqualTo(String value) {
            addCriterion("qualification_add_url =", value, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlNotEqualTo(String value) {
            addCriterion("qualification_add_url <>", value, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlGreaterThan(String value) {
            addCriterion("qualification_add_url >", value, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlGreaterThanOrEqualTo(String value) {
            addCriterion("qualification_add_url >=", value, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlLessThan(String value) {
            addCriterion("qualification_add_url <", value, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlLessThanOrEqualTo(String value) {
            addCriterion("qualification_add_url <=", value, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlLike(String value) {
            addCriterion("qualification_add_url like", value, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlNotLike(String value) {
            addCriterion("qualification_add_url not like", value, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlIn(List<String> values) {
            addCriterion("qualification_add_url in", values, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlNotIn(List<String> values) {
            addCriterion("qualification_add_url not in", values, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlBetween(String value1, String value2) {
            addCriterion("qualification_add_url between", value1, value2, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationAddUrlNotBetween(String value1, String value2) {
            addCriterion("qualification_add_url not between", value1, value2, "qualificationAddUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlIsNull() {
            addCriterion("qualification_update_url is null");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlIsNotNull() {
            addCriterion("qualification_update_url is not null");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlEqualTo(String value) {
            addCriterion("qualification_update_url =", value, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlNotEqualTo(String value) {
            addCriterion("qualification_update_url <>", value, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlGreaterThan(String value) {
            addCriterion("qualification_update_url >", value, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlGreaterThanOrEqualTo(String value) {
            addCriterion("qualification_update_url >=", value, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlLessThan(String value) {
            addCriterion("qualification_update_url <", value, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlLessThanOrEqualTo(String value) {
            addCriterion("qualification_update_url <=", value, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlLike(String value) {
            addCriterion("qualification_update_url like", value, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlNotLike(String value) {
            addCriterion("qualification_update_url not like", value, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlIn(List<String> values) {
            addCriterion("qualification_update_url in", values, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlNotIn(List<String> values) {
            addCriterion("qualification_update_url not in", values, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlBetween(String value1, String value2) {
            addCriterion("qualification_update_url between", value1, value2, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationUpdateUrlNotBetween(String value1, String value2) {
            addCriterion("qualification_update_url not between", value1, value2, "qualificationUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlIsNull() {
            addCriterion("qualification_query_url is null");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlIsNotNull() {
            addCriterion("qualification_query_url is not null");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlEqualTo(String value) {
            addCriterion("qualification_query_url =", value, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlNotEqualTo(String value) {
            addCriterion("qualification_query_url <>", value, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlGreaterThan(String value) {
            addCriterion("qualification_query_url >", value, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlGreaterThanOrEqualTo(String value) {
            addCriterion("qualification_query_url >=", value, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlLessThan(String value) {
            addCriterion("qualification_query_url <", value, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlLessThanOrEqualTo(String value) {
            addCriterion("qualification_query_url <=", value, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlLike(String value) {
            addCriterion("qualification_query_url like", value, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlNotLike(String value) {
            addCriterion("qualification_query_url not like", value, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlIn(List<String> values) {
            addCriterion("qualification_query_url in", values, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlNotIn(List<String> values) {
            addCriterion("qualification_query_url not in", values, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlBetween(String value1, String value2) {
            addCriterion("qualification_query_url between", value1, value2, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andQualificationQueryUrlNotBetween(String value1, String value2) {
            addCriterion("qualification_query_url not between", value1, value2, "qualificationQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlIsNull() {
            addCriterion("creative_add_url is null");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlIsNotNull() {
            addCriterion("creative_add_url is not null");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlEqualTo(String value) {
            addCriterion("creative_add_url =", value, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlNotEqualTo(String value) {
            addCriterion("creative_add_url <>", value, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlGreaterThan(String value) {
            addCriterion("creative_add_url >", value, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlGreaterThanOrEqualTo(String value) {
            addCriterion("creative_add_url >=", value, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlLessThan(String value) {
            addCriterion("creative_add_url <", value, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlLessThanOrEqualTo(String value) {
            addCriterion("creative_add_url <=", value, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlLike(String value) {
            addCriterion("creative_add_url like", value, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlNotLike(String value) {
            addCriterion("creative_add_url not like", value, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlIn(List<String> values) {
            addCriterion("creative_add_url in", values, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlNotIn(List<String> values) {
            addCriterion("creative_add_url not in", values, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlBetween(String value1, String value2) {
            addCriterion("creative_add_url between", value1, value2, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAddUrlNotBetween(String value1, String value2) {
            addCriterion("creative_add_url not between", value1, value2, "creativeAddUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlIsNull() {
            addCriterion("creative_update_url is null");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlIsNotNull() {
            addCriterion("creative_update_url is not null");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlEqualTo(String value) {
            addCriterion("creative_update_url =", value, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlNotEqualTo(String value) {
            addCriterion("creative_update_url <>", value, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlGreaterThan(String value) {
            addCriterion("creative_update_url >", value, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlGreaterThanOrEqualTo(String value) {
            addCriterion("creative_update_url >=", value, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlLessThan(String value) {
            addCriterion("creative_update_url <", value, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlLessThanOrEqualTo(String value) {
            addCriterion("creative_update_url <=", value, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlLike(String value) {
            addCriterion("creative_update_url like", value, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlNotLike(String value) {
            addCriterion("creative_update_url not like", value, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlIn(List<String> values) {
            addCriterion("creative_update_url in", values, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlNotIn(List<String> values) {
            addCriterion("creative_update_url not in", values, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlBetween(String value1, String value2) {
            addCriterion("creative_update_url between", value1, value2, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeUpdateUrlNotBetween(String value1, String value2) {
            addCriterion("creative_update_url not between", value1, value2, "creativeUpdateUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlIsNull() {
            addCriterion("creative_query_url is null");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlIsNotNull() {
            addCriterion("creative_query_url is not null");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlEqualTo(String value) {
            addCriterion("creative_query_url =", value, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlNotEqualTo(String value) {
            addCriterion("creative_query_url <>", value, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlGreaterThan(String value) {
            addCriterion("creative_query_url >", value, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlGreaterThanOrEqualTo(String value) {
            addCriterion("creative_query_url >=", value, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlLessThan(String value) {
            addCriterion("creative_query_url <", value, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlLessThanOrEqualTo(String value) {
            addCriterion("creative_query_url <=", value, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlLike(String value) {
            addCriterion("creative_query_url like", value, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlNotLike(String value) {
            addCriterion("creative_query_url not like", value, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlIn(List<String> values) {
            addCriterion("creative_query_url in", values, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlNotIn(List<String> values) {
            addCriterion("creative_query_url not in", values, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlBetween(String value1, String value2) {
            addCriterion("creative_query_url between", value1, value2, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeQueryUrlNotBetween(String value1, String value2) {
            addCriterion("creative_query_url not between", value1, value2, "creativeQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlIsNull() {
            addCriterion("creative_audit_state_query_url is null");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlIsNotNull() {
            addCriterion("creative_audit_state_query_url is not null");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlEqualTo(String value) {
            addCriterion("creative_audit_state_query_url =", value, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlNotEqualTo(String value) {
            addCriterion("creative_audit_state_query_url <>", value, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlGreaterThan(String value) {
            addCriterion("creative_audit_state_query_url >", value, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlGreaterThanOrEqualTo(String value) {
            addCriterion("creative_audit_state_query_url >=", value, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlLessThan(String value) {
            addCriterion("creative_audit_state_query_url <", value, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlLessThanOrEqualTo(String value) {
            addCriterion("creative_audit_state_query_url <=", value, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlLike(String value) {
            addCriterion("creative_audit_state_query_url like", value, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlNotLike(String value) {
            addCriterion("creative_audit_state_query_url not like", value, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlIn(List<String> values) {
            addCriterion("creative_audit_state_query_url in", values, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlNotIn(List<String> values) {
            addCriterion("creative_audit_state_query_url not in", values, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlBetween(String value1, String value2) {
            addCriterion("creative_audit_state_query_url between", value1, value2, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andCreativeAuditStateQueryUrlNotBetween(String value1, String value2) {
            addCriterion("creative_audit_state_query_url not between", value1, value2, "creativeAuditStateQueryUrl");
            return (Criteria) this;
        }

        public Criteria andSignKeyIsNull() {
            addCriterion("sign_key is null");
            return (Criteria) this;
        }

        public Criteria andSignKeyIsNotNull() {
            addCriterion("sign_key is not null");
            return (Criteria) this;
        }

        public Criteria andSignKeyEqualTo(String value) {
            addCriterion("sign_key =", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyNotEqualTo(String value) {
            addCriterion("sign_key <>", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyGreaterThan(String value) {
            addCriterion("sign_key >", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyGreaterThanOrEqualTo(String value) {
            addCriterion("sign_key >=", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyLessThan(String value) {
            addCriterion("sign_key <", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyLessThanOrEqualTo(String value) {
            addCriterion("sign_key <=", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyLike(String value) {
            addCriterion("sign_key like", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyNotLike(String value) {
            addCriterion("sign_key not like", value, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyIn(List<String> values) {
            addCriterion("sign_key in", values, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyNotIn(List<String> values) {
            addCriterion("sign_key not in", values, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyBetween(String value1, String value2) {
            addCriterion("sign_key between", value1, value2, "signKey");
            return (Criteria) this;
        }

        public Criteria andSignKeyNotBetween(String value1, String value2) {
            addCriterion("sign_key not between", value1, value2, "signKey");
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
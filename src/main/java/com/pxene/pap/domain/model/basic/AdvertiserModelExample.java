package com.pxene.pap.domain.model.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdvertiserModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdvertiserModelExample() {
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

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andContactIsNull() {
            addCriterion("contact is null");
            return (Criteria) this;
        }

        public Criteria andContactIsNotNull() {
            addCriterion("contact is not null");
            return (Criteria) this;
        }

        public Criteria andContactEqualTo(String value) {
            addCriterion("contact =", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactNotEqualTo(String value) {
            addCriterion("contact <>", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactGreaterThan(String value) {
            addCriterion("contact >", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactGreaterThanOrEqualTo(String value) {
            addCriterion("contact >=", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactLessThan(String value) {
            addCriterion("contact <", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactLessThanOrEqualTo(String value) {
            addCriterion("contact <=", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactLike(String value) {
            addCriterion("contact like", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactNotLike(String value) {
            addCriterion("contact not like", value, "contact");
            return (Criteria) this;
        }

        public Criteria andContactIn(List<String> values) {
            addCriterion("contact in", values, "contact");
            return (Criteria) this;
        }

        public Criteria andContactNotIn(List<String> values) {
            addCriterion("contact not in", values, "contact");
            return (Criteria) this;
        }

        public Criteria andContactBetween(String value1, String value2) {
            addCriterion("contact between", value1, value2, "contact");
            return (Criteria) this;
        }

        public Criteria andContactNotBetween(String value1, String value2) {
            addCriterion("contact not between", value1, value2, "contact");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andQqIsNull() {
            addCriterion("qq is null");
            return (Criteria) this;
        }

        public Criteria andQqIsNotNull() {
            addCriterion("qq is not null");
            return (Criteria) this;
        }

        public Criteria andQqEqualTo(String value) {
            addCriterion("qq =", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotEqualTo(String value) {
            addCriterion("qq <>", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThan(String value) {
            addCriterion("qq >", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqGreaterThanOrEqualTo(String value) {
            addCriterion("qq >=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThan(String value) {
            addCriterion("qq <", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLessThanOrEqualTo(String value) {
            addCriterion("qq <=", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqLike(String value) {
            addCriterion("qq like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotLike(String value) {
            addCriterion("qq not like", value, "qq");
            return (Criteria) this;
        }

        public Criteria andQqIn(List<String> values) {
            addCriterion("qq in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotIn(List<String> values) {
            addCriterion("qq not in", values, "qq");
            return (Criteria) this;
        }

        public Criteria andQqBetween(String value1, String value2) {
            addCriterion("qq between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andQqNotBetween(String value1, String value2) {
            addCriterion("qq not between", value1, value2, "qq");
            return (Criteria) this;
        }

        public Criteria andIndustryidIsNull() {
            addCriterion("industryid is null");
            return (Criteria) this;
        }

        public Criteria andIndustryidIsNotNull() {
            addCriterion("industryid is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryidEqualTo(String value) {
            addCriterion("industryid =", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidNotEqualTo(String value) {
            addCriterion("industryid <>", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidGreaterThan(String value) {
            addCriterion("industryid >", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidGreaterThanOrEqualTo(String value) {
            addCriterion("industryid >=", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidLessThan(String value) {
            addCriterion("industryid <", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidLessThanOrEqualTo(String value) {
            addCriterion("industryid <=", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidLike(String value) {
            addCriterion("industryid like", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidNotLike(String value) {
            addCriterion("industryid not like", value, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidIn(List<String> values) {
            addCriterion("industryid in", values, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidNotIn(List<String> values) {
            addCriterion("industryid not in", values, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidBetween(String value1, String value2) {
            addCriterion("industryid between", value1, value2, "industryid");
            return (Criteria) this;
        }

        public Criteria andIndustryidNotBetween(String value1, String value2) {
            addCriterion("industryid not between", value1, value2, "industryid");
            return (Criteria) this;
        }

        public Criteria andLicensenoIsNull() {
            addCriterion("licenseno is null");
            return (Criteria) this;
        }

        public Criteria andLicensenoIsNotNull() {
            addCriterion("licenseno is not null");
            return (Criteria) this;
        }

        public Criteria andLicensenoEqualTo(String value) {
            addCriterion("licenseno =", value, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoNotEqualTo(String value) {
            addCriterion("licenseno <>", value, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoGreaterThan(String value) {
            addCriterion("licenseno >", value, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoGreaterThanOrEqualTo(String value) {
            addCriterion("licenseno >=", value, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoLessThan(String value) {
            addCriterion("licenseno <", value, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoLessThanOrEqualTo(String value) {
            addCriterion("licenseno <=", value, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoLike(String value) {
            addCriterion("licenseno like", value, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoNotLike(String value) {
            addCriterion("licenseno not like", value, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoIn(List<String> values) {
            addCriterion("licenseno in", values, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoNotIn(List<String> values) {
            addCriterion("licenseno not in", values, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoBetween(String value1, String value2) {
            addCriterion("licenseno between", value1, value2, "licenseno");
            return (Criteria) this;
        }

        public Criteria andLicensenoNotBetween(String value1, String value2) {
            addCriterion("licenseno not between", value1, value2, "licenseno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoIsNull() {
            addCriterion("organizationno is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoIsNotNull() {
            addCriterion("organizationno is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoEqualTo(String value) {
            addCriterion("organizationno =", value, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoNotEqualTo(String value) {
            addCriterion("organizationno <>", value, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoGreaterThan(String value) {
            addCriterion("organizationno >", value, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoGreaterThanOrEqualTo(String value) {
            addCriterion("organizationno >=", value, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoLessThan(String value) {
            addCriterion("organizationno <", value, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoLessThanOrEqualTo(String value) {
            addCriterion("organizationno <=", value, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoLike(String value) {
            addCriterion("organizationno like", value, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoNotLike(String value) {
            addCriterion("organizationno not like", value, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoIn(List<String> values) {
            addCriterion("organizationno in", values, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoNotIn(List<String> values) {
            addCriterion("organizationno not in", values, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoBetween(String value1, String value2) {
            addCriterion("organizationno between", value1, value2, "organizationno");
            return (Criteria) this;
        }

        public Criteria andOrganizationnoNotBetween(String value1, String value2) {
            addCriterion("organizationno not between", value1, value2, "organizationno");
            return (Criteria) this;
        }

        public Criteria andLogourlIsNull() {
            addCriterion("logourl is null");
            return (Criteria) this;
        }

        public Criteria andLogourlIsNotNull() {
            addCriterion("logourl is not null");
            return (Criteria) this;
        }

        public Criteria andLogourlEqualTo(String value) {
            addCriterion("logourl =", value, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlNotEqualTo(String value) {
            addCriterion("logourl <>", value, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlGreaterThan(String value) {
            addCriterion("logourl >", value, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlGreaterThanOrEqualTo(String value) {
            addCriterion("logourl >=", value, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlLessThan(String value) {
            addCriterion("logourl <", value, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlLessThanOrEqualTo(String value) {
            addCriterion("logourl <=", value, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlLike(String value) {
            addCriterion("logourl like", value, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlNotLike(String value) {
            addCriterion("logourl not like", value, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlIn(List<String> values) {
            addCriterion("logourl in", values, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlNotIn(List<String> values) {
            addCriterion("logourl not in", values, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlBetween(String value1, String value2) {
            addCriterion("logourl between", value1, value2, "logourl");
            return (Criteria) this;
        }

        public Criteria andLogourlNotBetween(String value1, String value2) {
            addCriterion("logourl not between", value1, value2, "logourl");
            return (Criteria) this;
        }

        public Criteria andIcpurlIsNull() {
            addCriterion("icpurl is null");
            return (Criteria) this;
        }

        public Criteria andIcpurlIsNotNull() {
            addCriterion("icpurl is not null");
            return (Criteria) this;
        }

        public Criteria andIcpurlEqualTo(String value) {
            addCriterion("icpurl =", value, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlNotEqualTo(String value) {
            addCriterion("icpurl <>", value, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlGreaterThan(String value) {
            addCriterion("icpurl >", value, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlGreaterThanOrEqualTo(String value) {
            addCriterion("icpurl >=", value, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlLessThan(String value) {
            addCriterion("icpurl <", value, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlLessThanOrEqualTo(String value) {
            addCriterion("icpurl <=", value, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlLike(String value) {
            addCriterion("icpurl like", value, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlNotLike(String value) {
            addCriterion("icpurl not like", value, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlIn(List<String> values) {
            addCriterion("icpurl in", values, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlNotIn(List<String> values) {
            addCriterion("icpurl not in", values, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlBetween(String value1, String value2) {
            addCriterion("icpurl between", value1, value2, "icpurl");
            return (Criteria) this;
        }

        public Criteria andIcpurlNotBetween(String value1, String value2) {
            addCriterion("icpurl not between", value1, value2, "icpurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlIsNull() {
            addCriterion("organizationurl is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlIsNotNull() {
            addCriterion("organizationurl is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlEqualTo(String value) {
            addCriterion("organizationurl =", value, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlNotEqualTo(String value) {
            addCriterion("organizationurl <>", value, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlGreaterThan(String value) {
            addCriterion("organizationurl >", value, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlGreaterThanOrEqualTo(String value) {
            addCriterion("organizationurl >=", value, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlLessThan(String value) {
            addCriterion("organizationurl <", value, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlLessThanOrEqualTo(String value) {
            addCriterion("organizationurl <=", value, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlLike(String value) {
            addCriterion("organizationurl like", value, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlNotLike(String value) {
            addCriterion("organizationurl not like", value, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlIn(List<String> values) {
            addCriterion("organizationurl in", values, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlNotIn(List<String> values) {
            addCriterion("organizationurl not in", values, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlBetween(String value1, String value2) {
            addCriterion("organizationurl between", value1, value2, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andOrganizationurlNotBetween(String value1, String value2) {
            addCriterion("organizationurl not between", value1, value2, "organizationurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlIsNull() {
            addCriterion("licenseurl is null");
            return (Criteria) this;
        }

        public Criteria andLicenseurlIsNotNull() {
            addCriterion("licenseurl is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseurlEqualTo(String value) {
            addCriterion("licenseurl =", value, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlNotEqualTo(String value) {
            addCriterion("licenseurl <>", value, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlGreaterThan(String value) {
            addCriterion("licenseurl >", value, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlGreaterThanOrEqualTo(String value) {
            addCriterion("licenseurl >=", value, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlLessThan(String value) {
            addCriterion("licenseurl <", value, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlLessThanOrEqualTo(String value) {
            addCriterion("licenseurl <=", value, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlLike(String value) {
            addCriterion("licenseurl like", value, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlNotLike(String value) {
            addCriterion("licenseurl not like", value, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlIn(List<String> values) {
            addCriterion("licenseurl in", values, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlNotIn(List<String> values) {
            addCriterion("licenseurl not in", values, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlBetween(String value1, String value2) {
            addCriterion("licenseurl between", value1, value2, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andLicenseurlNotBetween(String value1, String value2) {
            addCriterion("licenseurl not between", value1, value2, "licenseurl");
            return (Criteria) this;
        }

        public Criteria andAccounturlIsNull() {
            addCriterion("accounturl is null");
            return (Criteria) this;
        }

        public Criteria andAccounturlIsNotNull() {
            addCriterion("accounturl is not null");
            return (Criteria) this;
        }

        public Criteria andAccounturlEqualTo(String value) {
            addCriterion("accounturl =", value, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlNotEqualTo(String value) {
            addCriterion("accounturl <>", value, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlGreaterThan(String value) {
            addCriterion("accounturl >", value, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlGreaterThanOrEqualTo(String value) {
            addCriterion("accounturl >=", value, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlLessThan(String value) {
            addCriterion("accounturl <", value, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlLessThanOrEqualTo(String value) {
            addCriterion("accounturl <=", value, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlLike(String value) {
            addCriterion("accounturl like", value, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlNotLike(String value) {
            addCriterion("accounturl not like", value, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlIn(List<String> values) {
            addCriterion("accounturl in", values, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlNotIn(List<String> values) {
            addCriterion("accounturl not in", values, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlBetween(String value1, String value2) {
            addCriterion("accounturl between", value1, value2, "accounturl");
            return (Criteria) this;
        }

        public Criteria andAccounturlNotBetween(String value1, String value2) {
            addCriterion("accounturl not between", value1, value2, "accounturl");
            return (Criteria) this;
        }

        public Criteria andSiteurlIsNull() {
            addCriterion("siteurl is null");
            return (Criteria) this;
        }

        public Criteria andSiteurlIsNotNull() {
            addCriterion("siteurl is not null");
            return (Criteria) this;
        }

        public Criteria andSiteurlEqualTo(String value) {
            addCriterion("siteurl =", value, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlNotEqualTo(String value) {
            addCriterion("siteurl <>", value, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlGreaterThan(String value) {
            addCriterion("siteurl >", value, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlGreaterThanOrEqualTo(String value) {
            addCriterion("siteurl >=", value, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlLessThan(String value) {
            addCriterion("siteurl <", value, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlLessThanOrEqualTo(String value) {
            addCriterion("siteurl <=", value, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlLike(String value) {
            addCriterion("siteurl like", value, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlNotLike(String value) {
            addCriterion("siteurl not like", value, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlIn(List<String> values) {
            addCriterion("siteurl in", values, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlNotIn(List<String> values) {
            addCriterion("siteurl not in", values, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlBetween(String value1, String value2) {
            addCriterion("siteurl between", value1, value2, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSiteurlNotBetween(String value1, String value2) {
            addCriterion("siteurl not between", value1, value2, "siteurl");
            return (Criteria) this;
        }

        public Criteria andSitenameIsNull() {
            addCriterion("sitename is null");
            return (Criteria) this;
        }

        public Criteria andSitenameIsNotNull() {
            addCriterion("sitename is not null");
            return (Criteria) this;
        }

        public Criteria andSitenameEqualTo(String value) {
            addCriterion("sitename =", value, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameNotEqualTo(String value) {
            addCriterion("sitename <>", value, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameGreaterThan(String value) {
            addCriterion("sitename >", value, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameGreaterThanOrEqualTo(String value) {
            addCriterion("sitename >=", value, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameLessThan(String value) {
            addCriterion("sitename <", value, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameLessThanOrEqualTo(String value) {
            addCriterion("sitename <=", value, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameLike(String value) {
            addCriterion("sitename like", value, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameNotLike(String value) {
            addCriterion("sitename not like", value, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameIn(List<String> values) {
            addCriterion("sitename in", values, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameNotIn(List<String> values) {
            addCriterion("sitename not in", values, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameBetween(String value1, String value2) {
            addCriterion("sitename between", value1, value2, "sitename");
            return (Criteria) this;
        }

        public Criteria andSitenameNotBetween(String value1, String value2) {
            addCriterion("sitename not between", value1, value2, "sitename");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andZipIsNull() {
            addCriterion("zip is null");
            return (Criteria) this;
        }

        public Criteria andZipIsNotNull() {
            addCriterion("zip is not null");
            return (Criteria) this;
        }

        public Criteria andZipEqualTo(String value) {
            addCriterion("zip =", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotEqualTo(String value) {
            addCriterion("zip <>", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipGreaterThan(String value) {
            addCriterion("zip >", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipGreaterThanOrEqualTo(String value) {
            addCriterion("zip >=", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLessThan(String value) {
            addCriterion("zip <", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLessThanOrEqualTo(String value) {
            addCriterion("zip <=", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLike(String value) {
            addCriterion("zip like", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotLike(String value) {
            addCriterion("zip not like", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipIn(List<String> values) {
            addCriterion("zip in", values, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotIn(List<String> values) {
            addCriterion("zip not in", values, "zip");
            return (Criteria) this;
        }

        public Criteria andZipBetween(String value1, String value2) {
            addCriterion("zip between", value1, value2, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotBetween(String value1, String value2) {
            addCriterion("zip not between", value1, value2, "zip");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
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

        public Criteria andCreattimeIsNull() {
            addCriterion("creattime is null");
            return (Criteria) this;
        }

        public Criteria andCreattimeIsNotNull() {
            addCriterion("creattime is not null");
            return (Criteria) this;
        }

        public Criteria andCreattimeEqualTo(Date value) {
            addCriterion("creattime =", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotEqualTo(Date value) {
            addCriterion("creattime <>", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeGreaterThan(Date value) {
            addCriterion("creattime >", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeGreaterThanOrEqualTo(Date value) {
            addCriterion("creattime >=", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeLessThan(Date value) {
            addCriterion("creattime <", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeLessThanOrEqualTo(Date value) {
            addCriterion("creattime <=", value, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeIn(List<Date> values) {
            addCriterion("creattime in", values, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotIn(List<Date> values) {
            addCriterion("creattime not in", values, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeBetween(Date value1, Date value2) {
            addCriterion("creattime between", value1, value2, "creattime");
            return (Criteria) this;
        }

        public Criteria andCreattimeNotBetween(Date value1, Date value2) {
            addCriterion("creattime not between", value1, value2, "creattime");
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
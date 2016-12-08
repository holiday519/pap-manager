package com.pxene.pap.domain.model.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndusttryModelExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IndusttryModelExample() {
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

        public Criteria andTanxIsNull() {
            addCriterion("tanx is null");
            return (Criteria) this;
        }

        public Criteria andTanxIsNotNull() {
            addCriterion("tanx is not null");
            return (Criteria) this;
        }

        public Criteria andTanxEqualTo(String value) {
            addCriterion("tanx =", value, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxNotEqualTo(String value) {
            addCriterion("tanx <>", value, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxGreaterThan(String value) {
            addCriterion("tanx >", value, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxGreaterThanOrEqualTo(String value) {
            addCriterion("tanx >=", value, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxLessThan(String value) {
            addCriterion("tanx <", value, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxLessThanOrEqualTo(String value) {
            addCriterion("tanx <=", value, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxLike(String value) {
            addCriterion("tanx like", value, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxNotLike(String value) {
            addCriterion("tanx not like", value, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxIn(List<String> values) {
            addCriterion("tanx in", values, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxNotIn(List<String> values) {
            addCriterion("tanx not in", values, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxBetween(String value1, String value2) {
            addCriterion("tanx between", value1, value2, "tanx");
            return (Criteria) this;
        }

        public Criteria andTanxNotBetween(String value1, String value2) {
            addCriterion("tanx not between", value1, value2, "tanx");
            return (Criteria) this;
        }

        public Criteria andImobiIsNull() {
            addCriterion("imobi is null");
            return (Criteria) this;
        }

        public Criteria andImobiIsNotNull() {
            addCriterion("imobi is not null");
            return (Criteria) this;
        }

        public Criteria andImobiEqualTo(String value) {
            addCriterion("imobi =", value, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiNotEqualTo(String value) {
            addCriterion("imobi <>", value, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiGreaterThan(String value) {
            addCriterion("imobi >", value, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiGreaterThanOrEqualTo(String value) {
            addCriterion("imobi >=", value, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiLessThan(String value) {
            addCriterion("imobi <", value, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiLessThanOrEqualTo(String value) {
            addCriterion("imobi <=", value, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiLike(String value) {
            addCriterion("imobi like", value, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiNotLike(String value) {
            addCriterion("imobi not like", value, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiIn(List<String> values) {
            addCriterion("imobi in", values, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiNotIn(List<String> values) {
            addCriterion("imobi not in", values, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiBetween(String value1, String value2) {
            addCriterion("imobi between", value1, value2, "imobi");
            return (Criteria) this;
        }

        public Criteria andImobiNotBetween(String value1, String value2) {
            addCriterion("imobi not between", value1, value2, "imobi");
            return (Criteria) this;
        }

        public Criteria andLetvIsNull() {
            addCriterion("letv is null");
            return (Criteria) this;
        }

        public Criteria andLetvIsNotNull() {
            addCriterion("letv is not null");
            return (Criteria) this;
        }

        public Criteria andLetvEqualTo(String value) {
            addCriterion("letv =", value, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvNotEqualTo(String value) {
            addCriterion("letv <>", value, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvGreaterThan(String value) {
            addCriterion("letv >", value, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvGreaterThanOrEqualTo(String value) {
            addCriterion("letv >=", value, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvLessThan(String value) {
            addCriterion("letv <", value, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvLessThanOrEqualTo(String value) {
            addCriterion("letv <=", value, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvLike(String value) {
            addCriterion("letv like", value, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvNotLike(String value) {
            addCriterion("letv not like", value, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvIn(List<String> values) {
            addCriterion("letv in", values, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvNotIn(List<String> values) {
            addCriterion("letv not in", values, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvBetween(String value1, String value2) {
            addCriterion("letv between", value1, value2, "letv");
            return (Criteria) this;
        }

        public Criteria andLetvNotBetween(String value1, String value2) {
            addCriterion("letv not between", value1, value2, "letv");
            return (Criteria) this;
        }

        public Criteria andBaiduIsNull() {
            addCriterion("baidu is null");
            return (Criteria) this;
        }

        public Criteria andBaiduIsNotNull() {
            addCriterion("baidu is not null");
            return (Criteria) this;
        }

        public Criteria andBaiduEqualTo(String value) {
            addCriterion("baidu =", value, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduNotEqualTo(String value) {
            addCriterion("baidu <>", value, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduGreaterThan(String value) {
            addCriterion("baidu >", value, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduGreaterThanOrEqualTo(String value) {
            addCriterion("baidu >=", value, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduLessThan(String value) {
            addCriterion("baidu <", value, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduLessThanOrEqualTo(String value) {
            addCriterion("baidu <=", value, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduLike(String value) {
            addCriterion("baidu like", value, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduNotLike(String value) {
            addCriterion("baidu not like", value, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduIn(List<String> values) {
            addCriterion("baidu in", values, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduNotIn(List<String> values) {
            addCriterion("baidu not in", values, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduBetween(String value1, String value2) {
            addCriterion("baidu between", value1, value2, "baidu");
            return (Criteria) this;
        }

        public Criteria andBaiduNotBetween(String value1, String value2) {
            addCriterion("baidu not between", value1, value2, "baidu");
            return (Criteria) this;
        }

        public Criteria andAdviewIsNull() {
            addCriterion("adview is null");
            return (Criteria) this;
        }

        public Criteria andAdviewIsNotNull() {
            addCriterion("adview is not null");
            return (Criteria) this;
        }

        public Criteria andAdviewEqualTo(String value) {
            addCriterion("adview =", value, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewNotEqualTo(String value) {
            addCriterion("adview <>", value, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewGreaterThan(String value) {
            addCriterion("adview >", value, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewGreaterThanOrEqualTo(String value) {
            addCriterion("adview >=", value, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewLessThan(String value) {
            addCriterion("adview <", value, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewLessThanOrEqualTo(String value) {
            addCriterion("adview <=", value, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewLike(String value) {
            addCriterion("adview like", value, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewNotLike(String value) {
            addCriterion("adview not like", value, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewIn(List<String> values) {
            addCriterion("adview in", values, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewNotIn(List<String> values) {
            addCriterion("adview not in", values, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewBetween(String value1, String value2) {
            addCriterion("adview between", value1, value2, "adview");
            return (Criteria) this;
        }

        public Criteria andAdviewNotBetween(String value1, String value2) {
            addCriterion("adview not between", value1, value2, "adview");
            return (Criteria) this;
        }

        public Criteria andGoogleIsNull() {
            addCriterion("google is null");
            return (Criteria) this;
        }

        public Criteria andGoogleIsNotNull() {
            addCriterion("google is not null");
            return (Criteria) this;
        }

        public Criteria andGoogleEqualTo(String value) {
            addCriterion("google =", value, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleNotEqualTo(String value) {
            addCriterion("google <>", value, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleGreaterThan(String value) {
            addCriterion("google >", value, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleGreaterThanOrEqualTo(String value) {
            addCriterion("google >=", value, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleLessThan(String value) {
            addCriterion("google <", value, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleLessThanOrEqualTo(String value) {
            addCriterion("google <=", value, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleLike(String value) {
            addCriterion("google like", value, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleNotLike(String value) {
            addCriterion("google not like", value, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleIn(List<String> values) {
            addCriterion("google in", values, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleNotIn(List<String> values) {
            addCriterion("google not in", values, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleBetween(String value1, String value2) {
            addCriterion("google between", value1, value2, "google");
            return (Criteria) this;
        }

        public Criteria andGoogleNotBetween(String value1, String value2) {
            addCriterion("google not between", value1, value2, "google");
            return (Criteria) this;
        }

        public Criteria andIqiyiIsNull() {
            addCriterion("iqiyi is null");
            return (Criteria) this;
        }

        public Criteria andIqiyiIsNotNull() {
            addCriterion("iqiyi is not null");
            return (Criteria) this;
        }

        public Criteria andIqiyiEqualTo(String value) {
            addCriterion("iqiyi =", value, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiNotEqualTo(String value) {
            addCriterion("iqiyi <>", value, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiGreaterThan(String value) {
            addCriterion("iqiyi >", value, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiGreaterThanOrEqualTo(String value) {
            addCriterion("iqiyi >=", value, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiLessThan(String value) {
            addCriterion("iqiyi <", value, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiLessThanOrEqualTo(String value) {
            addCriterion("iqiyi <=", value, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiLike(String value) {
            addCriterion("iqiyi like", value, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiNotLike(String value) {
            addCriterion("iqiyi not like", value, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiIn(List<String> values) {
            addCriterion("iqiyi in", values, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiNotIn(List<String> values) {
            addCriterion("iqiyi not in", values, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiBetween(String value1, String value2) {
            addCriterion("iqiyi between", value1, value2, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andIqiyiNotBetween(String value1, String value2) {
            addCriterion("iqiyi not between", value1, value2, "iqiyi");
            return (Criteria) this;
        }

        public Criteria andTencentIsNull() {
            addCriterion("tencent is null");
            return (Criteria) this;
        }

        public Criteria andTencentIsNotNull() {
            addCriterion("tencent is not null");
            return (Criteria) this;
        }

        public Criteria andTencentEqualTo(String value) {
            addCriterion("tencent =", value, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentNotEqualTo(String value) {
            addCriterion("tencent <>", value, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentGreaterThan(String value) {
            addCriterion("tencent >", value, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentGreaterThanOrEqualTo(String value) {
            addCriterion("tencent >=", value, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentLessThan(String value) {
            addCriterion("tencent <", value, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentLessThanOrEqualTo(String value) {
            addCriterion("tencent <=", value, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentLike(String value) {
            addCriterion("tencent like", value, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentNotLike(String value) {
            addCriterion("tencent not like", value, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentIn(List<String> values) {
            addCriterion("tencent in", values, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentNotIn(List<String> values) {
            addCriterion("tencent not in", values, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentBetween(String value1, String value2) {
            addCriterion("tencent between", value1, value2, "tencent");
            return (Criteria) this;
        }

        public Criteria andTencentNotBetween(String value1, String value2) {
            addCriterion("tencent not between", value1, value2, "tencent");
            return (Criteria) this;
        }

        public Criteria andAutohomeIsNull() {
            addCriterion("autohome is null");
            return (Criteria) this;
        }

        public Criteria andAutohomeIsNotNull() {
            addCriterion("autohome is not null");
            return (Criteria) this;
        }

        public Criteria andAutohomeEqualTo(String value) {
            addCriterion("autohome =", value, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeNotEqualTo(String value) {
            addCriterion("autohome <>", value, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeGreaterThan(String value) {
            addCriterion("autohome >", value, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeGreaterThanOrEqualTo(String value) {
            addCriterion("autohome >=", value, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeLessThan(String value) {
            addCriterion("autohome <", value, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeLessThanOrEqualTo(String value) {
            addCriterion("autohome <=", value, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeLike(String value) {
            addCriterion("autohome like", value, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeNotLike(String value) {
            addCriterion("autohome not like", value, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeIn(List<String> values) {
            addCriterion("autohome in", values, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeNotIn(List<String> values) {
            addCriterion("autohome not in", values, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeBetween(String value1, String value2) {
            addCriterion("autohome between", value1, value2, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomeNotBetween(String value1, String value2) {
            addCriterion("autohome not between", value1, value2, "autohome");
            return (Criteria) this;
        }

        public Criteria andAutohomenameIsNull() {
            addCriterion("autohomename is null");
            return (Criteria) this;
        }

        public Criteria andAutohomenameIsNotNull() {
            addCriterion("autohomename is not null");
            return (Criteria) this;
        }

        public Criteria andAutohomenameEqualTo(String value) {
            addCriterion("autohomename =", value, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameNotEqualTo(String value) {
            addCriterion("autohomename <>", value, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameGreaterThan(String value) {
            addCriterion("autohomename >", value, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameGreaterThanOrEqualTo(String value) {
            addCriterion("autohomename >=", value, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameLessThan(String value) {
            addCriterion("autohomename <", value, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameLessThanOrEqualTo(String value) {
            addCriterion("autohomename <=", value, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameLike(String value) {
            addCriterion("autohomename like", value, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameNotLike(String value) {
            addCriterion("autohomename not like", value, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameIn(List<String> values) {
            addCriterion("autohomename in", values, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameNotIn(List<String> values) {
            addCriterion("autohomename not in", values, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameBetween(String value1, String value2) {
            addCriterion("autohomename between", value1, value2, "autohomename");
            return (Criteria) this;
        }

        public Criteria andAutohomenameNotBetween(String value1, String value2) {
            addCriterion("autohomename not between", value1, value2, "autohomename");
            return (Criteria) this;
        }

        public Criteria andSohuIsNull() {
            addCriterion("sohu is null");
            return (Criteria) this;
        }

        public Criteria andSohuIsNotNull() {
            addCriterion("sohu is not null");
            return (Criteria) this;
        }

        public Criteria andSohuEqualTo(String value) {
            addCriterion("sohu =", value, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuNotEqualTo(String value) {
            addCriterion("sohu <>", value, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuGreaterThan(String value) {
            addCriterion("sohu >", value, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuGreaterThanOrEqualTo(String value) {
            addCriterion("sohu >=", value, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuLessThan(String value) {
            addCriterion("sohu <", value, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuLessThanOrEqualTo(String value) {
            addCriterion("sohu <=", value, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuLike(String value) {
            addCriterion("sohu like", value, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuNotLike(String value) {
            addCriterion("sohu not like", value, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuIn(List<String> values) {
            addCriterion("sohu in", values, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuNotIn(List<String> values) {
            addCriterion("sohu not in", values, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuBetween(String value1, String value2) {
            addCriterion("sohu between", value1, value2, "sohu");
            return (Criteria) this;
        }

        public Criteria andSohuNotBetween(String value1, String value2) {
            addCriterion("sohu not between", value1, value2, "sohu");
            return (Criteria) this;
        }

        public Criteria andMomoIsNull() {
            addCriterion("momo is null");
            return (Criteria) this;
        }

        public Criteria andMomoIsNotNull() {
            addCriterion("momo is not null");
            return (Criteria) this;
        }

        public Criteria andMomoEqualTo(String value) {
            addCriterion("momo =", value, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoNotEqualTo(String value) {
            addCriterion("momo <>", value, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoGreaterThan(String value) {
            addCriterion("momo >", value, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoGreaterThanOrEqualTo(String value) {
            addCriterion("momo >=", value, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoLessThan(String value) {
            addCriterion("momo <", value, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoLessThanOrEqualTo(String value) {
            addCriterion("momo <=", value, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoLike(String value) {
            addCriterion("momo like", value, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoNotLike(String value) {
            addCriterion("momo not like", value, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoIn(List<String> values) {
            addCriterion("momo in", values, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoNotIn(List<String> values) {
            addCriterion("momo not in", values, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoBetween(String value1, String value2) {
            addCriterion("momo between", value1, value2, "momo");
            return (Criteria) this;
        }

        public Criteria andMomoNotBetween(String value1, String value2) {
            addCriterion("momo not between", value1, value2, "momo");
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
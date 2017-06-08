package com.pxene.pap.domain.models;

import java.util.Date;

public class FormulaModel {
    private String id;

    private String name;

    private String ruleId;

    private String formula;

    private String staticId;

    private Double forwardVernier;

    private Double negativeVernier;

    private Float weight;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula == null ? null : formula.trim();
    }

    public String getStaticId() {
        return staticId;
    }

    public void setStaticId(String staticId) {
        this.staticId = staticId == null ? null : staticId.trim();
    }

    public Double getForwardVernier() {
        return forwardVernier;
    }

    public void setForwardVernier(Double forwardVernier) {
        this.forwardVernier = forwardVernier;
    }

    public Double getNegativeVernier() {
        return negativeVernier;
    }

    public void setNegativeVernier(Double negativeVernier) {
        this.negativeVernier = negativeVernier;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", ruleId=").append(ruleId);
        sb.append(", formula=").append(formula);
        sb.append(", staticId=").append(staticId);
        sb.append(", forwardVernier=").append(forwardVernier);
        sb.append(", negativeVernier=").append(negativeVernier);
        sb.append(", weight=").append(weight);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}
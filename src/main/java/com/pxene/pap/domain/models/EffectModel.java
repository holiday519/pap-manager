package com.pxene.pap.domain.models;

import java.util.Date;

public class EffectModel {
    private String id;

    private String projectId;

    private String code;

    private Date date;

    private Double a1;

    private Double a2;

    private Double a3;

    private Double a4;

    private Double a5;

    private Double a6;

    private Double a7;

    private Double a8;

    private Double a9;

    private Double a10;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getA1() {
        return a1;
    }

    public void setA1(Double a1) {
        this.a1 = a1;
    }

    public Double getA2() {
        return a2;
    }

    public void setA2(Double a2) {
        this.a2 = a2;
    }

    public Double getA3() {
        return a3;
    }

    public void setA3(Double a3) {
        this.a3 = a3;
    }

    public Double getA4() {
        return a4;
    }

    public void setA4(Double a4) {
        this.a4 = a4;
    }

    public Double getA5() {
        return a5;
    }

    public void setA5(Double a5) {
        this.a5 = a5;
    }

    public Double getA6() {
        return a6;
    }

    public void setA6(Double a6) {
        this.a6 = a6;
    }

    public Double getA7() {
        return a7;
    }

    public void setA7(Double a7) {
        this.a7 = a7;
    }

    public Double getA8() {
        return a8;
    }

    public void setA8(Double a8) {
        this.a8 = a8;
    }

    public Double getA9() {
        return a9;
    }

    public void setA9(Double a9) {
        this.a9 = a9;
    }

    public Double getA10() {
        return a10;
    }

    public void setA10(Double a10) {
        this.a10 = a10;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", projectId=").append(projectId);
        sb.append(", code=").append(code);
        sb.append(", date=").append(date);
        sb.append(", a1=").append(a1);
        sb.append(", a2=").append(a2);
        sb.append(", a3=").append(a3);
        sb.append(", a4=").append(a4);
        sb.append(", a5=").append(a5);
        sb.append(", a6=").append(a6);
        sb.append(", a7=").append(a7);
        sb.append(", a8=").append(a8);
        sb.append(", a9=").append(a9);
        sb.append(", a10=").append(a10);
        sb.append("]");
        return sb.toString();
    }
}
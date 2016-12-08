package com.pxene.pap.domain.model.basic;

import java.math.BigDecimal;
import java.util.Date;

public class CreativeMaterialModel {
    private String id;

    private String creativeid;

    private String materialid;

    private BigDecimal price;

    private Date createtime;

    private Date updatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreativeid() {
        return creativeid;
    }

    public void setCreativeid(String creativeid) {
        this.creativeid = creativeid == null ? null : creativeid.trim();
    }

    public String getMaterialid() {
        return materialid;
    }

    public void setMaterialid(String materialid) {
        this.materialid = materialid == null ? null : materialid.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
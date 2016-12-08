package com.pxene.pap.domain.model.basic;

import java.util.Date;

public class IndusttryModel {
    private String id;

    private String name;

    private String tanx;

    private String imobi;

    private String letv;

    private String baidu;

    private String adview;

    private String google;

    private String iqiyi;

    private String tencent;

    private String autohome;

    private String autohomename;

    private String sohu;

    private String momo;

    private String remark;

    private Date createtime;

    private Date updatetime;

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

    public String getTanx() {
        return tanx;
    }

    public void setTanx(String tanx) {
        this.tanx = tanx == null ? null : tanx.trim();
    }

    public String getImobi() {
        return imobi;
    }

    public void setImobi(String imobi) {
        this.imobi = imobi == null ? null : imobi.trim();
    }

    public String getLetv() {
        return letv;
    }

    public void setLetv(String letv) {
        this.letv = letv == null ? null : letv.trim();
    }

    public String getBaidu() {
        return baidu;
    }

    public void setBaidu(String baidu) {
        this.baidu = baidu == null ? null : baidu.trim();
    }

    public String getAdview() {
        return adview;
    }

    public void setAdview(String adview) {
        this.adview = adview == null ? null : adview.trim();
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google == null ? null : google.trim();
    }

    public String getIqiyi() {
        return iqiyi;
    }

    public void setIqiyi(String iqiyi) {
        this.iqiyi = iqiyi == null ? null : iqiyi.trim();
    }

    public String getTencent() {
        return tencent;
    }

    public void setTencent(String tencent) {
        this.tencent = tencent == null ? null : tencent.trim();
    }

    public String getAutohome() {
        return autohome;
    }

    public void setAutohome(String autohome) {
        this.autohome = autohome == null ? null : autohome.trim();
    }

    public String getAutohomename() {
        return autohomename;
    }

    public void setAutohomename(String autohomename) {
        this.autohomename = autohomename == null ? null : autohomename.trim();
    }

    public String getSohu() {
        return sohu;
    }

    public void setSohu(String sohu) {
        this.sohu = sohu == null ? null : sohu.trim();
    }

    public String getMomo() {
        return momo;
    }

    public void setMomo(String momo) {
        this.momo = momo == null ? null : momo.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
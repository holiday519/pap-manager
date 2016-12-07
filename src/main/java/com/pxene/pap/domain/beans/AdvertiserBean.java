package com.pxene.pap.domain.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvertiserBean
{
    private String id;
    
    private String name;
    
    private String company;
    
    private String contact;
    
    private String phone;
    
    private String qq;
    
    @JsonProperty(value = "industry_id")
    private String industryId;
    
    @JsonProperty(value = "license_no")
    private String licenseNO;
    
    @JsonProperty(value = "organization_no")
    private String organizationNO;
    
    @JsonProperty(value = "logo_url")
    private String logoURL;
    
    @JsonProperty(value = "icp_url")
    private String icpURL;
    
    @JsonProperty(value = "organization_url")
    private String organizationURL;
    
    @JsonProperty(value = "license_url")
    private String licenseURL;
    
    @JsonProperty(value = "account_url")
    private String accountURL;
    
    @JsonProperty(value = "site_url")
    private String siteURL;
    
    @JsonProperty(value = "site_name")
    private String siteName;
    
    private String email;
    
    private String zip;
    
    private String address;
    
    private String status;
    
    private String remark;
    
    @JsonProperty(value = "creattime")
    private Date creatTime;
    
    @JsonProperty(value = "updatetime")
    private Date updateTime;
    
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCompany()
    {
        return company;
    }
    public void setCompany(String company)
    {
        this.company = company;
    }
    
    public String getContact()
    {
        return contact;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }
    
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getQQ()
    {
        return qq;
    }
    public void setQQ(String qq)
    {
        this.qq = qq;
    }
    
    public String getIndustryId()
    {
        return industryId;
    }
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }
    
    public String getLicenseNO()
    {
        return licenseNO;
    }
    public void setLicenseNO(String licenseNO)
    {
        this.licenseNO = licenseNO;
    }
    
    public String getOrganizationNO()
    {
        return organizationNO;
    }
    public void setOrganizationNO(String organizationNO)
    {
        this.organizationNO = organizationNO;
    }
    
    public String getLogoURL()
    {
        return logoURL;
    }
    public void setLogoURL(String logoURL)
    {
        this.logoURL = logoURL;
    }
    
    public String getIcpURL()
    {
        return icpURL;
    }
    public void setIcpURL(String icpURL)
    {
        this.icpURL = icpURL;
    }
    
    public String getOrganizationURL()
    {
        return organizationURL;
    }
    public void setOrganizationURL(String organizationURL)
    {
        this.organizationURL = organizationURL;
    }
    
    public String getLicenseURL()
    {
        return licenseURL;
    }
    public void setLicenseURL(String licenseURL)
    {
        this.licenseURL = licenseURL;
    }
    
    public String getAccountURL()
    {
        return accountURL;
    }
    public void setAccountURL(String accountURL)
    {
        this.accountURL = accountURL;
    }
    
    public String getSiteURL()
    {
        return siteURL;
    }
    public void setSiteURL(String siteURL)
    {
        this.siteURL = siteURL;
    }
    
    public String getSiteName()
    {
        return siteName;
    }
    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }
    
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getZip()
    {
        return zip;
    }
    public void setZip(String zip)
    {
        this.zip = zip;
    }
    
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Date getCreatTime()
    {
        return creatTime;
    }
    public void setCreatTime(Date creatTime)
    {
        this.creatTime = creatTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    
    public AdvertiserBean()
    {
        super();
    }
    public AdvertiserBean(String id, String name, String company, String contact, String phone, String qq, String industryId, String licenseNO, String organizationNO, String logoURL, String icpURL,
            String organizationURL, String licenseURL, String accountURL, String siteURL, String siteName, String email, String zip, String address, String status, String remark)
    {
        super();
        this.id = id;
        this.name = name;
        this.company = company;
        this.contact = contact;
        this.phone = phone;
        this.qq = qq;
        this.industryId = industryId;
        this.licenseNO = licenseNO;
        this.organizationNO = organizationNO;
        this.logoURL = logoURL;
        this.icpURL = icpURL;
        this.organizationURL = organizationURL;
        this.licenseURL = licenseURL;
        this.accountURL = accountURL;
        this.siteURL = siteURL;
        this.siteName = siteName;
        this.email = email;
        this.zip = zip;
        this.address = address;
        this.status = status;
        this.remark = remark;
    }
    
    
    @Override
    public String toString()
    {
        return "AdvertiserBean [id=" + id + ", name=" + name + ", company=" + company + ", contact=" + contact + ", phone=" + phone + ", qq=" + qq + ", industryId=" + industryId + ", licenseNO="
                + licenseNO + ", organizationNO=" + organizationNO + ", logoURL=" + logoURL + ", icpURL=" + icpURL + ", organizationURL=" + organizationURL + ", licenseURL=" + licenseURL
                + ", accountURL=" + accountURL + ", siteURL=" + siteURL + ", siteName=" + siteName + ", email=" + email + ", zip=" + zip + ", address=" + address + ", status=" + status + ", remark="
                + remark + ", creatTime=" + creatTime + ", updateTime=" + updateTime + "]";
    }
}

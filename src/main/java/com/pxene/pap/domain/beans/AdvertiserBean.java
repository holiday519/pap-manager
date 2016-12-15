package com.pxene.pap.domain.beans;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RegexConstant;


public class AdvertiserBean
{
    private String id;
    
    @NotNull(message = PhrasesConstant.INVALID_ADVERTISER_NAME)
    private String name;
    
    private String company;
    
    private String contact;
    
    @Pattern(regexp = RegexConstant.PHONE, message = PhrasesConstant.INVALID_PHONE)
    private String phone;
    
    private String qq;
    
    @JsonProperty(value = "industry_id")
    private String industryId;
    
    @JsonProperty(value = "license_no")
    private String licenseNo;
    
    @JsonProperty(value = "organization_no")
    private String organizationNo;
    
    @JsonProperty(value = "logo_url")
    private String logoUrl;
    
    @JsonProperty(value = "icp_url")
    private String icpUrl;
    
    @JsonProperty(value = "organization_url")
    private String organizationUrl;
    
    @JsonProperty(value = "license_url")
    private String licenseUrl;
    
    @JsonProperty(value = "account_url")
    private String accountUrl;
    
    @JsonProperty(value = "site_url")
    private String siteUrl;
    
    @JsonProperty(value = "site_name")
    private String siteName;
    
    @Email
    private String email;
    
    private String zip;
    
    private String address;
    
    private String remark;
    
    @JsonProperty(value = "create_time")
    private Date createTime;
    
    @JsonProperty(value = "update_time")
    private Date updateTime;
    
    
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id == null ? null : id.trim();
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name == null ? null : name.trim();
    }
    
    public String getCompany()
    {
        return company;
    }
    public void setCompany(String company)
    {
        this.company = company == null ? null : company.trim();
    }
    
    public String getContact()
    {
        return contact;
    }
    public void setContact(String contact)
    {
        this.contact = contact == null ? null : contact.trim();
    }
    
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone == null ? null : phone.trim();
    }
    
    public String getQQ()
    {
        return qq;
    }
    public void setQQ(String qq)
    {
        this.qq = qq == null ? null : qq.trim();
    }
    
    public String getIndustryId()
    {
        return industryId;
    }
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId == null ? null : industryId.trim();
    }
    
    public String getLicenseNo()
    {
        return licenseNo;
    }
    public void setLicenseNo(String licenseNo)
    {
        this.licenseNo = licenseNo == null ? null : licenseNo.trim();
    }
    
    public String getOrganizationNo()
    {
        return organizationNo;
    }
    public void setOrganizationNo(String organizationNo)
    {
        this.organizationNo = organizationNo == null ? null : organizationNo.trim();
    }
    
    public String getLogoUrl()
    {
        return logoUrl;
    }
    public void setLogoUrl(String logoUrl)
    {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }
    
    public String getIcpUrl()
    {
        return icpUrl;
    }
    public void setIcpUrl(String icpUrl)
    {
        this.icpUrl = icpUrl == null ? null : icpUrl.trim();
    }
    
    public String getOrganizationUrl()
    {
        return organizationUrl;
    }
    public void setOrganizationUrl(String organizationUrl)
    {
        this.organizationUrl = organizationUrl == null ? null : organizationUrl.trim();
    }
    
    public String getLicenseUrl()
    {
        return licenseUrl;
    }
    public void setLicenseUrl(String licenseUrl)
    {
        this.licenseUrl = licenseUrl == null ? null : licenseUrl.trim();
    }
    
    public String getAccountUrl()
    {
        return accountUrl;
    }
    public void setAccountUrl(String accountUrl)
    {
        this.accountUrl = accountUrl == null ? null : accountUrl.trim();
    }
    
    public String getSiteUrl()
    {
        return siteUrl;
    }
    public void setSiteUrl(String siteUrl)
    {
        this.siteUrl = siteUrl == null ? null : siteUrl.trim();
    }
    
    public String getSiteName()
    {
        return siteName;
    }
    public void setSiteName(String siteName)
    {
        this.siteName = siteName == null ? null : siteName.trim();
    }
    
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email == null ? null : email.trim();
    }
    
    public String getZip()
    {
        return zip;
    }
    public void setZip(String zip)
    {
        this.zip = zip == null ? null : zip.trim();
    }
    
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address == null ? null : address.trim();
    }
    
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark == null ? null : remark.trim();
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    
    @Override
    public String toString()
    {
        return "AdvertiserBean [id=" + id + ", name=" + name + ", company=" + company + ", contact=" + contact + ", phone=" + phone + ", qq=" + qq + ", industryId=" + industryId + ", licenseNo="
                + licenseNo + ", organizationNo=" + organizationNo + ", logoUrl=" + logoUrl + ", icpUrl=" + icpUrl + ", organizationUrl=" + organizationUrl + ", licenseUrl=" + licenseUrl
                + ", accountUrl=" + accountUrl + ", siteUrl=" + siteUrl + ", siteName=" + siteName + ", email=" + email + ", zip=" + zip + ", address=" + address + ", remark=" + remark
                + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}
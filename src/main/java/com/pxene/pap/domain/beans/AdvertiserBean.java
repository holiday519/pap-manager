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
    
    @JsonProperty(value = "brand_name")
    private String brandName;
    
    @JsonProperty(value = "license_no")
    private String licenseNo;
    
    @JsonProperty(value = "organization_no")
    private String organizationNo;
    
    @JsonProperty(value = "logo_path")
    private String logoPath;
    
    @JsonProperty(value = "icp_path")
    private String icpPath;
    
    @JsonProperty(value = "organization_path")
    private String organizationPath;
    
    @JsonProperty(value = "license_path")
    private String licensePath;
    
    @JsonProperty(value = "account_path")
    private String accountPath;
    
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
    public String getQq()
    {
        return qq;
    }
    public void setQq(String qq)
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
    public String getBrandName()
    {
        return brandName;
    }
    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }
    public String getLicenseNo()
    {
        return licenseNo;
    }
    public void setLicenseNo(String licenseNo)
    {
        this.licenseNo = licenseNo;
    }
    public String getOrganizationNo()
    {
        return organizationNo;
    }
    public void setOrganizationNo(String organizationNo)
    {
        this.organizationNo = organizationNo;
    }
    public String getLogoPath()
    {
        return logoPath;
    }
    public void setLogoPath(String logoPath)
    {
        this.logoPath = logoPath;
    }
    public String getIcpPath()
    {
        return icpPath;
    }
    public void setIcpPath(String icpPath)
    {
        this.icpPath = icpPath;
    }
    public String getOrganizationPath()
    {
        return organizationPath;
    }
    public void setOrganizationPath(String organizationPath)
    {
        this.organizationPath = organizationPath;
    }
    public String getLicensePath()
    {
        return licensePath;
    }
    public void setLicensePath(String licensePath)
    {
        this.licensePath = licensePath;
    }
    public String getAccountPath()
    {
        return accountPath;
    }
    public void setAccountPath(String accountPath)
    {
        this.accountPath = accountPath;
    }
    public String getSiteUrl()
    {
        return siteUrl;
    }
    public void setSiteUrl(String siteUrl)
    {
        this.siteUrl = siteUrl;
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
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
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
        return "AdvertiserBean [id=" + id + ", name=" + name + ", company=" + company + ", contact=" + contact + ", phone=" + phone + ", qq=" + qq + ", industryId=" + industryId + ", brandName="
                + brandName + ", licenseNo=" + licenseNo + ", organizationNo=" + organizationNo + ", logoPath=" + logoPath + ", icpPath=" + icpPath + ", organizationPath=" + organizationPath
                + ", licensePath=" + licensePath + ", accountPath=" + accountPath + ", siteUrl=" + siteUrl + ", siteName=" + siteName + ", email=" + email + ", zip=" + zip + ", address=" + address
                + ", remark=" + remark + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
    }
}
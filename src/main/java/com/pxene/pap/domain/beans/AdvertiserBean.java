package com.pxene.pap.domain.beans;

import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RegexConstant;

/**
 * 广告主
 */
public class AdvertiserBean extends BasicDataBean {
	@Length(max = 36, message = PhrasesConstant.LENGTH_ERROR_ID)
	private String id;
	@NotNull(message = PhrasesConstant.NAME_NOT_NULL)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
	private String name;
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_COMPANY)
	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_COMPANY)
	private String company;
	@Length(max = 20, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_CONTACT)
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_CONTACT)
	private String contact;
	@Pattern(regexp = RegexConstant.PHONE, message = PhrasesConstant.INVALID_PHONE)
	@Length(max = 20, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_PHONE)
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_PHONE)
	private String phone;
	@Length(max = 20, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_QQ)
	private String qq;
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_INDUSTY)
	private String industryId;

	private String industryName;

	private Audit[] audits;	

	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_BRANDNAME)
	private String brandName;
	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_LICENSENO)
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_LICENSENO)
	private String licenseNo;
	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_ORGANIZATIONNO)
	private String organizationNo;

	private String logoPath;

	private String icpPath;

	private String organizationPath;
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_LICENSEPATH)
	private String licensePath;

	private String accountPath;
	@Length(max = 200, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_SITEURL)
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_SITEURL)
	private String siteUrl;
	@Length(max = 200, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_SITENAME)
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_SITENAME)
	private String siteName;

	@Email
	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_EMAIL)
	private String email;
	@Length(max = 8, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_ZIP)
	private String zip;
	@Length(max = 200, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_ADDRESS)
	private String address;
	@Length(max = 200, message = PhrasesConstant.LENGTH_ERROR_REMARK)
	private String remark;

	private String status;	
	
	public static class Audit {
		private String id;
		private String adxId;
		private String name;
		private String status;
		private String enable;
		private String message;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}		
		public String getAdxId() {
			return adxId;
		}
		public void setAdxId(String adxId) {
			this.adxId = adxId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getEnable() {
			return enable;
		}
		public void setEnable(String enable) {
			this.enable = enable;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}			
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getOrganizationNo() {
		return organizationNo;
	}

	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getIcpPath() {
		return icpPath;
	}

	public void setIcpPath(String icpPath) {
		this.icpPath = icpPath;
	}

	public String getOrganizationPath() {
		return organizationPath;
	}

	public void setOrganizationPath(String organizationPath) {
		this.organizationPath = organizationPath;
	}

	public String getLicensePath() {
		return licensePath;
	}

	public void setLicensePath(String licensePath) {
		this.licensePath = licensePath;
	}

	public String getAccountPath() {
		return accountPath;
	}

	public void setAccountPath(String accountPath) {
		this.accountPath = accountPath;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}					

	public Audit[] getAudits() {
		return audits;
	}

	public void setAudits(Audit[] audits) {
		this.audits = audits;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AdvertiserBean [id=" + id + ", name=" + name + ", company="
				+ company + ", contact=" + contact + ", phone=" + phone
				+ ", qq=" + qq + ", industryId=" + industryId
				+ ", industryName=" + industryName 
				+ ", audits=" + Arrays.toString(audits) + ", brandName=" + brandName
				+ ", licenseNo=" + licenseNo + ", organizationNo="
				+ organizationNo + ", logoPath=" + logoPath + ", icpPath="
				+ icpPath + ", organizationPath=" + organizationPath
				+ ", licensePath=" + licensePath + ", accountPath="
				+ accountPath + ", siteUrl=" + siteUrl + ", siteName="
				+ siteName + ", email=" + email + ", zip=" + zip + ", address="
				+ address + ", remark=" + remark + ", status=" + status + "]";
	}

}
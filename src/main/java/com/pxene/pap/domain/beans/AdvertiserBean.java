package com.pxene.pap.domain.beans;

import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

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
	
	@Length(max = 20, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_QQ)
	private String qq;
	
	@NotNull(message = PhrasesConstant.ADVERTISER_NOTNULL_INDUSTY)
	private String industryId;

	private String industryName;

	private Audit[] audits;	

	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_BRANDNAME)
	private String brandName;
	
	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_QUALIFICATIONNO)
	private String qualificationNo;

	@Length(max = 200, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_QUALIFICATIONPATH)
	private String qualificationPath;
	
	private String qualificationType;

	@Length(max = 200, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_SITEURL)
	private String siteUrl;
	
	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_SITENAME)
	private String siteName;
	
	@Length(max = 100, message = PhrasesConstant.ADVERTISER_LENGTH_ERROR_LEGALNAME)
	private String legalName;

	@Length(max = 200, message = PhrasesConstant.LENGTH_ERROR_REMARK)
	private String remark;
	
	private String[] adxIds;

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
	
	public String getQualificationNo() {
		return qualificationNo;
	}

	public void setQualificationNo(String qualificationNo) {
		this.qualificationNo = qualificationNo;
	}

	public String getQualificationPath() {
		return qualificationPath;
	}

	public void setQualificationPath(String qualificationPath) {
		this.qualificationPath = qualificationPath;
	}

	public String getQualificationType() {
		return qualificationType;
	}

	public void setQualificationType(String qualificationType) {
		this.qualificationType = qualificationType;
	}
	
	public String[] getAdxIds() {
		return adxIds;
	}

	public void setAdxIds(String[] adxIds) {
		this.adxIds = adxIds;
	}
	
	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	@Override
	public String toString() {
		return "AdvertiserBean [id=" + id + ", name=" + name + ", company="
				+ company + ", qq=" + qq + ", industryId=" + industryId
				+ ", industryName=" + industryName + ", audits="
				+ Arrays.toString(audits) + ", brandName=" + brandName
				+ ", qualificationNo=" + qualificationNo
				+ ", qualificationPath=" + qualificationPath
				+ ", qualificationType=" + qualificationType + ", siteUrl="
				+ siteUrl + ", siteName=" + siteName + ", remark=" + remark
				+ "]";
	}
	
}
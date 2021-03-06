package com.pxene.pap.domain.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

/**
 * 项目
 */
public class ProjectBean extends BasicDataBean {

	/**
	 * 项目id
	 */
	private String id;
	
	@NotNull(message = PhrasesConstant.NAME_NOT_NULL)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
	private String name;
	
	@NotNull(message = PhrasesConstant.CODE_NOT_NULL)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_CODE)
	private String code;
	
	public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
	 * 广告主id
	 */
	@NotNull(message = PhrasesConstant.PROJECT_NOTNULL_ADVERTISERID)
	private String advertiserId;
	/**
	 * 名称
	 */
	private String advertiserName;
	/**
	 * 行业id
	 */
	private String industryId;
	/**
	 * 行业名称
	 */
	private String industryName;
	/**
	 * 总预算
	 */
	@NotNull(message = PhrasesConstant.INVALID_TOTAL_BUDGET)
	private Integer totalBudget;
	/**
	 * 备注
	 */
    @Length(max = 200, message = PhrasesConstant.LENGTH_ERROR_REMARK)
	private String remark;
	/**
	 * 状态
	 */
	private String status;
	
	private EffectField[] effectFields;

	/**
	 * 静态值
     */
	private StaticvalBean[] staticvals;
	
	/**
	 * 规则
	 */
	private RuleFormulasBean[] rules;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdvertiserId() {
		return advertiserId;
	}
	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTotalBudget() {
		return totalBudget;
	}
	public void setTotalBudget(Integer totalBudget) {
		this.totalBudget = totalBudget;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public EffectField[] getEffectFields() {
		return effectFields;
	}
	public void setEffectFields(EffectField[] effectFields) {
		this.effectFields = effectFields;
	}					
	public StaticvalBean[] getStaticvals() {
		return staticvals;
	}
	public void setStaticvals(StaticvalBean[] staticvals) {
		this.staticvals = staticvals;
	}
	public RuleFormulasBean[] getRules() {
		return rules;
	}
	public void setRules(RuleFormulasBean[] rules) {
		this.rules = rules;
	}
	@Override
    public String toString()
    {
        return "ProjectBean [id=" + id + ", name=" + name + ", advertiserId=" + advertiserId + ", advertiserName=" + advertiserName + ", industryId=" + industryId + ", industryName=" + industryName
                + ", totalBudget=" + totalBudget + ", remark=" + remark + ", status=" + status + "]";
    }
	
	public static class EffectField {
		private String id;
		private String name;
		private String code;
		private String enable;
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
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getEnable() {
			return enable;
		}
		public void setEnable(String enable) {
			this.enable = enable;
		}
	}


}

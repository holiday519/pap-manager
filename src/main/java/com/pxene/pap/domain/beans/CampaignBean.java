package com.pxene.pap.domain.beans;

import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;


/**
 * 活动所有信息
 */
public class CampaignBean extends BasicDataBean {

	/**
	 * 活动id
	 */
	private String id;
	/**
	 * 项目id
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_PROJECTID)
	private String projectId;
	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 名称
	 */
	@NotNull(message = PhrasesConstant.NAME_NOT_NULL)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
	private String name;	

	/**
	 * 备注
	 */
	@Length(max = 200, message = PhrasesConstant.LANDPAGE_LENGTH_ERROR_REMARK)
	private String remark;

	/**
     * 创意数（审核通过数/审核未通过数/总数）
     */
    private String creativeAmount;
	
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 未正常投放原因
	 */
	private String reason;
	/**
	 * 开始时间
	 */
	private Date startDate;	

	/**
	 * 结束时间
	 */
	private Date endDate;

	private String uniform;

	/**
	 * 创意数（审核通过数/审核未通过数/总数）
	 */
	private String creativeNum;
	
	/**
	 * ADXId
	 */
	private String adxId;
	/**
	 * ADX名称
	 */
	private String adxName;
	/**
	 * 定向
	 */
	private Target target;
	
	private CampaignScoreBean campaignScore;
	
	private String ruleGroupId;

	public static class Target {

		/**
		 * 地域定向
		 */
		private String[] region;

		private Region[] regions;

		public static class Region {
			private String id;
			private String name;

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

			@Override
			public String toString() {
				return "Region [id=" + id + ", name=" + name + "]";
			}

		}

		/**
		 * 广告类型定向
		 */
		private String[] adType;
		/**
		 * 时间定向
		 */
		private String[] time;
		/**
		 * 网络定向
		 */
		private String[] network;
		/**
		 * 运营商定向
		 */
		private String[] operator;
		/**
		 * 设备定向
		 */
		private String[] device;
		/**
		 * 系统定向
		 */
		private String[] os;
		/**
		 * 品牌定向
		 */
		private String[] brand;
		
		/**
		 * 地域定向
		 */
		public Region[] getRegions() {
			return regions;
		}

		public void setRegions(Region[] regions) {
			this.regions = regions;
		}
		
		/**
		 * 人群定向
		 */
		private PopulationTargetBean population;	
		
		/**
		 * adx定向
		 */
		private String adx;
		
		/**
		 * 筛选条件
		 */
		private Include[] include;
		public static class Include {
			private String word;
			private String type;
			public String getWord() {
				return word;
			}
			public void setWord(String word) {
				this.word = word;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}	
			
			@Override
			public String toString() {
				return "Include [word=" + word + ", type=" + type + "]";
			}
		}
		
		/**
		 * 排除条件
		 */
		private Exclude[] exclude;
		public static class Exclude {
			private String word;
			private String type;
			public String getWord() {
				return word;
			}
			public void setWord(String word) {
				this.word = word;
			}
			public String getType() {
				return type;
			}
			public void setType(String type) {
				this.type = type;
			}
			
			@Override
			public String toString() {
				return "Exclude [word=" + word + ", type=" + type + "]";
			}
		}

		public String[] getRegion() {
			return region;
		}

		public void setRegion(String[] region) {
			this.region = region;
		}

		public String[] getAdType() {
			return adType;
		}

		public void setAdType(String[] adType) {
			this.adType = adType;
		}

		public String[] getTime() {
			return time;
		}

		public void setTime(String[] time) {
			this.time = time;
		}

		public String[] getNetwork() {
			return network;
		}

		public void setNetwork(String[] network) {
			this.network = network;
		}

		public String[] getOperator() {
			return operator;
		}

		public void setOperator(String[] operator) {
			this.operator = operator;
		}

		public String[] getDevice() {
			return device;
		}

		public void setDevice(String[] device) {
			this.device = device;
		}

		public String[] getOs() {
			return os;
		}

		public void setOs(String[] os) {
			this.os = os;
		}

		public String[] getBrand() {
			return brand;
		}

		public void setBrand(String[] brand) {
			this.brand = brand;
		}
		
		public PopulationTargetBean getPopulation() {
			return population;
		}

		public void setPopulation(PopulationTargetBean population) {
			this.population = population;
		}				

		public String getAdx() {
			return adx;
		}

		public void setAdx(String adx) {
			this.adx = adx;
		}		

		public Include[] getInclude() {
			return include;
		}

		public void setInclude(Include[] include) {
			this.include = include;
		}

		public Exclude[] getExclude() {
			return exclude;
		}

		public void setExclude(Exclude[] exclude) {
			this.exclude = exclude;
		}

		@Override
		public String toString() {
			return "Target [region=" + Arrays.toString(region) + ", regions="
					+ Arrays.toString(regions) + ", adType="
					+ Arrays.toString(adType) + ", time="
					+ Arrays.toString(time) + ", network="
					+ Arrays.toString(network) + ", operator="
					+ Arrays.toString(operator) + ", device="
					+ Arrays.toString(device) + ", os=" 
					+ Arrays.toString(os) + ", brand=" 
					+ Arrays.toString(brand) + ", population=" + population 
					+ ", adx=" + adx + ", include=" + Arrays.toString(include)
					+ ", exclude" + Arrays.toString(exclude) + "]";
		}	
	
	}

	/**
	 * 频次
	 */
	private Frequency frequency;

	public static class Frequency {

		private String id;
		/**
		 * 控制对象
		 */
		private String controlObj;
		/**
		 * 时间类型
		 */
		private String timeType;
		/**
		 * 频次
		 */
		private Integer number;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getControlObj() {
			return controlObj;
		}

		public void setControlObj(String controlObj) {
			this.controlObj = controlObj;
		}

		public String getTimeType() {
			return timeType;
		}

		public void setTimeType(String timeType) {
			this.timeType = timeType;
		}

		public Integer getNumber() {
			return number;
		}

		public void setNumber(Integer number) {
			this.number = number;
		}

		@Override
		public String toString() {
			return "Frequency [id=" + id + ", controlObj=" + controlObj
					+ ", timeType=" + timeType + ", number=" + number + "]";
		}

	}	

	/**
	 * 投放量控制策略
	 */
	private Quantity[] quantities;

	public static class Quantity {
		private Date startDate;
		private Date endDate;
		private Integer budget;
		private Integer impression;

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		
		public Integer getBudget() {
			return budget;
		}

		public void setBudget(Integer budget) {
			this.budget = budget;
		}

		public Integer getImpression() {
			return impression;
		}

		public void setImpression(Integer impression) {
			this.impression = impression;
		}

		@Override
		public String toString() {
			return "Quantity [startDate=" + startDate + ", endDate=" + endDate
					+ ", budget=" + budget + ", impression="
					+ impression + "]";
		}

	}

	// FIXME 设置不为空的注解
	private String landpageId;
	private String landpageName;
	private String landpageUrl;

	public Quantity[] getQuantities() {
		return quantities;
	}

	public void setQuantities(Quantity[] quantities) {
		this.quantities = quantities;
	}

	public String getLandpageId() {
		return landpageId;
	}

	public void setLandpageId(String landpageId) {
		this.landpageId = landpageId;
	}

	public String getLandpageName() {
		return landpageName;
	}

	public void setLandpageName(String landpageName) {
		this.landpageName = landpageName;
	}

	public String getLandpageUrl() {
		return landpageUrl;
	}

	public void setLandpageUrl(String landpageUrl) {
		this.landpageUrl = landpageUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}	

	public String getUniform() {
		return uniform;
	}

	public void setUniform(String uniform) {
		this.uniform = uniform;
	}

	public String getCreativeNum() {
		return creativeNum;
	}

	public void setCreativeNum(String creativeNum) {
		this.creativeNum = creativeNum;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCreativeAmount() {
		return creativeAmount;
	}

	public void setCreativeAmount(String creativeAmount) {
		this.creativeAmount = creativeAmount;
	}
	
	public CampaignScoreBean getCampaignScore()
    {
        return campaignScore;
    }

    public void setCampaignScore(CampaignScoreBean campaignScore)
    {
        this.campaignScore = campaignScore;
    }        
    
    public String getRuleGroupId()
    {
        return ruleGroupId;
    }

    public void setRuleGroupId(String ruleGroupId)
    {
        this.ruleGroupId = ruleGroupId;
    }

    public String getAdxId() {
		return adxId;
	}

	public void setAdxId(String adxId) {
		this.adxId = adxId;
	}

	public String getAdxName() {
		return adxName;
	}

	public void setAdxName(String adxName) {
		this.adxName = adxName;
	}

	@Override
    public String toString()
    {
        return "CampaignBean [id=" + id + ", projectId=" + projectId + ", projectName=" + projectName + ", name=" + name + ", remark=" + remark + ", creativeAmount=" + creativeAmount + ", status="
                + status + ", reason=" + reason + ", startDate=" + startDate + ", endDate=" + endDate + ", uniform=" + uniform + ", creativeNum=" + creativeNum + ", adxId=" + adxId + ", adxName="
                + adxName + ", target=" + target + ", campaignScore=" + campaignScore + ", ruleGroupId=" + ruleGroupId + ", frequency=" + frequency + ", quantities=" + Arrays.toString(quantities)
                + ", landpageId=" + landpageId + ", landpageName=" + landpageName + ", landpageUrl=" + landpageUrl + "]";
    }

}

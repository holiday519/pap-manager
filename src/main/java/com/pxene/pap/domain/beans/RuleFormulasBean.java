package com.pxene.pap.domain.beans;

import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.pxene.pap.constant.PhrasesConstant;

public class RuleFormulasBean {
	/**
	 * 规则id
	 */
	@Length(max = 36, message = PhrasesConstant.LENGTH_ERROR_ID)
	private String id;
	/**
	 * 规则名称
	 */
	@NotNull(message = PhrasesConstant.NAME_NOT_NULL)
	@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
	private String name;
	/**
	 * 项目ID
	 */
	@NotNull(message = PhrasesConstant.CAMPAIGN_NOTNULL_PROJECTID)
	private String projectId;
	/**
	 * 触发条件
	 */
	private String triggerCondition;
	/**
	 * 关系
	 */
	private String relation;
	/**
	 * 静态值id
	 */
	private String staticvalId;
	/**
	 * 参考值对象（规则对应的静态值）
	 */	
	private Staticval staticval;
	/**
	 * 更新时间
     */
	private Date updateTime;
	public static class Staticval {
		private String id;
		private String name;
		private Double value;		
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
		public Double getValue() {
			return value;
		}
		public void setValue(Double value) {
			this.value = value;
		}	
		
		@Override
		public String toString() {
			return "Staticval [id=" + id + ", name=" + name
					+ ", value=" + value + "]";
		}
	}
	
	/**
	 * 公式
	 */
	private Formulas[] formulas;
	
	public static class Formulas {
		/**
		 * 公式id
		 */
		@Length(max = 36, message = PhrasesConstant.LENGTH_ERROR_ID)
		private String id;
		/**
		 * 公式名称
		 */
		@NotNull(message = PhrasesConstant.NAME_NOT_NULL)
		@Length(max = 100, message = PhrasesConstant.LENGTH_ERROR_NAME)
		private String name;
		/**
		 * 公式
		 */
		@NotNull(message = PhrasesConstant.FORMULA_IS_NULL)
		private String formula;
		/**
		 * 规则id
		 */
		@NotNull(message = PhrasesConstant.RULEID_IS_NULL)
		private String ruleId;
		/**
		 * 静态值id
		 */
		@NotNull(message = PhrasesConstant.FORMULA_REFERENCE_VALUE_NULL)
		private String staticvalId;
		/**
		 * 参考值对象（规则对应的静态值）
		 */
		private Staticvals staticval;
		public static class Staticvals {
			private String id;
			private String name;
			private Double value;
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
			public Double getValue() {
				return value;
			}
			public void setValue(Double value) {
				this.value = value;
			}
			@Override
			public String toString() {
				return "Staticvals [id=" + id + ", name=" + name
						+ ", value=" + value + "]";
			}
		}
		/**
		 * 正向游标
		 */
		@NotNull(message = PhrasesConstant.VERNIER_IS_NULL)
		private Double forwardVernier;
		/**
		 * 负向游标
		 */
		@NotNull(message = PhrasesConstant.VERNIER_IS_NULL)
		private Double negativeVernier;
		/**
		 * 权重
		 */
		@NotNull(message = PhrasesConstant.WEIGHT_IS_NULL)
		private Float weight;
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
		public String getFormula() {
			return formula;
		}
		public void setFormula(String formula) {
			this.formula = formula;
		}
		public String getRuleId() {
			return ruleId;
		}
		public void setRuleId(String ruleId) {
			this.ruleId = ruleId;
		}				
		public String getStaticvalId() {
			return staticvalId;
		}
		public void setStaticvalId(String staticvalId) {
			this.staticvalId = staticvalId;
		}		
		public Staticvals getStaticval() {
			return staticval;
		}
		public void setStaticval(Staticvals staticval) {
			this.staticval = staticval;
		}
		public Double getForwardVernier() {
			return forwardVernier;
		}
		public void setForwardVernier(Double forwardVernier) {
			this.forwardVernier = forwardVernier;
		}
		public Double getNegativeVernier() {
			return negativeVernier;
		}
		public void setNegativeVernier(Double negativeVernier) {
			this.negativeVernier = negativeVernier;
		}
		public Float getWeight() {
			return weight;
		}
		public void setWeight(Float weight) {
			this.weight = weight;
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}		

	public String getTriggerCondition() {
		return triggerCondition;
	}

	public void setTriggerCondition(String triggerCondition) {
		this.triggerCondition = triggerCondition;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}			

	public String getStaticvalId() {
		return staticvalId;
	}

	public void setStaticvalId(String staticvalId) {
		this.staticvalId = staticvalId;
	}

	public Staticval getStaticval() {
		return staticval;
	}

	public void setStaticval(Staticval staticval) {
		this.staticval = staticval;
	}

	public Formulas[] getFormulas() {
		return formulas;
	}

	public void setFormulas(Formulas[] formulas) {
		this.formulas = formulas;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "RuleFormulasBean[id=" + id + ",name=" + name + ",projectId=" + projectId + ",triggerCondition=" + triggerCondition 
				+ ",relation=" + relation + ",staticvalId=" + staticvalId + ",staticval=" + staticval + ",formulas=" + Arrays.toString(formulas) +",updateTime"+updateTime+ "]";
	}
}

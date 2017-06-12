package com.pxene.pap.domain.beans;

import java.util.Arrays;

public class RuleFormulasBean {
	/**
	 * 规则id
	 */
	private String id;
	/**
	 * 规则名称
	 */
	private String name;
	/**
	 * 项目ID
	 */
	private String projectId;
	/**
	 * 触发条件
	 */
	private String conditions;
	/**
	 * 关系
	 */
	private String relation;
	/**
	 * 静态值id
	 */
	private String staticId;
	/**
	 * 参考值对象（规则对应的静态值）
	 */	
	private Static statics;
	public static class Static {
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
			return "Statics [id=" + id + ", name=" + name
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
		private String id;
		/**
		 * 公式名称
		 */
		private String name;
		/**
		 * 公式
		 */
		private String formula;
		/**
		 * 规则id
		 */
		private String ruleId;
		/**
		 * 静态值id
		 */
		private String staticId;
		/**
		 * 参考值对象（规则对应的静态值）
		 */
		private Statics statics;
		public static class Statics {
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
				return "Statics [id=" + id + ", name=" + name
						+ ", value=" + value + "]";
			}
		}
		/**
		 * 正向游标
		 */
		private Double forwardVernier;
		/**
		 * 负向游标
		 */
		private Double negativeVernier;
		/**
		 * 权重
		 */
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
		public String getStaticId() {
			return staticId;
		}
		public void setStaticId(String staticId) {
			this.staticId = staticId;
		}
		public Statics getStatics() {
			return statics;
		}
		public void setStatics(Statics statics) {
			this.statics = statics;
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

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}	

	public String getStaticId() {
		return staticId;
	}

	public void setStaticId(String staticId) {
		this.staticId = staticId;
	}

	public Static getStatics() {
		return statics;
	}

	public void setStatics(Static statics) {
		this.statics = statics;
	}

	public Formulas[] getFormulas() {
		return formulas;
	}

	public void setFormulas(Formulas[] formulas) {
		this.formulas = formulas;
	}
	
	@Override
	public String toString() {
		return "RuleFormulasBean[id=" + id + ",name=" + name + ",projectId=" + projectId + ",conditions=" + conditions 
				+ ",relation=" + relation + ",staticId=" + staticId + ",statics=" + statics + ",formulas=" + Arrays.toString(formulas) + "]";
	}
}

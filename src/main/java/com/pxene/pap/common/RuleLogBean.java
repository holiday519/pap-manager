package com.pxene.pap.common;

public class RuleLogBean {

	private String id;

	private String ruleId;

	private String ruleName;

	private String campaignId;

	private String campaignName;

	private String ruleType;

	private String actionType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
		return "RuleLogBean [id=" + id + ", ruleId=" + ruleId + ", ruleName="
				+ ruleName + ", campaignId=" + campaignId + ", campaignName="
				+ campaignName + ", ruleType=" + ruleType + ", actionType="
				+ actionType + "]";
	}

}

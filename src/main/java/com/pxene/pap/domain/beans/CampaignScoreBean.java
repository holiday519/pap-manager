package com.pxene.pap.domain.beans;

import java.util.List;
import java.util.Map;

public class CampaignScoreBean
{
    private Double score;
    private String ruleName;
    private String ruleTrigger;
    private List<Map<String, String>> formulaList;
    
    
    public Double getScore()
    {
        return score;
    }
    public void setScore(Double score)
    {
        this.score = score;
    }
    public String getRuleName()
    {
        return ruleName;
    }
    public void setRuleName(String ruleName)
    {
        this.ruleName = ruleName;
    }
    public String getRuleTrigger()
    {
        return ruleTrigger;
    }
    public void setRuleTrigger(String ruleTrigger)
    {
        this.ruleTrigger = ruleTrigger;
    }
    public List<Map<String, String>> getFormulaList()
    {
        return formulaList;
    }
    public void setFormulaList(List<Map<String, String>> formulaList)
    {
        this.formulaList = formulaList;
    }
    
    
    @Override
    public String toString()
    {
        return "CampaignScoreBean [score=" + score + ", ruleName=" + ruleName + ", ruleTrigger=" + ruleTrigger + ", formulaList=" + formulaList + "]";
    }
}
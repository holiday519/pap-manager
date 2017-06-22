package com.pxene.pap.domain.beans;

import com.pxene.pap.domain.models.RuleModel;

public class RuleBean extends RuleModel
{
    private String replacedVariableVal;

    public String getReplacedVariableVal()
    {
        return replacedVariableVal;
    }
    public void setReplacedVariableVal(String replacedVariableVal)
    {
        this.replacedVariableVal = replacedVariableVal;
    }
    
    
    @Override
    public String toString()
    {
        return "RuleBean [replacedVariableVal=" + replacedVariableVal + "]";
    }
}

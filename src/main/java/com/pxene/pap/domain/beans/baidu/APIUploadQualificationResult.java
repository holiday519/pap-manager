package com.pxene.pap.domain.beans.baidu;

import java.util.Arrays;
import java.util.List;

/**
 * 批量上传广告主资质响应。
 * @author ningyu
 */
public class APIUploadQualificationResult extends Response
{
    /**
     * 广告主资质提交状态。
     * 是否支持数组：是
     */
    private List<APIAdvertiserQualificationStatus> qualificationStatuses;
    
    
    public List<APIAdvertiserQualificationStatus> getQualificationStatuses()
    {
        return qualificationStatuses;
    }
    public void setQualificationStatuses(List<APIAdvertiserQualificationStatus> qualificationStatuses)
    {
        this.qualificationStatuses = qualificationStatuses;
    }
    
    
    public APIUploadQualificationResult()
    {
        super();
    }
    public APIUploadQualificationResult(int status, Error[] errors, List<APIAdvertiserQualificationStatus> qualificationStatuses)
    {
        super(status, errors);
        this.qualificationStatuses = qualificationStatuses;
    }
    
    
    @Override
    public String toString()
    {
        return "APIUploadQualificationResult [getQualificationStatuses()=" + getQualificationStatuses() + ", getStatus()=" + getStatus() + ", getErrors()=" + Arrays.toString(getErrors()) + "]";
    }
}

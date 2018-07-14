package com.pxene.pap.domain.beans.baidu;

public class APIQueryUploadQualificationInfoResult extends BaiduResponse
{
    private APIAdvertiserQualificationInfo qualification;

    
    public APIAdvertiserQualificationInfo getQualification()
    {
        return qualification;
    }
    public void setQualification(APIAdvertiserQualificationInfo qualification)
    {
        this.qualification = qualification;
    }
    
    
    public APIQueryUploadQualificationInfoResult()
    {
        super();
    }
    public APIQueryUploadQualificationInfoResult(APIAdvertiserQualificationInfo qualification)
    {
        super();
        this.qualification = qualification;
    }
    
    
    @Override
    public String toString()
    {
        return "APIQueryUploadQualificationInfoResult [qualification=" + qualification + "]";
    }
}

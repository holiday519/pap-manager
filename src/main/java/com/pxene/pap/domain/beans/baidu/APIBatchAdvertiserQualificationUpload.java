package com.pxene.pap.domain.beans.baidu;

import java.util.List;

/**
 * 批量上传广告主资质。
 * @author ningyu
 */
public class APIBatchAdvertiserQualificationUpload extends BaseBean
{
    /**
     * 资质列表，
     * 是否支持数组：是，
     * 每个广告主对应1个，不超过5个广告主。
     */
    private List<APIAdvertiserQualificationUpload> qualifications;
    
    
    public List<APIAdvertiserQualificationUpload> getQualifications()
    {
        return qualifications;
    }
    public void setQualifications(List<APIAdvertiserQualificationUpload> qualifications)
    {
        this.qualifications = qualifications;
    }
    
    
    @Override
    public String toString()
    {
        return "APIBatchAdvertiserQualificationUpload [qualifications=" + qualifications + "]";
    }
}

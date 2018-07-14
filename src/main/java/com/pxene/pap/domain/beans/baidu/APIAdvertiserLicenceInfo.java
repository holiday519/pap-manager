package com.pxene.pap.domain.beans.baidu;

import java.util.List;

public class APIAdvertiserLicenceInfo extends BaseBean
{
    /**
     * 资质审核状态，
     * 必填，
     * 2：通过、4：拒绝、6：待审
     */
    private Integer licenceStatus;
    
    /**
     * 资质类型或行业类型，
     * 必填，
     * 如果是主体资质，则填写资质类型，具体见资质类型字典；如果是可选资质，则填写资质行业类型，具体见资质行业类型字典。
     */
    private int type;
    
    /**
     * 资质名称（企业名称、主体名称、公司名称），
     * 必填，
     * 非空字符串，长度不超过 100。
     */
    private String name;
    
    /**
     * 资质编号（企业注册号、资质编号、登记号码、办学许可证号码），
     * 必填，
     * 非空字符串，长度不超过 100。
     */
    private String number;
    
    /**
     * 有效期，
     * 必填，
     * 非 空 字 符 串 ， 格 式 如 ：2016-11-11。
     */
    private String validDate;
    
    /**
     * 资质图片二进制数据列表，
     * 选填，
     * 图片不超过3个，单个图片大小不超过2M；当不存在imgUrls时，则选择使用imgDatas来上传，二者必须存在其一。imgDatas的处理优先级高于imgUrls。
     */
    private List<Byte[]> imgDatas;
    /**
     * 资质图片 URL 列表，
     * 选填，
     * 图片不超过3个，每个url长度限制2048个字节，每个图片大小不超过2M。
     * 注：API会在后台访问创意的URL地址，抓取图片或者flash创意。请确保URL可连通并且可以通过HTTP协议在万维网上访问到。
     */
    private List<String> imgUrls;
    
    
    public Integer getLicenceStatus()
    {
        return licenceStatus;
    }
    public void setLicenceStatus(Integer licenceStatus)
    {
        this.licenceStatus = licenceStatus;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getNumber()
    {
        return number;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }
    public String getValidDate()
    {
        return validDate;
    }
    public void setValidDate(String validDate)
    {
        this.validDate = validDate;
    }
    public List<Byte[]> getImgDatas()
    {
        return imgDatas;
    }
    public void setImgDatas(List<Byte[]> imgDatas)
    {
        this.imgDatas = imgDatas;
    }
    public List<String> getImgUrls()
    {
        return imgUrls;
    }
    public void setImgUrls(List<String> imgUrls)
    {
        this.imgUrls = imgUrls;
    }
    
    
    public APIAdvertiserLicenceInfo()
    {
        super();
    }
    public APIAdvertiserLicenceInfo(Integer licenceStatus, int type, String name, String number, String validDate, List<Byte[]> imgDatas, List<String> imgUrls)
    {
        super();
        this.licenceStatus = licenceStatus;
        this.type = type;
        this.name = name;
        this.number = number;
        this.validDate = validDate;
        this.imgDatas = imgDatas;
        this.imgUrls = imgUrls;
    }
    
    
    @Override
    public String toString()
    {
        return "APIAdvertiserLicenceInfo [licenceStatus=" + licenceStatus + ", type=" + type + ", name=" + name + ", number=" + number + ", validDate=" + validDate + ", imgDatas=" + imgDatas
                + ", imgUrls=" + imgUrls + "]";
    }
}

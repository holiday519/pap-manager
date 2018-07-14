package com.pxene.pap.domain.beans.baidu;

/**
 * 广告主。
 * @author ningyu
 */
public class Advertiser extends BaseBean
{
    /**
     * 广告主 id，
     * 必填，
     * 此 id 为 dsp 系统的广告主 id，需唯一。
     */
    private Long advertiserId;
    
    /**
     * 广告主名称，
     * 必填，
     * 不超过 200 个字节，需唯一。
     */
    private String advertiserLiteName;
    
    /**
     * 广告主主体资质名称，
     * 选填，
     * 最少3个字节，不超过200个字节。可重复。
     * 请确保此名称与客户营业执照上的名称一致，否则该客户创意无法正常投放。
     * 创建时非必填，但此字段只有填写后，百度才会进行广告主状态检查。
     */
    private String advertiserName;
    
    /**
     * 网站名，
     * 选填，
     * 最长 100 个字符。
     * 创建时非必填，但此字段只有填写后，百度才会进行广告主状态检查。
     */
    private String siteName;
    
    /**
     * 网站 URL，
     * 选填，
     * 最长 512 个字节，必须以 http:// 开头。创建时非必填，但此字段只有填写后，百度才会进行广告主状态检查。。
     */
    private String siteUrl;
    
    /**
     * 联系电话，
     * 选填，
     * 最长 100 个字符。
     */
    private String telephone;
    
    /**
     * 通讯地址，
     * 选填，
     * 最长 100 个字符。
     */
    private String address;
    
    /**
     * 广告主是否白名单，
     * 选填，
     * 0：非白名单；1：白名单。
     * 此字段在请求时不需要填写且不生效，只在返回时标识广告主是否白名单。
     */
    private int isWhiteUser;
    
    
    public Long getAdvertiserId()
    {
        return advertiserId;
    }
    public void setAdvertiserId(Long advertiserId)
    {
        this.advertiserId = advertiserId;
    }
    public String getAdvertiserLiteName()
    {
        return advertiserLiteName;
    }
    public void setAdvertiserLiteName(String advertiserLiteName)
    {
        this.advertiserLiteName = advertiserLiteName;
    }
    public String getAdvertiserName()
    {
        return advertiserName;
    }
    public void setAdvertiserName(String advertiserName)
    {
        this.advertiserName = advertiserName;
    }
    public String getSiteName()
    {
        return siteName;
    }
    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }
    public String getSiteUrl()
    {
        return siteUrl;
    }
    public void setSiteUrl(String siteUrl)
    {
        this.siteUrl = siteUrl;
    }
    public String getTelephone()
    {
        return telephone;
    }
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public int getIsWhiteUser()
    {
        return isWhiteUser;
    }
    public void setIsWhiteUser(int isWhiteUser)
    {
        this.isWhiteUser = isWhiteUser;
    }
    
    
    @Override
    public String toString()
    {
        return "BaiduAdvertiserBean [advertiserId=" + advertiserId + ", advertiserLiteName=" + advertiserLiteName + ", advertiserName=" + advertiserName + ", siteName=" + siteName + ", siteUrl="
                + siteUrl + ", telephone=" + telephone + ", address=" + address + ", isWhiteUser=" + isWhiteUser + "]";
    }
}

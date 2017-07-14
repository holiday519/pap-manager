package com.pxene.pap.domain.beans.baidu;

import java.util.Arrays;
import java.util.List;

public class Creative extends BaseBean
{
    /**
     * 创意 id，
     * 必填，
     * 此 id 为 dsp 系统的创意 id，需唯一。
     */
    private long creativeId;
    /**
     * 流量类型，
     * 非必填，
     * 1：Web 流量，2：Mobile 流量，3：Video 流量，默认为 1；
     * 如果投 Mobile 流量，该参数必须为 2；如果投放 Video流量，该参数必须为 3；其次，若是视频流量的图片创意，该参数为 3，且 type=1 的图片创意即可。
     */
    private int adviewType;
    /**
     * 类型，
     * 必填，
     * 当 adviewType 为 2 时，该字段只能为 1 或 4；当 adviewType为 3 时，该字段只能为 3；
     * 1：图片，2：flash，3：视频，4：图文
     * 注：原生/信息流流量请选择图文类型的创意 type=4。
     */
    private int type;
    /**
     * 状态，
     * N/A，
     * 该字段仅在调用get/getAll/queryAuditState 接口时由系统提供，定义如下：0：通过，1：待检查，2：检查拒绝
     */
    private int state;
    /**
     * 创意 URL，
     * 非必填，
     * 长度限制：2048 个字节，大小限制：图片、flash 不大于150kURL，后缀限制：jpg|gif|swf，png格式类型的创意无法通过该种方式上传，请使用 binaryData 字段进行该种类型图片的上传。
     * 注：
     *      1. API 会在后台访问创意的 URL地址，抓取图片或者 flash 创意。请确保URL可连通并且可以通过HTTP 协议在万维网上访问到。
     *      2. 图文创意该字段不生效，请使用 creativeUrls 字段。
     */
    private String creativeUrl;
    /**
     * 创意二进制数据，
     * 非必填，
     * 大小限制：图片、flash 不大于150k，当不存在 creativeUrl 时，则选择使用 binaryData 来上传创意，二者必须存在其一。binaryData 的处理优先级高于creativeUrl。
     * 注：图文创意请该字段不生效，请使用 binaryDatas 字段。
     */
    private byte[] binaryData;
    /**
     * 点击链接，
     * N/A，
     * 当 adviewType 为 2 时，且创意尺寸为 640*960、480*800 时，该字段为非必填，其余情况下为必填；
     * 或 当 adviewType 为 2 且interactiveStyle 为 5 且fallbackType 为 1 时，该字段必填；
     * 长度限制：1024 个字节。
     */
    private String targetUrl;

    /**
     * 到达页面，
     * N/A，
     * 当 adviewType 为 2 时，且创意尺寸为 640*960、480*800 时，该字段为非必填，其余情况下为必填；
     * 或 当 adviewType 为 2 且interactiveStyle 为 5 且fallbackType 为 1 时，该字段必填；
     * 长度限制：2048 个字节。
     */
    private String landingPage;
    private String[] monitorUrls;
    private int height;
    private int width;
    private int creativeTradeId;
    private long advertiserId;
    private String frameAgreementNo;
    private int interactiveStyle;
    private String telNo;
    private String downloadUrl;
    private String appName;
    private String appDesc;
    private float appPackageSize;
    private int dataRate;
    private int duration;
    private String playTimeMonitorUrl;
    private int style;
    private String title;
    private String description;
    private String brandName;
    private List<String> creativeUrls;
    private List<byte[]> binaryDatas;
    private String appPackageName;
    private String deeplinkUrl;
    private int appVersion;
    private int fallbackType;
    
    
    public long getCreativeId()
    {
        return creativeId;
    }
    public void setCreativeId(long creativeId)
    {
        this.creativeId = creativeId;
    }
    public int getAdviewType()
    {
        return adviewType;
    }
    public void setAdviewType(int adviewType)
    {
        this.adviewType = adviewType;
    }
    public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public int getState()
    {
        return state;
    }
    public void setState(int state)
    {
        this.state = state;
    }
    public String getCreativeUrl()
    {
        return creativeUrl;
    }
    public void setCreativeUrl(String creativeUrl)
    {
        this.creativeUrl = creativeUrl;
    }
    public byte[] getBinaryData()
    {
        return binaryData;
    }
    public void setBinaryData(byte[] binaryData)
    {
        this.binaryData = binaryData;
    }
    public String getTargetUrl()
    {
        return targetUrl;
    }
    public void setTargetUrl(String targetUrl)
    {
        this.targetUrl = targetUrl;
    }
    public String getLandingPage()
    {
        return landingPage;
    }
    public void setLandingPage(String landingPage)
    {
        this.landingPage = landingPage;
    }
    public String[] getMonitorUrls()
    {
        return monitorUrls;
    }
    public void setMonitorUrls(String[] monitorUrls)
    {
        this.monitorUrls = monitorUrls;
    }
    public int getHeight()
    {
        return height;
    }
    public void setHeight(int height)
    {
        this.height = height;
    }
    public int getWidth()
    {
        return width;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public int getCreativeTradeId()
    {
        return creativeTradeId;
    }
    public void setCreativeTradeId(int creativeTradeId)
    {
        this.creativeTradeId = creativeTradeId;
    }
    public long getAdvertiserId()
    {
        return advertiserId;
    }
    public void setAdvertiserId(long advertiserId)
    {
        this.advertiserId = advertiserId;
    }
    public String getFrameAgreementNo()
    {
        return frameAgreementNo;
    }
    public void setFrameAgreementNo(String frameAgreementNo)
    {
        this.frameAgreementNo = frameAgreementNo;
    }
    public int getInteractiveStyle()
    {
        return interactiveStyle;
    }
    public void setInteractiveStyle(int interactiveStyle)
    {
        this.interactiveStyle = interactiveStyle;
    }
    public String getTelNo()
    {
        return telNo;
    }
    public void setTelNo(String telNo)
    {
        this.telNo = telNo;
    }
    public String getDownloadUrl()
    {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
    }
    public String getAppName()
    {
        return appName;
    }
    public void setAppName(String appName)
    {
        this.appName = appName;
    }
    public String getAppDesc()
    {
        return appDesc;
    }
    public void setAppDesc(String appDesc)
    {
        this.appDesc = appDesc;
    }
    public float getAppPackageSize()
    {
        return appPackageSize;
    }
    public void setAppPackageSize(float appPackageSize)
    {
        this.appPackageSize = appPackageSize;
    }
    public int getDataRate()
    {
        return dataRate;
    }
    public void setDataRate(int dataRate)
    {
        this.dataRate = dataRate;
    }
    public int getDuration()
    {
        return duration;
    }
    public void setDuration(int duration)
    {
        this.duration = duration;
    }
    public String getPlayTimeMonitorUrl()
    {
        return playTimeMonitorUrl;
    }
    public void setPlayTimeMonitorUrl(String playTimeMonitorUrl)
    {
        this.playTimeMonitorUrl = playTimeMonitorUrl;
    }
    public int getStyle()
    {
        return style;
    }
    public void setStyle(int style)
    {
        this.style = style;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getBrandName()
    {
        return brandName;
    }
    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }
    public List<String> getCreativeUrls()
    {
        return creativeUrls;
    }
    public void setCreativeUrls(List<String> creativeUrls)
    {
        this.creativeUrls = creativeUrls;
    }
    public List<byte[]> getBinaryDatas()
    {
        return binaryDatas;
    }
    public void setBinaryDatas(List<byte[]> binaryDatas)
    {
        this.binaryDatas = binaryDatas;
    }
    public String getAppPackageName()
    {
        return appPackageName;
    }
    public void setAppPackageName(String appPackageName)
    {
        this.appPackageName = appPackageName;
    }
    public String getDeeplinkUrl()
    {
        return deeplinkUrl;
    }
    public void setDeeplinkUrl(String deeplinkUrl)
    {
        this.deeplinkUrl = deeplinkUrl;
    }
    public int getAppVersion()
    {
        return appVersion;
    }
    public void setAppVersion(int appVersion)
    {
        this.appVersion = appVersion;
    }
    public int getFallbackType()
    {
        return fallbackType;
    }
    public void setFallbackType(int fallbackType)
    {
        this.fallbackType = fallbackType;
    }
    
    
    public Creative()
    {
        super();
    }
    public Creative(long creativeId, int adviewType, int type, int state, String creativeUrl, byte[] binaryData, String targetUrl, String landingPage, String[] monitorUrls, int height, int width,
            int creativeTradeId, long advertiserId, String frameAgreementNo, int interactiveStyle, String telNo, String downloadUrl, String appName, String appDesc, float appPackageSize, int dataRate,
            int duration, String playTimeMonitorUrl, int style, String title, String description, String brandName, List<String> creativeUrls, List<byte[]> binaryDatas, String appPackageName,
            String deeplinkUrl, int appVersion, int fallbackType)
    {
        super();
        this.creativeId = creativeId;
        this.adviewType = adviewType;
        this.type = type;
        this.state = state;
        this.creativeUrl = creativeUrl;
        this.binaryData = binaryData;
        this.targetUrl = targetUrl;
        this.landingPage = landingPage;
        this.monitorUrls = monitorUrls;
        this.height = height;
        this.width = width;
        this.creativeTradeId = creativeTradeId;
        this.advertiserId = advertiserId;
        this.frameAgreementNo = frameAgreementNo;
        this.interactiveStyle = interactiveStyle;
        this.telNo = telNo;
        this.downloadUrl = downloadUrl;
        this.appName = appName;
        this.appDesc = appDesc;
        this.appPackageSize = appPackageSize;
        this.dataRate = dataRate;
        this.duration = duration;
        this.playTimeMonitorUrl = playTimeMonitorUrl;
        this.style = style;
        this.title = title;
        this.description = description;
        this.brandName = brandName;
        this.creativeUrls = creativeUrls;
        this.binaryDatas = binaryDatas;
        this.appPackageName = appPackageName;
        this.deeplinkUrl = deeplinkUrl;
        this.appVersion = appVersion;
        this.fallbackType = fallbackType;
    }
    
    
    @Override
    public String toString()
    {
        return "Creative [creativeId=" + creativeId + ", adviewType=" + adviewType + ", type=" + type + ", state=" + state + ", creativeUrl=" + creativeUrl + ", binaryData="
                + Arrays.toString(binaryData) + ", targetUrl=" + targetUrl + ", landingPage=" + landingPage + ", monitorUrls=" + Arrays.toString(monitorUrls) + ", height=" + height + ", width="
                + width + ", creativeTradeId=" + creativeTradeId + ", advertiserId=" + advertiserId + ", frameAgreementNo=" + frameAgreementNo + ", interactiveStyle=" + interactiveStyle + ", telNo="
                + telNo + ", downloadUrl=" + downloadUrl + ", appName=" + appName + ", appDesc=" + appDesc + ", appPackageSize=" + appPackageSize + ", dataRate=" + dataRate + ", duration=" + duration
                + ", playTimeMonitorUrl=" + playTimeMonitorUrl + ", style=" + style + ", title=" + title + ", description=" + description + ", brandName=" + brandName + ", creativeUrls="
                + creativeUrls + ", binaryDatas=" + binaryDatas + ", appPackageName=" + appPackageName + ", deeplinkUrl=" + deeplinkUrl + ", appVersion=" + appVersion + ", fallbackType="
                + fallbackType + "]";
    }
}


package com.iwanvi.adserv.ngx.router.broker.xiaomi.video;

import javax.xml.bind.annotation.*;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}TargetType"/>
 *         &lt;element ref="{}PackageName"/>
 *         &lt;element ref="{}AppSize"/>
 *         &lt;element ref="{}TotalDownloadNum"/>
 *         &lt;element ref="{}DownloadUrl"/>
 *         &lt;element ref="{}DownloadTrackingUrl"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "targetType",
    "packageName",
    "appSize",
    "totalDownloadNum",
    "downloadUrl",
    "downloadTrackingUrl"
})
@XmlRootElement(name = "Extension")
public class Extension {

    @XmlElement(name = "TargetType", required = true)
    protected String targetType;
    @XmlElement(name = "PackageName", required = true)
    protected String packageName;
    @XmlElement(name = "AppSize", required = true)
    protected String appSize;
    @XmlElement(name = "TotalDownloadNum", required = true)
    protected String totalDownloadNum;
    @XmlElement(name = "DownloadUrl", required = true)
    protected String downloadUrl;
    @XmlElement(name = "DownloadTrackingUrl", required = true)
    protected String downloadTrackingUrl;

    /**
     * 获取targetType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * 设置targetType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetType(String value) {
        this.targetType = value;
    }

    /**
     * 获取packageName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * 设置packageName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageName(String value) {
        this.packageName = value;
    }

    /**
     * 获取appSize属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppSize() {
        return appSize;
    }

    /**
     * 设置appSize属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppSize(String value) {
        this.appSize = value;
    }

    /**
     * 获取totalDownloadNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalDownloadNum() {
        return totalDownloadNum;
    }

    /**
     * 设置totalDownloadNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalDownloadNum(String value) {
        this.totalDownloadNum = value;
    }

    /**
     * 获取downloadUrl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * 设置downloadUrl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadUrl(String value) {
        this.downloadUrl = value;
    }

    /**
     * 获取downloadTrackingUrl属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDownloadTrackingUrl() {
        return downloadTrackingUrl;
    }

    /**
     * 设置downloadTrackingUrl属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDownloadTrackingUrl(String value) {
        this.downloadTrackingUrl = value;
    }

}


package com.iwanvi.adserv.ngx.router.broker.xiaomi.video;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element ref="{}AdSystem"/>
 *         &lt;element ref="{}AdTitle"/>
 *         &lt;element ref="{}Impression" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}Creatives"/>
 *         &lt;element ref="{}Description"/>
 *         &lt;element ref="{}Extensions"/>
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
    "adSystem",
    "adTitle",
    "impression",
    "creatives",
    "description",
    "extensions"
})
@XmlRootElement(name = "InLine")
public class InLine {

    @XmlElement(name = "AdSystem", required = true)
    protected AdSystem adSystem;
    @XmlElement(name = "AdTitle", required = true)
    protected String adTitle;
    @XmlElement(name = "Impression")
    protected List<String> impression;
    @XmlElement(name = "Creatives", required = true)
    protected Creatives creatives;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Extensions", required = true)
    protected Extensions extensions;

    /**
     * 获取adSystem属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AdSystem }
     *     
     */
    public AdSystem getAdSystem() {
        return adSystem;
    }

    /**
     * 设置adSystem属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AdSystem }
     *     
     */
    public void setAdSystem(AdSystem value) {
        this.adSystem = value;
    }

    /**
     * 获取adTitle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdTitle() {
        return adTitle;
    }

    /**
     * 设置adTitle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdTitle(String value) {
        this.adTitle = value;
    }

    /**
     * Gets the value of the impression property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the impression property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImpression().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getImpression() {
        if (impression == null) {
            impression = new ArrayList<String>();
        }
        return this.impression;
    }

    /**
     * 获取creatives属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Creatives }
     *     
     */
    public Creatives getCreatives() {
        return creatives;
    }

    /**
     * 设置creatives属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Creatives }
     *     
     */
    public void setCreatives(Creatives value) {
        this.creatives = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取extensions属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Extensions }
     *     
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /**
     * 设置extensions属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Extensions }
     *     
     */
    public void setExtensions(Extensions value) {
        this.extensions = value;
    }

}

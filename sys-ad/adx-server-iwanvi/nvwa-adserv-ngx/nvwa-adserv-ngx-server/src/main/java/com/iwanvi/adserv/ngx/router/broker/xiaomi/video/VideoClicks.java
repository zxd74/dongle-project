
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
 *         &lt;element ref="{}ClickThrough"/>
 *         &lt;element ref="{}ClickTracking" maxOccurs="unbounded" minOccurs="0"/>
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
    "clickThrough",
    "clickTracking"
})
@XmlRootElement(name = "VideoClicks")
public class VideoClicks {

    @XmlElement(name = "ClickThrough", required = true)
    protected String clickThrough;
    @XmlElement(name = "ClickTracking")
    protected List<String> clickTracking;

    /**
     * 获取clickThrough属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClickThrough() {
        return clickThrough;
    }

    /**
     * 设置clickThrough属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClickThrough(String value) {
        this.clickThrough = value;
    }

    /**
     * Gets the value of the clickTracking property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clickTracking property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClickTracking().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getClickTracking() {
        if (clickTracking == null) {
            clickTracking = new ArrayList<String>();
        }
        return this.clickTracking;
    }

}
